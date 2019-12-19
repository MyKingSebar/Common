package com.common.lib_log;

import android.content.Context;
import android.text.TextUtils;

import com.tencent.mars.xlog.Log;


/**
 * 写文件需要动态申请
 */
public final class CommonLog {
    private static Boolean debug = null;
    private static String tag = "CommonLog";
    public static final int VERBOSE = 1;
    public static final int DEBUG = 2;
    public static final int INFO = 3;
    public static final int WARN = 4;
    public static final int ERROR = 5;
    public static final int NOTHING = 6;

    //控制log等级
    private static int LEVEL = VERBOSE;
    private static int USESEGMENTATION = 3000;

    public static void v(String tag, String message) {
        if (LEVEL <= VERBOSE) {
            Log.v(tag, message);
        }
    }

    public static void v(String message) {
        if (LEVEL <= VERBOSE) {
            Log.v(tag, message);
        }
    }

    public static void d(String tag, String message) {
        if (LEVEL <= DEBUG) {
            Log.d(tag, message);
        }
    }

    public static void d(String message) {
        if (LEVEL <= DEBUG) {
            Log.d(tag, message);
        }
    }

    public static void i(String tag, String message) {
        if (LEVEL <= INFO) {
            Log.i(tag, message);
        }
    }

    public static void i(String message) {
        if (LEVEL <= INFO) {
            Log.i(tag, message);
        }
    }

    public static void w(String tag, String message) {
        if (LEVEL <= WARN) {
            Log.w(tag, message);
        }
    }

    public static void w(String message) {
        if (LEVEL <= WARN) {
            Log.w(tag, message);
        }
    }


    public static void e(String tag, String message) {
        if (LEVEL <= ERROR) {
            Log.e(tag, message);
        }
    }

    public static void e(String message) {
        if (LEVEL <= ERROR) {
            Log.e(tag, message);
        }
    }
    public static void vs(String tag, String message) {
        if (LEVEL <= VERBOSE) {
            logSegmentation(tag, message, new LogListener() {
                @Override
                public void log(String tag, String message) {
                    Log.v(tag, message);
                }
            });
        }
    }

    public static void vs(String message) {
        if (LEVEL <= VERBOSE) {
            vs(tag, message);
        }
    }

    public static void ds(String tag, String message) {
        if (LEVEL <= DEBUG) {
            logSegmentation(tag, message, new LogListener() {
                @Override
                public void log(String tag, String message) {
                    Log.d(tag, message);
                }
            });
        }
    }

    public static void ds(String message) {
        if (LEVEL <= DEBUG) {
            ds(tag, message);
        }
    }

    public static void is(String tag, String message) {
        if (LEVEL <= INFO) {
            logSegmentation(tag, message, new LogListener() {
                @Override
                public void log(String tag, String message) {
                    Log.i(tag, message);
                }
            });
        }
    }

    public static void is(String message) {
        if (LEVEL <= INFO) {
            is(tag, message);
        }
    }

    public static void ws(String tag, String message) {
        if (LEVEL <= WARN) {
            logSegmentation(tag, message, new LogListener() {
                @Override
                public void log(String tag, String message) {
                    Log.w(tag, message);
                }
            });
        }
    }

    public static void ws(String message) {
        if (LEVEL <= WARN) {
            ws(tag, message);
        }
    }


    public static void es(String tag, String message) {
        if (LEVEL <= ERROR) {
            logSegmentation(tag, message, new LogListener() {
                @Override
                public void log(String tag, String message) {
                    Log.e(tag, message);
                }
            });
        }
    }

    public static void es(String message) {
        if (LEVEL <= ERROR) {
            es(tag, message);
        }
    }

    /**
     *  切割日志 打印完整
     * @param msg
     */
    private static void logSegmentation(String tag, String msg, LogListener listener){
        if(listener==null){
            return;
        }
        if(USESEGMENTATION<=0){
            listener.log(tag,msg);
        }

        int length = msg.length();
        String log;
        if (length <= USESEGMENTATION ) {
            log = tag;
        } else {
            log = msg.substring(0,USESEGMENTATION);
            logSegmentation(tag,msg.substring(USESEGMENTATION),listener);
        }
        listener.log(tag,log);

    }
    interface LogListener{
        void log(String tag,String message);
    }

    public static void init(Context context, String filePath, String cachefilepath, String namePrefix) {
//        CsvFormatStrategy formatStrategy = CsvFormatStrategy.newBuilder()
//                .tag(tag)
//                .build();
//        if (getDebug()) {
//            Logger.addLogAdapter(new AndroidLogAdapter() {
//                // 是否开启打印功能，返回true则打印，否则不打印
//                @Override
//                public boolean isLoggable(int priority, String tag) {
//                    return true;
//                }
//            });
//        } else {
//            Logger.addLogAdapter(new AndroidLogAdapter() {
//                // 是否开启打印功能，返回true则打印，否则不打印
//                @Override
//                public boolean isLoggable(int priority, String tag) {
//                    return true;
//                }
//            });
//            Logger.addLogAdapter(new DiskLogAdapter(formatStrategy));
//        }
        CommonXlog.initLog(context.getApplicationContext(), filePath, context.getFilesDir() + "/xlog", namePrefix, null);
    }

    public static void init(Context context, String filePath, String cachefilepath, String namePrefix, boolean isDebug) {
        CommonXlog.initLog(context.getApplicationContext(), filePath, context.getFilesDir() + "/xlog", namePrefix, isDebug);
    }

    public static void onDestory() {
        CommonLog.e("CommonLog onDestroy");
        CommonXlog.logDestory();
    }

    private static boolean getDebug() {
        if (debug == null) {
            return TextUtils.equals("debug", BuildConfig.BUILD_TYPE);
        } else {
            return debug;
        }
    }

    public static class Builder {
        Boolean isDebug;
        String tag;
        int level;
        int useSegmentation;


        public Builder useSegmentation(int useSegmentation) {
            this.useSegmentation = useSegmentation;
            return this;
        }

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
            CommonLog.debug = isDebug;
            CommonLog.tag = tag;
            CommonLog.LEVEL = level;
            CommonLog.USESEGMENTATION = useSegmentation;
        }
    }
}