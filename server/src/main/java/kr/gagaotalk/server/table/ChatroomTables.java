package kr.gagaotalk.server.table;

import java.sql.Connection;
import java.util.ArrayList;

// The table with entire chatroom in **someone**
// NOTE: chatroomID is int
public class ChatroomTables extends Table {
    public ChatroomTables(Connection con) { super(con); }
    public ChatroomTables(Connection con, String userID) { super(con, userID, schema, database); }

        public static String database = "chatrooms";
        public static String schema = "chatroomID int not null, primary key(chatroomID)";

        //getCtRms
        public ArrayList<String> getChatroomIDsInUser(){
        ArrayList<String> chatroomIDs = executeQueryArrayList( "select chatroomID from " + tableName + ";", 1);
        return chatroomIDs;
    }

    // return type??
    // ******************************
    public void addChatroomToUser(String chatroomID) {
        if(!ChatroomTable.chatroomTableGlobal.doesExistChatID(chatroomID)) // if chatroomID is not exists
        executeUpdate("insert into " + tableName + " values (" + chatroomID + ");");
    }

    public void deleteChatroomInUser(String chatroomID) {
        if(ChatroomTable.chatroomTableGlobal.doesExistChatID(chatroomID)) // if chatroomID is exists
        executeUpdate("delete from " + tableName + " where chatroomID = " + chatroomID + ";");
    }
}
