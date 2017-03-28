package com.zcl.work.F_FacadePattern;

/**
 * Created by zhaocl1 on 2017/3/17.
 */
public class Client {

    public static void main(String[] args){
        ModenPostOffice modenPostOffice = new ModenPostOffice();
        String context = "hello girl....";
        String address = "±±¾© º£µí";
        modenPostOffice.sendLetter(context,address);
    }
}
