/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bug;

import javax.swing.tree.*;
import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;
//import org.xml.sax.*;
//import org.xml.sax.helpers.*;

/**
 *
 * @author bug
 */
class SAXTreeBuilder extends DefaultHandler{

       private DefaultMutableTreeNode currentNode = null;
       private DefaultMutableTreeNode previousNode = null;
       private DefaultMutableTreeNode rootNode = null;

       public SAXTreeBuilder(DefaultMutableTreeNode root){
              rootNode = root;
       }
       public void startDocument(){
              currentNode = rootNode;
       }
       public void endDocument(){
       }
       public void characters(char[] data,int start,int end){
              String str = new String(data,start,end);              
              if (!str.equals("") && Character.isLetterOrDigit(str.charAt(0)))
                  currentNode.add(new DefaultMutableTreeNode(new CheckBoxElement(false,str,2,"")));           
       }
       public void startElement(String uri,String qName,String lName,Attributes atts){
              previousNode = currentNode;
              currentNode = new DefaultMutableTreeNode(new CheckBoxElement(false,"",0,lName));
              //currentNode = new DefaultMutableTreeNode(lName);
              // Add attributes as child nodes //
              attachAttributeList(currentNode,atts);
              previousNode.add(currentNode);              
       }
       public void endElement(String uri,String qName,String lName){
              CheckBoxElement element=(CheckBoxElement)currentNode.getUserObject();
              if (currentNode.getUserObject() instanceof CheckBoxElement && element.name.equals(lName))
                  currentNode = (DefaultMutableTreeNode)currentNode.getParent();              
       }
       public DefaultMutableTreeNode getTree(){
              return rootNode;
       }

       private void attachAttributeList(DefaultMutableTreeNode node,Attributes atts){
               for (int i=0;i<atts.getLength();i++){
                    String name = atts.getLocalName(i);
                    String value = atts.getValue(name);
                    node.add(new DefaultMutableTreeNode(new CheckBoxElement(false,value,1,name)));
               }
       }

}

