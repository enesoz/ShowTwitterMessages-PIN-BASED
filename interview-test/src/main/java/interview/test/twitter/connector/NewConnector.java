package interview.test.twitter.connector;

import interview.test.twitter.property.PropertyKey;
import interview.test.twitter.util.RequestUtil;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.net.URL;
import java.nio.charset.CoderMalfunctionError;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import javax.net.ssl.HttpsURLConnection;

import org.interview.oauth.twitter.TwitterAuthenticationException;
import org.interview.oauth.twitter.TwitterAuthenticator;
import org.interview.oauth.twitter.TwitterException;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;

public class NewConnector {

	/** The access token. */
	private String accessToken;

	/** The access token secret. */
	private String accessTokenSecret;

	private ResourceBundle bundle;

	private String secret;

	private String key;

	private HttpRequestFactory httpRequestFactory;

	private String QUERY_SERVER_URL;

	public NewConnector() {
		// TODO Auto-generated constructor stub

		this.bundle = ResourceBundle.getBundle("twitter", Locale.getDefault());

		this.key = bundle.getString("key");
		this.secret = bundle.getString("secret");
		this.accessToken = bundle.getString("access.token");
		this.accessTokenSecret = bundle.getString("access.token.secret");
		this.QUERY_SERVER_URL = bundle.getString("request.query.url");
	}

	private HttpRequestFactory authenticate()
			throws TwitterAuthenticationException {

		try {
			TwitterAuthenticator authenticator = new TwitterAuthenticator(
					new PrintStream(System.out), key, secret);

			httpRequestFactory = authenticator
					.getAuthorizedHttpRequestFactory();

			return httpRequestFactory;
		} catch (TwitterAuthenticationException e) {
			// TODO Auto-generated catch block
			throw new TwitterAuthenticationException(
					"Connection has not been established", e);
		}

	}

	public JSONObject executeQuery(String basePath,
			Map<PropertyKey, String> properties) throws TwitterException {

		try {

			// To prepare requestQuery
			String query = RequestUtil.prepareRequest(basePath, properties);

			// To prepare full url
			String queryUrl = QUERY_SERVER_URL.concat(query);

			httpRequestFactory = authenticate();
			URL url = new URL(queryUrl);
			GenericUrl genericUrl = new GenericUrl(url);

			HttpRequest request = httpRequestFactory
					.buildGetRequest(genericUrl);

			org.json.simple.JSONObject obj = (org.json.simple.JSONObject) JSONValue
					.parse(request.execute().parseAsString());
			return obj;
		} catch (Exception e) {
			throw new TwitterException("Something wrong", e);
		}
	}

}
