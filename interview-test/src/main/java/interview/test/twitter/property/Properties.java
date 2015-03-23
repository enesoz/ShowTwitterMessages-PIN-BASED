package interview.test.twitter.property;

import java.util.HashMap;
import java.util.Map;

/**
 * The Class Properties.
 */
public class Properties {

	/** The property list. */
	private Map<PropertyKey, String> propertyList;

	/**
	 * Instantiates a new properties.
	 */
	public Properties() {
		this.propertyList = new HashMap<PropertyKey, String>();
	}

	/**
	 * Adds the property.
	 * 
	 * @param key
	 *            the key
	 * @param value
	 *            the value
	 */
	public void addProperty(PropertyKey key, String value) {
		this.propertyList.put(key, value);
	}

	/**
	 * Gets the property list.
	 * 
	 * @return the property list
	 */
	public Map<PropertyKey, String> getPropertyList() {
		return propertyList;
	}

	/**
	 * The Enum MessageResultType.
	 */
	public enum MessageResultType {

		/** The mixed. */
		mixed,
		/** The recent. */
		recent,
		/** The popular. */
		popular
	}

}
