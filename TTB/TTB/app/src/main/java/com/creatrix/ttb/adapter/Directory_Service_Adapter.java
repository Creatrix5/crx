package com.creatrix.ttb.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import com.creatrix.ttb.Obj.Dir_Service;

import com.creatrix.ttb.R;

public class Directory_Service_Adapter extends BaseAdapter {

    private Context activity;
    private LayoutInflater inflater;
    private List<Dir_Service> Tagmodels;
    private List<String> tag_val;

    List<Dir_Service> obj_product;

    public Directory_Service_Adapter(Context activity, List<Dir_Service> Tagmodels) {
        this.activity = activity;
        obj_product = Tagmodels;
        this.Tagmodels=new ArrayList<Dir_Service>();
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

        ViewHolder holder=new ViewHolder();
        if (inflater == null)
            inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
       // if (convertView == null)
            convertView = inflater.inflate(R.layout.person_list_item, null);
            holder.txt_personname=(TextView)convertView.findViewById(R.id.tv_person_name);
            holder.txt_personaddress=(TextView)convertView.findViewById(R.id.tv_person_address);


        Dir_Service cat_product=(Dir_Service)getItem(position);

            holder.txt_personname.setText(cat_product.getName());
            holder.txt_personaddress.setText(cat_product.getAddress());

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
        TextView txt_personname,txt_personaddress;


    }


    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        obj_product.clear();
        if (charText.length() == 0) {
            obj_product.addAll(Tagmodels);
        } else {
            for (Dir_Service wp : Tagmodels) {
                if (wp.getName().toLowerCase(Locale.getDefault())
                        .contains(charText)) {
                    obj_product.add(wp);
                }

            }
        }
        notifyDataSetChanged();
    }


}