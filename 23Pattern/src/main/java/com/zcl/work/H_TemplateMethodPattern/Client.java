package com.zcl.work.H_TemplateMethodPattern;

/**
 * Created by zhaocl1 on 2017/3/17.
 */
public class Client {

    public static void main(String[] args) {
        HummerH2Model h2 = new HummerH2Model();
        h2.run(); //H2�ͺŵĺ���������

        //�ͻ�����H1�ͺţ���ȥ������
        HummerH1Model h1 = new HummerH1Model();
        h1.setAlarm(true);
        h1.run(); //�����������ˣ�
    }
}
