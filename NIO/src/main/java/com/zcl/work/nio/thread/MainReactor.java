package com.zcl.work.nio.thread;

import java.io.IOException;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;


/**
 * ��Reactor,��Ҫ����������������ķ�Ӧ��
 * @author Administrator
 *
 */
public class MainReactor implements Runnable {

    private Selector selector;
    private SubReactorThreadGroup subReactorThreadGroup;

    public MainReactor(SelectableChannel channel) {
        try {
            selector = Selector.open();
            channel.register(selector, SelectionKey.OP_ACCEPT);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        subReactorThreadGroup = new SubReactorThreadGroup(4);
    }

    public void run() {

        System.out.println("MainReactor is running");
        // TODO Auto-generated method stub
        while (!Thread.interrupted()) {

            Set<SelectionKey> ops = null;
            try {
                selector.select(1000);
                ops = selector.selectedKeys();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            // ��������¼�
            for (Iterator<SelectionKey> it = ops.iterator(); it.hasNext();) {
                SelectionKey key = it.next();
                it.remove();
                try {
                    if (key.isAcceptable()) { // �ͻ��˽�������
                        System.out.println("�յ��ͻ��˵��������󡣡���");
                        ServerSocketChannel serverSc = (ServerSocketChannel) key.channel();// ������ʵ������ֱ��ʹ��ssl�������
                        SocketChannel clientChannel = serverSc.accept();
                        clientChannel.configureBlocking(false);
                        subReactorThreadGroup.dispatch(clientChannel); // ת��������
                    }
                } catch (Throwable e) {
                    e.printStackTrace();
                    System.out.println("�ͻ��������Ͽ����ӡ�������������");
                }
            }
        }
    }
}