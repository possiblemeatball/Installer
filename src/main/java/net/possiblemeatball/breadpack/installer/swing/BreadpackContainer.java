package net.possiblemeatball.breadpack.installer.swing;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.io.IOException;

public class BreadpackContainer extends JComponent {
    private final Font titleFont;
    private final Font basicFont;
    public BreadpackContainer(BreadpackFrame frame) {
        setLayout(null);
        Font titleFont;
        try {
            titleFont = Font.createFont(Font.TRUETYPE_FONT, getClass().getResourceAsStream("/fonts/anonymouspro.ttf")).deriveFont(48.0f).deriveFont(Font.PLAIN);
        } catch (FontFormatException | IOException e) {
            titleFont = new Font(Font.MONOSPACED, Font.BOLD, 48);
        }
        this.titleFont = titleFont;
        Font basicFont;
        try {
            basicFont = Font.createFont(Font.TRUETYPE_FONT, getClass().getResourceAsStream("/fonts/anonymouspro.ttf")).deriveFont(18.0f).deriveFont(Font.PLAIN);
        } catch (FontFormatException | IOException e) {
            basicFont = new Font(Font.MONOSPACED, Font.BOLD, 18);
        }
        this.basicFont = basicFont;
    }

    @Override
    protected void paintComponent(Graphics g) {
        Dimension dim = getSize();
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB);
        super.paintComponent(g);
        paintBorder(g2d, dim);

        g2d.setColor(new Color(32, 32, 32));
        g2d.fill(new Rectangle2D.Float(2, 2, dim.width - 4, dim.height - 4));

        g2d.setFont(titleFont);
        g2d.setColor(Color.lightGray);
        g2d.drawString("Woo", 8, (2 * (titleFont.getSize2D() / 2)));
        g2d.setFont(basicFont);
        g2d.setColor(Color.lightGray);
        g2d.drawString("Content goes here", 8, (16 * (basicFont.getSize2D() / 2)));
    }

    // border painting
    private void paintBorder(Graphics2D g2d, Dimension dim) {
        float offset = 0;
        g2d.setColor(new Color(117, 117, 117));
        g2d.draw(new Rectangle2D.Float(offset, offset, dim.width - (offset * 2) - 1, dim.height - (offset * 2) - 1));

        g2d.setColor(Color.black);
        offset = 1;
        g2d.draw(new Rectangle2D.Float(offset, offset, dim.width - (offset * 2) - 1, dim.height - (offset * 2) - 1));
    }
}
