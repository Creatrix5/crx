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
import java.util.List;

import com.creatrix.ttb.Obj.Cat_Product;

import com.creatrix.ttb.adapter.Product_Adapter;
import com.creatrix.ttb.app.AppController;
import com.creatrix.ttb.utils.Utils;
import com.creatrix.ttb.R;

/**
 * Created by Harshu on 29-10-2015.
 */
public class Seller_Other_Product extends Fragment {

    Context ctx;
    ListView listview;
    Product_Adapter adapter;
    int seller_id;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.seller_product, container, false);
        ctx = getActivity();

        listview =(ListView)view.findViewById(R.id.list_seller_product);
        seller_id=getArguments().getInt("seller_id",0);
        Seller_product();

        return view;
    }


    ProgressDialog pDialog;

    List<Cat_Product> cat_list;


    private void Seller_product() {

        pDialog = new ProgressDialog(ctx);
        pDialog.setMessage("Please wait...");
        pDialog.setCancelable(false);

        showpDialog();

        String url= Utils.main_url+"getsellerproducts.php?sellerid="+seller_id+"&id="+Utils.getPref(ctx,Utils.userid,0);
//System.out.println("Other Product "+url);
        cat_list = new ArrayList<Cat_Product>();

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                url, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
              //  System.out.println("URL "+url);
                Log.d("Responce ", response.toString());

                try {

                    JSONArray jsonArray = response.getJSONArray("products");
                    for (int j = 0; j < jsonArray.length(); j++) {


                        JSONObject catobj = (JSONObject) jsonArray
                                .get(j);
                        Cat_Product catItem = new Cat_Product();
                        catItem.setProduct_id(catobj.getInt("productid"));
                        catItem.setProduct_name(catobj.getString("productname"));
                        catItem.setProduct_image(Utils.main_url + catobj.getString("productimage"));
                        catItem.setProduct_price(catobj.getString("price"));
                        catItem.setQuantity(catobj.getString("quantity"));
                        catItem.setIsfavority(catobj.getInt("isfavourite"));
                        catItem.setCity(catobj.getString("city"));
                        // Utils.parentId = parentid;
                        // Toast.makeText(ctx,"Load "+parentid,Toast.LENGTH_LONG).show();
                        cat_list.add(catItem);


                    }

                    adapter = new Product_Adapter(getActivity(), cat_list, "List");


                        listview.setAdapter(adapter);

                } catch (JSONException e) {
                    e.printStackTrace();

                }
                hidepDialog();
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("", "Error: " + error.getMessage());
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
