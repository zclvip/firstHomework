package com.zcl.work.A_StrategyPattern;

/**
 * Created by zhaocl1 on 2017/3/14.
 */
public class GivenGreenLight implements IStrategy {

    public void operate() {
        System.out.println("求吴国太开个绿灯,放行！");
    }
}
