package com.common.lib_base;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.text.TextUtils;

/**
 * lib不能用BuildConfig 获取是否debug
 * AppUtils需要初始化
 */
public class AppUtils {
    private static Boolean isDebug = null;

    public static boolean isDebug() {
        return TextUtils.equals(BuildConfig.BUILD_TYPE,"debug");
    }
//    public static boolean isDebug() {
//        return isDebug == null ? false : isDebug.booleanValue();
//    }
//
//    /**
//     * Sync lib debug with app's debug value. Should be called in moudle Application
//     */
//    public static void syncIsDebug(Context context) {
//        if (isDebug == null) {
//            isDebug = context.getApplicationContext() != null &&
//                    (context.getApplicationInfo().flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0;
//        }
//    }
}
