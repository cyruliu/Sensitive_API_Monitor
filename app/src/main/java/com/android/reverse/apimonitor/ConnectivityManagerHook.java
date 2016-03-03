package com.android.reverse.apimonitor;

import java.lang.reflect.Method;


import com.android.reverse.hook.HookParam;
import com.android.reverse.util.Logger;
import com.android.reverse.util.RefInvoke;

public class ConnectivityManagerHook extends ApiMonitorHook {

	@Override
	public void startHook() {
		
		Method setMobileDataEnabledmethod = RefInvoke.findMethodExact(
				"android.net.ConnectivityManager", ClassLoader.getSystemClassLoader(),
				"setMobileDataEnabled",boolean.class);
		hookhelper.hookMethod(setMobileDataEnabledmethod, new AbstractBehaviorHookCallBack() {
			
			@Override
			public void descParam(HookParam param) {
				boolean status = (Boolean) param.args[0];
                Logger.log_behavior("android.net.ConnectivityManager.setMobileDataEnabled");
				Logger.log_behavior("Set MobileDataEnabled = "+ status);
			}
		});
		
	}

}
