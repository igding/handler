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
public class Message {

    public Object obj;

    public int message;

    public Message next;

    public Handler target;
    public boolean what;


    public void recycleUnchecked() {

    }
}
