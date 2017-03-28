package com.zcl.work.D_FactoryMethodPattern;

/**
 * Created by zhaocl1 on 2017/3/16.
 */
public class NvWa {
    public static void main(String[] args) {
        System.out.println("------------����ĵ�һ�����������ģ�����-----------------");
        Human whiteHuman = HumanFactory.createHuman(WhiteHuman.class);
        whiteHuman.cry();
        whiteHuman.laugh();
        whiteHuman.talk();

        System.out.println("\n\n------------����ĵڶ������������ģ�����-----------------");
        Human blackHuman = HumanFactory.createHuman(BlackHuman.class);
        blackHuman.cry();
        blackHuman.laugh();
        blackHuman.talk();

        System.out.println("\n\n------------����ĵ��������������ģ���ɫ����-----------------");
        Human yellowHuman = HumanFactory.createHuman(YellowHuman.class);
        yellowHuman.cry();
        yellowHuman.laugh();
        yellowHuman.talk();

        for(int i=0;i<1000;i++){
            System.out.println("\r\n ----------�����������------");
            Human human = HumanFactory.createHuman();
            human.cry();
            human.laugh();
            human.talk();
        }
    }
}
