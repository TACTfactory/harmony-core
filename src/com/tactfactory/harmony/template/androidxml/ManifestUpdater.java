/**
 * This file is part of the Harmony package.
 *
 * (c) Mickael Gaillard <mickael.gaillard@tactfactory.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
package com.tactfactory.harmony.template.androidxml;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.jdom2.Element;
import org.jdom2.Namespace;

import com.tactfactory.harmony.meta.ApplicationMetadata;
import com.tactfactory.harmony.plateforme.BaseAdapter;
import com.tactfactory.harmony.template.androidxml.manifest.ManifestActivity;
import com.tactfactory.harmony.template.androidxml.manifest.ManifestConstants;
import com.tactfactory.harmony.template.androidxml.manifest.ManifestIntentFilter;
import com.tactfactory.harmony.template.androidxml.manifest.ManifestIntentFilter.ManifestData;
import com.tactfactory.harmony.utils.ConsoleUtils;

/**
 * Manifest update.
 */
public class ManifestUpdater extends XmlManager {
	
	/**
	 * Comparator used to order elements by alphabetical order.
	 */
	private final static Comparator<Element> ABC_COMPARATOR =
			new Comparator<Element>() {
				@Override
				public int compare(final Element o1, final Element o2) {
					return o1.getName().compareToIgnoreCase(o2.getName());
				}
			};
	
	/**
	 * Constructor.
	 * @param adapter The adapter
	 */
	public ManifestUpdater(final BaseAdapter adapter) {
		super(adapter, adapter.getManifestPathFile());
	}
	
	/**
	 * Update Android Manifest.
	 * @param classF The class file name
	 * @param entityName the entity for which to update the manifest for.
	 */
	public void addActivity(
			final String projectNamespace,
			final String classF,
			final String entityName) {
		this.addActivity(
				projectNamespace,
				classF,
				entityName,
				entityName.toLowerCase());
	}
	
	public void addActivity(ManifestActivity activity) {
		ConsoleUtils.displayDebug(String.format(
				"Add activity %s to manifest.",
				activity.getName()));
		
		// Load Root element
		final Element rootNode = this.getDocument().getRootElement();

		// Load Name space (required for manipulate attributes)
		final Namespace ns = rootNode.getNamespace(
				ManifestConstants.NAMESPACE_ANDROID);
		
		// Find Application Node
		Element findActivity = null;

		// Find a element
		final Element applicationNode = rootNode.getChild(
				ManifestConstants.ELEMENT_APPLICATION);
		
		if (applicationNode != null) {	
			findActivity = this.findActivityNamed(activity.getName(), ns);
			
			// If not found Node, create it
			if (findActivity == null) {
				applicationNode.addContent(activity.toElement(ns));

				// Clean manifest
				applicationNode.sortChildren(ABC_COMPARATOR);
			}
		}

	}
	
	/**
	 * Update Android Manifest.
	 * @param classF The class file name
	 * @param entityName the entity for which to update the manifest for.
	 */
	public void addActivity(
			final String projectNamespace,
			final String classF,
			final String viewName,
			final String viewPackage) {
		
		final String classFile = viewName + classF;
		final String pathRelatif = String.format(".%s.%s.%s",
				this.getAdapter().getController(),
				viewPackage,
				classFile);
		
		ManifestActivity activity = new ManifestActivity();
		activity.setName(pathRelatif);
		activity.setLabel("@string/app_name");
		activity.setExported(false);
		
		ManifestIntentFilter intentFilter = new ManifestIntentFilter();
		activity.addIntentFilter(intentFilter);
		intentFilter.setCategoryName("android.intent.category.DEFAULT");
		
		String action = ManifestConstants.ACTION_VIEW;
		final StringBuffer data = new StringBuffer();
		if (pathRelatif.matches(".*List.*")) {
			data.append("vnd.android.cursor.collection/");
		} else {
			data.append("vnd.android.cursor.item/");

			if (pathRelatif.matches(".*Edit.*")) {
				action = ManifestConstants.ACTION_EDIT;
			} else

			if (pathRelatif.matches(".*Create.*")) {
				action = ManifestConstants.ACTION_INSERT;
			}
		}
		data.append(projectNamespace.replace('/', '.'));
		data.append('.');
		data.append(viewName);
		
		ManifestData manifestData = intentFilter.new ManifestData();
		manifestData.setMimeType(data.toString());
		
		intentFilter.setActionName(action);
		intentFilter.addData(manifestData);
		
		this.addActivity(activity);
	}
	
	/**
	 * Get the list of all the launcher activities names.
	 * @return The launcher activities names
	 */
	public List<String> getLauncherActivitiesNames() {
		// Load Root element
		final Element rootNode = this.getDocument().getRootElement();

		// Load Name space (required for manipulate attributes)
		final Namespace ns = rootNode.getNamespace(
				ManifestConstants.NAMESPACE_ANDROID);
		
		List<String> result = new ArrayList<String>();
		List<Element> activities = this.getActivities();
		if (activities != null) {
			for (Element activity : activities) {
				List<Element> intentFilters = activity.getChildren(
						ManifestConstants.ELEMENT_INTENT_FILTER);
				
				if (intentFilters != null) {
					for (Element intentFilter : intentFilters) {
						List<Element> categories = intentFilter.getChildren(
								ManifestConstants.ELEMENT_CATEGORY);
						
						if (categories != null) {
							for (Element category : categories) {
								String categoryName = 
									category.getAttributeValue(
										ManifestConstants.ATTRIBUTE_NAME, ns);
								if ("android.intent.category.LAUNCHER".equals(
										categoryName)) {
									result.add(activity.getAttributeValue(
											ManifestConstants.ATTRIBUTE_NAME,
											ns));
								}
							}
						}
					}
				}
			}
		}
		return result;
	}
	
	/**
	 * Remove the launcher category from the intent filter 
	 * of the given activity.
	 * 
	 * @param activityName The activity to remove the launcher category from
	 */
	public void removeLauncherIntentFilter(String activityName) {
		// Load Root element
		final Element rootNode = this.getDocument().getRootElement();

		// Load Name space (required for manipulate attributes)
		final Namespace ns = rootNode.getNamespace(
				ManifestConstants.NAMESPACE_ANDROID);
		
		Element activity = this.findActivityNamed(activityName, ns);
		Element foundCategory = null;
		List<Element> intentFilters = activity.getChildren(
				ManifestConstants.ELEMENT_INTENT_FILTER);
		
		if (intentFilters != null) {
			for (Element intentFilter : intentFilters) {
				List<Element> categories = intentFilter.getChildren(
						ManifestConstants.ELEMENT_CATEGORY);
				
				if (categories != null) {
					for (Element category : categories) {
						String categoryName = 
							category.getAttributeValue(
								ManifestConstants.ATTRIBUTE_NAME, ns);
						if ("android.intent.category.LAUNCHER".equals(
								categoryName)) {
							foundCategory = category;
						}
					}
					
					if (foundCategory != null) {
						intentFilter.removeContent(foundCategory);
					}
				}
			}
		}	
	}
	
	/**
	 * Get the activities.
	 * @return The list of activities.
	 */
	private List<Element> getActivities() {
		List<Element> result = null;
		Element appNode = 
				this.getDocument().getRootElement().getChild(
						ManifestConstants.ELEMENT_APPLICATION);
		result = appNode.getChildren(
				ManifestConstants.ELEMENT_ACTIVITY);
		return result;
	}
	
	/**
	 * Find the activity named "name"
	 * @param name The name
	 * @param namespace The namespace
	 * @return The found activity. null if none found.
	 */
	private Element findActivityNamed(
			String name,
			Namespace namespace) {
		Element result = null;
		List<Element> activities = this.getActivities();
		
		for (final Element activity : activities) {
			// Load attribute value
			if (activity.hasAttributes()
					&& activity.getAttributeValue(
							ManifestConstants.ATTRIBUTE_NAME,
							namespace).equals(name)) {
				result = activity;
				break;
			}
		}
		
		return result;
	}
	@Override
	protected Element getDefaultRoot() {
		Namespace androidNs = Namespace.getNamespace(
				"android", 
				"http://schemas.android.com/apk/res/android"); 
		Element rootElement = new Element("manifest");
		rootElement.addNamespaceDeclaration(androidNs);
		rootElement.setAttribute(
				"package",
				ApplicationMetadata.INSTANCE.getProjectNameSpace());
		
		rootElement.setAttribute("versionCode",
				"1",
				androidNs);
		
		rootElement.setAttribute("versionName",
				"@string/app_version",
				androidNs);
		return rootElement;
	}	
	
	/**
	 * Add a permission to manifest.
	 * @param permission The permission name
	 */
	public void addPermission(final String permission) {
		// Load Root element
		final Element rootNode = this.getDocument().getRootElement();

		// Load Name space (required for manipulate attributes)
		final Namespace ns = rootNode.getNamespace(
				ManifestConstants.NAMESPACE_ANDROID);
		boolean setPerm = true;
		for (Element elem : rootNode.getChildren(
				ManifestConstants.ELEMENT_PERMISSION)) {
			if (elem.getAttributeValue(
					ManifestConstants.ATTRIBUTE_NAME,
					ns).equals(permission)) {
				setPerm = false;
				break;
			}
		}
		
		if (setPerm) {
			final Element permissionElem = new Element(
					ManifestConstants.ELEMENT_PERMISSION);
			
			permissionElem.setAttribute(
					ManifestConstants.ATTRIBUTE_NAME, permission, ns);
			
			rootNode.addContent(2, permissionElem);
		}
	}
	
	/**
	 * Sets the application-level theme.
	 * @param theme The theme to set
	 */
	public void setApplicationTheme(String theme) {
		final Element rootNode = this.getDocument().getRootElement();
		final Namespace ns = rootNode.getNamespace(
				ManifestConstants.NAMESPACE_ANDROID);
		final Element appElem = rootNode.getChild(
				ManifestConstants.ELEMENT_APPLICATION);
		appElem.setAttribute(ManifestConstants.ATTRIBUTE_THEME, theme, ns);
	}
	
	/**
	 * Adds a service to the manifest. (exported default to false)
	 * @param serviceName The service name
	 * @param label The service label
	 */
	public void addService(final String serviceName, final String label) {
		this.addService(serviceName, label, false);
	}
	
	/**
	 * Adds a service to the manifest.
	 * @param serviceName The service name
	 * @param label The service label
	 * @param exported If the service is exported
	 */
	public void addService(
			final String serviceName,
			final String label,
			final boolean exported) {
		
		// Load Root element
		final Element rootNode = this.getDocument().getRootElement();

		// Load Name space (required for manipulate attributes)
		final Namespace ns = rootNode.getNamespace(
				ManifestConstants.NAMESPACE_ANDROID);
		final Element appElem = rootNode.getChild(
				ManifestConstants.ELEMENT_APPLICATION);
		boolean setService = true;
		for (Element elem : appElem.getChildren(
				ManifestConstants.ELEMENT_SERVICE)) {
			if (elem.getAttributeValue(
					ManifestConstants.ATTRIBUTE_NAME, ns).equals(serviceName)) {
				setService = false;
				break;
			}
		}
		
		if (setService) {
			final Element permissionElem = new Element(
					ManifestConstants.ELEMENT_SERVICE);
			permissionElem.setAttribute(
					ManifestConstants.ATTRIBUTE_NAME, serviceName, ns);
			permissionElem.setAttribute(
					ManifestConstants.ATTRIBUTE_LABEL, label, ns);
			permissionElem.setAttribute(
					ManifestConstants.ATTRIBUTE_EXPORTED,
					String.valueOf(exported),
					ns);
			appElem.addContent(permissionElem);
		}
	}
	
	/**
	 * Adds a content provider to the manifest.xml (exported default to false)
	 * @param name The name of the provider
	 * @param label The label of the provider
	 * @param authorities The authorities of the provider
	 * @param description The description of the provider
	 */
	public void addProvider(final String name,
			final String label,
			final String authorities,
			final String description) {
		this.addProvider(name, label, authorities, description, false);		
	}
	
	/**
	 * Adds a content provider to the manifest.xml
	 * @param name The name of the provider
	 * @param label The label of the provider
	 * @param authorities The authorities of the provider
	 * @param description The description of the provider
	 * @param exported The exported state of the provider
	 */
	public void addProvider(final String name,
			final String label,
			final String authorities,
			final String description,
			final boolean exported) {
		// Load Root element
		final Element rootNode = this.getDocument().getRootElement();

		// Load Name space (required for manipulate attributes)
		final Namespace ns = rootNode.getNamespace(
				ManifestConstants.NAMESPACE_ANDROID);
		final Element appElem = rootNode.getChild(
				ManifestConstants.ELEMENT_APPLICATION);
		boolean setProvider = true;
		for (Element elem : appElem.getChildren(
				ManifestConstants.ELEMENT_PROVIDER)) {
			if (elem.getAttributeValue(
					ManifestConstants.ATTRIBUTE_NAME, ns).equals(name)) {
				setProvider = false;
				break;
			}
		}
		
		if (setProvider) {
			final Element providerElem = new Element(
					ManifestConstants.ELEMENT_PROVIDER);
			providerElem.setAttribute(
					ManifestConstants.ATTRIBUTE_NAME, name, ns);
			providerElem.setAttribute(
					ManifestConstants.ATTRIBUTE_AUTHORITIES,
					authorities,
					ns);
			providerElem.setAttribute(
					ManifestConstants.ATTRIBUTE_LABEL,
					label, 
					ns);
			providerElem.setAttribute(
					ManifestConstants.ATTRIBUTE_DESCRIPTION,
					description, 
					ns);
			providerElem.setAttribute(
					ManifestConstants.ATTRIBUTE_EXPORTED,
					String.valueOf(exported),
					ns);
			

			appElem.addContent(providerElem);
		}
	}
	
	/**
	 * Get all services from the manifest.
	 * @return List of service name.
	 */
	public List<String> getServices() {
		List<String> result = new ArrayList<String>();
		
		// Load Root element
		final Element rootNode = this.getDocument().getRootElement();
		final Element appElem = rootNode.getChild(
				ManifestConstants.ELEMENT_APPLICATION);

		// Load Name space (required for manipulate attributes)
		final Namespace ns = rootNode.getNamespace(
				ManifestConstants.NAMESPACE_ANDROID);
		
		for (Element elem 
				: appElem.getChildren(ManifestConstants.ELEMENT_SERVICE)) {
			result.add(elem.getAttributeValue(
					ManifestConstants.ATTRIBUTE_NAME, ns));
		}
		
		return result;
	}
	
	/**
	 * Various available permissions for Android.
	 */
	public static class Permissions {
		// CHECKSTYLE: OFF
		public static final String ACCESS_CHECKIN_PROPERTIES = "android.permission.ACCESS_CHECKIN_PROPERTIES";
		public static final String ACCESS_COARSE_LOCATION = "android.permission.ACCESS_COARSE_LOCATION";
		public static final String ACCESS_FINE_LOCATION = "android.permission.ACCESS_FINE_LOCATION";
		public static final String ACCESS_LOCATION_EXTRA_COMMANDS = "android.permission.ACCESS_LOCATION_EXTRA_COMMANDS";
		public static final String ACCESS_MOCK_LOCATION = "android.permission.ACCESS_MOCK_LOCATION";
		public static final String ACCESS_NETWORK_STATE = "android.permission.ACCESS_NETWORK_STATE";
		public static final String ACCESS_SURFACE_FLINGER = "android.permission.ACCESS_SURFACE_FLINGER";
		public static final String ACCESS_WIFI_STATE = "android.permission.ACCESS_WIFI_STATE";
		public static final String ACCOUNT_MANAGER = "android.permission.ACCOUNT_MANAGER";
		public static final String ADD_VOICEMAIL = "com.android.voicemail.permission.ADD_VOICEMAIL";
		public static final String AUTHENTICATE_ACCOUNTS = "android.permission.AUTHENTICATE_ACCOUNTS";
		public static final String BATTERY_STATS = "android.permission.BATTERY_STATS";
		public static final String BIND_ACCESSIBILITY_SERVICE = "android.permission.BIND_ACCESSIBILITY_SERVICE";
		public static final String BIND_APPWIDGET = "android.permission.BIND_APPWIDGET";
		public static final String BIND_DEVICE_ADMIN = "android.permission.BIND_DEVICE_ADMIN";
		public static final String BIND_INPUT_METHOD = "android.permission.BIND_INPUT_METHOD";
		public static final String BIND_NOTIFICATION_LISTENER_SERVICE = "android.permission.BIND_NOTIFICATION_LISTENER_SERVICE";
		public static final String BIND_REMOTEVIEWS = "android.permission.BIND_REMOTEVIEWS";
		public static final String BIND_TEXT_SERVICE = "android.permission.BIND_TEXT_SERVICE";
		public static final String BIND_VPN_SERVICE = "android.permission.BIND_VPN_SERVICE";
		public static final String BIND_WALLPAPER = "android.permission.BIND_WALLPAPER";
		public static final String BLUETOOTH = "android.permission.BLUETOOTH";
		public static final String BLUETOOTH_ADMIN = "android.permission.BLUETOOTH_ADMIN";
		public static final String BRICK = "android.permission.BRICK";
		public static final String BROADCAST_PACKAGE_REMOVED = "android.permission.BROADCAST_PACKAGE_REMOVED";
		public static final String BROADCAST_SMS = "android.permission.BROADCAST_SMS";
		public static final String BROADCAST_STICKY = "android.permission.BROADCAST_STICKY";
		public static final String BROADCAST_WAP_PUSH = "android.permission.BROADCAST_WAP_PUSH";
		public static final String CALL_PHONE = "android.permission.CALL_PHONE";
		public static final String CALL_PRIVILEGED = "android.permission.CALL_PRIVILEGED";
		public static final String CAMERA = "android.permission.CAMERA";
		public static final String CHANGE_COMPONENT_ENABLED_STATE = "android.permission.CHANGE_COMPONENT_ENABLED_STATE";
		public static final String CHANGE_CONFIGURATION = "android.permission.CHANGE_CONFIGURATION";
		public static final String CHANGE_NETWORK_STATE = "android.permission.CHANGE_NETWORK_STATE";
		public static final String CHANGE_WIFI_MULTICAST_STATE = "android.permission.CHANGE_WIFI_MULTICAST_STATE";
		public static final String CHANGE_WIFI_STATE = "android.permission.CHANGE_WIFI_STATE";
		public static final String CLEAR_APP_CACHE = "android.permission.CLEAR_APP_CACHE";
		public static final String CLEAR_APP_USER_DATA = "android.permission.CLEAR_APP_USER_DATA";
		public static final String CONTROL_LOCATION_UPDATES = "android.permission.CONTROL_LOCATION_UPDATES";
		public static final String DELETE_CACHE_FILES = "android.permission.DELETE_CACHE_FILES";
		public static final String DELETE_PACKAGES = "android.permission.DELETE_PACKAGES";
		public static final String DEVICE_POWER = "android.permission.DEVICE_POWER";
		public static final String DIAGNOSTIC = "android.permission.DIAGNOSTIC";
		public static final String DISABLE_KEYGUARD = "android.permission.DISABLE_KEYGUARD";
		public static final String DUMP = "android.permission.DUMP";
		public static final String EXPAND_STATUS_BAR = "android.permission.EXPAND_STATUS_BAR";
		public static final String FACTORY_TEST = "android.permission.FACTORY_TEST";
		public static final String FLASHLIGHT = "android.permission.FLASHLIGHT";
		public static final String FORCE_BACK = "android.permission.FORCE_BACK";
		public static final String GET_ACCOUNTS = "android.permission.GET_ACCOUNTS";
		public static final String GET_PACKAGE_SIZE = "android.permission.GET_PACKAGE_SIZE";
		public static final String GET_TASKS = "android.permission.GET_TASKS";
		public static final String GET_TOP_ACTIVITY_INFO = "android.permission.GET_TOP_ACTIVITY_INFO";
		public static final String GLOBAL_SEARCH = "android.permission.GLOBAL_SEARCH";
		public static final String HARDWARE_TEST = "android.permission.HARDWARE_TEST";
		public static final String INJECT_EVENTS = "android.permission.INJECT_EVENTS";
		public static final String INSTALL_LOCATION_PROVIDER = "android.permission.INSTALL_LOCATION_PROVIDER";
		public static final String INSTALL_PACKAGES = "android.permission.INSTALL_PACKAGES";
		public static final String INTERNAL_SYSTEM_WINDOW = "android.permission.INTERNAL_SYSTEM_WINDOW";
		public static final String INTERNET = "android.permission.INTERNET";
		public static final String KILL_BACKGROUND_PROCESSES = "android.permission.KILL_BACKGROUND_PROCESSES";
		public static final String LOCATION_HARDWARE = "android.permission.LOCATION_HARDWARE";
		public static final String MANAGE_ACCOUNTS = "android.permission.MANAGE_ACCOUNTS";
		public static final String MANAGE_APP_TOKENS = "android.permission.MANAGE_APP_TOKENS";
		public static final String MASTER_CLEAR = "android.permission.MASTER_CLEAR";
		public static final String MODIFY_AUDIO_SETTINGS = "android.permission.MODIFY_AUDIO_SETTINGS";
		public static final String MODIFY_PHONE_STATE = "android.permission.MODIFY_PHONE_STATE";
		public static final String MOUNT_FORMAT_FILESYSTEMS = "android.permission.MOUNT_FORMAT_FILESYSTEMS";
		public static final String MOUNT_UNMOUNT_FILESYSTEMS = "android.permission.MOUNT_UNMOUNT_FILESYSTEMS";
		public static final String NFC = "android.permission.NFC";
		public static final String PERSISTENT_ACTIVITY = "android.permission.PERSISTENT_ACTIVITY";
		public static final String PROCESS_OUTGOING_CALLS = "android.permission.PROCESS_OUTGOING_CALLS";
		public static final String READ_CALENDAR = "android.permission.READ_CALENDAR";
		public static final String READ_CALL_LOG = "android.permission.READ_CALL_LOG";
		public static final String READ_CONTACTS = "android.permission.READ_CONTACTS";
		public static final String READ_EXTERNAL_STORAGE = "android.permission.READ_EXTERNAL_STORAGE";
		public static final String READ_FRAME_BUFFER = "android.permission.READ_FRAME_BUFFER";
		public static final String READ_HISTORY_BOOKMARKS = "com.android.browser.permission.READ_HISTORY_BOOKMARKS";
		public static final String READ_INPUT_STATE = "android.permission.READ_INPUT_STATE";
		public static final String READ_LOGS = "android.permission.READ_LOGS";
		public static final String READ_PHONE_STATE = "android.permission.READ_PHONE_STATE";
		public static final String READ_PROFILE = "android.permission.READ_PROFILE";
		public static final String READ_SMS = "android.permission.READ_SMS";
		public static final String READ_SOCIAL_STREAM = "android.permission.READ_SOCIAL_STREAM";
		public static final String READ_SYNC_SETTINGS = "android.permission.READ_SYNC_SETTINGS";
		public static final String READ_SYNC_STATS = "android.permission.READ_SYNC_STATS";
		public static final String READ_USER_DICTIONARY = "android.permission.READ_USER_DICTIONARY";
		public static final String REBOOT = "android.permission.REBOOT";
		public static final String RECEIVE_BOOT_COMPLETED = "android.permission.RECEIVE_BOOT_COMPLETED";
		public static final String RECEIVE_MMS = "android.permission.RECEIVE_MMS";
		public static final String RECEIVE_SMS = "android.permission.RECEIVE_SMS";
		public static final String RECEIVE_WAP_PUSH = "android.permission.RECEIVE_WAP_PUSH";
		public static final String RECORD_AUDIO = "android.permission.RECORD_AUDIO";
		public static final String REORDER_TASKS = "android.permission.REORDER_TASKS";
		public static final String RESTART_PACKAGES = "android.permission.RESTART_PACKAGES";
		public static final String SEND_RESPOND_VIA_MESSAGE = "android.permission.SEND_RESPOND_VIA_MESSAGE";
		public static final String SEND_SMS = "android.permission.SEND_SMS";
		public static final String SET_ACTIVITY_WATCHER = "android.permission.SET_ACTIVITY_WATCHER";
		public static final String SET_ALARM = "com.android.alarm.permission.SET_ALARM";
		public static final String SET_ALWAYS_FINISH = "android.permission.SET_ALWAYS_FINISH";
		public static final String SET_ANIMATION_SCALE = "android.permission.SET_ANIMATION_SCALE";
		public static final String SET_DEBUG_APP = "android.permission.SET_DEBUG_APP";
		public static final String SET_ORIENTATION = "android.permission.SET_ORIENTATION";
		public static final String SET_POINTER_SPEED = "android.permission.SET_POINTER_SPEED";
		public static final String SET_PREFERRED_APPLICATIONS = "android.permission.SET_PREFERRED_APPLICATIONS";
		public static final String SET_PROCESS_LIMIT = "android.permission.SET_PROCESS_LIMIT";
		public static final String SET_TIME = "android.permission.SET_TIME";
		public static final String SET_TIME_ZONE = "android.permission.SET_TIME_ZONE";
		public static final String SET_WALLPAPER = "android.permission.SET_WALLPAPER";
		public static final String SET_WALLPAPER_HINTS = "android.permission.SET_WALLPAPER_HINTS";
		public static final String SIGNAL_PERSISTENT_PROCESSES = "android.permission.SIGNAL_PERSISTENT_PROCESSES";
		public static final String STATUS_BAR = "android.permission.STATUS_BAR";
		public static final String SUBSCRIBED_FEEDS_READ = "android.permission.SUBSCRIBED_FEEDS_READ";
		public static final String SUBSCRIBED_FEEDS_WRITE = "android.permission.SUBSCRIBED_FEEDS_WRITE";
		public static final String SYSTEM_ALERT_WINDOW = "android.permission.SYSTEM_ALERT_WINDOW";
		public static final String UPDATE_DEVICE_STATS = "android.permission.UPDATE_DEVICE_STATS";
		public static final String USE_CREDENTIALS = "android.permission.USE_CREDENTIALS";
		public static final String USE_SIP = "android.permission.USE_SIP";
		public static final String VIBRATE = "android.permission.VIBRATE";
		public static final String WAKE_LOCK = "android.permission.WAKE_LOCK";
		public static final String WRITE_APN_SETTINGS = "android.permission.WRITE_APN_SETTINGS";
		public static final String WRITE_CALENDAR = "android.permission.WRITE_CALENDAR";
		public static final String WRITE_CALL_LOG = "android.permission.WRITE_CALL_LOG";
		public static final String WRITE_CONTACTS = "android.permission.WRITE_CONTACTS";
		public static final String WRITE_EXTERNAL_STORAGE = "android.permission.WRITE_EXTERNAL_STORAGE";
		public static final String WRITE_GSERVICES = "android.permission.WRITE_GSERVICES";
		public static final String WRITE_HISTORY_BOOKMARKS = "com.android.browser.permission.WRITE_HISTORY_BOOKMARKS";
		public static final String WRITE_PROFILE = "android.permission.WRITE_PROFILE";
		public static final String WRITE_SECURE_SETTINGS = "android.permission.WRITE_SECURE_SETTINGS";
		public static final String WRITE_SETTINGS = "android.permission.WRITE_SETTINGS";
		public static final String WRITE_SMS = "android.permission.WRITE_SMS";
		public static final String WRITE_SOCIAL_STREAM = "android.permission.WRITE_SOCIAL_STREAM";
		public static final String WRITE_SYNC_SETTINGS = "android.permission.WRITE_SYNC_SETTINGS";
		public static final String WRITE_USER_DICTIONARY = "android.permission.WRITE_USER_DICTIONARY";
		// CHECKSTYLE: ON
	}
}

