package com.common.lib_base;

import com.tencent.mars.xlog.Log;

/**
 * 写文件需要动态申请
 */
public final class CommonLogger {
    private static Boolean debug = null;
    private static String tag = CommonLogger.class.getSimpleName();
    public static final int VERBOSE = 1;
    public static final int DEBUG = 2;
    public static final int INFO = 3;
    public static final int WARN = 4;
    public static final int ERROR = 5;
    public static final int NOTHING = 6;

    //控制log等级
    private static int LEVEL = VERBOSE;

    public static void v(String tag, String message) {
        if (LEVEL <= VERBOSE) {
            Log.v(tag,message);
        }
    }
    public static void v(String message) {
        if (LEVEL <= VERBOSE) {
            Log.v(tag,message);
        }
    }

    public static void d(String tag, String message) {
        if (LEVEL <= DEBUG) {
            Log.d(tag,message);
        }
    }

    public static void d(String message) {
        if (LEVEL <= DEBUG) {
            Log.d(tag,message);
        }
    }

    public static void i(String tag, String message) {
        if (LEVEL <= INFO) {
            Log.i(tag,message);
        }
    }
    public static void i(String message) {
        if (LEVEL <= INFO) {
            Log.i(tag,message);
        }
    }

    public static void w(String tag, String message) {
        if (LEVEL <= WARN) {
            Log.w(tag,message);
        }
    }
    public static void w(String message) {
        if (LEVEL <= WARN) {
            Log.w(tag,message);
        }
    }


    public static void e(String tag, String message) {
        if (LEVEL <= ERROR) {
            Log.e(tag,message);
        }
    }
    public static void e(String message) {
        if (LEVEL <= ERROR) {
            Log.e(tag,message);
        }
    }

    public static void init() {


    }

    private static boolean getDebug() {
        if (debug == null) {
            return AppUtils.isDebug();
        } else {
            return debug;
        }
    }

    public static class Builder {
        Boolean isDebug;
        String tag;
        int level;

        public Builder isDebug(boolean isDebug) {
            this.isDebug = isDebug;
            return this;
        }

        public Builder tag(String tag) {
            this.tag = tag;
            return this;
        }

        public Builder level(int level) {
            this.level = level;
            return this;
        }

        public void build() {
            CommonLogger.debug = isDebug;
            CommonLogger.tag = tag;
            CommonLogger.LEVEL = level;
            init();
        }
    }
}
