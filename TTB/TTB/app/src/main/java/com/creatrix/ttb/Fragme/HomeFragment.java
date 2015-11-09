package com.creatrix.ttb.Fragme;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.creatrix.ttb.adapter.ImageSlideAdapter;
import com.creatrix.ttb.app.AppController;
import com.creatrix.ttb.utils.PageIndicator;
import com.creatrix.ttb.R;

import com.creatrix.ttb.Obj.Product;

import com.creatrix.ttb.utils.CheckNetworkConnection;
import com.creatrix.ttb.utils.CirclePageIndicator;
import com.creatrix.ttb.utils.Utils;

public class HomeFragment extends Fragment {

	public static final String ARG_ITEM_ID = "home_fragment";

	private static final long ANIM_VIEWPAGER_DELAY = 5000;
	private static final long ANIM_VIEWPAGER_DELAY_USER_VIEW = 10000;

	// UI References
	private ViewPager mViewPager;
	//TextView imgNameTxt;

	PageIndicator mIndicator;

	AlertDialog alertDialog;

	//RequestImgTask task;
	boolean stopSliding = false;
	String message;

	private Runnable animateViewPager;
	private Handler handler;

	String url ;
	FragmentActivity activity;


	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		activity = getActivity();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_home, container, false);
		String type = getArguments().getString("type");
		int id=getArguments().getInt("id");
		if (type.equals("MainActivity")){
			url = Utils.main_url+"getslider.php?id="+id+"&type=home";
		}else{
			url = Utils.main_url+"getslider.php?id="+id+"&type=category";
		}

		findViewById(view);



		mIndicator.setOnPageChangeListener(new PageChangeListener());
		mViewPager.setOnPageChangeListener(new PageChangeListener());
		mViewPager.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				v.getParent().requestDisallowInterceptTouchEvent(true);
				switch (event.getAction()) {


				case MotionEvent.ACTION_CANCEL:
					break;

				case MotionEvent.ACTION_UP:
					// calls when touch release on ViewPager
					if (products_list != null && products_list.size() != 0) {
						stopSliding = false;
						runnable(products_list.size());
						handler.postDelayed(animateViewPager,
								ANIM_VIEWPAGER_DELAY_USER_VIEW);
					}
					break;

				case MotionEvent.ACTION_MOVE:
					// calls when ViewPager touch
					if (handler != null && stopSliding == false) {
						stopSliding = true;
						handler.removeCallbacks(animateViewPager);
					}
					break;
				}
				return false;
			}
		});

		return view;
	}
FrameLayout img_slideshow_layout;
	private void findViewById(View view) {
		mViewPager = (ViewPager) view.findViewById(R.id.view_pager);
		mIndicator = (CirclePageIndicator) view.findViewById(R.id.indicator);
		img_slideshow_layout=(FrameLayout)view.findViewById(R.id.img_slideshow_layout);


		DisplayMetrics dm=new DisplayMetrics();
		getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
		int height = (int)(dm.heightPixels/3);

		int width = dm.widthPixels;
		RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(width, height);
		img_slideshow_layout.setLayoutParams(lp);

	}

	public void runnable(final int size) {
		handler = new Handler();
		animateViewPager = new Runnable() {
			public void run() {
				if (!stopSliding) {
					if (mViewPager.getCurrentItem() == size - 1) {
						mViewPager.setCurrentItem(0);
					} else {
						mViewPager.setCurrentItem(
								mViewPager.getCurrentItem() + 1, true);
					}
					handler.postDelayed(animateViewPager, ANIM_VIEWPAGER_DELAY);
				}
			}
		};
	}


	@Override
	public void onResume() {
		if (products_list == null) {
			sendRequest();
		} else {
			mViewPager.setAdapter(new ImageSlideAdapter(activity, products_list,
					HomeFragment.this));

			mIndicator.setViewPager(mViewPager);
			runnable(products_list.size());
			//Re-run callback
			handler.postDelayed(animateViewPager, ANIM_VIEWPAGER_DELAY);
		}
		super.onResume();
	}

	private void sendRequest() {
		if (CheckNetworkConnection.isConnectionAvailable(activity)) {
			makeJsonObjectRequest();
		} else {
		Toast.makeText(getActivity(),"Network Problem",Toast.LENGTH_LONG).show();
		}
	}

	public void showAlertDialog(String message, final boolean finish) {
		alertDialog = new AlertDialog.Builder(activity).create();
		alertDialog.setMessage(message);
		alertDialog.setCancelable(false);

		alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
						if (finish)
							activity.finish();
					}
				});
		alertDialog.show();
	}

	private class PageChangeListener implements OnPageChangeListener {

		@Override
		public void onPageScrollStateChanged(int state) {
			if (state == ViewPager.SCROLL_STATE_IDLE) {
				if (products_list != null) {
					/*imgNameTxt.setText(""
							+ ((Product) products.get(mViewPager
									.getCurrentItem())).getName());*/
				}
			}
		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
		}

		@Override
		public void onPageSelected(int arg0) {
		}
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
	}

//String url="";

	// Progress dialog
	private ProgressDialog pDialog;

	List<Product> products_list;

	/**
	 * Method to make json object request where json response starts wtih {
	 * */
	private void makeJsonObjectRequest() {

		pDialog = new ProgressDialog(activity);
		pDialog.setMessage("Please wait...");
		pDialog.setCancelable(false);

		showpDialog();

		products_list=new ArrayList<Product>();

		JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
				url, null, new Response.Listener<JSONObject>() {

			@Override
			public void onResponse(JSONObject response) {
				Log.d("Responce ", response.toString());

				try {

					JSONArray jsonArray=response.getJSONArray("slider");

						for (int j=0;j<jsonArray.length();j++){

						JSONObject person = (JSONObject) jsonArray
								.get(j);

						//System.out.println("Responce "+person.getString("cover"));
						Product product = new Product();
						//	JSONObject productObj = jsonObject.getJSONObject(i);
						//System.out.println("name 1 "+"http://appsrealworld.com/apps/"+productObj.getString(TagName.KEY_IMAGE_URL)+" "+productObj.getString(TagName.KEY_NAME)+" "+productObj.getInt(TagName.KEY_ID));
						product.setId(j+1);


						product.setImageUrl(Utils.main_url + person.getString("adimage"));

						products_list.add(product);


					}


					mViewPager.setAdapter(new ImageSlideAdapter(
							activity, products_list, HomeFragment.this));

					mIndicator.setViewPager(mViewPager);



				} catch (JSONException e) {
					e.printStackTrace();
					Toast.makeText(getActivity(),
							"Error: " + e.getMessage(),
							Toast.LENGTH_LONG).show();
				}
				hidepDialog();
			}
		}, new Response.ErrorListener() {

			@Override
			public void onErrorResponse(VolleyError error) {
				VolleyLog.d("", "Error: " + error.getMessage());
				Toast.makeText(getActivity(),
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

