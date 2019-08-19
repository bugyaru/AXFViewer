package com.bug;

// Дерево с отображаением флажков в листьях 
import java.awt.*;
import static java.awt.Color.white;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.tree.*;

public class CheckBoxTree extends JTree {

    private static final long serialVersionUID = 1L;
    // Стандартный объект для отображения узлов
    private DefaultTreeCellRenderer renderer = new DefaultTreeCellRenderer();
    // Конструктор

    public CheckBoxTree(TreeModel model) {
        super(model);
        // Слушатель мыши
        addMouseListener(new MouseListener());
        addKeyListener(new KeyListener());
        // Определение собственного отображающего объекта
        setCellRenderer(new CheckBoxRenderer());
        setFocusCycleRoot(true);
    }
    // Объект отображения узлов дерева с использованием флажков

    class CheckBoxRenderer extends JCheckBox implements TreeCellRenderer {

        private static final long serialVersionUID = 1L;

        public CheckBoxRenderer() {
            // Флажок будет прозрачным
            setOpaque(false);
            setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/images/checked.png")));
            setBackground(new java.awt.Color(255, 255, 255));
        }
        // Метод получения компонента узла

        public Component getTreeCellRendererComponent(JTree tree, Object value, boolean selected,
                boolean expanded, boolean leaf, int row, boolean hasFocus) {
            // Проверка принадлежности узла к стандартной модели
            if (!(value instanceof DefaultMutableTreeNode)) {
                // Если нет, то используется стандартный объект
                return renderer.getTreeCellRendererComponent(tree, value, selected, expanded, leaf, row, hasFocus);
            }
            var data = ((DefaultMutableTreeNode) value).getUserObject();
            // Проверка, являются ли данные CheckBoxElement
            if (data instanceof CheckBoxElement) {
                var element = (CheckBoxElement) data;
                // Настройка флажка и текста
                setSelected(element.selected);
                if (element.type == 0) {
                    setText(element.name);
                    setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/element.png")));
                } else if (element.type == 1) {
                    setText(element.name + " = " + element.text);
                    setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/attribute.png")));
                } else if (element.type == 2) {
                    setText(element.text);
                    setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/value.png")));
                }
                this.setFocusPainted(hasFocus);
                if(selected){
                    setBackground(new Color(192, 252, 255));
                    
                }else{
                    setBackground(white);
                }
                return this;
            }
            // В противном случае метод возвращает стандартный объект
            return renderer.getTreeCellRendererComponent(tree, value, selected, expanded, leaf, row, hasFocus);
        }
    }
    // Слушатель событий мыши

    class MouseListener extends MouseAdapter {

        public void mousePressed(MouseEvent e) {
            // Путь к узлу
           //TreePath path =getSelectionPath();
           var path = getClosestPathForLocation(e.getX(), e.getY());
            if (path == null) {
                return;
            }
            var node = (DefaultMutableTreeNode) path.getLastPathComponent();
            // Проверка принадлежности узла к стандартной модели
            if (node.getUserObject() instanceof CheckBoxElement) {
                // Изменение состояния флажка
                var element = (CheckBoxElement) node.getUserObject();
                element.selected = !element.selected;
                repaint();
                
            }
        }
    }
    class KeyListener extends KeyAdapter {
        public void keyPressed(KeyEvent e) {
            var path =getSelectionPaths();
            if (path == null) {
                return;
            }
            for (TreePath path1 : path) {
                var node = (DefaultMutableTreeNode) path1.getLastPathComponent();
                // Проверка принадлежности узла к стандартной модели
                if (node.getUserObject() instanceof CheckBoxElement && (e.getKeyCode()==32||e.getKeyCode()==10)) {
                    // Изменение состояния флажка
                    var element = (CheckBoxElement) node.getUserObject();
                    element.selected = !element.selected;
                }
            }
            if (e.getKeyCode()==27)
                clearSelection();
            repaint();
        }
    }
}





































