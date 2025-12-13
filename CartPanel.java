import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

public class CartPanel extends JPanel {

    private AfterLoginPage parent;
    private Map<CartItem, JCheckBox> selectionMap = new HashMap<>();
    private JCheckBox selectAll;
    private JLabel subtotalLabel;
    private JLabel totalLabel;
    private JPanel itemsPanel;
    private JScrollPane scroll;

    public CartPanel(AfterLoginPage parent) {
        this.parent = parent;

        setLayout(null);
        setBackground(Color.WHITE);
        setPreferredSize(new Dimension(1200, 800));

        createMenuPanel();

        JLabel title = new JLabel("USER'S CART");
        title.setFont(new Font("Arial", Font.BOLD, 36));
        title.setBounds(50, 70, 400, 40);
        add(title);

        JPanel itemsPanel = new JPanel();
        itemsPanel.setLayout(new BoxLayout(itemsPanel, BoxLayout.Y_AXIS));
        itemsPanel.setBackground(Color.WHITE);
        this.itemsPanel = itemsPanel;

        // --- HEADER (Select All + Summary)
        JPanel header = new JPanel(null);
        header.setPreferredSize(new Dimension(1100, 40));
        header.setBackground(Color.WHITE);

        selectAll = new JCheckBox();
        selectAll.setBounds(10, 8, 24, 24);
        selectAll.setBackground(Color.WHITE);

        selectAll.addActionListener(e -> {
            boolean sel = selectAll.isSelected();
            for (JCheckBox cb : selectionMap.values()) cb.setSelected(sel);
            updateSummary();
        });

        JLabel selLabel = new JLabel("Select All");
        selLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        selLabel.setBounds(35, 8, 100, 24);

        header.add(selectAll);
        header.add(selLabel);

        subtotalLabel = new JLabel("Subtotal: ₱0.00");
        subtotalLabel.setFont(new Font("Arial", Font.BOLD, 14));
        subtotalLabel.setBounds(700, 8, 200, 24);
        header.add(subtotalLabel);

        totalLabel = new JLabel("Total: ₱0.00");
        totalLabel.setFont(new Font("Arial", Font.BOLD, 14));
        totalLabel.setBounds(860, 8, 200, 24);
        header.add(totalLabel);

        itemsPanel.add(header);

        // --- ITEMS
        if (GlobalCartList.cartItems.isEmpty()) {
            JLabel empty = new JLabel("Your cart is empty.");
            empty.setFont(new Font("Arial", Font.BOLD, 20));
            empty.setAlignmentX(Component.CENTER_ALIGNMENT);
            itemsPanel.add(Box.createVerticalStrut(30));
            itemsPanel.add(empty);
        } else {
            for (CartItem item : GlobalCartList.cartItems) {
                itemsPanel.add(createItemPanel(item));
                itemsPanel.add(createGradientBar()); // gradient like your screenshot
            }
        }

        JScrollPane scroll = new JScrollPane(itemsPanel);
        scroll.setBounds(50, 120, 1100, 540);
        scroll.setBorder(null);
        add(scroll);
        this.scroll = scroll;

        // --- CHECKOUT BUTTON
        JButton checkoutBtn = new JButton("Check Out");
        checkoutBtn.setBounds(450, 680, 300, 55);
        checkoutBtn.setFont(new Font("Arial", Font.BOLD, 20));
        checkoutBtn.setBackground(new Color(10, 30, 70));
        checkoutBtn.setForeground(Color.WHITE);
        checkoutBtn.setFocusPainted(false);
        checkoutBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        checkoutBtn.setBorder(new RoundedBorder(30, new Color(10, 30, 70), 2));

        checkoutBtn.addActionListener(e -> {
            java.util.List<CartItem> selected = getSelectedItems();
            if (selected.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please select items to checkout.");
            } else {
                parent.loadCheckout(selected);
            }
        });

        add(checkoutBtn);
    }

    private JPanel createItemPanel(CartItem item) {
    JPanel box = new JPanel(null);
    box.setPreferredSize(new Dimension(1100, 185));
    box.setBackground(Color.WHITE);

    // --- CHECKBOX (left side)
    JCheckBox selector = new JCheckBox();
    selector.setBounds(10, 70, 26, 26);
    selector.setBackground(Color.WHITE);
    selector.setFont(new Font("Arial", Font.BOLD, 20));
    selectionMap.put(item, selector);
    selector.addActionListener(e -> updateSummary());
    box.add(selector);

    // --- BOOK IMAGE (120x150)
    ImageIcon raw = new ImageIcon(item.imagePath);
    Image scaled = raw.getImage().getScaledInstance(140, 160, Image.SCALE_SMOOTH);
    JLabel img = new JLabel(new ImageIcon(scaled));
    img.setBounds(60, 10, 140, 160);
    box.add(img);

    // --- TITLE (bold)
    JLabel title = new JLabel(item.title.toUpperCase());
    title.setFont(new Font("Arial", Font.BOLD, 22));
    title.setBounds(240, 20, 700, 30);
    box.add(title);

    // --- SUBTITLE / DESCRIPTION
    JLabel subtitle = new JLabel(
        item.description != null ? item.description 
        : "A STRUCTURED PROGRAMMING APPROACH IN C 4TH ED"
    );
    subtitle.setFont(new Font("Arial", Font.PLAIN, 14));
    subtitle.setBounds(240, 55, 620, 20);
    box.add(subtitle);

    // --- PRICE (big, dark red)
    JLabel price = new JLabel(item.price);
    price.setFont(new Font("Arial", Font.BOLD, 30));
    price.setForeground(new Color(139, 0, 0)); // dark red
    price.setBounds(240, 85, 500, 50);
    box.add(price);

    // --- TRASH BUTTON (right side)
    ImageIcon deleteIcon = new ImageIcon("c:\\Users\\ASUS\\Documents\\ImageFile\\trash.png");
    Image d = deleteIcon.getImage().getScaledInstance(42, 42, Image.SCALE_SMOOTH);
    JButton remove = new JButton(new ImageIcon(d));
    remove.setBounds(1030, 65, 42, 42);
    remove.setBorder(null);
    remove.setContentAreaFilled(false);
    remove.setCursor(new Cursor(Cursor.HAND_CURSOR));

    remove.addActionListener(e -> {
        GlobalCartList.cartItems.remove(item);
        refreshCart();
    });

    box.add(remove);

    return box;
}


    private JPanel createGradientBar() {
    JPanel bar = new JPanel() {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.setColor(new Color(200, 200, 200));
            g.fillRect(0, 0, getWidth(), 2);
        }
    };
    bar.setPreferredSize(new Dimension(1100, 35));
    bar.setBackground(Color.WHITE);
    return bar;
}


    private double parsePrice(String priceStr) {
        try {
            String cleaned = priceStr.replaceAll("[^0-9.]", "");
            if (cleaned.isEmpty()) return 0.0;
            return Double.parseDouble(cleaned);
        } catch (Exception ex) {
            return 0.0;
        }
    }

    private void updateSummary() {
        double subtotal = 0.0;
        for (Map.Entry<CartItem, JCheckBox> e : selectionMap.entrySet()) {
            if (e.getValue().isSelected()) {
                subtotal += parsePrice(e.getKey().price);
            }
        }

        subtotalLabel.setText(String.format("Subtotal: ₱%,.2f", subtotal));
        totalLabel.setText(String.format("Total: ₱%,.2f", subtotal));

        boolean all = !selectionMap.isEmpty() &&
                selectionMap.values().stream().allMatch(JCheckBox::isSelected);
        selectAll.setSelected(all);
    }

    private void refreshCart() {
        selectionMap.clear();
        itemsPanel.removeAll();

        // Rebuild header
        JPanel header = new JPanel(null);
        header.setPreferredSize(new Dimension(1100, 40));
        header.setBackground(Color.WHITE);

        selectAll = new JCheckBox();
        selectAll.setBounds(10, 8, 24, 24);
        selectAll.setBackground(Color.WHITE);

        selectAll.addActionListener(e -> {
            boolean sel = selectAll.isSelected();
            for (JCheckBox cb : selectionMap.values()) cb.setSelected(sel);
            updateSummary();
        });

        JLabel selLabel = new JLabel("Select All");
        selLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        selLabel.setBounds(35, 8, 100, 24);

        header.add(selectAll);
        header.add(selLabel);

        subtotalLabel = new JLabel("Subtotal: ₱0.00");
        subtotalLabel.setFont(new Font("Arial", Font.BOLD, 14));
        subtotalLabel.setBounds(700, 8, 200, 24);
        header.add(subtotalLabel);

        totalLabel = new JLabel("Total: ₱0.00");
        totalLabel.setFont(new Font("Arial", Font.BOLD, 14));
        totalLabel.setBounds(860, 8, 200, 24);
        header.add(totalLabel);

        itemsPanel.add(header);

        // Rebuild items
        if (GlobalCartList.cartItems.isEmpty()) {
            JLabel empty = new JLabel("Your cart is empty.");
            empty.setFont(new Font("Arial", Font.BOLD, 20));
            empty.setAlignmentX(Component.CENTER_ALIGNMENT);
            itemsPanel.add(Box.createVerticalStrut(30));
            itemsPanel.add(empty);
        } else {
            for (CartItem item : GlobalCartList.cartItems) {
                itemsPanel.add(createItemPanel(item));
                itemsPanel.add(createGradientBar());
            }
        }

        itemsPanel.revalidate();
        itemsPanel.repaint();
        scroll.revalidate();
        scroll.repaint();
        updateSummary();
    }

    private java.util.List<CartItem> getSelectedItems() {
        java.util.List<CartItem> list = new ArrayList<>();
        for (Map.Entry<CartItem, JCheckBox> e : selectionMap.entrySet()) {
            if (e.getValue().isSelected()) list.add(e.getKey());
        }
        return list;
    }

    // -----------------------
    // Menu Bar
    // -----------------------
    private void createMenuPanel() {
        JPanel menuPanel = new JPanel(null);
        menuPanel.setBackground(Color.BLACK);
        menuPanel.setBounds(0, 0, 1200, 60);

        String[] menuItems = {"Home", "Departments", "Recently Added", "New Arrivals"};
        int totalWidth = 1100;
        int itemHeight = 60;
        int slotWidth = totalWidth / menuItems.length;

        for (int i = 0; i < menuItems.length; i++) {
            String item = menuItems[i];
            JPanel slotPanel = new JPanel(null);
            slotPanel.setBounds(i * slotWidth, 0, slotWidth, itemHeight);
            slotPanel.setOpaque(false);

            JLabel menuLabel = new JLabel(item, SwingConstants.CENTER);
            menuLabel.setFont(new Font("Arial", Font.BOLD, 14));
            menuLabel.setForeground(Color.WHITE);
            menuLabel.setBounds(0, 0, slotWidth, itemHeight);
            menuLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));

            menuLabel.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if (parent != null) {
                        if (item.equals("Home")) {
                            Window window = SwingUtilities.getWindowAncestor(CartPanel.this);
                            if (window != null) window.dispose();
                            parent.loadHomePage();
                        }
                        else if (item.equals("Departments")) parent.showDepartmentMenu(slotPanel, menuLabel);
                        else JOptionPane.showMessageDialog(null, item + " clicked!");
                    }
                }
            });

            slotPanel.add(menuLabel);
            menuPanel.add(slotPanel);
        }

        add(menuPanel);
    }
}
