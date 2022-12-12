package kr.gagaotalk.client.gui.component;

import kr.gagaotalk.client.gui.window.ImageIconResizer;

import javax.swing.*;

public class PictureBox extends JLabel {
    public PictureBox(ImageIcon imageIcon, int width, int height) {
        setText("");
        setIcon(ImageIconResizer.resize(imageIcon, width, height));
    }
}
