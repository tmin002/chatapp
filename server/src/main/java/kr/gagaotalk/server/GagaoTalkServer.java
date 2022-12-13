package kr.gagaotalk.server;

import kr.gagaotalk.server.connection.Server;

import java.io.IOException;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;

public class GagaoTalkServer {
    public final static int SERVER_PORT = 24242;

    // Start server. main loop
    // 일단은 동기적으로 구현함. 따라서 강제종료하지 않는이상 종료되지 않는 함수
    public static void mainLoop() {
        ServerSocket server = null;

        try {
            server = new ServerSocket(GagaoTalkServer.SERVER_PORT);
            server.setReuseAddress(true);

            while (true) {
                Socket client = server.accept();

                System.out.println("* Connection attempt from "
                        + client.getInetAddress().getHostAddress());


            }
        }
        catch (BindException e) {
            System.out.printf("!! Failed to bind to port %d. Address is already in use.\n",
                    GagaoTalkServer.SERVER_PORT);
            System.exit(1);
        } catch (IOException e) {
            System.out.printf("!! IO Error while opening socket: %s\n", e.getMessage());
        }
    }
}
