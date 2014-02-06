package com.rc.devkit.debug;

import android.support.v4.app.Fragment;

import java.io.Serializable;

/**
 * Created by Softhis on 13.12.2013.
 */
public interface DebugRunnable extends Serializable
{
    public void runOnFragment(Fragment fragment);
}
