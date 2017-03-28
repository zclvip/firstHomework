package com.zcl.work;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by zhaocl1 on 2017/3/5.
 */
public class MyHttpClient {

    public static void uploadFile(String fileName){
        try {
            // ���з�
            final String newLine = "\r\n";
            final String boundaryPrefix = "--";
            //���ݷָ���
            String boundary = "===========zclllldsss";
            URL url = new URL("http://localhost:8080/zcl");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            // ����POST�������������������
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);

            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("Charset", "UTF-8");
            conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);

            OutputStream out = new DataOutputStream(conn.getOutputStream());
            StringBuffer sb = new StringBuffer();
            sb.append(boundaryPrefix+boundary+newLine);
            // �ļ�����,photo���������������޸�
            sb.append("Content-Disposition:form-data;name=\"photo\";filename=\""+fileName+"\""+newLine);
            sb.append("Content-Type:application/octet-stream");
            // ����ͷ�������Ժ���Ҫ�������У�Ȼ����ǲ�������
            sb.append(newLine).append(newLine);
            out.write(sb.toString().getBytes());// ������ͷ������д�뵽�������

            File file = new File(fileName);
            // ����������,���ڶ�ȡ�ļ�����
            DataInputStream in = new DataInputStream(new FileInputStream(file));
            byte[] bufferOut = new byte[1024];
            int bytes = 0;
            while ((bytes = in.read(bufferOut)) != -1){
                out.write(bufferOut,0,bytes);
            }
            // �����ӻ���
            out.write(newLine.getBytes());
            in.close();
            // ����������ݷָ��ߣ���--����BOUNDARY�ټ���--
            byte[] end_data =  (newLine + boundaryPrefix + boundary + boundaryPrefix + newLine).getBytes();
            out.write(end_data);// д�Ͻ�β��ʶ
            out.flush();
            out.close();

        } catch (Exception e) {
            System.out.println("post��������쳣��"+e);
            e.printStackTrace();
        }
    }

    public static void main(String[] args){
        String fileName="D:\\test\\hello.txt";
        uploadFile(fileName);
    }
}
