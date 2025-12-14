import java.awt.*;
import javax.swing.*;

public class HomePage extends JFrame {

    public HomePage() {

        setTitle("UC Textbook Portal");
        setSize(950, 520);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(new GridLayout(1, 2));

        JPanel leftPanel = new JPanel() {
            Image bg = new ImageIcon("c:\\Users\\ASUS\\Documents\\UcHomeBG.png").getImage(); 
           

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(bg, 0, 0, getWidth(), getHeight(), this);
            }
        };

        leftPanel.setLayout(null);

        JLabel ucLogo = new JLabel(new ImageIcon("c:\\Users\\ASUS\\Documents\\UcLogoBig.png"));
        ucLogo.setBounds(90, 80, 300, 200);
        leftPanel.add(ucLogo);

        JButton textbookPortalBtn = new JButton("TEXTBOOK PORTAL");
        textbookPortalBtn.setBounds(130, 320, 200, 40);
        textbookPortalBtn.setFont(new Font("Arial", Font.BOLD, 16));
        textbookPortalBtn.setBackground(Color.WHITE);
        leftPanel.add(textbookPortalBtn);


        /* RIGHT PANEL (CIRCLES + UC LOGO + TEXT + GET STARTED BUTTON) */
        JPanel rightPanel = new JPanel();
        rightPanel.setBackground(Color.WHITE);
        rightPanel.setLayout(null);

        JLabel circleDesign = new JLabel(new ImageIcon("c:\\Users\\ASUS\\Documents\\CircleDesign.png"));
        // ← Insert the circular design image
        circleDesign.setBounds(70, -10, 350, 250);
        rightPanel.add(circleDesign);

        JLabel title1 = new JLabel("UNIVERSITY OF CEBU");
        title1.setFont(new Font("Arial", Font.BOLD, 24));
        title1.setForeground(new Color(30, 45, 60));
        title1.setBounds(70, 240, 350, 40);
        rightPanel.add(title1);

        JLabel title2 = new JLabel("TEXTBOOK PORTAL");
        title2.setFont(new Font("Arial", Font.BOLD, 24));
        title2.setForeground(new Color(30, 45, 60));
        title2.setBounds(80, 275, 350, 40);
        rightPanel.add(title2);

        JButton getStartedBtn = new JButton("Get Started");
        getStartedBtn.setBounds(130, 340, 180, 50);
        getStartedBtn.setFont(new Font("Arial", Font.BOLD, 18));
        getStartedBtn.setBackground(Color.BLACK);
        getStartedBtn.setForeground(Color.WHITE);
        getStartedBtn.setFocusPainted(false);

        // OPEN LOGIN PAGE WHEN CLICKED
        getStartedBtn.addActionListener(e -> {
            new UcTextBook();  // ← opens your login page
            dispose();
        });

        rightPanel.add(getStartedBtn);

        // ADD BOTH PANELS
        add(leftPanel);
        add(rightPanel);

        setVisible(true);
    }

    public static void main(String[] args) {
        new HomePage();
    }
}
