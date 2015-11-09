package com.creatrix.ttb;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.creatrix.ttb.Fragme.HomeFragment;
import com.creatrix.ttb.Fragme.Main_cat_frag;
import com.creatrix.ttb.utils.Utils;

/**
 * Created by Harshu on 18-10-2015.
 */
public class DashBoard extends AppCompatActivity {


    private Fragment contentFragment;
    HomeFragment homeFragment;
    GridView gv_main_cat;
    Context ctx;
   // Main_Cat_Adapter adapter;
    LinearLayout linear_home, linear_directory, linear_seller, linear_favority, linear_setting;
    ImageView iv_home, iv_seller;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard);
        ctx = DashBoard.this;

        Utils.view_page= "HOME";
        Utils.init_viewstub(DashBoard.this);


        FragmentManager fragmentManager = getSupportFragmentManager();

        if (savedInstanceState != null) {
            if (savedInstanceState.containsKey("content")) {
                String content = savedInstanceState.getString("content");

            }
            if (fragmentManager.findFragmentByTag(HomeFragment.ARG_ITEM_ID) != null) {
                homeFragment = (HomeFragment) fragmentManager
                        .findFragmentByTag(HomeFragment.ARG_ITEM_ID);
                contentFragment = homeFragment;
            }
        } else {
            homeFragment = new HomeFragment();
            switchContent(homeFragment, HomeFragment.ARG_ITEM_ID);
        }


        Bundle bundle = new Bundle();
        bundle.putString("type", "OnCreate");

        FragmentManager fragmanager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmanager
                .beginTransaction();
        Main_cat_frag fragmentS1 = new Main_cat_frag();
        fragmentS1.setArguments(bundle);
        fragmentTransaction.replace(R.id.content_category, fragmentS1);
        fragmentTransaction.commit();


    }


    public void switchContent(Fragment fragment, String tag) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        while (fragmentManager.popBackStackImmediate())
            ;


        Bundle bundle = new Bundle();
        bundle.putString("type", "Dashboard");
        bundle.putInt("id", 0);

        if (fragment != null) {
            FragmentTransaction transaction = fragmentManager
                    .beginTransaction();
            transaction.replace(R.id.content_frame, fragment, tag);
            // Only ProductDetailFragment is added to the back stack.
            if (!(fragment instanceof HomeFragment)) {
                transaction.addToBackStack(tag);
            }
            transaction.commit();
            fragment.setArguments(bundle);
            contentFragment = fragment;
        }
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {

            if (Utils.parent_id.isEmpty() || Utils.parent_id == null || Utils.parent_id.size() == 1) {
                return super.onKeyDown(keyCode, event);

            } else {
                int parentsize = Utils.parent_id.size();
                int getval = Utils.parent_id.get(parentsize - 1);
                //  Toast.makeText(ctx,Utils.parent_id+" "+getval,Toast.LENGTH_LONG).show();
                Utils.parent_id.remove(parentsize - 1);

                //  Toast.makeText(ctx,Utils.parent_id+"",Toast.LENGTH_LONG).show();
                if (getval == 0) {


                    Bundle bundle = new Bundle();
                    bundle.putString("type", "OnCreate");
                    FragmentManager fragmanager = getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmanager
                            .beginTransaction();
                    Main_cat_frag fragmentS1 = new Main_cat_frag();
                    fragmentS1.setArguments(bundle);
                    fragmentTransaction.replace(R.id.content_category, fragmentS1);
                    fragmentTransaction.commit();


                    Bundle bundle1 = new Bundle();
                    bundle1.putString("type", "MainActivity");
                    bundle1.putInt("id", getval);

                    HomeFragment fragment1 = new HomeFragment();
                    FragmentManager fragmentManager1 = getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction1 = fragmentManager1.beginTransaction();
                    fragmentTransaction1.replace(R.id.content_frame, fragment1);
                    fragment1.setArguments(bundle);
                    fragmentTransaction1.commit();


                    return true;
                } else {


                    Bundle bundle = new Bundle();
                    bundle.putString("type", "OnBack");
                    bundle.putInt("id", getval);

                    FragmentManager fragmanager = getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmanager
                            .beginTransaction();
                    Main_cat_frag fragmentS1 = new Main_cat_frag();
                    fragmentS1.setArguments(bundle);
                    fragmentTransaction.replace(R.id.content_category, fragmentS1);
                    fragmentTransaction.commit();


                    Bundle bundle1 = new Bundle();
                    bundle1.putString("type", "Category");
                    bundle1.putInt("id", getval);

                    HomeFragment fragment1 = new HomeFragment();
                    FragmentManager fragmentManager1 = getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction1 = fragmentManager1.beginTransaction();
                    fragmentTransaction1.replace(R.id.content_frame, fragment1);
                    fragment1.setArguments(bundle);
                    fragmentTransaction1.commit();


                    return true;
                }

            }
        }

        return super.onKeyDown(keyCode, event);
    }

}
