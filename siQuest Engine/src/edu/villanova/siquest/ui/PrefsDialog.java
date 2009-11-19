/*
 * PreferencesDialog.java
 *
 * Created on November 11, 2008, 1:38 PM
 */

package edu.villanova.siquest.ui;

import edu.villanova.siquest.SqAnalyzers;
import edu.villanova.siquest.SqPreferences;
import edu.villanova.siquest.SqSearchAPI;

public class PrefsDialog extends javax.swing.JDialog {

  private SqPreferences prefs;
  
    /** Creates new form PreferencesDialog */
    public PrefsDialog(java.awt.Frame parent, boolean modal) 
    {
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

        analyzerComboBox = new javax.swing.JComboBox();
        indexPathTextField = new javax.swing.JTextField();
        browseButton = new javax.swing.JButton();
        resetButton = new javax.swing.JButton();
        saveButton = new javax.swing.JButton();
        stemmingLangComboBox = new javax.swing.JComboBox();
        jScrollPane1 = new javax.swing.JScrollPane();
        stopWordsTextArea = new javax.swing.JTextArea();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        searchAPILabel = new javax.swing.JLabel();
        numKeywordsLabel = new javax.swing.JLabel();
        searchAPIComboBox = new javax.swing.JComboBox();
        numKeywordsComboBox = new javax.swing.JComboBox();
        useCustomStopWordsCheckBox = new javax.swing.JCheckBox();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Preferences");

        analyzerComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] {"Simple", "Snowball", "Standard", "Stop", "Whitespace"}));

        browseButton.setText("Browse");
        browseButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                browseButtonActionPerformed(evt);
            }
        });

        resetButton.setText("Reset");
        resetButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                resetButtonActionPerformed(evt);
            }
        });

        saveButton.setText("Save");
        saveButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveButtonActionPerformed(evt);
            }
        });

        stemmingLangComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "English" }));

        stopWordsTextArea.setColumns(20);
        stopWordsTextArea.setLineWrap(true);
        stopWordsTextArea.setRows(5);
        jScrollPane1.setViewportView(stopWordsTextArea);
        stopWordsTextArea.setText(prefs.getStopWords());

        jLabel1.setText("Index Location:");

        jLabel2.setText("Analyzer:");

        jLabel3.setText("Stemming Language:");

        searchAPILabel.setText("Search API to use:");
        
        numKeywordsLabel.setText("Number of Keywords in Query:");
        
        jLabel4.setText("Custom Stop Words:");

        searchAPIComboBox.setModel(new javax.swing.DefaultComboBoxModel(SqSearchAPI.values()));
        searchAPIComboBox.setSelectedItem(prefs.getSearchAPI());
        
        numKeywordsComboBox.setModel(new javax.swing.DefaultComboBoxModel(new Integer[] {10,25,50,100}));
        numKeywordsComboBox.setSelectedItem(Integer.toString(prefs.getNumKeywords()));
        
        useCustomStopWordsCheckBox.setText("Use Custom stop words");
        
        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 461, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(useCustomStopWordsCheckBox)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2)
                            .addComponent(searchAPILabel)
                            .addComponent(numKeywordsLabel))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(indexPathTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 248, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(browseButton))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(analyzerComboBox, javax.swing.GroupLayout.Alignment.LEADING, 0, 131, Short.MAX_VALUE)
                                    .addComponent(stemmingLangComboBox, 0, 131, Short.MAX_VALUE)
                                    .addComponent(searchAPIComboBox, 0, 131, Short.MAX_VALUE)
                                    .addComponent(numKeywordsComboBox, 0, 131, Short.MAX_VALUE))
                                .addGap(240, 240, 240))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(resetButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(saveButton)
                        .addGap(12, 12, 12))
                    .addComponent(jLabel4))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(indexPathTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(browseButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(analyzerComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(stemmingLangComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(searchAPIComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(searchAPILabel))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(numKeywordsComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(numKeywordsLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(useCustomStopWordsCheckBox)
                .addGap(11, 11, 11)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 18, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(resetButton)
                    .addComponent(saveButton))
                .addContainerGap())
        );

        String anlzrName = prefs.getAnalyzerName();

        if(anlzrName.equalsIgnoreCase(SqAnalyzers.SIMPLE_ANALYZER))
        {
            analyzerComboBox.setSelectedIndex(0);
        }
        else if(anlzrName.equalsIgnoreCase(SqAnalyzers.SNOWBALL))
        {
            analyzerComboBox.setSelectedIndex(1);
        }
        else if(anlzrName.equalsIgnoreCase(SqAnalyzers.STOP_ANALYZER))
        {
            analyzerComboBox.setSelectedIndex(3);
        }
        else if(anlzrName.equalsIgnoreCase(SqAnalyzers.WHITE_SPACE_ANALYZER))
        {
            analyzerComboBox.setSelectedIndex(4);
        }
        else
        {
            analyzerComboBox.setSelectedIndex(2);
        }

        indexPathTextField.setText(prefs.getIndexDir());
        String analyzerName = prefs.getAnalyzerName();

        if(analyzerName.equalsIgnoreCase("English"))
        {
            stemmingLangComboBox.setSelectedIndex(0);
        }
        else
        {
            stemmingLangComboBox.setSelectedIndex(0);
        }

        if(prefs.getUseCustomStopWords())
        {
            useCustomStopWordsCheckBox.setSelected(true);
        }

        pack();
    }// </editor-fold>//GEN-END:initComponents

private void saveButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveButtonActionPerformed

  java.io.File indexDir = new  java.io.File(indexPathTextField.getText());
  
  if(indexDir.exists())
  {
    prefs.setIndexDir(indexPathTextField.getText());
  }
  
  if(analyzerComboBox.getSelectedIndex() == 0)
  {
    prefs.setAnalyzerName(SqAnalyzers.SIMPLE_ANALYZER);
  }
  else if(analyzerComboBox.getSelectedIndex() == 1)
  {
    prefs.setAnalyzerName(SqAnalyzers.SNOWBALL);
  }
  else if(analyzerComboBox.getSelectedIndex() == 2)
  {
    prefs.setAnalyzerName(SqAnalyzers.STANDARD_ANALYZER);
  }
  else if(analyzerComboBox.getSelectedIndex() == 3)
  {
    prefs.setAnalyzerName(SqAnalyzers.STOP_ANALYZER);
  }
  else if(analyzerComboBox.getSelectedIndex() == 4)
  {
    prefs.setAnalyzerName(SqAnalyzers.WHITE_SPACE_ANALYZER);
  }
  
  if(stemmingLangComboBox.getSelectedIndex() == 0)
  {
    prefs.setStemmingLanguage("English");
  }
  
  if(searchAPIComboBox.getSelectedIndex() >= 0)
  {
	prefs.setSearchAPI(searchAPIComboBox.getSelectedItem().toString());
  }
  
  if(numKeywordsComboBox.getSelectedIndex() >= 0)
  {
	prefs.setNumKeywords((Integer) numKeywordsComboBox.getSelectedItem());
  }
  
  if(stopWordsTextArea.getText().length() > 0)
  {
    prefs.setStopWords(stopWordsTextArea.getText());
  }
  
  if(useCustomStopWordsCheckBox.isSelected())
  {
    prefs.setUseCustomStopWords(true);
  }
  
  prefs.exportUserPrefs();
}//GEN-LAST:event_saveButtonActionPerformed

private void browseButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_browseButtonActionPerformed
  javax.swing.JFileChooser chooser = new javax.swing.JFileChooser();
  chooser.setFileSelectionMode(javax.swing.JFileChooser.DIRECTORIES_ONLY);
          
  int returnVal = chooser.showOpenDialog(this);
  
  if(returnVal == javax.swing.JFileChooser.APPROVE_OPTION)
  {
    indexPathTextField.setText(chooser.getSelectedFile().getPath());
  }
}//GEN-LAST:event_browseButtonActionPerformed

private void resetButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_resetButtonActionPerformed
  prefs.initializePrefs();
  prefs.setFirstRun(false);
  repaint();
}//GEN-LAST:event_resetButtonActionPerformed

//    /**
//    * @param args the command line arguments
//    */
//    public static void main(String args[]) {
//        java.awt.EventQueue.invokeLater(new Runnable() {
//            public void run() {
//                PrefsDialog dialog = new PrefsDialog(new javax.swing.JFrame(), true);
//                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
//                    public void windowClosing(java.awt.event.WindowEvent e) {
//                        System.exit(0);
//                    }
//                });
//                dialog.setVisible(true);
//            }
//        });
//    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox analyzerComboBox;
    private javax.swing.JButton browseButton;
    private javax.swing.JTextField indexPathTextField;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel searchAPILabel;
    private javax.swing.JLabel numKeywordsLabel;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton resetButton;
    private javax.swing.JButton saveButton;
    private javax.swing.JComboBox searchAPIComboBox;
    private javax.swing.JComboBox numKeywordsComboBox;
    private javax.swing.JComboBox stemmingLangComboBox;
    private javax.swing.JTextArea stopWordsTextArea;
    private javax.swing.JCheckBox useCustomStopWordsCheckBox;
    // End of variables declaration//GEN-END:variables

}