package kr.gagaotalk.client.gui.component;

import kr.gagaotalk.client.User;
import kr.gagaotalk.client.gui.ResourceManager;
import kr.gagaotalk.client.gui.window.ImageIconResizer;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.nio.Buffer;

public class FriendsListAtom extends JPanel {

    // Static things

    // Member things

    private final RoundPictureBox profilePicImage;
    private final PictureBox onlineIndicatorImage;
    private final Label userNicknameLabel;
    private final Label userBioLabel;
    private final SpringLayout layout = new SpringLayout();

    private final ImageIcon offlineImageIcon =
            ImageIconResizer.resize(ResourceManager.getImageIcon("/offline.png"),15, 15);
    private final ImageIcon onlineImageIcon =
            ImageIconResizer.resize(ResourceManager.getImageIcon("/online.png"),15, 15);

    private final ImageButton clickPanel = new ImageButton(239, 79);
    private final User user;

    public FriendsListAtom(User user) {

        // Initialize
        this.user = user;
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
                    System.out.println("FriendsListAtom: left click on user " + user.nickname);
                } else {
                    // Right click
                    System.out.println("FriendsListAtom: right click on user " + user.nickname);
                }
            }
        });

        // online dot image add (user online indication)
        onlineIndicatorImage = new PictureBox(onlineImageIcon, 15, 15);
        layout.putConstraint(SpringLayout.NORTH, onlineIndicatorImage, 50, SpringLayout.NORTH, this);
        layout.putConstraint(SpringLayout.WEST, onlineIndicatorImage, 34, SpringLayout.WEST, this);
        add(onlineIndicatorImage);

        // profile pic add
        profilePicImage = new RoundPictureBox(ImageIconResizer.resize(ResourceManager.getImageIcon("/user_default_profile_pic.png"), 50, 50), 50, 50);
        layout.putConstraint(SpringLayout.NORTH, profilePicImage, 15, SpringLayout.NORTH, this);
        layout.putConstraint(SpringLayout.WEST, profilePicImage, 0, SpringLayout.WEST, this);
        add(profilePicImage);

        // user nickname label add
        userNicknameLabel = new Label(user.nickname);
        userNicknameLabel.setFontSize(13);
        userNicknameLabel.setFontWeight(Font.BOLD);
        layout.putConstraint(SpringLayout.NORTH, userNicknameLabel, 20, SpringLayout.NORTH, this);
        layout.putConstraint(SpringLayout.WEST, userNicknameLabel, 67, SpringLayout.WEST, this);
        layout.putConstraint(SpringLayout.EAST, userNicknameLabel, 0, SpringLayout.EAST, this);
        add(userNicknameLabel);

        // user bio label add
        userBioLabel = new Label(user.bio);
        userBioLabel.setFontSize(11);
        userBioLabel.setFontWeight(Font.PLAIN);
        layout.putConstraint(SpringLayout.NORTH, userBioLabel, 40, SpringLayout.NORTH, this);
        layout.putConstraint(SpringLayout.WEST, userBioLabel, 67, SpringLayout.WEST, this);
        layout.putConstraint(SpringLayout.EAST, userBioLabel, 0, SpringLayout.EAST, this);
        add(userBioLabel);

        // finish
        // TODO: updateUserOnlineIndicator();
    }

    public void updateUserOnlineIndicator() {
        if (User.checkUserOnline(user.nickname)) {
           onlineIndicatorImage.setIcon(onlineImageIcon);
        } else {
            onlineIndicatorImage.setIcon(offlineImageIcon);
        }
    }
}
