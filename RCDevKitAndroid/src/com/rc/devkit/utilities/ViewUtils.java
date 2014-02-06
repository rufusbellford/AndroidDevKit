package com.rc.devkit.utilities;

import android.graphics.drawable.GradientDrawable;
import android.view.View;

/**
 * Created by Softhis on 16.12.2013.
 */
public class ViewUtils
{
    public static int goneVisiblity(boolean isVisible)
    {
        return isVisible ? View.VISIBLE : View.GONE;
    }

//    public static void roundView(View view)
//    {
//        GradientDrawable shape =  new GradientDrawable();
//
//        // add some color
//        // You can add your random color generator here
//        // and set color
//        if (i % 2 == 0) {
//            shape.setColor(Color.RED);
//        } else {
//            shape.setColor(Color.BLUE);
//        }
//
//        // now find your view and add background to it
//        View view = (LinearLayout) findViewById( R.id.my_view );
//        view.setBackground(shape);
//    }
}
