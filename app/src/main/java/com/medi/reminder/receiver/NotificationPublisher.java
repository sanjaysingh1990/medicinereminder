package com.medi.reminder.receiver;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.medi.reminder.realm.IMedicineContract;
import com.medi.reminder.realm.model.ContactData;
import com.medi.reminder.realm.model.Medicine;
import com.medi.reminder.realm.presenters.impl.MedicinePresenter;

import io.realm.RealmResults;

public class NotificationPublisher extends BroadcastReceiver implements IMedicineContract {

    public static String NOTIFICATION_ID = "notification-id";
    public static String NOTIFICATION = "notification";

    public void onReceive(Context context, Intent intent) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Notification notification = intent.getParcelableExtra(NOTIFICATION);
        int id = intent.getIntExtra(NOTIFICATION_ID, 0);
        Log.e("notiidnotipub",id+"'");
        notificationManager.notify(id, notification);
        MedicinePresenter medicinePresenter=new MedicinePresenter(this);
        medicinePresenter.updateByNotiId(id);

    }

    @Override
    public void showStudents(RealmResults<Medicine> medicines) {
      //nothing to do
    }

    @Override
    public void showContacts(RealmResults<ContactData> contactsList) {
        //nothing to do
    }

    @Override
    public void showMessage(String message) {
        Log.e("updated","yes");
    }
}