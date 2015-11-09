package com.creatrix.ttb;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.creatrix.ttb.Obj.Itemgetset;
import com.creatrix.ttb.app.AppController;
import com.creatrix.ttb.utils.PredicateLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import com.creatrix.ttb.utils.Utils;

/**
 * Created by Harshu on 14-10-2015.
 */
public class Registration_4 extends AppCompatActivity {


    Context ctx;
    LinearLayout linear_back, linear_submit;

    private ListView listView;
    // private CustomListAdapter_Product adapter;
    //HashMap<String, List<String>> hashMap_product = new HashMap<String, List<String>>();
    private ArrayList<Itemgetset> Tagmodels;
    ArrayList<String> get_selected_bussinesstype = new ArrayList<String>();

    ArrayList<Integer> selected_product = new ArrayList<Integer>();
    LinearLayout linear_category;

    Button btn_range_1, btn_range_2, btn_range_3, btn_range_4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration_screen_4);
        ctx = Registration_4.this;

        pDialog = new ProgressDialog(ctx);
        pDialog.setMessage("Please wait...");
        pDialog.setCancelable(true);

        Intent ii = getIntent();
        get_selected_bussinesstype = ii.getStringArrayListExtra("selected_business");

        Tagmodels = new ArrayList<Itemgetset>();
        if (get_selected_bussinesstype.size() == 1) {

            if (get_selected_bussinesstype.contains("Third Party Job")) {
                makeJsonThirdPartyProduct();
                //   Toast.makeText(ctx,"Third Party Job",Toast.LENGTH_LONG).show();
            } else if (get_selected_bussinesstype.contains("Transportation")) {

                Toast.makeText(ctx, "Transportation", Toast.LENGTH_LONG).show();
            } else {
                makeJsonProduct();
                // Toast.makeText(ctx,"All Product 1",Toast.LENGTH_LONG).show();

            }
        } else if (get_selected_bussinesstype.contains("Third Party Job")) {
            makeJsonProduct();

        } else {
            makeJsonProduct();
        }

        init();
    }


    public void init() {

        linear_back = (LinearLayout) findViewById(R.id.linear_back);
        linear_submit = (LinearLayout) findViewById(R.id.linear_submit);

        btn_range_1 = (Button) findViewById(R.id.btn_range_1);
        btn_range_2 = (Button) findViewById(R.id.btn_range_2);
        btn_range_3 = (Button) findViewById(R.id.btn_range_3);
        btn_range_4 = (Button) findViewById(R.id.btn_range_4);

        btn_range_1.setBackgroundResource(R.drawable.round_blue_1);

        btn_range_2.setBackgroundResource(R.drawable.round_blue_2);
        btn_range_3.setBackgroundResource(R.drawable.round_blue_3);
        btn_range_4.setBackgroundResource(R.drawable.round_blue_4);

        linear_category = (LinearLayout) findViewById(R.id.linear_category);
//        listView = (ListView) findViewById(R.id.lv_category);

        linear_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        linear_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selected_product.isEmpty() || selected_product == null) {
                    Toast.makeText(ctx, "Select at least one product", Toast.LENGTH_LONG).show();
                } else {

                    makeRegistration();
                }

                //startActivity(new Intent(ctx, DashBoard.class));
            }
        });
    }

    private String ProductUrl = Utils.main_url + "formproducts.php";

    /**
     * Method to make json object request where json response starts wtih {
     */

    ArrayList<String> sub_cat = new ArrayList<String>();
    ArrayList<Integer> sub_cat_id = new ArrayList<Integer>();

    private void makeJsonProduct() {

        showpDialog();


        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                ProductUrl, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {


                try {

                    JSONArray product = response.getJSONArray("products");


                    for (int i = 0; i < product.length(); i++) {
                        JSONObject jobject = product.getJSONObject(i);
                        String category_name = jobject.getString("categoryname");
                        int cat_id = jobject.getInt("categoryid");

                        Itemgetset tvmodel = new Itemgetset();
                        tvmodel.setCat_name(category_name);

                        JSONArray sub_array = jobject.getJSONArray("subcategories");

                        sub_cat = new ArrayList<String>();
                        sub_cat_id = new ArrayList<Integer>();
                        for (int j = 0; j < sub_array.length(); j++) {
                            JSONObject sub_jsonObject = sub_array.getJSONObject(j);
                            String sub_catname = sub_jsonObject.getString("categoryname");
                            int sub_id = sub_jsonObject.getInt("categoryid");
                            sub_cat.add(sub_catname);
                            sub_cat_id.add(sub_id);
                        }

                        tvmodel.setSub_cate_name(sub_cat);
                        tvmodel.setSub_cate_id(sub_cat_id);

                        System.out.println("Cat_Name " + category_name + " " + sub_cat);


                        Tagmodels.add(tvmodel);
                    }


                    if (get_selected_bussinesstype.size() != 1 && get_selected_bussinesstype.contains("Third Party Job")) {


                        makeJsonThirdPartyProduct();
                    } else {

                        for (int l = 0; l < Tagmodels.size(); l++) {
                            LinearLayout linear = new LinearLayout(ctx);
                            linear.setOrientation(LinearLayout.VERTICAL);
                            linear.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                            linear.setGravity(Gravity.CENTER);
                            TextView tv_title = new TextView(ctx);

                            tv_title.setTextAppearance(ctx, android.R.style.TextAppearance_Medium);
                            LinearLayout.LayoutParams llp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                            llp.setMargins(8, 8, 8, 8); // llp.setMargins(left, top, right, bottom);
                            tv_title.setLayoutParams(llp);


                            tv_title.setText(Tagmodels.get(l).getCat_name());
                            linear.addView(tv_title);
                            ArrayList<Integer> get_sub_cat_id = new ArrayList<Integer>();
                            ArrayList<String> get_sub_cat_name = new ArrayList<String>();
                            get_sub_cat_name = Tagmodels.get(l).getSub_cate_name();
                            get_sub_cat_id = Tagmodels.get(l).getSub_cat_id();
                            PredicateLayout lay_predicate = new PredicateLayout(ctx);
                            for (int k = 0; k < get_sub_cat_id.size(); k++) {
                                CheckBox button = new CheckBox(ctx);
                                button.setId(get_sub_cat_id.get(k));
                                button.setText(get_sub_cat_name.get(k));
                                Drawable transparentDrawable = new ColorDrawable(Color.TRANSPARENT);
                                button.setButtonDrawable(transparentDrawable);
                                button.setBackgroundResource(R.drawable.custom_checkbox_two);
                                button.setLayoutParams(new AbsListView.LayoutParams(10, 10));

                                button.setPadding(25, 20, 25, 20);
                                button.setTextColor(getResources().getColorStateList(R.color.text_selector));

                                lay_predicate.addView(button);

                                button.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                    @Override
                                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                                        if (isChecked) {
                                            selected_product.add(buttonView.getId());
                                        } else {
                                            selected_product.remove(new Integer(buttonView.getId()));
                                        }

                                    }
                                });


                            }

                            linear.addView(lay_predicate);
                            linear_category.addView(linear);
                        }


                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(),
                            "Error: " + e.getMessage(),
                            Toast.LENGTH_LONG).show();
                }
                hidepDialog();
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("", "Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_SHORT).show();
                // hide the progress dialog
                // hide the progress dialog
                hidepDialog();
            }
        });

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(jsonObjReq);
    }

    // Progress dialog
    private ProgressDialog pDialog;


    private void showpDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hidepDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }


    String thirdparty_url = Utils.main_url + "getthirdpartycategories.php";

    private void makeJsonThirdPartyProduct() {

        showpDialog();

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                thirdparty_url, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                // Log.d("Responce ", response.toString());

                try {
                    // Parsing json object response

                    JSONArray product = response.getJSONArray("categories");

                    for (int i = 0; i < product.length(); i++) {
                        JSONObject jobject = product.getJSONObject(i);
                        String category_name = jobject.getString("categoryname");
                        int cat_id = jobject.getInt("categoryid");

                        Itemgetset tvmodel = new Itemgetset();
                        tvmodel.setCat_name(category_name);

                        JSONArray sub_array = jobject.getJSONArray("subcategories");

                        sub_cat = new ArrayList<String>();
                        sub_cat_id = new ArrayList<Integer>();
                        for (int j = 0; j < sub_array.length(); j++) {
                            JSONObject sub_jsonObject = sub_array.getJSONObject(j);
                            String sub_catname = sub_jsonObject.getString("categoryname");
                            int sub_id = sub_jsonObject.getInt("categoryid");
                            // System.out.println("Cat_Name Sub " + category_name + ", " + sub_catname + ", " + sub_cat_id);
                            sub_cat.add(sub_catname);
                            sub_cat_id.add(sub_id);

                        }


                        tvmodel.setSub_cate_name(sub_cat);
                        tvmodel.setSub_cate_id(sub_cat_id);
                        Tagmodels.add(tvmodel);
                    }


                    for (int l = 0; l < Tagmodels.size(); l++) {
                        LinearLayout linear = new LinearLayout(ctx);
                        linear.setOrientation(LinearLayout.VERTICAL);
                        linear.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                        linear.setGravity(Gravity.CENTER_VERTICAL);
                        TextView tv_title = new TextView(ctx);
                        tv_title.setTextAppearance(ctx, android.R.style.TextAppearance_Medium);
                        LinearLayout.LayoutParams llp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                        llp.setMargins(8, 8, 8, 8); // llp.setMargins(left, top, right, bottom);
                        tv_title.setLayoutParams(llp);
                        tv_title.setText(Tagmodels.get(l).getCat_name());


                        linear.addView(tv_title);
                        ArrayList<Integer> get_sub_cat_id = new ArrayList<Integer>();
                        ArrayList<String> get_sub_cat_name = new ArrayList<String>();
                        get_sub_cat_name = Tagmodels.get(l).getSub_cate_name();
                        get_sub_cat_id = Tagmodels.get(l).getSub_cat_id();
                        PredicateLayout lay_predicate = new PredicateLayout(ctx);
                        for (int k = 0; k < get_sub_cat_id.size(); k++) {
                            CheckBox button = new CheckBox(ctx);
                            button.setId(get_sub_cat_id.get(k));
                            button.setText(get_sub_cat_name.get(k));
                            Drawable transparentDrawable = new ColorDrawable(Color.TRANSPARENT);
                            button.setButtonDrawable(transparentDrawable);
                            button.setBackgroundResource(R.drawable.custom_checkbox_two);
                            button.setLayoutParams(new AbsListView.LayoutParams(10, 10));

                            button.setPadding(25, 20, 25, 20);
                            button.setTextColor(getResources().getColorStateList(R.color.text_selector));

                            lay_predicate.addView(button);

                            button.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                @Override
                                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                                    if (isChecked) {
                                        selected_product.add(buttonView.getId());
                                    } else {
                                        selected_product.remove(new Integer(buttonView.getId()));
                                    }

                                }
                            });


                        }

                        linear.addView(lay_predicate);
                        linear_category.addView(linear);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(),
                            "Error: " + e.getMessage(),
                            Toast.LENGTH_LONG).show();
                }
                hidepDialog();
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("", "Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_SHORT).show();
                // hide the progress dialog
                hidepDialog();
            }
        });

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(jsonObjReq);
    }


    String submitUrl = Utils.main_url + "register.php";

    private void makeRegistration() {

        String firstname = Utils.getPref(ctx, Utils.firstname, "");
        String lastname = Utils.getPref(ctx, Utils.lastname, "");
        String username = Utils.getPref(ctx, Utils.username, "");
        String password = Utils.getPref(ctx, Utils.password, "");
        String gender = Utils.getPref(ctx, Utils.gender, "");
        String email = Utils.getPref(ctx, Utils.email, "");
        String company = Utils.getPref(ctx, Utils.company, "");
        String shopno = Utils.getPref(ctx, Utils.shopno, "");
        String phase = Utils.getPref(ctx, Utils.phase, "");
        String flore = Utils.getPref(ctx, Utils.flore, "");
        String marketname = Utils.getPref(ctx, Utils.marketname, "");
        String road = Utils.getPref(ctx, Utils.road, "");
        String landmark = Utils.getPref(ctx, Utils.landmark, "");
        String area = Utils.getPref(ctx, Utils.area, "");
        String city = Utils.getPref(ctx, Utils.city, "");
        String district = Utils.getPref(ctx, Utils.district, "");
        String state = Utils.getPref(ctx, Utils.state, "");
        String pincode = Utils.getPref(ctx, Utils.pincode, "");
        String latitude = Utils.getPref(ctx, Utils.latitude, "");
        String longitude = Utils.getPref(ctx, Utils.longitude, "");
        // String busiesstype = Utils.getPref(ctx, Utils.businesstype, "");
        // String product = Utils.getPref(ctx, Utils.product, "");
        String designation = Utils.getPref(ctx, Utils.designation, "");
        String contactno = Utils.getPref(ctx, Utils.contactno, "");
        String mobileno = Utils.getPref(ctx, Utils.mobileno, "");
        String officeno = Utils.getPref(ctx, Utils.officeno, "");


        String businesstype = get_selected_bussinesstype.toString();
        businesstype = businesstype.replace("[", "");
        businesstype = businesstype.replace("]", "");
        String product = selected_product.toString();
        product = product.replace("[", "");
        product = product.replace("]", "");
        businesstype = businesstype.replace(" ", "");
        product = product.replace(" ", "");


        showpDialog();

        submitUrl += "?firstname=" + firstname + "&lastname=" + lastname + "&username=" + username + "&password=" + password + "&gender=" + gender + "&email=" + email +
                "&company=" + company + "&shopno=" + shopno + "&phase=" + phase + "&flore=" + flore + "&marketname=" + marketname + "&road=" + road + "&landmark=" + landmark
                + "&area=" + area + "&city=" + city + "&district=" + district + "&state=" + state + "&pincode=" + pincode + "&latitude=" + latitude + "&longitude=" + longitude
                + "&businesstype=" + businesstype + "&product=" + product + "&designation=" + designation + "&contactno=" + contactno + "&mobileno=" + mobileno + "&officeno=" + officeno;

        System.out.println("Responce " + submitUrl);
        submitUrl = submitUrl.replaceAll(" ", "%20");
        System.out.println("Responce " + submitUrl);
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                submitUrl, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {

                System.out.println("Responce " + response.toString());

                try {

                    String result = response.getString("Result");
                    String responce_msg = response.getString("ResponseMsg");
                    if (result.equals("False")) {

                        int responcecode = response.getInt("ResponseCode");
                        // Toast.makeText(ctx,responce_msg,Toast.LENGTH_LONG).show();
                    } else {
                        int userid = response.getInt("userid");
                        // Toast.makeText(ctx,userid+"",Toast.LENGTH_LONG).show();
                        Utils.setPref(ctx, Utils.Check_login, false);
                        Utils.setPref(ctx, Utils.userid, userid);
                        startActivity(new Intent(ctx, DashBoard.class));
                        finish();
                    }

                    //  Toast.makeText(ctx, responce_msg, Toast.LENGTH_LONG).show();


                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(),
                            "Error: " + e.getMessage(),
                            Toast.LENGTH_LONG).show();
                }

                hidepDialog();
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("", "Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_SHORT).show();
                // hide the progress dialog
                // hide the progress dialog
                hidepDialog();
            }
        });

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(jsonObjReq);
    }

}
