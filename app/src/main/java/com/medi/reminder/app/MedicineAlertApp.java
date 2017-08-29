package com.medi.reminder.app;

import android.app.Application;


import com.medi.reminder.realm.realm.module.SimpleRealmModule;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by roma on 15.10.15.
 */
public class MedicineAlertApp extends Application {

    private static MedicineAlertApp instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        RealmConfiguration config = new RealmConfiguration.Builder(getApplicationContext()).deleteRealmIfMigrationNeeded().setModules(new SimpleRealmModule()).build();
        Realm.setDefaultConfiguration(config);
    }

    public static MedicineAlertApp getInstance() {
        return instance;
    }
}
