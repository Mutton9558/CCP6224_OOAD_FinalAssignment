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
import java.util.Map;
import User.User;
import User.Permission;

public class EquipmentPanelUI extends JPanel {
    private final UIConstants uiConstants = new UIConstants();
    private User curUser;
    private core.SystemFacade facade;
    private Map<Category, List<Equipment>> equipmentData;
    private core.SystemFacade.EquipmentPanelContext data;
    
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
    
    public EquipmentPanelUI(core.SystemFacade facade) {
        this.facade = facade;
        
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        setBackground(uiConstants.LightPurple);

        this.data = facade.getEquipmentPanelData();
        this.curUser = data.user();
        this.equipmentData = data.equipments();
        
        refreshData();
    }

    public void refreshData() {
        // Clear all existing UI components currently drawn on the panel
        this.removeAll();

        // Build header layout
        JPanel header = createHeaderPanel();
        header.setAlignmentX(Component.LEFT_ALIGNMENT);
        add(header);
        add(Box.createVerticalStrut(5));
        
        JLabel titleLabel = new JLabel("Search Equipment (Enter ID)");
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        add(titleLabel);
        
        JPanel searchPanel = new JPanel();
        searchPanel.setLayout(new BoxLayout(searchPanel, BoxLayout.X_AXIS));
        searchPanel.setBackground(uiConstants.LightPurple);
        searchPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        
        JTextField searchField = new JTextField(70);
        JButton searchBtn = new JButton("Search");
        JButton refreshBtn = new JButton("Refresh");
        searchBtn.addActionListener(e -> {
            this.equipmentData = this.facade.searchEquipment(searchField.getText());
            refreshData();
        });
        
        refreshBtn.addActionListener(e -> {
            this.equipmentData = facade.getEquipmentPanelData().equipments();
            refreshData();
        });
        
        searchPanel.add(searchField);
        searchPanel.add(searchBtn);
        searchPanel.add(refreshBtn);
        searchPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        add(searchPanel);
        
        add(Box.createVerticalStrut(20));

        // Rebuild each equipment category block layout
        this.equipmentData.forEach((category, equipmentList) -> {
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
        newCategoryBtn.setVisible(this.curUser.hasPermission(Permission.ADD_CATEGORY));
        newCategoryBtn.addActionListener(e -> {
            Window window = SwingUtilities.getWindowAncestor(this);
            JDialog createCategory = new AddCategoryUI(window, facade);
            createCategory.setVisible(true);
            this.equipmentData = facade.getEquipmentPanelData().equipments();
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
        editCategoryBtn.setVisible(this.curUser.hasPermission(Permission.EDIT_CATEGORY));
        editCategoryBtn.addActionListener(e -> {
            Window parent = SwingUtilities.getWindowAncestor(this);
            JDialog editCategoryDialog = new EditCategoryUI(parent, category, facade);
            editCategoryDialog.setVisible(true);
            this.equipmentData = facade.getEquipmentPanelData().equipments();
            refreshData();
        });
       
        JButton addEquipmentBtn = new JButton("+ Equipment");
        addEquipmentBtn.setBackground(Color.GRAY);
        addEquipmentBtn.setForeground(Color.WHITE);
        addEquipmentBtn.setFont(new Font("Arial", Font.PLAIN, 12));
        addEquipmentBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        addEquipmentBtn.setVisible(this.curUser.hasPermission(Permission.ADD_EQUIPMENT));
        addEquipmentBtn.addActionListener(e -> {
            Window parent = SwingUtilities.getWindowAncestor(this);
            JDialog addEquipmentDialog = new AddEquipmentUI(parent, category.getName(), facade);
            addEquipmentDialog.setVisible(true);
            this.equipmentData = facade.getEquipmentPanelData().equipments();
            refreshData(); // 5. Refresh when this modal closes
        });

        labelRow.add(nameLabel);
        labelRow.add(Box.createHorizontalGlue());
        labelRow.add(editCategoryBtn);
        labelRow.add(Box.createHorizontalStrut(10));
        labelRow.add(addEquipmentBtn);
        
        List<Object[]> tempData = new ArrayList<>();
        for(Equipment e: equipmentList){
            Object[] row = new Object[5];
            row[0] = Integer.toString(e.getId());
            row[1] = e.getName();
            row[2] = Float.toString(e.getRate());
            row[3] = e.getStatus();
            if(e.getStatus().equals("Available") || curUser.hasPermission(Permission.EDIT_EQUIPMENT)){
                row[4] = "Select";
            }
            tempData.add(row);
        }
        
        Object[] columns = new Object[5];
        columns[0] = "ID";
        columns[1] = "Equipment Name";
        columns[2] = "Daily Rental Rate";
        columns[3] = "Status";
        columns[4] = "Select Resource";
        
        DefaultTableModel model = new DefaultTableModel(tempData.toArray(new Object[0][]), columns) {
            @Override
            public boolean isCellEditable(int row, int column) {
                if (column == 4) {
                    return getValueAt(row, column) != null;
                }
                return false; // other columns are display-only anyway
            }
        };
        JTable table = new JTable(model);
        table.setRowHeight(32);
        
        table.getColumnModel().getColumn(4).setCellRenderer(new TableButtonRenderer());
        table.getColumnModel().getColumn(4).setCellEditor(new TableButtonEditor(table, equipmentList));

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setAlignmentX(Component.LEFT_ALIGNMENT);
       
        blockPanel.add(labelRow);
        blockPanel.add(Box.createVerticalStrut(10)); 
        blockPanel.add(scrollPane);

        return blockPanel;
    }

    private class TableButtonRenderer implements TableCellRenderer {
        private final ViewDetailsButton renderingButton = new ViewDetailsButton();
        private final JPanel emptyPanel = new JPanel();
        
        public TableButtonRenderer() {
            emptyPanel.setBackground(Color.WHITE);
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                boolean isSelected, boolean hasFocus, int row, int column) {
            return (value == null) ? emptyPanel : renderingButton;
        }
    }

    private class TableButtonEditor extends AbstractCellEditor implements TableCellEditor, ActionListener {
        private final ViewDetailsButton editingButton = new ViewDetailsButton();
        private final JTable table;
        private List<Equipment> equipmentList;
        private final JPanel emptyPanel = new JPanel();

        public TableButtonEditor(JTable table, List<Equipment> equipmentList) {
            this.table = table;
            this.equipmentList = equipmentList;
            this.editingButton.addActionListener(this);
            this.emptyPanel.setBackground(Color.WHITE);
        }

        @Override
        public Object getCellEditorValue() {
            return "Select";
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
            return (value == null) ? emptyPanel : editingButton;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            int modelRow = table.convertRowIndexToModel(table.getEditingRow());
            fireEditingStopped();

            Window parent = SwingUtilities.getWindowAncestor(EquipmentPanelUI.this);
            Equipment equipment = equipmentList.get(modelRow);

            JDialog equipmentDetails = new EquipmentDetailsUI(parent, equipment, curUser.hasPermission(Permission.EDIT_EQUIPMENT), facade);
            equipmentDetails.setVisible(true);

            equipmentData = facade.getEquipmentPanelData().equipments();
            refreshData(); 
        }
    }
}
