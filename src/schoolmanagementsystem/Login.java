
package schoolmanagementsystem;

/**
 *
 * @author Chosen Egbe
 */

import AdminWelcomepage.AdminPanel;
import connection.DBConnection;
import java.awt.Toolkit;
import java.sql.*;
import java.awt.event.*;
import javax.swing.JOptionPane;
import student.StudentSection;
import teacher.TeacherSection;

public class Login extends javax.swing.JFrame {
    
    	Connection conn = null;
	ResultSet rs = null;
	PreparedStatement pst = null;
	
    public Login() {
        initComponents();
        conn = DBConnection.connectDB();
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
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
               
                Login frame = new Login();
                frame.setLocationRelativeTo(null);
                frame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
                frame.setVisible(true);
                
            }
        });
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        btn_login = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        combo_select_user = new javax.swing.JComboBox<>();
        user_name = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        password_login = new javax.swing.JPasswordField();
        jLabel3 = new javax.swing.JLabel();
        jLabel49 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenu2 = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Login", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 14), new java.awt.Color(51, 204, 255))); // NOI18N

        btn_login.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgresource/animation-track-iconlogin.png"))); // NOI18N
        btn_login.setText("Login");
        btn_login.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_loginActionPerformed(evt);
            }
        });

        jLabel2.setText("Password");

        combo_select_user.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Student", "Teacher", "Admin" }));

        jLabel1.setText("Username/Email");

        password_login.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                password_loginKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel2)
                    .addComponent(btn_login, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(combo_select_user, 0, 121, Short.MAX_VALUE)
                    .addComponent(user_name)
                    .addComponent(password_login))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(user_name, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(password_login, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(combo_select_user, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_login))
                .addContainerGap(56, Short.MAX_VALUE))
        );

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgresource/school-management-system-masters-logo_2.png"))); // NOI18N

        jLabel49.setFont(new java.awt.Font("Serif", 1, 14)); // NOI18N
        jLabel49.setText("UIST Management System");

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgresource/Button-Close-icon.png"))); // NOI18N
        jButton1.setText("Close");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
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
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 245, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jButton1, javax.swing.GroupLayout.Alignment.LEADING))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel49)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton1)
                .addGap(28, 28, 28)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel49))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btn_loginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_loginActionPerformed
     String username = null;
     String password = null;
     
     String id = null;
     
       if(combo_select_user.getSelectedIndex() ==0){
          String sql = "SELECT * FROM students WHERE username =? AND password =?";
     
            try{
				  pst = conn.prepareStatement(sql);
				  pst.setString(1, user_name.getText());
				  pst.setString(2, password_login.getText());
				  
				  rs = pst.executeQuery();
				  if(rs.next()){
					  //JOptionPane.showMessageDialog(null, "Username and password is correct ");
					   username = rs.getString("username");
                                           password = rs.getString("password");
                                           id = rs.getString("id");
					  rs.close();
					  pst.close();
					  
					  close(); //close the login window before opening the employee window
					  
					 StudentSection ap = new StudentSection(username, id);
                                         ap.setLocationRelativeTo(null);
					 ap.setVisible(true);
					  
				  }else{
					  JOptionPane.showMessageDialog(null, "Username and password is NOT correct ", "ERROR",JOptionPane.ERROR_MESSAGE);				  
				  }
        
            }catch(Exception e){
                  JOptionPane.showMessageDialog(null, e);
            }finally{
                        try{
                            rs.close();
                            pst.close();
                            }catch(Exception e){
                    }                  
            }
       }else if(combo_select_user.getSelectedIndex() ==1){
          String sql = "SELECT * FROM teachers WHERE user_name =? AND password =?";
     
            try{
				  pst = conn.prepareStatement(sql);
				  pst.setString(1, user_name.getText());
				  pst.setString(2, password_login.getText());
				  
				  rs = pst.executeQuery();
				  if(rs.next()){
					  //JOptionPane.showMessageDialog(null, "Username and password is correct ");
					  username = rs.getString("user_name");
                                          password = rs.getString("password");
                                          id = rs.getString("id");
					  rs.close();
					  pst.close();
					  
					  close(); //close the login window before opening the employee window
					  
					 TeacherSection ap = new TeacherSection(username, id);
                                         ap.setLocationRelativeTo(null);
					 ap.setVisible(true);
					  
				  }else{
					  JOptionPane.showMessageDialog(null, "Username and password is NOT correct ", "ERROR",JOptionPane.ERROR_MESSAGE);
					  
					  
					  
				  }
        
            }catch(Exception e){
                  JOptionPane.showMessageDialog(null, e);
            }finally{
                        try{
                            rs.close();
                            pst.close();
                            }catch(Exception e){
                    }                  
            }      
       }
       else if(combo_select_user.getSelectedIndex() ==2){
        String sql = "SELECT * FROM administrator WHERE username =? AND password =?";
     
            try{
				  pst = conn.prepareStatement(sql);
				  pst.setString(1, user_name.getText());
				  pst.setString(2, password_login.getText());
				  
				  rs = pst.executeQuery();
				  if(rs.next()){
					  //JOptionPane.showMessageDialog(null, "Username and password is correct ");
					   username = rs.getString("username");
                                           password = rs.getString("password");
                                           id = rs.getString("admin_id");					  
					  rs.close();
					  pst.close();
					  
					  close(); //close the login window before opening the employee window
					  
					 AdminPanel ap = new AdminPanel(username , id);
                                         ap.setLocationRelativeTo(null);
					 ap.setVisible(true);
					  
				  }else{
					   JOptionPane.showMessageDialog(null, "Username and password is NOT correct ", "ERROR",JOptionPane.ERROR_MESSAGE);
					  
					  
					  
				  }
        
            }catch(Exception e){
                  JOptionPane.showMessageDialog(null, e);
            }finally{
                        try{
                            rs.close();
                            pst.close();
                            }catch(Exception e){
                    }                  
            }       
       }
        
    }//GEN-LAST:event_btn_loginActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        close();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void password_loginKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_password_loginKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
    String username = null;
     String password = null;
     
     String id = null;
     
       if(combo_select_user.getSelectedIndex() ==0){
          String sql = "SELECT * FROM students WHERE username =? AND password =?";
     
            try{
				  pst = conn.prepareStatement(sql);
				  pst.setString(1, user_name.getText());
				  pst.setString(2, password_login.getText());
				  
				  rs = pst.executeQuery();
				  if(rs.next()){
					  //JOptionPane.showMessageDialog(null, "Username and password is correct ");
					   username = rs.getString("username");
                                           password = rs.getString("password");
                                           id = rs.getString("id");
					  rs.close();
					  pst.close();
					  
					  close(); //close the login window before opening the employee window
					  
					 StudentSection ap = new StudentSection(username, id);
                                         ap.setLocationRelativeTo(null);
					 ap.setVisible(true);
					  
				  }else{
					  JOptionPane.showMessageDialog(null, "Username and password is NOT correct ", "ERROR",JOptionPane.ERROR_MESSAGE);				  
				  }
        
            }catch(Exception e){
                  JOptionPane.showMessageDialog(null, e);
            }finally{
                        try{
                            rs.close();
                            pst.close();
                            }catch(Exception e){
                    }                  
            }
       }else if(combo_select_user.getSelectedIndex() ==1){
          String sql = "SELECT * FROM teachers WHERE user_name =? AND password =?";
     
            try{
				  pst = conn.prepareStatement(sql);
				  pst.setString(1, user_name.getText());
				  pst.setString(2, password_login.getText());
				  
				  rs = pst.executeQuery();
				  if(rs.next()){
					  //JOptionPane.showMessageDialog(null, "Username and password is correct ");
					  username = rs.getString("user_name");
                                          password = rs.getString("password");
                                          id = rs.getString("id");
					  rs.close();
					  pst.close();
					  
					  close(); //close the login window before opening the employee window
					  
					 TeacherSection ap = new TeacherSection(username, id);
                                         ap.setLocationRelativeTo(null);
					 ap.setVisible(true);
					  
				  }else{
					  JOptionPane.showMessageDialog(null, "Username and password is NOT correct ", "ERROR",JOptionPane.ERROR_MESSAGE);
					  
					  
					  
				  }
        
            }catch(Exception e){
                  JOptionPane.showMessageDialog(null, e);
            }finally{
                        try{
                            rs.close();
                            pst.close();
                            }catch(Exception e){
                    }                  
            }      
       }
       else if(combo_select_user.getSelectedIndex() ==2){
        String sql = "SELECT * FROM administrator WHERE username =? AND password =?";
     
            try{
				  pst = conn.prepareStatement(sql);
				  pst.setString(1, user_name.getText());
				  pst.setString(2, password_login.getText());
				  
				  rs = pst.executeQuery();
				  if(rs.next()){
					  //JOptionPane.showMessageDialog(null, "Username and password is correct ");
					   username = rs.getString("username");
                                           password = rs.getString("password");
                                           id = rs.getString("admin_id");					  
					  rs.close();
					  pst.close();
					  
					  close(); //close the login window before opening the employee window
					  
					 AdminPanel ap = new AdminPanel(username , id);
                                         ap.setLocationRelativeTo(null);
					 ap.setVisible(true);
					  
				  }else{
					   JOptionPane.showMessageDialog(null, "Username and password is NOT correct ", "ERROR",JOptionPane.ERROR_MESSAGE);
					  
					  
					  
				  }
        
            }catch(Exception e){
                  JOptionPane.showMessageDialog(null, e);
            }finally{
                        try{
                            rs.close();
                            pst.close();
                            }catch(Exception e){
                    }                  
            }       
       }       
        
        }
    }//GEN-LAST:event_password_loginKeyPressed
public void close(){
		WindowEvent winClosingEvent = new WindowEvent(this,WindowEvent.WINDOW_CLOSING);
		Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(winClosingEvent);
	}



    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_login;
    private javax.swing.JComboBox<String> combo_select_user;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel49;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPasswordField password_login;
    private javax.swing.JTextField user_name;
    // End of variables declaration//GEN-END:variables
}
