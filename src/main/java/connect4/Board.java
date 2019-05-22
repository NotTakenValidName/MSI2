package connect4;

import lombok.Getter;
import lombok.Setter;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Board {
    private static final int ROWS_NUM = 6;
    private static final int COLS_NUM = 7;
    private static final int WINING_LENGTH = 4;

    private final Integer[][] boardState = new Integer[ROWS_NUM][COLS_NUM];

    @Getter @Setter
    private GameStatus status = GameStatus.IN_PROGRESS;

    public List<Integer> getAvailableColumns() {
        return IntStream
                .range(0, COLS_NUM)
                .filter(index -> isEmpty(ROWS_NUM - 1, index))
                .boxed()
                .collect(Collectors.toList());
    }

    public boolean isEmpty(int row, int col) {
        return boardState[row][col] == null;
    }

    public void makeMove(PlayerNumber playerNumber, Column selectedColumn) {
        OptionalInt rowNumber = IntStream.range(0, ROWS_NUM)
                .filter(index -> isEmpty(index, selectedColumn.getNumber()))
                .findFirst();
        if (!rowNumber.isPresent()) {
            System.out.println("Caution!");
            System.out.println("Available columns: " +
                    getAvailableColumns().stream().map(Object::toString).collect(Collectors.joining(", ")));
            System.out.println("Trying to use column no. " + selectedColumn.getNumber());
        }
        int row = rowNumber.getAsInt();
        boardState[row][selectedColumn.getNumber()] = playerNumber.getShownNumber();
        if (checkWin(row, selectedColumn.getNumber())) {
            setStatus(GameStatus.WON_BY_PLAYER_1.wonBy(playerNumber)
                    ? GameStatus.WON_BY_PLAYER_1
                    : GameStatus.WON_BY_PLAYER_2);
        }
        else if (getAvailableColumns().size() == 0) {
            setStatus(GameStatus.DRAW);
        }
    }

    public Board clone() {
        Board copy = new Board();
        for (int i = 0; i < ROWS_NUM; i++)
            for (int j = 0; j < COLS_NUM; j++)
                copy.boardState[i][j] = boardState[i][j];
        return copy;
    }

    private boolean checkWin(int rowNumber, int columnNumber) {
        return vertical4(rowNumber, columnNumber)
                || horizontal4(rowNumber, columnNumber)
                || slash4(rowNumber, columnNumber)
                || backslash4(rowNumber, columnNumber);
    }

    private boolean backslash4(int rowNumber, int columnNumber) {
        return countFieldsWithSameColors(rowNumber, columnNumber, r -> r - 1, c -> c + 1) +
                countFieldsWithSameColors(rowNumber, columnNumber, r -> r + 1, c -> c - 1) >= WINING_LENGTH - 1;
    }

    private boolean slash4(int rowNumber, int columnNumber) {
        return countFieldsWithSameColors(rowNumber, columnNumber, r -> r - 1, c -> c - 1) +
                countFieldsWithSameColors(rowNumber, columnNumber, r -> r + 1, c -> c + 1) >= WINING_LENGTH - 1;
    }

    private boolean horizontal4(int rowNumber, int columnNumber) {
        return countFieldsWithSameColors(rowNumber, columnNumber, r -> r, c -> c - 1) +
                countFieldsWithSameColors(rowNumber, columnNumber, r -> r, c -> c + 1) >= WINING_LENGTH - 1;
    }

    private boolean vertical4(int rowNumber, int columnNumber) {
        return countFieldsWithSameColors(rowNumber, columnNumber, r -> r - 1, c -> c) >= WINING_LENGTH - 1;
    }

    private int countFieldsWithSameColors(int rowNumber,
                                          int columnNumber,
                                          Function<Integer, Integer> rowsIterator,
                                          Function<Integer, Integer> colsIterator) {
        int sameColorsCounter = 0;
        Integer playerNum = boardState[rowNumber][columnNumber];
        for (int i = 1; i < WINING_LENGTH; i++) {
            rowNumber = rowsIterator.apply(rowNumber);
            columnNumber = colsIterator.apply(columnNumber);
            if (!playerNum.equals(getField(rowNumber, columnNumber).getValue())) {
                break;
            }
            sameColorsCounter++;
        }
        return sameColorsCounter;
    }

    private Field getField(int row, int col) {
        if (row < 0 || col < 0 || row >= ROWS_NUM || col >= COLS_NUM)
            return Field.builder().row(row).column(col).isValid(false).build();
        return Field.builder()
                .row(row)
                .column(col)
                .isValid(true)
                .value(boardState[row][col])
                .build();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = ROWS_NUM - 1; i >= 0; i--) {
            sb.append("| ");
            for (int j = 0; j < COLS_NUM; j++) {
                sb.append(boardState[i][j] != null ? boardState[i][j] : "-");
                sb.append(" ");
            }
            sb.append("|\n");
        }
        return sb.toString();
    }
}
