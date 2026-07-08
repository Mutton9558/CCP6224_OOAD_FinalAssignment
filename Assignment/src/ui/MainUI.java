package ui;
import core.*;
import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;

public class MainUI extends JFrame {
    
    private DashboardUI dashboard;
    private UserProfileUI profile;
    private HomepageUI homepage;
    private final core.SystemFacade facade;

    public MainUI(core.SystemFacade facade){
        
        super("MaoMao University Equipment Rental Service!");
        this.facade = facade;
        CardLayout cardlayout = new CardLayout();
        UIConstants UIConst = new UIConstants();
        
        JLabel bgImage = new JLabel(UIConstants.mainBg);
        bgImage.setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1366, 768);
        setResizable(false);
        
        //window icon
        setIconImage(UIConst.windowIcon.getImage());
        JPanel mainPanel = new JPanel(cardlayout);
        
        homepage = new HomepageUI();
        mainPanel.add(homepage, "HOME");
        
        //always show homepage first 
        cardlayout.show(mainPanel, "HOME");
        
        dashboard = new DashboardUI();
        mainPanel.add(dashboard, "DASHBOARD");
        
        profile = new UserProfileUI();
        mainPanel.add(profile, "PROFILE");

        mainPanel.setOpaque(false);
        bgImage.add(mainPanel, BorderLayout.CENTER);
        setContentPane(bgImage);
        setVisible(true);
    
    }
        //RUNNER TEMPORARY 
        public static void main(String[] args){
                        
        }
}