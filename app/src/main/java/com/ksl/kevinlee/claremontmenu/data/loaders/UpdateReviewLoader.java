package com.ksl.kevinlee.claremontmenu.data.loaders;

import android.content.AsyncTaskLoader;
import android.content.Context;

import com.ksl.kevinlee.claremontmenu.data.network.DBConfig;
import com.ksl.kevinlee.claremontmenu.data.network.RequestHandler;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by kevinlee on 1/18/17.
 */

public class UpdateReviewLoader extends AsyncTaskLoader<String> {

    private int review_id;
    private String rating;
    private String review_text;

    public UpdateReviewLoader(Context context, int review_id, String rating, String review_text) {
        super(context);
        this.review_id = review_id;
        this.rating = rating;
        this.review_text = review_text;
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
        params.put(DBConfig.KEY_REVIEW_ID, Integer.toString(review_id));
        params.put(DBConfig.KEY_RATING, rating);
        params.put(DBConfig.KEY_REVIEW_TEXT, review_text);
        params.put(DBConfig.KEY_REVIEW_CREATED_AT, today);
        RequestHandler rh = new RequestHandler();
        String data = rh.sendPostRequest(DBConfig.URL_UPDATE_REVIEW, params);
        return data;
    }

}
