package kr.gagaotalk.client.gui.window;

import kr.gagaotalk.client.gui.ResourceManager;

import javax.swing.*;
import java.awt.*;

// 프로그램의 모든 창에 적용되는 코드
public class Window extends JFrame {
    public Window() {
        setTitle("gagaotalk");
        setIconImage(ResourceManager.getImageIcon("/gagaotalk_icon.png").getImage());
        setBackground(Color.white);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);
    }
}
