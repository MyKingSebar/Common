package com.common.lib_network.mqtt.listener;

public interface ConnectionLostListener {
    /**
     * 服务器连接断开
     */
    void onConnectionLost(Throwable throwable);
}
