package com.common.lib_network.mqtt.listener;

public interface SubscriberFailedListener {
    /**
     * 订阅失败
     */
    void onSubscriberFailed(Throwable throwable);
}
