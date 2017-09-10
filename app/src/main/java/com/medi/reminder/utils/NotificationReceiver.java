package com.medi.reminder.utils;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.util.Log;

import com.medi.reminder.R;
import com.medi.reminder.history.HistoryActivity;


/**
 * Created by android on 5/12/16.
 */

public class NotificationReceiver extends WakefulBroadcastReceiver {
    private static final
    String MESSAGE =
            "gcm.notification.body";
    String TITLE =
            "gcm.notification.title";
    String MATCH_ID = "gcm.notification.match_id";
    String EVENT_TYPE = "gcm.notification.event_type";
    String MATCH_TIME = "gcm.notification.start_time";
    private String event_type="0";
    private String match_id="";
    private String mMatchStartTime;
    private String mMessage="";
    private int NOTIID;

    @Override
    public void onReceive(final Context context, Intent data) {



        /*
        *********************** if user logout the from app to avoid notifications device token is not deleted from backend while logout
         */
//        if (!Utilities.getInstance().getValueFromSharedPreference(Constants.REACHED_HOME, false, context))
//            return;
//

        try
        {
            mMessage=data.getStringExtra(MESSAGE);
            event_type = data.getExtras().get(EVENT_TYPE).toString();
            match_id = data.getStringExtra(MATCH_ID);

        } catch (Exception ex) {
            Log.e("pushdata", ex.getMessage() + "");


        }



         showNotificaiton(context);




    }

    private void showNotificaiton(Context context)
    {

        NOTIID = (int) System.currentTimeMillis();
        //   Logger.e("notiid",NOTIID+"");

        Intent intent = new Intent(context,HistoryActivity.class);

        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, NOTIID, intent, PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat
                .Builder(context)
                .setSmallIcon(R.drawable.ic_app_logo)
                .setContentTitle("CRICiT")
                .setContentText(mMessage)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);
        notificationBuilder.setSmallIcon(getNotificationIcon(notificationBuilder));

        notificationBuilder.setDefaults(Notification.DEFAULT_SOUND);
        notificationBuilder.setDefaults(Notification.DEFAULT_VIBRATE);
        notificationBuilder.setDefaults(Notification.DEFAULT_LIGHTS);

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(NOTIID, notificationBuilder.build());

        //to abort default notificaiton receiver of firebase
        abortBroadcast();
    }


    /**
     * ************************ notification icon below and above lollipop **********************
     *
     * @param notificationBuilder
     * @return
     */
    private int getNotificationIcon(NotificationCompat.Builder notificationBuilder) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            int color = Color.parseColor("#EF6A4A");
            notificationBuilder.setColor(color);
            return R.drawable.ic_app_logo;

        } else {
            return R.drawable.ic_app_logo;
        }
    }


}
