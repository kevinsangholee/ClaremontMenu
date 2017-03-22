package com.example.kevinlee.claremontmenu.data.network;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by kevinlee on 12/30/16.
 */

public class DBConfig {

    public static final int FRANK = 0;
    public static final int FRARY = 1;
    public static final int OLDENBORG = 2;
    public static final int CMC = 3;
    public static final int SCRIPPS = 4;
    public static final int PITZER = 5;
    public static final int MUDD = 6;

    public static final int BREAKFAST = 0;
    public static final int LUNCH = 1;
    public static final int DINNER = 2;
    public static final int BRUNCH = 3;

    public static final String URL_GET_ALL_FOODS = "https://claremontmenu.com/getAllFood.php";
    public static final String URL_GET_SCHOOL_FOOD = "https://claremontmenu.com/getSchoolFood.php?school=";
    public static final String URL_GET_FOOD = "https://claremontmenu.com/pdo/getFood.php?";
    public static final String URL_ADD_FOOD = "https://claremontmenu.com/addFood.php";
    public static final String URL_ADD_REVIEW = "https://claremontmenu.com/pdo/addReview.php";
    public static final String URL_GET_REVIEWS = "https://claremontmenu.com/pdo/getReviews.php?";
    public static final String URL_DELETE_REVIEW = "https://claremontmenu.com/pdo/deleteReview.php";
    public static final String URL_UPDATE_REVIEW = "https://claremontmenu.com/pdo/updateReview.php";
    public static final String URL_ADD_ASPC_FOOD = "https://claremontmenu.com/pdo/addASPCFood.php";
    public static final String URL_GET_SINGLE_FOOD = "https://claremontmenu.com/pdo/getSingleFood.php?id=";
    public static final String URL_UPDATE_IMAGE = "https://claremontmenu.com/pdo/updateImage.php";

    public static final String KEY_JSON = "json";

    public static final String KEY_FOOD_ID = "id";
    public static final String KEY_FOOD_NAME = "name";
    public static final String KEY_FOOD_SCHOOL = "school";
    public static final String KEY_FOOD_IMAGE = "image";
    public static final String KEY_FOOD_REVIEW_COUNT = "review_count";
    public static final String KEY_RATING = "rating";

    public static final String TAG_FOOD_ID = "id";
    public static final String TAG_FOOD_NAME = "name";
    public static final String TAG_FOOD_SCHOOL = "school";
    public static final String TAG_FOOD_IMAGE = "image";
    public static final String TAG_FOOD_REVIEW_COUNT = "review_count";

    public static final String KEY_REVIEW_ID = "id";
    public static final String KEY_REVIEW_FOOD_ID = "food_id";
    public static final String KEY_REVIEW_USER_ID = "user_id";
    public static final String KEY_REVIEW_RATING = "rating";
    public static final String KEY_REVIEW_TEXT = "review_text";
    public static final String KEY_REVIEW_CREATED_AT = "created_at";

    public static final String TAG_REVIEW_ID = "id";
    public static final String TAG_REVIEW_FOOD_ID = "food_id";
    public static final String TAG_REVIEW_USER_ID = "user_id";
    public static final String TAG_REVIEW_TEXT = "review_text";
    public static final String TAG_REVIEW_CREATED_AT = "created_at";

    public static final String TAG_JSON_ARRAY = "result";
    public static final String FOOD_ID = "food_id";

    public static final String ASPC_BASE_URL = "https://aspc.pomona.edu/api/menu/";
    public static final String ASPC_AUTH_TOKEN = "/?auth_token=2a9c963d749d6de4933579c611723625b74521c0";
    public static final String ASPC_DINING_HALL = "dining_hall";
    public static final String ASPC_DAY = "day";
    public static final String ASPC_MEAL = "meal";
    public static final String ASPC_FOOD_ITEMS = "food_items";

    public static final String TAG_BING_VALUE = "value";
    public static final String TAG_BING_CONTENT_URL = "contentUrl";

    public static final String HOURS_KEY = "hours";

    public static boolean isWeekend() {
        SimpleDateFormat sdf = new SimpleDateFormat("EEEE");
        Date d = new Date();
        String dayOfWeek = sdf.format(d);
        return dayOfWeek.equals("Sunday") || dayOfWeek.equals("Saturday");
    }
}
