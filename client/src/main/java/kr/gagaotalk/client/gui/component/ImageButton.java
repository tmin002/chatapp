package kr.gagaotalk.client.gui.component;

import kr.gagaotalk.client.gui.ResourceManager;
import kr.gagaotalk.client.gui.window.ImageIconResizer;

import javax.swing.*;
import java.awt.*;

public class ImageButton extends JButton {
    public ImageButton(String imagePath, int width, int height) {
        setBorderPainted(false);
        setBorder(null);
        setMargin(new Insets(0, 0, 0, 0));
        setContentAreaFilled(false);
        setSize(new Dimension(66, 63));
        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        setIcon(ImageIconResizer.resize(ResourceManager.getImageIcon(imagePath), width, height));
    }

    // Make transparent button
    public ImageButton(int width, int height) {
        setBorderPainted(false);
        setBorder(null);
        setMargin(new Insets(0, 0, 0, 0));
        setContentAreaFilled(false);
        setSize(new Dimension(66, 63));
        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }
}
