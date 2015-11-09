package com.creatrix.ttb.adapter;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.creatrix.ttb.Obj.CatItem;

import com.creatrix.ttb.R;
import com.creatrix.ttb.app.AppController;

/**
 * Created by Harshu on 19-10-2015.
 */
public class Main_Cat_Adapter extends BaseAdapter {
    Context context;
    List<CatItem> catItems;
    ImageLoader imageLoader = AppController.getInstance().getImageLoader();



    public Main_Cat_Adapter(Context context, List<CatItem> items) {
        this.context = context;
        this.catItems = items;
    }

    /*private view holder class*/
    private class ViewHolder {
        NetworkImageView imageView;
        TextView txtTitle;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;

        LayoutInflater mInflater = (LayoutInflater)
                context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.main_cat_list_item, null);
            holder = new ViewHolder();

            holder.txtTitle = (TextView) convertView.findViewById(R.id.tv_title);
            holder.imageView = (NetworkImageView) convertView.findViewById(R.id.iv_cat_image);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        CatItem catItem = (CatItem) getItem(position);
        holder.txtTitle.setText(catItem.getName());
        holder.txtTitle.setMaxLines(2);
        holder.txtTitle.setEllipsize(TextUtils.TruncateAt.END);
        holder.imageView.setImageUrl(catItem.getImage(), imageLoader);

        if (position % 3 == 0) {
            holder.txtTitle.setBackgroundResource(R.drawable.lay_bg_corner_0);

        } else if (position % 3 == 1) {
            holder.txtTitle.setBackgroundResource(R.drawable.lay_bg_corner_1);
        } else {
            holder.txtTitle.setBackgroundResource(R.drawable.lay_bg_corner_2);
        }


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
