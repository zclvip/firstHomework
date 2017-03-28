package com.zcl.work.nio.thread;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Nio �̣߳�ר�Ÿ���nio read,write
 * ������ʵ���д��룬�����nio,����������д����ȳ������д���,ּ����� Reactorģ�ͣ����̰߳汾��
 * @author dingwei2
 *
 */
public class SubReactorThread extends Thread {

    private Selector selector;
    private ExecutorService businessExecutorPool;
    private List<NioTask> taskList = new ArrayList<NioTask>(512);
    private ReentrantLock taskMainLock = new ReentrantLock();

    /**
     * ҵ���̳߳�
     * @param businessExecutorPool
     */
    public SubReactorThread(ExecutorService businessExecutorPool) {
        try {
            this.businessExecutorPool = businessExecutorPool;
            this.selector = Selector.open();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * socket channel
     *
     */
    public void register(NioTask task) {
        if (task != null) {
            try {
                taskMainLock.lock();
                taskList.add(task);
            } finally {
                taskMainLock.unlock();
            }
        }
    }

    // private

    public void run() {
        while (!Thread.interrupted()) {
            Set<SelectionKey> ops = null;
            try {
                selector.select(1000);
                ops = selector.selectedKeys();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                continue;
            }

            // ��������¼�
            for (Iterator<SelectionKey> it = ops.iterator(); it.hasNext();) {
                SelectionKey key = it.next();
                it.remove();

                try {
                    if (key.isWritable()) { // ��ͻ��˷�������
                        SocketChannel clientChannel = (SocketChannel) key
                                .channel();
                        ByteBuffer buf = (ByteBuffer) key.attachment();
                        buf.flip();
                        clientChannel.write(buf);
                        System.out.println("�������ͻ��˷������ݡ�����");
                        // ����ע����¼�
                        clientChannel.register(selector, SelectionKey.OP_READ);
                    } else if (key.isReadable()) { // ���ܿͻ�������
                        System.out.println("����˽��տͻ����������󡣡���");
                        SocketChannel clientChannel = (SocketChannel) key
                                .channel();
                        ByteBuffer buf = ByteBuffer.allocate(1024);
                        System.out.println(buf.capacity());
                        clientChannel.read(buf);//�����������

                        //ת�����󵽾����ҵ���̣߳���Ȼ��������ʵ������dubbo������֧��ת�����ԣ����ִ��ʱ��̣�
                        //������û�����ݿ�����ȣ�������io�߳���ִ�С���ʵ����ת����ҵ���̳߳�
                        dispatch(clientChannel, buf);

                    }
                } catch (Throwable e) {
                    e.printStackTrace();
                    System.out.println("�ͻ��������Ͽ����ӡ�������������");
                }

            }

            // ע���¼�
            if (!taskList.isEmpty()) {
                try {
                    taskMainLock.lock();
                    for (Iterator<NioTask> it = taskList
                            .iterator(); it.hasNext();) {
                        NioTask task = it.next();
                        try {
                            SocketChannel sc = task.getSc();
                            if(task.getData() != null) {
                                sc.register(selector, task.getOp(), task.getData());
                            } else {
                                sc.register(selector, task.getOp());
                            }

                        } catch (Throwable e) {
                            e.printStackTrace();// ignore
                        }
                        it.remove();
                    }

                } finally {
                    taskMainLock.unlock();
                }
            }

        }
    }

    /**
     * �˴���reqBuffer���ڿ�д״̬
     * @param sc
     * @param reqBuffer
     */
    private void dispatch(SocketChannel sc, ByteBuffer reqBuffer) {
        businessExecutorPool.submit( new Handler(sc, reqBuffer, this)  );
    }
}