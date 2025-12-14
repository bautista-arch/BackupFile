import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;

public class AfterLoginPage extends JFrame {

    private final JPanel mainPanel;
    private JPanel categoryPanel;
    private JPanel banner;
    private final JScrollPane scrollPane;

    public AfterLoginPage() {
        setTitle("UC Textbook Portal - Home");
        setSize(1200, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        mainPanel = new JPanel();
        mainPanel.setLayout(null);
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setPreferredSize(new Dimension(1200, 1000));

        createTopNavigation();
        createBanner("BANNER", "");
        createMenuPanel();
        createCategoryPanel("CCS");

        scrollPane = new JScrollPane(mainPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        setContentPane(scrollPane);
        setVisible(true);
    }

    private void createTopNavigation() {
        JPanel topNav = new JPanel(null);
        topNav.setBackground(Color.WHITE);
        topNav.setBounds(0, 0, 1200, 70);

        // UC Logo
        JLabel ucLogo = new JLabel(new ImageIcon("c:\\Users\\ASUS\\Documents\\ImageFile\\UcSmallLogo.png"));
        ucLogo.setBounds(20, 10, 60, 50);
        topNav.add(ucLogo);

        // Search bar
        JTextField searchBar = new JTextField("Search bar");
        searchBar.setBounds(120, 20, 700, 32);
        searchBar.setHorizontalAlignment(JTextField.CENTER);
        searchBar.setFont(new Font("Arial", Font.PLAIN, 14));
        searchBar.setOpaque(false);
        searchBar.setBorder(new RoundedFieldBorder(20));
        topNav.add(searchBar);

        // Cart icon
        JLabel cartIcon = new JLabel(new ImageIcon("c:\\Users\\ASUS\\Documents\\ImageFile\\cart.png"));
        cartIcon.setBounds(900, 15, 40, 40);
        cartIcon.setCursor(new Cursor(Cursor.HAND_CURSOR));
        topNav.add(cartIcon);
        cartIcon.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                loadCartPanel();
            }
        });

        // User Button
        JButton userButton = new JButton("User");
        userButton.setBounds(1000, 20, 120, 32);
        userButton.setFont(new Font("Arial", Font.BOLD, 14));
        userButton.setBackground(Color.WHITE);
        userButton.setOpaque(false);
        userButton.setBorder(new RoundedFieldBorder(20));
        userButton.addActionListener(e -> loadUserProfilePanel());
        topNav.add(userButton);

        mainPanel.add(topNav);
    }

    private void createBanner(String titleText, String subtitleText) {
        if (banner != null)
            mainPanel.remove(banner);

        String[] bannerPaths = {
                "c:\\Users\\ASUS\\Documents\\Banner1.png",
                "c:\\Users\\ASUS\\Documents\\Banner2.png",
                "c:\\Users\\ASUS\\Documents\\Banner3.png"
        };

        ArrayList<ImageIcon> imageList = new ArrayList<>();
        for (String path : bannerPaths) {
            ImageIcon raw = new ImageIcon(path);
            Image scaled = raw.getImage().getScaledInstance(1100, 220, Image.SCALE_SMOOTH);
            imageList.add(new ImageIcon(scaled));
        }

        JLabel imageLabel = new JLabel(imageList.get(0));
        imageLabel.setBounds(0, 0, 1100, 220);

        banner = new JPanel(null);
        banner.setBounds(40, 100, 1100, 220);
        banner.setOpaque(false);
        banner.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        banner.add(imageLabel);

        JLabel bannerText = new JLabel(titleText, SwingConstants.CENTER);
        bannerText.setForeground(Color.WHITE);
        bannerText.setFont(new Font("Arial", Font.BOLD, 36));
        bannerText.setBounds(0, 60, 1100, 50);
        banner.add(bannerText);

        if (!subtitleText.isEmpty()) {
            JLabel subtitle = new JLabel(subtitleText, SwingConstants.CENTER);
            subtitle.setForeground(Color.WHITE);
            subtitle.setFont(new Font("Arial", Font.PLAIN, 20));
            subtitle.setBounds(0, 120, 1100, 40);
            banner.add(subtitle);
        }

        final int[] index = {0};
        final int[] startX = {0};

        imageLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                startX[0] = e.getX();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                int endX = e.getX();
                if (startX[0] - endX > 40)
                    index[0] = (index[0] + 1) % imageList.size();
                else if (endX - startX[0] > 40)
                    index[0] = (index[0] - 1 + imageList.size()) % imageList.size();
                imageLabel.setIcon(imageList.get(index[0]));
            }
        });

        Timer autoSlide = new Timer(4000, e -> {
            index[0] = (index[0] + 1) % imageList.size();
            imageLabel.setIcon(imageList.get(index[0]));
        });
        autoSlide.start();

        mainPanel.add(banner);
        mainPanel.repaint();
    }

    private void createMenuPanel() {
        JPanel menuPanel = new JPanel(null);
        menuPanel.setBackground(Color.BLACK);
        menuPanel.setBounds(50, 340, 1100, 60);
        menuPanel.setBorder(new TopBorder(4, Color.BLACK));

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
                    if (item.equals("Home")) {
                        createBanner("BANNER", "");
                        createCategoryPanel("CCS");
                    } else if (item.equals("Departments")) {
                        JPopupMenu deptMenu = new JPopupMenu();
                        deptMenu.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
                        String[] departments = {"CBA", "CHM", "CCS"};

                        for (String dept : departments) {
                            JMenuItem menuItem = new JMenuItem(dept);
                            menuItem.setPreferredSize(new Dimension(150, 40));
                            menuItem.addActionListener(ae -> {
                                createBanner(dept, "");
                                createCategoryPanel(dept);
                            });
                            deptMenu.add(menuItem);
                        }
                        int x = (slotPanel.getWidth() - deptMenu.getPreferredSize().width) / 2;
                        deptMenu.show(menuLabel, x, slotPanel.getHeight());
                    } else if (item.equals("Recently Added")) {
                        createBanner("RECENTLY ADDED", "Check out the newest additions");
                        createRecentlyAddedPanel();
                    } else if (item.equals("New Arrivals")) {
                        createBanner("NEW ARRIVALS", "Discover the latest textbooks");
                        createNewArrivalsPanel();
                    } else {
                        JOptionPane.showMessageDialog(null, item + " clicked!");
                    }
                }
            });

            slotPanel.add(menuLabel);
            menuPanel.add(slotPanel);
        }

        mainPanel.add(menuPanel);
    }

    private void createCategoryPanel(String department) {
        if (categoryPanel != null)
            mainPanel.remove(categoryPanel);

        categoryPanel = new JPanel(null);
        categoryPanel.setBackground(new Color(233, 236, 239));
        categoryPanel.setBounds(50, 420, 1100, 320);
        categoryPanel.setBorder(new RoundedBorder(30, Color.GRAY, 2));

        JLabel title = new JLabel(department, SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 28));
        title.setForeground(new Color(20, 40, 100));
        title.setBounds(10, 10, 1100, 40);
        categoryPanel.add(title);

        JLabel subtitle = new JLabel("", SwingConstants.CENTER);
        subtitle.setFont(new Font("Arial", Font.PLAIN, 18));
        subtitle.setBounds(0, 45, 1100, 30);
        categoryPanel.add(subtitle);

        // Arrays for department info
        String[] images;
        String[] titles;
        String[] authors;
        String[] prices;
        String[] descriptions;

        switch (department) {
            case "CBA":
                subtitle.setText("COLLEGE OF BUSINESS ADMINISTRATION");
                images = new String[]{
                        "c:\\Users\\ASUS\\Downloads\\1_20251207_204022_0000.png",
                        "c:\\Users\\ASUS\\Downloads\\2_20251207_204022_0001.png",
                        "c:\\Users\\ASUS\\Downloads\\3_20251207_204022_0002.png",
                        "c:\\Users\\ASUS\\Downloads\\4_20251207_204022_0003.png"
                };
                titles = new String[]{"CBA-FOUND01","CBA-MARK02","CBA-LAW03","CBA-ACC04"};
                authors = new String[]{"Author A","Author B","Author C","Author D"};
                prices = new String[]{"₱3500.00","₱4200.00","₱3800.00","₱4500.00"};
                descriptions = new String[]{
                        "Intro to Business fundamentals...",
                        "Marketing Management text...",
                        "Business Law overview...",
                        "Accounting & Finance guide..."
                };
                break;
            case "CHM":
                subtitle.setText("COLLEGE OF HOSPITALITY MANAGEMENT");
                images = new String[]{
                        "c:\\Users\\ASUS\\Downloads\\1_20251207_204224_0000.png",
                        "c:\\Users\\ASUS\\Downloads\\2_20251207_204224_0001.png",
                        "c:\\Users\\ASUS\\Downloads\\3_20251207_204224_0002.png"
                };
                titles = new String[]{"CHM-FOP01","CHM-FNB02","CHM-HSK03"};
                authors = new String[]{"Auth E","Auth F","Auth G"};
                prices = new String[]{"₱2800.00","₱3200.00","₱3000.00"};
                descriptions = new String[]{
                        "Front Office procedures...",
                        "Food & Beverage services...",
                        "Housekeeping operations guide..."
                };
                break;
            default: // CCS
                subtitle.setText("COLLEGE OF COMPUTER STUDIES");
                images = new String[]{
                        "c:\\Users\\ASUS\\Documents\\ImageFile\\1_20251207_015751_0000.png",
                        "c:\\Users\\ASUS\\Documents\\ImageFile\\2_20251207_015751_0001.png",
                        "c:\\Users\\ASUS\\Documents\\ImageFile\\3_20251207_015751_0002.png",
                        "c:\\Users\\ASUS\\Documents\\ImageFile\\4_20251207_015752_0003.png"
                };
                titles = new String[]{"CC-COMPROG11","CC-COMRPOG12/IT-OOPROG21","IT-SAD21","CCS-DS04"};
                authors = new String[]{
                        "Hassan Afyouni, Ed.D.","Author Java","System Author","Data Author"};
                prices = new String[]{"₱5000.00","₱5000.00","₱5000.00","₱4500.00"};
                descriptions = new String[]{
                        "A Structured Programming Approach in C...",
                        "Java Programming 8th Ed...",
                        "Systems Analysis & Design...",
                        "Data Structures Overview..."
                };
                break;
        }

        // Preload images dynamically based on array length
        ImageIcon[] cachedImages = new ImageIcon[images.length];
        for (int i = 0; i < images.length; i++) {
            ImageIcon raw = new ImageIcon(images[i]);
            Image scaled = raw.getImage().getScaledInstance(200, 180, Image.SCALE_SMOOTH);
            cachedImages[i] = new ImageIcon(scaled);
        }

        // Create clickable image blocks dynamically
        for (int i = 0; i < images.length; i++) {
            String t = titles[i], a = authors[i], p = prices[i], d = descriptions[i];
            String imgPath = images[i];
            JPanel block = new JPanel(new BorderLayout());
            block.setBackground(Color.WHITE);
            block.setBounds(70 + (i * 250), 100, 200, 180);
            block.setBorder(new RoundedBorder(20, Color.GRAY, 2));

            JLabel imgLabel = new JLabel(cachedImages[i]);
            imgLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            imgLabel.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    showBookPopup(imgPath, t, a, p, d);
                }
            });

            block.add(imgLabel);
            categoryPanel.add(block);
        }

        mainPanel.add(categoryPanel);
        mainPanel.repaint();
    }

    private void createRecentlyAddedPanel() {
        if (categoryPanel != null)
            mainPanel.remove(categoryPanel);

        categoryPanel = new JPanel(null);
        categoryPanel.setBackground(new Color(233, 236, 239));
        categoryPanel.setBounds(50, 420, 1100, 320);
        categoryPanel.setBorder(new RoundedBorder(30, Color.GRAY, 2));

        JLabel title = new JLabel("RECENTLY ADDED", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 28));
        title.setForeground(new Color(20, 40, 100));
        title.setBounds(10, 10, 1100, 40);
        categoryPanel.add(title);

        JLabel subtitle = new JLabel("Freshly added textbooks to explore", SwingConstants.CENTER);
        subtitle.setFont(new Font("Arial", Font.PLAIN, 18));
        subtitle.setBounds(0, 45, 1100, 30);
        categoryPanel.add(subtitle);

        // Arrays for recently added (using CBA data as example)
        String[] images = {
                "c:\\Users\\ASUS\\Downloads\\1_20251207_204022_0000.png",
                "c:\\Users\\ASUS\\Downloads\\2_20251207_204022_0001.png",
                "c:\\Users\\ASUS\\Downloads\\3_20251207_204022_0002.png",
                "c:\\Users\\ASUS\\Downloads\\4_20251207_204022_0003.png"
        };
        String[] titles = {"CBA-FOUND01","CBA-MARK02","CBA-LAW03","CBA-ACC04"};
        String[] authors = {"Author A","Author B","Author C","Author D"};
        String[] prices = {"₱3500.00","₱4200.00","₱3800.00","₱4500.00"};
        String[] descriptions = {
                "Intro to Business fundamentals...",
                "Marketing Management text...",
                "Business Law overview...",
                "Accounting & Finance guide..."
        };

        // Preload images dynamically based on array length
        ImageIcon[] cachedImages = new ImageIcon[images.length];
        for (int i = 0; i < images.length; i++) {
            ImageIcon raw = new ImageIcon(images[i]);
            Image scaled = raw.getImage().getScaledInstance(200, 180, Image.SCALE_SMOOTH);
            cachedImages[i] = new ImageIcon(scaled);
        }

        // Create clickable image blocks dynamically
        for (int i = 0; i < images.length; i++) {
            String t = titles[i], a = authors[i], p = prices[i], d = descriptions[i];
            String imgPath = images[i];
            JPanel block = new JPanel(new BorderLayout());
            block.setBackground(Color.WHITE);
            block.setBounds(70 + (i * 250), 100, 200, 180);
            block.setBorder(new RoundedBorder(20, Color.GRAY, 2));

            JLabel imgLabel = new JLabel(cachedImages[i]);
            imgLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            imgLabel.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    showBookPopup(imgPath, t, a, p, d);
                }
            });

            block.add(imgLabel);
            categoryPanel.add(block);
        }

        mainPanel.add(categoryPanel);
        mainPanel.repaint();
    }

    private void createNewArrivalsPanel() {
        if (categoryPanel != null)
            mainPanel.remove(categoryPanel);

        categoryPanel = new JPanel(null);
        categoryPanel.setBackground(new Color(233, 236, 239));
        categoryPanel.setBounds(50, 420, 1100, 320);
        categoryPanel.setBorder(new RoundedBorder(30, Color.GRAY, 2));

        JLabel title = new JLabel("NEW ARRIVALS", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 28));
        title.setForeground(new Color(20, 40, 100));
        title.setBounds(10, 10, 1100, 40);
        categoryPanel.add(title);

        JLabel subtitle = new JLabel("Latest textbooks added to our collection", SwingConstants.CENTER);
        subtitle.setFont(new Font("Arial", Font.PLAIN, 18));
        subtitle.setBounds(0, 45, 1100, 30);
        categoryPanel.add(subtitle);

        // Arrays for new arrivals
        String[] images = {
                "c:\\Users\\ASUS\\Documents\\ImageFile\\1_20251207_015751_0000.png",
                "c:\\Users\\ASUS\\Documents\\ImageFile\\2_20251207_015751_0001.png",
                "c:\\Users\\ASUS\\Documents\\ImageFile\\3_20251207_015751_0002.png",
                "c:\\Users\\ASUS\\Documents\\ImageFile\\4_20251207_015752_0003.png"
        };
        String[] titles = {"CC-COMPROG11","CC-COMRPOG12/IT-OOPROG21","IT-SAD21","CCS-DS04"};
        String[] authors = {
                "Hassan Afyouni, Ed.D.","Author Java","System Author","Data Author"};
        String[] prices = {"₱5000.00","₱5000.00","₱5000.00","₱4500.00"};
        String[] descriptions = {
                "A Structured Programming Approach in C...",
                "Java Programming 8th Ed...",
                "Systems Analysis & Design...",
                "Data Structures Overview..."
        };

        // Preload images dynamically based on array length
        ImageIcon[] cachedImages = new ImageIcon[images.length];
        for (int i = 0; i < images.length; i++) {
            ImageIcon raw = new ImageIcon(images[i]);
            Image scaled = raw.getImage().getScaledInstance(200, 180, Image.SCALE_SMOOTH);
            cachedImages[i] = new ImageIcon(scaled);
        }

        // Create clickable image blocks dynamically
        for (int i = 0; i < images.length; i++) {
            String t = titles[i], a = authors[i], p = prices[i], d = descriptions[i];
            String imgPath = images[i];
            JPanel block = new JPanel(new BorderLayout());
            block.setBackground(Color.WHITE);
            block.setBounds(70 + (i * 250), 100, 200, 180);
            block.setBorder(new RoundedBorder(20, Color.GRAY, 2));

            JLabel imgLabel = new JLabel(cachedImages[i]);
            imgLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            imgLabel.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    showBookPopup(imgPath, t, a, p, d);
                }
            });

            block.add(imgLabel);
            categoryPanel.add(block);
        }

        mainPanel.add(categoryPanel);
        mainPanel.repaint();
    }

    private void showBookPopup(String imagePath, String code, String author, String price, String description) {

    JFrame popup = new JFrame();
    popup.setSize(1100, 600);
    popup.setLocationRelativeTo(null);
    popup.setResizable(false);
    popup.setUndecorated(true); // remove frame border
    popup.setLayout(null);

    JPanel bg = new JPanel(null);
    bg.setBackground(Color.WHITE);
    bg.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2));
    bg.setBounds(0, 0, 1100, 600);

    popup.add(bg);

    // ===== LEFT CONTAINER (gray background) =====
    JPanel leftPanel = new JPanel(null);
    leftPanel.setBackground(new Color(240, 240, 240));
    leftPanel.setBounds(40, 40, 400, 520);
    leftPanel.setBorder(BorderFactory.createLineBorder(new Color(180, 180, 180), 2));
    bg.add(leftPanel);

    // Book Image
    ImageIcon raw = new ImageIcon(imagePath);
    Image scaled = raw.getImage().getScaledInstance(320, 380, Image.SCALE_SMOOTH);
    JLabel img = new JLabel(new ImageIcon(scaled));
    img.setBounds(40, 60, 320, 380);
    leftPanel.add(img);

    // ===== CLOSE BUTTON (X) =====
    JLabel close = new JLabel("✕", SwingConstants.CENTER);
    close.setFont(new Font("Arial", Font.BOLD, 32));
    close.setForeground(Color.BLACK);
    close.setBounds(1040, 10, 50, 50);
    close.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    close.addMouseListener(new MouseAdapter() {
        @Override public void mouseClicked(MouseEvent e) {
            popup.dispose();
        }
    });
    bg.add(close);

    // ===== TITLE =====
    JLabel titleLabel = new JLabel(code);
    titleLabel.setFont(new Font("Arial", Font.BOLD, 32));
    titleLabel.setBounds(470, 60, 600, 40);
    bg.add(titleLabel);

    // ===== SUBTITLE small uppercase =====
    JLabel subtitle = new JLabel("A STRUCTURED PROGRAMMING APPROACH IN C 4ᵀᴴ ED");
    subtitle.setFont(new Font("Arial", Font.PLAIN, 20));
    subtitle.setBounds(470, 100, 700, 35);
    bg.add(subtitle);

    // ===== AUTHOR =====
    JLabel auth = new JLabel("AUTHOR:  " + author.toUpperCase());
    auth.setFont(new Font("Arial", Font.BOLD, 16));
    auth.setBounds(470, 140, 700, 30);
    bg.add(auth);

    // ===== PRICE (big red) =====
    JLabel priceLabel = new JLabel(price);
    priceLabel.setFont(new Font("Arial", Font.BOLD, 36));
    priceLabel.setForeground(new Color(139, 0, 0));
    priceLabel.setBounds(470, 180, 400, 50);
    bg.add(priceLabel);

    // ===== ABOUT THIS ITEM =====
    JLabel about = new JLabel("ABOUT THIS ITEM");
    about.setFont(new Font("Arial", Font.BOLD, 18));
    about.setBounds(470, 230, 500, 30);
    bg.add(about);

    // ===== DESCRIPTION (show first part only) =====
    String shortDesc = description.length() > 270 ? description.substring(0, 270) + "...SEE MORE" : description;

    JLabel descLabel = new JLabel("<html>" + shortDesc + "</html>");
    descLabel.setFont(new Font("Arial", Font.PLAIN, 14));
    descLabel.setBounds(470, 260, 600, 120);
    bg.add(descLabel);

    // ===== ADD TO CART BUTTON (white border) =====
    JButton addToCart = new JButton("ADD TO CART");
    addToCart.setBounds(470, 420, 220, 55);
    addToCart.setFont(new Font("Arial", Font.BOLD, 14));
    addToCart.setBackground(Color.WHITE);
    addToCart.setForeground(Color.BLACK);
    addToCart.setBorder(new RoundedBorder(50, Color.BLACK, 1));
    addToCart.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

    addToCart.addActionListener(e -> {
        GlobalCartList.cartItems.add(new CartItem(description, price, imagePath, description, "", code));
        JOptionPane.showMessageDialog(null, code + " added to cart!");
        popup.dispose();
    });

    bg.add(addToCart);

    // ===== PURCHASE BUTTON (Dark Blue) =====
    JButton purchase = new JButton("PURCHASE");
    purchase.setBounds(710, 420, 220, 55);
    purchase.setFont(new Font("Arial", Font.BOLD, 14));
    purchase.setBackground(new Color(10, 40, 110));
    purchase.setForeground(Color.WHITE);
    purchase.setBorder(new RoundedBorder(50, new Color(10, 40, 110), 1));
    purchase.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

    purchase.addActionListener(e -> {
        ArrayList<CartItem> single = new ArrayList<>();
        single.add(new CartItem(description, price, imagePath, description, "", code));
        loadCheckout(single);   // direct checkout
        popup.dispose();
    });

    bg.add(purchase);

    popup.setVisible(true);
}


    public void loadHomePage() {
        if (categoryPanel != null)
            mainPanel.remove(categoryPanel);
        createBanner("BANNER", "");
        createCategoryPanel("CCS");
        mainPanel.repaint();
    }

    public void loadCartPanel() {
        JFrame cartFrame = new JFrame("Shopping Cart");
        cartFrame.setSize(1200, 800);
        cartFrame.setLocationRelativeTo(null);
        cartFrame.setResizable(false);
        cartFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        cartFrame.add(new CartPanel(this));
        cartFrame.setVisible(true);
    }

    public void loadCheckout(java.util.List<CartItem> items) {
        JFrame checkoutFrame = new JFrame("Checkout");
        checkoutFrame.setSize(1200, 700);
        checkoutFrame.setLocationRelativeTo(null);
        checkoutFrame.setResizable(false);
        checkoutFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        checkoutFrame.add(new CheckoutPanel(this, items));
        checkoutFrame.setVisible(true);
    }

    public void loadUserProfilePanel() {
        JFrame userFrame = new JFrame("User Profile");
        userFrame.setSize(1200, 700);
        userFrame.setLocationRelativeTo(null);
        userFrame.setResizable(false);
        userFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        userFrame.add(new UserProfilePanel(this));
        userFrame.setVisible(true);
    }

    public void showDepartmentMenu(JPanel slotPanel, JLabel menuLabel) {
        JPopupMenu deptMenu = new JPopupMenu();
        deptMenu.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        String[] departments = {"CBA", "CHM", "CCS"};

        for (String dept : departments) {
            JMenuItem menuItem = new JMenuItem(dept);
            menuItem.setPreferredSize(new Dimension(150, 40));
            menuItem.addActionListener(ae -> {
                createBanner(dept, "");
                createCategoryPanel(dept);
            });
            deptMenu.add(menuItem);
        }
        int x = (slotPanel.getWidth() - deptMenu.getPreferredSize().width) / 2;
        deptMenu.show(menuLabel, x, slotPanel.getHeight());
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(AfterLoginPage::new);
    }
}
