package kr.gagaotalk.server.connection;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import kr.gagaotalk.core.Action;
import kr.gagaotalk.core.Constants;
import kr.gagaotalk.core.HexDataConvert;

import java.lang.reflect.Type;
import java.util.Map;

public class Received {
    public final byte[] sessionID;
    public final Action action;
    public final byte[] data;

    public Received(byte[] sessionID, Action action, byte[] data) {
        this.sessionID = sessionID;
        this.action = action;
        this.data = data;
    }

    public String sessionIDToString() {
        return HexDataConvert.BytesToHexString(sessionID).toUpperCase();
    }

    public String dataToString() {
        return new String(data);
    }
    public Map<String, Object> dataToDictionary() {
        Gson gson = new Gson();
        try {
            return gson.fromJson(dataToString(), Constants.JSON_PARSE_TYPE);
        } catch (JsonSyntaxException e) {
            // TODO: exception handling
            return null;
        }
    }

}
