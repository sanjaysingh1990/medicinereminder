package com.medi.reminder.receiver;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.medi.reminder.Constants;
import com.medi.reminder.MyAlertDialog;

public class MyReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {


        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        int id = intent.getExtras().getInt(Constants.NOTIFICATION_ID);
        int eventType = intent.getExtras().getInt(Constants.NOTIFICATION_FOR);
        Log.e("Receiver", "called" + id + "," + intent.getExtras().getString(Constants.MEDICINE_NAME));

        if (eventType == 2) {
            Intent intentHome = new Intent(context, MyAlertDialog.class);
            intentHome.putExtra(Constants.MEDICINE_NAME, intent.getStringExtra(Constants.MEDICINE_NAME));
            intentHome.putExtra(Constants.MEDICINE_EXPIRY_TIME, intent.getStringExtra(Constants.MEDICINE_EXPIRY_TIME));
            intentHome.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intentHome);
            Intent it = new Intent(Intent.ACTION_CLOSE_SYSTEM_DIALOGS);
            context.sendBroadcast(it);
        }
        notificationManager.cancel(id);


    }
}