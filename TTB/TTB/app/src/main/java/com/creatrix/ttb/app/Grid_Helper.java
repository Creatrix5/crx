package com.creatrix.ttb.app;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class Grid_Helper {
	public static void getListViewSize(GridView myListView) {
        ListAdapter myListAdapter = myListView.getAdapter();
        if (myListAdapter == null) {
            //do nothing return null
            return;
        }
        //set listAdapter in loop for getting final size
        int totalHeight = 0;
int listval=myListAdapter.getCount();
        if (listval%3==0){
            listval=myListAdapter.getCount()/3;
        }else{
            listval=myListAdapter.getCount()/3+1;
        }
        for (int size = 0; size < listval; size++) {
            View listItem = myListAdapter.getView(size, null, myListView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        System.out.println("Height  "+totalHeight+"    "+myListAdapter.getCount());
      //setting listview item in adapter
        ViewGroup.LayoutParams params = myListView.getLayoutParams();
        params.height = totalHeight + ((myListView.getVerticalSpacing()/3) * (myListAdapter.getCount() - 1));
        myListView.setLayoutParams(params);
        // print height of adapter on log
        Log.i("height of listItem:", String.valueOf(totalHeight));
    }


}
