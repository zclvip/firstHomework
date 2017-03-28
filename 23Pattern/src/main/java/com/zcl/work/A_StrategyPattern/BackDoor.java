package com.zcl.work.A_StrategyPattern;

/**
 * Created by zhaocl1 on 2017/3/14.
 */
public class BackDoor implements IStrategy {

    public void operate() {
        System.out.println("找乔国老帮忙，让吴国太给孙权施加压力");
    }
}
