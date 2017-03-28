package com.zcl.work;

import com.zcl.work.parser.MspParser;
import com.zcl.work.request.UrlParameter;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/** 
 * 对于上传附件，只能支持一个附件 
 * **/  
public class MiniHttpServerMsp {  
    //服务器根目录  
    public static final String WEB_ROOT = "c:/root";  
    private static final int PORT = 8080;
    //线程池大小：10
    private static final int THREAD_POOL_SIZE = 10;
  
    public void startUp(){
    	try{
    		ServerSocket serverSocket = new ServerSocket(PORT);
	        System.out.println("HTTP服务器已经就绪，服务端口：" + PORT); 
	        ExecutorService threadPool = Executors.newFixedThreadPool(THREAD_POOL_SIZE);
	        while (true) {  
	            Socket socket = serverSocket.accept();
	            threadPool.execute(new Worker(socket));
	        }
    	} catch(Exception e){
    		e.printStackTrace();
    	}
    }
    
    public static void main(String[] args){  
    	MiniHttpServerMsp server = new MiniHttpServerMsp();  
        server.startUp();  
    }
    
    class Worker implements Runnable{
    	private Socket socket;
        //用户请求的文件的url  
        private String requestUrl;  
        //mltipart/form-data方式提交post的分隔符,   
        private String boundary = null;  
        //post提交请求的正文的长度  
        private int contentLength = 0;  
    	
    	public Worker(Socket socket){
    		this.socket = socket;
    	}

		public void run() {
			String threadInfo = "线程[" + Thread.currentThread().getId() + "] ";
            System.out.println( "开启" + threadInfo + "处理请求...");  
            DataInputStream reader;
			try {
				String[] req, param;
				String urlParameter = null;
				reader = new DataInputStream((socket.getInputStream()));
	            String line = reader.readLine();
				System.out.println("line="+line);
				req = line.split(" ");
	            
	            String method = req[0];
	            String url = req[1];
	            param = url.split("\\?");
	            if(param.length < 2){
	            	this.requestUrl = url; 
	            } else{
	            	this.requestUrl = param[0];
	            	urlParameter = param[1];
	            }
	            
	            OutputStream out = socket.getOutputStream();  
	             
	            if ("GET".equalsIgnoreCase(method)) {  
	                System.out.println(threadInfo + "请求类别：GET");
	                System.out.println(line);
	                if(null == urlParameter){
	                	this.doGet(reader, out);
	                } else{
	                	this.doGetMsp(urlParameter, reader, out);
	                }
	                  
	            } else if ("POST".equalsIgnoreCase(method)) {  
	                System.out.println(threadInfo + "请求类别：POST");
	                System.out.println(line);
	                this.doPost(reader, out);  
	            }  
	            socket.close();  
	            System.out.println(threadInfo + "请求处理完毕，关闭Socket.");  							
			} catch (Exception e) {
				e.printStackTrace();
			}  
		}

		
		private void doGetMsp(String urlParameter, DataInputStream reader, OutputStream out) throws Exception {
			String msp = null, parsedMsp = null;
			if (new File(WEB_ROOT + this.requestUrl).exists()) {
				UrlParameter param = new UrlParameter(urlParameter);

				// 从服务器根目录下找到用户请求的文件并发送回浏览器
				InputStream fileIn = new FileInputStream(WEB_ROOT + this.requestUrl);
				byte[] buf = new byte[fileIn.available()];
				fileIn.read(buf);
				msp = new String(buf);
				System.out.println("msp:"+msp);
				parsedMsp = MspParser.parse(param, msp);
				out.write(parsedMsp.getBytes());
				out.flush();
				out.close();
				fileIn.close();
				reader.close();
			}

		}

/*		public void run() {
			String threadInfo = "线程[" + Thread.currentThread().getId() + "] ";
            System.out.println( "开启" + threadInfo + "处理请求...");  
            DataInputStream reader;
			try {
				reader = new DataInputStream((socket.getInputStream()));
	            String line = reader.readLine();
	            
	            String method = line.substring(0, 4).trim();  
	            OutputStream out = socket.getOutputStream();  
	            this.requestUrl = line.split(" ")[1];  
	            if ("GET".equalsIgnoreCase(method)) {  
	                System.out.println(threadInfo + "请求类别：GET");
	                System.out.println(line);
	                this.doGet(reader, out);  
	            } else if ("POST".equalsIgnoreCase(method)) {  
	                System.out.println(threadInfo + "请求类别：POST");
	                System.out.println(line);
	                this.doPost(reader, out);  
	            }  
	            socket.close();  
	            System.out.println(threadInfo + "请求处理完毕，关闭Socket.");  							
			} catch (Exception e) {
				e.printStackTrace();
			}  
		}
		*/
		//处理GET请求  
	    private void doGet(DataInputStream reader, OutputStream out) throws Exception {  
	        if (new File(WEB_ROOT + this.requestUrl).exists()) {  
	            //从服务器根目录下找到用户请求的文件并发送回浏览器  
	            InputStream fileIn = new FileInputStream(WEB_ROOT + this.requestUrl);  
	            byte[] buf = new byte[fileIn.available()];  
	            fileIn.read(buf);  
	            out.write(buf); 
	            out.flush();  
	            out.close();  
	            fileIn.close();  
	            reader.close(); 
	        }  
	    }
	    
	    //处理POST请求  
		private void doPost(DataInputStream reader, OutputStream out) throws Exception {  
	        String line = reader.readLine();  
	        while (line != null) {  
	            System.out.println(line);  
	            if ("".equals(line)) {  
	                break;  
	            } else if (line.indexOf("Content-Length") != -1) {  
	                this.contentLength = Integer.parseInt(line.substring(line.indexOf("Content-Length") + 16));  
	            }  
	            //表明要上传附件， 跳转到doMultiPart方法。  
	            else if(line.indexOf("multipart/form-data")!= -1){  
	                //得multiltipart的分隔符  
	                this.boundary = line.substring(line.indexOf("boundary") + 9);  
	                this.doMultiPart(reader, out);  
	                return;  
	            }
	            line = reader.readLine(); 
	        }  
	        //继续读取普通post（没有附件）提交的数据  
	        String dataLine = null;  
	        //用户发送的post数据正文  
	        byte[] buf = {};  
	        int size = 0;  
	        if (this.contentLength != 0) {  
	            buf = new byte[this.contentLength];  
	            while(size<this.contentLength){  
	                int c = reader.read();  
	                buf[size++] = (byte)c;  
	                  
	            }  
	            System.out.println("收到的POST信息为: " + new String(buf, 0, size));  
	        }  
	        String response = "<html><head><title>Mini Http Server</title></head><body><p>Post is finished successfully:</p>" + new String(buf, 0, size) + "</body></html>";  
	        System.out.println("返回信息：");
	        System.out.println(response);  
	        out.write(response.getBytes());  
	        out.flush(); 
	        out.close();
	        reader.close();  
	    }
	    
	    //处理附件  
	    private void doMultiPart(DataInputStream reader, OutputStream out) throws Exception {  
	        String line = reader.readLine();  
	        while (line != null) {  
	            System.out.println(line);  
	              
	            if ("".equals(line)) {  
	                break;  
	            } else if (line.indexOf("Content-Length") != -1) {  
	                this.contentLength = Integer.parseInt(line.substring(line.indexOf("Content-Length") + 16));  
	                System.out.println("contentLength: " + this.contentLength);  
	            } else if (line.indexOf("boundary") != -1) {  
	                //获取multipart分隔符  
	                this.boundary = line.substring(line.indexOf("boundary") + 9);  
	            }
	            line = reader.readLine();
	        }  
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
	                size = reader.read(buf, totalRead, this.contentLength - totalRead);  
	                totalRead += size;  
	            }  
	            //用buf构造一个字符串，可以用字符串方便的计算出附件所在的位置  
	            String dataString = new String(buf, 0, totalRead,"UTF-8");  
	            System.out.println(dataString);
	            int pos = dataString.indexOf(boundary);  
	            //以下略过4行就是第一个附件的位置  
	            pos = dataString.indexOf("\n", pos) + 1;  
	            pos = dataString.indexOf("\n", pos) + 1;  
	            pos = dataString.indexOf("\n", pos) + 1;  
	            pos = dataString.indexOf("\n", pos) + 1;  
	            //附件开始位置  
	            int start = dataString.substring(0, pos).getBytes("UTF-8").length;
	            //结束标志尺寸(包含回车换行和前后的“--”字符)
	            int tailSize = boundary.length() + 8; 
	            //附件结束位置  
	            int end = this.contentLength - tailSize;
	            //取出filename  
	            int fileNameBegin = dataString.indexOf("filename") + 10;  
	            int fileNameEnd = dataString.indexOf("\"\r\n", fileNameBegin);  
	            String fileName = dataString.substring(fileNameBegin, fileNameEnd);
	            //将上传文件保存到WEB_ROOT目录
	            OutputStream fileOut = new FileOutputStream(WEB_ROOT + "/" + fileName);  
	            fileOut.write(buf, start, end-start);  
	            fileOut.close();  
	            fileOut.close();  
	        }  
	        String response = "<html><head><title>Mini Http Server</title></head><body><p>File upload is finished Successfully!</p></body></html>";
	        System.out.println("返回信息：");
	        System.out.println(response);  
	        //out.write(responseHead.getBytes());
	        out.write(response.getBytes());  
	        out.flush();
	        out.close();
	        reader.close(); 
	    }		
    }
}  
