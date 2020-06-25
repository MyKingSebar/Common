package com.common.lib_network.mqtt;


public interface IMqttSubscriberJ {
    /**
     * 收到消息
     */
    void onMessageArrived(String topic, String message, int qos);

    /**
     * 消息发送完成
     */
    void onDeliveryComplete(String message);

    /**
     * 服务器连接成功
     */
    void onConnectSuccess();

    /**
     * 服务器连接断开
     */
    void onConnectionLost(Throwable throwable);

    /**
     * 服务器连接失败
     */
    void onConnectFailed(Throwable throwable);

    /**
     * 订阅成功
     */
    void onSubscriberSuccess();

    /**
     * 订阅失败
     */
    void onSubscriberFailed(Throwable throwable);
}
