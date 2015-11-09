package com.creatrix.ttb.adapter;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.creatrix.ttb.Fragme.Add_Product;

import java.util.ArrayList;

/**
 * Created by AA on 10/19/2015.
 */
public class SampleFragmentPagerAdapter extends FragmentPagerAdapter {
   // final int PAGE_COUNT = 5;
    private String tabTitles[] = new String[]{"Add Product", "My Product", "Expire Product", "Setting"};
    private Context context;



    public SampleFragmentPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }

    @Override
    public int getCount() {
        return tabTitles.length;
    }

    @Override
    public Fragment getItem(int position) {

        Fragment fm=new Add_Product();

        Bundle bundle=new Bundle();

        fm.setArguments(bundle);

        return fm;
       // return PageFragment.newInstance(position + 1);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position
        return tabTitles[position];
    }
}