package com.creatrix.ttb.custom_hashtag;

import android.graphics.Color;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;

/**
 * Created by Harshu on 16-10-2015.
 */
public class Transformers {

    public static final HashtagView.DataTransform<String> HASH = new HashtagView.DataTransform<String>() {
        @Override
        public CharSequence prepare(String item) {
            SpannableString spannableString = new SpannableString(" " + item);
            spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#0099CB")), 0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            return spannableString;
        }
    };

}