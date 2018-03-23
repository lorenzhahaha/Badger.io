package com.brogrammers.badger.io.Helpers;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.brogrammers.badger.io.R;

/**
 * Created by Lorenz-PC on 3/16/2018.
 */

public class SliderAdapter extends PagerAdapter {

    Context context;
    LayoutInflater layoutInflater;

    public SliderAdapter(Context context) {
        this.context = context;
    }

    //Arrays
    public int[] slide_images = {
            R.drawable.home,
            R.drawable.news,
            R.drawable.notification,
            R.drawable.safe
    };

    public String[] slide_headings = {
            "SEND LOCATION",
            "LATEST NEWS",
            "REPORT INCIDENTS",
            "SAFE LIFE"
    };

    public String[] slide_descs = {
            "GPS-based application which helps you send your current location for different purposes. This feature is essential if you want to send location to someone else.",
            "Be updated from the hottest news all around the globe. From trusted primary sources, all compiled in this app! Happy Reading!",
            "Report emergencies to trusted receivers so they can respond. This feature allows you to ask for a help in case of bad situations occur.",
            "Have a better life and security because of this application. Learn self-defense and refrain from bad happenings in life. Isn't wonderful? Register now!"
    };

    @Override
    public int getCount() {
        return slide_headings.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == (RelativeLayout) object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.slide_layout, container, false);

        ImageView slideImageView = (ImageView) view.findViewById(R.id.slide_image);
        TextView slideHeading = (TextView) view.findViewById(R.id.slide_heading);
        TextView slideDesc = (TextView) view.findViewById(R.id.slide_desc);

        slideImageView.setImageResource(slide_images[position]);
        slideHeading.setText(slide_headings[position]);
        slideDesc.setText(slide_descs[position]);

        container.addView(view);

        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((RelativeLayout)object);
    }
}
