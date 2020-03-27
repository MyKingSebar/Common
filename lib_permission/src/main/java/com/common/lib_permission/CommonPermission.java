package com.common.lib_permission;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.tbruyelle.rxpermissions2.RxPermissions;

import io.reactivex.functions.Consumer;

public class CommonPermission {
    private static final String TAG = CommonPermission.class.getSimpleName();
    RxPermissions rxPermissions = null;
    static Fragment fragment = null;
    static FragmentActivity activity = null;

    private void init(@NonNull FragmentActivity activity) {
        rxPermissions = new RxPermissions(activity); // where this is an Activity or Fragment instance
    }

    private void init(@NonNull Fragment fragment) {
        rxPermissions = new RxPermissions(fragment); // where this is an Activity or Fragment instance
    }

    public void requestPermissions(final IPermissionCallback callback, String... permissions) {
        // Must be done during an initialization phase like onCreate
        rxPermissions
                .request(permissions)
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean granted) throws Exception {
                        if (granted) { // Always true pre-M
                            if (callback != null) {
                                callback.onSuccess();
                            }

                        } else {
                            // Oups permission denied
                            if (callback != null) {
                                callback.onFail();
                            }
                        }
                    }
                });
    }

    public void destory() {
        fragment = null;
        activity = null;
    }

    private CommonPermission() {
//        if (fragment != null) {
//            Log.d(TAG, "init(fragment)");
//            init(fragment);
//        } else if (activity != null) {
//            Log.d(TAG, "init(activity)");
//            init(activity);
//        }
    }

    private static class CommonPermissionHoler {
        private static CommonPermission INSTANCE = new CommonPermission();
    }

    public static CommonPermission getInstance(@NonNull FragmentActivity activity) {
        CommonPermission.activity = activity;
        CommonPermission.fragment = null;
        Log.d(TAG, "init(activity)");
        CommonPermissionHoler.INSTANCE.init(activity);
        return CommonPermissionHoler.INSTANCE;
    }

    public static CommonPermission getInstance(@NonNull Fragment fragment) {
        CommonPermission.fragment = fragment;
        CommonPermission.activity = null;
        Log.d(TAG, "init(fragment)");
        CommonPermissionHoler.INSTANCE.init(fragment);
        return CommonPermissionHoler.INSTANCE;
    }
}
