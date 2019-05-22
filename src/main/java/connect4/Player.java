package connect4;

public interface Player {

    PlayerNumber getNumber();

    void makeMove(Board board);
}
