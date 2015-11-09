package com.creatrix.ttb;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.creatrix.ttb.adapter.SampleFragmentPagerAdapter;
import com.creatrix.ttb.utils.Utils;

/**
 * Created by Harshu on 28-10-2015.
 */
public class Seller_Detail extends AppCompatActivity {

    Context ctx;
    ViewPager viewPager;
    SampleFragmentPagerAdapter mAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.seller_detail);
        ctx=Seller_Detail.this;
        Utils.view_page= "SELLER";
        Utils.init_viewstub(Seller_Detail.this);

        final TabLayout tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);

        viewPager = (ViewPager) findViewById(R.id.pager_tab);


        mAdapter = new SampleFragmentPagerAdapter(getSupportFragmentManager(),ctx);

        viewPager.setAdapter(mAdapter);

        tabLayout.setupWithViewPager(viewPager);

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                // on changing the page
                // make respected tab selected


                mAdapter.getItem(position);


            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
            }

            @Override
            public void onPageScrollStateChanged(int arg0) {
            }
        });



    }
}
