package kr.gagaotalk.client.gui.window;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ChatRoom extends JFrame implements ActionListener {

    public void FileProgressbar() {
        JFrame f = new JFrame("File receiving");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Container content = f.getContentPane();
        JProgressBar progressBar = new JProgressBar(0, 100);
        progressBar.setValue(0);
        progressBar.setStringPainted(true);
        Border border = BorderFactory.createTitledBorder("Receiving...");
        progressBar.setBorder(border);
        content.add(progressBar, BorderLayout.NORTH);
        f.setSize(300, 80);
        f.setVisible(true);
        int num = 0;
        while(num < 100) {
            progressBar.setValue(num);
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
            }
            num += 1;
        }
        f.dispose();

    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
