package connect4;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

import static connect4.Program.readInt;

@RequiredArgsConstructor
public class HumanPlayer implements Player {

    @Getter
    private final PlayerNumber number;

    @Override
    public void makeMove(Board board) {
        List<Integer> availableColumns = board.getAvailableColumns();
        Integer col = readInt(availableColumns::contains, getPrompt(availableColumns));
        board.makeMove(getNumber(), new Column(col));
    }

    private String getPrompt(List<Integer> availableColumns) {
        return "Choose one of the following columns: " +
                availableColumns
                        .stream()
                        .map(Object::toString)
                        .collect(Collectors.joining(", "));
    }
}
