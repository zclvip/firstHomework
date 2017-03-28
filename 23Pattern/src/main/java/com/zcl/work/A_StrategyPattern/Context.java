package com.zcl.work.A_StrategyPattern;

/**
 * Created by zhaocl1 on 2017/3/14.
 * 计谋有了，那还要有锦囊
 */
public class Context {

    private IStrategy strategy;

    public Context(IStrategy strategy) {
        this.strategy = strategy;
    }

    //使用计谋了，出招
    public void operate(){
        this.strategy.operate();
    }
}
