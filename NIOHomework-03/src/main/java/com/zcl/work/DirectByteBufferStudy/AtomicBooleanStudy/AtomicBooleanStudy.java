package com.zcl.work.DirectByteBufferStudy.AtomicBooleanStudy;

import java.util.concurrent.TimeUnit;

/**
 * Created by zhaocl1 on 2017/3/27.
 */
public class AtomicBooleanStudy {

//    private static class BarWorker1 implements Runnable{
//        private static boolean exists = false;
//        private String name;
//        public void run() {
//            if (!exists) {
//                exists = true;
//                System.out.println(name + " enter");
//                System.out.println(name + " working");
//                System.out.println(name + " leave");
//                exists = false;
//            } else {
//                System.out.println(name + " give up");
//            }
//        }
//    }

    private static class BarWorker2 implements Runnable{
        private static boolean exists = false;
        private String name;

        public BarWorker2(String name) {
            this.name = name;
        }

        public void run() {
            if (!exists) {
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                exists = true;
                System.out.println(name + " enter");
                try {
                    System.out.println(name + " working");
                    TimeUnit.SECONDS.sleep(3);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                System.out.println(name + " leave");
                exists = false;
            } else {
                System.out.println(name + " give up");
            }
        }
    }

    public static void main(String[] args){
//        BarWorker1 barWorker1 = new BarWorker1();
//        barWorker1.name = "barWorker1";
//        BarWorker1 barWorker11 = new BarWorker1();
//        barWorker11.name = "barWorker11";
//        barWorker1.run();
//        barWorker11.run();
        System.out.println("-------------");
        BarWorker2 barWorker21 = new BarWorker2("barWorker21");
        BarWorker2 barWorker22 = new BarWorker2("barWorker22");
        for(int i=0;i<10;i++){
            barWorker22.run();
            barWorker21.run();
        }


    }
}
