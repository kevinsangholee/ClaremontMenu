package com.ksl.kevinlee.claremontmenu.data.loaders;

import android.content.Context;

import com.ksl.kevinlee.claremontmenu.data.network.DBConfig;
import com.ksl.kevinlee.claremontmenu.data.network.RequestHandler;

import java.util.HashMap;

/**
 * Created by kevinlee on 1/18/17.
 */

public class DeleteReviewLoader extends android.content.AsyncTaskLoader<String> {

    private int review_id;
    private int food_id;

    public DeleteReviewLoader(Context context, int review_id, int food_id) {
        super(context);
        this.review_id = review_id;
        this.food_id = food_id;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public String loadInBackground() {
        HashMap<String, String> params = new HashMap<>();
        params.put(DBConfig.KEY_REVIEW_ID, Integer.toString(review_id));
        params.put(DBConfig.KEY_REVIEW_FOOD_ID, Integer.toString(food_id));
        RequestHandler rh = new RequestHandler();
        String data = rh.sendPostRequest(DBConfig.URL_DELETE_REVIEW, params);
        return data;
    }
}
