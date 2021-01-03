package condor.sales.Login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import cn.pedant.SweetAlert.SweetAlertDialog;
import condor.sales.Constants;
import condor.sales.R;
import condor.sales.SplashScreen;
import condor.sales.Utils.Utils;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import static condor.sales.Constants.resetpassword;
import static condor.sales.Utils.Utils.getDeviceMetrics;

public class ResetPasswordActivity extends AppCompatActivity {
    Button send_btn;
    EditText forgetEmail;
    SweetAlertDialog mresetpassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        if(getIntent().getStringExtra("token") == null || getIntent().getStringExtra("code") == null ){
            finish();
        }

        forgetEmail = findViewById(R.id.forgetEmail);
        send_btn = findViewById(R.id.recuperer_password_btn);
        send_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                resetPassword(getIntent().getStringExtra("code"),getIntent().getStringExtra("token"),forgetEmail.getText().toString());
            }
        });

    }



    private void showDialogLoading() {

        mresetpassword = new SweetAlertDialog(ResetPasswordActivity.this, SweetAlertDialog.PROGRESS_TYPE);
        mresetpassword.setContentText(getString(R.string.Patienter));
        mresetpassword.setTitleText("Réinitialisation en cours");
        mresetpassword.getProgressHelper().setBarColor(getResources().getColor(R.color.circleble3));
        mresetpassword.setCancelable(false);

        mresetpassword.show();
        TextView titleView = mresetpassword.findViewById(R.id.title_text);
        if (titleView != null) {
            titleView.setTypeface(Constants.custom_font);
            titleView.setWidth((int) (getDeviceMetrics(this).widthPixels * 0.9));
        }
        TextView messageView = mresetpassword.findViewById(R.id.content_text);
        if (messageView != null) messageView.setTypeface(Constants.custom_font);

    }
    Toast lastToast = null;
    boolean isToastNotRunning() {
        return (lastToast == null || lastToast.getView().getWindowVisibility() != View.VISIBLE);
    }
    private void closeDialogLoading() {

        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mresetpassword.dismissWithAnimation();
            }

        });


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
                        lastToast = Toast.makeText(ResetPasswordActivity.this, getString(R.string.connexion_verification), Toast.LENGTH_LONG);
                        lastToast.show();


                    }
                    if (message.equals("invalid")) {
//                        Dialog_Connexion cdd=new Dialog_Connexion( Splash.this);
//                        cdd.setCancelable(false);
//                        cdd.show();
                        lastToast = Toast.makeText(ResetPasswordActivity.this, "Session expirée veuillez recommencer la procédure ", Toast.LENGTH_LONG);
                        lastToast.show();


                    }
                    if (message.equals("done")) {
//                        Dialog_Connexion cdd=new Dialog_Connexion( Splash.this);
//                        cdd.setCancelable(false);
//                        cdd.show();
                        lastToast = Toast.makeText(ResetPasswordActivity.this, "Le mot de passe a été réinitialisé avec succès", Toast.LENGTH_LONG);
                        lastToast.show();


                    }


                }
            });
        }
    }


    private void resetPassword(String code ,  String token, String newpassword) {

        Toast lastToast = null;
        if(code.isEmpty() || token.isEmpty() || newpassword.isEmpty()){
            lastToast = Toast.makeText(ResetPasswordActivity.this, getString(R.string.champs_vide), Toast.LENGTH_LONG);
            lastToast.show();

        }else{
            showDialogLoading();

            OkHttpClient client = new OkHttpClient.Builder()
                    .connectTimeout(20, TimeUnit.SECONDS)
                    .callTimeout(30, TimeUnit.SECONDS)
                    .writeTimeout(30, TimeUnit.SECONDS)
                    .readTimeout(30, TimeUnit.SECONDS)
                    .retryOnConnectionFailure(false)
                    .build();

            RequestBody requestBody = new MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart("code", "" +code)
                    .addFormDataPart("token", "" +token)
                    .addFormDataPart("password", "" +newpassword)
                    .build();
            Request request = new Request.Builder()
                    .url(resetpassword)
                    .post(requestBody)
                    .addHeader("Content-Type", "application/json")
                    .addHeader("X-Requested-With", "XHLHttpRequest")
                    .build();

            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    closeDialogLoading();
                    showToastFromBackground("nointernet");

                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    String fpc_json = response.body().string();
                    closeDialogLoading();
                    Log.e(">>>> onResponse <<<", "onResponse: "+fpc_json);
                    if(fpc_json.equals("invalid")){

                        showToastFromBackground("invalid");
                        finish();
                    }
                    if(fpc_json.equals("done")){
                        Utils.deleteusers(ResetPasswordActivity.this);
                        showToastFromBackground("done");
                        Intent intent = new Intent(ResetPasswordActivity.this, SplashScreen.class);
                        startActivity(intent);
                        finish();

                    }





                }

            });

        }


    }
}
