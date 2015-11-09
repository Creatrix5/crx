package com.creatrix.ttb;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.creatrix.ttb.app.AppController;
import com.creatrix.ttb.utils.Utils;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Harshu on 13-10-2015.
 */
public class Registration_1 extends AppCompatActivity {


    LinearLayout linear_next, linear_back;
    Context ctx;
    EditText et_user_name, et_first_name, et_last_name, et_password, et_repassword, et_email, et_company, et_designation, et_contact_no, et_mobile_no, et_office_no;
    Button btn_male, btn_female;
    String selected_gender = "m";
 Button b_male, b_female;
    TextView tv_title;

    Typeface hindi, gujarati;

    Button btn_range_1, btn_range_2, btn_range_3, btn_range_4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration_screen1);

        ctx = Registration_1.this;

        hindi = Typeface.createFromAsset(getAssets(), "fonts/DroidHindi.ttf");
        gujarati = Typeface.createFromAsset(getAssets(), "fonts/Lohit-Gujarati.ttf");


        init();

    }


    public void init() {

        linear_next = (LinearLayout) findViewById(R.id.linear_next);
        linear_back = (LinearLayout) findViewById(R.id.linear_back);

        /*first_name = (TextInputLayout) findViewById(R.id.layout_first_name);
        last_name = (TextInputLayout) findViewById(R.id.layout_last_name);
        conatctno = (TextInputLayout) findViewById(R.id.layout_contact_no);
*/
        b_male = (Button) findViewById(R.id.btn_male);
        b_female = (Button) findViewById(R.id.btn_female);
        tv_title = (TextView) findViewById(R.id.tv_reg_screen_1_title);



        btn_range_1 = (Button)findViewById(R.id.btn_range_1);
        btn_range_2 = (Button)findViewById(R.id.btn_range_2);
        btn_range_3 = (Button)findViewById(R.id.btn_range_3);
        btn_range_4 = (Button)findViewById(R.id.btn_range_4);

        btn_range_1.setBackgroundResource(R.drawable.round_blue_1);

        btn_range_2.setBackgroundResource(R.drawable.round_white_2);
        btn_range_3.setBackgroundResource(R.drawable.round_white_3);
        btn_range_4.setBackgroundResource(R.drawable.round_white_4);


        et_user_name = (EditText) findViewById(R.id.user_name);
        et_first_name = (EditText) findViewById(R.id.first_name);
        et_last_name = (EditText) findViewById(R.id.last_name);
        et_password = (EditText) findViewById(R.id.password);
        et_repassword = (EditText) findViewById(R.id.re_enter_password);
        et_email = (EditText) findViewById(R.id.email);
        et_company = (EditText) findViewById(R.id.company);
        et_designation = (EditText) findViewById(R.id.designation);
        et_contact_no = (EditText) findViewById(R.id.contact_no);
        et_mobile_no = (EditText) findViewById(R.id.mobile_no);
        et_office_no = (EditText) findViewById(R.id.office_no);
        btn_male = (Button) findViewById(R.id.btn_male);
        btn_female = (Button) findViewById(R.id.btn_female);
        LaungageMethod();

        btn_male.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selected_gender = "m";
                btn_male.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_design_selected));
                btn_female.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_design));
                btn_female.setTextColor(getResources().getColor(R.color.colorPrimary));
                btn_male.setTextColor(getResources().getColor(R.color.textColorPrimary));
            }
        });


        btn_female.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                selected_gender = "f";
                btn_female.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_design_selected));
                btn_male.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_design));
                btn_male.setTextColor(getResources().getColor(R.color.colorPrimary));
                btn_female.setTextColor(getResources().getColor(R.color.textColorPrimary));

            }
        });

        linear_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String firstname = et_first_name.getText().toString();
                String lastname = et_last_name.getText().toString();
                String username = et_user_name.getText().toString();
                String password = et_password.getText().toString();
                String email = et_email.getText().toString();
                String company = et_company.getText().toString();
                String designation = et_designation.getText().toString();
                String contactno = et_contact_no.getText().toString();
                String mobileno = et_mobile_no.getText().toString();
                String officeno = et_office_no.getText().toString();
                mobileno = "+91" + mobileno;

                if (check_validation()) {

                Utils.setPref(ctx, Utils.firstname, firstname);
                Utils.setPref(ctx, Utils.lastname, lastname);
                Utils.setPref(ctx, Utils.username, username);
                Utils.setPref(ctx, Utils.password, password);
                Utils.setPref(ctx, Utils.email, email);
                Utils.setPref(ctx, Utils.company, company);
                Utils.setPref(ctx, Utils.designation, designation);
                Utils.setPref(ctx, Utils.contactno, contactno);
                Utils.setPref(ctx, Utils.mobileno, mobileno);
                Utils.setPref(ctx, Utils.officeno, officeno);
                Utils.setPref(ctx, Utils.gender, selected_gender);

                Check_UserName();
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
            et_first_name.setHint("પ્રથમ નામ");
            et_first_name.setTypeface(gujarati);

            et_last_name.setHint("છેલ્લું નામ");
            et_last_name.setTypeface(gujarati);

            et_user_name.setHint("વપરાશકર્તા નામ");
            et_user_name.setTypeface(gujarati);

            et_password.setHint("પાસવર્ડ");
            et_password.setTypeface(gujarati);

            et_repassword.setHint("પાસવર્ડ ફરીથી નાખો");
            et_repassword.setTypeface(gujarati);

            et_email.setHint("ઇમેઇલ(વૈકલ્પિક)");
            et_email.setTypeface(gujarati);

            et_company.setHint("કંપની");
            et_company.setTypeface(gujarati);

            et_designation.setHint("હોદ્દો");
            et_designation.setTypeface(gujarati);

            et_contact_no.setHint("સંપર્ક નંબર");
            et_contact_no.setTypeface(gujarati);

            et_mobile_no.setHint("મોબાઇલ નંબર");
            et_mobile_no.setTypeface(gujarati);

            et_office_no.setHint("ઓફિસ નંબર");
            et_office_no.setTypeface(gujarati);


            b_male.setText("પુરૂષ");
            b_male.setTypeface(gujarati);

            b_female.setText("સ્ત્રી");
            b_female.setTypeface(gujarati);

            tv_title.setText("વ્યક્તિ અને કંપની વિગતો");
            tv_title.setTypeface(gujarati);


        } else if (Utils.Language.equals("H")) {
            et_first_name.setHint("पहला नाम");
            et_first_name.setTypeface(hindi);

            et_last_name.setHint("अंतिम नाम");

            et_last_name.setTypeface(hindi);

            et_user_name.setHint("यूज़र नेम");
            et_user_name.setTypeface(hindi);


            et_password.setHint("पासवर्ड");
            et_password.setTypeface(hindi);

            et_repassword.setHint("पासवर्ड फिर से दर्ज करें ");
            et_repassword.setTypeface(hindi);


            et_email.setHint("ईमेल(ऐच्छिक)");
            et_email.setTypeface(hindi);

            et_company.setHint("कंपनी");
            et_company.setTypeface(hindi);

            et_designation.setHint("पद");
            et_designation.setTypeface(hindi);

            et_contact_no.setHint("संपर्क नंबर");
            et_contact_no.setTypeface(hindi);

            et_mobile_no.setHint("मोबाइल नंबर");
            et_mobile_no.setTypeface(hindi);

            et_office_no.setHint("कार्यालय का नम्बर");
            et_office_no.setTypeface(hindi);

            b_male.setText("नर");
            b_male.setTypeface(hindi);

            b_female.setText("महिला");
            b_female.setTypeface(hindi);

            tv_title.setText("व्यक्ति और कंपनी विवरण");
            tv_title.setTypeface(hindi);

        } else if (Utils.Language.equals("E")) {

            et_first_name.setHint("First Name");
            et_last_name.setHint("Last Name");
            et_user_name.setHint("Username");
            et_password.setHint("Password");
            et_repassword.setHint("Re-Enter Password");
            et_email.setHint("Email(Optional)");
            et_company.setHint("Company");
            et_designation.setHint("Designation");
            et_contact_no.setHint("Contact No");
            et_mobile_no.setHint("Mobile No");
            et_office_no.setHint("Office No");
           b_male.setText("Male");
            b_female.setText("Female");
            tv_title.setText("Person & Comapny Detail");


        }
    }

    public boolean check_validation() {

        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        if (et_first_name.getText().toString().length() == 0) {
            et_first_name.setError("Enter First Name");
            et_first_name.requestFocus();
            et_first_name.setSelection(et_first_name.getText().length());
        } else if (et_last_name.getText().toString().length() == 0) {
            et_last_name.setError("Enter Second Name");
            et_last_name.requestFocus();
            et_last_name.setSelection(et_last_name.getText().length());
        } else if (et_user_name.getText().toString().length() == 0) {
            et_user_name.setError("Enter User Name");
            et_user_name.requestFocus();
            et_user_name.setSelection(et_user_name.getText().length());
        } else if (et_password.getText().toString().length() == 0) {
            et_password.setError("Enter Password");
            et_password.requestFocus();
            et_password.setSelection(et_password.getText().length());
        } else if (et_email.getText().toString().length() != 0) {
            if (!et_email.getText().toString().matches(emailPattern)) {
                et_email.setError("Enter Valid Email Address");
                et_email.requestFocus();
            }
            et_email.setSelection(et_email.getText().length());
        } else if (et_company.getText().toString().length() == 0) {
            et_company.setError("Enter company");
            et_company.requestFocus();
            et_company.setSelection(et_contact_no.getText().length());
        } else if (et_designation.getText().toString().length() == 0) {
            et_designation.setError("Enter Designation");
            et_designation.requestFocus();
            et_designation.setSelection(et_designation.getText().length());
        } else if (et_contact_no.getText().toString().length() == 0) {
            et_contact_no.setError("Enter Contact Number");
            et_contact_no.requestFocus();
            et_contact_no.setSelection(et_contact_no.getText().length());
        } else if (et_mobile_no.getText().toString().length() == 0) {
            et_mobile_no.setError("Enter Mobile Number");
            et_mobile_no.requestFocus();
            et_mobile_no.setSelection(et_mobile_no.getText().length());
        } else if (et_office_no.getText().toString().length() == 0) {
            et_office_no.setError("Enter Office Number");
            et_office_no.requestFocus();
            et_office_no.setSelection(et_office_no.getText().length());
        }

        else if (et_contact_no.getText().toString().length() != 10) {
            et_contact_no.setError("Enter Valid Contact Number");
            et_contact_no.requestFocus();
            et_contact_no.setSelection(et_mobile_no.getText().length());
        }        else if (et_mobile_no.getText().toString().length() != 10) {
            et_mobile_no.setError("Enter Valid Mobile Number");
            et_mobile_no.requestFocus();
            et_mobile_no.setSelection(et_mobile_no.getText().length());
        } else {
            String pass = et_password.getText().toString();
            String repass = et_repassword.getText().toString();
            if (!pass.equals(repass)) {
                et_repassword.setError("Password doesn't match");
                et_repassword.requestFocus();
                et_repassword.setSelection(et_repassword.getText().length());

            } else {
                return true;
            }
        }


        return false;
    }


    private ProgressDialog pDialog;
    String login_url = Utils.main_url + "checkusername.php";

    private void Check_UserName() {

        pDialog = new ProgressDialog(ctx);
        pDialog.setMessage("Please wait...");
        pDialog.setCancelable(false);

        showpDialog();

        String uname = et_user_name.getText().toString();

        // products_list=new ArrayList<Product>();
        login_url += "?username=" + uname;


        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                login_url, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Log.d("Responce ", response.toString());

                try {
                    // Parsing json object response
                    // response will be a json object
                    String ResponceMsg = response.getString("ResponseMsg");
                    int ResponceCode = response.getInt("ResponseCode");
                    String Result = response.getString("Result");

                    if (ResponceCode == 1) {
                        startActivity(new Intent(ctx, Registration_2.class));
                    } else {
                        Toast.makeText(ctx, ResponceMsg, Toast.LENGTH_LONG).show();
                        et_user_name.setError(ResponceMsg);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(ctx,
                            "Error: " + e.getMessage(),
                            Toast.LENGTH_LONG).show();
                }
                hidepDialog();
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("", "Error: " + error.getMessage());
                Toast.makeText(ctx,
                        error.getMessage(), Toast.LENGTH_SHORT).show();
                // hide the progress dialog
                hidepDialog();
            }
        });

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(jsonObjReq);
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
