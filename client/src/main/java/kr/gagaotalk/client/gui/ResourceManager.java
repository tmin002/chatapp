package kr.gagaotalk.client.gui;

import javax.swing.*;
import java.util.Objects;

public class ResourceManager {
    public static ImageIcon getImageIcon(String imageResourcePath) {
        return new ImageIcon(Objects.requireNonNull(ResourceManager.class.getResource(imageResourcePath)));
    }
}
