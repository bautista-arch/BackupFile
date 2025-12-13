import java.awt.*;
import java.awt.event.*;
import java.time.LocalDate;
import javax.swing.*;

public class CheckoutPanel extends JPanel {

    private AfterLoginPage parent;
    private java.util.List<CartItem> items;

    public CheckoutPanel(AfterLoginPage parent, java.util.List<CartItem> items) {
        this.parent = parent;
        this.items = items;

        setLayout(null);
        setBackground(Color.WHITE);
        setPreferredSize(new Dimension(1200, 800));

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

        // PURCHASE button
        JButton purchaseBtn = new JButton("PURCHASE");
        purchaseBtn.setBounds(400, yPos + 320, 400, 60);
        purchaseBtn.setFont(new Font("Arial", Font.BOLD, 16));
        purchaseBtn.setBackground(Color.WHITE);
        purchaseBtn.setForeground(Color.BLACK);
        purchaseBtn.setFocusPainted(false);
        purchaseBtn.setContentAreaFilled(false);
        purchaseBtn.setOpaque(true);
        purchaseBtn.setBorder(new RoundedBorder(40, Color.BLACK, 2));
        purchaseBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        purchaseBtn.addMouseListener(new MouseAdapter() {
            @Override public void mouseEntered(MouseEvent e) {
                purchaseBtn.setBackground(new Color(240, 240, 240));
            }
            @Override public void mouseExited(MouseEvent e) {
                purchaseBtn.setBackground(Color.WHITE);
            }
            @Override public void mousePressed(MouseEvent e) {
                purchaseBtn.setBackground(new Color(210, 210, 210));
            }
        });

        purchaseBtn.addActionListener(e -> {
            LocalDate today = LocalDate.now();
            for (CartItem item : items) {
                GlobalTransactionHistory.transactions.add(
                    new Transaction(item.title, item.price, today.toString())
                );
                GlobalCartList.cartItems.remove(item);
            }
            JOptionPane.showMessageDialog(this, "Purchase successful!\nThank you for your purchase.");

            if (parent != null) {
                Window window = SwingUtilities.getWindowAncestor(this);
                if (window != null) window.dispose();
                parent.loadHomePage();
            }
        });

        add(purchaseBtn);
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
        add(cashBtn);

        JButton onlineBtn = new JButton("ONLINE PAYMENT");
        onlineBtn.setBounds(770, yPos + 55, 350, 50);
        stylePaymentButton(onlineBtn);
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

    /* ================= TOP MENU ================= */

    private void createMenuPanel() {
        JPanel menuPanel = new JPanel(null);
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
