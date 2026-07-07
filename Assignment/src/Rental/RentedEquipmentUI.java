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
import core.SystemFacade.RentedEquipmentContext;
import Equipment.Equipment;

public class RentedEquipmentUI extends JPanel{
    private UIConstants uiConst = new UIConstants();
    private final SystemFacade facade;
    private RentedEquipmentContext data;
    
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
    
    public RentedEquipmentUI(SystemFacade facade){
        this.facade = facade;
        
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        setBackground(uiConst.LightPurple);
        
        refreshData();
    }
    
    private void refreshData(){
        this.removeAll();
        
        this.data = facade.getRentedEquipmentContext();
        
        JLabel headerLabel = new JLabel("Rented Equipments");
        headerLabel.setForeground(Color.WHITE);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 28));
        headerLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
//        table
        List<Object[]> rentedEquipments = new ArrayList<>();
        for(Equipment e: data.cur()){
            Object[] row = new Object[4];
            row[0] = e.getId();
            row[1] = e.getName();
            row[2] = e.getCategory().getName();
            row[3] = "Return";
            rentedEquipments.add(row);
        }
        
        
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
        
        
        JLabel headerLabel2 = new JLabel("Equipments Awaiting Return Confirmation");
        headerLabel2.setForeground(Color.WHITE);
        headerLabel2.setFont(new Font("Arial", Font.BOLD, 28));
        headerLabel2.setAlignmentX(Component.LEFT_ALIGNMENT);
        
//        table
        List<Object[]> previouslyRentedEquipments = new ArrayList<>();
        for(Equipment eq: data.prev()){
            Object[] previousEqRow = new Object[3];
            previousEqRow[0] = eq.getId();
            previousEqRow[1] = eq.getName();
            previousEqRow[2] = eq.getCategory().getName();
            previouslyRentedEquipments.add(previousEqRow);
        }        
        
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
//            wrong
//            int visualRow = table.getEditingRow();
//            if (visualRow != -1) {
//                int modelRow = table.convertRowIndexToModel(visualRow);
//                fireEditingStopped();
//                Window parent = SwingUtilities.getWindowAncestor(table.getParent());
//                JDialog confirmDialog = new ReturnConfirmationDialog(parent, data.cur().get(modelRow).getId(), data.cur().get(modelRow).getName());
//                confirmDialog.setVisible(true);
//            }
        }
    }
}
