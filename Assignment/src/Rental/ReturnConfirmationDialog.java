package Rental;

import java.awt.*;
import java.awt.event.ItemEvent;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

import Equipment.Equipment;
import Billing.BillingController;
import ui.UIConstants;
import core.SystemFacade;

public class ReturnConfirmationDialog extends JDialog {
    private UIConstants uiConst = new UIConstants();
    private boolean isReturned = false;
    private boolean isDamaged = false;
    private BillingController billingInstance;

    public ReturnConfirmationDialog(Window parent, int rentalId, int id, String name, SystemFacade facade) {
        super(parent, "Return Confirmation", Dialog.ModalityType.APPLICATION_MODAL);
        this.setSize(800, 600);
        
        // create a content panel to hold everything
        JPanel contentPanel = new JPanel();
        contentPanel.setBackground(uiConst.LightPurple);
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        
        // add 20px padding around the edges so elements don't touch the window borders
        contentPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        // build your UI components
        JLabel headerLabel = new JLabel("Return Confirmation");
        headerLabel.setForeground(Color.WHITE);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 24));
        headerLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        JLabel idLabel = new JLabel("Equipment ID");
        idLabel.setForeground(Color.WHITE);
        idLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        idLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        JTextField idTextField = new JTextField(30);
        idTextField.setAlignmentX(Component.LEFT_ALIGNMENT);
        idTextField.setMaximumSize(new Dimension(Integer.MAX_VALUE, idTextField.getPreferredSize().height));
        idTextField.setText(Integer.toString(id));
        idTextField.setEnabled(false);
        
        JLabel nameFieldLabel = new JLabel("Equipment Name");
        nameFieldLabel.setForeground(Color.WHITE);
        nameFieldLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        nameFieldLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        JTextField nameTextField = new JTextField(30);
        nameTextField.setAlignmentX(Component.LEFT_ALIGNMENT);
        nameTextField.setMaximumSize(new Dimension(Integer.MAX_VALUE, nameTextField.getPreferredSize().height));
        nameTextField.setText(name);
        nameTextField.setEnabled(false);
        
        JCheckBox returnStatus = new JCheckBox("Equipment returned fully?");
        returnStatus.setBackground(uiConst.LightPurple);
        
        JCheckBox damageStatus = new JCheckBox("Equipment damaged?");
        damageStatus.setEnabled(false);
        
        returnStatus.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                this.isReturned = true;
                damageStatus.setEnabled(true);
            } else {
                damageStatus.setEnabled(false);
                this.isReturned = false;
                this.isDamaged = false;
            }
        });
        
        damageStatus.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                this.isDamaged = true;
            } else  {
                this.isDamaged = false;
            }
        });
        damageStatus.setBackground(uiConst.LightPurple);
        
        JPanel confirmPanel = new JPanel();
        confirmPanel.setBackground(uiConst.LightPurple);
        JButton confirmBtn = new JButton("Confirm");
        confirmBtn.addActionListener(e -> {
            facade.returnEquipmentConfirmation(rentalId, isReturned, isDamaged);
            JOptionPane.showMessageDialog(null, "Successfully returned equipment!");
            dispose();
        });
        confirmBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        confirmBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        confirmBtn.addActionListener(e -> onConfirm(billingInstance, rental));
        confirmPanel.add(confirmBtn);
        confirmPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        contentPanel.add(headerLabel);
        contentPanel.add(Box.createVerticalStrut(20));
        contentPanel.add(idLabel);
        contentPanel.add(Box.createVerticalStrut(5));
        contentPanel.add(idTextField);
        contentPanel.add(Box.createVerticalStrut(10));
        contentPanel.add(nameFieldLabel);
        contentPanel.add(Box.createVerticalStrut(5));
        contentPanel.add(nameTextField);
        contentPanel.add(Box.createVerticalStrut(10));
        contentPanel.add(returnStatus);
        contentPanel.add(Box.createVerticalStrut(10));
        contentPanel.add(damageStatus);
        contentPanel.add(Box.createVerticalStrut(10));
        contentPanel.add(confirmPanel);
        
        this.add(contentPanel);
        this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        this.setLocationRelativeTo(parent);
    }

    public void onConfirm(BillingController billingInstance, Rental rental) {
        if (isDamaged) {
            billingInstance.createDamageBill(rental);
            JOptionPane.showMessageDialog(this, "Equipment returned successfully with damage bill.");
            this.dispose();
        } else if (isReturned){
            JOptionPane.showMessageDialog(this, "Equipment returned successfully.");
            this.dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Failed to confirm return. Please try again.",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
