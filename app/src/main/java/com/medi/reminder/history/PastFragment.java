package com.medi.reminder.history;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.medi.reminder.R;
import com.medi.reminder.adapter.HistoryAdapter;
import com.medi.reminder.databinding.FragmentUpcomingPastBinding;
import com.medi.reminder.model.history.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by android on 2/6/17.
 * *
 */

public class PastFragment extends Fragment {

     public List<Data> mList = new ArrayList<>();
    private FragmentUpcomingPastBinding binding;
    private HistoryAdapter mAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.bind(inflater.inflate(R.layout.fragment_upcoming_past, container, false));
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
    }

    private void init() {

        for(int i=0;i<20;i++)
        {
            Data data=new Data();
            mList.add(data);
        }
        mAdapter = new HistoryAdapter(getActivity(), mList, new ActionCallBack() {
            @Override
            public void cancelSchedule(int position) {

            }
        });
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        dividerItemDecoration.setDrawable(getActivity().getDrawable(R.drawable.divider));
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        binding.recyclerView.setLayoutManager(mLayoutManager);
        binding.recyclerView.addItemDecoration(dividerItemDecoration);
        binding.recyclerView.setAdapter(mAdapter);


    }

  }
