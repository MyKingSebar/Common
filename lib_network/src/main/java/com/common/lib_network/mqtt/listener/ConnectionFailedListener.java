package com.common.lib_network.mqtt.listener;

public interface ConnectionFailedListener {
    /**
     * 服务器连接断开
     */
    void onConnectionFailed(Throwable throwable);
}
