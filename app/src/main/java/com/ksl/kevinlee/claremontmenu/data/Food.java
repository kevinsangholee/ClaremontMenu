package com.ksl.kevinlee.claremontmenu.data;

/**
 * Created by kevinlee on 12/23/16.
 */

public class Food {

    private int id;
    private String name;
    private int school;
    private String image;
    private int review_count;
    private double rating;

    public Food(int id, String name, int school, String image, int review_count, double rating) {
        this.id = id;
        this.name = name;
        this.school = school;
        this.image = image;
        this.review_count = review_count;
        this.rating = rating;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getSchool() {
        return school;
    }

    public String getImage() {
        return image;
    }

    public int getReview_count() {
        return review_count;
    }

    public double getRating() {
        return rating;
    }
}
