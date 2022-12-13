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
        JPanel supanel = new JPanel() {
            public void paintComponent(Graphics g) {
                g.drawImage(icon.getImage(), 0, 0, null);
                setOpaque(false); //그림을 표시하게 설정,투명하게 조절
                super.paintComponent(g);
            }
        };


    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
