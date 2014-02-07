package com.rc.devkit.examples;

import com.rc.devkit.debug.DebugActivity;
import com.rc.devkit.debug.DebugRunOption;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;


public class MainExample extends DebugActivity 
{
	@Override
	public void initRunOptions() 
	{
		register(new DebugRunOption(FirstActivity.class, "First"));
		register(new DebugRunOption(SecondActivity.class, "Second"));
	}

	@Override
	public void onPostStartRunOption(DebugRunOption arg0) 
	{
	}

	@Override
	public void onPreStartRunOption(DebugRunOption arg0) 
	{
	}

	@Override
	public int runOnStartOptionOnIndex() 
	{
		return -1;
	}	
}
