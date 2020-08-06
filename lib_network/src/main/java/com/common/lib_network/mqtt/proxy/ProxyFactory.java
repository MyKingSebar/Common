package com.common.lib_network.mqtt.proxy;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.net.SocketFactory;

/**
 * @author Zz 张立男
 * @Description ProxyFactory mqtt 的 socks 代理
 * @date 2020/7/15 18:51
 * o(＞﹏＜)o
 */
public class ProxyFactory extends SocketFactory {

    private Proxy mProxy;

    public ProxyFactory(Proxy.Type type, String hostName, int port) {
        mProxy = new Proxy(type, new InetSocketAddress(hostName, port));
    }

    @Override
    public Socket createSocket() throws IOException {
        if (mProxy != null) {
            return new Socket(mProxy);
        } else {
            return new Socket();
        }
    }

    @Override
    public Socket createSocket(String host, int port) throws IOException, UnknownHostException {
        return new Socket(host, port);
    }

    @Override
    public Socket createSocket(String host, int port, InetAddress localHost, int localPort) throws IOException, UnknownHostException {
        return new Socket(host, port, localHost, localPort);
    }

    @Override
    public Socket createSocket(InetAddress host, int port) throws IOException {
        return new Socket(host, port);
    }

    @Override
    public Socket createSocket(InetAddress address, int port, InetAddress localAddress, int localPort) throws IOException {
        return new Socket(address, port, localAddress, localPort);
    }
}
