package kr.gagaotalk.client;
import kr.gagaotalk.core.Action;
import kr.gagaotalk.client.connection.Connection;
import kr.gagaotalk.client.connection.Received;
import kr.gagaotalk.core.Constants;
import kr.gagaotalk.core.DateConvert;
import kr.gagaotalk.core.UserBasic;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class User extends UserBasic {
    public User(String ID, String nickname, String bio, String birthday) {
        super(ID, nickname, bio, DateConvert.StringToDate(birthday));
    }

    // statics

    public static User getUserByID(String ID) {
        Map<String, Object> reqMap = new HashMap<>();
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

    public static ArrayList<User> getFriends() {
        Received rcv = Connection.communicate(Action.getFrens, Constants.EMPTY_MAP);
        if (rcv.statusCode != 0) {
            Map<String, Object> rcvData = rcv.dataToDictionary();
            ArrayList<User> friendsList = new ArrayList<>();

            Object[] rcvFriendList = (Object[]) rcvData.get("friends_list");
            for (Object friend : rcvFriendList) {
                if (friend instanceof Map) {
                    Map<?, ?> friendConverted = (Map<?, ?>) friend;
                    friendsList.add(new User(
                            (String) friendConverted.get("id"),
                            (String) friendConverted.get("nickname"),
                            (String) friendConverted.get("birthday"),
                            (String) friendConverted.get("bio")
                    ));
                } else {
                    // TODO: this part should be unreachable.
                    System.out.println("getChatRooms() casting error maybe");
                }
            }
            return friendsList;

        } else {
            // TODO: err handling.
            return null;
        }
    }

    public static Received updateUserInformation(String nickname, Date birthday, String bio) {
        Map<String, Object> request = new HashMap<>();
        request.put("nickname", nickname);
        request.put("birthday", DateConvert.DateToString(birthday));
        request.put("bio", bio);
        return Connection.communicate(Action.upUsrInf, request);
    }
}
