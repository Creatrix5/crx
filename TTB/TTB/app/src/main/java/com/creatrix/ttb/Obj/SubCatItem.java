package com.creatrix.ttb.Obj;

/**
 * Created by Harshu on 19-10-2015.
 */
public class SubCatItem {

    private String catname;
    private String catimage;
    private int cat_id;
    private int parent_id;
    private String lastchild;
    public SubCatItem() {
    }

    public SubCatItem(int cat_id,int parent_id, String catname, String catimage,String lastchild) {
        this.cat_id = cat_id;
        this.catname = catname;
        this.parent_id=parent_id;
        this.catimage = catimage;
        this.lastchild=lastchild;
    }

    public int getId() {
        return cat_id;
    }

    public void setId(int cat_id) {
        this.cat_id = cat_id;
    }

    public int getParent_id() {
        return parent_id;
    }

    public void setParent_id(int parent_id) {
        this.parent_id = parent_id;
    }


    public String getName() {
        return catname;
    }

    public void setName(String catname) {
        this.catname = catname;
    }

    public String getImage() {
        return catimage;
    }

    public void setImage(String catimage) {
        this.catimage = catimage;
    }

    public String getLastchild() {
        return lastchild;
    }

    public void setLastchild(String lastchild) {
        this.lastchild = lastchild;
    }
}
