package com.zcl.work.D_FactoryMethodPattern;

/**
 * Created by zhaocl1 on 2017/3/15.
 */
public class YellowHuman implements Human {

    public void cry() {
        System.out.println("黄色人类哭");
    }
    public void laugh() {
        System.out.println("黄色人类笑");
    }
    public void talk() {
        System.out.println("黄色人类说话");
    }
}
