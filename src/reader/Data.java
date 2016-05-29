package reader;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.AbstractListModel;

public class Data extends AbstractListModel<String> {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3554382983811861193L;
	List<String> message = new ArrayList<>();

	public void add(String state) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(
				"yyyy/MM/dd HH:mm:ss.SSSSS");
		Date date = new Date();
		message.add(dateFormat.format(date) + ": " + state);
		fireContentsChanged(message, 0, message.size());
	}

	public void reset() {
		message = new ArrayList<>();
		fireContentsChanged(message, 0, message.size());
	}

	@Override
	public String getElementAt(int arg0) {
		return message.get(arg0);
	}

	@Override
	public int getSize() {
		return message.size();
	}

	@Override
	public String toString() {
		String out = "";
		for (String s : message) {
			if (s.charAt(s.length() - 1) == 'H')
				out += "1";
			else
				out += "0";
		}
		return out;
	}
}
