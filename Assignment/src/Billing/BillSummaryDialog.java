package Billing;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import ui.UIConstants;

public class BillSummaryDialog extends JDialog {
    private UIConstants uiConst = new UIConstants();

    public BillSummaryDialog(Window parent, Bill bill) {
        super(parent, "Bill Summary", Dialog.ModalityType.APPLICATION_MODAL);
        this.setSize(800, 600);
        
        JPanel contentPanel = new JPanel();
        contentPanel.setBackground(uiConst.LightPurple);
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        
        contentPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        JLabel headerLabel = new JLabel(bill.getBillType());
        headerLabel.setForeground(Color.WHITE);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 24));
        headerLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        JLabel idLabel = new JLabel("Bill ID");
        idLabel.setForeground(Color.WHITE);
        idLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        idLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        JTextField idTextField = new JTextField(30);
        idTextField.setAlignmentX(Component.LEFT_ALIGNMENT);
        idTextField.setMaximumSize(new Dimension(Integer.MAX_VALUE, idTextField.getPreferredSize().height));
        idTextField.setText(Integer.toString(bill.getId()));
        idTextField.setEnabled(false);
        
        JLabel baseFeeLabel = new JLabel("Base Rental Fee");
        baseFeeLabel.setForeground(Color.WHITE);
        baseFeeLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        baseFeeLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        JTextField baseFeeTextField = new JTextField(30);
        baseFeeTextField.setAlignmentX(Component.LEFT_ALIGNMENT);
        baseFeeTextField.setMaximumSize(new Dimension(Integer.MAX_VALUE, baseFeeTextField.getPreferredSize().height));
        baseFeeTextField.setText(String.format("RM %.2f", bill.getBaseFee()));
        baseFeeTextField.setEnabled(false);
        
        JLabel discountLabel = new JLabel("Discount");
        discountLabel.setForeground(Color.WHITE);
        discountLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        discountLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        JTextField discountTextField = new JTextField(30);
        discountTextField.setAlignmentX(Component.LEFT_ALIGNMENT);
        discountTextField.setMaximumSize(new Dimension(Integer.MAX_VALUE, discountTextField.getPreferredSize().height));
        discountTextField.setText(String.format("- RM %.2f", bill.getDiscount()));
        discountTextField.setEnabled(false);
        
        JLabel penaltyLabel = new JLabel("Penalty");
        penaltyLabel.setForeground(Color.WHITE);
        penaltyLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        penaltyLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        JTextField penaltyTextField = new JTextField(30);
        penaltyTextField.setAlignmentX(Component.LEFT_ALIGNMENT);
        penaltyTextField.setMaximumSize(new Dimension(Integer.MAX_VALUE, penaltyTextField.getPreferredSize().height));
        penaltyTextField.setText(String.format("+ RM %.2f", bill.getPenalty()));
        penaltyTextField.setEnabled(false);
        
        JLabel netPayableLabel = new JLabel("Net Payable");
        netPayableLabel.setForeground(Color.WHITE);
        netPayableLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        netPayableLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        JTextField netPayableTextField = new JTextField(30);
        netPayableTextField.setAlignmentX(Component.LEFT_ALIGNMENT);
        netPayableTextField.setMaximumSize(new Dimension(Integer.MAX_VALUE, netPayableTextField.getPreferredSize().height));
        netPayableTextField.setText(String.format("RM %.2f", bill.getNetPayable()));
        netPayableTextField.setEnabled(false);
        
        JPanel closePanel = new JPanel();
        closePanel.setBackground(uiConst.LightPurple);
        JButton closeBtn = new JButton("Close");
        closeBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        closeBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        closeBtn.addActionListener(e -> this.dispose());
        closePanel.add(closeBtn);
        closePanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        contentPanel.add(headerLabel);
        contentPanel.add(Box.createVerticalStrut(20));
        contentPanel.add(idLabel);
        contentPanel.add(Box.createVerticalStrut(5));
        contentPanel.add(idTextField);
        contentPanel.add(Box.createVerticalStrut(10));
        contentPanel.add(baseFeeLabel);
        contentPanel.add(Box.createVerticalStrut(5));
        contentPanel.add(baseFeeTextField);
        contentPanel.add(Box.createVerticalStrut(10));
        contentPanel.add(discountLabel);
        contentPanel.add(Box.createVerticalStrut(5));
        contentPanel.add(discountTextField);
        contentPanel.add(Box.createVerticalStrut(10));
        contentPanel.add(penaltyLabel);
        contentPanel.add(Box.createVerticalStrut(5));
        contentPanel.add(penaltyTextField);
        contentPanel.add(Box.createVerticalStrut(10));
        contentPanel.add(netPayableLabel);
        contentPanel.add(Box.createVerticalStrut(5));
        contentPanel.add(netPayableTextField);
        contentPanel.add(Box.createVerticalStrut(20));
        contentPanel.add(closePanel);
        
        this.add(contentPanel);
        this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        this.setLocationRelativeTo(parent);
    }
}
