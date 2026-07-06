package Billing;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import ui.UIConstants;
import java.util.List;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

public class BillPaymentUI extends JPanel {
    private UIConstants uiConst = new UIConstants();
    private BillingController controller;
    private List<Bill> billList = new ArrayList<>();
    private DefaultTableModel tableModel;
    private JTable table;
    
    private class payBtn extends JButton {
        public payBtn() {
            super("Pay");
            this.setBackground(Color.GRAY);
            this.setForeground(Color.WHITE);
            this.setFont(new Font("Arial", Font.PLAIN, 12));
            this.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            this.setFocusPainted(false);
        }
    }
    
//    swap to facade once Shawn adds the billing methods there
    public BillPaymentUI(BillingController controller){
        this.controller = controller;
        
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        setBackground(uiConst.LightPurple);
        
        JLabel headerLabel = new JLabel("Make Payment");
        headerLabel.setForeground(Color.WHITE);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 28));
        headerLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        String[] columns = {"Bill ID", "Type", "Net Payable", "Pay"};
        
        tableModel = new DefaultTableModel(columns, 0){
            @Override
            public boolean isCellEditable(int row, int col){
                return col == 3;
            }
        };
        
        table = new JTable(tableModel);
        table.setRowHeight(32);
        table.getColumnModel().getColumn(3).setCellRenderer(new TableButtonRenderer());
        table.getColumnModel().getColumn(3).setCellEditor(new TableButtonEditor(table));
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        this.add(headerLabel);
        this.add(Box.createVerticalStrut(20));
        this.add(scrollPane);
        this.add(Box.createVerticalStrut(20));
        
        loadBills();
    }
    
    public void loadBills(){
        billList = controller.getUnpaidBills();
        tableModel.setRowCount(0);
        for(Bill b : billList){
            Object[] row = new Object[4];
            row[0] = b.getId();
            row[1] = b.getBillType();
            row[2] = String.format("RM %.2f", b.getNetPayable());
            row[3] = "Pay";
            tableModel.addRow(row);
        }
    }
    
    private class TableButtonRenderer implements TableCellRenderer {
        private final JButton renderingButton = new payBtn();

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                boolean isSelected, boolean hasFocus, int row, int column) {
            return renderingButton;
        }
    }

    private class TableButtonEditor extends AbstractCellEditor implements TableCellEditor, ActionListener {
        private final JButton editingButton = new payBtn();
        private final JTable table;
        private int currentRow;

        public TableButtonEditor(JTable table) {
            this.table = table;
            this.editingButton.addActionListener(this);
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value,
                boolean isSelected, int row, int column) {
            currentRow = row;
            return editingButton;
        }

        @Override
        public Object getCellEditorValue() {
            return "Pay";
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            fireEditingStopped();
            
            Bill selectedBill = billList.get(currentRow);
            controller.payBill(selectedBill.getId());
            loadBills();
        }
    }
}
