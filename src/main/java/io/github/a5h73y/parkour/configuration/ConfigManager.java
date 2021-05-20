package io.github.a5h73y.parkour.configuration;

import io.github.a5h73y.parkour.configuration.impl.DefaultConfig;
import io.github.a5h73y.parkour.configuration.impl.ParkourKitConfig;
import io.github.a5h73y.parkour.configuration.impl.StringsConfig;
import java.io.File;

/**
 * Parkour Configuration Manager.
 * Manages and stores references to each of the available Config files.
 */
public class ConfigManager {

	private final File dataFolder;

	private final DefaultConfig defaultConfig;
	private final StringsConfig stringsConfig;
	private final ParkourKitConfig parkourKitConfig;
//	private final OtherConfig otherConfig;

	/**
	 * Initialise the Config Manager.
	 * Will invoke setup for each available config type.
	 *
	 * @param dataFolder where to store the configs
	 */
	public ConfigManager(File dataFolder) {
		this.dataFolder = dataFolder;
		createParkourFolders();

		defaultConfig = new DefaultConfig(new File(dataFolder, "config.yml"));
		stringsConfig = new StringsConfig(new File(dataFolder, "strings.yml"));
		parkourKitConfig = new ParkourKitConfig(new File(dataFolder, "parkourkit.yml"));
	}

	public DefaultConfig getDefaultConfig() {
		return defaultConfig;
	}

	public StringsConfig getStringsConfig() {
		return stringsConfig;
	}

	public ParkourKitConfig getParkourKitConfig() {
		return parkourKitConfig;
	}

	/**
	 * Reload each of the configuration files.
	 */
	public void reloadConfigs() {
		defaultConfig.forceReload();
		stringsConfig.forceReload();
		parkourKitConfig.forceReload();
	}

	private void createParkourFolders() {
		if (!dataFolder.exists()) {
			dataFolder.mkdirs();
		}
	}
}
