package com.rc.devkit.utilities;

import android.content.Context;
import android.graphics.Typeface;
import android.widget.TextView;

/**
 * Created by Softhis on 13.12.2013.
 */
public class FontHelper
{
    public static void setFontFromAssetsWithName(Context context, TextView textView, String assetFontName)
    {
        textView.setTypeface(Typeface.createFromAsset(context.getAssets(), assetFontName));
    }

    public static void setFontTypeface(TextView textView, Typeface typeface)
    {
        textView.setTypeface(typeface);
    }
}
