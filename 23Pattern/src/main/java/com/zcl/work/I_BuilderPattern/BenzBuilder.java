package com.zcl.work.I_BuilderPattern;

import java.util.ArrayList;

/**
 * Created by zhaocl1 on 2017/3/22.
 */
public class BenzBuilder extends CarBuilder {

    private BenzModel benzModel = new BenzModel();
    @Override
    public void setSequence(ArrayList<String> sequence) {
        this.benzModel.setSequence(sequence);
    }

    @Override
    public CarModel getCarModel() {
        return this.benzModel;
    }
}
