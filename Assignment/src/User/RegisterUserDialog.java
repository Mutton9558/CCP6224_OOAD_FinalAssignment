package User;

import javax.swing.*;
import java.awt.*;
import javax.swing.border.EmptyBorder;
import ui.UIConstants;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JFormattedTextField;
import javax.swing.text.DateFormatter;

public class RegisterUserDialog extends JDialog {
    private UIConstants uiConst = new UIConstants();
    
    public RegisterUserDialog(Window parent){
        super(parent, "Register User", Dialog.ModalityType.APPLICATION_MODAL);
        this.setSize(800,600);
        
        JPanel contentPanel = new JPanel();
        contentPanel.setBackground(uiConst.LightPurple);
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        
        JLabel headerLabel = new JLabel("Register User");
        headerLabel.setForeground(Color.WHITE);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 24));
        headerLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        JLabel nameFieldLabel = new JLabel("Full Name");
        nameFieldLabel.setForeground(Color.WHITE);
        nameFieldLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        nameFieldLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        JTextField nameTextField = new JTextField(30);
        nameTextField.setAlignmentX(Component.LEFT_ALIGNMENT);
        nameTextField.setMaximumSize(new Dimension(Integer.MAX_VALUE, nameTextField.getPreferredSize().height));
        
        JLabel emailLabel = new JLabel("Email");
        emailLabel.setForeground(Color.WHITE);
        emailLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        emailLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        JTextField emailTextField = new JTextField(30);
        emailTextField.setAlignmentX(Component.LEFT_ALIGNMENT);
        emailTextField.setMaximumSize(new Dimension(Integer.MAX_VALUE, emailTextField.getPreferredSize().height));
        
        JLabel passwordLabel = new JLabel("Password");
        passwordLabel.setForeground(Color.WHITE);
        passwordLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        passwordLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        JTextField passwordTextField = new JTextField(30);
        passwordTextField.setAlignmentX(Component.LEFT_ALIGNMENT);
        passwordTextField.setMaximumSize(new Dimension(Integer.MAX_VALUE, passwordTextField.getPreferredSize().height));
        
        JLabel genderLabel = new JLabel("Select Gender");
        genderLabel.setForeground(Color.WHITE);
        genderLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        genderLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        String[] genderOptions = {"Male", "Female"};
        
        JComboBox genderDropdown = new JComboBox<>(genderOptions);
        genderDropdown.setAlignmentX(Component.LEFT_ALIGNMENT);
        genderDropdown.setMaximumSize(new Dimension(Integer.MAX_VALUE, genderDropdown.getPreferredSize().height));
        
        JLabel dobLabel = new JLabel("Select Date of Birth");
        dobLabel.setForeground(Color.WHITE);
        dobLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        dobLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);

        DateFormatter formatter = new DateFormatter(dateFormat);
        formatter.setAllowsInvalid(false); // Blocks wrong characters while typing
        formatter.setOverwriteMode(true);  // Overwrites placeholders natively

        JFormattedTextField dateInput = new JFormattedTextField(formatter);
        dateInput.setValue(new Date());
        dateInput.setColumns(10);
        dateInput.setAlignmentX(Component.LEFT_ALIGNMENT);
        dateInput.setMaximumSize(new Dimension(Integer.MAX_VALUE, dateInput.getPreferredSize().height));
        
        JPanel submitPanel = new JPanel();
        submitPanel.setBackground(uiConst.LightPurple);
        JButton submitBtn = new JButton("Submit");
        submitBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        submitBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        submitPanel.add(submitBtn);
        submitPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        contentPanel.add(headerLabel);
        contentPanel.add(Box.createVerticalStrut(20));
        contentPanel.add(nameFieldLabel);
        contentPanel.add(Box.createVerticalStrut(5));
        contentPanel.add(nameTextField);
        contentPanel.add(Box.createVerticalStrut(10));
        contentPanel.add(emailLabel);
        contentPanel.add(Box.createVerticalStrut(5));
        contentPanel.add(emailTextField);
        contentPanel.add(Box.createVerticalStrut(10));
        contentPanel.add(passwordLabel);
        contentPanel.add(Box.createVerticalStrut(5));
        contentPanel.add(passwordTextField);
        contentPanel.add(Box.createVerticalStrut(10));
        contentPanel.add(genderLabel);
        contentPanel.add(Box.createVerticalStrut(5));
        contentPanel.add(genderDropdown);
        contentPanel.add(Box.createVerticalStrut(10));
        contentPanel.add(dobLabel);
        contentPanel.add(Box.createVerticalStrut(5));
        contentPanel.add(dateInput);
        contentPanel.add(Box.createVerticalStrut(10));
        contentPanel.add(submitPanel);
        
        this.add(contentPanel);
        this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        this.setLocationRelativeTo(parent);
    }
}
