package com.zcl.work.Thread;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;

/**
 * Created by zhaocl1 on 2017/3/12.
 */
public class NIOWriter implements Runnable {

    private SelectionKey key;

    public NIOWriter(SelectionKey key) {
        this.key = key;
    }

    public void run() {
        String threadMsg = " thread:"+Thread.currentThread().getId()+" threadName:"+Thread.currentThread().getName();
        System.out.println("received write event "+threadMsg);
        if(key.isWritable()){
            ByteBuffer buffer = (ByteBuffer)key.attachment();
            System.out.println("ByteBuffer writer event position=" + buffer.position() + " limit=" + buffer.limit() + " capacity=" + buffer.capacity());
            SocketChannel socketChannel = (SocketChannel)key.channel();
            try {
                int writed = socketChannel.write(buffer);
                System.out.println("ByteBuffer writer event after write position=" + buffer.position() + " limit=" + buffer.limit() + " capacity=" + buffer.capacity());
                System.out.println("write event writed:"+writed);

                key.interestOps(key.interestOps() | SelectionKey.OP_READ);
                key.selector().wakeup();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
