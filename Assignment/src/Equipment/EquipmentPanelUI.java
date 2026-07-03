package Equipment;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import ui.UIConstants;
import java.util.List;
import java.util.ArrayList;

public class EquipmentPanelUI extends JPanel {
    private final UIConstants uiConstants = new UIConstants();
    private boolean isSelecting;
    
    private class ViewDetailsButton extends JButton {
        public ViewDetailsButton() {
            super("Select");
            this.setBackground(Color.GRAY);
            this.setForeground(Color.WHITE);
            this.setFont(new Font("Arial", Font.PLAIN, 12));
            this.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            this.setFocusPainted(false); // Clean visual styling
        }
    }
    
    public EquipmentPanelUI(boolean isSelecting) {
        this.isSelecting = isSelecting;
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        setBackground(uiConstants.LightPurple);

        JPanel header = createHeaderPanel();
        header.setAlignmentX(Component.LEFT_ALIGNMENT);
        add(header);
        
        add(Box.createVerticalStrut(25));
        
        Category camerasAndLenses = new Category(1, "Cameras & Lenses", 100.0f, 0.1f, 0.05f, 0.3f);
        Equipment test1 = new Equipment(101, "Camera Sony A7IV", camerasAndLenses, 100.0f, "Available");
        Equipment test2 = new Equipment(102, "Lens 24-70mm f2.8", camerasAndLenses, 50.0f, "Rented Out");
        List<Equipment> testList1 = new ArrayList<>();
        testList1.add(test1);
        testList1.add(test2);
        add(createCategoryBlock(camerasAndLenses, testList1));
        add(Box.createVerticalStrut(20));

        JSeparator separator = new JSeparator(JSeparator.HORIZONTAL);
        separator.setForeground(Color.WHITE);
        separator.setMaximumSize(new Dimension(Integer.MAX_VALUE, 2));
        separator.setAlignmentX(Component.LEFT_ALIGNMENT);
        add(separator);
        add(Box.createVerticalStrut(20));

        Category lightingEquipment = new Category(2, "Lighting Equipment", 70.0f, 0.05f, 0.05f, 0.2f);
        Equipment test3 = new Equipment(103, "Tripod Manfrotto", lightingEquipment, 15.00f, "Available");
        List<Equipment> testList2 = new ArrayList<>();
        testList2.add(test3);
        add(createCategoryBlock(lightingEquipment, testList2));
    }

    private JPanel createHeaderPanel() {
        JPanel headerPanel = new JPanel(new BorderLayout()) {
            @Override
            public Dimension getMaximumSize() {
                return new Dimension(Short.MAX_VALUE, 50); 
            }
        };
        
        headerPanel.setBackground(uiConstants.LightPurple);
        headerPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));

        JLabel titleLabel = new JLabel("Equipment List");
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 28));

        JButton newCategoryBtn = new JButton("+ New Category");
        newCategoryBtn.setBackground(Color.GRAY);
        newCategoryBtn.setForeground(Color.WHITE);
        newCategoryBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        newCategoryBtn.setFocusPainted(false);
        newCategoryBtn.addActionListener(e -> {
            Window window = SwingUtilities.getWindowAncestor(this);
            JDialog createCategory = new AddCategoryUI(window);
            createCategory.setVisible(true);
        });

        headerPanel.add(titleLabel, BorderLayout.WEST);
        headerPanel.add(newCategoryBtn, BorderLayout.EAST);
        return headerPanel;
    }

    private JPanel createCategoryBlock(Category category, List<Equipment> equipmentList) {
        JPanel blockPanel = new JPanel();
        blockPanel.setLayout(new BoxLayout(blockPanel, BoxLayout.Y_AXIS));
        blockPanel.setBackground(uiConstants.LightPurple);
        blockPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JPanel labelRow = new JPanel();
        labelRow.setLayout(new BoxLayout(labelRow, BoxLayout.X_AXIS));
        labelRow.setBackground(uiConstants.LightPurple);
        labelRow.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        labelRow.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel nameLabel = new JLabel(category.getName());
        nameLabel.setForeground(Color.WHITE);
        nameLabel.setFont(new Font("Arial", Font.PLAIN, 18));

        JButton editCategoryBtn = new JButton("Edit Category");
        editCategoryBtn.setBackground(Color.GRAY);
        editCategoryBtn.setForeground(Color.WHITE);
        editCategoryBtn.setFont(new Font("Arial", Font.PLAIN, 12));
        editCategoryBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        editCategoryBtn.addActionListener(e -> {
            Window parent = SwingUtilities.getWindowAncestor(this);
            JDialog editCategoryDialog = new EditCategoryUI(parent, category);
            editCategoryDialog.setVisible(true);
        });
       
        
        JButton addEquipmentBtn = new JButton("+ Equipment");
        addEquipmentBtn.setBackground(Color.GRAY);
        addEquipmentBtn.setForeground(Color.WHITE);
        addEquipmentBtn.setFont(new Font("Arial", Font.PLAIN, 12));
        addEquipmentBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        addEquipmentBtn.addActionListener(e -> {
            Window parent = SwingUtilities.getWindowAncestor(this);
            JDialog addEquipmentDialog = new AddEquipmentUI(parent);
            addEquipmentDialog.setVisible(true);
        });

        labelRow.add(nameLabel);
        labelRow.add(Box.createHorizontalGlue());
        labelRow.add(editCategoryBtn);
        labelRow.add(Box.createHorizontalStrut(10));
        labelRow.add(addEquipmentBtn);
        
        List<Object[]> data = new ArrayList<>();
        for(Equipment e: equipmentList){
            Object[] row = new Object[this.isSelecting ? 5 : 4];
            row[0] = Integer.toString(e.getId());
            row[1] = e.getName();
            row[2] = Float.toString(e.getRate());
            row[3] = e.getStatus();
            if(this.isSelecting){
                row[4] = "Select";
            }
            data.add(row);
        }
        
        // Corrected columns mismatch from previous mock data arrays
//        Object[][] data = {
//            {"101", "Camera Sony A7IV", "100.00", "Available", "Details"},
//            {"102", "Lens 24-70mm f2.8", "50.00", "Rented Out", "Details"},
//            {"103", "Tripod Manfrotto", "15.00", "Available", "Details"}
//        };
        Object[] columns = new Object[this.isSelecting ? 5 : 4];
        columns[0] = "ID";
        columns[1] = "Equipment Name";
        columns[2] = "Daily Rental Rate";
        columns[3] = "Status";
        if(this.isSelecting){
            columns[4] = "Select Resource";
        }
        
        // Construct the Table Components
        DefaultTableModel model = new DefaultTableModel(data.toArray(new Object[0][]), columns);
        JTable table = new JTable(model);
        table.setRowHeight(32);
        
        // Link custom handlers to column index 4 ("See Details")
        if(this.isSelecting){
            table.getColumnModel().getColumn(4).setCellRenderer(new TableButtonRenderer());
            table.getColumnModel().getColumn(4).setCellEditor(new TableButtonEditor(table, equipmentList));
        }
        

        // Wrap the JTable inside a JScrollPane
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setAlignmentX(Component.LEFT_ALIGNMENT);
       
        blockPanel.add(labelRow);
        blockPanel.add(Box.createVerticalStrut(10)); 
        blockPanel.add(scrollPane); // Add the structured scroll pane layout

        return blockPanel;
    }

    private class TableButtonRenderer implements TableCellRenderer {
        private final ViewDetailsButton renderingButton = new ViewDetailsButton();

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                boolean isSelected, boolean hasFocus, int row, int column) {
            return renderingButton;
        }
    }

    private class TableButtonEditor extends AbstractCellEditor implements TableCellEditor, ActionListener {
        private final ViewDetailsButton editingButton = new ViewDetailsButton();
        private final JTable table;
        private List<Equipment> equipmentList;

        public TableButtonEditor(JTable table, List<Equipment> equipmentList) {
            this.table = table;
            this.equipmentList = equipmentList;
            this.editingButton.addActionListener(this);
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value,
                boolean isSelected, int row, int column) {
            return editingButton;
        }

        @Override
        public Object getCellEditorValue() {
            return "Select";
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            int visualRow = table.getEditingRow();
            if (visualRow != -1) {
                int modelRow = table.convertRowIndexToModel(visualRow);
                fireEditingStopped();
                Window parent = SwingUtilities.getWindowAncestor(table.getParent());
                JDialog equipmentDetails = new EquipmentDetailsUI(parent, equipmentList.get(modelRow));
                equipmentDetails.setVisible(true);
            }
        }
    }
}
