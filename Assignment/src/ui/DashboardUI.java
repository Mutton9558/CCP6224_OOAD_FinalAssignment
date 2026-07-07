package ui;
import javax.swing.*;
import java.awt.*;

public class DashboardUI extends JPanel{
    
    CardLayout cardlayout1 = new CardLayout();
    CardLayout cardlayout2 = new CardLayout();
    JPanel westPanel, contentPanel, mainPanel, northPanel;
    public JButton back_btn = new JButton("Back");

    public DashboardUI(List<DashboardPanel> dashboardPanels){
        
        UIConstants UIConst = new UIConstants();
        setLayout(new BorderLayout());
        
        westPanel = new JPanel(cardlayout1)S;
        contentPanel = new JPanel(cardlayout2);
        mainPanel = new JPanel(new BorderLayout());
        
        //INViSISBLE SPACE TO SEE THE BG IMAGE
        northPanel = new JPanel();
        northPanel.setOpaque(false);
        northPanel.setPreferredSize(UIConst.topPanelDimension);
        
        westPanel.setOpaque(false);
        contentPanel.setBackground(UIConst.LightPurple);
        mainPanel.setBackground(UIConst.LightPurple);
        
        westPanel.setPreferredSize(UIConst.sidePanelDimension);
        
        //set all the subpanels tp the mainPanel
        mainPanel.add(contentPanel, BorderLayout.CENTER);
        mainPanel.add(westPanel, BorderLayout.WEST);
        
        JLabel MMlogo_square = new JLabel(UIConst.MMlogo_square);
        JPanel sidePanel = new JPanel();
        sidePanel.setLayout(new BoxLayout(sidePanel, BoxLayout.Y_AXIS));
        sidePanel.setBackground(UIConst.DarkPurple);
        sidePanel.add(MMlogo_square);
        
     // shawn's og code in previous to be reused
     // Iterates over dashboard panels and creates the appropriate side button and content panel
//        for(DashboardPanel i : dashboardPanels){
//           JButton sidebarButton = new JButton(i.getName());
//           sidebarButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, sidebarButton.getPreferredSize().height));
//           sidebarButton.setVisible(i.isVisible());
//           contentPanel.add(i.getPanel(), i.getName());
//           sidebarButton.addActionListener(event -> {
//                cardlayout2.show(contentPanel, i.getName());
//            });
//           sidePanel.add(sidebarButton);
//        }
        back_btn.setMaximumSize(new Dimension(Integer.MAX_VALUE, back_btn.getPreferredSize().height));
        back_btn.setVisible(true);
        sidePanel.add(back_btn);
        
        westPanel.add(sidePanel, "sidePanel");
        cardlayout1.show(westPanel, "sidePanel");

        add(northPanel, BorderLayout.NORTH);
        add(mainPanel, BorderLayout.CENTER); 
    }
}