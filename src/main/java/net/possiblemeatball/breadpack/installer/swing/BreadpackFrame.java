package net.possiblemeatball.breadpack.installer.swing;

import sun.misc.BASE64Decoder;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

public final class BreadpackFrame extends JFrame {
    public final BufferedImage breadIcon;
    public BreadpackFrame() {
        super("Breadpack Installer");
        BufferedImage breadIcon;
        try {
            breadIcon = ImageIO.read(getClass().getResourceAsStream("/img/bread.png"));
        } catch (IOException e) {
            breadIcon = null;
        }
        this.breadIcon = breadIcon;
        setIconImage(breadIcon);

        setUndecorated(true);
        getRootPane().setWindowDecorationStyle(JRootPane.NONE);

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setAutoRequestFocus(true);
    }

    public final void display() {
        GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        int width = gd.getDisplayMode().getWidth();
        int height = gd.getDisplayMode().getHeight();
        setMinimumSize(new Dimension(1026, 770));
        setLocation((width / 2) - (getWidth() / 2), (height / 2) - (getHeight() / 2));

        setLayout(null);
        add(new CustomFrameComponent(this));
        pack();

        setVisible(true);
    }

    static class CustomFrameComponent extends JComponent {
        private final Font titleFont;

        protected boolean dragging;
        private final BufferedImage breadIcon;

        public CustomFrameComponent(BreadpackFrame frame) {
            setBounds(0, 0, 1026, 770);
            breadIcon = frame.breadIcon;
            Font titleFont;
            try {
                titleFont = Font.createFont(Font.TRUETYPE_FONT, getClass().getResourceAsStream("/fonts/bungee.ttf")).deriveFont(48.0f).deriveFont(Font.PLAIN);
            } catch (FontFormatException | IOException e) {
                titleFont = new Font(Font.MONOSPACED, Font.BOLD, 48);
            }
            this.titleFont = titleFont;

            MouseAdapter dragListener = new MouseAdapter() {
                Point pressedPos = null;

                @Override
                public void mouseReleased(MouseEvent e) {
                    super.mouseReleased(e);
                    pressedPos = null;
                    dragging = false;
                }

                @Override
                public void mousePressed(MouseEvent e) {
                    super.mousePressed(e);
                    Dimension dim = getSize();
                    Point pos = e.getPoint();
                    if (pos.x >= 2 && pos.y >= 2 && pos.x <= dim.width - 4 && pos.y <= 56) {
                        pressedPos = e.getPoint();
                        dragging = true;
                    }
                }

                @Override
                public void mouseDragged(MouseEvent e) {
                    super.mouseDragged(e);
                    Point pos = e.getLocationOnScreen();
                    if (dragging)
                        frame.setLocation(pos.x - pressedPos.x, pos.y - pressedPos.y);
                }
            };

            MouseAdapter controlListener = new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    super.mousePressed(e);
                    Dimension dim = getSize();
                    Point pos = e.getPoint();
                    //close button
                    float x = 21, y = 4;

                    if (pos.x >= dim.width - x && pos.y >= y && pos.x <= (dim.width - x) + 16 && pos.y <= y + 16) {
                        dragging = false;
                        System.exit(0);
                    } else { // minimize button
                        x = 37;
                        y = 4;
                        if (pos.x >= dim.width - x && pos.y >= y && pos.x <= (dim.width - x) + 16 && pos.y <= y + 16) {
                            dragging = false;
                            frame.setState(ICONIFIED);
                        }
                    }
                }
            }; // yeah i hate me too
            addMouseListener(controlListener);
            addMouseListener(dragListener);
            addMouseMotionListener(dragListener);

            BreadpackStepLayoutBox stepLayoutBox = new BreadpackStepLayoutBox(frame);
            stepLayoutBox.setBounds(8, 58, 254, getSize().height - 67);
            add(stepLayoutBox);

            BreadpackContainer container = new BreadpackContainer(frame);
            container.setBounds(268, 58, getSize().width - 276, getSize().height - 67);
            add(container);
            container.setVisible(true);
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

            if (breadIcon != null) {
                g2d.drawImage(breadIcon.getScaledInstance(48, 48, 0), 6, 5, null);
            }

            g2d.setColor(Color.black);
            g2d.drawString("BREADPACK INSTALLER", (10 * 6) - 1, 48 - 1);
            g2d.setColor(Color.white);
            g2d.drawString("BREADPACK INSTALLER", (10 * 6), 48);

            paintWindowControls(g2d, dim);

        }

        private void paintWindowControls(Graphics2D g2d, Dimension dim) {
            // close button
            float x = 21, y = 4;
            g2d.setColor(new Color((244.0f/255), (103.0f/255), (60.0f/255)));
            g2d.fill(new Rectangle2D.Float(dim.width - x, y, 16, 16));
            g2d.setColor(Color.black);
            g2d.draw(new Rectangle2D.Float(dim.width - x, y, 16, 16));

            // minimize button
            x = 37;
            y = 4;
            g2d.setColor(new Color((60.0f/255), (201.0f/255), (244.0f/255)));
            g2d.fill(new Rectangle2D.Float(dim.width - x, y, 16, 16));
            g2d.setColor(Color.black);
            g2d.draw(new Rectangle2D.Float(dim.width - x, y, 16, 16));

            // i hate the way i did this too
        }

        // border painting
        private void paintBorder(Graphics2D g2d, Dimension dim) {
            float offset = 0;
            g2d.setColor(Color.black);
            g2d.draw(new Rectangle2D.Float(offset, offset, dim.width - (offset * 2) - 1, dim.height - (offset * 2) - 1));

            g2d.setColor(Color.gray);
            offset = 1;
            g2d.draw(new Rectangle2D.Float(offset, offset, dim.width - (offset * 2) - 1, dim.height - (offset * 2) - 1));
        }
    }
}