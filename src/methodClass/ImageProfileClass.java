/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package methodClass;

import connection.DBConnection;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JOptionPane;


public class ImageProfileClass {


    Connection conn = null;
    PreparedStatement pst = null;
    ResultSet rs = null;  
    public void ImageProfileClass(){
       conn = DBConnection.connectDB();
    }
    public Boolean add(File img, FileInputStream fis){
      String sql = "INSERT INTO imagetest VALUES(?)";
        
      try{
          pst = conn.prepareStatement(sql);
          pst.setBinaryStream(1, fis,(int)img.length());
          pst.execute();
          
          return true;
      }catch(Exception e){
          return false;
      }
     
    }
    public String retrieve(){
      FileOutputStream fos = null;
      String sql = "SELECT * FROM imagetest";
      
        try {
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            
            rs.last();
            Blob b = rs.getBlob("image");
            String id = rs.getString("id");
            
          
              //write file to our machine
              fos = new FileOutputStream("C:\\Users\\michel\\Documents\\NetBeansProjects\\SchoolManagementSystem\\Image\\"+id+".jpg");
              
        
            int len = (int)b.length();
            byte[] buf = b.getBytes(1, len);
            
            fos.write(buf,0,len);
            
            return id;
        } catch (Exception ex) {
           JOptionPane.showMessageDialog(null,ex);
            return "-1";
        }
    
}
}
