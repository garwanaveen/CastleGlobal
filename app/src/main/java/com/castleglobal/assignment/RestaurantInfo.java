package com.castleglobal.assignment;

import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.castleglobal.assignment.Objects.Restaurant;
import com.castleglobal.assignment.database.RestaurantDatabaseHelper;
import com.castleglobal.assignment.database.RestaurantDatabaseOperations;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.Locale;

public class RestaurantInfo extends AppCompatActivity {

    ImageView ftrdImage;
    Restaurant restaurant;
    SQLiteDatabase database;
    RatingBar ratingBar;
    TextView costForTwo, location, cuisines, votes, onlineOrder, onlineTableBooking;
    Button menu, events, photos, markFav;
    CollapsingToolbarLayout collapsingToolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_info);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        collapsingToolbar = (CollapsingToolbarLayout)    findViewById(R.id.toolbar_layout);


        ftrdImage = (ImageView) findViewById(R.id.ftrd_image);
        ratingBar = (RatingBar) findViewById(R.id.ratingBar);
        costForTwo = (TextView) findViewById(R.id.cost_for_two);
        location = (TextView) findViewById(R.id.location);
        cuisines = (TextView) findViewById(R.id.cuisines);
        onlineOrder = (TextView) findViewById(R.id.online_order_booking);
        onlineTableBooking = (TextView) findViewById(R.id.online_table_booking);
        votes = (TextView) findViewById(R.id.votes);
        menu = (Button) findViewById(R.id.menu);
        //events = (Button) findViewById(R.id.events);
        photos = (Button) findViewById(R.id.photos);
        markFav = (Button) findViewById(R.id.mark_fav);

        RestaurantDatabaseHelper dbHelper = new RestaurantDatabaseHelper(this);
        database = dbHelper.getWritableDatabase();
        restaurant = RestaurantDatabaseOperations.getRestaurantDetails(getIntent().getIntExtra("rest_id",308441),database);

        toolbar.setTitle(restaurant.getName());
        if(restaurant.getFeaturedImage() != null && !restaurant.getFeaturedImage().isEmpty()) {
            Picasso.with(this)
                    .load(restaurant.getFeaturedImage())
                    .into(ftrdImage);
        }

        if(restaurant.getHasOnlineDelivery()){
            onlineOrder.setText("Available");
        }

        if(restaurant.getHasTableBooking()){
            onlineTableBooking.setText("Available");
        }

        if(restaurant.getIsFav() != null && restaurant.getIsFav()){
            markFav.setText("Unmark Fav");
        } else {
            markFav.setText("Mark Fav");
        }

        collapsingToolbar.setTitle(restaurant.getName());

        ratingBar.setRating(Float.valueOf(restaurant.getUserRating().getAggregateRating()));
        votes.setText("(" + restaurant.getUserRating().getVotes() + " votes)");

        costForTwo.setText("Cost for 2: " + restaurant.getCurrency() + " "+ restaurant.getAverageCostForTwo());
        cuisines.setText(restaurant.getCuisines());

        location.setText(restaurant.getLocation().getLocality() + ", " + restaurant.getLocation().getCity());


        //mark restaurant as fav
        markFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean temp;
                if(restaurant.getIsFav() == null || !restaurant.getIsFav())
                    temp = true;
                else
                    temp = false;

                RestaurantDatabaseOperations.updateFav(restaurant.getId(), temp , database);

                restaurant.setIsFav(temp);

                if(restaurant.getIsFav() != null && restaurant.getIsFav()){
                    markFav.setText("Unmark Fav");
                } else {
                    markFav.setText("Mark Fav");
                }
            }
        });

        //open menu url in browser
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!restaurant.getMenuUrl().isEmpty()) {
                    try {
                        Intent i = new Intent("android.intent.action.MAIN");
                        i.setComponent(ComponentName.unflattenFromString("com.android.chrome/com.android.chrome.Main"));
                        i.addCategory("android.intent.category.LAUNCHER");
                        i.setData(Uri.parse(restaurant.getMenuUrl()));
                        startActivity(i);
                    } catch (ActivityNotFoundException e) {
                        // Chrome is not installed
                        Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(restaurant.getMenuUrl()));
                        startActivity(i);
                    }
                }else {
                    Snackbar.make(view, "No valid url found", Snackbar.LENGTH_LONG).show();
                }
            }
        });

        // open photos url in browser
        photos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!restaurant.getPhotosUrl().isEmpty()) {
                    try {
                        Intent i = new Intent("android.intent.action.MAIN");
                        i.setComponent(ComponentName.unflattenFromString("com.android.chrome/com.android.chrome.Main"));
                        i.addCategory("android.intent.category.LAUNCHER");
                        i.setData(Uri.parse(restaurant.getPhotosUrl()));
                        startActivity(i);
                    } catch (ActivityNotFoundException e) {
                        // Chrome is not installed
                        Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(restaurant.getPhotosUrl()));
                        startActivity(i);
                    }
                }else {
                    Snackbar.make(view, "No valid url found", Snackbar.LENGTH_LONG).show();
                }
            }
        });

        // open map with path from current location to restaurant location
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.directions);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String uri = "http://maps.google.com/maps?daddr=" + restaurant.getLocation().getLatitude() + "," +  restaurant.getLocation().getLongitude()
                        + "(" + restaurant.getName()+ ")";
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                intent.setPackage("com.google.android.apps.maps");
                try
                {
                    startActivity(intent);
                }
                catch(ActivityNotFoundException ex)
                {
                    Snackbar.make(view, "Please install a maps application", Snackbar.LENGTH_LONG).show();
                }
            }
        });
    }

    //close database to avoid leaks
    @Override
    protected void onDestroy() {
        database.close();
        super.onDestroy();
    }
}
