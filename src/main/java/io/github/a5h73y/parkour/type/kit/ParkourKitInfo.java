package io.github.a5h73y.parkour.type.kit;

import io.github.a5h73y.parkour.Parkour;
import io.github.a5h73y.parkour.configuration.impl.ParkourKitConfig;
import io.github.a5h73y.parkour.type.course.CourseInfo;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * ParkourKit Information Utility class.
 * Convenience methods for accessing parkourkit configuration file.
 */
public class ParkourKitInfo {

    public static final String PARKOUR_KIT_CONFIG_PREFIX = "ParkourKit.";

    /**
     * Get all available ParkourKit names.
     * @return parkour kit names
     */
    public static Set<String> getAllParkourKitNames() {
        return getParkourKitConfig().getSection("ParkourKit").keySet();
    }

    /**
     * Check if ParkourKit exists.
     * @param kitName parkour kit name
     * @return parkour kit exists
     */
    public static boolean doesParkourKitExist(String kitName) {
        return getAllParkourKitNames().contains(kitName.toLowerCase());
    }

    /**
     * Get the Material names for the ParkourKit.
     * @param kitName parkour kit name
     * @return material names for kit
     */
    public static Set<String> getParkourKitMaterials(String kitName) {
        return getParkourKitConfig().getSection(PARKOUR_KIT_CONFIG_PREFIX + kitName.toLowerCase()).keySet();
    }

    /**
     * Get the ActionType name for Material for the ParkourKit.
     * @param kitName parkour kit name
     * @param material material name
     * @return matching action type name
     */
    public static String getActionTypeForMaterial(String kitName, String material) {
        return getParkourKitConfig().getString(PARKOUR_KIT_CONFIG_PREFIX + kitName.toLowerCase()
                + "." + material.toUpperCase() + ".Action");
    }

    /**
     * Get Parkour Courses linked to the ParkourKit.
     * @param kitName parkour kit name
     * @return List Parkour course names
     */
    public static List<String> getDependentCourses(String kitName) {
        List<String> dependentCourses = new ArrayList<>();
        for (String courseName : CourseInfo.getAllCourseNames()) {
            String linkedKitName = CourseInfo.getParkourKit(courseName);
            if (kitName.equals(linkedKitName)) {
                dependentCourses.add(courseName);
            }
        }
        return dependentCourses;
    }

    /**
     * Delete the ParkourKit and all associated data.
     * @param kitName parkour kit name
     */
    public static void deleteKit(String kitName) {
        getParkourKitConfig().set(PARKOUR_KIT_CONFIG_PREFIX + kitName.toLowerCase(), null);
        Parkour.getInstance().getParkourKitManager().clearCache(kitName);
    }

    /**
     * Get the {@link ParkourKitConfig}.
     * @return the parkourkit.yml configuration
     */
    private static ParkourKitConfig getParkourKitConfig() {
        return Parkour.getInstance().getConfigManager().getParkourKitConfig();
    }

    private ParkourKitInfo() {
        throw new IllegalStateException("Utility class");
    }

    public static void removeMaterial(String kitName, String material) {
        getParkourKitConfig().set(kitName + "." + material, null);
    }
}
