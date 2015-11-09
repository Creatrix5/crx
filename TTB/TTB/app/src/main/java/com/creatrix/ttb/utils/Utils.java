package com.creatrix.ttb.utils;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.view.ViewStub;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import com.creatrix.ttb.DashBoard;
import com.creatrix.ttb.Directory;
import com.creatrix.ttb.Favority;

import com.creatrix.ttb.app.AppController;
import com.creatrix.ttb.R;
import com.creatrix.ttb.Seller_Detail;
import com.creatrix.ttb.Setting;

/**
 * Created by Harshu on 14-10-2015.
 */
public class Utils {

    public static String main_url = "http://www.vintechdreamlab.com/demo/ttb/protected/";

    /*===================================================================================
    ===========================Registration Field=========================================
    ======================================================================================*/
    public static String userid = "userid";
    public static String firstname = "firstname";
    public static String lastname = "lastname";
    public static String username = "username";
    public static String password = "password";
    public static String gender = "gender";
    public static String email = "email";
    public static String company = "company";
    public static String shopno = "shopno";
    public static String phase = "phase";
    public static String flore = "flore";
    public static String marketname = "marketname";
    public static String road = "road";
    public static String landmark = "landmark";
    public static String area = "area";
    public static String city = "city";
    public static String district = "district";
    public static String state = "state";
    public static String pincode = "pincode";
    public static String latitude = "latitude";
    public static String longitude = "longitude";
    public static String businesstype = "businesstype";
    public static String product = "product";
    public static String designation = "designation";
    public static String contactno = "contactno";
    public static String mobileno = "mobileno";
    public static String officeno = "officeno";

     /*===================================================================================
      =========================Language SetUp========================================
      ===================================================================================*/

    public static String Language = "E";

    /*===================================================================================
     =========================Check Login========================================
     ===================================================================================*/
    public static String Check_login = "check_login";

    /*===================================================================================
      =========================Main Fragment Back========================================
      ===================================================================================*/

    //    public static int parentId;
    public static ArrayList<Integer> parent_id = new ArrayList<Integer>();



    /*===================================================================================
      =========================SharedPreferences Method==================================
      ===================================================================================*/


    public static void setPref(Context c, String pref, String val) {
        SharedPreferences.Editor e = PreferenceManager.getDefaultSharedPreferences(c).edit();
        e.putString(pref, val);
        e.commit();

    }

    public static String getPref(Context c, String pref, String val) {
        return PreferenceManager.getDefaultSharedPreferences(c).getString(pref,
                val);
    }

    public static void setPref(Context c, String pref, boolean val) {
        SharedPreferences.Editor e = PreferenceManager.getDefaultSharedPreferences(c).edit();
        e.putBoolean(pref, val);
        e.commit();

    }

    public static boolean getPref(Context c, String pref, boolean val) {
        return PreferenceManager.getDefaultSharedPreferences(c).getBoolean(
                pref, val);
    }


    public static void setPref(Context c, String pref, int val) {
        SharedPreferences.Editor e = PreferenceManager.getDefaultSharedPreferences(c).edit();
        e.putInt(pref, val);
        e.commit();

    }

    public static int getPref(Context c, String pref, int val) {
        return PreferenceManager.getDefaultSharedPreferences(c).getInt(pref,
                val);
    }
   public static ImageView iv_home,iv_seller,iv_directory,iv_favority,iv_setting;
    public static TextView tv_home,tv_seller,tv_directory,tv_favority,tv_setting;
    public static String view_page = "HOME";

    public static void init_viewstub(final Activity ctx) {


        ViewStub stub = (ViewStub) ctx.findViewById(R.id.layout_stub);
        stub.setLayoutResource(R.layout.bottom_layout);
        View inflated = stub.inflate();

        final LinearLayout linear_home = (LinearLayout) inflated.findViewById(R.id.linear_home);
        iv_home = (ImageView) inflated.findViewById(R.id.iv_home);
        tv_home = (TextView) inflated.findViewById(R.id.tv_home);

        final LinearLayout linear_seller = (LinearLayout) inflated.findViewById(R.id.linear_seller);
        iv_seller = (ImageView) inflated.findViewById(R.id.iv_seller);
        tv_seller = (TextView) inflated.findViewById(R.id.tv_seller);

        final LinearLayout linear_directory = (LinearLayout) inflated.findViewById(R.id.linear_directory);
        iv_directory = (ImageView) inflated.findViewById(R.id.iv_directory);
        tv_directory = (TextView) inflated.findViewById(R.id.tv_directory);

        final LinearLayout linear_favority = (LinearLayout) inflated.findViewById(R.id.linear_favority);
        iv_favority = (ImageView) inflated.findViewById(R.id.iv_favority);
        tv_favority = (TextView) inflated.findViewById(R.id.tv_favourity);

        final LinearLayout linear_setting = (LinearLayout) inflated.findViewById(R.id.linear_setting);
        iv_setting = (ImageView) inflated.findViewById(R.id.iv_setting);
        tv_setting = (TextView) inflated.findViewById(R.id.tv_setting);

        SetButtonBg();
        linear_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //  iv_home.setImageDrawable();
                if (!view_page.equals("HOME"))
                    ctx.startActivity(new Intent(ctx, DashBoard.class));





            }
        });

        linear_directory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!view_page.equals("DIRECTORY"))
                ctx.startActivity(new Intent(ctx, Directory.class));

            }
        });


        linear_favority.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!view_page.equals("FAVORIRE"))
                ctx.startActivity(new Intent(ctx, Favority.class));

            }
        });


        linear_seller.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!view_page.equals("SELLER"))
                ctx.startActivity(new Intent(ctx, Seller_Detail.class));

            }
        });

        linear_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!view_page.equals("SETTING"))
                ctx.startActivity(new Intent(ctx, Setting.class));

            }
        });


    }


    public static void SetButtonBg(){

        switch (view_page) {

            case "HOME":

                iv_home.setBackgroundResource(R.drawable.ic_home);
                tv_home.setTextColor(Color.parseColor("#0099cc"));

                iv_directory.setBackgroundResource(R.drawable.ic_directory_un);
                tv_directory.setTextColor(Color.parseColor("#939598"));

                iv_seller.setBackgroundResource(R.drawable.ic_seller_un);
                tv_seller.setTextColor(Color.parseColor("#939598"));

                iv_favority.setBackgroundResource(R.drawable.ic_favorite_un);
                tv_favority.setTextColor(Color.parseColor("#939598"));

                iv_setting.setBackgroundResource(R.drawable.ic_setting_un);
                tv_setting.setTextColor(Color.parseColor("#939598"));

                break;

            case "DIRECTORY":

                iv_home.setBackgroundResource(R.drawable.ic_home_un);
                tv_home.setTextColor(Color.parseColor("#939598"));

                iv_directory.setBackgroundResource(R.drawable.ic_directory);
                tv_directory.setTextColor(Color.parseColor("#0099cc"));

                iv_seller.setBackgroundResource(R.drawable.ic_seller_un);
                tv_seller.setTextColor(Color.parseColor("#939598"));

                iv_favority.setBackgroundResource(R.drawable.ic_favorite_un);
                tv_favority.setTextColor(Color.parseColor("#939598"));

                iv_setting.setBackgroundResource(R.drawable.ic_setting_un);
                tv_setting.setTextColor(Color.parseColor("#939598"));

                break;

            case "FAVORIRE":

                iv_home.setBackgroundResource(R.drawable.ic_home_un);
                tv_home.setTextColor(Color.parseColor("#939598"));

                iv_directory.setBackgroundResource(R.drawable.ic_directory_un);
                tv_directory.setTextColor(Color.parseColor("#939598"));

                iv_seller.setBackgroundResource(R.drawable.ic_seller_un);
                tv_seller.setTextColor(Color.parseColor("#939598"));

                iv_favority.setBackgroundResource(R.drawable.ic_favorite);
                tv_favority.setTextColor(Color.parseColor("#0099cc"));

                iv_setting.setBackgroundResource(R.drawable.ic_setting_un);
                tv_setting.setTextColor(Color.parseColor("#939598"));

                break;

            case "SELLER":

                iv_home.setBackgroundResource(R.drawable.ic_home_un);
                tv_home.setTextColor(Color.parseColor("#939598"));

                iv_directory.setBackgroundResource(R.drawable.ic_directory_un);
                tv_directory.setTextColor(Color.parseColor("#939598"));

                iv_seller.setBackgroundResource(R.drawable.ic_seller);
                tv_seller.setTextColor(Color.parseColor("#0099cc"));

                iv_favority.setBackgroundResource(R.drawable.ic_favorite_un);
                tv_favority.setTextColor(Color.parseColor("#939598"));

                iv_setting.setBackgroundResource(R.drawable.ic_setting_un);
                tv_setting.setTextColor(Color.parseColor("#939598"));

                break;

            case "SETTING":

                iv_home.setBackgroundResource(R.drawable.ic_home_un);
                tv_home.setTextColor(Color.parseColor("#939598"));

                iv_directory.setBackgroundResource(R.drawable.ic_directory_un);
                tv_directory.setTextColor(Color.parseColor("#939598"));

                iv_seller.setBackgroundResource(R.drawable.ic_seller_un);

                tv_seller.setTextColor(Color.parseColor("#939598"));

                iv_favority.setBackgroundResource(R.drawable.ic_favorite_un);

                tv_favority.setTextColor(Color.parseColor("#939598"));

                iv_setting.setBackgroundResource(R.drawable.ic_setting);

                tv_setting.setTextColor(Color.parseColor("#0099cc"));

                break;
            default:

                iv_home.setBackgroundResource(R.drawable.ic_home);
                tv_home.setTextColor(Color.parseColor("#0099cc"));

                iv_directory.setBackgroundResource(R.drawable.ic_directory_un);
                tv_directory.setTextColor(Color.parseColor("#939598"));

                iv_seller.setBackgroundResource(R.drawable.ic_seller_un);
                tv_seller.setTextColor(Color.parseColor("#939598"));

                iv_favority.setBackgroundResource(R.drawable.ic_favorite_un);
                tv_favority.setTextColor(Color.parseColor("#939598"));

                iv_setting.setBackgroundResource(R.drawable.ic_setting_un);
                tv_setting.setTextColor(Color.parseColor("#939598"));
                break;
        }
    }




}

