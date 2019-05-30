package com.example.app_algorithm.designmode;

public class Consumer implements Runnable {

    private AppleFactory appleFactory;

    public Consumer(AppleFactory appleFactory) {
        this.appleFactory = appleFactory;
    }


    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            try {
                System.out.print("第" + i + "次消费");
                Thread.sleep(3000);
            } catch (Exception e) {
                e.printStackTrace();
            }
            appleFactory.decreate();
        }

    }
}
