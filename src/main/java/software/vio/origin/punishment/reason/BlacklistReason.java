package software.vio.origin.punishment.reason;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum BlacklistReason {
    PLACEHOLDER("Placeholder Reason", Long.MAX_VALUE);

    private String name;
    private long duration;
}
