package dev.almostsomeone.premiumpvp.data.user.groups;

import dev.almostsomeone.premiumpvp.data.DataContainer;
import dev.almostsomeone.premiumpvp.data.DataGroup;
import dev.almostsomeone.premiumpvp.data.DataObject;

import javax.annotation.Nonnull;
import java.util.UUID;

public class UserStatistics extends DataGroup {

    private final DataObject
            // Basic
            kills,
            assists,
            deaths,

            // Killstreak
            killstreak,
            bestKillstreak,

            // Damage
            damageDealt,
            damageReceived,

            // Arrows
            arrowsHit,
            arrowsShot,

            // Others
            bountiesClaimed;

    public UserStatistics(@Nonnull DataContainer dataContainer) {
        super(dataContainer);

        // Basic
        kills = new DataObject(this, "Kills", 0);
        assists = new DataObject(this, "Assists", 0);
        deaths = new DataObject(this, "Deaths", 0);

        // Killstreak
        killstreak = new DataObject(this, "Killstreak", 0);
        bestKillstreak = new DataObject(this, "BestKillstreak", 0);

        // Damage
        damageDealt = new DataObject(this, "DamageDealt", 0);
        damageReceived = new DataObject(this, "DamageReceived", 0);

        // Arrows
        arrowsHit = new DataObject(this, "ArrowsHit", 0);
        arrowsShot = new DataObject(this, "ArrowsShot", 0);

        // Others
        bountiesClaimed = new DataObject(this, "BountiesClaimed", 0);
    }

    @Override
    public @Nonnull String tableName() {
        return "statistics";
    }

    @Override
    public boolean inDatabase() {
        return true;
    }

    public Integer getKills() {
        return kills.getInteger();
    }

    public Integer getAssists() {
        return assists.getInteger();
    }

    public Integer getDeaths() {
        return deaths.getInteger();
    }

    public Integer getKillstreak() {
        return killstreak.getInteger();
    }

    public Integer getBestKillstreak() {
        return bestKillstreak.getInteger();
    }

    public Integer getDamageDealt() {
        return damageDealt.getInteger();
    }

    public Integer getDamageReceived() {
        return damageReceived.getInteger();
    }

    public Integer getArrowsHit() {
        return arrowsHit.getInteger();
    }

    public Integer getArrowsShot() {
        return arrowsShot.getInteger();
    }

    public Integer getBountiesClaimed() {
        return bountiesClaimed.getInteger();
    }
}
