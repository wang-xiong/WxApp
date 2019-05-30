package com.example.app_algorithm.designmode;

public class Producer implements Runnable {

    private AppleFactory appleFactory;

    public Producer(AppleFactory appleFactory) {
        this.appleFactory = appleFactory;
    }


    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            try {
                System.out.print("第" + i + "次生成");
                Thread.sleep(30);
            } catch (Exception e) {
                e.printStackTrace();
            }
            appleFactory.increace();
        }

    }
}
