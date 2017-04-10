package com.ksl.kevinlee.claremontmenu.data.loaders;


import android.content.Context;

import com.ksl.kevinlee.claremontmenu.data.network.DBConfig;
import com.ksl.kevinlee.claremontmenu.data.network.RequestHandler;

import java.util.HashMap;

/**
 * Created by kevinlee on 12/30/16.
 */

public class AddFoodLoader extends android.support.v4.content.AsyncTaskLoader<String> {

    int school_id;

    public AddFoodLoader(Context context, int id) {
        super(context);
        school_id = id;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public String loadInBackground() {
        HashMap<String, String> params = new HashMap<>();
        RequestHandler rh = new RequestHandler();
        String JSON_DATA = rh.sendGetRequestAspcAll(DBConfig.ASPC_BASE_URL, school_id);
        params.put(DBConfig.KEY_JSON, JSON_DATA);
        params.put(DBConfig.KEY_FOOD_SCHOOL, String.valueOf(school_id));
        String result = rh.sendPostRequest(DBConfig.URL_ADD_ASPC_FOOD, params);
        return result;
    }
}
