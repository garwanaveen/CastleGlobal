package com.castleglobal.assignment.Utils;

import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;

import com.castleglobal.assignment.Objects.Location;
import com.castleglobal.assignment.Objects.Restaurant;
import com.castleglobal.assignment.Objects.UserRating;
import com.castleglobal.assignment.database.RestaurantDatabaseHelper;
import com.castleglobal.assignment.database.RestaurantDatabaseOperations;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by naveenkumar on 24/09/17.
 */

public class Parser {

    public static ArrayList<Restaurant> parseAndSaveRestuarantDate(String data, SQLiteDatabase db, SharedPreferences.Editor spEditor){

        ArrayList<Restaurant> restaurants = new ArrayList<>();

        try{
            JSONObject mainObj = new JSONObject(data);
            spEditor.putInt("results_found", mainObj.optInt("results_found"));
            spEditor.putInt("results_start", mainObj.optInt("results_start"));
            spEditor.putInt("results_shown", mainObj.optInt("results_shown"));
            spEditor.commit();

            JSONArray restaurantsArr = mainObj.optJSONArray("restaurants");

            for(int i = 0; i< restaurantsArr.length(); i++){

                JSONObject restObject = restaurantsArr.getJSONObject(i).getJSONObject("restaurant");
                JSONObject locObject = restObject.getJSONObject("location");
                JSONObject ratingObject = restObject.getJSONObject("user_rating");

                Location tempLoc = new Location(locObject.optString("address"), locObject.optString("locality"), locObject.optString("city"), locObject.optInt("city_id"),
                        locObject.optString("latitude"), locObject.optString("longitude"), locObject.optString("zipcode"), locObject.optInt("country_id"), locObject.optString("locality_verbose"));

                UserRating tempRating = new UserRating(ratingObject.optString("aggregate_rating"), ratingObject.optString("rating_text"),
                        ratingObject.optString("rating_color"),ratingObject.optString("votes") );

                Restaurant temp = new Restaurant(restObject.optString("cuisines"),
                        restObject.optString("events_url"), restObject.optInt("has_table_booking") == 1,
                        restObject.optString("order_url"), tempRating, tempLoc,
                        restObject.optInt("has_online_delivery") == 1, restObject.optString("offers"),
                        restObject.optString("order_deep_link"), restObject.optInt("is_delivering_now") == 1,
                        restObject.optString("url"),restObject.optInt("switchToOrderMenu"),
                        restObject.optString("api_key"), restObject.optString("currency"),
                        restObject.optInt("id"), restObject.optInt("average_cost_for_two"),
                        restObject.optInt("price_range"), restObject.optString("menu_url"),
                        restObject.optString("photos_url"),restObject.optString("name"),
                        restObject.optString("deeplink"),restObject.optString("thumb"),
                        restObject.optString("featured_image"));
                restaurants.add(temp);
            }

        } catch(JSONException e){
            e.printStackTrace();
        }
        RestaurantDatabaseOperations.insertRestaurantsToDB(restaurants, db);
        return restaurants;
    }
}
