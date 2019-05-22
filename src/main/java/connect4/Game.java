package connect4;


import lombok.Getter;
import lombok.Setter;

public class Game {
    @Getter
    private final Board board;
    private final Player player1;
    private final Player player2;

    @Setter
    private boolean silent = false;

    public Game(Player player1, Player player2) {
        this(player1, player2, new Board());
    }

    public Game(Player player1, Player player2, Board board) {
        this.player1 = player1;
        this.player2 = player2;
        this.board = board;
    }

    public void play() {
        Player[] players = {player1, player2};
        PlayerNumber currentPlayer = player1.getNumber();
        while (board.getStatus().equals(GameStatus.IN_PROGRESS)) {
            sout(String.format("Player %d move", currentPlayer.getShownNumber()));
            sout("");
            sout(board);
            sout("");
            players[currentPlayer.getCodeNumber()].makeMove(board);
            currentPlayer = currentPlayer.getOpponent();
        }
    }

    private void sout(Object msg) {
        if (!silent) {
            System.out.println(msg);
        }
    }

    @Override
    public String toString() {
        switch (board.getStatus()) {
            case DRAW:
                return "Game ended with a draw.";
            case WON_BY_PLAYER_1:
                return "Game won by player 1!";
            case WON_BY_PLAYER_2:
                return "Game won by player 2!";
            default:
                return "Game in progress...";
        }
    }
}
