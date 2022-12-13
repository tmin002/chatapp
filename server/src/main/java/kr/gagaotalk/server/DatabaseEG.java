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
        String pw = "wpj481796";
        //makeConnection("jdbc:mysql://localhost", "root", pw);

        UserTable test = new UserTable();
        System.out.println(test.signup("user1", "ddong", "01012345478", "20021001", "cfkdfkdkdkdkdk"));


    }
}
