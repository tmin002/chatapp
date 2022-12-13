package kr.gagaotalk.server;

import sun.security.x509.FreshestCRLExtension;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseEG {
    static Connection con = null;

    private static void makeConnection(String url, String userName, String password) throws ClassNotFoundException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(url, userName, password);
            System.out.println(con);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String args[]) throws ClassNotFoundException {
        /*System.out.println("Enter the SQL server password:");
        Scanner kb = new Scanner(System.in);
        String pw = kb.nextLine(); // or create password String variable*/
        String pw = "1234";
        makeConnection("jdbc:mysql://localhost", "root", pw);

        ChatroomTables chatroomInUser1 = new ChatroomTables(con, "user1");
        chatroomInUser1.makeTable();

        UserTable user1 = new UserTable(con, "userTable");
        user1.makeTable();
        System.out.println(user1.signup("user1", "ddong", "01012345678", "20021001", "1234"));
        System.out.println(user1.updatePassword("user", "1111", "1234"));
        System.out.println(user1.updateUserInfo("user1", "ddong", "20021001", "sss"));
        System.out.println(user1.getUserInfo("user"));
        //System.out.println(user1.findPW("user1", "12345678"));




    }
}
