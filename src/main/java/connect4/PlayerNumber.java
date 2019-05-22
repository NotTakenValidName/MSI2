package connect4;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum PlayerNumber {
    PLAYER_1(1, 0),
    PLAYER_2(2, 1);

    private final Integer shownNumber;
    private final Integer codeNumber;

    public PlayerNumber getOpponent() {
        return this.equals(PLAYER_1) ? PLAYER_2 : PLAYER_1;
    }
}
