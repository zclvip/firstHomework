package com.zcl.work;

import org.codehaus.groovy.runtime.powerassert.SourceText;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

/**
 * Created by zhaocl1 on 2017/3/10.
 */
public class NIOTest {

    public static void nio_test01() throws IOException{
        //通道管理器
        Selector selector = Selector.open();
        //获得一个serverSocket通道
        ServerSocketChannel serverSocketChannel= ServerSocketChannel.open();
        //设置通道为阻塞
        serverSocketChannel.configureBlocking(false);
        //将该通道对应的serverSocket绑定到port端口
        InetSocketAddress address = new InetSocketAddress(9000);
        serverSocketChannel.socket().bind(address);
        System.out.println("stated at " + address);
        //将通道管理器和该通道绑定，并为该通道注册SelectionKey.OP_ACCEPT事件，注册事件后，当事件到达时，selector.select()会返回，如未到达，一直阻塞
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        while (true){
            //事件到达时，方法返回
            int selectedNum = selector.select();
            System.out.println("selecte Number is :" + selectedNum);
            Iterator<SelectionKey> iter = selector.selectedKeys().iterator();
            while (iter.hasNext()){
                SelectionKey selectionKey = iter.next();
                iter.remove();//删除key 以防重复处理
                //用“位与”操作interest 集合和给定的SelectionKey常量，可以确定某个确定的事件是否在interest 集合中
//                if((selectionKey.readyOps() & SelectionKey.OP_ACCEPT) == SelectionKey.OP_ACCEPT){
                if(selectionKey.isAcceptable()){//客户端请求连接事件
                    ServerSocketChannel serverChannel = (ServerSocketChannel)selectionKey.channel();
                    //获得和客户端连接的通道
                    SocketChannel socketChannel = serverChannel.accept();
                    socketChannel.configureBlocking(false);
                    //在和客户端连接成功后，为了可以接受到客户端的信息，需要给通道设置读权限
                    socketChannel.register(selector, SelectionKey.OP_READ);
                    //给客户端发送信息
                    socketChannel.write(ByteBuffer.wrap("Welcome leader.us Power Man java course ...\r\n".getBytes()));
//                }else if ((selectionKey.readyOps() & SelectionKey.OP_READ) == SelectionKey.OP_READ){
                }else if (selectionKey.isReadable()){
                    System.out.println("receive read event");
                    //服务器可读取消息，得到事件发生的socket通道
                    SocketChannel socketChannel = (SocketChannel)selectionKey.channel();
                    ByteBuffer buffer = ByteBuffer.allocate(100);
//                    buffer.put("\r\nFollow you:".getBytes());
                    socketChannel.read(buffer);
                    buffer = (ByteBuffer)selectionKey.attachment();
                    if(buffer==null || !buffer.hasRemaining()){
                        int writeBufferSize = socketChannel.socket().getSendBufferSize();
                        System.out.println("send buffer size:"+writeBufferSize);
                        buffer = ByteBuffer.allocate(writeBufferSize*50+2);
                        for(int i=0;i<buffer.capacity()-2;i++){
                            buffer.put((byte)('a'+i%25));
                        }
                        buffer.flip();
                        System.out.println("send another huge block data " + writeBufferSize * 50);
                    }
                    int writed = socketChannel.write(buffer);//将消息返回客户端
                    System.out.println("writed " + writed);
                    if(buffer.hasRemaining()){
                        System.out.println(" not write finished,remains " + buffer.remaining());
                        buffer.compact();
                        selectionKey.attach(buffer);
                        selectionKey.interestOps(selectionKey.interestOps() | SelectionKey.OP_WRITE);
                    }else {
                        System.out.println("block write finished ");
                        selectionKey.attach(null);
                        selectionKey.interestOps(selectionKey.interestOps()&~SelectionKey.OP_WRITE);
                    }
//                }else if(selectionKey.isWritable()) {
                }else if((selectionKey.readyOps() & SelectionKey.OP_WRITE) == SelectionKey.OP_WRITE) {
                    System.out.println("received write event");
                    ByteBuffer buffer = (ByteBuffer)selectionKey.attachment();
                    SocketChannel socketChannel = (SocketChannel)selectionKey.channel();
                    if(buffer != null){
                        int writed = socketChannel.write(buffer);
                        System.out.println("writed "+writed);
                        if (buffer.hasRemaining()){
                            System.out.println(" not write finished,bind to session,remains:"+buffer.remaining());
                            buffer=buffer.compact();
                            selectionKey.attach(buffer);
                            selectionKey.interestOps(selectionKey.interestOps() | SelectionKey.OP_WRITE);
                        }else {
                            System.out.println("block write finished ");
                            selectionKey.attach(null);
                            selectionKey.interestOps(selectionKey.interestOps()&~SelectionKey.OP_WRITE);
                        }
                    }
                }
            }
        }
    }

    public static void main(String[] args){
        try {
            nio_test01();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
