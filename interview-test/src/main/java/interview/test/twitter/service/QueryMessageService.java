package interview.test.twitter.service;

import interview.test.twitter.connector.NewConnector;
import interview.test.twitter.connector.TwitterConnector;
import interview.test.twitter.models.TwitterMessage;
import interview.test.twitter.property.Properties;
import interview.test.twitter.property.Properties.MessageResultType;
import interview.test.twitter.property.PropertyKey;
import interview.test.twitter.response.ResponseHandler;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

import org.apache.http.impl.cookie.DateParseException;
import org.interview.oauth.twitter.TwitterAuthenticationException;
import org.interview.oauth.twitter.TwitterException;
import org.json.simple.JSONObject;

// TODO: Auto-generated Javadoc
/**
 * The Class QueryMessageService.
 */
public class QueryMessageService {

	/** The Constant QUERY_MESSAGE_PATH. */
	private static final String QUERY_MESSAGE_PATH = "/search/tweets.json?";

	/** The property. */
	private Properties property;

	/** The twitter connector. */
	private TwitterConnector twitterConnector = new TwitterConnector();

	private NewConnector newTwitterConnector = new NewConnector();

	/**
	 * Instantiates a new query message service.
	 */
	public QueryMessageService() {
		this.property = new Properties();
	}

	// TODO Donusu duzelt
	/**
	 * Query.
	 * 
	 * @param keyword
	 *            the keyword
	 * @param resultType
	 *            the result type
	 * @param count
	 *            the count
	 * @return the list
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
	public List<TwitterMessage> query(String keyword,
			MessageResultType resultType, Integer count) throws IOException,
			TwitterException, TwitterAuthenticationException,
			DateParseException, ParseException {
		property.addProperty(PropertyKey.q, keyword);
		property.addProperty(PropertyKey.result_type, resultType.name());
		property.addProperty(PropertyKey.count, count.toString());

		JSONObject responseJson = newTwitterConnector.executeQuery(
				QUERY_MESSAGE_PATH, property.getPropertyList());

		if (responseJson == null) {
			throw new TwitterException("Couldnt Find Anything");
		}

		return ResponseHandler.getResponseAsTwitterMessageLists(responseJson);
	}

}
