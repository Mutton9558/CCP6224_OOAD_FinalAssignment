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

public class RentedEquipmentUI extends JPanel{
    private UIConstants uiConst = new UIConstants();
    
    private class returnEquipmentBtn extends JButton {
        public returnEquipmentBtn() {
            super("Return");
            this.setBackground(Color.GRAY);
            this.setForeground(Color.WHITE);
            this.setFont(new Font("Arial", Font.PLAIN, 12));
            this.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            this.setFocusPainted(false); // Clean visual styling
        }
    }
    
    public RentedEquipmentUI(){
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        setBackground(uiConst.LightPurple);
        
        JLabel headerLabel = new JLabel("Rented Equipments");
        headerLabel.setForeground(Color.WHITE);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 28));
        headerLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
//        table
        List<Object[]> rentedEquipments = new ArrayList<>();
        Object[] row = new Object[4];
        row[0] = "102";
        row[1] = "Lens 24-70mm f2.8";
        row[2] = "Camera and Lenses";
        row[3] = "Return";
        rentedEquipments.add(row);
        
        String[] columns = {"Equipment ID", "Equipment Name", "Category", "Return Item"};
        
        DefaultTableModel model = new DefaultTableModel(rentedEquipments.toArray(new Object[0][]), columns);
        JTable rentedEquipmentTable = new JTable(model);
        rentedEquipmentTable.setAutoCreateRowSorter(true);
        rentedEquipmentTable.setRowHeight(32);
        rentedEquipmentTable.getColumnModel().getColumn(3).setCellRenderer(new TableButtonRenderer());
        rentedEquipmentTable.getColumnModel().getColumn(3).setCellEditor(new TableButtonEditor(rentedEquipmentTable));
        JScrollPane rentedEquipmentScrollPane = new JScrollPane(rentedEquipmentTable);
        rentedEquipmentScrollPane.setAlignmentX(Component.LEFT_ALIGNMENT);

        JSeparator separator = new JSeparator(JSeparator.HORIZONTAL);
        separator.setForeground(Color.WHITE);
        separator.setMaximumSize(new Dimension(Integer.MAX_VALUE, 2));
        separator.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        
        JLabel headerLabel2 = new JLabel("Previously Rented Equipments");
        headerLabel2.setForeground(Color.WHITE);
        headerLabel2.setFont(new Font("Arial", Font.BOLD, 28));
        headerLabel2.setAlignmentX(Component.LEFT_ALIGNMENT);
        
//        table
        List<Object[]> previouslyRentedEquipments = new ArrayList<>();
        Object[] previousEqRow = new Object[3];
        previousEqRow[0] = "103";
        previousEqRow[1] = "Tripod Manfrotto";
        previousEqRow[2] = "Lighting Equipment";
        previouslyRentedEquipments.add(previousEqRow);
        
        String[] columns2 = {"Equipment ID", "Equipment Name", "Category"};
        
        DefaultTableModel model2 = new DefaultTableModel(previouslyRentedEquipments.toArray(new Object[0][]), columns2);
        JTable previouslyRentedTable = new JTable(model2);
        previouslyRentedTable.setAutoCreateRowSorter(true);
        previouslyRentedTable.setRowHeight(32);
        JScrollPane prevEquipmentScrollPane = new JScrollPane(previouslyRentedTable);
        prevEquipmentScrollPane.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        this.add(headerLabel);
        this.add(Box.createVerticalStrut(20));
        this.add(rentedEquipmentScrollPane);
        this.add(Box.createVerticalStrut(20));
        this.add(separator);
        this.add(Box.createVerticalStrut(20));
        this.add(headerLabel2);
        this.add(Box.createVerticalStrut(20));
        this.add(prevEquipmentScrollPane);
    }
    
    private class TableButtonRenderer implements TableCellRenderer {
        private final JButton renderingButton = new returnEquipmentBtn();

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                boolean isSelected, boolean hasFocus, int row, int column) {
            return renderingButton;
        }
    }

    private class TableButtonEditor extends AbstractCellEditor implements TableCellEditor, ActionListener {
        private final JButton editingButton = new returnEquipmentBtn();
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
            return "Return";
        }

        @Override
        public void actionPerformed(ActionEvent e) {
//            int visualRow = table.getEditingRow();
//            if (visualRow != -1) {
//                int modelRow = table.convertRowIndexToModel(visualRow);
//                fireEditingStopped();
//                Window parent = SwingUtilities.getWindowAncestor(table.getParent());
//                JDialog equipmentDetails = new EquipmentDetailsUI(parent, equipmentList.get(modelRow));
//                equipmentDetails.setVisible(true);
//            }
            System.out.println("Returned");
        }
    }
}
