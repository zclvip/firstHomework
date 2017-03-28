package com.zcl.work.E_AbstractFactoryPattern;

/**
 * Created by zhaocl1 on 2017/3/16.
 */
public class FemaleHumanFactory extends AbstractHumanFactory {

    public Human createYellowHuman() {
        return super.createHuman(HumanEnum.YelloFemaleHuman);
    }

    public Human createWhiteHuman() {
        return super.createHuman(HumanEnum.WhiteFemaleHuman);
    }

    public Human createBlackHuman() {
        return super.createHuman(HumanEnum.BlackFemaleHuman);
    }
}
