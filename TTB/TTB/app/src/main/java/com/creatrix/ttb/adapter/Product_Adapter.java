package com.creatrix.ttb.adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.creatrix.ttb.Product_Detail;
import com.creatrix.ttb.R;
import com.creatrix.ttb.app.AppController;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import com.creatrix.ttb.Obj.Cat_Product;

import com.creatrix.ttb.utils.Utils;

import org.json.JSONException;
import org.json.JSONObject;

public class Product_Adapter extends BaseAdapter {

    private Context activity;
    private LayoutInflater inflater;
    private List<Cat_Product> Tagmodels;
    private List<String> tag_val;
    String product_view_type;
    List<Cat_Product> obj_product;

  /*  ImageLoader imageLoader = ImageLoader.getInstance();
    DisplayImageOptions options;
*/
  ImageLoader imageLoader = AppController.getInstance().getImageLoader();


    public Product_Adapter(Context activity, List<Cat_Product> Tagmodels, String product_view_type) {
        this.activity = activity;
        obj_product = Tagmodels;
        this.product_view_type = product_view_type;
        this.Tagmodels = new ArrayList<Cat_Product>();
        this.Tagmodels.addAll(obj_product);
    }

    @Override
    public int getCount() {
        return obj_product.size();
    }

    @Override
    public Object getItem(int location) {
        return obj_product.get(location);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    Cat_Product cat_product;
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

      final  ViewHolder holder = new ViewHolder();
        if (inflater == null)
            inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        // if (convertView == null)
        if (product_view_type.equals("Grid"))
            convertView = inflater.inflate(R.layout.product_grid_item, null);
        else
            convertView = inflater.inflate(R.layout.product_list_item, null);


        holder.iv_product_image = (NetworkImageView) convertView.findViewById(R.id.iv_product);
        holder.txt_productname = (TextView) convertView.findViewById(R.id.tv_product_name);
        holder.txt_productprice = (TextView) convertView.findViewById(R.id.tv_product_price);
        holder.txt_quantity = (TextView) convertView.findViewById(R.id.tv_product_quantity);
        holder.txt_city = (TextView) convertView.findViewById(R.id.tv_product_city);
        holder.iv_favority = (ImageView) convertView.findViewById(R.id.iv_favority);
        holder.linear_favority = (LinearLayout) convertView.findViewById(R.id.linear_favority);


        cat_product= (Cat_Product) getItem(position);
        System.out.println("product " + cat_product.getProduct_image());

        holder.iv_product_image.setImageUrl(cat_product.getProduct_image(), imageLoader);

        holder.txt_productname.setText(cat_product.getProduct_name());
        holder.txt_productname.setMaxLines(1);
        holder.txt_productname.setEllipsize(TextUtils.TruncateAt.END);

        holder.txt_productprice.setText(cat_product.getProduct_price() + " Rs");
        holder.txt_quantity.setText("Minimum "+cat_product.getQuantity()+" Pieces");
        final int isfavority = cat_product.getIsfavority();
        System.out.println("Favority " + isfavority+" "+cat_product.getProduct_id());
        if (isfavority == 0) {
            holder.iv_favority.setImageResource(R.drawable.favorite);
        } else {
            holder.iv_favority.setImageResource(R.drawable.fav_vis);
        }

        holder.linear_favority.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cat_product= (Cat_Product) getItem(position);
             int isfavority=cat_product.getIsfavority();

                if (isfavority==0) {
                    int res = Add_Fav_Product(activity, "product", cat_product.getProduct_id(),"addfav");
                    Toast.makeText(activity,"Add "+res,Toast.LENGTH_LONG).show();
                    if (res==1) {
                        holder.iv_favority.setImageResource(R.drawable.fav_vis);
                       cat_product.setIsfavority(1);
                        Product_Adapter.this.notifyDataSetChanged();
                    }
                   // Toast.makeText(activity,res+" add",Toast.LENGTH_LONG).show();
                }else{
                    int res = Add_Fav_Product(activity, "product", cat_product.getProduct_id(),"removefav");
                    Toast.makeText(activity,"Rem "+res,Toast.LENGTH_LONG).show();
                    if (res==1) {
                        holder.iv_favority.setImageResource(R.drawable.favorite);
                        cat_product.setIsfavority(0);
                        Product_Adapter.this.notifyDataSetChanged();
                    }
                  //  Toast.makeText(activity,res+" remove",Toast.LENGTH_LONG).show();
                }
//Toast.makeText(activity,"Favority " + isfavority+" "+cat_product.getProduct_id(),Toast.LENGTH_LONG).show();
            }
        });

        String city = cat_product.getCity();
        if (city == null || city == "")
            holder.txt_city.setText("City");
        else
            holder.txt_city.setText(city);

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cat_Product cat_product = (Cat_Product) getItem(position);
                //     Toast.makeText(activity,cat_product.getProduct_name(),Toast.LENGTH_LONG).show();

                Intent ii = new Intent(activity, Product_Detail.class);
                ii.putExtra("product_id", cat_product.getProduct_id());

                activity.startActivity(ii);
            }
        });


        return convertView;
    }

    public class ViewHolder {
        TextView txt_productname, txt_productprice, txt_quantity, txt_city;
        NetworkImageView iv_product_image;
       ImageView iv_favority;

        LinearLayout linear_favority;

    }


    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        obj_product.clear();
        if (charText.length() == 0) {
            obj_product.addAll(Tagmodels);
        } else {
            for (Cat_Product wp : Tagmodels) {
                if (wp.getProduct_name().toLowerCase(Locale.getDefault())
                        .contains(charText)) {
                    obj_product.add(wp);
                }

            }
        }
        notifyDataSetChanged();
    }




    ProgressDialog pDialog;
    String fav_url;
    int resp;

    public int Add_Fav_Product(final Context ctx, String type, int typeid, String fav_ty) {

        pDialog = new ProgressDialog(ctx);
        pDialog.setMessage("Please wait...");
        pDialog.setCancelable(false);

        showpDialog();
        fav_url = "";
        // products_list=new ArrayList<Product>();
        if (fav_ty.equals("addfav"))
            fav_url = Utils.main_url + "favourite.php?id=" + Utils.getPref(ctx, Utils.userid, 0) + "&type=" + type + "&typeid=" + typeid;
        else
            fav_url = Utils.main_url + "unfavourite.php?id=" + Utils.getPref(ctx, Utils.userid, 0) + "&type=" + type + "&typeid=" + typeid;

        System.out.println(" Fav URL  ==>"+fav_url);

        //   Toast.makeText(ctx,fav_url,Toast.LENGTH_LONG).show();

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                fav_url, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Log.d("Responce fav ", response.toString());

                try {

                    String responcemsg = response.getString("ResponseMsg");
                    resp = response.getInt("ResponseCode");
                    Toast.makeText(ctx, responcemsg, Toast.LENGTH_LONG).show();

                } catch (JSONException e) {
                    e.printStackTrace();

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
        return resp;
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