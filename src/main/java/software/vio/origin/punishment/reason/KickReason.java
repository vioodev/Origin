package software.vio.origin.punishment.reason;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum KickReason {
    PLACEHOLDER("Placeholder Reason", 0L);

    private String name;
    private long duration;
}
