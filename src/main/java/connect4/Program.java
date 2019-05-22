package connect4;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Scanner;
import java.util.function.Predicate;

public class Program {

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        Game g = new Game(readPlayer(PlayerNumber.PLAYER_1), readPlayer(PlayerNumber.PLAYER_2));
        g.play();
        System.out.println(g);
        System.out.println();
        System.out.println(g.getBoard());
    }

    private static Player readPlayer(PlayerNumber number) {
        Integer chosenPlayer = readInt(
                val -> val >= 1 && val <= 3,
                String.join("\n",
                        String.format("Choose player type of player no. %s", number.getShownNumber()),
                        "1 - human player",
                        "2 - mcts player",
                        "3 - dummy player"));
        switch (chosenPlayer) {
            case 1: return new HumanPlayer(number);
            case 2: return new MCTSPlayer(number, readMctsIterations());
            default: return new RandomPlayer(number);
        }
    }

    private static int readMctsIterations() {
        return readInt(val -> val > 1,
                "Enter the number of algorithm iterations.\n" +
                "It should be greater than 1");
    }

    static int readInt(Predicate<Integer> validator, String msg) {
        Integer value = null;
        while (value == null) {
            System.out.println(msg);
            try {
                value = scanner.nextInt();
                if (!validator.test(value))
                    value = null;
            } catch (Exception e) {
                printStacktrace(e);
            }
        }
        return value;
    }

    private static void printStacktrace(Exception e) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        e.printStackTrace(pw);
        System.out.println(sw.toString());
    }
}
