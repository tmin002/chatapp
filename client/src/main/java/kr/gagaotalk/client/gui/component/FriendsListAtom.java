package kr.gagaotalk.client.gui.component;

import kr.gagaotalk.client.User;
import kr.gagaotalk.client.gui.ResourceManager;
import kr.gagaotalk.client.gui.window.ImageIconResizer;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
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

    public FriendsListAtom(ImageIcon userProfilePicture, User user) {

        // Initialize
        setLayout(layout);
        setSize(239, 79);
        setBackground(Color.red); // debug

        // profile pic add
        profilePicImage = new RoundPictureBox(userProfilePicture, 50, 50);
        layout.putConstraint(SpringLayout.NORTH, profilePicImage, 15, SpringLayout.NORTH, this);
        layout.putConstraint(SpringLayout.WEST, profilePicImage, 0, SpringLayout.WEST, this);
        add(profilePicImage);

        // online dot image add (user online indication)
        onlineIndicatorImage = new PictureBox(onlineImageIcon, 15, 15);
        layout.putConstraint(SpringLayout.NORTH, onlineIndicatorImage, 50, SpringLayout.NORTH, this);
        layout.putConstraint(SpringLayout.WEST, onlineIndicatorImage, 34, SpringLayout.WEST, this);
        add(onlineIndicatorImage);

        // user nickname label add
        userNicknameLabel = new Label(user.nickname);
        userNicknameLabel.setFontSize(13);
        userNicknameLabel.setFontWeight(Font.BOLD);
        layout.putConstraint(SpringLayout.NORTH, userNicknameLabel, 67, SpringLayout.NORTH, this);
        layout.putConstraint(SpringLayout.WEST, userNicknameLabel, 20, SpringLayout.WEST, this);
        layout.putConstraint(SpringLayout.EAST, userNicknameLabel, -50, SpringLayout.EAST, this);
        add(userNicknameLabel);

        // user nickname label add
        userBioLabel = new Label(user.bio);
        userBioLabel.setFontSize(11);
        userBioLabel.setFontWeight(Font.PLAIN);
        layout.putConstraint(SpringLayout.NORTH, userBioLabel, 67, SpringLayout.NORTH, this);
        layout.putConstraint(SpringLayout.WEST, userBioLabel, 40, SpringLayout.WEST, this);
        layout.putConstraint(SpringLayout.EAST, userBioLabel, -50, SpringLayout.EAST, this);
        add(userBioLabel);

        // finish
    }

    public void updateUserOnlineIndicator() {
    }
}
