package software.vio.origin.punishment;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum PunishmentType {
    MUTE("Mute", "muted"),
    KICK("Kick", "kicked"),
    BAN("Ban", "banned"),
    BLACKLIST("Blacklist", "blacklisted");

    public static PunishmentType getByName(String name) {
        return Arrays.stream(values()).filter(punishmentType -> punishmentType.getName().equals(name)).findFirst().orElse(null);
    }

    private String name;
    private String message;
}
