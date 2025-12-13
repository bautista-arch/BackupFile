import java.awt.*;
import java.awt.event.*;
import java.time.LocalDate;
import java.util.Random;
import javax.swing.*;

public class CheckoutPanel extends JPanel {

    private AfterLoginPage parent;
    private java.util.List<CartItem> items;
    private JPanel menuPanel;

    // Rounded border for buttons
    static class RoundedBorder extends javax.swing.border.AbstractBorder {
        private final int radius;
        private final Color color;
        private final int thickness;

        public RoundedBorder(int radius, Color color, int thickness) {
            this.radius = radius;
            this.color = color;
            this.thickness = thickness;
        }

        @Override
        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            Graphics2D g2 = (Graphics2D) g;
            g2.setColor(color);
            g2.setStroke(new BasicStroke(thickness));
            g2.drawRoundRect(x + thickness / 2, y + thickness / 2, width - thickness, height - thickness,
                    radius, radius);
        }

        @Override
        public Insets getBorderInsets(Component c) {
            return new Insets(thickness, thickness, thickness, thickness);
        }
    }

    public CheckoutPanel(AfterLoginPage parent, java.util.List<CartItem> items) {
        this.parent = parent;
        this.items = items;

        setLayout(null);
        setBackground(Color.WHITE);
        setPreferredSize(new Dimension(1200, 700));

        createMenuPanel();

        // CHECKOUT title
        JLabel checkoutTitle = new JLabel("CHECKOUT");
        checkoutTitle.setFont(new Font("Arial", Font.BOLD, 32));
        checkoutTitle.setBounds(50, 70, 300, 50);
        add(checkoutTitle);

        // Separator
        JSeparator sep1 = new JSeparator();
        sep1.setBounds(50, 125, 1100, 2);
        add(sep1);

        // Display items
        int yPos = 150;
        for (CartItem item : items) {
            // Item image
            ImageIcon raw = new ImageIcon(item.imagePath);
            Image scaled = raw.getImage().getScaledInstance(80, 100, Image.SCALE_SMOOTH);
            JLabel itemImg = new JLabel(new ImageIcon(scaled));
            itemImg.setBounds(50, yPos, 80, 100);
            add(itemImg);

            // Item title
            JLabel itemTitle = new JLabel(item.title);
            itemTitle.setFont(new Font("Arial", Font.BOLD, 18));
            itemTitle.setBounds(150, yPos, 600, 30);
            add(itemTitle);

            // Item description
            JLabel itemDesc = new JLabel(
                item.description != null && !item.description.isEmpty()
                    ? item.description
                    : "No description"
            );
            itemDesc.setFont(new Font("Arial", Font.PLAIN, 12));
            itemDesc.setForeground(Color.GRAY);
            itemDesc.setBounds(150, yPos + 35, 600, 20);
            add(itemDesc);

            // Item price
            JLabel itemPrice = new JLabel(item.price);
            itemPrice.setFont(new Font("Arial", Font.BOLD, 20));
            itemPrice.setForeground(new Color(150, 0, 0));
            itemPrice.setBounds(150, yPos + 60, 200, 30);
            add(itemPrice);

            yPos += 120; // Space for next item
        }

        // Separator after items
        JSeparator sep2 = new JSeparator();
        sep2.setBounds(50, yPos + 20, 1100, 2);
        add(sep2);

        addPaymentMethods(yPos + 30);

        // Separator
        JSeparator sep3 = new JSeparator();
        sep3.setBounds(50, yPos + 180, 1100, 2);
        add(sep3);

        addPaymentDetails(yPos + 200);
    }

    /* ================= PAYMENT METHODS ================= */

    private void addPaymentMethods(int yPos) {
        JLabel paymentLabel = new JLabel("PAYMENT METHODS");
        paymentLabel.setFont(new Font("Arial", Font.BOLD, 14));
        paymentLabel.setBounds(50, yPos, 200, 25);
        add(paymentLabel);

        JLabel selectLabel = new JLabel("SELECT A METHOD");
        selectLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        selectLabel.setForeground(Color.GRAY);
        selectLabel.setBounds(50, yPos + 25, 200, 20);
        add(selectLabel);

        JButton cashBtn = new JButton("CASH PAYMENT (FACE-TO-FACE)");
        cashBtn.setBounds(80, yPos + 55, 350, 50);
        stylePaymentButton(cashBtn);
        cashBtn.addActionListener(e -> {
            showReceipt();
        });
        add(cashBtn);

        JButton onlineBtn = new JButton("ONLINE PAYMENT");
        onlineBtn.setBounds(770, yPos + 55, 350, 50);
        stylePaymentButton(onlineBtn);
        onlineBtn.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Online payment not implemented yet.");
        });
        add(onlineBtn);
    }

    private void stylePaymentButton(JButton btn) {
        btn.setFont(new Font("Arial", Font.BOLD, 13));
        btn.setBackground(Color.WHITE);
        btn.setForeground(Color.BLACK);
        btn.setFocusPainted(false);
        btn.setContentAreaFilled(false);
        btn.setOpaque(true);
        btn.setBorder(new RoundedBorder(30, Color.BLACK, 1));
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        btn.addMouseListener(new MouseAdapter() {
            @Override public void mouseEntered(MouseEvent e) {
                btn.setBackground(new Color(240, 240, 240));
            }
            @Override public void mouseExited(MouseEvent e) {
                btn.setBackground(Color.WHITE);
            }
            @Override public void mousePressed(MouseEvent e) {
                btn.setBackground(new Color(220, 220, 220));
            }
            @Override public void mouseReleased(MouseEvent e) {
                btn.setBackground(new Color(240, 240, 240));
            }
        });
    }

    /* ================= PAYMENT DETAILS ================= */

    private void addPaymentDetails(int yPos) {
        JLabel paymentDetailsLabel = new JLabel("PAYMENT DETAILS");
        paymentDetailsLabel.setFont(new Font("Arial", Font.BOLD, 14));
        paymentDetailsLabel.setBounds(50, yPos, 200, 25);
        add(paymentDetailsLabel);

        JLabel itemSubLabel = new JLabel("ITEM SUBTOTAL");
        itemSubLabel.setBounds(70, yPos + 30, 150, 20);
        add(itemSubLabel);

        String subtotal = calculateTotalPrice();
        JLabel subtotalPrice = new JLabel(subtotal);
        subtotalPrice.setFont(new Font("Arial", Font.BOLD, 14));
        subtotalPrice.setForeground(new Color(150, 0, 0));
        subtotalPrice.setBounds(800, yPos + 30, 250, 20);
        add(subtotalPrice);

        JLabel totalLabel = new JLabel("TOTAL PAYMENT");
        totalLabel.setFont(new Font("Arial", Font.BOLD, 14));
        totalLabel.setBounds(70, yPos + 70, 200, 25);
        add(totalLabel);

        JLabel totalPrice = new JLabel(subtotal);
        totalPrice.setFont(new Font("Arial", Font.BOLD, 18));
        totalPrice.setForeground(new Color(150, 0, 0));
        totalPrice.setBounds(800, yPos + 70, 250, 25);
        add(totalPrice);
    }

    private String calculateTotalPrice() {
        double total = 0.0;
        for (CartItem item : items) {
            // Assuming price is like "₱100.00"
            String priceStr = item.price.replace("₱", "").trim();
            try {
                total += Double.parseDouble(priceStr);
            } catch (NumberFormatException e) {
                // Handle parsing error, maybe log or skip
            }
        }
        return String.format("₱%.2f", total);
    }

    private void showReceipt() {
        // Clear all components except menuPanel
        Component[] components = getComponents();
        for (Component c : components) {
            if (c != menuPanel) {
                remove(c);
            }
        }

        // Complete purchase
        LocalDate today = LocalDate.now();
        for (CartItem item : items) {
            GlobalTransactionHistory.transactions.add(
                new Transaction(item.title, item.price, today.toString())
            );
            GlobalCartList.cartItems.remove(item);
        }

        // Add receipt content
        addReceiptContent();

        revalidate();
        repaint();
    }

    private void addReceiptContent() {
        // Outer white panel with margin, centered and smaller
        JPanel outer = new JPanel(null);
        outer.setBackground(Color.WHITE);
        outer.setBounds(100, 100, 1000, 600); // Centered, smaller size
        outer.setBorder(BorderFactory.createLineBorder(new Color(200,200,200)));
        add(outer);

        // Header area with light-blue gradient panel
        JPanel header = new JPanel() {
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                GradientPaint gp = new GradientPaint(0, 0, new Color(190,215,255),
                        0, getHeight(), new Color(230,245,255));
                g2.setPaint(gp);
                g2.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        header.setBounds(0, 0, 1000, 120);
        header.setLayout(null);
        header.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        outer.add(header);

        // UC logo placeholder (left)
        JLabel logo = new JLabel("UC", SwingConstants.CENTER);
        logo.setFont(new Font("Arial", Font.BOLD, 36));
        logo.setForeground(new Color(10,40,90));
        logo.setBounds(12, 18, 90, 80);
        logo.setBorder(BorderFactory.createLineBorder(new Color(170,170,170)));
        header.add(logo);

        // University info
        JLabel uni = new JLabel("UNIVERSITY OF CEBU - MAIN CAMPUS");
        uni.setFont(new Font("Arial", Font.BOLD, 18));
        uni.setBounds(120, 16, 540, 26);
        header.add(uni);

        JLabel addr = new JLabel("OSMENA BLVD. COR. SANCIANGKO ST., CEBU CITY 6000, CEBU, PHILIPPINES");
        addr.setFont(new Font("Arial", Font.PLAIN, 11));
        addr.setBounds(120, 40, 700, 18);
        header.add(addr);

        JLabel tel = new JLabel("TEL. NO. (63) (32) 255-7777");
        tel.setFont(new Font("Arial", Font.PLAIN, 11));
        tel.setBounds(120, 58, 300, 16);
        header.add(tel);

        // Semester box (right)
        JTextField semField = new JTextField("1ST SEMESTER S.Y. 2025-2026");
        semField.setEditable(false);
        semField.setHorizontalAlignment(JTextField.CENTER);
        semField.setBounds(650, 18, 240, 26);
        header.add(semField);

        // Priority (right below semester)
        JTextField priorityField = new JTextField(generatePriority());
        priorityField.setEditable(false);
        priorityField.setHorizontalAlignment(JTextField.CENTER);
        priorityField.setBounds(650, 58, 240, 26);
        header.add(priorityField);

        // Student name row (below header)
        JTextField studentField = new JTextField("FIRST NAME, M.I., LAST NAME");
        studentField.setEditable(false);
        studentField.setBounds(20, 130, 620, 28);
        outer.add(studentField);

        JTextField courseField = new JTextField("BSIT");
        courseField.setEditable(false);
        courseField.setBounds(660, 130, 180, 28);
        outer.add(courseField);

        // Table area
        JPanel table = new JPanel(null);
        table.setBackground(Color.WHITE);
        table.setBorder(BorderFactory.createLineBorder(new Color(220,220,220)));
        table.setBounds(20, 170, 960, 420);
        outer.add(table);

        // Column labels
        JLabel colEdp = new JLabel("EDP CODE");
        colEdp.setFont(new Font("Arial", Font.BOLD, 13));
        colEdp.setBounds(20, 10, 120, 20);
        table.add(colEdp);

        JLabel colSub = new JLabel("SUBJECT");
        colSub.setFont(new Font("Arial", Font.BOLD, 13));
        colSub.setBounds(140, 10, 150, 20);
        table.add(colSub);

        JLabel colTitle = new JLabel("BOOK TITLE");
        colTitle.setFont(new Font("Arial", Font.BOLD, 13));
        colTitle.setBounds(300, 10, 360, 20);
        table.add(colTitle);

        JLabel colPrice = new JLabel("PRICE");
        colPrice.setFont(new Font("Arial", Font.BOLD, 13));
        colPrice.setBounds(680, 10, 120, 20);
        table.add(colPrice);

        // Add items
        int y = 40;
        double subtotal = 0.0;
        for (CartItem it : items) {
            JLabel edp = new JLabel(it.edpCode != null ? it.edpCode : "");
            edp.setBounds(20, y, 120, 18);
            table.add(edp);

            JLabel sub = new JLabel(it.subjectCode);
            sub.setBounds(140, y, 150, 18);
            table.add(sub);

            JLabel tt = new JLabel(it.title);
            tt.setBounds(300, y, 360, 18);
            table.add(tt);

            JLabel pr = new JLabel(it.price);
            pr.setHorizontalAlignment(SwingConstants.RIGHT);
            pr.setBounds(680, y, 120, 18);
            table.add(pr);

            subtotal += parsePrice(it.price);
            y += 28;
            // draw thin line
            JSeparator s = new JSeparator();
            s.setBounds(10, y - 8, 940, 1);
            table.add(s);
        }

        // Subtotal and total area (below table)
        JLabel lblSubtotal = new JLabel("ITEM SUBTOTAL");
        lblSubtotal.setFont(new Font("Arial", Font.PLAIN, 13));
        lblSubtotal.setBounds(20, 360, 200, 30);
        table.add(lblSubtotal);

        JLabel valSubtotal = new JLabel(String.format("₱%,.2f", subtotal));
        valSubtotal.setBounds(680, 360, 120, 30);
        valSubtotal.setHorizontalAlignment(SwingConstants.RIGHT);
        valSubtotal.setFont(new Font("Arial", Font.PLAIN, 13));
        table.add(valSubtotal);

        JLabel lblTotal = new JLabel("TOTAL PAYMENT");
        lblTotal.setFont(new Font("Arial", Font.BOLD, 14));
        lblTotal.setBounds(20, 392, 200, 30);
        table.add(lblTotal);

        JLabel valTotal = new JLabel(String.format("₱%,.2f", subtotal));
        valTotal.setBounds(680, 392, 120, 30);
        valTotal.setHorizontalAlignment(SwingConstants.RIGHT);
        valTotal.setFont(new Font("Arial", Font.BOLD, 14));
        table.add(valTotal);

        // Done button
        JButton done = new JButton("DONE");
        done.setBounds(540, 605, 120, 36);
        done.addActionListener(e -> {
            if (parent != null) {
                Window window = SwingUtilities.getWindowAncestor(this);
                if (window != null) window.dispose();
                parent.loadHomePage();
            }
        });
        outer.add(done);
    }

    private String generatePriority() {
        Random r = new Random();
        int num = 100000000 + r.nextInt(900000000);
        return Integer.toString(num);
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

    private void createMenuPanel() {
        menuPanel = new JPanel(null);
        menuPanel.setBackground(Color.BLACK);
        menuPanel.setBounds(0, 0, 1200, 60);

        String[] menuItems = {"Home", "Departments", "Recently Added", "New Arrivals"};
        int slotWidth = 1200 / menuItems.length;

        for (int i = 0; i < menuItems.length; i++) {
            String menuItem = menuItems[i];

            JPanel slotPanel = new JPanel(null);
            slotPanel.setBounds(i * slotWidth, 0, slotWidth, 60);
            slotPanel.setOpaque(false);

            JLabel menuLabel = new JLabel(menuItem, SwingConstants.CENTER);
            menuLabel.setFont(new Font("Arial", Font.BOLD, 14));
            menuLabel.setForeground(Color.WHITE);
            menuLabel.setBounds(0, 0, slotWidth, 60);
            menuLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

            menuLabel.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if (parent == null) return;

                    if (menuItem.equals("Home")) {
                        Window window = SwingUtilities.getWindowAncestor(CheckoutPanel.this);
                        if (window != null) window.dispose();
                        parent.loadHomePage();
                    } else if (menuItem.equals("Departments")) {
                        parent.showDepartmentMenu(slotPanel, menuLabel);
                    } else {
                        JOptionPane.showMessageDialog(null, menuItem + " clicked!");
                    }
                }
            });

            slotPanel.add(menuLabel);
            menuPanel.add(slotPanel);
        }

        add(menuPanel);
    }
}
