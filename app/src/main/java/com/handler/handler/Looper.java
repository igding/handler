package com.handler.handler;

/**
 * <pre>
 *     author : handler
 *     e-mail : dingchao314@gmail.com
 *     time   : 2018/06/14
 *     desc   :
 *     version: 1.0
 * </pre>
 */
public class Looper {

    /**
     * 一个looper对应一个MessageQueue
     */
    static ThreadLocal<Looper> sThreadLocal = new ThreadLocal();

    /**
     * 暂时不涉及ThreadLocal的源码写法。可以理解成是一个hashmap，以当前线程作为键，looper作为值，用于区分不同线程中的不同looper
     */

    public MessageQueue mQueue = new MessageQueue();

    public static Looper myLooper() {
        return sThreadLocal.get();
    }

    public static void prepare() {
        sThreadLocal.set(new Looper());
    }

    /**
     * 不断的从MessageQueue中提取消息
     */
    public static void loop() {
        Looper looper = myLooper();
        MessageQueue mQueue = looper.mQueue;

        for (; ; ) {
            Message msg = mQueue.next(); // might block
            if (msg == null) {
                continue;
            }

            /**
             * 最为经典的设计之一：消息持有Handler(发送者)，转发方法，可以直接调用handler重写的方法
             */
            msg.target.dispatchMessage(msg);
            msg.recycleUnchecked();
        }
    }
}
