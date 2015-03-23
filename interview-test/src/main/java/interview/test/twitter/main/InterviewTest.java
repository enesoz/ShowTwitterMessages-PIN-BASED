/*
 * 
 */
package interview.test.twitter.main;

import interview.test.twitter.models.TwitterMessage;
import interview.test.twitter.property.Properties.MessageResultType;
import interview.test.twitter.service.QueryMessageService;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import java.util.ResourceBundle;

import org.apache.http.impl.cookie.DateParseException;
import org.interview.oauth.twitter.TwitterAuthenticationException;
import org.interview.oauth.twitter.TwitterException;

import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;

/**
 * The Class InterviewTest.
 */
public class InterviewTest {

	/** The Constant bundle. */
	public static final ResourceBundle bundle = ResourceBundle
			.getBundle("twitter");

	/**
	 * The main method.
	 * 
	 * @param args
	 *            the arguments
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 * @throws TwitterException
	 *             the twitter exception
	 * @throws TwitterAuthenticationException
	 *             the twitter authentication exception
	 * @throws DateParseException
	 *             the date parse exception
	 * @throws ParseException
	 *             the parse exception
	 */
	public static void main(String[] args) throws IOException,
			TwitterException, TwitterAuthenticationException,
			DateParseException, ParseException {
		QueryMessageService service = new QueryMessageService();

		// To calculate work time
		long startTime = System.currentTimeMillis(); // Get the start Time
		long endTime = 0;

		//
		String str;

		// To add defult string for searching
		if (args.length == 0) {
			str = "bieber";
		} else {
			str = args[0];
		}

		List<TwitterMessage> resultList = service.query(str,
				MessageResultType.recent, 100);

		// set flag to file is written or not

		// Open result on browser
		AuthorizationCodeInstalledApp.browse(bundle.getString("report.html"));
		endTime = System.currentTimeMillis();

		// if browser doesn't open write result to system.console
		//System.out.println(resultList);

		// Calculate time
		System.out.println("Differencce in Seconds: " + (endTime - startTime)
				/ 1000);
	}
}
