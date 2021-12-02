package mnita.ansiconsole;

import org.eclipse.swt.SWT;

public class AnsiConsoleUtils {
    public static final String ESCAPE_SEQUENCE_REGEX_TXT = "\u001b\\[[\\d;]*[A-HJKSTfimnsu]";
    public static final String ESCAPE_SEQUENCE_REGEX_RTF = "\\{\\\\cf\\d+[^}]* \u001b\\[[\\d;]*[A-HJKSTfimnsu][^}]*\\}";
    public static final String ESCAPE_SEQUENCE_REGEX_RTF_FIX_SRC = "\\\\chshdng\\d+\\\\chcbpat(\\d+)";
    public static final String ESCAPE_SEQUENCE_REGEX_RTF_FIX_TRG = "$0\\\\cb$1";

    public static final char ESCAPE_SGR = 'm';

    private AnsiConsoleUtils() {
        // Utility class, should not be instantiated
    }

    public static boolean isWindows() {
        return "win32".equals(SWT.getPlatform());
    }

    public static boolean isMacOS() {
        return "cocoa".equals(SWT.getPlatform());
    }

    public static boolean isGTK() {
        return "gtk".equals(SWT.getPlatform());
    }
}
