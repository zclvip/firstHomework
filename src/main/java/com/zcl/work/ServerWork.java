package com.zcl.work;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by zhaocl1 on 2017/3/6.
 */
public class ServerWork  {

    private Socket socket;
    private String requestUrl;
    public static final String WEB_ROOT = "c:/root";
    //mltipart/form-data方式提交post的分隔符,
    private String boundary = null;
    //post提交请求的正文的长度
    private int contentLength = 0;

    private int port;

    public ServerWork(int port) {
        this.port = port;
    }

//    public void run() {
//        String threadInfo = "线程[" + Thread.currentThread().getId() + "] ";
//        System.out.println( "开启" + threadInfo + "处理请求...");
//        DataInputStream dis ;
//        try {
//            dis = new DataInputStream(socket.getInputStream());
//            String line = dis.readLine();
//            String method = line.substring(0, line.indexOf(" "));
//            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
//            this.requestUrl= line.substring(line.indexOf('/')+1,line.lastIndexOf(' '));
//            if ("GET".equalsIgnoreCase(method)) {
//                System.out.println(threadInfo + "请求类别：GET");
//                System.out.println(line);
//                this.doGet(dis, dos);
//            } else if ("POST".equalsIgnoreCase(method)) {
//                System.out.println(threadInfo + "请求类别：POST");
//                System.out.println(line);
//                this.doPost(dis, dos);
//            }
//            socket.close();
//            System.out.println(threadInfo + "请求处理完毕，关闭Socket.");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

    private void doGet(DataInputStream dis,DataOutputStream dos) throws Exception{

        File theFile = new File(WEB_ROOT,this.requestUrl);
        OutputStream out = socket.getOutputStream();
        if(theFile.exists()){
            InputStream fileIn = new FileInputStream(theFile);
            byte[] buf = new byte[fileIn.available()];
            fileIn.read(buf);
            fileIn.close();
            out.write(buf);
            out.flush();
            socket.close();
            System.out.println("request complete.");
        }else {
            String msg = " I can't find bao zang ...cry...\r\n";
            String response = "HTTP/1.1 200 OK\r\n";
            response += "Server: Leader Server/0.1\r\n";
            response += "Content-Length: "+(msg.length()-4)+"\r\n";
            response += "\r\n";
            response += msg;
            out.write(response.getBytes());
            out.flush();
        }
    }

    private void doPost(DataInputStream dis,DataOutputStream dos) throws Exception{
        //读取header信息
        String line = dis.readLine();
        while (line != null) {
            System.out.println("doPost:"+line);
            if ("".equals(line)) {
                break;
            } else if (line.indexOf("Content-Length") != -1) {
                this.contentLength = Integer.parseInt(line.substring(line.indexOf("Content-Length") + 16));
            }
            //表明要上传附件， 跳转到doMultiPart方法。
            else if(line.indexOf("multipart/form-data")!= -1){
                //得multiltipart的分隔符
                this.boundary = line.substring(line.indexOf("boundary") + 9);
                System.out.println("boundary="+boundary);
                this.doMultiPart(dis,dos);
                return;
            }
            line = dis.readLine();
        }

        //继续读取普通post（没有附件）提交的数据
        String dataLine = null;
        //用户发送的post数据正文
        byte[] buf;
        int size = 0;
        if(this.contentLength != 0){
            buf = new byte[this.contentLength];
            while (size < this.contentLength){
                int c = dis.read();
                buf[size++] = (byte)c;
            }
            System.out.println("收到的POST信息为: " + new String(buf, 0, size));
        }

        String msg = "POST multipart/form-data FINISH\r\n";
        String response = "HTTP/1.1 200 OK\r\n";
        response += "SERVER: zhj server/1.0\r\n";
        response += "Content-Length: " + (msg.length()-2) + "\r\n";
        response += "\r\n";
        response += msg;
        dos.write(response.getBytes());
        dos.flush();
        dis.close();
        dos.close();
    }

    private void doMultiPart(DataInputStream dis,DataOutputStream dos) throws Exception{
        //读取header信息
        System.out.println("doMultiPart ......");
        String line = dis.readLine();
        while (line != null) {
            System.out.println("line="+line);
            if ("".equals(line)) {
                break;
            } else if (line.indexOf("Content-Length") != -1) {
                this.contentLength = Integer.parseInt(line.substring(line.indexOf("Content-Length") + 16));
            }
            //表明要上传附件， 跳转到doMultiPart方法。
            else if (line.indexOf("boundary") != -1) {
                //获取multipart分隔符
                this.boundary = line.substring(line.indexOf("boundary") + 9);
            }
            line = dis.readLine();
        }

        System.out.println("begin get data......");
        /*下面的注释是一个浏览器发送带附件的请求的全文，所有中文都是说明性的文字*****
        <HTTP头部内容略>
        ............
        Cache-Control: no-cache
        <这里有一个空行，表明接下来的内容都是要提交的正文>
        -----------------------------7d925134501f6<这是multipart分隔符>
        Content-Disposition: form-data; name="myfile"; filename="mywork.doc"
        Content-Type: text/plain

        <附件正文>........................................
        .................................................

        -----------------------------7d925134501f6<这是multipart分隔符>
        Content-Disposition: form-data; name="myname"<其他字段或附件>
        <这里有一个空行>
        <其他字段或附件的内容>
        -----------------------------7d925134501f6--<这是multipart分隔符，最后一个分隔符多两个->
        ****************************************************************/
        /**
         * 上面的注释是一个带附件的multipart类型的POST的全文模型，
         * 要把附件去出来，就是要找到附件正文的起始位置和结束位置
         * **/
        if (this.contentLength != 0) {
            //把所有的提交的正文，包括附件和其他字段都先读到buf.
            byte[] buf = new byte[this.contentLength];
            int totalRead = 0;
            int size = 0;
            while (totalRead < this.contentLength) {
                size = dis.read(buf, totalRead, this.contentLength - totalRead);
                totalRead += size;
            }
            //用buf构造一个字符串，可以用字符串方便的计算出附件所在的位置
            String dataString = new String(buf, 0, totalRead);
            System.out.println("the data user posted:/n" + dataString);
            int pos = dataString.indexOf(boundary);
            //以下略过4行就是第一个附件的位置
            pos = dataString.indexOf("\n", pos) + 1;
            pos = dataString.indexOf("\n", pos) + 1;
            pos = dataString.indexOf("\n", pos) + 1;
            pos = dataString.indexOf("\n", pos) + 1;
            //附件开始位置
            int start = dataString.substring(0, pos).getBytes().length;
//            pos = dataString.indexOf(boundary, pos) - 4;
//            //附件结束位置
//            int end = dataString.substring(0, pos).getBytes().length;
            //结束标志尺寸(包含回车换行和前后的“--”字符)
            int tailSize = boundary.length() + 8;
            //附件结束位置
            int end = this.contentLength - tailSize;
            //以下找出filename
            int fileNameBegin = dataString.indexOf("filename") + 10;
            int fileNameEnd = dataString.indexOf("\"\r\n", fileNameBegin);
            String fileName = dataString.substring(fileNameBegin, fileNameEnd);
            System.out.println("fileName="+fileName);
            /**
             * 有时候上传的文件显示完整的文件名路径,比如c:/my file/somedir/project.doc
             * 但有时候只显示文件的名字，比如myphoto.jpg.
             * 所以需要做一个判断。
             */
//            if(fileName.lastIndexOf("//")!=-1){
//                fileName = fileName.substring(fileName.lastIndexOf("//") + 1);
//            }
//            fileName = fileName.substring(0, fileName.length()-2);
            OutputStream fileOut = new FileOutputStream(WEB_ROOT +"/"+ fileName);
            fileOut.write(buf, start, end-start);
            fileOut.close();
            fileOut.close();
        }
        String response = "";
        response += "HTTP/1.1 200 OK/n";
        response += "Server: zcl 1.0/n";
        response += "Content-Type: text/html/n";
        response += "Last-Modified: Mon, 11 Jan 1998 13:23:42 GMT/n";
        response += "Accept-ranges: bytes";
        response += "/n";
        dos.write("<html><head><title>test server</title></head><body><p>Post is ok</p></body></html>".getBytes());
        dos.flush();
        dis.close();
        System.out.println("request complete.");
    }

    public void service() throws Exception {
        ServerSocket serverSocket = new ServerSocket(this.port);
        System.out.println("server is ok.");
        //开启serverSocket等待用户请求到来，然后根据请求的类别作处理
        //在这里我只针对GET和POST作了处理
        //其中POST具有解析单个附件的能力
        while (true) {
            Socket socket = serverSocket.accept();
            System.out.println("new request coming.");
            DataInputStream reader = new DataInputStream((socket.getInputStream()));
            String line = reader.readLine();
            String method = line.substring(0, 4).trim();
            OutputStream out = socket.getOutputStream();
            DataOutputStream dos = new DataOutputStream(out);
            this.requestUrl = line.split(" ")[1];
            System.out.println("["+line+"]");
            System.out.println("requestUrl="+requestUrl);
            if ("GET".equalsIgnoreCase(method)) {
                System.out.println("do get......");
                this.doGet(reader, dos);
            } else if ("POST".equalsIgnoreCase(method)) {
                System.out.println("do post......");
                this.doPost(reader, dos);
            }
            socket.close();
            System.out.println("socket closed.");
        }
    }
    public static void main(String args[]) throws Exception {
        ServerWork server = new ServerWork(8080);
        server.service();
    }
}
