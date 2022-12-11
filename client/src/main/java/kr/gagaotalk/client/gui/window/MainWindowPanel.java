package kr.gagaotalk.client.gui.window;

import javax.swing.*;
import java.awt.*;

public abstract class MainWindowPanel extends JPanel {

    private SpringLayout layout = new SpringLayout();
    private Label titleLabel = new Label("title");

    public void setTitleLabelText(String title) {
        titleLabel.setText(title);
    }
    public MainWindowPanel() {

        // Initialize
        setPreferredSize(new Dimension(308, 656));
        setBackground(Color.white);
        setLayout(layout);

        // titleLabel
        layout.putConstraint(SpringLayout.WEST, titleLabel,36, SpringLayout.WEST, this);
        layout.putConstraint(SpringLayout.NORTH, titleLabel,54, SpringLayout.NORTH, this);
        titleLabel.setFont(new Font("Dialog", Font.BOLD, 24));
        add(titleLabel);



    }

}
