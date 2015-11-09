package com.creatrix.ttb.custom_hashtag;

/**
 * Created by greenfrvr
 */
class DefaultSelector<T> implements HashtagView.DataSelector<T> {

    public static DefaultSelector newInstance() {
        return new DefaultSelector<>();
    }

    private DefaultSelector(){}

    @Override
    public boolean preselect(T item) {
        return false;
    }
}
