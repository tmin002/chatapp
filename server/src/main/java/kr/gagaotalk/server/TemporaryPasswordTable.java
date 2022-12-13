package kr.gagaotalk.server;

import java.sql.Connection;

public class TemporaryPasswordTable extends Table {
    public TemporaryPasswordTable(Connection con) { super(con); }
    public TemporaryPasswordTable(Connection con, String tableName) { super(con, tableName, schema, database); }

    // what is fit primary key ???
    public static String schema = "userID varchar(32) not null, tempPassword varchar(16) not null, primary key(userID)";
    public static String database = "gagaotalkDB";

    // ture : the password is already use, false : not use
    public boolean isUse(String password) {
        StringBuilder t = executeQuery("select exists (select * from " + tableName + " where tempPassword = '" + password + "') as success;", 1);
        return t.equals("1");
    }

    // some user forgot password and received temporary password
    public void insertNewTempPassword(String password, String userID) {
        executeUpdate("insert into " + tableName + " values ('" + userID + "', '" + password + "');");
    }

    // some user change password when login using temporary password (maybe difficult...)
    public void deleteTempPassword(String userID) {
        executeUpdate("delete from " + tableName + " where userID = '" + userID + "';");
    }


}
