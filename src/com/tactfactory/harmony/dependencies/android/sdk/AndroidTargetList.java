package com.tactfactory.harmony.dependencies.android.sdk;

import java.util.ArrayList;

public class AndroidTargetList {
	/** SDKListItems list. */
	private final ArrayList<AndroidTarget> items =
			new ArrayList<AndroidTarget>();
	
	/**
	 * Parse the given string to build the list.
	 * @param listString The string to parse
	 */
	public final void parseString(final String listString) {
		final String[] itemStrings = listString.split("-{3,}\n");
		for (int i = 1; i < itemStrings.length; i++) {
			final String itemString = itemStrings[i];
			final AndroidTarget item = new AndroidTarget();
			item.parse(itemString);
			this.items.add(item);
		}
	}
	
	@Override
	public final String toString() {
		final StringBuilder builder = new StringBuilder();
		for (AndroidTarget item : this.items) {
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
	public final String getIdByName(final String name) {
		String result = null;
		for (AndroidTarget item : this.items) {
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
	public final ArrayList<String> getIdsLikeName(final String name) {
		final ArrayList<String> result = new ArrayList<String>();
		for (AndroidTarget item : this.items) {
			if (item.getAlternativeId().contains(name)) {
				result.add(String.valueOf(item.getId()));
			}
		}
		return result;
	}
	
	/**
	 * Gets the latest target corresponding to the corresponding vendor.
	 * 
	 * @param vendor The vendor. Can be null for android default targets
	 * 
	 * @return The latest target found. Null if none found.
	 */
	public final AndroidTarget getLatestTarget(String vendor) {
		AndroidTarget result = null;
		
		int maxVersion = 0;
		
		// If a vendor was specified, we try to find it in the target list.
		if (vendor != null) {
			boolean vendorFound = false;
			for (AndroidTarget target : this.items) {
				if (target.getVendor().equals(vendor)) {
					vendorFound = true;
					break;
				}
			}
			
			// If vendor is not found, set vendor to default (null)
			if (!vendorFound) {
				vendor = null;
			}
		}
		
		for (AndroidTarget target : this.items) {
			int version = Integer.parseInt(target.getApiLevel());
			if (version > maxVersion
					&& ((vendor == null && target.getVendor() == null)
							|| vendor.equals(target.getVendor()))) {
				maxVersion = version;
				result = target;
			}
		}
		
		return result;
	}
}
