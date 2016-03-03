package com.android.reverse.apimonitor;

import java.lang.reflect.Method;

import android.hardware.Camera.PictureCallback;
import android.hardware.Camera.PreviewCallback;
import android.hardware.Camera.ShutterCallback;

import com.android.reverse.hook.HookParam;
import com.android.reverse.util.Logger;
import com.android.reverse.util.RefInvoke;

public class CameraHook extends ApiMonitorHook {

	@Override
	public void startHook() {
		// TODO Auto-generated method stub
		Method takePictureMethod = RefInvoke.findMethodExact(
				"android.hardware.Camera", ClassLoader.getSystemClassLoader(),
				"takePicture",ShutterCallback.class,PictureCallback.class,PictureCallback.class,PictureCallback.class);
		hookhelper.hookMethod(takePictureMethod, new AbstractBehaviorHookCallBack() {
			
			@Override
			public void descParam(HookParam param) {
				// TODO Auto-generated method stub
				Logger.log_behavior("android.hardware.Camera.takePicture");
			}
		});
		
		Method setPreviewCallbackMethod = RefInvoke.findMethodExact(
				"android.hardware.Camera", ClassLoader.getSystemClassLoader(),
				"setPreviewCallback",PreviewCallback.class);
		hookhelper.hookMethod(setPreviewCallbackMethod, new AbstractBehaviorHookCallBack() {
			
			@Override
			public void descParam(HookParam param) {
				// TODO Auto-generated method stub
				Logger.log_behavior("android.hardware.Camera.setPreviewCallback");
			}
		});
		
		Method setPreviewCallbackWithBufferMethod = RefInvoke.findMethodExact(
				"android.hardware.Camera", ClassLoader.getSystemClassLoader(),
				"setPreviewCallbackWithBuffer",PreviewCallback.class);
		hookhelper.hookMethod(setPreviewCallbackWithBufferMethod, new AbstractBehaviorHookCallBack() {
			
			@Override
			public void descParam(HookParam param) {
				// TODO Auto-generated method stub
				Logger.log_behavior("android.hardware.Camera.setPreviewCallbackWithBuffer");
			}
		});
		
		Method setOneShotPreviewCallbackMethod = RefInvoke.findMethodExact(
				"android.hardware.Camera", ClassLoader.getSystemClassLoader(),
				"setOneShotPreviewCallback",PreviewCallback.class);
		hookhelper.hookMethod(setOneShotPreviewCallbackMethod, new AbstractBehaviorHookCallBack() {
			
			@Override
			public void descParam(HookParam param) {
				// TODO Auto-generated method stub
				Logger.log_behavior("android.hardware.Camera.setOneShotPreviewCallback");
			}
		});
	}

}
