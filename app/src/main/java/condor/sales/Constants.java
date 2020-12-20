package condor.sales;


import android.graphics.Typeface;
import java.util.ArrayList;
import condor.sales.Models.CommandDetails;
import condor.sales.Models.Incentive;
import condor.sales.Models.News;
import condor.sales.Models.ProductModel;
import condor.sales.Models.Product;
import condor.sales.Models.ProductRange;
import condor.sales.Models.Reception;
import condor.sales.Models.Stock;
import condor.sales.Models.User;


public class Constants {

    public static Typeface custom_font;
    public static String url_API = "https://sales.condor.dz/api";
   // public static String url_API = "http://192.168.1.45/api";
    public static String url_API_login = url_API +"/login";
    public static String url_API_fb_token = url_API +"/fbtoken";
    public static String sendcodefp = url_API +"/password/create";
    public static String verifiytoken = url_API +"/password/find";
    public static String resetpassword = url_API +"/password/reset";
    public static String changepassword = url_API +"/password/change";
    public static String url_API_UserInfo = url_API +"/userinfo";
    public static String url_API_Sync = url_API+"/sync";
    public static String url_API_reception = url_API+"/receptionFromPos";
    public static final String myPref = "MySharedPrefs";
    public static final String pref_users = "List_users";
    public static final String pref_stock = "List_stock";
    public static final String pref_historique = "List_historique";
    public static final String pref_reception = "List_reception";
    public static final String pref_product = "List_product";
    public static final String pref_all_products = "all_products_list";
    public static final String pref_all_news = "all_news_list";
    public static final String pref_sync = "sync";
    public static final String pref_incentive = "List_incentive";
    public static final String pref_CCIsSyncable = "CCIsSyncable ";
    public static final String pref_productmodel = "product_model_list";
    public static final String pref_productrange = "product_range_list ";
    public static final String pref_command = "List_command";
    public static final String pref_current_command_details = "current_command_details";
    public static ArrayList<User> list_users = new ArrayList<User>();
    public static ArrayList<Stock> stock = new ArrayList<Stock>();
    public static ArrayList<CommandDetails> currentCommandDetails = new ArrayList<CommandDetails>();
    public static ArrayList<Reception>  Reception = new ArrayList<Reception>();
    public static ArrayList<Reception> Historique = new ArrayList<Reception>();
    public static ArrayList<Incentive>  Incentive = new ArrayList<Incentive>(); // la list des incentives
    public static ArrayList<ProductModel>  productmodel = new ArrayList<ProductModel>();
    public static ArrayList<ProductRange>  productrange = new ArrayList<ProductRange>();
    public static ArrayList<Product>  all_Product = new ArrayList<Product>();
    public static ArrayList<News>  all_News = new ArrayList<News>();





}
