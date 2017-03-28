package com.zcl.work.J_BridgePattern;

/**
 * Created by zhaocl1 on 2017/3/22.
 */
public class House extends Product {

    @Override
    public void beProducted() {
        System.out.println("生产出的房子是这个样子。。。。");
    }

    @Override
    public void beSelled() {
        System.out.println("生产出的房子卖出去了，，，，");
    }
}
