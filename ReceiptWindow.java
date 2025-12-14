import java.awt.*;
import java.util.List;
import javax.swing.*;
import java.io.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

public class ReceiptWindow extends JFrame {

    private static final int RECEIPT_WIDTH = 1200;
    private static final int RECEIPT_HEIGHT = 700;

    public ReceiptWindow(List<CartItem> items) {
        this(items, "FIRST NAME, M.I., LAST NAME", "BSIT");
    }

    public ReceiptWindow(List<CartItem> items, String studentName, String course) {

        setTitle("Official Receipt");
        setSize(RECEIPT_WIDTH, RECEIPT_HEIGHT); 
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(null);

        JPanel mainPanel = new JPanel(null);
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setBounds(0, 0, RECEIPT_WIDTH, RECEIPT_HEIGHT);
        add(mainPanel);

        // OUTER RECEIPT PANEL
        JPanel outer = new JPanel(null);
        outer.setBackground(Color.WHITE);
        outer.setBounds(50, 50, 1100, 600);  // centered content like banner/menu
        outer.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 2));
        mainPanel.add(outer);

        // ===== HEADER =====
        JPanel header = new JPanel() {
            @Override
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

        JLabel studentLabel = new JLabel("Student Name:");
        studentLabel.setFont(new Font("Arial", Font.BOLD, 12));
        studentLabel.setBounds(40, 120, 100, 20);
        outer.add(studentLabel);

        JTextField studentField = new JTextField(studentName);
        studentField.setEditable(true);
        studentField.setBounds(40, 140, 620, 30);
        outer.add(studentField);
        studentField.requestFocus();
        studentField.selectAll();

        JTextField courseField = new JTextField(course);
        courseField.setEditable(false);
        courseField.setBounds(700, 140, 200, 30);
        outer.add(courseField);

        // ===== TABLE =====
        JPanel content = new JPanel(null);

        int y = 30;
        double total = 0;

        for (CartItem it : items) {
            JLabel title = new JLabel(it.title);
            title.setBounds(20, y, 500, 18);
            content.add(title);

            JLabel price = new JLabel(it.price, SwingConstants.RIGHT);
            price.setBounds(850, y, 150, 18);
            content.add(price);

            total += parsePrice(it.price);
            y += 22;
        }

        content.setPreferredSize(new Dimension(1020, Math.max(300, y + 20)));

        JScrollPane scrollPane = new JScrollPane(content);
        scrollPane.setBounds(40, 190, 1020, 300);
        scrollPane.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        outer.add(scrollPane);

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

        JButton download = new JButton("DOWNLOAD RECEIPT");
        download.setBounds(650, 540, 150, 35);
        download.addActionListener(e -> downloadReceipt(studentField.getText(), courseField.getText(), items));
        outer.add(download);

        setVisible(true);
    }

   private double parsePrice(String p) {
    try {
        return Double.parseDouble(p.replaceAll("[^0-9.]", ""));
    } catch (NumberFormatException e) {
        return 0;
    }
   }

   private void downloadReceipt(String studentName, String course, List<CartItem> items) {
    double total = 0;
    for (CartItem item : items) {
        total += parsePrice(item.price);
    }

    // Create image
    int width = 800;
    int height = 600;
    BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
    Graphics2D g2d = image.createGraphics();
    g2d.setColor(Color.WHITE);
    g2d.fillRect(0, 0, width, height);
    g2d.setColor(Color.BLACK);
    g2d.setFont(new Font("Arial", Font.BOLD, 16));

    int y = 50;
    g2d.drawString("UNIVERSITY OF CEBU - MAIN CAMPUS", 50, y);
    y += 30;
    g2d.drawString("Official Receipt", 50, y);
    y += 40;
    g2d.drawString("Student: " + studentName, 50, y);
    y += 20;
    g2d.drawString("Course: " + course, 50, y);
    y += 40;
    g2d.drawString("Items:", 50, y);
    y += 20;
    for (CartItem item : items) {
        g2d.drawString(item.title + " - " + item.price, 70, y);
        y += 20;
    }
    y += 20;
    g2d.drawString("Total: ₱" + String.format("%,.2f", total), 50, y);

    g2d.dispose();

    try {
        ImageIO.write(image, "png", new File("receipt.png"));
        JOptionPane.showMessageDialog(this, "Receipt downloaded as receipt.png");
    } catch (IOException ex) {
        JOptionPane.showMessageDialog(this, "Error saving receipt: " + ex.getMessage());
    }
   }
}
