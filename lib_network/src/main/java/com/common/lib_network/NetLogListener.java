package com.common.lib_network;

public interface NetLogListener {
    void v(String msg);

    void d(String msg);

    void i(String msg);

    void w(String msg);

    void e(String msg);

    void e(String msg, Exception e);

    //多段
    void vs(String msg);

    void ds(String msg);

    void is(String msg);

    void ws(String msg);

    void es(String msg);
}
