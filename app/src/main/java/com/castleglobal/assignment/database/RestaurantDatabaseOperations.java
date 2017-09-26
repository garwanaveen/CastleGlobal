package com.castleglobal.assignment.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.castleglobal.assignment.Objects.Location;
import com.castleglobal.assignment.Objects.Restaurant;
import com.castleglobal.assignment.Objects.UserRating;

import java.util.ArrayList;

/**
 * Created by naveenkumar on 24/09/17.
 */

public class RestaurantDatabaseOperations {


            /* Insert All restaurants
            @param SQLiteDatabase writableDatabase
            @param Arraylist of all restaurant entries
             */

    public static void insertRestaurantsToDB(ArrayList<Restaurant> restaurants, SQLiteDatabase db){



        // Create a new map of values, where column names are the keys
        for(int i=0; i<restaurants.size(); i++) {
            ContentValues values = new ContentValues();
            values.put(RestaurantDatabaseHelper.ID, restaurants.get(i).getId());
            values.put(RestaurantDatabaseHelper.NAME, restaurants.get(i).getName());
            values.put(RestaurantDatabaseHelper.URL, restaurants.get(i).getUrl());
            values.put(RestaurantDatabaseHelper.ADDRESS, restaurants.get(i).getLocation().getAddress());
            values.put(RestaurantDatabaseHelper.LOCALITY, restaurants.get(i).getLocation().getLocality());
            values.put(RestaurantDatabaseHelper.CITY, restaurants.get(i).getLocation().getCity());
            values.put(RestaurantDatabaseHelper.LATITUDE, restaurants.get(i).getLocation().getLatitude());
            values.put(RestaurantDatabaseHelper.LONGITUDE, restaurants.get(i).getLocation().getLongitude());
            values.put(RestaurantDatabaseHelper.ZIPCODE, restaurants.get(i).getLocation().getZipcode());
            values.put(RestaurantDatabaseHelper.CUISINES, restaurants.get(i).getCuisines());
            values.put(RestaurantDatabaseHelper.AVGCOSTFORTWO, restaurants.get(i).getAverageCostForTwo());
            values.put(RestaurantDatabaseHelper.PRICERANGE, restaurants.get(i).getPriceRange());
            values.put(RestaurantDatabaseHelper.CURRENCY, restaurants.get(i).getCurrency());
            values.put(RestaurantDatabaseHelper.THUMB, restaurants.get(i).getThumb());
            values.put(RestaurantDatabaseHelper.AVGRATING, restaurants.get(i).getUserRating().getAggregateRating());
            values.put(RestaurantDatabaseHelper.VOTES, restaurants.get(i).getUserRating().getVotes());
            values.put(RestaurantDatabaseHelper.RATINGTXT, restaurants.get(i).getUserRating().getRatingText());
            values.put(RestaurantDatabaseHelper.RATINGCOLOR, restaurants.get(i).getUserRating().getRatingColor());
            values.put(RestaurantDatabaseHelper.PHOTOSURL, restaurants.get(i).getPhotosUrl());
            values.put(RestaurantDatabaseHelper.MENUURL, restaurants.get(i).getMenuUrl());
            values.put(RestaurantDatabaseHelper.EVENTSURL, restaurants.get(i).getEventsUrl());
            values.put(RestaurantDatabaseHelper.FEATUREDIMAGE, restaurants.get(i).getFeaturedImage());
            values.put(RestaurantDatabaseHelper.EVENTSURL, restaurants.get(i).getEventsUrl());
            values.put(RestaurantDatabaseHelper.ISDELIVERYWORKINGNOW, restaurants.get(i).getIsDeliveringNow());
            values.put(RestaurantDatabaseHelper.HASONLINEDELIVERY, restaurants.get(i).getHasOnlineDelivery());
            values.put(RestaurantDatabaseHelper.DEEPLINK, restaurants.get(i).getDeeplink());
            values.put(RestaurantDatabaseHelper.ORDERURL, restaurants.get(i).getOrderUrl());
            values.put(RestaurantDatabaseHelper.ORDERDEEPLINK, restaurants.get(i).getOrderDeeplink());
            values.put(RestaurantDatabaseHelper.HASTABLEBOOKING, restaurants.get(i).getHasTableBooking());

            if(isFav(db,restaurants.get(i).getId()) >= 0){
                values.put(RestaurantDatabaseHelper.ISFAV, ((isFav(db,restaurants.get(i).getId()) == 1)));

                // Filter results WHERE "id" = 'restId'
                String selection = RestaurantDatabaseHelper.ID+ " = ?";
                String[] selectionArgs = { String.valueOf(restaurants.get(i).getId()) };
                long newRowId = db.update(RestaurantDatabaseHelper.TABLE_NAME, values, selection, selectionArgs );
            }else {
                values.put(RestaurantDatabaseHelper.ISFAV, false);
                // Insert the new row, returning the primary key value of the new row
                long newRowId = db.insert(RestaurantDatabaseHelper.TABLE_NAME, null, values);
            }


        }
    }


    /* All restaurants list
            @param SQLiteDatabase readableDatabase
            @return Arraylist of all restaurant database entries
             */
    public static ArrayList<Restaurant> getAllRestaurants(SQLiteDatabase db){


        // Define a projection that specifies which columns from the database
        String[] projection = {
                RestaurantDatabaseHelper.ID,
                RestaurantDatabaseHelper.CUISINES,
                RestaurantDatabaseHelper.NAME,
                RestaurantDatabaseHelper.PRICERANGE,
                RestaurantDatabaseHelper.AVGCOSTFORTWO,
                RestaurantDatabaseHelper.RATINGTXT,
                RestaurantDatabaseHelper.VOTES,
                RestaurantDatabaseHelper.AVGRATING,
                RestaurantDatabaseHelper.THUMB,
                RestaurantDatabaseHelper.HASONLINEDELIVERY,
                RestaurantDatabaseHelper.HASTABLEBOOKING,
                RestaurantDatabaseHelper.ISDELIVERYWORKINGNOW,
                RestaurantDatabaseHelper.ISFAV,
                RestaurantDatabaseHelper.CITY
        };

        // Sort order
        String sortOrder =
                RestaurantDatabaseHelper.ID + " ASC";

        Cursor cursor = db.query(
                RestaurantDatabaseHelper.TABLE_NAME, projection, null, null, null, null, sortOrder);

        ArrayList<Restaurant> restaurants = new ArrayList<>();
        while(cursor.moveToNext()){
            Restaurant temp = new Restaurant();
            UserRating tempRating = new UserRating();
            Location tempLoc = new Location();

            temp.setCuisines(cursor.getString(cursor.getColumnIndexOrThrow(RestaurantDatabaseHelper.CUISINES)));
            temp.setId(cursor.getInt(cursor.getColumnIndexOrThrow(RestaurantDatabaseHelper.ID)));
            temp.setName(cursor.getString(cursor.getColumnIndexOrThrow(RestaurantDatabaseHelper.NAME)));
            temp.setPriceRange(cursor.getInt(cursor.getColumnIndexOrThrow(RestaurantDatabaseHelper.PRICERANGE)));
            temp.setAverageCostForTwo(cursor.getInt(cursor.getColumnIndexOrThrow(RestaurantDatabaseHelper.AVGCOSTFORTWO)));
            temp.setIsDeliveringNow(cursor.getInt(cursor.getColumnIndexOrThrow(RestaurantDatabaseHelper.ISDELIVERYWORKINGNOW)) == 1);
            temp.setHasOnlineDelivery(cursor.getInt(cursor.getColumnIndexOrThrow(RestaurantDatabaseHelper.HASONLINEDELIVERY)) == 1);
            temp.setHasTableBooking(cursor.getInt(cursor.getColumnIndexOrThrow(RestaurantDatabaseHelper.HASTABLEBOOKING)) == 1);
            temp.setThumb(cursor.getString(cursor.getColumnIndexOrThrow(RestaurantDatabaseHelper.THUMB)));
            tempRating.setRatingText(cursor.getString(cursor.getColumnIndexOrThrow(RestaurantDatabaseHelper.RATINGTXT)));
            tempRating.setAggregateRating(cursor.getString(cursor.getColumnIndexOrThrow(RestaurantDatabaseHelper.AVGRATING)));
            tempRating.setVotes(cursor.getString(cursor.getColumnIndexOrThrow(RestaurantDatabaseHelper.VOTES)));
            tempLoc.setCity(cursor.getString(cursor.getColumnIndexOrThrow(RestaurantDatabaseHelper.CITY)));

            temp.setLocation(tempLoc);
            temp.setUserRating(tempRating);
            restaurants.add(temp);
        }
        return restaurants;
    }

    /* Check fav field for restaurant id
    @param SQLiteDatabase writabeDatabase
    @param int restaurantId
    @return -1 if no record, 0 for false, 1 for true
     */

    private static int isFav(SQLiteDatabase db, int restId){
        // Define a projection that specifies which columns from the database
        String[] projection = {
                RestaurantDatabaseHelper.ISFAV
        };

    // Filter results WHERE "id" = 'restId'
            String selection = RestaurantDatabaseHelper.ID+ " = ?";
            String[] selectionArgs = { String.valueOf(restId) };


        Cursor cursor = db.query(RestaurantDatabaseHelper.TABLE_NAME, projection,
                                    selection, selectionArgs, null, null, null);

        if (cursor.moveToFirst()) {
            int isFav = cursor.getInt(
                    cursor.getColumnIndexOrThrow(RestaurantDatabaseHelper.ISFAV));
            cursor.close();
            return isFav;

        }
        return -1;
    }

    /* Updates fav field for the restaurant
        @param SQLiteDatabase writabeDatabase
        @param int restaurantId
    */
    public static void updateFav(int  restaurantId, boolean fav, SQLiteDatabase db){

            ContentValues values = new ContentValues();
            values.put(RestaurantDatabaseHelper.ISFAV, fav);

                // Filter results WHERE "id" = 'restId'
                String selection = RestaurantDatabaseHelper.ID+ " = ?";
                String[] selectionArgs = { String.valueOf(restaurantId) };
                long newRowId = db.update(RestaurantDatabaseHelper.TABLE_NAME, values, selection, selectionArgs );

    }


    /* All restaurant details
           @param SQLiteDatabase readableDatabase
           @param int restaurantId
           @return Restaurant object
            */
    public static Restaurant getRestaurantDetails(int restId, SQLiteDatabase db) {


        // Define a projection that specifies which columns from the database
        String[] projection = {
                RestaurantDatabaseHelper.ID,
                RestaurantDatabaseHelper.CUISINES,
                RestaurantDatabaseHelper.NAME,
                RestaurantDatabaseHelper.PRICERANGE,
                RestaurantDatabaseHelper.FEATUREDIMAGE,
                RestaurantDatabaseHelper.AVGCOSTFORTWO,
                RestaurantDatabaseHelper.RATINGTXT,
                RestaurantDatabaseHelper.VOTES,
                RestaurantDatabaseHelper.AVGRATING,
                RestaurantDatabaseHelper.THUMB,
                RestaurantDatabaseHelper.HASONLINEDELIVERY,
                RestaurantDatabaseHelper.HASTABLEBOOKING,
                RestaurantDatabaseHelper.ISDELIVERYWORKINGNOW,
                RestaurantDatabaseHelper.ISFAV,
                RestaurantDatabaseHelper.CITY,
                RestaurantDatabaseHelper.LATITUDE,
                RestaurantDatabaseHelper.LONGITUDE,
                RestaurantDatabaseHelper.MENUURL,
                RestaurantDatabaseHelper.EVENTSURL,
                RestaurantDatabaseHelper.PHOTOSURL,
                RestaurantDatabaseHelper.LOCALITY,
                RestaurantDatabaseHelper.CURRENCY
        };

        String selection = RestaurantDatabaseHelper.ID + " = ?";
        String[] selectionArgs = {String.valueOf(restId)};


        Cursor cursor = db.query(
                RestaurantDatabaseHelper.TABLE_NAME, projection, selection, selectionArgs, null, null, null);

        Restaurant restaurant = new Restaurant();

        if (cursor.moveToNext()) {
            UserRating tempRating = new UserRating();
            Location tempLoc = new Location();

            restaurant.setCuisines(cursor.getString(cursor.getColumnIndexOrThrow(RestaurantDatabaseHelper.CUISINES)));
            restaurant.setId(cursor.getInt(cursor.getColumnIndexOrThrow(RestaurantDatabaseHelper.ID)));
            restaurant.setFeaturedImage(cursor.getString(cursor.getColumnIndexOrThrow(RestaurantDatabaseHelper.FEATUREDIMAGE)));
            restaurant.setName(cursor.getString(cursor.getColumnIndexOrThrow(RestaurantDatabaseHelper.NAME)));
            restaurant.setCurrency(cursor.getString(cursor.getColumnIndexOrThrow(RestaurantDatabaseHelper.CURRENCY)));
            restaurant.setPriceRange(cursor.getInt(cursor.getColumnIndexOrThrow(RestaurantDatabaseHelper.PRICERANGE)));
            restaurant.setAverageCostForTwo(cursor.getInt(cursor.getColumnIndexOrThrow(RestaurantDatabaseHelper.AVGCOSTFORTWO)));
            restaurant.setIsDeliveringNow(cursor.getInt(cursor.getColumnIndexOrThrow(RestaurantDatabaseHelper.ISDELIVERYWORKINGNOW)) == 1);
            restaurant.setIsFav(cursor.getInt(cursor.getColumnIndexOrThrow(RestaurantDatabaseHelper.ISFAV)) == 1);
            restaurant.setHasOnlineDelivery(cursor.getInt(cursor.getColumnIndexOrThrow(RestaurantDatabaseHelper.HASONLINEDELIVERY)) == 1);
            restaurant.setHasTableBooking(cursor.getInt(cursor.getColumnIndexOrThrow(RestaurantDatabaseHelper.HASTABLEBOOKING)) == 1);
            restaurant.setThumb(cursor.getString(cursor.getColumnIndexOrThrow(RestaurantDatabaseHelper.THUMB)));
            restaurant.setMenuUrl(cursor.getString(cursor.getColumnIndexOrThrow(RestaurantDatabaseHelper.MENUURL)));
            restaurant.setPhotosUrl(cursor.getString(cursor.getColumnIndexOrThrow(RestaurantDatabaseHelper.PHOTOSURL)));
            restaurant.setEventsUrl(cursor.getString(cursor.getColumnIndexOrThrow(RestaurantDatabaseHelper.EVENTSURL)));
            tempRating.setRatingText(cursor.getString(cursor.getColumnIndexOrThrow(RestaurantDatabaseHelper.RATINGTXT)));
            tempRating.setAggregateRating(cursor.getString(cursor.getColumnIndexOrThrow(RestaurantDatabaseHelper.AVGRATING)));
            tempRating.setVotes(cursor.getString(cursor.getColumnIndexOrThrow(RestaurantDatabaseHelper.VOTES)));
            tempLoc.setCity(cursor.getString(cursor.getColumnIndexOrThrow(RestaurantDatabaseHelper.CITY)));
            tempLoc.setLocality(cursor.getString(cursor.getColumnIndexOrThrow(RestaurantDatabaseHelper.LOCALITY)));
            tempLoc.setLatitude(cursor.getString(cursor.getColumnIndexOrThrow(RestaurantDatabaseHelper.LATITUDE)));
            tempLoc.setLongitude(cursor.getString(cursor.getColumnIndexOrThrow(RestaurantDatabaseHelper.LONGITUDE)));

            restaurant.setLocation(tempLoc);
            restaurant.setUserRating(tempRating);
        }
        return restaurant;
    }

    /* All restaurants list
            @param String query
            @param SQLiteDatabase readableDatabase
            @param boolean Favonly
            @return Arraylist of all restaurant database entries
             */
        public static ArrayList<Restaurant> getAllRestaurants(String query,SQLiteDatabase db, boolean favOnly){


            // Define a projection that specifies which columns from the database
            String[] projection = {
                    RestaurantDatabaseHelper.ID,
                    RestaurantDatabaseHelper.CUISINES,
                    RestaurantDatabaseHelper.NAME,
                    RestaurantDatabaseHelper.PRICERANGE,
                    RestaurantDatabaseHelper.AVGCOSTFORTWO,
                    RestaurantDatabaseHelper.RATINGTXT,
                    RestaurantDatabaseHelper.VOTES,
                    RestaurantDatabaseHelper.AVGRATING,
                    RestaurantDatabaseHelper.CURRENCY,
                    RestaurantDatabaseHelper.THUMB,
                    RestaurantDatabaseHelper.HASONLINEDELIVERY,
                    RestaurantDatabaseHelper.HASTABLEBOOKING,
                    RestaurantDatabaseHelper.ISDELIVERYWORKINGNOW,
                    RestaurantDatabaseHelper.ISFAV,
                    RestaurantDatabaseHelper.CITY
            };

            String selection = "(" + RestaurantDatabaseHelper.NAME + " like ? OR " + RestaurantDatabaseHelper.CUISINES + " like ?)";

            String[] selectionArgs;
            if(favOnly) {
                selection += "AND " + RestaurantDatabaseHelper.ISFAV + "= ?";
                 selectionArgs   = new String[]{"%" + query + "%", "%" + query + "%", "1"} ;
            } else {
                selectionArgs   = new String[]{"%" + query + "%", "%" + query + "%"} ;
            }

            // Sort order
            String sortOrder =
                    RestaurantDatabaseHelper.ID + " ASC";

            Cursor cursor = db.query(
                    RestaurantDatabaseHelper.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);

            ArrayList<Restaurant> restaurants = new ArrayList<>();
            while(cursor.moveToNext()){
                Restaurant temp = new Restaurant();
                UserRating tempRating = new UserRating();
                Location tempLoc = new Location();

                temp.setCuisines(cursor.getString(cursor.getColumnIndexOrThrow(RestaurantDatabaseHelper.CUISINES)));
                temp.setId(cursor.getInt(cursor.getColumnIndexOrThrow(RestaurantDatabaseHelper.ID)));
                temp.setName(cursor.getString(cursor.getColumnIndexOrThrow(RestaurantDatabaseHelper.NAME)));
                temp.setCurrency(cursor.getString(cursor.getColumnIndexOrThrow(RestaurantDatabaseHelper.CURRENCY)));
                temp.setPriceRange(cursor.getInt(cursor.getColumnIndexOrThrow(RestaurantDatabaseHelper.PRICERANGE)));
                temp.setAverageCostForTwo(cursor.getInt(cursor.getColumnIndexOrThrow(RestaurantDatabaseHelper.AVGCOSTFORTWO)));
                temp.setIsDeliveringNow(cursor.getInt(cursor.getColumnIndexOrThrow(RestaurantDatabaseHelper.ISDELIVERYWORKINGNOW)) == 1);
                temp.setHasOnlineDelivery(cursor.getInt(cursor.getColumnIndexOrThrow(RestaurantDatabaseHelper.HASONLINEDELIVERY)) == 1);
                temp.setHasTableBooking(cursor.getInt(cursor.getColumnIndexOrThrow(RestaurantDatabaseHelper.HASTABLEBOOKING)) == 1);
                temp.setThumb(cursor.getString(cursor.getColumnIndexOrThrow(RestaurantDatabaseHelper.THUMB)));
                tempRating.setRatingText(cursor.getString(cursor.getColumnIndexOrThrow(RestaurantDatabaseHelper.RATINGTXT)));
                tempRating.setAggregateRating(cursor.getString(cursor.getColumnIndexOrThrow(RestaurantDatabaseHelper.AVGRATING)));
                tempRating.setVotes(cursor.getString(cursor.getColumnIndexOrThrow(RestaurantDatabaseHelper.VOTES)));
                tempLoc.setCity(cursor.getString(cursor.getColumnIndexOrThrow(RestaurantDatabaseHelper.CITY)));

                temp.setLocation(tempLoc);
                temp.setUserRating(tempRating);
                restaurants.add(temp);
            }
            return restaurants;
        }


    /* All restaurants list
    @param String query
    @param String cuisine
    @param SQLiteDatabase readableDatabase
    @return Arraylist of all restaurant database entries
     */
    public static ArrayList<Restaurant> getAllRestaurants(String query, String cuisine, SQLiteDatabase db){


        // Define a projection that specifies which columns from the database
        String[] projection = {
                RestaurantDatabaseHelper.ID,
                RestaurantDatabaseHelper.CUISINES,
                RestaurantDatabaseHelper.NAME,
                RestaurantDatabaseHelper.PRICERANGE,
                RestaurantDatabaseHelper.AVGCOSTFORTWO,
                RestaurantDatabaseHelper.RATINGTXT,
                RestaurantDatabaseHelper.VOTES,
                RestaurantDatabaseHelper.AVGRATING,
                RestaurantDatabaseHelper.THUMB,
                RestaurantDatabaseHelper.HASONLINEDELIVERY,
                RestaurantDatabaseHelper.HASTABLEBOOKING,
                RestaurantDatabaseHelper.ISDELIVERYWORKINGNOW,
                RestaurantDatabaseHelper.ISFAV,
                RestaurantDatabaseHelper.CITY
        };

        String selection = RestaurantDatabaseHelper.NAME + " like ? AND " + RestaurantDatabaseHelper.CUISINES + " like ?";
        String[] selectionArgs = {"%" + query + "%", "%" + cuisine + "%"};

        // Sort order
        String sortOrder =
                RestaurantDatabaseHelper.ID + " ASC";

        Cursor cursor = db.query(
                RestaurantDatabaseHelper.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);

        ArrayList<Restaurant> restaurants = new ArrayList<>();
        while(cursor.moveToNext()){
            Restaurant temp = new Restaurant();
            UserRating tempRating = new UserRating();
            Location tempLoc = new Location();

            temp.setCuisines(cursor.getString(cursor.getColumnIndexOrThrow(RestaurantDatabaseHelper.CUISINES)));
            temp.setId(cursor.getInt(cursor.getColumnIndexOrThrow(RestaurantDatabaseHelper.ID)));
            temp.setName(cursor.getString(cursor.getColumnIndexOrThrow(RestaurantDatabaseHelper.NAME)));
            temp.setPriceRange(cursor.getInt(cursor.getColumnIndexOrThrow(RestaurantDatabaseHelper.PRICERANGE)));
            temp.setAverageCostForTwo(cursor.getInt(cursor.getColumnIndexOrThrow(RestaurantDatabaseHelper.AVGCOSTFORTWO)));
            temp.setIsDeliveringNow(cursor.getInt(cursor.getColumnIndexOrThrow(RestaurantDatabaseHelper.ISDELIVERYWORKINGNOW)) == 1);
            temp.setHasOnlineDelivery(cursor.getInt(cursor.getColumnIndexOrThrow(RestaurantDatabaseHelper.HASONLINEDELIVERY)) == 1);
            temp.setHasTableBooking(cursor.getInt(cursor.getColumnIndexOrThrow(RestaurantDatabaseHelper.HASTABLEBOOKING)) == 1);
            temp.setThumb(cursor.getString(cursor.getColumnIndexOrThrow(RestaurantDatabaseHelper.THUMB)));
            tempRating.setRatingText(cursor.getString(cursor.getColumnIndexOrThrow(RestaurantDatabaseHelper.RATINGTXT)));
            tempRating.setAggregateRating(cursor.getString(cursor.getColumnIndexOrThrow(RestaurantDatabaseHelper.AVGRATING)));
            tempRating.setVotes(cursor.getString(cursor.getColumnIndexOrThrow(RestaurantDatabaseHelper.VOTES)));
            tempLoc.setCity(cursor.getString(cursor.getColumnIndexOrThrow(RestaurantDatabaseHelper.CITY)));

            temp.setLocation(tempLoc);
            temp.setUserRating(tempRating);
            restaurants.add(temp);
        }
        return restaurants;
    }




}
