package dev.almostsomeone.premiumpvp.data.user.groups;

import dev.almostsomeone.premiumpvp.data.DataContainer;
import dev.almostsomeone.premiumpvp.data.DataGroup;
import dev.almostsomeone.premiumpvp.data.DataObject;
import org.jetbrains.annotations.NotNull;

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

    public UserStatistics(@NotNull DataContainer dataContainer) {
        super(dataContainer);

        // Basic
        this.kills = new DataObject(this, "Kills", 0);
        this.assists = new DataObject(this, "Assists", 0);
        this.deaths = new DataObject(this, "Deaths", 0);

        // Killstreak
        this.killstreak = new DataObject(this, "Killstreak", 0);
        this.bestKillstreak = new DataObject(this, "BestKillstreak", 0);

        // Damage
        this.damageDealt = new DataObject(this, "DamageDealt", 0);
        this.damageReceived = new DataObject(this, "DamageReceived", 0);

        // Arrows
        this.arrowsHit = new DataObject(this, "ArrowsHit", 0);
        this.arrowsShot = new DataObject(this, "ArrowsShot", 0);

        // Others
        this.bountiesClaimed = new DataObject(this, "BountiesClaimed", 0);
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

    public void addKill(UUID killed) {
        this.killstreak.setInteger(this.killstreak.getInteger() + 1);
    }

    public Integer getAssists() {
        return this.assists.getInteger();
    }

    public void addAssist(UUID killed) {
        this.assists.setInteger(this.assists.getInteger() + 1);
    }

    public Integer getDeaths() {
        return this.deaths.getInteger();
    }

    public void addDeath(UUID died) {
        this.deaths.setInteger(this.deaths.getInteger() + 1);
        this.bestKillstreak.setInteger(this.killstreak.getInteger());
        this.killstreak.setInteger(0);
    }

    public Integer getKillstreak() {
        return this.killstreak.getInteger();
    }

    public Integer getBestKillstreak() {
        return this.bestKillstreak.getInteger();
    }

    public Integer getDamageDealt() {
        return damageDealt.getInteger();
    }

    public void setDamageDealt(Integer damageDealt) {
        this.damageDealt.setInteger(damageDealt);
    }

    public Integer getDamageReceived() {
        return damageReceived.getInteger();
    }

    public void setDamageReceived(Integer damageReceived) {
        this.damageReceived.setInteger(damageReceived);
    }

    public Integer getArrowsHit() {
        return arrowsHit.getInteger();
    }

    public void setArrowsHit(Integer arrowsHit) {
        this.arrowsHit.setInteger(arrowsHit);
    }

    public Integer getArrowsShot() {
        return arrowsShot.getInteger();
    }

    public void setArrowsShot(Integer arrowsShot) {
        this.arrowsShot.setInteger(arrowsShot);
    }

    public Integer getBountiesClaimed() {
        return bountiesClaimed.getInteger();
    }

    public void setBountiesClaimed(Integer bountiesClaimed) {
        this.bountiesClaimed.setInteger(bountiesClaimed);
    }
}
