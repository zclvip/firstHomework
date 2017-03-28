package com.zcl.work.I_BuilderPattern;

/**
 * Created by zhaocl1 on 2017/3/22.
 */
public class BMWModel extends CarModel {

    @Override
    protected void start() {
        System.out.println("宝马车启动。。。");
    }

    @Override
    protected void stop() {
        System.out.println("宝马车停车。。。");
    }

    @Override
    protected void alarm() {
        System.out.println("宝马 喇叭声。。。");
    }

    @Override
    protected void engineBoom() {
        System.out.println("宝马 引擎声。。。");
    }
}
