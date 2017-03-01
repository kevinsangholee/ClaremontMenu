package com.example.kevinlee.claremontmenu.data.loaders;


import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import com.example.kevinlee.claremontmenu.data.Food;
import com.example.kevinlee.claremontmenu.data.network.DBConfig;
import com.example.kevinlee.claremontmenu.data.network.QueryUtils;
import com.example.kevinlee.claremontmenu.data.network.RequestHandler;

import java.util.ArrayList;

/**
 * Created by kevinlee on 1/1/17.
 */

public class GetMealLoader extends AsyncTaskLoader<ArrayList<Food>> {

    private int school_id;
    private int meal;

    public GetMealLoader(Context context, int school_id, int meal) {
        super(context);
        this.school_id = school_id;
        this.meal = meal;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public ArrayList<Food> loadInBackground() {
        RequestHandler rh = new RequestHandler();
        String ASPC_JSON = rh.sendGetRequestAspcToday(DBConfig.ASPC_BASE_URL, school_id, meal);
        Log.i("JSON FOR ASPC ", ASPC_JSON);
        ArrayList<String> foodList = QueryUtils.extractASPCFoodList(ASPC_JSON);
        if(foodList.size() != 0) {
            String JSON_DATA = rh.sendGetRequestWithStringArray(DBConfig.URL_GET_FOOD, school_id, foodList);
            Log.i("JSON Food data ASPC", JSON_DATA);
            return QueryUtils.extractFoods(JSON_DATA);
        }
        else {
            return new ArrayList<Food>();
        }
    }
}
