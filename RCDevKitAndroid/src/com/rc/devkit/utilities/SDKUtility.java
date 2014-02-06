package com.rc.devkit.utilities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

/**
 * Created by Softhis on 04.12.2013.
 */
public class SDKUtility
{
    public static void showKeyboard(final EditText editText)
    {
        Context context = Registry.getInstance().getApplicationContext();
        final InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        editText.postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
                imm.showSoftInput(editText, 0);
            }
        }, 100);
    }

    public static void hideKeyboard(final EditText editText)
    {
        Context context = Registry.getInstance().getApplicationContext();
        final InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
    }

    public static boolean hasHoneycomb()
    {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB;
    }

    public static boolean isSDKEquealOrAbove(int sdkVersion)
    {
        return Build.VERSION.SDK_INT >= sdkVersion;
    }
}
