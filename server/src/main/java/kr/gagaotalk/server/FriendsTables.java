package kr.gagaotalk.server;

import javax.xml.crypto.Data;
import java.sql.Connection;

public class FriendsTables extends Table {
    FriendsTables(Connection con) { super(con); }
    FriendsTables(Connection con, String userID) { super(con, userID + "_friends", schema, database); }

    public static String database = "friends";
    public static String schema = "id varchar(32) not null, primary key(id)"; // or "id varchar(32) not null, state varchar(8), primary key(id)" state : invisible/ block etc.



    public boolean isFriend(String myID, String searchID) {
        StringBuilder countS = executeQuery("select exists ( select from " + tableName + " where id = '" + searchID + "') as success;", 1);
        return countS.equals("1");
    }

    public int getNumberOfFriends(String ID) {
        StringBuilder countS = executeQuery("select count(*) from " + tableName + ";", 1);
        int count = Integer.parseInt(countS.toString());
        return count;
    }

    public StringBuilder getFriends(String ID) {
        StringBuilder friends = executeQuery("select id from " + tableName + ";", 1, '\n');
        return friends;
    }




}
