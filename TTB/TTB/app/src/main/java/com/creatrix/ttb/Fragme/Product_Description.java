package com.creatrix.ttb.Fragme;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import com.creatrix.ttb.R;

/**
 * Created by Harshu on 24-10-2015.
 */
public class Product_Description extends Fragment {

    Context ctx;
    String productname;
    String productprice, productquantity;
    int other;
    ArrayList<String> product_other_name = new ArrayList<String>();
    ArrayList<String> product_other_value = new ArrayList<String>();

    TextView tv_product_name, tv_product_price, tv_product_quantity;
    LinearLayout linear_dynamic_info;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.product_descrption, container, false);
        ctx = getActivity();


        tv_product_name = (TextView) view.findViewById(R.id.tv_product_desc_name);
        tv_product_price = (TextView) view.findViewById(R.id.tv_product_desc_price);
        tv_product_quantity = (TextView) view.findViewById(R.id.tv_product_desc_quantity);
        linear_dynamic_info = (LinearLayout) view.findViewById(R.id.linear_dynamic_info);

        productname = getArguments().getString("product_name");
        productprice = getArguments().getString("product_price");
        productquantity = getArguments().getString("product_quantity");
        other = getArguments().getInt("other", 0);

        tv_product_name.setText("Name : "+productname);
        tv_product_price.setText("Price : "+productprice);
        tv_product_quantity.setText("Quantity : "+productquantity);

        if (other == 1) {
            linear_dynamic_info.setVisibility(View.VISIBLE);
            product_other_name = getArguments().getStringArrayList("product_other_name");
            product_other_value = getArguments().getStringArrayList("product_other_value");


            for (int i = 0; i < product_other_name.size(); i++) {


                LinearLayout parent = new LinearLayout(ctx);

                parent.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                parent.setOrientation(LinearLayout.HORIZONTAL);

                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                params.setMargins(4, 4, 4, 4);
                TextView tv1 = new TextView(ctx);
                tv1.setText(product_other_name.get(i)+" :");
                tv1.setTextSize(15);
                tv1.setLayoutParams(params);


                TextView tv2 = new TextView(ctx);
                tv2.setText(product_other_value.get(i));
                tv2.setTextSize(15);
                tv2.setLayoutParams(params);
              parent.addView(tv1);
                parent.addView(tv2);

                          linear_dynamic_info.addView(parent);
            }
        }else{
            linear_dynamic_info.setVisibility(View.GONE);
        }

        return view;

    }
}
