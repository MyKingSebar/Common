package com.common.lib_log;

import android.content.Context;

import com.tencent.mars.xlog.Log;

public class UnCatchExceptionHandler implements Thread.UncaughtExceptionHandler {
    private Context context;//留作备用

    private Thread.UncaughtExceptionHandler defaultExceptionHandler;//系统的默认异常处理类

    private static UnCatchExceptionHandler instance = new UnCatchExceptionHandler();//用户自定义的异常处理类



    private UnCatchExceptionHandler() {
    }

    public static UnCatchExceptionHandler getInstance(){
        return instance;
    }

    public void  init(Context context){
        this.context=context.getApplicationContext();
        defaultExceptionHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    @Override
    public void uncaughtException(Thread t, Throwable e) {


        Log.e("UnCatchExceptionHandler","Thread:"+t.getName()+"Throwable:"+e.getMessage());


        //如果系统提供了异常处理类，则交给系统去处理
        if (defaultExceptionHandler != null) {
            defaultExceptionHandler.uncaughtException(t,e);
        }else {
            //否则我们自己处理，自己处理通常是让app退出
            android.os.Process.killProcess(android.os.Process.myPid());
        }
    }
}
