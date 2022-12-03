package kr.gagaotalk.client.chat;

import kr.gagaotalk.client.User;
import kr.gagaotalk.client.connection.Connection;
import kr.gagaotalk.client.connection.Received;
import kr.gagaotalk.core.Action;
import kr.gagaotalk.core.Constants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static kr.gagaotalk.core.Action.*;

public class Chatroom {
    public final String chatRoomID;
    public final String chatRoomName;
    public final int chatRoomPeopleCount;


    public Chatroom(String chatRoomID, String chatRoomName, int chatRoomPeopleCount) {
        this.chatRoomID = chatRoomID;
        this.chatRoomName = chatRoomName;
        this.chatRoomPeopleCount = chatRoomPeopleCount;
    }

    // static

    // get chat rooms: get list of chat room IDs user is in.
    // TODO: not certain the array type will be in simple array type. Needs further inspection
    public static ArrayList<Chatroom> getChatRooms() {
        Received rcv = Connection.communicate(getCtRms, Constants.EMPTY_MAP);
        if (rcv.statusCode != 0) {
            Map<String, Object> rcvData = rcv.dataToDictionary();
            ArrayList<Chatroom> chatroomList = new ArrayList<>();

            Object[] rcvChatList = (Object[]) rcvData.get("chatroom_list");
            for (Object chatroom : rcvChatList) {
                if (chatroom instanceof Map) {
                    Map<?, ?> chatroomMap = (Map<?, ?>) chatroom;
                    chatroomList.add(new Chatroom(
                            (String) chatroomMap.get("chatroom_id"),
                            (String) chatroomMap.get("chatroom_name"),
                            (Integer) chatroomMap.get("chatroom_people_count")
                    ));
                } else {
                    // TODO: this part should be unreachable.
                    System.out.println("getChatRooms() casting error maybe");
                }
            }
            return chatroomList;

        } else {
            // TODO: err handling.
            return null;
        }
    }

    // Make chat room. returns chatroom ID
    public static String makeChatRoom(String chatRoomName, ArrayList<User> chatRoomPeople) {
        Map<String, Object> request = new HashMap<>();
        request.put("chatroom_name", chatRoomName);

        ArrayList<String> userIDs = new ArrayList<>();
        for (User u : chatRoomPeople)
            userIDs.add(u.ID);
        request.put("chatroom_people", userIDs.toArray());

        Received rcv = Connection.communicate(Action.mkCtRm, request);
        if (rcv.statusCode != 0) {
            return (String) rcv.dataToDictionary().get("chatroom_id");
        } else {
            // TODO: err handling.
            return null;
        }
    }

    // Add people to chat room
    public static Received addToChatRoom(String chatRoomID, ArrayList<User> peopleToAdd) {
        Map<String, Object> request = new HashMap<>();
        request.put("chatroom_id", chatRoomID);

        ArrayList<String> userIDs = new ArrayList<>();
        for (User u : peopleToAdd)
            userIDs.add(u.ID);
        request.put("id_to_add", userIDs.toArray());

        return Connection.communicate(Action.addCtRm, request);
    }

    // Leave chat room
    public static Received leaveChatRoom(String chatRoomID) {
        Map<String, Object> request = new HashMap<>();
        request.put("chatroom_id", chatRoomID);
        return Connection.communicate(Action.lvCtRm, request);
    }
}
