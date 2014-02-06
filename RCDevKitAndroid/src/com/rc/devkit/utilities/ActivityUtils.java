package com.rc.devkit.utilities;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;

public class ActivityUtils
{
    // TODO: Add different type of start activity
    // Read more, think what to add

    public static void startActivity(Activity root, Class<?> activityClass)
    {
        Intent intent = new Intent(root, activityClass);
        root.startActivity(intent);
    }

    public static void startActivity(Activity root, Class<?> activityClass, Bundle extras)
    {
        if (extras == null) {
            throw new RuntimeException("Bundle is null, use method without bundle.");
        }

        Intent intent = new Intent(root, activityClass);
        intent.putExtras(extras);
        root.startActivity(intent);
    }

    public static void startActivityAndCloseCurrent(Activity root, Class<?> activityClass)
    {
        Intent intent = new Intent(root, activityClass);
        root.startActivity(intent);
        root.finish();
    }

    public static void openInBrowser(Activity source, String siteHttp)
    {
        Intent myIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(siteHttp));
        source.startActivity(myIntent);
    }

    public static int lockActivityOrientation(Activity activity)
    {
        int oldOrientation = activity.getRequestedOrientation();

        int currentOrientation = activity.getResources().getConfiguration().orientation;
        if (currentOrientation == Configuration.ORIENTATION_LANDSCAPE) {
            activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE);
        }
        else {
            activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT);
        }

        return oldOrientation;
    }

    public static void unlockActivityOrientation(Activity activity, int orientation)
    {
        activity.setRequestedOrientation(orientation);
    }


    public static void startActivityForResult(Activity root, Class<?> activityClass, int requestCode)
    {
        Intent intent = new Intent(root, activityClass);
        root.startActivityForResult(intent, requestCode);
    }

    public static void startActivityForResult(Activity root, Class<?> activityClass, int requestCode, Bundle extras)
    {
        if (extras == null) {
            throw new RuntimeException("Bundle is null, use method without bundle.");
        }

        Intent intent = new Intent(root, activityClass);
        intent.putExtras(extras);
        root.startActivityForResult(intent, requestCode);
    }
}
