package com.smurali.tgame1;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by Anubhav on 29-07-2017.
 */

public class JosBold extends TextView {

    public JosBold(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public JosBold(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public JosBold(Context context) {
        super(context);
        init();
    }

    public void init() {
        Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "fonts/JosefinSlab-Bold.ttf");
        setTypeface(tf ,1);

    }
}