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
        colorfulChar = Screen.colorString(cha, color);
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


}
