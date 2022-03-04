package dev.almostsomeone.premiumpvp.data.user.groups;

import dev.almostsomeone.premiumpvp.data.DataContainer;
import dev.almostsomeone.premiumpvp.data.DataGroup;
import dev.almostsomeone.premiumpvp.data.DataObject;
import org.jetbrains.annotations.NotNull;

public class UserStatistics extends DataGroup {

    private DataObject kills,
            assists,
            deaths,
            killstreak,
            bestKillstreak;

    public UserStatistics(@NotNull DataContainer dataContainer) {
        super(dataContainer);

        this.kills = new DataObject(this, "Kills", 0);
        this.assists = new DataObject(this, "Assists", 0);
        this.deaths = new DataObject(this, "Deaths", 0);
        this.killstreak = new DataObject(this, "Killstreak", 0);
        this.bestKillstreak = new DataObject(this, "BestKillstreak", 0);

        //TODO HashMap BetweenStats? Kills, Assists, Deaths
    }

    @Override
    public @NotNull String tableName() {
        return "statistics";
    }

    @Override
    public boolean inDatabase() {
        return true;
    }

    public Integer getKills() {
        return this.kills.getInteger();
    }

    public Integer getAssists() {
        return this.assists.getInteger();
    }

    public Integer getDeaths() {
        return this.deaths.getInteger();
    }

    public Integer getKillstreak() {
        return this.killstreak.getInteger();
    }

    public Integer getBestKillstreak() {
        return this.bestKillstreak.getInteger();
    }
}
