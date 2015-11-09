package com.creatrix.ttb.Fragme;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;

import java.util.ArrayList;

import com.creatrix.ttb.R;
import com.creatrix.ttb.adapter.ProductImageSlideAdapter;
import com.creatrix.ttb.utils.CirclePageIndicator;
import com.creatrix.ttb.utils.PageIndicator;

public class Product_Image_Fragment extends Fragment {

	public static final String ARG_ITEM_ID = "product_image_fragment";

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

//	String url ;
	FragmentActivity activity;
	ArrayList<String> products_list;


	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		activity = getActivity();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_home, container, false);

		products_list=new ArrayList<String>();
		products_list = getArguments().getStringArrayList("product_image");

		findViewById(view);


/*

		mViewPager.setAdapter(new ProductImageSlideAdapter(
				activity, products_list, Product_Image_Fragment.this));

		mIndicator.setViewPager(mViewPager);
*/


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

	private void findViewById(View view) {
		mViewPager = (ViewPager) view.findViewById(R.id.view_pager);
		mIndicator = (CirclePageIndicator) view.findViewById(R.id.indicator);
		//imgNameTxt = (TextView) view.findViewById(R.id.img_name);

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
			mViewPager.setAdapter(new ProductImageSlideAdapter(activity, products_list,
					Product_Image_Fragment.this));

			mIndicator.setViewPager(mViewPager);
			runnable(products_list.size());
			//Re-run callback
			handler.postDelayed(animateViewPager, ANIM_VIEWPAGER_DELAY);
		super.onResume();
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
}

