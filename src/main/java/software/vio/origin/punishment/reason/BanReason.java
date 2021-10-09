package software.vio.origin.punishment.reason;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.concurrent.TimeUnit;

@Getter
@AllArgsConstructor
public enum BanReason {
    PLACEHOLDER("Placeholder Reason", TimeUnit.DAYS.toMillis(3L));

    private String name;
    private long duration;
}
