package com.zcl.work.G_AdapterPattern;

/**
 * Created by zhaocl1 on 2017/3/17.
 */
public class App {
    public static void main(String[] args) {
        //û������ϵͳ���ӵ�ʱ��������д��
        IUserInfo youngGirl = new UserInfo();
        //�����ݿ��в鵽101��
        for(int i=0;i<101;i++){
            youngGirl.getMobileNumber();
        }

        System.out.println("---------------");

        IUserInfo youngGirl_out = new OuterUserInfo();
        //�����ݿ��в鵽101��
        for(int i=0;i<101;i++){
            youngGirl.getMobileNumber();
        }
    }
}
