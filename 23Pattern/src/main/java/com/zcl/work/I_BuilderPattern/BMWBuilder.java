package com.zcl.work.I_BuilderPattern;

import java.util.ArrayList;

/**
 * Created by zhaocl1 on 2017/3/22.
 */
public class BMWBuilder extends CarBuilder {

    private BMWModel bmwModel = new BMWModel();
    @Override
    public void setSequence(ArrayList<String> sequence) {
        this.bmwModel.setSequence(sequence);
    }

    @Override
    public CarModel getCarModel() {
        return this.bmwModel;
    }
}
