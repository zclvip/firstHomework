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
            // 换行符
            final String newLine = "\r\n";
            final String boundaryPrefix = "--";
            //数据分割线
            String boundary = "===========zclllldsss";
            URL url = new URL("http://localhost:8080/zcl");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);

            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("Charset", "UTF-8");
            conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);

            OutputStream out = new DataOutputStream(conn.getOutputStream());
            StringBuffer sb = new StringBuffer();
            sb.append(boundaryPrefix+boundary+newLine);
            // 文件参数,photo参数名可以随意修改
            sb.append("Content-Disposition:form-data;name=\"photo\";filename=\""+fileName+"\""+newLine);
            sb.append("Content-Type:application/octet-stream");
            // 参数头设置完以后需要两个换行，然后才是参数内容
            sb.append(newLine).append(newLine);
            out.write(sb.toString().getBytes());// 将参数头的数据写入到输出流中

            File file = new File(fileName);
            // 数据输入流,用于读取文件数据
            DataInputStream in = new DataInputStream(new FileInputStream(file));
            byte[] bufferOut = new byte[1024];
            int bytes = 0;
            while ((bytes = in.read(bufferOut)) != -1){
                out.write(bufferOut,0,bytes);
            }
            // 最后添加换行
            out.write(newLine.getBytes());
            in.close();
            // 定义最后数据分隔线，即--加上BOUNDARY再加上--
            byte[] end_data =  (newLine + boundaryPrefix + boundary + boundaryPrefix + newLine).getBytes();
            out.write(end_data);// 写上结尾标识
            out.flush();
            out.close();

        } catch (Exception e) {
            System.out.println("post请求出现异常！"+e);
            e.printStackTrace();
        }
    }

    public static void main(String[] args){
        String fileName="D:\\test\\hello.txt";
        uploadFile(fileName);
    }
}
