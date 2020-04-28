package com.common.lib_network.mqtt.listener;

public interface DeliveryCompleteListener {
    /**
     * 消息发送完成
     */
    void onDeliveryComplete(String message);
}
