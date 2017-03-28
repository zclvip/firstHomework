package com.zcl.work.DirectByteBufferStudy.pool;

import java.nio.ByteBuffer;
import java.util.TreeSet;

/**
 * Created by zhaocl1 on 2017/3/28.
 */
public class MyDirectByteBufferPool {

    TreeSet<MyDirectByteBuffer> usedSet = new TreeSet<MyDirectByteBuffer>();
    private int pageInit = 1024 * 1024;//单块的总大小

    public MyDirectByteBufferPool() {
        int chunkSize = 128;
        while (pageInit / chunkSize >4){
            MyDirectByteBuffer myDirectByteBuffer = new MyDirectByteBuffer(pageInit, chunkSize);
            usedSet.add(myDirectByteBuffer);
            chunkSize = chunkSize * 2;
        }
        System.out.println("usedSet.size()=" + usedSet.size() );
    }

    public ByteBuffer alloctMemory(int size) {
        return usedSet.ceiling(new MyDirectByteBuffer(size)).alloctMemory();
    }

    public void recycle(ByteBuffer byteBuffer) {
        usedSet.ceiling(new MyDirectByteBuffer(byteBuffer.limit())).recycle(byteBuffer);
    }

    public static void main(String[] args){
        MyDirectByteBufferPool pool = new MyDirectByteBufferPool();
        ByteBuffer buffer1 = pool.alloctMemory(128);
        ByteBuffer buffer2 = pool.alloctMemory(256);
        ByteBuffer buffer3 = pool.alloctMemory(720);
        ByteBuffer buffer4 = pool.alloctMemory(1024);

        pool.recycle(buffer1);
        pool.recycle(buffer2);
        pool.recycle(buffer3);
        pool.recycle(buffer4);

        System.out.println(pool);
    }
}
