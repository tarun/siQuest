package edu.villanova.siquest.ui;

import javax.swing.filechooser.FileFilter;
import java.io.File;

public class FileChooserFilter extends FileFilter
{
  public boolean accept(File pathname) 
  {
    return pathname.isDirectory() ||
           pathname.getName().toLowerCase().endsWith(".txt") ||
           pathname.getName().toLowerCase().endsWith(".html") ||
           pathname.getName().toLowerCase().endsWith(".htm") ||
           pathname.getName().toLowerCase().endsWith(".xml") ||
           pathname.getName().toLowerCase().endsWith(".pdf") || 
           pathname.getName().toLowerCase().endsWith(".doc") ||
           pathname.getName().toLowerCase().endsWith(".docx") ||
           pathname.getName().toLowerCase().endsWith(".xls") ||
           pathname.getName().toLowerCase().endsWith(".ppt");
  }
  
  public String getDescription() 
  {
    return "*.txt, *.html, *.xml, *.pdf, *.doc, *.docx, *.xls, *.ppt";
  }
}
