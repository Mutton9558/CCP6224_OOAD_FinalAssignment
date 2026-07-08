package Equipment;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import ui.UIConstants;
import core.SystemFacade;
import Rental.RentalCreationDialog;

public class EquipmentDetailsUI extends JDialog {
    private UIConstants uiConst = new UIConstants();

    public EquipmentDetailsUI(Window parent, Equipment equipment, boolean canEdit, SystemFacade facade) {
        super(parent, "Add Equipment", Dialog.ModalityType.APPLICATION_MODAL);
        this.setSize(800, 600);
        
        // create a content panel to hold everything
        JPanel contentPanel = new JPanel();
        contentPanel.setBackground(uiConst.LightPurple);
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        
        // add 20px padding around the edges so elements don't touch the window borders
        contentPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        // build your UI components
        JLabel headerLabel = new JLabel("Equipment Details");
        headerLabel.setForeground(Color.WHITE);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 24));
        headerLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        JLabel nameFieldLabel = new JLabel("Equipment Name");
        nameFieldLabel.setForeground(Color.WHITE);
        nameFieldLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        nameFieldLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        JTextField nameTextField = new JTextField(30);
        nameTextField.setAlignmentX(Component.LEFT_ALIGNMENT);
        nameTextField.setMaximumSize(new Dimension(Integer.MAX_VALUE, nameTextField.getPreferredSize().height));
        nameTextField.setText(equipment.getName());
//        will change depending on user
        nameTextField.setEnabled(canEdit);
        
        JLabel categoryLabel = new JLabel("Category");
        categoryLabel.setForeground(Color.WHITE);
        categoryLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        categoryLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        String[] categoryOptions = {"Camera and Lenses", "Lighting"};
        
        JComboBox categoryDropdown = new JComboBox<>(categoryOptions);
        categoryDropdown.setAlignmentX(Component.LEFT_ALIGNMENT);
        categoryDropdown.setMaximumSize(new Dimension(Integer.MAX_VALUE, categoryDropdown.getPreferredSize().height));
        categoryDropdown.setSelectedItem(equipment.getCategory());
//        will change depending on user
        categoryDropdown.setEnabled(canEdit);
        
        JLabel rentalLabel = new JLabel("Daily Rental Rate");
        rentalLabel.setForeground(Color.WHITE);
        rentalLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        rentalLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        JTextField rentalTextField = new JTextField(30);
        rentalTextField.setAlignmentX(Component.LEFT_ALIGNMENT);
        rentalTextField.setMaximumSize(new Dimension(Integer.MAX_VALUE, rentalTextField.getPreferredSize().height));
        rentalTextField.setText(Float.toString(equipment.getRate()));
//        will change depending on user
        rentalTextField.setEnabled(canEdit);
        
        JLabel categoryMaintenanceFee = new JLabel("Category Maintenance Fee");
        categoryMaintenanceFee.setForeground(Color.WHITE);
        categoryMaintenanceFee.setFont(new Font("Arial", Font.PLAIN, 16));
        categoryMaintenanceFee.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        JTextField maintenanceTextField = new JTextField(30);
        maintenanceTextField.setAlignmentX(Component.LEFT_ALIGNMENT);
        maintenanceTextField.setMaximumSize(new Dimension(Integer.MAX_VALUE, rentalTextField.getPreferredSize().height));
        maintenanceTextField.setText(Float.toString(equipment.getCategory().getFee()));
//        will change depending on user
        maintenanceTextField.setEnabled(false);
        
        JLabel statusLabel = new JLabel("Status");
        statusLabel.setForeground(Color.WHITE);
        statusLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        statusLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        String[] statusOptions = {"Available", "Rented Out", "Pending Return Confirmation"};
        
        JComboBox statusDropdown = new JComboBox<>(statusOptions);
        statusDropdown.setAlignmentX(Component.LEFT_ALIGNMENT);
        statusDropdown.setMaximumSize(new Dimension(Integer.MAX_VALUE, statusDropdown.getPreferredSize().height));
        statusDropdown.setSelectedItem(equipment.getStatus());
        statusDropdown.setEnabled(canEdit);
        
        JPanel submitPanel = new JPanel();
        submitPanel.setBackground(uiConst.LightPurple);
        JButton submitBtn = new JButton("Submit");
        submitBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        submitBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        submitBtn.addActionListener(e -> {
//            edit detail
            String rentalRate = (String) rentalTextField.getText();
            String status = (String) statusDropdown.getSelectedItem();
            
            try {
                // Send everything as strings to the facade
                facade.editEquipment(equipment.getId(), rentalRate, status);

                JOptionPane.showMessageDialog(null, "Successfully edited equipment");
                dispose();
            } catch (IllegalArgumentException ex) {
                // The facade rejected the input (e.g. empty string, negative rate, or letters instead of numbers)
                JOptionPane.showMessageDialog(null, ex.getMessage(), "Input Validation Error", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                // Catch any database or system-level issues safely
                JOptionPane.showMessageDialog(null, "Failed to edit equipment in system!", "Database Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        
        JButton deleteBtn = new JButton("Delete");
        deleteBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        deleteBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        deleteBtn.addActionListener(e -> {
            boolean success = facade.deleteEquipment(equipment.getId());
            if(success){
                JOptionPane.showMessageDialog(null, "Successfully deleted equipment");
                dispose();
            } else {
                JOptionPane.showMessageDialog(null, "Failed to delete equipment!", "Deletion Error", JOptionPane.ERROR_MESSAGE);
            }
            
        });
        submitPanel.add(submitBtn);
        submitPanel.add(deleteBtn);
        submitPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        submitPanel.setVisible(canEdit);

//      enabled for normal user, disabled for resource manager
        JPanel selectPanel = new JPanel();
        selectPanel.setBackground(uiConst.LightPurple);
        JButton selectBtn = new JButton("Rent");
        selectBtn.addActionListener(e -> {
            Window parent2 = SwingUtilities.getWindowAncestor(this);
            JDialog addRental = new RentalCreationDialog(parent2, facade, equipment.getId());
            facade.editEquipment(equipment.getId(), String.valueOf(equipment.getRate()), "Rented Out");
            addRental.setVisible(true);
        });
        selectBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        selectBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        selectPanel.add(selectBtn);
        selectPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        selectPanel.setVisible(!canEdit);
        
        contentPanel.add(headerLabel);
        contentPanel.add(Box.createVerticalStrut(20));
        contentPanel.add(nameFieldLabel);
        contentPanel.add(Box.createVerticalStrut(5));
        contentPanel.add(nameTextField);
        contentPanel.add(Box.createVerticalStrut(10));
        contentPanel.add(categoryLabel);
        contentPanel.add(Box.createVerticalStrut(5));
        contentPanel.add(categoryDropdown);
        contentPanel.add(Box.createVerticalStrut(10));
        contentPanel.add(rentalLabel);
        contentPanel.add(Box.createVerticalStrut(5));
        contentPanel.add(rentalTextField);
        contentPanel.add(Box.createVerticalStrut(10));
        contentPanel.add(categoryMaintenanceFee);
        contentPanel.add(Box.createVerticalStrut(5));
        contentPanel.add(maintenanceTextField);
        contentPanel.add(Box.createVerticalStrut(10));
        contentPanel.add(statusLabel);
        contentPanel.add(Box.createVerticalStrut(5));
        contentPanel.add(statusDropdown);
        contentPanel.add(Box.createVerticalStrut(10));
        contentPanel.add(submitPanel);
        contentPanel.add(Box.createVerticalStrut(10));
        contentPanel.add(selectPanel);
        
        this.add(contentPanel);
        this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        this.setLocationRelativeTo(parent);
    }
}
