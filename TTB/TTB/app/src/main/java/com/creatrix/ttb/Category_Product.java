package com.creatrix.ttb;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
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
import java.util.List;

import com.creatrix.ttb.Obj.Cat_Product;
import com.creatrix.ttb.adapter.Product_Adapter;
import com.creatrix.ttb.app.AppController;
import com.creatrix.ttb.utils.Utils;

/**
 * Created by Harshu on 23-10-2015.
 */
public class Category_Product extends FragmentActivity {
    Context ctx;

    ListView lv_product;
    GridView gv_product;

    int cat_id;
    int user_id;
    Product_Adapter adapter;
    String url = Utils.main_url + "getcategoryproduct.php";
    LinearLayout linear_view_type,linear_back;
    String product_view_type = "Grid";
    //EditText et_search;
Button btn_view_change;
    ImageView iv_search;
LinearLayout linear_filter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.category_product);
        ctx = Category_Product.this;
        Intent ii = getIntent();
        cat_id = ii.getIntExtra("id", 0);
        user_id = Utils.getPref(ctx, Utils.userid, 0);

        if (cat_id==0){
            String search_text=ii.getStringExtra("SearchText");
            String filter_option=ii.getStringExtra("filter_option");
            int number_of_filter=ii.getIntExtra("Number_Of_Filter", 0);
           // ii.putExtra("Number_Of_Filter",number_of_filter);
            url=Utils.main_url+"productgridwithfilter.php?&id=" + user_id+"&search="+search_text+""+filter_option+"&filtercount="+number_of_filter;
        }else{
            url += "?categoryid=" + cat_id + "&id=" + user_id;

        }


        Utils.view_page= "fd";
        Utils.init_viewstub(Category_Product.this);

        init();

        gv_product.setVisibility(View.VISIBLE);
        LoadProduct();


    }

    public void init() {

        FrameLayout footerLayout = (FrameLayout) getLayoutInflater().inflate(R.layout.footer, null);
        TextView footer = (TextView) footerLayout.findViewById(R.id.footer);


        lv_product = (ListView) findViewById(R.id.lv_product);
        gv_product = (GridView) findViewById(R.id.gv_product);
        linear_view_type = (LinearLayout) findViewById(R.id.linear_view_type);
        linear_back=(LinearLayout)findViewById(R.id.linear_back);
        btn_view_change=(Button)findViewById(R.id.btn_view_change);
        iv_search=(ImageView)findViewById(R.id.iv_search);
        linear_filter=(LinearLayout)findViewById(R.id.linear_filter);
linear_filter.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
       // startActivity(new Intent(ctx,Filter_Activity.class));
        Intent ii=new Intent(ctx, Filter_Activity.class);
        ii.putExtra("cat_id",cat_id);
        startActivity(ii);
    }
});

        iv_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ctx,Search_Activity.class));
            }
        });

        linear_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        linear_view_type.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (product_view_type.equals("Grid")) {
                    product_view_type = "List";
                    lv_product.setVisibility(View.VISIBLE);
                    gv_product.setVisibility(View.GONE);

                    btn_view_change.setBackgroundResource(R.drawable.ic_action_view_as_grid);


                } else {
                    product_view_type = "Grid";
                    lv_product.setVisibility(View.GONE);
                    gv_product.setVisibility(View.VISIBLE);
                   // LoadProduct();
                    btn_view_change.setBackgroundResource(R.drawable.ic_action_view_as_list);
                }

                LoadProduct();


            }
        });

    }


    ProgressDialog pDialog;

    List<Cat_Product> cat_list;

    private void LoadProduct() {

        pDialog = new ProgressDialog(ctx);
        pDialog.setMessage("Please wait...");
        pDialog.setCancelable(false);

        showpDialog();

        cat_list = new ArrayList<Cat_Product>();
        System.out.println("URL ==>"+url);

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                url, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                System.out.println("URL "+url);
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

                    adapter = new Product_Adapter(Category_Product.this, cat_list, product_view_type);


                    if (product_view_type.equals("Grid")) {

                        gv_product.setAdapter(adapter);
                    } else {
                        lv_product.setAdapter(adapter);
                    }

               /*     String text = et_search.getText().toString()
                            .toLowerCase(Locale.getDefault());
                    if (text == null || text.equals("")) {
                    } else {
                        adapter.filter(text);
                    }
               */ } catch (JSONException e) {
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
