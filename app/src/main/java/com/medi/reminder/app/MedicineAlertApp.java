package com.medi.reminder.app;

import android.app.Application;
import android.content.Context;


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
//        RealmConfiguration config = new RealmConfiguration.Builder(getApplicationContext()).deleteRealmIfMigrationNeeded().setModules(new SimpleRealmModule()).build();
//        Realm.setDefaultConfiguration(config);
    }

    public static Context getContextValue()
    {
        return instance;
    }

    public static Realm getInstance() {
        Realm realm;
        try{
            realm = Realm.getDefaultInstance();

        }catch (Exception e){

            // Get a Realm instance for this thread
            RealmConfiguration config = new RealmConfiguration.Builder(instance)
                    .deleteRealmIfMigrationNeeded()
                    .build();
            realm = Realm.getInstance(config);

        }
        return realm;
    }
}
