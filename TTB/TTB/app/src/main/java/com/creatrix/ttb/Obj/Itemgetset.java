package com.creatrix.ttb.Obj;

import java.util.ArrayList;

public class Itemgetset {
    String cat_name;
    ArrayList<String> sub_cate_name;
    ArrayList<Integer> sub_cat_id;

    public ArrayList<String> getSub_cate_name() {
        return sub_cate_name;
    }

    public void setSub_cate_name(ArrayList<String> sub_cate_name) {
        this.sub_cate_name = sub_cate_name;
    }

    public ArrayList<Integer> getSub_cat_id() {
        return sub_cat_id;
    }

    public void setSub_cate_id(ArrayList<Integer> sub_cat_id) {
        this.sub_cat_id = sub_cat_id;
    }

    public String getCat_name() {
        return cat_name;
    }

    public void setCat_name(String cat_name) {
        this.cat_name = cat_name;
    }

}
