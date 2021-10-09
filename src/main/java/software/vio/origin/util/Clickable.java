package software.vio.origin.util;

import lombok.Getter;
import lombok.NoArgsConstructor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

@Getter
@NoArgsConstructor
public class Clickable {

    private String message, hoverText, clickString;
    private ClickEvent.Action clickAction;
    private TextComponent textComponent;

    /**
     * Create a new clickable message
     *
     * @param message     Chat message to send
     * @param hoverText   Text displayed upon hovering above the chat message
     * @param clickAction Action that is performed upon clicking on the chat message
     * @param clickString String that is dealt with upon clicking on the chat message -> Can be a command, url & more
     */

    public Clickable(String message, String hoverText, ClickEvent.Action clickAction, String clickString) {
        this.message = message;
        this.hoverText = hoverText;
        this.clickAction = clickAction;
        this.clickString = clickString;

        this.textComponent = new TextComponent(message);
        this.textComponent.setHoverEvent(
                new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(hoverText).create())
        );
        this.textComponent.setClickEvent(
                new ClickEvent(clickAction, clickString)
        );
    }

    public Clickable setMessage(String message) {
        this.message = message;

        if (this.textComponent == null) {
            this.textComponent = new TextComponent(message);
        }
        else {
            this.textComponent.setText(message);
        }
        return this;
    }

    public Clickable setHoverText(String hoverText) {
        this.hoverText = hoverText;

        if (this.textComponent == null) {
            this.textComponent = new TextComponent(this.message == null ? "" : this.message);
        }
        this.textComponent.setHoverEvent(
                new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(hoverText).create())
        );
        return this;
    }

    public Clickable setClickAction(ClickEvent.Action clickAction) {
        this.clickAction = clickAction;

        if (this.textComponent == null) {
            this.textComponent = new TextComponent(this.message == null ? "" : this.message);
        }
        this.textComponent.setClickEvent(
                new ClickEvent(clickAction, clickString)
        );
        return this;
    }

    public Clickable setClickString(String clickString) {
        this.clickString = clickString;

        if (this.textComponent == null) {
            this.textComponent = new TextComponent(this.message == null ? "" : this.message);
        }
        this.textComponent.setClickEvent(
                new ClickEvent(ClickEvent.Action.RUN_COMMAND, clickString)
        );
        return this;
    }

    /**
     * Send the clickable message to a player
     *
     * @param player Player to send to
     */

    public void send(Player player) {
        player.spigot().sendMessage(this.textComponent);
    }

    /**
     * Send the clickable message to everyone
     */

    public void broadcast() {
        Bukkit.getOnlinePlayers().forEach(online -> online.spigot().sendMessage(this.textComponent));
        Msg.log(this.message);
    }
}
