package kr.gagaotalk.server;

import java.sql.Connection;

// The table with entire chatroom in **someone**
public class ChatroomTables extends Table {
    public ChatroomTables(Connection con) { super(con); }
    public ChatroomTables(Connection con, String userID) { super(con, userID, schema, database); }

    public static String database = "chatrooms";
    public static String schema = "chatroomID int not null, primary key(chatroomID)";

    public StringBuilder getChatroomIDs(String userID) {
        StringBuilder chatroomIDs = executeQuery("select chatroomID from " + tableName + ";", 1, '\n');
        return chatroomIDs;
    }


}
