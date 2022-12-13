package kr.gagaotalk.server;

import java.sql.*;
import java.util.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.security.SecureRandom;

public class UserTable extends Table{
    public UserTable() { super(); } // just test
    public UserTable(Connection con) { super(con); }
    public UserTable(Connection con, String tableName) { super(con, tableName, schema, database); }
    public static String schema = "ID varchar(32) not null, password varchar(32) not null, nickname varchar(32), bio varchar(32), phoneNumber varchar(16) not null, birthday varchar(16) not null, primary key(ID)";
    public static String database = "gagaotalkDB";

    // true : already exist false : does not exist
    private boolean doesExistID(String userID) {
        StringBuilder t = executeQuery("select exists (select * from " + tableName + " where id = '" + userID + "') as success;", 1);
        return t.equals("1");
    }

    // need to test
    private boolean isValidBirthdayFormat(String birth) {
        boolean t = true;
        try {
            LocalDate localDate2 = LocalDate.parse("birth", DateTimeFormatter.ofPattern("yyyyMMdd"));
        } catch(DateTimeParseException e) {
            t = false;
        }
        return t;
    } // format : yyyymmdd

    private String getPW(String userID) {
        StringBuilder password = executeQuery("select password from " + tableName + " where id = '" + userID + "';", 1);
        return password.toString();
    }

    public String getRandomPassword(int temporaryPasswordSize) {
        char[] charSet = new char[] {
                '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
                'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
                '!', '@', '#', '$', '%', '^', '&' };

        StringBuilder sb = new StringBuilder();
        SecureRandom sr = new SecureRandom();
        sr.setSeed(new Date().getTime());

        int idx = 0;
        int len = charSet.length;
        for (int i = 0; i < temporaryPasswordSize; i++) {
            // idx = (int) (len * Math.random());
            idx = sr.nextInt(len);    // 강력한 난수를 발생시키기 위해 SecureRandom을 사용한다.
            sb.append(charSet[idx]);
        }

        return sb.toString();
    }

    public String login(String inputtedUserID, String inputtedPW) {
        StringBuilder password = executeQuery("select password from " + tableName + "userTable where id = '" + inputtedUserID + "';", 1);
        if(password.length() == 0) { // doesn't exist inputted ID in table
            return "1";
        }
        else {
            if(inputtedUserID.equals(password)) {
                // login success
                /* already logged in
                if()
                else
                */
            }
            else {
                return "1"; //wrong password

            }
        }
        return ""; // success
    }

    //non_finished
    public String logout() {
        return "";
    }

    public String signup(String userID, String nickname, String phoneNumber, String birth, String password) {
        // ********* need to add Error type : password security (8 over)
        if(doesExistID(userID)) { return "1"; } //already exist IDddddddd
        else {
            if(!isValidBirthdayFormat(birth)) { return "2"; } //invalid birthday
            if(nickname.isEmpty()) { return "3"; } //nickname is null
            // need to add checking phone number // not character
            if(password.isEmpty()) { return "5"; } //password is null
            // need to Check for duplicates ID
        }
        executeUpdate("insert into " + tableName + " values ('" + userID + "', '" + nickname + "', '" + phoneNumber + "', '" + birth + "', '" + password + "' );");
        return ""; // success
    }

    public String findID(String nickname, String birth) {
        StringBuilder id = executeQuery("select id from " + tableName + " where nickname = '" + nickname + "' and birthday = '" + birth + "';", 1);
        if(id.equals("")) { return "1"; } //does not exist
        return id.toString(); // return id
    }

    public String findPW(String userID, String phoneNumber) {

        if(!doesExistID(userID)) { return "2"; } // userID does not exist
        StringBuilder password = executeQuery("select password from " + tableName + " where id = '" + userID + "' and phoneNumber = '" + phoneNumber + "';", 1);
        if(password.equals("")) { return "1"; }
        // ******* return temporary password *******
        return password.toString(); // return password in String (success)
    }

    public String updateUserInfo(String userID, String nickname, String birth, String bio) {
        if(!isValidBirthdayFormat(birth)) { return "1"; }
        if(nickname.isEmpty()) { return "2"; }
        executeUpdate("update " + tableName + " set nickname = '" + nickname + "', birthday = '" + birth + "', bio = '" + bio + "' where id = '" + userID + "';");
        return "0"; //success
    }

    public String updatePassword(String userID, String inputtedCurrentPW, String newPW) {
        String curPW = getPW(userID);
        if(curPW.equals(inputtedCurrentPW)) { // if same
            executeUpdate("update " + tableName + " set password = '" + newPW + "' where id = '" + userID + "';");
            return "0"; // update success
        }
        else { return "1"; } // wrong password
    }

    public StringBuilder getUserInfo(String userID) {
        ResultSet userInfoResultSet = null;
        userInfoResultSet = executeQuery("select nickname, birthday, bio from " + tableName + " where id = '" + userID + "';");
    }

    // **** 아이디 입력하면 그 아이디 계정의 정보 return

    //session ID 16자리 String 16진법
}
