package com.common.lib_network.mqtt;

import java.io.Serializable;

public class ShortMqttConfigBean implements Serializable {

    public ShortMqttConfigBean() {
    }

    public int delayOpenTime = 1 * 60 * 1000;
    public int delayCloseTime = 1 * 60 * 1000;
    public boolean useSHort = true;
    public int canTryConnectTimes = 5;
}
