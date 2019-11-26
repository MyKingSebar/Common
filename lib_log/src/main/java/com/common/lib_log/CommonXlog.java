package com.common.lib_log;

import android.content.Context;
import android.os.Environment;
import android.text.TextUtils;

import com.tencent.mars.xlog.Log;
import com.tencent.mars.xlog.Xlog;

class CommonXlog {
    protected static void initLog(Context context, String CommonXlog, String cachefilepath, String namePrefix, Boolean isDebnug) {
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

        //TODO 注意这个一直是release，所以想控制台输出日志需要传debug进来
        boolean debug = TextUtils.equals(BuildConfig.BUILD_TYPE, "debug");
        if (isDebnug != null) {
            debug = isDebnug;
        }
        //init xlog
        if (debug) {
            android.util.Log.d("commonlog", "init:debug");
            Xlog.appenderOpen(Xlog.LEVEL_ALL, Xlog.AppednerModeAsync, cachePath, logPath, namePrefix, 0, "");
            Xlog.setConsoleLogOpen(true);
        } else {
            android.util.Log.d("commonlog", "init:release");
            Xlog.appenderOpen(Xlog.LEVEL_INFO, Xlog.AppednerModeAsync, cachePath, logPath, namePrefix, 0, "");
            Xlog.setConsoleLogOpen(false);
        }
        Log.setLogImp(new Xlog());
    }

    protected static void logDestory() {
        Log.appenderClose();
    }
}
