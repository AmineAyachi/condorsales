package condor.sales.Utils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.os.Build;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import static android.content.Context.MODE_PRIVATE;
import static condor.sales.Constants.list_users;
import static condor.sales.Constants.pref_CCIsSyncable;
import static condor.sales.Constants.pref_all_news;
import static condor.sales.Constants.pref_all_products;
import static condor.sales.Constants.pref_command;
import static condor.sales.Constants.pref_current_command_details;
import static condor.sales.Constants.pref_historique;
import static condor.sales.Constants.pref_incentive;
import static condor.sales.Constants.pref_product;
import static condor.sales.Constants.pref_productmodel;
import static condor.sales.Constants.pref_productrange;
import static condor.sales.Constants.pref_reception;
import static condor.sales.Constants.pref_stock;
import static condor.sales.Constants.pref_sync;
import static condor.sales.Constants.pref_users;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import cn.pedant.SweetAlert.SweetAlertDialog;
import condor.sales.Dialogs.DialogLogout;
import condor.sales.Dialogs.DialogNewUpdate;
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
import condor.sales.Models.User;
import condor.sales.R;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import static condor.sales.Constants.myPref;
import static condor.sales.Constants.url_API_login;

public class Utils {

    public static Typeface custom_font;
    static SweetAlertDialog mSyncEncours;
    static SharedPreferences.Editor editprefLogin;


    private static String CURRENT_TAG = null;

    public static DisplayMetrics getDeviceMetrics(Context context) {
        DisplayMetrics metrics = new DisplayMetrics();
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        display.getMetrics(metrics);
        return metrics;
    }


    public static ArrayList<News> getNews(Context c) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.serializeNulls();
        // This is the main class for using Gson. Gson is typically used by first constructing a Gson instance and then invoking toJson(Object) or fromJson(String, Class) methods on it.
        // Gson instances are Thread-safe so you can reuse them freely across multiple threads.
        Gson gson = gsonBuilder.create();
        ArrayList<News> allNewsFromShard = new ArrayList<News>();
        SharedPreferences sharedPref = c.getSharedPreferences(myPref, MODE_PRIVATE);
        String jsonPreferences = sharedPref.getString(pref_all_news, "");

        Type type = new TypeToken<ArrayList<News>>() {
        }.getType();
        allNewsFromShard = gson.fromJson(jsonPreferences, type);
        if (allNewsFromShard == null) {
            allNewsFromShard = new ArrayList<News>();

        }
        return allNewsFromShard;
    }


    public static void saveNews (ArrayList<News> mAllNews, Context c) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.serializeNulls();
        // This is the main class for using Gson. Gson is typically used by first constructing a Gson instance and then invoking toJson(Object) or fromJson(String, Class) methods on it.
        // Gson instances are Thread-safe so you can reuse them freely across multiple threads.
        Gson gson = gsonBuilder.create();
        String jsoncurCode = gson.toJson(mAllNews);
        SharedPreferences sharedPref = c.getSharedPreferences(myPref, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();

        editor.putString(pref_all_news, jsoncurCode);
        editor.apply();
    }


    public static ArrayList<Product> getAllProducts(Context c) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.serializeNulls();
        // This is the main class for using Gson. Gson is typically used by first constructing a Gson instance and then invoking toJson(Object) or fromJson(String, Class) methods on it.
        // Gson instances are Thread-safe so you can reuse them freely across multiple threads.
        Gson gson = gsonBuilder.create();
        ArrayList<Product> allProductsFromShared = new ArrayList<Product>();
        SharedPreferences sharedPref = c.getSharedPreferences(myPref, MODE_PRIVATE);
        String jsonPreferences = sharedPref.getString(pref_all_products, "");

        Type type = new TypeToken<ArrayList<Product>>() {
        }.getType();
        allProductsFromShared = gson.fromJson(jsonPreferences, type);
        if (allProductsFromShared == null) {
            allProductsFromShared = new ArrayList<Product>();

        }
        return allProductsFromShared;
    }

    public static void saveAllProducts (ArrayList<Product> mAllProducts, Context c) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.serializeNulls();
        // This is the main class for using Gson. Gson is typically used by first constructing a Gson instance and then invoking toJson(Object) or fromJson(String, Class) methods on it.
        // Gson instances are Thread-safe so you can reuse them freely across multiple threads.
        Gson gson = gsonBuilder.create();
        String jsoncurCode = gson.toJson(mAllProducts);

        SharedPreferences sharedPref = c.getSharedPreferences(myPref, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();

        editor.putString(pref_all_products, jsoncurCode);
        editor.apply();
    }


    public static void deleteAllProducts (Context c) {

        SharedPreferences sharedPref = c.getSharedPreferences(myPref, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(pref_all_products, null);
        editor.apply();
    }


    public static ArrayList<ProductModel> getProductModel(Context c) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.serializeNulls();
        // This is the main class for using Gson. Gson is typically used by first constructing a Gson instance and then invoking toJson(Object) or fromJson(String, Class) methods on it.
        // Gson instances are Thread-safe so you can reuse them freely across multiple threads.
        Gson gson = gsonBuilder.create();
        ArrayList<ProductModel> ProductModelFromShared = new ArrayList<ProductModel>();
        SharedPreferences sharedPref = c.getSharedPreferences(myPref, MODE_PRIVATE);
        String jsonPreferences = sharedPref.getString(pref_productmodel, "");

        Type type = new TypeToken<List<ProductModel>>() {
        }.getType();
        ProductModelFromShared = gson.fromJson(jsonPreferences, type);
        if (ProductModelFromShared == null) {
            ProductModelFromShared = new ArrayList<ProductModel>();

        }
        return ProductModelFromShared;
    }

    public static void saveProductModel(List<ProductModel> mProductModel, Context c) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.serializeNulls();
        // This is the main class for using Gson. Gson is typically used by first constructing a Gson instance and then invoking toJson(Object) or fromJson(String, Class) methods on it.
        // Gson instances are Thread-safe so you can reuse them freely across multiple threads.
        Gson gson = gsonBuilder.create();
        String jsoncurCode = gson.toJson(mProductModel);

        SharedPreferences sharedPref = c.getSharedPreferences(myPref, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();

        editor.putString(pref_productmodel, jsoncurCode);
        editor.apply();
    }


    public static void deleteProductModelList (Context c) {

        SharedPreferences sharedPref = c.getSharedPreferences(myPref, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(pref_productmodel, null);
        editor.apply();
    }


    public static ArrayList<ProductRange> getProductRange(Context c) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.serializeNulls();
        // This is the main class for using Gson. Gson is typically used by first constructing a Gson instance and then invoking toJson(Object) or fromJson(String, Class) methods on it.
        // Gson instances are Thread-safe so you can reuse them freely across multiple threads.
        Gson gson = gsonBuilder.create();
        ArrayList<ProductRange> ProductRangeFromShared = new ArrayList<ProductRange>();
        SharedPreferences sharedPref = c.getSharedPreferences(myPref, MODE_PRIVATE);
        String jsonPreferences = sharedPref.getString(pref_productrange, "");

        Type type = new TypeToken<List<ProductRange>>() {
        }.getType();
        ProductRangeFromShared = gson.fromJson(jsonPreferences, type);
        if (ProductRangeFromShared == null) {
            ProductRangeFromShared = new ArrayList<ProductRange>();

        }
        return ProductRangeFromShared;
    }

    public static void saveProductRange(List<ProductRange> mProductRange, Context c) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.serializeNulls();
        // This is the main class for using Gson. Gson is typically used by first constructing a Gson instance and then invoking toJson(Object) or fromJson(String, Class) methods on it.
        // Gson instances are Thread-safe so you can reuse them freely across multiple threads.
        Gson gson = gsonBuilder.create();
        String jsoncurCode = gson.toJson(mProductRange);

        SharedPreferences sharedPref = c.getSharedPreferences(myPref, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();

        editor.putString(pref_productrange, jsoncurCode);
        editor.apply();
    }


    public static void deleteProductRangeList (Context c) {

        SharedPreferences sharedPref = c.getSharedPreferences(myPref, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(pref_productrange, null);
        editor.apply();
    }





    public static String getSync(Context c) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.serializeNulls();
        // This is the main class for using Gson. Gson is typically used by first constructing a Gson instance and then invoking toJson(Object) or fromJson(String, Class) methods on it.
        // Gson instances are Thread-safe so you can reuse them freely across multiple threads.
        Gson gson = gsonBuilder.create();
        String sync = "";
        SharedPreferences sharedPref = c.getSharedPreferences(myPref, MODE_PRIVATE);
        String jsonPreferences = sharedPref.getString(pref_sync, "");

        Type type = new TypeToken<String>() {
        }.getType();
        sync = gson.fromJson(jsonPreferences, type);
        if (sync == null) {
            sync = "false";
        }


        return sync;
    }
//this is it
    public static void saveSync(String sync, Context c) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.serializeNulls();
        // This is the main class for using Gson. Gson is typically used by first constructing a Gson instance and then invoking toJson(Object) or fromJson(String, Class) methods on it.
        // Gson instances are Thread-safe so you can reuse them freely across multiple threads.
        Gson gson = gsonBuilder.create();
        String jsoncurCode = gson.toJson(sync);

        SharedPreferences sharedPref = c.getSharedPreferences(myPref, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();

        editor.putString(pref_sync, jsoncurCode);
        editor.apply();
    }


    public static void deleteSync(Context c) {

        SharedPreferences sharedPref = c.getSharedPreferences(myPref, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(pref_sync, null);
        editor.apply();
    }

    public static ArrayList<CommandDetails> getCurrentCommandDtails(Context c) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.serializeNulls();
        // This is the main class for using Gson. Gson is typically used by first constructing a Gson instance and then invoking toJson(Object) or fromJson(String, Class) methods on it.
        // Gson instances are Thread-safe so you can reuse them freely across multiple threads.
        Gson gson = gsonBuilder.create();
        ArrayList<CommandDetails> CurrentCommandFromShared = new ArrayList<CommandDetails>();
        SharedPreferences sharedPref = c.getSharedPreferences(myPref, MODE_PRIVATE);
        String jsonPreferences = sharedPref.getString(pref_current_command_details, "");

        Type type = new TypeToken<List<CommandDetails>>() {
        }.getType();
        CurrentCommandFromShared = gson.fromJson(jsonPreferences, type);
        if (CurrentCommandFromShared == null) {
            CurrentCommandFromShared = new ArrayList<CommandDetails>();

        }
        return CurrentCommandFromShared;
    }


    public static void saveCurrentCommandDetails(List<CommandDetails> mCommand, Context c) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.serializeNulls();
        // This is the main class for using Gson. Gson is typically used by first constructing a Gson instance and then invoking toJson(Object) or fromJson(String, Class) methods on it.
        // Gson instances are Thread-safe so you can reuse them freely across multiple threads.
        Gson gson = gsonBuilder.create();
        String jsoncurCode = gson.toJson(mCommand);

        SharedPreferences sharedPref = c.getSharedPreferences(myPref, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();

        editor.putString(pref_current_command_details, jsoncurCode);
        editor.apply();
    }


    public static void deleteCurrentCommand(Context c) {

        SharedPreferences sharedPref = c.getSharedPreferences(myPref, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(pref_current_command_details, null);
        editor.apply();
    }


    public static ArrayList<Command> getCommand(Context c) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.serializeNulls();
        // This is the main class for using Gson. Gson is typically used by first constructing a Gson instance and then invoking toJson(Object) or fromJson(String, Class) methods on it.
        // Gson instances are Thread-safe so you can reuse them freely across multiple threads.
        Gson gson = gsonBuilder.create();
        ArrayList<Command> CommandFromShared = new ArrayList<Command>();
        SharedPreferences sharedPref = c.getSharedPreferences(myPref, MODE_PRIVATE);
        String jsonPreferences = sharedPref.getString(pref_command, "");

        Type type = new TypeToken<ArrayList<Command>>() {
        }.getType();
        CommandFromShared = gson.fromJson(jsonPreferences, type);
        if (CommandFromShared == null) {
            CommandFromShared = new ArrayList<Command>();

        }
        return CommandFromShared;
    }

    public static void saveCommand(ArrayList<Command> mCommand, Context c) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.serializeNulls();
        // This is the main class for using Gson. Gson is typically used by first constructing a Gson instance and then invoking toJson(Object) or fromJson(String, Class) methods on it.
        // Gson instances are Thread-safe so you can reuse them freely across multiple threads.
        Gson gson = gsonBuilder.create();
        String jsoncurCode = gson.toJson(mCommand);

        SharedPreferences sharedPref = c.getSharedPreferences(myPref, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();

        editor.putString(pref_command, jsoncurCode);
        editor.apply();
    }


    public static void deleteCommand(Context c) {

        SharedPreferences sharedPref = c.getSharedPreferences(myPref, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(pref_command, null);
        editor.apply();
    }

//this is it
    public static ArrayList<Reception> getHistorique(Context c) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.serializeNulls();
        // This is the main class for using Gson. Gson is typically used by first constructing a Gson instance and then invoking toJson(Object) or fromJson(String, Class) methods on it.
        // Gson instances are Thread-safe so you can reuse them freely across multiple threads.
        Gson gson = gsonBuilder.create();
        ArrayList<Reception> HistoriqueFromShared = new ArrayList<Reception>();
        SharedPreferences sharedPref = c.getSharedPreferences(myPref, MODE_PRIVATE);
        String jsonPreferences = sharedPref.getString(pref_historique, "");

        Type type = new TypeToken<List<Reception>>() {
        }.getType();
        HistoriqueFromShared = gson.fromJson(jsonPreferences, type);
        if (HistoriqueFromShared == null) {
            HistoriqueFromShared = new ArrayList<Reception>();

        }
        return HistoriqueFromShared;
    }

    public static void saveHistorique(List<Reception> mHistorique, Context c) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.serializeNulls();
        // This is the main class for using Gson. Gson is typically used by first constructing a Gson instance and then invoking toJson(Object) or fromJson(String, Class) methods on it.
        // Gson instances are Thread-safe so you can reuse them freely across multiple threads.
        Gson gson = gsonBuilder.create();
        String jsoncurCode = gson.toJson(mHistorique);

        SharedPreferences sharedPref = c.getSharedPreferences(myPref, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();

        editor.putString(pref_historique, jsoncurCode);
        editor.apply();
    }


    public static void deleteHistorique(Context c) {

        SharedPreferences sharedPref = c.getSharedPreferences(myPref, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(pref_historique, null);
        editor.apply();
    }

    public static Boolean CCIsSyncable(Context c) {

        SharedPreferences sharedPref = c.getSharedPreferences(myPref, MODE_PRIVATE);
        Boolean sync = sharedPref.getBoolean(pref_CCIsSyncable, false);
        return sync;
    }

    public static void setCCsyncable(Context c) {
        SharedPreferences sharedPref = c.getSharedPreferences(myPref, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putBoolean(pref_CCIsSyncable, true);
        editor.apply();
    }

    public static void setCCnotsyncable(Context c) {

        SharedPreferences sharedPref = c.getSharedPreferences(myPref, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putBoolean(pref_CCIsSyncable, false);
        editor.apply();
    }


    public static ArrayList<Incentive> getIncentive(Context c) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.serializeNulls();
        // This is the main class for using Gson. Gson is typically used by first constructing a Gson instance and then invoking toJson(Object) or fromJson(String, Class) methods on it.
        // Gson instances are Thread-safe so you can reuse them freely across multiple threads.
        Gson gson = gsonBuilder.create();
        ArrayList<Incentive> IncentiveFromShared = new ArrayList<Incentive>();
        SharedPreferences sharedPref = c.getSharedPreferences(myPref, MODE_PRIVATE);
        String jsonPreferences = sharedPref.getString(pref_incentive, "");

        Type type = new TypeToken<List<Incentive>>() {
        }.getType();
        IncentiveFromShared = gson.fromJson(jsonPreferences, type);
        if (IncentiveFromShared == null) {
            IncentiveFromShared = new ArrayList<Incentive>();

        }
        return IncentiveFromShared;
    }

    public static void saveIncentive(List<Incentive> mIncentive, Context c) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.serializeNulls();
        // This is the main class for using Gson. Gson is typically used by first constructing a Gson instance and then invoking toJson(Object) or fromJson(String, Class) methods on it.
        // Gson instances are Thread-safe so you can reuse them freely across multiple threads.
        Gson gson = gsonBuilder.create();
        String jsoncurCode = gson.toJson(mIncentive);

        SharedPreferences sharedPref = c.getSharedPreferences(myPref, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();

        editor.putString(pref_incentive, jsoncurCode);
        editor.apply();
    }


    public static void deleteIncentive(Context c) {

        SharedPreferences sharedPref = c.getSharedPreferences(myPref, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(pref_incentive, null);
        editor.apply();
    }

    public static ArrayList<Product> getProduct(Context c) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.serializeNulls();
        // This is the main class for using Gson. Gson is typically used by first constructing a Gson instance and then invoking toJson(Object) or fromJson(String, Class) methods on it.
        // Gson instances are Thread-safe so you can reuse them freely across multiple threads.
        Gson gson = gsonBuilder.create();
        ArrayList<Product> ProductFromShared = new ArrayList<Product>();
        SharedPreferences sharedPref = c.getSharedPreferences(myPref, MODE_PRIVATE);
        String jsonPreferences = sharedPref.getString(pref_product, "");

        Type type = new TypeToken<List<Product>>() {
        }.getType();
        ProductFromShared = gson.fromJson(jsonPreferences, type);

        return ProductFromShared;
    }

    public static void saveProduct(List<Product> mProduct, Context c) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.serializeNulls();
        // This is the main class for using Gson. Gson is typically used by first constructing a Gson instance and then invoking toJson(Object) or fromJson(String, Class) methods on it.
        // Gson instances are Thread-safe so you can reuse them freely across multiple threads.
        Gson gson = gsonBuilder.create();
        String jsoncurCode = gson.toJson(mProduct);

        SharedPreferences sharedPref = c.getSharedPreferences(myPref, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();

        editor.putString(pref_product, jsoncurCode);
        editor.apply();
    }


    public static void deleteProduct(Context c) {

        SharedPreferences sharedPref = c.getSharedPreferences(myPref, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(pref_product, null);
        editor.apply();
    }


    public static ArrayList<Reception> getReception(Context c) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.serializeNulls();
        // This is the main class for using Gson. Gson is typically used by first constructing a Gson instance and then invoking toJson(Object) or fromJson(String, Class) methods on it.
        // Gson instances are Thread-safe so you can reuse them freely across multiple threads.
        Gson gson = gsonBuilder.create();
        ArrayList<Reception> ReceptionFromShared = new ArrayList<Reception>();
        SharedPreferences sharedPref = c.getSharedPreferences(myPref, MODE_PRIVATE);
        String jsonPreferences = sharedPref.getString(pref_reception, "");

        Type type = new TypeToken<List<Reception>>() {
        }.getType();
        ReceptionFromShared = gson.fromJson(jsonPreferences, type);
        if (ReceptionFromShared == null) {
            ReceptionFromShared = new ArrayList<Reception>();

        }
        return ReceptionFromShared;
    }

    public static void saveReception(List<Reception> mReception, Context c) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.serializeNulls();
        // This is the main class for using Gson. Gson is typically used by first constructing a Gson instance and then invoking toJson(Object) or fromJson(String, Class) methods on it.
        // Gson instances are Thread-safe so you can reuse them freely across multiple threads.
        Gson gson = gsonBuilder.create();
        String jsoncurCode = gson.toJson(mReception);

        SharedPreferences sharedPref = c.getSharedPreferences(myPref, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();

        editor.putString(pref_reception, jsoncurCode);
        editor.apply();
    }


    public static void deleteReception(Context c) {

        SharedPreferences sharedPref = c.getSharedPreferences(myPref, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(pref_reception, null);
        editor.apply();
    }


    public static ArrayList<Stock> getStock(Context c) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.serializeNulls();
        // This is the main class for using Gson. Gson is typically used by first constructing a Gson instance and then invoking toJson(Object) or fromJson(String, Class) methods on it.
        // Gson instances are Thread-safe so you can reuse them freely across multiple threads.
        Gson gson = gsonBuilder.create();
        ArrayList<Stock> stockFromShared = new ArrayList<Stock>();
        SharedPreferences sharedPref = c.getSharedPreferences(myPref, MODE_PRIVATE);
        String jsonPreferences = sharedPref.getString(pref_stock, "");

        Type type = new TypeToken<List<Stock>>() {
        }.getType();
        stockFromShared = gson.fromJson(jsonPreferences, type);
        if (stockFromShared == null) {
            stockFromShared = new ArrayList<Stock>();

        }
        return stockFromShared;
    }

    public static void saveStock(List<Stock> mStock, Context c) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.serializeNulls();
        // This is the main class for using Gson. Gson is typically used by first constructing a Gson instance and then invoking toJson(Object) or fromJson(String, Class) methods on it.
        // Gson instances are Thread-safe so you can reuse them freely across multiple threads.
        Gson gson = gsonBuilder.create();
        String jsoncurCode = gson.toJson(mStock);

        SharedPreferences sharedPref = c.getSharedPreferences(myPref, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();

        editor.putString(pref_stock, jsoncurCode);
        editor.apply();
    }


    public static void deleteStock(Context c) {

        SharedPreferences sharedPref = c.getSharedPreferences(myPref, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(pref_stock, null);
        editor.apply();
    }


    public static ArrayList<User> getUsers(Context c) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.serializeNulls();
        // This is the main class for using Gson. Gson is typically used by first constructing a Gson instance and then invoking toJson(Object) or fromJson(String, Class) methods on it.
        // Gson instances are Thread-safe so you can reuse them freely across multiple threads.
        Gson gson = gsonBuilder.create();
        ArrayList<User> usersFromShared = new ArrayList<User>();
        SharedPreferences sharedPref = c.getSharedPreferences(myPref, MODE_PRIVATE);
        String jsonPreferences = sharedPref.getString(pref_users, "");

        Type type = new TypeToken<List<User>>() {
        }.getType();
        usersFromShared = gson.fromJson(jsonPreferences, type);

        return usersFromShared;
    }


    public static void saveUsers(List<User> mUsers, Context c) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.serializeNulls();
        // This is the main class for using Gson. Gson is typically used by first constructing a Gson instance and then invoking toJson(Object) or fromJson(String, Class) methods on it.
        // Gson instances are Thread-safe so you can reuse them freely across multiple threads.
        Gson gson = gsonBuilder.create();
        String jsoncurCode = gson.toJson(mUsers);

        SharedPreferences sharedPref = c.getSharedPreferences(myPref, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();

        editor.putString(pref_users, jsoncurCode);
        editor.apply();
    }

    public static void deleteusers(Context c) {


        SharedPreferences sharedPref = c.getSharedPreferences(myPref, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();

        editor.putString(pref_users, null);
        editor.apply();
    }


    public static void switchFragmentWithAnimation(int id, Fragment fragment,
                                                   FragmentActivity activity, String TAG, AnimationType transitionStyle) {

        FragmentManager fragmentManager = activity.getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager
                .beginTransaction();

        if (transitionStyle != null) {
            switch (transitionStyle) {

                case SLIDE_DOWN:

                    // Exit from down
                    fragmentTransaction.setCustomAnimations(R.anim.slide_up, R.anim.slide_down);

                    break;

                case SLIDE_UP:

                    // Enter from Up
                    fragmentTransaction.setCustomAnimations(R.anim.slide_in_up, R.anim.slide_out_up);

                    break;

                case SLIDE_LEFT:

                    // Enter from left
                    fragmentTransaction.setCustomAnimations(R.anim.slide_left,
                            R.anim.slide_out_left);

                    break;

                // Enter from right
                case SLIDE_RIGHT:
                    fragmentTransaction.setCustomAnimations(R.anim.slide_right,
                            R.anim.slide_out_right);

                    break;

                case FADE_IN:
                    fragmentTransaction.setCustomAnimations(R.anim.fade_in,
                            R.anim.fade_out);

                case FADE_OUT:
                    fragmentTransaction.setCustomAnimations(R.anim.fade_in,
                            R.anim.donot_move);

                    break;

                case SLIDE_IN_SLIDE_OUT:

                    fragmentTransaction.setCustomAnimations(R.anim.slide_left, R.anim.slide_out_left);

                    break;

                default:
                    break;
            }
        }

        CURRENT_TAG = TAG;

        fragmentTransaction.replace(id, fragment);
        fragmentTransaction.addToBackStack(TAG);
        fragmentTransaction.commit();
    }


    public enum AnimationType {
        SLIDE_LEFT, SLIDE_RIGHT, SLIDE_UP, SLIDE_DOWN, FADE_IN, SLIDE_IN_SLIDE_OUT, FADE_OUT
    }


    public static void logMeIn(final Activity c){

        showDialogLoading(c) ;
        Log.e("info", "code" + list_users.get(0).getCode());
        Log.e("info", "pass" + list_users.get(0).getPassword());
        Log.e("info", "imei" + getIMEINumber(c)  );

        OkHttpClient client = new OkHttpClient();

        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("username", "" +list_users.get(0).getCode())
                .addFormDataPart("password", "" +list_users.get(0).getPassword())
                .addFormDataPart("imei", "" +getIMEINumber(c))
                .addFormDataPart("version", ""+getVersionCode(c))
                .build();
        Request request = new Request.Builder()
                .url(url_API_login)
                .post(requestBody)
                .addHeader("Accept", "application/json")
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

                closeDialogLoading( c);
                showToastFromBackground("nointernet",c);

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                closeDialogLoading( c);
                String login_json = response.body().string();
                Log.e("LoginActivity", "responce login login_json -----> " + login_json);


                if (login_json.equals("empty") || login_json.equals("incorrect") || login_json.equals("bug") || login_json.contains("update")) {
if(login_json.contains("update")){
    showToastFromBackground(login_json,c);
}else{
    showToastFromBackground("failed",c);

}

                } else {

                    try {
                        JSONObject obj = new JSONObject(login_json);
                        list_users.get(0).setToken(obj.getString("access_token"));
                        saveUsers(list_users, c.getApplicationContext());
                        showToastFromBackground("logged",c);


                    } catch (JSONException e) {
                        e.printStackTrace();
                        showToastFromBackground("failed",c);

                    }

                }


            }
        });

            }

    static Toast lastToast = null;

    static boolean isToastNotRunning() {
        return (lastToast == null || lastToast.getView().getWindowVisibility() != View.VISIBLE);
    }
    public static void showToastFromBackground(final String message, final Activity c) {
        if (isToastNotRunning()) {
            c.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    //lastToast = Toast.makeText(WilayaPicker.this, message, Toast.LENGTH_LONG);
                    //lastToast.show();
                    if (message.contains("update")) {


                        String[] separated = message.split("::");
                        String url = separated[1];
                        DialogNewUpdate cdd = new DialogNewUpdate(c,url);
                        cdd.setCancelable(false);
                        cdd.setOnDismissListener(new DialogInterface.OnDismissListener() {
                            @Override
                            public void onDismiss(DialogInterface dialogInterface) {

                            }
                        });
                        cdd.show();


                    }
                    if (message.equals("logged")) {



                    }
                    if (message.equals("failed")) {

                        DialogLogout lod = new DialogLogout(c);
                        lod.setCancelable(false);
                        lod.setOnDismissListener(new DialogInterface.OnDismissListener() {
                            @Override
                            public void onDismiss(DialogInterface dialogInterface) {
                                LogOUT(c);
                            }
                        });
                        lod.show();


                    }


                }
            });
        }
    }

    private static void closeDialogLoading(Activity c) {

        c.runOnUiThread(new Runnable() {
            @Override
            public void run() {

                mSyncEncours.dismissWithAnimation();
            }

        });


    }


    public static String getVersionCode(Context context) {
        PackageManager pm = context.getPackageManager();
        try {
            PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
            return pi.versionName;

        } catch (PackageManager.NameNotFoundException ex) {}
        return "unknown";
    }
    private static void showDialogLoading(Activity c) {
        mSyncEncours = new SweetAlertDialog(c, SweetAlertDialog.PROGRESS_TYPE);
        mSyncEncours.setContentText(c.getString(R.string.Patienter));
        mSyncEncours.setTitleText(c.getString(R.string.opp_sync_encours));
        mSyncEncours.getProgressHelper().setBarColor(c.getResources().getColor(R.color.circleble3));
        mSyncEncours.setCancelable(false);

        mSyncEncours.show();
        TextView titleView = mSyncEncours.findViewById(R.id.title_text);
        if (titleView != null) {
            titleView.setTypeface(custom_font);
            titleView.setWidth((int) (getDeviceMetrics(c).widthPixels * 0.9));
        }
        TextView messageView = mSyncEncours.findViewById(R.id.content_text);
        if (messageView != null) messageView.setTypeface(custom_font);

    }

    public static String getIMEINumber(Context c) {

        String imei = "";
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {

            imei  = Settings.Secure.getString(
                    c.getContentResolver(),
                    Settings.Secure.ANDROID_ID);
            return imei;
        }
        TelephonyManager telephonyManager = (TelephonyManager) c.getSystemService(Context.TELEPHONY_SERVICE);

        if (android.os.Build.VERSION.SDK_INT >= 26) {
            if (c.checkSelfPermission(Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {

            }
            imei = telephonyManager.getImei();

        } else {
            imei = telephonyManager.getDeviceId();
        }

        return imei;
    }


    private static void LogOUT(Activity c) {

        editprefLogin = c.getSharedPreferences("MyPrefsLogin", MODE_PRIVATE).edit();
        editprefLogin.putString("savedEmail", "");
        editprefLogin.putString("savedPass", "");
        editprefLogin.apply();
        list_users.clear();
        saveUsers(list_users, c.getApplicationContext());
        Intent myIntent = new Intent(c, LoginActivity.class);
        c.startActivity(myIntent);
        c.finish();


    }

}
