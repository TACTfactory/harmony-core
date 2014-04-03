package com.tactfactory.harmony.dependencies.android.sdk;

import java.util.ArrayList;
import java.util.List;

/**
 * Android Target.
 * 
 * Example :
 * 	id: 15 or "android-17"
 * 		Name: Android 4.2.2
 * 		Type: Platform
 * 		API level: 17
 * 		Revision: 2
 * 		Skins: WSVGA, WQVGA400, WVGA854, HVGA, WXGA800-7in, WQVGA432, WVGA800 (default), WXGA720, WXGA800, QVGA
 * 		Tag/ABIs : default/armeabi-v7a, default/mips, default/x86
 * 
 * OR
 * 
 * 	id: 19 or "Google Inc.:Google APIs:4"
 * 		Name: Google APIs
 * 		Type: Add-On
 * 		Vendor: Google Inc.
 * 		Revision: 2
 * 		Description: Android + Google APIs
 * 		Based on Android 1.6 (API level 4)
 * 		Libraries:
 * 			* com.google.android.maps (maps.jar)
 * 				API for Google Maps
 * 		Skins: HVGA, WVGA854, QVGA, WVGA800 (default)
 * 		Tag/ABIs : default/armeabi
 *
 *
 */
public class AndroidTarget {
	/** Constant for id. */
	private static final String ID = "id";
	/** Constant for or. */
	private static final String OR = "or";
	/** Constant for Name. */
	private static final String NAME = "Name";
	/** Constant for Type. */
	private static final String TYPE = "Type";
	/** Constant for Vendor. */
	private static final String VENDOR = "Vendor";
	/** Constant for Revision. */
	private static final String REVISION = "Revision";
	/** Constant for Description. */
	private static final String DESCRIPTION = "Description";
	/** Constant for Api Level. */
	private static final String API_LEVEL = "API level";
	/** Constant for Api Level. */
	private static final String BASED_ON = "Based on";
	/** Constant for Libraries. */
	private static final String LIBRARIES = "Libraries";
	/** Constant for Skins. */
	private static final String SKINS = "Skins";
	/** Constant for Tag/ABIs. */
	private static final String TAGS = "Tag/ABIs";
	
	/** ID. */
	private int id;
	/** Alternative ID. */
	private String alternativeId;
	/** Name. */
	private String name;
	/** Vendor. */
	private String vendor;
	/** Type. */
	private Type type;
	/** Description. */
	private String description;
	/** API Level. */
	private String apiLevel;
	/** Revision. */
	private String revision;
	/** Skins. */
	private List<String> skins;
	/** Tag/ABIs. */
	private List<String> tags;
	/** Libraries. */
	private List<String> libraries;

	private enum Type {
		ADD_ON("Add-On"),
		PLATFORM("Platform");
		
		private final String name;
		
		private Type(final String name) {
			this.name = name;
		}
		
		public static final Type parse(String name) {
			Type result = null;
			for (Type type : Type.values()) {
				if (type.name.equals(name)) {
					result = type;
					break;
				}
			}
			
			return result;
		}
	}
	
	public void parse(String stream) {
		String[] lines = stream.split("\n");
		for (String line : lines) {
			line = line.trim();
			if (line.startsWith(ID)) {
				this.parseIdAndAlternateId(line);
			} else if (line.startsWith(NAME)) {
				this.parseName(line);
			} else if (line.startsWith(TYPE)) {
				this.parseType(line);
			} else if (line.startsWith(VENDOR)) {
				this.parseVendor(line);
			} else if (line.startsWith(REVISION)) {
				this.parseRevision(line);
			} else if (line.startsWith(DESCRIPTION)) {
				this.parseDescription(line);
			} else if (line.startsWith(API_LEVEL)) {
				this.parseAPILevel(line);
			} else if (line.startsWith(BASED_ON)) {
				this.parseBasedOn(line);
			} else if (line.startsWith(LIBRARIES)) {
				this.parseLibraries(line);
			} else if (line.startsWith(SKINS)) {
				this.parseSkins(line);
			} else if (line.startsWith(TAGS)) {
				this.parseTags(line);
			}
		}
	}
	
	/**
	 * Parse the id and the alternate id from a line.
	 * 
	 * @param line The line to parse
	 */
	private void parseIdAndAlternateId(String line) {
		// ID
		int start = line.indexOf(':') + 1;
		int end = line.indexOf(OR);
		this.id = Integer.parseInt(line.substring(start, end).trim());
		
		// Alternate id
		start = line.indexOf('\"', end) + 1;
		end = line.lastIndexOf('\"');
		this.alternativeId = line.substring(start, end).trim();
	}

	/**
	 * Parse the name from a line.
	 * 
	 * @param line The line to parse
	 */
	private void parseName(String line) {
		int start = line.indexOf(':') + 1;
		this.name = line.substring(start).trim();
	}
	
	/**
	 * Parse the type from a line.
	 * 
	 * @param line The line to parse
	 */
	private void parseType(String line) {
		int start = line.indexOf(':') + 1;
		this.type = Type.parse(line.substring(start).trim());
	}
	
	/**
	 * Parse the vendor from a line.
	 * 
	 * @param line The line to parse
	 */
	private void parseVendor(String line) {
		int start = line.indexOf(':') + 1;
		this.vendor = line.substring(start).trim();
	}
	
	/**
	 * Parse the description from a line.
	 * 
	 * @param line The line to parse
	 */
	private void parseDescription(String line) {
		int start = line.indexOf(':') + 1;
		this.description = line.substring(start).trim();
	}
	
	/**
	 * Parse the revision from a line.
	 * 
	 * @param line The line to parse
	 */
	private void parseRevision(String line) {
		int start = line.indexOf(':') + 1;
		this.revision = line.substring(start).trim();
	}
	
	/**
	 * Parse the api level from a line.
	 * 
	 * @param line The line to parse
	 */
	private void parseAPILevel(String line) {
		int start = line.indexOf(':') + 1;
		this.apiLevel = line.substring(start).trim();
	}
	
	/**
	 * Parse the api level from a line.
	 * 
	 * @param line The line to parse
	 */
	private void parseBasedOn(String line) {
		int start = line.indexOf("API level") + "API level".length();
		int end = line.indexOf(")", start);
		this.apiLevel = line.substring(start, end).trim();
	}
	
	/**
	 * Parse the libraries from a line.
	 * 
	 * @param line The line to parse
	 */
	private void parseLibraries(String line) {
		this.libraries = new ArrayList<String>();
		// TODO : Parse libraries
	}
	
	/**
	 * Parse the skins from a line.
	 * 
	 * @param line The line to parse
	 */
	private void parseSkins(String line) {
		this.skins = new ArrayList<String>();
		int start = line.indexOf(':');
		String skinsString = line.substring(start).trim();
		String[] skins = skinsString.split(",");
		if (skins != null) {
			for (String skin : skins) {
				this.skins.add(skin.trim());
			}
		}
	}
	
	/**
	 * Parse the tags from a line.
	 * 
	 * @param line The line to parse
	 */
	private void parseTags(String line) {
		this.tags = new ArrayList<String>();
		int start = line.indexOf(':');
		String tagsString = line.substring(start).trim();
		String[] tags = tagsString.split(",");
		if (tags != null) {
			for (String tag : tags) {
				this.tags.add(tag.trim());
			}
		}
	}

	/**
	 * @return the id
	 */
	public final int getId() {
		return id;
	}

	/**
	 * @return the alternativeId
	 */
	public final String getAlternativeId() {
		return alternativeId;
	}

	/**
	 * @return the name
	 */
	public final String getName() {
		return name;
	}

	/**
	 * @return the vendor
	 */
	public final String getVendor() {
		return vendor;
	}

	/**
	 * @return the type
	 */
	public final Type getType() {
		return type;
	}

	/**
	 * @return the description
	 */
	public final String getDescription() {
		return description;
	}

	/**
	 * @return the apiLevel
	 */
	public final String getApiLevel() {
		return apiLevel;
	}

	/**
	 * @return the revision
	 */
	public final String getRevision() {
		return revision;
	}

	/**
	 * @return the skins
	 */
	public final List<String> getSkins() {
		return skins;
	}

	/**
	 * @return the tags
	 */
	public final List<String> getTags() {
		return tags;
	}

	/**
	 * @return the libraries
	 */
	public final List<String> getLibraries() {
		return libraries;
	}
}
