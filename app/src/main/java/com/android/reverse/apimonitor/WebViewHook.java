package com.android.reverse.apimonitor;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import android.os.Binder;
import android.util.Log;
import android.webkit.WebView;


import com.android.reverse.hook.HookParam;
import com.android.reverse.util.Logger;
import com.android.reverse.util.RefInvoke;


public class WebViewHook extends ApiMonitorHook{
	@Override
	public void startHook(){
		Method loadUrlMethod=RefInvoke.findMethodExact("android.webkit.WebView", ClassLoader.getSystemClassLoader(), "loadUrl",String.class);
		hookhelper.hookMethod(loadUrlMethod, new AbstractBehaviorHookCallBack(){
			@Override
			public void descParam(HookParam param){	
				Logger.log_behavior("Connect to WebView ->");
//				WebView webView = (WebView) param.thisObject;
				if (param.args.length > 0 && param.args[0] instanceof String){				
					for(int i = 0; i < param.args.length; i ++){
						Logger.log_behavior("" + i + ": " + param.args[i]);
						}			
				}		
			}
			
		});
		
		
	}
	
	
	
	
	

}
