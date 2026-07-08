package User;

import core.SystemFacade;
import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import ui.UIConstants;

public class LoginUI extends JDialog {
    public interface LoginCallback {
        void onLoginSuccess();
    }

    public LoginUI(Window parent, SystemFacade facade, LoginCallback callback){
        super(parent, "Login", Dialog.ModalityType.APPLICATION_MODAL);
        this.setSize(500, 450);
        
        JPanel contentPanel = new JPanel();
        contentPanel.setBackground(UIConstants.DarkPurple);
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        
        JLabel logoLabel = new JLabel(UIConstants.MMlogo);
        logoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel headerLabel = new JLabel("Login");
        headerLabel.setForeground(Color.WHITE);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 24));
        headerLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel idLabel = new JLabel("User ID");
        idLabel.setForeground(Color.WHITE);
        idLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        idLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JTextField idTextField = new JTextField(30);
        idTextField.setAlignmentX(Component.CENTER_ALIGNMENT);
        idTextField.setMaximumSize(new Dimension(Integer.MAX_VALUE, idTextField.getPreferredSize().height));
        
        JLabel passwordLabel = new JLabel("Password");
        passwordLabel.setForeground(Color.WHITE);
        passwordLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        passwordLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JPasswordField passwordField = new JPasswordField(30);
        passwordField.setAlignmentX(Component.CENTER_ALIGNMENT);
        passwordField.setMaximumSize(new Dimension(Integer.MAX_VALUE, passwordField.getPreferredSize().height));
        
        JLabel messageLabel = new JLabel(" ");
        messageLabel.setForeground(Color.RED);
        messageLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        messageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JPanel submitPanel = new JPanel();
        submitPanel.setBackground(UIConstants.DarkPurple);
        JButton submitBtn = new JButton("Sign In");
        submitBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        submitBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        submitPanel.add(submitBtn);
        submitPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        submitBtn.addActionListener(e -> {
            String idText = idTextField.getText().trim();
            String password = new String(passwordField.getPassword()).trim();
            
            if(idText.isEmpty() || password.isEmpty()){
                messageLabel.setText("Please enter User ID and password.");
                return;
            }
            
            int id;
            try{
                id = Integer.parseInt(idText);
            } catch (NumberFormatException ex){
                messageLabel.setText("User ID must be a number.");
                return;
            }
            
            boolean success = facade.login(id, password);
            if(success){
                this.dispose();
                callback.onLoginSuccess();
            } else {
                messageLabel.setText("Invalid User ID or password.");
                passwordField.setText("");
            }
        });
        
        contentPanel.add(logoLabel);
        contentPanel.add(Box.createVerticalStrut(20));
        contentPanel.add(headerLabel);
        contentPanel.add(Box.createVerticalStrut(20));
        contentPanel.add(idLabel);
        contentPanel.add(Box.createVerticalStrut(5));
        contentPanel.add(idTextField);
        contentPanel.add(Box.createVerticalStrut(10));
        contentPanel.add(passwordLabel);
        contentPanel.add(Box.createVerticalStrut(5));
        contentPanel.add(passwordField);
        contentPanel.add(Box.createVerticalStrut(10));
        contentPanel.add(messageLabel);
        contentPanel.add(Box.createVerticalStrut(10));
        contentPanel.add(submitPanel);
        
        this.add(contentPanel);
        this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        this.setLocationRelativeTo(parent);
    }
}
