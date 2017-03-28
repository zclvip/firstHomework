package com.zcl.work.E_AbstractFactoryPattern;

/**
 * Created by zhaocl1 on 2017/3/16.
 */
public enum HumanEnum {
    YelloMaleHuma("com.zcl.work.E_AbstractFactoryPattern.YellowMaleHuman"),
    YelloFemaleHuman("com.zcl.work.E_AbstractFactoryPattern.YellowFemaleHuman"),
    WhiteFemaleHuman("com.zcl.work.E_AbstractFactoryPattern.WhiteFemaleHuman"),
    WhiteMaleHuman("com.zcl.work.E_AbstractFactoryPattern.WhiteMaleHuman"),
    BlackFemaleHuman("com.zcl.work.E_AbstractFactoryPattern.BlackFemaleHuman"),
    BlackMaleHuman("com.zcl.work.E_AbstractFactoryPattern.BlackMaleHuman");

    private String value = "";
    HumanEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
