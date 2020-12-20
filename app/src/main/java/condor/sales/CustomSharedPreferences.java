package condor.sales;

import android.content.Context;
import android.content.SharedPreferences;

public class CustomSharedPreferences {


    private static CustomSharedPreferences customSharedPreferences;
    private SharedPreferences sharedPreferences;

    public static CustomSharedPreferences getInstance(Context context) {
        if (customSharedPreferences == null) {
            customSharedPreferences = new CustomSharedPreferences(context);
        }
        return customSharedPreferences;
    }

    private CustomSharedPreferences(Context context) {
        sharedPreferences = context.getSharedPreferences("YourCustomNamedPreference",Context.MODE_PRIVATE);
    }

    public void saveData(String key,String value) {
        SharedPreferences.Editor prefsEditor = sharedPreferences.edit();
        prefsEditor .putString(key, value);
        prefsEditor.commit();
    }

    public String getData(String key) {
        if (sharedPreferences!= null) {
            return sharedPreferences.getString(key, "");
        }
        return "";
    }
}
