package com.creatrix.ttb;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
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

import com.creatrix.ttb.Fragme.Product_Description;
import com.creatrix.ttb.Fragme.Product_Image;
import com.creatrix.ttb.Fragme.Seller_Other_Product;
import com.creatrix.ttb.app.AppController;
import com.creatrix.ttb.utils.Utils;

/**
 * Created by Harshu on 24-10-2015.
 */
public class Product_Detail extends AppCompatActivity {

    Context ctx;
    TextView tv_photo, tv_descption, tv_seller, tv_otherproduct;
    int product_id, user_id;
    LinearLayout linear_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_detail);
        ctx = Product_Detail.this;

        Intent ii = getIntent();
        product_id = ii.getIntExtra("product_id", 0);
        user_id = Utils.getPref(ctx, Utils.userid, 0);

      //  Toast.makeText(ctx, product_id + " " + user_id, Toast.LENGTH_LONG).show();
        init();
        Load_ProdectDetail();

    }

    public void init() {

        tv_photo = (TextView) findViewById(R.id.tv_photo);
        tv_descption = (TextView) findViewById(R.id.tv_description);
        tv_seller = (TextView) findViewById(R.id.tv_seller);
        tv_otherproduct = (TextView) findViewById(R.id.tv_otherproduct);
        linear_back=(LinearLayout)findViewById(R.id.linear_back);

        linear_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        tv_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                tv_photo.setTextColor(getResources().getColor(R.color.colorPrimary));
                tv_descption.setTextColor(getResources().getColor(R.color.navigationBarColor));
                tv_seller.setTextColor(getResources().getColor(R.color.navigationBarColor));
                tv_otherproduct.setTextColor(getResources().getColor(R.color.navigationBarColor));

                Bundle bundle = new Bundle();
                bundle.putStringArrayList("product_image", product_other_image);
                bundle.putString("product_price", productprice);
                bundle.putString("product_quantity", productquantity);

                FragmentManager fragmanager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmanager
                        .beginTransaction();
                Product_Image fragmentS1 = new Product_Image();
                fragmentS1.setArguments(bundle);
                fragmentTransaction.replace(R.id.content_product, fragmentS1);
                fragmentTransaction.commit();







            }
        });

        tv_descption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                tv_descption.setTextColor(getResources().getColor(R.color.colorPrimary));
                tv_photo.setTextColor(getResources().getColor(R.color.navigationBarColor));
                tv_seller.setTextColor(getResources().getColor(R.color.navigationBarColor));
                tv_otherproduct.setTextColor(getResources().getColor(R.color.navigationBarColor));

                Bundle bundle = new Bundle();
                bundle.putString("product_name", productname);
                bundle.putString("product_price", productprice);
                bundle.putString("product_quantity", productquantity);

                if (!product_other_info_name.isEmpty()){

                    bundle.putInt("other",1);
                    bundle.putStringArrayList("product_other_name", product_other_info_name);
                    bundle.putStringArrayList("product_other_value", product_other_info_value);
                }else{
                    bundle.putInt("other",0);
                }


                FragmentManager fragmanager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmanager
                        .beginTransaction();
                Product_Description fragmentS1 = new Product_Description();
                fragmentS1.setArguments(bundle);
                fragmentTransaction.replace(R.id.content_product, fragmentS1);
                fragmentTransaction.commit();

            }
        });

        tv_seller.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                tv_seller.setTextColor(getResources().getColor(R.color.colorPrimary));
                tv_photo.setTextColor(getResources().getColor(R.color.navigationBarColor));
                tv_descption.setTextColor(getResources().getColor(R.color.navigationBarColor));
                tv_otherproduct.setTextColor(getResources().getColor(R.color.navigationBarColor));


                Intent ii=new Intent(ctx,Seller_info.class);
                ii.putExtra("seller_id",seller_id);
                startActivity(ii);

               /* FragmentManager fragmanager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmanager
                        .beginTransaction();
                Seller_info fragmentS1 = new Seller_info();
                fragmentS1.setArguments(bundle);
                fragmentTransaction.replace(R.id.content_product, fragmentS1);
                fragmentTransaction.commit();
*/

            }
        });


        tv_otherproduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                tv_otherproduct.setTextColor(getResources().getColor(R.color.colorPrimary));
                tv_photo.setTextColor(getResources().getColor(R.color.navigationBarColor));
                tv_descption.setTextColor(getResources().getColor(R.color.navigationBarColor));
                tv_seller.setTextColor(getResources().getColor(R.color.navigationBarColor));

                Bundle bundle = new Bundle();
                bundle.putInt("seller_id", seller_id);


                FragmentManager fragmanager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmanager
                        .beginTransaction();
                Seller_Other_Product fragmentS1 = new Seller_Other_Product();
                fragmentS1.setArguments(bundle);
                fragmentTransaction.replace(R.id.content_product, fragmentS1);
                fragmentTransaction.commit();

            }
        });
    }


    ProgressDialog pDialog;
    String url = Utils.main_url + "getproductdetail.php";
    ArrayList<String> product_other_info_name;
    ArrayList<String> product_other_info_value;

    String productname;
    String productimage;
    String productprice;
    String productcity;
    String productquantity;
    int productfavority,seller_id;

ArrayList<String> product_other_image=new ArrayList<String>();
    /**
     * Method to make json object request where json response starts wtih {
     */
    private void Load_ProdectDetail() {

        pDialog = new ProgressDialog(ctx);
        pDialog.setMessage("Please wait...");
        pDialog.setCancelable(false);

        showpDialog();
        product_other_image=new ArrayList<String>();


        url += "?productid=" + product_id + "&id=" + user_id;

System.out.println("Other Detail "+url);
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                url, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Log.d("Responce  detail", response.toString());

                try {
                    // Parsing json object response
                    // response will be a json object

                    JSONArray jsonArray = response.getJSONArray("product");
                    for (int j = 0; j < jsonArray.length(); j++) {

                        product_other_info_name=new ArrayList<String>();
                        product_other_info_value=new ArrayList<String>();
                        JSONObject productobj = (JSONObject) jsonArray
                                .get(j);
                        productname = productobj.getString("productname");
                        productimage = productobj.getString("productimage");

                        String[] tokens = productimage.split("[,]");
                        System.out.println("total img 1 "+productimage);
                        product_other_image = new ArrayList<String>();
                        for (int i = 0; i < tokens.length; i++) {
                            product_other_image.add(Utils.main_url+tokens[i]);
                        }
                        System.out.println("total img 2 "+product_other_image);


                      //  Toast.makeText(ctx,intList.toString(),Toast.LENGTH_LONG).show();
                        productprice = productobj.getString("price");
                        productcity = productobj.getString("city");
                        productquantity = productobj.getString("quantity");
                        productfavority = productobj.getInt("isfavourite");
                        seller_id=productobj.getInt("userid");
                        JSONArray otherinfo = productobj.getJSONArray("otherinfo");

                        System.out.println("Other Info "+otherinfo);
                        if (otherinfo != null) {

                            for (int k = 0; k < otherinfo.length(); k++) {

                                JSONObject otherobj = (JSONObject) otherinfo
                                        .get(k);
                               /* System.out.println("Jobj "+otherobj);
                                System.out.println("Name "+otherobj.getString("name"));
                               */ product_other_info_name.add(otherobj.getString("name"));
                                product_other_info_value.add(otherobj.getString("value"));

                            }

                        }

                    }

                    tv_photo.setTextColor(getResources().getColor(R.color.colorPrimary));
                    tv_descption.setTextColor(getResources().getColor(R.color.navigationBarColor));
                    tv_seller.setTextColor(getResources().getColor(R.color.navigationBarColor));
                    tv_otherproduct.setTextColor(getResources().getColor(R.color.navigationBarColor));

                    Bundle bundle = new Bundle();
                    bundle.putStringArrayList("product_image", product_other_image);
                    bundle.putString("product_price", productprice);
                    bundle.putString("product_quantity", productquantity);

//Toast.makeText(ctx,product_other_image.toString(),Toast.LENGTH_LONG).show();
                   // System.out.println("product img 0 " + product_other_image);
                    FragmentManager fragmanager = getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmanager
                            .beginTransaction();
                    Product_Image fragmentS1 = new Product_Image();
                    fragmentS1.setArguments(bundle);
                    fragmentTransaction.replace(R.id.content_product, fragmentS1);
                    fragmentTransaction.commit();


                } catch (JSONException e) {
                    e.printStackTrace();


                    tv_photo.setTextColor(getResources().getColor(R.color.colorPrimary));
                    tv_descption.setTextColor(getResources().getColor(R.color.navigationBarColor));
                    tv_seller.setTextColor(getResources().getColor(R.color.navigationBarColor));
                    tv_otherproduct.setTextColor(getResources().getColor(R.color.navigationBarColor));

                    Bundle bundle = new Bundle();
                    bundle.putStringArrayList("product_image", product_other_image);
                    bundle.putString("product_price", productprice);
                    bundle.putString("product_quantity", productquantity);


                    FragmentManager fragmanager = getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmanager
                            .beginTransaction();
                    Product_Image fragmentS1 = new Product_Image();
                    fragmentS1.setArguments(bundle);
                    fragmentTransaction.replace(R.id.content_product, fragmentS1);
                    fragmentTransaction.commit();




                   /* Toast.makeText(ctx,
                            "Error: " + e.getMessage(),
                            Toast.LENGTH_LONG).show();
              */  }
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
