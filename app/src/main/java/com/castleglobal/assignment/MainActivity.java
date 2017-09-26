package com.castleglobal.assignment;

import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.MenuInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.castleglobal.assignment.Objects.Restaurant;
import com.castleglobal.assignment.Objects.RestaurantItemObject;
import com.castleglobal.assignment.Utils.CallBackInterface;
import com.castleglobal.assignment.Utils.Constants;
import com.castleglobal.assignment.Utils.HTTPGetRequest;
import com.castleglobal.assignment.Utils.Parser;
import com.castleglobal.assignment.Utils.Utilities;
import com.castleglobal.assignment.database.RestaurantDatabaseHelper;
import com.castleglobal.assignment.database.RestaurantDatabaseOperations;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;

public class MainActivity extends AppCompatActivity implements CallBackInterface, RestaurantRecyclerViewAdapter.RecyclerClickActivity {

    ProgressDialog dialog;
    String endpoint, query = "";
    ArrayList<Restaurant> restaurants;
    SearchView searchView;
    SharedPreferences sp;
    SharedPreferences.Editor spEditor;
    TextView searchText;
    RecyclerView restaurantRecyclerView;
    RestaurantRecyclerViewAdapter adapter;
    ArrayList<RestaurantItemObject> restaurantItemObjectArrayList;

    Button favs, byPrice, byRating;
    ImageView favsCheck, priceCheck, ratingCheck;
    boolean favsOnly = false;
    int sortPrice= 0, sortRating = 0 ;

    SQLiteDatabase database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        searchText = (TextView) findViewById(R.id.search_text);
        restaurantRecyclerView = (RecyclerView) findViewById(R.id.restaurant_list);
        restaurantRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        favs = (Button) findViewById(R.id.favs);
        byPrice = (Button) findViewById(R.id.price);
        byRating = (Button) findViewById(R.id.rating);
        favsCheck = (ImageView) findViewById(R.id.check_favs);
        priceCheck = (ImageView) findViewById(R.id.check_price);
        ratingCheck = (ImageView) findViewById(R.id.check_rating);

        //writable database, heavy operation so referance for all usage
        database = (new RestaurantDatabaseHelper(this)).getWritableDatabase();

        //A common sharedpref
        sp =   getPreferences(Context. MODE_PRIVATE);
        spEditor = sp.edit();

        //progress dialog to stop user interaction while APIcall
        dialog = new ProgressDialog(this);
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setMessage("Searching, please wait...");
        dialog.setCancelable(false);




        //only fav restaurants are made visible (this is available only on offline database)
        favs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                favsOnly = !favsOnly;
                favsCheck.setVisibility(favsOnly ? View.VISIBLE : View.INVISIBLE);
                handleIntent(getIntent(),true);
            }
        });


        //Sort by price updating variable according state
        byPrice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sortRating = 0;
                ratingCheck.setVisibility(View.GONE);
               if(sortPrice == 0) {
                   sortPrice = 1;
                   priceCheck.setVisibility(View.VISIBLE);
                   priceCheck.setBackgroundResource(android.R.drawable.arrow_up_float);
               } else if (sortPrice == 1){
                   sortPrice = 2;
                   priceCheck.setBackgroundResource(android.R.drawable.arrow_down_float);
               } else {
                   sortPrice = 0;
                   priceCheck.setVisibility(View.GONE);
               }
                handleIntent(getIntent(),false);
            }
        });

        //Sort by Rating updating variable accordingly
        byRating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sortPrice = 0;
                priceCheck.setVisibility(View.GONE);
                if(sortRating == 0) {
                    sortRating = 1;
                    ratingCheck.setVisibility(View.VISIBLE);
                    ratingCheck.setBackgroundResource(android.R.drawable.arrow_up_float);
                } else if (sortRating == 1){
                    sortRating = 2;
                    ratingCheck.setBackgroundResource(android.R.drawable.arrow_down_float);
                } else {
                    sortRating = 0;
                    ratingCheck.setVisibility(View.GONE);
                }
                handleIntent(getIntent(),false);
            }
        });


        //set adapter from offline database
        restaurants = RestaurantDatabaseOperations.getAllRestaurants(database);
        restaurantItemObjectArrayList = getRestaurantDataList(restaurants);
        adapter = new RestaurantRecyclerViewAdapter((restaurantItemObjectArrayList),this);
        restaurantRecyclerView.setAdapter(adapter);

        searchText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchView.setIconified(false);
            }
        });

        handleIntent(getIntent(), true);
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void postExecute(String url, int status) {
        dialog.hide();

        updateRecyclerView();

    }

    @Override
    public void preExecute(String url) {
        dialog.show();
    }

    @Override
    public void progressUpdate(String url, int percentage) {
        // Use for long requests
        //dialog.incrementProgressBy(percentage-dialog.getProgress());
    }

    @Override
    public void processResponse(String url, String response) {
        restaurants = Parser.parseAndSaveRestuarantDate(response, database, spEditor);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView =
                (SearchView) menu.findItem(R.id.menu_search).getActionView();
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));
        searchView.setIconified(false);

        return true;
    }

    @Override
    protected void onNewIntent(Intent intent) {
        setIntent(intent);
        handleIntent(intent, true);
    }

    /* Loads data from database or API according the variables
        if fav are to be displayed then only from offline data
    Calls updaterecyclerVIew to update adapter
     */

    private void handleIntent(Intent intent, boolean dataReload) {

        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            query = intent.getStringExtra(SearchManager.QUERY);
        }

            if(dataReload || favsOnly) {
                if (Utilities.haveNetworkConnection(this) && !favsOnly && !query.isEmpty()) {
                    endpoint = Constants.BASE_URL + Constants.QUERY_URL + "?q=" + query;
                    HTTPGetRequest searchRequest = new HTTPGetRequest(this, endpoint, this);
                } else {
                    restaurants = RestaurantDatabaseOperations.getAllRestaurants(query, database, favsOnly);
                    updateRecyclerView();
                }
            }else {
                updateRecyclerView();
            }

    }


    //Handles click on restaurant card
    @Override
    public void onClickListItem(int id) {

               Intent toInfo = new Intent(this, RestaurantInfo.class);
               toInfo.putExtra("rest_id", id);
               startActivity(toInfo);


    }


    // Close data base to avoid memory leaks
    @Override
    protected void onDestroy() {
        database.close();
        super.onDestroy();
    }

    //Get final list in adRestaurantItemObject  datatype from list of restaurants
    public  ArrayList<RestaurantItemObject> getRestaurantDataList(ArrayList<Restaurant> restaurants){
        if(restaurants == null)
            return null;
        ArrayList<RestaurantItemObject> restaurantList = new ArrayList<>();
        HashSet<String> uniqueness = new HashSet<>();

        for(int i = 0; i< restaurants.size(); i++){
            if(!restaurants.get(i).getCuisines().isEmpty()) {
                String []data = restaurants.get(i).getCuisines().split(",");
                for(int j = 0; j< data.length ;j++ ) {
                    if(!uniqueness.contains(data[j].trim())) {
                        uniqueness.add(data[j].trim());
                        restaurantList.add(new RestaurantItemObject(data[j].trim(), null));
                    }
                    restaurantList.add(new RestaurantItemObject(data[j].trim(), restaurants.get(i)));
                }
            }

        }



        //return sorted list according to chosen option
        return sortList(restaurantList);

    }

    //Sort function based on chosen config
    private ArrayList<RestaurantItemObject> sortList(ArrayList<RestaurantItemObject> tempList){
        if(sortPrice == 1){

            Collections.sort(tempList, new Comparator<RestaurantItemObject>() {
                public int compare(RestaurantItemObject v1, RestaurantItemObject v2) {
                    if(v1.getCuisine().equalsIgnoreCase(v2.getCuisine()))
                        if(v1.getRestaurant() == null)
                            return 1;
                        else if(v2.getRestaurant() == null)
                            return 0;
                        else
                            return v1.getRestaurant().getAverageCostForTwo() - v2.getRestaurant().getAverageCostForTwo();
                    else
                        return v1.getCuisine().compareTo(v2.getCuisine());
                }
            });


        }else if(sortPrice == 2){

            Collections.sort(tempList, new Comparator<RestaurantItemObject>() {
                public int compare(RestaurantItemObject v1, RestaurantItemObject v2) {
                    if(v1.getCuisine().equalsIgnoreCase(v2.getCuisine()))
                        if(v1.getRestaurant() == null)
                            return 1;
                        else if(v2.getRestaurant() == null)
                            return 0;
                        else
                            return v2.getRestaurant().getAverageCostForTwo() - v1.getRestaurant().getAverageCostForTwo();
                    else
                        return v1.getCuisine().compareTo(v2.getCuisine());
                }
            });

        }else if(sortRating == 1){

            Collections.sort(tempList, new Comparator<RestaurantItemObject>() {
                public int compare(RestaurantItemObject v1, RestaurantItemObject v2) {
                    if(v1.getCuisine().equalsIgnoreCase(v2.getCuisine()))
                        if(v1.getRestaurant() == null)
                            return 1;
                        else if(v2.getRestaurant() == null)
                            return 0;
                        else
                            return v1.getRestaurant().getUserRating().getAggregateRating().compareTo(v2.getRestaurant().getUserRating().getAggregateRating());
                    else
                        return v1.getCuisine().compareTo(v2.getCuisine());
                }
            });

        }else if(sortRating == 2){

            Collections.sort(tempList, new Comparator<RestaurantItemObject>() {
                public int compare(RestaurantItemObject v1, RestaurantItemObject v2) {
                    if(v1.getCuisine().equalsIgnoreCase(v2.getCuisine()))
                        if(v1.getRestaurant() == null)
                            return 1;
                        else if(v2.getRestaurant() == null)
                            return 0;
                        else
                            return v2.getRestaurant().getUserRating().getAggregateRating().compareTo(v1.getRestaurant().getUserRating().getAggregateRating());
                    else
                        return v1.getCuisine().compareTo(v2.getCuisine());
                }
            });

        } else {
            Collections.sort(tempList, new Comparator<RestaurantItemObject>() {
                public int compare(RestaurantItemObject v1, RestaurantItemObject v2) {
                    if(v1.getCuisine() == v2.getCuisine())
                        return (v1.getRestaurant() == null ? 1 : 0);
                    else
                        return v1.getCuisine().compareTo(v2.getCuisine());
                }
            });
        }

        return tempList;
    }


    //updates data from adapter

    private void updateRecyclerView(){
        restaurantItemObjectArrayList = getRestaurantDataList(restaurants);
        if(restaurantItemObjectArrayList.size() > 0)
            searchText.setVisibility(View.GONE);
        else
            searchText.setVisibility(View.VISIBLE);

        adapter.updateAdapter(restaurantItemObjectArrayList);
    }

    //handle intent (updates adapter) in case of any attribute change in fav restaurants
    @Override
    protected void onResume() {
        handleIntent(getIntent(), false);
        super.onResume();
    }
}
