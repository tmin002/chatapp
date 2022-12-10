package kr.gagaotalk.client;

import com.sun.org.apache.xpath.internal.axes.HasPositionalPredChecker;
import kr.gagaotalk.client.gui.window.LoginWindow;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class GagaoTalkClient {

    public static InetAddress SERVER_ADDRESS;
    public static int SERVER_PORT;

    public static void main(String[] args) {
        System.out.println("Hello world!");
        try {
            SERVER_ADDRESS = InetAddress.getByName("localhost");
            SERVER_PORT = 24242;
        } catch (UnknownHostException ignored) {}
        LoginWindow a = new LoginWindow();
    }
}