package kr.gagaotalk.client.connection;

import java.lang.reflect.Type;
import java.util.Map;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import kr.gagaotalk.core.Action;

public class Received {
    public final byte statusCode;
    public final Action action;
    public final byte[] data;
    private final Type jsonType = new TypeToken<Map<String,Object>>(){}.getType();

    public Received(byte statusCode, Action action, byte[] data) {
        this.statusCode = statusCode;
        this.action = action;
        this.data = data;
    }

    public String dataToString() {
        return new String(data);
    }
    public Map<String, Object> dataToDictionary() {
       Gson gson = new Gson();
       try {
           return gson.fromJson(dataToString(), jsonType);
       } catch (JsonSyntaxException e) {
           // TODO: exception handling
           return null;
       }
    }

    public int getErrorCode() {
        return (int) dataToDictionary().get("error_code");
    }
    public String getErrorMessage() {
        return (String) dataToDictionary().get("message");
    }
}
