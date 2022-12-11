package kr.gagaotalk.client.gui.window;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;
import javax.imageio.*;

public class LoginWindow extends JFrame implements ActionListener {
    JButton lgButton = new JButton();
    JButton suButton = new JButton();
    JButton fpButton = new JButton();
    public LoginWindow() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ImageIcon icon = new ImageIcon(Objects.requireNonNull(getClass().getResource("/login.png")));
        JPanel lgpanel = new JPanel() {
            public void paintComponent(Graphics g) {
                // Approach 1: Dispaly image at at full size
                g.drawImage(icon.getImage(), 0, 0, null);
                // Approach 2: Scale image to size of component
                // Dimension d = getSize();
                // g.drawImage(icon.getImage(), 0, 0, d.width, d.height, null);
                // Approach 3: Fix the image position in the scroll pane
                // Point p = scrollPane.getViewport().getViewPosition();
                // g.drawImage(icon.getImage(), p.x, p.y, null);
                setOpaque(false); //그림을 표시하게 설정,투명하게 조절
                super.paintComponent(g);
            }
        };
        lgpanel.setLayout(null);
        JTextField id = new JTextField("id");
        id.setSize(300, 50);
        id.setLocation(100, 350);

        JPasswordField password = new JPasswordField("password");
        password.setSize(300, 50);
        password.setLocation(100, 440);

//        JButton lg = new JButton();
        lgButton.setSize(300, 50);
        lgButton.setLocation(100, 540);
        lgButton.setBorderPainted(false);
        lgButton.setContentAreaFilled(false);
        lgButton.addActionListener(this);

        suButton.setSize(90, 30);
        suButton.setLocation(90, 690);
        suButton.setBorderPainted(false);
        suButton.setContentAreaFilled(false);
        suButton.addActionListener(this);

        fpButton.setSize(180, 60);
        fpButton.setLocation(220, 690);
        fpButton.setBorderPainted(false);
        fpButton.setContentAreaFilled(false);
        fpButton.addActionListener(this);

        lgpanel.add(id);
        lgpanel.add(password);
        lgpanel.add(lgButton);
        lgpanel.add(suButton);
        lgpanel.add(fpButton);

        add(lgpanel);
        setTitle("Login");
        setSize(500, 817);
        setResizable(false);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == lgButton) {
            JOptionPane.showMessageDialog(null, "Login Success");
        }
        if(e.getSource() == suButton) {
            SignUpWindow b = new SignUpWindow();
//          JOptionPane.showMessageDialog(null, "Sign up");
        }
        if(e.getSource() == fpButton) {
            Forgot_IdPw c = new Forgot_IdPw();
//            JOptionPane.showMessageDialog(null, "Forgot ID/password");
        }
    }
}