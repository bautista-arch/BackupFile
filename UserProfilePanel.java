import java.awt.*;
import javax.swing.*;

public class UserProfilePanel extends JPanel {

    private AfterLoginPage parent;

    public UserProfilePanel(AfterLoginPage parent) {
        this.parent = parent;

        setLayout(null);
        setBackground(Color.WHITE);
        setPreferredSize(new Dimension(1200, 700));

        // Back button
        JButton backBtn = new JButton("BACK");
        backBtn.setBounds(50, 20, 100, 40);
        backBtn.setFont(new Font("Arial", Font.BOLD, 14));
        backBtn.setBackground(new Color(10, 40, 90));
        backBtn.setForeground(Color.WHITE);
        backBtn.setFocusPainted(false);
        backBtn.setBorder(BorderFactory.createEmptyBorder());
        backBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        backBtn.addActionListener(e -> {
            Window window = SwingUtilities.getWindowAncestor(this);
            if (window != null) window.dispose();
        });
        add(backBtn);

        // User Icon (centered at top)
        JLabel userIcon = new JLabel("👤");
        userIcon.setFont(new Font("Arial", Font.PLAIN, 60));
        userIcon.setHorizontalAlignment(SwingConstants.CENTER);
        userIcon.setBounds(550, 20, 100, 100);
        add(userIcon);

        // USER label
        JLabel userLabel = new JLabel("USER");
        userLabel.setFont(new Font("Arial", Font.BOLD, 24));
        userLabel.setHorizontalAlignment(SwingConstants.CENTER);
        userLabel.setBounds(500, 120, 200, 30);
        add(userLabel);

        // Separator line
        JSeparator sep1 = new JSeparator();
        sep1.setBounds(450, 155, 300, 2);
        add(sep1);

        // TRANSACTION HISTORY label
        JLabel transLabel = new JLabel("TRANSACTION HISTORY");
        transLabel.setFont(new Font("Arial", Font.BOLD, 20));
        transLabel.setHorizontalAlignment(SwingConstants.CENTER);
        transLabel.setBounds(450, 170, 300, 30);
        add(transLabel);

        // Transaction items panel
        JPanel transPanel = new JPanel();
        transPanel.setLayout(new BoxLayout(transPanel, BoxLayout.Y_AXIS));
        transPanel.setBackground(Color.WHITE);

        if (GlobalTransactionHistory.transactions.isEmpty()) {
            JLabel empty = new JLabel("No transactions yet");
            empty.setFont(new Font("Arial", Font.PLAIN, 16));
            empty.setForeground(Color.GRAY);
            empty.setAlignmentX(Component.CENTER_ALIGNMENT);
            transPanel.add(Box.createVerticalStrut(30));
            transPanel.add(empty);
        } else {
            for (Transaction trans : GlobalTransactionHistory.transactions) {
                transPanel.add(createTransactionItem(trans));
                transPanel.add(Box.createVerticalStrut(15));
            }
        }

        JScrollPane scroll = new JScrollPane(transPanel);
        scroll.setBounds(200, 210, 800, 380);
        scroll.setBorder(BorderFactory.createEmptyBorder());
        scroll.getVerticalScrollBar().setUnitIncrement(10);
        add(scroll);

        // LOG OUT button
        JButton logoutBtn = new JButton("LOG OUT");
        logoutBtn.setBounds(450, 610, 300, 50);
        logoutBtn.setFont(new Font("Arial", Font.BOLD, 18));
        logoutBtn.setBackground(new Color(10, 40, 90));
        logoutBtn.setForeground(Color.WHITE);
        logoutBtn.setFocusPainted(false);
        logoutBtn.setBorder(BorderFactory.createEmptyBorder());
        logoutBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        logoutBtn.addActionListener(e -> {
            // Close the user profile window
            Window userWindow = SwingUtilities.getWindowAncestor(this);
            if (userWindow != null) userWindow.dispose();

            // Close the main window
            Window parentWindow = SwingUtilities.getWindowAncestor(parent);
            if (parentWindow != null) parentWindow.dispose();

            // Go back to main GUI
            new UcTextBook.HomePage();
        });
        add(logoutBtn);
    }

    private JPanel createTransactionItem(Transaction trans) {
        JPanel box = new JPanel(null);
        box.setPreferredSize(new Dimension(750, 60));
        box.setBackground(new Color(240, 240, 240));
        box.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(200, 200, 200)));
        box.setMaximumSize(new Dimension(750, 60));

        JLabel titleLabel = new JLabel(trans.title);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 14));
        titleLabel.setBounds(15, 5, 400, 20);
        box.add(titleLabel);

        JLabel priceLabel = new JLabel(trans.price);
        priceLabel.setFont(new Font("Arial", Font.BOLD, 16));
        priceLabel.setForeground(new Color(150, 0, 0));
        priceLabel.setBounds(15, 30, 150, 20);
        box.add(priceLabel);

        JLabel dateLabel = new JLabel(trans.date);
        dateLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        dateLabel.setForeground(Color.GRAY);
        dateLabel.setBounds(600, 30, 130, 20);
        box.add(dateLabel);

        return box;
    }
}
