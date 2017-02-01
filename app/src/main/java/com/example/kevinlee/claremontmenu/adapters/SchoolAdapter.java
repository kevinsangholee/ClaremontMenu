package com.example.kevinlee.claremontmenu.adapters;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;

import com.example.kevinlee.claremontmenu.screen.SchoolFragment;
import com.example.kevinlee.claremontmenu.data.network.DBConfig;

/**
 * Created by kevinlee on 12/23/16.
 */

public class SchoolAdapter extends FragmentStatePagerAdapter {

    public static final String DINING_HALL_KEY = "dining_hall";
    public static final String MEAL_KEY = "meal";
    private String tabTitles[] = {"Frank", "Frary", "Oldenborg", "CMC", "Scripps", "Pitzer", "Mudd"};
    int meal;

    public SchoolAdapter(FragmentManager fm, int meal) {
        super(fm);
        this.meal = meal;
        Log.i("Value of selected", String.valueOf(meal));
    }

    @Override
    public Fragment getItem(int position) {
        Bundle bundle = new Bundle();
        SchoolFragment f = new SchoolFragment();
        switch(position) {
            case 0:
                bundle.putInt(DINING_HALL_KEY, DBConfig.FRANK);
                bundle.putInt(MEAL_KEY, meal);
                f.setArguments(bundle);
                return f;
            case 1:
                bundle.putInt(DINING_HALL_KEY, DBConfig.FRARY);
                bundle.putInt(MEAL_KEY, meal);
                f.setArguments(bundle);
                return f;
            case 2:
                bundle.putInt(DINING_HALL_KEY, DBConfig.OLDENBORG);
                bundle.putInt(MEAL_KEY, meal);
                f.setArguments(bundle);
                return f;
            case 3:
                bundle.putInt(DINING_HALL_KEY, DBConfig.CMC);
                bundle.putInt(MEAL_KEY, meal);
                f.setArguments(bundle);
                return f;
            case 4:
                bundle.putInt(DINING_HALL_KEY, DBConfig.SCRIPPS);
                bundle.putInt(MEAL_KEY, meal);
                f.setArguments(bundle);
                return f;
            case 5:
                bundle.putInt(DINING_HALL_KEY, DBConfig.PITZER);
                bundle.putInt(MEAL_KEY, meal);
                f.setArguments(bundle);
                return f;
            case 6:
                bundle.putInt(DINING_HALL_KEY, DBConfig.MUDD);
                bundle.putInt(MEAL_KEY, meal);
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



}
