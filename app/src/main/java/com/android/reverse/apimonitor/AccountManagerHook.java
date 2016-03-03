package com.android.reverse.apimonitor;

import java.lang.reflect.Method;

import com.android.reverse.hook.HookParam;
import com.android.reverse.util.Logger;
import com.android.reverse.util.RefInvoke;

public class AccountManagerHook extends ApiMonitorHook {

	
	@Override
	public void startHook() {
		
		Method getAccountsMethod = RefInvoke.findMethodExact(
				"android.accounts.AccountManager", ClassLoader.getSystemClassLoader(),
				"getAccounts");
		hookhelper.hookMethod(getAccountsMethod, new AbstractBehaviorHookCallBack() {
			
			@Override
			public void descParam(HookParam param) {
				// TODO Auto-generated method stub
				Logger.log_behavior("android.accounts.AccountManager.getAccounts");
			}
		});	
		
		Method getAccountsByTypeMethod = RefInvoke.findMethodExact(
				"android.accounts.AccountManager", ClassLoader.getSystemClassLoader(),
				"getAccountsByType",String.class);
		hookhelper.hookMethod(getAccountsByTypeMethod, new AbstractBehaviorHookCallBack() {
			
			@Override
			public void descParam(HookParam param) {
				// TODO Auto-generated method stub
				String type = (String) param.args[0];
				Logger.log_behavior("android.accounts.AccountManager.getAccountsByType");
				Logger.log_behavior("type :" +type);
			}
		});	
	}

}
