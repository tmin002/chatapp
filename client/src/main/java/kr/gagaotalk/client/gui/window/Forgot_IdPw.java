package kr.gagaotalk.client.gui.window;

import kr.gagaotalk.client.authentication.Authentication;
import kr.gagaotalk.client.connection.Received;
import kr.gagaotalk.client.gui.ResourceManager;
import kr.gagaotalk.core.DateConvert;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Forgot_IdPw extends JFrame implements ActionListener {
    JButton fButton = new JButton();
    JButton idButton = new JButton("ID");
    JButton pwButton = new JButton("PW");
    JTextField cp1 = new JTextField("component1");
    JTextField cp2 = new JTextField("component2");
    public Forgot_IdPw() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ImageIcon icon = ResourceManager.getImageIcon("/forgot_idpw.png");
                JPanel fippanel = new JPanel() {
                    public void paintComponent(Graphics g) {
                g.drawImage(icon.getImage(), 0, 0, null);
                setOpaque(false); //그림을 표시하게 설정,투명하게 조절
                super.paintComponent(g);
            }
        };
        fippanel.setLayout(null);

        //Set nickname or id textfield
        cp1.setOpaque(false);
        cp1.setBorder(javax.swing.BorderFactory.createEmptyBorder());
        cp1.setSize(300, 40);
        cp1.setLocation(88, 298);

        //Set birthday or phone_number textfield
        cp2.setOpaque(false);
        cp2.setBorder(javax.swing.BorderFactory.createEmptyBorder());
        cp2.setSize(300, 40);
        cp2.setLocation(88, 371);

        //Set Sign Up button
        fButton.setSize(130, 50);
        fButton.setLocation(184, 607);
        fButton.setBorderPainted(false);
        fButton.setContentAreaFilled(false);
        fButton.addActionListener(this);

        //Set Forgot ID button
        idButton.setSize(130, 50);
        idButton.setLocation(115, 200);
        idButton.setBackground(Color.GRAY);
        idButton.addActionListener(this);

        //Set Forgot password button
        pwButton.setSize(130, 50);
        pwButton.setLocation(246, 200);
        pwButton.setBackground(Color.WHITE);
        pwButton.addActionListener(this);

        //add components at forgot field
        fippanel.add(cp1);
        fippanel.add(cp2);
        fippanel.add(fButton);
        fippanel.add(idButton);
        fippanel.add(pwButton);

        //add forgot field at Jframe
        add(fippanel);
        setTitle("Forgot id/pw");
        setSize(499, 713);
        setResizable(false);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == idButton) {
            if (idButton.getBackground() != Color.GRAY) {
                idButton.setBackground(Color.GRAY);
                pwButton.setBackground(Color.WHITE);
            }
        }
        if (e.getSource() == pwButton) {
            if (pwButton.getBackground() != Color.GRAY) {
                pwButton.setBackground(Color.GRAY);
                idButton.setBackground(Color.WHITE);
            }
        }
        if (e.getSource() == fButton) {
            if (idButton.getBackground() == Color.GRAY) {
                String user_id = Authentication.findID(cp1.getText(), DateConvert.StringToDate(cp2.getText()));
                if (user_id != null) {
                    JOptionPane.showMessageDialog(null, user_id, "Find ID/PW", JOptionPane.INFORMATION_MESSAGE);
                }
                else {
                    JOptionPane.showMessageDialog(null, "Can't find your id.", "Find ID/PW", JOptionPane.ERROR_MESSAGE);
                }
            }
            else {
                String temp_pw = Authentication.findPassword(cp1.getText(), cp2.getText());
                if (temp_pw != null) {
                    JOptionPane.showMessageDialog(null, "Temporary_password: " + temp_pw, "Find ID/PW", JOptionPane.INFORMATION_MESSAGE);
                }
                else {
                    JOptionPane.showMessageDialog(null, "Invalid information.", "Find ID/PW", JOptionPane.ERROR_MESSAGE);
                }
//                JOptionPane.showMessageDialog(null, "Search and to show user's password", "Find PASSWORD", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }
}
