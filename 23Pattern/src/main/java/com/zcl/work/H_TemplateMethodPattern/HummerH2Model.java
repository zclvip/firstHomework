package com.zcl.work.H_TemplateMethodPattern;

/**
 * Created by zhaocl1 on 2017/3/17.
 */
public class HummerH2Model extends HummerModel {

    @Override
    protected void alarm() {
        System.out.println("����H2����...");
    }
    @Override
    protected void engineBoom() {
        System.out.println("����H2����������������...");
    }
    @Override
    protected void start() {
        System.out.println("����H2����...");
    }
    @Override
    protected void stop() {
        System.out.println("����H2ͣ��...");
    }
    //Ĭ��û�����ȵ�
    @Override
    protected boolean isAlarm() {
        return false;
    }
}
