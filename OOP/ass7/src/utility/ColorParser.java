package utility;


import java.awt.Color;
import java.lang.reflect.Field;

public class ColorParser {
    // parse color definition and return the specified color.
    public java.awt.Color colorFromString(String s) {
        try {
            Field field = Class.forName("java.awt.Color").getField(s);
            return (Color) field.get(null);
        } catch (Exception e) {
            return null; // Not defined
        }
    }
}
