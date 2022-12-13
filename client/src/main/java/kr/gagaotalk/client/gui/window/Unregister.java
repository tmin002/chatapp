package kr.gagaotalk.client.gui.window;

import kr.gagaotalk.client.gui.ResourceManager;

import javax.swing.*;
import java.awt.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Unregister extends JFrame implements ActionListener {
    JButton urButton = new JButton();

    public Unregister() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ImageIcon icon = ResourceManager.getImageIcon("/unregister.png");
        JPanel urpanel = new JPanel() {
            public void paintComponent(Graphics g) {
                g.drawImage(icon.getImage(), 0, 0, null);
                setOpaque(false); //그림을 표시하게 설정,투명하게 조절
                super.paintComponent(g);
            }
        };

        //Set Sign Up button
        urButton.setSize(130, 50);
        urButton.setLocation(184, 607);
//        urButton.setBorderPainted(false);
//        urButton.setContentAreaFilled(false);
        urButton.addActionListener(this);


        urpanel.add(urButton);
        add(urpanel);

        //add sign up panel at Jframe
        add(urpanel);
        setTitle("Unregister");
        setSize(499, 713);
        setResizable(false);
        setVisible(true);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == urButton) {
            //여기서 처리
            JOptionPane.showMessageDialog(null, "회원탈퇴가 완료되었습니다.", "Unregister", JOptionPane.INFORMATION_MESSAGE);
        }
    }
}
