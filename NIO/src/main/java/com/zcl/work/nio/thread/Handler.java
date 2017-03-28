package com.zcl.work.nio.thread;

import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;

/**
 * ҵ���߳�
 * ��handler�Ĺ��ܾ������յ���������Ϣ��������� hello,�������յ��������Ϣ��Ȼ�󷵻ظ��ͻ���
 * @author Administrator
 *
 */
public class Handler implements Runnable {

    private static final byte[] b = "hello,�������յ��������Ϣ��".getBytes(); // ����˸��ͻ��˵���Ӧ

    private SocketChannel sc;
    private ByteBuffer reqBuffer;
    private SubReactorThread parent;

    public Handler(SocketChannel sc, ByteBuffer reqBuffer,
                   SubReactorThread parent) {
        super();
        this.sc = sc;
        this.reqBuffer = reqBuffer;
        this.parent = parent;
    }

    public void run() {
        System.out.println("ҵ����handler�п�ʼִ�С�����");
        // TODO Auto-generated method stub
        //ҵ����
        reqBuffer.put(b);
        parent.register(new NioTask(sc, SelectionKey.OP_WRITE, reqBuffer));
        System.out.println("ҵ����handler��ִ�н���������");
    }

}


