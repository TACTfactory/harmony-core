<#assign sync=false />
<#list entities?values as entity>
	<#if entity.options.sync??>
		<#assign sync=true />
	</#if>
</#list>
package ${project_namespace};

import java.text.DateFormat;

import ${project_namespace}.R;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.Settings.Secure;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.content.SharedPreferences;

<#if (sync)>
import ${project_namespace}.harmony.util.DateUtils;
import org.joda.time.DateTime;
</#if>

/** 
 * Common all life data/service.
 * 
 * <b><i>This class will be overwrited whenever you regenerate the project with Harmony. 
 * You should edit ${project_name?cap_first}Application class instead of this one or you will lose all your modifications.</i></b>
 * 
 */
public abstract class ${project_name?cap_first}ApplicationBase extends Application {
	private static final String TAG = "${project_name?cap_first}";
	private static volatile ${project_name?cap_first}ApplicationBase singleton;
	private static DateFormat df;
	private static DateFormat tf;
	<#if (sync)>
	private static SharedPreferences preferences;
	</#if>
	
	/** Called when the application is first created. */
	@Override
	public void onCreate() {
		super.onCreate();
		
		<#if (sync)>
		preferences = this.getSharedPreferences(
				"${project_name?uncap_first}", Context.MODE_PRIVATE);
		
		if (!preferences.contains("lastSyncDate")) {
			// TODO: First Sync
			
			${project_name?cap_first}ApplicationBase.setLastSyncDate(new DateTime().minusWeeks(1));
		}
		</#if>
		singleton = this;
		Log.i(TAG, "Starting application...");
		
		deviceID = getUDID(this);
		df = android.text.format.DateFormat.getDateFormat((Context) singleton);
		tf = android.text.format.DateFormat.getTimeFormat((Context) singleton);
		
		// Manage unmanaged error of application
		//Thread.setDefaultUncaughtExceptionHandler(
		//		new ApplicationCrashHandler(super.getApplicationContext()));
		

	}

	/**
	 * Get the device's UDID.
	 * @return A String containing the UDID
	 */
	public static String getUDID(final Context context) {
		String udid = Secure.getString(context.getContentResolver(), Secure.ANDROID_ID);
		
		// for emulator
		if (udid == null) {
			udid = "000000000android00000000000000";
		}
		
		// for google bug, android < 2.3 (many device)
		if (udid.equals("9774d56d682e549c")) {
			final TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
			udid = telephonyManager.getDeviceId();
		}
		
		return udid;
	}
	
	/**
	 * Get the singleton
	 * @return The singleton of the application
	 */ 
	public static Context getApplication() {
		return singleton;
	}
	
	
	/** UUID equivalent. */
	private static String deviceID;
	public static String getAndroidID() {
		return deviceID;
	}
	
	/** Application. */
	private static final String PREFS_PUBL = "puapsd"; // Public Application Shared Data
	private static final String PREFS_VERS = "version";
	
	/** Get Application Version.
	 * 
	 * @param ctx
	 * @return the version number
	 */
	public static String getVersion(final Context ctx) {
		final SharedPreferences settings = ctx.getSharedPreferences(
				${project_name?cap_first}ApplicationBase.PREFS_PUBL, Context.MODE_WORLD_READABLE);
		
		return settings.getString(
				${project_name?cap_first}ApplicationBase.PREFS_VERS, "");
	}
	/** Check if is a new version of Application.
	 * 
	 * @param ctx
	 * @return true if same version
	 */
	public static boolean isGoodVersion(final Context ctx) {
		final String oldVersion = getVersion(ctx);
		final String currentVersion = ctx.getString(R.string.app_version);
		
		return oldVersion.equals(currentVersion);
	}
	/** Save if a new version is install.
	 * 
	 * @param ctx
	 */
	public static void setVersion(final Context ctx) {
		final SharedPreferences settings = ctx.getSharedPreferences(
				${project_name?cap_first}ApplicationBase.PREFS_PUBL, Context.MODE_WORLD_READABLE);
		
		final String currentVersion = ctx.getString(R.string.app_version);
	    final SharedPreferences.Editor editor = settings.edit();
	    editor.putString(${project_name?cap_first}ApplicationBase.PREFS_VERS, currentVersion);
		
	    // Commit the edits!
	    editor.commit();
	}	

	
	/** Check if Network is available.
	 * 
	 * @param ctx
	 * @return true if have a network
	 */
	public static boolean isNetworkAvailable(final Context ctx) {
	    final ConnectivityManager connectivityManager = (ConnectivityManager) ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
	    final NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
	    return (activeNetworkInfo != null && activeNetworkInfo.isConnected());
	}
	
	/**
	 * Get the Date Format
	 * @return the DateFormat
	 */
	public static DateFormat getDateFormat() {
		return df;
	}
	
	/**
	 * Get the Time Format
	 * @return the TimeFormat
	 */
	public static DateFormat getTimeFormat() {
		return tf;
	}
		
	<#if (sync)>
	/**
	 * Get the last sync date.
	 * @return A DateTime representing the last sync date
	 */
	public static DateTime getLastSyncDate() {
		return DateUtils.formatISOStringToDateTime(preferences.getString("lastSyncDate", null));
	}
	
	/**
	 * Set the last sync date.
	 * @param dt DateTime representing the last sync date to set
	 */
	public static void setLastSyncDate(DateTime dt) {
		Editor edit = preferences.edit();
		edit.putString("lastSyncDate", dt.toString());
		edit.commit();
	}
	</#if>
}
