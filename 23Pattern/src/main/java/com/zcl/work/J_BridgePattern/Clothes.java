package com.zcl.work.J_BridgePattern;

/**
 * Created by zhaocl1 on 2017/3/22.
 */
public class Clothes extends Product {

    @Override
    public void beProducted() {
        System.out.println("生产衣服");
    }

    @Override
    public void beSelled() {
        System.out.println("卖衣服");
    }
}
