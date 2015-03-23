package interview.test.twitter.response;

import interview.test.twitter.models.Author;
import interview.test.twitter.models.TwitterMessage;

import java.text.Collator;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import org.apache.http.impl.cookie.DateParseException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 * The Class ResponseHandler.
 */
public class ResponseHandler {

	/**
	 * Gets the response as twitter message lists.
	 * 
	 * @param obj
	 *            the obj
	 * @return the response as twitter message lists
	 * @throws DateParseException
	 *             the date parse exception
	 * @throws ParseException
	 *             the parse exception
	 */
	public static List<TwitterMessage> getResponseAsTwitterMessageLists(
			JSONObject obj) throws DateParseException, ParseException {

		List<TwitterMessage> resultList = new ArrayList<TwitterMessage>();

		JSONArray array = (JSONArray) obj.get("statuses");

		@SuppressWarnings("unchecked")
		Iterator<JSONObject> arrayIterator = array.iterator();

		while (arrayIterator.hasNext()) {
			JSONObject statuse = arrayIterator.next();
			TwitterMessage twitterMessage = new TwitterMessage();
			twitterMessage.setMessageId((Long) statuse.get("id"));
			twitterMessage.setText((String) statuse.get("text"));
			twitterMessage.setCreateDate(statuse.get("created_at").toString());

			JSONObject authorObj = (JSONObject) statuse.get("user");
			Author author = new Author();
			author.setUserId((Long) authorObj.get("id"));
			author.setCreateDate(authorObj.get("created_at").toString());
			author.setScreenName((String) authorObj.get("screen_name"));
			author.setName((String) authorObj.get("name"));

			twitterMessage.setAuthor(author);

			resultList.add(twitterMessage);

		}
		// Sort List according to author name ASC
		Collections.sort(resultList, new Comparator<TwitterMessage>() {

			public int compare(TwitterMessage o1, TwitterMessage o2) {
				Collator collator = Collator.getInstance(Locale.getDefault());
				return collator.compare(o1.getAuthor().getName(), o2
						.getAuthor().getName());
			}
		});
		return resultList;
	}
}
