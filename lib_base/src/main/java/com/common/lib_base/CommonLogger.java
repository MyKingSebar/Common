package com.common.lib_base;

import android.os.Build;

import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.CsvFormatStrategy;
import com.orhanobut.logger.DiskLogAdapter;
import com.orhanobut.logger.Logger;

/**
 * 写文件需要动态申请
 */
public final class CommonLogger {
    private static Boolean debug = null;
    private static String tag = "logger";
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
            Logger.t(tag).v(message);
        }
    }

    public static void d(String tag, Object message) {
        if (LEVEL <= DEBUG) {
            Logger.t(tag).d(message);
        }
    }

    public static void d(Object message) {
        if (LEVEL <= DEBUG) {
            Logger.d(message);
        }
    }

    public static void i(String tag, String message) {
        if (LEVEL <= INFO) {
            Logger.t(tag).i(message);
        }
    }

    public static void w(String tag, String message) {
        if (LEVEL <= WARN) {
            Logger.t(tag).w(message);
        }
    }

    public static void json(String tag, String message) {
        if (LEVEL <= WARN) {
            Logger.t(tag).json(message);
        }
    }

    public static void e(String tag, String message) {
        if (LEVEL <= ERROR) {
            Logger.t(tag).e(message);
        }
    }

    public static void init() {
        CsvFormatStrategy formatStrategy = CsvFormatStrategy.newBuilder()
                .tag(tag)
                .build();
        if (getDebug()) {
            Logger.addLogAdapter(new AndroidLogAdapter() {
                // 是否开启打印功能，返回true则打印，否则不打印
                @Override
                public boolean isLoggable(int priority, String tag) {
                    return true;
                }
            });
        } else {
            Logger.addLogAdapter(new AndroidLogAdapter() {
                // 是否开启打印功能，返回true则打印，否则不打印
                @Override
                public boolean isLoggable(int priority, String tag) {
                    return true;
                }
            });
            Logger.addLogAdapter(new DiskLogAdapter(formatStrategy));
        }


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
