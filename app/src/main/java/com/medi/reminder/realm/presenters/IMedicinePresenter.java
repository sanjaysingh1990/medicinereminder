package com.medi.reminder.realm.presenters;


import com.medi.reminder.realm.model.Medicine;
import com.medi.reminder.realm.realm.repository.IMedicineRepository;

/**
 * Created by roma on 03.11.15.
 */
public interface IMedicinePresenter extends IBasePresenter{

    void addStudent(Medicine medicine);


    void deleteStudentByPosition(int position);

    void deleteStudentById(String studentId);

    void getAllStudents(int historytype);


    void getStudentById(String id);
    void updateByNotiId(int id);


}
