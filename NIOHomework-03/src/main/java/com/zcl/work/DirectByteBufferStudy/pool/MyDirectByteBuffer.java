package com.zcl.work.DirectByteBufferStudy.pool;

import sun.nio.ch.DirectBuffer;

import java.nio.ByteBuffer;
import java.util.BitSet;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * 先申请一大块内存 page，然后按块大小 bufferSize 分割这一大块内存，memoryUse记录内存块的信息
 * 回收完快内存后 pool内存没回收
 * Created by zhaocl1 on 2017/3/28.
 */
public class MyDirectByteBuffer implements Comparable<MyDirectByteBuffer>{

    private ByteBuffer pool;//内存池
    private BitSet memoryUse;//记录内存信息 true 表示木有被占用，false表示已经分配了内存
    private int pageInit = 1024 * 1024;//单块的总大小
    private int bufferSize;//单个块的大小
    protected AtomicBoolean isLock = new AtomicBoolean(false);

    public MyDirectByteBuffer(int bufferSize) {
        System.out.println("---bufferSize="+bufferSize);
        this.bufferSize = bufferSize;
    }

    public MyDirectByteBuffer(int pageInit, int bufferSize) {
        if(isLock.compareAndSet(false,true)){//默认内存大小为 1024 * 1024
            if(pageInit > this.pageInit){
                this.pageInit = pageInit;
            }
            this.bufferSize = bufferSize;

            pool = ByteBuffer.allocateDirect(pageInit);//首先申请 pageInit大小的内存
//            System.out.println("pool  position="+pool.position()+" limit="+pool.limit()+" capacity="+pool.capacity());
            int chunkNum = pageInit/bufferSize;//内存块的数量
            System.out.println("chunkNum="+chunkNum);
            //记录内存块
            memoryUse = new BitSet(chunkNum);
            for(int i=0;i<chunkNum;i++){
                memoryUse.set(i, true);
            }
            System.out.println("cardinality="+memoryUse.cardinality());
        }else {
            System.out.println("已经创建好了，不用重复创建 pool  position="+pool.position()+" limit="+pool.limit()+" capacity="+pool.capacity());
        }


    }

    //分配内存 分配一块立即返回
    public ByteBuffer alloctMemory(){
        for(int i=0;i<memoryUse.length();i++){
            if(memoryUse.get(i)){
                memoryUse.set(i, false);
                System.out.println("i=" + i + " bufferSize="+bufferSize);
                pool.position(i * bufferSize);
                pool.limit((i + 1) * bufferSize);
//                System.out.println(i+" before slice pool  position=" + pool.position() + " limit=" + pool.limit() + " capacity=" + pool.capacity());
                ByteBuffer newBuffer = pool.slice();
//                System.out.println(i+" pool  position="+pool.position()+" limit="+pool.limit()+" capacity="+pool.capacity());
                System.out.println(i+" chunk  position="+newBuffer.position()+" limit="+newBuffer.limit()+" capacity="+newBuffer.capacity());
                return newBuffer;
            }
        }
        return null;
    }

    public void recycle(ByteBuffer byteBuffer){
        DirectBuffer sunbuffer = (DirectBuffer)byteBuffer;
        DirectBuffer parentBuffer = (DirectBuffer) sunbuffer.attachment();
//        System.out.println("sunbuffer.address()="+sunbuffer.address()+" parentBuffer.address()="+parentBuffer.address());
        int startChunk = (int) ((sunbuffer.address() - parentBuffer.address()) / this.bufferSize);
//        System.out.println("pool  position="+pool.position()+" limit="+pool.limit()+" capacity="+pool.capacity());
        memoryUse.set(startChunk,true);
    }

    public int compareTo(MyDirectByteBuffer o) {
//        System.out.println("compareTo this.bufferSize="+this.bufferSize+" o.bufferSize"+o.bufferSize);
        if(this.bufferSize > o.bufferSize){
            return 1;
        }else if (this.bufferSize < o.bufferSize){
            return -1;
        }
        return 0;
    }

    public static void main(String[] args){
        MyDirectByteBuffer myDirectByteBuffer = new MyDirectByteBuffer(1024*1024,128);
        ByteBuffer buffer1 = myDirectByteBuffer.alloctMemory();
        ByteBuffer buffer2 = myDirectByteBuffer.alloctMemory();

        myDirectByteBuffer.recycle(buffer1);
        myDirectByteBuffer.recycle(buffer2);
        System.out.println("myDirectByteBuffer=" + myDirectByteBuffer.pool.position() + " limit=" + myDirectByteBuffer.pool.limit() + " capacity=" + myDirectByteBuffer.pool.capacity());
        System.out.println("myDirectByteBuffer="+myDirectByteBuffer.memoryUse);
        for(int i=0;i<myDirectByteBuffer.memoryUse.size();i++){
            if(!myDirectByteBuffer.memoryUse.get(i)){
                System.out.println("---------"+i);
            }
        }

        buffer1 = myDirectByteBuffer.alloctMemory();
        buffer2 = myDirectByteBuffer.alloctMemory();

        myDirectByteBuffer.recycle(buffer1);
        myDirectByteBuffer.recycle(buffer2);
    }
}
