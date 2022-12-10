package kr.gagaotalk.client.gui.window;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SignUpWindow extends JFrame implements ActionListener {
    JButton suButton = new JButton();
    public SignUpWindow() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ImageIcon icon = new ImageIcon("C:\\Users\\Administrator\\Downloads\\gagaotalk\\client\\src\\main\\java\\kr\\gagaotalk\\client\\gui\\window\\signup.png");
        JPanel supanel = new JPanel() {
            public void paintComponent(Graphics g) {
                g.drawImage(icon.getImage(), 0, 0, null);
                setOpaque(false); //그림을 표시하게 설정,투명하게 조절
                super.paintComponent(g);
            }
        };
        supanel.setLayout(null);

        //Set nickname textfield
        JTextField nickname = new JTextField("nickname");
        nickname.setOpaque(false);
        nickname.setBorder(javax.swing.BorderFactory.createEmptyBorder());
        nickname.setSize(300, 40);
        nickname.setLocation(88, 213);

        //Set id textfield
        JTextField id = new JTextField("id");
        id.setOpaque(false);
        id.setBorder(javax.swing.BorderFactory.createEmptyBorder());
        id.setSize(300, 40);
        id.setLocation(88, 287);

        //Set password textfield
        JPasswordField password = new JPasswordField("password");
        password.setOpaque(false);
        password.setBorder(javax.swing.BorderFactory.createEmptyBorder());
        password.setSize(300, 40);
        password.setLocation(88, 361);

        //Set birthday textfield
        JTextField bday = new JTextField("birthday");
        bday.setOpaque(false);
        bday.setBorder(javax.swing.BorderFactory.createEmptyBorder());
        bday.setSize(300, 40);
        bday.setLocation(88, 435);

        //Set phoneNumber textfield
        JTextField pnum = new JTextField("phonenumber");
        pnum.setOpaque(false);
        pnum.setBorder(javax.swing.BorderFactory.createEmptyBorder());
        pnum.setSize(300, 40);
        pnum.setLocation(88, 509);

        //Set Sign Up button
        suButton.setSize(130, 50);
        suButton.setLocation(184, 607);
        suButton.setBorderPainted(false);
        suButton.setContentAreaFilled(false);
        suButton.addActionListener(this);

        //add components at sign up panel
        supanel.add(nickname);
        supanel.add(id);
        supanel.add(password);
        supanel.add(bday);
        supanel.add(pnum);
        supanel.add(suButton);

        //add sign up panel at Jframe
        add(supanel);
        setTitle("SignUp");
        setSize(499, 713);
        setResizable(false);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == suButton) {
            JOptionPane.showMessageDialog(null, "Sign Up Success!!");
            dispose();
        }
    }
}
