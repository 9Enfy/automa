package it.univr.wordautoma;

import java.awt.*;

/**
 * Classe per gestire la grandezza della finestra in base allo spazio disponibile nello schermo
 * (tiene conto anche delle barre laterali)
 */
public class MaxWindow {
    private static final MaxWindow instance = new MaxWindow();
    private final int width;
    private final int height;

    /**
     * Costruttore che imposta la larghezza e l'altezza della finestra
     */
    private MaxWindow() {
        Rectangle screenSize = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds();
        width = screenSize.width;
        height = screenSize.height;
    }

    /**
     * Metodo che ritorna l'istanza della classe
     * @return Istanza della classe
     */
    public static MaxWindow getInstance() { return instance; }

    /**
     * Metodo che ritorna la larghezza della finestra
     * @return Larghezza della finestra
     */
    public int getWidth() { return width; }

    /**
     * Metodo che ritorna l'altezza della finestra
     * @return Altezza della finestra
     */
    public int getHeight() { return height; }
}
