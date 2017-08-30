package com.medi.reminder.adapter;

import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.medi.reminder.R;
import com.medi.reminder.model.history.ContactData;

import java.util.List;

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
//        Medicine medicine = mMedicineList.get(position);
//        viewHolder.mTxtMediName.setText(medicine.getMedicineName());
//        viewHolder.mTxtExpiryTime.setText(medicine.getExpiryMedicineTime());
//        if (ALERT_TYPE == Constants.UPCOMING_ALERT) {
//            viewHolder.mBtnCancelReminder.setVisibility(View.VISIBLE);
//        } else {
//            viewHolder.mBtnCancelReminder.setVisibility(View.INVISIBLE);
//
//        }
//        String imageurl1 = medicine.getMedicineImageUrl1();
//        String imageurl2 = medicine.getMedicineImageUrl2();
//        //load first medi image
//        if (imageurl1.length() > 0)
//            Utils.getInstance().downloadImage(mActivity, imageurl1, viewHolder.mMediImage1);
//        else
//            viewHolder.mMediImage1.setImageResource(R.mipmap.images);
//        //load second medi image
//        if (imageurl2.length() > 0)
//            Utils.getInstance().downloadImage(mActivity, imageurl2, viewHolder.mMediImage2);
//        else
//            viewHolder.mMediImage2.setImageResource(R.mipmap.images);


    }


    @Override
    public int getItemCount() {
        return 10;//mList.size();
    }


    class ContactHolder extends RecyclerView.ViewHolder {
        private TextView mTxtMediName;
        private TextView mTxtExpiryTime;
        private Button mBtnCancelReminder;
        private ImageView mMediImage1;
        private ImageView mMediImage2;


        ContactHolder(View itemView) {
            super(itemView);
//            mBtnCancelReminder = (Button) itemView.findViewById(R.id.btn_set_reminder);
//            mMediImage1 = (ImageView) itemView.findViewById(R.id.image_medi1);
//            mMediImage2 = (ImageView) itemView.findViewById(R.id.image_medi2);
//            mTxtMediName = (TextView) itemView.findViewById(R.id.text_medi_name);
//            mTxtExpiryTime = (TextView) itemView.findViewById(R.id.text_expiry_time);
//
//
//            mBtnCancelReminder.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    if (mCallback != null) {
//                        mCallback.cancelSchedule(getAdapterPosition());
//                    }
//                }
//            });
//
//            mMediImage1.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    showAlert(getAdapterPosition());
//                }
//            });
//
//            mMediImage2.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    showAlert(getAdapterPosition());
//                }
//            });

        }


    }


}
