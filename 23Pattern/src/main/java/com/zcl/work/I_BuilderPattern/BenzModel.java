package com.zcl.work.I_BuilderPattern;

/**
 * Created by zhaocl1 on 2017/3/22.
 */
public class BenzModel extends CarModel {

    @Override
    protected void start() {
        System.out.println("���۳�����������");
    }

    @Override
    protected void stop() {
        System.out.println("���۳�ͣ��������");
    }

    @Override
    protected void alarm() {
        System.out.println("���� ������������");
    }

    @Override
    protected void engineBoom() {
        System.out.println("���� ������������");
    }
}
