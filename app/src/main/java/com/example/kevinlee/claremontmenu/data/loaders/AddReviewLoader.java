package com.example.kevinlee.claremontmenu.data.loaders;


import android.content.AsyncTaskLoader;
import android.content.Context;

import com.example.kevinlee.claremontmenu.data.Installation;
import com.example.kevinlee.claremontmenu.data.network.DBConfig;
import com.example.kevinlee.claremontmenu.data.network.RequestHandler;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by kevinlee on 1/1/17.
 */

public class AddReviewLoader extends AsyncTaskLoader<String> {

    private String reviewText;
    private int food_id;
    private String rating;

    public AddReviewLoader(Context context, int food_id, String reviewText, String rating) {
        super(context);
        this.food_id = food_id;
        this.reviewText = reviewText;
        this.rating = rating;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public String loadInBackground() {
        HashMap<String, String> params = new HashMap<>();
        Date now = new Date();
        String pattern = "yyyy-MM-dd";
        SimpleDateFormat formatter = new SimpleDateFormat(pattern);
        String today = formatter.format(now);
        params.put(DBConfig.KEY_REVIEW_FOOD_ID, Integer.toString(food_id));
        params.put(DBConfig.KEY_REVIEW_USER_ID, Installation.id(getContext()));
        params.put(DBConfig.KEY_RATING, rating);
        params.put(DBConfig.KEY_REVIEW_TEXT, reviewText);
        params.put(DBConfig.KEY_REVIEW_CREATED_AT, today);
        RequestHandler rh = new RequestHandler();
        String result = rh.sendPostRequest(DBConfig.URL_ADD_REVIEW, params);
        return result;
    }
}
