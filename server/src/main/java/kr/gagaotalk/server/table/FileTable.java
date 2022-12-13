package kr.gagaotalk.server.table;

import kr.gagaotalk.server.Database;
import kr.gagaotalk.server.DatabaseEG;

import javax.xml.crypto.Data;
import java.security.SecureRandom;
import java.sql.Connection;
import java.util.Date;

public class FileTable extends Table {
    public FileTable(Connection con) { super(con); }
    public FileTable(Connection con, String tableName) { super(con, tableName, schema, database); }

    public static String schema = "fileID int not null, fileName varchar(32) not null, userID varchar(32) not null, fileAddress varchar(32) not null, primary key(fileID)";
    public static String database = "gagaotalkDB";

    public static FileTable fileTableGlobal;
    public static void initializeFileTableGlobal() {
        fileTableGlobal = new FileTable(DatabaseEG.con, "FileTable");
        fileTableGlobal.makeTable();
    }

    private final int fileIDLength = 3;
    private final String fileAddress = "./database/file/";

    private boolean doesExistFileID(String fileID) {
        StringBuilder checkExists = executeQuery("select exists (select from " + tableName + " where fileID = '" + fileID + "') as success;", 1);
        return  checkExists.toString().trim().equals("1");
    }

    private String getRandomFileID() {
        char[] charSet = new char[] {
                '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
        };

        StringBuilder sb;
        do {
            sb = new StringBuilder();
            SecureRandom sr = new SecureRandom();
            sr.setSeed(new Date().getTime());

            int idx = 0;
            int len = charSet.length;
            for (int i = 0; i < fileIDLength; i++) {
                // idx = (int) (len * Math.random());
                idx = sr.nextInt(len);
                sb.append(charSet[idx]);
            }
        } while (doesExistFileID(sb.toString()));
        return sb.toString();
    }

    public void uploadFile(String fileName, String userID) {
        String fileID = getRandomFileID();
        //executeUpdate("insert into " + tableName + " values ('" + fileID + "', '" + fileName + "', '" + userID + "', '")
    }


}
