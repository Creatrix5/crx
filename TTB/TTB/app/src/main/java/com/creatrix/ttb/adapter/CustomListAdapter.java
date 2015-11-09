package com.creatrix.ttb.adapter;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.creatrix.ttb.Obj.TagViewModel;

import com.creatrix.ttb.R;
import com.creatrix.ttb.custom_hashtag.HashtagView;

public class CustomListAdapter extends BaseAdapter {

    private Activity activity;
    private LayoutInflater inflater;
    private List<TagViewModel> Tagmodels;
    private List<String> tag_val;

    public CustomListAdapter(Activity activity, List<TagViewModel> Tagmodels) {
        this.activity = activity;
        this.Tagmodels = Tagmodels;
    }

    @Override
    public int getCount() {
        return Tagmodels.size();
    }

    @Override
    public Object getItem(int location) {
        return Tagmodels.get(location);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (inflater == null)
            inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null)
            convertView = inflater.inflate(R.layout.list_row, null);

        final TextView title = (TextView) convertView.findViewById(R.id.txt_cat_title);

        final HashtagView hashtagView2 = (HashtagView)convertView.findViewById(R.id.hashtags2);

        // getting movie data for the row
        tag_val = new ArrayList<String>();
        TagViewModel m = Tagmodels.get(position);
        title.setText(m.getTitle());
        // title.setText(m.getTitle());
        tag_val = m.gettagvalues();
     //   Log.d("tag_val",""+tag_val);

       // hashtagView2.setData(m.gettagvalues());
//        hashtagView2.addOnTagSelectListener(new HashtagView.TagsSelectListener() {
//            @Override
//            public void onItemSelected(Object item, boolean selected) {
//
//            }
//        });
//        hashtagView2.getSelectedItems();

        return convertView;
    }

}