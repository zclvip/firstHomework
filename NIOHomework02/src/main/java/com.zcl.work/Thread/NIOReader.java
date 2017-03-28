package com.zcl.work.Thread;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;

/**
 * Created by zhaocl1 on 2017/3/12.
 */
public class NIOReader implements Runnable {
    private SelectionKey key;

    public NIOReader(SelectionKey key) {
        this.key=key;
    }

    public void run() {
        String threadMsg = " thread:"+Thread.currentThread().getId()+" threadName:"+Thread.currentThread().getName();
        System.out.println("receive read event "+threadMsg);
        if(key.isReadable()){
            //服务器可读取消息，得到事件发生的socket通道
            SocketChannel socketChannel = (SocketChannel)key.channel();
            ByteBuffer buffer = (ByteBuffer)key.attachment();
            buffer.clear();
            System.out.println("ByteBuffer read event after clear position=" + buffer.position() + " limit=" + buffer.limit() + " capacity=" + buffer.capacity());
            buffer.put("\r\nthis message come from server:".getBytes());
            System.out.println("ByteBuffer read event after put position=" + buffer.position() + " limit=" + buffer.limit() + " capacity=" + buffer.capacity());
            int readLength=0;
            try {
                readLength = socketChannel.read(buffer);
                System.out.println("ByteBuffer read event after read position="+buffer.position()+" limit="+buffer.limit()+" capacity="+buffer.capacity());
                System.out.println("receive read event readLength:"+readLength);
                buffer.put("\r\n".getBytes());
                System.out.println("ByteBuffer read event before flip position=" + buffer.position() + " limit=" + buffer.limit() + " capacity=" + buffer.capacity());
                buffer.flip();
                System.out.println("ByteBuffer read event after flip position=" + buffer.position() + " limit=" + buffer.limit() + " capacity=" + buffer.capacity());
                int writed = 0;
//          writed = socketChannel.write(buffer);//将消息返回客户端
                System.out.println("receive read event end! writed:" + writed);

                //
//            key.interestOps(key.interestOps() | SelectionKey.OP_READ);
                key.interestOps(key.interestOps() | SelectionKey.OP_WRITE);
                key.selector().wakeup();
            } catch (IOException e) {
                try {
                    socketChannel.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                e.printStackTrace();
            }
        }
    }


    /**
     * 读取客户端发出请求数据
     * @param sc 套接通道
     */
    private static int BUFFER_SIZE = 1024;
    public static byte[] readRequest(SocketChannel sc) throws IOException {
        ByteBuffer buffer = ByteBuffer.allocate(BUFFER_SIZE);
        int off = 0;
        int r = 0;
        byte[] data = new byte[BUFFER_SIZE * 10];

        while ( true ) {
            buffer.clear();

            r = sc.read(buffer);
            if (r == -1) break;
            if ( (off + r) > data.length) {
//                data = grow(data, BUFFER_SIZE * 10);
            }
            byte[] buf = buffer.array();
            System.arraycopy(buf, 0, data, off, r);
            off += r;
        }
        byte[] req = new byte[off];
        System.arraycopy(data, 0, req, 0, off);
        return req;
    }

}
