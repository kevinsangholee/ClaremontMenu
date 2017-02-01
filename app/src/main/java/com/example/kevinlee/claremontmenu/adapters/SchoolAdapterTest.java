package com.example.kevinlee.claremontmenu.adapters;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

import com.example.kevinlee.claremontmenu.screen.SchoolFragment;
import com.example.kevinlee.claremontmenu.data.network.DBConfig;

/**
 * Created by kevinlee on 12/23/16.
 */

public class SchoolAdapterTest extends FragmentPagerAdapter {

    public static final String DINING_HALL_KEY = "dining_hall";
    private String tabTitles[] = {"Frank", "Frary", "Oldenborg", "CMC", "Scripps", "Pitzer", "Mudd"};
    int school;

    public SchoolAdapterTest(FragmentManager fm, int school) {
        super(fm);
        this.school = school;
        Log.i("Value of selected", String.valueOf(school));
    }

    @Override
    public Fragment getItem(int position) {
        Bundle bundle = new Bundle();
        SchoolFragment f = new SchoolFragment();
        switch(position) {
            case 0:
                bundle.putInt(DINING_HALL_KEY, school);
                f.setArguments(bundle);
                return f;
            case 1:
                bundle.putInt(DINING_HALL_KEY, DBConfig.FRARY);
                f.setArguments(bundle);
                return f;
            case 2:
                bundle.putInt(DINING_HALL_KEY, DBConfig.OLDENBORG);
                f.setArguments(bundle);
                return f;
            case 3:
                bundle.putInt(DINING_HALL_KEY, DBConfig.CMC);
                f.setArguments(bundle);
                return f;
            case 4:
                bundle.putInt(DINING_HALL_KEY, DBConfig.SCRIPPS);
                f.setArguments(bundle);
                return f;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 5;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles[position];
    }



}
