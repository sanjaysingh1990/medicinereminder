package com.medi.reminder.realm.realm.repository.impl;

import android.util.Log;

import com.medi.reminder.app.MedicineAlertApp;
import com.medi.reminder.realm.model.Medicine;
import com.medi.reminder.realm.realm.repository.IMedicineRepository;
import com.medi.reminder.realm.realm.table.RealmTable;

import java.util.UUID;

import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;

/**
 * Created by roma on 16.10.15.
 */
public class MedicineRepository implements IMedicineRepository {

    @Override
    public void addStudent(Medicine medicine, OnSaveStudentCallback callback) {
        Realm realm = Realm.getInstance(MedicineAlertApp.getInstance());
        realm.beginTransaction();
        Medicine realmMedicine = realm.createObject(Medicine.class);
        realmMedicine.setId(UUID.randomUUID().toString());
        realmMedicine.setMedicineName(medicine.getMedicineName());
        realmMedicine.setTakeMedicineTime(medicine.getTakeMedicineTime());
        realmMedicine.setExpiryMedicineTime(medicine.getExpiryMedicineTime());
        realmMedicine.setMedicineImageUrl1(medicine.getMedicineImageUrl1());
        realmMedicine.setMedicineImageUrl2(medicine.getMedicineImageUrl2());
        realmMedicine.setNotificationId(medicine.getNotificationId());
        realmMedicine.setHistorytype(medicine.getHistorytype());


        realm.commitTransaction();

        if (callback != null)
            callback.onSuccess();
    }



    @Override
    public void deleteStudentById(String id, OnDeleteStudentCallback callback) {
        Realm realm = Realm.getInstance(MedicineAlertApp.getInstance());
        realm.beginTransaction();
        Medicine result = realm.where(Medicine.class).equalTo(RealmTable.ID, id).findFirst();
        result.removeFromRealm();
        realm.commitTransaction();

        if (callback != null)
            callback.onSuccess();
    }

    @Override
    public void deleteStudentByPosition(int position, OnDeleteStudentCallback callback) {
        Realm realm = Realm.getInstance(MedicineAlertApp.getInstance());
        realm.beginTransaction();
        RealmQuery<Medicine> query = realm.where(Medicine.class);
        RealmResults<Medicine> results = query.findAll();
        results.remove(position);
        realm.commitTransaction();

        if (callback != null)
            callback.onSuccess();
    }

    @Override
    public void getAllStudents(int historytype,OnGetAllStudentsCallback callback) {
        Realm realm = Realm.getInstance(MedicineAlertApp.getInstance());
        RealmResults<Medicine> results = realm.where(Medicine.class).equalTo(RealmTable.Medicine.historytype,historytype).findAll();
        Log.e("datalen",results.size()+"");
        if (callback != null)
            callback.onSuccess(results);
        else Log.e("callabck","is null");


    }



    @Override
    public void getStudentById(String id, OnGetStudentByIdCallback callback) {
        Realm realm = Realm.getInstance(MedicineAlertApp.getInstance());
        Medicine medicine = realm.where(Medicine.class).equalTo(RealmTable.ID, id).findFirst();

        if (callback != null)
            callback.onSuccess(medicine);
    }


    @Override
    public void updateByNotiId(int id, OnUpdateCallback callback) {
        Realm realm = Realm.getInstance(MedicineAlertApp.getInstance());

            Medicine toEdit = realm.where(Medicine.class)
                    .equalTo(RealmTable.Medicine.notificationId,id).findFirst();
            realm.beginTransaction();
            toEdit.setHistorytype(0); // move to expiry list
            realm.commitTransaction();
        if(callback!=null)
        {
            callback.onSuccess("item move to past list");
        }

    }


}
