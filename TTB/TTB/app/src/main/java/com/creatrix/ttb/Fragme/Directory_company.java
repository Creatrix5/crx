package com.creatrix.ttb.Fragme;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import com.creatrix.ttb.GPSTracker;
import com.creatrix.ttb.Obj.Dir_Company;

import com.creatrix.ttb.Obj.dir_product;
import com.creatrix.ttb.R;
import com.creatrix.ttb.adapter.Directory_Company_Adapter;
import com.creatrix.ttb.app.AppController;
import com.creatrix.ttb.app.Helper;
import com.creatrix.ttb.utils.Utils;

/**
 * Created by Harshu on 27-10-2015.
 */
public class Directory_company extends Fragment {

Context ctx;
    EditText et_company_name,et_company_area;
    Button btn_search;
    double latitude,longitude;
    GPSTracker gps;
    Directory_Company_Adapter adapter;
    ListView listView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.directory_company, container, false);
        ctx = getActivity();


        init(view);

        gps = new GPSTracker(ctx);

        // check if GPS enabled
        if (gps.canGetLocation()) {

            latitude = gps.getLatitude();
            longitude = gps.getLongitude();

            makeJsonObject_Company();
        } else {
            showSettingsAlert();
        }

        return view;

    }

    public void init(View v){

        et_company_name = (EditText) v.findViewById(R.id.et_company_name);
        et_company_area = (EditText) v.findViewById(R.id.et_area);
        btn_search=(Button)v.findViewById(R.id.btn_company_search);
        listView=(ListView)v.findViewById(R.id.list_directory_company);

        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gps = new GPSTracker(ctx);

                // check if GPS enabled
                if (gps.canGetLocation()) {

                    latitude = gps.getLatitude();
                    longitude = gps.getLongitude();

                    makeJsonObject_Company();
                } else {
                    showSettingsAlert();
                }

            }
        });
    }



    List<dir_product> dir_company = new ArrayList<dir_product>();

    private ProgressDialog pDialog;
    String dir_pro_url = Utils.main_url + "getdirectorycompany.php";

    private void makeJsonObject_Company() {

        pDialog = new ProgressDialog(ctx);
        pDialog.setMessage("Please wait...");
        pDialog.setCancelable(false);

        showpDialog();

        dir_company = new ArrayList<dir_product>();


        String cname = et_company_name.getText().toString();

        String area = et_company_area.getText().toString();

        // products_list=new ArrayList<Product>();
        dir_pro_url += "?id=" + Utils.getPref(ctx, Utils.userid, 0) + "&name=" + cname +"&area=" + area + "&latitude="+latitude+"&longitude="+longitude;
        System.out.println("Url ==> " + dir_pro_url);
        // Toast.makeText(ctx,login_url,Toast.LENGTH_LONG).show();

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                dir_pro_url, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Log.d("Responce ", response.toString());

                try {
                    // Parsing json object response
                    // response will be a json object
                    String ResponceMsg = response.getString("ResponseMsg");
                    int ResponceCode = response.getInt("ResponseCode");
                    String Result = response.getString("Result");


                 //
                 //   Toast.makeText(ctx, ResponceMsg, Toast.LENGTH_LONG).show();
                    if (Result.equals("True")) {

                        JSONArray jsonArray = response.getJSONArray("company");
                        for (int k = 0; k < jsonArray.length(); k++) {
                            JSONObject jobj = jsonArray.getJSONObject(k);

                            dir_product dprod = new dir_product();
                            dprod.setUserid(jobj.getInt("userid"));
                            dprod.setCompany(jobj.getString("company"));
                            dprod.setAddress(jobj.getString("address"));
                            dprod.setOwner(jobj.getString("owner"));
                            dprod.setLatituted(jobj.getString("latitude"));
                            dprod.setLongituted(jobj.getString("longitude"));
                            dprod.setMobileno(jobj.getString("mobileno"));

                            dprod.setIs_favority(jobj.getInt("isfavourite"));
                            dprod.setDistance(jobj.getString("distance"));

                            dir_company.add(dprod);
                        }


                        adapter=new Directory_Company_Adapter(ctx,dir_company);
                        listView.setAdapter(adapter);
                        Helper.getListViewSize(listView);
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



    public void showSettingsAlert() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(ctx);

        // Setting Dialog Title
        alertDialog.setTitle("GPS is settings");

        // Setting Dialog Message
        alertDialog.setMessage("GPS is not enabled. Do you want to go to settings menu?");

        // On pressing Settings button
        alertDialog.setPositiveButton("Settings", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
              /*  Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
*/
                startActivityForResult(new Intent(
                        Settings.ACTION_LOCATION_SOURCE_SETTINGS), 1);
            }
        });

        // on pressing cancel button
        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        // Showing Alert Message
        alertDialog.show();
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Toast.makeText(ctx, data + " "+resultCode, Toast.LENGTH_LONG).show();
        // if (resultCode == 0) {

        gps = new GPSTracker(ctx);

        // check if GPS enabled
        if (gps.canGetLocation()) {

            latitude = gps.getLatitude();
            longitude = gps.getLongitude();
            makeJsonObject_Company();

        } else {
            showSettingsAlert();
        }

    }


}
