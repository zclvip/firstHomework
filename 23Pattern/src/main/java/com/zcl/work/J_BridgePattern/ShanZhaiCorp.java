package com.zcl.work.J_BridgePattern;

/**
 * Created by zhaocl1 on 2017/3/22.
 */
public class ShanZhaiCorp extends Corp {

    public ShanZhaiCorp(Product product) {
        super(product);
    }

    @Override
    public void makeMoney() {
        super.makeMoney();
        System.out.println("山寨公司赚大钱了，，，，");
    }
}
