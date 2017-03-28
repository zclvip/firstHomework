package com.zcl.work.DirectByteBufferStudy.pool;

import sun.nio.ch.DirectBuffer;

import java.nio.ByteBuffer;
import java.util.BitSet;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * ������һ����ڴ� page��Ȼ�󰴿��С bufferSize �ָ���һ����ڴ棬memoryUse��¼�ڴ�����Ϣ
 * ��������ڴ�� pool�ڴ�û����
 * Created by zhaocl1 on 2017/3/28.
 */
public class MyDirectByteBuffer implements Comparable<MyDirectByteBuffer>{

    private ByteBuffer pool;//�ڴ��
    private BitSet memoryUse;//��¼�ڴ���Ϣ true ��ʾľ�б�ռ�ã�false��ʾ�Ѿ��������ڴ�
    private int pageInit = 1024 * 1024;//������ܴ�С
    private int bufferSize;//������Ĵ�С
    protected AtomicBoolean isLock = new AtomicBoolean(false);

    public MyDirectByteBuffer(int bufferSize) {
        System.out.println("---bufferSize="+bufferSize);
        this.bufferSize = bufferSize;
    }

    public MyDirectByteBuffer(int pageInit, int bufferSize) {
        if(isLock.compareAndSet(false,true)){//Ĭ���ڴ��СΪ 1024 * 1024
            if(pageInit > this.pageInit){
                this.pageInit = pageInit;
            }
            this.bufferSize = bufferSize;

            pool = ByteBuffer.allocateDirect(pageInit);//�������� pageInit��С���ڴ�
//            System.out.println("pool  position="+pool.position()+" limit="+pool.limit()+" capacity="+pool.capacity());
            int chunkNum = pageInit/bufferSize;//�ڴ�������
            System.out.println("chunkNum="+chunkNum);
            //��¼�ڴ��
            memoryUse = new BitSet(chunkNum);
            for(int i=0;i<chunkNum;i++){
                memoryUse.set(i, true);
            }
            System.out.println("cardinality="+memoryUse.cardinality());
        }else {
            System.out.println("�Ѿ��������ˣ������ظ����� pool  position="+pool.position()+" limit="+pool.limit()+" capacity="+pool.capacity());
        }


    }

    //�����ڴ� ����һ����������
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
