package com.medi.reminder.adapter;

import android.app.Activity;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.medi.reminder.R;
import com.medi.reminder.activity.AddAlertContactsActivity;
import com.medi.reminder.app.MedicineAlertApp;
import com.medi.reminder.login.MyAppUser;
import com.medi.reminder.realm.model.ContactData;
import com.medi.reminder.realm.model.Medicine;
import com.medi.reminder.realm.realm.table.RealmTable;

import java.util.List;

import io.realm.Realm;

import static com.medi.reminder.R.id.button_add_contact;

/**
 * Created by sanjay on 6/6/17.
 * *
 */

public class ContactAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final String FORMAT = "EEE, dd MMM, yyyy hh:mm a";
    private Activity mActivity;
    private List<Object> mList;
    private static final int ADDED_DATA = 1;
    private static final int NOT_ADDED_DATA = 2;


    public ContactAdapter(Activity activity, List<Object> dataList) {
        mActivity = activity;
        this.mList = dataList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == ADDED_DATA) {
            View view = LayoutInflater.from(mActivity).inflate(R.layout.item_contact, parent, false);
            return new ContactHolder(view);

        } else {
            View view = LayoutInflater.from(mActivity).inflate(R.layout.item_contact_not_added, parent, false);
            return new ContactNotAddedHolder(view);

        }

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        if (viewHolder.getItemViewType() == ADDED_DATA) {
            ContactData contactData = (ContactData) mList.get(position);
            ContactHolder holder = (ContactHolder) viewHolder;
            holder.mTxtContactName.setText(contactData.getContactName());
            holder.mTxtContactNo.setText(contactData.getPhoneNo());
            //for current sms status
            if (contactData.isSms()) {
                holder.mCheckBoxSms.setChecked(true);
            } else {
                holder.mCheckBoxSms.setChecked(false);

            }

            //for current phone status
            if (contactData.isPhone()) {
                holder.mCheckBoxPhone.setChecked(true);
            } else {
                holder.mCheckBoxPhone.setChecked(false);

            }
        } else {
            MyAppUser myAppUser = (MyAppUser) mList.get(position);
            ContactNotAddedHolder contactNotAddedHolder = (ContactNotAddedHolder) viewHolder;
            contactNotAddedHolder.mTxtContactName.setText(myAppUser.getUserName());
            contactNotAddedHolder.mTxtContactNo.setText(myAppUser.getUserPhone());
        }


    }


    @Override
    public int getItemCount() {
        return mList.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (mList.get(position) instanceof ContactData) {
            return ADDED_DATA;
        } else {
            return NOT_ADDED_DATA;
        }
    }

    class ContactHolder extends RecyclerView.ViewHolder {
        private TextView mTxtContactName;
        private TextView mTxtContactNo;
        private CheckBox mCheckBoxSms;
        private CheckBox mCheckBoxPhone;
        private Button mButtonRemove;

        ContactHolder(View itemView) {
            super(itemView);
            mTxtContactName = (TextView) itemView.findViewById(R.id.text_contact_name);
            mCheckBoxSms = (CheckBox) itemView.findViewById(R.id.checkBox_sms);
            mCheckBoxPhone = (CheckBox) itemView.findViewById(R.id.checkBox_phone);
            mTxtContactNo = (TextView) itemView.findViewById(R.id.text_contact_no);
            mButtonRemove=itemView.findViewById(R.id.button_remove_contact);

            mCheckBoxSms.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    CheckBox checkBox = (CheckBox) view;
                    ContactData contactData = (ContactData) mList.get(getAdapterPosition());
                    Realm realm = MedicineAlertApp.getInstance();
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
                    ContactData contactData = (ContactData) mList.get(getAdapterPosition());
                    Realm realm = MedicineAlertApp.getInstance();
                    realm.beginTransaction();

                    if (checkBox.isChecked()) {
                        contactData.setPhone(true);
                    } else {
                        contactData.setPhone(false);
                    }
                    realm.commitTransaction();
                }
            });

        mButtonRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddAlertContactsActivity addAlertContactsActivity= (AddAlertContactsActivity) mActivity;
                addAlertContactsActivity.removeContact(getAdapterPosition());
            }
        });
        }


    }

    class ContactNotAddedHolder extends RecyclerView.ViewHolder {
        private TextView mTxtContactName;
        private TextView mTxtContactNo;
        private Button mBtnAddContact;

        ContactNotAddedHolder(View itemView) {
            super(itemView);
            mTxtContactName = (TextView) itemView.findViewById(R.id.text_contact_name);
            mTxtContactNo = (TextView) itemView.findViewById(R.id.text_contact_no);
            mBtnAddContact = itemView.findViewById(button_add_contact);

            mBtnAddContact.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    MyAppUser myAppUser= (MyAppUser) mList.get(getAdapterPosition());
                    AddAlertContactsActivity addAlert= (AddAlertContactsActivity) mActivity;
                    addAlert.addContact(myAppUser,getAdapterPosition());
                }
            });
        }


    }

}
