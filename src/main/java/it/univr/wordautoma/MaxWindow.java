package it.univr.wordautoma;

import java.awt.*;

public class MaxWindow {
    private static final MaxWindow instance = new MaxWindow();
    private final int width;
    private final int height;

    private MaxWindow() {
        Rectangle screenSize = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds();
        width = screenSize.width;
        height = screenSize.height;
    }

    public static MaxWindow getInstance() { return instance; }

    public int getWidth() { return width; }

    public int getHeight() { return height; }
}
