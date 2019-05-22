package connect4;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Deque;
import java.util.LinkedList;

@RequiredArgsConstructor
public class MCTSPlayer implements Player {

    @Getter
    private final PlayerNumber number;
    private final Integer iterations;

    @Override
    public void makeMove(Board board) {
        TreeNode<Integer> treeRoot = new TreeNode<>();
        for (int i = 0; i < iterations; i++)
            makeIteration(board.clone(), treeRoot);
        Integer move = treeRoot.select();
        board.makeMove(getNumber(), new Column(move));
    }

    private void makeIteration(Board board, TreeNode<Integer> node) {
        Deque<TreeNode<Integer>> path = new LinkedList<>();
        PlayerNumber currentPlayer = getNumber();
        while (!node.isLeaf()) {
            path.push(node);
            node = moveDown(node, board, currentPlayer);
            currentPlayer = currentPlayer.getOpponent();
        }
        path.push(node);
        Double value = 0.5d;
        if (board.getStatus().equals(GameStatus.IN_PROGRESS)) {
            node.expand(board.getAvailableColumns());
            node = moveDown(node, board, currentPlayer);
            currentPlayer = currentPlayer.getOpponent();
            path.push(node);
            Game randomGame = new Game(
                    new RandomPlayer(currentPlayer),
                    new RandomPlayer(currentPlayer.getOpponent()),
                    board);
            randomGame.setSilent(true);
            randomGame.play();
            if (board.getStatus().wonBy(currentPlayer)) value = 1d;
            if (board.getStatus().wonBy(currentPlayer.getOpponent())) value = 0d;
        } else {
            if (board.getStatus().wonBy(currentPlayer)) value = 0d;
            if (board.getStatus().wonBy(currentPlayer.getOpponent())) value = 1d;
        }
        while (!path.isEmpty()) {
            path.pop().updateValue(value);
            value = 1 - value;
        }
    }

    private TreeNode<Integer> moveDown(TreeNode<Integer> node, Board workingCopy, PlayerNumber currentPlayer) {
        Integer col = node.select();
        node = node.getChild(col);
        workingCopy.makeMove(currentPlayer, new Column(col));
        return node;
    }
}
