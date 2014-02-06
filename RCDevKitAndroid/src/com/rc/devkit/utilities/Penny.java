package com.rc.devkit.utilities;

import android.app.AlertDialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Penny
{
    private static Penny instance = null;
    private Config config;

    public static Penny getDefaultInstance()
    {
        if (instance == null) {
            instance = new Penny();
            instance.resetConfiguration();
        }
        return instance;
    }

    public void resetConfiguration()
    {
        config = new Config();
    }

    public void configure(HandleType defaultHandleType, String logFileName, int toastLength, String errorTitle)
    {
        String logTag = "";
        config = new Config(logTag, defaultHandleType, logFileName, toastLength, errorTitle);
    }

    // TODO: Resolve TimeStamp issue!
//    public ErrorHandler appendTimeStamp()
//    {
//        // TODO: This should work only for one print, now it is implemented wrong
//        this.shouldAddTimestamp = true;
//        return this;
//    }

    public void handleError(String errorMessage)
    {
        displayMessage(config.errorTitle, errorMessage);
    }

    public void handleError(String errorMessage, HandleType type)
    {
        displayMessage(config.errorTitle, errorMessage, type);
    }

    public void handleError(String errorTitle, String error, HandleType type)
    {
        displayMessage(errorTitle, error, type);
    }

    public void handleException(Exception exception)
    {
        displayMessage("Exception", exception.getMessage());
    }

    public void presentInfo(String message)
    {
        displayMessage("I:", message);
    }

    public void printMethod()
    {
        String message = Thread.currentThread().getStackTrace()[3].getMethodName().toString();
        displayMessage(message, null);
    }

    public void printMethod(HandleType type)
    {
        String message = Thread.currentThread().getStackTrace()[3].getMethodName().toString();
        displayMessage(message, null, type);
    }

    public void presentMessage(String message)
    {
        displayMessage(message, null);
    }

    public void presentMessage(String message, HandleType type)
    {
        displayMessage(message, null, type);
    }

    public void presentMessage(String title, String message)
    {
        displayMessage(title, message);
    }

    public void presentMessage(String title, String message, HandleType type)
    {
        displayMessage(title, message, type);
    }

    private void displayMessage(String title, String message)
    {
        displayMessage(title, message, config.defaultHandleType);
    }

    private void displayMessage(String title, String message, HandleType type)
    {
        switch (type) {
            case HANDLE_TYPE_LOG:
                displayLog(title, message);
                break;
            case HANDLE_TYPE_FILE:
                writeToFile(title, message);
                break;
            case HANDLE_TYPE_ALERT:
                displayAlert(title, message);
                break;
            case HANDLE_TYPE_TOAST:
                displayToast(title, message);
                break;
            case HANDLE_TYPE_CUSTOM:
                break;
            case HANDLE_TYPE_NOTHING:
            default:
                break;
        }
    }

    private void displayLog(String title, String message)
    {
        String logMessage = prepareMessage(title, message);
        Log.e(config.logTag, logMessage);
    }

    private void displayToast(String title, String message)
    {
        String toastMessage = prepareMessage(title, message);
        Toast.makeText(Registry.getInstance().getApplicationContext(), toastMessage, config.toastLength).show();
    }

    private void displayAlert(String title, String message)
    {
        new AlertDialog.Builder(Registry.getInstance().getApplicationContext())
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                }).show();
    }

    private void writeToFile(String title, String message)
    {
        if (config.logFile == null)
        {
            config.createLogFile(config.logFileName, true);
        }

        try
        {
            PrintWriter printWriter = new PrintWriter(new BufferedWriter(new FileWriter(config.logFile, true)));
            String writeMessage = prepareMessage(title, message);
            printWriter.println(writeMessage);
            printWriter.close();
        }
        catch(Exception e)
        {
            displayLog("Error", "Can't write file. " + e.getMessage());
        }
    }

    private String prepareMessage(String title, String message)
    {
        String resultMessage = "";
        boolean hasTitle = false;

        // TODO: TimeStamp
//        if (config.shouldAddTimestamp)
        if(false)
        {
            Date date = new Date();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss:SSS");
            resultMessage += simpleDateFormat.format(date) + "#";
        }

        if (title != null && !title.equalsIgnoreCase("")) {
            resultMessage += title;
            hasTitle = true;
        }

        if (message != null && !message.equalsIgnoreCase(""))
        {
            if (hasTitle) {
                resultMessage += ": ";
            }

            resultMessage += message;
        }

        return resultMessage;
    }

    public static enum HandleType
    {
        HANDLE_TYPE_LOG,
        HANDLE_TYPE_NOTHING,
        HANDLE_TYPE_FILE,
        HANDLE_TYPE_ALERT,
        HANDLE_TYPE_TOAST,
        HANDLE_TYPE_CUSTOM
    }

    public static class Config
    {
        private String logTag;
        private HandleType defaultHandleType;
        private String logFileName;
        private int toastLength;
        private String errorTitle;
        private File logFile;

        public Config()
        {
            logFileName = "ERROR_HANDLE_LOG_FILE.log";
            toastLength = Toast.LENGTH_SHORT;
            logFile = null;
            errorTitle = "Error";
            defaultHandleType = HandleType.HANDLE_TYPE_LOG;
//            this.displayPackage(false);
        }

        public Config(String logTag, HandleType defaultHandleType, String logFileName, int toastLength, String errorTitle)
        {
            this.logTag = logTag;
            this.defaultHandleType = defaultHandleType;
            this.logFileName = logFileName;
            this.toastLength = toastLength;
            this.errorTitle = errorTitle;
//            this.displayPackage(displayPackage);
        }

        // Display package in logs
        public void displayPackage(boolean displayPackage)
        {
            Context applicationContext = Registry.getInstance().getApplicationContext();
            if (displayPackage)
            {
                logTag = applicationContext.getPackageName();
            }
            else
            {
                PackageManager packageManager = Registry.getInstance().getApplicationContext().getPackageManager();
                ApplicationInfo ai;
                try
                {
                    ai = packageManager.getApplicationInfo( applicationContext.getPackageName(), 0);
                }
                catch (final PackageManager.NameNotFoundException e) {
                    ai = null;
                }

                final String applicationName = (String) (ai != null ? packageManager.getApplicationLabel(ai) : "(unknown)");
                logTag = applicationName;
            }
        }

        public void createLogFile(String logFileName, boolean shouldBeExternal)
        {
            String absoluteFilePath;

            if (shouldBeExternal)
            {
                absoluteFilePath = Environment.getExternalStorageDirectory() + "/" + logFileName;
            }
            else
            {
                ContextWrapper contextWrapper = new ContextWrapper(Registry.getInstance().getApplicationContext());
                absoluteFilePath = contextWrapper.getFilesDir().getAbsolutePath() + "/" + logFileName;
            }

            logFile = new File(absoluteFilePath);
        }

        // Handle this
//        private final String APP_NAME = "Orbizer";
//        private final String logFileName = APP_NAME + ".log";
//        private boolean shouldAddTimestamp = false;
//        private String packageName = null;

//        private final int toastLength = Toast.LENGTH_SHORT;
//        private File logFile = null;
//        private String errorTitle = "Error";
//        private HandleType defaultHandleType = HandleType.HANDLE_TYPE_LOG;
    }

    //    private final String APP_NAME = "Orbizer";
//    private final String logFileName = APP_NAME + ".log";
//    private final int toastLength = Toast.LENGTH_SHORT;
//    private File logFile = null;
//    private String errorTitle = "Error";
//    private boolean shouldAddTimestamp = false;
//    private String packageName = null;
//    private HandleType defaultHandleType = HandleType.HANDLE_TYPE_LOG;
}
