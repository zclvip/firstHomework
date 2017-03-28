package com.zcl.work;

import com.sun.org.apache.xpath.internal.SourceTree;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhaocl1 on 2017/3/5.
 */
public class MyHttpServer {
    //post提交请求的正文的长度
    public static int contentLength = 0;
    //mltipart/form-data方式提交post的分隔符,
    public static String boundary = null;

    public static String prefix = "--";

    public static void main(String[] args) throws IOException{

        try {
            ServerSocket serverSocket = new ServerSocket(8998);
            while(true) {
                Socket socket = serverSocket.accept();
                InputStream is = socket.getInputStream();
                DataInputStream dis = new DataInputStream(is);
                String method = null;
                String lineInput = null;
                String requestPage = null;
                LineNumberReader in = new LineNumberReader(new InputStreamReader(socket.getInputStream()));
//                lineInput = dis.readLine();
//                method = lineInput.substring(0, lineInput.indexOf(" "));
//                requestPage = lineInput.substring(lineInput.indexOf("/") + 1, lineInput.lastIndexOf(" "));
//                OutputStream out = socket.getOutputStream();

                while ((lineInput = in.readLine()) != null){
                    System.out.println(lineInput);
                    if(in.getLineNumber() == 1){
                        requestPage = lineInput.substring(lineInput.indexOf('/')+1,lineInput.lastIndexOf(' '));
                        System.out.println("request page: "+requestPage);
                    }else {
                        if(lineInput.isEmpty()){
                            System.out.println("header finished");
                        }
                    }
                }
                System.out.println(method);
                if ("GET".equals(method)) {
                    System.out.println("Method:get");
                } else if ("POST".equals(method)) {
                    System.out.println("Method:post");

//                    doPost(dis, out);
                }
//                dis.close();
//                is.close();
//                socket.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

//    class Worker implements Runnable{
//
//    }

    private static void doPost(DataInputStream dis, OutputStream out) throws IOException{
        String line = dis.readLine();
        while(line != null){
            System.out.println(line);
            if("".equals(line)){//请求头读完
                break;
            }else if(line.indexOf("Content-Length: ") != -1){
                contentLength = Integer.parseInt(line.substring(line.indexOf("Content-Length: ")+"Content-Length: ".length()));
            }else if(line.indexOf("multipart/form-data")!= -1){
                boundary = line.substring(line.indexOf("boundary=")+"boundary=".length());
                doMultiPart(dis, out);
                return;
            }
            line = dis.readLine();
        }

        System.out.println("begin reading posted data......");
        byte[] bytes = new byte[contentLength];
        dis.read(bytes);
        System.out.println("data:"+new String(bytes));

        String msg = "POST x-www-form-urlencoded FINISH\r\n";
        String response = "HTTP/1.1 200 OK\r\n";
        response += "SERVER: zhj server/1.0\r\n";
        response += "Content-Length: " + (msg.length()-2) + "\r\n";
        response += "\r\n";
        response += msg;
        out.write(response.getBytes());
        out.flush();
        dis.close();
        out.close();
    }

    private static void doMultiPart(DataInputStream dis, OutputStream out) throws IOException {
        System.out.println("doMultiPart ......");
        String line = dis.readLine();
        while(line != null){
            System.out.println(line);
            if("".equals(line)){//请求头读完
                break;
            }else if(line.indexOf("Content-Length: ") != -1){
                contentLength = Integer.parseInt(line.substring(line.indexOf("Content-Length: ")+"Content-Length: ".length()));
            }else if(line.indexOf("multipart/form-data")!= -1){
                boundary = line.substring(line.indexOf("boundary=")+"boundary=".length());
            }
            line = dis.readLine();
        }

        System.out.println("begin get data......");

        if (contentLength != 0) {
            //把所有的提交的正文，包括附件和其他字段都先读到buf.
            byte[] buf = new byte[contentLength];
            int totalRead = 0;
            int size = 0;
            while (totalRead < contentLength) {
                size = dis.read(buf, totalRead, contentLength - totalRead);
                totalRead += size;
            }

            //查找出边界
            List<Integer> boundaryIndexList = new ArrayList<Integer>();
            int index = -1;
            while(true){
                index = findBoundryIndex(buf,prefix+boundary,index+1);
                if(index != -1){
                    boundaryIndexList.add(index);
                }else{
                    break;
                }
            }

            doResolveMultiPart(buf,boundaryIndexList);
        }

        String msg = "POST multipart/form-data FINISH\r\n";
        String response = "HTTP/1.1 200 OK\r\n";
        response += "SERVER: zhj server/1.0\r\n";
        response += "Content-Length: " + (msg.length()-2) + "\r\n";
        response += "\r\n";
        response += msg;
        out.write(response.getBytes());
        out.flush();
        dis.close();
        out.close();
    }

    private static int findBoundryIndex(byte[] buf, String boundary, int index) {
        for(int i=index;i<buf.length-boundary.length();i++){
            String temp = new String(buf,i,boundary.length());
            if(temp.equals(boundary)){
                return i;
            }
        }
        return -1;
    }

    private static void doResolveMultiPart(byte[] buf, List<Integer> boundaryIndexList) throws IOException {
        for(int i=0;i<boundaryIndexList.size()-1;i++){
            doResolveData(buf,boundaryIndexList.get(i),boundaryIndexList.get(i+1));
        }
    }

    private static void doResolveData(byte[] buf, Integer start, Integer end) throws IOException {
        int index = 0;//数据域中头部和真实数据分割的索引
        for(int i=start;i<end;i++){
            if(buf[i] == 13 && buf[i+1] == 10 && buf[i+2] == 13 && buf[i+3] == 10 ){//找到\r\n
                index = i+4;
                break;
            }
        }

        //解析头部
        String dataHead = new String(buf,start,index-start);
        System.out.println(dataHead);

        String[] heads = dataHead.split("\r\n");
        for(String head:heads){
            if(head.indexOf("Content-Disposition") != -1){
                if(head.indexOf("filename=") != -1){
                    String filename = head.substring(head.indexOf("filename=")+"filename=".length()).replace("\"", "");
                    System.out.println("保存文件名："+filename);
                    FileOutputStream fos = new FileOutputStream(filename);
                    fos.write(buf,index,end-index-2);//-2是报文后面的\r\n
                    fos.close();
                }else{
                    String name = head.substring(head.indexOf("name=")+"name=".length()).replace("\"", "");
                    String value = new String(buf,index,end-index-2);//-2是报文后面的\r\n
                    System.out.println("key:"+name+",value:"+value);
                }
            }
        }
    }

}
