package ui;
import core.*;
import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;
import User.*;

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
        
        loggedOutButtonState(homepage);

        
        //LOGIN FEATURE
//        dashboard = new DashboardUI();
//        mainPanel.add(dashboard, "DASHBOARD");
//        
//        profile = new UserProfileUI();
//        mainPanel.add(profile, "PROFILE");

        //TEMPORARY
        homepage.login_btn.addActionListener(event -> {
            System.out.println("Login button clicked"); // TEMP debug
            try {
                User testUser = facade.login(1, "test01"); // Elsa, from your seedTestUsers data
                buildLoggedInPanels(testUser, mainPanel, cardlayout);
                loggedInButtonState(homepage);
                cardlayout.show(mainPanel, "DASHBOARD");
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage());
            }
        });
        //TEMPORARY
        
     
        //add Action listeners to the buttons that exist in those pages 
        homepage.dashboard_btn.addActionListener(event -> { cardlayout.show(mainPanel, "DASHBOARD");});
        homepage.profile_btn.addActionListener(event -> { cardlayout.show(mainPanel, "PROFILE"); });

        //action listener for Logout
        homepage.logout_btn.addActionListener(event ->{
            facade.logout();
            loggedOutButtonState(homepage);            
            if(dashboard != null){
                mainPanel.remove(dashboard);
                dashboard = null;
            }
            if(profile != null){
                mainPanel.remove(profile);
                profile = null;
            }
            cardlayout.show(mainPanel, "HOME");
        }
        );
      
        mainPanel.setOpaque(false);
        bgImage.add(mainPanel, BorderLayout.CENTER);
        setContentPane(bgImage);
        setVisible(true);
    }
    
    //TEMPORARY
    private void buildLoggedInPanels(User user, JPanel mainPanel, CardLayout cardlayout) {
        DashboardPanels panelList = new DashboardPanels(facade, user);
        dashboard = new DashboardUI(panelList.returnDashboardPanels());
        profile = new UserProfileUI();

        dashboard.back_btn.addActionListener(e -> cardlayout.show(mainPanel, "HOME"));

        mainPanel.add(dashboard, "DASHBOARD");
        mainPanel.add(profile, "PROFILE");
        mainPanel.revalidate();
        mainPanel.repaint();
    }
    //TEMPORARY
    
    //helper fucntions to show the state of buttons !!!!
        void loggedInButtonState(HomepageUI homepage){
            homepage.dashboard_btn.setVisible(true);
            homepage.login_btn.setVisible(false);
            homepage.logout_btn.setVisible(true);
            homepage.profile_btn.setVisible(true);
        }
        
        void loggedOutButtonState(HomepageUI homepage){
            homepage.dashboard_btn.setVisible(false);
            homepage.login_btn.setVisible(true);
            homepage.logout_btn.setVisible(false);
            homepage.profile_btn.setVisible(false);
        }
}