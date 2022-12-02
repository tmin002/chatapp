package kr.gagaotalk.client.authentication;

import kr.gagaotalk.client.connection.Action;
import kr.gagaotalk.client.connection.Connection;
import kr.gagaotalk.client.connection.Received;
import kr.gagaotalk.core.HexDataConvert;

import java.util.HashMap;
import java.util.Map;

public class Authentication {

    // Session ID
    private static final byte[] defaultSessionID = new byte[] {0};
    private static byte[] sessionID = defaultSessionID;
    public static byte[] getSessionID() {
        return sessionID;
    }

    // Sign In : saves session ID.
    public static Received signIn(String ID, String password) {
        final Map<String, Object> request = new HashMap<>();
        request.put("id", ID);
        request.put("password", password);

        Received received = Connection.communicate(Action.signIn, request);
        if (received.statusCode == 0)
            sessionID = HexDataConvert.HexStringToBytes((String) received.dataToDictionary().get("session_id"));
        return received;
    }

    // Sign Out: resets session ID.
    public static Received signOut() {
        final Map<String, Object> request = new HashMap<>();
        Received received = Connection.communicate(Action.signOut, request);
        if (received.statusCode == 0)
            sessionID = defaultSessionID;
        return received;
    }
}
