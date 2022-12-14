package kr.gagaotalk.client.gui.window;

import kr.gagaotalk.client.User;
import kr.gagaotalk.client.gui.ResourceManager;
import javax.swing.*;
import kr.gagaotalk.client.authentication.Authentication;
import kr.gagaotalk.client.connection.Received;
import kr.gagaotalk.client.gui.ResourceManager;
import kr.gagaotalk.core.DateConvert;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddChatUser extends JFrame implements ActionListener {
    JButton saButton = new JButton();
    JButton adButton = new JButton();
    JTextField sid = new JTextField("enter id");
    JTextArea aid = new JTextArea("add id");

    String totalUser;

    public AddChatUser() {
        ImageIcon icon = ImageIconResizer.resize(ResourceManager.getImageIcon("/addchatuser.png"), 301, 197);
        JPanel sapanel = new JPanel() {
            public void paintComponent(Graphics g) {
                g.drawImage(icon.getImage(), 0, 0, null);
                setOpaque(false); //그림을 표시하게 설정,투명하게 조절
                super.paintComponent(g);
            }
        };
        sapanel.setLayout(null);

        //Set search id textfield
        sid.setOpaque(false);
        sid.setBorder(javax.swing.BorderFactory.createEmptyBorder());
        sid.setSize(225, 35);
        sid.setLocation(12, 12);

        //Set find id textfield
        aid.setOpaque(false);
        aid.setBorder(javax.swing.BorderFactory.createEmptyBorder());
        aid.setSize(223, 129);
        aid.setLocation(12, 57);

        //Set Sign Up button
        saButton.setSize(48, 33);
        saButton.setLocation(242, 13);
        saButton.setBorderPainted(false);
        saButton.setContentAreaFilled(false);
        saButton.addActionListener(this);

        //Set Sign Up button
        adButton.setSize(48, 35);
        adButton.setLocation(243, 104);
        adButton.setBorderPainted(false);
        adButton.setContentAreaFilled(false);
        adButton.addActionListener(this);


        sapanel.add(sid);
        sapanel.add(aid);
        sapanel.add(saButton);
        sapanel.add(adButton);

        //add sign up panel at Jframe
        add(sapanel);
        setTitle("Add user in chat");
        setSize(301, 224);
        setResizable(false);
        setVisible(true);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == saButton) {
            String sauser = User.getUserByID(sid.getText()).toString();
            if (sauser != null) {
                if (totalUser == null) {
                    totalUser = sauser;
                }
                else {
                    totalUser = totalUser + "," + sauser;
                }
                aid.setText(totalUser);
            }
            else {
                JOptionPane.showMessageDialog(null, "Failed", "Add user", JOptionPane.ERROR_MESSAGE);
            }
        }
        if (e.getSource() == adButton) {
            String[] idList = sid.getText().split(",");
            boolean success = true;
            for (String id : idList) {
                if (User.addFriend(id).statusCode != 0) {
                    success = false;
                    break;
                }
            }
            if (!success) {
                JOptionPane.showMessageDialog(null, "Failed!", "Add user", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
