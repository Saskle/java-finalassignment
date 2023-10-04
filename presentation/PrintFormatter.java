package presentation;

// ----------------- PURPOSE: Print colours and format strings for printing in a pretty way :) -----------------

public abstract class PrintFormatter {
    public static final String RESET_COLOR = "\u001B[0m";
    public static final String BLACK = "\u001B[30m";
    public static final String RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";

    // usage: 
    // System.out.println(ANSI_RED + "This text is red!" + ANSI_RESET);

    public static final int MAX_LINE_LENGHT = 100;

    public static final String HEADER_LINE = "\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500";
    public static final String DIVIDER = "\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\n";
    public static final String DIVIDER_BROKEN = "----------------------------------------------------------------------------------------------------";
    public static final String DIVIDER_LOW = "____________________________________________________________________________________________________";

    public static String addBox(String text) {
        // split the text on line breaks
        String lines[] = text.split("\\r?\\n");
        
        // go through each line and add calculated whitespace before and after
        String boxedText = "";
        for (String line : lines) {

            int whitespacePerLine = MAX_LINE_LENGHT - line.length() - 3; // minus 3 for 2x | and line break (?)
            String whitespace = calculateWhitespace(line, whitespacePerLine);

            // if the lenght of the line is uneven, add one character less whitespace
            if (line.length() % 2 == 0) {
                boxedText = boxedText + "|" + whitespace + line + whitespace + "|\n";
            } else {
                boxedText = boxedText + "|" + whitespace + line + calculateWhitespace(line, whitespacePerLine - 1) + "|\n";
            }
        }
        return DIVIDER + boxedText + DIVIDER;
    }

    public static String header(String title) {
        // caculate amount of white space so title + 2 * header.lenght = 100 characters long
        int whitespace = MAX_LINE_LENGHT - (2 * HEADER_LINE.length()) - title.length();
        String whitespaceToAdd = calculateWhitespace(title, whitespace);
        return HEADER_LINE + whitespaceToAdd + title + whitespaceToAdd + HEADER_LINE;
    }

    private static String calculateWhitespace(String text, int whitespace) {
        // add whitespace to string
        String whitespaceToAdd = "";
        for (int i = 0; i <= (whitespace / 2); i++) {
            whitespaceToAdd += "\u00A0";
        }
        return whitespaceToAdd;
    }
}
