package kr.gagaotalk.client;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class Connection {

    private static InetAddress serverAddress;
    private static int serverPort;

    private final static byte[] headerStringBytes = "gagaotalk!".getBytes(StandardCharsets.UTF_8);

    public static Received communicate(byte[] sessionID, Action action, byte[] data) {
        byte[] sendBuffer = new byte[4096];
        byte[] rcvBuffer = new byte[4096];

        byte statusCode = -1;
        Action receivedAction = null;
        byte[] receivedData = null;

        // Header
        System.arraycopy(headerStringBytes, 0, sendBuffer, 0, 10);
        // session ID
        System.arraycopy(sessionID, 0, sendBuffer, 10, 16);
        // action
        System.arraycopy(action.getBytes(), 0, sendBuffer, 26, 8);
        // data
        System.arraycopy(data, 0, sendBuffer, 34, 2048);

        try (Socket socket = new Socket(serverAddress, serverPort)) {
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
            if (!(Arrays.equals(receivedHeader, headerStringBytes))) {
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

        } catch (IOException e) {
           // TODO: IOException handling
        }

        if (statusCode == -1 || receivedAction == null) {
            return new Received();
        } else {
            return new Received(statusCode, receivedAction, receivedData);
        }
    }
}
