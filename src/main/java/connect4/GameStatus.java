package connect4;

public enum GameStatus {
    WON_BY_PLAYER_1,
    WON_BY_PLAYER_2,
    DRAW,
    IN_PROGRESS;

    public boolean wonBy(PlayerNumber playerNumber) {
        return
                (this.equals(WON_BY_PLAYER_1) && playerNumber.equals(PlayerNumber.PLAYER_1))
                || (this.equals(WON_BY_PLAYER_2) && playerNumber.equals(PlayerNumber.PLAYER_2));
    }
}
