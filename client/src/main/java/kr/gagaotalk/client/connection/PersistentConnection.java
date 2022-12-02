package kr.gagaotalk.client.connection;

import kr.gagaotalk.client.GagaoTalkClient;
import kr.gagaotalk.core.Constants;

import java.io.IOException;
import java.net.Socket;

public class PersistentConnection {

    private static Socket socket = null;

    private static void initializeSocket() throws IOException {
        socket = new Socket(GagaoTalkClient.SERVER_ADDRESS, GagaoTalkClient.SERVER_PORT);
    }

    public static boolean connect() {
        Received handshakeReceived = Connection.communicate(socket, Action.hi, Constants.EMPTY_MAP);
        return handshakeReceived.statusCode == 0;
    }
}
