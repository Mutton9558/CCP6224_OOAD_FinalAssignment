package Equipment;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import ui.UIConstants;
import core.SystemFacade;
import java.util.List;

public class AddEquipmentUI extends JDialog {
    private UIConstants uiConst = new UIConstants();

    public AddEquipmentUI(Window parent, String defaultCategory, SystemFacade facade) {
        super(parent, "Add Equipment", Dialog.ModalityType.APPLICATION_MODAL);
        this.setSize(800, 600);
        
        // create a content panel to hold everything
        JPanel contentPanel = new JPanel();
        contentPanel.setBackground(uiConst.LightPurple);
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        
        // add 20px padding around the edges so elements don't touch the window borders
        contentPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        JLabel headerLabel = new JLabel("Add Equipment");
        headerLabel.setForeground(Color.WHITE);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 24));
        headerLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        JLabel nameFieldLabel = new JLabel("Set Equipment Name");
        nameFieldLabel.setForeground(Color.WHITE);
        nameFieldLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        nameFieldLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        JTextField nameTextField = new JTextField(30);
        nameTextField.setAlignmentX(Component.LEFT_ALIGNMENT);
        nameTextField.setMaximumSize(new Dimension(Integer.MAX_VALUE, nameTextField.getPreferredSize().height));
        
        JLabel categoryLabel = new JLabel("Select Category");
        categoryLabel.setForeground(Color.WHITE);
        categoryLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        categoryLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        SystemFacade.AddEquipmentContext data = facade.getAddEquipmentContext();
        
        
//        String[] categoryOptions = {"Camera and Lenses", "Lighting"};
        
        JComboBox categoryDropdown = new JComboBox<>(data.categoryNames().toArray());
        categoryDropdown.setAlignmentX(Component.LEFT_ALIGNMENT);
        categoryDropdown.setMaximumSize(new Dimension(Integer.MAX_VALUE, categoryDropdown.getPreferredSize().height));
        categoryDropdown.setSelectedItem(defaultCategory);
        
        JLabel rentalLabel = new JLabel("Set Daily Rental Rate");
        rentalLabel.setForeground(Color.WHITE);
        rentalLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        rentalLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        JTextField rentalTextField = new JTextField(30);
        rentalTextField.setAlignmentX(Component.LEFT_ALIGNMENT);
        rentalTextField.setMaximumSize(new Dimension(Integer.MAX_VALUE, rentalTextField.getPreferredSize().height));
        
        JLabel statusLabel = new JLabel("Select Status");
        statusLabel.setForeground(Color.WHITE);
        statusLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        statusLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        String[] statusOptions = {"Available", "Rented Out", "Pending Return Confirmation"};
        
        JComboBox statusDropdown = new JComboBox<>(statusOptions);
        statusDropdown.setAlignmentX(Component.LEFT_ALIGNMENT);
        statusDropdown.setMaximumSize(new Dimension(Integer.MAX_VALUE, statusDropdown.getPreferredSize().height));
        
        JPanel submitPanel = new JPanel();
        submitPanel.setBackground(uiConst.LightPurple);
        JButton submitBtn = new JButton("Submit");
        submitBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        submitBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        submitBtn.addActionListener(e -> {
            // Pure data extraction, no data conversions are happening here anymore
            String name = nameTextField.getText();
            String categoryName = (String) categoryDropdown.getSelectedItem();
            String rentalRate = rentalTextField.getText();
            String status = (String) statusDropdown.getSelectedItem();

            try {
                // Send everything as strings to the facade
                facade.addNewEquipment(name, categoryName, rentalRate, status);

                JOptionPane.showMessageDialog(null, "Successfully added equipment");
                dispose();
            } catch (IllegalArgumentException ex) {
                // The facade rejected the input (e.g. empty string, negative rate, or letters instead of numbers)
                JOptionPane.showMessageDialog(null, ex.getMessage(), "Input Validation Error", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                // Catch any database or system-level issues safely
                JOptionPane.showMessageDialog(null, "Failed to add equipment to system!", "Database Error", JOptionPane.ERROR_MESSAGE);
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
        contentPanel.add(categoryLabel);
        contentPanel.add(Box.createVerticalStrut(5));
        contentPanel.add(categoryDropdown);
        contentPanel.add(Box.createVerticalStrut(10));
        contentPanel.add(rentalLabel);
        contentPanel.add(Box.createVerticalStrut(5));
        contentPanel.add(rentalTextField);
        contentPanel.add(Box.createVerticalStrut(10));
        contentPanel.add(statusLabel);
        contentPanel.add(Box.createVerticalStrut(5));
        contentPanel.add(statusDropdown);
        contentPanel.add(Box.createVerticalStrut(10));
        contentPanel.add(submitPanel);
        
        this.add(contentPanel);
        this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        this.setLocationRelativeTo(parent);
    }
}
