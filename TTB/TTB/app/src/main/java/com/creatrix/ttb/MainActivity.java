package com.creatrix.ttb;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import com.creatrix.ttb.Fragme.HomeFragment;
import com.creatrix.ttb.app.AppController;
import com.creatrix.ttb.utils.Utils;

public class MainActivity extends AppCompatActivity {

    private EditText inputEmail, inputPassword;
    private TextInputLayout inputLayoutEmail, inputLayoutPassword;
    Context ctx;

    private Fragment contentFragment;
    HomeFragment homeFragment;


    Typeface hindi, gujarati;
    LinearLayout lay_login, lay_signup, lv_english, lv_gujarati, lv_hindi;
    TextView tv_login, tv_signup, tv_english, tv_gujarati, tv_hindi, tv_password;
    boolean chk_login;
    CheckBox chk_password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        hindi = Typeface.createFromAsset(getAssets(), "fonts/DroidHindi.ttf");
        gujarati = Typeface.createFromAsset(getAssets(), "fonts/Lohit-Gujarati.ttf");
        ctx = MainActivity.this;

        chk_login = Utils.getPref(ctx, Utils.Check_login, true);
        if (chk_login) {
        } else {
            startActivity(new Intent(ctx, DashBoard.class));
            finish();
        }

        lay_login = (LinearLayout) findViewById(R.id.lay_login);
        lay_signup = (LinearLayout) findViewById(R.id.lay_signup);

        lv_english = (LinearLayout) findViewById(R.id.lv_english);
        lv_gujarati = (LinearLayout) findViewById(R.id.lv_gujarati);
        lv_hindi = (LinearLayout) findViewById(R.id.lv_hindi);

        tv_login = (TextView) findViewById(R.id.tv_login);
        tv_signup = (TextView) findViewById(R.id.tv_signup);

        tv_english = (TextView) findViewById(R.id.b_english);
        tv_gujarati = (TextView) findViewById(R.id.b_gujarati);
        tv_hindi = (TextView) findViewById(R.id.b_hindi);
        tv_password = (TextView) findViewById(R.id.tv_password);


        inputLayoutEmail = (TextInputLayout) findViewById(R.id.input_layout_email);
        inputLayoutPassword = (TextInputLayout) findViewById(R.id.input_layout_password);

//        btn_login=(Button)findViewById(R.id.btn_login);
//        btnSignUp=(Button)findViewById(R.id.btn_sig_up);
        inputEmail = (EditText) findViewById(R.id.input_email);
        inputPassword = (EditText) findViewById(R.id.input_password);

        chk_password = (CheckBox) findViewById(R.id.chk_password);


        chk_password.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {

                    inputPassword.setInputType(InputType.TYPE_CLASS_TEXT);
                } else {
                    inputPassword.setInputType(InputType.TYPE_CLASS_TEXT |
                            InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }
            }
        });

        LaungageMethod();

        lv_english.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                tv_english.setTextColor(getResources().getColor(R.color.colorPrimary));
                tv_hindi.setTextColor(getResources().getColor(R.color.view_divider_color));
                tv_gujarati.setTextColor(getResources().getColor(R.color.view_divider_color));

                Utils.Language = "E";
                LaungageMethod();
            }
        });

        lv_hindi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv_hindi.setTextColor(getResources().getColor(R.color.colorPrimary));
                tv_english.setTextColor(getResources().getColor(R.color.view_divider_color));
                tv_gujarati.setTextColor(getResources().getColor(R.color.view_divider_color));
                Utils.Language = "H";
                LaungageMethod();
            }
        });

        lv_gujarati.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                tv_gujarati.setTextColor(getResources().getColor(R.color.colorPrimary));
                tv_hindi.setTextColor(getResources().getColor(R.color.view_divider_color));
                tv_english.setTextColor(getResources().getColor(R.color.view_divider_color));
                Utils.Language = "G";
                LaungageMethod();
            }
        });


        inputEmail.addTextChangedListener(new MyTextWatcher(inputEmail));
        inputPassword.addTextChangedListener(new MyTextWatcher(inputPassword));

        lay_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ctx, Registration_1.class));
            }
        });


        lay_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                makeJsonObjectLogin();

            }
        });

        FragmentManager fragmentManager = getSupportFragmentManager();

        if (savedInstanceState != null) {
            if (savedInstanceState.containsKey("content")) {
                String content = savedInstanceState.getString("content");

            }
            if (fragmentManager.findFragmentByTag(HomeFragment.ARG_ITEM_ID) != null) {
                homeFragment = (HomeFragment) fragmentManager
                        .findFragmentByTag(HomeFragment.ARG_ITEM_ID);
                contentFragment = homeFragment;
            }
        } else {
            homeFragment = new HomeFragment();
            switchContent(homeFragment, HomeFragment.ARG_ITEM_ID);
        }

    }


    private void LaungageMethod() {

        if (Utils.Language.equals("G")) {


            //inputLayoutEmail.setHintTextAppearance(R.style.TextAppearence_App_TextInputLayout);
            inputLayoutEmail.setHint("વપરાશકર્તા નામ");
            inputLayoutEmail.setTypeface(gujarati);

            inputLayoutPassword.setHint("પાસવર્ડ");
            inputLayoutPassword.setTypeface(gujarati);

            tv_signup.setText("મફત રજીસ્ટર");
            tv_signup.setTypeface(gujarati);

            tv_login.setText("પ્રવેશ");
            tv_login.setTypeface(gujarati);

            tv_password.setText("પાસવર્ડ બતાવો");
            tv_password.setTypeface(gujarati);

        } else if (Utils.Language.equals("H")) {
            inputLayoutEmail.setHint("यूज़र नेम");
            inputLayoutEmail.setTypeface(hindi);

            inputLayoutPassword.setHint("पासवर्ड");
            inputLayoutPassword.setTypeface(hindi);

            tv_signup.setText("मुफ़्त रजिस्टर");
            tv_signup.setTypeface(hindi);

            tv_login.setText("लॉग इन करें");
            tv_login.setTypeface(hindi);

            tv_password.setText("पासवर्ड दिखाए");
            tv_password.setTypeface(hindi);
        } else if (Utils.Language.equals("E")) {

            inputLayoutEmail.setHint("User Name");
            inputLayoutPassword.setHint("Password");
            tv_signup.setText(R.string.btn_signup);
            tv_login.setText(R.string.btn_login);
            tv_password.setText("Show Password");

        }
    }


    private ProgressDialog pDialog;
    String login_url = Utils.main_url + "login.php";

    private void makeJsonObjectLogin() {

        pDialog = new ProgressDialog(ctx);
        pDialog.setMessage("Please wait...");
        pDialog.setCancelable(false);

        showpDialog();

        String uname = inputEmail.getText().toString().trim();
        String pass = inputPassword.getText().toString().trim();
        // products_list=new ArrayList<Product>();
        login_url += "?username=" + uname + "&password=" + pass;
        login_url = login_url.replaceAll(" ", "%20");
        System.out.println("login url " + login_url);
        // Toast.makeText(ctx,login_url,Toast.LENGTH_LONG).show();

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


                    Toast.makeText(ctx, ResponceMsg, Toast.LENGTH_LONG).show();
                    if (Result.equals("True")) {
                        Utils.setPref(ctx, Utils.Check_login, false);
                        Utils.setPref(ctx, Utils.userid, response.getInt("userid"));
                        startActivity(new Intent(ctx, DashBoard.class));
                        finish();
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

    public void switchContent(Fragment fragment, String tag) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        while (fragmentManager.popBackStackImmediate())
            ;


        Bundle bundle = new Bundle();
        bundle.putString("type", "MainActivity");
        bundle.putInt("id", 0);
        if (fragment != null) {
            FragmentTransaction transaction = fragmentManager
                    .beginTransaction();
            transaction.replace(R.id.content_frame, fragment, tag);
            // Only ProductDetailFragment is added to the back stack.
            if (!(fragment instanceof HomeFragment)) {
                transaction.addToBackStack(tag);
            }
            transaction.commit();
            fragment.setArguments(bundle);
            contentFragment = fragment;
        }
    }


    private boolean validateEmail() {
        String email = inputEmail.getText().toString().trim();

        if (email.isEmpty()) {
            inputEmail.setError(getString(R.string.err_msg_email));
            requestFocus(inputEmail);
            return false;
        } else {
            inputLayoutEmail.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validatePassword() {
        if (inputPassword.getText().toString().trim().isEmpty()) {
            inputPassword.setError(getString(R.string.err_msg_password));
            requestFocus(inputPassword);
            return false;
        } else {
            inputLayoutPassword.setErrorEnabled(false);
        }

        return true;
    }

    private static boolean isValidEmail(String email) {
        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    private class MyTextWatcher implements TextWatcher {

        private View view;

        private MyTextWatcher(View view) {
            this.view = view;
        }

        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void afterTextChanged(Editable editable) {
            switch (view.getId()) {
                case R.id.input_email:
                    validateEmail();
                    break;
                case R.id.input_password:
                    validatePassword();
                    break;
            }
        }
    }
}
