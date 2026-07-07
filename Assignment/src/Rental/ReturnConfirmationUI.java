package Rental;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import ui.UIConstants;
import java.util.List;
import java.util.ArrayList;
import java.time.format.DateTimeFormatter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import Equipment.Equipment;

public class ReturnConfirmationUI extends JPanel{
    private UIConstants uiConst = new UIConstants();
    private RentalController rentalController;
    
    private class confirmBtn extends JButton {
        public confirmBtn() {
            super("Confirm Return");
            this.setBackground(Color.GRAY);
            this.setForeground(Color.WHITE);
            this.setFont(new Font("Arial", Font.PLAIN, 12));
            this.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            this.setFocusPainted(false);
        }
    }
    
    public ReturnConfirmationUI(RentalController rentalController){
        this.rentalController = rentalController;
        
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        setBackground(uiConst.LightPurple);
        
        JLabel headerLabel = new JLabel("Equipment Return Confirmation");
        headerLabel.setForeground(Color.WHITE);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 28));
        headerLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        List<Rental> rentals = rentalController.getAllRentals();
        List<Object[]> rentedEquipments = new ArrayList<>();
        
        for (Rental r : rentals) {
            if (Boolean.TRUE.equals(r.getReturnStatus())) continue;
            
            Equipment eq = r.getEquipment();
            Object[] row = new Object[5];
            row[0] = String.valueOf(r.getId());
            row[1] = eq.getName();
            row[2] = eq.getCategory();
            row[3] = r.getDueDate().format(dateFormatter);
            row[4] = "Confirm Return";
            rentedEquipments.add(row);
        }
        
        String[] columns = {"Equipment ID", "Equipment Name", "Category", "Expected Return Date", "Confirm Return"};
        
        DefaultTableModel model = new DefaultTableModel(rentedEquipments.toArray(new Object[0][]), columns) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 4; 
            }
        };
        
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
                
                Window parent = SwingUtilities.getWindowAncestor(table);
                int id = Integer.parseInt((String) table.getModel().getValueAt(modelRow, 0));
                String name = (String) table.getModel().getValueAt(modelRow, 1);
                JDialog returnConfirmation = new ReturnConfirmationDialog(parent, id, name);
                returnConfirmation.setVisible(true);
            }
            fireEditingStopped();
        }
    }
}