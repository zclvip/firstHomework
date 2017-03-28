package com.zcl.work.E_AbstractFactoryPattern;

/**
 * Created by zhaocl1 on 2017/3/16.
 */
public class NvWa {
    public static void main(String[] args) {
        //男性生产线
        HumanFactory maleHumanFactory = new MaleHumanFactory();
        //女性生产线
        HumanFactory femaleHumanFactory = new FemaleHumanFactory();

        Human maleYellowHuman = maleHumanFactory.createYellowHuman();
        Human femaleYelloHuman = femaleHumanFactory.createYellowHuman();

        maleYellowHuman.cry();
        maleYellowHuman.laugh();
        maleYellowHuman.talk();
        maleYellowHuman.sex();

        femaleYelloHuman.cry();
        femaleYelloHuman.laugh();
        femaleYelloHuman.talk();
        femaleYelloHuman.sex();
    }
}
