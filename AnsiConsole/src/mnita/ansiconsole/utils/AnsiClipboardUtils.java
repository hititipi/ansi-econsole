package mnita.ansiconsole.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

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
			String plainText = textData.toString().replaceAll(AnsiConsoleUtils.ESCAPE_SEQUENCE_REGEX_TXT, "");
			plainText = plainText .toString().replaceAll("nice", "nice, from Text");
			clipboardData.add(plainText);
			clipboardTransfers.add(textTransfer);
		}

		RTFTransfer rtfTransfer = RTFTransfer.getInstance();
		Object rtfData = clipboard.getContents(rtfTransfer);
		if (rtfData != null && rtfData instanceof String) {
			System.out.println("Found RTF!");
			String rtfText = rtfData.toString().replaceAll(AnsiConsoleUtils.ESCAPE_SEQUENCE_REGEX_RTF, "");
		    if (!isWindows()) {
		    	rtfText = rtfText.replaceAll("\\\\chshdng\\d+\\\\chcbpat", "\\\\cb");
		    }
		    rtfText = rtfText.replaceAll("nice", "nice, from RTF");
			clipboardData.add(rtfText);
			clipboardTransfers.add(rtfTransfer);
		}

		HTMLTransfer htmlTransfer = HTMLTransfer.getInstance();
		Object htmlData = clipboard.getContents(htmlTransfer);
		if (htmlData != null && htmlData instanceof String) {
			System.out.println("Found HTML!");
			String htmlText = htmlData.toString().replaceAll("nice", "nice, from HTML");
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
		dump();
	}

	static boolean isWindows() {
		// prop: osgi.os : win32
	    String os = System.getProperty("os.name").toLowerCase(Locale.ROOT);
        return os != null && os.contains("win");
	}

	static void dump() {
		Properties props = System.getProperties();
		for (Entry<Object, Object> e : props.entrySet()) {
			System.out.println("prop: " + e.getKey() + " : " + e.getValue());
		}
		Map<String, String> environ = System.getenv();
		for (Entry<String, String> e : environ.entrySet()) {
			System.out.println("env : " + e.getKey() + " : " + e.getValue());
		}
	}
}
