package com.zcl.work.DirectByteBufferStudy;

import java.nio.ByteBuffer;

/**
 * Created by zhaocl1 on 2017/3/27.
 */
public class DirectByteBufferTest {

    private void testDirectBuf(int count,int cap){
        long st;
        long ed;
        ByteBuffer buffer = null;
        st = System.currentTimeMillis();
        for(int i=0;i<count;i++){
            buffer = allocDirectBuffer(cap);
        }
        ed = System.currentTimeMillis();
        System.out.println("alloc directByteBuffer for " + count
                + " times spends " + (ed - st) + "ms");

        st = System.currentTimeMillis();
        for(int i=0;i<count;i++){
            processBuf(buffer);
        }
        ed = System.currentTimeMillis();
        System.out.println("directByteBuffer process " + count
                + " times spends " + (ed - st) + "ms");
    }

    private ByteBuffer testNonDirectBuf(int count, int cap) {
        long st = System.currentTimeMillis();
        ByteBuffer byteBuf = null;
        for (int i = 0; i < count; i++) {
            byteBuf = allocNonDirectBuffer(cap);
        }
        long ed = System.currentTimeMillis();
        System.out.println("alloc nonDirectByteBuffer for " + count
                + " times spends " + (ed - st) + "ms");
        st = System.currentTimeMillis();
        for (int i = 0; i < count; i++) {
            processBuf(byteBuf);

        }
        ed = System.currentTimeMillis();
        System.out.println("nonDirectByteBuffer process " + count
                + " times spends " + (ed - st) + "ms");
        return byteBuf;
    }

    private ByteBuffer allocNonDirectBuffer(int cap ){
        ByteBuffer buffer = ByteBuffer.allocate(cap);
        return buffer;
    }

    private ByteBuffer allocDirectBuffer(int cap ){
        ByteBuffer buffer = ByteBuffer.allocateDirect(cap);
        return buffer;
    }

    private void processBuf(ByteBuffer byteBuffer){
        byte[] bytes = "adbadgsg".getBytes();
        byteBuffer.put(bytes);
        byte[] bytes2 = null;
        for(int i=0;i<bytes.length;i++){
            byte b = byteBuffer.get(i);
            bytes2 = new byte[]{b};
        }
        System.out.println("----"+bytes2.toString());
    }

    public static void main(String[] args){
        DirectByteBufferTest directByteBufferTest = new DirectByteBufferTest();
        int count = 1000;
        int cap = 1024;
        directByteBufferTest.testDirectBuf(count, cap);
        directByteBufferTest.testNonDirectBuf(count,cap);

    }

}
