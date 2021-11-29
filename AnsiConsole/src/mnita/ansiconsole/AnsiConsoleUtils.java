package mnita.ansiconsole;

public class AnsiConsoleUtils {
    public static final String ESCAPE_SEQUENCE_REGEX_TXT = "\u001b\\[[\\d;]*[A-HJKSTfimnsu]";
    public static final String ESCAPE_SEQUENCE_REGEX_RTF = "\\{\\\\cf\\d+[^}]* \u001b\\[[\\d;]*[A-HJKSTfimnsu][^}]*\\}";
    public static final char ESCAPE_SGR = 'm';

    private AnsiConsoleUtils() {
        // Utility class, should not be instantiated
    }
}
