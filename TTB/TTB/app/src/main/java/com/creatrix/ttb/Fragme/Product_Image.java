package com.creatrix.ttb.Fragme;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import com.creatrix.ttb.R;

/**
 * Created by Harshu on 24-10-2015.
 */
public class Product_Image extends Fragment {
Context ctx;

    TextView tv_price,tv_quantity;

ArrayList<String> other_image;
    String quantity;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.product_image, container, false);
        ctx = getActivity();
        other_image=new ArrayList<String>();
        other_image = getArguments().getStringArrayList("product_image");
        String price=getArguments().getString("product_price");
        quantity=getArguments().getString("product_quantity");

        tv_price=(TextView)view.findViewById(R.id.tv_product_detail_price);
        tv_quantity=(TextView)view.findViewById(R.id.tv_product_detail_quantity);

        Bundle bundle = new Bundle();
        bundle.putStringArrayList("product_image", other_image);
        System.out.println("product img " + other_image);
        FragmentManager fragmanager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmanager
                .beginTransaction();
        Product_Image_Fragment fragmentS1 = new Product_Image_Fragment();
        fragmentS1.setArguments(bundle);
        fragmentTransaction.replace(R.id.content_frame, fragmentS1);
        fragmentTransaction.commit();



        tv_price.setText(price + " Rs.");
        tv_quantity.setText("Minimum "+quantity+" Pieces");
  return view;
    }
}
