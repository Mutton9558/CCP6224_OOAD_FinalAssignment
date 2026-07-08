package Rental;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

import Equipment.Equipment;
import core.SystemFacade;
import ui.UIConstants;

public class RentalCreationDialog extends JDialog {
    private UIConstants uiConst = new UIConstants();
    private JTextField userIdTextField;
    private JSpinner durationSpinner;
    private SystemFacade facade;
    private int equipment_id;

    public RentalCreationDialog(Window parent, SystemFacade facade, int equipment_id) {
        super(parent, "Equipment Rental", Dialog.ModalityType.APPLICATION_MODAL);
        this.setSize(800, 600);
        this.facade = facade;
        this.equipment_id = equipment_id;

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

//        JLabel idLabel = new JLabel("User ID");
//        idLabel.setForeground(Color.WHITE);
//        idLabel.setFont(new Font("Arial", Font.PLAIN, 16));
//        idLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
//
//        userIdTextField = new JTextField(30);
//        userIdTextField.setAlignmentX(Component.LEFT_ALIGNMENT);
//        userIdTextField.setMaximumSize(new Dimension(Integer.MAX_VALUE, userIdTextField.getPreferredSize().height));

//        JLabel equipmentFieldLabel = new JLabel("Equipment ID");
//        equipmentFieldLabel.setForeground(Color.WHITE);
//        equipmentFieldLabel.setFont(new Font("Arial", Font.PLAIN, 16));
//        equipmentFieldLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
//
//        equipmentTextField = new JTextField(30);
//        equipmentTextField.setAlignmentX(Component.LEFT_ALIGNMENT);
//        equipmentTextField.setMaximumSize(new Dimension(Integer.MAX_VALUE, equipmentTextField.getPreferredSize().height));

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
        confirmBtn.addActionListener(e -> onCreate());
        confirmPanel.add(confirmBtn);
        confirmPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        contentPanel.add(headerLabel);
        contentPanel.add(Box.createVerticalStrut(20));
//        contentPanel.add(idLabel);
//        contentPanel.add(Box.createVerticalStrut(5));
//        contentPanel.add(userIdTextField);
//        contentPanel.add(Box.createVerticalStrut(10));
//        contentPanel.add(equipmentFieldLabel);
//        contentPanel.add(Box.createVerticalStrut(5));
//        contentPanel.add(equipmentTextField);
//        contentPanel.add(Box.createVerticalStrut(10));
        contentPanel.add(durationLabel);
        contentPanel.add(Box.createVerticalStrut(5));
        contentPanel.add(durationSpinner);
        contentPanel.add(Box.createVerticalStrut(10));
        contentPanel.add(confirmPanel);

        this.add(contentPanel);
        this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        this.setLocationRelativeTo(parent);
    }

    public void onCreate() {
        String userIdText = (String) userIdTextField.getText();
        String duration = (String) durationSpinner.getValue();
        
        try{
            this.facade.createRental(equipment_id, duration);
            JOptionPane.showMessageDialog(null, "Successfully created rental!");
            dispose();
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(null, "Ensure your inputs are correct!", "Invalid Input!", JOptionPane.ERROR_MESSAGE);
        } catch (RuntimeException e){
            JOptionPane.showMessageDialog(null, "Could not add Rental to DB", "Internal Server Exception", JOptionPane.ERROR_MESSAGE);
        }
    }
}