package condor.sales.Dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyboardShortcutGroup;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.Nullable;
import java.io.IOException;
import java.util.List;
import cn.pedant.SweetAlert.SweetAlertDialog;
import condor.sales.R;
import condor.sales.Utils.Utils;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import static condor.sales.Constants.changepassword;
import static condor.sales.Constants.custom_font;
import static condor.sales.Constants.list_users;
import static condor.sales.Utils.Utils.getDeviceMetrics;
import static condor.sales.Utils.Utils.saveUsers;

public class DialogEditPassword extends Dialog implements android.view.View.OnClickListener {


    public Activity c;
    public Dialog d;
    boolean opp_encours = false;
    SweetAlertDialog mConnextionEncours;
    Button close_btn, save_btn;
    EditText eFirstPassword, eSecondPassword, eOldPassword;


    public DialogEditPassword(Activity a) {

        super(a);
        // TODO Auto-generated constructor stub
        this.c = a;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialogchangepassword);
        this.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        close_btn = findViewById(R.id.close_btn);
        eFirstPassword = findViewById(R.id.new_password);
        eSecondPassword = findViewById(R.id.r_new_password);
        eOldPassword = findViewById(R.id.old_password);
        save_btn = findViewById(R.id.save_btn);
        close_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        save_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String oldPassword = "", firstPassword = "", secondPassword = "";
                oldPassword = eOldPassword.getText().toString().trim();
                firstPassword = eFirstPassword.getText().toString().trim();
                secondPassword = eSecondPassword.getText().toString().trim();
                changePassword(oldPassword, firstPassword, secondPassword);
            }
        });

        // TODO: 23-03-2020  implement incentive history list interface and controller
    }

    @Override
    public void onBackPressed() {
        // super.onBackPressed();
        dismiss();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.close_btn:

                break;

            default:
                break;
        }
    }

    @Override
    public void onProvideKeyboardShortcuts(List<KeyboardShortcutGroup> data, @Nullable Menu menu, int deviceId) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }


    private void changePassword(String oldPassword, String firstPassword, String secondPassword) {

       if(oldPassword.equals("") || firstPassword.equals("")  || secondPassword.equals("") ){

           Toast.makeText(c, " Veuillez remplir les champs manquants !", Toast.LENGTH_LONG).show();


       }else{

           if (firstPassword.equals(secondPassword)) {

               if(firstPassword.length() < 8 || secondPassword.length() < 8 ){

                   Toast.makeText(c, " Mot de passe trop court (8 min) !", Toast.LENGTH_LONG).show();
               }else{
                   showDialogLoading();
                   httpRequest (oldPassword ,firstPassword ,secondPassword) ;

               }




           } else {

               Toast.makeText(c, "Les mots de passe ne correspondent pas !", Toast.LENGTH_LONG).show();
           }
       }




    }

private void httpRequest (String oldPassword, final String firstPassword, String secondPassword){


        OkHttpClient client = new OkHttpClient();

    RequestBody requestBody = new MultipartBody.Builder()
            .setType(MultipartBody.FORM)
            .addFormDataPart("oldPassword", "" + oldPassword)
            .addFormDataPart("firstPassword", "" + firstPassword)
            .addFormDataPart("secondPassword", "" +secondPassword )
            .build();
    Request request = new Request.Builder()
            .url(changepassword)
            .post(requestBody)
            .addHeader("Accept", "application/json")
            .addHeader("Authorization", "Bearer " + list_users.get(0).getToken())
            .addHeader("Content-Type", "multipart/form-data")
            .build();

    client.newCall(request).enqueue(new Callback() {
        @Override
        public void onFailure(Call call, IOException e) {
            // inser shared pref actualite last

            //Log.e("onFailure", "" + e);
            closeDialogLoading();
            showToastFromBackground("nointernet","");

        }


        @Override
        public void onResponse(Call call, Response response) throws IOException {
            String jsonResponse = response.body().string();
            Boolean found =false ;

            closeDialogLoading();
            Log.e("changepasswordresponse", "jsonResponse: "+jsonResponse );



            if(jsonResponse.equals("ok")){
                showToastFromBackground("ok" ,firstPassword);
                found =true ;
            }

            if(jsonResponse.equals("notok")){
                showToastFromBackground("notok","");
                found =true;
            }
            if(jsonResponse.equals("wrong")){
                showToastFromBackground("wrong","");
                found = true ;
            }

            if(!found){


                if (jsonResponse.contains("Unauthenticated")) {
                    showToastFromBackground("Unauthenticated","");
                }else{

                    showToastFromBackground("notok","");
                }


            }

        }  });


    }



    private void showDialogLoading() {
        opp_encours = true;
        mConnextionEncours = new SweetAlertDialog(c, SweetAlertDialog.PROGRESS_TYPE);
        mConnextionEncours.setContentText(c.getString(R.string.Patienter));
        mConnextionEncours.setTitleText(c.getString(R.string.connexion_encours));
        mConnextionEncours.getProgressHelper().setBarColor(c.getResources().getColor(R.color.circleble3));
        mConnextionEncours.setCancelable(false);
        c.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mConnextionEncours.show();
            }

        });

        TextView titleView = mConnextionEncours.findViewById(R.id.title_text);
        if (titleView != null) {
            titleView.setTypeface(custom_font);
            titleView.setWidth((int) (getDeviceMetrics(c).widthPixels * 0.9));
        }
        TextView messageView = mConnextionEncours.findViewById(R.id.content_text);
        if (messageView != null) messageView.setTypeface(custom_font);

    }

    private void closeDialogLoading() {
        opp_encours = false;
        c.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mConnextionEncours.dismissWithAnimation();
            }

        });


    }

    Toast lastToast = null;

    boolean isToastNotRunning() {
        return (lastToast == null || lastToast.getView().getWindowVisibility() != View.VISIBLE);
    }

    public void showToastFromBackground(final String message , final String firstPassword ) {
        if (isToastNotRunning()) {
            c.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    //lastToast = Toast.makeText(WilayaPicker.this, message, Toast.LENGTH_LONG);
                    //lastToast.show();

                    if (message.equals("nointernet")) {

                        lastToast = Toast.makeText(c, c.getString(R.string.connexion_verification), Toast.LENGTH_LONG);
                        lastToast.show();


                    }

                    if (message.equals("Unauthenticated")) {

                        lastToast = Toast.makeText(c, "Votre session a expiré. /n Connexion en cours... /n  Merci de réessayer une autre fois  ", Toast.LENGTH_LONG);
                        lastToast.show();
                        Utils.logMeIn(c);

                    }
                    if (message.equals("ok")) {

                        lastToast = Toast.makeText(c, "Votre mot de passe à été changé avec succès", Toast.LENGTH_LONG);
                        lastToast.show();
                        list_users.get(0).setPassword(firstPassword);
                        saveUsers(list_users, c.getApplicationContext());
                        DialogEditPassword.this.dismiss();


                    }
                    if (message.equals("notok")) {

                        lastToast = Toast.makeText(c, "Le format du mot de passe que vous avez entré n'est pas valide", Toast.LENGTH_LONG);
                        lastToast.show();


                    }
                    if (message.equals("wrong")) {

                        lastToast = Toast.makeText(c, "l ancien mot de passe que vous avez entré est incorrect", Toast.LENGTH_LONG);
                        lastToast.show();


                    }

                    if (message.equals("wrong_credentials")) {

                        lastToast = Toast.makeText(c, c.getString(R.string.wrong_credentials), Toast.LENGTH_LONG);
                        lastToast.show();


                    }


                }
            });
        }
    }

}
