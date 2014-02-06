package com.rc.devkit.utilities;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by Softhis on 19.11.2013.
 */
public class Registry
{
    private static Registry instance = null;
    private Context applicationContext = null;
    private DeviceScreenProperties screenProperties = null;

    // If You Override this Registry:
    // - add seperate static variable "instance" and "getInstance" method
    // - add this:
    // super.setInstance(instance);
    // after instantiating instance of derivated class
    //
    protected static void setInstance(Registry instance)
    {
        Registry.instance = instance;
    }

    // Example of usage:
//    private static AutentiRegistry instance;
//    public static synchronized AutentiRegistry getInstance()
//    {
//        if (instance == null)
//        {
//            instance = new AutentiRegistry();
//            Registry.setInstance(instance);
//        }
//
//        return instance;
//    }

    protected Registry()
    {
        init();
    }

    public static synchronized Registry getInstance()
    {
        if (instance == null)
        {
            instance = new Registry();
        }

        return instance;
    }

    public void init()
    {
        Penny.getDefaultInstance().configure(Penny.HandleType.HANDLE_TYPE_TOAST, "Default.log", Toast.LENGTH_SHORT, "Error");
    }

    public Context getApplicationContext()
    {
        return applicationContext;
    }

    public void setApplicationContext(Context applicationContext)
    {
        this.applicationContext = applicationContext;
    }

    public DeviceScreenProperties getScreenProperties()
    {
        if (screenProperties == null) {
            screenProperties = new DeviceScreenProperties(getApplicationContext());
        }

        return screenProperties;
    }
}
