package kr.gagaotalk.client.gui;

import javax.swing.*;
import java.net.URL;
import java.util.Objects;

public class ResourceManager {
    public static ImageIcon getImageIcon(String imageResourcePath) {
        return new ImageIcon(Objects.requireNonNull(getImageURL(imageResourcePath)));
    }
    public static URL getImageURL(String imageResourcePath) {
        return ResourceManager.class.getResource(imageResourcePath);
    }
}
