package com.medi.reminder.adapter;

import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.medi.reminder.R;
import com.medi.reminder.history.ActionCallBack;
import com.medi.reminder.model.history.Data;

import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by sanjay on 6/6/17.
 * *
 */

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.DataHolder> {
    private static final String FORMAT = "EEE, dd MMM, yyyy hh:mm a";
    private FragmentActivity mActivity;
    private List<Data> mDataList;
    private int mType = 1; // 1-for upcoming 2- for past
    private ActionCallBack mCallback;

    public HistoryAdapter(FragmentActivity activity, List<Data> dataList, ActionCallBack actionCallBack) {
        mActivity = activity;
        mDataList = dataList;
        mCallback = actionCallBack;

    }

    @Override
    public DataHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        View view = LayoutInflater.from(mActivity).inflate(R.layout.item_medicine_list, parent, false);
        return new DataHolder(view);
    }

    @Override
    public void onBindViewHolder(DataHolder viewHolder, int position) {


    }


    @Override
    public int getItemCount() {
        return mDataList.size();
    }


    class DataHolder extends RecyclerView.ViewHolder {
        //        private TextView mTxtUname;
//        private TextView mTxtOrderId;
//        private TextView mTxtServices;
//        private TextView mTxtTotalTime;
//        private TextView mTxtTime;
//        private TextView mTxtTotalPrice;
//        private CircleImageView mImgProfilePic;
//        private ConstraintLayout mConstraintLayout;
//        private ImageView ImgBtnCancelRequest;
//        private ImageView ImgBtnAcceptRequest;
        private Button mBtnCancelReminder;
        private ImageView mMediImage1;
        private ImageView mMediImage2;


        DataHolder(View itemView) {
            super(itemView);
            mBtnCancelReminder = (Button) itemView.findViewById(R.id.btn_set_reminder);
            mMediImage1 = (ImageView) itemView.findViewById(R.id.image_medi1);
            mMediImage2 = (ImageView) itemView.findViewById(R.id.image_medi2);

//            mImgProfilePic = (CircleImageView) itemView.findViewById(R.id.image_profile_pic);
//            mTxtUname = (TextView) itemView.findViewById(R.id.text_user_name);
//            mTxtOrderId = (TextView) itemView.findViewById(R.id.text_orderid);
//            mTxtServices = (TextView) itemView.findViewById(R.id.text_services);
//            mTxtTotalTime = (TextView) itemView.findViewById(R.id.text_total_time);
//            mTxtTime = (TextView) itemView.findViewById(R.id.text_timing);
//            mTxtTotalPrice = (TextView) itemView.findViewById(R.id.text_total_price);
//
//            mConstraintLayout = (ConstraintLayout) itemView.findViewById(R.id.constraintLayout);
//            mConstraintLayout.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    if (bookingSelectedCallBack != null) {
//                        bookingSelectedCallBack.bookingSelected(getAdapterPosition());
//                    }
//                }
//            });
//
//            ImgBtnAcceptRequest = (ImageView) itemView.findViewById(R.id.button_accept_request);
//            ImgBtnCancelRequest = (ImageView) itemView.findViewById(R.id.button_cancel_request);
//
//            //bind with events
//            ImgBtnAcceptRequest.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    if (mCallback != null) {
//                        mCallback.accept(getAdapterPosition());
//                    }
//                }
//            });
//
//            ImgBtnCancelRequest.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    if (mCallback != null) {
//                        mCallback.reject(getAdapterPosition());
//                    }
//                }
//            });

            mBtnCancelReminder.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mCallback != null) {
                        mCallback.cancelSchedule(getAdapterPosition());
                    }
                }
            });

            mMediImage1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showAlert(getAdapterPosition());
                }
            });

            mMediImage2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showAlert(getAdapterPosition());
                }
            });

        }

        private void showAlert(int postion)
        {
            new SweetAlertDialog(mActivity, SweetAlertDialog.CUSTOM_IMAGE_TYPE)
                    .setTitleText("Sweet!")
                    .setContentText("Here's a custom image.")
                    .setCustomImage(R.mipmap.images)
                    .show();
        }

    }


}
