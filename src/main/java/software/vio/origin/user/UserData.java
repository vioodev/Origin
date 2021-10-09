package software.vio.origin.user;

import com.mongodb.client.FindIterable;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.ReplaceOptions;
import lombok.Getter;
import lombok.Setter;
import org.bson.Document;
import org.bukkit.Bukkit;
import software.vio.origin.Origin;
import software.vio.origin.punishment.Punishment;
import software.vio.origin.punishment.PunishmentType;
import software.vio.origin.rank.Rank;

import java.util.*;
import java.util.stream.Stream;

@Getter
@Setter
public class UserData {

    // User data is stored alongside the UUID of the respective player
    @Getter
    private static final Map<UUID, UserData> users = new HashMap<>();
    private final UUID uuid;
    // Name of the user (stored in database)
    private String name;
    // Rank of the user (stored in database)
    private Rank rank;
    // Whether the user is in staff mode or not
    private boolean staffMode;
    // Punishments of the user (stored in database)
    private List<Punishment> punishments;

    /**
     * Create a new user data object
     *
     * @param uuid UUID of the respective player
     */

    public UserData(UUID uuid) {
        this.uuid = uuid;

        this.punishments = new ArrayList<>();

        this.loadData();
        this.loadPunishments();
    }

    /**
     * Get the user data of player by their UUID
     *
     * @param uuid UUID of player
     * @return User data of the player
     */

    public static UserData getByUuid(UUID uuid) {
        UserData user = users.get(uuid);

        if (user == null) {
            user = new UserData(uuid);
            users.put(uuid, user);
        }
        return user;
    }

    /**
     * Get a pretty-printed name of the user
     *
     * @return Prefix and colored name
     */

    public String getPrettyName() {
        return this.rank.getPrefix() + this.getRank().getColor() + this.name;
    }

    /**
     * Get all active punishments of a user
     *
     * @return Stream of all active punishments
     */

    public Stream<Punishment> getActivePunishments() {
        return this.punishments.stream()
                .filter(Punishment::isActive);
    }

    /**
     * Get all active punishments of a user filtered by type
     *
     * @param punishmentTypes Punishment types to filter
     * @return Stream of all active punishments filtered by type
     */

    public Stream<Punishment> getActivePunishments(PunishmentType... punishmentTypes) {
        return this.getActivePunishments()
                .filter(punishment -> Arrays.asList(punishmentTypes).contains(punishment.getType()));
    }

    /**
     * Load a user's data from database
     */

    private void loadData() {
        Document document = Origin.getInstance().getMongoService().getUsers()
                .find(Filters.eq("uuid", this.uuid.toString())).first();

        if (document == null) {
            this.setDefaults();
            this.saveData();
            return;
        }
        this.name = document.getString("name");
        this.rank = Rank.getByName(document.getString("rank"));
    }

    /**
     * Save a user's data to database
     */

    public void saveData() {
        Document document = new Document();

        document.put("uuid", this.uuid.toString());
        document.put("name", this.name);
        document.put("rank", this.rank.getName());

        Origin.getInstance().getMongoService().getUsers().replaceOne(Filters.eq("uuid", this.uuid.toString()), document, new ReplaceOptions().upsert(true));
    }

    /**
     * Set user data defaults
     */

    private void setDefaults() {
        this.name = (Bukkit.getPlayer(this.uuid) == null ? Bukkit.getOfflinePlayer(this.uuid).getName() : Bukkit.getPlayer(this.uuid).getName());
        this.rank = Rank.DEFAULT;
    }

    /**
     * Load punishments from database
     */

    public void loadPunishments() {
        this.punishments.clear();
        FindIterable<Document> documents = Origin.getInstance().getMongoService().getPunishments().find(Filters.eq("targetUUID", this.uuid.toString()));

        for (Document document : documents) {
            Punishment punishment = new Punishment();

            punishment.setType(PunishmentType.getByName(document.getString("type")));
            punishment.setTarget(UUID.fromString(document.getString("targetUUID")));
            punishment.setSender(UUID.fromString(document.getString("senderUUID")));
            punishment.setReason(document.getString("reason"));
            punishment.setDuration(document.getLong("duration"));
            punishment.setIssuedWhen(document.getLong("issuedWhen"));

            this.punishments.add(punishment);
        }
    }
}
