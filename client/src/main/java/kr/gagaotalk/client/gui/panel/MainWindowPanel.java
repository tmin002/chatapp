package kr.gagaotalk.client.gui.panel;


import kr.gagaotalk.client.gui.component.ImageButton;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public abstract class MainWindowPanel extends JPanel {

    public final SpringLayout layout = new SpringLayout();
    public Label titleLabel;
    private ImageButton topButton;

    private void initialize(String titleLabelText) {
        setPreferredSize(new Dimension(308, 656));
        setBackground(Color.white);
        setLayout(layout);

        // titleLabel
        titleLabel = new Label(titleLabelText);
        layout.putConstraint(SpringLayout.WEST, titleLabel,36, SpringLayout.WEST, this);
        layout.putConstraint(SpringLayout.NORTH, titleLabel,54, SpringLayout.NORTH, this);
        titleLabel.setFont(new Font("Dialog", Font.BOLD, 24));
        add(titleLabel);
    }

    // topButton actionListener
    public void addTopButtonActionListener(ActionListener al) {
        topButton.addActionListener(al);
    }

    // topButton 보이게
    public MainWindowPanel(String titleLabelText, String topButtonImagePath) {
        initialize(titleLabelText);

        // topButton placement
        topButton = new ImageButton(topButtonImagePath, 30, 30);
        layout.putConstraint(SpringLayout.NORTH, topButton, 54, SpringLayout.NORTH, this);
        layout.putConstraint(SpringLayout.EAST, topButton, -33, SpringLayout.EAST, this);
        add(topButton);
    }

    // topButton 안보이게
    public MainWindowPanel(String titleLabelText) {
        initialize(titleLabelText);
    }

}
