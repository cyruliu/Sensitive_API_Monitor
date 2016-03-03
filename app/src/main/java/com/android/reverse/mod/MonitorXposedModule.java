package com.android.reverse.mod;

import android.content.pm.ApplicationInfo;

import com.android.reverse.apimonitor.ApiMonitorHookManager;
import com.android.reverse.util.CapturedAPITransfer;
import com.android.reverse.util.Logger;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.callbacks.XC_LoadPackage.LoadPackageParam;

//import com.android.reverse.collecter.DexFileInfoCollecter;
//import com.android.reverse.collecter.LuaScriptInvoker;
//import com.android.reverse.collecter.ModuleContext;

public class MonitorXposedModule implements IXposedHookLoadPackage {

	private static final String MONITOR_PACKAGENAME = "com.android.monitor";
    private static final String ACCESSIBILITY_PACKAGE = "com.softsec.standalone";

	@Override
	public void handleLoadPackage(LoadPackageParam lpparam) throws Throwable {

		if(lpparam.appInfo == null || 
				(lpparam.appInfo.flags & (ApplicationInfo.FLAG_SYSTEM | ApplicationInfo.FLAG_UPDATED_SYSTEM_APP)) !=0){
			return;
		}else if(lpparam.isFirstApplication && !MONITOR_PACKAGENAME.equals(lpparam.packageName)
                && !ACCESSIBILITY_PACKAGE.equals(lpparam.packageName)){

		  Logger.PACKAGENAME = lpparam.packageName;
		  Logger.log("the package = "+lpparam.packageName +" has hook");
		  Logger.log("the app target id = "+android.os.Process.myPid());
		  
		  // If package name equals testing app's package name then established socket connection
          CapturedAPITransfer.connectToPC();

		  PackageMetaInfo pminfo = PackageMetaInfo.fromXposed(lpparam);
//		  Logger.log_behavior("launch app "+pminfo.getPackageName());

		  ApiMonitorHookManager.getInstance().startMonitor();

		}else{
			
		}
	}

}
