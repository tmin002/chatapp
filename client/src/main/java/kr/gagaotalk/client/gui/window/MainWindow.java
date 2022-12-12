package kr.gagaotalk.client.gui.window;

import kr.gagaotalk.client.authentication.Authentication;
import kr.gagaotalk.client.gui.Colors;
import kr.gagaotalk.client.gui.ResourceManager;
import kr.gagaotalk.client.gui.component.ImageButton;
import kr.gagaotalk.client.gui.panel.FriendsPanel;
import kr.gagaotalk.client.gui.panel.MainWindowPanel;
import kr.gagaotalk.client.gui.panel.SettingsPanel;
import kr.gagaotalk.client.gui.panel.WeatherPanel;

import javax.swing.*;
import java.awt.*;
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

    // 보여질 판넬
    private final MainWindowPanel chatPanel = new ChatPanel();
    private final MainWindowPanel weatherPanel = new WeatherPanel();
    private final MainWindowPanel settingsPanel = new SettingsPanel();
    private final MainWindowPanel friendsPanel = new FriendsPanel();
    private JPanel shownPanel = new JPanel(new GridLayout(1, 1)); // default

    public MainWindow() {
        // Initialize
        Container root = this.getContentPane();
        setSize(374, 656);
        setLayout(mainWindowLayout);

        // Configure sidebar
        sideBar.setBackground(Colors.GACHON_BLUE);
        sideBar.setPreferredSize(new Dimension(66, 0));
        mainWindowLayout.putConstraint(SpringLayout.WEST, sideBar, 0, SpringLayout.WEST, root);
        mainWindowLayout.putConstraint(SpringLayout.NORTH, sideBar, 0, SpringLayout.NORTH, root);
        mainWindowLayout.putConstraint(SpringLayout.SOUTH, sideBar, 0, SpringLayout.NORTH, signOutButton);
        root.add(sideBar);

        // Add buttons to sidebar
        sideBar.add(friendsButton);
        friendsButton.setBounds(0, 37, 66, 63);

        sideBar.add(chatButton);
        chatButton.setBounds(0, 101, 66, 63);

        sideBar.add(weatherButton);
        weatherButton.setBounds(0, 164, 66, 63);

        sideBar.add(settingsButton);
        settingsButton.setBounds(0, 227, 66, 63);

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
            System.out.println("친구추가 버튼 클릭됨");
        });
        chatPanel.addTopButtonActionListener(e -> {
            System.out.println("채팅방추가 버튼 클릭됨");
        });
        weatherPanel.addTopButtonActionListener(e -> {
            System.out.println("날씨 새로고침 버튼 클릭됨");
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
        });
    }

    public void changeImageToSelected() {
        setIcon(imageWhenSelected);
    }
    public void changeImageToDefault() {
        setIcon(imageWhenDefault);
    }
}
