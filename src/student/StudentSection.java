/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package student;

import AdminWelcomepage.Student;
import com.sun.mail.util.MailSSLSocketFactory;
import connection.DBConnection;
import java.awt.Color;
import java.awt.Toolkit;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.InputStream;
import static java.lang.Thread.sleep;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import net.proteanit.sql.DbUtils;
import schoolmanagementsystem.Login;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.swing.ImageIcon;

/**
 *
 * @author Chosen Egbe
 */
public class StudentSection extends javax.swing.JFrame {

    Connection conn = null;
    PreparedStatement pst = null;
    ResultSet rs  = null;
    PreparedStatement pst2 = null;
    ResultSet rs2  = null;   
    String username = null;
    String id = null;
    String admission_number = null;
    String first_name = null;
    String last_name = null;
    String middle_name = null;
    String faculty = null;
    
    public StudentSection(String username1, String id1) {

        initComponents();
        conn = DBConnection.connectDB();
        
        String getAdmissionNumber = "SELECT * FROM students WHERE username = '"+username1+"' AND id = '"+id1+"'";
        
        try{
          pst2 = conn.prepareStatement(getAdmissionNumber);
          rs2 = pst2.executeQuery();
          if(rs2.next()){
              admission_number = rs2.getString("admission_number");
              first_name = rs2.getString("first_name");
              last_name = rs2.getString("last_name");
              middle_name = rs2.getString("middle_name");
              faculty = rs2.getString("Faculty");
          }
        }catch(Exception e){
        
        }                
        getStudentdata(username1,id1);       
        fill_table_grades(admission_number);       
        enroll_courses_filled(admission_number);
        fill_unerollcourses_field();
        currentDate();
        //documents_courses.setVisible(false);
    }

    private void getStudentdata(String username1, String id1){
    
        String sql = "SELECT * FROM students WHERE username = '"+username1+"' OR id = '"+id1+"'";
        try{
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            if(rs.next()){
               txt_id.setText(rs.getString("id"));
               txt_fn.setText(rs.getString("first_name"));
               txt_mn.setText(rs.getString("middle_name"));
               txt_ln.setText(rs.getString("last_name"));
               txt_gender.setText(rs.getString("gender"));
               txt_country.setText(rs.getString("nationality"));
               txt_birth_day.setText(rs.getString("date_of_birth"));
               txt_email.setText(rs.getString("email"));               
               txt_address.setText(rs.getString("address"));
               txt_phone.setText(rs.getString("phone_number"));
               txt_faculty.setText(rs.getString("Faculty"));
               txt_student_status.setText(rs.getString("regular_or_part_time"));
               txt_study_year.setText(rs.getString("year_of_studies"));
               
               txt_username.setText(rs.getString("username"));
               txt_password.setText(rs.getString("password"));
               
               lbl_names.setText(rs.getString("first_name") + " " + rs.getString("last_name"));
               

                            byte[] imageData = rs.getBytes("photo");
                            InputStream input = rs.getBinaryStream("photo"); 
                            byte[] buffer = new byte[1024];
                            if(input.read(buffer) < 0){
                              lbl_pic.setIcon(null);
                            }
                            /*if(imageData.length ==0){
                              lbl_pic.setIcon(null);
                            }else{*/
                            
                            
                            else{
                            
                            ImageIcon imageIcon = new ImageIcon(imageData); // load the image to a imageIcon
                            java.awt.Image image1 = imageIcon.getImage(); // transform it 
                            java.awt.Image newimg = image1.getScaledInstance(106, 100,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
                            //imageIcon = new ImageIcon(newimg);  
                            
                            ImageIcon studentPicFormat = new ImageIcon(newimg); // transform it back
                            lbl_pic.setIcon(studentPicFormat);
                        }               
               
               
               
               
            }
        }catch(Exception e){
        
        }
    }
    
    private void fill_table_grades(String admission_number){
         String sql = "SELECT course_name AS 'Course Name', score AS 'Score on 100', grade_num AS 'Grade(In numbers)', "
                 + "grade_words AS 'Grade(In words)', remarks AS 'Remarks' FROM grade WHERE student_admission_number = '"+admission_number+"'";
         
         try{
             pst = conn.prepareStatement(sql);
             rs = pst.executeQuery();
             
             table_courses.setModel(DbUtils.resultSetToTableModel(rs));
             
         }catch(Exception e){
             JOptionPane.showMessageDialog(null, e);
         }
    }
    private void enroll_courses_filled( String admission_number){
        String course_code = null;
        String course_name = null;

        String sql = "SELECT DISTINCT courses.course_code, courses.course_name "
                       + " FROM courses " 
                       + " JOIN grade " 
                       + " ON courses.course_code != grade.course_code WHERE grade.grade_num > 6 AND grade.student_admission_number = '"+admission_number+"'";

        try{
             pst = conn.prepareStatement(sql);
             rs = pst.executeQuery();
             combo_course_name.addItem(" ");
               while(rs.next()){
                 course_code = rs.getString("course_code");
                 course_name = rs.getString("course_name");
                 
                 combo_course_name.addItem(course_name);
                
             }
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        } 
         
    }
	public void currentDate(){

                
		Thread clock = new Thread(){
                    public void run(){
                     for(;;){
                       	Calendar cal = new GregorianCalendar();
                        int month = cal.get(Calendar.MONTH);
                        int year = cal.get(Calendar.YEAR);
                        int day = cal.get(Calendar.DAY_OF_MONTH);
                        
                        txt_date.setText("Date: " +year+ "/"+ (month+1)+ "/"+ day);

                        int second = cal.get(Calendar.SECOND);
                        int minute = cal.get(Calendar.MINUTE);
                        int hour = cal.get(Calendar.HOUR);
                        txt_time.setText("Time "+hour+ ":"+ minute+ ":"+ second);
                        //System.out.println("Time "+hour+ ":"+ minute+ ":"+ second);
                      
                         try {
                             sleep(1000);
                         } catch (InterruptedException ex) {
                             Logger.getLogger(Student.class.getName()).log(Level.SEVERE, null, ex);
                         }
                       }
                   }
                };
                clock.start();
	}     
    private void fill_unerollcourses_field(){
        String sql = "SELECT * FROM enrol_student WHERE student_admission_number = '"+admission_number+"'";
        try{
             pst = conn.prepareStatement(sql);
             rs = pst.executeQuery();
            
             combo_enrol_courses1.addItem(" ");
             while(rs.next()){
                 combo_enrol_courses.addItem(rs.getString("course_name"));
                 combo_enrol_courses1.addItem(rs.getString("course_name"));
             }
        }catch(Exception e){
             JOptionPane.showMessageDialog(null, e);
        }
    }
    private void fill_teacher_name(String course_name1){
        String teacher_name = null;
        String assistant_name = null;
        String sql = "SELECT * FROM assign_courses WHERE course_name = '"+course_name1+"'";
        try{
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            if(rs.next()){
                teacher_name = rs.getString("professor_names");
                assistant_name = rs.getString("assistant_name");
                txt_professor_name.setText(teacher_name);
                teaching_assistant_names.setText(assistant_name);
            }
        }catch(Exception e){
            
     }
    }
       private void Update_table_documents(){
            String sql = "SELECT id,course_name,comments AS 'Title',doc_path AS 'File' FROM document_courses WHERE "
                    + "course_name = '"+lbl_courses_doc_name.getText()+"'";
		try {
			pst = conn.prepareStatement(sql);
			rs = pst.executeQuery();
			documents_courses.setModel(DbUtils.resultSetToTableModel(rs));
			
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e);
			
		}finally{
			try{
				rs.close();
				pst.close();
                                //conn.close();
			}catch(Exception e ){
			JOptionPane.showMessageDialog(null, e);
		   }
			
		}      
       }      
       
    
    
    
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
                if ("Windows Classic".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(StudentSection.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(StudentSection.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(StudentSection.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(StudentSection.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                
                StudentSection frame = new StudentSection("username","id");
                frame.setLocationRelativeTo(null);
                frame.setTitle("Student section");
                frame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
                
                frame.setVisible(true);
            }
        });
    }   
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        txt_phone = new javax.swing.JTextField();
        txt_gender = new javax.swing.JTextField();
        txt_faculty = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        txt_username = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        txt_password = new javax.swing.JPasswordField();
        btn_login_change = new javax.swing.JButton();
        jLabel12 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txt_ln = new javax.swing.JTextField();
        txt_email = new javax.swing.JTextField();
        txt_student_status = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txt_id = new javax.swing.JTextField();
        txt_mn = new javax.swing.JTextField();
        txt_country = new javax.swing.JTextField();
        txt_study_year = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        txt_address = new javax.swing.JTextField();
        txt_fn = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        txt_birth_day = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        lbl_names = new javax.swing.JLabel();
        btn_logout = new javax.swing.JButton();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        table_courses = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jLabel22 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jLabel21 = new javax.swing.JLabel();
        txt_professor_name = new javax.swing.JTextField();
        jLabel20 = new javax.swing.JLabel();
        teaching_assistant_names = new javax.swing.JTextField();
        txt_course_year = new javax.swing.JTextField();
        jLabel19 = new javax.swing.JLabel();
        txt_course_code = new javax.swing.JTextField();
        txt_course_semester = new javax.swing.JTextField();
        btn_enrol = new javax.swing.JButton();
        jPanel8 = new javax.swing.JPanel();
        combo_course_name = new javax.swing.JComboBox<>();
        jPanel9 = new javax.swing.JPanel();
        btn_unenrol = new javax.swing.JButton();
        jPanel10 = new javax.swing.JPanel();
        combo_enrol_courses = new javax.swing.JComboBox<>();
        jPanel11 = new javax.swing.JPanel();
        jPanel13 = new javax.swing.JPanel();
        combo_enrol_courses1 = new javax.swing.JComboBox<>();
        jScrollPane6 = new javax.swing.JScrollPane();
        documents_courses = new javax.swing.JTable();
        lbl_courses_doc_name = new javax.swing.JLabel();
        jPanel12 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        txt_feed1 = new javax.swing.JTextArea();
        jButton3 = new javax.swing.JButton();
        jScrollPane5 = new javax.swing.JScrollPane();
        txt_feed_area1 = new javax.swing.JPanel();
        jDesktopPane1 = new javax.swing.JDesktopPane();
        lbl_pic = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        txt_time = new javax.swing.JLabel();
        txt_date = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenu2 = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Student Information", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Serif", 1, 14), new java.awt.Color(204, 204, 255))); // NOI18N

        txt_phone.setEnabled(false);

        txt_gender.setEnabled(false);

        txt_faculty.setEnabled(false);

        jLabel6.setText("Address");

        jLabel13.setText("Study Year");

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Edit Login Details", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Serif", 2, 12), new java.awt.Color(255, 51, 51))); // NOI18N

        jLabel8.setText("Username");

        jLabel9.setText("Password");

        btn_login_change.setFont(new java.awt.Font("Serif", 1, 12)); // NOI18N
        btn_login_change.setForeground(new java.awt.Color(0, 153, 204));
        btn_login_change.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgresource/Actions-document-edit-icon.png"))); // NOI18N
        btn_login_change.setText("Edit Login");
        btn_login_change.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_login_changeActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btn_login_change)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel8)
                            .addComponent(jLabel9))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txt_username, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txt_password, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(txt_username, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(11, 11, 11)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(txt_password, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btn_login_change)
                .addContainerGap(16, Short.MAX_VALUE))
        );

        jLabel12.setText("Faculty");

        jLabel11.setText("Nationality");

        jLabel2.setText("Last Name");

        txt_ln.setEnabled(false);
        txt_ln.setPreferredSize(new java.awt.Dimension(146, 20));

        txt_email.setEnabled(false);

        txt_student_status.setEnabled(false);

        jLabel10.setText("Gender");

        jLabel14.setText("Student ID");

        jLabel3.setText("Middle Name");

        txt_id.setEnabled(false);
        txt_id.setPreferredSize(new java.awt.Dimension(146, 20));

        txt_mn.setEnabled(false);
        txt_mn.setPreferredSize(new java.awt.Dimension(146, 20));

        txt_country.setEnabled(false);

        txt_study_year.setEnabled(false);

        jLabel5.setText("Phone");

        jLabel7.setText("Student Status");

        jLabel1.setText("First Name");

        txt_address.setEnabled(false);

        txt_fn.setEnabled(false);
        txt_fn.setPreferredSize(new java.awt.Dimension(146, 20));

        jLabel4.setText("Email");

        jLabel16.setText("Birth Day");

        txt_birth_day.setEnabled(false);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addGroup(jPanel2Layout.createSequentialGroup()
                                    .addComponent(jLabel14)
                                    .addGap(30, 30, 30)
                                    .addComponent(txt_id, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(jPanel2Layout.createSequentialGroup()
                                    .addComponent(jLabel1)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(txt_fn, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(jPanel2Layout.createSequentialGroup()
                                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel3)
                                        .addComponent(jLabel2)
                                        .addComponent(jLabel4))
                                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(txt_country, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addComponent(txt_gender, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addComponent(txt_ln, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                            .addGroup(jPanel2Layout.createSequentialGroup()
                                                .addGap(22, 22, 22)
                                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(txt_email, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addComponent(txt_birth_day, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addComponent(txt_address, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addComponent(txt_phone, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addComponent(txt_faculty, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(txt_mn, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                            .addComponent(jLabel10)
                            .addComponent(jLabel11)
                            .addComponent(jLabel5)
                            .addComponent(jLabel12)
                            .addComponent(jLabel16)
                            .addComponent(jLabel6)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                                    .addComponent(jLabel13)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(txt_study_year, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                                    .addComponent(jLabel7)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(txt_student_status, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(txt_id, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(2, 2, 2)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txt_fn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(9, 9, 9)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txt_mn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txt_ln, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(txt_gender, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(txt_country, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel16)
                    .addComponent(txt_birth_day, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_email, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(txt_address, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(txt_phone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(txt_faculty, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(txt_student_status, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(txt_study_year, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 11, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jLabel15.setFont(new java.awt.Font("Serif", 1, 14)); // NOI18N
        jLabel15.setText("Welcome");

        lbl_names.setBackground(new java.awt.Color(204, 204, 255));
        lbl_names.setFont(new java.awt.Font("Serif", 1, 14)); // NOI18N
        lbl_names.setForeground(new java.awt.Color(204, 204, 255));

        btn_logout.setFont(new java.awt.Font("Serif", 1, 10)); // NOI18N
        btn_logout.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgresource/Apps-session-logout-icon.png"))); // NOI18N
        btn_logout.setText("Logout");
        btn_logout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_logoutActionPerformed(evt);
            }
        });

        table_courses.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        table_courses.setEnabled(false);
        jScrollPane1.setViewportView(table_courses);

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgresource/printer-icon.png"))); // NOI18N
        jButton1.setText("Print");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 440, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton1)
                .addContainerGap(21, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(jButton1)
                .addContainerGap(316, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Courses Passed", jPanel3);

        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Course Details", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Serif", 1, 14))); // NOI18N

        jLabel22.setText("Course Code");

        jLabel18.setText("Semester");

        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Instructor Details", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Serif", 3, 12))); // NOI18N

        jLabel21.setText("Teaching Assistant");

        txt_professor_name.setEditable(false);
        txt_professor_name.setPreferredSize(new java.awt.Dimension(180, 20));

        jLabel20.setText("Professor name");

        teaching_assistant_names.setEditable(false);
        teaching_assistant_names.setPreferredSize(new java.awt.Dimension(180, 20));

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel21)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(teaching_assistant_names, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel20)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txt_professor_name, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel20)
                    .addComponent(txt_professor_name, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel21)
                    .addComponent(teaching_assistant_names, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        txt_course_year.setEditable(false);

        jLabel19.setText("Course Year");

        txt_course_code.setEnabled(false);

        txt_course_semester.setEditable(false);

        btn_enrol.setText("Enrol");
        btn_enrol.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_enrolActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel22)
                            .addComponent(jLabel19)
                            .addComponent(jLabel18))
                        .addGap(31, 31, 31)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txt_course_semester, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txt_course_year, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txt_course_code, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(20, 20, 20))
                    .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(btn_enrol)
                        .addGap(18, 18, 18)))
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel22)
                    .addComponent(txt_course_code, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel19)
                    .addComponent(txt_course_year, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel18)
                    .addComponent(txt_course_semester, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btn_enrol)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel8.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Course name", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Serif", 1, 14))); // NOI18N

        combo_course_name.addPopupMenuListener(new javax.swing.event.PopupMenuListener() {
            public void popupMenuCanceled(javax.swing.event.PopupMenuEvent evt) {
            }
            public void popupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {
                combo_course_namePopupMenuWillBecomeInvisible(evt);
            }
            public void popupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt) {
            }
        });

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(combo_course_name, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(combo_course_name, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel9.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Unenroll course", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 11))); // NOI18N

        btn_unenrol.setText("Unenrol");
        btn_unenrol.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_unenrolActionPerformed(evt);
            }
        });

        jPanel10.setBorder(javax.swing.BorderFactory.createTitledBorder("Enrolled Courses"));

        combo_enrol_courses.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                combo_enrol_coursesActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(combo_enrol_courses, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(combo_enrol_courses, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_unenrol))
                .addContainerGap())
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btn_unenrol)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, 312, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(32, 32, 32))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(82, 82, 82)
                        .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jPanel6, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 270, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(64, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Enroll for a Course", jPanel4);

        jPanel13.setBorder(javax.swing.BorderFactory.createTitledBorder("Enrolled Courses"));

        combo_enrol_courses1.addPopupMenuListener(new javax.swing.event.PopupMenuListener() {
            public void popupMenuCanceled(javax.swing.event.PopupMenuEvent evt) {
            }
            public void popupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {
                combo_enrol_courses1PopupMenuWillBecomeInvisible(evt);
            }
            public void popupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt) {
            }
        });
        combo_enrol_courses1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                combo_enrol_courses1MouseClicked(evt);
            }
        });
        combo_enrol_courses1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                combo_enrol_courses1KeyReleased(evt);
            }
        });

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(combo_enrol_courses1, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(combo_enrol_courses1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        documents_courses.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        documents_courses.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                documents_coursesMouseClicked(evt);
            }
        });
        jScrollPane6.setViewportView(documents_courses);

        lbl_courses_doc_name.setFont(new java.awt.Font("Serif", 1, 14)); // NOI18N
        lbl_courses_doc_name.setForeground(new java.awt.Color(0, 153, 204));

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(34, 34, 34)
                .addComponent(lbl_courses_doc_name, javax.swing.GroupLayout.PREFERRED_SIZE, 265, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 84, Short.MAX_VALUE))
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane6)
                .addContainerGap())
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(lbl_courses_doc_name, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 289, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("Course Document", jPanel11);

        txt_feed1.setColumns(20);
        txt_feed1.setRows(5);
        jScrollPane4.setViewportView(txt_feed1);

        jButton3.setText("Post");

        txt_feed_area1.setBackground(new java.awt.Color(204, 204, 255));

        javax.swing.GroupLayout txt_feed_area1Layout = new javax.swing.GroupLayout(txt_feed_area1);
        txt_feed_area1.setLayout(txt_feed_area1Layout);
        txt_feed_area1Layout.setHorizontalGroup(
            txt_feed_area1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        txt_feed_area1Layout.setVerticalGroup(
            txt_feed_area1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 187, Short.MAX_VALUE)
        );

        jScrollPane5.setViewportView(txt_feed_area1);

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButton3, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jScrollPane4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 528, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane5))
                .addContainerGap())
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 187, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("Feeds", jPanel12);

        jDesktopPane1.setBackground(new java.awt.Color(204, 204, 255));

        jDesktopPane1.setLayer(lbl_pic, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout jDesktopPane1Layout = new javax.swing.GroupLayout(jDesktopPane1);
        jDesktopPane1.setLayout(jDesktopPane1Layout);
        jDesktopPane1Layout.setHorizontalGroup(
            jDesktopPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jDesktopPane1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lbl_pic, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jDesktopPane1Layout.setVerticalGroup(
            jDesktopPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jDesktopPane1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lbl_pic, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
                .addContainerGap())
        );

        jLabel17.setFont(new java.awt.Font("Serif", 3, 12)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(102, 102, 102));
        jLabel17.setText("You're login as a student");

        jLabel23.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel23.setText("UIST Management System");

        txt_time.setForeground(new java.awt.Color(0, 153, 204));

        txt_date.setForeground(new java.awt.Color(0, 153, 204));

        jMenu1.setText("File");
        jMenuBar1.add(jMenu1);

        jMenu2.setText("Edit");
        jMenuBar1.add(jMenu2);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(313, 313, 313)
                .addComponent(jLabel15)
                .addGap(22, 22, 22)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(225, 225, 225)
                        .addComponent(jLabel23)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 24, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lbl_names, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btn_logout, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(50, 50, 50)
                        .addComponent(txt_date, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txt_time, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jDesktopPane1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 561, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel15)
                    .addComponent(lbl_names, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_logout)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(txt_time, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txt_date, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(39, 39, 39)
                                .addComponent(jDesktopPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 388, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel17)
                            .addComponent(jLabel23))))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btn_logoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_logoutActionPerformed
       close1();    
    }//GEN-LAST:event_btn_logoutActionPerformed

    private void btn_unenrolActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_unenrolActionPerformed

        String sql = "SELECT * FROM enrol_student  WHERE student_admission_number= '"+admission_number+"' AND "
        + "course_name = '"+combo_enrol_courses.getSelectedItem().toString()+"'";
        String checker = null;
        try{
            pst = conn.prepareStatement(sql);
            rs =pst.executeQuery();

            if(rs.next()){
                checker = rs.getString("checker");

            }

        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
        //System.out.println(checker);
        if(combo_enrol_courses.getSelectedIndex() != 0 ){
            if(Integer.parseInt(checker) <= 6){
                int option = JOptionPane.YES_NO_CANCEL_OPTION;

                String select = "DELETE FROM enrol_student  WHERE student_admission_number= '"+admission_number+"' AND "
                + "course_name = '"+combo_enrol_courses.getSelectedItem().toString()+"'";
                
                String deleteMid12 = "DELETE FROM mid12 WHERE admission_number = '"+admission_number+"' AND course_name = "
                        + "'"+combo_enrol_courses.getSelectedItem().toString()+"'";

                try{
                    pst = conn.prepareStatement(select);
                    pst.execute();

                    combo_enrol_courses.setSelectedIndex(0);
                    JOptionPane.showMessageDialog(null, "You are now unenrol for the course "+ combo_enrol_courses.getSelectedItem().toString());
                }catch(Exception e){
                    JOptionPane.showMessageDialog(null, e);
                }
                try{
                    pst2 = conn.prepareStatement(deleteMid12);
                    pst2.execute();                
                }catch(Exception e){
                
                }
                
            }else{
                JOptionPane.showMessageDialog(null, "You can't unenrol from this course, You have a positive score, See Admin");
            }

        }else{
            JOptionPane.showMessageDialog(null, "Choose the subject you want to unenrol from the drop down list");
        }
        combo_enrol_courses.removeAllItems();
        fill_unerollcourses_field();
    }//GEN-LAST:event_btn_unenrolActionPerformed

    private void combo_course_namePopupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {//GEN-FIRST:event_combo_course_namePopupMenuWillBecomeInvisible

        String course_name1 = combo_course_name.getSelectedItem().toString();
        String sqlCourse = "SELECT * FROM courses WHERE course_name = '"+course_name1+"'";
        try{
            pst = conn.prepareStatement(sqlCourse);
            rs = pst.executeQuery();
            if(rs.next()){
                txt_course_code.setText(rs.getString("course_code"));
                txt_course_year.setText(rs.getString("course_year"));
                txt_course_semester.setText(rs.getString("course_semester"));
                fill_teacher_name(course_name1);
            }
        }catch(Exception e){
            JOptionPane.showMessageDialog(null,e);
        }
        
    }//GEN-LAST:event_combo_course_namePopupMenuWillBecomeInvisible

    private void btn_enrolActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_enrolActionPerformed
        
        /*##########################################################################################################*/
        /*Check student is already enrolled for the course */
        
        int enrolCheck = 0;
        String checkIfStudentAlreadyEnroll = "SELECT COUNT(course_name) from enrol_student WHERE course_name = '"+combo_course_name.getSelectedItem().toString()+"'"
                + " AND student_admission_number = '"+admission_number+"'";
        
        try{
            pst = conn.prepareStatement(checkIfStudentAlreadyEnroll);
            rs = pst.executeQuery();
            if(rs.next()){
               enrolCheck = Integer.parseInt(rs.getString("COUNT(course_name)"));
            }
        }catch(Exception e){
        
        }
        
         /*##########################################################################################################*/
        
        String names = null;
        if(middle_name == null || middle_name.equals("")){
           names = first_name + " " + last_name;
        }else{
          names = first_name + " " + middle_name + " " + last_name;
        }
        
       
        if(combo_course_name.getSelectedIndex() != 0){
            

            String sql = "INSERT INTO enrol_student (student_admission_number, course_code, course_name,enrolment_year, enrolment_semester,names,faculty) "
            + "  VALUES(?,?,?,?,?,?,?)";

            try{
                if(enrolCheck == 0){
                pst = conn.prepareStatement(sql);

                pst.setString(1,admission_number);
                pst.setString(2,txt_course_code.getText());
                pst.setString(3, combo_course_name.getSelectedItem().toString());
                pst.setString(4,txt_course_year.getText());
                pst.setString(5, txt_course_semester.getText());
                pst.setString(6,names);
                pst.setString(7,faculty);

                pst.execute();
                
                String updatemid12 = "INSERT INTO mid12 (names, admission_number, course_name, course_code) VALUES(?,?,?,?)";
                 pst2 = conn.prepareStatement(updatemid12);
                 pst2.setString(1,names);
                 pst2.setString(2,admission_number);
                 pst2.setString(3,combo_course_name.getSelectedItem().toString());
                 pst2.setString(4,txt_course_code.getText());
                 
                 pst2.execute();
                
                JOptionPane.showMessageDialog(null, "You are enrolled for the course " + combo_course_name.getSelectedItem().toString());
                clear_course_fields();
                }else{
                 JOptionPane.showMessageDialog(null, "You are already enrolled for the course " + combo_course_name.getSelectedItem().toString()
                 ,"ERROR",JOptionPane.ERROR_MESSAGE);   
                }
            }catch(Exception e){
                JOptionPane.showMessageDialog(null, e);
            }
        }else{
            JOptionPane.showMessageDialog(null, "Please choose a subject");
        }
        combo_course_name.removeAllItems();
        combo_enrol_courses.removeAllItems();
        fill_unerollcourses_field();
        enroll_courses_filled(admission_number);
    }//GEN-LAST:event_btn_enrolActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed

    }//GEN-LAST:event_jButton1ActionPerformed

    private void combo_enrol_coursesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_combo_enrol_coursesActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_combo_enrol_coursesActionPerformed

    private void documents_coursesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_documents_coursesMouseClicked
        int row = documents_courses.getSelectedRow();
        String value = (documents_courses.getModel().getValueAt(row, 3).toString());
        try{

            Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler "+value);

        }catch(Exception e){
            JOptionPane.showMessageDialog(null, "Error");
        }
    }//GEN-LAST:event_documents_coursesMouseClicked

    private void combo_enrol_courses1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_combo_enrol_courses1MouseClicked
       lbl_courses_doc_name.setText(combo_enrol_courses1.getSelectedItem().toString());
       documents_courses.setVisible(true);
       Update_table_documents();
    }//GEN-LAST:event_combo_enrol_courses1MouseClicked

    private void combo_enrol_courses1PopupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {//GEN-FIRST:event_combo_enrol_courses1PopupMenuWillBecomeInvisible
        lbl_courses_doc_name.setText(combo_enrol_courses1.getSelectedItem().toString());
        documents_courses.setVisible(true);
        Update_table_documents();
    }//GEN-LAST:event_combo_enrol_courses1PopupMenuWillBecomeInvisible
 int checkifDetailsExist = 0;
public int checkUsername(){
    
 String checkEmailAndUsername = "SELECT COUNT(id) FROM students WHERE id != '"+txt_id.getText()+"' AND username = '"+txt_username.getText()+"'"; 
   try{
      pst = conn.prepareStatement(checkEmailAndUsername);
      rs = pst.executeQuery();
      if(rs.next()){
          checkifDetailsExist = Integer.parseInt(rs.getString("COUNT(id)"));
      }
   }catch(Exception e){
   
   }
   return checkifDetailsExist;
}
    private void btn_login_changeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_login_changeActionPerformed
 
       if(!txt_username.getText().isEmpty() && !txt_password.getText().isEmpty()) {
        if(txt_username.getText().length() >=5){
            if(txt_password.getText().length() >=5){
                if(checkUsername() == 0){
                    
        try{
           
            String sql = "UPDATE students  SET username = '"+txt_username.getText()+"', password = '"+txt_password.getText()+"' "
                         + " WHERE id = '"+txt_id.getText()+"'";
            pst = conn.prepareStatement(sql);
            pst.execute();
                txt_username.setBackground(Color.WHITE);
                txt_password.setBackground(Color.WHITE);
                JOptionPane.showMessageDialog(null, "Credentails Updated");
        } catch(Exception e)  {
            JOptionPane.showMessageDialog(null, e);
        }
                }else{
            JOptionPane.showMessageDialog(null, "Username already taken by another registered user", "ERROR",JOptionPane.ERROR_MESSAGE);
            txt_username.setBackground(Color.RED);     
                }
        }else{
            JOptionPane.showMessageDialog(null, "The password must be at least 5 characters long", "ERROR",JOptionPane.ERROR_MESSAGE);
            txt_password.setBackground(Color.RED);        
        }
        }else{
            JOptionPane.showMessageDialog(null, "The username must be at least 5 characters long", "ERROR",JOptionPane.ERROR_MESSAGE);
            txt_username.setBackground(Color.RED);
        }  
       }else{
            JOptionPane.showMessageDialog(null, "The username and password cannot be empty", "Data NOT updated",JOptionPane.ERROR_MESSAGE);
            txt_username.setBackground(Color.RED);
       }        
        
    }//GEN-LAST:event_btn_login_changeActionPerformed

    private void combo_enrol_courses1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_combo_enrol_courses1KeyReleased
        lbl_courses_doc_name.setText(combo_enrol_courses1.getSelectedItem().toString());
        documents_courses.setVisible(true);
        Update_table_documents();
    }//GEN-LAST:event_combo_enrol_courses1KeyReleased

    public void close(){
		WindowEvent winClosingEvent = new WindowEvent(this,WindowEvent.WINDOW_CLOSING);
		Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(winClosingEvent);          
	}
    public void close1(){
                close();
                
                Login frameLogin = new Login();
                frameLogin.setLocationRelativeTo(null);
                frameLogin.setVisible(true);   
    }

    private void clear_course_fields(){
        combo_course_name.setSelectedIndex(0);
        txt_course_code.setText("");
        txt_course_year.setText("");
        txt_course_semester.setText("");
        txt_professor_name.setText("");
        teaching_assistant_names.setText("");
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_enrol;
    private javax.swing.JButton btn_login_change;
    private javax.swing.JButton btn_logout;
    private javax.swing.JButton btn_unenrol;
    private javax.swing.JComboBox<String> combo_course_name;
    private javax.swing.JComboBox<String> combo_enrol_courses;
    private javax.swing.JComboBox<String> combo_enrol_courses1;
    private javax.swing.JTable documents_courses;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton3;
    private javax.swing.JDesktopPane jDesktopPane1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JLabel lbl_courses_doc_name;
    private javax.swing.JLabel lbl_names;
    private javax.swing.JLabel lbl_pic;
    private javax.swing.JTable table_courses;
    private javax.swing.JTextField teaching_assistant_names;
    private javax.swing.JTextField txt_address;
    private javax.swing.JTextField txt_birth_day;
    private javax.swing.JTextField txt_country;
    private javax.swing.JTextField txt_course_code;
    private javax.swing.JTextField txt_course_semester;
    private javax.swing.JTextField txt_course_year;
    private javax.swing.JLabel txt_date;
    private javax.swing.JTextField txt_email;
    private javax.swing.JTextField txt_faculty;
    private javax.swing.JTextArea txt_feed1;
    private javax.swing.JPanel txt_feed_area1;
    private javax.swing.JTextField txt_fn;
    private javax.swing.JTextField txt_gender;
    private javax.swing.JTextField txt_id;
    private javax.swing.JTextField txt_ln;
    private javax.swing.JTextField txt_mn;
    private javax.swing.JPasswordField txt_password;
    private javax.swing.JTextField txt_phone;
    private javax.swing.JTextField txt_professor_name;
    private javax.swing.JTextField txt_student_status;
    private javax.swing.JTextField txt_study_year;
    private javax.swing.JLabel txt_time;
    private javax.swing.JTextField txt_username;
    // End of variables declaration//GEN-END:variables

    String attachFileEmail_path = null;

}
