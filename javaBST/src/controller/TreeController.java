package controller;
import model.TreeModel;
import strategy.IterativeStrategy;
import strategy.StreamStrategy;
import view.TreeView;

import javax.swing.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class TreeController {
    private final TreeModel<Integer> treeModel;
    private final TreeView view;

    public TreeController(TreeModel<Integer> treeModel, TreeView view) {
        this.treeModel = treeModel;
        this.view = view;
        initController();
    }

    private void initController() {
        view.getAddButton().addActionListener(e -> addElementToTree());
        view.getClearButton().addActionListener(e -> clearTree());
        view.getRemoveButton().addActionListener(e -> removeSelectedElements());
        view.getFindMaxButton().addActionListener(e -> findMaxUsingIterative());
        view.getFindMaxStreamButton().addActionListener(e -> findMaxUsingStream());
        view.getShowArrayButton().addActionListener(e -> showTreeAsArray());
        view.getShowPathToMaxButton().addActionListener(e -> showPathToMax());
        view.getPrefixTraversalButton().addActionListener(e -> showPrefixTraversal());
        view.getOpenFileButton().addActionListener(e -> openTreeFromFile());
    }

    private void addElementToTree() {
        String input = view.getElemToAddTextField().getText().trim();
        if (!input.isEmpty()) {
            StringTokenizer st = new StringTokenizer(input);
            StringBuilder invalidInputs = new StringBuilder(); // Для хранения некорректных входных данных

            while (st.hasMoreTokens()) {
                String token = st.nextToken();
                // Проверяем, что токен содержит только цифры
                if (token.matches("\\d+")) {
                    int value = Integer.parseInt(token);
                    treeModel.add(value);
                } else {
                    invalidInputs.append(token).append(" "); // Добавляем некорректный токен
                }
            }

            if (invalidInputs.length() > 0) {
                System.out.println("Игнорированные некорректные значения: " + invalidInputs.toString().trim());
            }

            view.updateTreeDisplay(treeModel);
        }
    }

    private void clearTree() {
        treeModel.clear();
        view.updateTreeDisplay(treeModel);
    }

    private void removeSelectedElements() {
        String input = view.getElemToAddTextField().getText().trim();
        if (!input.isEmpty()) {
            StringTokenizer st = new StringTokenizer(input);
            while (st.hasMoreTokens()) {
                int value = Integer.parseInt(st.nextToken());
                treeModel.remove(value);
            }
        }
        view.updateTreeDisplay(treeModel);
    }

    private void findMaxUsingIterative() {
        IterativeStrategy<Integer> iterativeStrategy = new IterativeStrategy<>();
        Integer max = iterativeStrategy.findMax(treeModel);
        view.getResultTextField().setText(max != null ? max.toString() : "Tree is empty");
    }

    private void findMaxUsingStream() {
        StreamStrategy<Integer> streamStrategy = new StreamStrategy<>();
        Integer max = streamStrategy.findMax(treeModel);
        view.getResultTextField().setText(max != null ? max.toString() : "Tree is empty");
    }

    private void showTreeAsArray() {
        StringBuilder arrayRepresentation = new StringBuilder();
        for (Integer value : treeModel.getTree()) {
            arrayRepresentation.append(value != null ? value : 0).append(" ");
        }
        view.getResultTextField().setText(arrayRepresentation.toString().trim());
    }

    private void showPathToMax() {
        String path = treeModel.moveToMax();
        view.getResultTextField().setText(path.isEmpty() ? "Tree is empty" : path);
    }

    private void showPrefixTraversal() {
        StringBuilder prefixTraversal = new StringBuilder();
        prefixTraversal(treeModel, 0, prefixTraversal);
        view.getResultTextField().setText(prefixTraversal.toString().trim());
    }

    private void prefixTraversal(TreeModel<Integer> model, int index, StringBuilder sb) {
        if (index < model.size() && model.getTree().get(index) != null) {
            sb.append(model.getTree().get(index)).append(" ");
            prefixTraversal(model, 2 * index + 1, sb); // Левый сын
            prefixTraversal(model, 2 * index + 2, sb); // Правый сын
        }
    }

    private void openTreeFromFile() {
        JFileChooser fileChooser = new JFileChooser();
        int returnValue = fileChooser.showOpenDialog(view);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            StringBuilder fileContents = new StringBuilder();
            try (BufferedReader br = new BufferedReader(new FileReader(selectedFile))) {
                String line;
                while ((line = br.readLine()) != null) {
                    StringTokenizer st = new StringTokenizer(line);
                    while (st.hasMoreTokens()) {
                        int value = Integer.parseInt(st.nextToken());
                        treeModel.add(value);
                        fileContents.append(value).append(" "); // Сохраняем значения для отображения
                    }
                }
                view.updateTreeDisplay(treeModel);
                view.getResultTextField().setText(fileContents.toString().trim()); // Отображаем в resultTextField
            } catch (IOException | NumberFormatException e) {
                view.getResultTextField().setText("Error loading tree: " + e.getMessage());
            }
        }
    }
}