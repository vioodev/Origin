package software.vio.origin.punishment;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.bson.Document;
import org.bukkit.Bukkit;
import software.vio.origin.Origin;
import software.vio.origin.punishment.event.PunishmentEvent;
import software.vio.origin.user.UserData;
import software.vio.origin.util.CC;
import software.vio.origin.util.Msg;
import software.vio.origin.util.TimeUtil;

import java.util.Objects;
import java.util.UUID;
import java.util.stream.Stream;

@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
public class Punishment {

    private PunishmentType type;
    private UUID target, sender;
    private String reason;
    private long duration, issuedWhen;

    /**
     * Create a new punishment
     *
     * @param type     Punishment type (Mute, Kick, Ban, Blacklist)
     * @param target   UUID of player to punish
     * @param sender   UUID of player who issues the punishment
     * @param duration How long the punishment will last (unix time)
     * @param reason   Punishment reason
     */

    public Punishment(PunishmentType type, UUID target, UUID sender, String reason, long duration) {
        this.type = type;
        this.target = target;
        this.sender = sender;
        this.reason = reason;
        this.duration = duration;
    }

    public int getPower() {
        return this.type.ordinal();
    }

    public boolean isActive() {
        return this.issuedWhen + this.duration > System.currentTimeMillis() || this.type == PunishmentType.BLACKLIST;
    }

    /**
     * Issue the punishment
     */

    public void issue() {
        if (Stream.of(this.type, this.target, this.sender).anyMatch(Objects::isNull)) {
            throw new IllegalArgumentException("Missing parameters");
        }

        if (this.reason == null) this.reason = "N/A";
        this.issuedWhen = System.currentTimeMillis();

        // Calling this event mainly for other plugins to hook in and do stuff on punishments
        Bukkit.getServer().getPluginManager().callEvent(new PunishmentEvent(Bukkit.getPlayer(this.target), Bukkit.getPlayer(this.sender), this));
        Msg.broadcast(CC.RED + Bukkit.getPlayer(this.target).getName() + " was " + this.type.getMessage() + " for " + this.reason + (this.type == PunishmentType.MUTE || this.type == PunishmentType.BAN ? " for a duration of " + TimeUtil.getPrettyTime(this.duration).toLowerCase() : "") + ".");

        Document document = new Document();

        document.put("type", this.type.getName());
        document.put("targetUUID", this.target.toString());
        document.put("senderUUID", this.sender.toString());
        document.put("reason", this.reason);
        document.put("duration", this.duration);
        document.put("issuedWhen", this.issuedWhen);

        Origin.getInstance().getMongoService().getPunishments().insertOne(document);
        // Reload punishments from database
        UserData.getByUuid(this.target).loadPunishments();

        if (this.type != PunishmentType.MUTE) {
            String kickScreen = CC.RED + "You were " + this.getType().getMessage() + " from " + Msg.SERVER_NAME + ".\n" +
                    CC.RED + "Reason: " + this.getReason() + "\n" +
                    (this.type == PunishmentType.BAN ? CC.RED + "Expires in: " + TimeUtil.getPrettyTime(this.duration) + "\n" : "") +
                    "\n" +
                    CC.RED + (this.getType() == PunishmentType.BLACKLIST ? "You may not appeal this type of punishment." : this.type == PunishmentType.BAN ? "You can appeal this punishment at " + Msg.APPEAL_LINK + "." : "");

            Bukkit.getPlayer(this.target).kickPlayer(kickScreen);
        }
    }
}
