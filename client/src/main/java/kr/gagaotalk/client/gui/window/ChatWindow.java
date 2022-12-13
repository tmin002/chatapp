package kr.gagaotalk.client.gui.window;

import kr.gagaotalk.client.chat.Chat;
import kr.gagaotalk.client.gui.Colors;
import kr.gagaotalk.client.gui.ResourceManager;
import kr.gagaotalk.client.gui.component.ColoredButton;
import kr.gagaotalk.client.gui.component.ImageButton;

import javax.annotation.Resource;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ChatWindow extends Window {

    private ColoredButton sendButton = new ColoredButton("send", Colors.GACHON_GREEN);
    private ImageButton fileAddButton = new ImageButton("/addfile.png", 20 ,20);
    private JTextArea writeTextField = new JTextArea(50, 5);
    private ChatViewPanel chatView = new ChatViewPanel();
    private SpringLayout layout = new SpringLayout();
    public ChatWindow() {

        // Initialize
        Container root = this.getContentPane();
        setSize(374, 656);
        root.setBackground(Color.white);
        setLayout(layout);

        // Automatically focus to text field if clicked on anywhere
        writeTextField.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                writeTextField.requestFocus();
            }
        });

        // Add sendButton
        layout.putConstraint(SpringLayout.SOUTH, sendButton, -132, SpringLayout.SOUTH, root);
        layout.putConstraint(SpringLayout.EAST, sendButton, -18, SpringLayout.EAST, root);
        root.add(sendButton);

        // Add fileAddButton
        layout.putConstraint(SpringLayout.SOUTH, fileAddButton, -28, SpringLayout.SOUTH, root);
        layout.putConstraint(SpringLayout.EAST, fileAddButton, -28, SpringLayout.EAST, root);
        root.add(fileAddButton);

        // Add writeTextField
        writeTextField.setBackground(Color.white);
        writeTextField.setBorder(null);
        writeTextField.setSize(new Dimension(374, 175));
        writeTextField.setLineWrap(true);
        layout.putConstraint(SpringLayout.NORTH, writeTextField, 17, SpringLayout.SOUTH, chatView);
        layout.putConstraint(SpringLayout.SOUTH, writeTextField, -16, SpringLayout.SOUTH, root);
        layout.putConstraint(SpringLayout.WEST, writeTextField, 20, SpringLayout.WEST, root);
        layout.putConstraint(SpringLayout.EAST, writeTextField, -90, SpringLayout.EAST, root);
        root.add(writeTextField);

        // Add chatView
        layout.putConstraint(SpringLayout.NORTH, chatView, 0, SpringLayout.NORTH, root);
        layout.putConstraint(SpringLayout.WEST, chatView, 0, SpringLayout.WEST, root);
        layout.putConstraint(SpringLayout.EAST, chatView, 0, SpringLayout.EAST, root);
        layout.putConstraint(SpringLayout.SOUTH, chatView, -175, SpringLayout.SOUTH, root);
        root.add(chatView);



        // Finish
        super.showWindow();
    }
}

class ChatViewPanel extends JPanel {
    public ChatViewPanel() {
        setBackground(Colors.GACHON_BLUE);
    }
}
