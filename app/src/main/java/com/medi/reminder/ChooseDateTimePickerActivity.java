package com.medi.reminder;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TimePicker;

import com.medi.reminder.databinding.ActivityChooseDateTimePickerBinding;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class ChooseDateTimePickerActivity extends AppCompatActivity implements Constants {

    ActivityChooseDateTimePickerBinding binding;
    private int mHour;
    private int mMinute;
    private int mYear;
    private int mMonth;
    private int mDay;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_choose_date_time_picker);
        binding.setClickEvent(new ClickHandler());
        initListener();
    }

    private void initListener() {

        binding.timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                mHour = hourOfDay;
                mMinute = minute;
            }
        });
    }

    public class ClickHandler {
        public void done(View view) {
            Calendar calendar = Calendar.getInstance();
            calendar.set(binding.datePicker.getYear(), binding.datePicker.getMonth(), binding.datePicker.getDayOfMonth(), mHour, mMinute, 0);
            Intent data = new Intent();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss a");
            String timing = simpleDateFormat.format(calendar.getTime());
            long futureseconds=calendar.getTimeInMillis()-System.currentTimeMillis();
            data.putExtra(DATETIME, timing);
            data.putExtra(DELAYTIME,futureseconds);
            if (getIntent().getExtras().getInt(TYPE) == CHOOSESTARTDATETIME) {
                setResult(CHOOSESTARTDATETIME, data);
            } else {
                setResult(CHOOSEEXPIRYDATETIME, data);
            }
            finish();
        }
    }

}
