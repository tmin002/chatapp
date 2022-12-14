package kr.gagaotalk.server.connection;

import kr.gagaotalk.core.Action;
import kr.gagaotalk.core.Constants;
import kr.gagaotalk.server.GagaoTalkServer;
import kr.gagaotalk.server.User;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PersistentConnection {

    // persistent socket 관리. key: userID, value: user 당 persistent connection
    public static Map<String, PersistentConnection> persistentConnectionMap = new HashMap<>();

    private Socket socket = null;
    private DataInputStream rcv = null;
    private DataOutputStream send= null;
    private Thread receiveThread = null;
    private String userID = null;

    public PersistentConnection(Socket socket, String userID) {
        try {
            this.userID = userID;
            initializeSocket(socket);
        } catch (IOException e) {
            System.out.println("!! Error while persistent socket init.");
            e.printStackTrace();
            return;
        }
        persistentConnectionMap.put(userID, this);
    }

    private void initializeSocket(Socket socket) throws IOException {
        this.socket = socket;
        this.receiveThread = new Thread(this::receiveContent);
        this.receiveThread.start();
        rcv = new DataInputStream(socket.getInputStream());
        send = new DataOutputStream(socket.getOutputStream());

        // handshake finish
        send.write(PacketParse.constructSendBytes( 4096, (byte) 0,  Action.hi, Constants.EMPTY_MAP));
        send.flush();
    }

    private void receiveContent() {
        while (true) {
            try {
                // read
                byte[] rcvBuffer = new byte[4096];
                if (rcv.read(rcvBuffer) == 0) {
                    System.out.println("!! NULL received from client at persistent socket.");
                    return;
                }

                // parse
                Received received = PacketParse.parseReceivedData(rcvBuffer);
                if (received == null) {
                    System.out.println("!! Something wrong at parsing response at persistent socket.");
                    continue;
                }

                // do thing
                if (received.action == Action.bye) {
                    send.write(PacketParse.constructSendBytes( 4096, (byte) 0,  Action.bye, Constants.EMPTY_MAP));
                    send.flush();
                    socket.close();
                    return;
                }

            } catch (IOException e) {
                System.out.println("!! IOException from client at persistent socket.");
            }
        }
    }

    public void onUserTextChatReceive(String chatRoomID, String senderID, String content) {
        Map<String, Object> sendMap = new HashMap<>();
        sendMap.put("chatroom_id", chatRoomID);
        sendMap.put("user_id", userID);
        sendMap.put("message_type", "text");
        sendMap.put("content", content);
        try {
            send.write(PacketParse.constructSendBytes( 4096, (byte) 0,  Action.rcvMsg, sendMap));
            send.flush();
        } catch (IOException e) {
            System.out.println("!! IOException while onUserTextChatReceive()");
        }
    }
    public void onUserFileChatReceive(String chatRoomID, String senderID, String fileName, String fileID) {
        Map<String, Object> sendMap = new HashMap<>();
        sendMap.put("chatroom_id", chatRoomID);
        sendMap.put("user_id", userID);
        sendMap.put("message_type", "file");
        sendMap.put("file_name", fileName);
        sendMap.put("file_id", fileID);
        try {
            send.write(PacketParse.constructSendBytes( 4096, (byte) 0,  Action.rcvMsg, sendMap));
            send.flush();
        } catch (IOException e) {
            System.out.println("!! IOException while onUserFileChatReceive()");
        }
    }

    public void onUserInvited(String chatRoomID) {
        Map<String, Object> sendMap = new HashMap<>();
        sendMap.put("chatroom_id", chatRoomID);
        try {
            send.write(PacketParse.constructSendBytes( 4096, (byte) 0,  Action.rcvMsg, sendMap));
            send.flush();
        } catch (IOException e) {
            System.out.println("!! IOException while onUserInvited()");
        }
    }

}
