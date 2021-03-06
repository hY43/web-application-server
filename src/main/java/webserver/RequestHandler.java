package webserver;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.file.Files;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import util.SplitUtil;

public class RequestHandler extends Thread {
    private static final Logger log = LoggerFactory.getLogger(RequestHandler.class);

    private Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        log.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(), connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            // TODO 사용자 요청에 대한 처리는 이 곳에 구현하면 된다.
            DataOutputStream dos = new DataOutputStream(out);
            
            // hY43 한줄읽기를 위한 InputStreamReader와 BufferedReader 선언
            InputStreamReader isr = new InputStreamReader(in);
            BufferedReader br = new BufferedReader(isr);

            String line = br.readLine();
            if(line == null){
            	// Header가 null 이면 반화
            	return ;
            }
            
            // Header 내 URL 추출
            String[] tokens = SplitUtil.splitStr(line);
            String url = tokens[1];
            
            while(!"".equals(line = br.readLine())){
            	//
            	
            }            
            
            // 추출한 url을 통해 index 파일을 읽고, 이를 body에 전달한다.
            byte[] body = Files.readAllBytes(new File("./webapp" + url).toPath());
            //byte[] body ="haha success!!".getBytes();
            response200Header(dos, body.length);
            responseBody(dos, body);
            
        } catch (IOException e) {
            log.error(e.getMessage());
        } 
    }

    private void response200Header(DataOutputStream dos, int lengthOfBodyContent) {
        try {
            dos.writeBytes("HTTP/1.1 200 OK \r\n");
            dos.writeBytes("Content-Type: text/html;charset=utf-8\r\n");
            dos.writeBytes("Content-Length: " + lengthOfBodyContent + "\r\n");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    private void responseBody(DataOutputStream dos, byte[] body) {
        try {
            dos.write(body, 0, body.length);
            dos.flush();
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }
}
