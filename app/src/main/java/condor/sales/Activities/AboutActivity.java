package condor.sales.Activities;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import condor.sales.R;

public class AboutActivity extends AppCompatActivity {
    private ImageView back_btn,facebook,twitter,youtube,instagram ;
    TextView condordz ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        setContentView(R.layout.activity_about);

        back_btn = findViewById(R.id.back_btn);
        facebook = findViewById(R.id.facebook);
        twitter = findViewById(R.id.twitter);
        youtube = findViewById(R.id.youtube);
        instagram = findViewById(R.id.instagram);
        condordz = findViewById(R.id.condordz);

        condordz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openCondorWebSite("http://www.condor.dz/fr/");

            }
        });

        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               AboutActivity.super.onBackPressed();

            }
        });

        facebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openFacebookPage("CondorMobileDZ/");

            }
        });

        twitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                openTwitterPage("Condor_MobileFR");
            }
        });

        youtube.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openYoutubePage("UC1hi_DWWez7E73iJfq1OLzw");

            }
        });

        instagram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                openInstagramPage("condor_mobile/?hl=fr");
            }
        });

        TextView b = findViewById(R.id.info);
        ImageView v = findViewById(R.id.nvertbtn);

        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String mobileNumber = "3075";
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_DIAL); // Action for what intent called for
                intent.setData(Uri.parse("tel: " + mobileNumber)); // Data with intent respective action on intent
                startActivity(intent);
            }
        });

        PackageManager manager = this.getPackageManager();
        try {
            PackageInfo info = manager.getPackageInfo(this.getPackageName(), PackageManager.GET_ACTIVITIES);
            b.setText("Version "+info.versionName);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }




    }

    @Override
    protected void onPause() {
        super.onPause();
        if (isFinishing()) {
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        }  }


    private void openFacebookPage(String pageId) {
        String pageUrl = "https://www.facebook.com/" + pageId;

        try {
            ApplicationInfo applicationInfo = getPackageManager().getApplicationInfo("com.facebook.katana", 0);

            if (applicationInfo.enabled) {
                int versionCode = getPackageManager().getPackageInfo("com.facebook.katana", 0).versionCode;
                String url;

                if (versionCode >= 3002850) {
                    url = "fb://facewebmodal/f?href=" + pageUrl;
                } else {
                    url = "fb://page/" + pageId;
                }

                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
            } else {
                throw new Exception("Facebook is disabled");
            }
        } catch (Exception e) {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(pageUrl)));
        }
    }


    private void openYoutubePage(String pageId) {
        String pageUrl = "https://www.youtube.com/channel/" + pageId;

        try {
            ApplicationInfo applicationInfo = getPackageManager().getApplicationInfo("com.google.android.youtube", 0);

            if (applicationInfo.enabled) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(pageUrl));
                intent.setPackage("com.google.android.youtube");
                startActivity(intent);
            } else {
                throw new Exception("Youtube is disabled");
            }
        } catch (Exception e) {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(pageUrl)));
        }
    }



    private void openTwitterPage(String pageId) {
        String pageUrl = "https://twitter.com/" + pageId;

        try {
            ApplicationInfo applicationInfo = getPackageManager().getApplicationInfo("com.twitter.android", 0);

            if (applicationInfo.enabled) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(pageUrl));
                intent.setPackage("com.twitter.android");
                startActivity(intent);

            } else {
                throw new Exception("Youtube is disabled");
            }
        } catch (Exception e) {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(pageUrl)));
        }
    }


    private void openInstagramPage(String pageId) {
        String pageUrl = "https://www.instagram.com/" + pageId;

        try {
            ApplicationInfo applicationInfo = getPackageManager().getApplicationInfo("com.instagram.android", 0);

            if (applicationInfo.enabled) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(pageUrl));
                intent.setPackage("com.instagram.android");
                startActivity(intent);

            } else {
                throw new Exception("instagram is disabled");
            }
        } catch (Exception e) {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(pageUrl)));
        }
    }


    private void openCondorWebSite(String pageUrl) {

            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(pageUrl)));

    }

}
