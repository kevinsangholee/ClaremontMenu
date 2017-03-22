package com.example.kevinlee.claremontmenu.screen;

import android.app.LoaderManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Loader;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kevinlee.claremontmenu.R;
import com.example.kevinlee.claremontmenu.adapters.ReviewAdapter;
import com.example.kevinlee.claremontmenu.data.Food;
import com.example.kevinlee.claremontmenu.data.Review;
import com.example.kevinlee.claremontmenu.data.loaders.AddReviewLoader;
import com.example.kevinlee.claremontmenu.data.loaders.DeleteReviewLoader;
import com.example.kevinlee.claremontmenu.data.loaders.GetReviewsLoader;
import com.example.kevinlee.claremontmenu.data.loaders.GetSingleFoodLoader;
import com.example.kevinlee.claremontmenu.data.loaders.UpdateReviewLoader;
import com.example.kevinlee.claremontmenu.data.network.DBConfig;
import com.example.kevinlee.claremontmenu.data.network.QueryUtils;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.Collections;

public class
ReviewActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<String>{

    private static final String LOG_TAG = ReviewActivity.class.getSimpleName();

    /**
     */
    final Context context = this;

    private static final int GET_REVIEWS_LOADER = 2;
    private static final int ADD_REVIEW_LOADER = 3;
    private static final int UPDATE_REVIEW_LOADER = 4;
    private static final int DELETE_REVIEW_LOADER = 5;
    private static final int GET_SINGLE_FOOD_LOADER = 6;
    private static final String KEY_REVIEW_TEXT = "review";
    private static final String KEY_RATING = "rating";

    private SharedPreferences sharedPrefs;
    private SharedPreferences.Editor editor;

    private int food_id;
    private String ID_KEY;
    private ReviewAdapter adapter;
    private ArrayList<Review> reviews;
    private int review_id;

    private TextView nameTextView;
    private RatingBar reviewRatingBar;
    private TextView ratingTextView;

    private TextView noReviewsTextView;

    private int count = 0;

    private LoaderManager.LoaderCallbacks<Food> GetSingleFoodListener = new LoaderManager.LoaderCallbacks<Food>() {
        @Override
        public Loader<Food> onCreateLoader(int id, Bundle args) {
            return new GetSingleFoodLoader(getApplicationContext(), args.getInt(DBConfig.KEY_FOOD_ID));
        }

        @Override
        public void onLoadFinished(Loader<Food> loader, Food data) {
            nameTextView.setText(data.getName());
            reviewRatingBar.setRating((float) data.getRating());
            ratingTextView.setText(String.format(getString(R.string.rating_template), data.getRating()));
        }

        @Override
        public void onLoaderReset(Loader<Food> loader) {
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);

        Toolbar toolBar = (Toolbar) findViewById(R.id.my_review_toolbar);
        setSupportActionBar(toolBar);

        ActionBar ab = getSupportActionBar();

        ab.setDisplayHomeAsUpEnabled(true);

        sharedPrefs = getPreferences(Context.MODE_PRIVATE);
        editor = sharedPrefs.edit();

        nameTextView = (TextView) findViewById(R.id.review_name_text_view);
        ImageView foodImageView = (ImageView) findViewById(R.id.review_food_image_view);
        reviewRatingBar = (RatingBar) findViewById(R.id.review_activity_rating_bar);
        ratingTextView = (TextView) findViewById(R.id.review_activity_rating);
        noReviewsTextView = (TextView) findViewById(R.id.no_reviews_text_view);

        FloatingActionButton addReviewButton = (FloatingActionButton) findViewById(R.id.review_fab);

        Bundle b = getIntent().getExtras();
        double rating = b.getDouble(DBConfig.KEY_RATING);
        food_id = b.getInt(DBConfig.KEY_FOOD_ID);
        ID_KEY = String.valueOf(food_id);
        review_id = sharedPrefs.getInt(ID_KEY, -1);

//        editor.putInt(ID_KEY, -1);
//        editor.putString(ID_KEY + DBConfig.KEY_REVIEW_TEXT, "");
//        editor.putInt(ID_KEY + DBConfig.KEY_RATING, 0);
//        editor.apply();

        nameTextView.setText(b.getString(DBConfig.KEY_FOOD_NAME));
        reviewRatingBar.setRating((float) rating);
        ratingTextView.setText(String.format(getString(R.string.rating_template), rating));

        String imageURL = b.getString(DBConfig.KEY_FOOD_IMAGE);
        if(!imageURL.equals("null")) {
            ImageLoader.getInstance().displayImage(imageURL, foodImageView);
        } else {
            foodImageView.setImageResource(R.drawable.no_image);
        }

        // Setting up review ListView
        ListView listView = (ListView) findViewById(R.id.review_list);
        adapter = new ReviewAdapter(this, new ArrayList<Review>());
        listView.setAdapter(adapter);

        // Setting up the add review button
        addReviewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                LayoutInflater li = LayoutInflater.from(context);
                View addReviewView = li.inflate(R.layout.add_review_dialog, null);
                final EditText reviewEditText = (EditText) addReviewView.findViewById(R.id.add_review_edit_text);
                final RatingBar ratingBar = (RatingBar) addReviewView.findViewById(R.id.add_review_rating_bar);
                builder.setView(addReviewView);
                builder.setCancelable(true);
                if(review_id == -1) {
                    builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            String review = reviewEditText.getText().toString();
                            float rating = ratingBar.getRating();
                            if (rating == 0) {
                                Toast.makeText(ReviewActivity.this, "Error: Please indicate a star rating.", Toast.LENGTH_SHORT).show();
                            } else {
                                addReview(review, Integer.toString((int) rating));
                                editor.putInt(ID_KEY + KEY_RATING, (int) rating);
                                editor.putString(ID_KEY + KEY_REVIEW_TEXT, review);
                                editor.apply();
                            }
                        }
                    });
                    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                } else {
                    reviewEditText.setText(sharedPrefs.getString(ID_KEY + KEY_REVIEW_TEXT, ""));
                    ratingBar.setRating(sharedPrefs.getInt(ID_KEY + KEY_RATING, 0));
                    builder.setPositiveButton("Update", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            String review = reviewEditText.getText().toString();
                            float rating = ratingBar.getRating();
                            if (rating == 0) {
                                Toast.makeText(ReviewActivity.this, "Error: Please indicate a star rating.", Toast.LENGTH_SHORT).show();
                            } else {
                                updateReview(review, Integer.toString((int) rating));
                                editor.putInt(ID_KEY + KEY_RATING, (int) rating);
                                editor.putString(ID_KEY + KEY_REVIEW_TEXT, review);
                                editor.apply();
                            }
                        }
                    });
                    builder.setNeutralButton("Delete", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            deleteReview();
                        }
                    });
                    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                }
                AlertDialog addReviewDialog = builder.create();
                addReviewDialog.show();
            }
        });

        showReviews();
    }

    /**
     *
     * ADD DELETE REVIEW UPDATE REVIEW LOADERS HERE
     *
     */

    @Override
    public Loader<String> onCreateLoader(int id, Bundle args) {
        String review;
        String rating;
        switch(id) {
            case GET_REVIEWS_LOADER:
                return new GetReviewsLoader(this, food_id);
            case ADD_REVIEW_LOADER:
                review = args.getString(KEY_REVIEW_TEXT);
                rating = args.getString(DBConfig.KEY_RATING);
                return new AddReviewLoader(this, food_id, review, rating);
            case UPDATE_REVIEW_LOADER:
                review = args.getString(KEY_REVIEW_TEXT);
                rating = args.getString(DBConfig.KEY_RATING);
                return new UpdateReviewLoader(this, review_id, rating, review);
            case DELETE_REVIEW_LOADER:
                return new DeleteReviewLoader(this, review_id, food_id);
            default:
                return null;
        }
    }

    @Override
    public void onLoadFinished(Loader<String> loader, String data) {
        int loaderId;
        switch(loaderId = loader.getId()) {
            case ADD_REVIEW_LOADER:
                Toast.makeText(this, "Review added!", Toast.LENGTH_SHORT).show();
                editor.putInt(ID_KEY, Integer.parseInt(data));
                editor.apply();
                review_id = Integer.parseInt(data);
                showReviews();
                refreshFoodData();
                break;
            case GET_REVIEWS_LOADER:
                reviews = QueryUtils.extractReviews(data);
                Collections.reverse(reviews);
                adapter.clear();
                adapter.addAll(reviews);
                if(reviews.size() == 0) {
                    noReviewsTextView.setText(getString(R.string.no_reviews));
                } else {
                    noReviewsTextView.setText("");
                }
                break;
            case UPDATE_REVIEW_LOADER:
                Toast.makeText(this, data, Toast.LENGTH_SHORT).show();
                Log.i(LOG_TAG, data);
                showReviews();
                refreshFoodData();
                break;
            case DELETE_REVIEW_LOADER:
                Toast.makeText(this, data, Toast.LENGTH_SHORT).show();
                Log.i(LOG_TAG, data);
                editor.putInt(ID_KEY, -1);
                editor.putString(ID_KEY + KEY_REVIEW_TEXT, "");
                editor.putInt(ID_KEY + KEY_RATING, 0);
                editor.apply();
                review_id = -1;
                showReviews();
                refreshFoodData();
                break;
            default:
        }
        getLoaderManager().destroyLoader(loaderId);
    }

    @Override
    public void onLoaderReset(Loader<String> loader) {
        adapter.clear();
    }

    public void showReviews() {
        getLoaderManager().initLoader(GET_REVIEWS_LOADER, null, this);
    }

    public void addReview(String reviewText, String rating) {
        Bundle b = new Bundle();
        b.putString(KEY_REVIEW_TEXT, reviewText);
        b.putString(DBConfig.KEY_RATING, rating);
        getLoaderManager().initLoader(ADD_REVIEW_LOADER, b, this);
    }

    public void updateReview(String reviewText, String rating) {
        Bundle b = new Bundle();
        b.putString(KEY_REVIEW_TEXT, reviewText);
        b.putString(DBConfig.KEY_RATING, rating);
        getLoaderManager().initLoader(UPDATE_REVIEW_LOADER, b, this);
    }

    public void deleteReview() {
        getLoaderManager().initLoader(DELETE_REVIEW_LOADER, null, this);
    }

    public void refreshFoodData() {
        Bundle b = new Bundle();
        b.putInt(DBConfig.KEY_FOOD_ID, food_id);
        if(count == 0) {
            getLoaderManager().initLoader(GET_SINGLE_FOOD_LOADER, b, GetSingleFoodListener);
        } else {
            getLoaderManager().restartLoader(GET_SINGLE_FOOD_LOADER, b, GetSingleFoodListener);
        }
        count++;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
