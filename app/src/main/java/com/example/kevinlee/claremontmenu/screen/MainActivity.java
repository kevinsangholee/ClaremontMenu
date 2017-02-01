package com.example.kevinlee.claremontmenu.screen;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.kevinlee.claremontmenu.R;
import com.example.kevinlee.claremontmenu.adapters.SchoolAdapter;
import com.example.kevinlee.claremontmenu.data.network.DBConfig;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class MainActivity extends AppCompatActivity {

    private static final String LOG_TAG = MainActivity.class.getSimpleName();
    private SchoolAdapter schoolAdapter;
    private ViewPager viewPager;

    /**
     * THINGS TO DO
     *
     * integrate API and the internet into the app
     * create an online database filled with the food, and reviews
     * upgrade the layout
     *
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);

        viewPager = (ViewPager) findViewById(R.id.viewpager);

        schoolAdapter = new SchoolAdapter(getSupportFragmentManager(), DBConfig.BREAKFAST);

        viewPager.setAdapter(schoolAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);

        tabLayout.setupWithViewPager(viewPager);

        ArrayAdapter<CharSequence> adapter;
        if(DBConfig.isWeekend()) {
            adapter = ArrayAdapter.createFromResource(this,
                    R.array.meals_weekend_array, R.layout.spinner_drop_title);
        } else {
            adapter = ArrayAdapter.createFromResource(this,
                    R.array.meals_weekday_array, R.layout.spinner_drop_title);
        }
        adapter.setDropDownViewResource(R.layout.spinner_drop_list);
        Spinner mealSpinner = (Spinner) findViewById(R.id.meal_spinner);
        mealSpinner.setAdapter(adapter);

        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("America/Los_Angeles"));
        Date currentTime = cal.getTime();
        DateFormat date = new SimpleDateFormat("HH");
        date.setTimeZone(TimeZone.getTimeZone("America/Los_Angeles"));
        String time = date.format(currentTime);
        int hour = Integer.parseInt(time);
        if(DBConfig.isWeekend()) {
            if (hour >= 14 && hour < 24) {
                mealSpinner.setSelection(1);
            } else {
                mealSpinner.setSelection(0);
            }
        } else {
            if (hour >= 15 && hour < 24) {
                mealSpinner.setSelection(2);
            } else if (hour >= 10 && hour < 15) {
                mealSpinner.setSelection(1);
            } else {
                mealSpinner.setSelection(0);
            }
        }
        mealSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selected = parent.getItemAtPosition(position).toString();
                if(selected.equals("Breakfast")) {
                    viewPager.removeAllViews();
                    viewPager.setAdapter(null);
                    Log.i(LOG_TAG, "Views removed and set to null");
                    viewPager.setAdapter(new SchoolAdapter(getSupportFragmentManager(), DBConfig.BREAKFAST));
                }
                if(selected.equals("Lunch")) {
                    viewPager.removeAllViews();
                    viewPager.setAdapter(null);
                    Log.i(LOG_TAG, "Views removed and set to null");
                    viewPager.setAdapter(new SchoolAdapter(getSupportFragmentManager(), DBConfig.LUNCH));
                }
                if(selected.equals("Dinner")) {
                    viewPager.removeAllViews();
                    viewPager.setAdapter(null);
                    Log.i(LOG_TAG, "Views removed and set to null");
                    viewPager.setAdapter(new SchoolAdapter(getSupportFragmentManager(), DBConfig.DINNER));
                }
                if(selected.equals("Brunch")) {
                    viewPager.removeAllViews();
                    viewPager.setAdapter(null);
                    Log.i(LOG_TAG, "Views removed and set to null");
                    viewPager.setAdapter(new SchoolAdapter(getSupportFragmentManager(), DBConfig.BRUNCH));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
}
