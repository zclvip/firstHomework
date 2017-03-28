package com.zcl.work.D_FactoryMethodPattern;

/**
 * Created by zhaocl1 on 2017/3/15.
 */
public class BlackHuman implements Human {

    public void cry() {
        System.out.println("黑人哭");
    }
    public void laugh() {
        System.out.println("黑人笑");
    }
    public void talk() {
        System.out.println("黑人说话");
    }
}
