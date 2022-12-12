package kr.gagaotalk.client.gui.panel;

import kr.gagaotalk.client.authentication.Authentication;
import kr.gagaotalk.client.gui.Colors;
import kr.gagaotalk.client.gui.ResourceManager;
import kr.gagaotalk.client.gui.component.ColoredButton;
import kr.gagaotalk.client.gui.component.Label;
import kr.gagaotalk.client.gui.component.RoundPictureBox;
import kr.gagaotalk.client.gui.window.ImageIconResizer;

import javax.swing.*;
import java.awt.*;

public class SettingsPanel extends MainWindowPanel {


    public SettingsPanel() {
        super("설정");

        // 내 프로필 설정하기 label
        Label profileSettingTitleLabel = new Label("내 프로필 설정하기");
        profileSettingTitleLabel.setFontSize(11);
        profileSettingTitleLabel.setFontWeight(Font.BOLD);

        layout.putConstraint(SpringLayout.NORTH, profileSettingTitleLabel, 115, SpringLayout.NORTH, this);
        layout.putConstraint(SpringLayout.WEST, profileSettingTitleLabel, 36, SpringLayout.WEST, this);
        add(profileSettingTitleLabel);

        // 내 프사 RoundedPictureBox
//        RoundPictureBox myProfilePic = new RoundPictureBox(
//                ImageIconResizer.resize(Authentication.getCurrentUser().userProfilePicture, 100, 100)
//                , 100, 100);
        RoundPictureBox myProfilePic = new RoundPictureBox(
                ImageIconResizer.resize(ResourceManager.getImageIcon("/user_default_profile_pic.png"),
                100, 100)
                , 100, 100);

        layout.putConstraint(SpringLayout.NORTH, myProfilePic, 159, SpringLayout.NORTH, this);
        layout.putConstraint(SpringLayout.WEST, myProfilePic, 36, SpringLayout.WEST, this);
        add(myProfilePic);

        // 이미지 변경 button
        ColoredButton changeProfilePicButton = new ColoredButton("이미지 변경", Colors.GACHON_GREEN);
        layout.putConstraint(SpringLayout.NORTH, changeProfilePicButton, 279, SpringLayout.NORTH, this);
        layout.putConstraint(SpringLayout.WEST, changeProfilePicButton, 36, SpringLayout.WEST, this);
        add(changeProfilePicButton);



    }
}
