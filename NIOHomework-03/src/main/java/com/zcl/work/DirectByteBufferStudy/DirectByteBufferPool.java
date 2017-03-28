package com.zcl.work.DirectByteBufferStudy;

import java.nio.ByteBuffer;
import java.util.TreeSet;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * ʵ��һ���򵥵Ķ��߳�������DirectByteBufferPool�� *
 * ������DirectByteBuffer�����TreeSet���ֿ��Է�Χ��ѯ�����ݽṹ �� ʵ�������С��ByteBuffer�ķ��临��������
 * ������Ҫһ��1024��С��ByteBuffer������Է��ش���1024��ĳ����С�Ŀ���ByteBuffer
 * Created by zhaocl1 on 2017/3/27.
 */
public class DirectByteBufferPool {

    private final ByteBuffer[] bufferPool;//�ڴ�ض�����Ϣ
    private final TreeSet<Integer> useSet;//�ڵ㱣����Ϣ
    private final int bufferSize;//�����ڴ���С
    protected AtomicBoolean isLock = new AtomicBoolean(false);
    int maxSize=0;

    /**
     * ��ʼ���ڴ����Ϣ
     * @param poolSize
     * @param bufferSize
     */
    public DirectByteBufferPool(int poolSize,int bufferSize){
        this.bufferSize = bufferSize;
        try {
            if(isLock.compareAndSet(false,true)){
                bufferPool = new ByteBuffer[poolSize];
                useSet = new TreeSet<Integer>();

                for (int i=0;i<bufferPool.length;i++){
                    bufferPool[i] = ByteBuffer.allocateDirect(bufferSize);
                    useSet.add(i);
                }
            }else {
                bufferPool = null;
                useSet = null;
                return;
            }
        } finally {
            isLock.set(false);
        }
    }

    /**
     * �����ڴ�ķ���  �������С�����ڴ�
     * @param size
     * @return
     */
    public ByteBuffer allocMemory(int size){
        try {
            if(isLock.compareAndSet(false,true)){
                if(size > bufferSize){
                    throw new RuntimeException("current size too big");
                }else {
                    int index = useSet.pollFirst();
                    return bufferPool[index];
                }
            }
        } finally {
            isLock.set(false);
        }
        return null;
    }

    public void recycle(ByteBuffer byteBuffer){
        try {
            if(isLock.compareAndSet(false,true)){
                for(int i=0;i<this.bufferPool.length;i++){
                    if(bufferPool[i] == byteBuffer){
                        useSet.add(i);
                        byteBuffer.position(0);
                        byteBuffer.limit(bufferSize);
                        break;
                    }
                }
            }
        } finally {
            isLock.set(false);
        }
    }

    public static void main(String[] args){
        DirectByteBufferPool pool = new DirectByteBufferPool(10,1024);
        ByteBuffer buffer = pool.allocMemory(512);
        ByteBuffer buffer1 = pool.allocMemory(512);

        pool.recycle(buffer);
        pool.recycle(buffer1);

        System.out.println(pool);
    }
}
