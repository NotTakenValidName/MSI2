package connect4;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Random;

@RequiredArgsConstructor
public class RandomPlayer implements Player {

    private final Random random = new Random();
    @Getter
    private final PlayerNumber number;

    @Override
    public void makeMove(Board board) {
        List<Integer> cols = board.getAvailableColumns();
        board.makeMove(getNumber(), new Column(cols.get(random.nextInt(cols.size()))));
    }
}
