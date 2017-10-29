package com.medi.reminder.realm.presenters.impl;



import android.util.Log;

import com.medi.reminder.realm.IMedicineContract;
import com.medi.reminder.realm.model.ContactData;
import com.medi.reminder.realm.model.Medicine;
import com.medi.reminder.realm.presenters.IMedicinePresenter;
import com.medi.reminder.realm.realm.repository.IMedicineRepository;
import com.medi.reminder.realm.realm.repository.impl.MedicineRepository;

import io.realm.RealmList;
import io.realm.RealmResults;

/**
 * Created by roma on 03.11.15.
 */
public class MedicinePresenter implements IMedicinePresenter {

    private IMedicineContract view;

    private IMedicineRepository.OnDeleteStudentCallback onDeleteStudentCallback;
    private IMedicineRepository.OnSaveStudentCallback onSaveStudentCallback;
    private IMedicineRepository.OnGetAllStudentsCallback onGetAllStudentsCallback;
    private IMedicineRepository.OnGetStudentByIdCallback onGetStudentByIdCallback;
    private IMedicineRepository.OnGetStudentsCallback onGetStudentsCallback;
    private IMedicineRepository.OnUpdateCallback onUpdateCallback;
    private IMedicineRepository.OnSaveContactCallback onSaveContactCallback;
    private IMedicineRepository.OnGetAllContactsCallback onGetAllContactsCallback;
    private IMedicineRepository.OnDeleteContactCallback onDeleteContactCallback;
    private IMedicineRepository.OnClearAllDataCallback onDeleteAllDataCallback;




    private IMedicineRepository studentRepository;

    public MedicinePresenter(IMedicineContract view) {
        this.view = view;
        studentRepository = new MedicineRepository();

    }

    @Override
    public void addStudent(Medicine medicine) {
        studentRepository.addStudent(medicine, onSaveStudentCallback);
    }

    @Override
    public void addContact(ContactData contactData) {
        studentRepository.addContact(contactData, onSaveContactCallback);

    }


    @Override
    public void deleteStudentByPosition(int position) {
        studentRepository.deleteStudentByPosition(position, onDeleteStudentCallback);
    }

    @Override
    public void deleteContactByPosition(int position) {
       studentRepository.deleteContactByPosition(position,onDeleteContactCallback);

    }

    @Override
    public void deleteStudentById(String studentId) {
        studentRepository.deleteStudentById(studentId, onDeleteStudentCallback);
    }

    @Override
    public void getAllStudents(int historytype) {
        studentRepository.getAllStudents(historytype,onGetAllStudentsCallback);
    }

    @Override
    public void getAllContacts() {
        studentRepository.getAllContacts(onGetAllContactsCallback);

    }

    @Override
    public void delAllRecords() {
        studentRepository.delAllRecords(onDeleteAllDataCallback);
    }


    @Override
    public void getStudentById(String id) {
        studentRepository.getStudentById(id, onGetStudentByIdCallback);
    }

    @Override
    public void updateByNotiId(int id) {
        studentRepository.updateByNotiId(id,onUpdateCallback);
    }


    @Override
    public void subscribeCallbacks() {
        onSaveStudentCallback = new IMedicineRepository.OnSaveStudentCallback() {
            @Override
            public void onSuccess() {
                view.showMessage("Added");
            }

            @Override
            public void onError(String message) {
                view.showMessage(message);
            }
        };

        onSaveContactCallback = new IMedicineRepository.OnSaveContactCallback() {
            @Override
            public void onSuccess() {
                view.showMessage("ContactSaved");
            }

            @Override
            public void onError(String message) {
                view.showMessage(message);
            }
        };




        onDeleteStudentCallback = new IMedicineRepository.OnDeleteStudentCallback() {
            @Override
            public void onSuccess() {
                view.showMessage("Deleted");
            }

            @Override
            public void onError(String message) {
                view.showMessage(message);
            }
        };


        onDeleteContactCallback = new IMedicineRepository.OnDeleteContactCallback() {
            @Override
            public void onSuccess() {
                view.showMessage("Contact Removed!");
            }

            @Override
            public void onError(String message) {
                view.showMessage(message);
            }
        };

        onGetAllStudentsCallback = new IMedicineRepository.OnGetAllStudentsCallback() {
            @Override
            public void onSuccess(RealmResults<Medicine> medicines) {
            view.showStudents(medicines);
            }

            @Override
            public void onError(String message) {
                view.showMessage(message);
            }
        };

        onDeleteAllDataCallback=new IMedicineRepository.OnClearAllDataCallback() {
            @Override
            public void onSuccess(String message) {
                view.showMessage(message);
            }

            @Override
            public void onError(String message) {
                view.showMessage(message);

            }
        };
        onGetAllContactsCallback = new IMedicineRepository.OnGetAllContactsCallback() {
            @Override
            public void onSuccess(RealmResults<ContactData> contactList) {
                view.showContacts(contactList);
            }

            @Override
            public void onError(String message) {
                view.showMessage(message);
            }
        };
        onGetStudentByIdCallback = new IMedicineRepository.OnGetStudentByIdCallback() {
            @Override
            public void onSuccess(Medicine medicine) {

            }

            @Override
            public void onError(String message) {

            }
        };
        onGetStudentsCallback = new IMedicineRepository.OnGetStudentsCallback() {
            @Override
            public void onSuccess(RealmList<Medicine> medicines) {
                //view.showStudents(medicines);
            }

            @Override
            public void onError(String message) {
                view.showMessage(message);
            }
        };

        onUpdateCallback=new IMedicineRepository.OnUpdateCallback() {
            @Override
            public void onSuccess(String message) {

                view.showMessage(message);
            }

            @Override
            public void onError(String message) {

            }
        };

    }

    @Override
    public void unSubscribeCallbacks() {
        onDeleteStudentCallback = null;
        onSaveStudentCallback = null;
        onGetAllStudentsCallback = null;
        onGetStudentByIdCallback = null;
    }
}
