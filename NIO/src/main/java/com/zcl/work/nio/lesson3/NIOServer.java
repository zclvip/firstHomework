package com.zcl.work.nio.lesson3;

import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by zhaocl1 on 2017/3/12.
 */
public class NIOServer {

    private static Selector selector;
    private ServerSocketChannel sschannel;
    private InetSocketAddress address;
    private int port;
    ExecutorService readThreadPool ;
    ExecutorService writeThreadPool ;

    public NIOServer(int port) throws Exception {
        readThreadPool = Executors.newFixedThreadPool(10);
        writeThreadPool = Executors.newFixedThreadPool(10);
        this.port = port;
        selector = Selector.open();
        sschannel = ServerSocketChannel.open();
        sschannel.configureBlocking(false);
        address = new InetSocketAddress(port);
        ServerSocket ss = sschannel.socket();
        ss.bind(address);
        sschannel.register(selector, SelectionKey.OP_ACCEPT);
    }

    public void service() {
        System.out.println("Server started ...  Server listening on port:" + port);
        while (true) {
            try {
                int num = 0;
                num = selector.select();
                System.out.println("num:" + num);
                if(num==0) continue;
                Set selectedKeys = selector.selectedKeys();
                Iterator it = selectedKeys.iterator();
                while (it.hasNext()) {
                    SelectionKey key = (SelectionKey) it.next();
                    it.remove();
                    // 处理IO事件
                    if ( (key.readyOps() & SelectionKey.OP_ACCEPT) == SelectionKey.OP_ACCEPT) {
                        System.out.println("-------------OP_ACCEPT");
                        // Accept the new connection
                        ServerSocketChannel ssc = (ServerSocketChannel) key.channel();

                        SocketChannel sc = ssc.accept();
                        sc.configureBlocking(false);

                        // 注册读操作,以进行下一步的读操作
                        ByteBuffer buffer = ByteBuffer.allocate(100);
                        System.out.println("ByteBuffer init position="+buffer.position()+" limit="+buffer.limit()+" capacity="+buffer.capacity());
                        sc.register(selector, SelectionKey.OP_READ, buffer);
                        sc.write(ByteBuffer.wrap("Welcome  ...\r\n".getBytes()));
                    }
                    else if ( (key.readyOps() & SelectionKey.OP_READ) == SelectionKey.OP_READ ) {
                        System.out.println("============OP_READ");
                        key.interestOps(key.interestOps()&(~SelectionKey.OP_READ));
                        readThreadPool.submit(new NIOReader(key));
                    }
                    else if ( (key.readyOps() & SelectionKey.OP_WRITE) == SelectionKey.OP_WRITE ) {
                        System.out.println("============OP_WRITE");
                        key.interestOps(key.interestOps()&(~SelectionKey.OP_WRITE));
                        writeThreadPool.submit(new NIOWriter(key));
                    }
                }
            }
            catch (Exception e) {
                System.out.println("catch...");
                continue;
            }
        }
    }


    public static void main(String[] args){
        try {
            NIOServer nioServer = new NIOServer(9000);
            nioServer.service();
        } catch (Exception e) {
            e.printStackTrace();
        }

        //telnet localhost 9000
    }
}
