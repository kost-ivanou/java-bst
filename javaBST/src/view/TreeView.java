package view;

import controller.TreeController;
import model.TreeModel;

import javax.swing.*;
import javax.swing.plaf.ColorUIResource;
import java.awt.*;

public class TreeView extends JFrame {

    JList<Integer> treeList;
    TreeModel<Integer> treeModel;
    JTextField elemToAddTextField;

    JButton addButton;
    JButton clearButton;
    JButton removeButton;
    JButton findMaxButton;
    JButton findMaxStreamButton;
    JButton showArrayButton;
    JButton showPathToMaxButton;
    JButton prefixTraversalButton;
    JButton openFileButton;

    JLabel treeOperationsLabel;
    JTextField resultTextField;

    public TreeView(String title) {
        super(title);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new GridBagLayout());

        Font largerFont = new Font("Dialog", Font.BOLD, 16);
        UIManager.put("Button.focus", new ColorUIResource(new Color(0, 0, 0, 0)));

        treeModel = new TreeModel<>();
        treeList = treeModel.toJList();
        elemToAddTextField = new JTextField();

        addButton = new JButton("Add Element");
        clearButton = new JButton("Clear");
        removeButton = new JButton("Remove Element");
        findMaxButton = new JButton("Find Max (Iterative)");
        findMaxStreamButton = new JButton("Find Max (Stream)");
        showArrayButton = new JButton("Show Array");
        showPathToMaxButton = new JButton("Show Path to Max");
        prefixTraversalButton = new JButton("Prefix Traversal");
        openFileButton = new JButton("Open Tree from File");

        treeOperationsLabel = new JLabel("Tree Operations");
        treeOperationsLabel.setFont(largerFont);

        resultTextField = new JTextField();
        resultTextField.setEditable(false);
        resultTextField.setPreferredSize(new Dimension(300, 100));

        JScrollPane scrollPane = new JScrollPane(treeList);
        scrollPane.setPreferredSize(new Dimension(150, 200));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.CENTER;

        // Добавляем JScrollPane
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridheight = 5;
        add(scrollPane, gbc);

        // Добавляем текстовое поле для ввода элемента
        gbc.gridy = 5;
        gbc.gridheight = 1; // Сбрасываем высоту
        add(elemToAddTextField, gbc);

        // Добавляем кнопки
        gbc.gridy = 6;
        add(addButton, gbc);
        gbc.gridy = 7;
        add(clearButton, gbc);
        gbc.gridy = 8;
        add(removeButton, gbc);
        gbc.gridy = 9;
        add(showArrayButton, gbc);
        gbc.gridy = 10;
        add(showPathToMaxButton, gbc);
        gbc.gridy = 11;
        add(prefixTraversalButton, gbc);
        gbc.gridy = 12;
        add(openFileButton, gbc);
        gbc.gridy = 13;
        add(findMaxButton, gbc);
        gbc.gridy = 14;
        add(findMaxStreamButton, gbc);

        // Добавляем метку операций
        gbc.gridy = 15;
        add(treeOperationsLabel, gbc);

        gbc.gridy = 16;
        add(resultTextField, gbc);

        // Инициализируем контроллер
        new TreeController(treeModel, this);
    }

    public JButton getAddButton() {
        return addButton;
    }

    public JButton getClearButton() {
        return clearButton;
    }

    public JButton getRemoveButton() {
        return removeButton;
    }

    public JButton getFindMaxButton() {
        return findMaxButton;
    }

    public JButton getFindMaxStreamButton() {
        return findMaxStreamButton;
    }

    public JButton getShowArrayButton() {
        return showArrayButton;
    }

    public JButton getShowPathToMaxButton() {
        return showPathToMaxButton;
    }

    public JButton getPrefixTraversalButton() {
        return prefixTraversalButton;
    }

    public JButton getOpenFileButton() {
        return openFileButton;
    }

    public JTextField getElemToAddTextField() {
        return elemToAddTextField;
    }

    public JTextField getResultTextField() {
        return resultTextField;
    }

    public JList<Integer> getTreeList() {
        return treeList;
    }

    public void updateTreeDisplay(TreeModel<Integer> model) {
        treeList.setModel(model.toJList().getModel());
    }
}