package condor.sales.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import condor.sales.Constants;

import java.io.IOException;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import cn.pedant.SweetAlert.SweetAlertDialog;
import condor.sales.Dialogs.DialogRecapReception;
import condor.sales.Login.LoginActivity;
import condor.sales.Models.Command;
import condor.sales.Models.Incentive;
import condor.sales.Models.Product;
import condor.sales.Models.ProductModel;
import condor.sales.Models.ProductRange;
import condor.sales.Models.Recap;
import condor.sales.Models.Reception;
import condor.sales.Models.Stock;
import condor.sales.R;
import condor.sales.Utils.Utils;
import me.sudar.zxingorient.ZxingOrient;
import me.sudar.zxingorient.ZxingOrientResult;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static condor.sales.Constants.currentCommandDetails;
import static condor.sales.Constants.custom_font;
import static condor.sales.Constants.list_users;
import static condor.sales.Constants.url_API_Sync;
import static condor.sales.Constants.url_API_login;
import static condor.sales.Constants.url_API_reception;
import static condor.sales.Utils.Utils.getDeviceMetrics;
import static condor.sales.Utils.Utils.getProduct;
import static condor.sales.Utils.Utils.getReception;
import static condor.sales.Utils.Utils.getVersionCode;
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


public class ReceptionActivity extends AppCompatActivity {
    public static ArrayList<String> localReception = new ArrayList<String>();
    SweetAlertDialog mDialogexit;
    ImageView closereception;
    Button next,done;
    EditText serialnumber;
    TextView message;
    Reception rec;
    SweetAlertDialog mConnextionEncours;
    SweetAlertDialog mSyncEncours;

    boolean sync_encours = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reception);
        localReception = new ArrayList<String>();
        Constants.Reception = getReception(ReceptionActivity.this);
        sacn();

        closereception = findViewById(R.id.close_reception);
        next = findViewById(R.id.next_btn);
        done = findViewById(R.id.done_btn);
        message = findViewById(R.id.messagetext);
        serialnumber = findViewById(R.id.serailnumber);
//        serialnumber.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable editable) {
//                String sn = serialnumber.getText().toString();
//                if(sn.length() == 17 ){
//
//
//                }
//
//            }
//        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String sn = serialnumber.getText().toString();

                if(sn.length() > 5 ){

                if(localReception.contains(sn)){
//                    message.setTextColor(getResources().getColor(R.color.circleble3));
//                    message.setText("le dernier numero de serie scanné SN a déjà été scanné");
                    Log.e("Reception List", ""+localReception);
                }else{
//                    message.setTextColor(getResources().getColor(R.color.condorgreen));
//                    message.setText("Le sn : "+sn+" a été scanné");
                    localReception.add(sn);
                    Log.e("Reception List", ""+localReception);

                }
                message.setText("");
                serialnumber.setText("");
                sacn();
                }
            }
        });
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                done.setEnabled(false);
                String sn = serialnumber.getText().toString();

                if(sn.length() > 5 ){

                    if(localReception.contains(sn)){
//                    message.setTextColor(getResources().getColor(R.color.circleble3));
//                    message.setText("le dernier numero de serie scanné SN a déjà été scanné");
                        Log.e("Reception List", ""+localReception);
                    }else{
//                    message.setTextColor(getResources().getColor(R.color.condorgreen));
//                    message.setText("Le sn : "+sn+" a été scanné");
                        localReception.add(sn);
                        Log.e("Reception List", ""+localReception);

                    }
                    message.setText("");
                    serialnumber.setText("");
                }
                savelist(localReception ,Constants.Reception);
                saveInStock(localReception,Constants.Reception);
                Log.e("stock List", ""+Constants.stock.size());


            }
        });

        closereception.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                exit ();

            }
        });



    }

    public Reception rec (String refe , ArrayList<Reception> recs) {
        if(recs == null){
            return null;
        }else{

            for (final Reception rec : recs) {
                // Access properties of person, usage of getter methods would be good
                if (rec.getSerialnumber().equals(refe)) {
                    // Found matching person
                    return rec;
                }
            }
        }


        // Traversed whole list but did not find a matching person
        return null;
    }

    public void savelist (ArrayList<String>  locallist, ArrayList<Reception> recs) {
//        if(recs == null || locallist == null){
//
//        }else{
//            for (final String localelement : locallist){
//
//                for (final Reception rec : recs) {
//                    // Access properties of person, usage of getter methods would be good
//                    if (rec.getSerialnumber().equals(localelement)) {
//                        rec.setDate_rec( Calendar.getInstance().getTime());
//                        Utils.saveSync("false", ReceptionActivity.this);
//                    }
//                }
//            }
//
//            saveReception(recs,ReceptionActivity.this);
//        }


//todo send http request to the server so we can have the movment linked to pos then we have to add it to local reception list

        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                showDialogLoading();
            }

        });


        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.serializeNulls();
        Gson gson = gsonBuilder.create();
        String locallist_json =  gson.toJson(locallist);
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(20, TimeUnit.SECONDS)
                .callTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .retryOnConnectionFailure(false)
                .build();


        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("serial_numbers", locallist_json)
                .build();

        Request request = new Request.Builder()
                .url(url_API_reception)
                .post(requestBody)
                .addHeader("Accept", "application/json")
                .addHeader("Authorization", "Bearer " + list_users.get(0).getToken())
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                // inser shared pref actualite last
                //Log.e("onFailure", "" + e);

               showToastFromBackground("nointernet",null);
               closeDialogLoading();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Utils.saveSync("false", ReceptionActivity.this);
                String json_resultStr = response.body().string();
                if (json_resultStr.contains("Unauthenticated")) {
                    Log.e("Unauthenticated","Unauthenticated");
                    showToastFromBackground("Unauthenticated",null);
                    closeDialogLoading();
                }

                if( !json_resultStr.contains("Unauthenticated")){
                    try {
                       Gson gson = new Gson();
                        ArrayList<Recap>  Receptionfromserver = new ArrayList<Recap>();
                        JSONObject jsonobjetc = new JSONObject(json_resultStr);
                        JSONArray recap = jsonobjetc.getJSONArray("movements_to_pos");
                        Type recaptype = new TypeToken<ArrayList<Recap>>(){}.getType();
                        Receptionfromserver = gson.fromJson(recap.toString(),recaptype) ;
                        showToastFromBackground("recap",Receptionfromserver);

//                        JSONArray reception_for_pos = jsonobjetc.getJSONArray("movements_to_pos");
//                        Type receptiontype = new TypeToken<ArrayList<Reception>>(){}.getType();
//                        Receptionfromserver = gson.fromJson(reception_for_pos.toString(),receptiontype) ;
//                        closeDialogLoading();

                        Log.e("Reception data",""+json_resultStr);

                    } catch (JSONException e) {
                        Log.e("TagAcceuil", "*** sync json_result exeption ***  ");
                        Log.e("e", ""+e);
                        //showToastFromBackground("nointernet");
                        showToastFromBackground("error",null);
                        closeDialogLoading();
                        e.printStackTrace();
                    }

                }

                }

        });


    }

    private void showDialogLoading() {
        sync_encours = true;
        mSyncEncours = new SweetAlertDialog(ReceptionActivity.this, SweetAlertDialog.PROGRESS_TYPE);
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

    private void closeDialogLoading() {

        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                sync_encours = false;
                mSyncEncours.dismissWithAnimation();
            }

        });


    }

    public void showToastFromBackground(final String message , final ArrayList<Recap> recaplist) {

            this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    //lastToast = Toast.makeText(WilayaPicker.this, message, Toast.LENGTH_LONG);
                    //lastToast.show();
                    if (message.equals("recap")) {
                        DialogRecapReception cdd = new DialogRecapReception(ReceptionActivity.this ,recaplist);
                        cdd.setCancelable(false);
                        cdd.setOnDismissListener(new DialogInterface.OnDismissListener() {
                            @Override
                            public void onDismiss(DialogInterface dialogInterface) {
                           finish();
                            }
                        });
                        cdd.show();


                    }
                    if (message.equals("nointernet")) {
//                        Dialog_Connexion cdd=new Dialog_Connexion( Splash.this);
//                        cdd.setCancelable(false);
//                        cdd.show();
//                        sync_btn.setAnimation("notsure.json");
//                        sync_btn.playAnimation();
                       Toast.makeText(ReceptionActivity.this, "Cette opération doit être effectuée en ligne, Veuillez vérifier votre connexion Internet et réessayez", Toast.LENGTH_LONG).show();


                    }

                    if (message.equals("error")) {
//                        Dialog_Connexion cdd=new Dialog_Connexion( Splash.this);
//                        cdd.setCancelable(false);
//                        cdd.show();



                    }



                }
            });

    }

    public void saveInStock ( ArrayList<String> referlist ,ArrayList<Reception>  recs) {

        if( referlist == null){


        }else{


            for (final String localelement : referlist){

                for (final Reception rec : recs) {
                    // Access properties of person, usage of getter methods would be good

                    if (rec.getSerialnumber().equals(localelement)) {
                        boolean finder = false;
                        for (final Stock stock : Constants.stock) {
                            if (rec.getCommecial_name().equals(stock.getProductname())) {
                                finder=true;
                                stock.setQuantity(stock.getQuantity()+1);
                            }

                        }

                        if(!finder){
                            String productref = "" ;
                            ArrayList<Product> products = getProduct(ReceptionActivity.this);
                            for (final Product product : products) {
                                Log.e("working", "ref" );
                                if(product.getProduct_id().equals(rec.getProduct_id())){
                                    productref = product.getProductreference();
                                    break;
                                }

                            }

                            Stock s = new Stock(rec.getCommecial_name(),productref,1);
                            Constants.stock.add(s);
                        }
                    }
                }
            }

            saveStock( Constants.stock,ReceptionActivity.this);
        }


    }

    public Date StringToDate(String s){

        Date result = null;
        try{
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            result  = dateFormat.parse(s);
        }

        catch(ParseException e){
            e.printStackTrace();

        }
        return result ;
    }

    private void exit () {


        mDialogexit = new SweetAlertDialog(ReceptionActivity.this, SweetAlertDialog.WARNING_TYPE);

        mDialogexit.setTitleText("Annuler l'operation")
                .setContentText("Voulez-vous vraiment annuler l'operation sans enregistré les données")
                .setConfirmText("Non")
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {

                        mDialogexit.dismissWithAnimation();
                    }
                })
                .setCancelText("Oui, Annuler")
                .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {

                        mDialogexit.changeAlertType(SweetAlertDialog.PROGRESS_TYPE);
                        mDialogexit.setContentText(getString(R.string.logout_laoding));
                        mDialogexit.getProgressHelper().setBarColor(getResources().getColor(R.color.circleble3));
                        mDialogexit.setCancelable(false);
                        localReception.clear();
                        Toast.makeText(ReceptionActivity.this, "Opération annulé", Toast.LENGTH_LONG).show();
                        finish();

                    }
                });

        ReceptionActivity.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mDialogexit.show();
            }

        });




    }

    public void sacn(){
        ZxingOrient integrator = new ZxingOrient(ReceptionActivity.this);
        integrator.setIcon(R.drawable.conwhite)  // Sets the custom icon.setIcon(R.drawable.custom_icon)
                .setToolbarColor("#051A42")
                .setInfoBoxColor("#051A42")
                .setInfo("Scanner le numero de serie du produit.")
                .initiateScan();
    }



    @Override
    public void onBackPressed() {
        exit ();


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent){

        ZxingOrientResult scanResult =
                ZxingOrient.parseActivityResult(requestCode, resultCode, intent);

        if (scanResult != null) {


            if(scanResult.getContents() == null) {
                Toast.makeText(this, "Opération annulé", Toast.LENGTH_LONG).show();

            } else {

                serialnumber.setText(scanResult.getContents());

            }


        } else {
            super.onActivityResult(requestCode, resultCode, intent);
        }


    }



}
