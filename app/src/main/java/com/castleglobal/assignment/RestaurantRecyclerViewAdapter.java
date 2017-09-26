package com.castleglobal.assignment;

import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.castleglobal.assignment.Objects.RestaurantItemObject;
import com.squareup.picasso.Picasso;
import com.castleglobal.assignment.ViewHolder.*;

import java.util.ArrayList;


public class RestaurantRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private  ArrayList<RestaurantItemObject> mValues;
    private final RecyclerClickActivity mListener;


    // public constructor
    public RestaurantRecyclerViewAdapter(ArrayList<RestaurantItemObject> items, RecyclerClickActivity listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if(viewType == 0) {
            // Restaurant view
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.restaurant_item, parent, false);
            return new ItemViewHolder(view);
        }
        else {
            // Cuisine view
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.cuisine_item, parent, false);
            return new HeaderViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder,final int position) {
        RestaurantItemObject mItem = mValues.get(position);
        if(mItem.getRestaurant() != null) {
            //Updates values from datapoint as position to UI
            ((ItemViewHolder) holder).name.setText(mItem.getRestaurant().getName() + "(" + mItem.getRestaurant().getId() + ") \n"  + "(Rating: " + mItem.getRestaurant().getUserRating().getAggregateRating()+ ")") ;
            ((ItemViewHolder) holder).cuisines.setText(mItem.getRestaurant().getCuisines());
            ((ItemViewHolder) holder).location.setText(mItem.getRestaurant().getLocation().getLocality());
            ((ItemViewHolder) holder).avgPrice.setText("Cost for two: " + mItem.getRestaurant().getCurrency()+ mItem.getRestaurant().getAverageCostForTwo());
            ;
            if(!mItem.getRestaurant().getThumb().isEmpty())
                Picasso.with((Context)mListener)
                    .load(mItem.getRestaurant().getThumb())
                    .resize(600, 0)
                    .into(((ItemViewHolder) holder).thumb);
            if(mItem.getRestaurant().getHasTableBooking()){
                ((ItemViewHolder) holder).onlineTable.setVisibility(View.INVISIBLE);
            }

            if(mItem.getRestaurant().getHasOnlineDelivery()){
                ((ItemViewHolder) holder).onlineOrder.setVisibility(View.INVISIBLE);
            }

            if(mItem.getRestaurant().getIsDeliveringNow()){
                ((ItemViewHolder) holder).open.setVisibility(View.INVISIBLE);
            }

            if(mItem.getRestaurant().getIsFav() != null && mItem.getRestaurant().getIsFav()){
                ((ItemViewHolder) holder).events.setText("Unmark Fav");
            }

            ((ItemViewHolder) holder).open.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                        Snackbar.make(v, "Delivering working now", Snackbar.LENGTH_LONG).show();
                }
            });

            ((ItemViewHolder) holder).onlineOrder.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                        Snackbar.make(v, "Can place order online", Snackbar.LENGTH_LONG).show();
                }
            });

            ((ItemViewHolder) holder).onlineTable.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Snackbar.make(v, "Can book table online", Snackbar.LENGTH_LONG).show();
                }
            });

            //call back function to go to restaurant info
            ((ItemViewHolder) holder).mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (null != mListener) {
                        // Notify the active callbacks interface (the activity, if the
                        // fragment is attached to one) that an item has been selected.
                        mListener.onClickListItem(mValues.get(position).getRestaurant().getId());
                    }
                }
            });

            //open menu url in browser
            ((ItemViewHolder) holder).menu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!mValues.get(position).getRestaurant().getMenuUrl().isEmpty()) {
                        try {
                            Intent i = new Intent("android.intent.action.MAIN");
                            i.setComponent(ComponentName.unflattenFromString("com.android.chrome/com.android.chrome.Main"));
                            i.addCategory("android.intent.category.LAUNCHER");
                            i.setData(Uri.parse(mValues.get(position).getRestaurant().getMenuUrl()));
                            ((Context) mListener).startActivity(i);
                        } catch (ActivityNotFoundException e) {
                            // Chrome is not installed
                            Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(mValues.get(position).getRestaurant().getMenuUrl()));
                            ((Context) mListener).startActivity(i);
                        }
                    } else {
                        Snackbar.make(v, "No valid url found", Snackbar.LENGTH_LONG).show();
                    }
                }
            });

            //open photos url in browser
            ((ItemViewHolder) holder).photos.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!mValues.get(position).getRestaurant().getPhotosUrl().isEmpty()) {
                        try {
                            Intent i = new Intent("android.intent.action.MAIN");
                            i.setComponent(ComponentName.unflattenFromString("com.android.chrome/com.android.chrome.Main"));
                            i.addCategory("android.intent.category.LAUNCHER");
                            i.setData(Uri.parse(mValues.get(position).getRestaurant().getPhotosUrl()));
                            ((Context)mListener).startActivity(i);
                        } catch (ActivityNotFoundException e) {
                            // Chrome is not installed
                            Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(mValues.get(position).getRestaurant().getPhotosUrl()));
                            ((Context)mListener).startActivity(i);
                        }
                    }else {
                        Snackbar.make(v, "No valid url found", Snackbar.LENGTH_LONG).show();
                    }
                }
            });


            //open events url in browser
            ((ItemViewHolder) holder).events.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!mValues.get(position).getRestaurant().getEventsUrl().isEmpty()) {
                        try {
                            Intent i = new Intent("android.intent.action.MAIN");
                            i.setComponent(ComponentName.unflattenFromString("com.android.chrome/com.android.chrome.Main"));
                            i.addCategory("android.intent.category.LAUNCHER");
                            i.setData(Uri.parse(mValues.get(position).getRestaurant().getEventsUrl()));
                            ((Context)mListener).startActivity(i);
                        } catch (ActivityNotFoundException e) {
                            // Chrome is not installed
                            Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(mValues.get(position).getRestaurant().getEventsUrl()));
                            ((Context)mListener).startActivity(i);
                        }
                    }else {
                        Snackbar.make(v, "No valid url found", Snackbar.LENGTH_LONG).show();
                    }
                }
            });
        } else {
            ((HeaderViewHolder)holder).name.setText(mItem.getCuisine());
        }

    }

    @Override
    public int getItemCount() {
        if (mValues == null)
            return 0;
        return mValues.size();
    }

    //If RestaurantItemObject doesn't have restaurant then it is a headerView
    @Override
    public int getItemViewType(int position) {
        if (mValues.get(position).getRestaurant() == null)
            return 1;
        return 0;
    }

    // function to update dataset from activity
    public void updateAdapter(ArrayList<RestaurantItemObject> items) {
        if(mValues !=null)
            mValues.clear();
        mValues = items;
        this.notifyDataSetChanged();
    }



    //interface for item click call back
    public interface RecyclerClickActivity{
        public void onClickListItem(int id);
    }
}
