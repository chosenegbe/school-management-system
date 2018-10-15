/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package methodClass;

import connection.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JOptionPane;

public class insertUpdateResult {
  Connection conn = null;
  PreparedStatement pst = null;
  ResultSet rs  =  null;
      public void add_score_result(String admission_number, String course_name, String course_code, double score){
          conn = DBConnection.connectDB();
          if(score < 50){
              String grade_words = "Five";
              int grade_num = 5;
                try{
                    String insertResult = "INSERT INTO grade (student_admission_number,course_name,course_code,score,grade_num,grade_words,remarks)"
                    + "VALUES('"+admission_number+"','"+course_name+"','"+course_code+"','"+score+"','"+grade_num+"','"+grade_words+"','Failed')";
                    pst = conn.prepareStatement(insertResult); 
                    pst.execute();
                    JOptionPane.showMessageDialog(null,"Resul addded");
                    }catch(Exception e){
                        
                }               
           }
           else if(score >= 50 && score <= 60){
               String grade_words = "Six";
               int grade_num = 6;
                try{
                    String insertResult = "INSERT INTO grade (student_admission_number,course_name,course_code,score,grade_num,grade_words,remarks)"
                    + "VALUES('"+admission_number+"','"+course_name+"','"+course_code+"','"+score+"','"+grade_num+"','"+grade_words+"','Average')";
                    pst = conn.prepareStatement(insertResult); 
                    pst.execute(); 
                    JOptionPane.showMessageDialog(null,"Result added");
                    }catch(Exception e){
                }               
           }
           else if(score > 60 && score <= 70){
               String grade_words = "Seven";
               int grade_num = 7;
                try{
                    String insertResult = "INSERT INTO grade (student_admission_number,course_name,course_code,score,grade_num,grade_words,remarks)"
                    + "VALUES('"+admission_number+"','"+course_name+"','"+course_code+"','"+score+"','"+grade_num+"','"+grade_words+"','Fairly Good')";
                    pst = conn.prepareStatement(insertResult); 
                    pst.execute(); 
                    JOptionPane.showMessageDialog(null,"Result added");                 
                    }catch(Exception e){
                }               
           }           
           else if(score > 70 && score <= 80){
               String grade_words = "Eight";
               int grade_num = 8;
                try{
                    String insertResult = "INSERT INTO grade (student_admission_number,course_name,course_code,score,grade_num,grade_words,remarks)"
                    + "VALUES('"+admission_number+"','"+course_name+"','"+course_code+"','"+score+"','"+grade_num+"','"+grade_words+"','Good')";
                    pst = conn.prepareStatement(insertResult); 
                    pst.execute(); 
                    JOptionPane.showMessageDialog(null,"Result added");
                    }catch(Exception e){
                }               
           } 
           else if(score > 80 && score <= 90){
               String grade_words = "Nine";
               int grade_num = 9;
                try{
                    String insertResult = "INSERT INTO grade (student_admission_number,course_name,course_code,score,grade_num,grade_words,remarks)"
                    + "VALUES('"+admission_number+"','"+course_name+"','"+course_code+"','"+score+"','"+grade_num+"','"+grade_words+"','Very Good')";
                    pst = conn.prepareStatement(insertResult); 
                    pst.execute(); 
                    JOptionPane.showMessageDialog(null,"Result added");
                    }catch(Exception e){
                }               
           }
           else if(score > 90 && score <= 100){
               String grade_words = "Ten";
               int grade_num = 10;
                try{
                    String insertResult = "INSERT INTO grade (student_admission_number,course_name,course_code,score,grade_num,grade_words,remarks)"
                    + "VALUES('"+admission_number+"','"+course_name+"','"+course_code+"','"+score+"','"+grade_num+"','"+grade_words+"','Excellent')";
                    pst = conn.prepareStatement(insertResult); 
                    pst.execute(); 
                    JOptionPane.showMessageDialog(null,"Result added");
                    }catch(Exception e){
                }               
           }           
    }
 
    public void update_score_result(double score, String result_table_click){
        conn = DBConnection.connectDB();
               if(score < 50){
              String grade_words = "Five";
              int grade_num = 5;
                try{
                    String update_sql = "UPDATE grade SET score = '"+score+"', grade_num ='"+grade_num+"', grade_words ='"+grade_words+"'"
                            + ", remarks = 'Failed' WHERE grade_id = '"+result_table_click+"'"  ;
                    pst = conn.prepareStatement(update_sql); 
                    pst.execute(); 
                    JOptionPane.showMessageDialog(null,"Result Updated");                 
                    }catch(Exception e){
                }               
           }
           else if(score >= 50 && score <= 60){
               String grade_words = "Six";
               int grade_num = 6;
                
                try{
                    String update_sql = "UPDATE grade SET score = '"+score+"', grade_num ='"+grade_num+"', grade_words ='"+grade_words+"'"
                            + ", remarks = 'Average' WHERE grade_id = '"+result_table_click+"'"  ;
                    pst = conn.prepareStatement(update_sql); 
                    pst.execute(); 
                    JOptionPane.showMessageDialog(null,"Result Updated");                 
                    }catch(Exception e){
                }                
           }
           else if(score > 60 && score <= 70){
               String grade_words = "Seven";
               int grade_num = 7;
                try{
                    String update_sql = "UPDATE grade SET score = '"+score+"', grade_num ='"+grade_num+"', grade_words ='"+grade_words+"'"
                            + ", remarks = 'Fairly Good' WHERE grade_id = '"+result_table_click+"'"  ;
                    pst = conn.prepareStatement(update_sql); 
                    pst.execute(); 
                    JOptionPane.showMessageDialog(null,"Result Updated");                 
                    }catch(Exception e){
                }               
           }           
           else if(score > 70 && score <= 80){
               String grade_words = "Eight";
               int grade_num = 8;
                  try{
                    String update_sql = "UPDATE grade SET score = '"+score+"', grade_num ='"+grade_num+"', grade_words ='"+grade_words+"'"
                            + ", remarks = 'Good' WHERE grade_id = '"+result_table_click+"'"  ;
                    pst = conn.prepareStatement(update_sql); 
                    pst.execute(); 
                    JOptionPane.showMessageDialog(null,"Result Updated");                 
                    }catch(Exception e){
                }              
           } 
           else if(score > 80 && score <= 90){
               String grade_words = "Nine";
               int grade_num = 9;
                  try{
                    String update_sql = "UPDATE grade SET score = '"+score+"', grade_num ='"+grade_num+"', grade_words ='"+grade_words+"'"
                            + ", remarks = 'Very Good' WHERE grade_id = '"+result_table_click+"'"  ;
                    pst = conn.prepareStatement(update_sql); 
                    pst.execute(); 
                    JOptionPane.showMessageDialog(null,"Result Updated");                 
                    }catch(Exception e){
                }                
           }
           else if(score > 90 && score <= 100){
               String grade_words = "Ten";
               int grade_num = 10;
                 try{
                    String update_sql = "UPDATE grade SET score = '"+score+"', grade_num ='"+grade_num+"', grade_words ='"+grade_words+"'"
                            + ", remarks = 'Excellent' WHERE grade_id = '"+result_table_click+"'"  ;
                    pst = conn.prepareStatement(update_sql); 
                    pst.execute(); 
                    JOptionPane.showMessageDialog(null,"Result Updated");                 
                    }catch(Exception e){
                }               
           }           
    }

  
  
}
