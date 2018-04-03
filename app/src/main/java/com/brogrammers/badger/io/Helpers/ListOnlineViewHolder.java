package com.brogrammers.badger.io.Helpers;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.brogrammers.badger.io.Map.ItemClickListener;
import com.brogrammers.badger.io.R;

/**
 * Created by Lorenz-PC on 3/23/2018.
 */

public class ListOnlineViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    public TextView mEmailText;
    public LinearLayout mWhole;
    ItemClickListener itemClickListener;

    public ListOnlineViewHolder(View itemView) {
        super(itemView);
        mWhole =(LinearLayout)itemView.findViewById(R.id.whole);
        mEmailText = (TextView)itemView.findViewById(R.id.emailText);
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public void onClick(View v) {
        itemClickListener.onClick(v, getAdapterPosition());
    }
}
