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
import java.util.Map;

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
    
    public void refreshData(){
        this.removeAll();
        
        this.data = facade.getRentedEquipmentContext();
        
        JLabel headerLabel = new JLabel("Rented Equipments");
        headerLabel.setForeground(Color.WHITE);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 28));
        headerLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
//        table
        List<Object[]> rentedEquipments = new ArrayList<>();
        Map<Integer, Equipment> cur = data.cur();
        cur.forEach((rentalId, e) -> {
            Object[] row = new Object[5];
            row[0] = rentalId;
            row[1] = e.getId();
            row[2] = e.getName();
            row[3] = e.getCategory().getName();
            row[4] = "Return";
            rentedEquipments.add(row);
        });
        
        
        String[] columns = {"Rental ID", "Equipment ID", "Equipment Name", "Category", "Return Item"};
        
        DefaultTableModel model = new DefaultTableModel(rentedEquipments.toArray(new Object[0][]), columns);
        JTable rentedEquipmentTable = new JTable(model);
        rentedEquipmentTable.setAutoCreateRowSorter(true);
        rentedEquipmentTable.setRowHeight(32);
        rentedEquipmentTable.getColumnModel().getColumn(4).setCellRenderer(new TableButtonRenderer());
        rentedEquipmentTable.getColumnModel().getColumn(4).setCellEditor(new TableButtonEditor(rentedEquipmentTable));
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
        Map<Integer, Equipment> prev = data.prev();
        cur.forEach((rentalId, e) -> {
            Object[] row = new Object[4];
            row[0] = rentalId;
            row[1] = e.getId();
            row[2] = e.getName();
            row[3] = e.getCategory().getName();
            rentedEquipments.add(row);
        });       
        
        String[] columns2 = {"Rental ID", "Equipment ID", "Equipment Name", "Category"};
        
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
        
        this.revalidate();
        this.repaint();
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
            int visualRow = table.getEditingRow();
            if (visualRow != -1) {
                int modelRow = table.convertRowIndexToModel(visualRow);
                fireEditingStopped();
                int rentalId = (Integer) table.getModel().getValueAt(modelRow, 0);
                int equipmentId = (Integer) table.getModel().getValueAt(modelRow, 1);
                boolean success = facade.returnEquipment(rentalId, equipmentId);
                if(success){
                    JOptionPane.showMessageDialog(null, "Successfully sent return confirmation request.");
                } else {
                    JOptionPane.showMessageDialog(null, "Failed to return equipment", "Failure", JOptionPane.ERROR_MESSAGE);
                }
                refreshData();
            }
        }
    }
}
