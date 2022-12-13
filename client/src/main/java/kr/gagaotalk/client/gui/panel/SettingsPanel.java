package kr.gagaotalk.client.gui.panel;

import kr.gagaotalk.client.authentication.Authentication;
import kr.gagaotalk.client.gui.Colors;
import kr.gagaotalk.client.gui.ResourceManager;
import kr.gagaotalk.client.gui.component.ColoredButton;
import kr.gagaotalk.client.gui.component.HintTextField;
import kr.gagaotalk.client.gui.component.Label;
import kr.gagaotalk.client.gui.component.RoundPictureBox;
import kr.gagaotalk.client.gui.window.ImageIconResizer;

import javax.swing.*;
import java.awt.*;

public class SettingsPanel extends MainWindowPanel {


    public SettingsPanel() {
        super("Settings", "/save.png");

        // 내 프로필 설정하기 label
        Label profileSettingTitleLabel = new Label("Edit my profile");
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
        ColoredButton changeProfilePicButton = new ColoredButton("Change profile", Colors.GACHON_GREEN);
        layout.putConstraint(SpringLayout.NORTH, changeProfilePicButton, 279, SpringLayout.NORTH, this);
        layout.putConstraint(SpringLayout.WEST, changeProfilePicButton, 36, SpringLayout.WEST, this);
        add(changeProfilePicButton);

        // 닉네임 변경
        HintTextField changeNicknameTextField = new HintTextField("Your nickname","",  30);
        changeNicknameTextField.setBorder(
                BorderFactory.createLineBorder(Color.BLACK)
        );
        layout.putConstraint(SpringLayout.NORTH, changeNicknameTextField, 163, SpringLayout.NORTH, this);
        layout.putConstraint(SpringLayout.WEST, changeNicknameTextField, 156, SpringLayout.WEST, this);
        layout.putConstraint(SpringLayout.EAST, changeNicknameTextField, -28, SpringLayout.EAST, this);
        add(changeNicknameTextField);

        // 생일 변경
        HintTextField changeBirthdayTextField = new HintTextField("Your birthday","", 30);
        changeBirthdayTextField.setBorder(
                BorderFactory.createLineBorder(Color.BLACK)
        );
        layout.putConstraint(SpringLayout.NORTH, changeBirthdayTextField, 193, SpringLayout.NORTH, this);
        layout.putConstraint(SpringLayout.WEST, changeBirthdayTextField, 156, SpringLayout.WEST, this);
        layout.putConstraint(SpringLayout.EAST, changeBirthdayTextField, -28, SpringLayout.EAST, this);
        add(changeBirthdayTextField);

        // 상태메시지 변경
        JTextArea changeBioTextField = new JTextArea(5, 30);
        changeBioTextField.setBorder(
                BorderFactory.createLineBorder(Color.BLACK)
        );
        changeBioTextField.setText("Your bio");
        layout.putConstraint(SpringLayout.NORTH, changeBioTextField, 223, SpringLayout.NORTH, this);
        layout.putConstraint(SpringLayout.WEST, changeBioTextField, 156, SpringLayout.WEST, this);
        layout.putConstraint(SpringLayout.EAST, changeBioTextField, -28, SpringLayout.EAST, this);
        add(changeBioTextField);

        // 정보 Label
        Label informationTitleLabel = new Label("About");
        informationTitleLabel.setFontSize(11);
        informationTitleLabel.setFontWeight(Font.BOLD);
        layout.putConstraint(SpringLayout.NORTH, informationTitleLabel, 356, SpringLayout.NORTH, this);
        layout.putConstraint(SpringLayout.WEST, informationTitleLabel, 36, SpringLayout.WEST, this);
        add(informationTitleLabel);


        // 정보 Content
        JTextArea informationContent = new JTextArea(5, 30);
        informationContent.setBorder(null);
        informationContent.setForeground(Color.gray);
        informationContent.setText("2022년 알고리즘 텀 프로젝트 gagaotalk\n참여자: 박주혁, 조우영, 송승환\nhttps://github.com/tmin002/gagaotalk");
        informationContent.setFont(new Font("Dialog", Font.PLAIN, 11));
        informationContent.setEditable(false);
        layout.putConstraint(SpringLayout.NORTH, informationContent, 380, SpringLayout.NORTH, this);
        layout.putConstraint(SpringLayout.WEST, informationContent, 36, SpringLayout.WEST, this);
        layout.putConstraint(SpringLayout.EAST, informationContent, -28, SpringLayout.EAST, this);
        add(informationContent);



    }
}
