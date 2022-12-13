package kr.gagaotalk.client.connection;

import com.google.gson.Gson;
import kr.gagaotalk.client.GagaoTalkClient;
import kr.gagaotalk.client.authentication.Authentication;
import kr.gagaotalk.core.Action;
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

    public static byte[] constructSendBytes(int byteSize, Action action, Map<String, Object> data) {
        Gson gson = new Gson();
        byte[] bytes = new byte[byteSize];

        // Header
        System.arraycopy(Constants.HEADER_STRING_BYTES, 0, bytes, 0, 10);
        // session ID
        System.arraycopy(Authentication.getSessionID(), 0, bytes, 10, 16);
        // action
        System.arraycopy(action.getBytes(), 0, bytes, 26, action.getBytes().length);
        // data
        byte[] gsonBytes = gson.toJson(data).getBytes(StandardCharsets.UTF_8);
        System.arraycopy(gsonBytes, 0, bytes, 34, gsonBytes.length);

        return bytes;
    }

    private static Received sendAndReceive(Socket givenSocket, Action action, Map<String, Object> data) {
        byte[] sendBuffer = constructSendBytes(4096, action, data);
        byte[] rcvBuffer = new byte[4096];

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

            // close socket if needed
            if (closeAfterConnection)
                socket.close();

            // return
            return parseReceivedData(rcvBuffer);

        } catch (IOException e) {
           // TODO: IOException handling
            return null;
        }
    }

    public static Received parseReceivedData(byte[] receivedData) {
        // initialize
        byte statusCode;
        Action receivedAction;

        // header check
        byte[] receivedHeader = new byte[10];
        System.arraycopy(receivedData, 0, receivedHeader, 0, 10);
        if (!(Arrays.equals(receivedHeader, Constants.HEADER_STRING_BYTES))) {
            // wrong header; ignore.
            return null;
        }

        // status code parse
        statusCode = receivedData[10];

        // action parse
        byte[] receivedActionBytes = new byte[8];
        System.arraycopy(receivedData, 11, receivedActionBytes, 0, 8);
        try {
            receivedAction = Action.valueOf(new String(receivedActionBytes, StandardCharsets.US_ASCII).trim());
        } catch (IllegalArgumentException e) {
            // TODO: make err message
            return null;
        }

        // data parse
        byte[] dataSection = new byte[2048];
        System.arraycopy(receivedData, 19, dataSection, 0, 2048);

        // return data
        return new Received(statusCode, receivedAction, dataSection);
    }
}
