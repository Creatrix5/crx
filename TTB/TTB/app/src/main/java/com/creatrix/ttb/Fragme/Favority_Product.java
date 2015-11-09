package com.creatrix.ttb.Fragme;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import com.creatrix.ttb.Obj.Cat_Product;

import com.creatrix.ttb.adapter.Product_Adapter;
import com.creatrix.ttb.app.AppController;
import com.creatrix.ttb.R;
import com.creatrix.ttb.utils.Utils;

/**
 * Created by Harshu on 28-10-2015.
 */
public class Favority_Product extends Fragment {

    Context ctx;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.favority_product, container, false);
        ctx = getActivity();

        init(view);
        Load_Fav_Product();


        return view;
    }

ListView lv_fav_product;

    public void init(View v) {

        lv_fav_product = (ListView) v.findViewById(R.id.list_favority_product);

    }

    ProgressDialog pDialog;
    ArrayList<Cat_Product> cat_list;

    /**
     * Method to make json object request where json response starts wtih {
     */
    String dir_pro_url = Utils.main_url + "favouriteproduct.php";
    Product_Adapter adapter;

    private void Load_Fav_Product() {

        pDialog = new ProgressDialog(ctx);
        pDialog.setMessage("Please wait...");
        pDialog.setCancelable(false);

        showpDialog();

        // products_list=new ArrayList<Product>();
        dir_pro_url += "?id=" + Utils.getPref(ctx, Utils.userid, 0);

        System.out.println("URL ==> " + dir_pro_url);
//
        cat_list = new ArrayList<Cat_Product>();
     //   Toast.makeText(ctx,dir_pro_url,Toast.LENGTH_LONG).show();
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                dir_pro_url, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Log.d("Responce url", response.toString());

                try {

                    JSONArray jsonArray = response.getJSONArray("products");
                    for (int j = 0; j < jsonArray.length(); j++) {
                        JSONObject catobj = (JSONObject) jsonArray
                                .get(j);
                        Cat_Product catItem = new Cat_Product();
                      //  Toast.makeText(ctx,catobj.getString("productname"),Toast.LENGTH_LONG).show();
                        catItem.setProduct_id(catobj.getInt("productid"));
                        catItem.setProduct_name(catobj.getString("productname"));
                        catItem.setProduct_image(Utils.main_url + catobj.getString("productimage"));
                        catItem.setProduct_price(catobj.getString("price"));
                        catItem.setQuantity(catobj.getString("quantity"));
                        catItem.setIsfavority(catobj.getInt("isfavourite"));
                       // catItem.setCity(catobj.getString("city"));
                        // Utils.parentId = parentid;
                        // Toast.makeText(ctx,"Load "+parentid,Toast.LENGTH_LONG).show();
                        cat_list.add(catItem);


                    }



                    adapter = new Product_Adapter(ctx, cat_list, "List");

                        lv_fav_product.setAdapter(adapter);


                } catch (JSONException e) {
                    e.printStackTrace();
                  /*  Toast.makeText(ctx,
                            "Error: " + e.getMessage(),
                            Toast.LENGTH_LONG).show();
             */
                }
                hidepDialog();
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("", "Error: " + error.getMessage());
             /*   Toast.makeText(ctx,
                        error.getMessage(), Toast.LENGTH_SHORT).show();
           */     // hide the progress dialog
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
