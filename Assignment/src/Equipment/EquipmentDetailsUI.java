package Equipment;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import ui.UIConstants;

public class EquipmentDetailsUI extends JDialog {
    private UIConstants uiConst = new UIConstants();

    public EquipmentDetailsUI(Window parent, Equipment equipment) {
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
        nameTextField.setEnabled(false);
        
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
        categoryDropdown.setEnabled(false);
        
        JLabel rentalLabel = new JLabel("Daily Rental Rate");
        rentalLabel.setForeground(Color.WHITE);
        rentalLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        rentalLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        JTextField rentalTextField = new JTextField(30);
        rentalTextField.setAlignmentX(Component.LEFT_ALIGNMENT);
        rentalTextField.setMaximumSize(new Dimension(Integer.MAX_VALUE, rentalTextField.getPreferredSize().height));
        rentalTextField.setText(Float.toString(equipment.getRate()));
//        will change depending on user
        rentalTextField.setEnabled(false);
        
        JLabel statusLabel = new JLabel("Status");
        statusLabel.setForeground(Color.WHITE);
        statusLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        statusLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        String[] statusOptions = {"Available", "Rented Out", "Pending Return Confirmation"};
        
        JComboBox statusDropdown = new JComboBox<>(statusOptions);
        statusDropdown.setAlignmentX(Component.LEFT_ALIGNMENT);
        statusDropdown.setMaximumSize(new Dimension(Integer.MAX_VALUE, statusDropdown.getPreferredSize().height));
        statusDropdown.setSelectedItem(equipment.getStatus());
        statusDropdown.setEnabled(false);
  
//        enabled for resource manager, disabled for normal user        
//        JPanel submitPanel = new JPanel();
//        submitPanel.setBackground(uiConst.LightPurple);
//        JButton submitBtn = new JButton("Submit");
//        submitBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
//        submitBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
//        submitPanel.add(submitBtn);
//        submitPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

//      enabled for normal user, disabled for resource manager
        JPanel selectPanel = new JPanel();
        selectPanel.setBackground(uiConst.LightPurple);
        JButton selectBtn = new JButton("Select");
        selectBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        selectBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        selectPanel.add(selectBtn);
        selectPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
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
//        contentPanel.add(Box.createVerticalStrut(10));
//        contentPanel.add(submitPanel);
        contentPanel.add(Box.createVerticalStrut(10));
        contentPanel.add(selectPanel);
        
        this.add(contentPanel);
        this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        this.setLocationRelativeTo(parent);
    }
}
