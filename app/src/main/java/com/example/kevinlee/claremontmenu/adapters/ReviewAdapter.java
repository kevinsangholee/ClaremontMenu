package com.example.kevinlee.claremontmenu.adapters;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.kevinlee.claremontmenu.R;
import com.example.kevinlee.claremontmenu.data.Review;

import java.util.ArrayList;

/**
 * Created by kevinlee on 1/2/17.
 */

public class ReviewAdapter extends ArrayAdapter<Review> {

    public ReviewAdapter(Activity context, ArrayList<Review> reviews) {
        super(context, 0, reviews);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.review_list_item, parent, false);
        }

        Review currentReview =  getItem(position);
        String reviewText = currentReview.getReview_text();
        String createdAt = currentReview.getCreated_at();
        int rating = currentReview.getRating();

        TextView reviewTextView = (TextView) listItemView.findViewById(R.id.review_text_view);
        TextView createdAtTextView = (TextView) listItemView.findViewById(R.id.created_at_text_view);
        RatingBar ratingBar = (RatingBar) listItemView.findViewById(R.id.review_list_rating_bar);

        reviewTextView.setText(reviewText);
        createdAtTextView.setText("Created at " + createdAt);
        ratingBar.setRating(rating);

        return listItemView;
    }

}
