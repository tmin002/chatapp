package kr.gagaotalk.server;

import java.io.*;
import java.sql.Connection;
import java.sql.SQLException;

// The table with entire chatrooms in **server**
// NOTE: chatroomID is int
public class ChatroomTable extends Table {
    public ChatroomTable(Connection con) { super(con); }
    public ChatroomTable(Connection con, String tableName) { super(con, tableName, schema, database); }

    public static String schema = "chatroomID int not null, name varchar(32) not null, contentAddress varchar(32) not null, participantsAddress varchar(32) not null, primary key(ID)";
    public static String database = "gagaotalkDB";

    public int getNumberOfParticipants(String chatroomID) {
        StringBuilder countS = executeQuery("select count(*) from " + tableName + " where id = " + chatroomID + ";", 1);
        int count = Integer.parseInt(countS.toString());
        return count;
    }

    //non-finished
    //mkCtRm
    public String createChatroom() {

        return "";
    }

    //non-finished
    //addCtRm
    public String inviteUserToChatroom() {
        return "";
    }

    //non-finished
    //invCtRm (notification)
    public String invitedChatroom() {
        return "";
    }

    //non-finished
    public String leaveChatroom() {
        return "";
    }

    //non-finished
    public String sendMessage(String chatroomID, String content, String userID) {
        StringBuilder chatroomContentAddress = executeQuery("select contentAddress from " + tableName + " where chatroomID = " + chatroomID + ";", 1);
        File chatroomContentFile = new File(chatroomContentAddress.toString());
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(chatroomContentFile, true));
            // format : userID, date and time, content
            bw.write(userID + "" + content + '\n');
            bw.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return "";
    }



    // need to extract only the message part
    public StringBuilder getContents(String chatroomID) {
        StringBuilder contentsAddress = executeQuery("select contentAddress from " + tableName + " where id = " + chatroomID + ";", 1);
        StringBuilder content = new StringBuilder("");
        try {
            File addressFile = new File(contentsAddress.toString());
            BufferedReader reader = new BufferedReader(new FileReader(addressFile));

            String line = "";
            while((line = reader.readLine()) != null) {
                content.append(line + "\n");

                reader.close();
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return content;
    }


}
