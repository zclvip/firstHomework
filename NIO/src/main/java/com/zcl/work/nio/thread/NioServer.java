package com.zcl.work.nio.thread;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.ServerSocketChannel;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/**
 * Reactor ����Reactorģʽʵ��
 *
 * Acceptor,��ʵ������Ϊ��������Ƿ���˽�ɫ
 * @author Administrator
 *
 */
public class NioServer {

    private static final int DEFAULT_PORT = 9080;

    public static void main(String[] args) {

        new Thread(new Acceptor()).start();

    }


    private static class Acceptor implements Runnable {

        // main Reactor �̳߳أ����ڴ���ͻ��˵���������
        private static ExecutorService mainReactor = Executors.newSingleThreadExecutor();

        public void run() {
            // TODO Auto-generated method stub
            ServerSocketChannel ssc = null;

            try {
                ssc = ServerSocketChannel.open();
                ssc.configureBlocking(false);
                ssc.bind(new InetSocketAddress(DEFAULT_PORT));

                //ת���� MainReactor��Ӧ��
                dispatch(ssc);

                System.out.println("����˳ɹ�����������������");

            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        private void dispatch(ServerSocketChannel ssc) {
            mainReactor.submit(new MainReactor(ssc));
        }

    }


}

