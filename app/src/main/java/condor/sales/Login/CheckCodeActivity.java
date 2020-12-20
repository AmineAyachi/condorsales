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
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import cn.pedant.SweetAlert.SweetAlertDialog;
import condor.sales.Constants;
import condor.sales.R;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import static condor.sales.Constants.verifiytoken;
import static condor.sales.Utils.Utils.getDeviceMetrics;

public class CheckCodeActivity extends AppCompatActivity {
    Button send_btn;
    EditText forgetEmail;
    SweetAlertDialog mresetpassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_code);


        forgetEmail = findViewById(R.id.forgetEmail);
        send_btn = findViewById(R.id.recuperer_password_btn);
        send_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(getIntent().getStringExtra("CODE") == null){
                    finish();
                }
                String code = getIntent().getStringExtra("CODE");
                String token = forgetEmail.getText().toString();


                if(code != null && !code.equals("") && !token.equals("") && !token.isEmpty()){
                    checkcode(code,token);
                }else{

                    lastToast = Toast.makeText(CheckCodeActivity.this, getString(R.string.champs_vide), Toast.LENGTH_LONG);
                    lastToast.show();
                }

            }
        });

    }

    private void checkcode(String code, String token) {


        if(!code.isEmpty() || token.isEmpty()){

            showDialogLoading();
            OkHttpClient client = new OkHttpClient();

            RequestBody requestBody = new MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart("code", "" +code)
                    .addFormDataPart("token", "" +token)
                    .build();
            Request request = new Request.Builder()
                    .url(verifiytoken)
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
                    JSONObject obj = null;
                    closeDialogLoading();
                    Log.e(">>>> onResponse <<<", "onResponse: "+fpc_json);
                    if(fpc_json.equals("invalid")){
                        showToastFromBackground("invalid");
                    }else {
                        try {
                            obj = new JSONObject(fpc_json);
                            Log.e("befor send -->", "onResponse Exception -----> " + obj.getString("token") );
                            Intent intent = new Intent(CheckCodeActivity.this, ResetPasswordActivity.class);
                            intent.putExtra("token",  obj.getString("token"));
                            intent.putExtra("code",  obj.getString("email"));
                            startActivity(intent);

                        } catch (JSONException e) {
                            e.printStackTrace();
                            // Toast.makeText(AcceuilActivity.this, "somthing went wrong", Toast.LENGTH_LONG).show();
                            closeDialogLoading();
                            showToastFromBackground("nointernet");
                            Log.e("TagLogin", "onResponse Exception -----> " + e);
                        }
                    }


                }

            });


        }else{

            Toast.makeText(CheckCodeActivity.this, getString(R.string.champs_vide), Toast.LENGTH_LONG).show();


        }


    }

    private void showDialogLoading() {

        mresetpassword = new SweetAlertDialog(CheckCodeActivity.this, SweetAlertDialog.PROGRESS_TYPE);
        mresetpassword.setContentText(getString(R.string.Patienter));
        mresetpassword.setTitleText("Validation en cours");
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

                    if (message.equals("invalid")) {
//                        Dialog_Connexion cdd=new Dialog_Connexion( Splash.this);
//                        cdd.setCancelable(false);
//                        cdd.show();
                        send_btn.setEnabled(true);
                        lastToast = Toast.makeText(CheckCodeActivity.this, "Le code que vous tentez d'utiliser n'est pas valide", Toast.LENGTH_LONG);
                        lastToast.show();


                    }

                    if (message.equals("nointernet")) {
//                        Dialog_Connexion cdd=new Dialog_Connexion( Splash.this);
//                        cdd.setCancelable(false);
//                        cdd.show();
                        lastToast = Toast.makeText(CheckCodeActivity.this, getString(R.string.connexion_verification), Toast.LENGTH_LONG);
                        lastToast.show();


                    }




                }
            });
        }
    }
}
