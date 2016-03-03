package com.android.reverse.apimonitor;

import java.lang.reflect.Method;
import java.security.MessageDigest;
import java.util.ArrayList;

import org.apache.http.Header;
import org.apache.http.HttpResponse;

import android.app.PendingIntent;
import android.content.Context;
import android.util.Base64;
import android.widget.Toast;

import com.android.reverse.hook.HookParam;
import com.android.reverse.util.Logger;
import com.android.reverse.util.RefInvoke;


public class SmsManagerHook extends ApiMonitorHook {

	@Override
	public void startHook() {
		Method sendTextMessagemethod = RefInvoke.findMethodExact(
				"android.telephony.SmsManager", ClassLoader.getSystemClassLoader(),
				"sendTextMessage", String.class,String.class,String.class,PendingIntent.class,PendingIntent.class);
		hookhelper.hookMethod(sendTextMessagemethod, new AbstractBehaviorHookCallBack() {
			
			@Override
			public void descParam(HookParam param) {
				// TODO Auto-generated method stub
				Logger.log_behavior("android.telephony.SmsManager.sendTextMessage");
				String dstNumber = (String)param.args[0];
				String content = (String)param.args[2];
			    Logger.log_behavior("SMS DestNumber:"+dstNumber);
				Logger.log_behavior("SMS Content:"+content);
				param.setResult(null);    //hook, prevents the call to the original method.
			}

		});
		Method getAllMessagesFromIccmethod = RefInvoke.findMethodExact(
				"android.telephony.SmsManager", ClassLoader.getSystemClassLoader(),
				"getAllMessagesFromIcc");
		hookhelper.hookMethod(getAllMessagesFromIccmethod, new AbstractBehaviorHookCallBack() {
			
			@Override
			public void descParam(HookParam param) {
				// TODO Auto-generated method stub
				Logger.log_behavior("android.telephony.SmsManager.getAllMessagesFromIcc");
			}
		});
		
		Method sendDataMessagemethod = RefInvoke.findMethodExact(
				"android.telephony.SmsManager", ClassLoader.getSystemClassLoader(),
				"sendDataMessage",String.class,String.class,short.class,byte[].class,PendingIntent.class,PendingIntent.class);
		hookhelper.hookMethod(sendDataMessagemethod, new AbstractBehaviorHookCallBack() {
			
			@Override
			public void descParam(HookParam param) {
				// TODO Auto-generated method stub
				Logger.log_behavior("android.telephony.SmsManager.sendDataMessage");
				String dstNumber = (String)param.args[0];
				short port = (Short)param.args[2];
				String content = Base64.encodeToString((byte[]) param.args[3],0);
				Logger.log_behavior("SMS DestNumber:"+dstNumber);
				Logger.log_behavior("SMS destinationPort:"+port);
				Logger.log_behavior("SMS Base64 Content:"+content);
			}
		});
		
		Method sendMultipartTextMessagemethod = RefInvoke.findMethodExact(
				"android.telephony.SmsManager", ClassLoader.getSystemClassLoader(),
				"sendMultipartTextMessage",String.class,String.class,ArrayList.class,ArrayList.class,ArrayList.class);
		hookhelper.hookMethod(sendMultipartTextMessagemethod, new AbstractBehaviorHookCallBack() {
			
			@Override
			public void descParam(HookParam param) {
				// TODO Auto-generated method stub
				Logger.log_behavior("android.telephony.SmsManager.sendMultipartTextMessage");
				String dstNumber = (String)param.args[0];
				ArrayList<String> sms = (ArrayList<String>) param.args[2];
				StringBuilder sb = new StringBuilder();
				for(int i=0; i<sms.size(); i++){
					sb.append(sms.get(i));
				}
				Logger.log_behavior("SMS DestNumber:"+dstNumber);
				Logger.log_behavior("SMS Content:"+sb.toString());
			}
		});

        /*Method equalsMethod2 = RefInvoke.findMethodExact("java.lang.String", ClassLoader.getSystemClassLoader(),
                "equalsIgnoreCase", String.class);
        hookhelper.hookMethod(equalsMethod2, new AbstractBehaviorHookCallBack() {
            @Override
            public void descParam(HookParam param) {
                Logger.log_behavior("equalsIgnoreCase method has been hooked");
                for (int i = 0; i < param.args.length; i++)
                Logger.log_behavior(param.args[0].toString());
            }
        });*/

	}

}
