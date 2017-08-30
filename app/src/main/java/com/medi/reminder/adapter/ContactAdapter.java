package com.medi.reminder.adapter;

import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.medi.reminder.R;
import com.medi.reminder.app.MedicineAlertApp;
import com.medi.reminder.realm.model.ContactData;
import com.medi.reminder.realm.model.Medicine;
import com.medi.reminder.realm.realm.table.RealmTable;

import java.util.List;

import io.realm.Realm;

/**
 * Created by sanjay on 6/6/17.
 * *
 */

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ContactHolder> {
    private static final String FORMAT = "EEE, dd MMM, yyyy hh:mm a";
    private FragmentActivity mActivity;
    private List<ContactData> mList;


    public ContactAdapter(FragmentActivity activity, List<ContactData> dataList) {
        mActivity = activity;
        this.mList = dataList;
    }

    @Override
    public ContactHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        View view = LayoutInflater.from(mActivity).inflate(R.layout.item_contact, parent, false);
        return new ContactHolder(view);
    }

    @Override
    public void onBindViewHolder(ContactHolder viewHolder, int position) {
        ContactData contactData = mList.get(position);
        viewHolder.mTxtContactName.setText(contactData.getContactName());
        viewHolder.mTxtContactNo.setText(contactData.getPhoneNo());
        //for current sms status
        if (contactData.isSms()) {
            viewHolder.mCheckBoxSms.setChecked(true);
        } else {
            viewHolder.mCheckBoxSms.setChecked(false);

        }

        //for current phone status
        if (contactData.isPhone()) {
            viewHolder.mCheckBoxPhone.setChecked(true);
        } else {
            viewHolder.mCheckBoxPhone.setChecked(false);

        }


    }


    @Override
    public int getItemCount() {
        return mList.size();
    }


    class ContactHolder extends RecyclerView.ViewHolder {
        private TextView mTxtContactName;
        private TextView mTxtContactNo;
        private CheckBox mCheckBoxSms;
        private CheckBox mCheckBoxPhone;


        ContactHolder(View itemView) {
            super(itemView);
            mTxtContactName = (TextView) itemView.findViewById(R.id.text_contact_name);
            mCheckBoxSms = (CheckBox) itemView.findViewById(R.id.checkBox_sms);
            mCheckBoxPhone = (CheckBox) itemView.findViewById(R.id.checkBox_phone);
            mTxtContactNo = (TextView) itemView.findViewById(R.id.text_contact_no);

               mCheckBoxSms.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    CheckBox checkBox = (CheckBox) view;
                    ContactData contactData = mList.get(getAdapterPosition());
                    Realm realm = Realm.getInstance(MedicineAlertApp.getInstance());
                    realm.beginTransaction();
                    if (checkBox.isChecked()) {

                        contactData.setSms(true);
                    } else {
                        contactData.setSms(false);
                    }
                    realm.commitTransaction();
                }
            });

            mCheckBoxPhone.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    CheckBox checkBox = (CheckBox) view;
                    ContactData contactData = mList.get(getAdapterPosition());
                    Realm realm = Realm.getInstance(MedicineAlertApp.getInstance());
                    realm.beginTransaction();

                    if (checkBox.isChecked()) {
                        contactData.setPhone(true);
                    } else {
                        contactData.setPhone(false);
                    }
                    realm.commitTransaction();
                }
            });

        }


    }


}
