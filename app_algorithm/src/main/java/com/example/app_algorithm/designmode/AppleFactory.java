package com.example.app_algorithm.designmode;

public class AppleFactory {

    private int apple = 0;


    public synchronized void increace() {
        while (apple == 5) {
            // 不使用if的原因是，wait线程不知道其他线程什么时候notify，
            // 所有被唤醒，抢占到锁，并且从wait方法退出的时候，再次判断指定条件。
            try{
                wait();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        apple ++;
        System.out.print("生成成功");
        notifyAll(); // notifyAll不用notif的原因时，在生产消费者多个的情况下，可能造成死锁，如果在可控制范围可以使用notify。
    }

    public synchronized void decreate() {
        while (apple == 0) {
            try {
                wait();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        apple--;
        System.out.print("消费成功");
        notifyAll();
    }
}
