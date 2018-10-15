/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package admin;

import AdminWelcomepage.AdminPanel;
import AdminWelcomepage.ValidateEmail;
import com.sun.glass.events.KeyEvent;
import connection.DBConnection;
import java.awt.Color;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.WindowEvent;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import static java.util.logging.Level.WARNING;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.Position;
import static jdk.nashorn.internal.objects.NativeString.trim;
import schoolmanagementsystem.Login;

/**
 *
 * @author Chosen Egbe
 */
public class AdminSection extends javax.swing.JFrame {

    /**
     * Creates new form AdminSection
     */
    String username = null;
    String id = null;
    String position = null;
    
    Connection conn = null;
    PreparedStatement pst = null;
    ResultSet rs = null;
    
    PreparedStatement pst1 = null;
    ResultSet rs1 = null;
    
    public AdminSection(String username1, String id1) {
        initComponents();
        conn = DBConnection.connectDB();
        
        try{
         String userName = "SELECT * FROM administrator WHERE username = '"+username1+"' AND admin_id = '"+id1+"'";
         pst= conn.prepareStatement(userName);
         rs = pst.executeQuery();
         if(rs.next()){
              position = rs.getString("level");
             lbl_names.setText(rs.getString("first_name") + " " + rs.getString("last_name"));
         }
        }catch(Exception e){
           JOptionPane.showMessageDialog(null, e);
        }
        username = username1;
        id = id1;
        lbl_position.setText(position);
        getUserInfo(username, id);
        fill_list_box();
    }
  private void getUserInfo(String username1, String id1){
      try{
         String userName = "SELECT * FROM administrator WHERE username = '"+username1+"' AND admin_id = '"+id1+"'"; 
         pst= conn.prepareStatement(userName);
         rs = pst.executeQuery();
         if(rs.next()){
                           String id = rs.getString("admin_id");
                            txt_admin_id.setText(id);
                            String fn = rs.getString("first_name");
                            txt_fn.setText(fn);
                            String ln = rs.getString("last_name");
                            txt_ln.setText(ln);
                            String mn = rs.getString("middle_name");
                            txt_mn.setText(mn);
                            String admin_level= rs.getString("level");
                            combo_level.setSelectedItem(admin_level);   
                            String email = rs.getString("email");
                            txt_email.setText(email);   
                            String address = rs.getString("address");
                            txt_address.setText(address);
                            String phone_number = rs.getString("phone");
                            txt_phone.setText(phone_number);                                                     
                            String user_name = rs.getString("username");
                            txt_username.setText(user_name);
                            String password = rs.getString("password");
                            txt_password.setText(password);
                            String gender = rs.getString("gender");
                            combo_gender.setSelectedItem(gender);
                            
                            ((JTextField)txt_position.getDateEditor().getUiComponent()).setText(rs.getString("position"));
                             ((JTextField)txt_birth_date.getDateEditor().getUiComponent()).setText(rs.getString("position"));
                            byte[] imageData = rs.getBytes("photo");
                            InputStream input = rs.getBinaryStream("photo"); 
                            byte[] buffer = new byte[1024];
                            if(input.read(buffer) <= 0){
                              lbl_profile_pic.setIcon(null);
                            }
                            /*if(imageData.length ==0){
                              lbl_pic.setIcon(null);
                            }else{*/
                            
                            
                            else{
                            
                            ImageIcon imageIcon = new ImageIcon(imageData); // load the image to a imageIcon
                            java.awt.Image image1 = imageIcon.getImage(); // transform it 
                            java.awt.Image newimg = image1.getScaledInstance(120, 120,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
                            //imageIcon = new ImageIcon(newimg);  
                            
                            adminPicFormat = new ImageIcon(newimg); // transform it back
                            lbl_profile_pic.setIcon(adminPicFormat);
                        }  
    
         }
      }catch(Exception e){
        //JOptionPane.showMessageDialog(null, e);
      }
  }
    public void fill_list_box(){
        DefaultListModel dlm = new DefaultListModel();
        
        try{
         String sql = "SELECT * FROM administrator ORDER BY first_name ASC";
         pst = conn.prepareStatement(sql);
         rs = pst.executeQuery();
       while(rs.next()){
             String first_name = rs.getString("first_name");
             //String middle_name = rs.getString("middle_name");
             String last_name = rs.getString("last_name");
           
             //String username = rs.getString("username");

              dlm.addElement(first_name +" "+last_name);
              
            
             jList1.setFont( new Font("monospaced", Font.PLAIN, 12) );
             jList1.setModel(dlm);
            
             
         
         }
        }catch(Exception e){
            JOptionPane.showMessageDialog(null,e);
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
            java.util.logging.Logger.getLogger(AdminSection.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AdminSection.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AdminSection.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AdminSection.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                AdminSection frame = new AdminSection("", "");
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

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txt_fn = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txt_admin_id = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        combo_level = new javax.swing.JComboBox<>();
        jLabel8 = new javax.swing.JLabel();
        txt_mn = new javax.swing.JTextField();
        txt_password = new javax.swing.JPasswordField();
        txt_ln = new javax.swing.JTextField();
        txt_username = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        txt_email = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        txt_address = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        txt_phone = new javax.swing.JTextField();
        jDesktopPane1 = new javax.swing.JDesktopPane();
        lbl_profile_pic = new javax.swing.JLabel();
        brn_add_image = new javax.swing.JButton();
        txt_image_file_path = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        combo_gender = new javax.swing.JComboBox<>();
        txt_position = new com.toedter.calendar.JDateChooser();
        jLabel9 = new javax.swing.JLabel();
        txt_birth_date = new com.toedter.calendar.JDateChooser();
        jPanel2 = new javax.swing.JPanel();
        btn_save = new javax.swing.JButton();
        btn_clear = new javax.swing.JButton();
        btn_update = new javax.swing.JButton();
        btn_close = new javax.swing.JButton();
        btn_delete = new javax.swing.JButton();
        btn_logout = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jList1 = new javax.swing.JList<>();
        jLabel23 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel36 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        lbl_names = new javax.swing.JLabel();
        lbl_position = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenu2 = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Personal Information", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Serif", 1, 14), new java.awt.Color(102, 102, 102))); // NOI18N

        jLabel1.setText("Admin id");

        jLabel4.setText("Last Name");

        jLabel5.setText("Level");

        txt_admin_id.setEnabled(false);

        jLabel2.setText("First Name");

        jLabel7.setText("Username");

        combo_level.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Choose User Level", "Administrator", "Manager", "Employee" }));

        jLabel8.setText("Password");

        jLabel3.setText("Middle Name");

        jLabel6.setText("Hire Date");

        jLabel11.setText("Email");

        txt_email.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_emailKeyReleased(evt);
            }
        });

        jLabel12.setText("Address");

        jLabel13.setText("Phone");

        txt_phone.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_phoneKeyTyped(evt);
            }
        });

        jDesktopPane1.setBackground(new java.awt.Color(153, 153, 255));

        lbl_profile_pic.setText("NO IMAGE");

        jDesktopPane1.setLayer(lbl_profile_pic, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout jDesktopPane1Layout = new javax.swing.GroupLayout(jDesktopPane1);
        jDesktopPane1.setLayout(jDesktopPane1Layout);
        jDesktopPane1Layout.setHorizontalGroup(
            jDesktopPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jDesktopPane1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lbl_profile_pic, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jDesktopPane1Layout.setVerticalGroup(
            jDesktopPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jDesktopPane1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lbl_profile_pic, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        brn_add_image.setText("Add Image");
        brn_add_image.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                brn_add_imageActionPerformed(evt);
            }
        });

        txt_image_file_path.setEnabled(false);

        jLabel14.setText("Gender");

        combo_gender.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Female", "Male" }));

        txt_position.setDateFormatString("yyyy-MM-dd");

        jLabel9.setText("Birth Date");

        txt_birth_date.setDateFormatString("yyyy-MM-dd");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addGap(18, 18, 18)
                                .addComponent(txt_fn, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addGap(29, 29, 29)
                                .addComponent(txt_admin_id, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txt_mn, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                    .addComponent(jLabel5)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(combo_level, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                    .addComponent(jLabel4)
                                    .addGap(18, 18, 18)
                                    .addComponent(txt_ln, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel11)
                                        .addComponent(jLabel12)
                                        .addComponent(jLabel13))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(txt_email)
                                        .addComponent(txt_address)
                                        .addComponent(txt_phone, javax.swing.GroupLayout.DEFAULT_SIZE, 120, Short.MAX_VALUE)))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jDesktopPane1)
                            .addComponent(brn_add_image)
                            .addComponent(txt_image_file_path, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel8)
                            .addComponent(jLabel14))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txt_password)
                            .addComponent(combo_gender, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel7)
                            .addComponent(jLabel6))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txt_position, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txt_username, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel9)
                        .addGap(18, 18, 18)
                        .addComponent(txt_birth_date, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(txt_admin_id, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(9, 9, 9)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(txt_fn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(txt_mn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4)
                            .addComponent(txt_ln, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5)
                            .addComponent(combo_level, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jDesktopPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel11)
                            .addComponent(txt_email, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel12)
                            .addComponent(txt_address, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel13)
                            .addComponent(txt_phone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel6)
                            .addComponent(txt_position, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel7)
                            .addComponent(txt_username, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel8)
                            .addComponent(txt_password, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(16, 16, 16)
                        .addComponent(brn_add_image)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txt_image_file_path, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel14)
                    .addComponent(combo_gender, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel9)
                    .addComponent(txt_birth_date, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Choose Action", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 14), new java.awt.Color(255, 0, 0))); // NOI18N

        btn_save.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgresource/Button-Add-icon.png"))); // NOI18N
        btn_save.setText("Add New Admin");
        btn_save.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_saveActionPerformed(evt);
            }
        });

        btn_clear.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgresource/Clear-icon.png"))); // NOI18N
        btn_clear.setText("Clear Data");
        btn_clear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_clearActionPerformed(evt);
            }
        });

        btn_update.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgresource/Actions-document-edit-icon.png"))); // NOI18N
        btn_update.setText("Update Info");
        btn_update.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_updateActionPerformed(evt);
            }
        });

        btn_close.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgresource/Button-Close-icon.png"))); // NOI18N
        btn_close.setText("Close");
        btn_close.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_closeActionPerformed(evt);
            }
        });

        btn_delete.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgresource/Button-Delete-icon.png"))); // NOI18N
        btn_delete.setText("Delete");
        btn_delete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_deleteActionPerformed(evt);
            }
        });

        btn_logout.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgresource/Apps-session-logout-icon.png"))); // NOI18N
        btn_logout.setText("Logout");
        btn_logout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_logoutActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btn_save)
                .addGap(2, 2, 2)
                .addComponent(btn_update)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btn_delete)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btn_clear)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btn_close)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btn_logout)
                .addContainerGap(21, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_save)
                    .addComponent(btn_update)
                    .addComponent(btn_delete)
                    .addComponent(btn_clear)
                    .addComponent(btn_close)
                    .addComponent(btn_logout))
                .addContainerGap())
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "List of Admins", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(0, 153, 204))); // NOI18N

        jList1.setBackground(new java.awt.Color(204, 204, 255));
        jList1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jList1MouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jList1);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 298, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1)
                .addContainerGap())
        );

        jLabel23.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel23.setText("UIST Management System");

        jLabel17.setFont(new java.awt.Font("Serif", 3, 12)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(102, 102, 102));
        jLabel17.setText("You're login as an admin");

        jLabel36.setFont(new java.awt.Font("Serif", 1, 14)); // NOI18N
        jLabel36.setText("UIST Management System Admin Teacher Section");

        jLabel10.setFont(new java.awt.Font("Serif", 1, 14)); // NOI18N
        jLabel10.setText("Welcome");

        lbl_names.setFont(new java.awt.Font("Serif", 1, 14)); // NOI18N
        lbl_names.setForeground(new java.awt.Color(0, 102, 204));

        lbl_position.setFont(new java.awt.Font("Serif", 3, 12)); // NOI18N
        lbl_position.setForeground(new java.awt.Color(102, 102, 102));

        jMenu1.setText("File");
        jMenuBar1.add(jMenu1);

        jMenu2.setText("Edit");
        jMenuBar1.add(jMenu2);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel23)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lbl_position, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addComponent(jLabel10)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(lbl_names, javax.swing.GroupLayout.PREFERRED_SIZE, 266, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel36, javax.swing.GroupLayout.PREFERRED_SIZE, 321, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel10)
                    .addComponent(lbl_names, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel36))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(20, 20, 20)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel23)
                                .addComponent(jLabel17))
                            .addComponent(lbl_position, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(21, 21, 21))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(11, 11, 11)
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jList1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jList1MouseClicked
       clickData();
    }//GEN-LAST:event_jList1MouseClicked

    private void brn_add_imageActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_brn_add_imageActionPerformed

        JFileChooser chooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("*.IMAGE", "jpg","gif","png");
        chooser.addChoosableFileFilter(filter);
        //int result = fileChooser.showSaveDialog(null);
        
        int result = chooser.showSaveDialog(null);
        if(result == JFileChooser.APPROVE_OPTION){
        File f = chooser.getSelectedFile();
        fileprofile_pic = f.getAbsolutePath();
        txt_image_file_path.setText(fileprofile_pic);

        ImageIcon imageIcon = new ImageIcon(fileprofile_pic); // load the image to a imageIcon
        java.awt.Image image1 = imageIcon.getImage(); // transform it 
	java.awt.Image newimg = image1.getScaledInstance(120, 120,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
        imageIcon = new ImageIcon(newimg);  // transform it back
        
        
        lbl_profile_pic.setIcon(imageIcon);
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
        
    }//GEN-LAST:event_brn_add_imageActionPerformed

    private void btn_saveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_saveActionPerformed

             emailExist = checkEmailifExxist(txt_email.getText());
             userNamecheck = checkUsername(txt_username.getText());
             String username1 = txt_username.getText();
             String password1 = txt_password.getText();
       if(position.equalsIgnoreCase("Manager")){
        if(!txt_username.getText().isEmpty() && !txt_password.getText().isEmpty() && !txt_fn.getText().isEmpty()
                && !txt_ln.getText().isEmpty() &&!txt_email.getText().isEmpty()&& 
                !((JTextField)txt_position.getDateEditor().getUiComponent()).getText().isEmpty()
                && combo_level.getSelectedIndex() !=0 && !((JTextField)txt_birth_date.getDateEditor().getUiComponent()).getText().isEmpty()){
            
            if(emailStatus){
                if(emailExist){
                   if(username1.length()>=5 || password1.length() >=5){
                      if(username1.length()>=5){
                          txt_username.setBackground(Color.WHITE);
                          if(password1.length() >= 5){
                              txt_password.setBackground(Color.WHITE);
                        if(userNamecheck){    
                          String sql = "INSERT INTO administrator (first_name, last_name, username, password,position, level,"
                                  + "photo,middle_name,email,address,phone,gender,DOB) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?)";
                          try{
                             pst = conn.prepareStatement(sql);
                             
                             pst.setString(1, txt_fn.getText());
                             pst.setString(2,txt_ln.getText());
                             pst.setString(3,txt_username.getText());
                             pst.setString(4,txt_password.getText());
                             pst.setString(5,((JTextField)txt_position.getDateEditor().getUiComponent()).getText());
                             pst.setString(6, combo_level.getSelectedItem().toString());
                             pst.setBytes(7,profile_image);
                             pst.setString(8, txt_mn.getText());
                             pst.setString(9,txt_email.getText());
                             pst.setString(10,txt_address.getText());
                             pst.setString(11, txt_phone.getText());
                             pst.setString(12,combo_gender.getSelectedItem().toString());
                             pst.setString(13, ((JTextField)txt_birth_date.getDateEditor().getUiComponent()).getText());
                             
                             pst.execute();
                             
                             
                             JOptionPane.showMessageDialog(null, "Data Inserted");
                             clearData();
                             
                          }catch(Exception e){
                              JOptionPane.showMessageDialog(null, e);
                          }
                          
                          txt_email.setBackground(Color.WHITE);
                          txt_username.setBackground(Color.WHITE);
                          txt_password.setBackground(Color.WHITE);
                      }else{
                         JOptionPane.showMessageDialog(null, "Username is taken!!!");
                         txt_username.setBackground(Color.RED);
                      }
                      }else{
                             txt_password.setBackground(Color.RED);
                             JOptionPane.showMessageDialog(null, "Your password must be at least 5 characters long!!!");
                      }
                      }else{
                             JOptionPane.showMessageDialog(null, "Your username must be at least 5 characters long!!!");
                             txt_username.setBackground(Color.RED);
                      }
                   }else{
                             JOptionPane.showMessageDialog(null,"Your username and password must be at least 5 characters long");
                             txt_password.setBackground(Color.RED);
                             txt_username.setBackground(Color.RED);
                   }
                }else{                  
                             JOptionPane.showMessageDialog(null, "Email is already registered!!!");               
                }
            }else{                 
                             JOptionPane.showMessageDialog(null, "Your email address doesn't exist!!!");
                             txt_email.setBackground(Color.RED);
                }  
        }else{
                             JOptionPane.showMessageDialog(null, "Please fill all neccessay field correctly");
        }
       }else{
           JOptionPane.showMessageDialog(null, "You do not have clearance for this operation","WARNING",JOptionPane.WARNING_MESSAGE);
       }
        fill_list_box();
    }//GEN-LAST:event_btn_saveActionPerformed
    Boolean emailExist = false;
    Boolean userNamecheck = false;
    private boolean checkEmailifExxist(String email){
            
            String  sqlCheckEmail = "SELECT COUNT(email) FROM administrator WHERE email = '"+email+"'";
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
                txt_email.setBackground(Color.RED);
            }else{
                status = true;
                txt_email.setBackground(Color.WHITE);
            }
            return status;
    }
    private boolean checkUsername(String username){
            String  sqlCheckEmail = "SELECT COUNT(username) FROM administrator WHERE username = '"+username+"'";
            int count = 0;  
            boolean status = false;
            try{
                  pst = conn.prepareStatement(sqlCheckEmail);
                  rs = pst.executeQuery();
                  if(rs.next()){
                       count = rs.getInt("COUNT(username)");
                  }
                  txt_username.setBackground(Color.WHITE);
            }catch(Exception e){
                JOptionPane.showMessageDialog(null,e);
                txt_username.setBackground(Color.RED);
            }
            if(count >=1){
                status = false;
                txt_username.setBackground(Color.RED);
            }else{
                status = true;
                txt_username.setBackground(Color.WHITE);
            }
            return status;    
    }
    private void txt_emailKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_emailKeyReleased
        emailStatus = ValidateEmail.validateEmail(txt_email.getText());
        if(emailStatus){
            txt_email.setBackground(Color.WHITE);
        }else{
            txt_email.setBackground(Color.RED);
        }
    }//GEN-LAST:event_txt_emailKeyReleased

    private void btn_closeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_closeActionPerformed
       close1();
    }//GEN-LAST:event_btn_closeActionPerformed

    private void btn_logoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_logoutActionPerformed
       close2();
    }//GEN-LAST:event_btn_logoutActionPerformed

    private void txt_phoneKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_phoneKeyTyped
         char c = evt.getKeyChar();
        
         if(!(Character.isDigit(c) || c==KeyEvent.VK_BACKSPACE) || c==KeyEvent.VK_DELETE){
             evt.consume();
         }
    }//GEN-LAST:event_txt_phoneKeyTyped
    
    
    private void btn_deleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_deleteActionPerformed
        
        //System.out.println(position + "hello");
         if(!txt_admin_id.getText().isEmpty()){
            try{ 
            if(position.equalsIgnoreCase("Manager")){
               
            try{
                String delete = "DELETE FROM administrator WHERE admin_id = '"+txt_admin_id.getText()+"'";
                int reply = JOptionPane.showConfirmDialog(null, "Administrator with name '"+txt_fn.getText()+"' '"+txt_ln.getText()+"' will be deleted"
                        + "", "Confirm delete", JOptionPane.YES_NO_OPTION);
                if (reply == JOptionPane.YES_OPTION) {
                   try{
                      pst = conn.prepareStatement(delete);
                      pst.execute();
                      clearData();
                      fill_list_box();
                   }catch(Exception e){
                       JOptionPane.showMessageDialog(null,e);
                   }
                }
             
            }catch(Exception e){
            
            }
            }else{
                //
                
                JOptionPane.showMessageDialog(null, "You do not have clearance to delete","WARNING",JOptionPane.WARNING_MESSAGE);
            }
         }catch(Exception e){}
        }else{
             JOptionPane.showMessageDialog(null, "Nothing to delete","WARNING",JOptionPane.WARNING_MESSAGE);   
          //DO NOTHING!!!
        }
    }//GEN-LAST:event_btn_deleteActionPerformed

    private void btn_clearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_clearActionPerformed
        clearData();
    }//GEN-LAST:event_btn_clearActionPerformed
 int checkifDetailsExist = 0;
public int checkUsername(){
    
 String checkEmailAndUsername = "SELECT COUNT(admin_id) FROM administrator WHERE admin_id != '"+txt_admin_id.getText()+"' AND username = '"+txt_username.getText()+"'"; 
   try{
      pst = conn.prepareStatement(checkEmailAndUsername);
      rs = pst.executeQuery();
      if(rs.next()){
          checkifDetailsExist = Integer.parseInt(rs.getString("COUNT(admin_id)"));
      }
   }catch(Exception e){
     JOptionPane.showMessageDialog(null, e);
   }
   return checkifDetailsExist;
}
public int checkEmail(){
     String checkEmailAndUsername = "SELECT COUNT(admin_id) FROM administrator WHERE admin_id != '"+txt_admin_id.getText()+"' AND email = '"+txt_email.getText()+"'"; 
   try{
      pst = conn.prepareStatement(checkEmailAndUsername);
      rs = pst.executeQuery();
      if(rs.next()){
          checkifDetailsExist = Integer.parseInt(rs.getString("COUNT(admin_id)"));
      }
   }catch(Exception e){
    JOptionPane.showMessageDialog(null, e);
   }
 return checkifDetailsExist;
}
    private void btn_updateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_updateActionPerformed
        String username1 = txt_username.getText();
        String password1 = txt_password.getText();
        String fn1 = txt_fn.getText();
        String ln1 = txt_ln.getText();
        String mn1 = txt_mn.getText();
        String email1= txt_email.getText();
        String address1 = txt_address.getText();
        String phone1 = txt_phone.getText();
        String hire_date1 =  ((JTextField)txt_position.getDateEditor().getUiComponent()).getText();
        String birth_date1 = ((JTextField)txt_birth_date.getDateEditor().getUiComponent()).getText();
        String gender1 = combo_gender.getSelectedItem().toString();
        String level1  = combo_level.getSelectedItem().toString();
  
        if(!txt_admin_id.getText().isEmpty()){ 
            try{
                  if(position.equalsIgnoreCase("Manager") && position != null){
                                                if(!txt_fn.getText().isEmpty() && !txt_ln.getText().isEmpty()&& combo_level.getSelectedIndex() !=0 && !txt_email.getText().isEmpty()
                   && !((JTextField)txt_position.getDateEditor().getUiComponent()).getText().isEmpty() && !txt_username.getText().isEmpty()
                   && !txt_password.getText().isEmpty() && !((JTextField)txt_birth_date.getDateEditor().getUiComponent()).getText().isEmpty()){
           if(emailStatus){
                   if(username1.length()>=5 || password1.length() >=5){
                      if(username1.length()>=5){
                          txt_username.setBackground(Color.WHITE);
                          if(password1.length() >= 5){
                              if(checkEmail() == 0){
                              if(checkUsername() == 0){
                              txt_password.setBackground(Color.WHITE);
                                        String update = "UPDATE administrator SET first_name = '"+fn1+"', last_name = '"+ln1+"',middle_name = '"+mn1+"',"
                       + "email = '"+email1+"', address = '"+address1+"', phone = '"+phone1+"',position ='"+hire_date1+"', DOB = '"+birth_date1+"',"
                       + "gender = '"+gender1+"',username = '"+username1+"', password = '"+password1+"',level = '"+level1+"' WHERE admin_id = '"+txt_admin_id.getText()+"'" ;  
               try{
                  pst = conn.prepareStatement(update);
                  pst.execute();
                  
                if(!txt_image_file_path.getText().isEmpty()){
                    byte [] profile_image1 = null;
                        try{
                           File image = new File(txt_image_file_path.getText());
                           FileInputStream fis = new FileInputStream(image);

                           ByteArrayOutputStream bos = new ByteArrayOutputStream(); //converting the file to the right format
                           byte[] buf = new byte[1024]; //giving it a buffer size
                           for(int readNum; (readNum = fis.read(buf))!= -1;){
                               bos.write(buf, 0, readNum);
                           }

                          profile_image1 = bos.toByteArray();
                          
                          String update_image = "UPDATE administrator SET photo = ? WHERE admin_id='"+txt_admin_id.getText()+"'";
                          
                          pst1 = conn.prepareStatement(update_image);
                          pst1.setBytes(1,profile_image1);
                          pst1.execute();
                          
                          txt_image_file_path.setText("");

                       }catch(Exception e){
                         JOptionPane.showMessageDialog(null, e);
                       } 
                    
                }   
                                               
                          txt_email.setBackground(Color.WHITE);
                          txt_username.setBackground(Color.WHITE);
                          txt_password.setBackground(Color.WHITE);
                     JOptionPane.showMessageDialog(null,"Data updated");
               }catch(Exception e){
                   JOptionPane.showMessageDialog(null, e);
               }
                              }else{
                                JOptionPane.showMessageDialog(null, "Your username is already taken!!!", "ERROR",JOptionPane.ERROR_MESSAGE);
                                
                                txt_username.setBackground(Color.RED);   
                              }
                          }else{
                             JOptionPane.showMessageDialog(null, "Your email is in used by another user!!!", "ERROR",JOptionPane.ERROR_MESSAGE);
                             txt_email.setBackground(Color.RED);                 
                          }
                                    
                      }else{
                             txt_password.setBackground(Color.RED);
                             JOptionPane.showMessageDialog(null, "Your password must be at least 5 characters long!!!", "ERROR",JOptionPane.ERROR_MESSAGE);
                      }
                      }else{
                             JOptionPane.showMessageDialog(null, "Your username must be at least 5 characters long!!!", "ERROR",JOptionPane.ERROR_MESSAGE);
                             txt_username.setBackground(Color.RED);
                      }
                   }else{
                             JOptionPane.showMessageDialog(null,"Your username and password must be at least 5 characters long", "ERROR",JOptionPane.ERROR_MESSAGE);
                             txt_password.setBackground(Color.RED);
                             txt_username.setBackground(Color.RED);
                   }
               
            }else{                 
                             JOptionPane.showMessageDialog(null, "Your email address doesn't exist!!!", "ERROR",JOptionPane.ERROR_MESSAGE);
                             txt_email.setBackground(Color.RED);
                }  
        }else{
                             JOptionPane.showMessageDialog(null, "Please fill all neccessay field correctly", "ERROR",JOptionPane.ERROR_MESSAGE);
        }
       }else{
           JOptionPane.showMessageDialog(null, "You do not have clearance for this operation","WARNING",JOptionPane.WARNING_MESSAGE);
       }
       
        }catch(Exception e){}
        }
        else{
         JOptionPane.showMessageDialog(null, "Nothing to update","WARNING",JOptionPane.WARNING_MESSAGE);
        }              
    }//GEN-LAST:event_btn_updateActionPerformed
    
    public void close(){
                   WindowEvent winClosingEvent = new WindowEvent(this,WindowEvent.WINDOW_CLOSING);
                   Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(winClosingEvent);       
    }
    public void close2(){
          close();
         Login frame = new Login();
         frame.setLocationRelativeTo(null);
         frame.setVisible(true);     
    }
    public void close1(){
          close();
         AdminPanel frame = new AdminPanel(username, id);
         frame.setLocationRelativeTo(null);
         frame.setVisible(true);   
    }
    
    public void clearData(){
    txt_email.setText("");
    txt_password.setText("");
    txt_username.setText("");
    txt_fn.setText("");
    txt_ln.setText("");
    txt_mn.setText("");
    ((JTextField)txt_position.getDateEditor().getUiComponent()).setText("");
    ((JTextField)txt_birth_date.getDateEditor().getUiComponent()).setText("");
    combo_gender.setSelectedIndex(0);
    combo_level.setSelectedIndex(0);
    txt_address.setText("");
    txt_phone.setText("");
    txt_image_file_path.setText("");
    lbl_profile_pic.setIcon(null);
    txt_admin_id.setText("");
  }

  public void clickData(){

       String value = (String)jList1.getSelectedValue();
        String first_name = null;
        String lastWord = trim(value.substring(value.lastIndexOf(" ")+1));
        if(value.contains(" ")){
             first_name= value.substring(0, value.indexOf(" ")); 
            }
        
        String sql = "SELECT * FROM administrator WHERE first_name =? AND last_name =?";
        try{
        pst = conn.prepareStatement(sql);
	pst.setString(1, first_name);
        pst.setString(2,lastWord);
	rs = pst.executeQuery();
        if(rs.next()){
                            String id = rs.getString("admin_id");
                            txt_admin_id.setText(id);
                            String fn = rs.getString("first_name");
                            txt_fn.setText(fn);
                            String ln = rs.getString("last_name");
                            txt_ln.setText(ln);
                            String mn = rs.getString("middle_name");
                            txt_mn.setText(mn);
                            String admin_level= rs.getString("level");
                            combo_level.setSelectedItem(admin_level);   
                            String email = rs.getString("email");
                            txt_email.setText(email);   
                            String address = rs.getString("address");
                            txt_address.setText(address);
                            String phone_number = rs.getString("phone");
                            txt_phone.setText(phone_number);                                                     
                            String user_name = rs.getString("username");
                            txt_username.setText(user_name);
                            String password = rs.getString("password");
                            txt_password.setText(password);
                            String gender = rs.getString("gender");
                            combo_gender.setSelectedItem(gender);
                            
                            ((JTextField)txt_position.getDateEditor().getUiComponent()).setText(rs.getString("position"));
                            ((JTextField)txt_birth_date.getDateEditor().getUiComponent()).setText(rs.getString("position"));
                            
                            byte[] imageData = rs.getBytes("photo");
                            InputStream input = rs.getBinaryStream("photo"); 
                            byte[] buffer = new byte[1024];
                            if(input.read(buffer) <= 0){
                              lbl_profile_pic.setIcon(null);
                            }
                            /*if(imageData.length ==0){
                              lbl_pic.setIcon(null);
                            }else{*/
                            
                            
                            else{
                            
                            ImageIcon imageIcon = new ImageIcon(imageData); // load the image to a imageIcon
                            java.awt.Image image1 = imageIcon.getImage(); // transform it 
                            java.awt.Image newimg = image1.getScaledInstance(120, 120,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
                            //imageIcon = new ImageIcon(newimg);  
                            
                            adminPicFormat = new ImageIcon(newimg); // transform it back
                            lbl_profile_pic.setIcon(adminPicFormat);
                        }  
                           
                            
                            
        }
        }catch(Exception e){
            //JOptionPane.showMessageDialog(null,e);
        }
      
  }  
    
  ImageIcon adminPicFormat = null; 
  byte[] profile_image = null;
  String fileprofile_pic  = null;
  String file_name_profile_pic = null;
  
   Boolean emailStatus = false; 
  
  

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton brn_add_image;
    private javax.swing.JButton btn_clear;
    private javax.swing.JButton btn_close;
    private javax.swing.JButton btn_delete;
    private javax.swing.JButton btn_logout;
    private javax.swing.JButton btn_save;
    private javax.swing.JButton btn_update;
    private javax.swing.JComboBox<String> combo_gender;
    private javax.swing.JComboBox<String> combo_level;
    private javax.swing.JDesktopPane jDesktopPane1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel36;
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
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lbl_names;
    private javax.swing.JLabel lbl_position;
    private javax.swing.JLabel lbl_profile_pic;
    private javax.swing.JTextField txt_address;
    private javax.swing.JTextField txt_admin_id;
    private com.toedter.calendar.JDateChooser txt_birth_date;
    private javax.swing.JTextField txt_email;
    private javax.swing.JTextField txt_fn;
    private javax.swing.JTextField txt_image_file_path;
    private javax.swing.JTextField txt_ln;
    private javax.swing.JTextField txt_mn;
    private javax.swing.JPasswordField txt_password;
    private javax.swing.JTextField txt_phone;
    private com.toedter.calendar.JDateChooser txt_position;
    private javax.swing.JTextField txt_username;
    // End of variables declaration//GEN-END:variables
}
