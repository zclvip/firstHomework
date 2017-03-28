package com.zcl.work;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

/**
 * Created by zhaocl1 on 2017/3/6.
 */
public class ClientWork {
    public static final String NEWLINE= "\r\n";
    public static final String BOUNDARYPRIFIX = "--";
    public static final String BOUNDARY = "MiniHttpBoundaryHdG6xBwZYp6dSKqa";
    public static final String UPLOAD_URL = "http://localhost/zcl/test";
    public static final String fileToUpload = "c:/share/中文12020405010302.jpg";

    public static final String TAIL = NEWLINE + BOUNDARYPRIFIX + BOUNDARY + BOUNDARYPRIFIX + NEWLINE;
    public static final int BUFFER_SIZE = 1024;

    public static void uploadFile(String fileName) {
        try {
            long contentSize;
            File file = new File(fileName);
            long fileSize = file.length();
            Socket socket =new Socket(InetAddress.getByName("127.0.0.1"),8080);
            DataOutputStream out=new DataOutputStream(socket.getOutputStream());

            StringBuilder multiPartHead = new StringBuilder();
            multiPartHead.append(BOUNDARYPRIFIX);
            multiPartHead.append(BOUNDARY);
            multiPartHead.append(NEWLINE);
            multiPartHead.append("Content-Disposition: form-data;name=\"uploadFile\";filename=\""
                    + file.getName() + "\"" + NEWLINE);
            multiPartHead.append("Content-Type:application/octet-stream");
            multiPartHead.append(NEWLINE);
            multiPartHead.append(NEWLINE);
            contentSize = multiPartHead.toString().getBytes().length
                    + fileSize + TAIL.getBytes().length;

            String httpHead =""
                    +"POST "+ UPLOAD_URL +" HTTP/1.1 \r\n"
                    +"Host: localhost \r\n "+"Accept: */* \r\n"
                    +"Cache-Control:no-cache \r\n" +"User-Agent: MSIE6.0; \r\n"
                    +"Content-Type: multipart/form-data; boundary=" + BOUNDARY + "\r\n"
                    +"Content-Length: "+ contentSize + "\r\n "
                    +"Connection: Keep-Alive \r\n\r\n";//报头以两个\n作为结束标志
            //1) 先写HTTP头
            out.write(httpHead.getBytes());
            //2) 再写上传文件对应的multipart头
            out.write(multiPartHead.toString().getBytes());
            DataInputStream in = new DataInputStream(new FileInputStream(file));
            byte[] bufferOut = new byte[BUFFER_SIZE];
            int bytes = 0;
            //3)每次从需要上传的文件读1K字节,并且写到输出流中
            while ((bytes = in.read(bufferOut)) != -1) {
                out.write(bufferOut, 0, bytes);
            }
            in.close();
            //4)写结束标志，即--加上BOUNDARY再加上--
            byte[] end_data = TAIL.getBytes();
            out.write(end_data);
            out.flush();


            // 定义BufferedReader输入流来读取URL的响应
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    socket.getInputStream()));
            String line = null;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
            out.close();
            reader.close();
            socket.close();
        } catch (Exception e) {
            System.out.println("发送POST请求出现异常！" + e);
            e.printStackTrace();
        }
    }

    public static void main(String[] args){
        uploadFile("D:/test/hello.txt");
    }
}
