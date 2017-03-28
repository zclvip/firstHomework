package com.zcl.work.DirectByteBufferStudy;

import java.nio.ByteBuffer;
import java.util.TreeSet;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * 实现一个简单的多线程能力的DirectByteBufferPool， *
 * 里面存放DirectByteBuffer，结合TreeSet这种可以范围查询的数据结构 ， 实现任意大小的ByteBuffer的分配复用能力，
 * 比如需要一个1024大小的ByteBuffer，则可以返回大于1024的某个最小的空闲ByteBuffer
 * Created by zhaocl1 on 2017/3/27.
 */
public class DirectByteBufferPool {

    private final ByteBuffer[] bufferPool;//内存池对象信息
    private final TreeSet<Integer> useSet;//节点保存信息
    private final int bufferSize;//最大的内存块大小
    protected AtomicBoolean isLock = new AtomicBoolean(false);
    int maxSize=0;

    /**
     * 初始化内存池信息
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
     * 进行内存的分配  按传入大小分配内存
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
