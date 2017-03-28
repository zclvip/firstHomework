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
    //mltipart/form-data��ʽ�ύpost�ķָ���,
    private String boundary = null;
    //post�ύ��������ĵĳ���
    private int contentLength = 0;

    private int port;

    public ServerWork(int port) {
        this.port = port;
    }

//    public void run() {
//        String threadInfo = "�߳�[" + Thread.currentThread().getId() + "] ";
//        System.out.println( "����" + threadInfo + "��������...");
//        DataInputStream dis ;
//        try {
//            dis = new DataInputStream(socket.getInputStream());
//            String line = dis.readLine();
//            String method = line.substring(0, line.indexOf(" "));
//            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
//            this.requestUrl= line.substring(line.indexOf('/')+1,line.lastIndexOf(' '));
//            if ("GET".equalsIgnoreCase(method)) {
//                System.out.println(threadInfo + "�������GET");
//                System.out.println(line);
//                this.doGet(dis, dos);
//            } else if ("POST".equalsIgnoreCase(method)) {
//                System.out.println(threadInfo + "�������POST");
//                System.out.println(line);
//                this.doPost(dis, dos);
//            }
//            socket.close();
//            System.out.println(threadInfo + "��������ϣ��ر�Socket.");
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
        //��ȡheader��Ϣ
        String line = dis.readLine();
        while (line != null) {
            System.out.println("doPost:"+line);
            if ("".equals(line)) {
                break;
            } else if (line.indexOf("Content-Length") != -1) {
                this.contentLength = Integer.parseInt(line.substring(line.indexOf("Content-Length") + 16));
            }
            //����Ҫ�ϴ������� ��ת��doMultiPart������
            else if(line.indexOf("multipart/form-data")!= -1){
                //��multiltipart�ķָ���
                this.boundary = line.substring(line.indexOf("boundary") + 9);
                System.out.println("boundary="+boundary);
                this.doMultiPart(dis,dos);
                return;
            }
            line = dis.readLine();
        }

        //������ȡ��ͨpost��û�и������ύ������
        String dataLine = null;
        //�û����͵�post��������
        byte[] buf;
        int size = 0;
        if(this.contentLength != 0){
            buf = new byte[this.contentLength];
            while (size < this.contentLength){
                int c = dis.read();
                buf[size++] = (byte)c;
            }
            System.out.println("�յ���POST��ϢΪ: " + new String(buf, 0, size));
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
        //��ȡheader��Ϣ
        System.out.println("doMultiPart ......");
        String line = dis.readLine();
        while (line != null) {
            System.out.println("line="+line);
            if ("".equals(line)) {
                break;
            } else if (line.indexOf("Content-Length") != -1) {
                this.contentLength = Integer.parseInt(line.substring(line.indexOf("Content-Length") + 16));
            }
            //����Ҫ�ϴ������� ��ת��doMultiPart������
            else if (line.indexOf("boundary") != -1) {
                //��ȡmultipart�ָ���
                this.boundary = line.substring(line.indexOf("boundary") + 9);
            }
            line = dis.readLine();
        }

        System.out.println("begin get data......");
        /*�����ע����һ����������ʹ������������ȫ�ģ��������Ķ���˵���Ե�����*****
        <HTTPͷ��������>
        ............
        Cache-Control: no-cache
        <������һ�����У����������������ݶ���Ҫ�ύ������>
        -----------------------------7d925134501f6<����multipart�ָ���>
        Content-Disposition: form-data; name="myfile"; filename="mywork.doc"
        Content-Type: text/plain

        <��������>........................................
        .................................................

        -----------------------------7d925134501f6<����multipart�ָ���>
        Content-Disposition: form-data; name="myname"<�����ֶλ򸽼�>
        <������һ������>
        <�����ֶλ򸽼�������>
        -----------------------------7d925134501f6--<����multipart�ָ��������һ���ָ���������->
        ****************************************************************/
        /**
         * �����ע����һ����������multipart���͵�POST��ȫ��ģ�ͣ�
         * Ҫ�Ѹ���ȥ����������Ҫ�ҵ��������ĵ���ʼλ�úͽ���λ��
         * **/
        if (this.contentLength != 0) {
            //�����е��ύ�����ģ����������������ֶζ��ȶ���buf.
            byte[] buf = new byte[this.contentLength];
            int totalRead = 0;
            int size = 0;
            while (totalRead < this.contentLength) {
                size = dis.read(buf, totalRead, this.contentLength - totalRead);
                totalRead += size;
            }
            //��buf����һ���ַ������������ַ�������ļ�����������ڵ�λ��
            String dataString = new String(buf, 0, totalRead);
            System.out.println("the data user posted:/n" + dataString);
            int pos = dataString.indexOf(boundary);
            //�����Թ�4�о��ǵ�һ��������λ��
            pos = dataString.indexOf("\n", pos) + 1;
            pos = dataString.indexOf("\n", pos) + 1;
            pos = dataString.indexOf("\n", pos) + 1;
            pos = dataString.indexOf("\n", pos) + 1;
            //������ʼλ��
            int start = dataString.substring(0, pos).getBytes().length;
//            pos = dataString.indexOf(boundary, pos) - 4;
//            //��������λ��
//            int end = dataString.substring(0, pos).getBytes().length;
            //������־�ߴ�(�����س����к�ǰ��ġ�--���ַ�)
            int tailSize = boundary.length() + 8;
            //��������λ��
            int end = this.contentLength - tailSize;
            //�����ҳ�filename
            int fileNameBegin = dataString.indexOf("filename") + 10;
            int fileNameEnd = dataString.indexOf("\"\r\n", fileNameBegin);
            String fileName = dataString.substring(fileNameBegin, fileNameEnd);
            System.out.println("fileName="+fileName);
            /**
             * ��ʱ���ϴ����ļ���ʾ�������ļ���·��,����c:/my file/somedir/project.doc
             * ����ʱ��ֻ��ʾ�ļ������֣�����myphoto.jpg.
             * ������Ҫ��һ���жϡ�
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
        //����serverSocket�ȴ��û���������Ȼ�������������������
        //��������ֻ���GET��POST���˴���
        //����POST���н�����������������
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
