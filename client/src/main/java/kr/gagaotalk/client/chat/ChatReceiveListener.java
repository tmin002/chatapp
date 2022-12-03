package kr.gagaotalk.client.chat;

import kr.gagaotalk.client.connection.Received;

public interface ChatReceiveListener {
    String getChatroomID();
    void onChatReceive(Received r);
}
