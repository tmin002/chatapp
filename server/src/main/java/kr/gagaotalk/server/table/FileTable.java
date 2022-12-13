package kr.gagaotalk.server.table;

import java.sql.Connection;

public class FileTable extends Table {
    public FileTable(Connection con) { super(con); }
    public FileTable(Connection con, String tableName) { super(con, tableName, schema, database); }

    public static String schema = "fileID int not null, userID varchar(32) not null, fileAddress varchar(32) not null, primary key(fileID)";
    public static String database = "gagaotalkDB";


}
