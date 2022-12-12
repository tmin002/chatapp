package kr.gagaotalk.client.gui.component;

import kr.gagaotalk.client.User;
import kr.gagaotalk.client.chat.Chatroom;
import kr.gagaotalk.client.gui.ResourceManager;
import kr.gagaotalk.client.gui.window.ImageIconResizer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ChatRoomListAtom extends JPanel {

    // Static things

    // Member things

    private final RoundPictureBox profilePicImage;
    private final Label chatRoomNameLabel;
    private final Label chatRoomDescriptionLabel;
    private final SpringLayout layout = new SpringLayout();
    private final ImageIcon defaultChatRoomImage =
            ImageIconResizer.resize(ResourceManager.getImageIcon("/chatroom_default_profile_pic.png"),
                    50, 50);

    private final ImageButton clickPanel = new ImageButton(239, 79);
    private final Chatroom chatroom;

    public ChatRoomListAtom(Chatroom chatroom) {

        // Initialize
        this.chatroom = chatroom;
        setLayout(layout);
        setPreferredSize(new Dimension(239, 79));
        setBackground(Color.white);

        // click panel add (used for click detection)
        layout.putConstraint(SpringLayout.NORTH, clickPanel, 0, SpringLayout.NORTH, this);
        layout.putConstraint(SpringLayout.WEST, clickPanel, 0, SpringLayout.WEST, this);
        layout.putConstraint(SpringLayout.EAST, clickPanel, 0, SpringLayout.EAST, this);
        layout.putConstraint(SpringLayout.SOUTH, clickPanel, 0, SpringLayout.SOUTH, this);
        add(clickPanel);

        // click panel click event
        clickPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1) {
                    // Left click
                    System.out.println("ChatRoomListAtom: left click on user " + chatroom.chatRoomName);
                } else {
                    // Right click
                    System.out.println("ChatRoomListAtom: right click on user " + chatroom.chatRoomName);
                }
            }
        });

        // profile pic add
        profilePicImage = new RoundPictureBox(defaultChatRoomImage, 50, 50);
        layout.putConstraint(SpringLayout.NORTH, profilePicImage, 15, SpringLayout.NORTH, this);
        layout.putConstraint(SpringLayout.WEST, profilePicImage, 0, SpringLayout.WEST, this);
        add(profilePicImage);

        // chat room name label add
        chatRoomNameLabel = new Label(chatroom.chatRoomName);
        chatRoomNameLabel.setFontSize(13);
        chatRoomNameLabel.setFontWeight(Font.BOLD);
        layout.putConstraint(SpringLayout.NORTH, chatRoomNameLabel, 20, SpringLayout.NORTH, this);
        layout.putConstraint(SpringLayout.WEST, chatRoomNameLabel, 67, SpringLayout.WEST, this);
        layout.putConstraint(SpringLayout.EAST, chatRoomNameLabel, 0, SpringLayout.EAST, this);
        add(chatRoomNameLabel);

        // chat room description label add
        chatRoomDescriptionLabel = new Label(chatroom.chatRoomPeopleCount + " people");
        chatRoomDescriptionLabel.setFontSize(11);
        chatRoomDescriptionLabel.setFontWeight(Font.PLAIN);
        layout.putConstraint(SpringLayout.NORTH, chatRoomDescriptionLabel, 40, SpringLayout.NORTH, this);
        layout.putConstraint(SpringLayout.WEST, chatRoomDescriptionLabel, 67, SpringLayout.WEST, this);
        layout.putConstraint(SpringLayout.EAST, chatRoomDescriptionLabel, 0, SpringLayout.EAST, this);
        add(chatRoomDescriptionLabel);

    }
}
