package com.common.lib_network;

import android.util.Log;

import com.common.lib_network.mqtt.MqttManager;

public class NetLog {
    public static void v(String msg) {
        if (MqttManager.getInstance().getLogListener() != null) {
            MqttManager.getInstance().getLogListener().v(msg);
        } else {
            Log.v("NetWork", msg);
        }
    }

    public static void d(String msg) {
        if (MqttManager.getInstance().getLogListener() != null) {
            MqttManager.getInstance().getLogListener().d(msg);
//            android.util.Log.d("jialei", msg,new RuntimeException("jialei2").fillInStackTrace());
        } else {
            Log.d("NetWork", msg);
        }
    }

    public static void i(String msg) {
        if (MqttManager.getInstance().getLogListener() != null) {
            MqttManager.getInstance().getLogListener().i(msg);
        } else {
            Log.i("NetWork", msg);
        }
    }

    public static void w(String msg) {
        if (MqttManager.getInstance().getLogListener() != null) {
            MqttManager.getInstance().getLogListener().w(msg);
        } else {
            Log.w("NetWork", msg);
        }
    }

    public static void e(String msg) {
        if (MqttManager.getInstance().getLogListener() != null) {
            MqttManager.getInstance().getLogListener().e(msg);
        } else {
            Log.e("NetWork", msg);
        }
    }
    public static void vs(String msg) {
        if (MqttManager.getInstance().getLogListener() != null) {
            MqttManager.getInstance().getLogListener().vs(msg);
        } else {
            Log.v("NetWork", msg);
        }
    }

    public static void ds(String msg) {
        if (MqttManager.getInstance().getLogListener() != null) {
            MqttManager.getInstance().getLogListener().ds(msg);
        } else {
            Log.d("NetWork", msg);
        }
    }

    public static void is(String msg) {
        if (MqttManager.getInstance().getLogListener() != null) {
            MqttManager.getInstance().getLogListener().is(msg);
        } else {
            Log.i("NetWork", msg);
        }
    }

    public static void ws(String msg) {
        if (MqttManager.getInstance().getLogListener() != null) {
            MqttManager.getInstance().getLogListener().ws(msg);
        } else {
            Log.w("NetWork", msg);
        }
    }

    public static void es(String msg) {
        if (MqttManager.getInstance().getLogListener() != null) {
            MqttManager.getInstance().getLogListener().es(msg);
        } else {
            Log.e("NetWork", msg);
        }
    }
}
