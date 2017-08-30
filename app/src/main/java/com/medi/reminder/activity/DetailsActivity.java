package com.medi.reminder.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.medi.reminder.Constants;
import com.medi.reminder.R;
import com.medi.reminder.databinding.ActivityMedicineDetailsBinding;
import com.medi.reminder.utils.Utils;

public class DetailsActivity extends AppCompatActivity {
    ActivityMedicineDetailsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_medicine_details);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        if (getIntent() != null) {
            String medicineName;
            String startDate;
            String expiryDate;
            String imageUrl1;
            String imageUrl2;


            Bundle bundle = getIntent().getBundleExtra(Constants.DATA);
            if (bundle.containsKey(Constants.MEDICINE_NAME)) {
                medicineName = bundle.getString(Constants.MEDICINE_NAME);
            } else {
                medicineName = "Medicine Name";
            }
            if (bundle.containsKey(Constants.MEDICINE_EXPIRY_TIME)) {
                expiryDate = bundle.getString(Constants.MEDICINE_EXPIRY_TIME);
            } else {
                expiryDate = "expiry date";
            }
            if (bundle.containsKey(Constants.MEDICINE_START_TIME)) {
                startDate = bundle.getString(Constants.MEDICINE_START_TIME);
            } else {
                startDate = "start date";
            }
            if (bundle.containsKey(Constants.IMAGE_URL1)) {
                imageUrl1 = bundle.getString(Constants.IMAGE_URL1);
            } else {
                imageUrl1 = null;
            }
            if (bundle.containsKey(Constants.IMAGE_URL2)) {
                imageUrl2 = bundle.getString(Constants.IMAGE_URL2);
            } else {
                imageUrl2 = null;
            }

            binding.textMediName.setText(medicineName);
            expiryDate = "Expiry At: " + expiryDate;
            binding.textExpiryTime.setText(expiryDate);
            startDate = "Start At: " + startDate;
            binding.textStartTime.setText(startDate);

            //to load first medi image
            if (imageUrl1 != null && imageUrl1.length() > 0) {
                Utils.getInstance().downloadImage(this, imageUrl1, binding.imageMedi1);
            }

            //to load second medi image
            if (imageUrl2 != null && imageUrl2.length() > 0) {
                Utils.getInstance().downloadImage(this, imageUrl2, binding.imageMedi2);
            }

            //set image views height at run time

            int width = Utils.getInstance().getWidth(this);
            binding.imageMedi1.getLayoutParams().height = width / 2;
            binding.imageMedi2.getLayoutParams().height = width / 2;


        }


    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // app icon action bar is clicked; go to parent activity
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}