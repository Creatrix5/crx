package com.creatrix.ttb.adapter;

import java.util.List;

import android.app.Activity;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerAdapter;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;


import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.creatrix.ttb.app.AppController;
import com.creatrix.ttb.Fragme.HomeFragment;
import com.creatrix.ttb.Obj.Product;

import com.creatrix.ttb.R;

public class ImageSlideAdapter extends PagerAdapter {
    //private ImageLoadingListener imageListener;
    FragmentActivity activity;
    List<Product> products;
    HomeFragment homeFragment;
    ImageLoader imageLoader = AppController.getInstance().getImageLoader();


    public ImageSlideAdapter(FragmentActivity activity, List<Product> products,
                             HomeFragment homeFragment) {
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
        View view = inflater.inflate(R.layout.vp_image, container, false);

        NetworkImageView mImageView = (NetworkImageView) view
                .findViewById(R.id.image_display);

        mImageView.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
				/*Bundle arguments = new Bundle();
				Fragment fragment = null;
				Log.d("position adapter", "" + position);
				Product product = (Product) products.get(position);
				arguments.putParcelable("singleProduct", product);

				// Start a new fragment
				fragment = new ProductDetailFragment();
				fragment.setArguments(arguments);

				FragmentTransaction transaction = activity
						.getSupportFragmentManager().beginTransaction();
				transaction.replace(R.id.content_frame, fragment,
						ProductDetailFragment.ARG_ITEM_ID);
				transaction.addToBackStack(ProductDetailFragment.ARG_ITEM_ID);
				transaction.commit();
*/
            }
        });

        mImageView.setImageUrl(products.get(position).getImageUrl(), imageLoader);

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