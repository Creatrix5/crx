package com.creatrix.ttb;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.creatrix.ttb.utils.Utils;

/**
 * Created by Harshu on 28-10-2015.
 */
public class Setting extends Activity {

    TextView tv_logout;
    Context ctx;
    LinearLayout linear_back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_layout);
        ctx=Setting.this;

            Utils.view_page = "SETTING";
            Utils.init_viewstub(Setting.this);


        init();

    }

    public void init(){
        tv_logout=(TextView)findViewById(R.id.tv_logout);
        linear_back=(LinearLayout)findViewById(R.id.linear_back);



        tv_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.setPref(ctx, Utils.Check_login, true);
                Intent ii=new Intent(ctx,MainActivity.class);
                startActivity(ii);
                finish();

            }
        });



        linear_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}
