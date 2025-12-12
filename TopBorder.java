import java.awt.*;
import javax.swing.border.AbstractBorder;

class TopBorder extends AbstractBorder {
    private final int thickness;
    private final Color color;

    public TopBorder(int thickness, Color color) {
        this.thickness = thickness;
        this.color = color;
    }

    @Override
    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
        g.setColor(color);
        g.fillRect(x, y, width, thickness);
    }
}
