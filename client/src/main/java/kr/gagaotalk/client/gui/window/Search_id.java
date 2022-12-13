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

public class Search_id extends JFrame implements ActionListener {

    JButton seButton = new JButton();
    JButton adButton = new JButton();
    JTextField sid = new JTextField("enter id");
    JTextField aid = new JTextField("find id");

    public Search_id() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ImageIcon icon = ImageIconResizer.resize(ResourceManager.getImageIcon("/search_id.png"), 337, 99);
        JPanel sepanel = new JPanel() {
            public void paintComponent(Graphics g) {
                g.drawImage(icon.getImage(), 0, 0, null);
                setOpaque(false); //그림을 표시하게 설정,투명하게 조절
                super.paintComponent(g);
            }
        };
        sepanel.setLayout(null);

        //Set search id textfield
        sid.setOpaque(false);
        sid.setBorder(javax.swing.BorderFactory.createEmptyBorder());
        sid.setSize(253, 40);
        sid.setLocation(15, 10);

        //Set find id textfield
        aid.setOpaque(false);
        aid.setBorder(javax.swing.BorderFactory.createEmptyBorder());
        aid.setSize(253, 40);
        aid.setLocation(15, 50);
//
        //Set Sign Up button
        seButton.setSize(57, 33);
        seButton.setLocation(268, 15);
        seButton.setBorderPainted(false);
        seButton.setContentAreaFilled(false);
        seButton.addActionListener(this);
//
        //Set Sign Up button
        adButton.setSize(57, 33);
        adButton.setLocation(268, 53);
        adButton.setBorderPainted(false);
        adButton.setContentAreaFilled(false);
        adButton.addActionListener(this);
//
        sepanel.add(sid);
        sepanel.add(aid);
        sepanel.add(seButton);
        sepanel.add(adButton);

        //add sign up panel at Jframe
        add(sepanel);
        setTitle("Search ID");
        setSize(334, 126);
        setResizable(false);
        setVisible(true);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == seButton) {
            String fuser = User.getUserByID(sid.toString()).toString();
            if (fuser != null) {
                aid.setText(fuser);
            }
            else {
                aid.setText("Can't find user");
            }
        }
        if(e.getSource() == adButton) {
            if (aid.getText() != "Can't find user") {
                JOptionPane.showMessageDialog(null, "Add user Success.", "Add user", JOptionPane.INFORMATION_MESSAGE);
                dispose();
            }
        }
    }
}
