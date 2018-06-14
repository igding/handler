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
public class Handler {

    private final Looper mLooper;
    private final MessageQueue mQueue;

    public Handler() {
        /**
         * 实际上handler中会有一个callback回调
         */
        mLooper = Looper.myLooper();
        mQueue = mLooper.mQueue;
    }

    public void sendMessage(Message message) {
        message.target = this;
        mQueue.enqueueMessage(message);
    }

    public void handleMessage(Message msg) {
    }


    public void dispatchMessage(Message msg) {
        handleMessage(msg);
    }
}
