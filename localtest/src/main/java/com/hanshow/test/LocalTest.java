package com.hanshow.test;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;


public class LocalTest {
    private static final String TAG = "LocalTest";

    private static void sendLocalMessage(Context context, int id) {
        Intent intent = new Intent();
        //设置广播的名字（设置Action）
        intent.setAction(TestConfig.TESTACTION);
        //携带数据
        intent.putExtra(TestConfig.TESTDATA, id);
        Log.e(TAG, "send id" + id);
        //发送广播（无序广播）
        context.sendBroadcast(intent);
//        Toast.makeText(context,id+"",Toast.LENGTH_SHORT).show();
    }

    private static void sendLocalMessage(Context context, int id, boolean useToast) {
        Intent intent = new Intent();
        //设置广播的名字（设置Action）
        intent.setAction(TestConfig.TESTACTION);
        //携带数据
        intent.putExtra(TestConfig.TESTDATA, id);
        Log.e(TAG, "send id" + id);
        //发送广播（无序广播）
        context.sendBroadcast(intent);
        if (useToast) {
            Toast.makeText(context, id + "", Toast.LENGTH_SHORT).show();
        }

    }

    public static void disposData(Context context, TestCustomerbean customer, LocalTestBean bean) {
        if (customer == null && bean == null) {
            Log.e(TAG, "customer bean==null");
            return;
        }
        if (bean.getPriority() == 2) {
            if (customer.age < 10) {
                sendLocalMessage(context, 19);
                return;
            } else if (customer.age < 20) {
                sendLocalMessage(context, 20);
                return;
            } else if (customer.age < 30) {
                sendLocalMessage(context, 21);
                return;
            } else if (customer.age < 40) {
                sendLocalMessage(context, 22);
                return;
            } else if (customer.age < 50) {
                sendLocalMessage(context, 23);
                return;
            } else if (customer.age >= 50) {
                sendLocalMessage(context, 24);
                return;
            }
        }
        if (customer.gender == 1) {
            sendLocalMessage(context, 17);
            return;
        } else {
            sendLocalMessage(context, 18);
            return;
        }

    }

    public static void disposData(Context context, TestCustomerbean customer, LocalTestBean bean, boolean useToast) {
        if (customer == null || bean == null) {
            Log.e(TAG, "customer bean==null");
            return;
        }
        if (bean.getPriority() == 2) {
            if (customer.age < 10) {
                sendLocalMessage(context, 19, useToast);
                return;
            } else if (customer.age < 20) {
                sendLocalMessage(context, 20, useToast);
                return;
            } else if (customer.age < 30) {
                sendLocalMessage(context, 21, useToast);
                return;
            } else if (customer.age < 40) {
                sendLocalMessage(context, 22, useToast);
                return;
            } else if (customer.age < 50) {
                sendLocalMessage(context, 23, useToast);
                return;
            } else if (customer.age >= 50) {
                sendLocalMessage(context, 24, useToast);
                return;
            }
        }
        if (customer.gender == 1) {
            sendLocalMessage(context, 17, useToast);
            return;
        } else {
            sendLocalMessage(context, 18, useToast);
            return;
        }

    }

    public static void disposData2(Context context, int age, int gender, int priority) {
        if (priority == 2) {
            if (age < 10) {
                sendLocalMessage(context, 19);
                return;
            } else if (age < 20) {
                sendLocalMessage(context, 20);
                return;
            } else if (age < 30) {
                sendLocalMessage(context, 21);
                return;
            } else if (age < 40) {
                sendLocalMessage(context, 22);
                return;
            } else if (age < 50) {
                sendLocalMessage(context, 23);
                return;
            } else if (age >= 50) {
                sendLocalMessage(context, 24);
                return;
            }
        }
        if (gender == 1) {
            sendLocalMessage(context, 17);
            return;
        } else {
            sendLocalMessage(context, 18);
            return;
        }

    }

    public static void disposData(Context context, float location) {
        sendLocalMessage(context, (int) location);
    }
}
