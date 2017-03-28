package com.zcl.work.I_BuilderPattern;

import java.util.ArrayList;

/**
 * Created by zhaocl1 on 2017/3/22.
 */
public class Client {

    public static void main(String[] args){
//        BenzModel benzModel = new BenzModel();
//        ArrayList<String> sequence = new ArrayList<String>();
//
//        sequence.add("engine boom");
//        sequence.add("start");
//        sequence.add("stop");
//
//        benzModel.setSequence(sequence);
//        benzModel.run();

//        ArrayList<String> sequence = new ArrayList<String>();
//        sequence.add("engine boom");
//        sequence.add("start");
//        sequence.add("stop");
//        BenzBuilder benzBuilder = new BenzBuilder();
//        benzBuilder.setSequence(sequence);
//        BenzModel benzModel  = (BenzModel)benzBuilder.getCarModel();
//        benzModel.run();
//
//        BMWBuilder bmwBuilder = new BMWBuilder();
//        bmwBuilder.setSequence(sequence);
//        BMWModel bmwModel = (BMWModel)bmwBuilder.getCarModel();
//        bmwModel.run();

        Director director = new Director();
        director.getABenzModel().run();
        director.getBBenzModel().run();
        director.getABWModel().run();
        director.getBBWModel().run();
    }
}
