import java.awt.*;
import javax.swing.*;

public class UcTextBook {

    public static class AccountStorage { 
        public static String savedUser;
        public static String savedPass;
    }

    public static class HomePage extends JFrame {

        public HomePage() {

            setTitle("UC Textbook Portal");
            setSize(950, 520);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setLocationRelativeTo(null);
            setResizable(false);
            setLayout(new GridLayout(1, 2));

            JPanel leftPanel = new JPanel() {
                Image bg = new ImageIcon("c:\\Users\\ASUS\\Documents\\Screenshot 2025-12-06 160304.png").getImage();
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

            JPanel rightPanel = new JPanel();
            rightPanel.setBackground(Color.WHITE);
            rightPanel.setLayout(null);

            JLabel circle = new JLabel(new ImageIcon("c:\\Users\\ASUS\\Documents\\CircleDesign.png"));
            circle.setBounds(70, -10, 350, 250);
            rightPanel.add(circle);

            JLabel title1 = new JLabel("UNIVERSITY OF CEBU");
            title1.setFont(new Font("Arial", Font.BOLD, 24));
            title1.setForeground(new Color(30, 45, 60));
            title1.setBounds(80, 240, 350, 40);
            rightPanel.add(title1);

            JLabel title2 = new JLabel("TEXTBOOK PORTAL");
            title2.setFont(new Font("Arial", Font.BOLD, 24));
            title2.setForeground(new Color(30, 45, 60));
            title2.setBounds(90, 275, 350, 40);
            rightPanel.add(title2);

            JButton startBtn = new JButton("Get Started");
            startBtn.setBounds(130, 340, 180, 50);
            startBtn.setFont(new Font("Arial", Font.BOLD, 18));
            startBtn.setBackground(Color.BLACK);
            startBtn.setForeground(Color.WHITE);
            startBtn.setFocusPainted(false);

            startBtn.addActionListener(e -> {
                new LoginPage();
                dispose();
            });

            rightPanel.add(startBtn);

            add(leftPanel);
            add(rightPanel);

            setVisible(true);
        }
    }

    public static class LoginPage extends JFrame {

        public LoginPage() {
            setTitle("University of Cebu Login");
            setSize(900, 500);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setLocationRelativeTo(null);
            setResizable(false);
            setLayout(new BorderLayout());

            JPanel leftPanel = new JPanel() {
                Image bg = new ImageIcon("c:\\Users\\ASUS\\Documents\\Ucimage.png").getImage();
                @Override
                protected void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    g.drawImage(bg, 0, 0, getWidth(), getHeight(), this);
                }
            };
            leftPanel.setLayout(new GridBagLayout());
            leftPanel.setPreferredSize(new Dimension(450, 500));

            JPanel rightPanel = new JPanel();
            rightPanel.setBackground(new Color(70, 82, 92));
            rightPanel.setLayout(null);

            JLabel loginLabel = new JLabel("LOGIN");
            loginLabel.setFont(new Font("Arial", Font.BOLD, 32));
            loginLabel.setForeground(Color.WHITE);
            loginLabel.setBounds(160, 40, 200, 40);

            JLabel userLabel = new JLabel("USERNAME");
            userLabel.setForeground(Color.WHITE);
            userLabel.setFont(new Font("Arial", Font.BOLD, 14));
            userLabel.setBounds(70, 120, 200, 20);

            JTextField userField = new JTextField();
            userField.setBounds(70, 145, 300, 35);

            JLabel passLabel = new JLabel("PASSWORD");
            passLabel.setForeground(Color.WHITE);
            passLabel.setFont(new Font("Arial", Font.BOLD, 14));
            passLabel.setBounds(70, 200, 200, 20);

            JPasswordField passField = new JPasswordField();
            passField.setBounds(70, 225, 300, 35);

            JButton loginBtn = new JButton("Login");
            loginBtn.setBounds(73, 290, 100, 40);

            loginBtn.addActionListener(e -> {

    String usernameInput = userField.getText();
    String passwordInput = new String(passField.getPassword());

    // Check empty fields
    if (usernameInput.isEmpty() || passwordInput.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Please fill up all fields.");
        return;
    }

    // Check if any account exists
    if (AccountStorage.savedUser == null || AccountStorage.savedPass == null) {
        JOptionPane.showMessageDialog(this, "No registered account found.");
        return;
    }

    // Check if username and password match
    if (!usernameInput.equals(AccountStorage.savedUser) ||
        !passwordInput.equals(AccountStorage.savedPass)) {
        JOptionPane.showMessageDialog(this, "Incorrect username or password!");
        return;
    }

    // Successful login
    new AfterLoginPage();
    dispose();
});


            JLabel signUpText = new JLabel("Don't have an account?");
            signUpText.setForeground(Color.WHITE);
            signUpText.setBounds(75, 350, 150, 20);

            JButton signUpBtn = new JButton("Sign Up");
            signUpBtn.setBounds(250, 345, 100, 30);

            signUpBtn.addActionListener(e -> {
                new SignUpForm();
                dispose();
            });

            JLabel forgotLabel = new JLabel("Forgot Password?");
            forgotLabel.setForeground(new Color(173, 216, 230));
            forgotLabel.setBounds(75, 380, 150, 25);
            forgotLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));

            forgotLabel.addMouseListener(new java.awt.event.MouseAdapter() {
                @Override
                public void mouseEntered(java.awt.event.MouseEvent e) {
                    forgotLabel.setText("<html><u>Forgot Password?</u></html>");
                }

                @Override
                public void mouseExited(java.awt.event.MouseEvent e) {
                    forgotLabel.setText("Forgot Password?");
                }

                @Override
                public void mouseClicked(java.awt.event.MouseEvent e) {
                    new ChangePassword();
                    dispose();
                }
            });

            rightPanel.add(loginLabel);
            rightPanel.add(userLabel);
            rightPanel.add(userField);
            rightPanel.add(passLabel);
            rightPanel.add(passField);
            rightPanel.add(loginBtn);
            rightPanel.add(signUpText);
            rightPanel.add(signUpBtn);
            rightPanel.add(forgotLabel);

            add(leftPanel, BorderLayout.WEST);
            add(rightPanel, BorderLayout.CENTER);

            setVisible(true);
        }
    }

    public static class SignUpForm extends JFrame {

        public SignUpForm() {

            setTitle("Create Account");
            setSize(900, 500);
            setLocationRelativeTo(null);
            setResizable(false);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setLayout(new BorderLayout());

            JPanel leftPanel = new JPanel() {
                Image bg = new ImageIcon("c:\\Users\\ASUS\\Documents\\Ucimage.png").getImage();
                @Override
                protected void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    g.drawImage(bg, 0, 0, getWidth(), getHeight(), this);
                }
            };
            leftPanel.setPreferredSize(new Dimension(390, 500));

            JPanel rightPanel = new JPanel();
            rightPanel.setBackground(new Color(70, 82, 92));
            rightPanel.setLayout(null);

            JLabel title = new JLabel("CREATE ACCOUNT");
            title.setForeground(Color.WHITE);
            title.setFont(new Font("Arial", Font.BOLD, 26));
            title.setBounds(120, 30, 300, 40);

            JLabel userLabel = new JLabel("USERNAME");
            userLabel.setForeground(Color.WHITE);
            userLabel.setBounds(70, 110, 200, 20);

            JTextField userField = new JTextField();
            userField.setBounds(70, 135, 350, 35);

            JLabel passLabel = new JLabel("PASSWORD");
            passLabel.setForeground(Color.WHITE);
            passLabel.setBounds(70, 185, 200, 20);

            JPasswordField passField = new JPasswordField();
            passField.setBounds(70, 210, 350, 35);

            JLabel confirmLabel = new JLabel("CONFIRM PASSWORD");
            confirmLabel.setForeground(Color.WHITE);
            confirmLabel.setBounds(70, 260, 200, 20);

            JPasswordField confirmField = new JPasswordField();
            confirmField.setBounds(70, 285, 350, 35);

            JButton createBtn = new JButton("Sign Up");
            createBtn.setBounds(180, 340, 120, 40);

            createBtn.addActionListener(e -> {
                String user = userField.getText();
                String pass = new String(passField.getPassword());
                String confirm = new String(confirmField.getPassword());

                if (user.isEmpty() || pass.isEmpty() || confirm.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "All fields are required!");
                    return;
                }

                if (!pass.equals(confirm)) {
                    JOptionPane.showMessageDialog(this, "Passwords do not match!");
                    return;
                }

                AccountStorage.savedUser = user;
                AccountStorage.savedPass = pass;

                JOptionPane.showMessageDialog(this, "Account created successfully!");

                new LoginPage();
                dispose();
            });

            JLabel backLabel = new JLabel("Back");
            backLabel.setForeground(new Color(200, 220, 255));
            backLabel.setBounds(20, 430, 100, 30);
            backLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));

            backLabel.addMouseListener(new java.awt.event.MouseAdapter() {
                @Override
                public void mouseEntered(java.awt.event.MouseEvent e) {
                    backLabel.setText("<html><u>Back</u></html>");
                }

                @Override
                public void mouseExited(java.awt.event.MouseEvent e) {
                    backLabel.setText("Back");
                }

                @Override
                public void mouseClicked(java.awt.event.MouseEvent e) {
                    new LoginPage();
                    dispose();
                }
            });

            rightPanel.add(title);
            rightPanel.add(userLabel);
            rightPanel.add(userField);
            rightPanel.add(passLabel);
            rightPanel.add(passField);
            rightPanel.add(confirmLabel);
            rightPanel.add(confirmField);
            rightPanel.add(createBtn);
            rightPanel.add(backLabel);

            add(leftPanel, BorderLayout.WEST);
            add(rightPanel, BorderLayout.CENTER);

            setVisible(true);
        }
    }

    public static class ChangePassword extends JFrame {

        public ChangePassword() {

            setTitle("Change Password");
            setSize(900, 500);
            setLocationRelativeTo(null);
            setResizable(false);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setLayout(new BorderLayout());

            JPanel leftPanel = new JPanel() {
                Image bg = new ImageIcon("c:\\Users\\ASUS\\Documents\\Ucimage.png").getImage();
                @Override
                protected void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    g.drawImage(bg, 0, 0, getWidth(), getHeight(), this);
                }
            };
            leftPanel.setPreferredSize(new Dimension(390, 500));

            JPanel rightPanel = new JPanel();
            rightPanel.setBackground(new Color(70, 82, 92));
            rightPanel.setLayout(null);

            JLabel title = new JLabel("CHANGE PASSWORD");
            title.setForeground(Color.WHITE);
            title.setFont(new Font("Arial", Font.BOLD, 26));
            title.setBounds(100, 30, 350, 40);

            JLabel userLabel = new JLabel("USERNAME");
            userLabel.setForeground(Color.WHITE);
            userLabel.setBounds(70, 110, 200, 20);

            JTextField userField = new JTextField();
            userField.setBounds(70, 135, 350, 35);

            JLabel newPassLabel = new JLabel("NEW PASSWORD");
            newPassLabel.setForeground(Color.WHITE);
            newPassLabel.setBounds(70, 185, 200, 20);

            JPasswordField newPassField = new JPasswordField();
            newPassField.setBounds(70, 210, 350, 35);

            JLabel confirmLabel = new JLabel("CONFIRM PASSWORD");
            confirmLabel.setForeground(Color.WHITE);
            confirmLabel.setBounds(70, 260, 200, 20);

            JPasswordField confirmField = new JPasswordField();
            confirmField.setBounds(70, 285, 350, 35);

            JButton changeBtn = new JButton("Update Password");
            changeBtn.setBounds(160, 340, 160, 40);

            changeBtn.addActionListener(e -> {
                String user = userField.getText();
                String pass = new String(newPassField.getPassword());
                String confirm = new String(confirmField.getPassword());

                if (user.isEmpty() || pass.isEmpty() || confirm.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "All fields are required!");
                    return;
                }

                if (!pass.equals(confirm)) {
                    JOptionPane.showMessageDialog(this, "Passwords do not match!");
                    return;
                }

                JOptionPane.showMessageDialog(this, "Password updated successfully!");

                new LoginPage();
                dispose();
            });

            JLabel backLabel = new JLabel("Back");
            backLabel.setForeground(new Color(200, 220, 255));
            backLabel.setBounds(20, 430, 100, 30);
            backLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));

            backLabel.addMouseListener(new java.awt.event.MouseAdapter() {
                @Override
                public void mouseEntered(java.awt.event.MouseEvent e) {
                    backLabel.setText("<html><u>Back</u></html>");
                }

                @Override
                public void mouseExited(java.awt.event.MouseEvent e) {
                    backLabel.setText("Back");
                }

                @Override
                public void mouseClicked(java.awt.event.MouseEvent e) {
                    new LoginPage();
                    dispose();
                }
            });

            rightPanel.add(title);
            rightPanel.add(userLabel);
            rightPanel.add(userField);
            rightPanel.add(newPassLabel);
            rightPanel.add(newPassField);
            rightPanel.add(confirmLabel);
            rightPanel.add(confirmField);
            rightPanel.add(changeBtn);
            rightPanel.add(backLabel);

            add(leftPanel, BorderLayout.WEST);
            add(rightPanel, BorderLayout.CENTER);

            setVisible(true);
        }
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new HomePage());
    }
}
