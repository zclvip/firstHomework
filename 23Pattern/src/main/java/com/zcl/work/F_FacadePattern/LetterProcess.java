package com.zcl.work.F_FacadePattern;

/**
 * Created by zhaocl1 on 2017/3/17.
 */
public interface LetterProcess {

    //先写信的内容
    public void writeContext(String context);

    //再写信封
    public void fillEnvelope(String address);

    //信放信封里
    public void letterInotoEnvelope();

    //邮递
    public void sendLetter();
}
