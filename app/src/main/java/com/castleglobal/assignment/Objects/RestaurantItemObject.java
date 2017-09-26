package com.castleglobal.assignment.Objects;

import android.support.annotation.NonNull;

import java.util.Comparator;

/**
 * Created by naveenkumar on 25/09/17.
 */

public class RestaurantItemObject {

    String cuisine;
    Restaurant restaurant;

    public RestaurantItemObject() {

    }

    public RestaurantItemObject(String cuisine, Restaurant restaurant) {
        this.cuisine = cuisine;
        this.restaurant = restaurant;
    }

    public String getCuisine() {
        return cuisine;
    }

    public void setCuisine(String cuisine) {
        this.cuisine = cuisine;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

}
