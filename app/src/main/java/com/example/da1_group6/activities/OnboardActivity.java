package com.example.da1_group6.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.da1_group6.R;
import com.example.da1_group6.adapter.Adapter_ViewPager;

import org.w3c.dom.Text;

import me.relex.circleindicator.CircleIndicator;

public class OnboardActivity extends AppCompatActivity {
    ViewPager pager;
    TextView tv_skip;
    ImageView btnnext;
    CircleIndicator circle;
    LinearLayout layout_showhint;
    Adapter_ViewPager adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onboard);

        pager = findViewById(R.id.pager_onboard);
        btnnext = findViewById(R.id.ic_next_onboard);
        tv_skip = findViewById(R.id.tv_skip);
        circle = findViewById(R.id.circle_ic);
        layout_showhint = findViewById(R.id.layout_show_hint);
        adapter = new Adapter_ViewPager(getSupportFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        pager.setAdapter(adapter);

        circle.setViewPager(pager);

        tv_skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pager.setCurrentItem(2);
            }
        });

        layout_showhint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(pager.getCurrentItem() < 2) {
                    pager.setCurrentItem(pager.getCurrentItem() + 1);
                }
            }
        });

        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if(position == 2) {
                    tv_skip.setVisibility(View.GONE);
                    layout_showhint.setVisibility(View.GONE);
                } else {
                    tv_skip.setVisibility(View.VISIBLE);
                    layout_showhint.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
}