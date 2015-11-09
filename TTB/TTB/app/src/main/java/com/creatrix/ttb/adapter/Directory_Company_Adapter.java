package com.creatrix.ttb.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.creatrix.ttb.Obj.dir_product;
import com.creatrix.ttb.Seller_info;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import com.creatrix.ttb.Obj.Dir_Company;

import com.creatrix.ttb.R;

public class Directory_Company_Adapter extends BaseAdapter {

    private Context activity;
    private LayoutInflater inflater;
    private List<dir_product> Tagmodels;
    private List<String> tag_val;

    List<dir_product> obj_product;

    public Directory_Company_Adapter(Context activity, List<dir_product> Tagmodels) {
        this.activity = activity;
        obj_product = Tagmodels;
        this.Tagmodels = new ArrayList<dir_product>();
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

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        ViewHolder holder = new ViewHolder();
        if (inflater == null)
            inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        // if (convertView == null)
        convertView = inflater.inflate(R.layout.company_list_item, null);
        holder.txt_companyname = (TextView) convertView.findViewById(R.id.tv_company_name);
        holder.txt_companyaddress = (TextView) convertView.findViewById(R.id.tv_company_address);
        holder.txt_distance = (TextView) convertView.findViewById(R.id.tv_company_distance);
        holder.tv_owner_name = (TextView) convertView.findViewById(R.id.tv_owner_name);
        holder.linear_call = (LinearLayout) convertView.findViewById(R.id.linear_call);

        final dir_product cat_product = (dir_product) getItem(position);

        holder.txt_companyname.setText(cat_product.getOwner());
        holder.txt_companyaddress.setText(cat_product.getAddress());
        holder.txt_distance.setText(cat_product.getDistance() + "");
        if (cat_product.getOwner() == null || cat_product.getOwner().equals("")) {
            holder.tv_owner_name.setVisibility(View.GONE);
        } else {
            holder.tv_owner_name.setText(cat_product.getCompany() + "");
        }
        //   int featured= cat_product.getIs_featured();
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ii=new Intent(activity, Seller_info.class);
                ii.putExtra("seller_id",cat_product.getUserid());
                activity.startActivity(ii);
            }
        });


        holder.linear_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent callIntent = new Intent(Intent.ACTION_CALL);
                    callIntent.setData(Uri.parse("tel:" + cat_product.getMobileno()));
                    activity.startActivity(callIntent);

                } catch (SecurityException e) {

                }

            }
        });


          /*  convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dir_product cat_product=(dir_product)getItem(position);
               //     Toast.makeText(activity,cat_product.getProduct_name(),Toast.LENGTH_LONG).show();

                    Intent ii=new Intent(activity, Product_Detail.class);
                    ii.putExtra("product_id",cat_product.getProduct_id());

                    activity.startActivity(ii);
                }
            });
*/


        return convertView;
    }

    public class ViewHolder {
        TextView txt_companyname, txt_companyaddress, txt_distance, tv_owner_name;
        LinearLayout linear_call;

    }


}