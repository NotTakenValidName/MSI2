package connect4;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.val;

import java.util.*;

@Getter
@RequiredArgsConstructor
public class TreeNode<T> {

    public static final int EXPLORATION = 2;
    private Double value = 0d;
    private Integer simulations = 0;
    private final Random r = new Random();

    private Map<T, TreeNode<T>> children;

    public void expand(Collection<T> actions) {
        children = new HashMap<>();
        actions.forEach(act -> children.put(act, new TreeNode<>()));
    }

    public TreeNode<T> getChild(T key) {
        return children.get(key);
    }

    public T select() {
        List<T> bestKeys = new LinkedList<>();
        Double bestValue = Double.NEGATIVE_INFINITY;
        for (val entry: children.entrySet()) {
            Double uct = countUct(entry.getValue());
            if (uct > bestValue) {
                bestKeys = new LinkedList<>();
                bestKeys.add(entry.getKey());
                bestValue = uct;
            } else if (uct.equals(bestValue)) {
                bestKeys.add(entry.getKey());
            }
        }
        return bestKeys.get(r.nextInt(bestKeys.size()));
    }

    private double countUct(TreeNode childNode) {
        return (childNode.value / childNode.getPositiveSimulations())
                + Math.sqrt(EXPLORATION * Math.log(getPositiveSimulations()) / childNode.getPositiveSimulations());
    }

    private int getPositiveSimulations() {
        return Math.max(simulations, 1);
    }

    public boolean isLeaf() {
        return children == null;
    }

    public void updateValue(double change) {
        value += change;
        simulations += 1;
    }
}
