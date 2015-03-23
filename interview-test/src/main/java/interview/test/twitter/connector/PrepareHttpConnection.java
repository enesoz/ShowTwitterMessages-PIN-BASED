package interview.test.twitter.connector;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.net.ssl.HttpsURLConnection;

import com.google.api.client.util.Base64;

/**
 * The Class PrepareHttpConnection.
 */
public class PrepareHttpConnection {

	/** The request token url. */
	private static String REQUEST_TOKEN_URL;

	/** The query server url. */
	private static String QUERY_SERVER_URL;

	/** The key of consumer. */
	private String key;

	/** The secret of consumer. */
	private String secret;

	/** The bundle. */
	private ResourceBundle bundle;

	/**
	 * Instantiates a new prepare http connection.
	 */
	public PrepareHttpConnection() {
		this.bundle = ResourceBundle.getBundle("twitter", Locale.getDefault());
		this.key = bundle.getString("key");
		this.secret = bundle.getString("secret");
		this.REQUEST_TOKEN_URL = bundle.getString("request.token.url");
		this.QUERY_SERVER_URL = bundle.getString("request.query.url");
	}

	/**
	 * Prepare connection for authorization.
	 * 
	 * @return the https url connection
	 */
	public HttpsURLConnection prepareAuthorizeConnection() {
		HttpsURLConnection connection = null;
		String encodedCredentials = prepareBearerToken();

		try {
			URL url = new URL(REQUEST_TOKEN_URL);
			connection = (HttpsURLConnection) url.openConnection();
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setRequestMethod("POST");
			connection.setRequestProperty("Host", "api.twitter.com");
			connection.setRequestProperty("User-Agent",
					bundle.getString("application.name"));
			connection.setRequestProperty("Authorization", "Basic "
					+ encodedCredentials);
			connection.setRequestProperty("Content-Type",
					"application/x-www-form-urlencoded;charset=UTF-8");
			connection.setRequestProperty("Content-Length", "29");
			connection.setUseCaches(false);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (ProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return connection;
	}

	/**
	 * Prepare connection for query execution.
	 * 
	 * @param bearerToken
	 *            the bearer token
	 * @param query
	 *            the query
	 * @return the https url connection
	 */
	public HttpsURLConnection prepareExecuteConnection(String bearerToken,
			String query) {
		HttpsURLConnection connection = null;
		String queryUrl = QUERY_SERVER_URL.concat(query);
		try {
			URL url = new URL(queryUrl);
			connection = (HttpsURLConnection) url.openConnection();
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setRequestMethod("GET");
			connection.setRequestProperty("Host", "api.twitter.com");
			connection.setRequestProperty("User-Agent",
					bundle.getString("application.name"));
			connection.setRequestProperty("Authorization", "Bearer "
					+ bearerToken);
			connection.setUseCaches(false);

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (ProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return connection;
	}

	/**
	 * Prepare bearer token.
	 * 
	 * @return the string
	 */
	private String prepareBearerToken() {
		String tokenizer = key.concat(":").concat(secret);
		return Base64.encodeBase64String(tokenizer.getBytes());
	}
}
