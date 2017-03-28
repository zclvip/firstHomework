package com.zcl.work.H_TemplateMethodPattern;

/**
 * Created by zhaocl1 on 2017/3/17.
 */
public class Client {

    public static void main(String[] args) {
        HummerH2Model h2 = new HummerH2Model();
        h2.run(); //H2型号的悍马跑起来

        //客户开着H1型号，出去遛弯了
        HummerH1Model h1 = new HummerH1Model();
        h1.setAlarm(true);
        h1.run(); //汽车跑起来了；
    }
}
