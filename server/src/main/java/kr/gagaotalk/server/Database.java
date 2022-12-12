package kr.gagaotalk.server;

import java.sql.*;

public class Database {
    Connection con = null;
    String databaseName;
    public Database(Connection con) {
        this.con = con;
    }
    public Database(Connection con, String databaseName) {
        this.con = con;
        this.databaseName = databaseName;
    }

    private ResultSet executeQuery(String query) {
        ResultSet rs = null;
        try {
            Statement stmt = con.createStatement();
            rs = stmt.executeQuery(query);

        } catch(SQLException e) {
            e.printStackTrace();
        }
        return rs;
    }

    private int executeUpdate(String query) {
        int res = 0;
        try {
            Statement stmt = con.createStatement();
            res = stmt.executeUpdate(query);
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return res;
    }

    private boolean isDatabase(String name) throws SQLException {
        int cnt = 0;
        ResultSet rs = executeQuery("select 1 from Information_schema.SCHEMATA where schema_name = '" + name + "';");
        while(rs.next()) {
            cnt++;
        }
        if(cnt >= 1) {
            System.out.println(name + " does already exist");
            return true;
        }
        else {
            System.out.println("doesn't exist : " + name);
            return false;
        }
    }

    public void makeDB() {
        try {
            if(!isDatabase(databaseName)) { // 없음
                int createRow = executeUpdate("create database " + databaseName + ";");
                int useRow = executeUpdate("use " + databaseName + ";");
            }
            else {
                int alreadyExistRow = executeUpdate("use " + databaseName + ";");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void dropDB() { executeUpdate("drop database " + databaseName + ";"); }
    public void dropDB(String name) {
        int dropRow = executeUpdate("drop database " + name + ";");
    }

}
