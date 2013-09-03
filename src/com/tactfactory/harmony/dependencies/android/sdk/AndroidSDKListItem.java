package com.tactfactory.harmony.dependencies.android.sdk;

/**
 * Android SDK dependencies list item.
 * Example :
 * id: 1 or "platform-tools"
 *    Type: PlatformTool
 *    Desc: Android SDK Platform-tools, revision 18.0.1
 *
 */
public class AndroidSDKListItem {
	/** ID. */
	private int id;
	/** Alternative ID / Name. */
	private String alternativeId;
	/** Type. */
	private String type;
	/** Description. */
	private String description;

	/**
	 * Constructor.
	 */
	public AndroidSDKListItem() {
		this(0, null, null, null);
	}

	/**
	 * Constructor.
	 * @param id ID
	 * @param alternativeId Alternative id
	 * @param type Type
	 * @param description Description
	 */
	public AndroidSDKListItem(
			int id,
			String alternativeId,
			String type,
			String description) {
		
		this.id = id;
		this.alternativeId = alternativeId;
		this.type = type;
		this.description = description;
	}
	
	/**
	 * Parse the given String to build this item.
	 * @param listItemString The string to parse
	 */
	public void parseString(String listItemString) {
		int start;
		int end;
		
		// Find id.
		start = listItemString.indexOf(':') + 1;
		end = listItemString.indexOf("or", start);
		this.id = Integer.parseInt(listItemString.substring(start, end).trim());
		
		// Find alternate id
		start = listItemString.indexOf('\"') + 1;
		end = listItemString.indexOf('\"', start);
		this.alternativeId = listItemString.substring(start, end).trim();
		
		start = listItemString.indexOf("Type:") + 5;
		end = listItemString.indexOf('\n', start);
		this.type = listItemString.substring(start, end).trim();
		
		start = listItemString.indexOf("Desc:") + "Desc:".length();
		end = listItemString.indexOf('\n', start);
		this.description = listItemString.substring(start, end).trim();
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Id: ");
		builder.append(this.id);
		builder.append('\n');
		
		builder.append("Name: ");
		builder.append(this.alternativeId);
		builder.append('\n');
		
		builder.append("Type: ");
		builder.append(this.type);
		builder.append('\n');
		
		builder.append("Description: ");
		builder.append(this.description);
		builder.append('\n');
		return builder.toString(); 
	}

	/**
	 * Return id.
	 * @return ID.
	 */
	public int getId() {
		return this.id;
	}

	/**
	 * Return alternative id.
	 * @return Alternative id.
	 */
	public String getAlternativeId() {
		return this.alternativeId;
	}

	/**
	 * Return type.
	 * @return Type
	 */
	public String getType() {
		return this.type;
	}

	/**
	 * Return description.
	 * @return Description
	 */
	public String getDescription() {
		return this.description;
	}
}