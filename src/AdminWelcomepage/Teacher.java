/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AdminWelcomepage;

import com.sun.glass.events.KeyEvent;
import connection.DBConnection;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.WindowEvent;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import static java.lang.Thread.sleep;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.MessageFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import static jdk.nashorn.internal.objects.NativeString.trim;
import net.proteanit.sql.DbUtils;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JRDesignQuery;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;
import schoolmanagementsystem.Login;


public class Teacher extends javax.swing.JFrame {
     Connection conn = null;
     PreparedStatement pst = null;
     PreparedStatement pst1 = null;
     ResultSet rs = null;
     ResultSet rs1 = null;
    String username = null;
    String id = null;
    public Teacher(String username1, String id1) {
        initComponents();
        conn = DBConnection.connectDB();
        
        //Update_table();
        fill_list_box();
        fill_teacher_name();
        fill_course_name();
        fill_courses_table();
        fill_course_name_reassign();
        currentDate();
        
        username = username1;
        id = id1;
        getNames(username1, id1);
        
        
        
       // jScrollPane3.setVisible(false);
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
    public void fill_list_box(){
        DefaultListModel dlm = new DefaultListModel();
        
        JTable table = new JTable();
        String [] column_names = {"","","","",""};
        int countTeachers = 0;
        try{
         String sql1 = "SELECT COUNT(id) FROM teachers";
         String sql = "SELECT * FROM teachers ORDER BY first_name ASC";
         pst = conn.prepareStatement(sql);
         rs = pst.executeQuery();
         if(rs.next()){
          countTeachers = rs.getInt("COUNT(first_name)");
         }
        }catch(Exception e){
        }
        try{
         String sql = "SELECT * FROM teachers ORDER BY first_name ASC";
         pst = conn.prepareStatement(sql);
         rs = pst.executeQuery();
        while(rs.next()){
             String first_name = rs.getString("first_name");
             String middle_name = rs.getString("middle_name");
             String last_name = rs.getString("last_name");
             String country = rs.getString("nationality");
             String username = rs.getString("user_name");
             if(middle_name.length() == 0){
                 
              dlm.addElement(first_name +" "+last_name);
              
              
             }else{
              
              dlm.addElement(first_name + "  "+middle_name+" "+last_name);
              
             }
             jList1.setFont( new Font("monospaced", Font.PLAIN, 12) );
             jList1.setModel(dlm);
            
             
         
         }
        }catch(Exception e){
            //JOptionPane.showMessageDialog(null,e);
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
    private void fill_list_box_courses(){
        DefaultListModel dlm = new DefaultListModel();
  
        try{
         String sql = "SELECT * FROM assign_courses WHERE teacher_id = '"+teacher_id1.getText()+"'";
         pst = conn.prepareStatement(sql);
         rs = pst.executeQuery();
        while(rs.next()){
             String course_names = rs.getString("course_name");
 
              dlm.addElement(course_names);
              
             }
             //jList2.setFont( new Font("monospaced", Font.PLAIN, 12) );
             jList2.setModel(dlm);
        }catch(Exception e){
        }
    }
    private void fill_teacher_name(){
     try{
         String sql = "SELECT * FROM teachers ORDER by first_name ASC";
         pst= conn.prepareStatement(sql);
         rs = pst.executeQuery();
           combo_teacher_name.addItem(" ");  
           combo_teaching_assistant.addItem(" ");
           combo_teacher_reassign.addItem(" ");
           combo_assistant_name_reassign.addItem(" ");
           
           
          while(rs.next()){
              String fn = rs.getString("first_name");
              String ln = rs.getString("last_name");
              String mn = rs.getString("middle_name");
              combo_teacher_name.addItem(fn + " "+mn+" " + ln);
              combo_teaching_assistant.addItem(fn + " "+mn+" " + ln);
              combo_teacher_reassign.addItem(fn + " "+mn+" " + ln);
              combo_assistant_name_reassign.addItem(fn + " "+mn+" " + ln);
     }
     }catch(Exception e){
      JOptionPane.showMessageDialog(null, e);
     }finally{
			try{
				rs.close();
				pst.close();
			}catch(Exception e ){
			
		   }
			
		}
    }
    private void fill_course_name(){

     try{
         String sql = "SELECT * FROM courses ORDER by course_name ASC";
         pst= conn.prepareStatement(sql);
         rs = pst.executeQuery();
            combo_course_name.addItem(" ");     
          while(rs.next()){
              String fn = rs.getString("course_name");
              combo_course_name.addItem(fn);
     }
     }catch(Exception e){
      JOptionPane.showMessageDialog(null, e);
     }finally{
			try{
				rs.close();
				pst.close();
			}catch(Exception e ){
			
		   }
			
		}
    }
        private void fill_course_name_reassign(){

     try{
         String sql = "SELECT * FROM assign_courses ORDER by course_name ASC";
         pst= conn.prepareStatement(sql);
         rs = pst.executeQuery();
              course_name_reassigned.addItem(" ");   
          while(rs.next()){
              String fn = rs.getString("course_name");
              course_name_reassigned.addItem(fn);
     }
     }catch(Exception e){
      JOptionPane.showMessageDialog(null, e);
     }finally{
			try{
				rs.close();
				pst.close();
			}catch(Exception e ){
			
		   }
			
		}
    }
 private void fill_courses_table(){
     String sql = "SELECT course_name, course_code,course_status,course_year,course_semester, course_status FROM courses";
     
        try{
              pst = conn.prepareStatement(sql);
              rs = pst.executeQuery();
              course_table.setModel(DbUtils.resultSetToTableModel(rs));
        }catch(Exception e){
              JOptionPane.showMessageDialog(null, e);
        }finally{
			try{
				rs.close();
				pst.close();
			}catch(Exception e ){
			
		   }
			
		}
        
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
            java.util.logging.Logger.getLogger(Teacher.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Teacher.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Teacher.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Teacher.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                
                Teacher frame = new Teacher("username","id");
                frame.setLocationRelativeTo(null);
                frame.setTitle("UIST Management System Teacher Section");
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

        buttonGroup1 = new javax.swing.ButtonGroup();
        buttonGroup2 = new javax.swing.ButtonGroup();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        txt_address = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        txt_first_name = new javax.swing.JTextField();
        txt_email = new javax.swing.JTextField();
        rbtn_male = new javax.swing.JRadioButton();
        combo_nationality = new javax.swing.JComboBox<>();
        txt_date_birth = new com.toedter.calendar.JDateChooser();
        jLabel5 = new javax.swing.JLabel();
        txt_phone = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        rbtn_female = new javax.swing.JRadioButton();
        jLabel6 = new javax.swing.JLabel();
        txt_middlename = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txt_lastname = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        txt_admission_date = new com.toedter.calendar.JDateChooser();
        jLabel16 = new javax.swing.JLabel();
        combo_teacher_position = new javax.swing.JComboBox<>();
        jPanel3 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        txt_username = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        txt_comment = new javax.swing.JTextArea();
        jLabel12 = new javax.swing.JLabel();
        txt_password = new javax.swing.JPasswordField();
        jPanel4 = new javax.swing.JPanel();
        btn_clear = new javax.swing.JButton();
        btn_delete = new javax.swing.JButton();
        btn_save = new javax.swing.JButton();
        btn_update = new javax.swing.JButton();
        teacher_id1 = new javax.swing.JTextField();
        jButton3 = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        txt_profile_image_path = new javax.swing.JTextField();
        jDesktopPane1 = new javax.swing.JDesktopPane();
        lbel_profile_pic = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jList2 = new javax.swing.JList<>();
        teacher_first_last_names = new javax.swing.JLabel();
        jPanel10 = new javax.swing.JPanel();
        combo_search_category = new javax.swing.JComboBox<>();
        txt_search = new javax.swing.JTextField();
        jScrollPane5 = new javax.swing.JScrollPane();
        jList1 = new javax.swing.JList<>();
        jLabel18 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        txt_course_year = new javax.swing.JTextField();
        btn_assigned_course = new javax.swing.JButton();
        combo_teacher_name = new javax.swing.JComboBox<>();
        jLabel13 = new javax.swing.JLabel();
        combo_course_name = new javax.swing.JComboBox<>();
        jLabel14 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        txt_course_semester = new javax.swing.JTextField();
        jLabel28 = new javax.swing.JLabel();
        course_year = new javax.swing.JComboBox<>();
        jLabel15 = new javax.swing.JLabel();
        combo_teaching_assistant = new javax.swing.JComboBox<>();
        jPanel11 = new javax.swing.JPanel();
        combo_teacher_reassign = new javax.swing.JComboBox<>();
        combo_assistant_name_reassign = new javax.swing.JComboBox<>();
        jLabel30 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        course_name_reassigned = new javax.swing.JComboBox<>();
        combo_year_reassign = new javax.swing.JComboBox<>();
        txt_course_reassign = new javax.swing.JTextField();
        btn_reassign_course = new javax.swing.JButton();
        jPanel6 = new javax.swing.JPanel();
        jPanel9 = new javax.swing.JPanel();
        txt_course_code = new javax.swing.JTextField();
        combo_yos_course = new javax.swing.JComboBox<>();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        txt_course_name = new javax.swing.JTextField();
        jLabel25 = new javax.swing.JLabel();
        combo_semester_course = new javax.swing.JComboBox<>();
        jLabel26 = new javax.swing.JLabel();
        combo_course_faculty = new javax.swing.JComboBox<>();
        btn_add_course = new javax.swing.JButton();
        jLabel27 = new javax.swing.JLabel();
        rbtn_mandatory = new javax.swing.JRadioButton();
        rbtn_elective = new javax.swing.JRadioButton();
        btn_edit_courses = new javax.swing.JButton();
        txt_course_edit_id = new javax.swing.JTextField();
        jScrollPane4 = new javax.swing.JScrollPane();
        course_table = new javax.swing.JTable();
        btn_print_courses = new javax.swing.JButton();
        jLabel33 = new javax.swing.JLabel();
        jLabel34 = new javax.swing.JLabel();
        jLabel35 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        txt_date = new javax.swing.JLabel();
        txt_time = new javax.swing.JLabel();
        jLabel36 = new javax.swing.JLabel();
        lbl_names = new javax.swing.JLabel();
        btn_close = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenu2 = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Teacher Information", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 14), new java.awt.Color(102, 102, 102))); // NOI18N

        jLabel3.setText("Last Name*");

        jLabel8.setText("Email*");

        txt_address.setPreferredSize(new java.awt.Dimension(120, 20));

        jLabel1.setText("First Name*");

        txt_first_name.setPreferredSize(new java.awt.Dimension(120, 20));

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

        buttonGroup1.add(rbtn_male);
        rbtn_male.setText("Male");
        rbtn_male.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbtn_maleActionPerformed(evt);
            }
        });

        combo_nationality.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "[none]", "Afghanistan", "Albania", "Algeria", "American Samoa", "Andorra", "Angola", "Anguilla", "Antigua and Barbuda", "Argentina", "Armenia", "Aruba", "Australia", "Austria", "Azerbaijan", "Bahamas", "Bahrain", "Bangladesh", "Barbados", "Belarus", "Belgium", "Belize", "Benin", "Bermuda", "Bhutan", "Bolivia", "Bosnia and Herzegovina", "Botswana", "Brazil", "British Virgin Islands", "Brunei", "Bulgaria", "Burkina Faso", "Burma", "Burundi", "Cambodia", "Cameroon", "Canada", "Cape Verde", "Caribbean Netherlands", "Cayman Islands", "Central African Republic", "Chad", "Chile", "China", "Christmas Island", "Cocos Islands", "Colombia", "Comoros", "Congo", "Cook Islands", "Costa Rica", "Croatia", "Cuba", "Curaçao", "Cyprus", "Czech Republic", "Democratic Republic of the Congo", "Denmark", "Djibouti", "Dominica", "Dominican Republic", "Ecuador", "Egypt", "El Salvador", "Equatorial Guinea", "Eritrea", "Estonia", "Ethiopia", "Falkland Islands", "Faroe Islands", "Federated States of Micronesia", "Fiji", "Finland", "France", "French Guiana", "French Polynesia", "Gabon", "Gambia", "Georgia", "Germany", "Ghana", "Gibraltar", "Greece", "Greenland", "Grenada", "Guadeloupe", "Guam", "Guatemala", "Guernsey", "Guinea", "Guinea-Bissau", "Guyana", "Haiti", "Honduras", "Hong Kong", "Hungary", "Iceland", "India", "Indonesia", "Iran", "Iraq", "Ireland", "Isle of Man", "Israel", "Italy", "Ivory Coast", "Jamaica", "Japan", "Jersey", "Jordan", "Kazakhstan", "Kenya", "Kiribati", "Kosovo", "Kuwait", "Kyrgyzstan", "Laos", "Latvia", "Lebanon", "Lesotho", "Liberia", "Libya", "Liechtenstein", "Lithuania", "Luxembourg", "Macau", "Macedonia", "Madagascar", "Malawi", "Malaysia", "Maldives", "Mali", "Malta", "Marshall Islands", "Martinique", "Mauritania", "Mauritius", "Mayotte", "Mexico", "Moldova", "Monaco", "Mongolia", "Montenegro", "Montserrat", "Morocco", "Mozambique", "Namibia", "Nauru", "Nepal", "Netherlands", "New Caledonia", "New Zealand", "Nicaragua", "Niger", "Nigeria", "Niue", "Norfolk Island", "North Korea", "Northern Mariana Islands", "Norway", "Oman", "Pakistan", "Palau", "Palestine", "Panama", "Papua New Guinea", "Paraguay", "Peru", "Philippines", "Pitcairn Islands", "Poland", "Portugal", "Puerto Rico", "Qatar", "Réunion", "Romania", "Russia", "Rwanda", "Saint Barthélemy", "Saint Helena, Ascension and Tristan da Cunha", "Saint Kitts and Nevis", "Saint Lucia", "Saint Martin", "Saint Pierre and Miquelon", "Saint Vincent and the Grenadines", "Samoa", "San Marino", "São Tomé and Príncipe", "Saudi Arabia", "Senegal", "Serbia", "Seychelles", "Sierra Leone", "Singapore", "Sint Maarten", "Slovakia", "Slovenia", "Solomon Islands", "Somalia", "South Africa", "South Korea", "South Sudan", "Spain", "Sri Lanka", "Sudan", "Suriname", "Svalbard and Jan Mayen", "Swaziland", "Sweden", "Switzerland", "Syria", "Taiwan", "Tajikistan", "Tanzania", "Thailand", "Timor-Leste", "Togo", "Tokelau", "Tonga", "Trinidad and Tobago", "Tunisia", "Turkey", "Turkmenistan", "Turks and Caicos Islands", "Tuvalu", "Uganda", "Ukraine", "United Arab Emirates", "United Kingdom", "United States", "United States Virgin Islands", "Uruguay", "Uzbekistan", "Vanuatu", "Vatican City", "Venezuela", "Vietnam", "Wallis and Futuna", "Western Sahara", "Yemen", "Zambia", "Zimbabwe", "Åland Islands" }));
        combo_nationality.setPreferredSize(new java.awt.Dimension(120, 20));

        txt_date_birth.setDateFormatString("yyyy-MM-dd");
        txt_date_birth.setPreferredSize(new java.awt.Dimension(120, 20));

        jLabel5.setText("Date of Birth*");

        txt_phone.setPreferredSize(new java.awt.Dimension(120, 20));
        txt_phone.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_phoneKeyTyped(evt);
            }
        });

        jLabel2.setText("Middle Name");

        jLabel7.setText("Adress");

        jLabel9.setText("Phone");

        buttonGroup1.add(rbtn_female);
        rbtn_female.setText("Female");
        rbtn_female.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbtn_femaleActionPerformed(evt);
            }
        });

        jLabel6.setText("Country*");

        txt_middlename.setPreferredSize(new java.awt.Dimension(120, 20));

        jLabel4.setText("Gender*");

        txt_lastname.setPreferredSize(new java.awt.Dimension(120, 20));

        jLabel17.setText("Admission date:");

        txt_admission_date.setDateFormatString("yyyy-MM-dd");

        jLabel16.setText("Choose Position");

        combo_teacher_position.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "[none]", "Professor", "Assistant" }));
        combo_teacher_position.setPreferredSize(new java.awt.Dimension(120, 20));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txt_first_name, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGap(78, 78, 78)
                        .addComponent(rbtn_male)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(rbtn_female))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(18, 18, 18)
                        .addComponent(txt_middlename, javax.swing.GroupLayout.PREFERRED_SIZE, 1, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addGap(22, 22, 22)
                        .addComponent(txt_lastname, javax.swing.GroupLayout.PREFERRED_SIZE, 1, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txt_date_birth, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(combo_nationality, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addGap(44, 44, 44)
                        .addComponent(txt_address, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(36, 36, 36)
                        .addComponent(txt_email, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel9)
                        .addGap(47, 47, 47)
                        .addComponent(txt_phone, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel16)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(combo_teacher_position, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel17)
                        .addGap(1, 1, 1)
                        .addComponent(txt_admission_date, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txt_first_name, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txt_middlename, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txt_lastname, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(rbtn_male)
                        .addComponent(rbtn_female)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5)
                    .addComponent(txt_date_birth, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6)
                    .addComponent(combo_nationality, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7)
                    .addComponent(txt_address, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel8)
                    .addComponent(txt_email, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel9)
                    .addComponent(txt_phone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel16)
                    .addComponent(combo_teacher_position, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel17)
                    .addComponent(txt_admission_date, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        jLabel11.setText("Password*");

        txt_username.setPreferredSize(new java.awt.Dimension(120, 20));

        jLabel10.setText("Username*");

        txt_comment.setColumns(20);
        txt_comment.setRows(5);
        jScrollPane2.setViewportView(txt_comment);

        jLabel12.setText("Comment");

        txt_password.setPreferredSize(new java.awt.Dimension(120, 20));

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel12)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 195, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel11)
                            .addComponent(jLabel10))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txt_password, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txt_username, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(txt_username, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(txt_password, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jLabel12)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20))
        );

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Choose Action\n", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 14), new java.awt.Color(102, 102, 102))); // NOI18N

        btn_clear.setFont(new java.awt.Font("Serif", 1, 12)); // NOI18N
        btn_clear.setForeground(new java.awt.Color(0, 153, 204));
        btn_clear.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgresource/Clear-icon.png"))); // NOI18N
        btn_clear.setText("Clear");
        btn_clear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_clearActionPerformed(evt);
            }
        });

        btn_delete.setFont(new java.awt.Font("Serif", 1, 12)); // NOI18N
        btn_delete.setForeground(new java.awt.Color(0, 153, 204));
        btn_delete.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgresource/Button-Delete-icon.png"))); // NOI18N
        btn_delete.setText("Delete");
        btn_delete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_deleteActionPerformed(evt);
            }
        });

        btn_save.setFont(new java.awt.Font("Serif", 1, 12)); // NOI18N
        btn_save.setForeground(new java.awt.Color(0, 153, 204));
        btn_save.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgresource/Save-icon.png"))); // NOI18N
        btn_save.setText("Add New Teacher");
        btn_save.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_saveActionPerformed(evt);
            }
        });

        btn_update.setFont(new java.awt.Font("Serif", 1, 12)); // NOI18N
        btn_update.setForeground(new java.awt.Color(0, 153, 204));
        btn_update.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgresource/Actions-document-edit-icon.png"))); // NOI18N
        btn_update.setText("Update");
        btn_update.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_updateActionPerformed(evt);
            }
        });

        teacher_id1.setEditable(false);

        jButton3.setFont(new java.awt.Font("Serif", 1, 12)); // NOI18N
        jButton3.setForeground(new java.awt.Color(0, 153, 204));
        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgresource/ireport.png"))); // NOI18N
        jButton3.setText("IReport");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btn_save, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btn_update)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btn_delete)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton3)
                .addGap(12, 12, 12)
                .addComponent(btn_clear, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(teacher_id1, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(17, 17, 17))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(btn_save)
                .addComponent(btn_update)
                .addComponent(btn_delete)
                .addComponent(btn_clear)
                .addComponent(teacher_id1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(jButton3))
        );

        jButton1.setFont(new java.awt.Font("Serif", 1, 12)); // NOI18N
        jButton1.setForeground(new java.awt.Color(0, 153, 204));
        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgresource/Editing-Attach-icon.png"))); // NOI18N
        jButton1.setText("Add Image");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        txt_profile_image_path.setEnabled(false);
        txt_profile_image_path.setPreferredSize(new java.awt.Dimension(120, 20));

        jDesktopPane1.setBackground(new java.awt.Color(204, 204, 255));

        lbel_profile_pic.setPreferredSize(new java.awt.Dimension(120, 100));

        jDesktopPane1.setLayer(lbel_profile_pic, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout jDesktopPane1Layout = new javax.swing.GroupLayout(jDesktopPane1);
        jDesktopPane1.setLayout(jDesktopPane1Layout);
        jDesktopPane1Layout.setHorizontalGroup(
            jDesktopPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jDesktopPane1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lbel_profile_pic, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
                .addContainerGap())
        );
        jDesktopPane1Layout.setVerticalGroup(
            jDesktopPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jDesktopPane1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lbel_profile_pic, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jList2.setMinimumSize(new java.awt.Dimension(152, 150));
        jList2.setPreferredSize(new java.awt.Dimension(120, 139));
        jList2.setSelectionForeground(new java.awt.Color(204, 204, 204));
        jScrollPane1.setViewportView(jList2);

        teacher_first_last_names.setBackground(new java.awt.Color(204, 204, 255));
        teacher_first_last_names.setFont(new java.awt.Font("Serif", 1, 12)); // NOI18N
        teacher_first_last_names.setForeground(new java.awt.Color(204, 204, 204));
        teacher_first_last_names.setPreferredSize(new java.awt.Dimension(152, 20));

        jPanel10.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Search Category", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Serif", 1, 14), new java.awt.Color(102, 102, 102))); // NOI18N

        combo_search_category.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "All Category", "Professors", "Assistants", " " }));
        combo_search_category.setPreferredSize(new java.awt.Dimension(180, 20));
        combo_search_category.addPopupMenuListener(new javax.swing.event.PopupMenuListener() {
            public void popupMenuCanceled(javax.swing.event.PopupMenuEvent evt) {
            }
            public void popupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {
                combo_search_categoryPopupMenuWillBecomeInvisible(evt);
            }
            public void popupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt) {
            }
        });
        combo_search_category.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                combo_search_categoryMouseClicked(evt);
            }
        });

        txt_search.setBackground(new java.awt.Color(204, 204, 255));
        txt_search.setPreferredSize(new java.awt.Dimension(180, 20));
        txt_search.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_searchKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(combo_search_category, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_search, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txt_search, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(combo_search_category, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jList1.setBackground(new java.awt.Color(204, 204, 255));
        jList1.setFont(new java.awt.Font("Serif", 1, 12)); // NOI18N
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
        jScrollPane5.setViewportView(jList1);

        jLabel18.setFont(new java.awt.Font("Serif", 1, 12)); // NOI18N
        jLabel18.setText("List of Staffs");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 229, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jButton1)
                            .addComponent(txt_profile_image_path, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                            .addComponent(teacher_first_last_names, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jDesktopPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(31, 31, 31))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 225, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel18))
                .addGap(35, 35, 35))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel18))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jDesktopPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txt_profile_image_path, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(teacher_first_last_names, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(12, 12, 12)))
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 346, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("General", jPanel1);

        jPanel8.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Assign Course to teacher", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Serif", 1, 14), new java.awt.Color(204, 204, 255))); // NOI18N

        txt_course_year.setPreferredSize(new java.awt.Dimension(80, 20));

        btn_assigned_course.setFont(new java.awt.Font("Serif", 1, 12)); // NOI18N
        btn_assigned_course.setForeground(new java.awt.Color(0, 153, 204));
        btn_assigned_course.setText("Assign course");
        btn_assigned_course.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_assigned_courseActionPerformed(evt);
            }
        });

        combo_teacher_name.addPopupMenuListener(new javax.swing.event.PopupMenuListener() {
            public void popupMenuCanceled(javax.swing.event.PopupMenuEvent evt) {
            }
            public void popupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {
                combo_teacher_namePopupMenuWillBecomeInvisible(evt);
            }
            public void popupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt) {
            }
        });
        combo_teacher_name.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                combo_teacher_nameActionPerformed(evt);
            }
        });

        jLabel13.setText("Course name: ");

        combo_course_name.setPreferredSize(new java.awt.Dimension(138, 20));
        combo_course_name.addPopupMenuListener(new javax.swing.event.PopupMenuListener() {
            public void popupMenuCanceled(javax.swing.event.PopupMenuEvent evt) {
            }
            public void popupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {
                combo_course_namePopupMenuWillBecomeInvisible(evt);
            }
            public void popupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt) {
            }
        });

        jLabel14.setText("Teacher name:");

        jLabel22.setText("Year of Studies");

        jLabel23.setText("Semester");

        txt_course_semester.setPreferredSize(new java.awt.Dimension(80, 20));

        jLabel28.setText("Year");

        course_year.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "------------", "2011/2012", "2012/2013", "2013/2014", "2014/2015", "2015/2016" }));
        course_year.setPreferredSize(new java.awt.Dimension(80, 20));

        jLabel15.setText("Assistant name");

        combo_teaching_assistant.setPreferredSize(new java.awt.Dimension(135, 20));
        combo_teaching_assistant.addPopupMenuListener(new javax.swing.event.PopupMenuListener() {
            public void popupMenuCanceled(javax.swing.event.PopupMenuEvent evt) {
            }
            public void popupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {
                combo_teaching_assistantPopupMenuWillBecomeInvisible(evt);
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
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btn_assigned_course)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel13)
                            .addComponent(jLabel14)
                            .addComponent(jLabel15))
                        .addGap(23, 23, 23)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(combo_course_name, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(combo_teacher_name, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(combo_teaching_assistant, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))))
                .addGap(18, 18, 18)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addComponent(jLabel22)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txt_course_year, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel23)
                            .addComponent(jLabel28))
                        .addGap(48, 48, 48)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txt_course_semester, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(course_year, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(combo_course_name, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel22)
                    .addComponent(txt_course_year, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel14)
                    .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(combo_teacher_name, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel23)
                        .addComponent(txt_course_semester, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel28)
                    .addComponent(course_year, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel15)
                        .addComponent(combo_teaching_assistant, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btn_assigned_course)
                .addContainerGap())
        );

        jPanel11.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Reassign Course", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Serif", 1, 14), new java.awt.Color(204, 204, 255))); // NOI18N

        combo_teacher_reassign.addPopupMenuListener(new javax.swing.event.PopupMenuListener() {
            public void popupMenuCanceled(javax.swing.event.PopupMenuEvent evt) {
            }
            public void popupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {
                combo_teacher_reassignPopupMenuWillBecomeInvisible(evt);
            }
            public void popupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt) {
            }
        });

        combo_assistant_name_reassign.addPopupMenuListener(new javax.swing.event.PopupMenuListener() {
            public void popupMenuCanceled(javax.swing.event.PopupMenuEvent evt) {
            }
            public void popupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {
                combo_assistant_name_reassignPopupMenuWillBecomeInvisible(evt);
            }
            public void popupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt) {
            }
        });

        jLabel30.setText("Assistant name");

        jLabel29.setText("Teacher name");

        jLabel32.setText("Course name");

        jLabel31.setText("Year");

        course_name_reassigned.addPopupMenuListener(new javax.swing.event.PopupMenuListener() {
            public void popupMenuCanceled(javax.swing.event.PopupMenuEvent evt) {
            }
            public void popupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {
                course_name_reassignedPopupMenuWillBecomeInvisible(evt);
            }
            public void popupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt) {
            }
        });
        course_name_reassigned.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                course_name_reassignedMouseClicked(evt);
            }
        });

        combo_year_reassign.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "------------", "2011/2012", "2012/2013", "2013/2014", "2014/2015", "2015/2016" }));

        btn_reassign_course.setFont(new java.awt.Font("Serif", 1, 12)); // NOI18N
        btn_reassign_course.setForeground(new java.awt.Color(0, 153, 204));
        btn_reassign_course.setText("Reassign Course");
        btn_reassign_course.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_reassign_courseActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel29, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel30)
                    .addComponent(jLabel31)
                    .addComponent(jLabel32))
                .addGap(18, 18, 18)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(combo_teacher_reassign, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(combo_assistant_name_reassign, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(combo_year_reassign, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(course_name_reassigned, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txt_course_reassign, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_reassign_course, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(60, Short.MAX_VALUE))
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel32)
                    .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(course_name_reassigned, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txt_course_reassign, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel29)
                    .addComponent(combo_teacher_reassign, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel30)
                    .addComponent(combo_assistant_name_reassign, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addComponent(jLabel31))
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(combo_year_reassign, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btn_reassign_course))))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(210, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Assign course", jPanel5);

        jPanel9.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Add Course", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 14), new java.awt.Color(204, 204, 255))); // NOI18N

        txt_course_code.setPreferredSize(new java.awt.Dimension(133, 20));

        combo_yos_course.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1", "2", "3", "4" }));
        combo_yos_course.setPreferredSize(new java.awt.Dimension(120, 20));

        jLabel20.setText("Course name*");

        jLabel21.setText("Course code*");

        jLabel24.setText("Year of studies");

        txt_course_name.setPreferredSize(new java.awt.Dimension(133, 20));

        jLabel25.setText("Semester");

        combo_semester_course.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1", "2", "3", "4", "5", "6", "7", "8" }));
        combo_semester_course.setPreferredSize(new java.awt.Dimension(120, 20));

        jLabel26.setText("Faculty");

        combo_course_faculty.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "All Category", "CSE", "CNS", "ISVMA", "MIR", "ISA" }));
        combo_course_faculty.setMinimumSize(new java.awt.Dimension(120, 20));
        combo_course_faculty.setPreferredSize(new java.awt.Dimension(120, 20));

        btn_add_course.setFont(new java.awt.Font("Serif", 1, 12)); // NOI18N
        btn_add_course.setForeground(new java.awt.Color(0, 153, 204));
        btn_add_course.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgresource/Button-Add-icon.png"))); // NOI18N
        btn_add_course.setText("Add");
        btn_add_course.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_add_courseActionPerformed(evt);
            }
        });

        jLabel27.setText("Course status");

        buttonGroup2.add(rbtn_mandatory);
        rbtn_mandatory.setText("Mandatory");
        rbtn_mandatory.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbtn_mandatoryActionPerformed(evt);
            }
        });

        buttonGroup2.add(rbtn_elective);
        rbtn_elective.setText("Elective");
        rbtn_elective.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbtn_electiveActionPerformed(evt);
            }
        });

        btn_edit_courses.setFont(new java.awt.Font("Serif", 1, 12)); // NOI18N
        btn_edit_courses.setForeground(new java.awt.Color(0, 153, 204));
        btn_edit_courses.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgresource/Actions-document-edit-icon.png"))); // NOI18N
        btn_edit_courses.setText("Edit");
        btn_edit_courses.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_edit_coursesActionPerformed(evt);
            }
        });

        txt_course_edit_id.setEnabled(false);

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jPanel9Layout.createSequentialGroup()
                                    .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel21)
                                        .addComponent(jLabel20))
                                    .addGap(21, 21, 21))
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                                    .addComponent(jLabel24)
                                    .addGap(18, 18, 18)))
                            .addGroup(jPanel9Layout.createSequentialGroup()
                                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel26)
                                    .addComponent(jLabel25))
                                .addGap(45, 45, 45)))
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(combo_course_faculty, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txt_course_name, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(combo_yos_course, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(combo_semester_course, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txt_course_code, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addComponent(jLabel27)
                        .addGap(18, 18, 18)
                        .addComponent(rbtn_mandatory)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(rbtn_elective))
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addGap(47, 47, 47)
                        .addComponent(txt_course_edit_id, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn_add_course)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn_edit_courses)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel20)
                    .addComponent(txt_course_name, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel21)
                    .addComponent(txt_course_code, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel26)
                    .addComponent(combo_course_faculty, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(24, 24, 24)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(combo_yos_course, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel24))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel25)
                    .addComponent(combo_semester_course, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel27)
                    .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(rbtn_mandatory)
                        .addComponent(rbtn_elective)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 16, Short.MAX_VALUE)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_add_course)
                    .addComponent(btn_edit_courses)
                    .addComponent(txt_course_edit_id, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        course_table.setBackground(new java.awt.Color(204, 204, 255));
        course_table.setModel(new javax.swing.table.DefaultTableModel(
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
        course_table.setSelectionBackground(new java.awt.Color(204, 204, 204));
        course_table.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                course_tableMouseClicked(evt);
            }
        });
        course_table.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                course_tableKeyReleased(evt);
            }
        });
        jScrollPane4.setViewportView(course_table);

        btn_print_courses.setFont(new java.awt.Font("Serif", 1, 12)); // NOI18N
        btn_print_courses.setForeground(new java.awt.Color(0, 153, 204));
        btn_print_courses.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgresource/printer-icon.png"))); // NOI18N
        btn_print_courses.setText("Print Courses");
        btn_print_courses.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_print_coursesActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, 255, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 161, Short.MAX_VALUE)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btn_print_courses)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 468, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(23, 23, 23))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btn_print_courses)
                .addContainerGap(51, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Courses", jPanel6);

        jLabel33.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel33.setText("UIST Management System");

        jLabel34.setFont(new java.awt.Font("Serif", 3, 12)); // NOI18N
        jLabel34.setForeground(new java.awt.Color(102, 102, 102));
        jLabel34.setText("You're login as an admin");

        jLabel35.setFont(new java.awt.Font("Serif", 1, 14)); // NOI18N
        jLabel35.setText("Welcome ");

        jButton2.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgresource/Apps-session-logout-icon.png"))); // NOI18N
        jButton2.setText("Logout");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        txt_date.setForeground(new java.awt.Color(0, 102, 204));

        txt_time.setForeground(new java.awt.Color(0, 102, 204));

        jLabel36.setFont(new java.awt.Font("Serif", 1, 14)); // NOI18N
        jLabel36.setText("UIST Management System Admin Teacher Section");

        lbl_names.setFont(new java.awt.Font("Serif", 1, 14)); // NOI18N
        lbl_names.setForeground(new java.awt.Color(0, 153, 204));

        btn_close.setFont(new java.awt.Font("Serif", 1, 10)); // NOI18N
        btn_close.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgresource/Button-Close-icon.png"))); // NOI18N
        btn_close.setText("Close");
        btn_close.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_closeActionPerformed(evt);
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
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel35)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lbl_names, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel36, javax.swing.GroupLayout.PREFERRED_SIZE, 319, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton2)
                                .addGap(2, 2, 2)
                                .addComponent(btn_close)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txt_date, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txt_time, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 922, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(628, 628, 628)
                        .addComponent(jLabel33)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel34)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txt_date, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txt_time, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lbl_names, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel35)
                        .addComponent(jButton2)
                        .addComponent(jLabel36)
                        .addComponent(btn_close)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 441, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel34)
                    .addComponent(jLabel33)))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btn_saveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_saveActionPerformed
        

        
        String first_name = txt_first_name.getText();
        String last_name = txt_lastname.getText();
        String middle_name = txt_middlename.getText();
        String date_birth = ((JTextField)txt_date_birth.getDateEditor().getUiComponent()).getText();
        
        String email = txt_email.getText();
        String address = txt_address.getText();
        String phone = txt_phone.getText();
        String username = txt_username.getText();
        String password = txt_password.getText();
        String comment = txt_comment.getText();
        String nationality = combo_nationality.getSelectedItem().toString();
        String teacher_position = combo_teacher_position.getSelectedItem().toString();
        String admission_date = ((JTextField)txt_admission_date.getDateEditor().getUiComponent()).getText();
        
        if(!first_name.isEmpty() && !last_name.isEmpty() && !date_birth.equals("") && buttonGroup1.getSelection() != null
                && !username.isEmpty() && !email.isEmpty() && combo_nationality.getSelectedIndex() != 0 
                && combo_teacher_position.getSelectedIndex() != 0){
            
                            if(admission_date.equals("")){
                                Calendar cal = new GregorianCalendar();
                                int month = cal.get(Calendar.MONTH);
                                int year = cal.get(Calendar.YEAR);
                                int day = cal.get(Calendar.DAY_OF_MONTH);
                                admission_date = year+"-"+month+"-"+day;
            
                    }  
       
                            
        emailExist1 = checkEmailifExxist(txt_email.getText());
        userNamecheck = checkUsername(txt_username.getText());
        if(emailStatus){
            txt_email.setBackground(Color.WHITE);
           if(emailExist1){
               txt_email.setBackground(Color.WHITE);
           if(username.length()>=5 || password.length() >=5){
               if(username.length()>=5){
                   txt_username.setBackground(Color.WHITE);
                   if(password.length() >= 5){
                       txt_password.setBackground(Color.WHITE);
                   
             if(userNamecheck){
                /*Check if the User is given a picture or not*/
                /*If the user is given a picture , do nothing or else assign the user a default picture based on his/her gender*/
                    if(lbel_profile_pic.getIcon() == null){
                       if(gender.equalsIgnoreCase("Male")){
                        try{
                         File image = new File("defaultpicturesImportant\\male_default.jpg");
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
                       }else if(gender.equalsIgnoreCase("Female")){
                        try{
                         File image = new File("defaultpicturesImportant\\female_default.jpg");
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
                       }
                    }               
                
                /*############################################*/
             try{
               String sql = "INSERT INTO teachers (first_name, last_name,middle_name,email_address,user_name,password,gender,dob,nationality,"
                       + "address,phone_number,comment,admission_date,photo,position) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
               pst = conn.prepareStatement(sql);
               pst.setString(1,first_name);
               pst.setString(2,last_name);
               pst.setString(3,middle_name);
               pst.setString(4,email);
               pst.setString(5,username);
               pst.setString(6,password);
               pst.setString(7,gender);
               pst.setString(8,date_birth);
               pst.setString(9,nationality);
               pst.setString(10,address);
               pst.setString(11,txt_phone.getText());
               pst.setString(12,comment);
               pst.setString(13,admission_date);
               pst.setBytes(14,profile_image);
               pst.setString(15,teacher_position);
               
               pst.execute();
               
               
               
               JOptionPane.showMessageDialog(null, "Data Inserted");
               txt_email.setBackground(Color.WHITE);
               txt_username.setBackground(Color.WHITE);
               txt_password.setBackground(Color.WHITE);
               clear_field();
              }catch(Exception e){
                 //JOptionPane.showMessageDialog(null, e);
             }

             }else{
               txt_username.setBackground(Color.RED);
               JOptionPane.showMessageDialog(null, "Username is taken!!!");
                                
             }
            }else{
               txt_password.setBackground(Color.RED);
               JOptionPane.showMessageDialog(null, "Your password must be at least 5 characters long!!!");             
             }
           }else{
                 txt_username.setBackground(Color.RED);
                JOptionPane.showMessageDialog(null,"Your username must be at least 5 characters long");
           }
             }else{
                 JOptionPane.showMessageDialog(null,"Your username and password must be at least 5 characters long");
                 txt_password.setBackground(Color.RED);
                 txt_username.setBackground(Color.RED);
                 }   
           }else{
               txt_email.setBackground(Color.RED);
               JOptionPane.showMessageDialog(null, "Email is already registered!!!");
               
           }    
             }else{
                JOptionPane.showMessageDialog(null, "Your email address doesn't exist!");
             }
        
        }else{
                JOptionPane.showMessageDialog(null, "Please fill all neccessay field correctly");
        }
        fill_list_box();
        //Update_table();
        
        
    }//GEN-LAST:event_btn_saveActionPerformed

    private void rbtn_maleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbtn_maleActionPerformed
       gender = "Male";
    }//GEN-LAST:event_rbtn_maleActionPerformed

    private void rbtn_femaleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbtn_femaleActionPerformed
        gender = "Female";
    }//GEN-LAST:event_rbtn_femaleActionPerformed
    Boolean emailExist1 = false;
    Boolean userNamecheck = false;
    private boolean checkEmailifExxist(String email){
            
            String  sqlCheckEmail = "SELECT COUNT(email) FROM teachers WHERE email = '"+email+"'";
            int count = 0;  
            boolean status = false;
            try{
                  pst = conn.prepareStatement(sqlCheckEmail);
                  rs = pst.executeQuery();
                  if(rs.next()){
                       count = rs.getInt("COUNT(email)");
                  }
                  
            }catch(Exception e){
                //JOptionPane.showMessageDialog(null,"Email already registered!!!");
                txt_email.setBackground(Color.RED);
            }
            if(count >=1){
                status = false;
            }else{
                status = true;
            }
            return status;
    }
    private boolean checkUsername(String username){
            String  sqlCheckEmail = "SELECT COUNT(user_name) FROM teachers WHERE user_name = '"+username+"'";
            int count = 0;  
            boolean status = false;
            try{
                  pst = conn.prepareStatement(sqlCheckEmail);
                  rs = pst.executeQuery();
                  if(rs.next()){
                       count = rs.getInt("COUNT(user_name)");
                  }
                  txt_username.setBackground(Color.WHITE);
            }catch(Exception e){
                JOptionPane.showMessageDialog(null,e);
                txt_username.setBackground(Color.RED);
            }
            if(count >=1){
                status = false;
            }else{
                status = true;
            }
            return status;    
    }
    private void txt_emailKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_emailKeyReleased
        emailStatus = ValidateEmail.validateEmail(txt_email.getText());
        
        if(emailStatus){
            txt_email.setBackground(Color.WHITE);
        
        }
        else{
            txt_email.setBackground(Color.RED);
        }
     
    }//GEN-LAST:event_txt_emailKeyReleased

    private void txt_phoneKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_phoneKeyTyped
         char c = evt.getKeyChar();
        
         if(!(Character.isDigit(c) || c==KeyEvent.VK_BACKSPACE) || c==KeyEvent.VK_DELETE){
             evt.consume();
         }
    }//GEN-LAST:event_txt_phoneKeyTyped
public int checkUsername(){   
 String checkEmailAndUsername = "SELECT COUNT(user_name) FROM teachers WHERE id != '"+teacher_id1.getText()+"' AND user_name = '"+txt_email.getText()+"'";
 int checkifDetailsExist = 0;       
   try{
      pst = conn.prepareStatement(checkEmailAndUsername);
      rs = pst.executeQuery();
      if(rs.next()){
          checkifDetailsExist = Integer.parseInt(rs.getString("COUNT(user_name)"));
           
      }
   }catch(Exception e){
      JOptionPane.showMessageDialog(null, e);
   }
   return checkifDetailsExist;
}  
/*Check the email for full update*/
public int checkEmailUpdate(){   
 String checkEmailAndUsername = "SELECT COUNT(email_address) FROM teachers WHERE id != '"+teacher_id1.getText()+"' AND email_address = '"+txt_email.getText()+"'";
 int checkifDetailsExist = 0;       
   try{
      pst = conn.prepareStatement(checkEmailAndUsername);
      rs = pst.executeQuery();
      if(rs.next()){
          checkifDetailsExist = Integer.parseInt(rs.getString("COUNT(email_address)"));        
      }
   }catch(Exception e){
      JOptionPane.showMessageDialog(null, e);
   }
   return checkifDetailsExist;
}

    
    
    
    private void btn_updateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_updateActionPerformed
        
        System.out.println(checkUsername() + " hello");
        
        String first_name = txt_first_name.getText();
        String last_name = txt_lastname.getText();
        String middle_name = txt_middlename.getText();
        String date_birth = ((JTextField)txt_date_birth.getDateEditor().getUiComponent()).getText();
        
        String email = txt_email.getText();
        String address = txt_address.getText();
        String phone = txt_phone.getText();
        String username = txt_username.getText();
        String password = txt_password.getText();
        String comment = txt_comment.getText();
        String nationality = combo_nationality.getSelectedItem().toString();
        String teacher_position = (String)combo_teacher_position.getSelectedItem();
        String admission_date = ((JTextField)txt_admission_date.getDateEditor().getUiComponent()).getText();
        if(!teacher_id1.getText().isEmpty()) {      
      if(!first_name.isEmpty() && !last_name.isEmpty() && !date_birth.equals("") && buttonGroup1.getSelection() != null
                && !username.isEmpty() && !email.isEmpty() && combo_nationality.getSelectedIndex() != 0
              && combo_teacher_position.getSelectedIndex() != 0){
                if(emailStatus){
                    txt_email.setBackground(Color.WHITE);
                   if(txt_username.getText().length() >= 5){
                       txt_username.setBackground(Color.WHITE);
                              if(txt_password.getText().length() >=5){
                                  txt_password.setBackground(Color.WHITE);
                                                                
                try{
                String update = "UPDATE teachers SET first_name= '"+first_name+"', last_name ='"+last_name+"',"
                        + "middle_name='"+middle_name+"',email_address='"+email+"',user_name='"+username+"',"
                        + "password='"+password+"',gender='"+gender+"',dob='"+date_birth+"',nationality='"+nationality+"',"
                        + "address='"+address+"',phone_number='"+phone+"',comment='"+comment+"',admission_date='"+admission_date+"', "
                        + "position = '"+teacher_position+"'"
                        + "WHERE id='"+teacher_id1.getText()+"'";  
               
                   pst = conn.prepareStatement(update);
                   pst.execute();
                   
                if(!txt_profile_image_path.getText().isEmpty()){
                    byte [] profile_image1 = null;
                        try{
                           File image = new File(txt_profile_image_path.getText());
                           FileInputStream fis = new FileInputStream(image);

                           ByteArrayOutputStream bos = new ByteArrayOutputStream(); //converting the file to the right format
                           byte[] buf = new byte[1024]; //giving it a buffer size
                           for(int readNum; (readNum = fis.read(buf))!= -1;){
                               bos.write(buf, 0, readNum);
                           }

                          profile_image1 = bos.toByteArray();
                          
                          String update_image = "UPDATE teachers SET photo = ? WHERE id='"+teacher_id1.getText()+"'";
                          
                          pst1 = conn.prepareStatement(update_image);
                          pst1.setBytes(1,profile_image1);
                          pst1.execute();

                       }catch(Exception e){
                         JOptionPane.showMessageDialog(null, e);
                       } 
                    
                }
                JOptionPane.showMessageDialog(null, "Data Updated"); 
                txt_username.setBackground(Color.WHITE);
                txt_email.setBackground(Color.WHITE);
                txt_password.setBackground(Color.WHITE);
                  
                  fill_list_box();
                  //Update_table();                         
                  //clear_field();
                }catch(Exception e){
                   JOptionPane.showMessageDialog(null, "Your email or username is already taken");
                                   txt_username.setBackground(Color.RED);
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
             JOptionPane.showMessageDialog(null,"You must fill all fields with *");
         }
        }else{}
        
    }//GEN-LAST:event_btn_updateActionPerformed

    private void btn_clearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_clearActionPerformed
       clear_field();
    }//GEN-LAST:event_btn_clearActionPerformed

    private void btn_deleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_deleteActionPerformed


        if(!teacher_id1.getText().isEmpty()){
            
            try{
                String delete = "DELETE FROM teachers WHERE id = '"+teacher_id1.getText()+"'";
                int reply = JOptionPane.showConfirmDialog(null, "Teacher with name '"+txt_first_name.getText()+"' '"+txt_lastname.getText()+"' will be deleted"
                        + "", "Confirm delete", JOptionPane.YES_NO_OPTION);
                if (reply == JOptionPane.YES_OPTION) {
                   try{
                      pst = conn.prepareStatement(delete);
                      pst.execute();
                      clear_field();
                      fill_list_box();
                   }catch(Exception e){
                       JOptionPane.showMessageDialog(null,e);
                   }
                }
             
            }catch(Exception e){
            
            }
        }
    }//GEN-LAST:event_btn_deleteActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        JFileChooser chooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("*.IMAGE", "jpg","gif","png");
        chooser.addChoosableFileFilter(filter);
        //int result = fileChooser.showSaveDialog(null);
        
        
       int result = chooser.showSaveDialog(null);
        if(result == JFileChooser.APPROVE_OPTION){
            
       
        File f = chooser.getSelectedFile();
        file_name_profile_pic = f.getAbsolutePath();
        txt_profile_image_path.setText(file_name_profile_pic);
        

        ImageIcon imageIcon = new ImageIcon(file_name_profile_pic); // load the image to a imageIcon
        java.awt.Image image1 = imageIcon.getImage(); // transform it 
	java.awt.Image newimg = image1.getScaledInstance(100, 100,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
        imageIcon = new ImageIcon(newimg);  // transform it back
        
        lbel_profile_pic.setIcon(imageIcon);
        
        try{
            File image = new File(file_name_profile_pic);
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

    private void combo_course_namePopupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {//GEN-FIRST:event_combo_course_namePopupMenuWillBecomeInvisible
              		            try{
					
					String combo_course = (String) combo_course_name.getSelectedItem();
					
					String sql = "SELECT * FROM courses WHERE course_name = ? ";
					pst = conn.prepareStatement(sql);
					pst.setString(1, combo_course);
					rs = pst.executeQuery();
					
					if(rs.next()){
						String addCourseId = rs.getString("course_id");
												
						String addCourse_year = rs.getString("course_year");
						txt_course_year.setText(addCourse_year);						
						String addSemester = rs.getString("course_semester");
						txt_course_semester.setText(addSemester);
                                                course_code= rs.getString("course_code");
                                                course_id = rs.getString("course_id");
					}	
				    }catch(Exception e){
					JOptionPane.showMessageDialog(null,e);
				}
    }//GEN-LAST:event_combo_course_namePopupMenuWillBecomeInvisible

    private void combo_teacher_namePopupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {//GEN-FIRST:event_combo_teacher_namePopupMenuWillBecomeInvisible
                		    
                                try{
					
					String combo_teacher = (String) combo_teacher_name.getSelectedItem();
                                        String first_name = null;
                                        String last_name = trim(combo_teacher.substring(combo_teacher.lastIndexOf(" ")+1));
                                            if(combo_teacher.contains(" ")){
                                                    first_name= combo_teacher.substring(0, combo_teacher.indexOf(" ")); 
                                            }
        
                                        
					String sql = "SELECT * FROM teachers WHERE first_name = ? AND last_name = ?";
					pst = conn.prepareStatement(sql);
					pst.setString(1, first_name);
                                        pst.setString(2, last_name);
					rs = pst.executeQuery();
					
					if(rs.next()){
						get_teacher_id = rs.getString("id");
                                                teacher_fn = rs.getString("first_name");
                                                teacher_ln = rs.getString("last_name");
                                                teacher_mn = rs.getString("middle_name");
                                                teacher_names = teacher_fn + " "+ teacher_ln;
                                                
						
					}	
				    }catch(Exception e){
					JOptionPane.showMessageDialog(null,e);
				}      
    }//GEN-LAST:event_combo_teacher_namePopupMenuWillBecomeInvisible

    private void btn_assigned_courseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_assigned_courseActionPerformed
        /*String sqlCheck = "SELECT * FROM courses";
        try{
         pst = conn.prepareStatement(sqlCheck);
         rs = pst.executeQuery();
         while(rs.next()){
         
         }
        }catch(Exception e){
        
        }*/
        
        /*##############################################################################################*/
        /*Check if course is already assign*/
                
        int assignCourseCheck = 0;
        String checkIfStudentAlreadyEnroll = "SELECT COUNT(course_name) from assign_courses WHERE course_name = '"+combo_course_name.getSelectedItem().toString()+"'" ;
        
        try{
            pst = conn.prepareStatement(checkIfStudentAlreadyEnroll);
            rs = pst.executeQuery();
            if(rs.next()){
               assignCourseCheck = Integer.parseInt(rs.getString("COUNT(course_name)"));
            }
        }catch(Exception e){
        
        }
        /*##############################################################################################*/
        
        if(get_teacher_id !=null && course_year.getSelectedIndex() !=0 && assist_get_teacher_id !=null){
               
                String sql ="INSERT INTO assign_courses (professor_names,teacher_id,course_name, course_code,course_year,assistant_name,assistant_id)"
                        + "VALUES(?,?,?,?,?,?,?)";
                try{
                    if(assignCourseCheck == 0){
                pst = conn.prepareStatement(sql);
                pst.setString(1,teacher_names);
                pst.setString(2,get_teacher_id);
                pst.setString(3,combo_course_name.getSelectedItem().toString());
                pst.setString(4,course_code);
                pst.setString(5,course_year.getSelectedItem().toString());  
                pst.setString(6,assist_teacher_names);
                pst.setString(7,assist_get_teacher_id);
             
                pst.execute();
                
                course_name_reassigned.removeAllItems();
                fill_course_name_reassign();
                JOptionPane.showMessageDialog(null, "Course assigned to "+combo_teacher_name.getSelectedItem().toString());
                
                combo_course_name.setSelectedIndex(0);
                combo_teacher_name.setSelectedIndex(0);
                combo_teaching_assistant.setSelectedIndex(0);
                txt_course_year.setText("");
                txt_course_semester.setText("");
                course_year.setSelectedIndex(0);
                    }else{
                     JOptionPane.showMessageDialog(null, "Course already assigned to ","ERROR",JOptionPane.ERROR_MESSAGE);
                    }
                }catch(Exception e){
                  JOptionPane.showMessageDialog(null, e);
                }
                
        }
        else{
            JOptionPane.showMessageDialog(null, "Choose year of studies");
        }
    }//GEN-LAST:event_btn_assigned_courseActionPerformed

    private void rbtn_mandatoryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbtn_mandatoryActionPerformed
        course_status ="M";
    }//GEN-LAST:event_rbtn_mandatoryActionPerformed

    private void rbtn_electiveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbtn_electiveActionPerformed
        course_status = "E";
    }//GEN-LAST:event_rbtn_electiveActionPerformed

    private void btn_add_courseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_add_courseActionPerformed
 
    int checkIfCourseCodeExist = 0;
    String check_code = "SELECT COUNT(course_code) FROM courses WHERE course_code = '"+txt_course_code.getText()+"'";
        try{
          pst = conn.prepareStatement(check_code);
          rs = pst.executeQuery();
          if(rs.next()){
              checkIfCourseCodeExist = rs.getInt("COUNT(course_code)");              
          }
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }

    int checkIfCourseNameExist = 0;
    String check_course = "SELECT COUNT(course_name) FROM courses WHERE course_name = '"+txt_course_name.getText()+"'";
        try{
          pst = conn.prepareStatement(check_course);
          rs = pst.executeQuery();
          if(rs.next()){
              checkIfCourseNameExist = rs.getInt("COUNT(course_name)");
          }
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
              
        String course_code = txt_course_code.getText();
        String course_name = txt_course_name.getText();
        String faculty = combo_course_faculty.getSelectedItem().toString();
        String semester = combo_semester_course.getSelectedItem().toString();
        String course_year = combo_yos_course.getSelectedItem().toString();
        
        if(!course_code.isEmpty() && ! course_name.isEmpty() && buttonGroup2.getSelection() != null){
            if(checkIfCourseCodeExist == 0){
                txt_course_code.setBackground(Color.WHITE);
                if(checkIfCourseNameExist == 0){
            String sqlInsert = "INSERT INTO courses (course_code,course_name,faculty,course_status,course_year,course_semester) "
                    + "VALUES(?,?,?,?,?,?) ";
            try{
             pst = conn.prepareStatement(sqlInsert);
             pst.setString(1,course_code);
             pst.setString(2,course_name);
             pst.setString(3,faculty);
             pst.setString(4,course_status);
             pst.setString(5,course_year);
             pst.setString(6,semester);
             pst.execute();
                
             JOptionPane.showMessageDialog(null,"Course Added");
             fill_course_name();
             
             txt_course_name.setText("");
             txt_course_code.setText("");
             combo_course_faculty.setSelectedIndex(0);
             combo_yos_course.setSelectedIndex(0);
             combo_semester_course.setSelectedIndex(0);
             txt_course_code.setBackground(Color.WHITE);
             txt_course_name.setBackground(Color.WHITE);
            }catch(Exception e){
              JOptionPane.showMessageDialog(null,e);
            }
                }else{
                JOptionPane.showMessageDialog(null,"The course name already exist for another subject","ERROR",JOptionPane.ERROR_MESSAGE);
                txt_course_name.setBackground(Color.RED);                
                }
         }else{
                JOptionPane.showMessageDialog(null,"The course code already exist for another subject","ERROR",JOptionPane.ERROR_MESSAGE);
                txt_course_code.setBackground(Color.RED);
            }
        }
        fill_courses_table();
        
        
    }//GEN-LAST:event_btn_add_courseActionPerformed

    private void course_tableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_course_tableMouseClicked
        tableCourseClickorReleased();
        txt_course_name.setBackground(Color.WHITE);
        txt_course_code.setBackground(Color.WHITE);        
    }//GEN-LAST:event_course_tableMouseClicked

    private void btn_print_coursesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_print_coursesActionPerformed
                                MessageFormat header = new MessageFormat("Courses");
				MessageFormat footer = new MessageFormat("Page{0,number,integer}");
				
				try{
					course_table.print(JTable.PrintMode.NORMAL,header,footer);// print the table
					
					
				}catch(java.awt.print.PrinterException e){
					
					System.err.format("Cannot print %s %n", e.getMessage());
				}
                              
    }//GEN-LAST:event_btn_print_coursesActionPerformed

    private void combo_teacher_nameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_combo_teacher_nameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_combo_teacher_nameActionPerformed

    private void btn_edit_coursesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_edit_coursesActionPerformed

     int checkIfCourseCodeExist = 0;
    String check_code = "SELECT COUNT(course_code) FROM courses WHERE course_id != '"+txt_course_edit_id.getText()+"' AND course_code = '"+txt_course_code.getText()+"'";
        try{
          pst = conn.prepareStatement(check_code);
          rs = pst.executeQuery();
          if(rs.next()){
              checkIfCourseCodeExist = rs.getInt("COUNT(course_code)");
              
          }
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }

    int checkIfCourseNameExist = 0;
    String check_course = "SELECT COUNT(course_name) FROM courses WHERE course_id != '"+txt_course_edit_id.getText()+"' AND course_name = '"+txt_course_name.getText()+"'";
        try{
          pst = conn.prepareStatement(check_course);
          rs = pst.executeQuery();
          if(rs.next()){
              checkIfCourseNameExist = rs.getInt("COUNT(course_name)");
             
          }
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
        
    if(buttonGroup2.getSelection() != null && !txt_course_code.getText().isEmpty() && !txt_course_name.getText().isEmpty()){
         if(checkIfCourseCodeExist ==0){
              txt_course_code.setBackground(Color.WHITE);
             if(checkIfCourseNameExist == 0){
             
          String course_name1 = txt_course_name.getText();
          String course_code1 = txt_course_code.getText();
          String faculty1 = combo_course_faculty.getSelectedItem().toString();
          String course_year1 = combo_yos_course.getSelectedItem().toString();
          String course_semester1 = combo_semester_course.getSelectedItem().toString();
           String sqlUpdate = "UPDATE courses SET course_name='"+course_name1+"', course_code ='"+course_code1+"',"
                   + "faculty ='"+faculty1+"',course_year='"+course_year1+"', course_semester = '"+course_semester1+"' "
                   + "WHERE course_id='"+txt_course_edit_id.getText()+"'";
           
           try{
           pst = conn.prepareStatement(sqlUpdate);
           pst.execute();
           
           
           JOptionPane.showMessageDialog(null, "Update successful!");
           
           clear_course();
            txt_course_code.setBackground(Color.WHITE);
           fill_course_name();
             }catch(Exception e){
                 JOptionPane.showMessageDialog(null, e);
               }
             }else{
                JOptionPane.showMessageDialog(null,"The course name already exist for another subject","ERROR",JOptionPane.ERROR_MESSAGE);
                txt_course_name.setBackground(Color.RED);             
             }
        }else{
                JOptionPane.showMessageDialog(null,"The course code already exist for another subject","ERROR",JOptionPane.ERROR_MESSAGE);
                txt_course_code.setBackground(Color.RED);
         }
       }else{
           JOptionPane.showMessageDialog(null, "Please eneter all fields");
       }
       fill_courses_table();
       
    }//GEN-LAST:event_btn_edit_coursesActionPerformed

    private void txt_searchKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_searchKeyReleased
        if(!txt_search.getText().isEmpty()){
        try{
            String sql = "SELECT * FROM teachers WHERE first_name = ? OR last_name = ? OR middle_name = ?";
            pst = conn.prepareStatement(sql);
            pst.setString(1,txt_search.getText());
            pst.setString(2,txt_search.getText());
            pst.setString(3,txt_search.getText());
            
            rs = pst.executeQuery();
            if(rs.next()){
                            String id = rs.getString("id");
                            teacher_id1.setText(id);
                            String fn = rs.getString("first_name");
                            txt_first_name.setText(fn);
                            String ln = rs.getString("last_name");
                            txt_lastname.setText(ln);
                            String mn = rs.getString("middle_name");
                            txt_middlename.setText(mn);
                            String nationality = rs.getString("nationality");
                            combo_nationality.setSelectedItem(nationality); 
                            String teacher_position = rs.getString("position");
                            combo_teacher_position.setSelectedItem(teacher_position);                            
                            String email = rs.getString("email_address");
                            txt_email.setText(email);   
                            String address = rs.getString("address");
                            txt_address.setText(address);
                            String phone_number = rs.getString("phone_number");
                            txt_phone.setText(phone_number);                            
                            Date admission_date = rs.getDate("admission_date");
                            txt_admission_date.setDate(admission_date);
                            String user_name = rs.getString("user_name");
                            txt_username.setText(user_name);
                            String password = rs.getString("password");
                            txt_password.setText(password);
                            String gender = rs.getString("gender");
                             if(gender == "Male"){
                                 rbtn_male.setSelected(true);
                                
                             }
                             else if(gender == "Female"){
                                 rbtn_female.setSelected(true);
                             }
                            String comment = rs.getString("comment");
                            txt_comment.setText(comment);
                            Date Dob = rs.getDate("dob");
                            txt_date_birth.setDate(Dob);  
                            byte[] imageData = rs.getBytes("photo");
                                   
                            InputStream input = rs.getBinaryStream("photo"); 
                            byte[] buffer = new byte[1024];
                            if(input.read(buffer) <= 0){
                              lbel_profile_pic.setIcon(null);
                            }
                            /*if(imageData.length ==0){
                              lbl_pic.setIcon(null);
                            }else{*/
                            
                            
                            else{
                            
                            ImageIcon imageIcon = new ImageIcon(imageData); // load the image to a imageIcon
                            java.awt.Image image1 = imageIcon.getImage(); // transform it 
                            java.awt.Image newimg = image1.getScaledInstance(100, 100,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
                            //imageIcon = new ImageIcon(newimg);  
                            
                            employeePicFormat = new ImageIcon(newimg); // transform it back
                            lbel_profile_pic.setIcon(employeePicFormat);
                        } 
                            
                            teacher_first_last_names.setText(fn + " "+ ln +" courses");            
        
            }
            
        }catch(Exception e){
        
        }
        fill_list_box_courses();
       }
        
    }//GEN-LAST:event_txt_searchKeyReleased

    private void combo_teaching_assistantPopupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {//GEN-FIRST:event_combo_teaching_assistantPopupMenuWillBecomeInvisible
                                        try{
					
					String combo_teacher = (String) combo_teaching_assistant.getSelectedItem();
                                        String first_name = null;
                                        String last_name = trim(combo_teacher.substring(combo_teacher.lastIndexOf(" ")+1));
                                            if(combo_teacher.contains(" ")){
                                                    first_name= combo_teacher.substring(0, combo_teacher.indexOf(" ")); 
                                            }
        
                                        
					String sql = "SELECT * FROM teachers WHERE first_name = ? AND last_name = ?";
					pst = conn.prepareStatement(sql);
					pst.setString(1, first_name);
                                        pst.setString(2, last_name);
					rs = pst.executeQuery();
					
					if(rs.next()){
						assist_get_teacher_id = rs.getString("id");
                                                assist_teacher_fn = rs.getString("first_name");
                                                assist_teacher_ln = rs.getString("last_name");
                                                assist_teacher_mn = rs.getString("middle_name");
                                                assist_teacher_names = assist_teacher_fn  + " "+ assist_teacher_ln;
                                                
						
					}	
				    }catch(Exception e){
					JOptionPane.showMessageDialog(null,e);
				} 
    }//GEN-LAST:event_combo_teaching_assistantPopupMenuWillBecomeInvisible

    private void course_name_reassignedMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_course_name_reassignedMouseClicked
        
    }//GEN-LAST:event_course_name_reassignedMouseClicked

    private void course_name_reassignedPopupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {//GEN-FIRST:event_course_name_reassignedPopupMenuWillBecomeInvisible
        				try{
					
					String combo_courses = (String) course_name_reassigned.getSelectedItem();
					
					String sql = "SELECT * FROM assign_courses WHERE course_name = ? ";
					pst = conn.prepareStatement(sql);
					pst.setString(1, combo_courses);
					rs = pst.executeQuery();
					
					if(rs.next()){
                                               String id = rs.getString("id");
                                               txt_course_reassign.setText(id);
						String addTeacher_reassigned = rs.getString("professor_names");
						combo_teacher_reassign.setSelectedItem(addTeacher_reassigned);
						
						String addAssistant_name = rs.getString("assistant_name");
						combo_assistant_name_reassign.setSelectedItem(addAssistant_name);
						
						String year_assigned = rs.getString("course_year");
                                                combo_year_reassign.setSelectedItem(year_assigned);
						
					
					}
					
					
					
					
				}catch(Exception e){
					
				}
    }//GEN-LAST:event_course_name_reassignedPopupMenuWillBecomeInvisible

    private void combo_teacher_reassignPopupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {//GEN-FIRST:event_combo_teacher_reassignPopupMenuWillBecomeInvisible
      
                		    
                                try{
					
					String combo_teacher = (String) combo_teacher_reassign.getSelectedItem();
                                        String first_name = null;
                                        String last_name = trim(combo_teacher.substring(combo_teacher.lastIndexOf(" ")+1));
                                            if(combo_teacher.contains(" ")){
                                                    first_name= combo_teacher.substring(0, combo_teacher.indexOf(" ")); 
                                            }
        
                                        
					String sql = "SELECT * FROM teachers WHERE first_name = ? AND last_name = ?";
					pst = conn.prepareStatement(sql);
					pst.setString(1, first_name);
                                        pst.setString(2, last_name);
					rs = pst.executeQuery();
					
					if(rs.next()){
						rget_teacher_id = rs.getString("id");
                                                rteacher_fn = rs.getString("first_name");
                                                rteacher_ln = rs.getString("last_name");
                                                rteacher_mn = rs.getString("middle_name");
                                                rteacher_names = rteacher_fn + " "+ rteacher_ln;
                                                
						
					}	
				    }catch(Exception e){
					JOptionPane.showMessageDialog(null,e);
				}         

    }//GEN-LAST:event_combo_teacher_reassignPopupMenuWillBecomeInvisible

    private void combo_assistant_name_reassignPopupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {//GEN-FIRST:event_combo_assistant_name_reassignPopupMenuWillBecomeInvisible

                		    
                                try{
					
					String combo_teacher = (String) combo_assistant_name_reassign.getSelectedItem();
                                        String first_name = null;
                                        String last_name = trim(combo_teacher.substring(combo_teacher.lastIndexOf(" ")+1));
                                            if(combo_teacher.contains(" ")){
                                                    first_name= combo_teacher.substring(0, combo_teacher.indexOf(" ")); 
                                            }
        
                                        
					String sql = "SELECT * FROM teachers WHERE first_name = ? AND last_name = ?";
					pst = conn.prepareStatement(sql);
					pst.setString(1, first_name);
                                        pst.setString(2, last_name);
					rs = pst.executeQuery();
					
					if(rs.next()){
						rassist_get_teacher_id = rs.getString("id");
                                                rassist_teacher_fn = rs.getString("first_name");
                                                rassist_teacher_ln = rs.getString("last_name");                                                
                                                rassist_teacher_names = rassist_teacher_fn + " "+ rassist_teacher_ln;
                                                
						
					}	
				    }catch(Exception e){
					JOptionPane.showMessageDialog(null,e);
				}         
       
    }//GEN-LAST:event_combo_assistant_name_reassignPopupMenuWillBecomeInvisible

    private void btn_reassign_courseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_reassign_courseActionPerformed
        if(course_name_reassigned.getSelectedIndex() !=0){
        if(rget_teacher_id !=null && rassist_get_teacher_id !=null ){
                
                String sql ="UPDATE assign_courses SET professor_names ='"+rteacher_names+"', assistant_name ='"+rassist_teacher_names+"'"
                        + "WHERE id ='"+txt_course_reassign.getText()+"'";
                try{
                pst = conn.prepareStatement(sql);
                pst.execute();
                
                course_name_reassigned.removeAllItems();
                fill_course_name_reassign();
                JOptionPane.showMessageDialog(null, "Course re-assigned to "+combo_teacher_reassign.getSelectedItem().toString());
                combo_teacher_reassign.setSelectedIndex(0);
                combo_assistant_name_reassign.setSelectedIndex(0);
                course_name_reassigned.setSelectedIndex(0);
                combo_year_reassign.setSelectedIndex(0);
                txt_course_reassign.setText("");
                }catch(Exception e){
                  JOptionPane.showMessageDialog(null, e);
                }
                
        }
        else{
            JOptionPane.showMessageDialog(null, "Choose the professor and assistant","ERROR",JOptionPane.ERROR_MESSAGE);
        }
        }else{
          JOptionPane.showMessageDialog(null, "Choose the subject to reassign professor","ERROR",JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btn_reassign_courseActionPerformed

    private void txt_emailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_emailActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_emailActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
       close1();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void btn_closeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_closeActionPerformed
       close2();
    }//GEN-LAST:event_btn_closeActionPerformed

    private void jList1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jList1MouseClicked
        jlistFilled();
        txt_email.setBackground(Color.WHITE);
        txt_username.setBackground(Color.WHITE);
        txt_password.setBackground(Color.WHITE);
    }//GEN-LAST:event_jList1MouseClicked

    private void jList1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jList1KeyReleased
       if(evt.getKeyCode() == java.awt.event.KeyEvent.VK_DOWN || evt.getKeyCode() == java.awt.event.KeyEvent.VK_UP){
           jlistFilled();
           txt_email.setBackground(Color.WHITE);
           txt_username.setBackground(Color.WHITE);
           txt_password.setBackground(Color.WHITE);
        }
    }//GEN-LAST:event_jList1KeyReleased

    private void combo_search_categoryPopupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {//GEN-FIRST:event_combo_search_categoryPopupMenuWillBecomeInvisible

    }//GEN-LAST:event_combo_search_categoryPopupMenuWillBecomeInvisible

    private void combo_search_categoryMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_combo_search_categoryMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_combo_search_categoryMouseClicked

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
             try{
         /*String report = "C:\\Users\\michel\\Documents\\NetBeansProjects\\SchoolManagementSystem\\IReports\\Student List.jrxml";
         JasperReport jr = JasperCompileManager.compileReport(report);
         JasperPrint jp = JasperFillManager.fillReport(jr, null, conn);
         JasperViewer.viewReport(jp);
         */
         
            JasperDesign jdesign = JRXmlLoader.load("C:\\Users\\michel\\Documents\\NetBeansProjects\\SchoolManagementSystem\\IReports\\Teachers.jrxml");
            String sql = "SELECT first_name AS 'First Name', middle_name AS 'Middle Name', "
                       + "last_name AS 'Last Name', email_address AS 'Email' FROM teachers ORDER BY first_name ASC";
            JRDesignQuery newQuery = new JRDesignQuery();
            newQuery.setText(sql);
            jdesign.setQuery(newQuery);
            JasperReport jreport = JasperCompileManager.compileReport(jdesign);
            JasperPrint jprint = JasperFillManager.fillReport(jreport, null, conn);
            JasperViewer.viewReport(jprint);

         
        
        }catch(Exception e){
         JOptionPane.showMessageDialog(null,e);
        } 
    }//GEN-LAST:event_jButton3ActionPerformed

    private void course_tableKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_course_tableKeyReleased
        if(evt.getKeyCode() == java.awt.event.KeyEvent.VK_DOWN || evt.getKeyCode() == java.awt.event.KeyEvent.VK_UP){
        tableCourseClickorReleased();
        txt_course_name.setBackground(Color.WHITE);
        txt_course_code.setBackground(Color.WHITE);
        }       
    }//GEN-LAST:event_course_tableKeyReleased
    
    
    public void close(){
		WindowEvent winClosingEvent = new WindowEvent(this,WindowEvent.WINDOW_CLOSING);
		Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(winClosingEvent);       
    }
    public void close2(){
      close();
        AdminPanel frameLogin = new AdminPanel(username, id);
        frameLogin.setLocationRelativeTo(null);
        frameLogin.setVisible(true);
    }
    public void close1(){
        
        close();
        Login frameLogin = new Login();
        frameLogin.setLocationRelativeTo(null);
        frameLogin.setVisible(true);
    }
       private void tableCourseClickorReleased(){
       
               int rowCourse = course_table.getSelectedRow();
               String course_table_click = (course_table.getModel().getValueAt(rowCourse, 1).toString());
                                                             
                      
       try{
         		String sql = "SELECT * FROM courses WHERE course_code ='"+course_table_click+"'";
			pst = conn.prepareStatement(sql);
			rs= pst.executeQuery();
                        if(rs.next()){
                          String course_id_edit = rs.getString("course_id");
                          String course_code = rs.getString("course_code");
                          String course_name = rs.getString("course_name");
                          String course_year = rs.getString("course_year");
                          String course_semester = rs.getString("course_year");
                          String course_faculty = rs.getString("faculty");
                          course_id_tab_3 =rs.getString("course_id");                          
                          String course_status = rs.getString("course_status");
                          
                         if(course_status.equalsIgnoreCase("M")){
                              rbtn_mandatory.setSelected(true);
                          }else if(course_status.equalsIgnoreCase("E")){
                              rbtn_elective.setSelected(true);
                          }
                              
                          txt_course_edit_id.setText(course_id_edit);
                          txt_course_code.setText(course_code);
                          txt_course_name.setText(course_name);
                          combo_yos_course.setSelectedItem(course_year);
                          combo_semester_course.setSelectedItem(course_semester);
                          combo_course_faculty.setSelectedItem(course_faculty);
                          
                        }
                        
       }catch(Exception e){
         JOptionPane.showMessageDialog(null,e);
       }
       }    
       private void clear_field(){
        txt_first_name.setText("");
        txt_lastname.setText("");
        txt_middlename.setText("");
        combo_nationality.setSelectedIndex(0);
        combo_teacher_position.setSelectedIndex(0);
        ((JTextField)txt_admission_date.getDateEditor().getUiComponent()).setText("");
        ((JTextField)txt_date_birth.getDateEditor().getUiComponent()).setText("");  
        txt_email.setText("");
        txt_address.setText("");
        txt_password.setText("");
        txt_username.setText("");
        txt_phone.setText("");
        txt_comment.setText("");
        teacher_id1.setText("");
        //lbel_profile_pic.setText("");
        lbel_profile_pic.setIcon(null);
        txt_profile_image_path.setText("");
        
        buttonGroup1.clearSelection();
        
       }
       private void clear_course(){
           txt_course_name.setText("");
           txt_course_code.setText("");
           combo_semester_course.setSelectedIndex(0);
           combo_course_faculty.setSelectedIndex(0);
           combo_yos_course.setSelectedIndex(0);
           buttonGroup2.clearSelection();
          
       }
   private void jlistFilled(){

        
        String value = (String)jList1.getSelectedValue();
        String first_name = null;
        String lastWord = trim(value.substring(value.lastIndexOf(" ")+1));
        if(value.contains(" ")){
             first_name= value.substring(0, value.indexOf(" ")); 
            }
        String sql = "SELECT * FROM teachers WHERE first_name =? AND last_name =?";
       
        try{
        pst = conn.prepareStatement(sql);
	pst.setString(1, first_name);
        pst.setString(2,lastWord);
	rs = pst.executeQuery();
        if(rs.next()){
                            String id = rs.getString("id");
                            teacher_id1.setText(id);
                            String fn = rs.getString("first_name");
                            txt_first_name.setText(fn);
                            String ln = rs.getString("last_name");
                            txt_lastname.setText(ln);
                            String mn = rs.getString("middle_name");
                            txt_middlename.setText(mn);
                            String nationality = rs.getString("nationality");
                            combo_nationality.setSelectedItem(nationality);  
                            String teacher_position = rs.getString("position");
                            combo_teacher_position.setSelectedItem(teacher_position);
                            String email = rs.getString("email_address");
                            txt_email.setText(email);   
                            String address = rs.getString("address");
                            txt_address.setText(address);
                            String phone_number = rs.getString("phone_number");
                            txt_phone.setText(phone_number);                            
                            Date admission_date = rs.getDate("admission_date");
                            txt_admission_date.setDate(admission_date);
                            String user_name = rs.getString("user_name");
                            txt_username.setText(user_name);
                            String password = rs.getString("password");
                            txt_password.setText(password);
                            String gender = rs.getString("gender");
                             if(gender.equalsIgnoreCase("Male")){
                                 rbtn_male.setSelected(true);
                                 //buttonGroup1.add(rbtn_male);
                                
                             }
                             else if(gender.equalsIgnoreCase("Female")){                                
                                 rbtn_female.setSelected(true);
                                 
                             }
                             //System.out.println("Hello " + gender);
                            
                             
                            String comment = rs.getString("comment");
                            txt_comment.setText(comment);
                            Date Dob = rs.getDate("dob");
                            txt_date_birth.setDate(Dob);  
                            teacher_first_last_names.setText(fn + " "+ ln +" courses");
                            
                            
                            
                            byte[] imageData = rs.getBytes("photo");
                            InputStream input = rs.getBinaryStream("photo"); 
                            byte[] buffer = new byte[1024];
                            if(input.read(buffer) <= 0){
                              lbel_profile_pic.setIcon(null);
                            }
                            /*if(imageData.length ==0){
                              lbl_pic.setIcon(null);
                            }else{*/
                            
                            
                            else{
                            
                            ImageIcon imageIcon = new ImageIcon(imageData); // load the image to a imageIcon
                            java.awt.Image image1 = imageIcon.getImage(); // transform it 
                            java.awt.Image newimg = image1.getScaledInstance(100, 100,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
                            //imageIcon = new ImageIcon(newimg);  
                            
                            employeePicFormat = new ImageIcon(newimg); // transform it back
                            lbel_profile_pic.setIcon(employeePicFormat);
                        }                             
                            
                            
                            
        }
        }catch(Exception e){
            //JOptionPane.showMessageDialog(null,e);
        }
		fill_list_box_courses();			
       
}
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_add_course;
    private javax.swing.JButton btn_assigned_course;
    private javax.swing.JButton btn_clear;
    private javax.swing.JButton btn_close;
    private javax.swing.JButton btn_delete;
    private javax.swing.JButton btn_edit_courses;
    private javax.swing.JButton btn_print_courses;
    private javax.swing.JButton btn_reassign_course;
    private javax.swing.JButton btn_save;
    private javax.swing.JButton btn_update;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;
    private javax.swing.JComboBox<String> combo_assistant_name_reassign;
    private javax.swing.JComboBox<String> combo_course_faculty;
    private javax.swing.JComboBox<String> combo_course_name;
    private javax.swing.JComboBox<String> combo_nationality;
    private javax.swing.JComboBox<String> combo_search_category;
    private javax.swing.JComboBox<String> combo_semester_course;
    private javax.swing.JComboBox<String> combo_teacher_name;
    private javax.swing.JComboBox<String> combo_teacher_position;
    private javax.swing.JComboBox<String> combo_teacher_reassign;
    private javax.swing.JComboBox<String> combo_teaching_assistant;
    private javax.swing.JComboBox<String> combo_year_reassign;
    private javax.swing.JComboBox<String> combo_yos_course;
    private javax.swing.JComboBox<String> course_name_reassigned;
    private javax.swing.JTable course_table;
    private javax.swing.JComboBox<String> course_year;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
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
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JList<String> jList1;
    private javax.swing.JList<String> jList2;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JLabel lbel_profile_pic;
    private javax.swing.JLabel lbl_names;
    private javax.swing.JRadioButton rbtn_elective;
    private javax.swing.JRadioButton rbtn_female;
    private javax.swing.JRadioButton rbtn_male;
    private javax.swing.JRadioButton rbtn_mandatory;
    private javax.swing.JLabel teacher_first_last_names;
    private javax.swing.JTextField teacher_id1;
    private javax.swing.JTextField txt_address;
    private com.toedter.calendar.JDateChooser txt_admission_date;
    private javax.swing.JTextArea txt_comment;
    private javax.swing.JTextField txt_course_code;
    private javax.swing.JTextField txt_course_edit_id;
    private javax.swing.JTextField txt_course_name;
    private javax.swing.JTextField txt_course_reassign;
    private javax.swing.JTextField txt_course_semester;
    private javax.swing.JTextField txt_course_year;
    private javax.swing.JLabel txt_date;
    private com.toedter.calendar.JDateChooser txt_date_birth;
    private javax.swing.JTextField txt_email;
    private javax.swing.JTextField txt_first_name;
    private javax.swing.JTextField txt_lastname;
    private javax.swing.JTextField txt_middlename;
    private javax.swing.JPasswordField txt_password;
    private javax.swing.JTextField txt_phone;
    private javax.swing.JTextField txt_profile_image_path;
    private javax.swing.JTextField txt_search;
    private javax.swing.JLabel txt_time;
    private javax.swing.JTextField txt_username;
    // End of variables declaration//GEN-END:variables

        private ImageIcon employeePicFormat = null; 
        String gender = null;
        boolean emailStatus = false;

        byte[] profile_image = null;
        String file_name_profile_pic = null;
        
        /**/
        
        String course_status = null;
        
        String get_teacher_id = null;        
        String teacher_fn = null;
        String teacher_mn = null;
        String teacher_ln = null;
        String teacher_names = null;

        String assist_get_teacher_id = null;        
        String assist_teacher_fn = null;
        String assist_teacher_mn = null;
        String assist_teacher_ln = null;
        String assist_teacher_names = null;
        
        /*Variables for reassigning courses*/
        String rget_teacher_id = null;        
        String rteacher_fn = null;
        String rteacher_mn = null;
        String rteacher_ln = null;
        String rteacher_names = null;

        String rassist_get_teacher_id = null;        
        String rassist_teacher_fn = null;
        String rassist_teacher_mn = null;
        String rassist_teacher_ln = null;
        String rassist_teacher_names = null;
                 
        
        String course_code = null;
        String course_id = null;
        
        String course_id_tab_3 = null;
}
