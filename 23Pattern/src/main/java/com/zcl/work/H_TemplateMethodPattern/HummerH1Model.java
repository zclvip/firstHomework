package com.zcl.work.H_TemplateMethodPattern;

/**
 * Created by zhaocl1 on 2017/3/17.
 */
public class HummerH1Model extends HummerModel {

    private boolean alarmFlag = true; //�Ƿ�Ҫ������
    @Override
    protected void alarm() {
        System.out.println("����H1����...");
    }
    @Override
    protected void engineBoom() {
        System.out.println("����H1����������������...");
    }
    @Override
    protected void start() {
        System.out.println("����H1����...");
    }
    @Override
    protected void stop() {
        System.out.println("����H1ͣ��...");
    }
    @Override
    protected boolean isAlarm() {
        return this.alarmFlag;
    }
    //Ҫ��Ҫ�����ȣ����пͻ�����������
    public void setAlarm(boolean isAlarm){
        this.alarmFlag = isAlarm;
    }
}
