package kr.gagaotalk.client;
import kr.gagaotalk.core.UserBasic;

import java.util.Date;

public class User extends UserBasic {
    public User() {
        super("id", "nickname", "num", "bio", new Date());
    }
}
