package com.example.app_algorithm.designmode;

public class AppleTest {

    public static void main(String[] args) {
        AppleFactory appleFactory = new AppleFactory();
        Producer producer = new Producer(appleFactory);
        Consumer consumer = new Consumer(appleFactory);
        Thread thread1 = new Thread(producer);
        Thread thread2 = new Thread(consumer);
        thread1.start();
        thread2.start();
    }
}
