package com.handler.handler;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * <pre>
 *     author : handler
 *     e-mail : dingchao314@gmail.com
 *     time   : 2018/06/14
 *     desc   : 比较经典的就是，MessageQueue中会使用一种生产者和消费者模式(用于控制内存)
 *      考虑产能问题，给固定大小，
 *      存的话，如果存满了  就不存了
 *      取的话，如果没有了  就不取了
 *     version: 1.0
 * </pre>
 */
public class MessageQueue {

    Message[] mMessagesArray;

    /**
     * 根据锁和两个判断条件，相互唤醒，为empty就锁住线程A，为full就锁住线程B
     */
    Lock lock;

    Condition notEmpty;

    Condition notFull;


    /**
     * 入队的个数
     */
    int putIndex = 0;
    /**
     * 出队的个数
     */
    int takeIndex = 0;
    /**
     * 实际的个数
     */
    int count;

    /**
     * 这里用数组来代替，实际上是可以用单链表，都可以，只是效率问题。
     */

    public MessageQueue() {
        this.mMessagesArray = new Message[50];
        this.lock = new ReentrantLock();
        this.notEmpty = lock.newCondition();
        this.notFull = lock.newCondition();
    }

    /*
        保存Message，并且扩大长度，当然这里只是仿写，会存在会出现同类唤醒和异类唤醒的问题

        一个主线程可以开N个子线程，唤醒所有的话，会造成并发问题

     */
    public void enqueueMessage(Message message) {
        try {
            lock.lock();
//            if (count == mMessagesArray.length) {
            while (count == mMessagesArray.length) {
                //if需要替换成while(while的目的就是为了唤醒多个子线程)

                notEmpty.await();
            }

            mMessagesArray[putIndex] = message;
            putIndex = (++putIndex == mMessagesArray.length) ? 0 : putIndex;
            count++;

            notFull.signalAll();//唤醒线程
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    /**
     * 提取消息
     *
     * @return
     */
    public Message next() {
        Message msg = null;
        try {
            lock.lock();
//            if (count == 0) {
            while (count == 0) {
                notFull.await();
            }


            msg = mMessagesArray[takeIndex];
            takeIndex = (++takeIndex == mMessagesArray.length) ? 0 : takeIndex;
            count--;

            notEmpty.signalAll();//唤醒线程

        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
        return msg;
    }

}