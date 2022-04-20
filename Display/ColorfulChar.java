package Display;

public class ColorfulChar {
    String colorfulChar;

    /**
     * Generate a "char" with color
     * 
     * @param cha
     * @param color
     */
    public ColorfulChar(char cha, Color color) {
        this.colorfulChar = ColorfulChar.colorString(cha, color);
    }

    /**
     * Generate a "char" without color
     * 
     * @param cha
     */
    public ColorfulChar(char cha) {
        colorfulChar = String.valueOf(cha);
    }

    @Override
    public String toString() {
        return colorfulChar;
    }

    public static String colorString(char cha, Color color) {
        return colorString(String.valueOf(cha), color);
    }

    public static String colorString(char[] charArray, Color color) {
        return colorString(new String(charArray), color);
    }

    public static String colorString(String string, Color color) {
        StringBuffer sb = new StringBuffer();
        sb.append(color);
        sb.append(new String(string));
        sb.append(Color.RESET);
        return sb.toString();
    }


}
