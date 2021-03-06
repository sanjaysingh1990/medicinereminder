package com.medi.reminder.realm;

import com.medi.reminder.realm.model.ContactData;
import com.medi.reminder.realm.model.Medicine;

import io.realm.RealmResults;

/**
 * Created by android on 29/8/17.
 */

public interface IMedicineContract {
    void showStudents(RealmResults<Medicine> medicines);
    void showContacts(RealmResults<ContactData> contactsList);
    void showMessage(String message);
}
