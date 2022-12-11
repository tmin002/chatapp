package kr.gagaotalk.client.gui.window;

import kr.gagaotalk.client.gui.Colors;
import kr.gagaotalk.client.gui.ResourceManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class MainWindow extends Window {

    private final SpringLayout mainWindowLayout = new SpringLayout();
    private final JPanel sideBar = new JPanel(null);
    private final SideBarButton friendsButton = new SideBarButton(
            "friends",
            "/friends.png",
            "/friends_selected.png",
            false, this);
    private final SideBarButton chatButton = new SideBarButton(
            "chat",
            "/chat.png",
            "/chat_selected.png",
            false, this);
    private final SideBarButton weatherButton = new SideBarButton(
            "weather",
            "/weather.png",
            "/weather_selected.png",
            false, this);
    private final SideBarButton settingsButton = new SideBarButton(
            "settings",
            "/settings.png",
            "/settings_selected.png",
            false, this);
    private final SideBarButton signOutButton = new SideBarButton(
            "signOut",
            "/logout.png",
            "/logout.png",
            true, this);
    public MainWindow() {
        // Initialize
        Container root = this.getContentPane();
        setSize(459, 656);
        setLayout(mainWindowLayout);

        // Configure sidebar
        sideBar.setBackground(Colors.GACHON_BLUE);
        sideBar.setPreferredSize(new Dimension(84, 500));
        mainWindowLayout.putConstraint(SpringLayout.WEST, sideBar, 0, SpringLayout.WEST, root);
        mainWindowLayout.putConstraint(SpringLayout.NORTH, sideBar, 0, SpringLayout.NORTH, root);
        mainWindowLayout.putConstraint(SpringLayout.SOUTH, sideBar, 0, SpringLayout.NORTH, signOutButton);
        root.add(sideBar);

        // Add buttons to sidebar
        sideBar.add(friendsButton);
        friendsButton.setBounds(0, 0, 84, 65);

        sideBar.add(chatButton);
        chatButton.setBounds(0, 66, 84, 65);

        sideBar.add(weatherButton);
        weatherButton.setBounds(0, 131, 84, 65);

        sideBar.add(settingsButton);
        settingsButton.setBounds(0, 196, 84, 65);

        // Add sign out button
        mainWindowLayout.putConstraint(SpringLayout.WEST, signOutButton, 0, SpringLayout.WEST, root);
        mainWindowLayout.putConstraint(SpringLayout.SOUTH, signOutButton, 0, SpringLayout.SOUTH, root);
        signOutButton.setPreferredSize(new Dimension(84, 65));
        root.add(signOutButton);

        // Default image set
        friendsButton.setIcon(
                ImageIconResizer.resize(
                        ResourceManager.getImageIcon("/friends_selected.png"), 84, 65));

        // Finish
        root.setBackground(Color.white);
        revalidate();
        repaint();
    }
}

class SideBarButton extends JButton {

    // static: SideBarButton 끼리 클릭된 버튼의 정보 공유. 기본값은 friends
    private static String selectedButtonName = "friends";
    private static ArrayList<SideBarButton> buttons = new ArrayList<>();

    // member variables and constructor
    private final String buttonName;
    private final ImageIcon imageWhenDefault;
    private final ImageIcon imageWhenSelected;
    public SideBarButton(String buttonName, String imagePathWhenDefault,
                         String imagePathWhenSelected, boolean isSignOutButton, MainWindow parent) {
        // Initialize
        if (!isSignOutButton)
            buttons.add(this);
        this.buttonName = buttonName;
        this.imageWhenSelected = ImageIconResizer.resize(
                ResourceManager.getImageIcon(imagePathWhenSelected), 84, 65);
        this.imageWhenDefault = ImageIconResizer.resize(
                ResourceManager.getImageIcon(imagePathWhenDefault), 84, 65);

        // Init style
        setBorderPainted(false);
        setBorder(null);
        setMargin(new Insets(0, 0, 0, 0));
        setContentAreaFilled(false);
        setSize(new Dimension(84, 65));
        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        changeImageToDefault();

        // Add handler
        this.addActionListener(e -> {
            if (!selectedButtonName.equals(buttonName)) {
                changeImageToSelected();

                if (!isSignOutButton) {
                    selectedButtonName = this.buttonName;
                    for (SideBarButton sbb : buttons) {
                        if (sbb != this)
                            sbb.changeImageToDefault();
                    }
                }
            }
        });
    }

    public void changeImageToSelected() {
        setIcon(imageWhenSelected);
    }
    public void changeImageToDefault() {
        setIcon(imageWhenDefault);
    }
}
