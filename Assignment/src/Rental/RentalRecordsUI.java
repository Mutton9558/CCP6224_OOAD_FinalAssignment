package Rental;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import core.SystemFacade;

import Equipment.*;
import ui.UIConstants;
import User.*;

public class RentalRecordsUI extends JPanel {

    private final UIConstants uiConstant = new UIConstants();

    private List<Rental> rentalList = new ArrayList<>();
    private DefaultTableModel tableModel;
    private JTable table;

    // Kept so the search feature can call back into the controller/user context
    private final SystemFacade facade;

    private JTextField searchField;

    public RentalRecordsUI(SystemFacade facade) {
        this.facade = facade;

        this.setLayout(new GridBagLayout());
        GridBagConstraints adj = new GridBagConstraints();
        this.setBackground(uiConstant.LightPurple);

        adj.gridwidth = GridBagConstraints.REMAINDER;
        adj.anchor = GridBagConstraints.CENTER;
        adj.insets = new Insets(20, 0, 15, 0);
        adj.weightx = 1.0;
        adj.weighty = 0.0;
        adj.fill = GridBagConstraints.NONE;
        adj.gridy = 0;

        JPanel header = createHeaderPanel();
        header.setAlignmentX(Component.LEFT_ALIGNMENT);
        add(header);

        JPanel searchPanel = createSearchPanel();
        GridBagConstraints searchConstraints = new GridBagConstraints();
        searchConstraints.gridwidth = GridBagConstraints.REMAINDER;
        searchConstraints.anchor = GridBagConstraints.WEST;
        searchConstraints.insets = new Insets(0, 40, 15, 40);
        searchConstraints.weightx = 1.0;
        searchConstraints.weighty = 0.0;
        searchConstraints.fill = GridBagConstraints.HORIZONTAL;
        searchConstraints.gridy = 1;
        add(searchPanel, searchConstraints);

        String[] columns = new String[5];
        columns[0] = "Rental ID";
        columns[1] = "Booked User ID";
        columns[2] = "Equipment";
        columns[3] = "Due Date";
        columns[4] = "Status";

        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int col) {
                return col == 5;
            }
        };

        table = new JTable(tableModel);
        table.setRowHeight(32);

        adj.gridwidth = GridBagConstraints.REMAINDER;
        adj.gridy = 2;
        adj.weightx = 1.0;
        adj.weighty = 1.0;
        adj.fill = GridBagConstraints.BOTH;
        adj.insets = new Insets(0, 40, 25, 40);

        table.setAutoResizeMode(JTable.AUTO_RESIZE_SUBSEQUENT_COLUMNS);
        autoSizeColumns(table);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setHorizontalScrollBarPolicy(
                JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrollPane.setPreferredSize(new Dimension(550, 300));

        adj.gridy = 2;
        adj.weightx = 3.0;
        adj.weighty = 1.0;
        adj.fill = GridBagConstraints.BOTH;
        adj.insets = new Insets(0, 40, 25, 40);
        this.add(scrollPane, adj);

        this.rentalList = facade.getAllRentals();
        loadRentals();
        this.setFocusable(true);
    }

    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public void loadRentals() {
        tableModel.setRowCount(0);
        for (Rental r : rentalList) {
            addRow(r);
        }
        autoSizeColumns(table);
    }

    private void addRow(Rental r) {
        Object[] row = new Object[6];
        row[0] = r.getId();
        row[1] = r.getUserId();
        row[2] = r.getEquipment().getName();
        row[3] = r.getDueDate() != null ? r.getDueDate().format(DATE_FORMAT) : "N/A";
        row[4] = Boolean.TRUE.equals(r.getReturnStatus())
                ? "Returned"
                : (Boolean.TRUE.equals(r.getLateStatus()) ? "Overdue" : "Active");
        row[5] = "Damage Report";
        tableModel.addRow(row);
    }

    private void autoSizeColumns(JTable tbl) {
        TableColumnModel cm = tbl.getColumnModel();
        for (int col = 0; col < tbl.getColumnCount(); col++) {
            int maxWidth = 50;
            Object header = cm.getColumn(col).getHeaderValue();
            if (header != null) {
                maxWidth = Math.max(maxWidth,
                        tbl.getTableHeader()
                                .getFontMetrics(tbl.getTableHeader().getFont())
                                .stringWidth(header.toString()) + 25);
            }
            for (int row = 0; row < tbl.getRowCount(); row++) {
                Object val = tbl.getValueAt(row, col);
                if (val != null) {
                    maxWidth = Math.max(maxWidth,
                            tbl.getFontMetrics(tbl.getFont())
                                    .stringWidth(val.toString()) + 25);
                }
            }
            cm.getColumn(col).setMinWidth(maxWidth);
            cm.getColumn(col).setPreferredWidth(maxWidth);
        }
    }

    private JPanel createHeaderPanel() {
        JPanel headerPanel = new JPanel(new BorderLayout()) {
            @Override
            public Dimension getMaximumSize() {
                return new Dimension(Short.MAX_VALUE, 50);
            }
        };

        headerPanel.setBackground(uiConstant.LightPurple);
        headerPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));

        JLabel titleLabel = new JLabel("My Rentals");
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 28));

        headerPanel.add(titleLabel, BorderLayout.WEST);
        return headerPanel;
    }

    private JPanel createSearchPanel() {
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        searchPanel.setBackground(uiConstant.LightPurple);
        searchPanel.setOpaque(true);

        JLabel searchLabel = new JLabel("Search by User ID:");
        searchLabel.setForeground(Color.WHITE);
        searchLabel.setFont(new Font("Arial", Font.PLAIN, 14));

        searchField = new JTextField(10);

        JButton searchButton = new JButton("Search");
        searchButton.addActionListener(e -> performSearch());
        // accepts enter as an input to search
        searchField.addActionListener(e -> performSearch());

        JButton resetButton = new JButton("Reset");
        resetButton.addActionListener(e -> resetSearch());

        searchPanel.add(searchLabel);
        searchPanel.add(searchField);
        searchPanel.add(searchButton);
        searchPanel.add(resetButton);

        return searchPanel;
    }

    private void performSearch() {
        String text = searchField.getText() == null ? "" : searchField.getText().trim();

        if (text.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Please enter a User ID to search.",
                    "Missing Input",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        int userID;
        try {
            userID = Integer.parseInt(text);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this,
                    "Please enter a valid numeric User ID.",
                    "Invalid Input",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        List<Rental> results = facade.getRentalsByUserID(userID);
        this.rentalList = (results != null) ? results : new ArrayList<>();
        loadRentals();

        if (this.rentalList.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "No rentals found for User ID " + userID + ".",
                    "No Results",
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void resetSearch() {
        searchField.setText("");
        loadRentals();
    }
}