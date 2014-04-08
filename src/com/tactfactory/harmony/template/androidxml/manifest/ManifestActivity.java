package com.tactfactory.harmony.template.androidxml.manifest;

import java.util.ArrayList;
import java.util.List;

import org.jdom2.Element;
import org.jdom2.Namespace;

import com.tactfactory.harmony.template.androidxml.manifest.enums.ConfigChange;
import com.tactfactory.harmony.template.androidxml.manifest.enums.LaunchMode;
import com.tactfactory.harmony.template.androidxml.manifest.enums.ScreenOrientation;
import com.tactfactory.harmony.template.androidxml.manifest.enums.SoftInputMode;
import com.tactfactory.harmony.template.androidxml.manifest.enums.UiOptions;

public class ManifestActivity extends ManifestElement {
	private Boolean allowTaskReparenting;
	private Boolean alwaysRetainTaskState;
	private Boolean clearTaskOnLaunch;
	private List<ConfigChange> configChanges;
	private Boolean enabled;
	private Boolean excludeFromRecents;
	private Boolean exported;
	private Boolean finishOnTaskLaunch;
	private Boolean hardwareAccelerated;
	private String icon;
	private String label;
	private LaunchMode launchMode;
	private Boolean multiprocess;
	private String name;
	private Boolean noHistory; 
	private String parentActivityName; 
	private String permission;
	private String process;
	private ScreenOrientation screenOrientation; 
	private Boolean stateNotNeeded;
	private String taskAffinity;
	private String theme;
	private UiOptions uiOptions;
	private List<SoftInputMode> windowSoftInputMode;
	private List<ManifestIntentFilter> intentFilters;

	public ManifestActivity() {
		this.intentFilters = new ArrayList<ManifestIntentFilter>();
	}
	
    @Override
	public Element toElement(Namespace ns) {
		Element result = new Element(ELEMENT_ACTIVITY);
		
		this.addAttribute(
				result, ns, ATTRIBUTE_NAME, this.name);
		this.addAttribute(
				result, ns, ATTRIBUTE_THEME, this.theme);
		this.addAttribute(
				result, ns, ATTRIBUTE_LABEL, this.label);
		this.addAttribute(
				result, ns, ATTRIBUTE_EXPORTED, this.exported);
		
		if (this.intentFilters != null) {
			for (ManifestIntentFilter intentFilter : this.intentFilters) {
				result.addContent(intentFilter.toElement(ns));
			}
		}
		
		return result;
	}
	
	/**
	 * @return the allowTaskReparenting
	 */
	public final Boolean getAllowTaskReparenting() {
		return allowTaskReparenting;
	}

	/**
	 * @return the alwaysRetainTaskState
	 */
	public final Boolean getAlwaysRetainTaskState() {
		return alwaysRetainTaskState;
	}

	/**
	 * @return the clearTaskOnLaunch
	 */
	public final Boolean getClearTaskOnLaunch() {
		return clearTaskOnLaunch;
	}

	/**
	 * @return the configChanges
	 */
	public final List<ConfigChange> getConfigChanges() {
		return configChanges;
	}

	/**
	 * @return the enabled
	 */
	public final Boolean getEnabled() {
		return enabled;
	}

	/**
	 * @return the excludeFromRecents
	 */
	public final Boolean getExcludeFromRecents() {
		return excludeFromRecents;
	}

	/**
	 * @return the exported
	 */
	public final Boolean getExported() {
		return exported;
	}

	/**
	 * @return the finishOnTaskLaunch
	 */
	public final Boolean getFinishOnTaskLaunch() {
		return finishOnTaskLaunch;
	}

	/**
	 * @return the hardwareAccelerated
	 */
	public final Boolean getHardwareAccelerated() {
		return hardwareAccelerated;
	}

	/**
	 * @return the icon
	 */
	public final String getIcon() {
		return icon;
	}

	/**
	 * @return the label
	 */
	public final String getLabel() {
		return label;
	}

	/**
	 * @return the launchMode
	 */
	public final LaunchMode getLaunchMode() {
		return launchMode;
	}

	/**
	 * @return the multiprocess
	 */
	public final Boolean getMultiprocess() {
		return multiprocess;
	}

	/**
	 * @return the name
	 */
	public final String getName() {
		return name;
	}

	/**
	 * @return the noHistory
	 */
	public final Boolean getNoHistory() {
		return noHistory;
	}

	/**
	 * @return the parentActivityName
	 */
	public final String getParentActivityName() {
		return parentActivityName;
	}

	/**
	 * @return the permission
	 */
	public final String getPermission() {
		return permission;
	}

	/**
	 * @return the process
	 */
	public final String getProcess() {
		return process;
	}

	/**
	 * @return the screenOrientation
	 */
	public final ScreenOrientation getScreenOrientation() {
		return screenOrientation;
	}

	/**
	 * @return the stateNotNeeded
	 */
	public final Boolean getStateNotNeeded() {
		return stateNotNeeded;
	}

	/**
	 * @return the taskAffinity
	 */
	public final String getTaskAffinity() {
		return taskAffinity;
	}

	/**
	 * @return the theme
	 */
	public final String getTheme() {
		return theme;
	}

	/**
	 * @return the uiOptions
	 */
	public final UiOptions getUiOptions() {
		return uiOptions;
	}

	/**
	 * @return the windowSoftInputMode
	 */
	public final List<SoftInputMode> getWindowSoftInputMode() {
		return windowSoftInputMode;
	}

	/**
	 * @param allowTaskReparenting the allowTaskReparenting to set
	 */
	public final void setAllowTaskReparenting(Boolean allowTaskReparenting) {
		this.allowTaskReparenting = allowTaskReparenting;
	}

	/**
	 * @param alwaysRetainTaskState the alwaysRetainTaskState to set
	 */
	public final void setAlwaysRetainTaskState(Boolean alwaysRetainTaskState) {
		this.alwaysRetainTaskState = alwaysRetainTaskState;
	}

	/**
	 * @param clearTaskOnLaunch the clearTaskOnLaunch to set
	 */
	public final void setClearTaskOnLaunch(Boolean clearTaskOnLaunch) {
		this.clearTaskOnLaunch = clearTaskOnLaunch;
	}

	/**
	 * @param configChanges the configChanges to set
	 */
	public final void setConfigChanges(List<ConfigChange> configChanges) {
		this.configChanges = configChanges;
	}

	/**
	 * @param enabled the enabled to set
	 */
	public final void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	/**
	 * @param excludeFromRecents the excludeFromRecents to set
	 */
	public final void setExcludeFromRecents(Boolean excludeFromRecents) {
		this.excludeFromRecents = excludeFromRecents;
	}

	/**
	 * @param exported the exported to set
	 */
	public final void setExported(Boolean exported) {
		this.exported = exported;
	}

	/**
	 * @param finishOnTaskLaunch the finishOnTaskLaunch to set
	 */
	public final void setFinishOnTaskLaunch(Boolean finishOnTaskLaunch) {
		this.finishOnTaskLaunch = finishOnTaskLaunch;
	}

	/**
	 * @param hardwareAccelerated the hardwareAccelerated to set
	 */
	public final void setHardwareAccelerated(Boolean hardwareAccelerated) {
		this.hardwareAccelerated = hardwareAccelerated;
	}

	/**
	 * @param icon the icon to set
	 */
	public final void setIcon(String icon) {
		this.icon = icon;
	}

	/**
	 * @param label the label to set
	 */
	public final void setLabel(String label) {
		this.label = label;
	}

	/**
	 * @param launchMode the launchMode to set
	 */
	public final void setLaunchMode(LaunchMode launchMode) {
		this.launchMode = launchMode;
	}

	/**
	 * @param multiprocess the multiprocess to set
	 */
	public final void setMultiprocess(Boolean multiprocess) {
		this.multiprocess = multiprocess;
	}

	/**
	 * @param name the name to set
	 */
	public final void setName(String name) {
		this.name = name;
	}

	/**
	 * @param noHistory the noHistory to set
	 */
	public final void setNoHistory(Boolean noHistory) {
		this.noHistory = noHistory;
	}

	/**
	 * @param parentActivityName the parentActivityName to set
	 */
	public final void setParentActivityName(String parentActivityName) {
		this.parentActivityName = parentActivityName;
	}

	/**
	 * @param permission the permission to set
	 */
	public final void setPermission(String permission) {
		this.permission = permission;
	}

	/**
	 * @param process the process to set
	 */
	public final void setProcess(String process) {
		this.process = process;
	}

	/**
	 * @param screenOrientation the screenOrientation to set
	 */
	public final void setScreenOrientation(ScreenOrientation screenOrientation) {
		this.screenOrientation = screenOrientation;
	}

	/**
	 * @param stateNotNeeded the stateNotNeeded to set
	 */
	public final void setStateNotNeeded(Boolean stateNotNeeded) {
		this.stateNotNeeded = stateNotNeeded;
	}

	/**
	 * @param taskAffinity the taskAffinity to set
	 */
	public final void setTaskAffinity(String taskAffinity) {
		this.taskAffinity = taskAffinity;
	}

	/**
	 * @param theme the theme to set
	 */
	public final void setTheme(String theme) {
		this.theme = theme;
	}

	/**
	 * @param uiOptions the uiOptions to set
	 */
	public final void setUiOptions(UiOptions uiOptions) {
		this.uiOptions = uiOptions;
	}

	/**
	 * @param windowSoftInputMode the windowSoftInputMode to set
	 */
	public final void setWindowSoftInputMode(List<SoftInputMode> windowSoftInputMode) {
		this.windowSoftInputMode = windowSoftInputMode;
	}

	/**
	 * @return the intentFilters
	 */
	public final List<ManifestIntentFilter> getIntentFilters() {
		return intentFilters;
	}

	/**
	 * @param intentFilters the intentFilters to set
	 */
	public final void setIntentFilters(List<ManifestIntentFilter> intentFilters) {
		this.intentFilters = intentFilters;
	}
	
	public final void addIntentFilter(ManifestIntentFilter intentFilter) {
		this.intentFilters.add(intentFilter);
	}
}