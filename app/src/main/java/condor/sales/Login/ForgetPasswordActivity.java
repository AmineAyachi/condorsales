package condor.sales.Login;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
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
import static condor.sales.Constants.sendcodefp;
import static condor.sales.Utils.Utils.getDeviceMetrics;


public class ForgetPasswordActivity extends AppCompatActivity {
    Button send_btn;
    EditText forgetEmail;
    SweetAlertDialog mresetpassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);

        forgetEmail = findViewById(R.id.forgetEmail);
        send_btn = findViewById(R.id.recuperer_password_btn);


        send_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                resetpasswoord();
            }
        });

    }

    private void showDialogLoading() {

        mresetpassword = new SweetAlertDialog(ForgetPasswordActivity.this, SweetAlertDialog.PROGRESS_TYPE);
        mresetpassword.setContentText(getString(R.string.Patienter));
        mresetpassword.setTitleText("Vérification en cours");
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

                    if (message.equals("usernotfound")) {
//                        Dialog_Connexion cdd=new Dialog_Connexion( Splash.this);
//                        cdd.setCancelable(false);
//                        cdd.show();
                        send_btn.setEnabled(true);
                        lastToast = Toast.makeText(ForgetPasswordActivity.this, "Votre code pos n'est pas autorisé à accéder à ce service. merci de contacter l'administrateur", Toast.LENGTH_LONG);
                        lastToast.show();

                    }
                    if (message.equals("codesent")) {

                        lastToast = Toast.makeText(ForgetPasswordActivity.this, "Le code de vérification a bien été envoyé à votre e-mail", Toast.LENGTH_LONG);
                        lastToast.show();


                    }

                    if (message.equals("nointernet")) {
//                        Dialog_Connexion cdd=new Dialog_Connexion( Splash.this);
//                        cdd.setCancelable(false);
//                        cdd.show();
                        lastToast = Toast.makeText(ForgetPasswordActivity.this, getString(R.string.connexion_verification), Toast.LENGTH_LONG);
                        lastToast.show();


                    }


                }
            });
        }
    }



    private void resetpasswoord() {
        send_btn.setEnabled(false);
        String code = forgetEmail.getText().toString().trim();
//send password reset request to sever using pos code

        if(!code.isEmpty()){

            showDialogLoading();
            OkHttpClient client = new OkHttpClient();

            RequestBody requestBody = new MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart("code", "" +forgetEmail.getText())
                    .build();
            Request request = new Request.Builder()
                    .url(sendcodefp)
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
                    if(fpc_json.equals("usernotfound")){

                        showToastFromBackground("usernotfound");
                    }
                    if(fpc_json.equals("done")){
                        showToastFromBackground("codesent");
                        startcodeverification(forgetEmail.getText().toString());
                    }

                }

            });


        }else{

            Toast.makeText(ForgetPasswordActivity.this, getString(R.string.champs_vide), Toast.LENGTH_LONG).show();
        }

    }

    private void startcodeverification(String code) {

        Intent intent = new Intent(ForgetPasswordActivity.this, CheckCodeActivity.class);
        intent.putExtra("CODE", code);
        startActivity(intent);
    }

    @Override
    protected void onResume() {

        send_btn.setEnabled(true);
        super.onResume();
    }

}
