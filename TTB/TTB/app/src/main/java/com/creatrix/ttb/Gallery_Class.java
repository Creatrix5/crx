package com.creatrix.ttb;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.creatrix.ttb.app.AppController;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by Harshu on 16-09-2015.
 */
public class Gallery_Class extends Activity {

    ViewPager viewPager;
    Context ctx;
    ArrayList<String> Img_path = new ArrayList<String>();
    int position;
    Gallery_ViewPager adapter;
//    Button btn_share;
//    ActionBar actionBar;

    LinearLayout lay_share, lay_back;
    TextView txt_photovalue;
    int totalimges;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.gallery_view);

        ctx = this;

        viewPager = (ViewPager) findViewById(R.id.pager_gallery);
        txt_photovalue = (TextView)findViewById(R.id.txt_photovalue);
        lay_back = (LinearLayout)findViewById(R.id.lay_back);



        Intent ii = getIntent();
        Img_path = ii.getStringArrayListExtra("img_path");
        position = ii.getIntExtra("position", 0);
        viewPager.setCurrentItem(position);
        adapter = new Gallery_ViewPager(ctx, Img_path);
        viewPager.setAdapter(adapter);


        lay_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();

            }
        });

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                txt_photovalue.setText("Photos "+(position+1)+ " of " + totalimges);
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    ProgressDialog mProgressDialog;
    FileOutputStream output;
    // DownloadImage AsyncTask
    private class DownloadImage extends AsyncTask<String, Void, Bitmap> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Create a progressdialog
            mProgressDialog = new ProgressDialog(ctx);
            // Set progressdialog message
            mProgressDialog.setMessage("Loading...");
            mProgressDialog.setIndeterminate(false);
            // Show progressdialog
            mProgressDialog.show();
        }

        @Override
        protected Bitmap doInBackground(String... URL) {

            String imageURL = URL[0];

            Bitmap bitmap = null;
            try {
                // Download Image from URL
                InputStream input = new java.net.URL(imageURL).openStream();
                // Decode Bitmap
                bitmap = BitmapFactory.decodeStream(input);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap result) {
            // Set the bitmap into ImageView
            //image.setImageBitmap(result);
            // Close progressdialog
            mProgressDialog.dismiss();


            // Find the SD Card path
            File filepath = Environment.getExternalStorageDirectory();

            // Create a new folder AndroidBegin in SD Card
            File dir = new File(filepath.getAbsolutePath() + "/");
            dir.mkdirs();

            // Create a name for the saved image
            File file = new File(dir, "Groupon.png");

            try {

                // Share Intent
                Intent share = new Intent(Intent.ACTION_SEND);

                // Type of file to share
                share.setType("image/jpeg");

                output = new FileOutputStream(file);

                // Compress into png format image from 0% - 100%
                result.compress(Bitmap.CompressFormat.PNG, 100, output);
                output.flush();
                output.close();

                // Locate the image to Share
                Uri uri = Uri.fromFile(file);

                // Pass the image into an Intnet
                share.putExtra(Intent.EXTRA_STREAM, uri);

                // Show the social share chooser list
                startActivity(Intent.createChooser(share, "Share Image"));

            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }


				/*

				Intent shareIntent = new Intent(
						android.content.Intent.ACTION_SEND);
				shareIntent.setType("image/*");
				shareIntent.putExtra(Intent.EXTRA_STREAM, );

				startActivity(shareIntent);
*/			}
    }





    public class Gallery_ViewPager extends PagerAdapter {

        // Declare Variables
        Context context;

        LayoutInflater inflater;

        ArrayList<String> get_image_list;
        ImageLoader imageLoader = AppController.getInstance().getImageLoader();



        public Gallery_ViewPager(Context ctx, ArrayList<String> get_image_list) {

            this.context = ctx;
            this.get_image_list = get_image_list;


        }


        @Override
        public int getCount() {
            return get_image_list.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == ((LinearLayout) object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {

            // Declare Variables
           NetworkImageView imageView;


            inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);


            View itemView = inflater.inflate(R.layout.viewpager_item, container,
                    false);

            imageView = (NetworkImageView) itemView.findViewById(R.id.iv_image);
            System.out.println("img_path " + get_image_list.get(position));
            totalimges = get_image_list.size();

            imageView.setImageUrl(get_image_list.get(position), imageLoader);

            ((ViewPager) container).addView(itemView);

            return itemView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            // Remove viewpager_item.xml from ViewPager
            ((ViewPager) container).removeView((LinearLayout) object);

        }
    }

}
