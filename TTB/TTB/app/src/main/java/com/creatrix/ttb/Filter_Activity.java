package com.creatrix.ttb;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CheckedTextView;
import android.widget.CompoundButton;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.creatrix.ttb.Obj.Filter_1_Item;
import com.creatrix.ttb.Obj.Filter_2_Item;
import com.creatrix.ttb.android.common.logger.Log;
import com.creatrix.ttb.app.AppController;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by Harshu on 05-11-2015.
 */
public class Filter_Activity extends Activity {

    ListView lv_mainactivity, lv_subactivity;
    Filter_1_Adapter main_adapter;
    Filter_2_Adapter sub_adapter;
    ArrayList<Filter_1_Item> main_cat;
    TextView tv_select_cat;
    int back_pos;
    String back_string;
    ArrayList<String> back_array = new ArrayList<String>();
    ArrayAdapter<String> adapter;
    LinearLayout linear_clear, linear_apply,linear_back;
    Context ctx;
    int cat_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.filter_activity);
        ctx = Filter_Activity.this;

        lv_mainactivity = (ListView) findViewById(R.id.lv_maincat);
        lv_subactivity = (ListView) findViewById(R.id.lv_subcat);
        tv_select_cat = (TextView) findViewById(R.id.tv_select_cat);
        linear_clear = (LinearLayout) findViewById(R.id.linear_clear);
        linear_apply = (LinearLayout) findViewById(R.id.linear_apply);
        linear_back=(LinearLayout)findViewById(R.id.linear_back);

        linear_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filter_map.clear();
                //
                //    Toast.makeText(ctx,sub_adapter_2+"",Toast.LENGTH_LONG).show();
                if (sub_adapter_2 != null)
                    sub_adapter_2.notifyDataSetChanged();
            }
        });

        linear_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        linear_apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String filter_string = "";

                for (int k = 0; k < filter_map.size(); k++) {
                    HashMap<String, ArrayList<String>> map = filter_map.get(k);
                    //  System.out.println("Map "+map);

                    Set mapSet = (Set) map.entrySet();
                    Iterator mapIterator = mapSet.iterator();
                    while (mapIterator.hasNext()) {
                        Map.Entry mapEntry = (Map.Entry) mapIterator.next();
                        ArrayList<String> value = (ArrayList<String>) mapEntry.getValue();
                        String key = (String) mapEntry.getKey();
                        //System.out.println("Value "+value);

                        if (value == null || value.isEmpty()) {
                        } else {
                            filter_string += "&" + key + "=";
                            for (int l = 0; l < value.size(); l++) {
                                filter_string += value.get(l) + ",";

                            }
                        }
                        if (filter_string.length() > 0 && filter_string.charAt(filter_string.length() - 1) == ',') {
                            filter_string = filter_string.substring(0, filter_string.length() - 1);
                        }

                        System.out.println();
                        System.out.println("String " + filter_string);
                    }
                }

                Intent ii = new Intent(ctx, Category_Product.class);
                ii.putExtra("id", 0);
                ii.putExtra("filter_option", filter_string);
                ii.putExtra("SearchText", "");
                ii.putExtra("Number_Of_Filter", filter_map.size());
                startActivity(ii);

            }
        });

        lv_mainactivity.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                view.setSelected(true);
            }
        });

        Intent ii = getIntent();
        cat_id = ii.getIntExtra("cat_id", 0);

        if (cat_id != 0) {

            Load_Filter(cat_id);
        } else {


            main_cat = new ArrayList<Filter_1_Item>();
            Filter_1_Item catitem = new Filter_1_Item();
            catitem.setFiltername("Category");
            main_cat.add(catitem);

            main_adapter = new Filter_1_Adapter(ctx, main_cat);
            lv_mainactivity.setAdapter(main_adapter);
            main_adapter.notifyDataSetChanged();

            back_array.add("Sub Category");
            Load_Sub_Cat(0);
        }
        tv_select_cat.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Load_Sub_Cat(back_pos);
                if (back_array.isEmpty() || back_array == null
                        || back_array.size() == 1) {

                } else {
                    tv_select_cat.setText(back_array.get(back_array.size() - 2));
                    back_array.remove(back_array.size() - 1);
                }
            }
        });


    }


    ProgressDialog pDialog;
    ArrayList<Filter_2_Item> sub_item_array;

    public void Load_Sub_Cat(int sub_cat_item) {

        pDialog = new ProgressDialog(ctx);
        pDialog.setMessage("Please wait...");
        pDialog.setCancelable(false);

        showpDialog();

        sub_item_array = new ArrayList<Filter_2_Item>();

        // products_list=new ArrayList<Product>();
        String sub_url = "http://www.vintechdreamlab.com/demo/ttb/protected/getsubcategoriesfilter.php?categoryid="
                + sub_cat_item;
        System.out.println("Url ==> " + sub_url);

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(
                Request.Method.POST, sub_url, null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("Responce ", response.toString());

                        try {
                            // Parsing json object response
                            // response will be a json object
                            String ResponceMsg = response
                                    .getString("ResponseMsg");
                            int ResponceCode = response.getInt("ResponseCode");
                            String Result = response.getString("Result");

                            //
                            // Toast.makeText(ctx, ResponceMsg,
                            // Toast.LENGTH_LONG).show();
                            if (Result.equals("True")) {

                                JSONArray jsonArray = response
                                        .getJSONArray("categories");
                                for (int k = 0; k < jsonArray.length(); k++) {
                                    JSONObject jobj = jsonArray
                                            .getJSONObject(k);

                                    Filter_2_Item dprod = new Filter_2_Item();
                                    dprod.setId(jobj.getInt("categoryid"));
                                    dprod.setName(jobj
                                            .getString("categoryname"));
                                    dprod.setParent_id(jobj.getInt("parentid"));
                                    dprod.setChildivailable(jobj
                                            .getString("lastchild"));

                                    sub_item_array.add(dprod);
                                }

                                sub_adapter = new Filter_2_Adapter(ctx,
                                        sub_item_array);
                                lv_subactivity.setAdapter(sub_adapter);
                                sub_adapter.notifyDataSetChanged();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(ctx, "Error: " + e.getMessage(),
                                    Toast.LENGTH_LONG).show();
                        }
                        hidepDialog();
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("", "Error: " + error.getMessage());
                Toast.makeText(ctx, error.getMessage(),
                        Toast.LENGTH_SHORT).show();
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


    public class Filter_2_Adapter extends BaseAdapter {
        Context context;
        List<Filter_2_Item> catItems;

        public Filter_2_Adapter(Context context, List<Filter_2_Item> items) {
            this.context = context;
            this.catItems = items;

        }

        /* private view holder class */
        private class ViewHolder {
            TextView txtTitle;
        }

        public View getView(final int position, View convertView,
                            ViewGroup parent) {
            ViewHolder holder = null;

            LayoutInflater mInflater = (LayoutInflater) context
                    .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            if (convertView == null) {
                convertView = mInflater.inflate(R.layout.filter_list_item,
                        null);
                holder = new ViewHolder();

                holder.txtTitle = (TextView) convertView
                        .findViewById(R.id.tv_title);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            Filter_2_Item catItem = (Filter_2_Item) getItem(position);
            holder.txtTitle.setText(catItem.getName());
            holder.txtTitle.setMaxLines(2);
            holder.txtTitle.setEllipsize(TextUtils.TruncateAt.END);
            convertView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    Filter_2_Item catItem = (Filter_2_Item) getItem(position);
                    // Toast.makeText(MainActivity.this, catItem.getId(),
                    // Toast.LENGTH_LONG).show();
                    if (catItem.getChildivailable().equals("true")) {

                        Load_Filter(catItem.getId());

                    } else {

                        if (back_array == null || back_array.isEmpty()) {
                        } else {
                            Load_Sub_Cat(catItem.getId());
                            tv_select_cat.setText(catItem.getName());
                        }
                    }

                    if (!back_array.contains(catItem.getName())) {
                        back_pos = catItem.getParent_id();
                        back_array.add(catItem.getName());

                    }

                }

            });

            return convertView;
        }

        @Override
        public int getCount() {
            return catItems.size();
        }

        @Override
        public Object getItem(int position) {
            return catItems.get(position);
        }

        @Override
        public long getItemId(int position) {
            return catItems.indexOf(getItem(position));
        }

    }

    ArrayList<Filter_1_Item> favority_item;
    Filter_2_adapter_2 sub_adapter_2;


    public void Load_Filter(int sub_cat_item) {

        pDialog = new ProgressDialog(ctx);
        pDialog.setMessage("Please wait...");
        pDialog.setCancelable(false);

        showpDialog();

        favority_item = new ArrayList<Filter_1_Item>();
        // products_list=new ArrayList<Product>();
        String filter_url = "http://www.vintechdreamlab.com/demo/ttb/protected/getfilter.php?categoryid="
                + sub_cat_item;
        System.out.println("Url ==> " + filter_url);

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(
                Request.Method.POST, filter_url, null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("Responce ", response.toString());
                        try {
                            // Parsing json object response
                            // response will be a json object
                            String ResponceMsg = response
                                    .getString("ResponseMsg");
                            int ResponceCode = response.getInt("ResponseCode");
                            String Result = response.getString("Result");

                            if (Result.equals("True")) {

                                JSONArray jsonArray = response
                                        .getJSONArray("Filters");
                                for (int k = 0; k < jsonArray.length(); k++) {
                                    JSONObject jobj = jsonArray
                                            .getJSONObject(k);
                                    String name = jobj.getString("name");
                                    JSONArray option_array = jobj
                                            .getJSONArray("option");
                                    Filter_1_Item cat = new Filter_1_Item();
                                    cat.setFiltername(name);
                                    ArrayList<String> filter_val = new ArrayList<String>();
                                    ArrayList<String> filter_val_count = new ArrayList<String>();
                                    for (int i = 0; i < option_array.length(); i++) {
                                        JSONObject jobj_val = option_array
                                                .getJSONObject(i);
                                        filter_val.add(jobj_val
                                                .getString("value"));
                                        filter_val_count.add(jobj_val.getString("count"));

                                    }
                                    cat.setFilter_item(filter_val);
                                    cat.setFilter_item_count(filter_val_count);
                                    // Toast.makeText(ctx,
                                    // name+" "+option_array+"",
                                    // Toast.LENGTH_LONG).show();

                                    favority_item.add(cat);
                                }

                                main_adapter = new Filter_1_Adapter(ctx,
                                        favority_item);
                                lv_mainactivity.setAdapter(main_adapter);
                                main_adapter.notifyDataSetChanged();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(ctx, "Error: " + e.getMessage(),
                                    Toast.LENGTH_LONG).show();
                        }
                        hidepDialog();
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("", "Error: " + error.getMessage());
                Toast.makeText(ctx, error.getMessage(),
                        Toast.LENGTH_SHORT).show();
                // hide the progress dialog
                hidepDialog();
            }
        });

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(jsonObjReq);
    }


    public class Filter_1_Adapter extends BaseAdapter {
        Context context;
        List<Filter_1_Item> catItems;
        ArrayList<String> empty_array;

        public Filter_1_Adapter(Context context, List<Filter_1_Item> items) {
            this.context = context;
            this.catItems = items;
            empty_array = new ArrayList<String>();
        }

        /* private view holder class */
        private class ViewHolder {

            TextView txtTitle;
            LinearLayout linear_listitem;
        }

        ViewHolder holder = null;

        public View getView(final int position, View convertView, ViewGroup parent) {

            LayoutInflater mInflater = (LayoutInflater) context
                    .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

            if (convertView == null) {
                convertView = mInflater.inflate(R.layout.filter_list_item,
                        null);
                holder = new ViewHolder();

                holder.txtTitle = (TextView) convertView
                        .findViewById(R.id.tv_title);
                holder.linear_listitem = (LinearLayout) convertView.findViewById(R.id.linear_listitem);

                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            Filter_1_Item catItem = (Filter_1_Item) getItem(position);
            holder.txtTitle.setText(catItem.getFiltername());
            holder.txtTitle.setMaxLines(2);
            holder.txtTitle.setEllipsize(TextUtils.TruncateAt.END);


           /* if (mpos == position) {
                holder.txtTitle.setTextColor(Color.parseColor("#0099CB"));
            } else {
                holder.txtTitle.setTextColor(Color.parseColor("#484848"));
            }
*/

            if (position == 0) {


                if (catItem.getFilter_item() == null || catItem.getFilter_item().isEmpty()) {

                } else {

                    sub_adapter_2 = new Filter_2_adapter_2(ctx, catItem.getFilter_item(), catItem.getFilter_item_count(), position, catItem.getFiltername());
                    lv_subactivity.setAdapter(sub_adapter_2);
                    sub_adapter_2.notifyDataSetChanged();

                }

            }
            HashMap<String, ArrayList<String>> map = new HashMap<String, ArrayList<String>>();

            map.put("filteroption" + (position + 1) + "=" + catItem.getFiltername(), empty_array);
            filter_map.add(map);


            convertView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    Filter_1_Item catItem = (Filter_1_Item) getItem(position);
                    if (catItem.getFilter_item() == null || catItem.getFilter_item().isEmpty()) {

                    } else {

                        sub_adapter_2 = new Filter_2_adapter_2(ctx, catItem.getFilter_item(), catItem.getFilter_item_count(), position, catItem.getFiltername());
                        lv_subactivity.setAdapter(sub_adapter_2);
                        sub_adapter_2.notifyDataSetChanged();

                    }

                }
            });

            return convertView;
        }

        @Override
        public int getCount() {
            return catItems.size();
        }

        @Override
        public Object getItem(int position) {
            return catItems.get(position);
        }

        @Override
        public long getItemId(int position) {
            return catItems.indexOf(getItem(position));
        }
    }

    ArrayList<HashMap<String, ArrayList<String>>> filter_map = new ArrayList<HashMap<String, ArrayList<String>>>();

    public class Filter_2_adapter_2 extends BaseAdapter {
        Context context;
        List<String> catItems;
        List<String> catItems_count;
        int firstadapter_position;
        String first_filter_selected_name;
        HashMap<String, ArrayList<String>> map = new HashMap<String, ArrayList<String>>();
        ArrayList<String> checkd_filter;


        public Filter_2_adapter_2(Context context, List<String> items, List<String> items_count, int firstadapter_position, String first_filter_selected_name) {
            this.context = context;
            this.catItems = items;
            this.catItems_count = items_count;
            this.firstadapter_position = firstadapter_position;
            this.first_filter_selected_name = first_filter_selected_name;
            checkd_filter = new ArrayList<String>();


            Toast.makeText(ctx,catItems+"\n"+catItems_count,Toast.LENGTH_LONG).show();

        }

        //  private view holder class *//*
        private class ViewHolder {
           /* TextView txtTitle;
            CheckBox chk_filter;*/

            CheckedTextView chk_tv;
        }


        public View getView(final int position, View convertView,
                            ViewGroup parent) {
            ViewHolder holder = null;


            LayoutInflater mInflater = (LayoutInflater) ctx
                    .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            if (convertView == null) {
                convertView = mInflater.inflate(R.layout.filter_checked_list_item,
                        null);
                holder = new ViewHolder();

                holder.chk_tv = (CheckedTextView) convertView
                        .findViewById(R.id.chk_tv_filter);
                //holder.chk_filter = (CheckBox) convertView.findViewById(R.id.chk_filter);

                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            //   holder.chk_filter.setVisibility(View.VISIBLE);
            holder.chk_tv.setText(catItems.get(position) + "  (" + catItems_count.get(position) + ")");
            // holder.txtTitle.setMaxLines(2);
            // holder.txtTitle.setEllipsize(TextUtils.TruncateAt.END);


            map = filter_map.get(firstadapter_position);
            System.out.println("Value 1 " + map);
            if (map.get("filteroption" + (firstadapter_position + 1) + "=" + first_filter_selected_name) != null) {

                ArrayList<String> value = new ArrayList<String>();
                Set mapSet = (Set) map.entrySet();
                Iterator mapIterator = mapSet.iterator();
                while (mapIterator.hasNext()) {
                    Map.Entry mapEntry = (Map.Entry) mapIterator.next();
                    value = (ArrayList<String>) mapEntry.getValue();
                    System.out.println(value);
                }


                if (value.contains(catItems.get(position))) {
                    holder.chk_tv.setChecked(true);

                } else {
                    holder.chk_tv.setChecked(false);
                }

            }


            holder.chk_tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CheckedTextView chk = (CheckedTextView) v.findViewById(R.id.chk_tv_filter);
                    if (chk.isChecked()) {
                        chk.setChecked(false);

                    } else {
                        chk.setChecked(true);
                    }

                    if (chk.isChecked()) {
                        checkd_filter.add(catItems.get(position));
                        map.put("filteroption" + (firstadapter_position + 1) + "=" + first_filter_selected_name, checkd_filter);

                    } else {
                        checkd_filter.remove(catItems.get(position));
                        map.put("filteroption" + (firstadapter_position + 1) + "=" + first_filter_selected_name, checkd_filter);
                    }
                }

            });

            if (!filter_map.contains(map))
                filter_map.add(map);

            return convertView;
        }

        @Override
        public int getCount() {
            //  System.out.println("Count "+catItems.size());
            return catItems.size();

        }

        @Override
        public Object getItem(int position) {

            return catItems.get(position);
        }

        @Override
        public long getItemId(int position) {
            return catItems.indexOf(getItem(position));
        }


    }


}
