package net.possiblemeatball.breadpack.installer.swing;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public final class BreadpackFrame extends JFrame {
    public BreadpackFrame() {
        super("Breadpack Installer");
        setUndecorated(true);
        getRootPane().setWindowDecorationStyle(JRootPane.NONE);

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setAutoRequestFocus(true);

        MouseAdapter dragListener = new MouseAdapter() {
            private Point pressedPos = null;

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
        setMinimumSize(getSize());
        GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        int width = gd.getDisplayMode().getWidth();
        int height = gd.getDisplayMode().getHeight();

        setLocation((width / 2) - (getWidth() / 2), (height / 2) - (getHeight() / 2));
        setVisible(true);
    }

    static class CustomFrameComponent extends JComponent {
        @Override
        public Dimension getMinimumSize() {
            return new Dimension(640, 480);
        }

        @Override
        public Dimension getPreferredSize() {
            return new Dimension(1024, 768);
        }

        @Override
        public void paintComponent(Graphics g) {
            Dimension dim = getSize();
            Graphics2D g2d = (Graphics2D) g;
            super.paintComponent(g);
            // painting the border
            g.setColor(Color.black);
            g.fillRect(0, 0, dim.width, dim.height);

            g.setColor(Color.gray);
            g.fillRect(1, 1, dim.width - (1 * 2), dim.height - (1 * 2));

            g.setColor(Color.darkGray);
            g.fillRect(2, 2, dim.width - (2 * 2), dim.height - (2 * 2));

            // close / minimize buttons

        }
    }
}