package model;

import iterator.Aggregate;
import strategy.Strategy;
import visitor.Context;

import iterator.Iterator;
import visitor.Visitor;

import javax.swing.*;
import javax.swing.DefaultListModel;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

public class TreeModel<T extends Comparable<T>> implements Aggregate<T>, Context<T> {
    private ArrayList<T> tree;
    private Strategy<T> strategy;

    public TreeModel() {
        tree = new ArrayList<>();
    }

    public TreeModel(TreeModel<? extends T> otherTree) {
        tree = new ArrayList<>(otherTree.tree);
    }

    public TreeModel(T... elements) {
        tree = new ArrayList<>();
        for (T element : elements) {
            add(element);
        }
    }

    public int size() {
        return tree.size();
    }

    public boolean isEmpty() {
        return tree.isEmpty();
    }

    public void clear() {
        tree.clear();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TreeModel<?> treeModel = (TreeModel<?>) o;
        return Objects.equals(tree, treeModel.tree);
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        Iterator<T> iterator = createIterator();
        while (!iterator.isDone()) {
            stringBuilder.append(iterator.currentItem()).append(" ");
            iterator.next();
        }
        return stringBuilder.toString().trim();
    }

    public JList<T> toJList() {
        DefaultListModel<T> listModel = new DefaultListModel<>();
        for (T element : tree) {
            listModel.addElement(element);
        }
        return new JList<>(listModel);
    }

    public void add(T value) {
        // Проверка, что value не null и является числом
        if (value == null || !(value instanceof Number) || ((Number) value).doubleValue() <= 0) {
            return; // Игнорируем некорректные значения
        }

        // Если дерево пустое, добавляем элемент
        if (tree.isEmpty()) {
            tree.add(value);
        } else {
            addRecursive(value, 0);
        }
    }

    private void addRecursive(T value, int index) {

        while (index >= tree.size()) {
            tree.add(null);
        }


        if (tree.get(index) == null) {
            tree.set(index, value);
        } else {

            if (value.compareTo(tree.get(index)) < 0) {
                addRecursive(value, 2 * index + 1); // Левый сын
            }

            else if (value.compareTo(tree.get(index)) > 0) {
                addRecursive(value, 2 * index + 2); // Правый сын
            }

        }
    }

    public void cleanTree() {
        while (!tree.isEmpty() && tree.get(tree.size() - 1) == null) {
            tree.remove(tree.size() - 1);
        }
    }

    public void remove(T value) {
        if (isEmpty()) {
            throw new IllegalStateException("Tree is empty");
        }
        removeRecursive(value, 0);
    }

    private void removeRecursive(T value, int index) {
        if (index >= tree.size() || tree.get(index) == null) {
            return;
        }

        if (tree.get(index).equals(value)) {
            tree.set(index, null);
        } else if (value.compareTo(tree.get(index)) < 0) {
            removeRecursive(value, 2 * index + 1);
        } else {
            removeRecursive(value, 2 * index + 2);
        }
    }

    public void addAll(Collection<? extends T> collection) {
        for (T element : collection) {
            add(element);
        }
    }

    @Override
    public Iterator<T> createIterator() {
        return new Iterator<>() {
            private int index = 0;

            @Override
            public void first() {
                index = 0;
            }

            @Override
            public boolean isDone() {
                return index >= size();
            }

            @Override
            public void next() {
                index++;
            }

            @Override
            public T currentItem() {
                return tree.get(index);
            }
        };
    }

    @Override
    public void accept(Visitor<T> visitor) {
        visitor.visit(this);
    }

    public ArrayList<T> getTree() {
        return tree;
    }

    public void setStrategy(Strategy<T> strategy) {
        this.strategy = strategy;
    }

    public T findMax() {
        if (strategy == null) {
            throw new IllegalStateException("Strategy not set");
        }
        return strategy.findMax(this);
    }

    public String moveToMax() {
        StringBuilder path = new StringBuilder();
        moveToMax(0, path);
        return path.toString().trim();
    }

    private void moveToMax(int index, StringBuilder path) {
        if (index < tree.size() && tree.get(index) != null) {
            path.append(tree.get(index)).append(" ");
            moveToMax(2 * index + 2, path);
        }
    }
}