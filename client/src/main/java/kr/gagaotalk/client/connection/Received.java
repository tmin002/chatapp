package kr.gagaotalk.client.connection;

import java.lang.reflect.Type;
import java.util.Map;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import kr.gagaotalk.core.Action;
import kr.gagaotalk.core.Constants;

public class Received {
    public final byte statusCode;
    public final Action action;
    public final byte[] data;

    public Received(byte statusCode, Action action, byte[] data) {
        this.statusCode = statusCode;
        this.action = action;
        this.data = data;
    }

    public String dataToString() {
        return new String(data).trim();
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

    public int getErrorCode() {
        return ((Double) dataToDictionary().get("error_code")).intValue();
    }
    public String getErrorMessage() {
        return (String) dataToDictionary().get("message");
    }
}
