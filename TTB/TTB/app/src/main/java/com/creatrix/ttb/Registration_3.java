package com.creatrix.ttb;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.creatrix.ttb.adapter.PlaceAutocompleteAdapter;
import com.creatrix.ttb.android.common.activities.SampleActivityBase;
import com.creatrix.ttb.utils.Utils;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import com.creatrix.ttb.custom_hashtag.HashtagView;
import com.creatrix.ttb.custom_hashtag.Transformers;

/**
 * Created by Harshu on 13-10-2015.
 */
public class Registration_3 extends SampleActivityBase implements HashtagView.TagsSelectListener, GoogleApiClient.OnConnectionFailedListener, LocationListener {

    LinearLayout linear_next, linear_back;
    Context ctx;

    HashtagView ht_businesstype;
    List<String> businesstype;
    ArrayList<String> selected_businesstype;
    EditText business_other;

    Button btn_range_1, btn_range_2, btn_range_3, btn_range_4;
    protected GoogleApiClient mGoogleApiClient;

    private PlaceAutocompleteAdapter mAdapter;

    private AutoCompleteTextView mAutocompleteView;

    double latitude=0.0, longitude=0.0;

    static double source_latitude;
    static double source_longitude;

    GoogleMap map;
    LatLng position;
    AlertDialog alert;
    private static View promptView;
    Button ok, cancel;

    public static String Address_Final;

    private static final LatLngBounds BOUNDS_GREATER_SYDNEY = new LatLngBounds(
            new LatLng(-34.041458, 150.790100), new LatLng(-33.682247, 151.383362));

    GPSTracker gps;

    LatLng Current_Location;

    GoogleMap current_map;

    TextView tv_get_location,tv_locate_map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        final StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                .detectAll()
                .penaltyLog()
                .build();
        StrictMode.setThreadPolicy(policy);

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, 0 /* clientId */, this)
                .addApi(Places.GEO_DATA_API)
                .build();

        setContentView(R.layout.registration_screen3);
        ctx = Registration_3.this;
        gps = new GPSTracker(ctx);
        selected_businesstype = new ArrayList<String>();
        businesstype = new ArrayList<String>();
        businesstype.add("Manufacture");
        businesstype.add("Retailer");
        businesstype.add("wholeseller");
        businesstype.add("Trading");
        businesstype.add("Other");
        businesstype.add("Transportation");
        businesstype.add("Third Party Job");

        init();


        // Retrieve the AutoCompleteTextView that will display Place suggestions.
        mAutocompleteView = (AutoCompleteTextView)
                findViewById(R.id.autocomplete_places);

        // Register a listener that receives callbacks when a suggestion has been selected
        //mAutocompleteView.setOnItemClickListener(mAutocompleteClickListener);

        // Retrieve the TextViews that will display details and attributions of the selected place.


        // Set up the adapter that will retrieve suggestions from the Places Geo Data API that cover
        // the entire world.
        mAdapter = new PlaceAutocompleteAdapter(this, mGoogleApiClient, BOUNDS_GREATER_SYDNEY,
                null);
        mAutocompleteView.setAdapter(mAdapter);

        // check if GPS enabled
        if (gps.canGetLocation()) {

            latitude = gps.getLatitude();
            longitude = gps.getLongitude();

            Utils.setPref(ctx, Utils.longitude, String.valueOf(latitude));
            Utils.setPref(ctx, Utils.latitude, String.valueOf(longitude));


            Current_Location = new LatLng(latitude, longitude);
            current_map = ((SupportMapFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.map_id)).getMap();
            current_map.setMyLocationEnabled(true);
            // Move the camera instantly to hamburg with a zoom of 15.
            current_map.moveCamera(CameraUpdateFactory.newLatLngZoom(Current_Location, 8));

            // Zoom in, animating the camera.
            current_map.animateCamera(CameraUpdateFactory.zoomTo(8), 2000, null);
            current_map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
            current_map.addMarker(new MarkerOptions()
                    .position(Current_Location)
                    .icon(BitmapDescriptorFactory
                            .defaultMarker(BitmapDescriptorFactory.HUE_YELLOW)));

            getAddress(latitude, longitude);


            Utils.setPref(ctx, Utils.longitude, String.valueOf(latitude));
            Utils.setPref(ctx, Utils.latitude, String.valueOf(longitude));


            current_map.setOnMapClickListener(maplistner);
            tv_locate_map.setTextColor(Color.parseColor("#0099CB"));
            tv_get_location.setTextColor(Color.parseColor("#000000"));
        } else {

            gps.showSettingsAlert();
        }


        tv_locate_map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                tv_locate_map.setTextColor(Color.parseColor("#0099CB"));
                tv_get_location.setTextColor(Color.parseColor("#000000"));

                current_map.setOnMapClickListener(maplistner);
            }
        });

        tv_get_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                tv_locate_map.setTextColor(Color.parseColor("#000000"));
                tv_get_location.setTextColor(Color.parseColor("#0099CB"));




                gps=new GPSTracker(ctx);
                if (gps.canGetLocation()) {
//                    current_map.setOnMapClickListener(null);
                    latitude = gps.getLatitude();
                    longitude = gps.getLongitude();

                    Current_Location = new LatLng(latitude, longitude);
                    current_map = ((SupportMapFragment) getSupportFragmentManager()
                            .findFragmentById(R.id.map_id)).getMap();
                    current_map.setMyLocationEnabled(true);
                    // Move the camera instantly to hamburg with a zoom of 15.
                    current_map.moveCamera(CameraUpdateFactory.newLatLngZoom(Current_Location, 8));

                    // Zoom in, animating the camera.
                    current_map.animateCamera(CameraUpdateFactory.zoomTo(8), 2000, null);
                    current_map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                    current_map.addMarker(new MarkerOptions()
                            .position(Current_Location)
                            .icon(BitmapDescriptorFactory
                                    .defaultMarker(BitmapDescriptorFactory.HUE_YELLOW)));

                    getAddress(latitude, longitude);
                }else{


                        gps.showSettingsAlert();

                }
            }
        });


        mAutocompleteView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                current_map.clear();
                getLatLongFromAddressSource(mAutocompleteView.getText().toString().replaceAll(" ", ""));

                LatLng SOURCE = new LatLng(source_latitude, source_longitude);
                Utils.setPref(ctx, Utils.longitude, String.valueOf(source_longitude));
                Utils.setPref(ctx, Utils.latitude, String.valueOf(source_latitude));


                current_map.setMyLocationEnabled(true);
                // Move the camera instantly to hamburg with a zoom of 15.
                current_map.moveCamera(CameraUpdateFactory.newLatLngZoom(SOURCE, 8));

                // Zoom in, animating the camera.
                current_map.animateCamera(CameraUpdateFactory.zoomTo(8), 2000, null);
                current_map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                current_map.addMarker(new MarkerOptions()
                        .position(SOURCE)

                        .icon(BitmapDescriptorFactory
                                .defaultMarker(BitmapDescriptorFactory.HUE_YELLOW)));
            }
        });

    }


    public GoogleMap.OnMapClickListener maplistner = new GoogleMap.OnMapClickListener() {
        @Override
        public void onMapClick(LatLng latLng) {



            getLatLongFromAddressSource(mAutocompleteView.getText().toString().replaceAll(" ", ""));

            LatLng SOURCE = new LatLng(source_latitude, source_longitude);
            System.out.println("Latitude " + source_latitude + " longitude " + source_latitude);
            // System.out.println("");
            Utils.setPref(ctx, Utils.longitude, String.valueOf(source_longitude));
            Utils.setPref(ctx, Utils.latitude, String.valueOf(source_latitude));
            // Toast.makeText(ctx,source_latitude+" "+source_longitude,Toast.LENGTH_LONG).show();

            LayoutInflater inflater = getLayoutInflater().from(Registration_3.this);

            if (promptView != null) {
                ViewGroup parent = (ViewGroup) promptView.getParent();
                if (parent != null)
                    parent.removeView(promptView);
            }
            try {
                promptView = inflater.inflate(R.layout.locationact, null);
            } catch (InflateException e) {
        /* map is already there, just return view as it is */
            }

            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                    Registration_3.this);
            alertDialogBuilder.setView(promptView);
            alertDialogBuilder.setCancelable(true);


            GooglePlayServicesUtil
                    .isGooglePlayServicesAvailable(getApplicationContext());

            ok = (Button) promptView.findViewById(R.id.b_ok);
            cancel = (Button) promptView.findViewById(R.id.b_cancel);

            map = ((SupportMapFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.map)).getMap();
            map.setMyLocationEnabled(true);
            // Move the camera instantly to hamburg with a zoom of 15.
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(SOURCE, 8));

            // Zoom in, animating the camera.
            map.animateCamera(CameraUpdateFactory.zoomTo(8), 2000, null);
            map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
            map.addMarker(new MarkerOptions()
                    .position(SOURCE)
                    .draggable(true)
                    .icon(BitmapDescriptorFactory
                            .defaultMarker(BitmapDescriptorFactory.HUE_YELLOW)));

            ok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    current_map.clear();

                    // System.out.println("Latitude "+position.latitude+" longitude "+position.longitude);
                    if (position == null) {
                        position = new LatLng(source_latitude, source_longitude);
                    }

                    getAddress(position.latitude, position.longitude);

                    alert.dismiss();
                    map.clear();
                    mAutocompleteView.setText(Address_Final);


                    current_map.setMyLocationEnabled(true);
                    // Move the camera instantly to hamburg with a zoom of 15.
                    current_map.moveCamera(CameraUpdateFactory.newLatLngZoom(position, 8));

                    // Zoom in, animating the camera.
                    current_map.animateCamera(CameraUpdateFactory.zoomTo(8), 2000, null);
                    current_map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                    current_map.addMarker(new MarkerOptions()
                            .position(position)

                            .icon(BitmapDescriptorFactory
                                    .defaultMarker(BitmapDescriptorFactory.HUE_YELLOW)));
                }
            });
            cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    alert.dismiss();
                }
            });

            map.setOnMarkerDragListener(new GoogleMap.OnMarkerDragListener() {

                @Override
                public void onMarkerDragStart(Marker marker) {
                    // TODO Auto-generated method stub
                    // Here your code
                    Toast.makeText(ctx, "Dragging Start",
                            Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onMarkerDragEnd(Marker marker) {
                    // TODO Auto-generated method stub
                    position = marker.getPosition(); //

                    //System.out.println("Latitude "+position.latitude+" longitude "+position.longitude);


                }

                @Override
                public void onMarkerDrag(Marker marker) {
                    // TODO Auto-generated method stub

                    System.out.println("Draagging");
                }
            });

            alert = alertDialogBuilder.create();

            alert.show();

        }
    };

    public static void getLatLongFromAddressSource(String youraddress) {
        String uri = "http://maps.google.com/maps/api/geocode/json?address="
                + youraddress + "&sensor=false";
        HttpGet httpGet = new HttpGet(uri);
        HttpClient client = new DefaultHttpClient();
        HttpResponse response;
        StringBuilder stringBuilder = new StringBuilder();

        try {
            response = client.execute(httpGet);
            //Log.d("jignesh", "hello");
            HttpEntity entity = response.getEntity();
            InputStream stream = entity.getContent();
            int b;
            while ((b = stream.read()) != -1) {
                stringBuilder.append((char) b);
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject = new JSONObject(stringBuilder.toString());

            source_longitude = ((JSONArray) jsonObject.get("results"))
                    .getJSONObject(0).getJSONObject("geometry")
                    .getJSONObject("location").getDouble("lng");

            source_latitude = ((JSONArray) jsonObject.get("results"))
                    .getJSONObject(0).getJSONObject("geometry")
                    .getJSONObject("location").getDouble("lat");


        } catch (JSONException e) {
            e.printStackTrace();
        }


    }


    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

     /*   Log.e("", "onConnectionFailed: ConnectionResult.getErrorCode() = "
                + connectionResult.getErrorCode());
*/
        // TODO(Developer): Check error code and notify the user of error state and resolution.
        Toast.makeText(ctx,
                "Could not connect to Google API Client: Error " + connectionResult.getErrorCode(),
                Toast.LENGTH_SHORT).show();
    }

    private String getAddress(double latitude, double longitude) {

        StringBuilder result = new StringBuilder();
        Utils.setPref(ctx, Utils.longitude, String.valueOf(latitude));
        Utils.setPref(ctx, Utils.latitude, String.valueOf(longitude));

        try {
            Geocoder geocoder = new Geocoder(ctx, Locale.getDefault());
            List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);
            if (addresses.size() > 0) {
                // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
                String city = addresses.get(0).getLocality();
                String state = addresses.get(0).getAdminArea();
                String country = addresses.get(0).getCountryName();

                Address_Final = city + "," + state + "," + country;
                mAutocompleteView.setText(Address_Final);
                 Log.d("jigo", "city" + city + "state" + state + "country" + country);
            }
        } catch (IOException e) {
            android.util.Log.e("tag", e.getMessage());
        }

        return result.toString();
    }

    public boolean onMarkerClick(final Marker marker) {

        if (marker.equals(map)) {
            // handle click here
            // map.getMyLocation();
            System.out.println("Clicked");
            double lat = map.getMyLocation().getLatitude();
            System.out.println("Lat" + lat);
            Toast.makeText(ctx,
                    "Current location " + map.getMyLocation().getLatitude(),
                    Toast.LENGTH_SHORT).show();
        }
        return true;
    }

    @Override
    public void onLocationChanged(Location location) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onProviderDisabled(String provider) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onProviderEnabled(String provider) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        // TODO Auto-generated method stub

    }


    public void init() {

        linear_next = (LinearLayout) findViewById(R.id.linear_next);
        linear_back = (LinearLayout) findViewById(R.id.linear_back);
        ht_businesstype = (HashtagView) findViewById(R.id.ht_businesstype);
        business_other = (EditText) findViewById(R.id.business_other);


        tv_get_location=(TextView)findViewById(R.id.tv_get_location);
        tv_locate_map=(TextView)findViewById(R.id.tv_locate_on_map);

        btn_range_1 = (Button)findViewById(R.id.btn_range_1);
        btn_range_2 = (Button)findViewById(R.id.btn_range_2);
        btn_range_3 = (Button)findViewById(R.id.btn_range_3);
        btn_range_4 = (Button)findViewById(R.id.btn_range_4);

        btn_range_1.setBackgroundResource(R.drawable.round_blue_1);

        btn_range_2.setBackgroundResource(R.drawable.round_blue_2);
        btn_range_3.setBackgroundResource(R.drawable.round_blue_3);
        btn_range_4.setBackgroundResource(R.drawable.round_white_4);



        ht_businesstype.setData(businesstype, Transformers.HASH);
        ht_businesstype.addOnTagSelectListener(this);



        linear_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (selected_businesstype.isEmpty()){
                    Toast.makeText(ctx,"Select Atleast One Business Type",Toast.LENGTH_LONG).show();

                }else {

                    if (selected_businesstype.contains("Other")) {

                        if (business_other.getText().toString().length() == 0 || business_other.getText().toString().equals("")) {

                            business_other.setError("Enter Other Business Type");

                        } else {
                            String othertype = business_other.getText().toString();
                            for (int i = 0; i < selected_businesstype.size(); i++) {
                                String getval = selected_businesstype.get(i);
                                if (getval.equals("Other")) {


                                    selected_businesstype.set(i, othertype);
                                }

                            }


                            Intent ii = new Intent(ctx, Registration_4.class);
                            ii.putStringArrayListExtra("selected_business", selected_businesstype);
                            startActivity(ii);


                        }

                    }else{


                        String othertype = business_other.getText().toString();
                        for (int i = 0; i < selected_businesstype.size(); i++) {
                            String getval = selected_businesstype.get(i);
                            if (getval.equals("Other")) {


                                selected_businesstype.set(i, othertype);
                            }

                        }

                        Intent ii = new Intent(ctx, Registration_4.class);
                        ii.putStringArrayListExtra("selected_business", selected_businesstype);
                        startActivity(ii);

                    }
                }
                // startActivity(new Intent(ctx, Registration_4.class));
            }
        });

        linear_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    @Override
    public void onItemSelected(Object item, boolean isSelected) {

        if (isSelected) {
            selected_businesstype.add(item.toString());
        } else {
            selected_businesstype.remove(item.toString());
        }

        if (selected_businesstype.contains("Other")) {

            business_other.setVisibility(View.VISIBLE);
            business_other.requestFocus();
        } else {
            business_other.setVisibility(View.GONE);
        }


    }


    @Override
    protected void onRestart() {
        super.onRestart();
        selected_businesstype = new ArrayList<String>();
        ht_businesstype.setData(businesstype, Transformers.HASH);
        business_other.setVisibility(View.GONE);
        business_other.setText("");

    }
}
