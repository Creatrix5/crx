package com.creatrix.ttb.Obj;

import java.util.ArrayList;

/**
 * Created by Harshu on 19-10-2015.
 */
public class Filter_1_Item {

	private String filtername;

	private ArrayList<String> filter_item, filter_item_count;

	public Filter_1_Item() {
	}

	public Filter_1_Item(String filtername, ArrayList<String> filter_item,
			ArrayList<String> filter_item_count) {
		this.filtername = filtername;
		this.filter_item = filter_item;
		this.filter_item_count = filter_item_count;
	}

	public String getFiltername() {
		return filtername;
	}

	public void setFiltername(String filtername) {
		this.filtername = filtername;
	}

	public ArrayList<String> getFilter_item() {
		return filter_item;
	}

	public void setFilter_item(ArrayList<String> filter_item) {
		this.filter_item = filter_item;
	}

	public ArrayList<String> getFilter_item_count() {
		return filter_item_count;
	}

	public void setFilter_item_count(ArrayList<String> filter_item_count) {
		this.filter_item_count = filter_item_count;
	}

}
