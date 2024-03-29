/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package methodClass;
import java.io.File;

import javax.swing.filechooser.FileFilter;

/*
* This class implements a generic file name filter that allows the listing/selection
* of JPEG files.
*/
public class JPEGImageFileFilter extends FileFilter implements java.io.FileFilter
 {
 public boolean accept(File f)
   {
   if (f.getName().toLowerCase().endsWith(".jpeg")) return true;
   if (f.getName().toLowerCase().endsWith(".jpg")) return true;
   if(f.isDirectory())return true;
   return false;
  }
 public String getDescription()
   {
   return "JPEG files";
   }

} 