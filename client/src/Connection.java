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

    /* 소켓 및 입출력 버퍼 */
    private Socket socket;
    private BufferedReader receiver;
    private PrintWriter sender;

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

    // 서버와 연결된 TCP 소켓은 하나밖에 없으므로, 다른 쓰레드에서 동시에 요청을 보내거나
    // 응답을 기다리고 있는데 요청이 들어오면 Queue에 저장.
    private Queue<byte[]> sendQueue = new LinkedList<>();







}