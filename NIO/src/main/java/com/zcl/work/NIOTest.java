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
        //ͨ��������
        Selector selector = Selector.open();
        //���һ��serverSocketͨ��
        ServerSocketChannel serverSocketChannel= ServerSocketChannel.open();
        //����ͨ��Ϊ����
        serverSocketChannel.configureBlocking(false);
        //����ͨ����Ӧ��serverSocket�󶨵�port�˿�
        InetSocketAddress address = new InetSocketAddress(9000);
        serverSocketChannel.socket().bind(address);
        System.out.println("stated at " + address);
        //��ͨ���������͸�ͨ���󶨣���Ϊ��ͨ��ע��SelectionKey.OP_ACCEPT�¼���ע���¼��󣬵��¼�����ʱ��selector.select()�᷵�أ���δ���һֱ����
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        while (true){
            //�¼�����ʱ����������
            int selectedNum = selector.select();
            System.out.println("selecte Number is :" + selectedNum);
            Iterator<SelectionKey> iter = selector.selectedKeys().iterator();
            while (iter.hasNext()){
                SelectionKey selectionKey = iter.next();
                iter.remove();//ɾ��key �Է��ظ�����
                //�á�λ�롱����interest ���Ϻ͸�����SelectionKey����������ȷ��ĳ��ȷ�����¼��Ƿ���interest ������
//                if((selectionKey.readyOps() & SelectionKey.OP_ACCEPT) == SelectionKey.OP_ACCEPT){
                if(selectionKey.isAcceptable()){//�ͻ������������¼�
                    ServerSocketChannel serverChannel = (ServerSocketChannel)selectionKey.channel();
                    //��úͿͻ������ӵ�ͨ��
                    SocketChannel socketChannel = serverChannel.accept();
                    socketChannel.configureBlocking(false);
                    //�ںͿͻ������ӳɹ���Ϊ�˿��Խ��ܵ��ͻ��˵���Ϣ����Ҫ��ͨ�����ö�Ȩ��
                    socketChannel.register(selector, SelectionKey.OP_READ);
                    //���ͻ��˷�����Ϣ
                    socketChannel.write(ByteBuffer.wrap("Welcome leader.us Power Man java course ...\r\n".getBytes()));
//                }else if ((selectionKey.readyOps() & SelectionKey.OP_READ) == SelectionKey.OP_READ){
                }else if (selectionKey.isReadable()){
                    System.out.println("receive read event");
                    //�������ɶ�ȡ��Ϣ���õ��¼�������socketͨ��
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
                    int writed = socketChannel.write(buffer);//����Ϣ���ؿͻ���
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
