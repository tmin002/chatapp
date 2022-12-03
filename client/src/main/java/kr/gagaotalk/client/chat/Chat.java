package kr.gagaotalk.client.chat;

import kr.gagaotalk.client.connection.Connection;
import kr.gagaotalk.client.connection.Received;
import kr.gagaotalk.core.Action;

import java.util.HashMap;
import java.util.Map;

public class Chat {

    // send chat: send chat to specific chat room
    // send text
    public static Received sendTextChat(String chatRoomID, String textContent) {
        Map<String, Object> request = new HashMap<>();
        request.put("chatroom_id", chatRoomID);
        request.put("message_type", "text");
        request.put("content", textContent);
        return Connection.communicate(Action.sendMsg, request);
    }

    // send chat: send chat to specific chat room
    // send file (requires file ID. file ID can be acquired by uploading file and receiving file ID.)
    public static Received sendFileChat(String chatRoomID, String fileName, String fileID) {
        Map<String, Object> request = new HashMap<>();
        request.put("chatroom_id", chatRoomID);
        request.put("message_type", "file");
        request.put("file_name", fileName);
        request.put("file_id", fileID);
        return Connection.communicate(Action.sendMsg, request);
    }

}
