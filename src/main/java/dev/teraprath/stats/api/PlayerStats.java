package dev.teraprath.stats.api;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import java.util.UUID;

public class PlayerStats {

    private final UUID uuid;
    private int kills;
    private int deaths;
    private int wins;
    private int loses;
    private int gamesPlayed;
    private int streak;
    private int gamePoints;

    public PlayerStats(@Nonnull UUID uuid) {
        this.uuid = uuid;
        this.kills = 0;
        this.deaths = 0;
        this.wins = 0;
        this.loses = 0;
        this.gamesPlayed = 0;
        this.streak = 0;
        this.gamePoints = 0;
    }

    public UUID getUniqueId() {
        return this.uuid;
    }

    public void setKills(@Nonnegative int amount) {
        this.kills = amount;
    }

    public int getKills() {
        return this.kills;
    }

    public void setDeaths(@Nonnegative int amount) {
        this.deaths = amount;
    }

    public int getDeaths() {
        return this.deaths;
    }

    public void setWins(@Nonnegative int amount) {
        this.wins = amount;
    }

    public int getWins() {
        return this.wins;
    }

    public void setLoses(@Nonnegative int amount) {
        this.loses = amount;
    }

    public int getLoses() {
        return this.loses;
    }

    public void setGamesPlayed(@Nonnegative int amount) {
        this.gamesPlayed = amount;
    }

    public int getGamesPlayed() {
        return this.gamesPlayed;
    }

    public void setStreak(@Nonnegative int amount) {
        this.streak = amount;
    }

    public int getStreak() {
        return this.streak;
    }

    public void setGamePoints(@Nonnegative int amount) {
        this.gamePoints = amount;
    }

    public int getGamePoints() {
        return this.gamePoints;
    }

    public void reset() {
        this.kills = 0;
        this.deaths = 0;
        this.wins = 0;
        this.loses = 0;
        this.gamesPlayed = 0;
        this.streak = 0;
        this.gamePoints = 0;
    }

}
