package kr.gagaotalk.client.connection;

import com.google.gson.Gson;
import kr.gagaotalk.client.GagaoTalkClient;
import kr.gagaotalk.client.authentication.Authentication;
import kr.gagaotalk.core.Constants;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Map;

public class Connection {

    public static Received communicate(Action action, Map<String, Object> data) {
        return sendAndReceive(null, action, data);
    }
    public static Received communicate(Socket socket, Action action, Map<String, Object> data) {
        return sendAndReceive(socket, action, data);
    }

    private static Received sendAndReceive(Socket givenSocket, Action action, Map<String, Object> data) {
        byte[] sendBuffer = new byte[4096];
        byte[] rcvBuffer = new byte[4096];

        byte statusCode = -1;
        Action receivedAction;
        byte[] receivedData = null;

        Gson gson = new Gson();

        // Header
        System.arraycopy(Constants.HEADER_STRING_BYTES, 0, sendBuffer, 0, 10);
        // session ID
        System.arraycopy(Authentication.getSessionID(), 0, sendBuffer, 10, 16);
        // action
        System.arraycopy(action.getBytes(), 0, sendBuffer, 26, 8);
        // data
        System.arraycopy(gson.toJson(data).getBytes(StandardCharsets.UTF_8), 0, sendBuffer, 34, 2048);

        // determine persistent connection or not
        boolean closeAfterConnection = (givenSocket == null);

        // define socket
        try (Socket socket = givenSocket != null ?
             givenSocket : new Socket(GagaoTalkClient.SERVER_ADDRESS,GagaoTalkClient.SERVER_PORT))
        {
            // initialize
            DataInputStream rcv = new DataInputStream(socket.getInputStream());
            DataOutputStream send = new DataOutputStream(socket.getOutputStream());

            // send
            send.write(sendBuffer);
            send.flush();

            // receive
            int result = rcv.read(rcvBuffer);
            if (result == 0) {
                // nothing received. maybe connection closed.
                throw new IOException("Connection with server terminated.");
            }

            // header check
            byte[] receivedHeader = new byte[10];
            System.arraycopy(rcvBuffer, 0, receivedHeader, 0, 10);
            if (!(Arrays.equals(receivedHeader, Constants.HEADER_STRING_BYTES))) {
               // wrong header; ignore.
                return new Received();
            }

            // status code parse
            statusCode = rcvBuffer[10];

            // action parse
            byte[] receivedActionBytes = new byte[8];
            System.arraycopy(rcvBuffer, 11, receivedActionBytes, 0, 8);
            try {
                receivedAction = Action.valueOf(new String(receivedActionBytes));
            } catch (IllegalArgumentException e) {
                // TODO: make err message
                return new Received();
            }

            // data parse
            receivedData = new byte[2048];
            System.arraycopy(rcvBuffer, 19, receivedData, 0, 2048);

            // return data
            if (closeAfterConnection) {
                socket.close();
            }
            if (statusCode == -1) {
                // This part should be unreachable.
                return new Received();
            } else {
                return new Received(statusCode, receivedAction, receivedData);
            }
        } catch (IOException e) {
           // TODO: IOException handling
            return new Received();
        }
    }
}
