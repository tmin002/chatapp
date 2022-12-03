package kr.gagaotalk.client.chat;

import kr.gagaotalk.client.connection.Received;

public interface InvitationListener {
    void onInvitation(Received r);
}
