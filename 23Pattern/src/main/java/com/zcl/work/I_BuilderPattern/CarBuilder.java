package com.zcl.work.I_BuilderPattern;

import java.util.ArrayList;

/**
 * Created by zhaocl1 on 2017/3/22.
 */
public abstract class CarBuilder {

    public abstract void setSequence(ArrayList<String> sequence);
    public abstract CarModel getCarModel();
}
