/*
 * Copyright (C) 2019 bug
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.bug;

import java.util.Collections;
import javax.swing.tree.DefaultMutableTreeNode;

/**
 *
 * @author bug
 */
public class ComboActions {

    DefaultMutableTreeNode root;
    DefaultMutableTreeNode node;
    private int match = 0;

    public ComboActions() {

    }

    public ComboActions(DefaultMutableTreeNode root) {
        this.root = root;
    }

    public DefaultMutableTreeNode getRoot() {
        return root;
    }

    public void setRoot(DefaultMutableTreeNode root) {
        this.root = root;
    }

    public DefaultMutableTreeNode getNode() {
        return node;
    }

    public void setNode(DefaultMutableTreeNode node) {
        this.node = node;
    }

    public DefaultMutableTreeNode segmentsSelected() {
        int layer = 0;
        match = 0;
        return nodeActionMetaEx((DefaultMutableTreeNode) root.getFirstChild(), match,true);
    }

    private DefaultMutableTreeNode nodeActionMetaEx(DefaultMutableTreeNode worknode, int layer,boolean flag) {
        if (worknode.getUserObject() instanceof CheckBoxElement) {
            if (((CheckBoxElement) worknode.getUserObject()).name.equals("MAObject")) {
                match++;
            }
            if (match == 1 && ((CheckBoxElement) worknode.getUserObject()).name.equals("Meta")) {
                ((CheckBoxElement) worknode.getUserObject()).selected = false;
                 for (Object child : Collections.list(worknode.children())) {
                    nodeActionMetaEx((DefaultMutableTreeNode) child, match,false);
                }
           } else {
                ((CheckBoxElement) worknode.getUserObject()).selected = flag;
                for (Object child : Collections.list(worknode.children())) {
                    nodeActionMetaEx((DefaultMutableTreeNode) child, match,flag);
                }
            }
        }
        return worknode;
    }
}
