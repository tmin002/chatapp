package kr.gagaotalk.server;

import com.google.gson.Gson;
import kr.gagaotalk.core.Action;
import kr.gagaotalk.core.Constants;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Map;

public class PacketParse {

    // 받은 패킷 데이터를 Received 객체로 변환.
    // Received 객체에서 JSON 스트링 확인할 수 있고, Map 객체로 변환된 JSON 데이터 확인 가능.
    public static Received parseReceivedData(byte[] receivedData) {
        // initialize
        Action receivedAction;

        // header check
        byte[] receivedHeader = new byte[10];
        System.arraycopy(receivedData, 0, receivedHeader, 0, 10);
        if (!(Arrays.equals(receivedHeader, Constants.HEADER_STRING_BYTES))) {
            // wrong header; ignore.
            return null;
        }

        // session ID parse
        byte[] sessionID = new byte[16];
        System.arraycopy(receivedData, 10, sessionID, 0, 16);

        // Action parse
        byte[] receivedActionBytes = new byte[8];
        System.arraycopy(receivedData, 26, receivedActionBytes, 0, 8);
        try {
            receivedAction = Action.valueOf(new String(receivedActionBytes));
        } catch (IllegalArgumentException e) {
            // TODO: make err message
            return null;
        }

        // data parse
        byte[] dataSection = new byte[2048];
        System.arraycopy(receivedData, 34, dataSection, 0, 2048);

        // return data
        return new Received(sessionID, receivedAction, dataSection);
    }

    // 클라이언트로 답장을 보낼때 필요한 데이터 byte 배열을 만드는 함수.
    // 데이터 크기, status code, action 및 Map 형태의 데이터 필요함.
    // Map 형태의 데이터는 JSON 형태로 변환되어 저장됨.
    public static byte[] constructSendBytes(int byteSize, byte statusCode, Action action, Map<String, Object> data) {
       Gson gson = new Gson();
       byte[] bytes = new byte[byteSize];

        // Header
        System.arraycopy(Constants.HEADER_STRING_BYTES, 0, bytes, 0, 10);
        // Status code
        bytes[10] = statusCode;
        // action
        System.arraycopy(action.getBytes(), 0, bytes, 11, 8);
        // data
        System.arraycopy(gson.toJson(data).getBytes(StandardCharsets.UTF_8), 0, bytes, 19, 2048);

        return bytes;
    }


}
