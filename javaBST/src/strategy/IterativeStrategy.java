package strategy;

import model.TreeModel;

import java.util.LinkedList;
import java.util.Queue;

public class IterativeStrategy<T extends Comparable<T>> implements Strategy<T> {
    @Override
    public T findMax(TreeModel<T> model) {
        if (model.getTree().isEmpty()) {
            return null; // Если дерево пустое, возвращаем null
        }

        T max = model.getTree().get(0); // Начинаем с корня
        Queue<Integer> queue = new LinkedList<>(); // Используем очередь для обхода
        queue.add(0); // Начинаем с корня (индекс 0)

        while (!queue.isEmpty()) {
            int index = queue.poll(); // Извлекаем индекс текущего узла

            if (index < model.getTree().size()) {
                T currentValue = model.getTree().get(index);
                if (currentValue != null && currentValue.compareTo(max) > 0) {
                    max = currentValue; // Обновляем максимум
                }


                int leftChildIndex = 2 * index + 1;
                int rightChildIndex = 2 * index + 2;

                if (leftChildIndex < model.getTree().size()) {
                    queue.add(leftChildIndex);
                }
                if (rightChildIndex < model.getTree().size()) {
                    queue.add(rightChildIndex);
                }
            }
        }

        return max; // Возвращаем найденный максимум
    }
}