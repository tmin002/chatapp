package kr.gagaotalk.server;

import kr.gagaotalk.core.DateConvert;
import kr.gagaotalk.core.UserBasic;

public class User extends UserBasic {
    public User(String userID, String nickname, String birthday, String bio) {
        super(userID, nickname, bio, DateConvert.StringToDate(birthday));
    }
}
