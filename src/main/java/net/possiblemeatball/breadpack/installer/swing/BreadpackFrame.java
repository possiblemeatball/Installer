package net.possiblemeatball.breadpack.installer.swing;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

public final class BreadpackFrame extends JFrame {
    public BreadpackFrame() {
        super("Breadpack Installer");
        try {
            BufferedImage breadIcon = ImageIO.read(getClass().getResourceAsStream("/img/bread.png"));
            setIconImage(breadIcon.getScaledInstance(64, 64, 0));
        } catch (IOException e) {
            e.printStackTrace();
        }

        setUndecorated(true);
        getRootPane().setWindowDecorationStyle(JRootPane.NONE);

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setAutoRequestFocus(true);

        MouseAdapter dragListener = new MouseAdapter() {
            Point pressedPos = null;

            public void mouseReleased(MouseEvent e) {
                pressedPos = null;
            }

            public void mousePressed(MouseEvent e) {
                pressedPos = e.getPoint();
            }

            public void mouseDragged(MouseEvent e) {
                Point pos = e.getLocationOnScreen();
                setLocation(pos.x - pressedPos.x, pos.y - pressedPos.y);
            }
        };
        addMouseListener(dragListener);
        addMouseMotionListener(dragListener);
    }

    public final void display() {
        add(new CustomFrameComponent());
        pack();
        setMinimumSize(new Dimension(1026, 770));
        GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        int width = gd.getDisplayMode().getWidth();
        int height = gd.getDisplayMode().getHeight();

        setLocation((width / 2) - (getWidth() / 2), (height / 2) - (getHeight() / 2));
        setVisible(true);
    }

    static class CustomFrameComponent extends JComponent {
        private final Font titleFont;

        public CustomFrameComponent() {
            Font titleFont;
            try {
                titleFont = Font.createFont(Font.TRUETYPE_FONT, getClass().getResourceAsStream("/fonts/bungee.ttf")).deriveFont(48.0f).deriveFont(Font.PLAIN);
            } catch (FontFormatException | IOException e) {
                titleFont = new Font(Font.MONOSPACED, Font.BOLD, 48);
            }
            this.titleFont = titleFont;
        }
        @Override
        public Dimension getMinimumSize() {
            return new Dimension(1026, 770);
        }

        @Override
        public void paintComponent(Graphics g) {
            Dimension dim = getSize();
            Graphics2D g2d = (Graphics2D) g;
            g2d.setFont(titleFont);
            g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB);
            super.paintComponent(g);
            paintBorder(g2d, dim);

            g2d.setColor(Color.darkGray);
            g2d.fill(new Rectangle2D.Float(2, 2, dim.width - 4, dim.height - 4));

            try {
                BufferedImage breadIcon = ImageIO.read(getClass().getResourceAsStream("/img/bread.png"));

                g2d.drawImage(breadIcon.getScaledInstance(48, 48, 0), 8, 8, null);
            } catch (IOException e) {
                e.printStackTrace();
            }

            g2d.setColor(Color.black);
            g2d.drawString("Breadpack Installer", (10 * 6) - 1, 50 - 1);
            g2d.setColor(Color.white);
            g2d.drawString("Breadpack Installer", (10 * 6), 50);
        }

        // border painting
        private void paintBorder(Graphics2D g2d, Dimension dim) {
            float offset = 0;
            g2d.setColor(Color.black);
            g2d.draw(new Rectangle2D.Float());
            g2d.draw(new Rectangle2D.Float(offset, offset, dim.width - (offset * 2) - 1, dim.height - (offset * 2) - 1));

            g2d.setColor(Color.gray);
            offset = 1;
            g2d.draw(new Rectangle2D.Float(offset, offset, dim.width - (offset * 2) - 1, dim.height - (offset * 2) - 1));
        }
    }
}