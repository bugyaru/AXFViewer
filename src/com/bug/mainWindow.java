/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bug;

import java.io.File;
import java.io.FileInputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
//import org.apache.xerces.parsers.*;
import org.xml.sax.InputSource;

/**
 *
 * @author bug
 */
public class mainWindow extends javax.swing.JFrame {

    DefaultMutableTreeNode modelRoot;

    SAXTreeBuilder saxTree;

    public mainWindow() {
        initComponents();
        jComboBox1.setEnabled(false);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        jButton1 = new javax.swing.JButton();
        jComboBox1 = new javax.swing.JComboBox<>();
        jButton2 = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(490, 160));

        jButton1.setToolTipText("");
        jButton1.setText("Get AXF file");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox1ActionPerformed(evt);
            }
        });

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Manual select ", "Select segments" }));

        jButton2.setText("GO!");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        tree = new CheckBoxTree(null);
        jScrollPane2.setViewportView(tree);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2)
                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 291, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, 505, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton1)
                .addGap(7, 7, 7)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton2))
                .addGap(11, 11, 11)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 304, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {
        modelRoot = (DefaultMutableTreeNode) ((DefaultMutableTreeNode) tree.getModel().getRoot());
        DefaultMutableTreeNode tst = (DefaultMutableTreeNode) ((DefaultMutableTreeNode) tree.getModel().getRoot());
        if (jComboBox1.getSelectedIndex() == 1) {
            ComboActions act = new ComboActions(tst);
            act.segmentsSelected();
            tree.setModel(new DefaultTreeModel(tst));
        }
        if (jComboBox1.getSelectedIndex() == 0) {
           tree.setModel(new DefaultTreeModel(modelRoot));
        }
        
        int i;
        for (i = 0; i <= tree.getRowCount(); i++) {
            tree.expandRow(i);
        }
    }

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt)  {
         Tree2Xml tt= new Tree2Xml();
         try{
         tt.convert((DefaultMutableTreeNode)tree.getModel().getRoot());
         }catch(Exception ex){}
         
    }

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        JFileChooser fileopen = new JFileChooser();
        int ret = fileopen.showDialog(null, "Открыть файл");
        if (ret == JFileChooser.APPROVE_OPTION) {
            File file = fileopen.getSelectedFile();
            DefaultMutableTreeNode top = new DefaultMutableTreeNode(file);
            saxTree = new SAXTreeBuilder(top);
            try {
                org.apache.xerces.parsers.SAXParser saxParser = new org.apache.xerces.parsers.SAXParser();
                saxParser.setContentHandler(saxTree);
                saxParser.parse(new InputSource(new FileInputStream(file)));
            } catch (Exception ex) {
                top.add(new DefaultMutableTreeNode(ex.getMessage()));
            }
            tree.setModel(new DefaultTreeModel(top));
            tree.putClientProperty("JTree.lineStyle", "None");
            tree.setEditable(false);
            jComboBox1.setEnabled(true);
            jComboBox1.setSelectedIndex(1);
            DefaultMutableTreeNode tst = (DefaultMutableTreeNode) ((DefaultMutableTreeNode) tree.getModel().getRoot());
            ComboActions act = new ComboActions(tst);
            act.segmentsSelected();
            tree.setModel(new DefaultTreeModel(tst));
            int i;
            for (i = 0; i <= tree.getRowCount(); i++) {
                tree.expandRow(i);
            }
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(mainWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(mainWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(mainWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(mainWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new mainWindow().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JScrollPane jScrollPane2;
    private com.bug.CheckBoxTree tree;
    // End of variables declaration//GEN-END:variables

}
