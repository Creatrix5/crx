package com.creatrix.ttb;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.creatrix.ttb.Fragme.Favority_Person;
import com.creatrix.ttb.Fragme.Favority_Product;
import com.creatrix.ttb.utils.Utils;

/**
 * Created by Harshu on 28-10-2015.
 */
public class Favority extends AppCompatActivity {

    Context ctx;
    TextView tv_fav_product,tv_fav_person;
    LinearLayout linear_back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.favority);

        ctx=Favority.this;
        Utils.view_page= "FAVORIRE";
        Utils.init_viewstub(Favority.this);

        tv_fav_product=(TextView)findViewById(R.id.tv_favority_product);
        tv_fav_person=(TextView)findViewById(R.id.tv_favority_person);

        linear_back=(LinearLayout)findViewById(R.id.linear_back);


        linear_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        FragmentManager fragmanager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmanager
                .beginTransaction();
        Favority_Product fragmentS1 = new Favority_Product();
        //   fragmentS1.setArguments(bundle);
        fragmentTransaction.replace(R.id.content_favority, fragmentS1);
        fragmentTransaction.commit();

        tv_fav_product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                FragmentManager fragmanager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmanager
                        .beginTransaction();
                Favority_Product fragmentS1 = new Favority_Product();
                //   fragmentS1.setArguments(bundle);
                fragmentTransaction.replace(R.id.content_favority, fragmentS1);
                fragmentTransaction.commit();


            }
        });
        tv_fav_person.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                FragmentManager fragmanager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmanager
                        .beginTransaction();
                Favority_Person fragmentS1 = new Favority_Person();
                //   fragmentS1.setArguments(bundle);
                fragmentTransaction.replace(R.id.content_favority, fragmentS1);
                fragmentTransaction.commit();


            }
        });

    }
}
