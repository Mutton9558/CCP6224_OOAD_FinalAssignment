package Rental;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

import ui.UIConstants;

public class EquipmentDamageUI extends JDialog{
    private UIConstants uiConst = new UIConstants();

    public EquipmentDamageUI(Window parent, Rental rental) {
        super(parent, "Equipment Status Report", Dialog.ModalityType.APPLICATION_MODAL);
        this.setSize(800, 600);
        
        JPanel contentPanel = new JPanel();
        contentPanel.setBackground(uiConst.LightPurple);
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        
        contentPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        JLabel headerLabel = new JLabel("Equipment Status");
        headerLabel.setForeground(Color.WHITE);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 24));
        headerLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        JLabel statusFieldLabel = new JLabel("Set Equipment Status");
        statusFieldLabel.setForeground(Color.WHITE);
        statusFieldLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        statusFieldLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        JTextField statusTextField = new JTextField(30);
        statusTextField.setAlignmentX(Component.LEFT_ALIGNMENT);
        statusTextField.setMaximumSize(new Dimension(Integer.MAX_VALUE, statusTextField.getPreferredSize().height));
        
        JLabel dmgPenaltyLabel = new JLabel("Set Damage Penalty");
        dmgPenaltyLabel.setForeground(Color.WHITE);
        dmgPenaltyLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        dmgPenaltyLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        JTextField dmgPenaltyField = new JTextField(30);
        dmgPenaltyField.setAlignmentX(Component.LEFT_ALIGNMENT);
        dmgPenaltyField.setMaximumSize(new Dimension(Integer.MAX_VALUE, dmgPenaltyField.getPreferredSize().height));
        
        JPanel submitPanel = new JPanel();
        submitPanel.setBackground(uiConst.LightPurple);
        JButton submitBtn = new JButton("Submit");
        submitBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        submitBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        submitPanel.add(submitBtn);
        submitPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        contentPanel.add(headerLabel);
        contentPanel.add(Box.createVerticalStrut(20));
        contentPanel.add(statusFieldLabel);
        contentPanel.add(Box.createVerticalStrut(5));
        contentPanel.add(statusTextField);
        contentPanel.add(Box.createVerticalStrut(10));
        contentPanel.add(Box.createVerticalStrut(10));
        contentPanel.add(dmgPenaltyLabel);
        contentPanel.add(Box.createVerticalStrut(5));
        contentPanel.add(dmgPenaltyField);
        contentPanel.add(Box.createVerticalStrut(10));
        contentPanel.add(submitPanel);
        
        this.add(contentPanel);
        this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        this.setLocationRelativeTo(parent);
    }
}
