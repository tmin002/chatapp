package kr.gagaotalk.client.gui.component;

import javax.swing.*;
import java.awt.*;

public class ColoredButton extends JButton {
    public ColoredButton(String title, Color color) {
        setBackground(color);
        setOpaque(true);
        setText(title);
        setFont(new Font("Dialog", Font.PLAIN, 11));
        setBorderPainted(false);
        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        setSize(new Dimension(this.getWidth(), 30));
    }
}
