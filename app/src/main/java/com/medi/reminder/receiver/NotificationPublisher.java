package com.medi.reminder.receiver;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import com.medi.reminder.Constants;
import com.medi.reminder.activity.MainActivity;
import com.medi.reminder.realm.IMedicineContract;
import com.medi.reminder.realm.model.ContactData;
import com.medi.reminder.realm.model.Medicine;
import com.medi.reminder.realm.presenters.impl.MedicinePresenter;
import com.medi.reminder.utils.SendPushFromDevice;
import com.medi.reminder.utils.Utils;

import io.realm.RealmResults;

public class NotificationPublisher extends BroadcastReceiver implements IMedicineContract {

    public static String NOTIFICATION_ID = "notification-id";
    public static String NOTIFICATION = "notification";
    private Context mContext;

    public void onReceive(Context context, Intent intent) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Notification notification = intent.getParcelableExtra(NOTIFICATION);
        int id = intent.getIntExtra(NOTIFICATION_ID, 0);
        Log.e("notiidnotipub", id + "'");
        notificationManager.notify(id, notification);
        MedicinePresenter medicinePresenter = new MedicinePresenter(this);
        medicinePresenter.updateByNotiId(id);
        mContext = context;
        new GetData().execute();

    }

    class GetData extends AsyncTask<String, Void, String> {


        @Override
        protected String doInBackground(String... strings) {
            SendPushFromDevice sendPushFromDevice = new SendPushFromDevice();
            try {
                String deviceToken = Utils.getInstance().getValue(Constants.DEVICE_TOKEN, "", mContext);
                return sendPushFromDevice.sendPush(deviceToken);
            } catch (Exception ex) {
                return ex.getMessage();
            }
        }

        @Override
        protected void onPostExecute(String s) {
            Log.e("data", s + "");
        }
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
        Log.e("updated", "yes");
    }
}