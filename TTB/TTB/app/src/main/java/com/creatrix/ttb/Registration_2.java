package com.creatrix.ttb;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.creatrix.ttb.utils.Utils;

/**
 * Created by Harshu on 13-10-2015.
 */
public class Registration_2 extends AppCompatActivity {

    Context ctx;
    LinearLayout linear_next, linear_back;
    EditText et_shopno,et_floor,et_phase,et_marketname,et_road,et_landmark,et_area,et_city,et_stat,et_district,et_pincode;

   // TextInputLayout shop_no,floor,phase,market_name,road,landmark,area,city,state,district,pincode;
    TextView address;
    Typeface hindi, gujarati;

    Button btn_range_1, btn_range_2, btn_range_3, btn_range_4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration_screen_2);

        ctx = Registration_2.this;

        hindi = Typeface.createFromAsset(getAssets(), "fonts/DroidHindi.ttf");
        gujarati = Typeface.createFromAsset(getAssets(), "fonts/Lohit-Gujarati.ttf");

        init();


    }

    public void init() {



        address = (TextView) findViewById(R.id.tv_reg_screen_2_title);

        et_shopno = (EditText) findViewById(R.id.shop_no);
        et_floor = (EditText) findViewById(R.id.floor);
        et_phase = (EditText) findViewById(R.id.phase);
        et_marketname = (EditText) findViewById(R.id.market_name);
        et_road = (EditText) findViewById(R.id.road);
        et_landmark = (EditText) findViewById(R.id.landmark);
        et_area = (EditText) findViewById(R.id.area);
        et_city = (EditText) findViewById(R.id.city);
        et_stat = (EditText) findViewById(R.id.state);
        et_district = (EditText) findViewById(R.id.district);
        et_pincode = (EditText) findViewById(R.id.pincode);

        linear_next = (LinearLayout) findViewById(R.id.linear_next);
        linear_back = (LinearLayout) findViewById(R.id.linear_back);


        btn_range_1 = (Button)findViewById(R.id.btn_range_1);
        btn_range_2 = (Button)findViewById(R.id.btn_range_2);
        btn_range_3 = (Button)findViewById(R.id.btn_range_3);
        btn_range_4 = (Button)findViewById(R.id.btn_range_4);

        btn_range_1.setBackgroundResource(R.drawable.round_blue_1);

        btn_range_2.setBackgroundResource(R.drawable.round_blue_2);
        btn_range_3.setBackgroundResource(R.drawable.round_white_3);
        btn_range_4.setBackgroundResource(R.drawable.round_white_4);


        LaungageMethod();


        linear_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String shopno = et_shopno.getText().toString();
                String floor = et_floor.getText().toString();
                String phase = et_phase.getText().toString();
                String marketname = et_marketname.getText().toString();
                String road = et_road.getText().toString();
                String landmark = et_landmark.getText().toString();
                String area = et_area.getText().toString();
                String city = et_city.getText().toString();
                String state = et_stat.getText().toString();
                String district = et_district.getText().toString();
                String pincode = et_pincode.getText().toString();

             if (check_validation()) {
                Utils.setPref(ctx, Utils.shopno, shopno);
                Utils.setPref(ctx, Utils.flore, floor);
                Utils.setPref(ctx, Utils.phase, phase);
                Utils.setPref(ctx, Utils.marketname, marketname);
                Utils.setPref(ctx, Utils.road, road);
                Utils.setPref(ctx, Utils.landmark, landmark);
                Utils.setPref(ctx, Utils.area, area);
                Utils.setPref(ctx, Utils.city, city);
                Utils.setPref(ctx, Utils.state, state);
                Utils.setPref(ctx, Utils.district, district);
                Utils.setPref(ctx, Utils.pincode, pincode);

                startActivity(new Intent(ctx, Registration_3.class));
                       }
            }
        });

        linear_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }


    private void LaungageMethod() {

        if (Utils.Language.equals("G")) {



            //inputLayoutEmail.setHintTextAppearance(R.style.TextAppearence_App_TextInputLayout);
            et_shopno.setHint("દુકાન નંબર");
            et_shopno.setTypeface(gujarati);

            et_floor.setHint("માળ");
            et_floor.setTypeface(gujarati);

            et_phase.setHint("તબક્કો(વૈકલ્પિક)");
            et_phase.setTypeface(gujarati);

            et_marketname.setHint("બજાર નામ");
            et_marketname.setTypeface(gujarati);

            et_road.setHint("રોડ");
            et_road.setTypeface(gujarati);

            et_landmark.setHint("લેન્ડમાર્ક");
            et_landmark.setTypeface(gujarati);

            et_area.setHint("વિસ્તાર");
            et_area.setTypeface(gujarati);

            et_stat.setHint("હોદ્દો");
            et_stat.setTypeface(gujarati);

            et_district.setHint("રાજ્ય");
            et_district.setTypeface(gujarati);

            et_city.setHint("જિલ્લા(વૈકલ્પિક)");
            et_city.setTypeface(gujarati);

            et_pincode.setHint("પિનકોડ");
            et_pincode.setTypeface(gujarati);

            address.setText("સરનામું");
            address.setTypeface(gujarati);





        } else if (Utils.Language.equals("H")) {
            et_shopno.setHint("दुकान संख्या");
            et_shopno.setTypeface(hindi);

            et_floor.setHint("मंज़िल");
            et_floor.setTypeface(hindi);

            et_phase.setHint("अवस्था(ऐच्छिक)");
            et_phase.setTypeface(hindi);


            et_marketname.setHint("बाजार नाम");
            et_marketname.setTypeface(hindi);

            et_road.setHint("सड़क");
            et_road.setTypeface(hindi);


            et_landmark.setHint("सीमा चिन्ह");
            et_landmark.setTypeface(hindi);

            et_area.setHint("क्षेत्र");
            et_area.setTypeface(hindi);

            et_stat.setHint("राज्य");
            et_stat.setTypeface(hindi);

            et_district.setHint("जिला(ऐच्छिक)");
            et_district.setTypeface(hindi);

            et_city.setHint("शहर");
            et_city.setTypeface(gujarati);

            et_pincode.setHint("पिन कोड");
            et_pincode.setTypeface(hindi);

            address.setText("पता");
            address.setTypeface(hindi);



        } else if (Utils.Language.equals("E")) {

            et_shopno.setHint("Shop No");
            et_floor.setHint("Floor");
            et_phase.setHint("Phase(Optional)");
            et_marketname.setHint("Market Name");
            et_road.setHint("Road");
            et_landmark.setHint("Landmark");
            et_area.setHint("Area");
            et_stat.setHint("State");
            et_district.setHint("District(Optional)");
            et_pincode.setHint("Pincode");
            address.setText("Address");


        }
    }


    public boolean check_validation(){

        if (et_shopno.getText().toString().length()==0){
            et_shopno.setError("Enter Shop Number");
            et_shopno.requestFocus();
            et_shopno.setSelection(0);
        }else if (et_floor.getText().toString().length()==0){
            et_floor.setError("Enter Floor");
            et_floor.requestFocus();
            et_floor.setSelection(0);
        }

        else if (et_marketname.getText().toString().length()==0){
            et_marketname.setError("Enter Market Name");
            et_marketname.requestFocus();
            et_marketname.setSelection(0);
        }else if (et_road.getText().toString().length()==0){
            et_road.setError("Enter Road");
            et_road.requestFocus();
            et_road.setSelection(0);
        }else if (et_landmark.getText().toString().length()==0){
            et_landmark.setError("Enter Landmark");
            et_landmark.requestFocus();
            et_landmark.setSelection(0);
        }else if (et_area.getText().toString().length()==0){
            et_area.setError("Enter Area");
            et_area.requestFocus();
            et_area.setSelection(0);
        }else if (et_city.getText().toString().length()==0){
            et_city.setError("Enter City");
            et_city.requestFocus();
            et_city.setSelection(0);
        }else if (et_stat.getText().toString().length()==0){
            et_stat.setError("Enter State");
            et_stat.requestFocus();
            et_stat.setSelection(0);
        }else if (et_pincode.getText().toString().length()==0){
            et_pincode.setError("Enter Pincode");
            et_pincode.requestFocus();
            et_pincode.setSelection(0);
        }else if(et_pincode.getText().toString().length() !=6){
            et_pincode.setError("Enter Valid Pincode");
            et_pincode.requestFocus();
            et_pincode.setSelection(et_pincode.getText().length());
        }else{
                return true;

        }


        return false;
    }

}
