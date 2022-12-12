package kr.gagaotalk.client;

import kr.gagaotalk.client.gui.window.MainWindow;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class GagaoTalkClient {

    public static InetAddress SERVER_ADDRESS;
    public static int SERVER_PORT;

    public static void main(String[] args) throws IOException {
        System.out.println("Hello world!");
        try {
            SERVER_ADDRESS = InetAddress.getByName("localhost");
            SERVER_PORT = 24242;
        } catch (UnknownHostException ignored) {}
        new MainWindow();
    }
}