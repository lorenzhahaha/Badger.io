package com.brogrammers.emergency;

/**
 * Created by Lorenz-PC on 2/10/2018.
 */

import android.app.Application;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.OkHttpDownloader;
import com.squareup.picasso.Picasso;

public class Emergency extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        FirebaseDatabase.getInstance().setPersistenceEnabled(true);

//        Picasso.Builder builder = new Picasso.Builder(this);
//        builder.downloader(new OkHttpDownloader(this, Integer.MAX_VALUE));
//        Picasso built = builder.build();
//        built.setIndicatorsEnabled(false);
//        built.setLoggingEnabled(true);
//        Picasso.setSingletonInstance(built);
    }
}
