// ID: 584698174

package gameio;

import java.awt.Color;
import java.lang.reflect.Field;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Contains a utility method to derive the color described in a string of the form:
 * "color(red)" or "color(RGB(r, g, b))".
 * @author David Dinkevich
 */
public class ColorsParser {
    /**
     * Parse the string and derive the described color.
     * @param s the string that contains the color
     * @return a Color object representing the color
     */
    public Color colorFromString(String s) {
        // String is of the form "color(RGB(r, g, b))"
        if (s.contains("RGB")) {
            float[] rgb = new float[3];
            Matcher matcher = Pattern.compile("\\d+").matcher(s);
            for (int i = 0; matcher.find(); i++) {
                rgb[i] = Integer.parseInt(matcher.group()) / 255f;
            }
            return new Color(rgb[0], rgb[1], rgb[2]);
        } else {
            String colName = s.split("\\(")[1].split("\\)")[0];
            try {
                Field field = Class.forName("java.awt.Color").getField(colName);
                return (Color) field.get(null);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
