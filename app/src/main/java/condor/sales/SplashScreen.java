package condor.sales;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import com.wang.avi.AVLoadingIndicatorView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import android.os.Handler;
import android.view.View;
import android.widget.Toast;
import condor.sales.Login.LoginActivity;

public class SplashScreen extends AppCompatActivity {


    int secondsDelayed = 2;
    private AVLoadingIndicatorView progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        Constants.custom_font = ResourcesCompat.getFont(this, R.font.montserratr);
        progressBar = findViewById(R.id.loading_bar);
        progressBar.setVisibility(View.VISIBLE);

        if (ContextCompat.checkSelfPermission(SplashScreen.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(SplashScreen.this, new String[]{Manifest.permission.CALL_PHONE},1);
        }else{

            if (ContextCompat.checkSelfPermission(SplashScreen.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(SplashScreen.this, new String[]{Manifest.permission.CAMERA},2);
            }else{

                if (ContextCompat.checkSelfPermission(SplashScreen.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(SplashScreen.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},3);
                }else{
                    if (ContextCompat.checkSelfPermission(SplashScreen.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(SplashScreen.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},4);
                    }else{
                        startSplach();
                    }

                }


            }
        }



    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 1: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ContextCompat.checkSelfPermission(SplashScreen.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(SplashScreen.this, new String[]{Manifest.permission.CAMERA},2);
                    }else{
                        startSplach();

                    }
                }
                else
                {
                    Toast.makeText(SplashScreen.this, "Cette application a besoin de ces autorisations pour fonctionner correctement!",
                            Toast.LENGTH_LONG).show();
                    SplashScreen.this.finish();
                }



                return;
            }

            case 2 :{

                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ContextCompat.checkSelfPermission(SplashScreen.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(SplashScreen.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},3);
                    }else{
                        startSplach();

                    }
                }
                else
                {
                    Toast.makeText(SplashScreen.this, "Cette application a besoin de ces autorisations pour fonctionner correctement!",
                            Toast.LENGTH_LONG).show();

                    SplashScreen.this.finish();
                }



                return;
            }

            case 3 :{

                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ContextCompat.checkSelfPermission(SplashScreen.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(SplashScreen.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},4);
                    }else{
                        startSplach();

                    }
                }
                else
                {
                    Toast.makeText(SplashScreen.this, "Cette application a besoin de ces autorisations pour fonctionner correctement!",
                            Toast.LENGTH_LONG).show();

                    SplashScreen.this.finish();
                }



                return;
            }

            case 4 :{

                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    startSplach();
                }
                else
                {
                    Toast.makeText(SplashScreen.this, "Cette application a besoin de ces autorisations pour fonctionner correctement!",
                            Toast.LENGTH_LONG).show();

                    SplashScreen.this.finish();
                }



                return;
            }
        }
    }



    private void startSplach() {

        new Handler().postDelayed(new Runnable() {
            public void run() {

                progressBar.setVisibility(View.INVISIBLE);
                // connected_user_id = "8";
                //Intent myIntent = new Intent(SplashScreen.this, CollectFromAgency.class);

                Intent myIntent = new Intent(SplashScreen.this, LoginActivity.class);
                startActivity(myIntent);
                finish();


            }
        }, secondsDelayed * 1000);
    }


    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public void onBackPressed() {

        super.onBackPressed();


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


}
