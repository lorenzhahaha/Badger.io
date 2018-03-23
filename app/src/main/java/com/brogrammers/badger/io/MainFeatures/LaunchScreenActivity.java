package com.brogrammers.badger.io.MainFeatures;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.brogrammers.badger.io.R;

public class LaunchScreenActivity extends AppCompatActivity {

    public ImageView appLogo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch_screen);

        //REGISTER ANIMATION
        appLogo = (ImageView) findViewById(R.id.appLogo);
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.animation);
        appLogo.startAnimation(animation);

        //CHECK IF ALREADY VISITED THE APP
        SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);
        boolean firstTime = prefs.getBoolean("firstTime", true);

        //DECISION INTENTS
        final Intent firstVisitIntent = new Intent(LaunchScreenActivity.this, OnboardingScreenActivity.class);
        final Intent nextVisitIntent = new Intent(LaunchScreenActivity.this, MainInterfaceActivity.class);

        if (firstTime) {
            Thread timer = new Thread() {
                @Override
                public void run() {
                    super.run();
                    try {
                        sleep(8000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } finally {
                        startActivity(firstVisitIntent);
                        finish();
                    }
                }
            };
            timer.start();
            SharedPreferences.Editor editor = prefs.edit();
            editor.putBoolean("firstTime", false);
            editor.apply();
        } else {
            Thread timer = new Thread() {
                @Override
                public void run() {
                    super.run();
                    try {
                        sleep(8000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } finally {
                        startActivity(nextVisitIntent);
                        finish();
                    }
                }
            };
            timer.start();
        }
    }
}
