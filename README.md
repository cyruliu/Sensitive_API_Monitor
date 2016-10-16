_Knowing what your apps are really doing when you are using them._
=================================================================
- This is an Android dynamic detection program, the module is developed based on Xposed Framework. The program could monitor the status of system API invoked by the running apps on the mobile phone. This module can:
- Record the details of app running on Android system, the information include, specific API being invoked, and the parameter values being called;
- Stop the suspicious app getting the personal information without user's permission, e.g., sending message secretly, turning on the camera background, etc.
- To be modified for more features...

# System Requirement

| Configuration       | Requirement        |
| ------------------- |:--------------------:|
| Root Access       | The mechanism of Xposed framework is the substitute of the file under directory: /system/bin. Root permission needed when installing the framework.|
| Version        | Android 4.0 above     |  

Framework of Sensitive API Monitor module
----------------------------------------------
This module is developed based on Xposed framework, its working flow can be describe as follow:

![image loading](https://github.com/donggobler/Sensitive_API_Monitor/blob/master/images/monitor-flow.png "module working flow" wide="480")

The module consist of two parts:
1.  XposedBridge.jar loading, design classes that inherit its XC_MethodHook, XCallBack, MethodHookParam etc.
2.  API Hooking, Parameter modifying.

![image loading](https://github.com/donggobler/Sensitive_API_Monitor/blob/master/images/monitor-framework.png "module components")

All classes and its API that hooked by the module are list below:


| Class      | Method        |
| ------------------- |:--------------------:|
| andriod.harware.Camera    | takePicture, setPreviewCallback, setPreviewCallbackWithBuffer, setOneShotPreviewCallback|
| android.accounts.AccountManager       | getAccountsByType     |
| android.app.ActivityManager | killBackgroundPreocess, forceStopPAckage            |  
| android.media.AudioRecord| startRecording |
|android.net.ConnectivityManager| setMobileDataEnable|
| android.media.MediaRecorder| start, stop|
| java.net.URL| openConnetction |
| org.apache.http.impl.client.AbstractHttpClient| excute|
| android.app.NotificationManager| notify|
| android.app.ApplicationPackageManager| setComponentEnabledSetting, installPackage, deletePackage, getInstallPackages|
| java.lang.ProcessBuilder| start|
| java.lang.Runtime| exec|
| android.telephony.SmsManager| sendTextMessage, getAllMessagesFromIcc, sendDataMessage, sendMultipartTextMessage|
| android.telephony.TelephonyManager| getLine1Number, listen|

All relevant APIs hook methods are defined in the class ApiMonitorHookManager:

    public void startMonitor(){
        this.smsManagerHook.startHook();
        this.telephonyManagerHook.startHook();
        this.mediaRecorderHook.startHook();
        this.accountManagerHook.startHook();
        this.activityManagerHook.startHook();
        this.alarmManagerHook.startHook();
        this.locationHook.startHook();
        this.connectivityManagerHook.startHook();
        this.contentResolverHook.startHook();
        this.contextImplHook.startHook();
        this.packageManagerHook.startHook();
        this.runtimeHook.startHook();
        this.activityThreadHook.startHook();
        this.audioRecordHook.startHook();
        this.cameraHook.startHook();		  
        this.networkHook.startHook();
        }
Through Java reflection, each object of the method invoked by the application can be obtained by program, overriding the Xposed method before(), after(), in which we can imbed our personal code, such as for the class smsManagerHook:

    @Override
      public void descParam(HookParam param) {
        // TODO Auto-generated method stub
        Logger.log_behavior("Send SMS ->");
        String dstNumber = (String)param.args[0];
        String content = (String)param.args[2];
        Logger.log_behavior("SMS DestNumber:"+dstNumber);
        Logger.log_behavior("SMS Content:"+content);
        param.setResult(null);    //hook, prevents the call to the original method.
      }
 Experimental Testing
------------------------------
-  Download/Upload: When the application try to download something from remote server, this module can catch its server address:

![download-demo](https://github.com/donggobler/Sensitive_API_Monitor/blob/master/images/download.png "app-download"),
![download-demo](https://github.com/donggobler/Sensitive_API_Monitor/blob/master/images/upload.png "log-upload"),  ![code-loading](https://github.com/donggobler/Sensitive_API_Monitor/blob/master/images/download-demo.png "logcate-download")
-  Sending Message: Destination number and the text will be recorded when program has the function of sending message.

![sms-demo](https://github.com/donggobler/Sensitive_API_Monitor/blob/master/images/sms.png "sms-app"), ![catlog-sms](https://github.com/donggobler/Sensitive_API_Monitor/blob/master/images/sms-demo.png)
-  Network Connection: For now the module monitor post and get ways of http connection.

![network-demo](https://github.com/donggobler/Sensitive_API_Monitor/blob/master/images/network.png "web-app"), ![network-connection](https://github.com/donggobler/Sensitive_API_Monitor/blob/master/images/network-demo.png "connection recording")
