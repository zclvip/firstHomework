package com.zcl.work.F_FacadePattern;

/**
 * Created by zhaocl1 on 2017/3/17.
 */
public class LetterProcessImpl implements LetterProcess {

    public void writeContext(String context) {
        System.out.println("写信的内容..."+context);
    }

    public void fillEnvelope(String address) {
        System.out.println("写收件人地址。。。"+address);
    }

    public void letterInotoEnvelope() {
        System.out.println("把信放入信箱。。。");
    }

    public void sendLetter() {
        System.out.println("邮递信件。。。");
    }
}
