package com.zcl.work.F_FacadePattern;

/**
 * Created by zhaocl1 on 2017/3/17.
 */
public class LetterProcessImpl implements LetterProcess {

    public void writeContext(String context) {
        System.out.println("д�ŵ�����..."+context);
    }

    public void fillEnvelope(String address) {
        System.out.println("д�ռ��˵�ַ������"+address);
    }

    public void letterInotoEnvelope() {
        System.out.println("���ŷ������䡣����");
    }

    public void sendLetter() {
        System.out.println("�ʵ��ż�������");
    }
}
