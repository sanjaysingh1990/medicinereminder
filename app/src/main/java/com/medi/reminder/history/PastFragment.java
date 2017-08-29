package com.medi.reminder.history;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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
import com.medi.reminder.adapter.HistoryAdapter;
import com.medi.reminder.databinding.FragmentUpcomingPastBinding;
import com.medi.reminder.model.history.Data;
import com.medi.reminder.realm.IMedicineContract;
import com.medi.reminder.realm.model.Medicine;
import com.medi.reminder.realm.presenters.IMedicinePresenter;
import com.medi.reminder.realm.presenters.impl.MedicinePresenter;

import java.util.ArrayList;
import java.util.List;

import io.realm.RealmResults;

/**
 * Created by android on 2/6/17.
 * *
 */

public class PastFragment extends BaseFragment implements IMedicineContract {

     public List<Medicine> mList = new ArrayList<>();
    private FragmentUpcomingPastBinding binding;
    private HistoryAdapter mAdapter;
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


        mAdapter = new HistoryAdapter(getActivity(), mList,Constants.HISTORY_ALERT, new ActionCallBack() {
            @Override
            public void cancelSchedule(int position) {

            }
        });
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        dividerItemDecoration.setDrawable(ContextCompat.getDrawable(getActivity(),R.drawable.divider));
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        binding.recyclerView.setLayoutManager(mLayoutManager);
        binding.recyclerView.addItemDecoration(dividerItemDecoration);
        binding.recyclerView.setAdapter(mAdapter);


    }
    @Override
    public void onStart() {
        super.onStart();
        presenter.subscribeCallbacks();
        presenter.getAllStudents(Constants.HISTORY_ALERT);
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

        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void showMessage(String message) {
        super.showMessage(message);
    }

  }