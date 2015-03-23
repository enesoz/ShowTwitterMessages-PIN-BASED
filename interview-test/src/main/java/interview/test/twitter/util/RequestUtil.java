package interview.test.twitter.util;

import interview.test.twitter.property.PropertyKey;

import java.util.Map;

import com.google.api.client.repackaged.com.google.common.base.Joiner;

// TODO: Auto-generated Javadoc
/**
 * The Class RequestUtil.
 */
public class RequestUtil {

	/**
	 * Prepare request.
	 * 
	 * @param baseUrl
	 *            the base url
	 * @param properties
	 *            the properties
	 * @return the string
	 */
	public static String prepareRequest(final String baseUrl,
			Map<PropertyKey, String> properties) {

		StringBuffer request = new StringBuffer();
		request.append(baseUrl);

		// to add all properties to request string
		Joiner.MapJoiner mapJoiner = Joiner.on("&").withKeyValueSeparator("=");
		String param = mapJoiner.join(properties);

		request.append(param);

		return request.toString();
	}
}
