package kr.gagaotalk.server;

import java.sql.Connection;

public class OnlineUserTable extends Table {
    public OnlineUserTable(Connection con) { super(con); }
    public OnlineUserTable(Connection con, String tableName) { super(con, tableName, schema, database); }

    public static String schema = "userID varchar(32) not null, primary key(userID)";
    public static String database = "gagaotalkDB";

    public void insertOnlineTableLoginUser(String userID) {
        executeUpdate("insert into " + tableName + " values ('" + userID + "');");
    }

    public boolean isOnline(String userID) {
        StringBuilder t = executeQuery("select exists (select * from " + tableName + " where id = '" + userID + "') as success;", 1);
        return t.equals("1");
    }


}
