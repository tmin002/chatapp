package kr.gagaotalk.server.table;

import java.sql.Connection;

public class ProfileImgTable extends Table {
    public ProfileImgTable(Connection con) { super(con); }
    public ProfileImgTable(Connection con, String tableName) { super(con, tableName, schema, database); }

    public static String database = "gagaotalkDB";
    public static String schema = "userID varchar(32) not null, imageAddress varchar(32), primary key(ID)"; // 프사 파일을 files에 저장하기

    public String getAddress(String ID) {
        StringBuilder address = executeQuery("select address from " + tableName + " where userID = '" + ID + "';", 1);
        return address.toString();
    }

    public String updateImg(String ID, String address) {
        return "";
    }

}
