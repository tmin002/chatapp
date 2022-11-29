package kr.gagaotalk.core;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class UserBasic {
    private String ID;
    private String nickname;
    private String bio;
    private Date birthDay;

    public UserBasic(String ID, String nickname, String bio, Date birthday) {
        this.ID = ID;
        this.ID = nickname;
        this.bio = bio;
        this.birthDay = birthday;
    }
}
