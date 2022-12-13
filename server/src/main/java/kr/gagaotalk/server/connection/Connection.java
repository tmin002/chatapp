package kr.gagaotalk.server.connection;

import java.io.*;
import java.net.*;
import com.google.gson.Gson;
import kr.gagaotalk.core.Action;

// 한 클라이언트와 소켓과 소켓을 관리하는 쓰레드를 담고있는 객체
public class Connection implements Runnable {

    private Socket client;
    private String addr;
    private DataInputStream in;
    private DataOutputStream out;
    private Thread thread;

    // 생성시 소켓 정보를 가져오고, 쓰레드를 시작함
    public Connection(Socket client) {
        this.client = client;
        this.thread = new Thread(this);
        this.addr = client.getInetAddress().getHostAddress();
        thread.start();
    }

    public void run() {

        try {
            // 소켓 I/O 스트림 선언
            in = new DataInputStream(client.getInputStream());
            out = new DataOutputStream(client.getOutputStream());

            // receive
            byte[] rcvBuffer = new byte[4096];
            int result = in.read(rcvBuffer);
            if (result == 0) {
                // nothing received. maybe connection closed.
                throw new IOException("Connection with client terminated.");
            }

            // Create instance
            Received rcv = PacketParse.parseReceivedData(rcvBuffer);
            if (rcv == null) {
                throw new IOException("Connection with client got wrong.");
            }

            // Do different things based on actions

            switch (rcv.action) {
                case signIn:
                    break;
                case signOut:
                    break;
                case findID:
                    break;
                case findPW:
                    break;
                case upPW:
                    break;
                case getFrens:
                    break;
                case getUser:
                    break;
                case getCtRms:
                    break;
                case upUsrInf:
                    break;
                case chkUsrOn:
                    break;
                case unRegs:
                    break;
                case mkCtRm:
                    break;
                case addCtRm:
                    break;
                case lvCtRm:
                    break;
                case sendMsg:
                    break;
                case downFile:
                    break;
                case uplFile:
                    break;
                case hi:
                    break;
            }

        } catch (SocketException e) {
            // 소켓 연결이 끊어짐. 쓰레드 종료.
            System.out.printf("* Socket connection with %s closed, terminating thread.\n", addr);
        } catch (Exception e) {
            // 알수없는 예외가 발생함. 출력하고 쓰레드 종료.
            System.out.println("!! Exception: " + e + ", terminating thread.");
        } finally {
            try {
                client.close();
            } catch (IOException e) {
                // 알수없는 예외가 발생함. 출력하고 쓰레드 종료.
                System.out.println("!! Exception while closing connection: " + e + ", terminating thread.");
            }
        }
    }

}
