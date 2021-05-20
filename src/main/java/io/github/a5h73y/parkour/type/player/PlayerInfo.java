package io.github.a5h73y.parkour.type.player;

import io.github.a5h73y.parkour.Parkour;
import io.github.a5h73y.parkour.type.course.CourseInfo;
import io.github.a5h73y.parkour.utility.TranslationUtils;
import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import de.leonhard.storage.Json;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Player Information Utility class.
 * Convenience methods for accessing the player configuration file.
 */
public class PlayerInfo {

    private static final String PLAYER_CONFIG_PATH = Parkour.getInstance().getDataFolder() + "/players";

    /**
     * Check if the Player is known to Parkour.
     * @param player player
     * @return player has Parkour information
     */
    public static boolean hasPlayerInfo(OfflinePlayer player) {
        return player != null && getPlayerConfig(player).getFile().exists();
    }

    /**
     * Get the Player's selected Course name.
     * @param player player
     * @return selected course name
     */
    public static String getSelectedCourse(OfflinePlayer player) {
        return getPlayerConfig(player).getString("Selected");
    }

    /**
     * Check if Player has selected a Course.
     * This does not guarantee they've selected a valid course.
     * @param player player
     * @return player has selected course
     */
    public static boolean hasSelectedCourse(OfflinePlayer player) {
        return getSelectedCourse(player) != null;
    }

    /**
     * Set the Player's selected Course name.
     * @param player player
     * @param courseName selected course name
     */
    public static void setSelectedCourse(OfflinePlayer player, @NotNull String courseName) {
        savePlayerConfig(player, "Selected", courseName.toLowerCase());
    }

    /**
     * Reset the Player's selected Course Name.
     * @param player player
     */
    public static void resetSelected(OfflinePlayer player) {
        savePlayerConfig(player, "Selected", null);
    }

    /**
     * Get the Player's last played Course.
     * The course they most recently joined, but may not have finished.
     * @param player player
     * @return course name last played
     */
    public static String getLastPlayedCourse(OfflinePlayer player) {
        return getPlayerConfig(player).getString("LastPlayed");
    }

    /**
     * Set the Player's last played Course.
     * @param player player
     * @param courseName course name last played
     */
    public static void setLastPlayedCourse(OfflinePlayer player, String courseName) {
        savePlayerConfig(player, "LastPlayed", courseName.toLowerCase());
    }

    /**
     * Get the Player's last completed Course.
     * @param player player
     * @return course name last completed
     */
    public static String getLastCompletedCourse(OfflinePlayer player) {
        return getPlayerConfig(player).getString("LastCompleted");
    }

    /**
     * Set the Player's last completed Course.
     * @param player player
     * @param courseName course name last completed
     */
    public static void setLastCompletedCourse(OfflinePlayer player, String courseName) {
        savePlayerConfig(player, "LastCompleted", courseName.toLowerCase());
    }

    /**
     * Get the number of Courses completed by Player.
     * @param player player
     * @return number of courses completed
     */
    public static int getNumberOfCompletedCourses(OfflinePlayer player) {
        return getCompletedCourses(player).size();
    }

    /**
     * Get the Completed Course names for Player.
     * @param player player
     * @return completed course names
     */
    public static List<String> getCompletedCourses(OfflinePlayer player) {
        return getPlayerConfig(player).getStringList("Completed");
    }

    public static boolean hasCompletedCourse(Player player, String name) {
        return getCompletedCourses(player).contains(name.toLowerCase());
    }

    public static int getNumberOfUncompletedCourses(OfflinePlayer player) {
        return getUncompletedCourses(player).size();
    }

    /**
     * Get the Uncompleted Course names for Player.
     * The Courses the player has yet to complete on the server.
     * @param player player
     * @return uncompleted course names
     */
    public static List<String> getUncompletedCourses(OfflinePlayer player) {
        List<String> completedCourses = getCompletedCourses(player);
        return CourseInfo.getAllCourseNames().stream()
                .filter(s -> !completedCourses.contains(s))
                .collect(Collectors.toList());
    }

    /**
     * Add a Course name to the Player's completions.
     * @param player player
     * @param courseName completed course name
     */
    public static void addCompletedCourse(OfflinePlayer player, String courseName) {
        List<String> completedCourses = getCompletedCourses(player);

        if (!completedCourses.contains(courseName)) {
            completedCourses.add(courseName);
            getPlayerConfig(player).set("Completed", completedCourses);
        }
    }

    /**
     * Remove a Course from each Player's completed courses.
     * When a Course gets deleted, it must be removed from each player's completed courses.
     * @param courseName course name
     */
    public static void removeCompletedCourse(String courseName) {
//        Set<String> playersIds = getPlayersConfig().getConfigurationSection("").getKeys(false);
//
//        for (String uuid : playersIds) {
//            String completedPath = uuid + ".Completed";
//            List<String> completedCourses = getPlayersConfig().getStringList(completedPath);
//
//            if (completedCourses.contains(courseName)) {
//                completedCourses.remove(courseName);
//                getPlayersConfig().set(completedPath, completedCourses);
//            }
//        }
//        persistChanges();
        // TODO this is going to be crazy
    }

    /**
     * Get the Player's ParkourLevel.
     * @param player player
     * @return parkour level value
     */
    public static int getParkourLevel(OfflinePlayer player) {
        return getPlayerConfig(player).getInt("ParkourLevel");
    }

    /**
     * Increase the Player's ParkourLevel by the amount.
     * @param player player
     * @param amount amount to increase
     */
    public static void increaseParkourLevel(OfflinePlayer player, int amount) {
        setParkourLevel(player, getParkourLevel(player) + amount);
    }

    /**
     * Set the Player's ParkourLevel.
     * @param player player
     * @param level new parkour level value
     */
    public static void setParkourLevel(OfflinePlayer player, int level) {
        savePlayerConfig(player, "ParkourLevel", level);
    }

    /**
     * Get the Player's ParkourRank.
     * The default rank value will be used if not set.
     * @param player player
     * @return player's parkour rank
     */
    public static String getParkourRank(OfflinePlayer player) {
        return getPlayerConfig(player).getOrDefault("ParkourRank",
                TranslationUtils.getTranslation("Event.DefaultRank", false));
    }

    /**
     * Set the Player's ParkourRank.
     * @param player target player
     * @param parkourRank parkour rank value
     */
    public static void setParkourRank(OfflinePlayer player, String parkourRank) {
        savePlayerConfig(player, "ParkourRank", parkourRank);
    }

    /**
     * Get last rewarded time for Course.
     * Get the timestamp the Player last received a reward for the completing Course.
     * @param player player
     * @param courseName course name
     * @return last rewarded time
     */
    public static long getLastRewardedTime(OfflinePlayer player, String courseName) {
        return getPlayerConfig(player).getLong("LastRewarded-" + courseName.toLowerCase());
    }

    /**
     * Set the last rewarded time for Course.
     * @param player player
     * @param courseName course name
     * @param rewardTime time of reward given
     */
    public static void setLastRewardedTime(OfflinePlayer player, String courseName, long rewardTime) {
        savePlayerConfig(player, "LastRewarded-" + courseName.toLowerCase(), rewardTime);
    }

    /**
     * Reset Player's Parkour data.
     * @param player target player
     */
    public static void resetPlayerData(OfflinePlayer player) {
//        getPlayersConfig().set(player.getUniqueId().toString(), null);
//        persistChanges();
//
        // delete the file, same way we delete bin file

    }

    /**
     * Set the ParkourRank reward for a ParkourLevel.
     * @param parkourLevel parkour level
     * @param parkourRank parkour rank value
     */
    public static void setRewardParkourRank(int parkourLevel, String parkourRank) {
//        getPlayersConfig().set("ServerInfo.Levels." + parkourLevel + ".Rank", parkourRank);
//        persistChanges();
    }

    public static boolean hasInventoryData(Player player) {
        return getPlayerConfig(player).contains("Inventory");
    }

    /**
     * Save the Player's Inventory and Armor contents.
     * @param player player
     */
    public static void saveInventoryArmorData(Player player) {
        savePlayerConfig(player, "Inventory", Arrays.asList(player.getInventory().getContents()));
        savePlayerConfig(player, "Armor", Arrays.asList(player.getInventory().getArmorContents()));
    }

    /**
     * Reset the Player's Inventory and Armor contents.
     * @param player player
     */
    public static void resetInventoryArmorData(Player player) {
        savePlayerConfig(player, "Inventory", null);
        savePlayerConfig(player, "Armor", null);
    }

    /**
     * Get the Player's saved Health.
     * @param player player
     * @return stored health
     */
    public static double getSavedHealth(Player player) {
        return getPlayerConfig(player).getDouble("Health");
    }

    /**
     * Get the Player's saved Health.
     * @param player player
     * @return stored health
     */
    public static int getSavedFoodLevel(Player player) {
        return getPlayerConfig(player).getInt("Hunger");
    }

    /**
     * Save the Player's Health and Food Level.
     * @param player player
     */
    public static void saveHealthFoodLevel(Player player) {
        savePlayerConfig(player, "Health", player.getHealth());
        savePlayerConfig(player, "Hunger", player.getFoodLevel());
    }

    /**
     * Reset the saved Health and Food Level.
     * @param player player
     */
    public static void resetSavedHealthFoodLevel(Player player) {
        savePlayerConfig(player, "Health", null);
        savePlayerConfig(player, "Hunger", null);
    }

    /**
     * Get the Player's Saved XP Level.
     * @param player player
     * @return saved XP level
     */
    public static int getSavedXpLevel(Player player) {
        return getPlayerConfig(player).getInt("XPLevel");
    }

    /**
     * Save the Player's XP Level.
     * @param player player
     */
    public static void saveXpLevel(Player player) {
        savePlayerConfig(player, "XPLevel", player.getLevel());
    }

    /**
     * Reset the Player's saved XP Level.
     * @param player player
     */
    public static void resetSavedXpLevel(Player player) {
        savePlayerConfig(player, "XPLevel", null);
    }

    /**
     * Retrieve the Player's saved Inventory contents.
     * @param player player
     * @return player's inventory contents
     */
    public static ItemStack[] getSavedInventoryContents(Player player) {
        List<ItemStack> contents = (List<ItemStack>) getPlayerConfig(player).getList("Inventory");
        return contents != null ? contents.toArray(new ItemStack[0]) : null;
    }

    /**
     * Retrieve the Player's saved Armor contents.
     * @param player player
     * @return player's armor contents
     */
    public static ItemStack[] getSavedArmorContents(Player player) {
        List<ItemStack> contents = (List<ItemStack>) getPlayerConfig(player).getList("Armor");
        return contents != null ? contents.toArray(new ItemStack[0]) : null;
    }

    /**
     * Get the Player's Join Location.
     * The {@link Location} from which the player joined the course.
     * @param player player
     * @return saved join location
     */
    public static Location getJoinLocation(Player player) {
        return (Location) getPlayerConfig(player).get("JoinLocation");
    }

    /**
     * Check whether the Player has a Join Location set.
     * @param player player
     * @return join location set
     */
    public static boolean hasJoinLocation(Player player) {
        return getPlayerConfig(player).contains("JoinLocation");
    }

    /**
     * Set the Player's Join Location.
     * The player's current position will be saved as their join location.
     * @param player player
     */
    public static void setJoinLocation(Player player) {
        savePlayerConfig(player, "JoinLocation", player.getLocation());
    }

    /**
     * Reset the Player's Join Location.
     * @param player player
     */
    public static void resetJoinLocation(Player player) {
        savePlayerConfig(player, "JoinLocation", null);
    }

    /**
     * Check if the Player is in Quiet Mode.
     * Quite Mode will not message the player as often with non-important messages.
     * @param player player
     * @return is quiet mode enabled
     */
    public static boolean isQuietMode(Player player) {
        return getPlayerConfig(player).getBoolean("QuietMode");
    }

    /**
     * Toggle the Player's Quiet Mode status.
     * @param player player
     */
    public static void toggleQuietMode(Player player) {
        setQuietMode(player, !isQuietMode(player));
    }

    /**
     * Set the Player's Quiet Mode status.
     * @param player target player
     * @param quietMode value to set
     */
    public static void setQuietMode(Player player, boolean quietMode) {
        savePlayerConfig(player, "QuietMode", quietMode);
    }

    /**
     * Get the number of accumulated Parkoins for Player.
     * @param player player
     * @return number of Parkoins
     */
    public static double getParkoins(OfflinePlayer player) {
        return getPlayerConfig(player).getDouble("Parkoins");
    }

    /**
     * Increase the amount of Parkoins the Player has.
     * @param player player
     * @param amount amount to increase by
     */
    public static void increaseParkoins(OfflinePlayer player, double amount) {
        setParkoins(player, getParkoins(player) + amount);
    }

    /**
     * Set the number of Parkoins for Player.
     * @param player player
     * @param amount amount to set
     */
    public static void setParkoins(OfflinePlayer player, double amount) {
        savePlayerConfig(player, "Parkoins", amount);
    }

    /**
     * Get the existing Session Course name.
     * The name of the Course they were on when leaving the server.
     * @param player player
     * @return session course name
     */
    @Nullable
    public static String getExistingSessionCourseName(Player player) {
        return getPlayerConfig(player).getString("ExistingSessionCourseName");
    }

    /**
     * Player has existing Session Course name.
     * @param player player
     * @return player has existing session course name
     */
    public static boolean hasExistingSessionCourseName(Player player) {
        return getPlayerConfig(player).contains("ExistingSessionCourseName");
    }

    /**
     * Set the existing Session Course name.
     * @param player player
     * @param courseName course name
     */
    public static void setExistingSessionCourseName(Player player, String courseName) {
        savePlayerConfig(player, "ExistingSessionCourseName", courseName);
    }

    private static Json getPlayerConfig(OfflinePlayer player) {
        return new Json(player.getUniqueId().toString(), PLAYER_CONFIG_PATH);
    }

    private static void savePlayerConfig(OfflinePlayer player, String key, Object value) {
        Json json = getPlayerConfig(player);
        json.set(key, value);
    }

    private PlayerInfo() {
        throw new IllegalStateException("Utility class");
    }
}
