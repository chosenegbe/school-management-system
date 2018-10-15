/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package teacher;

import AdminWelcomepage.Student;
import charts.ChartResult;
import com.sun.glass.events.KeyEvent;
import connection.DBConnection;
import java.awt.Color;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.InputStream;
import static java.lang.Thread.sleep;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.event.TableModelEvent;
import methodClass.insertFirstSecondMidterm;
import methodClass.insertUpdateResult;
import net.proteanit.sql.DbUtils;
import schoolmanagementsystem.Login;

/**
 *
 * @author Chosen Egbe
 */
public class TeacherSection extends javax.swing.JFrame {
    Connection conn = null;
    PreparedStatement pst = null;
    ResultSet rs  = null;
    PreparedStatement pst2 = null;
    ResultSet rs2  = null;   
    String username = null;
    String id = null;
    String admission_number = null;
    insertFirstSecondMidterm result = new insertFirstSecondMidterm();
     String course_code = "";
    
    public TeacherSection(String username1, String id1) {
        initComponents();
        conn = DBConnection.connectDB();
        getTeacherdata(username1,id1);
        fill_list_box_courses();
        currentDate();
       
        //updateTable();
    }
   
   private void updateTable(){
       
       
       try{
           String sql = "SELECT names, faculty,signature FROM enrol_student WHERE course_name = '"+jList1.getSelectedValue().toString()+"'";
           pst = conn.prepareStatement(sql);
           rs = pst.executeQuery();
           table_enrol_student.setModel(DbUtils.resultSetToTableModel(rs));
           lbl_course_name.setText(jList1.getSelectedValue());
           lbl_course_name1.setText(jList1.getSelectedValue());
           lbl_course_name2.setText(jList1.getSelectedValue());
         
            
           
          
       }catch(Exception e){
        JOptionPane.showMessageDialog(null,e);
       }
   }
   private void updateMidterms(){
       try{
          String sql = "SELECT id, names, first_midterm, second_midterm,Total,Grade FROM mid12 WHERE course_name = '"+jList1.getSelectedValue().toString()+"'";
           pst = conn.prepareStatement(sql);
           rs = pst.executeQuery();
           midterms_table.setModel(DbUtils.resultSetToTableModel(rs));  
          
       }catch(Exception e){
       
       }
   }
   private void getTeacherdata(String username1, String id1){
    
        String sql = "SELECT * FROM teachers WHERE user_name = '"+username1+"' OR id = '"+id1+"'";
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
               txt_birth_day.setText(rs.getString("dob"));
               txt_email.setText(rs.getString("email_address"));               
               txt_address.setText(rs.getString("address"));
               txt_phone.setText(rs.getString("phone_number"));
               txt_admission_date.setText(rs.getString("admission_date"));
               
               txt_position.setText(rs.getString("position"));
               
               txt_username.setText(rs.getString("user_name"));
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
            JOptionPane.showMessageDialog(null, e);
        }
    }
      private void fill_list_box_courses(){
        DefaultListModel dlm = new DefaultListModel();
  
        try{
         String sql = "SELECT DISTINCT(course_name) FROM assign_courses WHERE teacher_id = '"+txt_id.getText()+"'";
         pst = conn.prepareStatement(sql);
         rs = pst.executeQuery();
        while(rs.next()){
             String course_names = rs.getString("course_name");
 
              dlm.addElement(course_names);
              
             }
             jList1.setFont( new Font("monospaced", Font.PLAIN, 12) );
             jList1.setModel(dlm);
        }catch(Exception e){
        }
    }
        private void getScoreMaxMinAvg(){
          if(!lbl_course_name2.getText().isEmpty()){
            try{
        try{
            String sql = "SELECT min(Total),max(Total), avg(Total) FROM mid12 WHERE course_name = '"+lbl_course_name2.getText()+"'";
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();

            if(rs.next()){
                String max = rs.getString("max(Total)");
                txt_max_score.setText(max);
                String min = rs.getString("min(Total)");
                txt_min_score.setText(min);
                String avg = rs.getString("avg(Total)");
                txt_average_score.setText(avg);

            }
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, e);
        }            
            }catch(Exception e){
            
            }
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
      
       private void Update_table_documents(){
            String sql = "SELECT id,course_name,comments,doc_path FROM document_courses WHERE course_name = '"+lbl_course_name2.getText()+"'";
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
            java.util.logging.Logger.getLogger(TeacherSection.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TeacherSection.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TeacherSection.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TeacherSection.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                TeacherSection frame = new TeacherSection("", "");
                frame.setLocationRelativeTo(null);
                frame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
                frame.setTitle("Teacher section");
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

        txt_doc_comment = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        txt_phone = new javax.swing.JTextField();
        txt_gender = new javax.swing.JTextField();
        txt_admission_date = new javax.swing.JTextField();
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
        jLabel10 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txt_id = new javax.swing.JTextField();
        txt_mn = new javax.swing.JTextField();
        txt_country = new javax.swing.JTextField();
        txt_position = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        txt_address = new javax.swing.JTextField();
        txt_fn = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        txt_birth_day = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        lbl_names = new javax.swing.JLabel();
        btn_logout = new javax.swing.JButton();
        txt_date = new javax.swing.JLabel();
        txt_time = new javax.swing.JLabel();
        jDesktopPane1 = new javax.swing.JDesktopPane();
        lbl_pic = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jList1 = new javax.swing.JList<>();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        table_enrol_student = new javax.swing.JTable();
        btn_print_list = new javax.swing.JButton();
        jLabel18 = new javax.swing.JLabel();
        lbl_course_name = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLabel19 = new javax.swing.JLabel();
        lbl_course_name1 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        midterms_table = new javax.swing.JTable();
        btn_add_score = new javax.swing.JButton();
        txt_first_midterm = new javax.swing.JTextField();
        txt_second_midterm = new javax.swing.JTextField();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        lbl_name_midterm = new javax.swing.JLabel();
        txt_admission_number = new javax.swing.JLabel();
        lbl_course_code = new javax.swing.JLabel();
        btn_print_midterms = new javax.swing.JButton();
        jLabel24 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        txt_max_score = new javax.swing.JTextField();
        txt_min_score = new javax.swing.JTextField();
        txt_average_score = new javax.swing.JTextField();
        jPanel5 = new javax.swing.JPanel();
        btn_attach = new javax.swing.JButton();
        txt_attach_path = new javax.swing.JTextField();
        btn_save_doc = new javax.swing.JButton();
        open_web_page = new javax.swing.JButton();
        jScrollPane4 = new javax.swing.JScrollPane();
        documents_courses = new javax.swing.JTable();
        txt_doc_no = new javax.swing.JTextField();
        txt_doc_comment1 = new javax.swing.JTextField();
        jLabel20 = new javax.swing.JLabel();
        lbl_course_name2 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        view_statistics = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenu2 = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Teacher Information", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Serif", 1, 14), new java.awt.Color(204, 204, 255))); // NOI18N

        txt_phone.setEnabled(false);

        txt_gender.setEnabled(false);

        txt_admission_date.setEnabled(false);

        jLabel6.setText("Address");

        jLabel13.setText("Position");

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Edit Login Details", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Serif", 2, 12), new java.awt.Color(255, 51, 51))); // NOI18N

        jLabel8.setText("Username");

        jLabel9.setText("Password");

        btn_login_change.setFont(new java.awt.Font("Serif", 1, 10)); // NOI18N
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
                .addGap(0, 23, Short.MAX_VALUE))
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

        jLabel12.setText("Admission Date");

        jLabel11.setText("Country");

        jLabel2.setText("Last Name");

        txt_ln.setEnabled(false);
        txt_ln.setPreferredSize(new java.awt.Dimension(146, 20));

        txt_email.setEnabled(false);

        jLabel10.setText("Gender");

        jLabel14.setText("Teacher ID");

        jLabel3.setText("Middle Name");

        txt_id.setEnabled(false);
        txt_id.setPreferredSize(new java.awt.Dimension(146, 20));

        txt_mn.setEnabled(false);
        txt_mn.setPreferredSize(new java.awt.Dimension(146, 20));

        txt_country.setEnabled(false);

        txt_position.setEnabled(false);

        jLabel5.setText("Phone");

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
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
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
                                                            .addComponent(txt_admission_date, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                    .addComponent(txt_mn, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                                    .addComponent(jLabel10)
                                    .addComponent(jLabel11)
                                    .addComponent(jLabel5)
                                    .addComponent(jLabel12)
                                    .addComponent(jLabel16)
                                    .addComponent(jLabel6))
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel13)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(txt_position, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap())
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
                    .addComponent(txt_admission_date, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_position, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel13))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
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

        txt_date.setForeground(new java.awt.Color(0, 153, 204));

        txt_time.setForeground(new java.awt.Color(0, 153, 204));

        jDesktopPane1.setBackground(new java.awt.Color(204, 204, 255));

        jDesktopPane1.setLayer(lbl_pic, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout jDesktopPane1Layout = new javax.swing.GroupLayout(jDesktopPane1);
        jDesktopPane1.setLayout(jDesktopPane1Layout);
        jDesktopPane1Layout.setHorizontalGroup(
            jDesktopPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jDesktopPane1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lbl_pic, javax.swing.GroupLayout.DEFAULT_SIZE, 101, Short.MAX_VALUE)
                .addContainerGap())
        );
        jDesktopPane1Layout.setVerticalGroup(
            jDesktopPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jDesktopPane1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lbl_pic, javax.swing.GroupLayout.DEFAULT_SIZE, 106, Short.MAX_VALUE)
                .addContainerGap())
        );

        jLabel23.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel23.setText("UIST Management System");

        jLabel17.setFont(new java.awt.Font("Serif", 3, 12)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(102, 102, 102));
        jLabel17.setText("You're login as a staff");

        jList1.setBackground(new java.awt.Color(204, 255, 204));
        jList1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jList1MouseClicked(evt);
            }
        });
        jList1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jList1KeyReleased(evt);
            }
        });
        jScrollPane1.setViewportView(jList1);

        table_enrol_student.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane2.setViewportView(table_enrol_student);

        btn_print_list.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        btn_print_list.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgresource/printer-icon.png"))); // NOI18N
        btn_print_list.setText("Print student list");
        btn_print_list.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_print_listActionPerformed(evt);
            }
        });

        jLabel18.setText("Course Name");

        lbl_course_name.setFont(new java.awt.Font("Serif", 1, 14)); // NOI18N

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 358, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel18)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lbl_course_name, javax.swing.GroupLayout.PREFERRED_SIZE, 214, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 30, Short.MAX_VALUE)
                        .addComponent(btn_print_list)))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 404, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(29, 29, 29)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel18)
                            .addComponent(lbl_course_name, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(btn_print_list)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );

        jTabbedPane1.addTab("Enroll Sudent", jPanel3);

        jLabel19.setText("Course Name");

        lbl_course_name1.setFont(new java.awt.Font("Serif", 1, 14)); // NOI18N

        midterms_table.setModel(new javax.swing.table.DefaultTableModel(
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
        midterms_table.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                midterms_tableMouseClicked(evt);
            }
        });
        midterms_table.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                midterms_tableKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                midterms_tableKeyTyped(evt);
            }
        });
        jScrollPane3.setViewportView(midterms_table);

        btn_add_score.setText("Update Result");
        btn_add_score.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_add_scoreActionPerformed(evt);
            }
        });

        txt_first_midterm.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_first_midtermKeyTyped(evt);
            }
        });

        txt_second_midterm.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_second_midtermKeyTyped(evt);
            }
        });

        jLabel21.setFont(new java.awt.Font("Serif", 2, 11)); // NOI18N
        jLabel21.setForeground(new java.awt.Color(51, 51, 51));
        jLabel21.setText("First Midterm");

        jLabel22.setFont(new java.awt.Font("Serif", 2, 11)); // NOI18N
        jLabel22.setForeground(new java.awt.Color(51, 51, 51));
        jLabel22.setText("Second Midterm");

        btn_print_midterms.setFont(new java.awt.Font("Serif", 1, 10)); // NOI18N
        btn_print_midterms.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgresource/printer-icon.png"))); // NOI18N
        btn_print_midterms.setText("Print Result");
        btn_print_midterms.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_print_midtermsActionPerformed(evt);
            }
        });

        jLabel24.setFont(new java.awt.Font("Serif", 1, 12)); // NOI18N
        jLabel24.setForeground(new java.awt.Color(0, 153, 204));
        jLabel24.setText("Max Score");

        jLabel25.setFont(new java.awt.Font("Serif", 1, 12)); // NOI18N
        jLabel25.setForeground(new java.awt.Color(0, 153, 204));
        jLabel25.setText("Min Score");

        jLabel26.setFont(new java.awt.Font("Serif", 1, 12)); // NOI18N
        jLabel26.setForeground(new java.awt.Color(0, 153, 204));
        jLabel26.setText("Average");

        txt_max_score.setEditable(false);

        txt_min_score.setEditable(false);

        txt_average_score.setEditable(false);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 344, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(jLabel19)
                                .addGap(10, 10, 10)
                                .addComponent(lbl_course_name1, javax.swing.GroupLayout.PREFERRED_SIZE, 214, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lbl_course_code, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(jLabel24)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txt_max_score, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel25)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txt_min_score, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel26)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txt_average_score, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(txt_first_midterm, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel22)
                                .addComponent(txt_second_midterm, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGap(54, 54, 54)
                            .addComponent(lbl_name_midterm, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(jLabel21)
                        .addComponent(btn_add_score))
                    .addComponent(btn_print_midterms)
                    .addComponent(txt_admission_number, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel4Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 374, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel24)
                            .addComponent(jLabel25)
                            .addComponent(txt_max_score, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txt_min_score, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel26)
                            .addComponent(txt_average_score, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel4Layout.createSequentialGroup()
                            .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lbl_name_midterm, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(339, 339, 339))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                            .addComponent(jLabel21)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(txt_first_midterm, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jLabel22)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(txt_second_midterm, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(btn_add_score)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(txt_admission_number, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btn_print_midterms)
                            .addComponent(lbl_course_code, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel19)
                        .addComponent(lbl_course_name1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );

        jTabbedPane1.addTab("Add Score", jPanel4);

        btn_attach.setText("Add Document");
        btn_attach.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_attachActionPerformed(evt);
            }
        });

        txt_attach_path.setEnabled(false);

        btn_save_doc.setText("Save Document");
        btn_save_doc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_save_docActionPerformed(evt);
            }
        });

        open_web_page.setText("Open Web");
        open_web_page.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                open_web_pageActionPerformed(evt);
            }
        });

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
        jScrollPane4.setViewportView(documents_courses);

        txt_doc_no.setEnabled(false);

        jLabel20.setText("Course Name");

        lbl_course_name2.setFont(new java.awt.Font("Serif", 1, 14)); // NOI18N

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btn_save_doc)
                            .addComponent(open_web_page))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addComponent(txt_doc_no, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txt_doc_comment1, javax.swing.GroupLayout.PREFERRED_SIZE, 314, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(btn_attach)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txt_attach_path, javax.swing.GroupLayout.PREFERRED_SIZE, 363, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel20)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lbl_course_name2, javax.swing.GroupLayout.PREFERRED_SIZE, 214, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txt_attach_path, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_attach))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_save_doc)
                    .addComponent(txt_doc_no, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_doc_comment1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(open_web_page)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 348, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(0, 7, Short.MAX_VALUE)
                        .addComponent(jLabel20))
                    .addComponent(lbl_course_name2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        jTabbedPane1.addTab("Add Material", jPanel5);

        jLabel7.setFont(new java.awt.Font("Serif", 1, 14)); // NOI18N
        jLabel7.setText("Courses You Manage");

        view_statistics.setFont(new java.awt.Font("Serif", 1, 10)); // NOI18N
        view_statistics.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgresource/Statistics-icon.png"))); // NOI18N
        view_statistics.setText("Choose Subject to view Statistics");
        view_statistics.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                view_statisticsActionPerformed(evt);
            }
        });

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
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jLabel23)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel15)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lbl_names, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jDesktopPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel7))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                                .addGap(10, 10, 10))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btn_logout, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(view_statistics, javax.swing.GroupLayout.PREFERRED_SIZE, 242, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 102, Short.MAX_VALUE)
                                .addComponent(txt_date, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txt_time, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap())))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel15)
                    .addComponent(lbl_names, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btn_logout)
                        .addComponent(view_statistics))
                    .addComponent(txt_date, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_time, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jDesktopPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(12, 12, 12)
                        .addComponent(jLabel7))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 324, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 493, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel23)
                    .addComponent(jLabel17))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btn_logoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_logoutActionPerformed
        close1();
    }//GEN-LAST:event_btn_logoutActionPerformed
int checkifDetailsExist = 0;
public int checkUsername(){
    
 String checkEmailAndUsername = "SELECT COUNT(id) FROM teachers WHERE id != '"+txt_id.getText()+"' AND user_name = '"+txt_username.getText()+"'"; 
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
           
            String sql = "UPDATE teachers  SET user_name = '"+txt_username.getText()+"', password = '"+txt_password.getText()+"' "
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

    private void jList1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jList1MouseClicked
                                     
        updateTable();
        updateMidterms();
        Update_table_documents();
        getScoreMaxMinAvg();
        try{
           String sql = "SELECT course_code FROM enrol_student WHERE course_name = '"+jList1.getSelectedValue().toString()+"'";
           pst = conn.prepareStatement(sql);
           rs = pst.executeQuery();
           if(rs.next()){
            course_code = rs.getString("course_code");
           }
        }catch(Exception e){
           JOptionPane.showMessageDialog(null, "WRONG");
        }
        lbl_course_code.setText(course_code + "hey");
        
        
    }//GEN-LAST:event_jList1MouseClicked
 
    private void btn_print_listActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_print_listActionPerformed
        
        if(jList1.getSelectedValue().toString() !=""){
                                MessageFormat header = new MessageFormat("Students for the Course " + jList1.getSelectedValue());
				MessageFormat footer = new MessageFormat("Page{0,number,integer}");
				
				try{
					table_enrol_student.print(JTable.PrintMode.NORMAL,header,footer);// print the table										
				}catch(java.awt.print.PrinterException e){
					
					System.err.format("Cannot print %s %n", e.getMessage());
				}
                                
        }else{
            JOptionPane.showMessageDialog(null, "Can't print empty table", "WARNING",JOptionPane.ERROR_MESSAGE);
        }
       
    }//GEN-LAST:event_btn_print_listActionPerformed

    private void btn_add_scoreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_add_scoreActionPerformed
         
        int rowResult = midterms_table.getSelectedRow();
        String result_table_click = (midterms_table.getModel().getValueAt(rowResult, 0).toString());      
        if(!txt_first_midterm.getText().isEmpty() && !txt_second_midterm.getText().isEmpty() && !lbl_course_name2.getText().isEmpty()){
                
               result.updateResult(txt_first_midterm.getText(), txt_second_midterm.getText(),result_table_click);
                
               updateMidterms();
            }else{
            JOptionPane.showMessageDialog(null,"Please fill in all text field appropriately");
        }if(!course_code.equalsIgnoreCase("")){
             if(!txt_first_midterm.getText().isEmpty() && !txt_second_midterm.getText().isEmpty() && !lbl_course_name2.getText().isEmpty()){
           
           /*#######################################################################################################
            Check to see if the student already has mark for the course           
           #######################################################################################################*/
           String checkCourse = "SELECT COUNT(course_name) FROM grade WHERE course_name='"+lbl_course_name2.getText()+"' AND student_admission_number"
                   + "='"+txt_admission_number.getText()+"'";
          
           try{
               pst = conn.prepareStatement(checkCourse);
               rs = pst.executeQuery();
               if(rs.next()){
                   int count = rs.getInt("COUNT(course_name)");
                   if(count >= 1){
                       
                         result.update_score_result(txt_admission_number.getText(), txt_first_midterm.getText(),txt_second_midterm.getText(),lbl_course_name2.getText(), course_code);
                         
                       //JOptionPane.showMessageDialog(null,"Student already has mark for the subject");
               txt_admission_number.setText("");
               txt_first_midterm.setText("");
               txt_second_midterm.setText("");
               lbl_name_midterm.setText("");
                          
               }else{
                       //call the function score_result to insert the data into database              
                       result.add_score_result(txt_admission_number.getText(), txt_first_midterm.getText(),txt_second_midterm.getText(),lbl_course_name2.getText(), course_code);               
               txt_admission_number.setText("");
               txt_first_midterm.setText("");
               txt_second_midterm.setText("");
               lbl_name_midterm.setText("");
                   }
               }
           }catch(Exception e){
               JOptionPane.showMessageDialog(null,e);
           }                   
              // result.add_score_result(txt_admission_number.getText(), lbl_course_name2.getText(),course_code);
               
              
            }else{
            JOptionPane.showMessageDialog(null,"Please fill in all text field appropriately");
        }
        }else{
           JOptionPane.showMessageDialog(null,"Choose a subject");
        }
         getScoreMaxMinAvg();
        
    }//GEN-LAST:event_btn_add_scoreActionPerformed

    private void btn_attachActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_attachActionPerformed
        JFileChooser chooser = new JFileChooser();
        chooser.showOpenDialog(null);
        File f = chooser.getSelectedFile();
        //File f = chooser.getSelectedFile();
        String filename = f.getAbsolutePath();
        txt_attach_path.setText(filename);
    }//GEN-LAST:event_btn_attachActionPerformed

    private void btn_save_docActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_save_docActionPerformed
        try{
            String sql = "INSERT INTO document_courses(course_name, comments, doc_path) VALUES(?,?,?)";
          if(!lbl_course_name.getText().isEmpty()){
            if(!txt_attach_path.getText().isEmpty()){
                if(!txt_doc_comment1.getText().isEmpty()){
            pst = conn.prepareStatement(sql);

            pst.setString(1,lbl_course_name.getText());
            pst.setString(2,txt_doc_comment1.getText());
            pst.setString(3,txt_attach_path.getText());
            
            pst.execute();
            txt_attach_path.setText("");
            txt_doc_comment1.setText("");
            JOptionPane.showMessageDialog(null, "Document inserted for course "+jList1.getSelectedValue(),"Upload Successful",JOptionPane.INFORMATION_MESSAGE);
                }else{
                JOptionPane.showMessageDialog(null, "Add file description","ERROR",JOptionPane.ERROR_MESSAGE);
                }
                }else{
                JOptionPane.showMessageDialog(null, "You need to choose a file to upload","ERROR",JOptionPane.ERROR_MESSAGE);
            }
            }else{
              JOptionPane.showMessageDialog(null, "You need to choose a course to upload the file","ERROR",JOptionPane.ERROR_MESSAGE);
          }
            //JOptionPane.showMessageDialog(null, "Document Saved");
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
        Update_table_documents();
    }//GEN-LAST:event_btn_save_docActionPerformed

    private void open_web_pageActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_open_web_pageActionPerformed
        try{
            String URL ="https://www.google.com/";

            java.awt.Desktop.getDesktop().browse(java.net.URI.create(URL));
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }//GEN-LAST:event_open_web_pageActionPerformed

    private void documents_coursesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_documents_coursesMouseClicked
        int row = documents_courses.getSelectedRow();
        String value = (documents_courses.getModel().getValueAt(row, 3).toString());
        try{

            Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler "+value);

        }catch(Exception e){
            JOptionPane.showMessageDialog(null, "Error");
        }
    }//GEN-LAST:event_documents_coursesMouseClicked

    private void midterms_tableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_midterms_tableMouseClicked
        resultsUpdate();
    }//GEN-LAST:event_midterms_tableMouseClicked

    private void view_statisticsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_view_statisticsActionPerformed
           if(jList1.getSelectedValue().toString() !=""){
        ChartResult chart = new ChartResult(jList1.getSelectedValue());
        chart.setTitle("Select how to view statististic");
        chart.setLocationRelativeTo(null);
        chart.setVisible(true);
           }else{
           JOptionPane.showMessageDialog(null, "Select a course to view it`s statistic", "WARNING",JOptionPane.ERROR_MESSAGE);
           }
    }//GEN-LAST:event_view_statisticsActionPerformed

    private void btn_print_midtermsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_print_midtermsActionPerformed
       
                if(jList1.getSelectedValue().toString() !=""){
                                MessageFormat header = new MessageFormat("Result for " + jList1.getSelectedValue());
				MessageFormat footer = new MessageFormat("Page{0,number,integer}");
				
				try{
					midterms_table.print(JTable.PrintMode.NORMAL,header,footer);// print the table										
				}catch(java.awt.print.PrinterException e){
					
					System.err.format("Cannot print %s %n", e.getMessage());
				}
                                
        }else{
            JOptionPane.showMessageDialog(null, "Can't print empty table", "WARNING",JOptionPane.ERROR_MESSAGE);
        }   
    }//GEN-LAST:event_btn_print_midtermsActionPerformed

    private void jList1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jList1KeyReleased
  if(evt.getKeyCode() == java.awt.event.KeyEvent.VK_DOWN || evt.getKeyCode() == java.awt.event.KeyEvent.VK_UP){        
        updateTable();
        updateMidterms();
        Update_table_documents();
        getScoreMaxMinAvg();
        try{
           String sql = "SELECT course_code FROM enrol_student WHERE course_name = '"+jList1.getSelectedValue().toString()+"'";
           pst = conn.prepareStatement(sql);
           rs = pst.executeQuery();
           if(rs.next()){
            course_code = rs.getString("course_code");
           }
        }catch(Exception e){
           JOptionPane.showMessageDialog(null, "WRONG");
        }
        lbl_course_code.setText(course_code);
         
     } 
    }//GEN-LAST:event_jList1KeyReleased

    private void txt_first_midtermKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_first_midtermKeyTyped
              char c = evt.getKeyChar();
         if(!(Character.isDigit(c) || c==KeyEvent.VK_BACKSPACE) || c==KeyEvent.VK_DELETE){
             evt.consume();
         }
    }//GEN-LAST:event_txt_first_midtermKeyTyped

    private void txt_second_midtermKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_second_midtermKeyTyped
                 char c = evt.getKeyChar();
         if(!(Character.isDigit(c) || c==KeyEvent.VK_BACKSPACE) || c==KeyEvent.VK_DELETE){
             evt.consume();
         }
    }//GEN-LAST:event_txt_second_midtermKeyTyped

    private void midterms_tableKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_midterms_tableKeyReleased
      if(evt.getKeyCode() == java.awt.event.KeyEvent.VK_DOWN || evt.getKeyCode() == java.awt.event.KeyEvent.VK_UP){       
        resultsUpdate();
      }
    }//GEN-LAST:event_midterms_tableKeyReleased

    private void midterms_tableKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_midterms_tableKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_midterms_tableKeyTyped
 
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

    public void resultsUpdate(){
        int rowSelect = midterms_table.getSelectedRow();       
        String student_table_click = (midterms_table.getModel().getValueAt(rowSelect, 0).toString());
        
        try{
         		String sql = "SELECT * FROM mid12 WHERE id ='"+student_table_click+"'";
			pst = conn.prepareStatement(sql);
			rs= pst.executeQuery();
                        if(rs.next()){
                            lbl_name_midterm.setText(rs.getString("names"));
                            txt_first_midterm.setText(rs.getString("first_midterm"));
                            txt_second_midterm.setText(rs.getString("second_midterm"));
                            txt_admission_number.setText(rs.getString("admission_number"));
                        }
                         //JOptionPane.showMessageDialog(null,"OK");
        }catch(Exception e){
                         JOptionPane.showMessageDialog(null,e);
                }
                  }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_add_score;
    private javax.swing.JButton btn_attach;
    private javax.swing.JButton btn_login_change;
    private javax.swing.JButton btn_logout;
    private javax.swing.JButton btn_print_list;
    private javax.swing.JButton btn_print_midterms;
    private javax.swing.JButton btn_save_doc;
    private javax.swing.JTable documents_courses;
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
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JList<String> jList1;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JLabel lbl_course_code;
    private javax.swing.JLabel lbl_course_name;
    private javax.swing.JLabel lbl_course_name1;
    private javax.swing.JLabel lbl_course_name2;
    private javax.swing.JLabel lbl_name_midterm;
    private javax.swing.JLabel lbl_names;
    private javax.swing.JLabel lbl_pic;
    private javax.swing.JTable midterms_table;
    private javax.swing.JButton open_web_page;
    private javax.swing.JTable table_enrol_student;
    private javax.swing.JTextField txt_address;
    private javax.swing.JTextField txt_admission_date;
    private javax.swing.JLabel txt_admission_number;
    private javax.swing.JTextField txt_attach_path;
    private javax.swing.JTextField txt_average_score;
    private javax.swing.JTextField txt_birth_day;
    private javax.swing.JTextField txt_country;
    private javax.swing.JLabel txt_date;
    private javax.swing.JTextField txt_doc_comment;
    private javax.swing.JTextField txt_doc_comment1;
    private javax.swing.JTextField txt_doc_no;
    private javax.swing.JTextField txt_email;
    private javax.swing.JTextField txt_first_midterm;
    private javax.swing.JTextField txt_fn;
    private javax.swing.JTextField txt_gender;
    private javax.swing.JTextField txt_id;
    private javax.swing.JTextField txt_ln;
    private javax.swing.JTextField txt_max_score;
    private javax.swing.JTextField txt_min_score;
    private javax.swing.JTextField txt_mn;
    private javax.swing.JPasswordField txt_password;
    private javax.swing.JTextField txt_phone;
    private javax.swing.JTextField txt_position;
    private javax.swing.JTextField txt_second_midterm;
    private javax.swing.JLabel txt_time;
    private javax.swing.JTextField txt_username;
    private javax.swing.JButton view_statistics;
    // End of variables declaration//GEN-END:variables
}
