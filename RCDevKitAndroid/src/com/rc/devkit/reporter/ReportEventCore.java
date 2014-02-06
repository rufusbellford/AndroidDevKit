package com.rc.devkit.reporter;

/**
 * Created by Softhis on 19.12.2013.
 */
public abstract class ReportEventCore
{
    private Reporter parent;

    public ReportEventCore(Reporter parent)
    {
        this.parent = parent;
    }

    private void addLog(ReportLog entry)
    {
        processReportLog(entry);
        parent.addLog(entry);
    }

    private String getInvokingMethodName()
    {
        String methodName = Thread.currentThread().getStackTrace()[5].getMethodName().toString();
        return methodName;
    }

    protected void addTextLog(String log)
    {
        String method = getInvokingMethodName();
        ReportLog entry = new ReportLog(log, method);
        addLog(entry);
    }

    protected void addProgress(Integer current, Integer max)
    {
        String method = getInvokingMethodName();
        ReportLog entry = new ReportLog(current + " / " + max, method);
        addLog(entry);
    }

    protected abstract void processReportLog(ReportLog reportLog);
}
