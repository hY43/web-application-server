package webserver;

import java.net.ServerSocket;
import java.net.Socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WebServer {
    private static final Logger log = LoggerFactory.getLogger(WebServer.class);
    private static final int DEFAULT_PORT = 8080;

    public static void main(String args[]) throws Exception {
        int port = 0;
        if (args == null || args.length == 0) {
            // 파라미터를 통해 포트번호를 입력하지 않으면 기본으로 설정된 포트 번호(8080)로 초기화
        	port = DEFAULT_PORT;
        } else {
        	// 포트번호를 파라미터로 입력한다면 해당 포트 번호로 초기화
            port = Integer.parseInt(args[0]);
        }

        // 서버소켓을 생성한다. 웹서버는 기본적으로 8080번 포트를 사용한다.

        // 입력된 포트 번호로 소켓 객체를 생성하고, 해당 포트 번호로 접속이 들어오기를 기다린다.
        // listenSocket의 경우 클라이언트와 연결되면 이를 connectionSocket에게 돌려주는 중개자 역할을 수행한다.
        try (ServerSocket listenSocket = new ServerSocket(port)) {
            log.info("Web Application Server started {} port.", port);

            // 클라이언트가 연결될때까지 대기한다.
            Socket connection; // 연결되면 실제로 클라이언트와 통신할 소켓을 생성
            while ((connection = listenSocket.accept()) != null) {
                RequestHandler requestHandler = new RequestHandler(connection);
                requestHandler.start();
            }
        }
    }
}
