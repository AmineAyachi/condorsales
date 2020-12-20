package condor.sales.Activities;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import com.airbnb.lottie.LottieAnimationView;
import com.airbnb.lottie.LottieDrawable;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import cn.pedant.SweetAlert.SweetAlertDialog;
import condor.sales.Constants;
import condor.sales.Login.LoginActivity;
import condor.sales.Models.Command;
import condor.sales.Models.CommandDetails;
import condor.sales.Models.Incentive;
import condor.sales.Models.News;
import condor.sales.Models.Product;
import condor.sales.Models.ProductModel;
import condor.sales.Models.ProductRange;
import condor.sales.Models.Reception;
import condor.sales.Models.Stock;
import condor.sales.R;
import condor.sales.Utils.Utils;
import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import timber.log.Timber;

import static condor.sales.Constants.custom_font;
import static condor.sales.Constants.list_users;
import static condor.sales.Constants.url_API_Sync;
import static condor.sales.Constants.url_API_fb_token;
import static condor.sales.Utils.Utils.getCurrentCommandDtails;
import static condor.sales.Utils.Utils.getDeviceMetrics;
import static condor.sales.Utils.Utils.getHistorique;
import static condor.sales.Utils.Utils.getIncentive;
import static condor.sales.Utils.Utils.getNews;
import static condor.sales.Utils.Utils.getProduct;
import static condor.sales.Utils.Utils.getReception;
import static condor.sales.Utils.Utils.getStock;
import static condor.sales.Utils.Utils.getSync;
import static condor.sales.Utils.Utils.saveAllProducts;
import static condor.sales.Utils.Utils.saveCommand;
import static condor.sales.Utils.Utils.saveHistorique;
import static condor.sales.Utils.Utils.saveIncentive;
import static condor.sales.Utils.Utils.saveProduct;
import static condor.sales.Utils.Utils.saveProductModel;
import static condor.sales.Utils.Utils.saveProductRange;
import static condor.sales.Utils.Utils.saveReception;
import static condor.sales.Utils.Utils.saveStock;
import static condor.sales.Utils.Utils.saveSync;
import static condor.sales.Utils.Utils.saveUsers;


    public class AcceuilActivity extends AppCompatActivity {
    private String scanmode = "";
    private boolean doubleBackToExitPressedOnce;
    private final Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            doubleBackToExitPressedOnce = false;
        }
    };
    private Handler mHandler = new Handler();
    private TextView incentive ;
    static View view_news;

    // TODO: 23-03-2020  incentive should have history directly here and not in history activity i will work on that too.
    Runnable runnable;
    SweetAlertDialog mSyncEncours;
    boolean sync_encours = false;
    public static TextView actualiteTxt ;
    private CircleImageView m_buy , m_sell , m_stock , m_command, m_historic ;
    private boolean started = false;
    private Handler handler = new Handler();
    private DrawerLayout mDrawer;
    private ImageView mOpendrawer_btn;
    private NavigationView mNavigationView;
    private RelativeLayout incentive_header;
    private boolean mSlideState = false ;
    public static boolean item_clicked=false;
    LottieAnimationView sync_btn;
    String sync;
    SweetAlertDialog mDialogDeconnexion;
    SharedPreferences.Editor editprefLogin;
    private FirebaseAnalytics mFirebaseAnalytics;

    BroadcastReceiver receiver=new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // intent will holding data show the data here
            refreshnews ();

        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acceuil);
        FirebaseInstanceId.getInstance().getInstanceId().addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
            @Override
            public void onComplete(@NonNull Task<InstanceIdResult> task) {
                if (task.isSuccessful()) {

                    String token = task.getResult().getToken();

                    Log.e( "token fb : ",token );

                    OkHttpClient clientfb = new OkHttpClient();

                    RequestBody requestBodyfb = new MultipartBody.Builder()
                            .setType(MultipartBody.FORM)
                            .addFormDataPart("token", token )
                            .build();
                    Request request = new Request.Builder()
                            .url(url_API_fb_token)
                            .post(requestBodyfb)
                            .addHeader("Accept", "application/json")
                            .addHeader("Authorization", "Bearer " + list_users.get(0).getToken())
                            .build();

                    clientfb.newCall(request).enqueue(new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                            Log.e( "fff","token not sent "+e );
                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            Log.e( "fff","token sent " );
                        }
                    });



                }


            }
        });
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        incentive = findViewById(R.id.incentive_value);
        sync = getSync(AcceuilActivity.this);
        Constants.Reception = getReception(AcceuilActivity.this);
        Constants.stock = getStock(AcceuilActivity.this);
        Constants.Incentive = getIncentive(AcceuilActivity.this);
        float incentive_value = findCurrentIncentive();
        DecimalFormat decimalFormat = new DecimalFormat("#,##0.00");
        String numberAsString = decimalFormat.format(incentive_value);
        Log.e("numberAsString", ":"+numberAsString);
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.ITEM_ID, "here i am");
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME,  "here i am");
        bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "image");
        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);
        incentive.setText(numberAsString);
        mDrawer = findViewById(R.id.mdrawer);
        incentive_header = findViewById(R.id.incentive_header);
        mDrawer.setScrimColor(Color.TRANSPARENT);
        mNavigationView = findViewById(R.id.nav_view);
        sync_btn = findViewById(R.id.sync_btn);
        if(sync.equals("false")){
            sync_btn.setAnimation("notsynced.json");
            sync_btn.playAnimation();

        }else{
            sync_btn.setAnimation("synced.json");
            sync_btn.playAnimation();
        }


        sync_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startsyncing ("false");

            }
        });

        View headerview = mNavigationView.getHeaderView(0);
        ImageView closedrawer = headerview.findViewById(R.id.close_drawer);
        closedrawer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDrawer.closeDrawer(Gravity.LEFT);
            }
        });

        RelativeLayout rl = headerview.findViewById(R.id.bot_rl);
        rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDrawer.closeDrawer(Gravity.LEFT);
                profile();

            }
        });

        incentive_header.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                incentive ();
            }
        });



        mOpendrawer_btn = findViewById(R.id.opendrawer_btn);


        actualiteTxt = findViewById(R.id.new_offers_banner);
        view_news = findViewById(R.id.view_bottom_news);

        actualiteTxt.setSelected(true);




        refreshnews ();
        m_buy = findViewById(R.id.fm_buy);
        m_sell = findViewById(R.id.fm_sell);
        m_stock = findViewById(R.id.stock);
        m_command = findViewById(R.id.command);
        m_historic  = findViewById(R.id.historique);


        m_buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                achat();
            }
        });
        m_sell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                vente();

            }
        });

        m_stock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stock();

            }
        });

        m_command.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                command();
            }
        });

        m_historic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                historique();


            }
        });

        mOpendrawer_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mSlideState){
                    mDrawer.closeDrawer(Gravity.LEFT);
                }else{
                    mDrawer.openDrawer(GravityCompat.START);
                }
            }
        });


        mDrawer.addDrawerListener(new ActionBarDrawerToggle(this,
                mDrawer,
                0,
                0){
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                mSlideState=false;//is Closed
            }
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                mSlideState=true;//is Opened
            }});




        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                menuItem.setChecked(true);
                switch (menuItem.getItemId()) {
                    case R.id.acceuil:
                        mDrawer.closeDrawer(Gravity.LEFT);
                        return true;
                    case R.id.incentive:
                        incentive();
                        return true;
                    case R.id.achat:
                        achat();
                        return true;
                    case R.id.vente:
                        vente();
                        return true;
                    case R.id.about:
                        about();
                        return true;
                    case R.id.logout:
                        LogOUT();
                        return true;

                    default:
                        return true;
                }
            }


        });





    }


    public void refreshnews () {
        String newsmsg = "";
        ArrayList<News> news = getNews(this);
        for (final News n : news) {
            newsmsg += ""+n.getTxt()+"   ✸✸✸   ";
        }
        actualiteTxt.setText(newsmsg);
        actualiteTxt.setSelected(true);
    }

    public void historique () {
        mDrawer.closeDrawer(Gravity.LEFT);
        Intent myIntent = new Intent(AcceuilActivity.this, HistoriqueActivity.class);
        startActivity(myIntent);


    }

    public void startsyncing (String sync) {
       if(sync.equals("false")){

           sync_btn.setAnimation("notsure.json");
           sync_btn.playAnimation();
           ArrayList<Stock> syncStock = new ArrayList<Stock>();
           ArrayList<Reception>  syncReception = new ArrayList<Reception>();
           ArrayList<Reception> syncHistorique = new ArrayList<Reception>();
           ArrayList<Incentive>  syncIncentive = new ArrayList<Incentive>();
           ArrayList<Product>  syncProduct = new ArrayList<Product>();
           ArrayList<CommandDetails>  syncCommand = new ArrayList<CommandDetails>();

           syncStock  = getStock(AcceuilActivity.this);

           syncReception  = getReception(AcceuilActivity.this);
           syncHistorique = getHistorique(AcceuilActivity.this);
           syncIncentive  = getIncentive(AcceuilActivity.this);
           syncProduct = getProduct(AcceuilActivity.this);
           if(Utils.CCIsSyncable(AcceuilActivity.this)){
               syncCommand= getCurrentCommandDtails(AcceuilActivity.this);

           }

           if (syncStock == null) {
               syncStock = new ArrayList<Stock>();
           }
           if (syncReception == null) {
               syncReception = new ArrayList<Reception>();
           }
           if (syncHistorique == null) {
               syncHistorique = new ArrayList<Reception>();
           }
           if (syncIncentive == null) {
               syncIncentive = new ArrayList<Incentive>();
           }
           if (syncProduct == null) {
               syncProduct = new ArrayList<Product>();
           }

           if (syncCommand == null) {
               syncCommand = new ArrayList<CommandDetails>();
           }



           GsonBuilder gsonBuilder = new GsonBuilder();
           gsonBuilder.serializeNulls();
           // This is the main class for using Gson. Gson is typically used by first constructing a Gson instance and then invoking toJson(Object) or fromJson(String, Class) methods on it.
           // Gson instances are Thread-safe so you can reuse them freely across multiple threads.
           Gson gson = gsonBuilder.create();


           String syncStock_json =  gson.toJson(syncStock);
           String syncReception_json = gson.toJson(syncReception);
           String syncHistorique_json = gson.toJson(syncHistorique);
           String syncIncentive_json = gson.toJson(syncIncentive);
           String syncCommand_json = gson.toJson(syncCommand);

           if(!sync_encours && !list_users.get(0).getToken().isEmpty()){

               syncWithServer (syncStock_json,syncReception_json, syncHistorique_json, syncIncentive_json, syncCommand_json, syncHistorique );

           }





       }


    }


    public void syncWithServer (String syncStockJson , String syncReceptionJson, String syncHistoriqueJson, String  syncIncentiveJson, String syncCommand_json , final ArrayList<Reception> syncHistorique) {

        AcceuilActivity.this.runOnUiThread(new Runnable() {
            public void run() {
                showDialogLoading();
            }
        });



        OkHttpClient client = new OkHttpClient();

        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("sync_stock", "" + syncStockJson)
                .addFormDataPart("sync_reception", "" + syncReceptionJson)
                .addFormDataPart("sync_historique", "" + syncHistoriqueJson)
                .addFormDataPart("sync_incentive", "" + syncIncentiveJson)
                .addFormDataPart("sync_command", "" + syncCommand_json)
                .build();

        Request request = new Request.Builder()
                .url(url_API_Sync)
                .post(requestBody)
                .addHeader("Accept", "application/json")
                .addHeader("Authorization", "Bearer " + list_users.get(0).getToken())
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                // inser shared pref actualite last
                //Log.e("onFailure", "" + e);

                showToastFromBackground("nointernet");
                closeDialogLoading();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final Calendar cc = Calendar.getInstance();
                SimpleDateFormat df = new SimpleDateFormat("dd-MM-yy HH:mm:ss");
                String formattedDate = df.format(cc.getTime());
                list_users.get(0).setLastsync(formattedDate);
                String json_resultStr = response.body().string();
                saveUsers(list_users, AcceuilActivity.this);
                Log.e("TagAcceuil", "*** sync json_result *** -----> " + json_resultStr);
                boolean sync_success = true;
                if( Utils.CCIsSyncable(AcceuilActivity.this)){
                    Utils.setCCnotsyncable(AcceuilActivity.this);
                    Utils.deleteCurrentCommand(AcceuilActivity.this) ;
                }


                if (json_resultStr.contains("Unauthenticated")) {
                    Log.e("Unauthenticated","Unauthenticated");
                    showToastFromBackground("Unauthenticated");
                    closeDialogLoading();
                }


 if( !json_resultStr.contains("Unauthenticated")){
                    try {
                        Gson gson = new Gson();
                        ArrayList<Reception>  Receptionfromserver = new ArrayList<Reception>();
                        ArrayList<Product>  productFromServer = new ArrayList<Product>();
                        ArrayList<Stock>  stockFromServer = new ArrayList<Stock>();
                        ArrayList<ProductModel>  modelFromServer = new ArrayList<ProductModel>();
                        ArrayList<ProductRange>  rangesFromServer = new ArrayList<ProductRange>();
                        ArrayList<Product>  productsFromServer = new ArrayList<Product>();
                        ArrayList<Command>  commandhistoryFromServer = new ArrayList<Command>();

                        JSONObject jsonobjetc = new JSONObject(json_resultStr);
                        JSONArray reception_for_pos = jsonobjetc.getJSONArray("reception_for_pos");
                        JSONArray incentive_list = jsonobjetc.getJSONArray("incentive_list");
                        JSONArray products_list = jsonobjetc.getJSONArray("products_list");
                        JSONArray stock_list = jsonobjetc.getJSONArray("stock_list");
                        JSONArray model_list = jsonobjetc.getJSONArray("models_for_pos");
                        JSONArray range_list = jsonobjetc.getJSONArray("ranges_for_pos");
                        JSONArray all_products_list = jsonobjetc.getJSONArray("all_products_for_pos");
                        JSONArray commandhistory_list = jsonobjetc.getJSONArray("orders_of_pos");

                        Type commandhtype = new TypeToken<ArrayList<Command>>(){}.getType();
                        commandhistoryFromServer = gson.fromJson(commandhistory_list.toString(),commandhtype) ;

                        Type modeltype = new TypeToken<ArrayList<ProductModel>>(){}.getType();
                        modelFromServer = gson.fromJson(model_list.toString(),modeltype) ;
                        Type rangetype = new TypeToken<ArrayList<ProductRange>>(){}.getType();
                        rangesFromServer = gson.fromJson(range_list.toString(),rangetype) ;
                        Type producttype = new TypeToken<ArrayList<Product>>(){}.getType();
                        productsFromServer = gson.fromJson(all_products_list.toString(),producttype) ;
                        saveProductModel(modelFromServer , AcceuilActivity.this);
                        saveProductRange(rangesFromServer , AcceuilActivity.this);
                        saveAllProducts(productsFromServer , AcceuilActivity.this);
                        saveCommand(commandhistoryFromServer , AcceuilActivity.this);
                        for (int i = 0; i < stock_list.length(); i++) {
                            JSONObject jsonobject = stock_list.getJSONObject(i);
                            int quantity =0 ;
                            try {
                                quantity = Integer.parseInt(jsonobject.getString("quantity"));

                            }
                            catch (NumberFormatException e)
                            {
                                quantity =0;

                            }

                            Stock stock = new Stock(jsonobject.getString("commecial_name"),jsonobject.getString("code"),quantity);
                            stockFromServer.add(stock);
                        }
                        saveStock(stockFromServer,AcceuilActivity.this);

                        for (int i = 0; i < products_list.length(); i++) {
                            JSONObject jsonobject = products_list.getJSONObject(i);
                            int incentive =0 ;
                            try {
                                incentive = Integer.parseInt(jsonobject.getString("product_incentive"));

                            }
                            catch (NumberFormatException e)
                            {
                                incentive =0;

                            }

                            Product product = new Product(jsonobject.getString("product_id"),jsonobject.getString("product_reference"),jsonobject.getString("product_name"),Float.parseFloat(jsonobject.getString("product_price")),jsonobject.getString("model_id"),incentive);
                            productFromServer.add(product);
                        }
                        saveProduct(productFromServer,AcceuilActivity.this);
                        for (int i = 0; i < reception_for_pos.length(); i++) {
                            JSONObject jsonobject = reception_for_pos.getJSONObject(i);
                            int incentive =0 ;
                            boolean free = false;
                            free = jsonobject.getString("free").equals("1");
                            float price =0;
                            //2020-02-15 23:00:00
                            Date date_rec  =  stringTodate (jsonobject.getString("date_rec"));
                            Date date_vente = stringTodate (jsonobject.getString("date_vente")) ;
                            Log.e("AcceuilActivity", "incentive: "+jsonobject.getString("incentive"));
                            try {
                                incentive = Integer.parseInt(jsonobject.getString("incentive"));
                                price = Float.parseFloat(jsonobject.getString("price"));
                            }
                            catch (NumberFormatException e)
                            {
                                incentive =0;
                                price = 0;
                            }
                            Log.e("date_rec",""+jsonobject.getString("date_vente") );


                            Reception reception = new Reception( jsonobject.getString("product_id"),jsonobject.getString("commecial_name"), jsonobject.getString("serialnumber"), incentive, free ,date_rec ,date_vente,price);
                            Receptionfromserver.add(reception);

                        }

                        saveReception(Receptionfromserver,AcceuilActivity.this);
                        Constants.Incentive.clear();
                        for (int i = 0; i < incentive_list.length(); i++) {
                            JSONObject jsonobject = incentive_list.getJSONObject(i);
                            boolean checked = false;
                            if (jsonobject.getString("checked") == "1"){
                                checked = true;
                            }
                            Incentive incentive = new Incentive(jsonobject.getInt("year"),jsonobject.getInt("month"), BigDecimal.valueOf(jsonobject.getDouble("incentive")).floatValue(),checked);
                            Constants.Incentive.add(incentive);
                            Log.e("incentive list",""+jsonobject.getString("month") );
                        }
                        saveIncentive( Constants.Incentive , AcceuilActivity.this);

                        if (sync_success) {

                            showToastFromBackground("sync_opp_finished");
                        } else {
                            showToastFromBackground("nointernet");
                        }



                        for (final Reception h_rec : syncHistorique) {
                            h_rec.setSynced(true);
                        }

                        saveHistorique(syncHistorique , AcceuilActivity.this);

                        float incentive_value = findCurrentIncentive();

                        Log.e("onResume","happend" + String.valueOf(incentive_value));
                        DecimalFormat decimalFormat = new DecimalFormat("#,##0.00");
                        final String numberAsString = decimalFormat.format(incentive_value);

                        AcceuilActivity.this.runOnUiThread(new Runnable() {
                                               @Override
                                               public void run() {
                                                   incentive.setText(numberAsString);
                                               }        });
                        closeDialogLoading();

                    } catch (JSONException e) {
                        Log.e("TagAcceuil", "*** sync json_result exeption ***  ");
                        Log.e("e", ""+e);
                        //  showToastFromBackground("nointernet");
                        showToastFromBackground("error");
                        closeDialogLoading();
                        e.printStackTrace();
                    }

                }


                //to make icon sync from red to green if the syn is correct
                //fct_refrech_sync();


            }
        });





    }
    Toast lastToast = null;

    boolean isToastNotRunning() {
        return (lastToast == null || lastToast.getView().getWindowVisibility() != View.VISIBLE);
    }


    public void showToastFromBackground(final String message) {
        if (isToastNotRunning()) {
            this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    //lastToast = Toast.makeText(WilayaPicker.this, message, Toast.LENGTH_LONG);
                    //lastToast.show();

                    if (message.equals("nointernet")) {
//                        Dialog_Connexion cdd=new Dialog_Connexion( Splash.this);
//                        cdd.setCancelable(false);
//                        cdd.show();
                        sync_btn.setAnimation("notsure.json");
                        sync_btn.playAnimation();
                        lastToast = Toast.makeText(AcceuilActivity.this, getString(R.string.connexion_verification), Toast.LENGTH_LONG);
                        lastToast.show();


                    }

                    if (message.equals("error")) {
//                        Dialog_Connexion cdd=new Dialog_Connexion( Splash.this);
//                        cdd.setCancelable(false);
//                        cdd.show();
                        sync_btn.setAnimation("notsure.json");
                        sync_btn.playAnimation();
                        lastToast = Toast.makeText(AcceuilActivity.this,"", Toast.LENGTH_LONG);
                        lastToast.show();


                    }


                    if (message.equals("done")) {


                        lastToast = Toast.makeText(AcceuilActivity.this, "done", Toast.LENGTH_LONG);
                        lastToast.show();

                    }
                    if (message.equals("sync_opp_finished")) {
                        sync = "true";
                        saveSync(sync , AcceuilActivity.this);
                        sync_btn.setAnimation("synced.json");
                        sync_btn.playAnimation();

                    }
                    if (message.equals("sync_opp_failed")) {

                        lastToast = Toast.makeText(AcceuilActivity.this, getString(R.string.sync_failed), Toast.LENGTH_LONG);
                        lastToast.show();
                        sync_btn.setAnimation("notsure.json");
                        sync_btn.playAnimation();
                    }

                    if (message.equals("Unauthenticated")) {
                        lastToast = Toast.makeText(AcceuilActivity.this, "Votre session a expiré, Merci de réessayer une autre fois  ", Toast.LENGTH_LONG);
                        lastToast.show();
                        sync = "false";
                        Utils.logMeIn(AcceuilActivity.this);
                        saveSync(sync , AcceuilActivity.this);
                        sync_btn.setAnimation("notsynced.json");
                        sync_btn.playAnimation();


                    }


                }
            });
        }
    }


    private void closeDialogLoading() {

        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                sync_encours = false;
                mSyncEncours.dismissWithAnimation();
            }

        });


    }
    private void showDialogLoading() {
        sync_encours = true;
        mSyncEncours = new SweetAlertDialog(AcceuilActivity.this, SweetAlertDialog.PROGRESS_TYPE);
        mSyncEncours.setContentText(getString(R.string.Patienter));
        mSyncEncours.setTitleText(getString(R.string.opp_sync_encours));
        mSyncEncours.getProgressHelper().setBarColor(getResources().getColor(R.color.circleble3));
        mSyncEncours.setCancelable(false);

        mSyncEncours.show();
        TextView titleView = mSyncEncours.findViewById(R.id.title_text);
        if (titleView != null) {
            titleView.setTypeface(custom_font);
            titleView.setWidth((int) (getDeviceMetrics(this).widthPixels * 0.9));
        }
        TextView messageView = mSyncEncours.findViewById(R.id.content_text);
        if (messageView != null) messageView.setTypeface(custom_font);

    }



    public void set_sync_btn (String state) {

        switch (state) {
            case "green":

                sync_btn.setAnimation("synced.json");
                sync_btn.playAnimation();
            case "orange":
                sync_btn.setAnimation("notsure.json");
                sync_btn.playAnimation();
                sync_btn.setRepeatCount(LottieDrawable.INFINITE);

            case "red":
                sync_btn.setAnimation("notsynced.json");
                sync_btn.playAnimation();
                sync_btn.setRepeatCount(LottieDrawable.INFINITE);
            case "cancel":
                sync_btn.cancelAnimation();
                sync_btn.setVisibility(View.GONE);
            default:

        }


    }

    public void incentive () {
        mDrawer.closeDrawer(Gravity.LEFT);
        Intent myIntent = new Intent(AcceuilActivity.this, IncentiveActivity.class);
        startActivity(myIntent);
//        DialogIncentiveHistory dih = new DialogIncentiveHistory(AcceuilActivity.this);
//        dih.setCancelable(false);
//        dih.setOnDismissListener(new DialogInterface.OnDismissListener() {
//            @Override
//            public void onDismiss(DialogInterface dialogInterface) {
//
//            }
//        });
//        dih.show();

    }

    public float findCurrentIncentive(){

        ArrayList<Incentive> incetives = Utils.getIncentive(AcceuilActivity.this);
        Log.e("findCurrentIncentive","");
        Calendar c = Calendar.getInstance();
        int month = c.get(Calendar.MONTH)+1;

        for (final Incentive incentive : incetives) {
         Log.e("findCurrentIncentive"," month:"+incentive.getMonth()+"calandar month:"+ month+"incentive year:"+incentive.getYear()+"calandar year"+c.get(Calendar.YEAR));
                if(incentive.getMonth() == month && incentive.getYear() == c.get(Calendar.YEAR)){
                return incentive.getIncentive();
            }

        }



        return 0;
    }




    public void command () {
        if (mDrawer.isDrawerOpen(Gravity.LEFT)) {

            mDrawer.closeDrawer(Gravity.LEFT);

        }

        Intent myIntent = new Intent(AcceuilActivity.this, CommandActivity.class);
        startActivity(myIntent);


    }
    public void stock () {
        if (mDrawer.isDrawerOpen(Gravity.LEFT)) {

            mDrawer.closeDrawer(Gravity.LEFT);

        }

        Intent myIntent = new Intent(AcceuilActivity.this, StockActivity.class);
        startActivity(myIntent);


    }

    public void about(){
        if (mDrawer.isDrawerOpen(Gravity.LEFT)) {

            mDrawer.closeDrawer(Gravity.LEFT);

        }

        Intent myIntent = new Intent(AcceuilActivity.this, AboutActivity.class);
        startActivity(myIntent);
    }

    public void profile (){
        if (mDrawer.isDrawerOpen(Gravity.LEFT)) {

            mDrawer.closeDrawer(Gravity.LEFT);

        }

        Intent myIntent = new Intent(AcceuilActivity.this, ProfileActivity.class);
        startActivity(myIntent);


    }

    public void achat() {
        if (mDrawer.isDrawerOpen(Gravity.LEFT)) {

            mDrawer.closeDrawer(Gravity.LEFT);

        }

        Intent myIntent = new Intent(AcceuilActivity.this, ReceptionActivity.class);
        startActivity(myIntent);
    }


    public void vente() {
        if (mDrawer.isDrawerOpen(Gravity.LEFT)) {

            mDrawer.closeDrawer(Gravity.LEFT);

        }

        Intent myIntent = new Intent(AcceuilActivity.this, VenteActivity.class);
        startActivity(myIntent);

    }







    @Override
    public void onBackPressed() {
        item_clicked = false;
        if (mDrawer.isDrawerOpen(Gravity.LEFT)) {

            mDrawer.closeDrawer(Gravity.LEFT);

        }

        if (doubleBackToExitPressedOnce) {
                super.onBackPressed();

                if (mHandler != null) {
                    mHandler.removeCallbacks(mRunnable);
                }

            }

            doubleBackToExitPressedOnce = true;
            Toast.makeText(this, "Cliquez à nouveau pour quitter", Toast.LENGTH_SHORT).show();
            mHandler.postDelayed(mRunnable, 2000);


        }


    private void LogOUT() {


        mDialogDeconnexion = new SweetAlertDialog(AcceuilActivity.this, SweetAlertDialog.WARNING_TYPE);
        mDialogDeconnexion.setTitleText(getString(R.string.logout_title))
                .setContentText(getString(R.string.logout_confirmation))
                .setConfirmText(getString(R.string.ok))
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        mDialogDeconnexion.changeAlertType(SweetAlertDialog.PROGRESS_TYPE);
                        mDialogDeconnexion.setContentText(getString(R.string.logout_laoding));
                        mDialogDeconnexion.getProgressHelper().setBarColor(getResources().getColor(R.color.condorbg));
                        mDialogDeconnexion.setCancelable(false);
                        editprefLogin = getSharedPreferences("MyPrefsLogin", MODE_PRIVATE).edit();
                        editprefLogin.putString("checked", "");
                        editprefLogin.apply();
                        mDialogDeconnexion.dismissWithAnimation();
                        Intent myIntent = new Intent(AcceuilActivity.this, LoginActivity.class);
                        startActivity(myIntent);
                        finish();


                    }
                })
                .setCancelText(getString(R.string.annuler))
                .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        mDialogDeconnexion.dismissWithAnimation();


                    }
                });


        AcceuilActivity.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mDialogDeconnexion.show();

            }

        });

        Button confirm_btn = mDialogDeconnexion.findViewById(R.id.confirm_button);
        Button annuler_btn = mDialogDeconnexion.findViewById(R.id.cancel_button);
        confirm_btn.setBackgroundResource(R.drawable.button);
        annuler_btn.setBackgroundResource(R.drawable.button);
        confirm_btn.setTypeface(custom_font);
        annuler_btn.setTypeface(custom_font);

        TextView titleView = mDialogDeconnexion.findViewById(R.id.title_text);
        TextView messageView = mDialogDeconnexion.findViewById(R.id.content_text);
        if (titleView != null) titleView.setTypeface(custom_font);
        if (messageView != null) messageView.setTypeface(custom_font);


    }

    private void showprofiledeatails(){


        mDialogDeconnexion = new SweetAlertDialog(AcceuilActivity.this, SweetAlertDialog.NORMAL_TYPE);
        LayoutInflater factory = LayoutInflater.from(this);
        View profile = factory.inflate(R.layout.profilesa, null);
        TextView p_email = profile.findViewById(R.id.profile_email);
        TextView p_name = profile.findViewById(R.id.profile_name);
        p_email.setText(list_users.get(0).getEmail());
        p_name.setText(list_users.get(0).getEmail());
        mDialogDeconnexion.setTitleText(getString(R.string.profile_title)).setCustomView(profile)
                .setContentText(getString(R.string.logout_confirmation))
                .setConfirmText(getString(R.string.pclose))
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        mDialogDeconnexion.dismissWithAnimation();

                    }
                });


        AcceuilActivity.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mDialogDeconnexion.show();
            }

        });

        Button confirm_btn = mDialogDeconnexion.findViewById(R.id.confirm_button);
        confirm_btn.setBackgroundResource(R.drawable.button);
        confirm_btn.setTypeface(custom_font);

        TextView titleView = mDialogDeconnexion.findViewById(R.id.title_text);
        TextView messageView = mDialogDeconnexion.findViewById(R.id.content_text);
        if (titleView != null) titleView.setTypeface(custom_font);
        if (messageView != null) messageView.setTypeface(custom_font);
    }

    @Override
    protected void onResume() {
        startsyncing (getSync(AcceuilActivity.this));
        if(getSync(AcceuilActivity.this).equals("false")){


            sync_btn.setAnimation("notsynced.json");
            sync_btn.playAnimation();
        }else{

            sync_btn.setAnimation("synced.json");
            sync_btn.playAnimation();
        }

        float incentive_value = findCurrentIncentive();

        Log.e("onResume","happend" + String.valueOf(incentive_value));
        DecimalFormat decimalFormat = new DecimalFormat("#,##0.00");
        String numberAsString = decimalFormat.format(incentive_value);
        incentive.setText(numberAsString);

        Log.e("onResume", "happend");
        super.onResume();
    }

    @Override
    protected void onStop(){
        unregisterReceiver(receiver);
        super.onStop();

    }

    @Override
    protected void onStart(){

        registerReceiver(receiver,new IntentFilter("com.push.message.received"));
        super.onStart();

    }
    public Date stringTodate (String date){
        Log.e("before time parse", ""+date);

        if(date == null ||date.equals("null")|| date.equals("") ){
    Log.e("time parse null", ""+date);
    return null ;
}
int year = Integer.parseInt(date.substring(0,4));
        Log.e("year", ""+year);
int month = Integer.parseInt(date.substring(5,7));
        Log.e("month", ""+month);
int day = Integer.parseInt(date.substring(8,10));
        Log.e("day", ""+day);
int hour = Integer.parseInt(date.substring(11,13));
        Log.e("hour", ""+hour);
int min = Integer.parseInt(date.substring(14,16));
        Log.e("min", ""+min);
int sec = Integer.parseInt(date.substring(17,19));
        Log.e("sec", ""+sec);
        Calendar c = Calendar.getInstance();
        c.set(year, month-1, day, hour, min,sec);
        Log.e("after time parse", ""+c.getTime().toString());
        return c.getTime() ;
    }
}
