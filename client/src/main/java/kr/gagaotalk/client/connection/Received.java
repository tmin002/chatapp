package kr.gagaotalk.client.connection;

import java.lang.reflect.Type;
import java.util.Map;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

public class Received {
    public final byte statusCode;
    public final Action action;
    public final byte[] data;
    public final Type jsonType = new TypeToken<Map<String,Object>>(){}.getType();
    public final boolean isValid;

    public Received(byte statusCode, Action action, byte[] data) {
        this.statusCode = statusCode;
        this.action = action;
        this.data = data;
        this.isValid = true;
    }

    // Default constructor is used for invalid packets
    public Received() {
        this.statusCode = 0;
        this.action = null;
        this.data = "".getBytes();
        this.isValid = false;
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
}
