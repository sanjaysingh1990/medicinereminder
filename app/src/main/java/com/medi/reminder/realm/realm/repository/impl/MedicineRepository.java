package com.medi.reminder.realm.realm.repository.impl;

import com.medi.reminder.app.MedicineAlertApp;
import com.medi.reminder.realm.model.ContactData;
import com.medi.reminder.realm.model.Medicine;
import com.medi.reminder.realm.realm.repository.IMedicineRepository;
import com.medi.reminder.realm.realm.table.RealmTable;

import java.util.UUID;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmQuery;
import io.realm.RealmResults;

/**
 * Created by roma on 16.10.15.
 */
public class MedicineRepository implements IMedicineRepository {

    @Override
    public void addStudent(Medicine medicine, OnSaveStudentCallback callback) {
        Realm realm = MedicineAlertApp.getInstance();


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
    public void addContact(ContactData contactData, OnSaveContactCallback callback) {
        Realm realm = MedicineAlertApp.getInstance();
        realm.beginTransaction();
        ContactData data = realm.createObject(ContactData.class);
        data.setId(UUID.randomUUID().toString());
        data.setContactName(contactData.getContactName());
        data.setPhoneNo(contactData.getPhoneNo());
        data.setSms(contactData.isSms());
        data.setPhone(contactData.isPhone());


        realm.commitTransaction();

        if (callback != null)
            callback.onSuccess();

    }

    @Override
    public void getAllContacts(OnGetAllContactsCallback callback) {
        Realm realm = MedicineAlertApp.getInstance();
        RealmResults<ContactData> results = realm.where(ContactData.class).findAll();
        if (callback != null)
            callback.onSuccess(results);
    }


    @Override
    public void deleteStudentById(String id, OnDeleteStudentCallback callback) {
        Realm realm = MedicineAlertApp.getInstance();
        realm.beginTransaction();
        Medicine result = realm.where(Medicine.class).equalTo(RealmTable.ID, id).findFirst();
        result.removeFromRealm();
        realm.commitTransaction();

        if (callback != null)
            callback.onSuccess();
    }

    @Override
    public void deleteStudentByPosition(int position, OnDeleteStudentCallback callback) {
        Realm realm = MedicineAlertApp.getInstance();
        realm.beginTransaction();
        RealmQuery<Medicine> query = realm.where(Medicine.class);
        RealmResults<Medicine> results = query.findAll();
        results.remove(position);
        realm.commitTransaction();

        if (callback != null)
            callback.onSuccess();
    }


    @Override
    public void deleteContactByPosition(int position, OnDeleteContactCallback callback) {
        Realm realm = MedicineAlertApp.getInstance();
        realm.beginTransaction();
        RealmQuery<ContactData> query = realm.where(ContactData.class);
        RealmResults<ContactData> results = query.findAll();
        results.remove(position);
        realm.commitTransaction();

        if (callback != null)
            callback.onSuccess();
    }


    @Override
    public void getAllStudents(int historytype, OnGetAllStudentsCallback callback) {
        Realm realm = MedicineAlertApp.getInstance();
        RealmResults<Medicine> results = realm.where(Medicine.class).equalTo(RealmTable.Medicine.historytype, historytype).findAll();
        if (callback != null)
            callback.onSuccess(results);


    }

    @Override
    public void delAllRecords(OnClearAllDataCallback callback) {
        Realm realm = MedicineAlertApp.getInstance();
        realm.beginTransaction();

        realm.where(Medicine.class).findAll().clear();
        realm.where(ContactData.class).findAll().clear();

        realm.commitTransaction();

        realm.close();
        if(callback!=null)
        {
            callback.onSuccess("data removed");
        }
    }


    @Override
    public void getStudentById(String id, OnGetStudentByIdCallback callback) {
        Realm realm = MedicineAlertApp.getInstance();
        Medicine medicine = realm.where(Medicine.class).equalTo(RealmTable.ID, id).findFirst();

        if (callback != null)
            callback.onSuccess(medicine);
    }


    @Override
    public void updateByNotiId(int id, OnUpdateCallback callback) {
        Realm realm = MedicineAlertApp.getInstance();

        Medicine toEdit = realm.where(Medicine.class)
                .equalTo(RealmTable.Medicine.notificationId, id).findFirst();
        realm.beginTransaction();
        toEdit.setHistorytype(0); // move to expiry list
        realm.commitTransaction();
        if (callback != null) {
            callback.onSuccess("item move to past list");
        }

    }


}
