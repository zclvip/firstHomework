package com.zcl.work.G_AdapterPattern;

/**
 * Created by zhaocl1 on 2017/3/17.
 */
public class App {
    public static void main(String[] args) {
        //没有与外系统连接的时候，是这样写的
        IUserInfo youngGirl = new UserInfo();
        //从数据库中查到101个
        for(int i=0;i<101;i++){
            youngGirl.getMobileNumber();
        }

        System.out.println("---------------");

        IUserInfo youngGirl_out = new OuterUserInfo();
        //从数据库中查到101个
        for(int i=0;i<101;i++){
            youngGirl.getMobileNumber();
        }
    }
}
