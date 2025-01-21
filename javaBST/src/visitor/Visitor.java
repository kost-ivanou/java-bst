package visitor;

import model.TreeModel;

public interface Visitor<T extends Comparable<T>> {
    void visit(TreeModel<? extends T > tree);
}
