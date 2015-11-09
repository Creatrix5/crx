package com.creatrix.ttb.adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.creatrix.ttb.Fragme.Product_Image_Fragment;
import com.creatrix.ttb.Gallery_Class;
import java.util.ArrayList;

import com.creatrix.ttb.R;
import com.creatrix.ttb.app.AppController;

public class ProductImageSlideAdapter extends PagerAdapter {
	//private ImageLoadingListener imageListener;
	FragmentActivity activity;
	ArrayList<String> products;
	Product_Image_Fragment homeFragment;

	ImageLoader imageLoader = AppController.getInstance().getImageLoader();

	public ProductImageSlideAdapter(FragmentActivity activity, ArrayList<String> products,
									Product_Image_Fragment homeFragment) {
		this.activity = activity;
		this.homeFragment = homeFragment;
		this.products = products;
	}



	@Override
	public int getCount() {
		return products.size();
	}

	@Override
	public View instantiateItem(ViewGroup container, final int position) {
		LayoutInflater inflater = (LayoutInflater) activity
				.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
		View view = inflater.inflate(R.layout.vp_image_1, container, false);

		NetworkImageView mImageView = (NetworkImageView) view
				.findViewById(R.id.image_display);

System.out.println("Responce " + products.get(position));

		mImageView.setImageUrl(products.get(position), imageLoader);


		mImageView.setOnClickListener(new OnClickListener() {
	@Override
	public void onClick(View v) {
		Intent ii = new Intent(activity, Gallery_Class.class);
		ii.putStringArrayListExtra("img_path", products);
		ii.putExtra("position", position);
		activity.startActivity(ii);

	}
});

				container.addView(view);
		return view;
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		container.removeView((View) object);
	}

	@Override
	public boolean isViewFromObject(View view, Object object) {
		return view == object;
	}

}