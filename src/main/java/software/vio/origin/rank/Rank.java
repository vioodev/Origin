package software.vio.origin.rank;

import software.vio.origin.util.CC;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@AllArgsConstructor
public enum Rank {
    DEFAULT("Default", "", CC.WHITE),

    GOLD("Gold", "&7[&6Gold&7] ", CC.GOLD),
    DIAMOND("Diamond", "&7[&bDiamond&7] ", CC.AQUA),

    MEDIA("Media", "&7[&dMedia&7] ", CC.LIGHT_PURPLE),
    FAMOUS("Famous", "&7[&5Famous&7] ", CC.DARK_PURPLE),
    PARTNER("Partner", "&7[&ePartner&7] ", CC.YELLOW),

    TRAINEE("Trainee", "&7[&aTrainee&7] ", CC.GREEN),
    MOD("Mod", "&7[&3Mod&7] ", CC.DARK_AQUA),
    SENIOR_MOD("SrMod", "&7[&5SrMod&7] ", CC.DARK_PURPLE),
    MANAGER("Manager", "&7[&dManager&7] ", CC.LIGHT_PURPLE),
    ADMIN("Admin", "&7[&cAdmin&7] ", CC.RED),
    DEVELOPER("Developer", "&7[&bDeveloper&7] ", CC.AQUA),
    OWNER("Owner", "&7[&4Owner&7] ", CC.DARK_RED);

    public static Rank getByName(String name) {
        return Arrays.stream(values()).filter(rank -> rank.getName().equals(name)).findFirst().orElse(null);
    }

    @Getter
    private final String name;
    private final String prefix, color;

    public String getPrefix() {
        return CC.translate(this.prefix);
    }

    public String getColor() {
        return CC.translate(this.color);
    }

    public int getPower() {
        return this.ordinal();
    }
}
