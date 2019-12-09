package utility;


import java.awt.Color;

/**
 * A class for parsing colors.
 *
 * @author Amit Twito
 */
public class ColorParser {
    /**
     * Parses color definition and return the specified color.
     *
     * @param s String for color.
     * @return Specified color.
     */
    public java.awt.Color colorFromString(String s) {
        Color color = null;
        switch (s.toLowerCase()) {
            default:
                color = null;
            case "black":
                color = Color.BLACK;
                break;
            case "blue":
                color = Color.BLUE;
                break;
            case "cyan":
                color = Color.CYAN;
                break;
            case "gray":
                color = Color.GRAY;
                break;
            case "green":
                color = Color.GREEN;
                break;
            case "yellow":
                color = Color.YELLOW;
                break;
            case "lightgray":
                color = Color.LIGHT_GRAY;
                break;
            case "orange":
                color = Color.ORANGE;
                break;
            case "pink":
                color = Color.PINK;
                break;
            case "red":
                color = Color.RED;
                break;
            case "white":
                color = Color.WHITE;
                break;

        }
        return color;
    }
}

