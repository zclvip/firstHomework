package com.zcl.work.J_BridgePattern;

/**
 * Created by zhaocl1 on 2017/3/22.
 */
public class HouseCorp extends Corp {
    public HouseCorp(House house) {
        super(house);
    }

    @Override
    public void makeMoney() {
        super.makeMoney();
        System.out.println("���ز���˾׬��Ǯ�ˣ�������");
    }
}
