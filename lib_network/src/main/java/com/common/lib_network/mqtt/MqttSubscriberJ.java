package com.common.lib_network.mqtt;

import com.common.lib_network.NetLog;
import com.common.lib_network.mqtt.listener.ConnectSuccessListener;
import com.common.lib_network.mqtt.listener.ConnectionFailedListener;
import com.common.lib_network.mqtt.listener.ConnectionLostListener;
import com.common.lib_network.mqtt.listener.DeliveryCompleteListener;
import com.common.lib_network.mqtt.listener.MessageArrivedListener;
import com.common.lib_network.mqtt.listener.SubscriberFailedListener;
import com.common.lib_network.mqtt.listener.SubscriberSuccessListener;

public class MqttSubscriberJ implements IMqttSubscriberJ {
    private ConnectionLostListener connectionLostListener;
    private ConnectionFailedListener connectFailedListener;
    private ConnectSuccessListener connectSuccessListener;
    private DeliveryCompleteListener deliveryCompleteListener;
    private MessageArrivedListener messageArrivedListener;
    private SubscriberFailedListener subscriberFailedListener;
    private SubscriberSuccessListener subscriberSuccessListener;

    public ConnectionFailedListener getConnectFailedListener() {
        return connectFailedListener;
    }

    public void setConnectFailedListener(ConnectionFailedListener connectFailedListener) {
        this.connectFailedListener = connectFailedListener;
    }

    public ConnectionLostListener getConnectionLostListener() {
        return connectionLostListener;
    }

    public void setConnectionLostListener(ConnectionLostListener connectionLostListener) {
        this.connectionLostListener = connectionLostListener;
    }

    public ConnectSuccessListener getConnectSuccessListener() {
        return connectSuccessListener;
    }

    public void setConnectSuccessListener(ConnectSuccessListener connectSuccessListener) {
        this.connectSuccessListener = connectSuccessListener;
    }

    public DeliveryCompleteListener getDeliveryCompleteListener() {
        return deliveryCompleteListener;
    }

    public void setDeliveryCompleteListener(DeliveryCompleteListener deliveryCompleteListener) {
        this.deliveryCompleteListener = deliveryCompleteListener;
    }

    public MessageArrivedListener getMessageArrivedListener() {
        return messageArrivedListener;
    }

    public void setMessageArrivedListener(MessageArrivedListener messageArrivedListener) {
        this.messageArrivedListener = messageArrivedListener;
    }

    public SubscriberFailedListener getSubscriberFailedListener() {
        return subscriberFailedListener;
    }

    public void setSubscriberFailedListener(SubscriberFailedListener subscriberFailedListener) {
        this.subscriberFailedListener = subscriberFailedListener;
    }

    public SubscriberSuccessListener getSubscriberSuccessListener() {
        return subscriberSuccessListener;
    }

    public void setSubscriberSuccessListener(SubscriberSuccessListener subscriberSuccessListener) {
        this.subscriberSuccessListener = subscriberSuccessListener;
    }

    @Override
    public void onMessageArrived(String topic, String message, int qos) {
        if (messageArrivedListener != null) {
            messageArrivedListener.onMessageArrived(topic, message, qos);
        } else {
            NetLog.e("messageArrivedListener==null");
        }
    }

    @Override
    public void onDeliveryComplete(String message) {
        if (deliveryCompleteListener != null) {
            deliveryCompleteListener.onDeliveryComplete(message);
        } else {
            NetLog.e("deliveryCompleteListener==null");
        }
    }

    @Override
    public void onConnectSuccess() {
        if (connectSuccessListener != null) {
            connectSuccessListener.onConnectSuccess();
        } else {
            NetLog.e("connectSuccessListener==null");
        }
    }

    @Override
    public void onConnectionLost(Throwable throwable) {
        if (connectionLostListener != null) {
            connectionLostListener.onConnectionLost(throwable);
        } else {
            NetLog.e("connectionLostListener==null");
        }
    }

    @Override
    public void onConnectFailed(Throwable throwable) {
        if (connectFailedListener != null) {
            connectFailedListener.onConnectionFailed(throwable);
        } else {
            NetLog.e("connectFailedListener==null");
        }
    }

    @Override
    public void onSubscriberSuccess() {
        if (subscriberSuccessListener != null) {
            subscriberSuccessListener.onSubscriberSuccess();
        } else {
            NetLog.e("subscriberSuccessListener==null");
        }
    }

    @Override
    public void onSubscriberFailed(Throwable throwable) {
        if (subscriberFailedListener != null) {
            subscriberFailedListener.onSubscriberFailed(throwable);
        } else {
            NetLog.e("subscriberFailedListener==null");
        }
    }
}
