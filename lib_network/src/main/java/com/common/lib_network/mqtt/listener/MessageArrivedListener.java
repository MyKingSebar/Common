package com.common.lib_network.mqtt.listener;

public interface MessageArrivedListener {
    void onMessageArrived(String topic, String message, int qos);
}
