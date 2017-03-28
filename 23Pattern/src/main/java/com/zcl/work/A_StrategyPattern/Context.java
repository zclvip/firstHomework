package com.zcl.work.A_StrategyPattern;

/**
 * Created by zhaocl1 on 2017/3/14.
 * ��ı���ˣ��ǻ�Ҫ�н���
 */
public class Context {

    private IStrategy strategy;

    public Context(IStrategy strategy) {
        this.strategy = strategy;
    }

    //ʹ�ü�ı�ˣ�����
    public void operate(){
        this.strategy.operate();
    }
}
