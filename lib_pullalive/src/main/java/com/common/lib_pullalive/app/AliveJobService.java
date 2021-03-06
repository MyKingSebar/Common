package com.common.lib_pullalive.app;

import android.annotation.TargetApi;
import android.app.job.JobInfo;
import android.app.job.JobParameters;
import android.app.job.JobScheduler;
import android.app.job.JobService;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.Message;

import com.common.lib_log.CommonLog;


/**
 * 一个轻量的后台job service,利用空闲时间执行一些小事情，提高进程不被回收的概率
 */
@TargetApi(value = Build.VERSION_CODES.LOLLIPOP)
public class AliveJobService extends JobService {
    private static final String TAG = AliveJobService.class.getName();

    private static volatile boolean sAlive = false;

    public static final int CLASS = 1;
    public static final int SERVICE = 2;

    private static String packageName = null;
    private static String action = null;
    private static String className = null;
    private static boolean needTop = false;
    private static int type = 0;
    private static Context mContext = null;
    private JobScheduler mJobScheduler;


    private Handler mJobHandler = new Handler(new Handler.Callback() {

        @Override
        public boolean handleMessage(Message msg) {
            CommonLog.d(TAG, "pull alive." + ",packageName:" + packageName + ",action:" + action + ",className:" + className);
//            if(mContext==null){
//                //只要不在前台就会空
//                getApplicationContext().sendBroadcast(new Intent(AliveJobService.action));
//                CommonLog.d(TAG, "mContextAPP现状：杀死，重启...");
//                return true;
//            }
            Context mContext = getApplicationContext();
            if (null == mContext) {
                CommonLog.d(TAG, "getApplicationContext()==null");
                return true;
            }
            if (needTop) {
                if (mContext == null || !SystemUtils.isAppOnForeground(mContext)) {
                    getApplicationContext().sendBroadcast(new Intent(AliveJobService.action));
                    CommonLog.d(TAG, "APP现状：杀死，重启...top");
                }
            } else {
                CommonLog.d(TAG, "intent" + packageName + action + className + type);
                if (packageName != null && action != null && className != null && type != 0) {
                    switch (type) {
                        case CLASS:
                            if (!SystemUtils.isActivityExisted(getApplicationContext(), packageName, className)) {
                                getApplicationContext().sendBroadcast(new Intent(AliveJobService.action));
                                CommonLog.d(TAG, "APP现状：杀死，重启...class");
                            }
                            break;
                        case SERVICE:
                            CommonLog.d(TAG,"context:"+getApplicationContext()+",className"+className);
                            if (!SystemUtils.isServiceRunning(getApplicationContext(), className)) {
//                            if (!SystemUtils.isServiceExisted(getApplicationContext(), className)) {
                                getApplicationContext().sendBroadcast(new Intent(AliveJobService.action));
                                CommonLog.d(TAG, "APP现状：杀死，重启...service");
                            }else {
                                CommonLog.d(TAG, "APP现状：还活着...service");
                            }
                            break;
                        default:
                            throw new IllegalStateException("Unexpected value: " + type);
                    }

                }
            }
            jobFinished((JobParameters) msg.obj, false);

            return true;
        }
    });

    //普通保活
    public static void start(Context context) {
        if (!AliveJobService.isJobServiceAlive() || Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mContext = context;
            Intent intent = new Intent(context, AliveJobService.class);
            context.startService(intent);
        }
    }

    //拉起保活
    public static void start(Context context, String packageName, String className, String action, boolean needTop, int type) {
        CommonLog.d(TAG, "start:context:" + context + ",packageName:" + packageName + ",className:" + className + ",action" + action + ",needTop:" + needTop + ",type:" + type);
        if (!AliveJobService.isJobServiceAlive() || Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mContext = context;
            Intent intent = new Intent(context, AliveJobService.class);
            intent.putExtra("packageName", packageName);
            intent.putExtra("action", action);
            intent.putExtra("className", className);
            intent.putExtra("needTop", needTop);
            intent.putExtra("type", type);
            context.startService(intent);
        }
    }

    public static boolean isJobServiceAlive() {
        return sAlive;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        CommonLog.d(TAG, "onCreate");
        mJobScheduler = (JobScheduler) getSystemService(Context.JOB_SCHEDULER_SERVICE);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        CommonLog.d(TAG, "onStartCommand");
        JobInfo job = initJobInfo(startId);
        if (mJobScheduler.schedule(job) <= 0) {
            CommonLog.d(TAG, "AliveJobService failed");
        } else {
            CommonLog.d(TAG, "AliveJobService success");
        }
        if (intent.hasExtra("packageName")) {
            AliveJobService.packageName = intent.getStringExtra("packageName");
        }
        if (intent.hasExtra("action")) {
            AliveJobService.action = intent.getStringExtra("action");
        }
        if (intent.hasExtra("className")) {
            AliveJobService.className = intent.getStringExtra("className");
        }
        if (intent.hasExtra("needTop")) {
            AliveJobService.needTop = intent.getBooleanExtra("needTop", false);
        }
        if (intent.hasExtra("type")) {
            AliveJobService.type = intent.getIntExtra("type", 0);
        }
        return START_REDELIVER_INTENT;
//        return START_STICKY;
    }

    //开始任务
    @Override
    public boolean onStartJob(JobParameters params) {
        CommonLog.e(TAG, "onStartJob" + params);
        sAlive = true;
        mJobHandler.sendMessage(Message.obtain(mJobHandler, 1, params));
        return true;
    }

    //结束任务
    @Override
    public boolean onStopJob(JobParameters params) {
        CommonLog.e(TAG, "onStopJob" + params);
        sAlive = false;
        mJobHandler.removeMessages(1);
//        mJobHandler.sendMessage(Message.obtain(mJobHandler, 1, params));
        return false;
    }

    //执行条件
    private JobInfo initJobInfo(int startId) {
        JobInfo.Builder builder = new JobInfo.Builder(startId,
                new ComponentName(getPackageName(), AliveJobService.class.getName()));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            builder.setMinimumLatency(JobInfo.DEFAULT_INITIAL_BACKOFF_MILLIS); //执行的最小延迟时间
            builder.setOverrideDeadline(JobInfo.DEFAULT_INITIAL_BACKOFF_MILLIS);  //执行的最长延时时间
            builder.setBackoffCriteria(JobInfo.DEFAULT_INITIAL_BACKOFF_MILLIS,
                    JobInfo.BACKOFF_POLICY_LINEAR);//线性重试方案
        } else {
            builder.setPeriodic(JobInfo.DEFAULT_INITIAL_BACKOFF_MILLIS);
        }
        builder.setPersisted(false);
        builder.setRequiredNetworkType(JobInfo.NETWORK_TYPE_NONE);
        builder.setRequiresCharging(false);
        return builder.build();
    }
}
