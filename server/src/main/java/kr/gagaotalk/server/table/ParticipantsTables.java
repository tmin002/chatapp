package kr.gagaotalk.server.table;

import java.sql.Connection;
import java.util.ArrayList;

public class ParticipantsTables extends Table {
    public ParticipantsTables(Connection con, String chatroomID) {
        super(con, chatroomID + "_participants", schema, database);
    }

    public static String database = "participants";
    public static String schema = "userID varchar(32), primary key (userID)";

    public boolean isParticipants(String userID) {
        StringBuilder t = executeQuery("select exists (select * from " + tableName + " where userID = '" + userID + "') as success;", 1);
        return t.toString().trim().equals("1");
    }

    public void addUsersToChatroom(ArrayList<String> participants) {
        for(String aUser : participants) {
            if(UserTable.userTableGlobal.doesExistID(aUser)) {
                executeUpdate("insert into " + tableName + " values ('" + aUser + "');");
            }
        }
    }

    public void deleteUserFromChatroom(String userID) {
        if(isParticipants(userID)) {
            executeUpdate("delete from " + tableName + " where userID = '" + userID + "';");
        }
    }

    public int getNumberOfParticipants() {
        StringBuilder counts = executeQuery("select count(*) from " + tableName + ";", 1);
        return Integer.parseInt(counts.toString());
    }

    public ArrayList<String> getParticipants() {
        ArrayList<String> participants = new ArrayList<>();
        participants = executeQueryArrayList("select userID from " + tableName + ";", 1);
        return participants;
    }
}
