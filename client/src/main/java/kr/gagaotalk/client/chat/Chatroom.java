package kr.gagaotalk.client.chat;

import kr.gagaotalk.client.User;
import kr.gagaotalk.client.authentication.Authentication;
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


    private Chatroom(String chatRoomID, String chatRoomName, int chatRoomPeopleCount) {
        this.chatRoomID = chatRoomID;
        this.chatRoomName = chatRoomName;
        this.chatRoomPeopleCount = chatRoomPeopleCount;
    }
    // static

    // Chatroom 객체를 생성하는 유일한 방법
    public static Chatroom createChatRoomInstance(String chatRoomID, String chatRoomName, int chatRoomPeopleCount) {
        Chatroom chatroom = new Chatroom(chatRoomID, chatRoomName, chatRoomPeopleCount);
        chatRoomDictionary.put(chatRoomID, chatroom);
        return chatroom;
    }

    // Chatroom 객체를 관리하는 곳.
    // Chatroom 객체는 생성자를 통해 public 하게 생성 불가능함. 생성된 Chatroom 객체는 반드시
    // 아래의 HashMap 에 일괄로 저장되어 관리된다.
    // key: chatroom ID, value: Chatroom 객체
    public static HashMap<String, Chatroom> chatRoomDictionary = new HashMap<>();
    public static Chatroom getChatRoomInstance(String chatRoomID) {
        return chatRoomDictionary.getOrDefault(chatRoomID, null);
    }

    // Make dummy chat room. only for debug purposes
    public static Chatroom createDummyChatRoom() {
        return new Chatroom("chatRoomID", "Chatroom Name", 10);
    }

    // get chat rooms: get list of chat room IDs user is in.
    // TODO: not certain the array type will be in simple array type. Needs further inspection

    @SuppressWarnings("unchecked")
    public static ArrayList<Chatroom> getChatRooms() {
        Received rcv = Connection.communicate(getCtRms, Constants.EMPTY_MAP);
        if (rcv.statusCode == 0) {
            Map<String, Object> rcvData = rcv.dataToDictionary();
            ArrayList<Chatroom> chatroomList = new ArrayList<>();

            ArrayList<Map<String, Object>> rcvChatList = (ArrayList<Map<String, Object>>) rcvData.get("chatroom_list");
            for (Map<String, Object> chatroom : rcvChatList) {
                    chatroomList.add(new Chatroom(
                            (String) chatroom.get("chatroom_id"),
                            (String) chatroom.get("chatroom_name"),
                            (Integer) chatroom.get("chatroom_people_count")
                    ));
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
        userIDs.add(Authentication.getCurrentUser().ID);
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
