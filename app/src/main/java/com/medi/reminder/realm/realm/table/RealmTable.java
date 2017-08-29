package com.medi.reminder.realm.realm.table;

/**
 * Created by roma on 16.10.15.
 */
public interface RealmTable {

    String ID = "id";



  public  interface Medicine{
        String NAME = "medicineName";
        String StartDateTime = "startdatetime";
        String ExpiryDateTime = "expirydatetime";
        String imageurl1 = "imageurl1";
        String imaegurl2 = "imageurl2";
        String notificationId = "notificationId";
        String historytype = "historytype";

    }
}
