package com.bug;

/**
 * Класс определения параметров элемента узла дерева
 */
public class CheckBoxElement {
    // Данные узла

    public boolean selected;
    public String text;
    public String name;
    public int type;
    // Конструктор

    public CheckBoxElement(boolean selected, String text, int type, String name) {
        this.selected = selected;
        this.text = text;
        this.type = type;
        this.name = name;
    }
}

