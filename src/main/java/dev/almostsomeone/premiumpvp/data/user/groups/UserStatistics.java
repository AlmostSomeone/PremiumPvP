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

    public void addKill(UUID killed) {
        killstreak.setInteger(killstreak.getInteger() + 1);
    }

    public Integer getAssists() {
        return assists.getInteger();
    }

    public void addAssist(UUID killed) {
        assists.setInteger(assists.getInteger() + 1);
    }

    public Integer getDeaths() {
        return deaths.getInteger();
    }

    public void addDeath(UUID died) {
        deaths.setInteger(deaths.getInteger() + 1);
        bestKillstreak.setInteger(killstreak.getInteger());
        killstreak.setInteger(0);
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
