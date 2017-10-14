package com.medi.reminder.history;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;

import com.medi.reminder.R;
import com.medi.reminder.adapter.ViewPagerAdapter;
import com.medi.reminder.databinding.ActivityHistoryBinding;
import com.medi.reminder.databinding.ActivityMainBinding;

public class HistoryActivity extends AppCompatActivity {
    private ActivityHistoryBinding binding;
    private ViewPagerAdapter mAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_history);
        binding.setClickEvent(new ClickHandler());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        init();

    }

   private void init()
    {
        setTitle("History");
        mAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        mAdapter.addFrag(new UpcomingFragment(), "UPCOMING");
        mAdapter.addFrag(new PastFragment(), "PAST");
        binding.viewpager.setAdapter(mAdapter);
        binding.tabLayout.setupWithViewPager(binding.viewpager);



    }

    public class ClickHandler {
        public void calendar(View view) {

        }

        public void resetFilter(View view) {}

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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.left_in, R.anim.right_out);
    }
}
