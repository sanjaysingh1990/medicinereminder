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
        Log.e("Receiver", "called" + id + "," + eventType);

        if (eventType == 2) {
            Intent intentHome = new Intent(context, MyAlertDialog.class);
            intentHome.putExtra(Constants.MEDICINE_NAME, "frzi");
            //intentHome.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intentHome.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intentHome.setAction(Intent.ACTION_CLOSE_SYSTEM_DIALOGS);
            context.startActivity(intentHome);
        }
        notificationManager.cancel(id);

        Intent it = new Intent(Intent.ACTION_CLOSE_SYSTEM_DIALOGS);
        context.sendBroadcast(it);
    }
}