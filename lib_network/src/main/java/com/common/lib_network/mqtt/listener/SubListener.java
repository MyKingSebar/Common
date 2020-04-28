package com.common.lib_network.mqtt.listener;

public interface SubListener {
    void onSubscriberSuccess();
    void onSubscriberFailed(Throwable exception);
    void onDeliveryComplete(String message);
    void onConnectionLost(Throwable exception);

}
