package kr.gagaotalk.server.connection;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;
import kr.gagaotalk.core.Action;
import kr.gagaotalk.server.Database;
import kr.gagaotalk.server.DatabaseEG;
import kr.gagaotalk.server.ErrorInProcessingException;
import kr.gagaotalk.server.User;
import kr.gagaotalk.server.table.*;

// 한 클라이언트와 소켓과 소켓을 관리하는 쓰레드를 담고있는 객체
public class Connection implements Runnable {

    private Socket client;
    private String addr;
    private DataInputStream in;
    private DataOutputStream out;
    private Thread thread;

    // 생성시 소켓 정보를 가져오고, 쓰레드를 시작함
    public Connection(Socket client) {
        this.client = client;
        this.thread = new Thread(this);
        this.addr = client.getInetAddress().getHostAddress();
        thread.start();
    }

    public String getUserOfSessionID(String sessionID) throws ErrorInProcessingException {
        return OnlineUserTable.onlineUserTableGlobal.getUserIDInOnlineTable(sessionID);
    }

    @SuppressWarnings("unchecked")
    public void run() {
        boolean closeConnection = true;

        try {
            // 소켓 I/O 스트림 선언
            in = new DataInputStream(client.getInputStream());
            out = new DataOutputStream(client.getOutputStream());

            // receive
            byte[] rcvBuffer = new byte[4096];
            int result = in.read(rcvBuffer);
            if (result == 0) {
                // nothing received. maybe connection closed.
                throw new IOException("Connection with client terminated.");
            }

            // Create instance
            Received rcv = PacketParse.parseReceivedData(rcvBuffer);
            if (rcv == null) {
                throw new IOException("Connection with client got wrong.");
            }

            // Data sent from client parsed to Map
            Map<String, Object> rcvMap = rcv.dataToDictionary();
            System.out.println(rcv.dataToString());

            // Do different things based on actions
            Gson gson = new Gson();
            Map<String, Object> sendMap = new HashMap<>();
            byte statusCode = 0;

            // does not uses persistent socket
            try {
                switch (rcv.action) {
                    case signIn:
                        sendMap.put("session_id",
                        UserTable.userTableGlobal.login(
                                (String) rcvMap.get("id"),
                                (String) rcvMap.get("password")
                        ));
                        break;

                    case signUp:
                        UserTable.userTableGlobal.signup(
                                (String) rcvMap.get("id"),
                                (String) rcvMap.get("nickname"),
                                (String) rcvMap.get("phoneNumber"),
                                (String) rcvMap.get("birthday"),
                                (String) rcvMap.get("password")
                        );
                        break;

                    case findID:
                        String foundID = UserTable.userTableGlobal.findID(
                            (String) rcvMap.get("nickname"),
                            (String) rcvMap.get("birthday")
                        );
                        sendMap.put("user_id", foundID);
                        break;

                    case findPW:
                        String generatedTempPassword = UserTable.userTableGlobal.findPW(
                                (String) rcvMap.get("id"),
                                (String) rcvMap.get("phone_number")
                        );
                        sendMap.put("temporary_password", generatedTempPassword);
                        break;

                    case getUser:
                        User foundUser = UserTable.userTableGlobal.getUserInfo(
                                (String) rcvMap.get("user_id"));
                        sendMap.put("id", foundUser.ID);
                        sendMap.put("nickname", foundUser.nickname);
                        sendMap.put("birthday", foundUser.birthDay);
                        sendMap.put("bio", foundUser.bio);
                        break;

                    case chkUsrOn:
                        boolean isUserOnline = OnlineUserTable.onlineUserTableGlobal.
                                isOnline((String) rcvMap.get("user_id"));
                        sendMap.put("user_online", isUserOnline);
                        break;

                    case signOut:
                        UserTable.userTableGlobal.logout(getUserOfSessionID(rcv.sessionIDToString()));
                        break;

                    case upPW:
                        UserTable.userTableGlobal.updatePassword(
                                getUserOfSessionID(rcv.sessionIDToString()),
                                (String) rcvMap.get("previous_password"),
                                (String) rcvMap.get("new_password"));
                        break;

                    case getFrens:
                        ArrayList<String> friendsIDList = new FriendsTables(
                                DatabaseEG.con, getUserOfSessionID(rcv.sessionIDToString()))
                                .getFriends();

                        ArrayList<Map<String, Object>> userObjList = new ArrayList<>();
                        for (String friendID : friendsIDList) {
                            Map<String, Object> userObj = new HashMap<>();
                            User userIns = UserTable.userTableGlobal.getUserInfo(friendID);
                            userObj.put("id", userIns.ID);
                            userObj.put("birthday", userIns.birthDay);
                            userObj.put("nickname", userIns.nickname);
                            userObj.put("bio", userIns.bio);
                            userObjList.add(userObj);
                        }

                        sendMap.put("friends_list", userObjList);
                        break;

                    case unRegs:
                        UserTable.userTableGlobal.unregister(
                                getUserOfSessionID(rcv.sessionIDToString()));
                        break;

                    case getCtRms:
                        ArrayList<String> chatIDsList = new ChatroomTables(DatabaseEG.con, getUserOfSessionID(rcv.sessionIDToString()))
                                .getChatroomIDsInUser();
                        ArrayList<Map<String, Object>> resultArrayList = new ArrayList<>();

                        for (String id : chatIDsList) {
                            Map<String, Object> as = new HashMap<>();
                            as.put("chatroom_id", id);
                            as.put("chatroom_name", ChatroomTable.chatroomTableGlobal.getChatroomName(id));
                            as.put("chatroom_people_count", ChatroomTable.chatroomTableGlobal.getNumberOfParticipants(id));
                            resultArrayList.add(as);
                        }

                        sendMap.put("chatroom_list", resultArrayList);
                        break;

                    case upUsrInf:
                        UserTable.userTableGlobal.updateUserInfo(
                                getUserOfSessionID(rcv.sessionIDToString()),
                                (String) rcvMap.get("nickname"),
                                (String) rcvMap.get("birthday"),
                                (String) rcvMap.get("bio"));
                        break;

                    case mkCtRm:
                        ArrayList<String> userList =
                                new ArrayList<>((ArrayList<String>) rcvMap.get("chatroom_people"));
                        ChatroomTable.chatroomTableGlobal.createChatroom(userList,
                                (String) rcvMap.get("chatroom_name"));
                        break;

                    case addCtRm:
                        // 구현 안됨
                        break;

                    case lvCtRm:
                        // 구현 안됨
                        break;

                    case sendMsg:
                        if (!(new ParticipantsTables(DatabaseEG.con,
                                (String) rcvMap.get("chatroom_id"))
                                    .isParticipants(getUserOfSessionID(rcv.sessionIDToString())))) {
                              break;
                        }
                        if (rcvMap.get("message_type").equals("file")) {
                            PersistentConnection.persistentConnectionMap.get(
                                    getUserOfSessionID(rcv.sessionIDToString()))
                                        .onUserFileChatReceive(
                                                (String) rcvMap.get("chatroom_id"),
                                                (String) rcvMap.get("user_id"),
                                                (String) rcvMap.get("file_name"),
                                                (String) rcvMap.get("file_id"));
                        } else {
                            PersistentConnection.persistentConnectionMap.get(
                                    getUserOfSessionID(rcv.sessionIDToString()))
                                        .onUserTextChatReceive(
                                            (String) rcvMap.get("chatroom_id"),
                                            (String) rcvMap.get("user_id"),
                                            (String) rcvMap.get("content"));
                        }

                        break;

                    case downFile:
                        closeConnection = false;
                        // 구현 안됨
                        break;

                    case uplFile:
                        closeConnection = false;
                        // 구현 안됨
                        break;

                    case hi:
                        closeConnection = false;
                        new PersistentConnection(client, getUserOfSessionID(rcv.sessionIDToString()));
                        break;
                }
            } catch (ErrorInProcessingException e) {
                // Construct the error data
                if (e.statusCode == 1) {
                    sendMap.put("error_code", e.errorCode);
                    sendMap.put("message", e.errorMessage);
                    statusCode = 1;
                }
            }

            // finally, send data to user
            System.out.printf("** sending back: action=%s, map=%s", rcv.action, gson.toJson(sendMap));
            byte[] sendBuffer = PacketParse.constructSendBytes(4096, statusCode, rcv.action, sendMap);
            out.write(sendBuffer);
            out.flush();

        } catch (SocketException e) {
            // 소켓 연결이 끊어짐. 쓰레드 종료.
            System.out.printf("* Socket connection with %s closed, terminating thread.\n", addr);
        } catch (Exception e) {
            // 알수없는 예외가 발생함. 출력하고 쓰레드 종료.
            System.out.println("!! Exception: " + e + ", terminating thread.");
            e.printStackTrace();
        } finally {
            try {
                if (closeConnection)
                    client.close();
            } catch (IOException e) {
                // 알수없는 예외가 발생함. 출력하고 쓰레드 종료.
                System.out.println("!! Exception while closing connection: " + e + ", terminating thread.");
            }
        }
    }

}
