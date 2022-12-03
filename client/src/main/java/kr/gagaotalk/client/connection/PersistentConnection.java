package kr.gagaotalk.client.connection;

import kr.gagaotalk.client.GagaoTalkClient;
import kr.gagaotalk.client.chat.ChatReceiveListener;
import kr.gagaotalk.client.chat.InvitationListener;
import kr.gagaotalk.core.Action;
import kr.gagaotalk.core.Constants;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class PersistentConnection {

    private static Socket socket = null;
    private static DataInputStream rcv = null;
    private static DataOutputStream send= null;
    private static final List<ChatReceiveListener> chatReceiveListeners = new ArrayList<>();
    private static final List<InvitationListener> invitationListeners = new ArrayList<>();
    private static Thread receiveThread = null;

    public static void addChatReceiveListener(ChatReceiveListener l) {
        chatReceiveListeners.add(l);
    }
    public static void addInvitationListener(InvitationListener l) {
        invitationListeners.add(l);
    }

    private static void initializeSocket() throws IOException {
        socket = new Socket(GagaoTalkClient.SERVER_ADDRESS, GagaoTalkClient.SERVER_PORT);
        rcv = new DataInputStream(socket.getInputStream());
        send = new DataOutputStream(socket.getOutputStream());
    }

    public static boolean connect() {
        // handshake
        Received handshakeReceived = Connection.communicate(socket, Action.hi, Constants.EMPTY_MAP);
        if (handshakeReceived.statusCode == 0) {
            // start receive thread
            receiveThread = new Thread(PersistentConnection::receiveThreadContent);
            receiveThread.start();
           return true;
        } else {
            return false;
        }
    }

    private static void receiveThreadContent() {
        while (true) {
            try {
                // receive buffer
                final byte[] rcvBuffer = new byte[4096];

                // wait until receive
                int result = rcv.read(rcvBuffer);
                if (result == 0) {
                    throw new IOException("Persistent socket: Connection with server terminated");
                }

                // parse data
                final Received rcvData = Connection.parseReceivedData(rcvBuffer);

                if (rcvData == null) {
                    // TODO: err happened. Received error handling required.
                    continue;
                }

                // do things
                if (rcvData.action.equals(Action.ruOnline)) {
                    // Server checking client online
                    send.write(Connection.constructSendBytes(1024, Action.ruOnline, Constants.EMPTY_MAP));
                    send.flush();

                } else if (rcvData.action.equals(Action.rcvMsg)) {
                    // Chat message received
                    // TODO: 그냥 Received 객체를 점겨주지 말자. EventArgs 나중에 만들자
                    String chatroomID = (String) rcvData.dataToDictionary().get("chatroom_id");

                    for (ChatReceiveListener l : chatReceiveListeners) {
                        if (l.getChatroomID().equals(chatroomID))
                            l.onChatReceive(rcvData);
                    }

                } else if (rcvData.action.equals(Action.invChtRm)) {
                    // User invited to chatroom
                    for (InvitationListener l : invitationListeners) {
                        l.onInvitation(rcvData);
                    }
                } else if (rcvData.action.equals(Action.bye)) {
                    // Server asserted disconnection
                    // TODO: terminate connection
                    System.out.println("Server send \"bye\": persistent socket");
                } else {
                    // Action not compatible with persistent connection socket.
                    // Ignore.
                    return;
                }

            } catch (IOException e) {
                // TODO: handle exception
                System.out.println("!!! IOException at persistent socket.");
                e.printStackTrace();
            }
        }
    }
}

