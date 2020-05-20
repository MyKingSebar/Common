package com.common.lib_timer;

import androidx.annotation.NonNull;

import com.common.lib_log.CommonLog;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * @author jialei
 */
public class RxTimerUtil {
    private static final String TAG = "RxTimerUtil";
    private Disposable mDisposable;

    enum TimerType {
        //主线程
        MAIN,
        //io线程
        IO
    }

    /**
     * milliseconds毫秒后执行next操作
     *
     * @param milliseconds
     * @param next
     */
    public void timer(long milliseconds, final IRxNext next, @NonNull TimerType type) {
        Scheduler scheduler;
        switch (type) {
            case MAIN:
                scheduler = AndroidSchedulers.mainThread();
                break;
            case IO:

            default:
                scheduler = Schedulers.io();
                break;
        }

        Observable.timer(milliseconds, TimeUnit.MILLISECONDS)
                .observeOn(scheduler)
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable disposable) {
                        CommonLog.d(TAG, "onSubscribe:" + disposable);
                        mDisposable = disposable;
                    }

                    @Override
                    public void onNext(@NonNull Long number) {
                        CommonLog.d(TAG, "onNext:" + number);
                        if (next != null) {
                            next.doNext(number);
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        CommonLog.d(TAG, "onError:", e);
                        //取消订阅
                        cancel();
                    }

                    @Override
                    public void onComplete() {
                        CommonLog.d(TAG, "onComplete:");
                        //取消订阅
                        cancel();
                    }
                });
    }

    /**
     * 每隔milliseconds毫秒后执行next操作
     *
     * @param milliseconds
     * @param next
     */
    public void interval(long milliseconds, final IRxNext next, @NonNull TimerType type) {
        Scheduler scheduler;
        switch (type) {
            case MAIN:
                scheduler = AndroidSchedulers.mainThread();
                break;
            case IO:

            default:
                scheduler = Schedulers.io();
                break;
        }

        Observable.interval(milliseconds, TimeUnit.MILLISECONDS)
                .observeOn(scheduler)
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable disposable) {
                        mDisposable = disposable;
                    }

                    @Override
                    public void onNext(@NonNull Long number) {
                        if (next != null) {
                            next.doNext(number);
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }


    /**
     * milliseconds毫秒后执行next操作
     *
     * @param milliseconds
     * @param next
     */
    public void timer(long milliseconds, final IRxNext next) {
        timer(milliseconds, next, TimerType.IO);
    }


    /**
     * 每隔milliseconds毫秒后执行next操作
     *
     * @param milliseconds
     * @param next
     */
    public void interval(long milliseconds, final IRxNext next) {
        interval(milliseconds, next, TimerType.IO);
    }

    /**
     * milliseconds毫秒后执行next操作
     *
     * @param milliseconds
     * @param next
     */
    public void timerMain(long milliseconds, final IRxNext next) {
        timer(milliseconds, next, TimerType.MAIN);
    }


    /**
     * 每隔milliseconds毫秒后执行next操作
     *
     * @param milliseconds
     * @param next
     */
    public void intervalMain(long milliseconds, final IRxNext next) {
        interval(milliseconds, next, TimerType.MAIN);
    }


    /**
     * 取消订阅
     */
    public void cancel() {
        CommonLog.d(TAG, "cancel");
        if (mDisposable != null && !mDisposable.isDisposed()) {
            mDisposable.dispose();
            CommonLog.d(TAG, "定时器取消");
        }
    }

    /**
     * 是否取消订阅
     */
    public boolean isCancel() {
        return (mDisposable == null || mDisposable.isDisposed());
    }

    public interface IRxNext {
        void doNext(long number);
    }
}