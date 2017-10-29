package com.medi.reminder.realm.presenters;


import com.medi.reminder.realm.model.ContactData;
import com.medi.reminder.realm.model.Medicine;
import com.medi.reminder.realm.realm.repository.IMedicineRepository;

/**
 * Created by roma on 03.11.15.
 */
public interface IMedicinePresenter extends IBasePresenter{

    void addStudent(Medicine medicine);
    void addContact(ContactData contactData);


    void deleteStudentByPosition(int position);
    void deleteContactByPosition(int position);

    void deleteStudentById(String studentId);

    void getAllStudents(int historytype);
    void getAllContacts();
    void delAllRecords();


    void getStudentById(String id);
    void updateByNotiId(int id);


}
