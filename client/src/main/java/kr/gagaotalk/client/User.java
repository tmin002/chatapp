package kr.gagaotalk.client;
import kr.gagaotalk.client.connection.Action;
import kr.gagaotalk.client.connection.Connection;
import kr.gagaotalk.client.connection.Received;
import kr.gagaotalk.core.DateConvert;
import kr.gagaotalk.core.UserBasic;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class User extends UserBasic {
    public User(String ID, String nickname, String bio, String birthday) {
        super(ID, nickname, bio, DateConvert.StringToDate(birthday));
    }

    public static User getUserByID(String ID) {
        Map<String, Object> reqMap = new HashMap<String, Object>();
        reqMap.put("user_id", ID);

        Received received = Connection.communicate(Action.getUser, reqMap);
        if (received.statusCode != 0) {
            // TODO: Exception handling
            return null;
        } else {
            Map<String, Object> userMap = received.dataToDictionary();
            return new User(
                    (String) userMap.get("id"),
                    (String) userMap.get("nickname"),
                    (String) userMap.get("birthday"),
                    (String) userMap.get("bio"));
        }
    }
}
