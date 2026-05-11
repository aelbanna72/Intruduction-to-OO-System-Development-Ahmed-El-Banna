package Assignment;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;


public class ShopGUI extends JFrame {



    private final List<StockItem> stockList = new ArrayList<>();

    // Table to display all stock items
    private JTable stockTable;
    private DefaultTableModel tableModel;

    // Output / log area
    private JTextArea outputArea;

    // Input fields (Create Item panel)
    private JTextField tfStockCode, tfQuantity, tfPrice;
    private JComboBox<String> cbItemType;

    // Extra fields for subclasses (shown/hidden dynamically)
    private JTextField tfExtra1, tfExtra2;
    private JLabel lblExtra1, lblExtra2;

    // Operation fields
    private JTextField tfOpAmount, tfOpPrice;

    // Color scheme
    private static final Color BG_DARK   = new Color(28, 32, 38);
    private static final Color BG_PANEL  = new Color(38, 44, 54);
    private static final Color ACCENT    = new Color(230, 95, 35);
    private static final Color TEXT_MAIN = new Color(230, 230, 230);
    private static final Color TEXT_DIM  = new Color(140, 148, 160);
    private static final Color BORDER_C  = new Color(55, 62, 75);
    private static final Color ROW_ALT   = new Color(44, 50, 62);

    // -------------------------------------------------------
    // Constructor
    // -------------------------------------------------------

    public ShopGUI() {
        super("Car Parts & Accessories Shop");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1100, 720);
        setMinimumSize(new Dimension(900, 600));
        setLocationRelativeTo(null);
        getContentPane().setBackground(BG_DARK);

        buildUI();
        setVisible(true);
    }

    // -------------------------------------------------------
    // UI Construction
    // -------------------------------------------------------

    private void buildUI() {
        setLayout(new BorderLayout(10, 10));

        // Top title bar
        JPanel titleBar = buildTitleBar();
        add(titleBar, BorderLayout.NORTH);

        // Centre: table
        JPanel centrePanel = buildTablePanel();
        add(centrePanel, BorderLayout.CENTER);

        // Right: controls
        JPanel rightPanel = buildControlPanel();
        add(rightPanel, BorderLayout.EAST);

        // Bottom: output log
        JPanel bottomPanel = buildOutputPanel();
        add(bottomPanel, BorderLayout.SOUTH);
    }

    // ---- Title bar ----
    private JPanel buildTitleBar() {
        JPanel p = new JPanel(new BorderLayout());
        p.setBackground(BG_PANEL);
        p.setBorder(new EmptyBorder(12, 20, 12, 20));

        JLabel title = new JLabel("CAR PARTS & ACCESSORIES");
        title.setFont(new Font("Monospaced", Font.BOLD, 18));
        title.setForeground(ACCENT);

        JLabel sub = new JLabel("Stock Management System");
        sub.setFont(new Font("SansSerif", Font.PLAIN, 12));
        sub.setForeground(TEXT_DIM);

        JPanel left = new JPanel(new GridLayout(2, 1));
        left.setOpaque(false);
        left.add(title);
        left.add(sub);
        p.add(left, BorderLayout.WEST);
        return p;
    }

    // ---- Stock table ----
    private JPanel buildTablePanel() {
        JPanel p = new JPanel(new BorderLayout(0, 6));
        p.setBackground(BG_DARK);
        p.setBorder(new EmptyBorder(10, 12, 0, 6));

        JLabel lbl = styledLabel("STOCK INVENTORY", true);
        p.add(lbl, BorderLayout.NORTH);

        String[] cols = {"#", "Code", "Type", "Description", "Qty", "Price (ex VAT)", "Price (inc VAT)"};
        tableModel = new DefaultTableModel(cols, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };

        stockTable = new JTable(tableModel);
        styleTable(stockTable);

        JScrollPane scroll = new JScrollPane(stockTable);
        scroll.setBackground(BG_PANEL);
        scroll.getViewport().setBackground(BG_PANEL);
        scroll.setBorder(BorderFactory.createLineBorder(BORDER_C));
        p.add(scroll, BorderLayout.CENTER);
        return p;
    }

    private void styleTable(JTable t) {
        t.setBackground(BG_PANEL);
        t.setForeground(TEXT_MAIN);
        t.setGridColor(BORDER_C);
        t.setFont(new Font("Monospaced", Font.PLAIN, 12));
        t.setRowHeight(24);
        t.setSelectionBackground(new Color(ACCENT.getRed(), ACCENT.getGreen(), ACCENT.getBlue(), 80));
        t.setSelectionForeground(TEXT_MAIN);

        JTableHeader header = t.getTableHeader();
        header.setBackground(BG_DARK);
        header.setForeground(ACCENT);
        header.setFont(new Font("SansSerif", Font.BOLD, 11));
        header.setBorder(BorderFactory.createLineBorder(BORDER_C));

        // Alternating row renderer
        t.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                                                           boolean isSelected, boolean hasFocus, int row, int col) {
                super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, col);
                setBackground(isSelected ? new Color(ACCENT.getRed(), ACCENT.getGreen(), ACCENT.getBlue(), 80)
                        : (row % 2 == 0 ? BG_PANEL : ROW_ALT));
                setForeground(TEXT_MAIN);
                setBorder(BorderFactory.createEmptyBorder(0, 6, 0, 6));
                return this;
            }
        });
    }

    // ---- Right control panel ----
    private JPanel buildControlPanel() {
        JPanel p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        p.setBackground(BG_DARK);
        p.setBorder(new EmptyBorder(10, 6, 10, 12));
        p.setPreferredSize(new Dimension(300, 0));

        p.add(buildCreatePanel());
        p.add(Box.createVerticalStrut(10));
        p.add(buildOperationsPanel());

        return p;
    }

    // ---- Create item panel ----
    private JPanel buildCreatePanel() {
        JPanel outer = darkPanel("CREATE NEW ITEM");

        // Item type selector
        String[] types = {"StockItem", "NavSys", "CarTyre", "EngineOil", "CarBattery"};
        cbItemType = new JComboBox<>(types);
        styleCombo(cbItemType);
        cbItemType.addActionListener(_ -> updateExtraFields());

        tfStockCode = styledField();
        tfQuantity  = styledField();
        tfPrice     = styledField();

        // Extra fields (for subclass-specific data)
        lblExtra1 = styledLabel("Extra field 1:", false);
        lblExtra2 = styledLabel("Extra field 2:", false);
        tfExtra1  = styledField();
        tfExtra2  = styledField();

        JButton btnCreate = accentButton("＋ Create Item");
        btnCreate.addActionListener(_ -> createItem());

        outer.add(formRow("Item Type:", cbItemType));
        outer.add(formRow("Stock Code:", tfStockCode));
        outer.add(formRow("Quantity:", tfQuantity));
        outer.add(formRow("Price (ex VAT):", tfPrice));
        outer.add(formRow(lblExtra1, tfExtra1));
        outer.add(formRow(lblExtra2, tfExtra2));
        outer.add(Box.createVerticalStrut(6));
        outer.add(btnCreate);

        updateExtraFields();
        return outer;
    }

    // ---- Operations panel ----
    private JPanel buildOperationsPanel() {
        JPanel outer = darkPanel("OPERATIONS  (select row first)");

        tfOpAmount = styledField();
        tfOpPrice  = styledField();

        JButton btnAdd   = accentButton("Add Stock");
        JButton btnSell  = grayButton("Sell Stock");
        JButton btnPrice = grayButton("Change Price");
        JButton btnDel   = grayButton("Delete Item");
        JButton btnClear = grayButton("Clear Log");

        btnAdd.addActionListener(_ -> opAddStock());
        btnSell.addActionListener(_ -> opSellStock());
        btnPrice.addActionListener(_ -> opChangePrice());
        btnDel.addActionListener(_ -> opDeleteItem());
        btnClear.addActionListener(_ -> outputArea.setText(""));

        outer.add(formRow("Amount:", tfOpAmount));
        outer.add(formRow("New Price:", tfOpPrice));
        outer.add(Box.createVerticalStrut(4));
        outer.add(btnAdd);
        outer.add(Box.createVerticalStrut(4));
        outer.add(btnSell);
        outer.add(Box.createVerticalStrut(4));
        outer.add(btnPrice);
        outer.add(Box.createVerticalStrut(4));
        outer.add(btnDel);
        outer.add(Box.createVerticalStrut(8));
        outer.add(btnClear);
        return outer;
    }

    // ---- Output log ----
    private JPanel buildOutputPanel() {
        JPanel p = new JPanel(new BorderLayout(0, 4));
        p.setBackground(BG_DARK);
        p.setBorder(new EmptyBorder(4, 12, 10, 12));
        p.setPreferredSize(new Dimension(0, 160));

        JLabel lbl = styledLabel("OUTPUT LOG", true);
        p.add(lbl, BorderLayout.NORTH);

        outputArea = new JTextArea();
        outputArea.setEditable(false);
        outputArea.setBackground(new Color(18, 20, 26));
        outputArea.setForeground(new Color(100, 220, 100));
        outputArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        outputArea.setBorder(new EmptyBorder(6, 8, 6, 8));

        JScrollPane scroll = new JScrollPane(outputArea);
        scroll.setBorder(BorderFactory.createLineBorder(BORDER_C));
        p.add(scroll, BorderLayout.CENTER);
        return p;
    }

    // -------------------------------------------------------
    // Helper UI builders
    // -------------------------------------------------------

    private JPanel darkPanel(String title) {
        JPanel p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        p.setBackground(BG_PANEL);
        p.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER_C),
                new EmptyBorder(10, 12, 10, 12)));
        JLabel lbl = new JLabel(title);
        lbl.setForeground(ACCENT);
        lbl.setFont(new Font("SansSerif", Font.BOLD, 11));
        lbl.setAlignmentX(LEFT_ALIGNMENT);
        p.add(lbl);
        p.add(Box.createVerticalStrut(8));
        return p;
    }

    private JPanel formRow(String label, JComponent field) {
        return formRow(styledLabel(label, false), field);
    }

    private JPanel formRow(JLabel label, JComponent field) {
        JPanel row = new JPanel(new BorderLayout(6, 0));
        row.setOpaque(false);
        row.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        row.setBorder(new EmptyBorder(2, 0, 2, 0));
        label.setPreferredSize(new Dimension(100, 24));
        row.add(label, BorderLayout.WEST);
        row.add(field, BorderLayout.CENTER);
        return row;
    }

    private JLabel styledLabel(String text, boolean accent) {
        JLabel l = new JLabel(text);
        l.setForeground(accent ? ACCENT : TEXT_DIM);
        l.setFont(new Font("SansSerif", accent ? Font.BOLD : Font.PLAIN, 11));
        return l;
    }

    private JTextField styledField() {
        JTextField f = new JTextField();
        f.setBackground(BG_DARK);
        f.setForeground(TEXT_MAIN);
        f.setCaretColor(TEXT_MAIN);
        f.setFont(new Font("Monospaced", Font.PLAIN, 12));
        f.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER_C),
                new EmptyBorder(2, 6, 2, 6)));
        return f;
    }

    private void styleCombo(JComboBox<String> cb) {
        cb.setBackground(BG_DARK);
        cb.setForeground(TEXT_MAIN);
        cb.setFont(new Font("Monospaced", Font.PLAIN, 12));
        cb.setBorder(BorderFactory.createLineBorder(BORDER_C));
    }

    private JButton accentButton(String text) {
        JButton b = new JButton(text);
        b.setBackground(ACCENT);
        b.setForeground(Color.WHITE);
        b.setFont(new Font("SansSerif", Font.BOLD, 12));
        b.setBorder(new EmptyBorder(6, 12, 6, 12));
        b.setFocusPainted(false);
        b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        b.setAlignmentX(LEFT_ALIGNMENT);
        b.setMaximumSize(new Dimension(Integer.MAX_VALUE, 32));
        return b;
    }

    private JButton grayButton(String text) {
        JButton b = new JButton(text);
        b.setBackground(BG_DARK);
        b.setForeground(TEXT_MAIN);
        b.setFont(new Font("SansSerif", Font.PLAIN, 12));
        b.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER_C),
                new EmptyBorder(5, 12, 5, 12)));
        b.setFocusPainted(false);
        b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        b.setAlignmentX(LEFT_ALIGNMENT);
        b.setMaximumSize(new Dimension(Integer.MAX_VALUE, 32));
        return b;
    }

    // -------------------------------------------------------
    // Dynamic extra fields based on item type
    // -------------------------------------------------------

    private void updateExtraFields() {
        String type = (String) cbItemType.getSelectedItem();
        switch (type) {
            case "CarTyre":
                lblExtra1.setText("Tyre Size:");
                lblExtra2.setText("Brand:");
                tfExtra1.setEnabled(true);
                tfExtra2.setEnabled(true);
                tfExtra1.setToolTipText("e.g. 205/55R16");
                tfExtra2.setToolTipText("e.g. Michelin");
                break;
            case "EngineOil":
                lblExtra1.setText("Viscosity:");
                lblExtra2.setText("Volume (L):");
                tfExtra1.setEnabled(true);
                tfExtra2.setEnabled(true);
                tfExtra1.setToolTipText("e.g. 5W-30");
                tfExtra2.setToolTipText("e.g. 5");
                break;
            case "CarBattery":
                lblExtra1.setText("Capacity (Ah):");
                lblExtra2.setText("Battery Type:");
                tfExtra1.setEnabled(true);
                tfExtra2.setEnabled(true);
                tfExtra1.setToolTipText("e.g. 60");
                tfExtra2.setToolTipText("e.g. AGM");
                break;
            case null:
                break;
            default:
                // StockItem or NavSys — no extra fields
                lblExtra1.setText("(N/A)");
                lblExtra2.setText("(N/A)");
                tfExtra1.setText("");
                tfExtra2.setText("");
                tfExtra1.setEnabled(false);
                tfExtra2.setEnabled(false);
        }
    }

    // -------------------------------------------------------
    // Actions
    // -------------------------------------------------------


    private void createItem() {
        try {
            String code = tfStockCode.getText().trim();
            int qty     = Integer.parseInt(tfQuantity.getText().trim());
            double price = Double.parseDouble(tfPrice.getText().trim());
            String type = (String) cbItemType.getSelectedItem();

            if (code.isEmpty()) {
                log("ERROR: Stock code cannot be empty.");
                return;
            }

            StockItem item = null;
            switch (type) {
                case "NavSys":
                    item = new NavSys(code, qty, price);
                    break;
                case "CarTyre":
                    String size  = tfExtra1.getText().trim();
                    String brand = tfExtra2.getText().trim();
                    item = new CarTyre(code, qty, price, size, brand);
                    break;
                case "EngineOil":
                    String misc = tfExtra1.getText().trim();
                    int vol = Integer.parseInt(tfExtra2.getText().trim());
                    item = new EngineOil(code, qty, price, misc, vol);
                    break;
                case "CarBattery":
                    int cap = Integer.parseInt(tfExtra1.getText().trim());
                    String bType = tfExtra2.getText().trim();
                    item = new CarBattery(code, qty, price, cap, bType);
                    break;
                case null:
                    break;
                default:
                    item = new StockItem(code, qty, price);
            }

            stockList.add(item);
            refreshTable();
            if (item != null) {
                log("✔ Created: " + item.getStockName() + " [" + code + "]\n" + item);
            }
            clearCreateFields();

        } catch (NumberFormatException ex) {
            log("ERROR: Invalid number format — check Quantity, Price, and extra fields.");
        }
    }

    private void opAddStock() {
        StockItem item = getSelectedItem();
        if (item == null) return;
        try {
            int amount = Integer.parseInt(tfOpAmount.getText().trim());
            item.addStock(amount);
            refreshTable();
            log("Add Stock (" + amount + ") → " + item.getStockCode() + "\n" + item);
        } catch (NumberFormatException e) {
            log("ERROR: Enter a valid integer for amount.");
        }
    }

    private void opSellStock() {
        StockItem item = getSelectedItem();
        if (item == null) return;
        try {
            int amount = Integer.parseInt(tfOpAmount.getText().trim());
            boolean ok = item.sellStock(amount);
            refreshTable();
            if (ok) {
                log("Sold " + amount + " unit(s) of " + item.getStockCode() + "\n" + item);
            } else {
                log("ERROR: Could not sell " + amount + " unit(s) — insufficient stock.");
            }
        } catch (NumberFormatException e) {
            log("ERROR: Enter a valid integer for amount.");
        }
    }

    private void opChangePrice() {
        StockItem item = getSelectedItem();
        if (item == null) return;
        try {
            double newPrice = Double.parseDouble(tfOpPrice.getText().trim());
            item.setPrice(newPrice);
            refreshTable();
            log("Price updated for " + item.getStockCode() + "\n" + item);
        } catch (NumberFormatException e) {
            log("ERROR: Enter a valid price.");
        }
    }

    private void opDeleteItem() {
        int row = stockTable.getSelectedRow();
        if (row < 0) { log("ERROR: Select a row first."); return; }
        StockItem removed = stockList.remove(row);
        refreshTable();
        log("Deleted: " + removed.getStockCode() + " – " + removed.getStockName());
    }

    // -------------------------------------------------------
    // Helpers/ Error message
    // -------------------------------------------------------

    private StockItem getSelectedItem() {
        int row = stockTable.getSelectedRow();
        if (row < 0) {
            log("ERROR: Please select a row in the table first.");
            return null;
        }
        return stockList.get(row);
    }


    private void refreshTable() {
        tableModel.setRowCount(0);
        for (int i = 0; i < stockList.size(); i++) {
            StockItem s = stockList.get(i);
            tableModel.addRow(new Object[]{
                    i + 1,
                    s.getStockCode(),
                    s.getStockName(),
                    s.getStockDescription(),
                    s.getQuantity(),
                    String.format("£%.2f", s.getPrice()),
                    String.format("£%.2f", s.getPriceWithVAT())
            });
        }
    }

    private void log(String message) {
        outputArea.append("[LOG] " + message + "\n" + "-".repeat(60) + "\n");
        outputArea.setCaretPosition(outputArea.getDocument().getLength());
    }

    private void clearCreateFields() {
        tfStockCode.setText("");
        tfQuantity.setText("");
        tfPrice.setText("");
        tfExtra1.setText("");
        tfExtra2.setText("");
    }

    // -------------------------------------------------------
    // Entry point
    // -------------------------------------------------------


    static void main() {
        SwingUtilities.invokeLater(ShopGUI::new);
    }
}