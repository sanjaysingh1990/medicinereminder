package com.medi.reminder.realm.realm.repository;



import com.medi.reminder.realm.model.Medicine;

import io.realm.RealmList;
import io.realm.RealmResults;

/**
 * Created by roma on 16.10.15.
 */
public interface IMedicineRepository {

    interface OnSaveStudentCallback {
        void onSuccess();
        void onError(String message);
    }

    interface OnDeleteStudentCallback {
        void onSuccess();
        void onError(String message);
    }

    interface OnGetStudentByIdCallback {
        void onSuccess(Medicine medicine);
        void onError(String message);
    }

    interface OnGetAllStudentsCallback {
        void onSuccess(RealmResults<Medicine> medicines);
        void onError(String message);
    }
    interface OnUpdateCallback {
        void onSuccess(String message);
        void onError(String message);
    }

    interface OnGetStudentsCallback{
        void onSuccess(RealmList<Medicine> medicines);
        void onError(String message);
    }

    void addStudent(Medicine medicine, OnSaveStudentCallback callback);


    void deleteStudentById(String id, OnDeleteStudentCallback callback);

    void deleteStudentByPosition(int position, OnDeleteStudentCallback callback);

    void getAllStudents(int historytype,OnGetAllStudentsCallback callback);


    void getStudentById(String id, OnGetStudentByIdCallback callback);
    void updateByNotiId(int id, OnUpdateCallback callback);

}