package ui;

import java.awt.*;
import javax.swing.*;

public class UIConstants {
    
//    // constant images 
public static ImageIcon windowIcon = new ImageIcon(UIConstants.class.getResource("/images/icon.png"));

    // MMlogo rectangle
    public static ImageIcon logoImg = new ImageIcon(UIConstants.class.getResource("/images/mmlogo_horizontal.png"));
    public static Image sizedMMIcon = logoImg.getImage().getScaledInstance(343, 104, Image.SCALE_SMOOTH);
    public static ImageIcon MMlogo = new ImageIcon(sizedMMIcon);

    //MMlogo square 
    public static ImageIcon MMImg_square = new ImageIcon(UIConstants.class.getResource("/images/mmlogo_vertical.png"));
    public static Image sizedMMlogo_square = MMImg_square.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH);
    public static ImageIcon MMlogo_square = new ImageIcon(sizedMMlogo_square);

    // main background image(readily scaled) thats used everywhere
    public static ImageIcon bgImage = new ImageIcon(UIConstants.class.getResource("/images/mainbg.png"));
    public static Image sizedBgImage = bgImage.getImage().getScaledInstance(1366, 768, Image.SCALE_SMOOTH);
    public static ImageIcon mainBg = new ImageIcon(sizedBgImage);
    
    // advertisement
    public static ImageIcon adImage = new ImageIcon(UIConstants.class.getResource("/images/ad.png"));
    public static Image sizedAdImage = adImage.getImage().getScaledInstance(1200, 500, Image.SCALE_SMOOTH);
    public static ImageIcon adImg = new ImageIcon(sizedAdImage);
//    
    //about us
    public static ImageIcon aboutUsImage = new ImageIcon(UIConstants.class.getResource("/images/about.png"));
    public static Image sizedAboutUsImage = aboutUsImage.getImage().getScaledInstance(1200, 200, Image.SCALE_SMOOTH);
    public static ImageIcon aboutUsImg = new ImageIcon(sizedAboutUsImage);
    
    //contact us
    public static ImageIcon contactUsImage = new ImageIcon(UIConstants.class.getResource("/images/contact.png"));
    public static Image sizedContactImage = contactUsImage.getImage().getScaledInstance(1200, 200, Image.SCALE_SMOOTH);
    public static ImageIcon contactImg = new ImageIcon(sizedContactImage);
    
    // //profilepicture 
    // public static ImageIcon pfp = new ImageIcon(UIConstants.class.getResource("/images/profilepic.png"));
    // public static Image sizedpfp = pfp.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);
    // public static ImageIcon pfpIcon = new ImageIcon(sizedpfp);

    // COLOR VARIABLES
    public static Color DarkPurple = new Color(0x540f99);
    public static Color LightPurple = new Color(0xa26ff9);
    public static Color LightBlue = new Color(0xe2f8fd);
    public static Color VeryDarkBlue = new Color(0x02a7c);
    // COLOR VARIABLES

    // DIMENSIONS
    Dimension sidePanelDimension = new Dimension(200, 100);
    Dimension contentPanelDimension = new Dimension(200, 600);
    Dimension topPanelDimension = new Dimension(0, 110);
}
