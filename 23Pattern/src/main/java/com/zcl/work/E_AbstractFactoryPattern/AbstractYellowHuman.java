package com.zcl.work.E_AbstractFactoryPattern;

/**
 * Created by zhaocl1 on 2017/3/16.
 */
public abstract class AbstractYellowHuman  implements Human {
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
