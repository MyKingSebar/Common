package com.common.lib_network.mqtt;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;

import androidx.annotation.NonNull;

import com.common.lib_network.NetCommon;
import com.common.lib_network.NetLog;
import com.common.lib_network.NetLogListener;
import com.common.lib_network.mqtt.proxy.ProxyBean;
import com.common.lib_network.mqtt.proxy.ProxyFactory;
import com.common.lib_network.mqtt.proxy.ProxySocketFactory;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.DisconnectedBufferOptions;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.net.Proxy;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * 消息队列管理器。
 * <p>
 * 使用流程：
 * 1，配置MqttConfig，设置账号和密码，MqttConfig().create()。
 * 2，初始化Mqtt客户端，MqttManager.getInstance().init(activity, MqttConfig().create())，建议在MainActivity的onCreate中调用。
 * 3，连接Mqtt客户端，MqttManager.getInstance().connect()，建议在init方法后调用。
 * 4，订阅Topic，MqttManager.getInstance().subscribe(topic, subscriber)，并在subscriber中处理消息的回调。
 * 5，发布消息，MqttManager.getInstance().publishMessage(topic,content)。
 * 6，退订Topic，MqttManager.getInstance().unsubscribe(topic)，建议在页面消失时调用。
 * 7，关闭Mqtt，MqttManager.getInstance().close()，建议在MainActivity的onDestroy中调用。
 */
public class MqttManager {
    private static final String TAG="MqttManager";
    /**
     * Quality of Service等级是发送与接收端的一种关于保证交付信息的协议。一共有3 个QoS 等级：
     * 最多一次（0）
     * 最少一次（1）
     * 只一次（2）
     */
    public static int QOS_MOSTONE = 0;
    public static int QOS_LEASTONE = 1;
    public static int QOS_ONLYONE = 2;

    private MqttConfig mqttConfig = null;
    private MqttAndroidClient mqttClient = null;
    private MqttConnectOptions mConOpt;
    private ConcurrentHashMap<String, MqttSubscriberJ> mSubscribers = new ConcurrentHashMap<>();
    private NetLogListener logListener = null;
    private MqttSubscriberJ mainListener = null;

    protected static final String URI_TYPE_TCP = "tcp";
    protected static final String URI_TYPE_SSL = "ssl";
    protected static final String URI_TYPE_LOCAL = "local";
    protected static final String URI_TYPE_WS = "ws";
    protected static final String URI_TYPE_WSS = "wss";

    /**
     * 短连接参数
     */
    private boolean useShortMqtt = false;
    private MqttSubscriberJ mMqttSubscriberJ = null;
    private CopyOnWriteArrayList<String> messageList = new CopyOnWriteArrayList<>();
    private final String ADDSTRING = "ADDSTRING";
    private final int CLOSE = 1;
    private final int OPEN = 2;
    private long delayCloseMqttTime = 2 * 60 * 1000;
    private long delayOpenMqttTime = 2 * 60 * 1000;
    private ShortMqttHandler shortMqttHandler = new ShortMqttHandler();
    private ShortMqttConfigBean shortMqttConfigBean = null;
    private int canTryConnectTimes = 5;
    private int tryConnectLeftTimes = 5;

    private Context mContext=null;

    /**
     * 代理
     */
    private boolean usePeoxy = false;
    private ProxyBean proxyBean = null;

    private boolean sslNoVerify = true;

    /**
     * 查询还剩重连次数
     *
     * @return
     */
    public int getTryConnectLeftTimes() {
        return tryConnectLeftTimes;
    }

    /**
     * 恢复重连次数
     */
    public void resetTryConnectLeftTimes() {
        tryConnectLeftTimes = canTryConnectTimes;
    }


    public ShortMqttConfigBean getShortMqttConfigBean() {
        return shortMqttConfigBean;
    }

    public void setShortMqttConfigBean(ShortMqttConfigBean shortMqttConfigBean) {
        if (shortMqttConfigBean != null) {

            if (shortMqttConfigBean.delayCloseTime > 0) {
                delayCloseMqttTime = shortMqttConfigBean.delayCloseTime;
            }
            if (shortMqttConfigBean.delayOpenTime > 0) {
                delayOpenMqttTime = shortMqttConfigBean.delayOpenTime;
            }
            if (shortMqttConfigBean.canTryConnectTimes > 0) {
                this.canTryConnectTimes = shortMqttConfigBean.canTryConnectTimes;
            }
            this.useShortMqtt = shortMqttConfigBean.useSHort;
            this.shortMqttConfigBean = shortMqttConfigBean;
        }

    }

    public NetLogListener getLogListener() {
        return logListener;
    }


    public void initLog(NetLogListener listener) {
        NetCommon.getInstance().setLogListener(listener);
    }

    public void init(Context context, MqttConfig config) {
        init(context, config, null);
    }

    public void init(Context context, MqttConfig config, NetLogListener listener) {
        init(context, config, listener, false);
    }

    public void init(Context context, MqttConfig config, NetLogListener listener, boolean useShortMqtt) {
        init(context, config, listener, useShortMqtt, null, true);
    }

    public void init(Context context, MqttConfig config, NetLogListener listener, boolean useShortMqtt, ProxyBean proxyBean, boolean sslNoVerify) {
        this.mContext=context;
        this.useShortMqtt = useShortMqtt;
        this.sslNoVerify = sslNoVerify;
        this.usePeoxy = checkProxyBean(proxyBean);
        this.proxyBean = proxyBean;
        logListener = listener;
        mqttConfig = config;
        clientBuild();
    }
    private void clientBuild(){
        if(mContext==null||mqttConfig==null){
            NetLog.e("mContext==null||mqttConfig==null");
            return;
        }
        mqttClient = new MqttAndroidClient(mContext, mqttConfig.getBaseUrl().split(",")[0], mqttConfig.getClientId()+System.currentTimeMillis());
        mqttClient.setCallback(new MqttCallbackExtended() {
            @Override
            public void connectComplete(boolean reconnect, String serverURI) {
                if (reconnect) {
                    NetLog.i("----> mqtt reconnect complete, serverUrl = " + serverURI);
                } else {
                    NetLog.i("----> mqtt connect complete, serverUrl = " + serverURI);
                }
            }

            @Override
            public void connectionLost(Throwable cause) {
//                for (LinkedHashMap.Entry<String, MqttSubscriberJ> entry : mSubscribers.entrySet()) {
//                    entry.getValue().onConnectionLost(cause);
//                }
                if (mainListener != null) {
                    mainListener.getConnectionLostListener().onConnectionLost(cause);
                }
                if (cause == null) {
                    NetLog.e("connectionLost.cause==null");
                    return;
                }
                NetLog.e("connectionLost" + (cause.getMessage() == null ? "null" : cause.getMessage()));
            }

            @Override
            public void messageArrived(String topic, MqttMessage message) {
                NetLog.d("MQTT收到消息的topic=" + topic);
                String msg = new String(message.getPayload());
                MqttSubscriberJ subscriberJ = mSubscribers.get(topic);
                if (subscriberJ != null) {
                    NetLog.v("MQTT收到消息" + msg);
                    subscriberJ.getMessageArrivedListener().onMessageArrived(topic, msg, message.getQos());
                }
            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken token) {
                try {

                    if (mainListener != null) {
                        mainListener.onDeliveryComplete((token.getMessage() == null ? "null" : token.getMessage().toString()));
                    }
//                    for (LinkedHashMap.Entry<String, MqttSubscriberJ> entry : mSubscribers.entrySet()) {
//                        DeliveryCompleteListener listener = entry.getValue().getDeliveryCompleteListener();
//                        if (listener != null&&token!=null) {
//                            listener.onDeliveryComplete((token.getMessage()==null?"null":token.getMessage().toString()));
//                        }
//                    }
                    NetLog.d("----> mqtt delivery complete, token = " + (token.getMessage() == null ? "null" : token.getMessage().toString()));
                } catch (MqttException e) {
                    e.printStackTrace();
                    if (e == null) {
                        NetLog.e("deliveryComplete.e==null");
                        return;
                    }
                    NetLog.e("deliveryComplete()" + (e.getMessage() == null ? "null" : e.getMessage()));
                }
            }
        });
    }

    private boolean checkProxyBean(ProxyBean bean) {
        if (bean != null && !TextUtils.isEmpty(bean.getProxyHost())) {
            return true;
        }
        return false;
    }

    /**
     * 关闭MQTT客户端，建议在MainActivity的onDestroy中调用
     */
    public void close() {
        try {
            if (mqttClient != null) {
                mqttClient.close();
                mqttClient.disconnect();
                mqttClient.unregisterResources();
                clear();
                NetLog.d("----> mqtt close success.");
            }
        } catch (MqttException e) {
            e.printStackTrace();
            NetLog.e("close()" + (e.getMessage() == null ? "null" : e.getMessage()));
        }
    }

    private void tryConnectTime() {
        if (useShortMqtt) {
            tryConnectLeftTimes--;
        }
    }

    /**
     * M
     * 连接服务器
     * 表示当前方法的回调，并不会作用到全局
     */
    public void connect(final MqttSubscriberJ listener) {
//        if (mqttClient == null) {
//            NetLog.e("----> mqtt publish message failed, please init mqtt first.");
//            return;
//        }
        clientBuild();
        if (listener != null) {
            mainListener = listener;
        }
        try {
            tryConnectTime();
            mqttClient.connect(generateConnectOptions(), null, new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    resetTryConnectLeftTimes();
                    if (listener != null) {
                        listener.onConnectSuccess();
                    } else {
                        NetLog.e("----> connect success but MqttSubscriberJ is null");
                    }
//                    NetLog.d("----> mSubscribers:" + mSubscribers.size());
//                    for (ConcurrentHashMap.Entry<String, MqttSubscriberJ> entry : mSubscribers.entrySet()) {
//                        if (entry.getValue() != null) {
//                            entry.getValue().onConnectSuccess();
//                            NetLog.d("----> mSubscribers:entrySet " + entry.getValue().toString());
//                        }
//                    }
                    NetLog.d("----> mqtt connect success.");
                    DisconnectedBufferOptions disconnectedBufferOptions = new DisconnectedBufferOptions();
                    disconnectedBufferOptions.setBufferEnabled(true);
                    disconnectedBufferOptions.setBufferSize(100);
                    disconnectedBufferOptions.setPersistBuffer(false);
                    disconnectedBufferOptions.setDeleteOldestMessages(false);
                    if (mqttClient != null) {
                        mqttClient.setBufferOpts(disconnectedBufferOptions);
                    }
                    if (useShortMqtt) {
                        shortMqttPerformPublishMessage();
                    }

                }

                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                    if (mainListener  != null) {
                        mainListener .onConnectFailed(exception);
                    } else {
                        NetLog.e("----> connect onFailure but MqttSubscriberJ is null");
                    }
//                    for (ConcurrentHashMap.Entry<String, MqttSubscriberJ> entry : mSubscribers.entrySet()) {
//                        entry.getValue().onConnectFailed(exception);
//                    }
                    if (exception == null) {
                        NetLog.e("connect.exception==null");
                        return;
                    }
                    NetLog.e("----> mqtt connect failed, exception = " + (exception.getMessage() == null ? "null" : exception.getMessage()));
                }
            });

        } catch (MqttException e) {
            e.printStackTrace();
            if (e == null) {
                NetLog.e("connect.e==null");
                return;
            }
            NetLog.e("connect()" + (e.getMessage() == null ? "null" : e.getMessage()));
        }

    }

    /**
     * 订阅一个话题
     */
    public void subscribe(final String topic, final MqttSubscriberJ listener) {
        if (mqttClient == null) {
            NetLog.e("----> mqtt subscribe failed, please init mqtt first.");
            return;
        }
        if (isConnected()) {
            performSubscribe(topic, listener);
        } else {
            if (mainListener != null) {
                mainListener.getConnectFailedListener().onConnectionFailed(null);
            }
//            if(mainListener!=null){
//                connect(mainListener);
//            }else {
//                // 如果没有连接，就先去连接
//                MqttSubscriberJ mqttSubscriberJ = new MqttSubscriberJ();
//                mqttSubscriberJ.setConnectSuccessListener(new ConnectSuccessListener() {
//                    @Override
//                    public void onConnectSuccess() {
//                        performSubscribe(topic, listener);
//                        NetLog.e("mqttSubscriberJ onConnectSuccess");
//                    }
//                });
//                mqttSubscriberJ.setConnectFailedListener(new ConnectionFailedListener() {
//                    @Override
//                    public void onConnectionFailed(Throwable throwable) {
//                        NetLog.e("mqttSubscriberJ onConnectionFailed");
//                    }
//                });
//                connect(mqttSubscriberJ);
//            }


            NetLog.e("TEST2");
        }
    }

    /**
     * 订阅实现
     */
    private void performSubscribe(final String topic, final MqttSubscriberJ subscriber) {
        // 判断是否已经订阅
        if (mSubscribers.containsKey(topic)) {
            NetLog.d("----> 已经订阅 = " + topic);
            return;
        }
        if (subscriber == null) {
            NetLog.e("----> MqttSubscriberJ = null" + topic);
        }
        mSubscribers.put(topic, subscriber);
        try {
            if (mqttClient != null) {
                mqttClient.subscribe(topic, 2, null, new IMqttActionListener() {
                    @Override
                    public void onSuccess(IMqttToken asyncActionToken) {
                        if (subscriber != null) {
                            subscriber.onSubscriberSuccess();
                            NetLog.d("----> mqtt subscribe success, topic = " + topic);
                        }
                    }

                    @Override
                    public void onFailure(IMqttToken asyncActionToken, Throwable e) {
                        if (e == null) {
                            NetLog.e("onFailure.e==null");
                            return;
                        }
                        if (subscriber != null) {
                            subscriber.onSubscriberFailed(e);
                            NetLog.d("----> mqtt subscribe failed, exception =  " + (e.getMessage() == null ? "null" : e.getMessage()));
                        }
                    }
                });
            }
        } catch (MqttException e) {
            e.printStackTrace();
            NetLog.e("performSubscribe()" + (e.getMessage() == null ? "null" : e.getMessage()));
        }
    }

    /**
     * 退订某一个topic
     */
    public void unsubscribe(final String topic) {
        mSubscribers.remove(topic);
        if (mqttClient != null) {
            try {
                mqttClient.unsubscribe(topic, null, new IMqttActionListener() {
                    @Override
                    public void onSuccess(IMqttToken asyncActionToken) {
                        NetLog.d("----> mqtt unsubscribe success, topic = " + topic);
                    }

                    @Override
                    public void onFailure(IMqttToken asyncActionToken, Throwable e) {
                        if (e == null) {
                            NetLog.e("mqtt unsubscribe failed, exception =onFailure.e==null");
                            return;
                        }
                        NetLog.e("----> mqtt unsubscribe failed, exception =" + (e.getMessage() == null ? "null" : e.getMessage()));
                    }
                });
            } catch (MqttException e) {
                e.printStackTrace();
                NetLog.e("unsubscribe()" + (e.getMessage() == null ? "null" : e.getMessage()));
            }
        }
    }

    /**
     * 发布消息
     */
    public String publishMessage(final String topic, final String content) {
        if (TextUtils.isEmpty(topic)) {
            NetLog.e("----> mqtt publish message failed, topic is null");
            return null;
        }
        if (useShortMqtt) {
            messageList.add(topic + ADDSTRING + content);
        }
        if (mqttClient == null) {
            NetLog.e("----> mqtt publish message failed, please init mqtt first.");
            return null;
        }
        if (isConnected()) {
            if (useShortMqtt) {
                shortMqttPerformPublishMessage();
            } else {
                return performPublishMessage(topic, content);
            }
        } else {
            if (mainListener != null) {
                mainListener.getConnectFailedListener().onConnectionFailed(null);
            }
//            // 如果没有连接，就先去连接
//            MqttSubscriberJ mqttSubscriberJ = new MqttSubscriberJ();
//            mqttSubscriberJ.setConnectSuccessListener(new ConnectSuccessListener() {
//                @Override
//                public void onConnectSuccess() {
//                    NetLog.e("mqttSubscriberJ onConnectSuccess1");
//                }
//            });
//            mqttSubscriberJ.setConnectFailedListener(new ConnectionFailedListener() {
//                @Override
//                public void onConnectionFailed(Throwable throwable) {
//                    NetLog.e("mqttSubscriberJ onConnectionFailed1");
//                }
//            });
//            connect(mqttSubscriberJ);
            NetLog.e("TEST3");
        }
        return null;
    }

    private String performPublishMessage(String topic, String content) {
        try {
//            MqttMessage message = new MqttMessage();
//            message.setPayload(content.getBytes());
            if (mqttClient != null) {
                IMqttDeliveryToken token = mqttClient.publish(topic, content.getBytes(), QOS_ONLYONE, false);
                if (token != null && token.getMessage() != null) {
                    return token.getMessage().toString();
                }
            }
        } catch (MqttException e) {
            e.printStackTrace();
            NetLog.e("performPublishMessage()" + (e.getMessage() == null ? "null" : e.getMessage()));
        }
        return null;
    }

    /**
     * 主动断开连接，不会自动重连
     */
    public void disconnect() {
        try {
            if (mqttClient != null) {
                clear();
                mqttClient.unregisterResources();
                if(isConnected()){
                    mqttClient.disconnect();
                }
                mqttClient.close();
            }
        } catch (MqttException e) {
            e.printStackTrace();
            NetLog.e("disconnect()" + (e.getMessage() == null ? "null" : e.getMessage()));
        }
    }

    /**
     * 判断连接是否断开
     */
    public boolean isConnected() {
        try {
            if (mqttClient != null) {
                return mqttClient.isConnected();
            }
        } catch (Exception e) {
            e.printStackTrace();
            NetLog.e("isConnected()" + (e.getMessage() == null ? "null" : e.getMessage()));
        }
        return false;
    }

    public String getServerUrl() {
        if (mqttConfig != null) {
            return mqttConfig.getBaseUrl().split(",")[0];
        }
        return null;
    }

    public ConcurrentHashMap<String, MqttSubscriberJ> getSubscribers() {
        return mSubscribers;
    }

    public void clear() {
        getSubscribers().clear();
    }
    public boolean isUsePeoxy(){
        return usePeoxy;
    }
    public ProxyBean getProxyBean(){
        return proxyBean;
    }
    private MqttConnectOptions generateConnectOptions() {
        mConOpt = new MqttConnectOptions();
        if (mqttConfig == null) {
            NetLog.e("MqttConfig need initialize!!!");
            return mConOpt;
        }
        if (mqttConfig.getBaseUrl() == null || mqttConfig.getUserName() == null || mqttConfig.getPassword() == null) {
            NetLog.e("mqttConfig 没传参");
            return mConOpt;
        }
        NetLog.d("mqttConfig==null" + (mqttConfig == null));
        NetLog.d("mqttConfig==getBaseUrl" + mqttConfig.getBaseUrl());
        NetLog.d("mqttConfig==mqttConfig.getBaseUrl().split(\",\")" + mqttConfig.getBaseUrl().split(",").toString());
        String[] urls = mqttConfig.getBaseUrl().split(",");
        mConOpt.setServerURIs(urls);
        // 不清除缓存 保持长久连接
        mConOpt.setCleanSession(mqttConfig.getCleanSession());
        // 设置超时时间，单位：秒
        mConOpt.setConnectionTimeout(mqttConfig.getTimeout());
        // 心跳包发送间隔，单位：秒
        mConOpt.setKeepAliveInterval(mqttConfig.getHeartbeat());
        // 用户名
        mConOpt.setUserName(mqttConfig.getUserName());
        // 密码  将字符串转换为字符串数组
        mConOpt.setPassword(mqttConfig.getPassword().toCharArray());
        // 自动重连
        mConOpt.setAutomaticReconnect(mqttConfig.getAutomaticReconnect());
        if (sslNoVerify) {
            if (urls.length > 0) {
                try {
                    URI vURI = new URI(urls[0]);
                    if (vURI.getScheme().equals(URI_TYPE_SSL)) {
                        // 加密连接
                        mConOpt.setSocketFactory(UnSafeTrustManager.getUnsafeOkHttpClient());
                    }
                } catch (URISyntaxException e) {
                    NetLog.e("URISyntaxException:", e);
                }
            }
        }
        if (usePeoxy && checkProxyBean(proxyBean)) {
            Proxy.Type type = null;
            switch (proxyBean.getType()) {
                case ProxyBean.DIRECT:
                    type = Proxy.Type.DIRECT;
                    break;
                case ProxyBean.HTTP:
                    type = Proxy.Type.HTTP;
                    break;
                case ProxyBean.SOCKS:
                    type = Proxy.Type.SOCKS;
                    break;
                default:
                    break;
            }
            mConOpt.setSocketFactory(new ProxyFactory(type,proxyBean.getProxyHost(), proxyBean.getProxyPort()));
        }
        //TODO
//        makeTopic();
        // 遗嘱
        if (mqttConfig.getWill() != null) {
            mConOpt.setWill(mqttConfig.getUpTopic(), mqttConfig.getWill(),
                    MqttManager.QOS_ONLYONE, false);
        }
        return mConOpt;
    }

    public static MqttManager getInstance() {
        return MqttManagerHolder.INSTANCE;
    }

    private MqttManager() {
    }

    private static class MqttManagerHolder {
        private static MqttManager INSTANCE = new MqttManager();
    }


    private final class ShortMqttHandler extends Handler {

        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what) {
                case CLOSE:
                    NetLog.d("CLOSE");
                    disconnect();
                    shortMqttHandler.sendEmptyMessageDelayed(OPEN, delayOpenMqttTime);
                    break;
                case OPEN:
                    NetLog.d("OPEN");
                    connect(null);
                    break;
                default:
                    break;
            }
        }
    }

    private void shortMqttPerformPublishMessage() {
            if (mqttClient != null) {
                for (String s : messageList) {
                    String[] value = s.split(ADDSTRING);
                    if (value.length == 2) {
                        if (isConnected()) {
                            performPublishMessage(value[0], value[1]);
                            messageList.remove(s);
                        }
                    }
                }

            }
            reflushClose();
    }

    public void reflushClose() {
        if (useShortMqtt) {
            shortMqttHandler.removeMessages(CLOSE);
            shortMqttHandler.removeMessages(OPEN);
            shortMqttHandler.sendEmptyMessageDelayed(CLOSE, delayCloseMqttTime);
        }
    }

    /**
     * 重连几次失败之后调用
     */
    public void reflushOpen() {
        if (useShortMqtt) {
            shortMqttHandler.removeMessages(CLOSE);
            shortMqttHandler.removeMessages(OPEN);
            shortMqttHandler.sendEmptyMessageDelayed(OPEN, delayOpenMqttTime);
            resetTryConnectLeftTimes();
        }
    }

    /**
     * M
     * 改短连接重连连接服务器
     * 表示当前方法的回调，并不会作用到全局
     */
    public void reConnect() {
        if (mqttClient == null) {
            NetLog.e("----> mqtt publish message failed, please init mqtt first.");
            return;
        }
        mqttClient.setCallback(new MqttCallbackExtended() {
            @Override
            public void connectComplete(boolean reconnect, String serverURI) {
                if (reconnect) {
                    NetLog.i("----> mqtt reconnect complete, serverUrl = " + serverURI);
                } else {
                    NetLog.i("----> mqtt connect complete, serverUrl = " + serverURI);
                }
            }

            @Override
            public void connectionLost(Throwable cause) {
//                for (LinkedHashMap.Entry<String, MqttSubscriberJ> entry : mSubscribers.entrySet()) {
//                    entry.getValue().onConnectionLost(cause);
//                }
                if (mainListener != null) {
                    mainListener.getConnectionLostListener().onConnectionLost(cause);
                }
                if (cause == null) {
                    NetLog.e("connectionLost.cause==null");
                    return;
                }
                NetLog.e("connectionLost" + (cause.getMessage() == null ? "null" : cause.getMessage()));
            }

            @Override
            public void messageArrived(String topic, MqttMessage message) {
                NetLog.d("MQTT收到消息的topic=" + topic);
                String msg = new String(message.getPayload());
                MqttSubscriberJ subscriberJ = mSubscribers.get(topic);
                if (subscriberJ != null) {
                    NetLog.v("MQTT收到消息" + msg);
                    subscriberJ.getMessageArrivedListener().onMessageArrived(topic, msg, message.getQos());
                }
            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken token) {
                try {

                    if (mainListener != null) {
                        mainListener.onDeliveryComplete((token.getMessage() == null ? "null" : token.getMessage().toString()));
                    }
//                    for (LinkedHashMap.Entry<String, MqttSubscriberJ> entry : mSubscribers.entrySet()) {
//                        DeliveryCompleteListener listener = entry.getValue().getDeliveryCompleteListener();
//                        if (listener != null&&token!=null) {
//                            listener.onDeliveryComplete((token.getMessage()==null?"null":token.getMessage().toString()));
//                        }
//                    }
                    NetLog.d("----> mqtt delivery complete, token = " + (token.getMessage() == null ? "null" : token.getMessage().toString()));
                } catch (MqttException e) {
                    e.printStackTrace();
                    if (e == null) {
                        NetLog.e("deliveryComplete.e==null");
                        return;
                    }
                    NetLog.e("deliveryComplete()" + (e.getMessage() == null ? "null" : e.getMessage()));
                }
            }
        });
        if (useShortMqtt) {
            shortMqttHandler.removeMessages(OPEN);
        }
        try {
            tryConnectTime();
            mqttClient.connect(generateConnectOptions(), null, new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    resetTryConnectLeftTimes();
                    reflushClose();
                    if (mainListener != null) {
                        mainListener.onConnectSuccess();
                    } else {
                        NetLog.e("----> connect success but mainListener is null");
                    }
                    NetLog.d("----> mSubscribers:" + mSubscribers.size());
//                    for (Map.Entry<String, MqttSubscriberJ> entry : mSubscribers.entrySet()) {
//                        if (entry.getValue() != null) {
//                            entry.getValue().onConnectSuccess();
//                            NetLog.d("----> mSubscribers:entrySet " + entry.getValue().toString());
//                        } else {
//                            NetLog.d("----> reConnect entry.getValue()!=null");
//                        }
//                    }
                    NetLog.d("----> mqtt connect success.");
                    DisconnectedBufferOptions disconnectedBufferOptions = new DisconnectedBufferOptions();
                    disconnectedBufferOptions.setBufferEnabled(true);
                    disconnectedBufferOptions.setBufferSize(100);
                    disconnectedBufferOptions.setPersistBuffer(false);
                    disconnectedBufferOptions.setDeleteOldestMessages(false);
                    if (mqttClient != null) {
                        mqttClient.setBufferOpts(disconnectedBufferOptions);
                    }
                    if (useShortMqtt) {
                        shortMqttPerformPublishMessage();
                    }
                }

                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                    if (mainListener != null) {
                        mainListener.onConnectFailed(exception);
                    } else {
                        NetLog.e("----> connect onFailure but mainListener is null");
                    }
//                    for (Map.Entry<String, MqttSubscriberJ> entry : mSubscribers.entrySet()) {
//                        if (entry.getValue() != null) {
//                            if (entry.getValue() != null) {
//                                entry.getValue().onConnectFailed(exception);
//                            } else {
//                                NetLog.d("----> onConnectFailed onFailure entry.getValue()!=null");
//                            }
//
//                        }
//                    }
                    NetLog.e("----> mqtt connect failed, exception = " + exception.getMessage());
                }
            });

        } catch (MqttException e) {
            e.printStackTrace();
            NetLog.e(e.getMessage());
        }

    }


}
