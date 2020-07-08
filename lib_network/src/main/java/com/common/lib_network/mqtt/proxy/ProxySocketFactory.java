package com.common.lib_network.mqtt.proxy;

import org.apache.commons.net.DefaultSocketFactory;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.Socket;
import java.net.UnknownHostException;


public class ProxySocketFactory extends DefaultSocketFactory {
    private final DefaultSocketFactory delegate;
    private final Proxy proxy;

    public ProxySocketFactory() {
        this("180.76.158.133", 60007);
    }

    private static Integer resolveProxyPort() {
        return Integer.valueOf(System.getProperty("http.proxyPort", "3128"));
    }

    private static String resolveProxyHost() {
        return System.getProperty("http.proxyHost", "localhost");
    }

    public ProxySocketFactory(String proxyHost, int proxyPort) {
        this(new DefaultSocketFactory(), proxyHost, proxyPort, null);
    }

    public ProxySocketFactory(String proxyHost, int proxyPort, Proxy.Type type) {
        this(new DefaultSocketFactory(), proxyHost, proxyPort, type);
    }

    public ProxySocketFactory(DefaultSocketFactory delegate, String proxyHost, int proxyPort, Proxy.Type type) {
        this.delegate = delegate;
        if (type != null) {
            this.proxy = buildProxy(proxyHost, proxyPort, type);
        } else {
            this.proxy = buildProxy(proxyHost, proxyPort);
        }

    }

    private Proxy buildProxy(String proxyHost, int proxyPort) {
        return buildProxy(proxyHost, proxyPort, Proxy.Type.SOCKS);
    }

    private Proxy buildProxy(String proxyHost, int proxyPort, Proxy.Type type) {
        return new Proxy(type, new InetSocketAddress(proxyHost, proxyPort));
    }

    @Override
    public Socket createSocket() throws IOException {
        return new ProxiedSocket(delegate, new Socket(proxy));
    }


    @Override
    public Socket createSocket(String host, int port) throws IOException, UnknownHostException {
        return delegate.createSocket(host, port);
    }

    @Override
    public Socket createSocket(String host, int port, InetAddress localHost, int localPort)
            throws IOException, UnknownHostException {
        return delegate.createSocket(host, port, localHost, localPort);
    }

    @Override
    public Socket createSocket(InetAddress host, int port) throws IOException {
        return delegate.createSocket(host, port);
    }

    @Override
    public Socket createSocket(InetAddress address, int port, InetAddress localAddress, int localPort)
            throws IOException {
        return delegate.createSocket(address, port, localAddress, localPort);
    }

}