package interview.test.twitter.service;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ResourceBundle;

// TODO: Auto-generated Javadoc
/**
 * The Class of File Operations.
 */
public class FileService {

	/** The bundle. */
	private static ResourceBundle bundle = ResourceBundle.getBundle("twitter");
	
	/** The Constant TWEETS_JSON. */
	private static final String TWEETS_JSON = bundle
			.getString("twitter.messages.json.path");

	/**
	 * Write messages to file.
	 *
	 * @param messages the messages
	 * @return true, if successful
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public static boolean writeMessagesToFile(String messages)
			throws IOException {
		try {
			FileOutputStream fos = new FileOutputStream(TWEETS_JSON);
			Writer out = new OutputStreamWriter(fos, "UTF8");
			out.write(messages);
			out.close();
			return true;
		} catch (IOException e) {
			throw new IOException("Couldnt write response to file "
					+ e.getMessage());
		}

	}
}
