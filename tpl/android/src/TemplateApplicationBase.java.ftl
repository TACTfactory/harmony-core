<#assign sync=false />
<#list entities?values as entity>
	<#if entity.options.sync??>
		<#assign sync=true />
	</#if>
</#list>
<@header?interpret />
package ${project_namespace};

import java.text.DateFormat;
<#if (services?size > 0)>
import java.util.ArrayList;
import java.util.List;
</#if>

import android.app.Application;
<#if (services?size > 0)>
import android.app.IntentService;
import android.app.Service;
</#if>
import android.content.Context;
<#if (services?size > 0)>
import android.content.Intent;
</#if>
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
<#if (sync)>import android.content.SharedPreferences.Editor;
</#if>import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.Settings.Secure;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.content.SharedPreferences;

<#if (sync)>
import ${project_namespace}.harmony.util.DateUtils;
import org.joda.time.DateTime;
</#if>

<#list services as service>
	<#if service?starts_with(".")>
import ${project_namespace}${service};
	<#else>
import ${service};
	</#if>
</#list>

/**
 * Common all life data/service.
 *
 * <b><i>This class will be overwrited whenever you regenerate the project with
 * Harmony.
 * You should edit ${project_name?cap_first}Application class instead of this
 * one or you will lose all your modifications.</i></b>
 *
 */
public abstract class ${project_name?cap_first}ApplicationBase
	extends Application {

	/** TAG for debug purpose. */
	protected static final String TAG = "${project_name?cap_first}Application";
	/** Singleton. */
	private static volatile ${project_name?cap_first}ApplicationBase singleton;
	/** Date format. */
	private static DateFormat dateFormat;
	/** Time format. */
	private static DateFormat timeFormat;
	/** 24HFormat. */
	private static boolean is24H;
	<#if (services?size > 0)>
	/** Services binded. */
	private boolean isServicesBinded = false;
	</#if>
	<#if (sync)>
	/** Preferences. */
	private static SharedPreferences preferences;
	</#if>

	@Override
	public void onCreate() {
		super.onCreate();

		setSingleton(this);
		
		<#if (services?size > 0)>
		this.doBindServices();
		
		</#if>
		Log.i(TAG, "Starting application...");
	}
	
	<#if (services?size > 0)>
	@Override
	protected void finalize() throws Throwable {
		this.doUnbindServices();
		
		super.finalize();
	}
	
	/**
	 * Bind all services to the application.
	 */
	private void doBindServices() {
		Log.i(TAG, "Bind Services.");
		
		if (!this.isServicesBinded) {
			for (Class<? extends Service> service : this.getServices()) {
				if (!(IntentService.class.isAssignableFrom(service))) {
					Intent intent = new Intent(this, service);
					this.startService(intent);
				}
			}
			
			this.isServicesBinded = true;
		}
	}
	
	/**
	 * Unbind all services to the application.
	 */
	private void doUnbindServices() {
		Log.i(TAG, "Unbind Services.");
		
		if (this.isServicesBinded) {
			for (Class<? extends Service> service : this.getServices()) {
				if (!(IntentService.class.isAssignableFrom(service))) {
					Intent intent = new Intent(this, service);
					this.stopService(intent);
				}
			}
			
			this.isServicesBinded = false;
		}
	}
	
	/**
	 * Get Services.
	 */
	protected List<Class<? extends Service>> getServices() {
		List<Class<? extends Service>> result
			= new ArrayList<Class<? extends Service>>();
		
		<#list services as service>
			<#assign serviceSplit= service?split(".")/>
		result.add(${serviceSplit[serviceSplit?size - 1]}.class);
		</#list>
		
		return result;
	}
	
	</#if>
	/**
	 * Set the application singleton.
	 * @param application The application instance
	 */
	private static void setSingleton(${project_name?cap_first}ApplicationBase application) {
		if (singleton == null) {
			singleton = application;
			deviceID = getUDID(application);
			dateFormat =
				android.text.format.DateFormat.getDateFormat(application);
			timeFormat =
				android.text.format.DateFormat.getTimeFormat(application);
			is24H =
				android.text.format.DateFormat.is24HourFormat(application);
			<#if (sync)>
			preferences = application.getSharedPreferences(
					"${project_name?uncap_first}", Context.MODE_PRIVATE);

			if (!preferences.contains("lastSyncDate")) {
				// TODO: First Sync

				${project_name?cap_first}ApplicationBase
					.setLastSyncDate(new DateTime().minusWeeks(1));
			}
			</#if>
		}
	}

	/**
	 * Get the device's UDID.
	 * @param ctx The context
	 * @return A String containing the UDID
	 */
	public static String getUDID(final Context ctx) {
		String udid = Secure.getString(
			ctx.getContentResolver(), Secure.ANDROID_ID);

		// for emulator
		if (udid == null) {
			udid = "000000000android00000000000000";
		}

		// for google bug, android < 2.3 (many device)
		if (udid.equals("9774d56d682e549c")) {
			final TelephonyManager telephonyManager = (TelephonyManager)
				ctx.getSystemService(Context.TELEPHONY_SERVICE);
			udid = telephonyManager.getDeviceId();
		}

		return udid;
	}

	/**
	 * Get the singleton.
	 * @return The singleton of the application
	 */
	public static ${project_name?cap_first}ApplicationBase getApplication() {
		return singleton;
	}


	/** UUID Equivalent. */
	private static String deviceID;
	/**
	 * UUID equivalent.
	 * @return UUID equivalent
	 */
	public static String getUDID() {
		return deviceID;
	}

	/** Application. */
	/** Public Application Shared Data. */
	private static final String PREFS_PUBL = "puapsd";
	/** Application version key. */
	private static final String PREFS_VERS = "version";

	/** Get Application Version.
	 *
	 * @param ctx The application context.
	 * @return the version number
	 */
	public static String getVersion(final Context ctx) {
		final SharedPreferences settings = ctx.getSharedPreferences(
				${project_name?cap_first}ApplicationBase.PREFS_PUBL,
				Context.MODE_WORLD_READABLE);

		return settings.getString(
				${project_name?cap_first}ApplicationBase.PREFS_VERS, "");
	}

	/** Check if is a new version of Application.
	 *
	 * @param ctx The application context.
	 * @return true if same version
	 */
	public static boolean isGoodVersion(final Context ctx) {
		final String oldVersion = getVersion(ctx);
		final String currentVersion = ctx.getString(R.string.app_version);

		return oldVersion.equals(currentVersion);
	}

	/** Save if a new version is install.
	 *
	 * @param ctx The application context.
	 */
	public static void setVersion(final Context ctx) {
		final SharedPreferences settings = ctx.getSharedPreferences(
				${project_name?cap_first}ApplicationBase.PREFS_PUBL,
				Context.MODE_WORLD_READABLE);

		final String currentVersion = ctx.getString(R.string.app_version);
	    final SharedPreferences.Editor editor = settings.edit();
	    editor.putString(
	    	${project_name?cap_first}ApplicationBase.PREFS_VERS,
	    	currentVersion);

	    // Commit the edits!
	    editor.commit();
	}

	/** Check if Network is available.
	 *
	 * @param ctx The application context
	 * @return true if have a network
	 */
	public static boolean isNetworkAvailable(final Context ctx) {
	    final ConnectivityManager connectivityManager = (ConnectivityManager)
	    	ctx.getSystemService(Context.CONNECTIVITY_SERVICE);

	    final NetworkInfo activeNetworkInfo = connectivityManager
	    	.getActiveNetworkInfo();

	    return (activeNetworkInfo != null && activeNetworkInfo.isConnected());
	}

	/**
	 * Get the Date Format.
	 * @return the DateFormat
	 */
	public static DateFormat getDateFormat() {
		return dateFormat;
	}

	/**
	 * Get the Time Format.
	 * @return the TimeFormat
	 */
	public static DateFormat getTimeFormat() {
		return timeFormat;
	}

	/**
	 * Get the 24H format.
	 * @return true if 24 hour mode. false if am/pm
	 */
	public static boolean is24Hour() {
		return is24H;
	}

	<#if (sync)>
	/**
	 * Get the last sync date.
	 * @return A DateTime representing the last sync date
	 */
	public static DateTime getLastSyncDate() {
		return DateUtils.formatISOStringToDateTime(
				preferences.getString("lastSyncDate", null));
	}

	/**
	 * Set the last sync date.
	 * @param dateTime DateTime representing the last sync date to set
	 */
	public static void setLastSyncDate(DateTime dateTime) {
		Editor edit = preferences.edit();
		edit.putString("lastSyncDate", dateTime.toString());
		edit.commit();
	}
	</#if>

	/**
	 * Get the application version code.
	 * @param ctx The context
	 * @return The application version code
	 */
	public static int getVersionCode(Context ctx) {
		int result = 1;

		try {
			PackageInfo manager = ctx.getPackageManager().getPackageInfo(
					ctx.getPackageName(), 0);

			result = manager.versionCode;
		} catch (NameNotFoundException e) {
			Log.e(TAG, "Version Code not found : " + e.toString());
		}

		return result;
	}

	/**
	 * Gets the device type associated to this context.
	 */
	public static DeviceType getDeviceType(Context context) {
		return DeviceType.fromValue(context.getString(R.string.device_type));
	}

	/**
	 * Enum for device type. (phone, tablet, tv, etc.)
	 */
	public enum DeviceType {
		PHONE("phone"),
		TABLET("tablet"),
		TV("tv"),
		WATCH("watch"),
		GLASS("glass");

		/** String value as in configs.xml */
		private String configValue;

		/**
		 * Constructor.
		 *
		 * @param configValue The string value as in configs.xml.
		 */
		private DeviceType(String configValue) {
			this.configValue = configValue;
		}

		/**
		 * Gets the device type corresponding to the given string.
		 */
		public static DeviceType fromValue(String configValue) {
			DeviceType result = null;
			for (DeviceType deviceType : DeviceType.values()) {
				if (deviceType.configValue.equals(configValue)) {
					result = deviceType;
					break;
				}
			}
			return result;
		}
	}
}
