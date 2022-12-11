package kr.gagaotalk.client.gui.window;

import javax.swing.*;
import java.awt.*;

public class ImageIconResizer {
    public static ImageIcon resize(ImageIcon from, int width, int height) {
        Image image = from.getImage();
        Image newImg = image.getScaledInstance(width, height,  java.awt.Image.SCALE_SMOOTH);
        return new ImageIcon(newImg);
    }
}
