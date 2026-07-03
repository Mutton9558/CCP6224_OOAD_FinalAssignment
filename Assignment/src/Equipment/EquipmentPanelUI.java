package Equipment;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import ui.UIConstants;

public class EquipmentPanelUI extends JPanel {
    private final UIConstants uiConstants = new UIConstants();
    
    private class ViewDetailsButton extends JButton {
        public ViewDetailsButton() {
            super("Details");
            this.setBackground(Color.GRAY);
            this.setForeground(Color.WHITE);
            this.setFont(new Font("Arial", Font.PLAIN, 12));
            this.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            this.setFocusPainted(false); // Clean visual styling
        }
    }
    
    public EquipmentPanelUI() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        setBackground(uiConstants.LightPurple);

        JPanel header = createHeaderPanel();
        header.setAlignmentX(Component.LEFT_ALIGNMENT);
        add(header);
        
        add(Box.createVerticalStrut(25));
        
        Category camerasAndLenses = new Category(1, "Cameras & Lenses", 100.0f, 0.1f, 0.05f, 0.3f);
        add(createCategoryBlock(camerasAndLenses));
        add(Box.createVerticalStrut(20));

        JSeparator separator = new JSeparator(JSeparator.HORIZONTAL);
        separator.setForeground(Color.WHITE);
        separator.setMaximumSize(new Dimension(Integer.MAX_VALUE, 2));
        separator.setAlignmentX(Component.LEFT_ALIGNMENT);
        add(separator);
        add(Box.createVerticalStrut(20));

        Category lightingEquipment = new Category(2, "Lighting Equipment", 70.0f, 0.05f, 0.05f, 0.2f);
        add(createCategoryBlock(lightingEquipment));
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

    private JPanel createCategoryBlock(Category category) {
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
        
        // Corrected columns mismatch from previous mock data arrays
        Object[][] data = {
            {"101", "Camera Sony A7IV", "100.00", "Available", "Details"},
            {"102", "Lens 24-70mm f2.8", "50.00", "Rented Out", "Details"},
            {"103", "Tripod Manfrotto", "15.00", "Available", "Details"}
        };
        Object[] columns = {"ID", "Equipment Name", "Daily Rental Rate", "Status", "See Details"};
        
        // Construct the Table Components
        DefaultTableModel model = new DefaultTableModel(data, columns);
        JTable table = new JTable(model);
        table.setRowHeight(32);
        
        // Link custom handlers to column index 4 ("See Details")
        table.getColumnModel().getColumn(4).setCellRenderer(new TableButtonRenderer());
        table.getColumnModel().getColumn(4).setCellEditor(new TableButtonEditor(table));

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
            return "Details";
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            int visualRow = table.getEditingRow();
            if (visualRow != -1) {
                int modelRow = table.convertRowIndexToModel(visualRow);
                
                String equipmentId = table.getModel().getValueAt(modelRow, 0).toString();
                String equipmentName = table.getModel().getValueAt(modelRow, 1).toString();
                
                JOptionPane.showMessageDialog(editingButton, 
                    "Opening Profile Details for:\nID: " + equipmentId + "\nName: " + equipmentName);
                
                fireEditingStopped();
            }
        }
    }
}
