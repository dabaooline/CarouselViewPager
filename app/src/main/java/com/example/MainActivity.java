package com.example;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.baoli.CarouselViewPager;
import com.baoli.CarouselViewPagerAdapter;
import com.example.test.R;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    public CarouselViewPager viewPager;
    private List<View> mViewList;
    private CarouselViewPagerAdapter carouselViewPagerAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewPager = (CarouselViewPager) findViewById(R.id.viewPager);
        mViewList = new ArrayList<>();



        carouselViewPagerAdapter = new CarouselViewPagerAdapter(mViewList);
        viewPager.setAdapter(carouselViewPagerAdapter);
        if (mViewList.size() == 0) {
            createViewForViewPager();
        }
        viewPager.startCarousel();
    }

    private void createViewForViewPager() {
        mViewList.clear();
        for (int i = 0; i < 4; i++) {
            TextView textView = new TextView(this);
            textView.setText("这是第" + i + "个ViewPager");
            textView.setTextSize(20);
            textView.setBackgroundColor(Color.BLUE);
            textView.setTextColor(Color.GRAY);
            textView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0));
            mViewList.add(textView);
        }
        carouselViewPagerAdapter.notifyDataSetChanged();


    }


}
