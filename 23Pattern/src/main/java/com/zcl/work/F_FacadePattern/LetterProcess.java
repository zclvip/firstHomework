package com.zcl.work.F_FacadePattern;

/**
 * Created by zhaocl1 on 2017/3/17.
 */
public interface LetterProcess {

    //��д�ŵ�����
    public void writeContext(String context);

    //��д�ŷ�
    public void fillEnvelope(String address);

    //�ŷ��ŷ���
    public void letterInotoEnvelope();

    //�ʵ�
    public void sendLetter();
}
