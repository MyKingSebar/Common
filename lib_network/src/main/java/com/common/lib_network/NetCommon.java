package com.common.lib_network;

public class NetCommon {
    private NetLogListener logListener = null;

    private NetCommon() {
    }

    private static class NetCommonHolder {
        private static NetCommon INSTANCE = new NetCommon();
    }

    public static NetCommon getInstance() {
        return NetCommonHolder.INSTANCE;
    }

    public void setLogListener(NetLogListener listener) {
        logListener = listener;
    }

    public NetLogListener getLogListener() {
        return logListener;
    }
}
