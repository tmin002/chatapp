package kr.gagaotalk.server.table;

import kr.gagaotalk.server.DatabaseEG;
import kr.gagaotalk.server.ErrorInProcessingException;
import kr.gagaotalk.server.User;
import kr.gagaotalk.server.table.Table;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.security.SecureRandom;

public class UserTable extends Table {

    // static
    public static UserTable userTableGlobal;
    public static void initializeUserTableGlobal() {
        userTableGlobal = new UserTable(DatabaseEG.con, "UserTable");
        userTableGlobal.makeTable();
    }

    public UserTable(Connection con, String tableName) { super(con, tableName, schema, database); }
    public static String schema = "ID varchar(32) not null, password varchar(32) not null, nickname varchar(32), bio varchar(32), phoneNumber varchar(16) not null, birthday varchar(16) not null, primary key(ID)";
    public static String database = "gagaotalkDB";

    // test result : no problem
    // true : already exist false : does not exist
    public boolean doesExistID(String userID) {
        StringBuilder t = executeQuery("select exists (select * from " + tableName + " where id = '" + userID + "') as success;", 1);
        return t.toString().trim().equals("1");
    }

    // test result : no problem
    private boolean isValidBirthdayFormat(String birth) {
        boolean t = true;
        try {
            SimpleDateFormat dtFormat = new SimpleDateFormat("yyyyMMdd");
            dtFormat.setLenient(false);
            Date formatDate = dtFormat.parse(birth);
            //LocalDate localDate2 = LocalDate.parse("birth", DateTimeFormatter.ofPattern("yyyyMMdd"));
        } catch(ParseException e) {
            t = false;
        }
        return t;
    } // format : yyyymmdd

    // test result : no problem
    private String getPW(String userID) {
        StringBuilder password = executeQuery("select password from " + tableName + " where id = '" + userID + "';", 1);
        return password.toString();
    }

    // test result : no problem
    private String getRandomPassword(int temporaryPasswordSize) {
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
            idx = sr.nextInt(len);
            sb.append(charSet[idx]);
        }

        return sb.toString();
    }

    //test result : no problem
    public void login(String inputtedUserID, String inputtedPW) throws ErrorInProcessingException {
        StringBuilder password = executeQuery("select password from " + tableName + " where id = '" + inputtedUserID + "';", 1);
        if(password.length() == 0) {
            throw new ErrorInProcessingException(1, "doesn't exist inputted ID in table");
        }
        else {
            if(inputtedPW.equals(password.toString().trim())) {
                // login success
                OnlineUserTable onlineUserTable = new OnlineUserTable(con, "onlineUserTable");
                if(onlineUserTable.isOnline(inputtedUserID)) {
                    throw new ErrorInProcessingException(2, " already online");
                }
                else {
                    onlineUserTable.insertOnlineTableLoginUser(inputtedUserID); // insert this account into onlineTable
                }

            }
            else {
                throw new ErrorInProcessingException(1, "wrong password");
            }
        }
    }


    public void logout(String userID) {
        OnlineUserTable onlineUserTable = new OnlineUserTable(con, "onlineUserTable");
        if(onlineUserTable.isOnline(userID)) { // already online
            onlineUserTable.deleteOnlineTableLogoutUser(userID);
        }
    }

    // phone number format : no hyphen
    // test check no problem
    public void signup(String userID, String nickname, String phoneNumber, String birth, String password) throws ErrorInProcessingException {
        // ********* need to add Error type : password security (8 over)
        if(doesExistID(userID)) { throw new ErrorInProcessingException(1, "already exist ID"); } //
        else {
            if(!isValidBirthdayFormat(birth)) { throw new ErrorInProcessingException(2, "invalid birthday"); }
            if(nickname.isEmpty()) { throw new ErrorInProcessingException(3, "nickname is null"); } //
            if(phoneNumber.length() != 11) { throw new ErrorInProcessingException(4, "phone number length is not 11"); }
            try {
                int phone = Integer.parseInt(phoneNumber);
            } catch (NumberFormatException e) { throw new ErrorInProcessingException(4, "invalid phone number"); }
            if(password.isEmpty()) { throw new ErrorInProcessingException(5, "password is null"); } //
            executeUpdate("insert into " + tableName + " values ('" + userID + "', '" + password + "', '" + nickname + "', '', '" + phoneNumber + "', '" + birth + "' );");
            FriendsTables friendsTable = new FriendsTables(DatabaseEG.con, userID);
            friendsTable.makeTable();
        }
    }

    // test result : no problem
    // delete account
    public void unregister(String userID) throws ErrorInProcessingException {
        if(doesExistID(userID)) {
            executeUpdate("delete from " + tableName + " where id = '" + userID + "';");
        }
        else { throw new ErrorInProcessingException(1, "does not exist id"); }
    }

    public String findID(String nickname, String birth) throws ErrorInProcessingException {
        StringBuilder id = executeQuery("select id from " + tableName + " where nickname = '" + nickname + "' and birthday = '" + birth + "';", 1);
        if(id.toString().trim().equals("")) { throw new ErrorInProcessingException(1, "does not exist id"); }
        return id.toString(); // return id
    }


    public String findPW(String userID, String phoneNumber) throws ErrorInProcessingException {

        if(!doesExistID(userID)) { throw new ErrorInProcessingException(2, "does not exist id"); } // userID does not exist
        StringBuilder password = executeQuery("select password from " + tableName + " where id = '" + userID + "' and phoneNumber = '" + phoneNumber + "';", 1);
        if(password.toString().trim().equals("")) { throw new ErrorInProcessingException(1, "does not exist id"); }
        String tempPassword = getRandomPassword(12);
        executeUpdate("update " + tableName + " set password = '" + tempPassword + "' where id = '" + userID + "';");
        return password.toString(); // return password in String (success)
    }

    // test result : no problem
    public void updateUserInfo(String userID, String nickname, String birth, String bio) throws ErrorInProcessingException {
        if(!isValidBirthdayFormat(birth)) { throw new ErrorInProcessingException(1, "invaild birthday"); }
        if(nickname.isEmpty()) { throw new ErrorInProcessingException(2, "nickname is null"); }
        executeUpdate("update " + tableName + " set nickname = '" + nickname + "', birthday = '" + birth + "', bio = '" + bio + "' where id = '" + userID + "';");

    }

    // test result : no problem
    public void updatePassword(String userID, String inputtedCurrentPW, String newPW) throws ErrorInProcessingException {
        String curPW = getPW(userID);
        if(curPW.trim().equals(inputtedCurrentPW)) { // if same
            executeUpdate("update " + tableName + " set password = '" + newPW + "' where id = '" + userID + "';");
        }
        else { throw new ErrorInProcessingException(1, "wrong password"); } // wrong password
    }

    // test result : no problem
    //NOTE: order is userID, nickname, birthday, bio
    public User getUserInfo(String userID) throws ErrorInProcessingException {
        if(doesExistID(userID)) {
            throw new ErrorInProcessingException(1, "ID not found");
        }
        ResultSet userInfoResultSet = null;
        userInfoResultSet = executeQuery("select nickname, birthday, bio from " + tableName + " where id = '" + userID + "';");

        try {
            if(userInfoResultSet.next()) {
                return new User(userID,
                        userInfoResultSet.getString(1),
                        userInfoResultSet.getString(2),
                        userInfoResultSet.getString(3));
                //e.g. user11\nddong\n20021001\n뿌직\n
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
}
