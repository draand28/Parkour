package io.github.a5h73y.parkour.database;

/**
 * Representation model of a `Time` stored in the database.
 */
public class TimeEntry {

    private final String playerId;
    private final String playerName;
    private final long time;
    private final int deaths;

    public TimeEntry(String playerId, String playerName, long time, int deaths) {
        this.playerId = playerId;
        this.playerName = playerName;
        this.time = time;
        this.deaths = deaths;
    }

    /**
     * The player of the time result.
     * @return player name
     */
    public String getPlayerId() {
        return playerId;
    }

    public String getPlayerName() {
        return playerName;
    }

    /**
     * The total time taken of the time result.
     * @return time
     */
    public long getTime() {
        return time;
    }

    /**
     * The deaths accumulated of the time result.
     * @return deaths
     */
    public int getDeaths() {
        return deaths;
    }
}
