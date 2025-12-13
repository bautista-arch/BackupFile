import java.awt.*;
import java.util.List;
import javax.swing.*;

public class ReceiptWindow extends JFrame {

    private static final int WIDTH = 1200;
    private static final int HEIGHT = 700;

    public ReceiptWindow(List<CartItem> items) {
        this(items, "FIRST NAME, M.I., LAST NAME", "BSIT");
    }

    public ReceiptWindow(List<CartItem> items, String studentName, String course) {

        setTitle("Official Receipt");
        setSize(WIDTH, HEIGHT);              // ✅ SAME AS AfterLoginPage
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(null);

        // MAIN PANEL (same behavior as AfterLoginPage mainPanel)
        JPanel mainPanel = new JPanel(null);
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setBounds(0, 0, WIDTH, HEIGHT);
        add(mainPanel);

        // OUTER RECEIPT PANEL
        JPanel outer = new JPanel(null);
        outer.setBackground(Color.WHITE);
        outer.setBounds(40, 40, 1100, 600);  // centered content like banner/menu
        outer.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 2));
        mainPanel.add(outer);

        // ===== HEADER =====
        JPanel header = new JPanel() {
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                GradientPaint gp = new GradientPaint(
                        0, 0, new Color(190, 215, 255),
                        0, getHeight(), new Color(230, 245, 255));
                g2.setPaint(gp);
                g2.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        header.setLayout(null);
        header.setBounds(0, 0, 1100, 120);
        outer.add(header);

        JLabel uni = new JLabel("UNIVERSITY OF CEBU - MAIN CAMPUS");
        uni.setFont(new Font("Arial", Font.BOLD, 14));
        uni.setBounds(100, 20, 400, 20);
        header.add(uni);

        JTextField studentField = new JTextField(studentName);
        studentField.setEditable(false);
        studentField.setBounds(40, 140, 620, 30);
        outer.add(studentField);

        JTextField courseField = new JTextField(course);
        courseField.setEditable(false);
        courseField.setBounds(700, 140, 200, 30);
        outer.add(courseField);

        // ===== TABLE =====
        JPanel table = new JPanel(null);
        table.setBounds(40, 190, 1020, 300);
        table.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        outer.add(table);

        int y = 30;
        double total = 0;

        for (CartItem it : items) {
            JLabel title = new JLabel(it.title);
            title.setBounds(20, y, 500, 18);
            table.add(title);

            JLabel price = new JLabel(it.price, SwingConstants.RIGHT);
            price.setBounds(850, y, 150, 18);
            table.add(price);

            total += parsePrice(it.price);
            y += 22;
        }

        JLabel totalLbl = new JLabel("TOTAL:");
        totalLbl.setFont(new Font("Arial", Font.BOLD, 14));
        totalLbl.setBounds(700, 510, 80, 20);
        outer.add(totalLbl);

        JLabel totalVal = new JLabel(String.format("₱%,.2f", total));
        totalVal.setFont(new Font("Arial", Font.BOLD, 16));
        totalVal.setBounds(820, 510, 200, 20);
        totalVal.setHorizontalAlignment(SwingConstants.RIGHT);
        outer.add(totalVal);

        JButton done = new JButton("DONE");
        done.setBounds(500, 540, 120, 35);
        done.addActionListener(e -> dispose());
        outer.add(done);

        setVisible(true);
    }

   private double parsePrice(String p) {
    try {
        return Double.parseDouble(p.replaceAll("[^0-9.]", ""));
    } catch (Exception e) {
        return 0;
    }
   }
}
