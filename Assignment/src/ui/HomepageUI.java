package ui;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HomepageUI extends JPanel{
    
    public JButton contact_btn, about_btn, login_btn, logout_btn, dashboard_btn, profile_btn;
    private JDialog aboutDialog, contactDialog;
    
    public HomepageUI(){
        
        UIConstants UIConst = new UIConstants();
        setLayout(new BorderLayout(0, 48));
        setOpaque(false);
        
        JPanel upperPanel = new JPanel(new BorderLayout());
        upperPanel.setBackground(UIConst.DarkPurple);
        upperPanel.setPreferredSize(new Dimension(100, 140));
        
        JPanel upperPanel_left = new JPanel(new FlowLayout());
        upperPanel_left.setBackground(UIConst.DarkPurple);
        
        //logo place at the left of upper panel 
        JButton MaoMaoLogo = new JButton(UIConst.MMlogo);
        MaoMaoLogo.setBorderPainted(false);
        MaoMaoLogo.setContentAreaFilled(false);
        MaoMaoLogo.setFocusPainted(false);
        MaoMaoLogo.setOpaque(false);
        
        upperPanel_left.add(MaoMaoLogo);
        upperPanel.add(upperPanel_left, BorderLayout.WEST);
        
        //BUTTONS
        contact_btn = new JButton("Contact Us");
        contact_btn.setBackground(UIConst.DarkPurple);
        contact_btn.setForeground(Color.WHITE);

        about_btn = new JButton("About Us");
        about_btn.setBackground(UIConst.DarkPurple);
        about_btn.setForeground(Color.WHITE);

        logout_btn = new JButton("Logout");
        logout_btn.setBackground(UIConst.DarkPurple);
        logout_btn.setForeground(Color.WHITE);

        login_btn = new JButton("Login");
        login_btn.setBackground(UIConst.DarkPurple);
        login_btn.setForeground(Color.WHITE);

        dashboard_btn = new JButton("Dashboard");
        dashboard_btn.setBackground(UIConst.DarkPurple);
        dashboard_btn.setForeground(Color.WHITE);
        
        profile_btn = new JButton("My Profile");
        profile_btn.setBackground(UIConst.DarkPurple);
        profile_btn.setForeground(Color.WHITE);
        
        JPanel upperPanel_right = new JPanel(new FlowLayout());
        upperPanel_right.setBackground(UIConst.DarkPurple);
        upperPanel_right.setPreferredSize(new Dimension(100, 40));
        upperPanel_right.add(contact_btn);
        upperPanel_right.add(about_btn);
        upperPanel_right.add(logout_btn);
        upperPanel_right.add(login_btn);
        upperPanel_right.add(dashboard_btn);
        upperPanel_right.add(profile_btn);
        upperPanel.add(upperPanel_right, BorderLayout.SOUTH);
        
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        JLabel greetingLbl = new JLabel("Search for Equipment, easily rent, and many more!");
        greetingLbl.setForeground(UIConstants.DarkPurple);
        greetingLbl.setFont(new Font("Arial", Font.PLAIN, 18));
        JLabel adLbl = new JLabel(UIConstants.adImg);     
        
        centerPanel.setBorder(BorderFactory.createEmptyBorder(20,40,20,40));
        greetingLbl.setAlignmentX(Component.CENTER_ALIGNMENT);
        adLbl.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        //JDIALOGS
        //get the parent frame 
        Frame parent = (Frame)SwingUtilities.getWindowAncestor(this);
        aboutDialog = new JDialog(parent, "About Us", true);
        aboutDialog.setSize(1200, 200);
        aboutDialog.setLocationRelativeTo(parent);
        aboutDialog.setResizable(false);
        JLabel abtLbl = new JLabel(UIConstants.aboutUsImg);
        aboutDialog.add(abtLbl);
        aboutDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        
        contactDialog = new JDialog(parent, "About Us", true);
        contactDialog.setSize(1200, 200);
        contactDialog.setLocationRelativeTo(parent);
        contactDialog.setResizable(false);
        JLabel contactLbl = new JLabel(UIConstants.contactImg);
        contactDialog.add(contactLbl);
        contactDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        // JDIALOGS
        
        // ABT AND CONTACT US listeners
        about_btn.addActionListener(e->{aboutDialog.setVisible(true);});
        contact_btn.addActionListener(e->{contactDialog.setVisible(true);});
        // ABT AND CONTACT US listeners

        centerPanel.setBackground(UIConst.LightPurple);
        centerPanel.add(greetingLbl);
        centerPanel.add(adLbl);
        add(upperPanel, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);

    }
}
