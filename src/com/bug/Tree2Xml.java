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

/**
 *
 * @author bug
 */
import java.io.StringWriter;
import java.util.Collections;
import java.util.Enumeration;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeNode;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
//import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class Tree2Xml {

    public void convert(DefaultMutableTreeNode root) throws ParserConfigurationException {

        try {
            //System.setProperty("org.xml.sax.driver", "org.xml.sax.helpers.ParserFactory");
            //System.setProperty("org.xml.sax.driver", "org.apache.xerces.parsers.SAXParser");
            javax.xml.parsers.DocumentBuilderFactory factory = javax.xml.parsers.DocumentBuilderFactory.newInstance();
            org.w3c.dom.Document doc = factory.newDocumentBuilder().newDocument();
            createRoot(root, doc);
            for (Object child : Collections.list(((DefaultMutableTreeNode) root.getFirstChild()).children())) {
                parseTreeNode((TreeNode) child, doc.getFirstChild());
            }
            Transformer tf = TransformerFactory.newInstance().newTransformer();
            tf.setOutputProperty(OutputKeys.INDENT, "yes");
            tf.setOutputProperty(OutputKeys.METHOD, "xml");
            tf.setOutputProperty(OutputKeys.ENCODING, "UTF-8");

            DOMSource domSource = new DOMSource(doc);

            // StringWriter writer = new StringWriter();
            // StreamResult sr = new StreamResult(writer);
            StreamResult sr = new StreamResult(System.out);
            //System.out.println(doc);
            //StreamResult sr = new StreamResult(new File("TreeModel.xml"));
            tf.transform(domSource, sr);
            //String strResult = writer.toString();

            //System.out.println(strResult);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void createRoot(DefaultMutableTreeNode root, Document doc) {
        Element rootElement = doc.createElement(((CheckBoxElement) ((DefaultMutableTreeNode) root.getFirstChild()).getUserObject()).name);
        doc.appendChild(rootElement);
    }

    private void parseTreeNode(TreeNode treeNode, Node top) {
        try {
            Element parentElement = null;
            if (((CheckBoxElement) ((DefaultMutableTreeNode) treeNode).getUserObject()).type == 0) {
                parentElement = top.getOwnerDocument().createElement(((CheckBoxElement) ((DefaultMutableTreeNode) treeNode).getUserObject()).name);
                for (Object child : Collections.list(treeNode.children())) {
                    parentElement=checkAttr((DefaultMutableTreeNode) child, parentElement);
                    System.out.println(parentElement.getNodeName());
                    top.appendChild(parentElement);
                }
            }
            System.out.println(parentElement.getNodeName());
            top.appendChild(parentElement);
            for (Object child : Collections.list(treeNode.children())) {
                parseTreeNode((TreeNode) child, parentElement);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private Element checkAttr(DefaultMutableTreeNode treeNode, Element parentElement) {
        try {
            if (((CheckBoxElement) treeNode.getUserObject()).type == 2) {
                parentElement.setTextContent(((CheckBoxElement) ((DefaultMutableTreeNode) treeNode).getUserObject()).text);
            }
            if (((CheckBoxElement) ((DefaultMutableTreeNode) treeNode).getUserObject()).type == 1) {
                
                Attr attrName = parentElement.getOwnerDocument().createAttribute(((CheckBoxElement) ((DefaultMutableTreeNode) treeNode).getUserObject()).name);
                attrName.setNodeValue(((CheckBoxElement) ((DefaultMutableTreeNode) treeNode).getUserObject()).text);
                parentElement.getAttributes().setNamedItem(attrName);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return parentElement;
    }

}
