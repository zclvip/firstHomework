package com.zcl.work.E_AbstractFactoryPattern;

/**
 * Created by zhaocl1 on 2017/3/16.
 */
public class MaleHumanFactory extends AbstractHumanFactory {

    public Human createYellowHuman() {
        return super.createHuman(HumanEnum.YelloMaleHuma);
    }

    public Human createWhiteHuman() {
        return super.createHuman(HumanEnum.WhiteMaleHuman);
    }

    public Human createBlackHuman() {
        return super.createHuman(HumanEnum.BlackMaleHuman);
    }
}
