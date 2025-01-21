package iterator;

public interface Iterator<T> {
    T currentItem();
    void first();
    void next();
    boolean isDone();
}
