package kr.gagaotalk.client.gui.component;

import javax.swing.*;
import java.awt.*;

public class Label extends JLabel {

    private String fontName = "Dialog";
    private int fontWeight = Font.PLAIN;
    private int fontSize = 18;

    private void initialize(String fontName, int fontWeight, int fontSize) {
        setFont(new Font(fontName, fontWeight, fontSize));
    }
    public Label(String text) {
        initialize(fontName, fontWeight, fontSize);
        setText(text);
    }

    public Label() {
        initialize(fontName, fontWeight, fontSize);
    }

    public void setFontName(String fontName) {
        this.fontName = fontName;
        initialize(fontName, fontWeight, fontSize);
    }
    public void setFontSize(int fontSize) {
        this.fontSize = fontSize;
        initialize(fontName, fontWeight, fontSize);
    }
    public void setFontWeight(int fontWeight) {
        this.fontWeight = fontWeight;
        initialize(fontName, fontWeight, fontSize);
    }
}
