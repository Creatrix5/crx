package com.creatrix.ttb.Obj;

import java.util.List;

/**
 * Created by Harshu on 16-10-2015.
 */
public class TagViewModel {

    private String cattitle;
    private List<String> tagvalues;

    public TagViewModel() {
    }

    public TagViewModel(String cattitle, List<String> tagvalues) {
        this.cattitle = cattitle;
        this.tagvalues = tagvalues;
    }

    public String getTitle() {
        return cattitle;
    }

    public void setTitle(String cattitle) {
        this.cattitle = cattitle;
    }

    public List<String> gettagvalues() {
        return tagvalues;
    }

    public void settagvalues(List<String> tagvalues) {
        this.tagvalues = tagvalues;
    }
}
