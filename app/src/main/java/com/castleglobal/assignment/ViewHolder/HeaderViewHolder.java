package com.castleglobal.assignment.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.castleglobal.assignment.R;

/**
 * Created by naveenkumar on 26/09/17.
 */

public class HeaderViewHolder extends RecyclerView.ViewHolder {
    public final View mView;
    public final TextView name;
    public HeaderViewHolder(View view) {
        super(view);
        mView = view;
        name = (TextView) view.findViewById(R.id.cuisine_name);

    }
}

