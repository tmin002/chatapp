package kr.gagaotalk.server.table;

import java.sql.Connection;
import java.util.ArrayList;

public class FriendsTables extends Table {
    FriendsTables(Connection con) { super(con); }
    public FriendsTables(Connection con, String userID) {
        super(con, userID + "_friends", schema, database);
    }

    public static String database = "friends";
    public static String schema = "userID varchar(32) not null, primary key(userID)"; // or "id varchar(32) not null, state varchar(8), primary key(id)" state : invisible/ block etc.

    public boolean isFriend(String searchID) {
        StringBuilder countS = executeQuery("select exists ( select from " + tableName + " where userID = '" + searchID + "') as success;", 1);
        return countS.toString().equals("1");
    }

    public int getNumberOfFriends() {
        StringBuilder countS = executeQuery("select count(*) from " + tableName + ";", 1);
        int count = Integer.parseInt(countS.toString());
        return count;
    }

    public ArrayList<String> getFriends() {
        ArrayList<String> friends = executeQueryArrayList("select userID from " + tableName + ";", 1);
        return  friends;
    }

    public void addFriend(String someoneUserID) {
        executeUpdate("insert into " + tableName + " values ('" + someoneUserID + "');");
    }




}
