package kr.gagaotalk.client.gui.panel;

import kr.gagaotalk.client.User;
import kr.gagaotalk.client.gui.Colors;
import kr.gagaotalk.client.gui.component.FriendsListAtom;
import kr.gagaotalk.client.gui.component.ColoredButton;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class FriendsPanel extends MainWindowPanel {

    private int friendsCount = 0;

    private SpringLayout friendsListPaneLayout = new SpringLayout();
    private JPanel friendsListPane = new JPanel(friendsListPaneLayout);
    private JScrollPane scrollPane = new JScrollPane(friendsListPane);
    public FriendsPanel() {

        // Initialize
        super("Friends", "/friend_new.png");

        // init scrollPane
        scrollPane.setPreferredSize(new Dimension(238, 520));
        scrollPane.setBorder(null);
        layout.putConstraint(SpringLayout.NORTH, scrollPane, 102, SpringLayout.NORTH, this);
        layout.putConstraint(SpringLayout.WEST, scrollPane, 36, SpringLayout.WEST, this);
        layout.putConstraint(SpringLayout.EAST, scrollPane, 0, SpringLayout.EAST, this);
        layout.putConstraint(SpringLayout.SOUTH, scrollPane, 0, SpringLayout.SOUTH, this);
        add(scrollPane);

        // init friendsListPane
        friendsListPane.setPreferredSize(new Dimension(238, 520));
        friendsListPane.setBackground(Color.white);

        // Add FriendsListAtom panels
        //ArrayList<User> friendList = User.getFriends();
        ArrayList<User> friendList = new ArrayList<>();
        for (int i=0; i<2; i++)
            friendList.add(User.makeDummyUser());

        for (User u : friendList)
            addFriendsListAtom(u);
    }

    private void updateView() {
        revalidate();
        repaint();
    }
    public void addFriendsListAtom(User user) {
        FriendsListAtom fla = new FriendsListAtom(User.makeDummyUser());
        friendsListPaneLayout.putConstraint(SpringLayout.NORTH, fla, friendsCount*79, SpringLayout.NORTH, friendsListPane);
        friendsListPaneLayout.putConstraint(SpringLayout.WEST, fla, 0, SpringLayout.WEST, friendsListPane);
        friendsListPaneLayout.putConstraint(SpringLayout.EAST, fla, 0, SpringLayout.EAST, friendsListPane);
        friendsListPane.add(fla);
        friendsListPane.setPreferredSize(new Dimension(238, friendsCount*79));
        friendsCount++;
        updateView();
    }
}
