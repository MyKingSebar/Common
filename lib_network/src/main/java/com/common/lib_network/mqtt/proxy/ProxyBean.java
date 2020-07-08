package com.common.lib_network.mqtt.proxy;

import androidx.annotation.IntDef;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

public class ProxyBean {
    String proxyHost;
    int proxyPort;
    int type;



    public static final int DIRECT=1;
    public  static final int HTTP=2;
    public  static final int SOCKS=3;

    @Retention(RetentionPolicy.SOURCE)
    @Target(ElementType.PARAMETER)
    @IntDef({DIRECT,HTTP,SOCKS})
    public @interface ProxyType {
    }

    public String getProxyHost() {
        return proxyHost;
    }

    public void setProxyHost(String proxyHost) {
        this.proxyHost = proxyHost;
    }

    public int getProxyPort() {
        return proxyPort;
    }

    public void setProxyPort(int proxyPort) {
        this.proxyPort = proxyPort;
    }

    public int getType() {
        return type;
    }

    public void setType(@ProxyType int type) {
        this.type = type;
    }
}
