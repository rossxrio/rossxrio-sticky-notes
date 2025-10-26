package studio.rossxrio;

import java.awt.*;
import java.io.File;
import java.io.IOException;

public class Fonts {
    private static final Font font;

    static {
        try {
            font = Font.createFont(Font.TRUETYPE_FONT, new File("./src/main/resources/fonts/0xProtoNerdFont-Regular.ttf") );
        } catch (FontFormatException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static Font getRegularFont(int style, int size) {
        return font.deriveFont(size, size);
    }
}
