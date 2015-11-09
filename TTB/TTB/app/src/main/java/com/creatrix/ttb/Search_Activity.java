package com.creatrix.ttb;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


/**
 * Created by Harshu on 04-11-2015.
 */
public class Search_Activity extends Activity {

    Button btn_search_apps;
    EditText et_search;
    Context ctx;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_activity);
        btn_search_apps=(Button)findViewById(R.id.btn_search_apps);
        et_search=(EditText)findViewById(R.id.et_search);
        ctx=Search_Activity.this;
        btn_search_apps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String search=et_search.getText().toString();
                Intent ii=new Intent(ctx,Category_Product.class);
                ii.putExtra("id",0);
                ii.putExtra("SearchText",search);
                ii.putExtra("filter_option","");
                ii.putExtra("Number_Of_Filter",0);
                startActivity(ii);
            }
        });
    }
}
