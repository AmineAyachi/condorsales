package condor.sales.Dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.Settings;
import android.util.Log;
import android.view.KeyboardShortcutGroup;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import condor.sales.BuildConfig;
import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import condor.sales.R;

public class DialogNewUpdate extends Dialog implements android.view.View.OnClickListener {

    public Activity c;
    public Dialog d;
    Button close_btn,download_btn;
    TextView text;
    String url;

    public DialogNewUpdate(Activity a , String url ) {

        super(a);
        // TODO Auto-generated constructor stub
        this.c = a;
        this.url = url;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialognewupdate);
        this.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        close_btn = findViewById(R.id.close_btn);
        download_btn = findViewById(R.id.ok_btn);
        close_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                c.finish();
            }
        });

        download_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                download_btn.setEnabled(false);
                download_btn.setText("Mise a jour...");
                final Calendar cc = Calendar.getInstance();
                SimpleDateFormat df = new SimpleDateFormat("ddMMyyyyHHmmss");
                String formattedDate = df.format(cc.getTime());
                final String filename= "cs_update_"+formattedDate+".apk";





                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    if(!c.getPackageManager().canRequestPackageInstalls()){
                        c.startActivity(new Intent(Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES, Uri.parse("package:condor.sales")));
                        download_btn.setEnabled(true);
                        download_btn.setText("Mettre à jour");
                    }else{

                        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
                        Log.e("url..................",""+url);
                        request.setDescription("Telechargement des mises à jour");
                        request.setTitle("Condor Sales");
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                            request.allowScanningByMediaScanner();
                            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                        }
                        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, filename);

                        DownloadManager manager = (DownloadManager) c.getSystemService(Context.DOWNLOAD_SERVICE);
                        final long downloadId = manager.enqueue(request);


                        BroadcastReceiver downloadReceiver = new BroadcastReceiver() {
                            @Override
                            public void onReceive(Context context, Intent intent) {
                                //check if the broadcast message is for our enqueued download
                                long referenceId = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);

                                if(downloadId == referenceId){
                                    File toInstall = new File( Environment.getExternalStorageDirectory() + "/Download/",filename);
                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                                        Uri apkUri = FileProvider.getUriForFile(c, BuildConfig.APPLICATION_ID + ".fileprovider", toInstall);
                                        intent = new Intent(Intent.ACTION_INSTALL_PACKAGE);
                                        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        intent.setData(apkUri);
                                        context.startActivity(intent);
                                    } else {
                                        Uri apkUri = Uri.fromFile(toInstall);
                                        Intent update = new Intent(Intent.ACTION_VIEW);
                                        update.setDataAndType(apkUri, "application/vnd.android.package-archive");
                                        update.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                        context.startActivity(update);
                                    }
                                }

                            }
                        };
                        c.registerReceiver(downloadReceiver, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));

                    }

                }else{

                    DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
                    Log.e("url..................",""+url);
                    request.setDescription("Telechargement des mises à jour");
                    request.setTitle("Condor Sales");
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                        request.allowScanningByMediaScanner();
                        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                    }
                    request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, filename);

                    DownloadManager manager = (DownloadManager) c.getSystemService(Context.DOWNLOAD_SERVICE);
                    final long downloadId = manager.enqueue(request);


                    BroadcastReceiver downloadReceiver = new BroadcastReceiver() {
                        @Override
                        public void onReceive(Context context, Intent intent) {
                            //check if the broadcast message is for our enqueued download
                            long referenceId = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);

                            if(downloadId == referenceId){
                                File toInstall = new File( Environment.getExternalStorageDirectory() + "/Download/",filename);
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                                    Uri apkUri = FileProvider.getUriForFile(c, BuildConfig.APPLICATION_ID + ".fileprovider", toInstall);
                                    intent = new Intent(Intent.ACTION_INSTALL_PACKAGE);
                                    intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    intent.setData(apkUri);
                                    context.startActivity(intent);
                                } else {
                                    Uri apkUri = Uri.fromFile(toInstall);
                                    Intent update = new Intent(Intent.ACTION_VIEW);
                                    update.setDataAndType(apkUri, "application/vnd.android.package-archive");
                                    update.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    context.startActivity(update);
                                }
                            }
                        }
                    };
                    c.registerReceiver(downloadReceiver, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));

                }

            }

        });
    }
    @Override
    public void onBackPressed() {

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



}
