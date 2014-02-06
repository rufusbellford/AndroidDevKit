package com.rc.devkit.utilities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class ActivityActionExecutor
{
    private Bundle extras = null;
    private boolean shouldFinishCurrent = false;
    private boolean startForResult = false;
    private int newIntentRequestCode = -1;
    private Class<?> activityClass = null;
    private Activity activity = null;

    private ActivityActionExecutor(Activity activity)
    {
        this.activity = activity;
    }

    public static ActivityActionExecutor create(Activity activity)
    {
        return new ActivityActionExecutor(activity);
    }

    public ActivityActionExecutor extras(Bundle extras)
    {
        this.extras = extras;
        return this;
    }

    public ActivityActionExecutor finishCurrent()
    {
        this.shouldFinishCurrent = true;
        return this;
    }

    public ActivityActionExecutor startForResult(int newIntentRequestCode)
    {
        this.startForResult = true;
        this.newIntentRequestCode = newIntentRequestCode;
        return this;
    }

    public ActivityActionExecutor startClass(Class<?> activityClass)
    {
        this.activityClass = activityClass;
        return this;
    }

    public void execute()
    {
        if (activityClass == null || activity == null) {
            throw new RuntimeException("You didn't specified class to run or activity which run from!");
        }

        Intent intent = new Intent(activity, activityClass);

        if (extras != null) {
            intent.putExtras(extras);
        }

        if (startForResult) {
            activity.startActivityForResult(intent, newIntentRequestCode);
        }
        else {
            activity.startActivity(intent);
        }

        if (shouldFinishCurrent) {
            activity.finish();
        }
    }
}
