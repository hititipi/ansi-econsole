package mnita.ansiconsole.utils;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.dnd.Clipboard;
import org.eclipse.swt.dnd.HTMLTransfer;
import org.eclipse.swt.dnd.RTFTransfer;
import org.eclipse.swt.dnd.TextTransfer;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.widgets.Display;

import mnita.ansiconsole.AnsiConsoleUtils;

public class AnsiClipboardUtils {

    public static void textToClipboard(StyledText styledText, boolean removeEscapeSeq) {
        Clipboard clipboard = new Clipboard(Display.getDefault());
        clipboard.clearContents();

        styledText.copy(); // copy to clipboard using the defaults

        if (!removeEscapeSeq) {
            return;
        }

        List<Object> clipboardData = new ArrayList<>(2);
        List<Transfer> clipboardTransfers = new ArrayList<>(2);

        TextTransfer textTransfer = TextTransfer.getInstance();
        Object textData = clipboard.getContents(textTransfer);
        if (textData != null && textData instanceof String) {
            System.out.println("Found TEXT!");
            String plainText = AnsiConsoleUtils.ESCAPE_SEQUENCE_REGEX_TXT
            		.matcher((String) textData)
            		.replaceAll("");
            plainText = plainText.replaceAll("nice", "nice, from Text");
            clipboardData.add(plainText);
            clipboardTransfers.add(textTransfer);
        }

        RTFTransfer rtfTransfer = RTFTransfer.getInstance();
        Object rtfData = clipboard.getContents(rtfTransfer);
        if (rtfData != null && rtfData instanceof String) {
            System.out.println("Found RTF!");
            String rtfText = AnsiConsoleUtils.ESCAPE_SEQUENCE_REGEX_RTF
            		.matcher((String) rtfData)
            		.replaceAll("");
            // The Win version of MS Word, and Write, understand \chshdng and \chcbpat, but not \cb
            // The MacOS tools seem to understand \cb, but not \chshdng and \chcbpat
            // But using both seems to work fine, both systems just ignore the tags they don't understand.
            rtfText = AnsiConsoleUtils.ESCAPE_SEQUENCE_REGEX_RTF_FIX_SRC
            		.matcher(rtfText)
            		.replaceAll(AnsiConsoleUtils.ESCAPE_SEQUENCE_REGEX_RTF_FIX_TRG);
            rtfText = rtfText.replaceAll("nice", "nice, from RTF");
            clipboardData.add(rtfText);
            clipboardTransfers.add(rtfTransfer);
        }

        HTMLTransfer htmlTransfer = HTMLTransfer.getInstance();
        Object htmlData = clipboard.getContents(htmlTransfer);
        if (htmlData != null && htmlData instanceof String) {
            System.out.println("Found HTML!");
            String htmlText = ((String) htmlData).replaceAll("nice", "nice, from HTML");
            clipboardData.add(htmlText);
            clipboardTransfers.add(htmlTransfer);
        } else {
            System.out.println("Created HTML!");
            clipboardData.add("<meta charset=\"utf-8\">"
                    + "<b style=\"font-weight:normal;\" id=\"docs-internal-guid-77fc1130-7fff-9f5a-ae37-70559b3d25b2\">"
                    + "<span style=\"font-family:monospace;color:#000000;background-color:transparent;font-weight:400;font-style:normal;text-decoration:none;white-space:pre;white-space:pre-wrap;\" xml:space=\"preserve\">This </span>"
                    + "<span style=\"font-family:monospace;color:#38761d;background-color:transparent;font-weight:400;font-style:normal;text-decoration:none;white-space:pre;white-space:pre-wrap;\" xml:space=\"preserve\">works</span>"
                    + "<span style=\"font-family:monospace;color:#000000;background-color:transparent;font-weight:400;font-style:normal;text-decoration:none;white-space:pre;white-space:pre-wrap;\" xml:space=\"preserve\">, and this </span>"
                    + "<span style=\"font-family:monospace;color:#000000;background-color:#ff0000;font-weight:400;font-style:normal;text-decoration:none;white-space:pre;white-space:pre-wrap;\" xml:space=\"preserve\"> </span>"
                    + "<span style=\"font-family:monospace;color:#000000;background-color:#ffff00;font-weight:400;font-style:normal;text-decoration:none;white-space:pre;white-space:pre-wrap;\" xml:space=\"preserve\">•</span>"
                    + "<span style=\"font-family:monospace;color:#000000;background-color:#0000ff;font-weight:400;font-style:normal;text-decoration:none;white-space:pre;white-space:pre-wrap;\" xml:space=\"preserve\"> </span>"
                    + "<span style=\"font-family:monospace;color:#000000;background-color:transparent;font-weight:400;font-style:normal;text-decoration:none;white-space:pre;white-space:pre-wrap;\" xml:space=\"preserve\"> is nice, from HTML.</span>"
                    + "</b>");
            clipboardTransfers.add(htmlTransfer);
        }

        clipboard.setContents(clipboardData.toArray(), clipboardTransfers.toArray(new Transfer[0]));

        clipboard.dispose();
    }
}
