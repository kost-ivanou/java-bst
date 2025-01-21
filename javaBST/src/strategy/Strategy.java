package strategy;

import model.TreeModel;

public interface Strategy<T extends Comparable<T>> {
    T findMax(TreeModel<T> model);
}
