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
import android.util.Log;


/**
 * 一个轻量的后台job service,利用空闲时间执行一些小事情，提高进程不被回收的概率
 */
@TargetApi(value = Build.VERSION_CODES.LOLLIPOP)
public class AliveJobService extends JobService {
    private static final String TAG = AliveJobService.class.getName();

    public static final int CLASS=1;
    public static final int SERVICE=2;

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
            Log.d(TAG, "pull alive." + ",packageName:" + packageName + ",action:" + action + ",className:" + className);
//            if(mContext==null){
//                //只要不在前台就会空
//                getApplicationContext().sendBroadcast(new Intent(AliveJobService.action));
//                Log.d(TAG, "mContextAPP现状：杀死，重启...");
//                return true;
//            }

            if (needTop) {
                if (mContext == null || !SystemUtils.isAppOnForeground(mContext)) {
                    getApplicationContext().sendBroadcast(new Intent(AliveJobService.action));
                    Log.d(TAG, "APP现状：杀死，重启...");
                }
            } else {
                Log.d(TAG,"intent"+packageName+action+className+type);
                if (packageName != null && action != null && className != null&&type!=0) {
                    switch (type){
                        case CLASS:
                            if (!SystemUtils.isActivityExisted(getApplicationContext(), packageName, className)) {
                                getApplicationContext().sendBroadcast(new Intent(AliveJobService.action));
                                Log.d(TAG, "APP现状：杀死，重启...");
                            }
                            break;
                        case SERVICE:
                            if(!SystemUtils.isServiceExisted(getApplicationContext(), className)){
                                getApplicationContext().sendBroadcast(new Intent(AliveJobService.action));
                                Log.d(TAG, "APP现状：杀死，重启...");
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
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mContext = context;
            Intent intent = new Intent(context, AliveJobService.class);
            context.startService(intent);
        }
    }


    /**
     * 拉起保活
     *
     *eg:AliveJobService.start(context, context.getPackageName(), DaemonService.class.getName(), KeepAliveReceiver.LINE_SCREEN, false, AliveJobService.SERVICE);
     */

    public static void start(Context context, String packageName, String className, String action, boolean needTop,int type) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
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

    @Override
    public void onCreate() {
        super.onCreate();
        mJobScheduler = (JobScheduler) getSystemService(Context.JOB_SCHEDULER_SERVICE);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        JobInfo job = initJobInfo(startId);
        if (mJobScheduler.schedule(job) <= 0) {
            Log.d(TAG, "AliveJobService failed");
        } else {
            Log.d(TAG, "AliveJobService success");
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
        mJobHandler.sendMessage(Message.obtain(mJobHandler, 1, params));
        return true;
    }

    //结束任务
    @Override
    public boolean onStopJob(JobParameters params) {
        mJobHandler.sendEmptyMessage(1);
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
