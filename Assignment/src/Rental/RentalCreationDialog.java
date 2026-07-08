package Rental;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

import Equipment.Equipment;
import Equipment.EquipmentController;
import ui.UIConstants;

public class RentalCreationDialog extends JDialog {
    private UIConstants uiConst = new UIConstants();
    private JTextField userIdTextField;
    private JTextField equipmentTextField;
    private JSpinner durationSpinner;

    public RentalCreationDialog(Window parent, EquipmentController equipmentInstance, RentalController rentalController) {
        super(parent, "Equipment Rental", Dialog.ModalityType.APPLICATION_MODAL);
        this.setSize(800, 600);

        // create a content panel to hold everything
        JPanel contentPanel = new JPanel();
        contentPanel.setBackground(uiConst.LightPurple);
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));

        // add 20px padding around the edges so elements don't touch the window borders
        contentPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        JLabel headerLabel = new JLabel("Rent an Equipment");
        headerLabel.setForeground(Color.WHITE);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 24));
        headerLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel idLabel = new JLabel("User ID");
        idLabel.setForeground(Color.WHITE);
        idLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        idLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        userIdTextField = new JTextField(30);
        userIdTextField.setAlignmentX(Component.LEFT_ALIGNMENT);
        userIdTextField.setMaximumSize(new Dimension(Integer.MAX_VALUE, userIdTextField.getPreferredSize().height));

        JLabel equipmentFieldLabel = new JLabel("Equipment ID");
        equipmentFieldLabel.setForeground(Color.WHITE);
        equipmentFieldLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        equipmentFieldLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        equipmentTextField = new JTextField(30);
        equipmentTextField.setAlignmentX(Component.LEFT_ALIGNMENT);
        equipmentTextField.setMaximumSize(new Dimension(Integer.MAX_VALUE, equipmentTextField.getPreferredSize().height));

        JLabel durationLabel = new JLabel("Duration (days)");
        durationLabel.setForeground(Color.WHITE);
        durationLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        durationLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        durationSpinner = new JSpinner(new SpinnerNumberModel(1, 1, 365, 1));
        durationSpinner.setAlignmentX(Component.LEFT_ALIGNMENT);
        durationSpinner.setMaximumSize(new Dimension(100, durationSpinner.getPreferredSize().height));

        JPanel confirmPanel = new JPanel();
        confirmPanel.setBackground(uiConst.LightPurple);
        JButton confirmBtn = new JButton("Create");
        confirmBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        confirmBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        confirmBtn.addActionListener(e -> onCreate(equipmentInstance, rentalController));
        confirmPanel.add(confirmBtn);
        confirmPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        contentPanel.add(headerLabel);
        contentPanel.add(Box.createVerticalStrut(20));
        contentPanel.add(idLabel);
        contentPanel.add(Box.createVerticalStrut(5));
        contentPanel.add(userIdTextField);
        contentPanel.add(Box.createVerticalStrut(10));
        contentPanel.add(equipmentFieldLabel);
        contentPanel.add(Box.createVerticalStrut(5));
        contentPanel.add(equipmentTextField);
        contentPanel.add(Box.createVerticalStrut(10));
        contentPanel.add(durationLabel);
        contentPanel.add(Box.createVerticalStrut(5));
        contentPanel.add(durationSpinner);
        contentPanel.add(Box.createVerticalStrut(10));
        contentPanel.add(confirmPanel);

        this.add(contentPanel);
        this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        this.setLocationRelativeTo(parent);
    }

    public void onCreate(EquipmentController equipmentInstance, RentalController rentalController) {
        String userIdText = userIdTextField.getText().trim();
        String equipmentIdText = equipmentTextField.getText().trim();

        if (userIdText.isEmpty() || equipmentIdText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter both User ID and Equipment ID.",
                    "Missing Information", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int userId;
        int equipmentId;
        try {
            userId = Integer.parseInt(userIdText);
            equipmentId = Integer.parseInt(equipmentIdText);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "User ID and Equipment ID must be numbers.",
                    "Invalid Input", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Equipment equipment = equipmentInstance.getEquipmentByID(equipmentId);
        if (equipment == null) {
            JOptionPane.showMessageDialog(this, "No equipment found with ID " + equipmentId + ".",
                    "Invalid Equipment", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int duration = (Integer) durationSpinner.getValue();

        boolean success = rentalController.addRental(userId, equipment, duration);
        if (success) {
            JOptionPane.showMessageDialog(this, "Rental created successfully.");
            this.dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Failed to create rental. Please try again.",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}