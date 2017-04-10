package com.ksl.kevinlee.claremontmenu.data.loaders;

import android.content.AsyncTaskLoader;
import android.content.Context;

import com.ksl.kevinlee.claremontmenu.data.Food;
import com.ksl.kevinlee.claremontmenu.data.network.DBConfig;
import com.ksl.kevinlee.claremontmenu.data.network.QueryUtils;
import com.ksl.kevinlee.claremontmenu.data.network.RequestHandler;

/**
 * Created by kevinlee on 1/18/17.
 */

public class GetSingleFoodLoader extends AsyncTaskLoader<Food> {

    private int food_id;

    public GetSingleFoodLoader(Context context, int food_id) {
        super(context);
        this.food_id = food_id;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public Food loadInBackground() {
        RequestHandler rh = new RequestHandler();
        String data = rh.sendGetRequestSingleFood(DBConfig.URL_GET_SINGLE_FOOD, food_id);
        return QueryUtils.extractSingleFood(data);
    }

}
