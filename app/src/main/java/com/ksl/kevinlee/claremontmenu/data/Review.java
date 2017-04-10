package com.ksl.kevinlee.claremontmenu.data;

/**
 * Created by kevinlee on 1/2/17.
 */

public class Review {

    private int id;
    private int food_id;
    private String user_id;
    private int rating;
    private String review_text;
    private String created_at;

    public Review(int food_id, String user_id, int rating, String review_text, String created_at) {
        this.food_id = food_id;
        this.user_id = user_id;
        this.rating = rating;
        this.review_text = review_text;
        this.created_at = created_at;
    }

    public int getFood_id() {
        return food_id;
    }

    public String getUser_id() {
        return getUser_id();
    }

    public int getRating() {
        return rating;
    }

    public String getReview_text() {
        return review_text;
    }

    public String getCreated_at() {
        return created_at;
    }

}
