package kr.gagaotalk.client.gui.window;

import kr.gagaotalk.client.chat.Chatroom;
import kr.gagaotalk.client.gui.component.ChatRoomListAtom;
import kr.gagaotalk.client.gui.panel.MainWindowPanel;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class ChatPanel extends MainWindowPanel {

    private int chatCount = 0;

    private SpringLayout chatListPaneLayout = new SpringLayout();
    private JPanel chatListPane = new JPanel(chatListPaneLayout);
    private JScrollPane scrollPane = new JScrollPane(chatListPane);
    public ChatPanel() {

        // Initialize
        super("채팅", "/chat_new.png");

        // init scrollPane
        scrollPane.setPreferredSize(new Dimension(238, 520));
        scrollPane.setBorder(null);
        layout.putConstraint(SpringLayout.NORTH, scrollPane, 102, SpringLayout.NORTH, this);
        layout.putConstraint(SpringLayout.WEST, scrollPane, 36, SpringLayout.WEST, this);
        layout.putConstraint(SpringLayout.EAST, scrollPane, 0, SpringLayout.EAST, this);
        layout.putConstraint(SpringLayout.SOUTH, scrollPane, 0, SpringLayout.SOUTH, this);
        add(scrollPane);

        // init chatListPane
        chatListPane.setPreferredSize(new Dimension(238, 520));
        chatListPane.setBackground(Color.white);

        // Add ChatRoomListAtom panels
        //ArrayList<Chatroom> chatRoomList = Chatroom.getChatRooms();
        ArrayList<Chatroom> chatRoomList = new ArrayList<>();
        for (int i=0; i<2; i++)
            chatRoomList.add(Chatroom.createDummyChatRoom());

        for (Chatroom c : chatRoomList)
            addChatListPane(c);

    }

    private void updateView() {
        revalidate();
        repaint();
    }
    public void addChatListPane(Chatroom chatroom) {
        ChatRoomListAtom cla = new ChatRoomListAtom(Chatroom.createDummyChatRoom());
        chatListPaneLayout.putConstraint(SpringLayout.NORTH, cla, chatCount*79, SpringLayout.NORTH, chatListPane);
        chatListPaneLayout.putConstraint(SpringLayout.WEST, cla, 0, SpringLayout.WEST, chatListPane);
        chatListPaneLayout.putConstraint(SpringLayout.EAST, cla, 0, SpringLayout.EAST, chatListPane);
        chatListPane.add(cla);
        chatListPane.setPreferredSize(new Dimension(238, chatCount*79));
        chatCount++;
        updateView();
    }
}
