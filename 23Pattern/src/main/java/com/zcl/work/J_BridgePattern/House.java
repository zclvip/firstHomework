package com.zcl.work.J_BridgePattern;

/**
 * Created by zhaocl1 on 2017/3/22.
 */
public class House extends Product {

    @Override
    public void beProducted() {
        System.out.println("�������ķ�����������ӡ�������");
    }

    @Override
    public void beSelled() {
        System.out.println("�������ķ�������ȥ�ˣ�������");
    }
}
