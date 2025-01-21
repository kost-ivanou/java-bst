package strategy;

import model.TreeModel;

import java.util.Optional;

public class StreamStrategy<T extends Comparable<T>> implements Strategy<T> {
    @Override
    public T findMax(TreeModel<T> model) {
        return model.getTree().stream()
                .filter(element -> element != null)
                .max(Comparable::compareTo)
                .orElse(null); // Возвращает null, если дерево пустое
    }
}