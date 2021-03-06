/*
 * IndexDialog.java
 *
 * Created on November 10, 2008, 10:34 PM
 */

package edu.villanova.siquest.ui;

import org.apache.log4j.Logger;

import edu.villanova.siquest.SqIndexer;
import edu.villanova.siquest.SqPreferences;

/**
 *
 * @author  narvaeza
 */
public class IndexDialog extends javax.swing.JDialog {

	static Logger logger = Logger.getLogger(IndexDialog.class);
	
    SqPreferences prefs;
    
    /** Creates new form IndexDialog */
    public IndexDialog(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        prefs = SqPreferences.getInstance();
        initComponents();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pathTextField = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Index");

        jButton1.setText("Browse");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("Index");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jLabel1.setText("Path:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pathTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 362, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton2)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(pathTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1)
                    .addComponent(jButton2))
                .addContainerGap(51, Short.MAX_VALUE))
        );

        pathTextField.setText(prefs.getLastIndexedDir());

        pack();
    }// </editor-fold>//GEN-END:initComponents

private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
  
  
  javax.swing.JFileChooser chooser = new javax.swing.JFileChooser(pathTextField.getText());
  chooser.setFileSelectionMode(javax.swing.JFileChooser.DIRECTORIES_ONLY);
          
  int returnVal = chooser.showOpenDialog(this);
  
  if(returnVal == javax.swing.JFileChooser.APPROVE_OPTION)
  {
    pathTextField.setText(chooser.getSelectedFile().getPath());
    prefs.setLastIndexedDir(chooser.getSelectedFile().getPath());
  }

}//GEN-LAST:event_jButton1ActionPerformed

private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
  try
  {
    SqIndexer indexer = new SqIndexer(prefs.getIndexDir(), prefs.getAnalyzerName());
        
    if(indexer.indexExist())
    {
      int result = javax.swing.JOptionPane.showConfirmDialog(this, "Indexes exists.  Would you like to delete?","Confirm", javax.swing.JOptionPane.YES_NO_OPTION);
        
      if(result == 0)
      {
        indexer.deleteIndex();
        logger.info("Index deleted");
                
        indexer.createIndex();
        logger.info("Index created");
                 
        logger.info("Start indexing " + pathTextField.getText());
        indexer.indexDirectory(pathTextField.getText());
        logger.info("Finished indexing");
        
        javax.swing.JOptionPane.showMessageDialog(this, "Indexing completed.");
      }
    }
    else
    {
      indexer.createIndex();
      logger.info("Index created");
                 
      logger.info("Start indexing " + pathTextField.getText());
      indexer.indexDirectory(pathTextField.getText());
      logger.info("Finished indexing");
      
      javax.swing.JOptionPane.showMessageDialog(this, "Indexing completed.  Please optimze to enhance performance.");
   
    }
  }
  catch(Exception e)
  {
    logger.error(e.getMessage());
    javax.swing.JOptionPane.showMessageDialog(this, "An Indexing error occured.\n" + "ERROR: " + e.getMessage());
  }
}//GEN-LAST:event_jButton2ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JTextField pathTextField;
    // End of variables declaration//GEN-END:variables

}
