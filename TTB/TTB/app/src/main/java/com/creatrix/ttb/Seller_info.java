package com.creatrix.ttb;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
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
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Harshu on 26-10-2015.
 */
public class Seller_info extends AppCompatActivity {


    TextView tv_seller_first_name,  tv_seller_company, tv_seller_address,tv_distance, tv_business_type, tv_category, tv_transportation;

    int seller_id, user_id;
    GPSTracker gps;

    GoogleMap mMap;
Context ctx;
    LinearLayout linear_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    setContentView(R.layout.seller_info);
        ctx = Seller_info.this;

        gps=new GPSTracker(ctx);


        Intent ii=getIntent();
        seller_id = ii.getIntExtra("seller_id", 0);
        user_id = Utils.getPref(ctx, Utils.userid, 0);


        tv_seller_first_name = (TextView) findViewById(R.id.tv_seller_fname);

        tv_seller_company = (TextView) findViewById(R.id.tv_seller_company);
        tv_transportation = (TextView) findViewById(R.id.tv_transport);
        tv_seller_address = (TextView) findViewById(R.id.tv_seller_address);
        tv_business_type = (TextView) findViewById(R.id.tv_seller_business_type);
        tv_distance = (TextView) findViewById(R.id.tv_distance);
        linear_back=(LinearLayout)findViewById(R.id.linear_back);
        linear_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        // Gets the MapView from the XML layout and creates it
        FragmentManager myFM = getSupportFragmentManager();

        final SupportMapFragment myMAPF = (SupportMapFragment) myFM
                .findFragmentById(R.id.map);
       /* myMAPF.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                mMap=googleMap;
            }
        });
*/
      /*  GoogleMap map = ((SupportMapFragment) getFragmentManager()
                .findFragmentById(R.id.map)).getMap();
*/

        if (gps.canGetLocation()) {

            latitued = gps.getLatitude();
            longitude = gps.getLongitude();

            Load_SellerDetail();
        } else {
            showSettingsAlert();
        }
    }


    /**
     * Method to make json object request where json response starts wtih {
     */
    ProgressDialog pDialog;
    String url = Utils.main_url + "getsellerinfo.php";
    double latitued, longitude;
    String fname, lname, company, address, businesstype, product, contact_num, mobile_num, office_num,distance,transportation;
    double lat,longi;
    LatLng Current_Location;


    private void Load_SellerDetail() {

        pDialog = new ProgressDialog(ctx);
        pDialog.setMessage("Please wait...");
        pDialog.setCancelable(false);

        showpDialog();


        url += "?sellerid=" + seller_id + "&id=" + user_id + "&latitude=" + latitued + "&longitude=" + longitude;

        System.out.println("Url => " + url);
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                url, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Log.d("Responce ", response.toString());

                try {
                    // Parsing json object response
                    // response will be a json object

                    JSONArray jsonArray = response.getJSONArray("person");
                    for (int j = 0; j < jsonArray.length(); j++) {
                        JSONObject jobj = jsonArray.getJSONObject(j);
                        fname = jobj.getString("firstname");
                        lname = jobj.getString("lastname");
                        company = jobj.getString("company");
                        address = jobj.getString("address");
                        businesstype = jobj.getString("businesstype");
                        product = jobj.getString("product");
                        contact_num = jobj.getString("contactno");
                        mobile_num = jobj.getString("mobileno");
                        office_num = jobj.getString("officeno");
                        lat=jobj.getDouble("latitude");
                        longi=jobj.getDouble("longitude");
                        distance=jobj.getString("distance");
                        transportation=jobj.getString("transportation");


                    }

                    tv_seller_first_name.setText(fname+" "+lname);

                    tv_seller_company.setText(company);
                    tv_seller_address.setText(address);
                    tv_seller_address.setText(distance);

                    tv_business_type.setText(businesstype);
                    tv_transportation.setText(transportation);


                /*    Current_Location = new LatLng(lat, longi);

                    mMap.setMyLocationEnabled(true);
                    // Move the camera instantly to hamburg with a zoom of 15.
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(Current_Location, 8));

                    // Zoom in, animating the camera.
                    mMap.animateCamera(CameraUpdateFactory.zoomTo(8), 2000, null);
                    mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                    mMap.addMarker(new MarkerOptions()
                            .position(Current_Location).snippet(address)
                            .icon(BitmapDescriptorFactory
                                    .defaultMarker(BitmapDescriptorFactory.HUE_YELLOW)));
*/
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


    /**
     * Function to show settings alert dialog
     * On pressing Settings button will lauch Settings Options
     */
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

            latitued = gps.getLatitude();
            longitude = gps.getLongitude();
            Load_SellerDetail();

        } else {
            showSettingsAlert();
        }

    }


}
