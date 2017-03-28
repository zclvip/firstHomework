package com.zcl.work.E_AbstractFactoryPattern;

/**
 * Created by zhaocl1 on 2017/3/16.
 */
public abstract class AbstractWhiteHuman implements Human{
    public void cry() {
        System.out.println("白人哭");
    }
    public void laugh() {
        System.out.println("白人笑");
    }
    public void talk() {
        System.out.println("白人说话");
    }
}
