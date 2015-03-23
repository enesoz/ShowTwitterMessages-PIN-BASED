package interview.test.twitter.connector;

import interview.test.twitter.property.PropertyKey;
import interview.test.twitter.util.HttpRequestUtil;
import interview.test.twitter.util.RequestUtil;

import java.io.IOException;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import javax.net.ssl.HttpsURLConnection;

import org.interview.oauth.twitter.TwitterAuthenticationException;
import org.interview.oauth.twitter.TwitterException;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;

/**
 * The Class TwitterConnector.
 */
public class TwitterConnector extends HttpRequestUtil {

	/** OAuth 2 scope. */
	private static final String SCOPE = "read";

	/** Global instance of the HTTP transport. */
	private static final HttpTransport HTTP_TRANSPORT = new NetHttpTransport();

	/** The bundle. */
	private ResourceBundle bundle;

	/** The access token. */
	private String accessToken;

	/** The access token secret. */
	private String accessTokenSecret;

	/**
	 * Instantiates a new twitter connector.
	 */
	public TwitterConnector() {
		this.bundle = ResourceBundle.getBundle("twitter", Locale.getDefault());

		this.accessToken = bundle.getString("access.token");
		this.accessTokenSecret = bundle.getString("access.token.secret");

	}

	/**
	 * Request bearer token.
	 * 
	 * @return the string
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 * @throws TwitterAuthenticationException
	 *             the twitter authentication exception
	 */
	private String requestBearerToken() throws IOException,
			TwitterAuthenticationException {
		HttpsURLConnection connection = null;
		try {
			connection = new PrepareHttpConnection()
					.prepareAuthorizeConnection();
			writeRequest(connection, "grant_type=client_credentials");

			org.json.simple.JSONObject obj = (org.json.simple.JSONObject) JSONValue
					.parse(readResponse(connection));

			if (obj != null) {
				String tokenType = (String) obj.get("token_type");
				String token = (String) obj.get("access_token");

				return ((tokenType.equals("bearer")) && (token != null)) ? token
						: "";
			}
			return new String();
		} catch (Exception e) {
			throw new TwitterAuthenticationException("An Error Occured");
		} finally {
			if (connection != null) {
				connection.disconnect();
			}
		}
	}

	/**
	 * Execute query.
	 * 
	 * @param basePath
	 *            the base path
	 * @param properties
	 *            the properties
	 * @return the JSON object
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 * @throws TwitterException
	 *             the twitter exception
	 * @throws TwitterAuthenticationException
	 *             the twitter authentication exception
	 */
	public JSONObject executeQuery(String basePath,
			Map<PropertyKey, String> properties) throws IOException,
			TwitterException, TwitterAuthenticationException {

		HttpsURLConnection connection = null;
		// To get bearerToken
		String bearer = requestBearerToken();
		// To prepare requestQuery
		String query = RequestUtil.prepareRequest(basePath, properties);
		try {

			connection = new PrepareHttpConnection().prepareExecuteConnection(
					bearer, query);
			JSONObject obj = (JSONObject) JSONValue
					.parse(readResponse(connection));

			return obj;
		} catch (Exception e) {
			throw new TwitterException("An Error Occured" + e.getMessage());
		} finally {
			if (connection != null) {
				connection.disconnect();
			}
		}
	}

}
