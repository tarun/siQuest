/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.villanova.siquest;

import java.io.File;
import java.io.FileFilter;

public class SqFileFilter implements FileFilter
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
}
