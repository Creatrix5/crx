package com.creatrix.ttb;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.creatrix.ttb.Fragme.Directory_company;
import com.creatrix.ttb.Fragme.Directory_product;
import com.creatrix.ttb.Fragme.Directory_service;
import com.creatrix.ttb.utils.Utils;
import com.creatrix.ttb.Fragme.Directory_person;

/**
 * Created by Harshu on 26-10-2015.
 */
public class Directory extends AppCompatActivity {

    Context ctx;

    TextView tv_product,tv_company,tv_person,tv_services;
    LinearLayout linear_back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.directory);
        ctx=Directory.this;
        Utils.view_page= "DIRECTORY";
        Utils.init_viewstub(Directory.this);

        tv_product=(TextView)findViewById(R.id.tv_product);
        tv_company=(TextView)findViewById(R.id.tv_company);
        tv_person=(TextView)findViewById(R.id.tv_person);
        tv_services=(TextView)findViewById(R.id.tv_services);
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
        Directory_product fragmentS1 = new Directory_product();
     //   fragmentS1.setArguments(bundle);
        fragmentTransaction.replace(R.id.content_directory, fragmentS1);
        fragmentTransaction.commit();

        tv_product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                tv_product.setTextColor(getResources().getColor(R.color.colorPrimary));
                tv_company.setTextColor(getResources().getColor(R.color.navigationBarColor));
                tv_person.setTextColor(getResources().getColor(R.color.navigationBarColor));
                tv_services.setTextColor(getResources().getColor(R.color.navigationBarColor));


                FragmentManager fragmanager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmanager
                        .beginTransaction();
                Directory_product fragmentS1 = new Directory_product();
                //   fragmentS1.setArguments(bundle);
                fragmentTransaction.replace(R.id.content_directory, fragmentS1);
                fragmentTransaction.commit();

            }
        });

        tv_product.setTextColor(getResources().getColor(R.color.colorPrimary));

        tv_company.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                tv_company.setTextColor(getResources().getColor(R.color.colorPrimary));
                tv_product.setTextColor(getResources().getColor(R.color.navigationBarColor));
                tv_person.setTextColor(getResources().getColor(R.color.navigationBarColor));
                tv_services.setTextColor(getResources().getColor(R.color.navigationBarColor));


                FragmentManager fragmanager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmanager
                        .beginTransaction();
                Directory_company fragmentS1 = new Directory_company();
                //   fragmentS1.setArguments(bundle);
                fragmentTransaction.replace(R.id.content_directory, fragmentS1);
                fragmentTransaction.commit();

            }
        });

        tv_person.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                tv_person.setTextColor(getResources().getColor(R.color.colorPrimary));
                tv_product.setTextColor(getResources().getColor(R.color.navigationBarColor));
                tv_company.setTextColor(getResources().getColor(R.color.navigationBarColor));
                tv_services.setTextColor(getResources().getColor(R.color.navigationBarColor));


                FragmentManager fragmanager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmanager
                        .beginTransaction();
                Directory_person fragmentS1 = new Directory_person();
                //   fragmentS1.setArguments(bundle);
                fragmentTransaction.replace(R.id.content_directory, fragmentS1);
                fragmentTransaction.commit();

            }
        });


        tv_services.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                tv_services.setTextColor(getResources().getColor(R.color.colorPrimary));
                tv_product.setTextColor(getResources().getColor(R.color.navigationBarColor));
                tv_company.setTextColor(getResources().getColor(R.color.navigationBarColor));
                tv_person.setTextColor(getResources().getColor(R.color.navigationBarColor));

                FragmentManager fragmanager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmanager
                        .beginTransaction();
                Directory_service fragmentS1 = new Directory_service();
                //   fragmentS1.setArguments(bundle);
                fragmentTransaction.replace(R.id.content_directory, fragmentS1);
                fragmentTransaction.commit();

            }
        });
    }
}
