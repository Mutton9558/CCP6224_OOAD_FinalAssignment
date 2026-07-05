package Rental;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import Equipment.*;
import ui.UIConstants;
import User.*;

public class RentalRecordsUI extends JPanel {

    private final UIConstants uiConstant = new UIConstants();

    private List<Rental> rentalList = new ArrayList<>();
    private DefaultTableModel tableModel;
    private JTable table;

    //Adding user once that one's implemented
    public RentalRecordsUI(User client, RentalController controller, UserController userController) {
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
        
        // Will probably alter this one to better fit the page
        JLabel panelTitle = new JLabel("My Rentals");
        panelTitle.setFont(new Font("Sans-Serif", Font.BOLD, 24));
        panelTitle.setForeground(Color.WHITE);
        this.add(panelTitle, adj);

        String[] columns = new String[6];
        columns[0] = "Rental ID";
        columns[1] = "Equipment";
        columns[2] = "Booked Date";
        columns[3] = "Due Date";
        columns[4] = "Duration";
        columns[5] = "Status";

        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int col) {
                return false;
            }
        };

        table = new JTable(tableModel);
        table.setRowHeight(32);

        adj.gridwidth = 1;
        adj.gridy = 1;
        adj.insets = new Insets(10, 0, 10, 0);
        adj.fill = GridBagConstraints.NONE;
        JLabel tableTitle = new JLabel("Rental List");
        tableTitle.setFont(new Font("Sans-Serif", Font.BOLD, 14));
        tableTitle.setForeground(Color.WHITE);
        this.add(tableTitle, adj);

        adj.gridwidth = 2;
        adj.gridy = 1;
        adj.insets = new Insets(0, 0, 10, 0);
        adj.fill = GridBagConstraints.NONE;
        JButton createRental = new JButton("Create Rental");
        createRental.addActionListener(e -> {
            Window window = SwingUtilities.getWindowAncestor(this);
            RentalCreationUI dialog = new RentalCreationUI(window);
            dialog.setVisible(true);
        });
        this.add(createRental, adj);

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

        this.rentalList = controller.getRentalsByUser(client);
        loadRentals(client, userController);
        this.setFocusable(true);
    }

    private static final DateTimeFormatter DATE_FORMAT =
            DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public void loadRentals(User client, UserController userController) {
        tableModel.setRowCount(0);
        for (Rental r : rentalList) {
            addRow(r, client, userController);
        }
        autoSizeColumns(table);
    }

    private String resolveUsername(int userId, User client, UserController userController) {
        if (client != null && client.getUserID() == userId) {
            return client.getName();
        }
        if (userController != null) {
            User u = userController.getUserById(userId);
            if (u != null) {
                return u.getName();
            }
        }
        return "Unknown (ID " + userId + ")";
    }

    private void addRow(Rental r, User client, UserController userController) {
        Equipment equipment = r.getEquipment();
        Object[] row = new Object[6];
        row[0] = r.getId();
        row[1] = equipment != null ? equipment.getName() : "N/A";
        row[2] = r.getBookedDate() != null ? r.getBookedDate().format(DATE_FORMAT) : "N/A";
        row[3] = r.getDueDate() != null ? r.getDueDate().format(DATE_FORMAT) : "N/A";
        row[4] = r.getDuration() + " day(s)";
        row[5] = Boolean.TRUE.equals(r.getReturnStatus())
                ? "Returned"
                : (Boolean.TRUE.equals(r.getLateStatus()) ? "Overdue" : "Active");
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
}