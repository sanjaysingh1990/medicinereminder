package com.medi.reminder.realm.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

/**
 * Created by roma on 14.10.15.
 */
public class Medicine extends RealmObject {

    @PrimaryKey
    private String id;
    @Required
    private String medicineName = "";
    @Required
    private String takeMedicineTime;
    @Required
    private String expiryMedicineTime;
    @Required
    private String medicineImageUrl1 = "";
    @Required
    private String medicineImageUrl2 = "";
    private int notificationId = 100;
    private int historytype = 1; //1 for upcoming 0 for past

    public int getHistorytype() {
        return historytype;
    }

    public void setHistorytype(int historytype) {
        this.historytype = historytype;
    }

    public String getExpiryMedicineTime() {
        return expiryMedicineTime;
    }

    public void setExpiryMedicineTime(String expiryMedicineTime) {
        this.expiryMedicineTime = expiryMedicineTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMedicineName() {
        return medicineName;
    }

    public void setMedicineName(String medicineName) {
        this.medicineName = medicineName;
    }

    public String getTakeMedicineTime() {
        return takeMedicineTime;
    }

    public void setTakeMedicineTime(String takeMedicineTime) {
        this.takeMedicineTime = takeMedicineTime;
    }

    public String getMedicineImageUrl1() {
        return medicineImageUrl1;
    }

    public void setMedicineImageUrl1(String medicineImageUrl1) {
        this.medicineImageUrl1 = medicineImageUrl1;
    }

    public String getMedicineImageUrl2() {
        return medicineImageUrl2;
    }

    public void setMedicineImageUrl2(String medicineImageUrl2) {
        this.medicineImageUrl2 = medicineImageUrl2;
    }

    public int getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(int notificationId) {
        this.notificationId = notificationId;
    }


}
