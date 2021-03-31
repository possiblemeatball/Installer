package net.possiblemeatball.breadpack.installer.swing;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
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

        add(new CustomFrameComponent(this));

        GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        int displayWidth = gd.getDisplayMode().getWidth();
        int displayHeight = gd.getDisplayMode().getHeight();

        int width = 1024, height = 768;
        int x = (displayWidth / 2) - (width / 2),
             y = (displayHeight / 2) - (height / 2);
        setBounds(x, y, width, height);
        setLayout(null);
        setResizable(false);

        setVisible(true);
    }
}