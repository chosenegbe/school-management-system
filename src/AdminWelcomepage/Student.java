
package AdminWelcomepage;

import charts.ChartStudentSelect;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.sun.glass.events.KeyEvent;
import com.sun.mail.util.MailSSLSocketFactory;
import connection.DBConnection;
import java.awt.*;
import java.awt.Toolkit;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.*;
import static java.lang.Thread.sleep;
import java.sql.*;
import java.text.MessageFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Properties;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.imageio.ImageIO;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.swing.ImageIcon;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JRDesignQuery;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

import java.awt.Toolkit;
import java.awt.Rectangle;
import java.awt.Robot;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;
import methodClass.insertUpdateResult;
import net.proteanit.sql.DbUtils;
import schoolmanagementsystem.Login;

/**
 *
 * @author Chosen Egbe
 */
public class Student extends javax.swing.JFrame {
    Connection conn = null;
    PreparedStatement pst = null;
    PreparedStatement pst1 = null;
    ResultSet rs = null;
    ResultSet rs1 = null;
    insertUpdateResult result = new insertUpdateResult();
    
    String username = null;
    String id = null;
    public Student(String username1, String id1) {
        initComponents();
        conn = DBConnection.connectDB();
        Update_table();
        fill_table_grade();
        fillstudent_combo();
        fill_combo_courses();
        currentDate();
                 
        String getAdmissionNumber = "SELECT * FROM administrator WHERE username = '"+username1+"' AND admin_id = '"+id1+"'";
        
        try{
          pst = conn.prepareStatement(getAdmissionNumber);
          rs = pst.executeQuery();
          if(rs.next()){
              id = rs.getString("admin_id");
          }
        }catch(Exception e){
        
        }
        getNames(username1,id1);
        
        id = id1;
        username = username1;
        
        student_table.getColumnModel().getColumn(20).setMaxWidth(0);
        student_table.getColumnModel().getColumn(11).setMaxWidth(0);
        student_grade_table.getColumnModel().getColumn(0).setMaxWidth(0);
        student_grade_table.getColumnModel().getColumn(1).setMaxWidth(0);
        student_grade_table.getColumnModel().getColumn(3).setMaxWidth(0);
        
    }

    public void getNames(String username, String id){
        String sql = "SELECT * FROM administrator WHERE username = '"+username+"' AND admin_id = '"+id+"'";
        try{
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            if(rs.next()){
                lbl_names.setText(rs.getString("first_name") + " "+ rs.getString("last_name"));
            }
        }catch(Exception e){
             JOptionPane.showMessageDialog(null,e);
        }
    }    
    
    private void Update_table(){
		
		String sql = "SELECT * FROM students";
		try {
			pst = conn.prepareStatement(sql);
			rs = pst.executeQuery();
			student_table.setModel(DbUtils.resultSetToTableModel(rs));
			
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e);
			
		}finally{
			try{
				rs.close();
				pst.close();
			}catch(Exception e ){
			
		   }
			
		}
		
		
	}
    private void fill_table_grade(){
     
        String sql = "SELECT grade_id AS 'id', course_code AS 'Code',course_name AS 'Title of the Course', score AS 'Score',grade_num AS 'Grade(In numbers)', "
                + "grade_words AS 'AS Grade(In words)',remarks AS 'Remarks' FROM grade";
        try{
               pst = conn.prepareStatement(sql);
               rs  = pst.executeQuery();
               student_grade_table.setModel(DbUtils.resultSetToTableModel(rs));
        }catch(Exception e){
           JOptionPane.showMessageDialog(null, e);
        }
        finally{
			try{
				rs.close();
				pst.close();
			}catch(Exception e ){
			
		   }
			
		}
       
    }
        private void fill_table_grade(String admission_number){
     
        String sql = "SELECT grade_id AS 'id',course_code AS 'Code',course_name AS 'Title of the Course', score AS 'Score',grade_num AS 'Grade(In numbers)', "
                + "grade_words AS 'AS Grade(In words)',remarks AS 'Remarks' FROM grade WHERE student_admission_number = '"+admission_number+"'";
        try{
               pst = conn.prepareStatement(sql);
               rs  = pst.executeQuery();
               student_grade_table.setModel(DbUtils.resultSetToTableModel(rs));
        }catch(Exception e){
           JOptionPane.showMessageDialog(null, e);
        }
        finally{
			try{
				rs.close();
				pst.close();
			}catch(Exception e ){
			
		   }
			
		}
       
    }
	private void fillstudent_combo(){
                    
		try{
			String sql = "SELECT * FROM students";
			pst = conn.prepareStatement(sql);
			rs = pst.executeQuery();			
			
			while(rs.next()){
				String first_name = rs.getString("first_name");
				student_name_result.addItem(first_name);
			}
			
		}catch(Exception e){
			JOptionPane.showMessageDialog(null, e);
		}
		finally{
			try{
				rs.close();
				pst.close();
			}catch(Exception e ){
			
		   }
			
		}
	} 
        public void fill_combo_courses(){
           

           try{
                String sql = "SELECT * FROM courses WHERE course_year";
                pst = conn.prepareStatement(sql);
                rs = pst.executeQuery(sql);
                while(rs.next()){
                    String getCourse = rs.getString("course_name");
                    combo_subject_for_marks.addItem(getCourse);
                }          
           }catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
           }
           
           
           
           
           
           finally{
			try{
				rs.close();
				pst.close();
			}catch(Exception e ){
			
		   }
			
		}
           //String sql = "INSERT INTO grade(student_admission_number,course_name,course_code,score, grade_num, grade_words,remarks)";
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
    public void close(){
                    WindowEvent winClosingEvent = new WindowEvent(this,WindowEvent.WINDOW_CLOSING);
                    Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(winClosingEvent);
            }

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
            java.util.logging.Logger.getLogger(Student.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Student.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Student.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Student.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                Student frame = new Student("","");
                frame.pack();
                frame.setLocationRelativeTo(null);
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

        jFileChooser1 = new javax.swing.JFileChooser();
        jFileChooser2 = new javax.swing.JFileChooser();
        buttonGroup1 = new javax.swing.ButtonGroup();
        buttonGroup2 = new javax.swing.ButtonGroup();
        jScrollPane1 = new javax.swing.JScrollPane();
        student_table = new javax.swing.JTable();
        txt_date = new javax.swing.JLabel();
        txt_time = new javax.swing.JLabel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel8 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        txt_file_attach_path = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tArea_student_comment = new javax.swing.JTextArea();
        jDesktopPane1 = new javax.swing.JDesktopPane();
        lbl_pic = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        txt_date_of_birth = new com.toedter.calendar.JDateChooser();
        jLabel8 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        txt_mn = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        txt_fn = new javax.swing.JTextField();
        txt_ln = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        combo_nationality_list = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();
        combo_country_of_birth = new javax.swing.JComboBox<>();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        txt_username = new javax.swing.JTextField();
        txt_password = new javax.swing.JTextField();
        combo_gender = new javax.swing.JComboBox<>();
        jPanel5 = new javax.swing.JPanel();
        txt_searchCategory = new javax.swing.JTextField();
        combo_searchCategory = new javax.swing.JComboBox<>();
        jPanel2 = new javax.swing.JPanel();
        combo_faculty = new javax.swing.JComboBox<>();
        jLabel6 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txt_admission_number = new javax.swing.JTextField();
        txt_email = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        txt_student_id = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txt_admission_date = new com.toedter.calendar.JDateChooser();
        combo_year_of_studies = new javax.swing.JComboBox<>();
        jPanel4 = new javax.swing.JPanel();
        btn_clear = new javax.swing.JButton();
        btn_update = new javax.swing.JButton();
        btn_print = new javax.swing.JButton();
        btn_delete = new javax.swing.JButton();
        btn_chart = new javax.swing.JButton();
        btn_ireport = new javax.swing.JButton();
        jPanel16 = new javax.swing.JPanel();
        combo_full_part_time = new javax.swing.JComboBox<>();
        combo_status = new javax.swing.JComboBox<>();
        txt_phone_number = new javax.swing.JTextField();
        jLabel35 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        jLabel34 = new javax.swing.JLabel();
        jLabel36 = new javax.swing.JLabel();
        txt_address = new javax.swing.JTextField();
        jPanel9 = new javax.swing.JPanel();
        jPanel12 = new javax.swing.JPanel();
        jPanel13 = new javax.swing.JPanel();
        jButton2 = new javax.swing.JButton();
        txt_file_attach_path1 = new javax.swing.JTextField();
        jPanel14 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tArea_student_comment1 = new javax.swing.JTextArea();
        jDesktopPane2 = new javax.swing.JDesktopPane();
        lbl_pic1 = new javax.swing.JLabel();
        jPanel15 = new javax.swing.JPanel();
        txt_date_of_birth1 = new com.toedter.calendar.JDateChooser();
        jLabel11 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        txt_mn1 = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        txt_fn1 = new javax.swing.JTextField();
        txt_ln1 = new javax.swing.JTextField();
        jLabel21 = new javax.swing.JLabel();
        combo_nationality_list1 = new javax.swing.JComboBox<>();
        jLabel22 = new javax.swing.JLabel();
        combo_country_of_birth1 = new javax.swing.JComboBox<>();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        txt_username1 = new javax.swing.JTextField();
        jLabel33 = new javax.swing.JLabel();
        combo_student_status1 = new javax.swing.JComboBox<>();
        rbtn_male = new javax.swing.JRadioButton();
        rbtn_female = new javax.swing.JRadioButton();
        txt_password1 = new javax.swing.JPasswordField();
        jPanel17 = new javax.swing.JPanel();
        combo_faculty1 = new javax.swing.JComboBox<>();
        jLabel25 = new javax.swing.JLabel();
        txt_email1 = new javax.swing.JTextField();
        jLabel27 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        txt_admission_date1 = new com.toedter.calendar.JDateChooser();
        jLabel31 = new javax.swing.JLabel();
        txt_phone_number1 = new javax.swing.JTextField();
        jLabel32 = new javax.swing.JLabel();
        txt_address1 = new javax.swing.JTextField();
        combo_year_of_studies1 = new javax.swing.JComboBox<>();
        jLabel26 = new javax.swing.JLabel();
        combo_regular_part1 = new javax.swing.JComboBox<>();
        btn_update1 = new javax.swing.JButton();
        btn_clear1 = new javax.swing.JButton();
        jPanel11 = new javax.swing.JPanel();
        jLabel45 = new javax.swing.JLabel();
        jLabel46 = new javax.swing.JLabel();
        jLabel47 = new javax.swing.JLabel();
        txt_to = new javax.swing.JTextField();
        txt_bcc = new javax.swing.JTextField();
        txt_subject = new javax.swing.JTextField();
        jScrollPane5 = new javax.swing.JScrollPane();
        txArea_msg = new javax.swing.JTextArea();
        btn_sendMsg = new javax.swing.JButton();
        btn_msgattach = new javax.swing.JButton();
        txt_attahedmsgpath = new javax.swing.JTextField();
        txt_attachment_name = new javax.swing.JTextField();
        jLabel50 = new javax.swing.JLabel();
        jPanel10 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        student_grade_table = new javax.swing.JTable();
        jPanel18 = new javax.swing.JPanel();
        jLabel37 = new javax.swing.JLabel();
        txt_student_mark = new javax.swing.JTextField();
        txt_student_fname_add_score = new javax.swing.JTextField();
        jLabel42 = new javax.swing.JLabel();
        btn_add_result = new javax.swing.JButton();
        jLabel43 = new javax.swing.JLabel();
        jLabel44 = new javax.swing.JLabel();
        txt_student_lname_add_score = new javax.swing.JTextField();
        txt_student_studentID_add_score = new javax.swing.JTextField();
        jLabel38 = new javax.swing.JLabel();
        combo_subject_for_marks = new javax.swing.JComboBox<>();
        jPanel19 = new javax.swing.JPanel();
        jLabel39 = new javax.swing.JLabel();
        txt_grade_score = new javax.swing.JTextField();
        jLabel40 = new javax.swing.JLabel();
        jLabel41 = new javax.swing.JLabel();
        studentAddResultNames = new javax.swing.JTextField();
        txt_subject_update = new javax.swing.JTextField();
        update_score = new javax.swing.JButton();
        jPanel20 = new javax.swing.JPanel();
        student_name_result = new javax.swing.JComboBox<>();
        jPanel21 = new javax.swing.JPanel();
        btn_print_grade = new javax.swing.JButton();
        btn_reportGrade = new javax.swing.JButton();
        btn_iReport_grade = new javax.swing.JButton();
        jLabel48 = new javax.swing.JLabel();
        jLabel49 = new javax.swing.JLabel();
        lbl_names = new javax.swing.JLabel();
        btn_logout = new javax.swing.JButton();
        btn_close = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        jMenuItem3 = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);

        student_table.setBackground(new java.awt.Color(204, 204, 255));
        student_table.setModel(new javax.swing.table.DefaultTableModel(
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
        student_table.setSelectionBackground(new java.awt.Color(204, 204, 204));
        student_table.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                student_tableMouseClicked(evt);
            }
        });
        student_table.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                student_tableKeyReleased(evt);
            }
        });
        jScrollPane1.setViewportView(student_table);

        txt_date.setForeground(new java.awt.Color(0, 102, 204));

        txt_time.setForeground(new java.awt.Color(0, 102, 204));

        jButton1.setFont(new java.awt.Font("Serif", 1, 12)); // NOI18N
        jButton1.setForeground(new java.awt.Color(0, 153, 204));
        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgresource/Editing-Attach-icon.png"))); // NOI18N
        jButton1.setText("Add Picture");
        jButton1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        txt_file_attach_path.setEnabled(false);
        txt_file_attach_path.setPreferredSize(new java.awt.Dimension(196, 20));

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(33, 33, 33)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(txt_file_attach_path, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txt_file_attach_path, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Comment", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 14), new java.awt.Color(102, 102, 102))); // NOI18N

        tArea_student_comment.setColumns(20);
        tArea_student_comment.setRows(5);
        tArea_student_comment.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        jScrollPane2.setViewportView(tArea_student_comment);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 104, Short.MAX_VALUE)
                .addContainerGap())
        );

        jDesktopPane1.setBackground(new java.awt.Color(204, 204, 255));

        lbl_pic.setFont(new java.awt.Font("Serif", 1, 12)); // NOI18N
        lbl_pic.setText("NO IMAGE");

        jDesktopPane1.setLayer(lbl_pic, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout jDesktopPane1Layout = new javax.swing.GroupLayout(jDesktopPane1);
        jDesktopPane1.setLayout(jDesktopPane1Layout);
        jDesktopPane1Layout.setHorizontalGroup(
            jDesktopPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jDesktopPane1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lbl_pic, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jDesktopPane1Layout.setVerticalGroup(
            jDesktopPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jDesktopPane1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lbl_pic, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        txt_date_of_birth.setDateFormatString("yyyy-MM-dd");

        jLabel8.setText("Date of Birth");

        jLabel12.setText("Middle name");

        jLabel2.setText("Last name");

        jLabel9.setText("Gender");

        jLabel16.setText("Country of birth");

        jLabel15.setText("Nationality");

        combo_nationality_list.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "[none]", "Afghan", "Albanian", "Algerian", "American", "Andorran", "Angolan", "Antiguans", "Argentinean", "Armenian", "Australian", "Austrian", "Azerbaijani", "Bahamian", "Bahraini", "Bangladeshi", "Barbadian", "Barbudans", "Batswana", "Belarusian", "Belgian", "Belizean", "Beninese", "Bhutanese", "Bolivian", "Bosnian", "Brazilian", "British", "Bruneian", "Bulgarian", "Burkinabe", "Burmese", "Burundian", "Cambodian", "Cameroonian", "Canadian", "Cape Verdean", "Central African", "Chadian", "Chilean", "Chinese", "Colombian", "Comoran", "Congolese", "Costa Rican", "Croatian", "Cuban", "Cypriot", "Czech", "Danish", "Djibouti", "Dominican", "Dutch", "East Timorese", "Ecuadorean", "Egyptian", "Emirian", "Equatorial Guinean", "Eritrean", "Estonian", "Ethiopian", "Fijian", "Filipino", "Finnish", "French", "Gabonese", "Gambian", "Georgian", "German", "Ghanaian", "Greek", "Grenadian", "Guatemalan", "Guinea-Bissauan", "Guinean", "Guyanese", "Haitian", "Herzegovinian", "Honduran", "Hungarian", "I-Kiribati", "Icelander", "Indian", "Indonesian", "Iranian", "Iraqi", "Irish", "Israeli", "Italian", "Ivorian", "Jamaican", "Japanese", "Jordanian", "Kazakhstani", "Kenyan", "Kittian and Nevisian", "Kuwaiti", "Kyrgyz", "Laotian", "Latvian", "Lebanese", "Liberian", "Libyan", "Liechtensteiner", "Lithuanian", "Luxembourger", "Macedonian", "Malagasy", "Malawian", "Malaysian", "Maldivan", "Malian", "Maltese", "Marshallese", "Mauritanian", "Mauritian", "Mexican", "Micronesian", "Moldovan", "Monacan", "Mongolian", "Moroccan", "Mosotho", "Motswana", "Mozambican", "Namibian", "Nauruan", "Nepalese", "New Zealander", "Nicaraguan", "Nigerian", "Nigerien", "North Korean", "Northern Irish", "Norwegian", "Omani", "Pakistani", "Palauan", "Panamanian", "Papua New Guinean", "Paraguayan", "Peruvian", "Polish", "Portuguese", "Qatari", "Romanian", "Russian", "Rwandan", "Saint Lucian", "Salvadoran", "Samoan", "San Marinese", "Sao Tomean", "Saudi", "Scottish", "Senegalese", "Serbian", "Seychellois", "Sierra Leonean", "Singaporean", "Slovakian", "Slovenian", "Solomon Islander", "Somali", "South African", "South Korean", "Spanish", "Sri Lankan", "Sudanese", "Surinamer", "Swazi", "Swedish", "Swiss", "Syrian", "Taiwanese", "Tajik", "Tanzanian", "Thai", "Togolese", "Tongan", "Trinidadian or Tobagonian", "Tunisian", "Turkish", "Tuvaluan", "Ugandan", "Ukrainian", "Uruguayan", "Uzbekistani", "Venezuelan", "Vietnamese", "Welsh", "Yemenite", "Zambian", "Zimbabwean" }));

        jLabel1.setText("First name");

        combo_country_of_birth.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "[none]", "Afghanistan", "Albania", "Algeria", "American Samoa", "Andorra", "Angola", "Anguilla", "Antigua and Barbuda", "Argentina", "Armenia", "Aruba", "Australia", "Austria", "Azerbaijan", "Bahamas", "Bahrain", "Bangladesh", "Barbados", "Belarus", "Belgium", "Belize", "Benin", "Bermuda", "Bhutan", "Bolivia", "Bosnia and Herzegovina", "Botswana", "Brazil", "British Virgin Islands", "Brunei", "Bulgaria", "Burkina Faso", "Burma", "Burundi", "Cambodia", "Cameroon", "Canada", "Cape Verde", "Caribbean Netherlands", "Cayman Islands", "Central African Republic", "Chad", "Chile", "China", "Christmas Island", "Cocos Islands", "Colombia", "Comoros", "Congo", "Cook Islands", "Costa Rica", "Croatia", "Cuba", "Curaçao", "Cyprus", "Czech Republic", "Democratic Republic of the Congo", "Denmark", "Djibouti", "Dominica", "Dominican Republic", "Ecuador", "Egypt", "El Salvador", "Equatorial Guinea", "Eritrea", "Estonia", "Ethiopia", "Falkland Islands", "Faroe Islands", "Federated States of Micronesia", "Fiji", "Finland", "France", "French Guiana", "French Polynesia", "Gabon", "Gambia", "Georgia", "Germany", "Ghana", "Gibraltar", "Greece", "Greenland", "Grenada", "Guadeloupe", "Guam", "Guatemala", "Guernsey", "Guinea", "Guinea-Bissau", "Guyana", "Haiti", "Honduras", "Hong Kong", "Hungary", "Iceland", "India", "Indonesia", "Iran", "Iraq", "Ireland", "Isle of Man", "Israel", "Italy", "Ivory Coast", "Jamaica", "Japan", "Jersey", "Jordan", "Kazakhstan", "Kenya", "Kiribati", "Kosovo", "Kuwait", "Kyrgyzstan", "Laos", "Latvia", "Lebanon", "Lesotho", "Liberia", "Libya", "Liechtenstein", "Lithuania", "Luxembourg", "Macau", "Macedonia", "Madagascar", "Malawi", "Malaysia", "Maldives", "Mali", "Malta", "Marshall Islands", "Martinique", "Mauritania", "Mauritius", "Mayotte", "Mexico", "Moldova", "Monaco", "Mongolia", "Montenegro", "Montserrat", "Morocco", "Mozambique", "Namibia", "Nauru", "Nepal", "Netherlands", "New Caledonia", "New Zealand", "Nicaragua", "Niger", "Nigeria", "Niue", "Norfolk Island", "North Korea", "Northern Mariana Islands", "Norway", "Oman", "Pakistan", "Palau", "Palestine", "Panama", "Papua New Guinea", "Paraguay", "Peru", "Philippines", "Pitcairn Islands", "Poland", "Portugal", "Puerto Rico", "Qatar", "Réunion", "Romania", "Russia", "Rwanda", "Saint Barthélemy", "Saint Helena, Ascension and Tristan da Cunha", "Saint Kitts and Nevis", "Saint Lucia", "Saint Martin", "Saint Pierre and Miquelon", "Saint Vincent and the Grenadines", "Samoa", "San Marino", "São Tomé and Príncipe", "Saudi Arabia", "Senegal", "Serbia", "Seychelles", "Sierra Leone", "Singapore", "Sint Maarten", "Slovakia", "Slovenia", "Solomon Islands", "Somalia", "South Africa", "South Korea", "South Sudan", "Spain", "Sri Lanka", "Sudan", "Suriname", "Svalbard and Jan Mayen", "Swaziland", "Sweden", "Switzerland", "Syria", "Taiwan", "Tajikistan", "Tanzania", "Thailand", "Timor-Leste", "Togo", "Tokelau", "Tonga", "Trinidad and Tobago", "Tunisia", "Turkey", "Turkmenistan", "Turks and Caicos Islands", "Tuvalu", "Uganda", "Ukraine", "United Arab Emirates", "United Kingdom", "United States", "United States Virgin Islands", "Uruguay", "Uzbekistan", "Vanuatu", "Vatican City", "Venezuela", "Vietnam", "Wallis and Futuna", "Western Sahara", "Yemen", "Zambia", "Zimbabwe", "Åland Islands" }));

        jLabel13.setText("Username*");

        jLabel14.setText("Password*");

        txt_username.setPreferredSize(new java.awt.Dimension(120, 20));

        combo_gender.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Female", "Male" }));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel14)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txt_password, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel8)
                                    .addComponent(jLabel9))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(txt_date_of_birth, javax.swing.GroupLayout.DEFAULT_SIZE, 121, Short.MAX_VALUE)
                                        .addComponent(combo_gender, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                    .addComponent(combo_nationality_list, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(jLabel15)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2)
                            .addComponent(jLabel12)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel16)
                                    .addComponent(jLabel13))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(combo_country_of_birth, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txt_username, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(txt_mn, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(txt_ln, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                        .addGap(80, 80, 80)
                                        .addComponent(txt_fn, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txt_fn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(txt_mn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(6, 6, 6)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txt_ln, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txt_date_of_birth, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addComponent(jLabel9))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(combo_gender, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(25, 25, 25)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15)
                    .addComponent(combo_nationality_list, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel16)
                    .addComponent(combo_country_of_birth, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(txt_username, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(txt_password, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(15, Short.MAX_VALUE))
        );

        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Search", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 14), new java.awt.Color(102, 102, 102))); // NOI18N

        txt_searchCategory.setBackground(new java.awt.Color(204, 204, 255));
        txt_searchCategory.setPreferredSize(new java.awt.Dimension(120, 20));
        txt_searchCategory.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_searchCategoryKeyReleased(evt);
            }
        });

        combo_searchCategory.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "All Category", "CSE", "CNS", "ISVMA", "MIR", "ISA", " " }));
        combo_searchCategory.setMinimumSize(new java.awt.Dimension(120, 20));
        combo_searchCategory.setPreferredSize(new java.awt.Dimension(120, 20));
        combo_searchCategory.addPopupMenuListener(new javax.swing.event.PopupMenuListener() {
            public void popupMenuCanceled(javax.swing.event.PopupMenuEvent evt) {
            }
            public void popupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {
                combo_searchCategoryPopupMenuWillBecomeInvisible(evt);
            }
            public void popupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt) {
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(combo_searchCategory, 0, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_searchCategory, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(20, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addComponent(txt_searchCategory, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(combo_searchCategory, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 21, Short.MAX_VALUE))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        combo_faculty.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "All", "CSE", "CNS", "ISVMA", "MIR", "ISA" }));

        jLabel6.setText("Year of studies");

        jLabel3.setText("Admission number*");

        txt_admission_number.setEditable(false);
        txt_admission_number.setPreferredSize(new java.awt.Dimension(120, 20));

        txt_email.setPreferredSize(new java.awt.Dimension(120, 20));
        txt_email.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_emailActionPerformed(evt);
            }
        });
        txt_email.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_emailKeyReleased(evt);
            }
        });

        jLabel7.setText("Admission Date");

        jLabel10.setText("Email Address");

        txt_student_id.setEditable(false);

        jLabel4.setText("Faculty");

        jLabel5.setText("Student ID*");

        txt_admission_date.setDateFormatString("yyyy-MM-dd");
        txt_admission_date.setEnabled(false);

        combo_year_of_studies.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1", "2", "3", "4" }));
        combo_year_of_studies.setPreferredSize(new java.awt.Dimension(120, 20));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5)
                            .addComponent(jLabel7))
                        .addGap(38, 38, 38)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txt_student_id, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(txt_admission_date, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(1, 1, 1))))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(jLabel4)
                            .addComponent(jLabel6)
                            .addComponent(jLabel10))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txt_email, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(combo_faculty, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txt_admission_number, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(combo_year_of_studies, 0, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(txt_student_id, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7)
                    .addComponent(txt_admission_date, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txt_admission_number, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel6))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(combo_faculty, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(combo_year_of_studies, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(16, 16, 16)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(txt_email, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Action", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 14), new java.awt.Color(102, 102, 102))); // NOI18N

        btn_clear.setFont(new java.awt.Font("Serif", 1, 12)); // NOI18N
        btn_clear.setForeground(new java.awt.Color(0, 153, 204));
        btn_clear.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgresource/Clear-icon.png"))); // NOI18N
        btn_clear.setText("Clear");
        btn_clear.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_clear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_clearActionPerformed(evt);
            }
        });

        btn_update.setFont(new java.awt.Font("Serif", 1, 12)); // NOI18N
        btn_update.setForeground(new java.awt.Color(0, 153, 204));
        btn_update.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgresource/Actions-document-edit-icon.png"))); // NOI18N
        btn_update.setText("Update");
        btn_update.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_update.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_updateActionPerformed(evt);
            }
        });

        btn_print.setFont(new java.awt.Font("Serif", 1, 12)); // NOI18N
        btn_print.setForeground(new java.awt.Color(0, 153, 204));
        btn_print.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgresource/printer-icon.png"))); // NOI18N
        btn_print.setText("Print");
        btn_print.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_print.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_printActionPerformed(evt);
            }
        });

        btn_delete.setFont(new java.awt.Font("Serif", 1, 12)); // NOI18N
        btn_delete.setForeground(new java.awt.Color(0, 153, 204));
        btn_delete.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgresource/Button-Delete-icon.png"))); // NOI18N
        btn_delete.setText("Delete");
        btn_delete.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_delete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_deleteActionPerformed(evt);
            }
        });

        btn_chart.setFont(new java.awt.Font("Serif", 1, 12)); // NOI18N
        btn_chart.setForeground(new java.awt.Color(0, 153, 204));
        btn_chart.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgresource/chart-icon.png"))); // NOI18N
        btn_chart.setText("Chart");
        btn_chart.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_chart.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_chartActionPerformed(evt);
            }
        });

        btn_ireport.setFont(new java.awt.Font("Serif", 1, 12)); // NOI18N
        btn_ireport.setForeground(new java.awt.Color(0, 153, 204));
        btn_ireport.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgresource/ireport.png"))); // NOI18N
        btn_ireport.setText("IReport");
        btn_ireport.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_ireportActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btn_clear, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btn_update, javax.swing.GroupLayout.DEFAULT_SIZE, 95, Short.MAX_VALUE)
                    .addComponent(btn_print, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btn_delete, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btn_chart, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btn_ireport, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btn_update)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btn_print)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btn_delete)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btn_clear)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btn_chart)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btn_ireport)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel16.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        combo_full_part_time.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Regular Student", "Part-time Student" }));
        combo_full_part_time.setPreferredSize(new java.awt.Dimension(120, 20));

        combo_status.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Active", "Graduate", "Dismissed" }));
        combo_status.setPreferredSize(new java.awt.Dimension(120, 20));

        txt_phone_number.setPreferredSize(new java.awt.Dimension(120, 20));
        txt_phone_number.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_phone_numberKeyTyped(evt);
            }
        });

        jLabel35.setText("Status");

        jLabel30.setText("Phone number");

        jLabel34.setText("Full/Part-time");

        jLabel36.setText("Address");

        txt_address.setPreferredSize(new java.awt.Dimension(120, 20));

        javax.swing.GroupLayout jPanel16Layout = new javax.swing.GroupLayout(jPanel16);
        jPanel16.setLayout(jPanel16Layout);
        jPanel16Layout.setHorizontalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel16Layout.createSequentialGroup()
                        .addComponent(jLabel30)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txt_phone_number, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel16Layout.createSequentialGroup()
                        .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel34)
                            .addComponent(jLabel35)
                            .addComponent(jLabel36))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(combo_full_part_time, 0, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(combo_status, 0, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txt_address, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        jPanel16Layout.setVerticalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel30)
                    .addComponent(txt_phone_number, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel36)
                    .addComponent(txt_address, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel34, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(combo_full_part_time, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel35)
                    .addComponent(combo_status, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(16, 16, 16))
        );

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(28, 28, 28)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jDesktopPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(33, 33, 33))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel7Layout.createSequentialGroup()
                                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel7Layout.createSequentialGroup()
                                .addComponent(jDesktopPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(11, 11, 11)
                                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jPanel16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, 903, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(13, Short.MAX_VALUE))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(25, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("General", jPanel8);

        jPanel13.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        jButton2.setFont(new java.awt.Font("Serif", 1, 12)); // NOI18N
        jButton2.setForeground(new java.awt.Color(0, 153, 204));
        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgresource/Editing-Attach-icon.png"))); // NOI18N
        jButton2.setText("Attach Image");
        jButton2.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        txt_file_attach_path1.setEnabled(false);
        txt_file_attach_path1.setPreferredSize(new java.awt.Dimension(166, 20));

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txt_file_attach_path1, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton2))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txt_file_attach_path1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel14.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Comment", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 14), new java.awt.Color(102, 102, 102))); // NOI18N

        tArea_student_comment1.setColumns(20);
        tArea_student_comment1.setRows(5);
        jScrollPane3.setViewportView(tArea_student_comment1);

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 226, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
        );

        jDesktopPane2.setBackground(new java.awt.Color(204, 204, 255));

        lbl_pic1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lbl_pic1.setText(" NO Image");
        lbl_pic1.setToolTipText("Add photo");

        jDesktopPane2.setLayer(lbl_pic1, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout jDesktopPane2Layout = new javax.swing.GroupLayout(jDesktopPane2);
        jDesktopPane2.setLayout(jDesktopPane2Layout);
        jDesktopPane2Layout.setHorizontalGroup(
            jDesktopPane2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jDesktopPane2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lbl_pic1, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jDesktopPane2Layout.setVerticalGroup(
            jDesktopPane2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jDesktopPane2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lbl_pic1, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel15.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        txt_date_of_birth1.setDateFormatString("yyyy-MM-dd");

        jLabel11.setText("Date of Birth");

        jLabel17.setText("Middle name");

        jLabel18.setText("Last name*");

        jLabel19.setText("Gender");

        jLabel20.setText("Country of birth");

        jLabel21.setText("Nationality");

        combo_nationality_list1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Choose Nationality", "Afghan", "Albanian", "Algerian", "American", "Andorran", "Angolan", "Antiguans", "Argentinean", "Armenian", "Australian", "Austrian", "Azerbaijani", "Bahamian", "Bahraini", "Bangladeshi", "Barbadian", "Barbudans", "Batswana", "Belarusian", "Belgian", "Belizean", "Beninese", "Bhutanese", "Bolivian", "Bosnian", "Brazilian", "British", "Bruneian", "Bulgarian", "Burkinabe", "Burmese", "Burundian", "Cambodian", "Cameroonian", "Canadian", "Cape Verdean", "Central African", "Chadian", "Chilean", "Chinese", "Colombian", "Comoran", "Congolese", "Costa Rican", "Croatian", "Cuban", "Cypriot", "Czech", "Danish", "Djibouti", "Dominican", "Dutch", "East Timorese", "Ecuadorean", "Egyptian", "Emirian", "Equatorial Guinean", "Eritrean", "Estonian", "Ethiopian", "Fijian", "Filipino", "Finnish", "French", "Gabonese", "Gambian", "Georgian", "German", "Ghanaian", "Greek", "Grenadian", "Guatemalan", "Guinea-Bissauan", "Guinean", "Guyanese", "Haitian", "Herzegovinian", "Honduran", "Hungarian", "I-Kiribati", "Icelander", "Indian", "Indonesian", "Iranian", "Iraqi", "Irish", "Israeli", "Italian", "Ivorian", "Jamaican", "Japanese", "Jordanian", "Kazakhstani", "Kenyan", "Kittian and Nevisian", "Kuwaiti", "Kyrgyz", "Laotian", "Latvian", "Lebanese", "Liberian", "Libyan", "Liechtensteiner", "Lithuanian", "Luxembourger", "Macedonian", "Malagasy", "Malawian", "Malaysian", "Maldivan", "Malian", "Maltese", "Marshallese", "Mauritanian", "Mauritian", "Mexican", "Micronesian", "Moldovan", "Monacan", "Mongolian", "Moroccan", "Mosotho", "Motswana", "Mozambican", "Namibian", "Nauruan", "Nepalese", "New Zealander", "Nicaraguan", "Nigerian", "Nigerien", "North Korean", "Northern Irish", "Norwegian", "Omani", "Pakistani", "Palauan", "Panamanian", "Papua New Guinean", "Paraguayan", "Peruvian", "Polish", "Portuguese", "Qatari", "Romanian", "Russian", "Rwandan", "Saint Lucian", "Salvadoran", "Samoan", "San Marinese", "Sao Tomean", "Saudi", "Scottish", "Senegalese", "Serbian", "Seychellois", "Sierra Leonean", "Singaporean", "Slovakian", "Slovenian", "Solomon Islander", "Somali", "South African", "South Korean", "Spanish", "Sri Lankan", "Sudanese", "Surinamer", "Swazi", "Swedish", "Swiss", "Syrian", "Taiwanese", "Tajik", "Tanzanian", "Thai", "Togolese", "Tongan", "Trinidadian or Tobagonian", "Tunisian", "Turkish", "Tuvaluan", "Ugandan", "Ukrainian", "Uruguayan", "Uzbekistani", "Venezuelan", "Vietnamese", "Welsh", "Yemenite", "Zambian", "Zimbabwean" }));

        jLabel22.setText("First name*");

        combo_country_of_birth1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Choose Country of Birth", "Afghanistan", "Albania", "Algeria", "American Samoa", "Andorra", "Angola", "Anguilla", "Antigua and Barbuda", "Argentina", "Armenia", "Aruba", "Australia", "Austria", "Azerbaijan", "Bahamas", "Bahrain", "Bangladesh", "Barbados", "Belarus", "Belgium", "Belize", "Benin", "Bermuda", "Bhutan", "Bolivia", "Bosnia and Herzegovina", "Botswana", "Brazil", "British Virgin Islands", "Brunei", "Bulgaria", "Burkina Faso", "Burma", "Burundi", "Cambodia", "Cameroon", "Canada", "Cape Verde", "Caribbean Netherlands", "Cayman Islands", "Central African Republic", "Chad", "Chile", "China", "Christmas Island", "Cocos Islands", "Colombia", "Comoros", "Congo", "Cook Islands", "Costa Rica", "Croatia", "Cuba", "Curaçao", "Cyprus", "Czech Republic", "Democratic Republic of the Congo", "Denmark", "Djibouti", "Dominica", "Dominican Republic", "Ecuador", "Egypt", "El Salvador", "Equatorial Guinea", "Eritrea", "Estonia", "Ethiopia", "Falkland Islands", "Faroe Islands", "Federated States of Micronesia", "Fiji", "Finland", "France", "French Guiana", "French Polynesia", "Gabon", "Gambia", "Georgia", "Germany", "Ghana", "Gibraltar", "Greece", "Greenland", "Grenada", "Guadeloupe", "Guam", "Guatemala", "Guernsey", "Guinea", "Guinea-Bissau", "Guyana", "Haiti", "Honduras", "Hong Kong", "Hungary", "Iceland", "India", "Indonesia", "Iran", "Iraq", "Ireland", "Isle of Man", "Israel", "Italy", "Ivory Coast", "Jamaica", "Japan", "Jersey", "Jordan", "Kazakhstan", "Kenya", "Kiribati", "Kosovo", "Kuwait", "Kyrgyzstan", "Laos", "Latvia", "Lebanon", "Lesotho", "Liberia", "Libya", "Liechtenstein", "Lithuania", "Luxembourg", "Macau", "Macedonia", "Madagascar", "Malawi", "Malaysia", "Maldives", "Mali", "Malta", "Marshall Islands", "Martinique", "Mauritania", "Mauritius", "Mayotte", "Mexico", "Moldova", "Monaco", "Mongolia", "Montenegro", "Montserrat", "Morocco", "Mozambique", "Namibia", "Nauru", "Nepal", "Netherlands", "New Caledonia", "New Zealand", "Nicaragua", "Niger", "Nigeria", "Niue", "Norfolk Island", "North Korea", "Northern Mariana Islands", "Norway", "Oman", "Pakistan", "Palau", "Palestine", "Panama", "Papua New Guinea", "Paraguay", "Peru", "Philippines", "Pitcairn Islands", "Poland", "Portugal", "Puerto Rico", "Qatar", "Réunion", "Romania", "Russia", "Rwanda", "Saint Barthélemy", "Saint Helena, Ascension and Tristan da Cunha", "Saint Kitts and Nevis", "Saint Lucia", "Saint Martin", "Saint Pierre and Miquelon", "Saint Vincent and the Grenadines", "Samoa", "San Marino", "São Tomé and Príncipe", "Saudi Arabia", "Senegal", "Serbia", "Seychelles", "Sierra Leone", "Singapore", "Sint Maarten", "Slovakia", "Slovenia", "Solomon Islands", "Somalia", "South Africa", "South Korea", "South Sudan", "Spain", "Sri Lanka", "Sudan", "Suriname", "Svalbard and Jan Mayen", "Swaziland", "Sweden", "Switzerland", "Syria", "Taiwan", "Tajikistan", "Tanzania", "Thailand", "Timor-Leste", "Togo", "Tokelau", "Tonga", "Trinidad and Tobago", "Tunisia", "Turkey", "Turkmenistan", "Turks and Caicos Islands", "Tuvalu", "Uganda", "Ukraine", "United Arab Emirates", "United Kingdom", "United States", "United States Virgin Islands", "Uruguay", "Uzbekistan", "Vanuatu", "Vatican City", "Venezuela", "Vietnam", "Wallis and Futuna", "Western Sahara", "Yemen", "Zambia", "Zimbabwe", "Åland Islands" }));

        jLabel23.setText("Username");

        jLabel24.setText("Password");

        txt_username1.setPreferredSize(new java.awt.Dimension(120, 20));

        jLabel33.setText("Student status");

        combo_student_status1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Regular", "Part time" }));

        buttonGroup2.add(rbtn_male);
        rbtn_male.setText("Male");
        rbtn_male.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        rbtn_male.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbtn_maleActionPerformed(evt);
            }
        });

        buttonGroup2.add(rbtn_female);
        rbtn_female.setText("Female");
        rbtn_female.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        rbtn_female.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbtn_femaleActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel15Layout = new javax.swing.GroupLayout(jPanel15);
        jPanel15.setLayout(jPanel15Layout);
        jPanel15Layout.setHorizontalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel15Layout.createSequentialGroup()
                        .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel15Layout.createSequentialGroup()
                                .addComponent(jLabel21)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(combo_nationality_list1, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel15Layout.createSequentialGroup()
                                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel18)
                                    .addComponent(jLabel17)
                                    .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel15Layout.createSequentialGroup()
                                            .addComponent(jLabel19)
                                            .addGap(44, 44, 44)
                                            .addComponent(rbtn_male))
                                        .addGroup(jPanel15Layout.createSequentialGroup()
                                            .addComponent(jLabel11)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 18, Short.MAX_VALUE)
                                            .addComponent(txt_date_of_birth1, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(txt_ln1, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel15Layout.createSequentialGroup()
                                                .addComponent(jLabel22, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(12, 12, 12)
                                                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                    .addComponent(txt_fn1, javax.swing.GroupLayout.DEFAULT_SIZE, 120, Short.MAX_VALUE)
                                                    .addComponent(txt_mn1))))))
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(jPanel15Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(rbtn_female)
                                    .addComponent(combo_country_of_birth1, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel15Layout.createSequentialGroup()
                        .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel15Layout.createSequentialGroup()
                                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel23)
                                    .addComponent(jLabel24))
                                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel15Layout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(txt_password1, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel15Layout.createSequentialGroup()
                                        .addGap(33, 33, 33)
                                        .addComponent(txt_username1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel15Layout.createSequentialGroup()
                                .addComponent(jLabel33)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(combo_student_status1, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(15, 15, 15))))
        );
        jPanel15Layout.setVerticalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel22)
                    .addComponent(txt_fn1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel17)
                    .addComponent(txt_mn1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(6, 6, 6)
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel18)
                    .addComponent(txt_ln1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txt_date_of_birth1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11))
                .addGap(8, 8, 8)
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel19)
                    .addComponent(rbtn_male)
                    .addComponent(rbtn_female))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel21)
                    .addComponent(combo_nationality_list1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(9, 9, 9)
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel20)
                    .addComponent(combo_country_of_birth1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel33)
                    .addComponent(combo_student_status1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(14, 14, 14)
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_username1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel23))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel24)
                    .addComponent(txt_password1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(17, Short.MAX_VALUE))
        );

        jPanel17.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        combo_faculty1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "CSE", "CNS", "ISVMA", "MIR", "ISA" }));

        jLabel25.setText("Year of studies");

        txt_email1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_email1KeyReleased(evt);
            }
        });

        jLabel27.setText("Admission Date");

        jLabel28.setText("Email Address");

        jLabel29.setText("Faculty");

        txt_admission_date1.setDateFormatString("yyyy-MM-dd");

        jLabel31.setText("Phone number");

        txt_phone_number1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_phone_number1KeyTyped(evt);
            }
        });

        jLabel32.setText("Address");

        combo_year_of_studies1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1", "2", "3", "4" }));
        combo_year_of_studies1.setPreferredSize(new java.awt.Dimension(120, 20));

        jLabel26.setText("Full/Part time");

        combo_regular_part1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Regular Student", "Part-time Student", " " }));
        combo_regular_part1.setPreferredSize(new java.awt.Dimension(120, 20));

        javax.swing.GroupLayout jPanel17Layout = new javax.swing.GroupLayout(jPanel17);
        jPanel17.setLayout(jPanel17Layout);
        jPanel17Layout.setHorizontalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel17Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel17Layout.createSequentialGroup()
                        .addComponent(jLabel27)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txt_admission_date1, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel17Layout.createSequentialGroup()
                        .addComponent(jLabel29)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(combo_faculty1, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel17Layout.createSequentialGroup()
                        .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel25)
                            .addComponent(jLabel28))
                        .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel17Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(txt_email1, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel17Layout.createSequentialGroup()
                                .addGap(39, 39, 39)
                                .addComponent(combo_year_of_studies1, 0, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel17Layout.createSequentialGroup()
                        .addComponent(jLabel31)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txt_phone_number1, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel17Layout.createSequentialGroup()
                        .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel32)
                            .addComponent(jLabel26))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txt_address1, javax.swing.GroupLayout.DEFAULT_SIZE, 121, Short.MAX_VALUE)
                            .addComponent(combo_regular_part1, 0, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap())
        );
        jPanel17Layout.setVerticalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel17Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel27)
                    .addComponent(txt_admission_date1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel17Layout.createSequentialGroup()
                        .addComponent(jLabel29)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel25))
                    .addGroup(jPanel17Layout.createSequentialGroup()
                        .addComponent(combo_faculty1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(combo_year_of_studies1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txt_email1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel28))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel31)
                    .addComponent(txt_phone_number1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel32)
                    .addComponent(txt_address1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel26)
                    .addComponent(combo_regular_part1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        btn_update1.setFont(new java.awt.Font("Serif", 1, 12)); // NOI18N
        btn_update1.setForeground(new java.awt.Color(0, 153, 204));
        btn_update1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgresource/Button-Add-icon.png"))); // NOI18N
        btn_update1.setText("Add New Student");
        btn_update1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_update1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_update1ActionPerformed(evt);
            }
        });

        btn_clear1.setFont(new java.awt.Font("Serif", 1, 12)); // NOI18N
        btn_clear1.setForeground(new java.awt.Color(0, 153, 204));
        btn_clear1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgresource/Clear-icon.png"))); // NOI18N
        btn_clear1.setText("Clear");
        btn_clear1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_clear1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_clear1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addComponent(jPanel17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jDesktopPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btn_update1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btn_clear1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addComponent(jPanel14, javax.swing.GroupLayout.PREFERRED_SIZE, 255, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(20, Short.MAX_VALUE))
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addComponent(jPanel17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jPanel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel12Layout.createSequentialGroup()
                                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(jPanel12Layout.createSequentialGroup()
                                        .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(btn_update1))
                                    .addComponent(jDesktopPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btn_clear1)))
                        .addGap(0, 48, Short.MAX_VALUE)))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(37, Short.MAX_VALUE))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(17, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Add New Student", jPanel9);

        jLabel45.setText("To:");

        jLabel46.setText("Bcc");

        jLabel47.setText("Subject");

        txt_to.setPreferredSize(new java.awt.Dimension(315, 20));

        txt_bcc.setPreferredSize(new java.awt.Dimension(315, 20));

        txt_subject.setPreferredSize(new java.awt.Dimension(315, 20));

        txArea_msg.setColumns(20);
        txArea_msg.setRows(5);
        jScrollPane5.setViewportView(txArea_msg);

        btn_sendMsg.setFont(new java.awt.Font("Serif", 1, 12)); // NOI18N
        btn_sendMsg.setForeground(new java.awt.Color(0, 153, 204));
        btn_sendMsg.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgresource/email-send-icon.png"))); // NOI18N
        btn_sendMsg.setText("Send Message");
        btn_sendMsg.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_sendMsgActionPerformed(evt);
            }
        });

        btn_msgattach.setFont(new java.awt.Font("Serif", 1, 12)); // NOI18N
        btn_msgattach.setForeground(new java.awt.Color(0, 153, 204));
        btn_msgattach.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgresource/Editing-Attach-icon.png"))); // NOI18N
        btn_msgattach.setText("Add Attachment");
        btn_msgattach.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_msgattachActionPerformed(evt);
            }
        });

        txt_attahedmsgpath.setPreferredSize(new java.awt.Dimension(260, 20));

        txt_attachment_name.setPreferredSize(new java.awt.Dimension(120, 20));
        txt_attachment_name.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_attachment_nameActionPerformed(evt);
            }
        });

        jLabel50.setText("Give Attachment Name");

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btn_sendMsg)
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jScrollPane5)
                            .addGroup(jPanel11Layout.createSequentialGroup()
                                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel45)
                                    .addComponent(jLabel46)
                                    .addComponent(jLabel47))
                                .addGap(21, 21, 21)
                                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txt_subject, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txt_to, javax.swing.GroupLayout.PREFERRED_SIZE, 315, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txt_bcc, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(58, 58, 58)
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel11Layout.createSequentialGroup()
                                .addComponent(jLabel50)
                                .addGap(31, 31, 31)
                                .addComponent(txt_attachment_name, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(txt_attahedmsgpath, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btn_msgattach))))
                .addContainerGap(206, Short.MAX_VALUE))
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel45)
                    .addComponent(txt_to, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_attachment_name, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel50))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel46)
                    .addComponent(txt_bcc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel47)
                            .addComponent(txt_subject, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addGap(5, 5, 5)
                        .addComponent(btn_msgattach)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txt_attahedmsgpath, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 211, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btn_sendMsg)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Send Email", jPanel11);

        student_grade_table.setBackground(new java.awt.Color(204, 204, 255));
        student_grade_table.setModel(new javax.swing.table.DefaultTableModel(
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
        student_grade_table.setSelectionBackground(new java.awt.Color(204, 204, 204));
        student_grade_table.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                student_grade_tableMouseClicked(evt);
            }
        });
        student_grade_table.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                student_grade_tableKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                student_grade_tableKeyTyped(evt);
            }
        });
        jScrollPane4.setViewportView(student_grade_table);

        jPanel18.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Enter new marks", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 14), new java.awt.Color(102, 102, 102))); // NOI18N

        jLabel37.setText("Total Score");

        txt_student_mark.setPreferredSize(new java.awt.Dimension(130, 20));
        txt_student_mark.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_student_markKeyTyped(evt);
            }
        });

        txt_student_fname_add_score.setPreferredSize(new java.awt.Dimension(130, 20));

        jLabel42.setText("First Name");

        btn_add_result.setFont(new java.awt.Font("Serif", 1, 12)); // NOI18N
        btn_add_result.setForeground(new java.awt.Color(0, 153, 204));
        btn_add_result.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgresource/Button-Add-icon.png"))); // NOI18N
        btn_add_result.setText("Add Result");
        btn_add_result.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_add_result.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_add_resultActionPerformed(evt);
            }
        });

        jLabel43.setText("Last Name");

        jLabel44.setText("Student ID");

        txt_student_lname_add_score.setPreferredSize(new java.awt.Dimension(130, 20));

        txt_student_studentID_add_score.setEditable(false);
        txt_student_studentID_add_score.setPreferredSize(new java.awt.Dimension(130, 20));
        txt_student_studentID_add_score.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_student_studentID_add_scoreKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_student_studentID_add_scoreKeyTyped(evt);
            }
        });

        jLabel38.setText("Subject");

        combo_subject_for_marks.setPreferredSize(new java.awt.Dimension(130, 20));

        javax.swing.GroupLayout jPanel18Layout = new javax.swing.GroupLayout(jPanel18);
        jPanel18.setLayout(jPanel18Layout);
        jPanel18Layout.setHorizontalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel18Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel18Layout.createSequentialGroup()
                        .addComponent(jLabel44)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txt_student_studentID_add_score, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel18Layout.createSequentialGroup()
                        .addComponent(jLabel38)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(combo_subject_for_marks, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel18Layout.createSequentialGroup()
                        .addComponent(jLabel37, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel18Layout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addComponent(btn_add_result, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(txt_student_mark, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel18Layout.createSequentialGroup()
                        .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel18Layout.createSequentialGroup()
                                .addComponent(jLabel42)
                                .addGap(25, 25, 25))
                            .addGroup(jPanel18Layout.createSequentialGroup()
                                .addComponent(jLabel43)
                                .addGap(26, 26, 26)))
                        .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txt_student_fname_add_score, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txt_student_lname_add_score, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap())
        );
        jPanel18Layout.setVerticalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel18Layout.createSequentialGroup()
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel42)
                    .addComponent(txt_student_fname_add_score, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txt_student_lname_add_score, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel43))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel44)
                    .addComponent(txt_student_studentID_add_score, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(4, 4, 4)
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel38)
                    .addComponent(combo_subject_for_marks, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel37, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_student_mark, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btn_add_result))
        );

        jPanel19.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Update score", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 14), new java.awt.Color(102, 102, 102))); // NOI18N

        jLabel39.setText("Score");

        txt_grade_score.setPreferredSize(new java.awt.Dimension(50, 20));
        txt_grade_score.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_grade_scoreKeyTyped(evt);
            }
        });

        jLabel40.setText("Student Name");

        jLabel41.setText("Subject");

        studentAddResultNames.setEditable(false);
        studentAddResultNames.setPreferredSize(new java.awt.Dimension(145, 20));

        txt_subject_update.setEditable(false);
        txt_subject_update.setPreferredSize(new java.awt.Dimension(180, 20));

        update_score.setFont(new java.awt.Font("Serif", 1, 12)); // NOI18N
        update_score.setForeground(new java.awt.Color(0, 153, 204));
        update_score.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgresource/Actions-document-edit-icon.png"))); // NOI18N
        update_score.setText("Edit Score");
        update_score.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        update_score.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                update_scoreActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel19Layout = new javax.swing.GroupLayout(jPanel19);
        jPanel19.setLayout(jPanel19Layout);
        jPanel19Layout.setHorizontalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel19Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel40)
                    .addComponent(jLabel39)
                    .addComponent(jLabel41))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txt_grade_score, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txt_subject_update, javax.swing.GroupLayout.PREFERRED_SIZE, 1, Short.MAX_VALUE)
                    .addComponent(studentAddResultNames, javax.swing.GroupLayout.PREFERRED_SIZE, 1, Short.MAX_VALUE))
                .addGap(12, 12, 12))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel19Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(update_score)
                .addContainerGap())
        );
        jPanel19Layout.setVerticalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel19Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel39)
                    .addComponent(txt_grade_score, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel40)
                    .addComponent(studentAddResultNames, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel41)
                    .addComponent(txt_subject_update, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(update_score))
        );

        jPanel20.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Select student to view results", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 10), new java.awt.Color(102, 102, 102))); // NOI18N
        jPanel20.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel20MouseClicked(evt);
            }
        });

        student_name_result.addPopupMenuListener(new javax.swing.event.PopupMenuListener() {
            public void popupMenuCanceled(javax.swing.event.PopupMenuEvent evt) {
            }
            public void popupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {
                student_name_resultPopupMenuWillBecomeInvisible(evt);
            }
            public void popupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt) {
            }
        });

        javax.swing.GroupLayout jPanel20Layout = new javax.swing.GroupLayout(jPanel20);
        jPanel20.setLayout(jPanel20Layout);
        jPanel20Layout.setHorizontalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel20Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(student_name_result, 0, 162, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel20Layout.setVerticalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(student_name_result, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        btn_print_grade.setFont(new java.awt.Font("Serif", 1, 12)); // NOI18N
        btn_print_grade.setForeground(new java.awt.Color(0, 153, 204));
        btn_print_grade.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgresource/printer-icon.png"))); // NOI18N
        btn_print_grade.setText("Print Result");
        btn_print_grade.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_print_grade.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_print_gradeActionPerformed(evt);
            }
        });

        btn_reportGrade.setFont(new java.awt.Font("Serif", 1, 12)); // NOI18N
        btn_reportGrade.setForeground(new java.awt.Color(0, 153, 204));
        btn_reportGrade.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgresource/Reports-icon.png"))); // NOI18N
        btn_reportGrade.setText("Transcript");
        btn_reportGrade.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_reportGrade.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_reportGradeActionPerformed(evt);
            }
        });

        btn_iReport_grade.setFont(new java.awt.Font("Serif", 1, 12)); // NOI18N
        btn_iReport_grade.setForeground(new java.awt.Color(0, 153, 204));
        btn_iReport_grade.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgresource/ireport.png"))); // NOI18N
        btn_iReport_grade.setText("IReport");
        btn_iReport_grade.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_iReport_grade.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_iReport_gradeActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel21Layout = new javax.swing.GroupLayout(jPanel21);
        jPanel21.setLayout(jPanel21Layout);
        jPanel21Layout.setHorizontalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel21Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btn_iReport_grade, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btn_reportGrade, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btn_print_grade, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel21Layout.setVerticalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel21Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btn_print_grade)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btn_reportGrade)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btn_iReport_grade)
                .addContainerGap(42, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jPanel19, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel20, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel18, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel21, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 73, Short.MAX_VALUE)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 341, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addComponent(jPanel20, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel18, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanel21, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel19, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(25, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Results", jPanel10);

        jLabel48.setFont(new java.awt.Font("Serif", 1, 14)); // NOI18N
        jLabel48.setText("Welcome");

        jLabel49.setFont(new java.awt.Font("Serif", 1, 14)); // NOI18N
        jLabel49.setText("UIST Management System Admin Student Section");

        lbl_names.setFont(new java.awt.Font("Serif", 1, 14)); // NOI18N
        lbl_names.setForeground(new java.awt.Color(0, 153, 204));

        btn_logout.setFont(new java.awt.Font("Serif", 1, 10)); // NOI18N
        btn_logout.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgresource/Apps-session-logout-icon.png"))); // NOI18N
        btn_logout.setText("Logout");
        btn_logout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_logoutActionPerformed(evt);
            }
        });

        btn_close.setFont(new java.awt.Font("Serif", 1, 10)); // NOI18N
        btn_close.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgresource/Button-Close-icon.png"))); // NOI18N
        btn_close.setText("Close");
        btn_close.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_closeActionPerformed(evt);
            }
        });

        jMenu1.setText("File");
        jMenu1.add(jSeparator1);

        jMenuItem3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgresource/Button-Close-icon.png"))); // NOI18N
        jMenuItem3.setText("Close");
        jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem3ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem3);

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
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 931, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jScrollPane1)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel48)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(lbl_names, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel49, javax.swing.GroupLayout.PREFERRED_SIZE, 318, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btn_logout)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btn_close)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txt_date, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txt_time, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txt_time, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_date, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl_names, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel48)
                        .addComponent(jLabel49)
                        .addComponent(btn_logout)
                        .addComponent(btn_close)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 434, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void student_tableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_student_tableMouseClicked
            tableStudentClickOrReleased();
            txt_username.setBackground(Color.WHITE);
            txt_password.setBackground(Color.WHITE);
            txt_email.setBackground(Color.WHITE);
    }//GEN-LAST:event_student_tableMouseClicked
 public void close2(){
           //new AdminPanel().setVisible(false);
         //new AdminPanel().setDefaultCloseOperation(HIDE_ON_CLOSE);
         close();
         AdminPanel frame = new AdminPanel(username, id);
         frame.setLocationRelativeTo(null);
         frame.setVisible(true);
 }
    private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem3ActionPerformed
        close2();
    }//GEN-LAST:event_jMenuItem3ActionPerformed

    private void btn_printActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_printActionPerformed
                          
        if(combo_searchCategory.getSelectedIndex() ==0){
        
                                MessageFormat header = new MessageFormat("Students for all faculty");
				MessageFormat footer = new MessageFormat("Page{0,number,integer}");
				
				try{
					student_table.print(JTable.PrintMode.NORMAL,header,footer);// print the table
					
					
				}catch(java.awt.print.PrinterException e){
					
					System.err.format("Cannot print %s %n", e.getMessage());
				}
                                
        }
        else if(combo_searchCategory.getSelectedItem() =="CSE"){
        
                                MessageFormat header = new MessageFormat("CSE students");
				MessageFormat footer = new MessageFormat("Page{0,number,integer}");
				
				try{
					student_table.print(JTable.PrintMode.NORMAL,header,footer);// print the table
					
					
				}catch(java.awt.print.PrinterException e){
					
					System.err.format("Cannot print %s %n", e.getMessage());
				}
                     
        
        }
        else if(combo_searchCategory.getSelectedItem() =="CNS"){
        
                                MessageFormat header = new MessageFormat("CNS students");
				MessageFormat footer = new MessageFormat("Page{0,number,integer}");
				
				try{
					student_table.print(JTable.PrintMode.NORMAL,header,footer);// print the table
					
					
				}catch(java.awt.print.PrinterException e){
					
					System.err.format("Cannot print %s %n", e.getMessage());
				}
                     
        
        }
        else if(combo_searchCategory.getSelectedItem() =="ISVMA"){
        
                                MessageFormat header = new MessageFormat("ISVMA students");
				MessageFormat footer = new MessageFormat("Page{0,number,integer}");
				
				try{
					student_table.print(JTable.PrintMode.NORMAL,header,footer);// print the table
					
					
				}catch(java.awt.print.PrinterException e){
					
					System.err.format("Cannot print %s %n", e.getMessage());
				}
                     
        
        }
        else if(combo_searchCategory.getSelectedItem() =="MIR"){
        
                                MessageFormat header = new MessageFormat("MIR students");
				MessageFormat footer = new MessageFormat("Page{0,number,integer}");
				
				try{
					student_table.print(JTable.PrintMode.NORMAL,header,footer);// print the table
					
					
				}catch(java.awt.print.PrinterException e){
					
					System.err.format("Cannot print %s %n", e.getMessage());
				}
                     
        
        }
        else if(combo_searchCategory.getSelectedItem() =="ISA"){
        
                                MessageFormat header = new MessageFormat("ISA students");
				MessageFormat footer = new MessageFormat("Page{0,number,integer}");
				
				try{
					student_table.print(JTable.PrintMode.NORMAL,header,footer);// print the table
					
					
				}catch(java.awt.print.PrinterException e){
					
					System.err.format("Cannot print %s %n", e.getMessage());
				}
                     
        
        }        
    }//GEN-LAST:event_btn_printActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        JFileChooser chooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("*.IMAGE", "jpg","gif","png");
        chooser.addChoosableFileFilter(filter);
        //int result = fileChooser.showSaveDialog(null);
        
        int result = chooser.showSaveDialog(null);
        if(result == JFileChooser.APPROVE_OPTION){
        File f = chooser.getSelectedFile();
        file_name_profile_pic = f.getAbsolutePath();
        txt_file_attach_path.setText(file_name_profile_pic);
        

        ImageIcon imageIcon = new ImageIcon(file_name_profile_pic); // load the image to a imageIcon
        java.awt.Image image1 = imageIcon.getImage(); // transform it 
	java.awt.Image newimg = image1.getScaledInstance(100, 100,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
        imageIcon = new ImageIcon(newimg);  // transform it back
        
        lbl_pic1.setIcon(imageIcon);

        try{
            File image = new File(file_name_profile_pic);
            FileInputStream fis = new FileInputStream(image);
            
            ByteArrayOutputStream bos = new ByteArrayOutputStream(); //converting the file to the right format
            byte[] buf = new byte[1024]; //giving it a buffer size
            for(int readNum; (readNum = fis.read(buf))!= -1;){
                bos.write(buf, 0, readNum);
            }
            
           profile_image1 = bos.toByteArray();
            
        }catch(Exception e){
          JOptionPane.showMessageDialog(null, e);
        }  
        }else{
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void txt_emailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_emailActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_emailActionPerformed

    private void btn_update1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_update1ActionPerformed
     
       
        /*
            #############################################################
        */       
         /* Generate a random admission number while checking if previous admission 
            number exists if generated number already exist, set admission_number
            to false and repeat the process until a new Rand is generated
          */
       boolean admissionNumberExist = false;
       int admission_number = 0;
       int getId = 0;
        do{
        try{ 
            Random rnd = new Random();
            admission_number = 123456789 + rnd.nextInt(876543210) + 1;
            String sqlQuery = "SELECT COUNT(admission_number) FROM students WHERE admission_number = '"+admission_number+"'";
            pst1 = conn.prepareStatement(sqlQuery);
            rs1  = pst1.executeQuery();
        if(rs1.next()){
            
            int getCount = rs1.getInt("COUNT(admission_number)");
            
            if(getCount == 0){             
               admissionNumberExist = true;
               
                }
           }
        }catch(Exception e){
            e.printStackTrace();
        }
        }while(!admissionNumberExist);
        //System.out.println(getId + " - " + admission_number);
        /*
            #############################################################
        */
        /*
            #############################################################
        */       
        /*
            Check if email inputed by the admin is valid 
        */

        
        /*
            #############################################################
        */
        String username1 = txt_username1.getText();
        String password1 = txt_password1.getText();
        /*Check if fields are empty or not before inserting data*/
        
        
        
        String date_of_birth = ((JTextField)txt_date_of_birth1.getDateEditor().getUiComponent()).getText();
        if(!txt_fn1.getText().isEmpty() && !txt_ln1.getText().isEmpty()  && buttonGroup2.getSelection() != null
                && combo_nationality_list1.getSelectedIndex()!=0 && combo_country_of_birth1.getSelectedIndex() != 0 && !date_of_birth.equals("")
                && !txt_email1.getText().isEmpty()){

             emailExist1 = checkEmailifExxist1(txt_email1.getText());
             userNamecheck1 = checkUsername1(txt_username1.getText());
             if(emailStatus){
                 if(emailExist1){
                           if(username1.length()>=5 && password1.length() >=5){
                                if(userNamecheck1){
                                    
            /*Check if admission date is choosing, if not set the admission date to the default date*/
                String admission_date = ((JTextField)txt_admission_date1.getDateEditor().getUiComponent()).getText();
                if(admission_date.equals("")){
                                Calendar cal = new GregorianCalendar();
                                int month = cal.get(Calendar.MONTH);
                                int year = cal.get(Calendar.YEAR);
                                int day = cal.get(Calendar.DAY_OF_MONTH);
                                admission_date = year+"-"+month+"-"+day;
            
                    }  
                       /*Check if the User is given a picture or not*/
                /*If the user is given a picture , do nothing or else assign the user a default picture based on his/her gender*/
                    if(lbl_pic1.getIcon() == null){
                       if(gender1.equalsIgnoreCase("Male")){
                        try{
                         File image = new File("defaultpicturesImportant\\male_default.jpg");
                         FileInputStream fis = new FileInputStream(image);

                         ByteArrayOutputStream bos = new ByteArrayOutputStream(); //converting the file to the right format
                         byte[] buf = new byte[1024]; //giving it a buffer size
                         for(int readNum; (readNum = fis.read(buf))!= -1;){
                             bos.write(buf, 0, readNum);
                         }

                        profile_image1 = bos.toByteArray();

                     }catch(Exception e){
                       JOptionPane.showMessageDialog(null, e);
                         }
                       }else if(gender1.equalsIgnoreCase("Female")){
                        try{
                         File image = new File("defaultpicturesImportant\\female_default.jpg");
                         FileInputStream fis = new FileInputStream(image);

                         ByteArrayOutputStream bos = new ByteArrayOutputStream(); //converting the file to the right format
                         byte[] buf = new byte[1024]; //giving it a buffer size
                         for(int readNum; (readNum = fis.read(buf))!= -1;){
                             bos.write(buf, 0, readNum);
                         }

                        profile_image1 = bos.toByteArray();

                     }catch(Exception e){
                       JOptionPane.showMessageDialog(null, e);
                         }          
                       }
                    }               
                
                /*############################################*/
        String sqlInsert = "INSERT INTO students (first_name,last_name,middle_name,gender,nationality,date_of_birth,email,faculty,"
                 + "admission_number, username, password,year_of_studies,status,phone_number,address,regular_or_part_time, comment,"
                 + "country_of_birth, admission_date,photo) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
            try{
             pst = conn.prepareStatement(sqlInsert);
             
             pst.setString(1,txt_fn1.getText());
             pst.setString(2,txt_ln1.getText());
             pst.setString(3,txt_mn1.getText());
             pst.setString(4,gender1);
             pst.setString(5,combo_nationality_list1.getSelectedItem().toString());
             pst.setString(6, ((JTextField)txt_date_of_birth1.getDateEditor().getUiComponent()).getText());
             pst.setString(7,txt_email1.getText());
             pst.setString(8,combo_faculty1.getSelectedItem().toString()); 
             pst.setString(9,Integer.toString(admission_number));
             pst.setString(10,txt_username1.getText());
             pst.setString(11,txt_password1.getText());
             pst.setString(12,combo_year_of_studies1.getSelectedItem().toString());
             pst.setString(13,"Active");
             pst.setString(14,txt_phone_number1.getText());
             pst.setString(15,txt_address1.getText());
             pst.setString(16,combo_regular_part1.getSelectedItem().toString());
             pst.setString(17,tArea_student_comment1.getText());
             pst.setString(18,combo_country_of_birth1.getSelectedItem().toString());
             pst.setString(19,admission_date);
             pst.setBytes(20,profile_image1);
             pst.execute();
                
              txt_email1.setBackground(Color.WHITE);
              txt_username1.setBackground(Color.WHITE);
              JOptionPane.showMessageDialog(null, "Data inserted");
              
              clear_fields1();
              
                 }catch(Exception e){
             JOptionPane.showMessageDialog(null, e);
              }
             }else{
              txt_username1.setBackground(Color.RED);
              JOptionPane.showMessageDialog(null, "Username is taken!!!");
                                                
             }
         }else{
               JOptionPane.showMessageDialog(null,"Your username and password must be at least 5 characters long");
                }
           }else{
                txt_email1.setBackground(Color.RED);
                JOptionPane.showMessageDialog(null, "Email is already registered!!!");                
               }
          }else{
             //email not correct
                     JOptionPane.showMessageDialog(null, "The email doesn't exist");
             }
        }else{
            JOptionPane.showMessageDialog(null, "Check to make sure all required field are entered");
        }
     

     Update_table();
     
    }//GEN-LAST:event_btn_update1ActionPerformed

    private void txt_phone_number1KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_phone_number1KeyTyped
         char c = evt.getKeyChar();
         if(!(Character.isDigit(c) || c==KeyEvent.VK_BACKSPACE) || c==KeyEvent.VK_DELETE){
             evt.consume();
         }
    }//GEN-LAST:event_txt_phone_number1KeyTyped

    private void rbtn_maleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbtn_maleActionPerformed
        gender1 = "Male";
    }//GEN-LAST:event_rbtn_maleActionPerformed

    private void rbtn_femaleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbtn_femaleActionPerformed
        gender1 = "Female";
    }//GEN-LAST:event_rbtn_femaleActionPerformed

public int checkUsername(){   
 String checkEmailAndUsername = "SELECT COUNT(username) FROM students WHERE id != '"+txt_student_id.getText()+"' AND username = '"+txt_username.getText()+"'";
 int checkifDetailsExist = 0;       
   try{
      pst = conn.prepareStatement(checkEmailAndUsername);
      rs = pst.executeQuery();
      if(rs.next()){
          checkifDetailsExist = Integer.parseInt(rs.getString("COUNT(username)"));
      }
   }catch(Exception e){
      JOptionPane.showMessageDialog(null, e);
   }
   return checkifDetailsExist;
}  
/*Check the email for full update*/
public int checkEmailUpdate(){   
 String checkEmailAndUsername = "SELECT COUNT(email) FROM students WHERE id != '"+txt_student_id.getText()+"' AND email = '"+txt_email.getText()+"'";
 int checkifDetailsExist = 0;       
   try{
      pst = conn.prepareStatement(checkEmailAndUsername);
      rs = pst.executeQuery();
      if(rs.next()){
          checkifDetailsExist = Integer.parseInt(rs.getString("COUNT(email)"));
      }
   }catch(Exception e){
      JOptionPane.showMessageDialog(null, e);
   }
   return checkifDetailsExist;
}

 //Update Student information
    private void btn_updateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_updateActionPerformed
           
        
           if(!txt_student_id.getText().isEmpty()){
             if(!txt_fn.getText().isEmpty() && !txt_ln.getText().isEmpty() 
                     && combo_nationality_list.getSelectedIndex()!=0 && combo_country_of_birth.getSelectedIndex() != 0
                && !txt_email.getText().isEmpty() && combo_faculty.getSelectedIndex() !=0){
                     if(emailStatus){
                         txt_email.setBackground(Color.WHITE);
                          if(txt_username.getText().length() >= 5){
                              txt_username.setBackground(Color.WHITE);
                              if(txt_password.getText().length() >=5){
                                  txt_password.setBackground(Color.WHITE);
                                  if(checkEmailUpdate() == 0){ 
                                      txt_email.setBackground(Color.WHITE);
                                     if(checkUsername() == 0){ 
                                       
					String student_id = txt_student_id.getText();
					String update_fn = txt_fn.getText();
					String update_ln = txt_ln.getText();
					String update_mn = txt_mn.getText();
                                        String update_dad_name = txt_username.getText();
                                        String update_mum_name = txt_password.getText();
                                        String update_comment = tArea_student_comment.getText();
                                        String update_phone = txt_phone_number.getText();
                                        String update_address = txt_address.getText();
                                        String update_email = txt_email.getText();
                                        String update_status = combo_status.getSelectedItem().toString();
                                        String update_regular_partString= combo_full_part_time.getSelectedItem().toString();                                        
                                        String update_faculty = combo_faculty.getSelectedItem().toString();
                                        String update_nationality = combo_nationality_list.getSelectedItem().toString();
                                        String update_country_of_birth = combo_country_of_birth.getSelectedItem().toString();
                                        String update_gender = combo_gender.getSelectedItem().toString();
                                        String update_birth_date = ((JTextField)txt_date_of_birth.getDateEditor().getUiComponent()).getText();
                                        String update_study_year = combo_year_of_studies.getSelectedItem().toString();
                                        
                try{                       

					String sql = "UPDATE students SET gender ='"+update_gender+"', first_name ='"+update_fn+"', last_name = '"+update_ln+"', middle_name = '"+update_mn+"',"
                                                +"nationality ='"+update_nationality+"', date_of_birth ='"+update_birth_date+"', email = '"+update_email+"', faculty = '"+update_faculty+"',"
                                                +"username ='"+update_dad_name+"', password ='"+update_mum_name+"', year_of_studies = '"+update_study_year+"', status = '"+update_status+"',"
                                                +"phone_number ='"+update_phone+"', address ='"+update_address+"', regular_or_part_time = '"+update_regular_partString+"', comment = '"+update_comment+"',"
                                                +"country_of_birth ='"+update_country_of_birth+"'"
                                                + " WHERE id ='"+student_id+"'";
					pst = conn.prepareStatement(sql);
					pst.execute();
                                        txt_username.setBackground(Color.WHITE);
                                        txt_password.setBackground(Color.WHITE);
					
                if(!txt_file_attach_path.getText().isEmpty()){
                      byte [] profile_imageS = null;
                        try{
                           File image = new File(txt_file_attach_path.getText());
                           FileInputStream fis = new FileInputStream(image);

                           ByteArrayOutputStream bos = new ByteArrayOutputStream(); //converting the file to the right format
                           byte[] buf = new byte[1024]; //giving it a buffer size
                           for(int readNum; (readNum = fis.read(buf))!= -1;){
                               bos.write(buf, 0, readNum);
                           }

                          profile_imageS = bos.toByteArray();
                          
                          String update_image = "UPDATE students SET photo = ? WHERE id='"+txt_student_id.getText()+"'";
                          
                          pst1 = conn.prepareStatement(update_image);
                          pst1.setBytes(1,profile_imageS);
                          pst1.execute();
                           
                          txt_file_attach_path.setText("");
                          
                       }catch(Exception e){
                         //JOptionPane.showMessageDialog(null, e);
                       } 
                                        }
                                                         
			            JOptionPane.showMessageDialog(null, "Data Updated");
				    Update_table();                       
                   }catch(Exception e){
		JOptionPane.showMessageDialog(null, e);
	 }                                   
                                        
 
                                     }else{
                                        JOptionPane.showMessageDialog(null,"Another student is already assigned the username!!!","ERROR",JOptionPane.ERROR_MESSAGE);
                                        txt_username.setBackground(Color.RED);                            
                                }
                                  }else{
                                        JOptionPane.showMessageDialog(null,"Another student is already assigned the email address!!!","ERROR",JOptionPane.ERROR_MESSAGE);
                                        txt_email.setBackground(Color.RED);                             
                                }
                              }else{
                            JOptionPane.showMessageDialog(null,"The password must be at least 5 Characters long","ERROR",JOptionPane.ERROR_MESSAGE);
                           txt_password.setBackground(Color.RED);
                              }
                     }else{
                           JOptionPane.showMessageDialog(null,"The username must be at least 5 Characters long","ERROR",JOptionPane.ERROR_MESSAGE);
                           txt_username.setBackground(Color.RED);
                          }
                     }else{
                         JOptionPane.showMessageDialog(null,"Please enter a valid email address","ERROR",JOptionPane.ERROR_MESSAGE);
                         txt_email.setBackground(Color.RED);
                     }
                        }else{
                     JOptionPane.showMessageDialog(null, "Please fill all neccessary field");
                             }
        }else{
                  JOptionPane.showMessageDialog(null,"Nothing to Update!!!","ERROR",JOptionPane.ERROR_MESSAGE);
         }

    }//GEN-LAST:event_btn_updateActionPerformed

    private void txt_phone_numberKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_phone_numberKeyTyped
         char c = evt.getKeyChar();
         if(!(Character.isDigit(c) || c==KeyEvent.VK_BACKSPACE) || c==KeyEvent.VK_DELETE){
             evt.consume();
         }
    }//GEN-LAST:event_txt_phone_numberKeyTyped

    private void txt_searchCategoryKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_searchCategoryKeyReleased
      		try{
			String sql = "SELECT * FROM students WHERE first_name =? OR last_name =? OR middle_name = ?";
					
			pst = conn.prepareStatement(sql);
			pst.setString(1, txt_searchCategory.getText());
                        pst.setString(2, txt_searchCategory.getText());
                        pst.setString(3, txt_searchCategory.getText());
					
			rs = pst.executeQuery();
					
		    if(rs.next()){
                        String id = rs.getString("id");
                        txt_student_id.setText(id);
                        String fn = rs.getString("first_name");
                        txt_fn.setText(fn);
                        String ln = rs.getString("last_name");
                        txt_ln.setText(ln);
                        String mn = rs.getString("middle_name");
                        txt_mn.setText(id);
                        String nationality = rs.getString("nationality");
                        combo_nationality_list.setSelectedItem(nationality);                           
                        String email = rs.getString("email");
                        txt_email.setText(email);   
                        String address = rs.getString("address");
                        txt_address.setText(address);
                        String YOS = rs.getString("year_of_studies");
                        combo_year_of_studies.setSelectedItem(YOS);
                        String phone_number = rs.getString("phone_number");
                        txt_phone_number.setText(phone_number);                            
                        String regular_part_time_student = rs.getString("regular_or_part_time");
                        combo_full_part_time.setSelectedItem(regular_part_time_student);
                        String status_of_student = rs.getString("status");
                        combo_status.setSelectedItem(status_of_student);
                        String admission_number = rs.getString("admission_number");
                        txt_admission_number.setText(admission_number);
                        Date admission_date = rs.getDate("admission_date");
                        txt_admission_date.setDate(admission_date);
                        String mother_name = rs.getString("password");
                        txt_password.setText(mother_name);
                        String father_name = rs.getString("username");
                        txt_username.setText(father_name);
                        String gender = rs.getString("gender");
                        combo_gender.setSelectedItem(gender);
                        String comment = rs.getString("comment");
                        tArea_student_comment.setText(comment);
                        String faculty = rs.getString("faculty");
                        combo_faculty.setSelectedItem(faculty);
                        String country_of_birth = rs.getString("country_of_birth");
                        combo_country_of_birth.setSelectedItem(country_of_birth);
                        Date Dob = rs.getDate("date_of_birth");
                        txt_date_of_birth.setDate(Dob);                                      					
		    }					
		}catch(Exception e){
		    JOptionPane.showMessageDialog(null, e);
	        }
      		try{
			String sql = "SELECT * FROM students WHERE last_name =?";
					
			pst = conn.prepareStatement(sql);
			pst.setString(1, txt_searchCategory.getText());
					
			rs = pst.executeQuery();
					
		    if(rs.next()){
                        String id = rs.getString("id");
                        txt_student_id.setText(id);
                        String fn = rs.getString("first_name");
                        txt_fn.setText(fn);
                        String ln = rs.getString("last_name");
                        txt_ln.setText(ln);
                        String mn = rs.getString("middle_name");
                        txt_mn.setText(id);
                        String nationality = rs.getString("nationality");
                        combo_nationality_list.setSelectedItem(nationality);                           
                        String email = rs.getString("email");
                        txt_email.setText(email);   
                        String address = rs.getString("address");
                        txt_address.setText(address);
                        String YOS = rs.getString("year_of_studies");
                        combo_year_of_studies.setSelectedItem(YOS);
                        String phone_number = rs.getString("phone_number");
                        txt_phone_number.setText(phone_number);                            
                        String regular_part_time_student = rs.getString("regular_or_part_time");
                        combo_full_part_time.setSelectedItem(regular_part_time_student);
                        String status_of_student = rs.getString("status");
                        combo_status.setSelectedItem(status_of_student);
                        String admission_number = rs.getString("admission_number");
                        txt_admission_number.setText(admission_number);
                        Date admission_date = rs.getDate("admission_date");
                        txt_admission_date.setDate(admission_date);
                        String mother_name = rs.getString("password");
                        txt_password.setText(mother_name);
                        String father_name = rs.getString("username");
                        txt_username.setText(father_name);
                        String gender = rs.getString("gender");
                        combo_gender.setSelectedItem(gender);
                        String comment = rs.getString("comment");
                        tArea_student_comment.setText(comment);
                        String faculty = rs.getString("faculty");
                        combo_faculty.setSelectedItem(faculty);
                        String country_of_birth = rs.getString("country_of_birth");
                        combo_country_of_birth.setSelectedItem(country_of_birth);
                        Date Dob = rs.getDate("date_of_birth");
                        txt_date_of_birth.setDate(Dob);                                      					
		    }					
		}catch(Exception e){
		    JOptionPane.showMessageDialog(null, e);
	        }                               
      		try{
			String sql = "SELECT * FROM students WHERE id =?";
					
			pst = conn.prepareStatement(sql);
			pst.setString(1, txt_searchCategory.getText());
					
			rs = pst.executeQuery();
					
		    if(rs.next()){
                        String id = rs.getString("id");
                        txt_student_id.setText(id);
                        String fn = rs.getString("first_name");
                        txt_fn.setText(fn);
                        String ln = rs.getString("last_name");
                        txt_ln.setText(ln);
                        String mn = rs.getString("middle_name");
                        txt_mn.setText(id);
                        String nationality = rs.getString("nationality");
                        combo_nationality_list.setSelectedItem(nationality);                           
                        String email = rs.getString("email");
                        txt_email.setText(email);   
                        String address = rs.getString("address");
                        txt_address.setText(address);
                        String YOS = rs.getString("year_of_studies");
                        combo_year_of_studies.setSelectedItem(YOS);
                        String phone_number = rs.getString("phone_number");
                        txt_phone_number.setText(phone_number);                            
                        String regular_part_time_student = rs.getString("regular_or_part_time");
                        combo_full_part_time.setSelectedItem(regular_part_time_student);
                        String status_of_student = rs.getString("status");
                        combo_status.setSelectedItem(status_of_student);
                        String admission_number = rs.getString("admission_number");
                        txt_admission_number.setText(admission_number);
                        Date admission_date = rs.getDate("admission_date");
                        txt_admission_date.setDate(admission_date);
                        String mother_name = rs.getString("password");
                        txt_password.setText(mother_name);
                        String father_name = rs.getString("username");
                        txt_username.setText(father_name);
                        String gender = rs.getString("gender");
                        combo_gender.setSelectedItem(gender);
                        String comment = rs.getString("comment");
                        tArea_student_comment.setText(comment);
                        String faculty = rs.getString("faculty");
                        combo_faculty.setSelectedItem(faculty);
                        String country_of_birth = rs.getString("country_of_birth");
                        combo_country_of_birth.setSelectedItem(country_of_birth);
                        Date Dob = rs.getDate("date_of_birth");
                        txt_date_of_birth.setDate(Dob);                                      					
		    }					
		}catch(Exception e){
		    JOptionPane.showMessageDialog(null, e);
	        }
      		try{
			String sql = "SELECT * FROM students WHERE middle_name =?";
					
			pst = conn.prepareStatement(sql);
			pst.setString(1, txt_searchCategory.getText());
					
			rs = pst.executeQuery();
					
		    if(rs.next()){
                        String id = rs.getString("id");
                        txt_student_id.setText(id);
                        String fn = rs.getString("first_name");
                        txt_fn.setText(fn);
                        String ln = rs.getString("last_name");
                        txt_ln.setText(ln);
                        String mn = rs.getString("middle_name");
                        txt_mn.setText(id);
                        String nationality = rs.getString("nationality");
                        combo_nationality_list.setSelectedItem(nationality);                           
                        String email = rs.getString("email");
                        txt_email.setText(email);   
                        String address = rs.getString("address");
                        txt_address.setText(address);
                        String YOS = rs.getString("year_of_studies");
                        combo_year_of_studies.setSelectedItem(YOS);
                        String phone_number = rs.getString("phone_number");
                        txt_phone_number.setText(phone_number);                            
                        String regular_part_time_student = rs.getString("regular_or_part_time");
                        combo_full_part_time.setSelectedItem(regular_part_time_student);
                        String status_of_student = rs.getString("status");
                        combo_status.setSelectedItem(status_of_student);
                        String admission_number = rs.getString("admission_number");
                        txt_admission_number.setText(admission_number);
                        Date admission_date = rs.getDate("admission_date");
                        txt_admission_date.setDate(admission_date);
                        String mother_name = rs.getString("password");
                        txt_password.setText(mother_name);
                        String father_name = rs.getString("username");
                        txt_username.setText(father_name);
                        String gender = rs.getString("gender");
                        combo_gender.setSelectedItem(gender);
                        String comment = rs.getString("comment");
                        tArea_student_comment.setText(comment);
                        String faculty = rs.getString("faculty");
                        combo_faculty.setSelectedItem(faculty);
                        String country_of_birth = rs.getString("country_of_birth");
                        combo_country_of_birth.setSelectedItem(country_of_birth);
                        Date Dob = rs.getDate("date_of_birth");
                        txt_date_of_birth.setDate(Dob);                                      					
		    }					
		}catch(Exception e){
		    JOptionPane.showMessageDialog(null, e);
	        } 
      		try{
			String sql = "SELECT * FROM students WHERE year_of_studies =?";
					
			pst = conn.prepareStatement(sql);
			pst.setString(1, txt_searchCategory.getText());
					
			rs = pst.executeQuery();
					
		    if(rs.next()){
                        String id = rs.getString("id");
                        txt_student_id.setText(id);
                        String fn = rs.getString("first_name");
                        txt_fn.setText(fn);
                        String ln = rs.getString("last_name");
                        txt_ln.setText(ln);
                        String mn = rs.getString("middle_name");
                        txt_mn.setText(id);
                        String nationality = rs.getString("nationality");
                        combo_nationality_list.setSelectedItem(nationality);                           
                        String email = rs.getString("email");
                        txt_email.setText(email);   
                        String address = rs.getString("address");
                        txt_address.setText(address);
                        String YOS = rs.getString("year_of_studies");
                        combo_year_of_studies.setSelectedItem(YOS);
                        String phone_number = rs.getString("phone_number");
                        txt_phone_number.setText(phone_number);                            
                        String regular_part_time_student = rs.getString("regular_or_part_time");
                        combo_full_part_time.setSelectedItem(regular_part_time_student);
                        String status_of_student = rs.getString("status");
                        combo_status.setSelectedItem(status_of_student);
                        String admission_number = rs.getString("admission_number");
                        txt_admission_number.setText(admission_number);
                        Date admission_date = rs.getDate("admission_date");
                        txt_admission_date.setDate(admission_date);
                        String mother_name = rs.getString("password");
                        txt_password.setText(mother_name);
                        String father_name = rs.getString("username");
                        txt_username.setText(father_name);
                        String gender = rs.getString("gender");
                        combo_gender.setSelectedItem(gender);
                        String comment = rs.getString("comment");
                        tArea_student_comment.setText(comment);
                        String faculty = rs.getString("faculty");
                        combo_faculty.setSelectedItem(faculty);
                        String country_of_birth = rs.getString("country_of_birth");
                        combo_country_of_birth.setSelectedItem(country_of_birth);
                        Date Dob = rs.getDate("date_of_birth");
                        txt_date_of_birth.setDate(Dob);                                      					
		    }					
		}catch(Exception e){
		    JOptionPane.showMessageDialog(null, e);
	        }
      		try{
			String sql = "SELECT * FROM students WHERE admission_number =?";
					
			pst = conn.prepareStatement(sql);
			pst.setString(1, txt_searchCategory.getText());
					
			rs = pst.executeQuery();
					
		    if(rs.next()){
                        String id = rs.getString("id");
                        txt_student_id.setText(id);
                        String fn = rs.getString("first_name");
                        txt_fn.setText(fn);
                        String ln = rs.getString("last_name");
                        txt_ln.setText(ln);
                        String mn = rs.getString("middle_name");
                        txt_mn.setText(id);
                        String nationality = rs.getString("nationality");
                        combo_nationality_list.setSelectedItem(nationality);                           
                        String email = rs.getString("email");
                        txt_email.setText(email);   
                        String address = rs.getString("address");
                        txt_address.setText(address);
                        String YOS = rs.getString("year_of_studies");
                        combo_year_of_studies.setSelectedItem(YOS);
                        String phone_number = rs.getString("phone_number");
                        txt_phone_number.setText(phone_number);                            
                        String regular_part_time_student = rs.getString("regular_or_part_time");
                        combo_full_part_time.setSelectedItem(regular_part_time_student);
                        String status_of_student = rs.getString("status");
                        combo_status.setSelectedItem(status_of_student);
                        String admission_number = rs.getString("admission_number");
                        txt_admission_number.setText(admission_number);
                        Date admission_date = rs.getDate("admission_date");
                        txt_admission_date.setDate(admission_date);
                        String mother_name = rs.getString("password");
                        txt_password.setText(mother_name);
                        String father_name = rs.getString("username");
                        txt_username.setText(father_name);
                        String gender = rs.getString("gender");
                        combo_gender.setSelectedItem(gender);
                        String comment = rs.getString("comment");
                        tArea_student_comment.setText(comment);
                        String faculty = rs.getString("faculty");
                        combo_faculty.setSelectedItem(faculty);
                        String country_of_birth = rs.getString("country_of_birth");
                        combo_country_of_birth.setSelectedItem(country_of_birth);
                        Date Dob = rs.getDate("date_of_birth");
                        txt_date_of_birth.setDate(Dob);                                      					
		    }					
		}catch(Exception e){
		    JOptionPane.showMessageDialog(null, e);
	        }
                   
    }//GEN-LAST:event_txt_searchCategoryKeyReleased

    private void combo_searchCategoryPopupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {//GEN-FIRST:event_combo_searchCategoryPopupMenuWillBecomeInvisible
        	
                String combo_faculty = (String) combo_searchCategory.getSelectedItem();
		if(combo_searchCategory.getSelectedIndex() == 0){
                    String sql = "SELECT * FROM students";
                    try {
                            pst = conn.prepareStatement(sql);
                            rs = pst.executeQuery();
                            student_table.setModel(DbUtils.resultSetToTableModel(rs));

                    } catch (SQLException e) {
                            JOptionPane.showMessageDialog(null, e);

                    }finally{
                            try{
                                    rs.close();
                                    pst.close();
                            }catch(Exception e ){

                       }

                    }                
                }			
                else{
                    String sql = "SELECT * FROM students WHERE faculty = '"+combo_faculty+"'";
                    try {
                            pst = conn.prepareStatement(sql);
                            rs = pst.executeQuery();
                            student_table.setModel(DbUtils.resultSetToTableModel(rs));

                    } catch (SQLException e) {
                            JOptionPane.showMessageDialog(null, e);

                    }finally{
                            try{
                                    rs.close();
                                    pst.close();
                            }catch(Exception e ){

                       }

                    }
                }
    }//GEN-LAST:event_combo_searchCategoryPopupMenuWillBecomeInvisible

    private void btn_clearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_clearActionPerformed
        clear_fields();
    }//GEN-LAST:event_btn_clearActionPerformed

    private void txt_emailKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_emailKeyReleased
                
        emailStatus = ValidateEmail.validateEmail(txt_email.getText());
        if(emailStatus){
            txt_email.setBackground(Color.WHITE);
        }else{
            txt_email.setBackground(Color.RED);
        }
        
    }//GEN-LAST:event_txt_emailKeyReleased

    Boolean emailExist1 = false;
    Boolean userNamecheck1 = false;
    
    private boolean checkEmailifExxist1(String email){
            
            String  sqlCheckEmail = "SELECT COUNT(email) FROM students WHERE email = '"+email+"'";
            int count = 0;  
            boolean status = false;
            try{
                  pst = conn.prepareStatement(sqlCheckEmail);
                  rs = pst.executeQuery();
                  if(rs.next()){
                       count = Integer.parseInt(rs.getString("COUNT(email)"));
                  }
                  
            }catch(Exception e){
                JOptionPane.showMessageDialog(null,"Email already registered!!!");
                txt_email1.setBackground(Color.RED);
            }
            if(count >=1){
                status = false;
            }else{
                status = true;
            }
            return status;
    }
    private boolean checkUsername1(String username){
            String  sqlCheckEmail = "SELECT COUNT(username) FROM students WHERE username = '"+username+"'";
            int count = 0;  
            boolean status = false;
            try{
                  pst = conn.prepareStatement(sqlCheckEmail);
                  rs = pst.executeQuery();
                  if(rs.next()){
                       count = Integer.parseInt(rs.getString("COUNT(username)"));
                  }
                  
            }catch(Exception e){
                JOptionPane.showMessageDialog(null,e);
                txt_username1.setBackground(Color.RED);
            }
            if(count >=1){
                status = false;
            }else{
                status = true;
            }
            return status;    
    }      
    

    private void txt_email1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_email1KeyReleased
        emailStatus = ValidateEmail.validateEmail(txt_email1.getText());
        if(emailStatus){
            txt_email1.setBackground(Color.WHITE);
        }else{
            txt_email1.setBackground(Color.RED);
        }
     
    }//GEN-LAST:event_txt_email1KeyReleased

    private void btn_clear1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_clear1ActionPerformed
        clear_fields1();
    }//GEN-LAST:event_btn_clear1ActionPerformed

    private void txt_student_studentID_add_scoreKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_student_studentID_add_scoreKeyTyped
        char c = evt.getKeyChar();
         if(!(Character.isDigit(c) || c==KeyEvent.VK_BACKSPACE) || c==KeyEvent.VK_DELETE){
             evt.consume();
         }
          
    }//GEN-LAST:event_txt_student_studentID_add_scoreKeyTyped

    private void txt_student_studentID_add_scoreKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_student_studentID_add_scoreKeyReleased
 
    }//GEN-LAST:event_txt_student_studentID_add_scoreKeyReleased

    private void student_name_resultPopupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {//GEN-FIRST:event_student_name_resultPopupMenuWillBecomeInvisible
       				try{
					
					String combo_student = (String) student_name_result.getSelectedItem();
					
					String sql = "SELECT * FROM students WHERE first_name = ? ";
					pst = conn.prepareStatement(sql);
					pst.setString(1, combo_student);
					rs = pst.executeQuery();
					
					if(rs.next()){
						String addStudentId = rs.getString("id");
						txt_student_studentID_add_score.setText(addStudentId);
						
						String addStudentFirstName = rs.getString("first_name");
						txt_student_fname_add_score.setText(addStudentFirstName);
						
						String addStudentLastName = rs.getString("last_name");
						txt_student_lname_add_score.setText(addStudentLastName);
						
						String addFirstLastname = addStudentFirstName + " " + addStudentLastName;
						studentAddResultNames.setText(addFirstLastname);
						
					
					}
					
					
					
					
				}catch(Exception e){
					
				}
                                
 /*The code below selects grades based on the student id */                               
     
        int id = Integer.parseInt(txt_student_studentID_add_score.getText());
        String admission_number = "";
        try{
            String sql = "SELECT * FROM students WHERE id = '"+id+"'";
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            if(rs.next()){
                 admission_number = rs.getString("admission_number");
            }
        }catch(Exception e){
        
        }
        
            try{
                String sql = "SELECT * FROM grade WHERE student_admission_number = '"+admission_number+"' ";
                pst = conn.prepareStatement(sql);
                rs = pst.executeQuery();
                student_grade_table.setModel(DbUtils.resultSetToTableModel(rs));

            }catch(Exception e){
                JOptionPane.showMessageDialog(null, e);

            }
                                        
				
    }//GEN-LAST:event_student_name_resultPopupMenuWillBecomeInvisible

    private void btn_add_resultActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_add_resultActionPerformed
        
        String admission_number = "";
        String course_name = "";
        String course_code  = "";        
        if(!txt_student_studentID_add_score.getText().isEmpty() && !txt_student_mark.getText().isEmpty()){
           int id = Integer.parseInt(txt_student_studentID_add_score.getText());
           double score = Double.parseDouble(txt_student_mark.getText());
 
           String selectStudent = "SELECT * FROM students WHERE id ='"+id+"'";
           
           try{
               pst1 = conn.prepareStatement(selectStudent);
               rs1 = pst1.executeQuery();
               if(rs1.next()){
                   admission_number = rs1.getString("admission_number");
               }
           }catch(Exception e){
           }
           
           try{
           String selectCoureName = "SELECT * FROM courses WHERE course_name = ?";
           course_name = (String) combo_subject_for_marks.getSelectedItem();              
           pst = conn.prepareStatement(selectCoureName);
	   pst.setString(1, course_name);
	   rs = pst.executeQuery();
           while(rs.next()){
                 //course_name = rs.getString("course_name");
                 course_code = rs.getString("course_code");
            }
              
           }catch(Exception e){
               
           }
          
           
           /*#######################################################################################################
            Check to see if the student already has mark for the course
             
           
           #######################################################################################################*/
           String checkCourse = "SELECT COUNT(course_name) FROM grade WHERE course_name='"+course_name+"' AND student_admission_number"
                   + "='"+admission_number+"'";
          
           try{
               pst = conn.prepareStatement(checkCourse);
               rs = pst.executeQuery();
               if(rs.next()){
                   int count = rs.getInt("COUNT(course_name)");
                   if(count >= 1){
                      JOptionPane.showMessageDialog(null,"Student already has mark for the subject");
                          
               }else{
                       //call the function score_result to insert the data into database              
                 result.add_score_result(admission_number, course_name, course_code, score);               
                  }
               }
           }catch(Exception e){
           
           }           
            
        
        }else {
            JOptionPane.showMessageDialog(null,"Check Fields, make sure student ID  is not empty");
        }  
         fill_table_grade(admission_number);
    }//GEN-LAST:event_btn_add_resultActionPerformed

    private void update_scoreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_update_scoreActionPerformed
       
        int rowResult = student_grade_table.getSelectedRow();
        String result_table_click = (student_grade_table.getModel().getValueAt(rowResult, 0).toString());
        String admission_number = "";
        String getAdmission_number = "SELECT student_admission_number FROM grade WHERE grade_id = '"+result_table_click+"'";
        try{
         pst = conn.prepareStatement(getAdmission_number);
         rs = pst.executeQuery();
         if(rs.next()){
             admission_number = rs.getString("student_admission_number");
         }
        }catch(Exception e){
       
        }
        
        if(!txt_grade_score.getText().isEmpty() && !txt_subject_update.getText().isEmpty()) {
        
         
               double total_score = Double.parseDouble(txt_grade_score.getText());
               
               result.update_score_result(total_score,result_table_click); 
               
        }  
        fill_table_grade(admission_number);
    }//GEN-LAST:event_update_scoreActionPerformed

    private void student_grade_tableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_student_grade_tableMouseClicked
          gradeFilled();      
    }//GEN-LAST:event_student_grade_tableMouseClicked

    private void jPanel20MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel20MouseClicked
 
    }//GEN-LAST:event_jPanel20MouseClicked

    private void txt_student_markKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_student_markKeyTyped
         char c = evt.getKeyChar();
         if(!(Character.isDigit(c) || c==KeyEvent.VK_BACKSPACE) || c==KeyEvent.VK_DELETE){
             evt.consume();
         }       

    }//GEN-LAST:event_txt_student_markKeyTyped

    private void txt_grade_scoreKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_grade_scoreKeyTyped
         char c = evt.getKeyChar();
         if(!(Character.isDigit(c) || c==KeyEvent.VK_BACKSPACE) || c==KeyEvent.VK_DELETE){
             evt.consume();
         }
    }//GEN-LAST:event_txt_grade_scoreKeyTyped

    private void btn_reportGradeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_reportGradeActionPerformed
        
        try{
            
            /*Get the admission number and faculty*/
            String studentName = student_name_result.getSelectedItem().toString();
            int student_id = Integer.parseInt(txt_student_studentID_add_score.getText());
            String admission_number = "";
            String faculty = "";
            String nationality = "";
            String dob = "";
            String country = "";
            String admission_date = "";
            String first_name = "";
            String last_name = "";
            String middle_name = "";
            int countSubject = 0;
            String queryStudentForAdmission_number = "SELECT * FROM students WHERE id = '"+student_id+"' AND first_name = '"+studentName+"'";
            try{
               pst = conn.prepareStatement(queryStudentForAdmission_number);
               rs = pst.executeQuery();
               if(rs.next()){
                 admission_number = rs.getString("admission_number");
                 faculty = rs.getString("Faculty");
                 nationality = rs.getString("nationality");
                 country = rs.getString("country_of_birth");
                 admission_date = rs.getString("admission_date");
                 dob = rs.getString("date_of_birth");
                 first_name = rs.getString("first_name");
                 last_name = rs.getString("last_name");
                 middle_name = rs.getString("middle_name");
               }
            }catch(Exception e){}
           /*extract admission year*/
         String getYearAdmission = "";
         String dateExtract = "SELECT EXTRACT(YEAR FROM admission_date) FROM students WHERE id= "+student_id+""; 
         try{
            pst = conn.prepareStatement(dateExtract);
            rs = pst.executeQuery();
            if(rs.next()){
                getYearAdmission = rs.getString("EXTRACT(YEAR FROM admission_date)");
            }
         }catch(Exception e){}
        /*Extract DOB FROM database*/ 
         String extractYearofBirth = "";
         String extractMonthofBirth = "";
         String extractDayofBirth = "";
         String dobExtract = "SELECT EXTRACT(YEAR FROM date_of_birth), EXTRACT(MONTH FROM date_of_birth), "
                 + "EXTRACT(DAY FROM date_of_birth) FROM students WHERE id= "+student_id+"";
          try{
            pst = conn.prepareStatement(dobExtract);
            rs = pst.executeQuery();
            if(rs.next()){
                extractYearofBirth = rs.getString("EXTRACT(YEAR FROM date_of_birth)");
                extractMonthofBirth = rs.getString("EXTRACT(MONTH FROM date_of_birth)");
                extractDayofBirth = rs.getString("EXTRACT(Day FROM date_of_birth)");
            }
         }catch(Exception e){}
                    
            
        Document document = new Document(PageSize.A4);
        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(studentName+"Result.pdf"));
        document.open();
        
        /*Paragraph p = new Paragraph("бр_______________");
        Paragraph p1 = new Paragraph("Охрид______20____год.");
        */
        Paragraph p = new Paragraph("No_______________");
        Paragraph p1 = new Paragraph("Ohrid_____20__year.");
        
        Image govLogo = Image.getInstance("imagesImportant\\macedoniaLogo.png");
        govLogo.scaleToFit(40f, 40f);
        govLogo.setAbsolutePosition((PageSize.A4.getWidth()-govLogo.getScaledWidth())/2,PageSize.A4.getHeight() - govLogo.getScaledHeight()-40f);
        Paragraph p3 = new Paragraph("The Republic of Macedonia",FontFactory.getFont(FontFactory.HELVETICA,14,BaseColor.BLACK));
        p3.setAlignment(Element.ALIGN_CENTER);
        document.add(p);
        document.add(p1);
        document.add(govLogo);
        document.add(p3);

        
        /*Creating the body of the page*/
        /*A4 has height of 842 pt and width of 595pt*/
        /*I want the images to start at 1/5 height from the top of the page, so i multiply 4/5 X 842 = 674 approximately*/
        Image uistLogo = Image.getInstance("imagesImportant\\uist_macedonia.png");
        uistLogo.scaleToFit(100f, 100f);
        uistLogo.setAbsolutePosition((PageSize.A4.getWidth()-uistLogo.getScaledWidth())/5,574f);
        Paragraph p4 = new Paragraph("University of Information Science and Technology",FontFactory.getFont(FontFactory.HELVETICA,10,BaseColor.BLACK));
        Paragraph p5 = new Paragraph("\"St. Paul the Apostle\" - Ohrid",FontFactory.getFont(FontFactory.HELVETICA,10,BaseColor.BLACK));
        
        
        /*Logo for faculty + accompany text
        ############################################
        */

        Image facultyLogo = Image.getInstance("imagesImportant\\uist_macedonia.png");
        
        String facultyname = "";
        Paragraph p6 = new Paragraph();
        /*##########################################*/
        switch(faculty){
            case "CSE":
                facultyLogo = Image.getInstance("imagesImportant\\cselogo.png");
                facultyLogo.scaleToFit(100f,100f); 
                facultyLogo.setAbsolutePosition(4*(PageSize.A4.getWidth()-facultyLogo.getScaledWidth())/5,574f);
                facultyname = "Faculty of Computer Science and Engineering, Ohrid";
                p6 = new Paragraph(facultyname,FontFactory.getFont(FontFactory.HELVETICA,10,BaseColor.BLACK));
                p6.setAlignment(Element.ALIGN_RIGHT);
                
                p6.setPaddingTop(PageSize.A4.getHeight()+574);
                break;
             case "CNS":
                facultyLogo = Image.getInstance("imagesImportant\\cnslogo.png");
                facultyLogo.scaleToFit(100f,100f); 
                facultyLogo.setAbsolutePosition(4*(PageSize.A4.getWidth()-facultyLogo.getScaledWidth())/5,574f);
                facultyname = "Faculty of Computer Network and Security, Ohrid";
                p6 = new Paragraph(facultyname,FontFactory.getFont(FontFactory.HELVETICA,10,BaseColor.BLACK));
                p6.setAlignment(Element.ALIGN_RIGHT);
                break;
             case "ISVMA":
                facultyLogo = Image.getInstance("imagesImportant\\isvmalogo.png");
                facultyLogo.scaleToFit(100f,100f); 
                facultyLogo.setAbsolutePosition(4*(PageSize.A4.getWidth()-facultyLogo.getScaledWidth())/5,574f);
                facultyname = "Faculty of Information Systems, Visualisation, Multimedia and Animation, Ohrid";
                p6 = new Paragraph(facultyname,FontFactory.getFont(FontFactory.HELVETICA,10,BaseColor.BLACK));
                p6.setAlignment(Element.ALIGN_RIGHT);
                break;
             case "MIR":
                facultyLogo = Image.getInstance("imagesImportant\\mirlogo.png");
                facultyLogo.scaleToFit(100f,100f); 
                facultyLogo.setAbsolutePosition(4*(PageSize.A4.getWidth()-facultyLogo.getScaledWidth())/5,574f);
                facultyname = "Faculty of Machine Intelligence and Robotics, Ohrid";
                p6 = new Paragraph(facultyname,FontFactory.getFont(FontFactory.HELVETICA,10,BaseColor.BLACK));
                p6.setAlignment(Element.ALIGN_RIGHT);
                break;                 
       }      
       
        p4.setSpacingBefore(180);

        document.add(uistLogo);
        document.add(p4);
        document.add(p5);
        
        document.add(facultyLogo);
        document.add(p6);
        
        document.add(new Paragraph(" "));
        document.add(new Paragraph(" "));
        document.add(new Paragraph(" "));   
        
        Paragraph p7 = new Paragraph("File number: "+student_id+"");
        p7.setAlignment(Element.ALIGN_RIGHT);
        Paragraph p8 = new Paragraph("The Faculty of Science and Engineering in Ohrid issues Certificate");
        p8.setAlignment(Element.ALIGN_CENTER);
        Paragraph p9 = new Paragraph(forpassedExams);
        p9.setAlignment(Element.ALIGN_CENTER);
        
        document.add(p7);
        document.add(new Paragraph(" "));
        document.add(new Paragraph(Lawarticle));        
        document.add(new Paragraph(" "));
        document.add(p8);
        document.add(new Paragraph(" "));
        document.add(p9);
        document.add(new Paragraph(" "));
        document.add(new Paragraph(" "));
        
        
        String names = first_name + " "+ middle_name + " "+ last_name ;
        String dob_place_country = extractDayofBirth +"."+extractMonthofBirth+"." +extractYearofBirth+ ", "+country;
        int admission2 = Integer.parseInt(getYearAdmission) + 1;  
        String academic_year = getYearAdmission + "/" + admission2;
        String facultyName = "";
        String facultyNameShort = "";
        
        switch(faculty){
            case "CSE":
                facultyName = "Computer Science and Engineering at the Faculty of Computer Science and Engineering";
                facultyNameShort = "Faculty of Computer Science and Engineering";
                Paragraph p10 = new Paragraph(studyProgram(facultyName, facultyNameShort, names, student_id,dob_place_country, nationality, academic_year));
                document.add(p10);
                break;
            case "CNS":
                facultyName = "Computer Network and Security at the Faculty of Computer Network and Security";
                facultyNameShort = "Faculty of Computer Network and Security";
                Paragraph p11 = new Paragraph(studyProgram(facultyName, facultyNameShort, names, student_id,dob_place_country, nationality, academic_year));
                document.add(p11);                
                break;
            case "ISVMA":
                facultyName = "Information Systems, Visualisation, Multimedia and Animation at the Faculty of Information Systems, "
                        + "Visualisation, Multimedia and Animation";
                facultyNameShort = "Faculty of Information Systems, Visualisation, Multimedia and Animation";
                Paragraph p12 = new Paragraph(studyProgram(facultyName, facultyNameShort, names, student_id,dob_place_country, nationality, academic_year));
                document.add(p12);                
                break;  
            case "MIR":
                facultyName = "Machine Intelligence and Robotics at the Faculty of Machine Intelligence and Robotics ";                      
                facultyNameShort = "Faculty of Machine Intelligence and Robotics";
                Paragraph p13 = new Paragraph(studyProgram(facultyName, facultyNameShort, names, student_id,dob_place_country, nationality, academic_year));
                document.add(p13);                
                break;                
        }
        
        
        
        document.newPage();
        /*This Creates the next Page*/
        //document.add(new Paragraph("Hello World"));
            //To add font size, color and title
            
            /*document.add(new Paragraph(new java.util.Date().toString()));
            document.add(new Paragraph("-------------------------------------------------------------"));
            */
            document.add(new Paragraph("During the studies, the student passed the following exams:"));
             document.add(new Paragraph(" "));
                 
        /*Create a table for adding cell column*/
            PdfPTable table = new PdfPTable(8);
            PdfPCell cell1 = new PdfPCell(new Paragraph("Item number"));
            table.addCell(cell1);
            PdfPCell cell2 = new PdfPCell(new Paragraph("Code"));
            table.addCell(cell2);
            PdfPCell cell3 = new PdfPCell(new Paragraph("Title of the course"));
            table.addCell(cell3);
            PdfPCell cell4 = new PdfPCell(new Paragraph("Number of Classes"));
            table.addCell(cell4);
            PdfPCell cell5 = new PdfPCell(new Paragraph("Grade(In numbers)"));
            table.addCell(cell5);
            PdfPCell cell6 = new PdfPCell(new Paragraph("Grade(In words)"));
            table.addCell(cell6);
            PdfPCell cell7 = new PdfPCell(new Paragraph("ECTS credits"));
            table.addCell(cell7);
            PdfPCell cell8 = new PdfPCell(new Paragraph("Status of the course(Mandatory/Elective)"));
            table.addCell(cell8);
           

           /*#################################################################################
                Get the count of the subject, this count would be needed to loop through the database getting values 
               from grade
            
            */
           
           try{
           
               String getCount = "SELECT COUNT(student_admission_number) FROM grade WHERE student_admission_number = '"+admission_number+"'";
                pst = conn.prepareStatement(getCount);
                rs = pst.executeQuery(); 
                if(rs.next()){
                   countSubject = rs.getInt("COUNT(student_admission_number)");
                }
           }catch(Exception e){
           
           }
             /*#################################################################################*/
           /*Get subjects for a particular Student*/
           String getStudentCourses = null;         
           getStudentCourses = "SELECT course_code,course_name, grade_num, grade_words FROM grade WHERE student_admission_number ="
                   + " '"+admission_number+"'";
           int i = 1;
           try{
               pst = conn.prepareStatement(getStudentCourses);
               rs = pst.executeQuery();
               while(rs.next() && i<=countSubject){                  
                   /*for(int i = 1; i<=countSubject; i++){     */                
                     table.addCell(""+i);
                     table.addCell(rs.getString("course_code"));
                     table.addCell(rs.getString("course_name"));
                     table.addCell("3+2");
                     table.addCell(rs.getString("grade_num"));
                     table.addCell(rs.getString("grade_words"));
                     table.addCell("6");
                     table.addCell("M");                    
                   i++;
               }
           }catch(Exception e){
           }
      
             int credit = countSubject * 6;
             String forResultReport = "The student passed "+countSubject+"(number of passed exams) from the total number "
                        + "of exams provided by the curriculum with number "
                    + "40(total number of exams of the study program) and gained "+credit+" ECTS credits";
              String transcriptGrade = "The transcript of grades is exempt from payment of administrative fees under Article 19, item 1 of the law "
                      + "for administrative fees";
            String rector = "Rector";
            String rectorName = "Professor Ninoslav Marina";
            String preparedBy = "Prepared by Jasmina Andonoska";
            String verifiedBy = "Verified by Elena Naumova";
            String approvedBy = "Approved by Ivan Bimbilovski";
            
            document.add(table); 
            document.add(new Paragraph("Partially implemented curriculum:",FontFactory.getFont(FontFactory.TIMES_BOLD,12,Font.BOLD,BaseColor.BLACK)));            
            document.add(new Paragraph(forResultReport)); 
            document.add(new Paragraph(rector));
            document.add(new Paragraph(" "));
            document.add(new Paragraph(" "));
            document.add(new Paragraph("_______________________"));
            document.add(new Paragraph(rectorName));
            document.add(new Paragraph(" "));
            document.add(new Paragraph(preparedBy));
            document.add(new Paragraph(verifiedBy));
            document.add(new Paragraph(approvedBy));
            document.add(new Paragraph(" "));
            document.add(new Paragraph(transcriptGrade));
            
            document.close();
            JOptionPane.showMessageDialog(null, "Report Generated");
        }catch(Exception e)   {
            JOptionPane.showMessageDialog(null, "Select the student to view report from the select student view");
        }
        
        
    }//GEN-LAST:event_btn_reportGradeActionPerformed

    private void btn_deleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_deleteActionPerformed
        
        if(!txt_student_id.getText().isEmpty()){
            int id = Integer.parseInt(txt_student_id.getText());
            String admission_number = txt_admission_number.getText();
            
            int reply = JOptionPane.showConfirmDialog(null, "Teacher with name '"+txt_fn.getText()+"' '"+txt_ln.getText()+"' will be deleted"
                        + "", "Confirm delete", JOptionPane.YES_NO_OPTION);
            if (reply == JOptionPane.YES_OPTION) {            
            try{
             String sql1 = "DELETE FROM students WHERE id='"+id+"'";
             pst = conn.prepareStatement(sql1);
             pst.execute();
             clear_fields();
             JOptionPane.showMessageDialog(null, "Data deleted");
            }catch(Exception e){
             JOptionPane.showMessageDialog(null,e);
            }

            try{
                String sql1 = "DELETE FROM enrol_student WHERE student_admission_number='"+admission_number+"'";
                pst = conn.prepareStatement(sql1);
                pst.execute();
            }catch(Exception e){}
            try{
                String sql1 = "DELETE FROM mid12 WHERE admission_number='"+admission_number+"'";
                pst = conn.prepareStatement(sql1);
                pst.execute();
            }catch(Exception e){} 
            
           
            try{                
                String sql1 = "DELETE FROM grade WHERE student_admission_number='"+admission_number+"'";
                pst = conn.prepareStatement(sql1);
                pst.execute();
            }catch(Exception e){
            
            }
        }
            Update_table();
        }
    }//GEN-LAST:event_btn_deleteActionPerformed

    private void student_tableKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_student_tableKeyReleased
        if(evt.getKeyCode() == java.awt.event.KeyEvent.VK_DOWN || evt.getKeyCode() == java.awt.event.KeyEvent.VK_UP){
            tableStudentClickOrReleased();
            txt_username.setBackground(Color.WHITE);
            txt_password.setBackground(Color.WHITE);
            txt_email.setBackground(Color.WHITE);
        }
    }//GEN-LAST:event_student_tableKeyReleased

    private void btn_chartActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_chartActionPerformed
        ChartStudentSelect chart = new ChartStudentSelect();
        chart.setLocationRelativeTo(null);
        chart.setVisible(true);        
    }//GEN-LAST:event_btn_chartActionPerformed

    private void btn_sendMsgActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_sendMsgActionPerformed
        
        String to = txt_to.getText();
        String subject = txt_subject.getText();
        String bcc = txt_bcc.getText();
        String msgBox = txArea_msg.getText();
        String From = "egbeeyongjavamail@gmail.com";
        String attachFileName = txt_attachment_name.getText(); 
            
        
        Properties props = new Properties();
        props.put("mail.smtp.host","smtp.gmail.com");
        props.put("mail.smtp.socketFactory.port", "587");  
        props.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth","true");
        props.put("mail.smtp.port","587");
        
        props.put("mail.debug", "true"); 
        
        props.put("mail.smtp.starttls.enable", "true"); 
        props.put("mail.smtp.socketFactory.fallback", "true");
        
        try{
        MailSSLSocketFactory sf=new MailSSLSocketFactory();
 
        sf.setTrustAllHosts(true);
        props.put("mail.smtp.ssl.socketFactory", sf);
        }
        catch(Exception ex)
        {
          ex.printStackTrace();
        }
              
        Session session = Session.getDefaultInstance(props,
                          
                          new javax.mail.Authenticator() {
                              protected PasswordAuthentication getPasswordAuthentication(){
                                  return new PasswordAuthentication("egbeeyongjavamail@gmail.com", "XpWQOr_5*45?");
                              }
                          }
                );
        try{
   
            
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(From));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            message.setRecipients(Message.RecipientType.CC, InternetAddress.parse(bcc));
            
            message.setSubject(subject);
            
            /*Attachment code*/
            MimeBodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setText(msgBox);
            Multipart multipart = new MimeMultipart();
            
            multipart.addBodyPart(messageBodyPart);
            
            messageBodyPart = new MimeBodyPart();
            javax.activation.DataSource source = new FileDataSource(attachFileEmail_path);
            messageBodyPart.setDataHandler(new DataHandler(source));
            messageBodyPart.setFileName(attachFileName);
            multipart.addBodyPart(messageBodyPart);
            message.setContent(multipart);
            
            Transport.send(message);
              
            JOptionPane.showMessageDialog(null, "Messsage sent");
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    
        
        
    }//GEN-LAST:event_btn_sendMsgActionPerformed

    private void btn_msgattachActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_msgattachActionPerformed
        JFileChooser chooser = new JFileChooser();

        int result = chooser.showSaveDialog(null);
        if(result == JFileChooser.APPROVE_OPTION){
        File f = chooser.getSelectedFile();
        attachFileEmail_path = f.getAbsolutePath();
        
        txt_attahedmsgpath.setText(attachFileEmail_path);
        }else{
        
        }
    }//GEN-LAST:event_btn_msgattachActionPerformed

    private void txt_attachment_nameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_attachment_nameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_attachment_nameActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        JFileChooser chooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("*.IMAGE", "jpg","gif","png");
        chooser.addChoosableFileFilter(filter);
        //int result = fileChooser.showSaveDialog(null);
        
        int result = chooser.showSaveDialog(null);
        if(result == JFileChooser.APPROVE_OPTION){
        File f = chooser.getSelectedFile();
        fileprofile_pic = f.getAbsolutePath();
        txt_file_attach_path.setText(fileprofile_pic);

        ImageIcon imageIcon = new ImageIcon(fileprofile_pic); // load the image to a imageIcon
        java.awt.Image image1 = imageIcon.getImage(); // transform it 
	java.awt.Image newimg = image1.getScaledInstance(100, 100,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
        imageIcon = new ImageIcon(newimg);  // transform it back
        
        
        lbl_pic.setIcon(imageIcon);
        try{

            File image = new File(fileprofile_pic);
            FileInputStream fis = new FileInputStream(image);
            
            ByteArrayOutputStream bos = new ByteArrayOutputStream(); //converting the file to the right format
            byte[] buf = new byte[1024]; //giving it a buffer size
            for(int readNum; (readNum = fis.read(buf))!= -1;){
                bos.write(buf, 0, readNum);
            }
            
           profile_image = bos.toByteArray();
            
        }catch(Exception e){
          JOptionPane.showMessageDialog(null, e);
        }
       }else{
        
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void btn_logoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_logoutActionPerformed
        close1();
    }//GEN-LAST:event_btn_logoutActionPerformed

    private void btn_closeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_closeActionPerformed
        close2();
    }//GEN-LAST:event_btn_closeActionPerformed

    private void btn_print_gradeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_print_gradeActionPerformed
              if(!txt_student_studentID_add_score.getText().isEmpty()){
                                MessageFormat header = new MessageFormat("Grades for "+ txt_student_fname_add_score + " "+txt_student_lname_add_score);
				MessageFormat footer = new MessageFormat("Page{0,number,integer}");
				
				try{
					student_grade_table.print(JTable.PrintMode.NORMAL,header,footer);// print the table
					
					
				}catch(java.awt.print.PrinterException e){
					
					System.err.format("Cannot print %s %n", e.getMessage());
				}
              }else{
                        MessageFormat header = new MessageFormat("Grades for all students and courses");
		        MessageFormat footer = new MessageFormat("Page{0,number,integer}");
				
		        try{
			    student_grade_table.print(JTable.PrintMode.NORMAL,header,footer);// print the table
	
			   }catch(java.awt.print.PrinterException e){					
					System.err.format("Cannot print %s %n", e.getMessage());
			   }             
              }
                                
    }//GEN-LAST:event_btn_print_gradeActionPerformed

    private void btn_ireportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_ireportActionPerformed
        
        if(combo_searchCategory.getSelectedIndex() == 0){
        try{
         /*String report = "C:\\Users\\michel\\Documents\\NetBeansProjects\\SchoolManagementSystem\\IReports\\Student List.jrxml";
         JasperReport jr = JasperCompileManager.compileReport(report);
         JasperPrint jp = JasperFillManager.fillReport(jr, null, conn);
         JasperViewer.viewReport(jp);
         */
         
            JasperDesign jdesign = JRXmlLoader.load("C:\\Users\\michel\\Documents\\NetBeansProjects\\SchoolManagementSystem\\IReports\\Student List.jrxml");
            String sql = "SELECT first_name AS 'First Name', middle_name AS 'Middle Name', "
                       + "last_name AS 'Last Name', Faculty,  year_of_studies AS 'Study Year', "
                       + "email AS 'Email' FROM students ORDER BY first_name ASC";
            JRDesignQuery newQuery = new JRDesignQuery();
            newQuery.setText(sql);
            jdesign.setQuery(newQuery);
            JasperReport jreport = JasperCompileManager.compileReport(jdesign);
            JasperPrint jprint = JasperFillManager.fillReport(jreport, null, conn);
            JasperViewer.viewReport(jprint);

         
        
        }catch(Exception e){
         JOptionPane.showMessageDialog(null,e);
        }       
        }else{
        try{        
            JasperDesign jdesign = JRXmlLoader.load("C:\\Users\\michel\\Documents\\NetBeansProjects\\SchoolManagementSystem\\IReports\\Faculty.jrxml");
            String sql = "SELECT first_name AS 'First Name', middle_name AS 'Middle Name', "
                       + "last_name AS 'Last Name',  year_of_studies AS 'Study Year', email AS 'Email'"
                       + " FROM students WHERE Faculty = '"+combo_searchCategory.getSelectedItem().toString()+"' ORDER BY first_name ASC";
            JRDesignQuery newQuery = new JRDesignQuery();
            newQuery.setText(sql);
            jdesign.setQuery(newQuery);
            JasperReport jreport = JasperCompileManager.compileReport(jdesign);
            JasperPrint jprint = JasperFillManager.fillReport(jreport, null, conn);
            JasperViewer.viewReport(jprint);           
        }catch(Exception e){
         JOptionPane.showMessageDialog(null,e);
        }       
        }
    }//GEN-LAST:event_btn_ireportActionPerformed

    private void btn_iReport_gradeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_iReport_gradeActionPerformed
       if(!txt_student_studentID_add_score.getText().isEmpty()){
        try{    
        JasperDesign jdesign = JRXmlLoader.load("C:\\Users\\michel\\Documents\\NetBeansProjects\\SchoolManagementSystem\\IReports\\GradeScore.jrxml");
            String sql = "SELECT course_name,score, grade_num, grade_words FROM grade WHERE student_admission_number = '"+txt_student_studentID_add_score.getText()+"'";
            JRDesignQuery newQuery = new JRDesignQuery();
            newQuery.setText(sql);
            jdesign.setQuery(newQuery);
            JasperReport jreport = JasperCompileManager.compileReport(jdesign);
            JasperPrint jprint = JasperFillManager.fillReport(jreport, null, conn);
            JasperViewer.viewReport(jprint);
        }catch(Exception e){}
    }else{
        }     
    }//GEN-LAST:event_btn_iReport_gradeActionPerformed

    private void student_grade_tableKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_student_grade_tableKeyReleased
    if(evt.getKeyCode() == java.awt.event.KeyEvent.VK_DOWN || evt.getKeyCode() == java.awt.event.KeyEvent.VK_UP){
        gradeFilled();
    }
    }//GEN-LAST:event_student_grade_tableKeyReleased

    private void student_grade_tableKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_student_grade_tableKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_student_grade_tableKeyTyped
   private void gradeFilled(){
         int rowResult = student_grade_table.getSelectedRow();
         String result_table_click = (student_grade_table.getModel().getValueAt(rowResult, 0).toString());
                                                             
                      
       try{
         		String sql = "SELECT * FROM grade WHERE grade_id ='"+result_table_click+"'";
			pst = conn.prepareStatement(sql);
			rs= pst.executeQuery();
                        if(rs.next()){
                            String score = rs.getString("score");
                            txt_grade_score.setText(score);  
                            String course_name = rs.getString("course_name");
                            txt_subject_update.setText(course_name);                           
                        }
                        
       }catch(Exception e){
           JOptionPane.showMessageDialog(null, e);
       }
      
   } 
    
   
    private void clear_fields(){
            lbl_pic.setText("");
            txt_fn.setText("");
            txt_ln.setText("");
            txt_mn.setText("");
            txt_phone_number.setText("");
            txt_address.setText("");
            txt_email.setText("");
            tArea_student_comment.setText("");
            txt_username.setText("");
            txt_password.setText("");
            txt_file_attach_path.setText("");
            txt_student_id.setText("");
            txt_admission_number.setText("");
            txt_searchCategory.setText("");
            ((JTextField)txt_admission_date.getDateEditor().getUiComponent()).setText("");
            ((JTextField)txt_date_of_birth.getDateEditor().getUiComponent()).setText("");            
            combo_searchCategory.setSelectedIndex(0);
            combo_gender.setSelectedIndex(0);
            combo_faculty.setSelectedIndex(0);
            combo_year_of_studies.setSelectedIndex(0);
            combo_country_of_birth.setSelectedIndex(0);
            combo_nationality_list.setSelectedIndex(0);
            combo_status.setSelectedIndex(0);
            combo_full_part_time.setSelectedIndex(0);   
            lbl_pic.setIcon(null);
            txt_file_attach_path.setText("");
            txt_username.setBackground(Color.WHITE);  
            txt_email.setBackground(Color.WHITE); 
            txt_password.setBackground(Color.WHITE); 
            buttonGroup2.clearSelection();
                           
    }
     private void clear_fields1(){
            txt_fn1.setText("");
            txt_ln1.setText("");
            txt_mn1.setText("");
            txt_phone_number1.setText("");
            txt_address1.setText("");
            txt_email1.setText("");
            tArea_student_comment1.setText("");
            txt_username1.setText("");
            txt_password1.setText("");
            txt_file_attach_path1.setText("");  
            buttonGroup2.clearSelection();
            ((JTextField)txt_admission_date1.getDateEditor().getUiComponent()).setText("");
            ((JTextField)txt_date_of_birth1.getDateEditor().getUiComponent()).setText("");            
            combo_gender.setSelectedIndex(0);
            combo_faculty1.setSelectedIndex(0);
            combo_year_of_studies1.setSelectedIndex(0);
            combo_country_of_birth1.setSelectedIndex(0);
            combo_nationality_list1.setSelectedIndex(0);
            combo_regular_part1.setSelectedIndex(0); 
            lbl_pic1.setIcon(null);
            txt_username1.setBackground(Color.WHITE);  
            txt_email1.setBackground(Color.WHITE); 
            txt_password1.setBackground(Color.WHITE); 
    } 
   private void tableStudentClickOrReleased(){
        int rowStudent = student_table.getSelectedRow();
       String student_table_click = (student_table.getModel().getValueAt(rowStudent, 0).toString());
                                                            
                      
       try{
         		String sql = "SELECT * FROM students WHERE id ='"+student_table_click+"'";
			pst = conn.prepareStatement(sql);
			rs= pst.executeQuery();
                        if(rs.next()){
                            String id = rs.getString("id");
                            txt_student_id.setText(id);
                            String fn = rs.getString("first_name");
                            txt_fn.setText(fn);
                            String ln = rs.getString("last_name");
                            txt_ln.setText(ln);
                            String mn = rs.getString("middle_name");
                            txt_mn.setText(mn);
                            String nationality = rs.getString("nationality");
                            combo_nationality_list.setSelectedItem(nationality);                           
                            String email = rs.getString("email");
                            txt_email.setText(email);   
                            String address = rs.getString("address");
                            txt_address.setText(address);
                            String YOS = rs.getString("year_of_studies");
                            combo_year_of_studies.setSelectedItem(YOS);
                            String phone_number = rs.getString("phone_number");
                            txt_phone_number.setText(phone_number);                            
                            String regular_part_time_student = rs.getString("regular_or_part_time");
                            combo_full_part_time.setSelectedItem(regular_part_time_student);
                            String status_of_student = rs.getString("status");
                            combo_status.setSelectedItem(status_of_student);
                            String admission_number = rs.getString("admission_number");
                            txt_admission_number.setText(admission_number);
                            Date admission_date = rs.getDate("admission_date");
                            txt_admission_date.setDate(admission_date);
                            String mother_name = rs.getString("password");
                            txt_password.setText(mother_name);
                            String father_name = rs.getString("username");
                            txt_username.setText(father_name);
                            String gender = rs.getString("gender");
                            combo_gender.setSelectedItem(gender);
                            String comment = rs.getString("comment");
                            tArea_student_comment.setText(comment);
                            String faculty = rs.getString("faculty");
                            combo_faculty.setSelectedItem(faculty);
                            String country_of_birth = rs.getString("country_of_birth");
                            combo_country_of_birth.setSelectedItem(country_of_birth);
                            Date Dob = rs.getDate("date_of_birth");
                            txt_date_of_birth.setDate(Dob);       
                            txt_username1.setBackground(Color.WHITE);  
                            txt_email1.setBackground(Color.WHITE); 
                            txt_password1.setBackground(Color.WHITE);  
  
                            //Setting the image profile to the right image                            
                            byte[] imageData = rs.getBytes("photo");
                            InputStream input = rs.getBinaryStream("photo"); 
                            byte[] buffer = new byte[1024];
                            if(input.read(buffer) <= 0){
                              lbl_pic.setIcon(null);
                            }
                            /*if(imageData.length ==0){
                              lbl_pic.setIcon(null);
                            }else{*/
                            
                            
                            else{
                            
                            ImageIcon imageIcon = new ImageIcon(imageData); // load the image to a imageIcon
                            java.awt.Image image1 = imageIcon.getImage(); // transform it 
                            java.awt.Image newimg = image1.getScaledInstance(100, 100,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
                            //imageIcon = new ImageIcon(newimg);  
                            
                            studentPicFormat = new ImageIcon(newimg); // transform it back
                            lbl_pic.setIcon(studentPicFormat);
                        }
                            
                            //}                            
                        }
                        
       }catch(Exception e){
           //JOptionPane.showMessageDialog(null, e);
       }  
   }
    public void close1(){
         //new AdminPanel().setVisible(false);
         //new AdminPanel().setDefaultCloseOperation(HIDE_ON_CLOSE);
         close();
         Login frame = new Login();
         frame.setLocationRelativeTo(null);
         frame.setVisible(true);
         
    }
   
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_add_result;
    private javax.swing.JButton btn_chart;
    private javax.swing.JButton btn_clear;
    private javax.swing.JButton btn_clear1;
    private javax.swing.JButton btn_close;
    private javax.swing.JButton btn_delete;
    private javax.swing.JButton btn_iReport_grade;
    private javax.swing.JButton btn_ireport;
    private javax.swing.JButton btn_logout;
    private javax.swing.JButton btn_msgattach;
    private javax.swing.JButton btn_print;
    private javax.swing.JButton btn_print_grade;
    private javax.swing.JButton btn_reportGrade;
    private javax.swing.JButton btn_sendMsg;
    private javax.swing.JButton btn_update;
    private javax.swing.JButton btn_update1;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;
    private javax.swing.JComboBox<String> combo_country_of_birth;
    private javax.swing.JComboBox<String> combo_country_of_birth1;
    private javax.swing.JComboBox<String> combo_faculty;
    private javax.swing.JComboBox<String> combo_faculty1;
    private javax.swing.JComboBox<String> combo_full_part_time;
    private javax.swing.JComboBox<String> combo_gender;
    private javax.swing.JComboBox<String> combo_nationality_list;
    private javax.swing.JComboBox<String> combo_nationality_list1;
    private javax.swing.JComboBox<String> combo_regular_part1;
    private javax.swing.JComboBox<String> combo_searchCategory;
    private javax.swing.JComboBox<String> combo_status;
    private javax.swing.JComboBox<String> combo_student_status1;
    private javax.swing.JComboBox<String> combo_subject_for_marks;
    private javax.swing.JComboBox<String> combo_year_of_studies;
    private javax.swing.JComboBox<String> combo_year_of_studies1;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JDesktopPane jDesktopPane1;
    private javax.swing.JDesktopPane jDesktopPane2;
    private javax.swing.JFileChooser jFileChooser1;
    private javax.swing.JFileChooser jFileChooser2;
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
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel47;
    private javax.swing.JLabel jLabel48;
    private javax.swing.JLabel jLabel49;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel50;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel19;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel20;
    private javax.swing.JPanel jPanel21;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JLabel lbl_names;
    private javax.swing.JLabel lbl_pic;
    private javax.swing.JLabel lbl_pic1;
    private javax.swing.JRadioButton rbtn_female;
    private javax.swing.JRadioButton rbtn_male;
    private javax.swing.JTextField studentAddResultNames;
    private javax.swing.JTable student_grade_table;
    private javax.swing.JComboBox<String> student_name_result;
    private javax.swing.JTable student_table;
    private javax.swing.JTextArea tArea_student_comment;
    private javax.swing.JTextArea tArea_student_comment1;
    private javax.swing.JTextArea txArea_msg;
    private javax.swing.JTextField txt_address;
    private javax.swing.JTextField txt_address1;
    private com.toedter.calendar.JDateChooser txt_admission_date;
    private com.toedter.calendar.JDateChooser txt_admission_date1;
    private javax.swing.JTextField txt_admission_number;
    private javax.swing.JTextField txt_attachment_name;
    private javax.swing.JTextField txt_attahedmsgpath;
    private javax.swing.JTextField txt_bcc;
    private javax.swing.JLabel txt_date;
    private com.toedter.calendar.JDateChooser txt_date_of_birth;
    private com.toedter.calendar.JDateChooser txt_date_of_birth1;
    private javax.swing.JTextField txt_email;
    private javax.swing.JTextField txt_email1;
    private javax.swing.JTextField txt_file_attach_path;
    private javax.swing.JTextField txt_file_attach_path1;
    private javax.swing.JTextField txt_fn;
    private javax.swing.JTextField txt_fn1;
    private javax.swing.JTextField txt_grade_score;
    private javax.swing.JTextField txt_ln;
    private javax.swing.JTextField txt_ln1;
    private javax.swing.JTextField txt_mn;
    private javax.swing.JTextField txt_mn1;
    private javax.swing.JTextField txt_password;
    private javax.swing.JPasswordField txt_password1;
    private javax.swing.JTextField txt_phone_number;
    private javax.swing.JTextField txt_phone_number1;
    private javax.swing.JTextField txt_searchCategory;
    private javax.swing.JTextField txt_student_fname_add_score;
    private javax.swing.JTextField txt_student_id;
    private javax.swing.JTextField txt_student_lname_add_score;
    private javax.swing.JTextField txt_student_mark;
    private javax.swing.JTextField txt_student_studentID_add_score;
    private javax.swing.JTextField txt_subject;
    private javax.swing.JTextField txt_subject_update;
    private javax.swing.JLabel txt_time;
    private javax.swing.JTextField txt_to;
    private javax.swing.JTextField txt_username;
    private javax.swing.JTextField txt_username1;
    private javax.swing.JButton update_score;
    // End of variables declaration//GEN-END:variables


    String Lawarticle ="According to article 116 from the law for higher education \"Official Gazettee of the Republic of Macedonia\" "
            + "no. 35/08, 103/08 and 26/09 and article 201 from the statute of the University for Information Science and Technology in Ohrid.";
    
    String forpassedExams = "For passed exams in undergraduate studies";
     
   private String studyProgram(String facultyName,String facultyShort,String names,int ID, String DOB,String citizenship,String dateEnrolled){
     String studyProgram = "Study program: 4 year studies in "+facultyName+" at the University of Information Science and Technology"
             + " \"St. Paul the Apostle\"- Ohrid \n"
             + "Course/department/module: "+facultyShort+" \n"
             + "Name, middle name and surname: "+names+"\n"
             + "ID number: "+ID+"\n"
             + "Date,nationality and country of birth: "+DOB+"\n"
             + "Citizenship: "+citizenship+"\n"
             + "Academic year of study enrollment: "+dateEnrolled+"";
            
             
     return studyProgram;
   }
   
    String attachFileEmail_path;
    
    
    String fileprofile_pic = null;
    
    byte[] profile_image1 = null;
    
    String file_name_profile_pic = null;
    
    byte[] profile_image = null;   

    String gender1 = null;
    boolean emailStatus = false;
    private ImageIcon studentPicFormat = null; 
    
}
