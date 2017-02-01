package com.example.kevinlee.claremontmenu.data.loaders;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.InputStream;

/**
 * Created by kevinlee on 1/8/17.
 */

public class GetFoodImageLoader extends AsyncTaskLoader<Bitmap>{

    private String imageURL;

    public GetFoodImageLoader(Context context, String imageURL) {
        super(context);
        this.imageURL = imageURL;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public Bitmap loadInBackground() {
        Bitmap image = null;
        try {
            InputStream in = new java.net.URL(imageURL).openStream();
            image  = BitmapFactory.decodeStream(in);
        } catch (Exception e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }
        return image;
    }
}
