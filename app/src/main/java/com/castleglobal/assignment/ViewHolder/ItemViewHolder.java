package com.castleglobal.assignment.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.castleglobal.assignment.R;

/**
 * Created by naveenkumar on 26/09/17.
 */
public class ItemViewHolder extends RecyclerView.ViewHolder {
    public final View mView;
    public final TextView name;
    public final TextView cuisines;
    public final TextView location, avgPrice;
    public final ImageView thumb;
    public final View onlineOrder;
    public final View onlineTable;
    public final View open;
    public final Button menu;
    public final Button photos;
    public final Button events;

    public ItemViewHolder(View view) {
        super(view);
        mView = view;
        name = (TextView) view.findViewById(R.id.name);
        cuisines = (TextView) view.findViewById(R.id.cuisines);
        location = (TextView) view.findViewById(R.id.location);
        avgPrice = (TextView) view.findViewById(R.id.avg_cost);
        thumb = (ImageView) view.findViewById(R.id.thumb);
        onlineOrder = view.findViewById(R.id.online_order);
        open = view.findViewById(R.id.open);
        onlineTable =  view.findViewById(R.id.online_table);
        menu = (Button) view.findViewById(R.id.menu);
        photos = (Button) view.findViewById(R.id.photos);
        events = (Button) view.findViewById(R.id.events);

    }
}