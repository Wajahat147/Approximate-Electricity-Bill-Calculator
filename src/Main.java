import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {
        // Apply dark title bar and smooth rendering hints
        System.setProperty("awt.useSystemAAFontSettings", "on");
        System.setProperty("swing.aatext", "true");
        System.setProperty("sun.java2d.opengl", "true");

        SwingUtilities.invokeLater(() -> {
            try {
                // Use system look-and-feel as a base, then override everything
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception ignored) {}
            applyGlobalDefaults();
            new MainWindow();
        });
    }

    private static void applyGlobalDefaults() {
        Color bg    = MainWindow.BG_DARK;
        Color panel = MainWindow.BG_PANEL;
        Color text  = MainWindow.TEXT_PRIMARY;
        Color sel   = MainWindow.ACCENT_BLUE;

        UIManager.put("Panel.background",           panel);
        UIManager.put("Frame.background",           bg);
        UIManager.put("Label.foreground",           text);
        UIManager.put("TextField.background",       MainWindow.BG_CARD);
        UIManager.put("TextField.foreground",       text);
        UIManager.put("TextField.caretForeground",  sel);
        UIManager.put("TextField.selectionBackground", sel);
        UIManager.put("TextField.selectionForeground", Color.WHITE);
        UIManager.put("ScrollPane.background",      bg);
        UIManager.put("ToolTip.background",         MainWindow.BG_CARD);
        UIManager.put("ToolTip.foreground",         text);
        UIManager.put("ToolTip.border",
            BorderFactory.createLineBorder(MainWindow.BORDER_COLOR));
    }
}