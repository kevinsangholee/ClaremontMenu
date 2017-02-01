package com.example.kevinlee.claremontmenu.data.loaders;

import android.content.AsyncTaskLoader;
import android.content.Context;

import com.example.kevinlee.claremontmenu.data.network.DBConfig;
import com.example.kevinlee.claremontmenu.data.network.RequestHandler;

/**
 * Created by kevinlee on 1/2/17.
 */

public class GetReviewsLoader extends AsyncTaskLoader<String> {

    private int food_id;

    public GetReviewsLoader(Context context, int food_id) {
        super(context);
        this.food_id = food_id;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public String loadInBackground() {
        RequestHandler rh = new RequestHandler();
        String JSON_DATA = rh.sendGetRequestReviews(DBConfig.URL_GET_REVIEWS, food_id);
        return JSON_DATA;
    }
}
