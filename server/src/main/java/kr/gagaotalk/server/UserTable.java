package kr.gagaotalk.server;

import java.sql.Connection;

public class UserTable extends Table{
    public UserTable(Connection con) {
        super(con);
    }
    public UserTable(Connection con, String tableName) {
        super(con, tableName, schema, database);
    }
    public static String schema = "ID varchar(32) not null, password varchar(32) not null, nickname varchar(32), phoneNumber varchar(16) not null, birthday varchar(16) not null, bioprimary key(ID)";
    public static String database = "gagaotalkDB";

    // true : already exist false : does not exist
    private boolean doesExistID(String ID) {
        StringBuilder t = executeQuery("select exists (select * from " + tableName + " where id = '" + ID + "') as success;", 1);
        return t.equals("1");
    }

    // non-finished
    private boolean isValidBirthdayFormat(String birth) {
        return true;
    } // format : yyyymmdd

    private String getPW(String ID) {
        StringBuilder password = executeQuery("select password from " + tableName + " where id = '" + ID + "';", 1);
        return password.toString();
    }

    public String login(String inputtedID, String inputtedPW) {
        StringBuilder password = executeQuery("select password from " + tableName + "userTable where id = '" + inputtedID + "';", 1);
        if(password.length() == 0) { // doesn't exist inputted ID in table
            return "1";
        }
        else {
            if(inputtedID.equals(password)) {
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

    public String signup(String ID, String nickname, String phoneNumber, String birth, String password) {
        // ********* need to add Error type : password security (8 over)
        if(doesExistID(ID)) { return "1"; } //already exist IDddddddd
        else {
            if(!isValidBirthdayFormat(birth)) { return "2"; } //invalid birthday
            if(nickname.isEmpty()) { return "3"; } //nickname is null
            // need to add checking phone number // not character
            if(password.isEmpty()) { return "5"; } //password is null
            // need to Check for duplicates ID
        }
        executeUpdate("insert into " + tableName + " values ('" + ID + "', '" + nickname + "', '" + phoneNumber + "', '" + birth + "', '" + password + "' );");
        return ""; // success
    }

    public String findID(String nickname, String birth) {
        StringBuilder id = executeQuery("select id from " + tableName + " where nickname = '" + nickname + "' and birthday = '" + birth + "';", 1);
        if(id.equals("")) { return "1"; } //does not exist
        return id.toString(); // return id
    }

    public String findPW(String ID, String phoneNumber) {

        if(!doesExistID(ID)) { return "2"; } // need to add error type
        StringBuilder password = executeQuery("select password from " + tableName + " where id = '" + ID + "' and phoneNumber = '" + phoneNumber + "';", 1);
        if(password.equals("")) { return "1"; }
        // ******* return temporary password *******
        return password.toString(); // return password in String
    }

    public String updateUserInfo(String ID, String nickname, String birth) {
        if(!isValidBirthdayFormat(birth)) { return "1"; }
        if(nickname.isEmpty()) { return "2"; }
        executeUpdate("update " + tableName + " set nickname = '" + nickname + "', birthday = '" + birth + "' where id = '" + ID + "';");
        // update success
        return "";
    }

    public String updatePassword(String ID, String inputtedCurrentPW, String newPW) {
        String curPW = getPW(ID);
        if(curPW.equals(inputtedCurrentPW)) { // same
            executeUpdate("update " + tableName + " set password = '" + newPW + "' where id = '" + ID + "';");
            return ""; // update success
        }
        else { return "1"; } // wrong password
    }

    // **** 아이디 입력하면 그 아이디 계정의 정보 return

    //session ID 16자리 String 16진법
}
