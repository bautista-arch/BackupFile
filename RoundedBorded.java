import java.awt.*;
import javax.swing.border.Border;

public class RoundedBorder implements Border {
    private int radius;
    private Color color;
    private int thickness;

    public RoundedBorder(int radius, Color color, int thickness) {
        this.radius = radius;
        this.color = color;
        this.thickness = thickness;
    }

    public Insets getBorderInsets(java.awt.Component c) {
        return new Insets(this.radius+1, this.radius+1, this.radius+1, this.radius+1);
    }

    public boolean isBorderOpaque() {
        return false;
    }

    public void paintBorder(java.awt.Component c, java.awt.Graphics g, int x, int y, int width, int height) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(color);
        g2.setStroke(new BasicStroke(thickness));
        g2.drawRoundRect(x + thickness/2, y + thickness/2, width - thickness, height - thickness, radius, radius);
    }
}
