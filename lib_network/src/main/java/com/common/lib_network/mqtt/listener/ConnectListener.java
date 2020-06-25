package com.common.lib_network.mqtt.listener;

public interface ConnectListener {
    void onConnectSuccess(String msg);

    void onConnectFailed(Throwable msg);
}
