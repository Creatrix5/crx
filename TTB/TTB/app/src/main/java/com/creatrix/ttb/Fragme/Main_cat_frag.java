package com.creatrix.ttb.Fragme;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import com.creatrix.ttb.Category_Product;
import com.creatrix.ttb.Obj.CatItem;
import com.creatrix.ttb.Obj.SubCatItem;

import com.creatrix.ttb.adapter.Main_Cat_Adapter;
import com.creatrix.ttb.R;
import com.creatrix.ttb.adapter.Sub_Cat_Adapter;
import com.creatrix.ttb.app.AppController;
import com.creatrix.ttb.app.Grid_Helper;
import com.creatrix.ttb.app.Helper;
import com.creatrix.ttb.utils.Utils;

/**
 * Created by Harshu on 19-10-2015.
 */
public class Main_cat_frag extends Fragment {


    GridView gv_main_cat;
    Context ctx;
    Main_Cat_Adapter adapter;
    Sub_Cat_Adapter sub_adapter;
    String sub_url;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main_cat, container, false);
        ctx = getActivity();
        gv_main_cat = (GridView) view.findViewById(R.id.gv_main_cat);

        String type = getArguments().getString("type");

        if (type.equals("OnCreate")) {
            LoadMainCat();
        } else {
            int id = getArguments().getInt("id", 0);
            sub_url = Utils.main_url + "getsubcategories.php?id=" + id;

            Toast.makeText(ctx, "Back Url " + sub_url, Toast.LENGTH_LONG).show();

            LoadSubCat();
        }


        gv_main_cat.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                if (sub_Cat.isEmpty() || sub_Cat == null) {
                    //Toast.makeText(ctx,cat_id+" cat id",Toast.LENGTH_LONG).show();
                    Utils.view_page= "fd";
                    CatItem catitem = cat_list.get(position);

                    int cat_id = catitem.getId();

                   // Toast.makeText(ctx,"first main"+cat_id+" "+Utils.parent_id,Toast.LENGTH_LONG).show();
                    if (Utils.parent_id.contains(cat_id)) {
                    } else {
                        Utils.parent_id.add(catitem.getParent_id());
                    }

                  //  Toast.makeText(ctx,"Second main"+cat_id+" "+Utils.parent_id,Toast.LENGTH_LONG).show();

                    sub_url = Utils.main_url + "getsubcategories.php?id=" + cat_id;

                    Bundle bundle = new Bundle();
                    bundle.putString("type", "Category");
                    bundle.putInt("id", cat_id);

                    HomeFragment fragment2 = new HomeFragment();
                    FragmentManager fragmentManager = getFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.content_frame, fragment2);
                    fragment2.setArguments(bundle);
                    fragmentTransaction.commit();


                    LoadSubCat();
                } else {

                    SubCatItem subitem = sub_Cat.get(position);
                    String lastchild = subitem.getLastchild();
                    int cat_id = subitem.getId();
                    //   Utils.parentId=subitem.getParent_id();
                   // Toast.makeText(ctx,"first sub"+cat_id+" "+Utils.parent_id+" "+subitem.getParent_id(),Toast.LENGTH_LONG).show();
                    if (Utils.parent_id.contains(cat_id)) {
                    } else {
                        Utils.parent_id.add(subitem.getParent_id());
                    }

                  //  Toast.makeText(ctx,"Second sub"+cat_id+" "+Utils.parent_id,Toast.LENGTH_LONG).show();

                    //  Utils.parentId=cat_id;
                    if (lastchild.equals("false")) {
                        sub_url = Utils.main_url + "getsubcategories.php?id=" + cat_id;

                        LoadSubCat();

                        Bundle bundle = new Bundle();
                        bundle.putString("type", "Category");
                        bundle.putInt("id", cat_id);

                        HomeFragment fragment2 = new HomeFragment();
                        FragmentManager fragmentManager = getFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.content_frame, fragment2);
                        fragment2.setArguments(bundle);
                        fragmentTransaction.commit();



                    } else {

                        Intent ii = new Intent(ctx, Category_Product.class);
                        ii.putExtra("id", cat_id);
                        ctx.startActivity(ii);
                        //       ctx.startActivity(new Intent(ctx, Category_Product.class));
                        //Toast.makeText(ctx, "Coming Soon", Toast.LENGTH_LONG).show();
                    }
                }

                //  Toast.makeText(ctx,Utils.parent_id+"",Toast.LENGTH_LONG).show();


            }
        });
        return view;
    }

    ProgressDialog pDialog;
    ArrayList<CatItem> cat_list;
    String url = Utils.main_url + "homecategory.php";
    ;

    /**
     * Method to make json object request where json response starts wtih {
     */
    private void LoadMainCat() {

        pDialog = new ProgressDialog(ctx);
        pDialog.setMessage("Please wait...");
        pDialog.setCancelable(false);

        showpDialog();


        cat_list = new ArrayList<CatItem>();

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                url, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Log.d("Responce ", response.toString());

                try {
                    // Parsing json object response
                    // response will be a json object

                    JSONArray jsonArray = response.getJSONArray("category");
                    for (int j = 0; j < jsonArray.length(); j++) {


                        JSONObject catobj = (JSONObject) jsonArray
                                .get(j);
                        parentid = catobj.getInt("parentid");
                        CatItem catItem = new CatItem();
                        catItem.setId(catobj.getInt("categoryid"));
                        catItem.setName(catobj.getString("categoryname"));
                        catItem.setParent_id(parentid);
                        catItem.setImage(Utils.main_url + catobj.getString("categoryimage"));
                        // Utils.parentId = parentid;
                        // Toast.makeText(ctx,"Load "+parentid,Toast.LENGTH_LONG).show();
                        cat_list.add(catItem);


                    }
                    adapter = new Main_Cat_Adapter(ctx, cat_list);
                    gv_main_cat.setAdapter(adapter);
                    Grid_Helper.getListViewSize(gv_main_cat);

                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(ctx,
                            "Error: " + e.getMessage(),
                            Toast.LENGTH_LONG).show();
                }
                hidepDialog();
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("", "Error: " + error.getMessage());
                Toast.makeText(ctx,
                        error.getMessage(), Toast.LENGTH_SHORT).show();
                // hide the progress dialog
                hidepDialog();
            }
        });

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(jsonObjReq);
    }


    /**
     * Method to make json object request where json response starts wtih {
     */
    int parentid;
    ArrayList<SubCatItem> sub_Cat = new ArrayList<SubCatItem>();

    private void LoadSubCat() {

        pDialog = new ProgressDialog(ctx);
        pDialog.setMessage("Please wait...");
        pDialog.setCancelable(false);

        //showpDialog();


        sub_Cat = new ArrayList<SubCatItem>();

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                sub_url, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Log.d("Responce ", response.toString());

                try {
                    // Parsing json object response
                    // response will be a json object

                    JSONArray jsonArray = response.getJSONArray("categories");
                    for (int j = 0; j < jsonArray.length(); j++) {


                        JSONObject catobj = (JSONObject) jsonArray
                                .get(j);
                        parentid = catobj.getInt("parentid");
                        SubCatItem catItem = new SubCatItem();
                        catItem.setId(catobj.getInt("categoryid"));
                        catItem.setName(catobj.getString("categoryname"));
                        catItem.setParent_id(parentid);
                        catItem.setImage(Utils.main_url + catobj.getString("categoryimage"));
                        catItem.setLastchild(catobj.getString("lastchild"));
                        sub_Cat.add(catItem);
                     /* Utils.parentId = catobj.getInt("parentid");
                        Toast.makeText(ctx,"Load "+parentid,Toast.LENGTH_LONG).show();
*/
                    }
                    sub_adapter = new Sub_Cat_Adapter(ctx, sub_Cat);
                    gv_main_cat.setAdapter(sub_adapter);
                    Grid_Helper.getListViewSize(gv_main_cat);



                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(ctx,
                            "Error: " + e.getMessage(),
                            Toast.LENGTH_LONG).show();
                }
                hidepDialog();
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("", "Error: " + error.getMessage());
                Toast.makeText(ctx,
                        error.getMessage(), Toast.LENGTH_SHORT).show();
                // hide the progress dialog
                hidepDialog();
            }
        });

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(jsonObjReq);
    }


    private void showpDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hidepDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }


}
