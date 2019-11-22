package com.common.lib_log;

import android.content.Context;
import android.os.Environment;
import android.text.TextUtils;

import com.tencent.mars.xlog.Log;
import com.tencent.mars.xlog.Xlog;

class CommonXlog {
   protected static void initLog(Context context, String CommonXlog, String cachefilepath, String namePrefix) {
       System.loadLibrary("c++_shared");
       System.loadLibrary("marsxlog");


       final String SDCARD = Environment.getExternalStorageDirectory().getAbsolutePath();

       if (TextUtils.isEmpty(CommonXlog)) {
           CommonXlog = SDCARD + "/marssample/log";
       }

       if (TextUtils.isEmpty(cachefilepath)) {
           if (null != context) {
               CommonXlog = context.getFilesDir() + "/xlog";
           }
//            else {
//                CommonXlog = SDCARD + "/xlog";
//            }

       }

       if (TextUtils.isEmpty(CommonXlog)) {
           CommonXlog = "xlog";
       }


       final String logPath = CommonXlog;
//        final String logPath = SDCARD + "/marssample/log";

       // this is necessary, or may crash for SIGBUS
       final String cachePath = cachefilepath;
//        final String cachePath = context.getFilesDir() + "/xlog";


       //init xlog
       if (BuildConfig.DEBUG) {
           Xlog.appenderOpen(Xlog.LEVEL_ALL, Xlog.AppednerModeAsync, cachePath, logPath, namePrefix, 0, "");
           Xlog.setConsoleLogOpen(true);
       } else {
           Xlog.appenderOpen(Xlog.LEVEL_INFO, Xlog.AppednerModeAsync, cachePath, logPath, namePrefix, 0, "");
           Xlog.setConsoleLogOpen(false);
       }
       Log.setLogImp(new Xlog());
   }

   protected static void logDestory() {
       Log.appenderClose();
   }
}
