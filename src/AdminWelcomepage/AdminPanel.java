package AdminWelcomepage;

import admin.AdminSection;
import connection.DBConnection;
import java.awt.Toolkit;
import java.awt.event.WindowEvent;
import java.sql.*;
import java.net.*;
import javax.swing.JOptionPane;
import schoolmanagementsystem.Login;

public class AdminPanel extends javax.swing.JFrame {
    
    Connection conn = null;
    PreparedStatement pst = null;
    ResultSet rs = null;
    
    String id = null;
    String username = null;
    String password = null;
    public AdminPanel(String username1,  String id1) {
        initComponents();
         conn = DBConnection.connectDB();
         
        String getAdmissionNumber = "SELECT * FROM administrator WHERE username = '"+username1+"' AND admin_id = '"+id1+"'";
        
        try{
          pst = conn.prepareStatement(getAdmissionNumber);
          rs = pst.executeQuery();
          if(rs.next()){
              id = rs.getString("admin_id");
          }
        }catch(Exception e){
        
        }         
         
         getAdminDetails(username1, id1);
         
         username = username1;
         id = id1;
    }
    
    public void getAdminDetails(String username, String id){
     String sql = "SELECT * FROM administrator WHERE username = '"+username+"' AND admin_id = '"+id+"'";
     
     try{
         pst = conn.prepareStatement(sql);
         rs = pst.executeQuery();
         if(rs.next()){
             txt_admin_name.setText(rs.getString("first_name") + " "+ rs.getString("last_name"));
         }
     }catch(Exception e){
         
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
            java.util.logging.Logger.getLogger(AdminPanel.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AdminPanel.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AdminPanel.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AdminPanel.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                
                AdminPanel frame = new AdminPanel("username","id");
                frame.setLocationRelativeTo(null);
                frame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
                frame.setVisible(true);
            }
    });
    }
    public void close(){
                    WindowEvent winClosingEvent = new WindowEvent(this,WindowEvent.WINDOW_CLOSING);
                    Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(winClosingEvent);
            }
    public void close1(){
         //new AdminPanel().setVisible(false);
         //new AdminPanel().setDefaultCloseOperation(HIDE_ON_CLOSE);
         close();
         Login frame = new Login();
         frame.setLocationRelativeTo(null);
         frame.setVisible(true);
         
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        student_section = new javax.swing.JLabel();
        teacher_section = new javax.swing.JLabel();
        btn_student = new javax.swing.JButton();
        btn_teacher = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        btn_logout = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        txt_admin_name = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenu2 = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        student_section.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgresource/student.png"))); // NOI18N

        teacher_section.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgresource/teacher.png"))); // NOI18N

        btn_student.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btn_student.setForeground(new java.awt.Color(102, 102, 102));
        btn_student.setText("Student");
        btn_student.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_studentActionPerformed(evt);
            }
        });

        btn_teacher.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btn_teacher.setForeground(new java.awt.Color(102, 102, 102));
        btn_teacher.setText("Teacher");
        btn_teacher.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_teacherActionPerformed(evt);
            }
        });

        jButton1.setBackground(new java.awt.Color(204, 204, 204));
        jButton1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jButton1.setForeground(new java.awt.Color(153, 153, 153));
        jButton1.setText("Manage Admin");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        btn_logout.setFont(new java.awt.Font("Serif", 1, 10)); // NOI18N
        btn_logout.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgresource/Apps-session-logout-icon.png"))); // NOI18N
        btn_logout.setText("Logout");
        btn_logout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_logoutActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Serif", 1, 12)); // NOI18N
        jLabel1.setText("Welcome");

        txt_admin_name.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txt_admin_name.setForeground(new java.awt.Color(0, 153, 204));

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
                .addGap(28, 28, 28)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btn_student)
                    .addComponent(student_section, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(99, 99, 99)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btn_teacher, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(teacher_section, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)))
            .addGroup(layout.createSequentialGroup()
                .addGap(57, 57, 57)
                .addComponent(jLabel1)
                .addGap(10, 10, 10)
                .addComponent(txt_admin_name, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btn_logout))
            .addGroup(layout.createSequentialGroup()
                .addGap(100, 100, 100)
                .addComponent(jButton1))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addComponent(btn_logout))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(txt_admin_name, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(teacher_section, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(student_section, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btn_student)
                    .addComponent(btn_teacher))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btn_studentActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_studentActionPerformed
                    close();
                    Student s = new Student(username,id);
                    s.setLocationRelativeTo(null);
                    s.setVisible(true);
               
    }//GEN-LAST:event_btn_studentActionPerformed

    private void btn_teacherActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_teacherActionPerformed
                    
                    close();
                    Teacher s = new Teacher(username,id);
                    s.setLocationRelativeTo(null);
                    s.setVisible(true);
               
    }//GEN-LAST:event_btn_teacherActionPerformed

    private void btn_logoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_logoutActionPerformed
        close1();
    }//GEN-LAST:event_btn_logoutActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
                    close();
                    AdminSection s = new AdminSection(username,id);
                    s.setLocationRelativeTo(null);
                    s.setVisible(true);
    }//GEN-LAST:event_jButton1ActionPerformed



    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_logout;
    private javax.swing.JButton btn_student;
    private javax.swing.JButton btn_teacher;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JLabel student_section;
    private javax.swing.JLabel teacher_section;
    private javax.swing.JLabel txt_admin_name;
    // End of variables declaration//GEN-END:variables
}
