package com.zcl.work.nio.scalableIO;

import java.nio.channels.SelectionKey;

//���� ��ʵ����Handler��ͬʱ����Read��Write�¼�, �����������״̬�ж�
//���ǿ�����State-Object pattern�������ŵ�ʵ��
public class Handler02 { // ...
    /**
    public void run() { // initial state is reader
        socket.read(input);
        if (inputIsComplete()) {
            process();
            sk.attach(new Sender());  //״̬Ǩ��, Read����write, ��Sender��Ϊ�µ�callback����
            sk.interest(SelectionKey.OP_WRITE);
            sk.selector().wakeup();
        }
    }
    class Sender implements Runnable {
        public void run(){ // ...
            socket.write(output);
            if (outputIsComplete()) sk.cancel();
        }
    }
     **/
    public static void main(String[] arg){

    }
}
