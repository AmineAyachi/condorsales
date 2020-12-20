package condor.sales.Services;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.util.Log;
import android.widget.RemoteViews;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.ArrayList;

import condor.sales.Models.News;
import condor.sales.R;
import condor.sales.Utils.Utils;

import static condor.sales.Constants.all_News;

public class MsgFirebaseService extends FirebaseMessagingService {
    public MsgFirebaseService() {

    }


    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        Log.d("Firebase Service", "From: " + remoteMessage.getFrom());

        String channelId = "";
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            channelId = createNotificationChannel();
        } else {
            // If earlier version channel ID is not used
            // https://developer.android.com/reference/android/support/v4/app/NotificationCompat.Builder.html#NotificationCompat.Builder(android.content.Context)
            channelId ="";
        }

        if (remoteMessage.getData().size() > 0) {



        }

        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {

            String notification_title = remoteMessage.getNotification().getTitle();
            String notification_message = remoteMessage.getNotification().getBody();
            News news = new News(notification_title,notification_message);
            all_News.clear();
            all_News = Utils.getNews(this);
            all_News.add(news);
            if(all_News.size() > 10){
               all_News.remove(0);
                Utils.saveNews(all_News,this);
            }else{
                Utils.saveNews(all_News,this);
            }
            Intent intent = new Intent("com.push.message.received");
            intent.putExtra("message",notification_message);// Add more data as per need
            sendBroadcast(intent);


            String click_action = remoteMessage.getNotification().getClickAction();
            String from_user_id = remoteMessage.getData().get("from_user_id");
            Notification.Builder mBuilder;
            RemoteViews notificationLayout = new RemoteViews(this.getPackageName(), R.layout.fb_notification_layout);
            RemoteViews notificationLayoutExpanded = new RemoteViews(this.getPackageName(), R.layout.fb_notification_layout);


            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.cs_blue_circle);
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                 mBuilder = new Notification.Builder(this, channelId)
                        .setSmallIcon(R.drawable.cs_blue_circle)
                         .setLargeIcon(bitmap)
                        .setContentTitle(notification_title)
                        .setContentText(notification_message);
            }else{

               mBuilder =
                        new  Notification.Builder(this)
                                .setSmallIcon(R.drawable.cs_blue_circle)
                                .setLargeIcon(bitmap)
                                .setContentTitle(notification_title)
                                .setContentText(notification_message);
            }



            Intent resultIntent = new Intent(click_action);
            resultIntent.putExtra("user_id", from_user_id);

            PendingIntent resultPendingIntent =
                    PendingIntent.getActivity(
                            this,
                            0,
                            resultIntent,
                            PendingIntent.FLAG_UPDATE_CURRENT
                    );

            mBuilder.setContentIntent(resultPendingIntent);

            int mNotificationId = (int) System.currentTimeMillis();


            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
            notificationManager.notify(mNotificationId, mBuilder.build());

        }

        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated. See sendNotification method below.
    }
    private String createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "CS";
            String description = "CS";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("CS", name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
        return "CS";
    }
}
