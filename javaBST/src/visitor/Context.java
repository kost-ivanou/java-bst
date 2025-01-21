package visitor;

public interface Context<T extends Comparable<T>> {
    void accept(Visitor<T> visitor);
}
