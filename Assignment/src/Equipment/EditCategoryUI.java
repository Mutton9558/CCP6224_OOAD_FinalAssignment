package Equipment;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import ui.UIConstants;
import core.SystemFacade;

public class EditCategoryUI extends JDialog {
    private UIConstants uiConst = new UIConstants();

    public EditCategoryUI(Window parent, Category category, SystemFacade facade) {
        super(parent, "Edit Category", Dialog.ModalityType.APPLICATION_MODAL);
        this.setSize(800, 600);
        
        // create a content panel to hold everything
        JPanel contentPanel = new JPanel();
        contentPanel.setBackground(uiConst.LightPurple);
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        
        // add 20px padding around the edges so elements don't touch the window borders
        contentPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        // build your UI components
        JLabel headerLabel = new JLabel("Edit Category");
        headerLabel.setForeground(Color.WHITE);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 24));
        headerLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        JLabel nameFieldLabel = new JLabel("Name");
        nameFieldLabel.setForeground(Color.WHITE);
        nameFieldLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        nameFieldLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        JTextField nameTextField = new JTextField(30);
        nameTextField.setAlignmentX(Component.LEFT_ALIGNMENT);
        nameTextField.setMaximumSize(new Dimension(Integer.MAX_VALUE, nameTextField.getPreferredSize().height));
        nameTextField.setText(category.getName());
        nameTextField.setEnabled(false);
        
        JLabel maintenanceFeeLabel = new JLabel("Edit Maintenance Fee");
        maintenanceFeeLabel.setForeground(Color.WHITE);
        maintenanceFeeLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        maintenanceFeeLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        JTextField maintenanceFeeTextField = new JTextField(30);
        maintenanceFeeTextField.setAlignmentX(Component.LEFT_ALIGNMENT);
        maintenanceFeeTextField.setMaximumSize(new Dimension(Integer.MAX_VALUE, maintenanceFeeTextField.getPreferredSize().height));
        maintenanceFeeTextField.setText(Float.toString(category.getFee()));
        
        JLabel latePenaltyLabel = new JLabel("Edit Late Penalty");
        latePenaltyLabel.setForeground(Color.WHITE);
        latePenaltyLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        latePenaltyLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        JTextField latePenaltyField = new JTextField(30);
        latePenaltyField.setAlignmentX(Component.LEFT_ALIGNMENT);
        latePenaltyField.setMaximumSize(new Dimension(Integer.MAX_VALUE, latePenaltyField.getPreferredSize().height));
        latePenaltyField.setText(Float.toString(category.getLatePenalty()));
        
        JLabel dmgPenaltyLabel = new JLabel("Edit Damage Penalty");
        dmgPenaltyLabel.setForeground(Color.WHITE);
        dmgPenaltyLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        dmgPenaltyLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        JTextField dmgPenaltyField = new JTextField(30);
        dmgPenaltyField.setAlignmentX(Component.LEFT_ALIGNMENT);
        dmgPenaltyField.setMaximumSize(new Dimension(Integer.MAX_VALUE, dmgPenaltyField.getPreferredSize().height));
        dmgPenaltyField.setText(Float.toString(category.getDamagePenalty()));
        
        JPanel submitPanel = new JPanel();
        submitPanel.setBackground(uiConst.LightPurple);
        JButton submitBtn = new JButton("Submit");
        submitBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        submitBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        submitBtn.addActionListener(e -> {
            String newFee = (String) maintenanceFeeTextField.getText();
            String newLatePenalty = (String) latePenaltyField.getText();
            String newDamagePenalty = (String) dmgPenaltyField.getText();
            
            try{
                facade.editCategory(category.getId(), newFee, newLatePenalty, newDamagePenalty);
                JOptionPane.showMessageDialog(null, "Successfully edited Category!");
                dispose();
            } catch (IllegalArgumentException ex){
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, ex.getMessage(), "Input Validation Error", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex){
                JOptionPane.showMessageDialog(null, "Failed to edit category to system!", "Database Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        
        JButton deleteBtn = new JButton("Delete");
        deleteBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        deleteBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        deleteBtn.addActionListener(e -> {
            boolean success = facade.deleteCategory(category.getId());
            if(success){
                JOptionPane.showMessageDialog(null, "Successfully deleted category!");
                dispose();
            } else {
                JOptionPane.showMessageDialog(null, "Failed to delete Category", "Database Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        submitPanel.add(submitBtn);
        submitPanel.add(deleteBtn);
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
