package com.medi.reminder.history;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.medi.reminder.BaseFragment;
import com.medi.reminder.Constants;
import com.medi.reminder.R;
import com.medi.reminder.adapter.AppAdapter;
import com.medi.reminder.databinding.FragmentUpcomingPastBinding;
import com.medi.reminder.realm.IMedicineContract;
import com.medi.reminder.realm.model.Medicine;
import com.medi.reminder.realm.presenters.IMedicinePresenter;
import com.medi.reminder.realm.presenters.impl.MedicinePresenter;
import com.medi.reminder.receiver.NotificationPublisher;

import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import io.realm.RealmResults;

/**
 * Created by android on 2/6/17.
 * *
 */

public class UpcomingFragment extends BaseFragment implements IMedicineContract {

    public boolean mDataRequestSuccess = false;
    public boolean mNoMoreLoad = true;
    public List<Medicine> mList = new ArrayList<>();
    private FragmentUpcomingPastBinding binding;
    private AppAdapter mAdapter;
    private IMedicinePresenter presenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.bind(inflater.inflate(R.layout.fragment_upcoming_past, container, false));
        presenter = new MedicinePresenter(this);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
    }

    private void init() {
        mAdapter = new AppAdapter(getActivity(), mList, Constants.UPCOMING_ALERT, new ActionCallBack() {
            @Override
            public void cancelSchedule(int position) {
                showWarningAlert(position);
            }
        });
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        dividerItemDecoration.setDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.divider));
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        binding.recyclerView.setLayoutManager(mLayoutManager);
        binding.recyclerView.addItemDecoration(dividerItemDecoration);
        binding.recyclerView.setAdapter(mAdapter);


    }

    private void showWarningAlert(final int position) {
        new SweetAlertDialog(getActivity(), SweetAlertDialog.WARNING_TYPE)
                .setTitleText("Are you sure?")
                .setContentText("Won't be able to recover this Alert!")
                .setConfirmText("Yes,delete it!")
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        sDialog
                                .setTitleText("Deleted!")
                                .setContentText("Your Alert has been cancelled successfully!")
                                .setConfirmText("OK")
                                .setConfirmClickListener(null)
                                .changeAlertType(SweetAlertDialog.SUCCESS_TYPE);

                        //remove deleted item from list and update the list
                        int alarm_request_code = mList.get(position).getNotificationId();
                        presenter.deleteStudentById(mList.get(position).getId());
                        mList.remove(position);
                        mAdapter.notifyItemRemoved(position);
                        /*
                        *************** cancel the alaram if pending *****************
                         */
                        AlarmManager am = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);
                        Intent intent = new Intent(getActivity(), NotificationPublisher.class);
                        PendingIntent sender = PendingIntent.getBroadcast(getActivity(), alarm_request_code, intent, 0);
                        am.cancel(sender);

                    }
                })
                .show();
    }

    @Override
    public void onStart() {
        super.onStart();
        presenter.subscribeCallbacks();
        presenter.getAllStudents(Constants.UPCOMING_ALERT);
    }

    @Override
    public void onStop() {
        super.onStop();
        presenter.unSubscribeCallbacks();
    }

    @Override
    public void showStudents(RealmResults<Medicine> medicines) {
        Log.e("data", "received");
        mList.clear();
        for (int i = 0; i < medicines.size(); i++) {
            mList.add(medicines.get(i));
        }
        //no data found show empty screen
        if (mList.size() == 0) {
            showEmptyScreen();
        }
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void showMessage(String message) {
        super.showMessage(message);
    }

    private void showEmptyScreen() {
        binding.textNodata.setText("No Upcoming Alerts!");
        binding.textNodata.setVisibility(View.VISIBLE);
    }
}
