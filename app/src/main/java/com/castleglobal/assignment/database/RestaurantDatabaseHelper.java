package com.castleglobal.assignment.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.castleglobal.assignment.Objects.Location;
import com.castleglobal.assignment.Objects.Restaurant;
import com.castleglobal.assignment.Objects.UserRating;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by naveenkumar on 24/09/17.
 */


public class RestaurantDatabaseHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Restaurant.db";
    public static final String TABLE_NAME = "alldata";

    public static final String ID = "ID";
    public static final String NAME = "NAME";
    public static final String URL = "URL";
    public static final String ADDRESS = "ADDRESS";
    public static final String LOCALITY = "LOCALITY";
    public static final String CITY = "CITY";
    public static final String LATITUDE = "LATITUDE";
    public static final String LONGITUDE = "LONGITUDE";
    public static final String ZIPCODE = "ZIPCODE";
    public static final String CUISINES = "CUISINES";
    public static final String AVGCOSTFORTWO = "AVGCOSTFORTWO" ;
    public static final String PRICERANGE = "PRICERANGE" ;
    public static final String CURRENCY = "CURRENCY";
    public static final String THUMB = "THUMB";
    public static final String AVGRATING = "AVGRATING";
    public static final String RATINGTXT = "RATINGTXT";
    public static final String RATINGCOLOR = "RATINGCOLOR";
    public static final String VOTES = "VOTES";
    public static final String PHOTOSURL = "PHOTOSURL";
    public static final String MENUURL = "MENUURL";
    public static final String FEATUREDIMAGE = "FEATUREDIMAGE";
    public static final String HASONLINEDELIVERY = "HASONLINEDELIVERY";
    public static final String ISDELIVERYWORKINGNOW = "ISDELIVERYWORKINGNOW";
    public static final String DEEPLINK = "DEEPLINK";
    public static final String ORDERURL = "ORDERURL";
    public static final String ORDERDEEPLINK = "ORDERDEEPLINK";
    public static final String HASTABLEBOOKING = "HASTABLEBOOKING";
    public static final String EVENTSURL = "EVENTSURL";
    public static final String ISFAV = "FAV";


    private final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    ID + " INTEGER PRIMARY KEY," + NAME + " TEXT," + URL + " TEXT," +  ADDRESS + " TEXT,"
                    + LOCALITY + " TEXT," + CITY + " TEXT," + LATITUDE + " DOUBLE," + LONGITUDE + " DOUBLE," + ZIPCODE + " TEXT,"
                    + CUISINES + " TEXT," + AVGCOSTFORTWO + " INTEGER," + PRICERANGE + " INTEGER," + CURRENCY + " TEXT," + THUMB + " TEXT,"
                    + AVGRATING + " FLOAT," + RATINGTXT + " TEXT," + RATINGCOLOR + " TEXT," + VOTES + " INTEGER," + PHOTOSURL + " TEXT,"
                    + MENUURL + " TEXT," + FEATUREDIMAGE + " TEXT," + HASONLINEDELIVERY + " BOOLEAN," + ISDELIVERYWORKINGNOW + " BOOLEAN,"
                    + DEEPLINK + " TEXT," + ORDERURL + " TEXT," + ORDERDEEPLINK + " TEXT," + HASTABLEBOOKING + " BOOLEAN," + EVENTSURL
                    + " TEXT," + ISFAV +" BOOLEAN )";


    public RestaurantDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }


}