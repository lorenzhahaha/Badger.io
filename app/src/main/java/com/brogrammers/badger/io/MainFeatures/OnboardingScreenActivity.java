package com.brogrammers.badger.io.MainFeatures;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.brogrammers.badger.io.Authentication.RegisterActivity;
import com.brogrammers.badger.io.R;
import com.brogrammers.badger.io.Helpers.SliderAdapter;

public class OnboardingScreenActivity extends AppCompatActivity {

    private ViewPager mSlideViewPager;
    private LinearLayout mDotLayout;

    private TextView[] mDots;

    private SliderAdapter sliderAdapter;

    private Button mPrevBtn;
    private Button mNextBtn;
    private Button mSetupBtn;
    private int mCurrentPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onboarding_screen);

        mSlideViewPager = (ViewPager) findViewById(R.id.slideViewPager);
        mDotLayout = (LinearLayout) findViewById(R.id.dotsLayout);

        mPrevBtn = (Button) findViewById(R.id.prevBtn);
        mNextBtn = (Button) findViewById(R.id.nextBtn);
        mSetupBtn = (Button) findViewById(R.id.setupBtn);

        sliderAdapter = new SliderAdapter(this);
        mSlideViewPager.setAdapter(sliderAdapter);
        addDotsIndicator(0);
        mSlideViewPager.addOnPageChangeListener(viewListener);

        mNextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSlideViewPager.setCurrentItem(mCurrentPage + 1);
            }
        });
        mPrevBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSlideViewPager.setCurrentItem(mCurrentPage - 1);
            }
        });
    }

    public void addDotsIndicator(int position) {
        mDots = new TextView[4];
        mDotLayout.removeAllViews();

        for(int i=0; i<mDots.length; i++) {
            mDots[i] = new TextView(this);
            mDots[i].setText(Html.fromHtml("&#8226;"));
            mDots[i].setTextSize(35);
            mDots[i].setTextColor(getResources().getColor(R.color.colorDark));
            mDotLayout.addView(mDots[i]);
        }

        if(mDots.length > 0) {
            mDots[position].setTextColor(getResources().getColor(R.color.colorYellow));
        }
    }

    ViewPager.OnPageChangeListener viewListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            addDotsIndicator(position);
            mCurrentPage = position;

            if (position == 0) {
                mNextBtn.setEnabled(true);
                mPrevBtn.setEnabled(false);
                mSetupBtn.setEnabled(false);
                mPrevBtn.setVisibility(View.INVISIBLE);
                mSetupBtn.setVisibility(View.INVISIBLE);
                mNextBtn.setText("NEXT");
                mPrevBtn.setText("");
                mSetupBtn.setText("");
                mDots[position].setTextColor(getResources().getColor(R.color.colorYellow));
            } else if (position == 3) {
                mNextBtn.setEnabled(false);
                mPrevBtn.setEnabled(true);
                mSetupBtn.setEnabled(true);
                mPrevBtn.setVisibility(View.VISIBLE);
                mNextBtn.setVisibility(View.INVISIBLE);
                mSetupBtn.setVisibility(View.VISIBLE);
                mSetupBtn.setText("FINISH");
                mPrevBtn.setText("BACK");
                mNextBtn.setText("");
                mDots[position].setTextColor(getResources().getColor(R.color.colorYellow));
                mSetupBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(OnboardingScreenActivity.this, RegisterActivity.class));
                    }
                });
            } else {
                mNextBtn.setEnabled(true);
                mPrevBtn.setEnabled(true);
                mSetupBtn.setEnabled(false);
                mPrevBtn.setVisibility(View.VISIBLE);
                mNextBtn.setVisibility(View.VISIBLE);
                mSetupBtn.setVisibility(View.INVISIBLE);
                mNextBtn.setText("NEXT");
                mPrevBtn.setText("BACK");
                mSetupBtn.setText("");
                if (position == 1) {
                    mDots[position].setTextColor(getResources().getColor(R.color.colorRed));
                } else if (position == 2) {
                    mDots[position].setTextColor(getResources().getColor(R.color.colorBlue));
                }
            }

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };
}
