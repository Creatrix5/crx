package com.creatrix.ttb.custom_hashtag;

/**
 * Created by greenfrvr
 */
class DefaultTransform<T> implements HashtagView.DataTransform<T> {

    public static DefaultTransform newInstance() {
        return new DefaultTransform<>();
    }

    private DefaultTransform(){}

    @Override
    public CharSequence prepare(T item) {
        return item.toString();
    }

}
