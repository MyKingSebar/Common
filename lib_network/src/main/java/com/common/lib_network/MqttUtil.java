package com.common.lib_network;

import android.text.TextUtils;

import org.eclipse.paho.client.mqttv3.MqttConnectOptions;

public class MqttUtil {

    public static class Builder {
//        private Context context;
//        private String serverURI;
//        private String clientId;
//        private RequestBody body;
//        private Object tag;


    }

    public static class MqttConnectOptionsBuilder {
        /* 用于设置客户端会话的ID。在setCleanSession(false);被调用时，MQTT服务器利用该ID获得相应的会话。此ID应少于23个字符，默认根据本机地址、端口和时间自动生成
       mqtt.setCleanSession(false); //若设为false，MQTT服务器将持久化客户端会话的主体订阅和ACK位置，默认为true*/
        private boolean cleanSession = true;
        private int connectionTimeout;
        private int keepAliveInterval;
        private String userName;
        private char[] password;
        private String[] serverURIs;
        private boolean automaticReconnect = false;
        private MqttConnectOptions mqttConnectOptions;

        public MqttConnectOptionsBuilder() {
        }

        public MqttConnectOptionsBuilder cleanSession(boolean cleanSession) {
            this.cleanSession = cleanSession;
            return this;
        }

        public MqttConnectOptionsBuilder connectionTimeout(int connectionTimeout) {
            this.connectionTimeout = connectionTimeout;
            return this;
        }

        public MqttConnectOptionsBuilder keepAliveInterval(int keepAliveInterval) {
            this.keepAliveInterval = keepAliveInterval;
            return this;
        }

        public MqttConnectOptionsBuilder userName(String userName) {
            this.userName = userName;
            return this;
        }

        public MqttConnectOptionsBuilder password(char[] password) {
            this.password = password;
            return this;
        }

        public MqttConnectOptionsBuilder serverURIs(String[] serverURIs) {
            this.serverURIs = serverURIs;
            return this;
        }

        public MqttConnectOptionsBuilder automaticReconnect(boolean automaticReconnect) {
            this.automaticReconnect = automaticReconnect;
            return this;
        }

        public MqttConnectOptions build() {
            mqttConnectOptions = new MqttConnectOptions();
            mqttConnectOptions.setCleanSession(cleanSession);
            if (connectionTimeout != 0) {
                mqttConnectOptions.setConnectionTimeout(connectionTimeout);
            }
            if (keepAliveInterval != 0) {
                mqttConnectOptions.setConnectionTimeout(keepAliveInterval);
            }
            if (!TextUtils.isEmpty(userName)) {
                mqttConnectOptions.setUserName(userName);
            }
            if (password.length != 0) {
                mqttConnectOptions.setPassword(password);
            }
            if (serverURIs.length != 0) {
                mqttConnectOptions.setServerURIs(serverURIs);
            }
            mqttConnectOptions.setAutomaticReconnect(automaticReconnect);
            return mqttConnectOptions;
        }
    }
}
