package connect4;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Field {
    private final Integer row;
    private final Integer column;
    private final Integer value;
    private final boolean isValid;
}
