package kr.gagaotalk.client.gui.window;

import kr.gagaotalk.client.authentication.Authentication;
import kr.gagaotalk.client.gui.Colors;
import kr.gagaotalk.client.gui.ResourceManager;
import kr.gagaotalk.client.gui.component.ImageButton;
import kr.gagaotalk.client.gui.panel.*;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class MainWindow extends Window {

    private final SpringLayout mainWindowLayout = new SpringLayout();
    public final JPanel sideBar = new JPanel(null);
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

    private final MainWindowPanel chatPanel = new ChatPanel();
    private final MainWindowPanel weatherPanel = new WeatherPanel(this);
    private final MainWindowPanel settingsPanel = new SettingsPanel();
    private final MainWindowPanel friendsPanel = new FriendsPanel();
    // 보여질 판넬
    private JPanel shownPanel = new JPanel(new GridLayout(1, 1)); // default

    // Background panel
    public BackgroundPanel backgroundPanel = new BackgroundPanel();

    public MainWindow() {
        // Initialize
        Container root = this.getContentPane();
        setSize(374, 656);
        setLayout(mainWindowLayout);


        // Add buttons
        friendsButton.setPreferredSize(new Dimension(66, 63));
        mainWindowLayout.putConstraint(SpringLayout.WEST, friendsButton, 0, SpringLayout.WEST, root);
        mainWindowLayout.putConstraint(SpringLayout.NORTH, friendsButton, 37, SpringLayout.NORTH, root);
        add(friendsButton);

        chatButton.setPreferredSize(new Dimension(66, 63));
        mainWindowLayout.putConstraint(SpringLayout.WEST, chatButton, 0, SpringLayout.WEST, root);
        mainWindowLayout.putConstraint(SpringLayout.NORTH, chatButton, 101, SpringLayout.NORTH, root);
        add(chatButton);

        weatherButton.setPreferredSize(new Dimension(66, 63));
        mainWindowLayout.putConstraint(SpringLayout.WEST, weatherButton, 0, SpringLayout.WEST, root);
        mainWindowLayout.putConstraint(SpringLayout.NORTH, weatherButton, 164, SpringLayout.NORTH, root);
        add(weatherButton);

        settingsButton.setPreferredSize(new Dimension(66, 63));
        mainWindowLayout.putConstraint(SpringLayout.WEST, settingsButton, 0, SpringLayout.WEST, root);
        mainWindowLayout.putConstraint(SpringLayout.NORTH, settingsButton, 227, SpringLayout.NORTH, root);
        add(settingsButton);

        // Configure sidebar
        sideBar.setBackground(Colors.GACHON_BLUE);
        sideBar.setPreferredSize(new Dimension(66, 0));
        mainWindowLayout.putConstraint(SpringLayout.WEST, sideBar, 0, SpringLayout.WEST, root);
        mainWindowLayout.putConstraint(SpringLayout.NORTH, sideBar, 0, SpringLayout.NORTH, root);
        mainWindowLayout.putConstraint(SpringLayout.SOUTH, sideBar, 0, SpringLayout.NORTH, signOutButton);
        root.add(sideBar);


        // Add sign out button
        mainWindowLayout.putConstraint(SpringLayout.WEST, signOutButton, 0, SpringLayout.WEST, root);
        mainWindowLayout.putConstraint(SpringLayout.SOUTH, signOutButton, 0, SpringLayout.SOUTH, root);
        signOutButton.setPreferredSize(new Dimension(66, 63));
        root.add(signOutButton);

        // side bar buttons default image set
        friendsButton.setIcon(
                ImageIconResizer.resize(
                        ResourceManager.getImageIcon("/friends_selected.png"), 66, 63));

        // Add shownPanel
        mainWindowLayout.putConstraint(SpringLayout.WEST, shownPanel, 0, SpringLayout.EAST, sideBar);
        mainWindowLayout.putConstraint(SpringLayout.EAST, shownPanel, 0, SpringLayout.EAST, root);
        mainWindowLayout.putConstraint(SpringLayout.NORTH, shownPanel, 0, SpringLayout.NORTH, root);
        mainWindowLayout.putConstraint(SpringLayout.SOUTH, shownPanel, 0, SpringLayout.SOUTH, root);
        root.add(shownPanel);

        // Add backgroundPanel
        mainWindowLayout.putConstraint(SpringLayout.NORTH, backgroundPanel, 0, SpringLayout.NORTH, root);
        mainWindowLayout.putConstraint(SpringLayout.WEST, backgroundPanel, 0, SpringLayout.WEST, root);
        mainWindowLayout.putConstraint(SpringLayout.EAST, backgroundPanel, 0, SpringLayout.EAST, root);
        mainWindowLayout.putConstraint(SpringLayout.SOUTH, backgroundPanel, 0, SpringLayout.SOUTH, root);
        add(backgroundPanel);
        backgroundPanel.setVisible(false);

        // Event handlers
        friendsButton.addActionListener(e -> changeShownPanel(friendsPanel));
        chatButton.addActionListener(e -> changeShownPanel(chatPanel));
        weatherButton.addActionListener(e -> changeShownPanel(weatherPanel));
        settingsButton.addActionListener(e -> changeShownPanel(settingsPanel));
        signOutButton.addActionListener(e -> {
           // Sign Out
            Authentication.signOut();
            dispose();
        });

        friendsPanel.addTopButtonActionListener(e -> {
            new AddFriend();
        });
        chatPanel.addTopButtonActionListener(e -> {
            new AddChatRoom();
        });

        // Finish
        root.setBackground(Color.white);
        changeShownPanel(friendsPanel);
        super.showWindow();
    }

    // 판넬 추가했을때 호출해야 하는 함수.
    public void updateView() {
        revalidate();
        repaint();
    }

    // shownPanel 바꾸는 함수.
    public void changeShownPanel(MainWindowPanel p) {
       shownPanel.removeAll();
       shownPanel.add(p);
       updateView();
    }
}

class SideBarButton extends ImageButton {

    // static: SideBarButton 끼리 클릭된 버튼의 정보 공유. 기본값은 friends
    private static String selectedButtonName = "friends";
    private static ArrayList<SideBarButton> buttons = new ArrayList<>();

    // member variables and constructor
    private final String buttonName;
    private final ImageIcon imageWhenDefault;
    private final ImageIcon imageWhenSelected;
    public SideBarButton(String buttonName, String imagePathWhenDefault,
                         String imagePathWhenSelected, boolean isSignOutButton,
                         MainWindow parent) {
        // Initialize
        super(imagePathWhenDefault, 66, 63);

        if (!isSignOutButton)
            buttons.add(this);

        this.buttonName = buttonName;
        this.imageWhenSelected = ImageIconResizer.resize(
                ResourceManager.getImageIcon(imagePathWhenSelected), 66, 63);
        this.imageWhenDefault = ImageIconResizer.resize(
                ResourceManager.getImageIcon(imagePathWhenDefault), 66, 63);

        // Init style
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
            if (selectedButtonName.equals("weather")) {
                parent.sideBar.setVisible(false);
                parent.backgroundPanel.setVisible(true);
                parent.backgroundPanel.setBackgroundImageIcon(
                        ResourceManager.getImageIcon("/nightsky.png"));
            } else {
                parent.sideBar.setVisible(true);
                parent.getContentPane().setBackground(Color.white);
                parent.backgroundPanel.setVisible(false);
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

class BackgroundPanel extends JLabel {
    private ImageIcon backgroundIconImage;
    public BackgroundPanel() {
       setText("");
    }
    public void setBackgroundImageIcon(ImageIcon backgroundIconImage) {
        this.backgroundIconImage = backgroundIconImage;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(ImageIconResizer.resize(backgroundIconImage,
                        this.getWidth(), this.getHeight()).getImage(),
                         0, 0, null);
    }
}