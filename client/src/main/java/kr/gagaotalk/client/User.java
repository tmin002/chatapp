package kr.gagaotalk.client;
import kr.gagaotalk.client.gui.ResourceManager;
import kr.gagaotalk.client.gui.window.ImageIconResizer;
import kr.gagaotalk.core.Action;
import kr.gagaotalk.client.connection.Connection;
import kr.gagaotalk.client.connection.Received;
import kr.gagaotalk.core.Constants;
import kr.gagaotalk.core.DateConvert;
import kr.gagaotalk.core.UserBasic;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class User extends UserBasic {


    //// Member variables, methods
    public final ImageIcon userProfilePicture;
    // TODO: user profile pic download implementation
    private User(String ID, String nickname, String bio, String birthday, ImageIcon userProfilePicture) {
        super(ID, nickname, bio, DateConvert.StringToDate(birthday));
        this.userProfilePicture = userProfilePicture;
    }

    public User updateUserInformation(String nickname, Date birthDay, String bio) {
        Map<String, Object> request = new HashMap<>();
        request.put("nickname", nickname);
        request.put("birthday", DateConvert.DateToString(birthDay));
        request.put("bio", bio);

        Received received = Connection.communicate(Action.upUsrInf, request);
        if (received.statusCode != 0) {
            return null;
        } else {
            this.nickname = nickname;
            this.birthDay = birthDay;
            this.bio = bio;
            return this;
        }
    }

    //// static things

    public static User makeDummyUser() {
        // Only for debug purpose
        return new User("userid", "User Nickname", "This is user biography.", "20020226",
                ImageIconResizer.resize(ResourceManager.getImageIcon("/user_default_profile_pic.png")
                        , 50, 50));
    }
    public static boolean checkUserOnline(String userID) {
        Map<String, Object> request = new HashMap<>();
        request.put("user_id", userID);

        Received received = Connection.communicate(Action.chkUsrOn, request);
        if (received.statusCode != 0) {
           return false;
        }
        return (Boolean) received.dataToDictionary().get("user_online");
    }

    // User 객체를 생성하는 유일한 방법
    public static User createUserInstance(String ID, String nickname, String bio, String birthday) {
        // TODO: user profile pic
        User user = new User(ID, nickname, bio, birthday,
                ImageIconResizer.resize(
                        ResourceManager.getImageIcon("/user_default_profile_pic.png")
                , 50, 50));
        userDictionary.put("ID", user);
        return user;
    }

    // User 객체를 관리하는 곳.
    // User 객체는 생성자를 통해 public 하게 생성 불가능함. 생성된 User 객체는 반드시
    // 아래의 HashMap 에 일괄로 저장되어 관리된다.
    // key: user ID, value: User 객체
    public static HashMap<String, User> userDictionary = new HashMap<>();

    public static User getUserByID(String ID) {
        if (userDictionary.containsKey(ID)) {
            // UserDictionary 에 저장된 객체.
            return userDictionary.get("ID");
        }

        Map<String, Object> reqMap = new HashMap<>();
        reqMap.put("user_id", ID);

        Received received = Connection.communicate(Action.getUser, reqMap);
        if (received.statusCode != 0) {
            // TODO: Exception handling
            return null;
        } else {
            Map<String, Object> userMap = received.dataToDictionary();
            return createUserInstance(
                    (String) userMap.get("id"),
                    (String) userMap.get("nickname"),
                    (String) userMap.get("birthday"),
                    (String) userMap.get("bio"));
        }
    }

    @SuppressWarnings("unchecked")
    public static ArrayList<User> getFriends() {
        Received rcv = Connection.communicate(Action.getFrens, Constants.EMPTY_MAP);
        if (rcv.statusCode == 0) {
            Map<String, Object> rcvData = rcv.dataToDictionary();
            ArrayList<User> friendsList = new ArrayList<>();

            ArrayList<Object> rcvFriendList = (ArrayList<Object>) rcvData.get("friends_list");
            for (Object friend : rcvFriendList) {
                if (friend instanceof Map) {
                    Map<?, ?> friendConverted = (Map<?, ?>) friend;
                    friendsList.add(getUserByID((String) friendConverted.get("user_id")));
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

    public static Received addFriend(String friendID) {
        Map<String, Object> request = new HashMap<>();
        request.put("user_id", friendID);
       return Connection.communicate(Action.adFriend, request);
    }

}
