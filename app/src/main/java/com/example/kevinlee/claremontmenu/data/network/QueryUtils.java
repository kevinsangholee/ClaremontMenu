package com.example.kevinlee.claremontmenu.data.network;

import android.text.Html;
import android.util.Log;

import com.example.kevinlee.claremontmenu.data.Food;
import com.example.kevinlee.claremontmenu.data.Review;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by kevinlee on 1/1/17.
 */

public class QueryUtils {

    private static final String LOG_TAG = QueryUtils.class.getSimpleName();

    private QueryUtils() {
    }

    public static ArrayList<Food> extractFoods(String JSON_DATA) {
        ArrayList<Food> foods = new ArrayList<Food>();

        try {
            JSONArray data = new JSONArray(JSON_DATA);
            for (int i = 0; i < data.length(); i++) {
                JSONObject currentFood = data.optJSONObject(i);
                int id = currentFood.getInt(DBConfig.TAG_FOOD_ID);
                String name = Html.fromHtml(currentFood.getString(DBConfig.TAG_FOOD_NAME)).toString();
                int school = currentFood.getInt(DBConfig.TAG_FOOD_SCHOOL);
                int review_count = currentFood.getInt(DBConfig.TAG_FOOD_REVIEW_COUNT);
                double rating = currentFood.getDouble(DBConfig.KEY_RATING);
                String imageURL = currentFood.getString(DBConfig.TAG_FOOD_IMAGE);
                foods.add(new Food(id, name, school, imageURL, review_count, rating));

//                 ADDING IMAGES TO THE DATABASE
                if(imageURL.equals("null")) {
                    HashMap<String, String> params = new HashMap<>();
                    RequestHandler rh = new RequestHandler();
                    String bingImageJSON = rh.sendGetRequestBingImageAPI(name);
                    String bingImageURL = extractFirstImageUrl(bingImageJSON);
                    params.put(DBConfig.KEY_FOOD_ID, String.valueOf(id));
                    params.put(DBConfig.KEY_FOOD_IMAGE, bingImageURL);
                    String result = rh.sendPostRequest(DBConfig.URL_UPDATE_IMAGE, params);
                    Log.i(LOG_TAG, result);
                }
            }
        } catch (JSONException e) {
            Log.e(LOG_TAG, "Error processing JSON data", e);
        } catch (Exception e) {
            Log.e(LOG_TAG, "Exception", e);
        }
        return foods;
    }

    public static ArrayList<Review> extractReviews(String JSON_DATA) {
        ArrayList<Review> reviews = new ArrayList<>();
        try {
            JSONArray data = new JSONArray(JSON_DATA);
            for(int i = 0; i < data.length(); i++) {
                JSONObject currentReview = data.optJSONObject(i);
                int food_id = currentReview.getInt(DBConfig.TAG_REVIEW_FOOD_ID);
                String user_id = currentReview.getString(DBConfig.TAG_REVIEW_USER_ID);
                int rating = currentReview.getInt(DBConfig.KEY_REVIEW_RATING);
                String review_text = currentReview.getString(DBConfig.TAG_REVIEW_TEXT);
                String created_at = currentReview.getString(DBConfig.TAG_REVIEW_CREATED_AT);
                if(!review_text.equals("")) {
                    reviews.add(new Review(food_id, user_id, rating, review_text, created_at));
                }
            }
        } catch (JSONException e) {
            Log.e(LOG_TAG, "Error processing JSON data", e);
        }
        return reviews;
    }

    public static ArrayList<String> extractASPCFoodList(String JSON_DATA) {
        ArrayList<String> foodList = new ArrayList<>();
        try {
            JSONArray root = new JSONArray(JSON_DATA);
            for(int i = 0; i < root.length(); i++) {
                JSONObject currentMeal = root.optJSONObject(i);
                JSONArray food_items = currentMeal.optJSONArray(DBConfig.ASPC_FOOD_ITEMS);
                for(int j = 0; j < food_items.length(); j++) {
                    foodList.add(food_items.getString(j));
                }
            }
        } catch (JSONException e) {
            Log.e(LOG_TAG, "Error processing JSON data", e);
        }
        return foodList;
    }

    public static Food extractSingleFood(String JSON_DATA) {
        try {
            JSONArray root = new JSONArray(JSON_DATA);
            JSONObject currentFood = root.optJSONObject(0);
            int id = currentFood.getInt(DBConfig.TAG_FOOD_ID);
            String name = currentFood.getString(DBConfig.TAG_FOOD_NAME);
            int school = currentFood.getInt(DBConfig.TAG_FOOD_SCHOOL);
            int review_count = currentFood.getInt(DBConfig.TAG_FOOD_REVIEW_COUNT);
            double rating = currentFood.getDouble(DBConfig.KEY_RATING);
            String imageURL = currentFood.getString(DBConfig.TAG_FOOD_IMAGE);
            return new Food(id, name, school, imageURL, review_count, rating);
        } catch (JSONException e) {
            Log.e(LOG_TAG, "Error processing JSON data", e);
        }
        return null;
    }

    public static String extractFirstImageUrl(String JSON_DATA) {
        try {
            JSONObject root = new JSONObject(JSON_DATA);
            JSONArray value = root.optJSONArray(DBConfig.TAG_BING_VALUE);
            JSONObject current = value.optJSONObject(0);
            String url = current.getString(DBConfig.TAG_BING_CONTENT_URL);
            return url;
        } catch (JSONException e) {
            Log.e(LOG_TAG, "Error processing JSON data", e);
        }
        return null;
    }

}
