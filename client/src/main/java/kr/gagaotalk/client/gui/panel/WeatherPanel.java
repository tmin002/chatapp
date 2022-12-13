package kr.gagaotalk.client.gui.panel;

import kr.gagaotalk.client.gui.ResourceManager;
import kr.gagaotalk.client.gui.component.Label;
import kr.gagaotalk.client.gui.component.PictureBox;
import kr.gagaotalk.client.gui.window.ImageIconResizer;
import kr.gagaotalk.client.gui.window.MainWindow;

import javax.swing.*;
import java.awt.*;

public class WeatherPanel extends MainWindowPanel {


    private ImageIcon cloudyImageIcon = ImageIconResizer.resize(
            ResourceManager.getImageIcon("/cloudy.png"),
            50, 50
    );
    private ImageIcon rainyImageIcon = ImageIconResizer.resize(
            ResourceManager.getImageIcon("/raining.png"),
            50, 50
    );
    private ImageIcon partlyCloudyImageIcon = ImageIconResizer.resize(
            ResourceManager.getImageIcon("/littlecloudy.png"),
            50, 50
    );
    private ImageIcon snowyImageIcon = ImageIconResizer.resize(
            ResourceManager.getImageIcon("/snowing.png"),
            50, 50
    );
    private ImageIcon snowAndRainImageIcon = ImageIconResizer.resize(
            ResourceManager.getImageIcon("/snowandrain.png"),
            50, 50
    );

    private SpringLayout contentPanelLayout = new SpringLayout();
    private JPanel contentPanel = new JPanel(contentPanelLayout);
    private JPanel contentPanelWrapper = new JPanel(new GridBagLayout());
    private JScrollPane contentPanelScrollPane = new JScrollPane(contentPanelWrapper);
    private PictureBox weatherStatusPictureBox = new PictureBox(rainyImageIcon, 50, 50);
    private Label temperatureLabel = new Label("10°C");
    public WeatherPanel(MainWindow parent) {

        // Initialize
        super("Weather");
        super.titleLabel.setVisible(false);
        setBackground(new Color(0,0,0,0));

        // Init new titleLabel
        Label newTitleLabel = new Label("Weather");
        layout.putConstraint(SpringLayout.WEST, newTitleLabel,36, SpringLayout.WEST, this);
        layout.putConstraint(SpringLayout.NORTH, newTitleLabel,54, SpringLayout.NORTH, this);
        newTitleLabel.setFont(new Font("Dialog", Font.BOLD, 24));
        newTitleLabel.setForeground(Color.white);
        add(newTitleLabel);

        // Init rootPanel
        contentPanelScrollPane.setBackground(new Color(0,0,0,0));
        contentPanelScrollPane.setBorder(null);
        layout.putConstraint(SpringLayout.NORTH, contentPanelScrollPane, 69, SpringLayout.NORTH, this);
        layout.putConstraint(SpringLayout.SOUTH, contentPanelScrollPane, 0, SpringLayout.SOUTH, this);
        layout.putConstraint(SpringLayout.WEST, contentPanelScrollPane, 0, SpringLayout.WEST, this);
        layout.putConstraint(SpringLayout.EAST, contentPanelScrollPane, 0, SpringLayout.EAST, this);
        add(contentPanelScrollPane);

        // Init contentPanelWrapper
        contentPanelWrapper.setBackground(new Color(0, 0, 0, 0));

        // Init contentPanel
        contentPanel.setBackground(new Color(0,0,0,0));
        contentPanel.setPreferredSize(new Dimension(200,200));
        contentPanelWrapper.add(contentPanel);

        // Add weather picture box
        contentPanelLayout.putConstraint(SpringLayout.NORTH, weatherStatusPictureBox, 4, SpringLayout.NORTH, this);
        contentPanelLayout.putConstraint(SpringLayout.WEST, weatherStatusPictureBox, 22, SpringLayout.WEST, this);
        contentPanel.add(weatherStatusPictureBox);

        // Add temperature label
        contentPanelLayout.putConstraint(SpringLayout.NORTH, temperatureLabel, 2, SpringLayout.NORTH, this);
        contentPanelLayout.putConstraint(SpringLayout.WEST, temperatureLabel, 84, SpringLayout.WEST, this);
        temperatureLabel.setFontSize(48);
        temperatureLabel.setFontWeight(Font.BOLD);
        temperatureLabel.setForeground(Color.white);
        contentPanel.add(temperatureLabel);

        // Add asdfasdfsaf
        WeatherHourlyAtom wha = new WeatherHourlyAtom("19시", "맑음");
        contentPanelLayout.putConstraint(SpringLayout.NORTH, wha, 94, SpringLayout.NORTH, this);
        contentPanelLayout.putConstraint(SpringLayout.WEST, wha, 0, SpringLayout.WEST, this);
        contentPanelLayout.putConstraint(SpringLayout.EAST, wha, 0, SpringLayout.EAST, this);
        contentPanel.add(wha);

    }

    public void updateWeather() {

    }

}

class WeatherHourlyAtom extends JPanel {
    public WeatherHourlyAtom(String hourString, String weather) {

        // Initialize
        setPreferredSize(new Dimension(200, 41));
        setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.white));
        setBackground(new Color(0, 0, 0, 0));
        setLayout(null);

        // Add hour string
        Label hourLabel = new Label(hourString);
        hourLabel.setForeground(Color.white);
        hourLabel.setFontSize(15);
        hourLabel.setBounds(5, 14, 70, 17);
        add(hourLabel);

        // Add weather img
        PictureBox weatherImage = new PictureBox(ResourceManager.getImageIcon("/raining.png"), 18, 18);
        weatherImage.setBounds(179, 14, 18, 18);
        add(weatherImage);
    }
}
