package com.example.kevinlee.claremontmenu.adapters;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;

import com.example.kevinlee.claremontmenu.R;
import com.example.kevinlee.claremontmenu.screen.SchoolFragment;

import java.text.SimpleDateFormat;
import java.util.Date;

import static com.example.kevinlee.claremontmenu.data.network.DBConfig.BREAKFAST;
import static com.example.kevinlee.claremontmenu.data.network.DBConfig.BRUNCH;
import static com.example.kevinlee.claremontmenu.data.network.DBConfig.CMC;
import static com.example.kevinlee.claremontmenu.data.network.DBConfig.DINNER;
import static com.example.kevinlee.claremontmenu.data.network.DBConfig.FRANK;
import static com.example.kevinlee.claremontmenu.data.network.DBConfig.FRARY;
import static com.example.kevinlee.claremontmenu.data.network.DBConfig.HOURS_KEY;
import static com.example.kevinlee.claremontmenu.data.network.DBConfig.LUNCH;
import static com.example.kevinlee.claremontmenu.data.network.DBConfig.MUDD;
import static com.example.kevinlee.claremontmenu.data.network.DBConfig.OLDENBORG;
import static com.example.kevinlee.claremontmenu.data.network.DBConfig.PITZER;
import static com.example.kevinlee.claremontmenu.data.network.DBConfig.SCRIPPS;
import static com.example.kevinlee.claremontmenu.data.network.DBConfig.isWeekend;

/**
 * Created by kevinlee on 12/23/16.
 */

public class SchoolAdapter extends FragmentStatePagerAdapter {

    public static final String DINING_HALL_KEY = "dining_hall";
    public static final String MEAL_KEY = "meal";
    private String tabTitles[] = {"Frank", "Frary", "Oldenborg", "CMC", "Scripps", "Pitzer", "Mudd"};
    int meal;
    Context context;

    public SchoolAdapter(FragmentManager fm, int meal, Context context) {
        super(fm);
        this.meal = meal;
        this.context = context;
        Log.i("Value of selected", String.valueOf(meal));
    }

    @Override
    public Fragment getItem(int position) {
        Bundle bundle = new Bundle();
        SchoolFragment f = new SchoolFragment();
        switch(position) {
            case 0:
                bundle.putInt(DINING_HALL_KEY, FRANK);
                bundle.putInt(MEAL_KEY, meal);
                bundle.putString(HOURS_KEY, getHours(FRANK, meal));
                f.setArguments(bundle);
                return f;
            case 1:
                bundle.putInt(DINING_HALL_KEY, FRARY);
                bundle.putInt(MEAL_KEY, meal);
                bundle.putString(HOURS_KEY, getHours(FRARY, meal));
                f.setArguments(bundle);
                return f;
            case 2:
                bundle.putInt(DINING_HALL_KEY, OLDENBORG);
                bundle.putInt(MEAL_KEY, meal);
                bundle.putString(HOURS_KEY, getHours(OLDENBORG, meal));
                f.setArguments(bundle);
                return f;
            case 3:
                bundle.putInt(DINING_HALL_KEY, CMC);
                bundle.putInt(MEAL_KEY, meal);
                bundle.putString(HOURS_KEY, getHours(CMC, meal));
                f.setArguments(bundle);
                return f;
            case 4:
                bundle.putInt(DINING_HALL_KEY, SCRIPPS);
                bundle.putInt(MEAL_KEY, meal);
                bundle.putString(HOURS_KEY, getHours(SCRIPPS, meal));
                f.setArguments(bundle);
                return f;
            case 5:
                bundle.putInt(DINING_HALL_KEY, PITZER);
                bundle.putInt(MEAL_KEY, meal);
                bundle.putString(HOURS_KEY, getHours(PITZER, meal));
                f.setArguments(bundle);
                return f;
            case 6:
                bundle.putInt(DINING_HALL_KEY, MUDD);
                bundle.putInt(MEAL_KEY, meal);
                bundle.putString(HOURS_KEY, getHours(MUDD, meal));
                f.setArguments(bundle);
                return f;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 7;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles[position];
    }

    public String getHours(int school, int meal) {
        SimpleDateFormat sdf = new SimpleDateFormat("EEEE");
        Date d = new Date();
        String dayOfWeek = sdf.format(d);
        switch(school) {
            case FRANK:
                if(dayOfWeek.equals("Friday") || dayOfWeek.equals("Saturday")) {
                    return context.getString(R.string.closed);
                } else {
                    switch (meal) {
                        case BREAKFAST:
                            return context.getString(R.string.frank_breakfast);
                        case BRUNCH:
                            return context.getString(R.string.frank_brunch);
                        case LUNCH:
                            return context.getString(R.string.frank_lunch);
                        case DINNER:
                            if (dayOfWeek.equals("Sunday")) {
                                return context.getString(R.string.frank_dinner_sun);
                            } else {
                                return context.getString(R.string.frank_dinner);
                            }
                    }
                }
            case FRARY:
                switch(meal) {
                    case BREAKFAST:
                        return context.getString(R.string.frary_breakfast);
                    case BRUNCH:
                        return context.getString(R.string.frary_brunch);
                    case LUNCH:
                        return context.getString(R.string.frary_lunch);
                    case DINNER:
                        if(isWeekend()) {
                            return context.getString(R.string.frary_dinner_weekend);
                        } else {
                            return context.getString(R.string.frary_dinner);
                        }
                }
            case OLDENBORG:
                switch(meal) {
                    case BREAKFAST:
                        return context.getString(R.string.closed);
                    case BRUNCH:
                        return context.getString(R.string.closed);
                    case LUNCH:
                        return context.getString(R.string.oldenborg_lunch);
                    case DINNER:
                            return context.getString(R.string.closed);
                }
            case CMC:
                switch(meal) {
                    case BREAKFAST:
                        return context.getString(R.string.cmc_breakfast);
                    case BRUNCH:
                        return context.getString(R.string.cmc_brunch);
                    case LUNCH:
                        return context.getString(R.string.cmc_lunch);
                    case DINNER:
                        if(isWeekend()) {
                            return context.getString(R.string.cmc_dinner_weekend);
                        } else {
                            return context.getString(R.string.cmc_dinner);
                        }
                }
            case SCRIPPS:
                switch(meal) {
                    case BREAKFAST:
                        return context.getString(R.string.scripps_breakfast);
                    case BRUNCH:
                        return context.getString(R.string.scripps_brunch);
                    case LUNCH:
                        return context.getString(R.string.scripps_lunch);
                    case DINNER:
                        if(isWeekend()) {
                            return context.getString(R.string.scripps_dinner_weekend);
                        } else {
                            return context.getString(R.string.scripps_dinner);
                        }
                }
            case PITZER:
                switch(meal) {
                    case BREAKFAST:
                        return context.getString(R.string.pitzer_breakfast);
                    case BRUNCH:
                        return context.getString(R.string.pitzer_brunch);
                    case LUNCH:
                        if(dayOfWeek.equals("Thursday")) {
                            return context.getString(R.string.pitzer_lunch_thurs);
                        } else {
                            return context.getString(R.string.pitzer_lunch);
                        }
                    case DINNER:
                        if(isWeekend()) {
                            return context.getString(R.string.pitzer_dinner_weekend);
                        } else {
                            return context.getString(R.string.pitzer_dinner);
                        }
                }
            case MUDD:
                switch(meal) {
                    case BREAKFAST:
                        return context.getString(R.string.mudd_breakfast);
                    case BRUNCH:
                        return context.getString(R.string.mudd_brunch);
                    case LUNCH:
                        return context.getString(R.string.mudd_lunch);
                    case DINNER:
                        return context.getString(R.string.mudd_dinner);
                }
            default:
                return "Error processing hours";
        }
    }

}
