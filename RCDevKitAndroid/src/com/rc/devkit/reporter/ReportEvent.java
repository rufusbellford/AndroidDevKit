package com.rc.devkit.reporter;

/**
 * Created by Softhis on 19.12.2013.
 */
public interface ReportEvent
{
    public void clicked();
    public void invoked();
    public void succedded();
    public void done();
    public void progress(int current, int max);
    public void reportValue(String title, Object val);
}
