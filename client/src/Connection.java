import java.io.*;
import java.net.Socket;
import java.util.LinkedList;
import java.util.Queue;

public class Connection {

    /* static한 Connection 변수 하나 저장하는 곳 */
    private static Connection connection;
    public static Connection getConnection() {
        return connection;
    }
    public static void connect(String ip, int port) {
        connection = new Connection(ip, port);
    }

    /* 생성자 */
    public Connection(String ip, int port) {
        try {
            socket = new Socket(ip, port);
            receiver = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            sender = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);
        } catch (IOException e) {
            // TODO: 예외발생시 dialogue 표시 필요
        }

    }

    /* 서버와 통신하는 곳 */

}