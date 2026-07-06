package Equipment;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import ui.UIConstants;
import core.SystemFacade;

public class AddCategoryUI extends JDialog {
    private UIConstants uiConst = new UIConstants();

    public AddCategoryUI(Window parent, SystemFacade facade) {
        super(parent, "Add Category", Dialog.ModalityType.APPLICATION_MODAL);
        this.setSize(800, 600);
        
        // create a content panel to hold everything
        JPanel contentPanel = new JPanel();
        contentPanel.setBackground(uiConst.LightPurple);
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        
        // add 20px padding around the edges so elements don't touch the window borders
        contentPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        // build your UI components
        JLabel headerLabel = new JLabel("Add Category");
        headerLabel.setForeground(Color.WHITE);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 24));
        headerLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        JLabel nameFieldLabel = new JLabel("Set Category Name");
        nameFieldLabel.setForeground(Color.WHITE);
        nameFieldLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        nameFieldLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        JTextField nameTextField = new JTextField(30);
        nameTextField.setAlignmentX(Component.LEFT_ALIGNMENT);
        nameTextField.setMaximumSize(new Dimension(Integer.MAX_VALUE, nameTextField.getPreferredSize().height));
        
        JLabel maintenanceFeeLabel = new JLabel("Set Maintenance Fee");
        maintenanceFeeLabel.setForeground(Color.WHITE);
        maintenanceFeeLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        maintenanceFeeLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        JTextField maintenanceFeeTextField = new JTextField(30);
        maintenanceFeeTextField.setAlignmentX(Component.LEFT_ALIGNMENT);
        maintenanceFeeTextField.setMaximumSize(new Dimension(Integer.MAX_VALUE, maintenanceFeeTextField.getPreferredSize().height));
        
        JLabel discountLabel = new JLabel("Set Category Discount");
        discountLabel.setForeground(Color.WHITE);
        discountLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        discountLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        JTextField discountTextField = new JTextField(30);
        discountTextField.setAlignmentX(Component.LEFT_ALIGNMENT);
        discountTextField.setMaximumSize(new Dimension(Integer.MAX_VALUE, discountTextField.getPreferredSize().height));
        
        JLabel latePenaltyLabel = new JLabel("Set Late Penalty");
        latePenaltyLabel.setForeground(Color.WHITE);
        latePenaltyLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        latePenaltyLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        JTextField latePenaltyField = new JTextField(30);
        latePenaltyField.setAlignmentX(Component.LEFT_ALIGNMENT);
        latePenaltyField.setMaximumSize(new Dimension(Integer.MAX_VALUE, latePenaltyField.getPreferredSize().height));
        
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
        submitBtn.addActionListener(e -> {
            
            String name = (String) nameTextField.getText();
            String fee = (String) maintenanceFeeTextField.getText();
            String discount = (String) discountTextField.getText();
            String latePenalty = (String) latePenaltyField.getText();
            String dmgPenalty = (String) dmgPenaltyField.getText();
            
            try {
                // Send everything as strings to the facade
                facade.addNewCategory(name, fee, discount, latePenalty, dmgPenalty);

                JOptionPane.showMessageDialog(null, "Successfully added Category!");
                dispose();
            } catch (IllegalArgumentException ex) {
                // The facade rejected the input (e.g. empty string, negative rate, or letters instead of numbers)
                JOptionPane.showMessageDialog(null, ex.getMessage(), "Input Validation Error", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                // Catch any database or system-level issues safely
                JOptionPane.showMessageDialog(null, "Failed to add category to system!", "Database Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        submitPanel.add(submitBtn);
        submitPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        contentPanel.add(headerLabel);
        contentPanel.add(Box.createVerticalStrut(20));
        contentPanel.add(nameFieldLabel);
        contentPanel.add(Box.createVerticalStrut(5));
        contentPanel.add(nameTextField);
        contentPanel.add(Box.createVerticalStrut(10));
        contentPanel.add(maintenanceFeeLabel);
        contentPanel.add(Box.createVerticalStrut(5));
        contentPanel.add(maintenanceFeeTextField);
        contentPanel.add(Box.createVerticalStrut(10));
        contentPanel.add(discountLabel);
        contentPanel.add(Box.createVerticalStrut(5));
        contentPanel.add(discountTextField);
        contentPanel.add(Box.createVerticalStrut(10));
        contentPanel.add(latePenaltyLabel);
        contentPanel.add(Box.createVerticalStrut(5));
        contentPanel.add(latePenaltyField);
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
