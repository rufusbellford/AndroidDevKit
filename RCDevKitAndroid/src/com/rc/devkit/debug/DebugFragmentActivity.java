package com.rc.devkit.debug;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.FrameLayout;

public class DebugFragmentActivity extends FragmentActivity
{
    public static final String FRAGMENT_CLASS_TAG = "FRG_CLASS";
    public static final String FRAGMENT_RUNNNABLE_TAG = "FRG_RUNNABLE";
    private final int CONTENT_VIEW_ID = 10101010;

    private Class fragmentClass;
    private DebugRunnable debugRunnable;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setup();
    }

    private void setup()
    {
        readIntent();
        setupContentLayout();
        setupFragmentWithClass(fragmentClass);
    }

    private void readIntent()
    {
        fragmentClass = (Class) getIntent().getSerializableExtra(FRAGMENT_CLASS_TAG);
        debugRunnable = (DebugRunnable) getIntent().getSerializableExtra(FRAGMENT_RUNNNABLE_TAG);
    }

    private void setupContentLayout()
    {
        FrameLayout frame = new FrameLayout(this);
        FrameLayout viewGroup = new FrameLayout(this);
        viewGroup.setId(CONTENT_VIEW_ID);
        frame.addView(viewGroup, new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT));
        setContentView(frame);
    }

    private void setupFragmentWithClass(Class fragmentClass)
    {
        try
        {
            Fragment fragment = (Fragment) fragmentClass.newInstance();
            fragment.setArguments(getIntent().getExtras());

            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction ft = fragmentManager.beginTransaction();
            ft.add(CONTENT_VIEW_ID, fragment);
            ft.commit();

            onPostFragmentRun(fragment);
        }
        catch (InstantiationException e)
        {
            e.printStackTrace();
        }
        catch (IllegalAccessException e)
        {
            e.printStackTrace();
        }
    }

    private void onPostFragmentRun(final Fragment fragment)
    {
    }
}
