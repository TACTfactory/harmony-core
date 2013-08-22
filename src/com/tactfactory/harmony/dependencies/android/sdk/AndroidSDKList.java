package com.tactfactory.harmony.dependencies.android.sdk;

import java.util.ArrayList;

/**
 * Android SDK dependencies list.
 */
public class AndroidSDKList {
	/** SDKListItems list. */
	private final ArrayList<AndroidSDKListItem> items =
			new ArrayList<AndroidSDKListItem>(65);
	
	/**
	 * Parse the given string to build the list.
	 * @param listString The string to parse
	 */
	public void parseString(String listString) {
		String[] itemStrings = listString.split("-{3,}\n");
		for (int i = 1; i < itemStrings.length; i++) {
			String itemString = itemStrings[i];
			AndroidSDKListItem item = new AndroidSDKListItem();
			item.parseString(itemString);
			this.items.add(item);
		}
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		for (AndroidSDKListItem item : this.items) {
			builder.append(item.toString());
			builder.append("\n---------------\n");
		}
		return builder.toString();
	}
	
	/**
	 * Find an id in the list thanks to the given name.
	 * @param name The name to look for
	 * @return The id of the item. Null if nothing found
	 */
	public String getIdByName(String name) {
		String result = null;
		for (AndroidSDKListItem item : this.items) {
			if (item.getAlternativeId().equals(name)) {
				result = String.valueOf(item.getId());
				break;
			}
		}
		return result;
	}
	
	/**
	 * Find an id in the list thanks to the given name.
	 * @param name The name to look for
	 * @return The list of ids found. Empty list if none found.
	 */
	public ArrayList<String> getIdsLikeName(String name) {
		ArrayList<String> result = new ArrayList<String>();
		for (AndroidSDKListItem item : this.items) {
			if (item.getAlternativeId().contains(name)) {
				result.add(String.valueOf(item.getId()));
			}
		}
		return result;
	}
}
