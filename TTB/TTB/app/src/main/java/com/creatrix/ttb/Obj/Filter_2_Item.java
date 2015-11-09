package com.creatrix.ttb.Obj;

/**
 * Created by Harshu on 19-10-2015.
 */
public class Filter_2_Item {

	private String Childivailable;
	private int parent_id;
	private String catname;
	private int cat_id;

	public Filter_2_Item() {
	}

	public Filter_2_Item(int cat_id, int parent_id, String catname,
			String Childivailable) {
		this.cat_id = cat_id;
		this.catname = catname;
		this.parent_id = parent_id;
		this.Childivailable = Childivailable;
	}

	public String getChildivailable() {
		return Childivailable;
	}

	public void setChildivailable(String childivailable) {
		Childivailable = childivailable;
	}

	public int getParent_id() {
		return parent_id;
	}

	public void setParent_id(int parent_id) {
		this.parent_id = parent_id;
	}

	public int getId() {
		return cat_id;
	}

	public void setId(int cat_id) {
		this.cat_id = cat_id;
	}

	public String getName() {
		return catname;
	}

	public void setName(String catname) {
		this.catname = catname;
	}

}
