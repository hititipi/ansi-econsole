package mnita.ansiconsole.utils;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.dnd.Clipboard;
import org.eclipse.swt.dnd.RTFTransfer;
import org.eclipse.swt.dnd.TextTransfer;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.widgets.Display;

import mnita.ansiconsole.AnsiConsoleUtils;

public class AnsiClipboardUtils {

	public static void textToClipboard(StyledText styledText, boolean removeEscapeSeq) {
		styledText.copy(); // copy to clipboard using the defaults

		if (!removeEscapeSeq) {
			return;
		}

		Clipboard clipboard = new Clipboard(Display.getDefault());

		List<Object> clipboardData = new ArrayList<>(2);
		List<Transfer> clipboardTransfers = new ArrayList<>(2);

		TextTransfer textTransfer = TextTransfer.getInstance();
		Object textData = clipboard.getContents(textTransfer);
		if (textData != null && textData instanceof String) {
			String plainText = textData.toString().replaceAll(AnsiConsoleUtils.ESCAPE_SEQUENCE_REGEX_TXT, "");
			clipboardData.add(plainText);
			clipboardTransfers.add(textTransfer);
		}

		RTFTransfer rtfTransfer = RTFTransfer.getInstance();
		Object rtfData = clipboard.getContents(rtfTransfer);
		if (rtfData != null && rtfData instanceof String) {
			String rtfText = rtfData.toString().replaceAll(AnsiConsoleUtils.ESCAPE_SEQUENCE_REGEX_RTF, "");
			clipboardData.add(rtfText);
			clipboardTransfers.add(rtfTransfer);
		}

		clipboard.setContents(clipboardData.toArray(), clipboardTransfers.toArray(new Transfer[0]));

		clipboard.dispose();
	}
}
