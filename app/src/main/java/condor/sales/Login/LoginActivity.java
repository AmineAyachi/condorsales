package condor.sales.Login;


import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.sanojpunchihewa.updatemanager.UpdateManager;
import com.sanojpunchihewa.updatemanager.UpdateManagerConstant;

import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import condor.sales.Activities.TermsAndConditionActivity;
import condor.sales.Dialogs.DialogNewUpdate;
import condor.sales.Models.User;
import condor.sales.R;
import condor.sales.Activities.AcceuilActivity;
import cn.pedant.SweetAlert.SweetAlertDialog;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import static condor.sales.Constants.custom_font;
import static condor.sales.Constants.list_users;
import static condor.sales.Constants.url_API_UserInfo;
import static condor.sales.Constants.url_API_login;
import static condor.sales.Utils.Utils.getDeviceMetrics;
import static condor.sales.Utils.Utils.getUsers;
import static condor.sales.Utils.Utils.getVersionCode;
import static condor.sales.Utils.Utils.saveUsers;


public class LoginActivity extends AppCompatActivity {

    boolean opp_encours = false;
    SweetAlertDialog mConnextionEncours;
    ImageView loginlogo;
    EditText mPassword;
    EditText mEmail;
    Button mLogin;
    CheckBox mRememberMe;
    TextView mForgetPassword, mApp_version ,tandu ;
    int count = 0;
    SharedPreferences prefLogin;
    SharedPreferences.Editor editprefLogin;
    UpdateManager mUpdateManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mUpdateManager = UpdateManager.Builder(this).mode(UpdateManagerConstant.IMMEDIATE);
        mUpdateManager.start();

        get_list_users();
        mEmail = findViewById(R.id.email);
        mApp_version = findViewById(R.id.app_version);
        tandu = findViewById(R.id.app_tandc);
        mApp_version.setText("Version "+getVersionCode(LoginActivity.this));
        mPassword = findViewById(R.id.password);
        mRememberMe = findViewById(R.id.rememberme);
        mForgetPassword = findViewById(R.id.forgetpass);
        mLogin = findViewById(R.id.login);
        loginlogo = findViewById(R.id.animation_view);
        loginlogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (count == 1) {
                    final Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            count = 0;
                            //Do something after 100ms
                        }
                    }, 3000);
                }


                count++;

                if (count > 20)              //Show Settings after count = 20
                {
                    count = 0;
                    showOurDialog();
                }


            }
        });

        prefLogin = getSharedPreferences("MyPrefsLogin", MODE_PRIVATE);
        String preflogin = prefLogin.getString("checked", "");


        if(!list_users.isEmpty() && preflogin.equals("yes")){

            Log.e("list_users", "list_users is not empty");
            Log.e("preflogin", ":"+preflogin);

            if (ContextCompat.checkSelfPermission(LoginActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(LoginActivity.this, new String[]{Manifest.permission.CALL_PHONE}, 1);
            } else {

                if (ContextCompat.checkSelfPermission(LoginActivity.this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(LoginActivity.this, new String[]{Manifest.permission.READ_PHONE_STATE}, 1);
                } else {



                    fct_login(list_users.get(0).getCode(),list_users.get(0).getPassword());
                }


            }
        }


        tandu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tandu.setEnabled(false);
                Intent myIntent = new Intent(LoginActivity.this, TermsAndConditionActivity.class);
                startActivity(myIntent);
                tandu.setEnabled(true);


            }
        });
        mForgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mForgetPassword.setEnabled(false);
                Intent myIntent = new Intent(LoginActivity.this, ForgetPasswordActivity.class);
                startActivity(myIntent);


            }
        });
        mLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(LoginActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(LoginActivity.this, new String[]{Manifest.permission.CALL_PHONE}, 1);
                } else {


                    if (ContextCompat.checkSelfPermission(LoginActivity.this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(LoginActivity.this, new String[]{Manifest.permission.READ_PHONE_STATE}, 1);
                    } else {
                        String myEmail = mEmail.getText().toString().trim();
                        String myPass = mPassword.getText().toString().trim();


                        if (!myEmail.isEmpty() && !myPass.isEmpty()) {


                            if (!opp_encours) {
                                fct_login(myEmail, myPass);
                            }


                        } else {

                            if (myEmail.isEmpty()) {
                                Toast.makeText(LoginActivity.this, getString(R.string.champs_vide), Toast.LENGTH_LONG).show();
                            }
                            if (myPass.isEmpty()) {
                                Toast.makeText(LoginActivity.this, getString(R.string.champs_vide), Toast.LENGTH_LONG).show();
                            }


                        }
                    }


                }


            }
        });


    }

    private void showOurDialog() {

        final SweetAlertDialog pDialog = new SweetAlertDialog(LoginActivity.this, SweetAlertDialog.NORMAL_TYPE);
        pDialog.setCancelable(false);

        pDialog.setTitleText("Développée Par :")
                .setContentText("Laoubi Hani\nhani.laoubi@gmail.com\n\nMohamed Amine Ayachi\nayachi.m.amine@gmail.com")
                .showCancelButton(false);

        pDialog.setConfirmText("OK")
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {

                        pDialog.dismiss();


                    }
                });

        pDialog.show();
        Button retour_btn = pDialog.findViewById(R.id.confirm_button);
        retour_btn.setBackgroundResource(R.drawable.button);
        retour_btn.setTypeface(custom_font);


        TextView titleView = pDialog.findViewById(R.id.title_text);
        if (titleView != null) {
            titleView.setTypeface(custom_font);
            titleView.setWidth((int) (getDeviceMetrics(this).widthPixels * 0.9));
        }

        TextView messageView = pDialog.findViewById(R.id.content_text);
        if (messageView != null) {
            messageView.setTypeface(custom_font);
            messageView.setWidth((int) (getDeviceMetrics(this).widthPixels * 0.9));
            // messageView.setHeight((int) (getDeviceMetrics(this).heightPixels*0.2));

            ViewGroup.LayoutParams params = messageView.getLayoutParams();
            params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
            messageView.setLayoutParams(params);
            messageView.setGravity(Gravity.CENTER);
        }

        pDialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

    }


    private void showDialogLoading() {
        opp_encours = true;
        mConnextionEncours = new SweetAlertDialog(LoginActivity.this, SweetAlertDialog.PROGRESS_TYPE);
        mConnextionEncours.setContentText(getString(R.string.Patienter));
        mConnextionEncours.setTitleText(getString(R.string.connexion_encours));
        mConnextionEncours.getProgressHelper().setBarColor(getResources().getColor(R.color.circleble3));
        mConnextionEncours.setCancelable(false);
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mConnextionEncours.show();
            }

        });

        TextView titleView = mConnextionEncours.findViewById(R.id.title_text);
        if (titleView != null) {
            titleView.setTypeface(custom_font);
            titleView.setWidth((int) (getDeviceMetrics(this).widthPixels * 0.9));
        }
        TextView messageView = mConnextionEncours.findViewById(R.id.content_text);
        if (messageView != null) messageView.setTypeface(custom_font);

    }

    private void closeDialogLoading() {
        opp_encours = false;
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mConnextionEncours.dismissWithAnimation();
            }

        });


    }

    public Date StringToDate(String s) {

        Date result = null;
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            result = dateFormat.parse(s);
        } catch (ParseException e) {
            e.printStackTrace();

        }
        return result;
    }

    @Override
    protected void onStop() {

        super.onStop();
    }


    private void fct_login(final String verify_user_email, final String verify_user_password) {
        showDialogLoading();
        boolean user_exist = false;

        if (!list_users.isEmpty()) {
            if (list_users.get(0).getCode().equalsIgnoreCase(verify_user_email) && list_users.get(0).getPassword().equals(verify_user_password) ) {
                closeDialogLoading();
                user_exist = true;
            }
        }


        if (!user_exist) {

            Log.e("starting", "startinglogin ********************************************************** MODE ONLINE");

            OkHttpClient client = new OkHttpClient.Builder()
                    .connectTimeout(20, TimeUnit.SECONDS)
                    .callTimeout(30, TimeUnit.SECONDS)
                    .writeTimeout(30, TimeUnit.SECONDS)
                    .readTimeout(30, TimeUnit.SECONDS)
                    .retryOnConnectionFailure(false)
                    .build();

            RequestBody requestBody = new MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart("username", "" + verify_user_email)
                    .addFormDataPart("password", "" + verify_user_password)
                    .addFormDataPart("imei", "" + getIMEINumber())
                    .addFormDataPart("version", "" + getVersionCode(LoginActivity.this))
                    .build();
            Request request = new Request.Builder()
                    .url(url_API_login)
                    .post(requestBody)
                    .addHeader("Accept", "application/json")
                    .build();

            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    // inser shared pref actualite last

                    Log.e("onFailure", "" + e);
                    closeDialogLoading();
                    showToastFromBackground("nointernet");

                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    String login_json = response.body().string();
                    Log.e("LoginActivity", "responce login login_json -----> " + login_json);


                    //u have to get the user id from data base so you can use it when you collect or distribut a courier


                    if (login_json.equals("empty") || login_json.equals("incorrect") || login_json.equals("bug") || login_json.contains("update")) {

                        if (login_json.equals("empty")) {
                            Log.e("LoginActivity", "empty ---->please fill empty fields ");
                            closeDialogLoading();


                        }

                        if (login_json.contains("update")) {
                            Log.e("LoginActivity", "update ---->update the app ");
                            closeDialogLoading();
                            showToastFromBackground(login_json);
                        }
                        if (login_json.equals("incorrect")) {

                            Log.e("LoginActivity", "incorrect ");
                            closeDialogLoading();
                            showToastFromBackground("wrong_credentials");
                        }

                        if (login_json.equals("bug")) {
                            //xamp tafi mysql tasra hadi l bug
                            Log.e("LoginActivity", "bug ");
                            showToastFromBackground("nointernet");
                            closeDialogLoading();
                        }
                    } else {

                        try {
                            JSONObject obj = new JSONObject(login_json);
                            String token = obj.getString("access_token");

                            if (mRememberMe.isChecked() ) {
                                editprefLogin = getSharedPreferences("MyPrefsLogin", MODE_PRIVATE).edit();
                                editprefLogin.putString("checked", "yes");
                                editprefLogin.apply();
                            }
                            fct_get_UserInfo(verify_user_email, verify_user_password, token);


                        } catch (JSONException e) {
                            e.printStackTrace();
                            showToastFromBackground("nointernet");
                            closeDialogLoading();
                        }


                    }


                }
            });
        } else {


            Log.e("starting", "startinglogin ********************************************************** MODE offline");
            if (mRememberMe.isChecked() ) {
                editprefLogin = getSharedPreferences("MyPrefsLogin", MODE_PRIVATE).edit();
                editprefLogin.putString("checked", "yes");
                editprefLogin.apply();
            }
            Intent myIntent = new Intent(LoginActivity.this, AcceuilActivity.class);
            startActivity(myIntent);
            closeDialogLoading();
            finish();
        }


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
                    if (message.contains("update")) {


                        String[] separated = message.split("::");
                        String url = separated[1];
                        DialogNewUpdate cdd = new DialogNewUpdate(LoginActivity.this,url);
                        cdd.setCancelable(false);
                        cdd.setOnDismissListener(new DialogInterface.OnDismissListener() {
                            @Override
                            public void onDismiss(DialogInterface dialogInterface) {

                            }
                        });
                        cdd.show();

                    }
                    if (message.equals("nointernet")) {
//                        Dialog_Connexion cdd=new Dialog_Connexion( Splash.this);
//                        cdd.setCancelable(false);
//                        cdd.show();
                        lastToast = Toast.makeText(LoginActivity.this, getString(R.string.connexion_verification), Toast.LENGTH_LONG);
                        lastToast.show();


                    }
                    if (message.equals("wrong_credentials")) {

                        lastToast = Toast.makeText(LoginActivity.this, getString(R.string.wrong_credentials), Toast.LENGTH_LONG);
                        lastToast.show();


                    }


                }
            });
        }
    }


    public String getIMEINumber() {
        String imei = "";
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {

            imei  = Settings.Secure.getString(
                    this.getContentResolver(),
                    Settings.Secure.ANDROID_ID);
            return imei;
        }
        TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);

        if (android.os.Build.VERSION.SDK_INT >= 26) {
            if (checkSelfPermission(Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {

            }
            imei = telephonyManager.getImei();

        } else {
            imei = telephonyManager.getDeviceId();
        }

        return imei;
    }

    private void fct_get_UserInfo(String user_email, final String user_password, final String user_token) {

        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(20, TimeUnit.SECONDS)
                .callTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .retryOnConnectionFailure(false)
                .build();

//        Log.e("fct_get_UserInfo", "fct_get_UserInfo"  );
//        Log.e("url_API", "url_API ***************-----> " + url_API);
        Request request = new Request.Builder()
                .url(url_API_UserInfo)
                .addHeader("Accept", "application/json")
                .addHeader("Authorization", "Bearer " + user_token)
                .addHeader("Content-Type", "multipart/form-data")
                .method("GET", null)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                // inser shared pref actualite last

                Log.e("", "onFailure" + e);
                closeDialogLoading();
                showToastFromBackground("nointernet");

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {


                String userinfo_json = response.body().string();
                JSONObject obj = null;
                Log.e("TagLogin", "userinfo_json : " + userinfo_json);

                if (userinfo_json.contains("Unauthenticated")) {
                    // Toast.makeText(AcceuilActivity.this, "U have to reconnect", Toast.LENGTH_LONG).show();;
                    Log.e("TagLogin", "onResponse Unauthenticated");
                    closeDialogLoading();
                } else {

                    try {
                        obj = new JSONObject(userinfo_json);
                        String connected_user_id = obj.getString("id");
                        String connected_user_email = obj.getString("email");
                        String connected_user_code = obj.getString("code");
                        String connected_user_name = obj.getString("name");
                        String connected_user_phone = obj.getString("phone");
                        String connected_user_city = obj.getString("city");
                        String connected_user_adress = obj.getString("adresse");
                        String connected_user_last_sync = obj.getString("last_sync");
                        User new_user = new User("" + connected_user_id, "" + connected_user_code, "" + connected_user_name, "" + connected_user_phone, "" + connected_user_city, "" + connected_user_adress, "" + connected_user_email, "" + user_password, "" + user_token, "" + connected_user_last_sync);
                        list_users.clear();
                        list_users.add(new_user);
                        saveUsers(list_users, getApplicationContext());
                        closeDialogLoading();
                        Intent myIntent = new Intent(LoginActivity.this, AcceuilActivity.class);
                        startActivity(myIntent);
                        closeDialogLoading();
                        finish();

                    } catch (JSONException e) {
                        e.printStackTrace();
                        closeDialogLoading();
                        showToastFromBackground("nointernet");
                        Log.e("TagLogin", "onResponse Exception -----> " + e);
                    }


                }



            }
        });
    }

    @Override
    protected void onResume() {

        mForgetPassword.setEnabled(true);
        get_list_users();
        super.onResume();
    }

    private void get_list_users() {
        list_users = getUsers(getApplicationContext());
        if (list_users == null) {
            list_users = new ArrayList<User>();
        }
        Log.e("get_list_users", "**** get_list_users list_users *** " + list_users.size());
    }


}
