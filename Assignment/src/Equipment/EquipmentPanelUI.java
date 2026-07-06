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
    private boolean canEdit;
    private core.SystemFacade facade;
    
    private class ViewDetailsButton extends JButton {
        public ViewDetailsButton() {
            super("Select");
            this.setBackground(Color.GRAY);
            this.setForeground(Color.WHITE);
            this.setFont(new Font("Arial", Font.PLAIN, 12));
            this.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            this.setFocusPainted(false);
        }
    }
    
    public EquipmentPanelUI(boolean isSelecting, core.SystemFacade facade) {
        this.isSelecting = isSelecting;
        this.facade = facade;
        
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        setBackground(uiConstants.LightPurple);

        refreshData();
    }

    public void refreshData() {
        // Clear all existing UI components currently drawn on the panel
        this.removeAll();

        // Fetch fresh state data from the backend facade
        core.SystemFacade.EquipmentPanelContext data = facade.getEquipmentPanelData();
        this.canEdit = data.canEdit();

        // Build header layout
        JPanel header = createHeaderPanel();
        header.setAlignmentX(Component.LEFT_ALIGNMENT);
        add(header);
        
        add(Box.createVerticalStrut(25));

        // Rebuild each equipment category block layout
        data.equipments().forEach((category, equipmentList) -> {
            add(createCategoryBlock(category, equipmentList));
            add(Box.createVerticalStrut(20));

            JSeparator separator = new JSeparator(JSeparator.HORIZONTAL);
            separator.setForeground(Color.WHITE);
            separator.setMaximumSize(new Dimension(Integer.MAX_VALUE, 2));
            separator.setAlignmentX(Component.LEFT_ALIGNMENT);
            add(separator);
            add(Box.createVerticalStrut(20));
        });

        this.revalidate();
        this.repaint();
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
        newCategoryBtn.setVisible(canEdit);
        newCategoryBtn.addActionListener(e -> {
            Window window = SwingUtilities.getWindowAncestor(this);
            JDialog createCategory = new AddCategoryUI(window, facade);
            createCategory.setVisible(true);
            refreshData();
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
        editCategoryBtn.setVisible(canEdit);
        editCategoryBtn.addActionListener(e -> {
            Window parent = SwingUtilities.getWindowAncestor(this);
            JDialog editCategoryDialog = new EditCategoryUI(parent, category, facade);
            editCategoryDialog.setVisible(true);
            refreshData();
        });
       
        JButton addEquipmentBtn = new JButton("+ Equipment");
        addEquipmentBtn.setBackground(Color.GRAY);
        addEquipmentBtn.setForeground(Color.WHITE);
        addEquipmentBtn.setFont(new Font("Arial", Font.PLAIN, 12));
        addEquipmentBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        addEquipmentBtn.setVisible(canEdit);
        addEquipmentBtn.addActionListener(e -> {
            Window parent = SwingUtilities.getWindowAncestor(this);
            JDialog addEquipmentDialog = new AddEquipmentUI(parent, category.getName(), facade);
            addEquipmentDialog.setVisible(true);
            refreshData(); // 5. Refresh when this modal closes
        });

        labelRow.add(nameLabel);
        labelRow.add(Box.createHorizontalGlue());
        labelRow.add(editCategoryBtn);
        labelRow.add(Box.createHorizontalStrut(10));
        labelRow.add(addEquipmentBtn);
        
        List<Object[]> data = new ArrayList<>();
        for(Equipment e: equipmentList){
            Object[] row = new Object[(this.isSelecting || this.canEdit) ? 5 : 4];
            row[0] = Integer.toString(e.getId());
            row[1] = e.getName();
            row[2] = Float.toString(e.getRate());
            row[3] = e.getStatus();
            if(this.isSelecting || this.canEdit){
                row[4] = "Select";
            }
            data.add(row);
        }
        
        Object[] columns = new Object[(this.isSelecting || this.canEdit) ? 5 : 4];
        columns[0] = "ID";
        columns[1] = "Equipment Name";
        columns[2] = "Daily Rental Rate";
        columns[3] = "Status";
        if(this.isSelecting || this.canEdit){
            columns[4] = "Select Resource";
        }
        
        DefaultTableModel model = new DefaultTableModel(data.toArray(new Object[0][]), columns);
        JTable table = new JTable(model);
        table.setRowHeight(32);
        
        if(this.isSelecting || this.canEdit){
            table.getColumnModel().getColumn(4).setCellRenderer(new TableButtonRenderer());
            table.getColumnModel().getColumn(4).setCellEditor(new TableButtonEditor(table, equipmentList));
        }

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setAlignmentX(Component.LEFT_ALIGNMENT);
       
        blockPanel.add(labelRow);
        blockPanel.add(Box.createVerticalStrut(10)); 
        blockPanel.add(scrollPane);

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
        public Object getCellEditorValue() {
            return "Select";
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
            return editingButton;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            int modelRow = table.convertRowIndexToModel(table.getEditingRow());
            fireEditingStopped();

            Window parent = SwingUtilities.getWindowAncestor(EquipmentPanelUI.this);
            Equipment equipment = equipmentList.get(modelRow);

            JDialog equipmentDetails = new EquipmentDetailsUI(parent, equipment, canEdit, facade);
            equipmentDetails.setVisible(true);

            refreshData(); 
        }
    }
}
