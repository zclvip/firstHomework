package com.zcl.work.E_AbstractFactoryPattern;

/**
 * Created by zhaocl1 on 2017/3/16.
 */
public interface HumanFactory {

    public Human createYellowHuman();
    public Human createWhiteHuman();
    public Human createBlackHuman();
}
