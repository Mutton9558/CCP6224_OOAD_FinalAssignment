package Rental;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import ui.UIConstants;
import java.util.List;
import java.util.ArrayList;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import core.SystemFacade;
import core.SystemFacade.ReturnConfirmationContext;
import Equipment.Equipment;

public class ReturnConfirmationUI extends JPanel{
    private UIConstants uiConst = new UIConstants();
    private List<Rental> rentals;
    private List<Rental> visibleRentals;
    private DefaultTableModel model;
    private JTable returnConfirmationTable;
    private SystemFacade facade;
    
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
    
    public ReturnConfirmationUI(SystemFacade facade) {
        this.facade = facade;
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        setBackground(uiConst.LightPurple);

        JLabel headerLabel = new JLabel("Equipment Return Confirmation");
        headerLabel.setForeground(Color.WHITE);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 28));
        headerLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        String[] columns = {"Equipment ID", "Equipment Name", "Category", "Expected Return Date", "Confirm Return"};

        // build the model empty first, then populate via the shared loadData() method
        model = new DefaultTableModel(new Object[0][], columns) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 4;
            }
        };

        returnConfirmationTable = new JTable(model);
        returnConfirmationTable.setAutoCreateRowSorter(true);
        returnConfirmationTable.setRowHeight(32);
        returnConfirmationTable.getColumnModel().getColumn(4).setCellRenderer(new TableButtonRenderer());
        returnConfirmationTable.getColumnModel().getColumn(4).setCellEditor(new TableButtonEditor(returnConfirmationTable));

        JScrollPane rentedEquipmentScrollPane = new JScrollPane(returnConfirmationTable);
        rentedEquipmentScrollPane.setAlignmentX(Component.LEFT_ALIGNMENT);

        this.add(headerLabel);
        this.add(Box.createVerticalStrut(20));
        this.add(rentedEquipmentScrollPane);
        this.add(Box.createVerticalStrut(20));

        loadData();
    }
    

    public void refresh() {
        loadData();
    }

    private void loadData() {
        this.rentals = facade.getReturnConfirmationContext().rentCur();
        this.visibleRentals = new ArrayList<>();

        model.setRowCount(0);

        for (Rental r : this.rentals) {
            if (Boolean.TRUE.equals(r.getReturnStatus())) continue;

            Equipment eq = r.getEquipment();
            Object[] row = new Object[5];
            row[0] = String.valueOf(r.getId());
            row[1] = eq.getName();
            row[2] = eq.getCategory();
            row[3] = (LocalDate) r.getDueDate();
            row[4] = "Confirm Return";

            model.addRow(row);
            visibleRentals.add(r); // stays in lockstep with the table rows
        }
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
                Rental targetRental = visibleRentals.get(modelRow);

                JDialog returnConfirmation = new ReturnConfirmationDialog(
                        parent, targetRental.getId(), id, name, facade,
                        ReturnConfirmationUI.this::refresh
                );
                returnConfirmation.setVisible(true);
            }
            fireEditingStopped();
        }
    }
}