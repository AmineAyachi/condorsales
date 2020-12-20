package condor.sales.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import static condor.sales.Constants.list_users;

import condor.sales.Dialogs.DialogEditPassword;
import condor.sales.R;

public class ProfileActivity extends AppCompatActivity {
    private ImageView back_btn , edit ;
    private TextView code,nom,email,phone,city,adresse,lastsync;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        setContentView(R.layout.activity_profile);

        code = findViewById(R.id.m_profile_code);
        edit = findViewById(R.id.edit_btn);
        nom =findViewById(R.id.m_profile_name);
        email = findViewById(R.id.m_profile_email);
        phone = findViewById(R.id.m_profile_phone);
        city = findViewById(R.id.m_profile_city);
        adresse = findViewById(R.id.m_profile_adresse);
        lastsync = findViewById(R.id.m_profile_lastsync);
        Log.e("---->", "connected user :"+list_users.get(0).getAdresse());
        code.setText(list_users.get(0).getCode());
        nom.setText(list_users.get(0).getName());
        email.setText(list_users.get(0).getEmail());
        phone.setText(list_users.get(0).getPhone());
        city.setText(list_users.get(0).getCity());
        adresse.setText(list_users.get(0).getAdresse());
        lastsync.setText(list_users.get(0).getLastsync());

        back_btn = findViewById(R.id.back_btn);
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ProfileActivity.super.onBackPressed();

            }
        });


        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogEditPassword cpd = new DialogEditPassword(ProfileActivity.this);
                cpd.setCancelable(false);
                cpd.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialogInterface) {


                    }
                });
                cpd.show();

            }
        });


    }


    @Override
    protected void onPause() {
        super.onPause();
        if (isFinishing()) {
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        }  }

}
