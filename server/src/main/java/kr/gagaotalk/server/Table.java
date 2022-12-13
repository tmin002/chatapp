package kr.gagaotalk.server;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Table {
    Connection con = null;
    public String tableName;
    public String databaseName;
    public String schema;

    public int cnt;

    //just test
    Table() {
        this.tableName = "noName";
        this.schema = "noSchema";
        this.databaseName = "noDatabase";
    }
    //just test
    public Table(String tableName) {
        this.tableName = tableName;
        this.schema = "noSchema";
        this.databaseName = "noDatabase";
    }
    public Table(Connection con) {
        cnt = 1;
        this.con = con;
        this.tableName = "noName";
    }
    public Table(Connection con, String tableName, String schema, String databaseName) {
        this.con = con;
        this.tableName = tableName;
        this.schema = schema;
        this.databaseName = databaseName;
        cnt = getTableLength(tableName) + 1;
    }

    // use database or change database
    private void start() {
        Database database = new Database(con, databaseName);
        database.makeDB();
    }

    //get number of rows in table
    public int getTableLength(String tableTame) {
        try {
            if(!isTable(tableTame))
                return 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        ResultSet rs = executeQuery("select count(*) from " + tableTame + ";");
        int n = 1;
        try {
            if(rs.next()) {
                n = rs.getInt(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return n;
    }

    // return ResultSet variable just call with query that multiple row (e.g. select)
    public ResultSet executeQuery(String query) {
        start();
        ResultSet rs = null;
        try {
            Statement stmt = con.createStatement();
            rs = stmt.executeQuery(query);

        } catch(SQLException e) {
            e.printStackTrace();
        }
        return rs;
    }

    //return result with StringBuilder default delimiter(' ') / When the expected result is one row
    public StringBuilder executeQuery(String query, int column) {
        start();
        ResultSet rs = null;
        StringBuilder resultStringBuilder = new StringBuilder("");
        try {
            Statement stmt = con.createStatement();
            rs = stmt.executeQuery(query);
            while(rs.next()) {
                resultStringBuilder.append(rs.getString(1) + " ");
                cnt++;
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return resultStringBuilder;
    }

    //return result with StringBuilder custom delimiter / When the expected result is multiple rows
    public StringBuilder executeQuery(String query, int column, char delimiter) {
        start();
        ResultSet rs = null;
        StringBuilder resultStringBuilder = new StringBuilder("");
        try {
            Statement stmt = con.createStatement();
            rs = stmt.executeQuery(query);
            while(rs.next()) {
                resultStringBuilder.append(rs.getString(1) + delimiter);
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return resultStringBuilder;
    }

    // return ResultSet variable just call with query (e.g. create database)
    public int executeUpdate(String query) {
        start();
        int res = 0;
        try {
            Statement stmt = con.createStatement();
            res = stmt.executeUpdate(query);
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return res;
    }

    public boolean isTable(String name) throws SQLException {
        int cnt = 0;
        ResultSet rs = executeQuery("select * from information_schema.tables where TABLE_NAME='" + name + "';");
        while(rs.next()) {
            cnt++;
        }
        if(cnt >= 1) {
            System.out.println("ERROR: '" + name + "' does already exist");
            return true;
        }
        else {
            System.out.println("doesn't exist : " + name);
            return false;
        }
    }
    public void makeTable()   {
        try {
            if(!isTable(tableName)) {
                int createRow = executeUpdate("create table " + tableName + " (" + schema + ");");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void dropTable() throws SQLException {
        if(isTable(tableName))
            executeUpdate("drop table " + tableName + ";");
    }
}
