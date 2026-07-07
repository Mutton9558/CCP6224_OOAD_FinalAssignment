package Rental;

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
import core.SystemFacade;
import core.SystemFacade.ReturnConfirmationContext;
import Equipment.Equipment;

public class ReturnConfirmationUI extends JPanel{
    private UIConstants uiConst = new UIConstants();
    
    private class confirmBtn extends JButton {
        public confirmBtn() {
            super("Confirm Return");
            this.setBackground(Color.GRAY);
            this.setForeground(Color.WHITE);
            this.setFont(new Font("Arial", Font.PLAIN, 12));
            this.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            this.setFocusPainted(false); // Clean visual styling
        }
    }
    
    public ReturnConfirmationUI(){
        
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        setBackground(uiConst.LightPurple);
        
        JLabel headerLabel = new JLabel("Equipment Return Confirmation");
        headerLabel.setForeground(Color.WHITE);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 28));
        headerLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
//        table
        List<Object[]> rentedEquipments = new ArrayList<>();
        Object[] row = new Object[5];
        row[0] = "101";
        row[1] = "Camera Sony A7IV";
        row[2] = "Camera and Lenses";
        row[3] = "06-11-2026";
        row[4] = "Confirm Return";
        rentedEquipments.add(row);
        
        String[] columns = {"Equipment ID", "Equipment Name", "Category", "Expected Return Date", "Confirm Return"};
        
        DefaultTableModel model = new DefaultTableModel(rentedEquipments.toArray(new Object[0][]), columns);
        JTable rentedEquipmentTable = new JTable(model);
        rentedEquipmentTable.setAutoCreateRowSorter(true);
        rentedEquipmentTable.setRowHeight(32);
        rentedEquipmentTable.getColumnModel().getColumn(4).setCellRenderer(new TableButtonRenderer());
        rentedEquipmentTable.getColumnModel().getColumn(4).setCellEditor(new TableButtonEditor(rentedEquipmentTable));
        JScrollPane rentedEquipmentScrollPane = new JScrollPane(rentedEquipmentTable);
        rentedEquipmentScrollPane.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        this.add(headerLabel);
        this.add(Box.createVerticalStrut(20));
        this.add(rentedEquipmentScrollPane);
        this.add(Box.createVerticalStrut(20));
    }
    
    private class TableButtonRenderer implements TableCellRenderer {
        private final JButton renderingButton = new confirmBtn();

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                boolean isSelected, boolean hasFocus, int row, int column) {
            return renderingButton;
        }
    }

    private class TableButtonEditor extends AbstractCellEditor implements TableCellEditor, ActionListener {
        private final JButton editingButton = new confirmBtn();
        private final JTable table;

        public TableButtonEditor(JTable table) {
            this.table = table;
            this.editingButton.addActionListener(this);
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value,
                boolean isSelected, int row, int column) {
            return editingButton;
        }

        @Override
        public Object getCellEditorValue() {
            return "Confirm Return";
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            int visualRow = table.getEditingRow();
            if (visualRow != -1) {
                int modelRow = table.convertRowIndexToModel(visualRow);
                
                Window parent = SwingUtilities.getWindowAncestor(table.getParent());
                int id = Integer.parseInt((String) table.getModel().getValueAt(modelRow, 0));
                String name = (String) table.getModel().getValueAt(modelRow, 1);
                JDialog returnConfirmation = new ReturnConfirmationDialog(parent, id, name);
                returnConfirmation.setVisible(true);
            }
            fireEditingStopped();
        }
    }
}
