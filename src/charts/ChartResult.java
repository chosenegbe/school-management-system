package charts;

import connection.DBConnection;
import java.awt.Color;
import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JOptionPane;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.ChartRenderingInfo;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.entity.StandardEntityCollection;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.jdbc.JDBCCategoryDataset;

/**
 *
 * @author Chosen Egbe
 */
public class ChartResult extends javax.swing.JFrame {

    Connection conn = null;
    PreparedStatement pst = null;
    ResultSet rs = null;
    PreparedStatement pst1 = null;
    ResultSet rs1 = null;
    PreparedStatement pst2 = null;
    ResultSet rs2 = null;
    PreparedStatement pst3 = null;
    ResultSet rs3 = null;
    PreparedStatement pst4 = null;
    ResultSet rs4 = null; 
    String course_name = null;
    public ChartResult(String course_name1) {
        initComponents();
        conn = DBConnection.connectDB();
        course_name = course_name1;
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
            java.util.logging.Logger.getLogger(ChartResult.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ChartResult.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ChartResult.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ChartResult.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                ChartResult frame = new ChartResult("");
                frame.setLocationRelativeTo(null);
                frame.setTitle("Select how to view statististic");
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

        buttonGroup1 = new javax.swing.ButtonGroup();
        jPanel4 = new javax.swing.JPanel();
        rbrn_3D = new javax.swing.JRadioButton();
        rbtn_2D = new javax.swing.JRadioButton();
        jPanel3 = new javax.swing.JPanel();
        btn_barChart = new javax.swing.JButton();
        btn_lineChart = new javax.swing.JButton();
        btn_pieChart = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Graph mode", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 10), new java.awt.Color(102, 102, 102))); // NOI18N

        buttonGroup1.add(rbrn_3D);
        rbrn_3D.setText("3D Chart");
        rbrn_3D.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbrn_3DActionPerformed(evt);
            }
        });

        buttonGroup1.add(rbtn_2D);
        rbtn_2D.setText("Normal Chart");
        rbtn_2D.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbtn_2DActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(rbtn_2D)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(rbrn_3D)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rbtn_2D)
                    .addComponent(rbrn_3D))
                .addContainerGap())
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Choose type of chart", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 14))); // NOI18N

        btn_barChart.setText("Bar Chart");
        btn_barChart.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_barChartActionPerformed(evt);
            }
        });

        btn_lineChart.setText("Line Graph");
        btn_lineChart.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_lineChartActionPerformed(evt);
            }
        });

        btn_pieChart.setText("Pie Chart");
        btn_pieChart.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_pieChartActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btn_barChart)
                .addGap(18, 18, 18)
                .addComponent(btn_pieChart)
                .addGap(18, 18, 18)
                .addComponent(btn_lineChart)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_barChart)
                    .addComponent(btn_pieChart)
                    .addComponent(btn_lineChart))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(48, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void rbrn_3DActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbrn_3DActionPerformed
        graphSelectionMode = "3dnormal";
    }//GEN-LAST:event_rbrn_3DActionPerformed

    private void rbtn_2DActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbtn_2DActionPerformed
        graphSelectionMode = "normal";
    }//GEN-LAST:event_rbtn_2DActionPerformed

    private void btn_barChartActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_barChartActionPerformed
        barChart();

    }//GEN-LAST:event_btn_barChartActionPerformed

    private void btn_lineChartActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_lineChartActionPerformed
        lineChart();
    }//GEN-LAST:event_btn_lineChartActionPerformed

    private void btn_pieChartActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_pieChartActionPerformed
        pieChart();

    }//GEN-LAST:event_btn_pieChartActionPerformed

    private void barChart(){
     Double grade_5 = 0.0;
     Double grade_6 = 0.0;
     Double grade_7 = 0.0;
     Double grade_8 = 0.0;
     Double grade_9 = 0.0;
     Double grade_10 = 0.0;
      
  
        String sql1 = "SELECT grade_num, COUNT(grade_num) FROM grade WHERE grade_num = '5'  AND course_name = '"+course_name+"'"
                      + " GROUP BY grade_num";
        try{
            pst1 = conn.prepareStatement(sql1);            
            rs1 = pst1.executeQuery();
            if(rs1.next()){
               grade_5 = Double.parseDouble(rs1.getString("COUNT(grade_num)"));
                //System.out.println(count + " hello");
                
            }
        }catch(Exception e){
            JOptionPane.showMessageDialog(null,e);
        }
 
        String sql2 = "SELECT grade_num, COUNT(grade_num) FROM grade WHERE grade_num = '6'  AND course_name = '"+course_name+"'"
                           + " GROUP BY grade_num";
        try{
            pst2 = conn.prepareStatement(sql2);            
            rs2 = pst2.executeQuery();
            if(rs2.next()){
               grade_6 = Double.parseDouble(rs2.getString("COUNT(grade_num)"));
                //System.out.println(count + " hello");
                
            }
        }catch(Exception e){
            JOptionPane.showMessageDialog(null,e);
        }
       String sql3 = "SELECT grade_num, COUNT(grade_num) FROM grade WHERE grade_num = '7'  AND course_name = '"+course_name+"'"
                       + " GROUP BY grade_num";
        try{
            pst3 = conn.prepareStatement(sql3);            
            rs3 = pst3.executeQuery();
            if(rs3.next()){
               grade_7 = Double.parseDouble(rs3.getString("COUNT(grade_num)"));
                //System.out.println(count + " hello");
                
            }
        }catch(Exception e){
            JOptionPane.showMessageDialog(null,e);
        } 
       String sql4 = "SELECT grade_num, COUNT(grade_num) FROM grade WHERE grade_num = '8'  AND course_name = '"+course_name+"'"
                       + " GROUP BY grade_num";
        try{
            pst4 = conn.prepareStatement(sql4);            
            rs4 = pst4.executeQuery();
            if(rs4.next()){
               grade_8 = Double.parseDouble(rs4.getString("COUNT(grade_num)"));
                //System.out.println(count + " hello");
                
            }
        }catch(Exception e){
            JOptionPane.showMessageDialog(null,e);
        } 
        String sql5 = "SELECT grade_num, COUNT(grade_num) FROM grade WHERE grade_num = '9'  AND course_name = '"+course_name+"'"
                        + " GROUP BY grade_num";
        try{
            pst4 = conn.prepareStatement(sql5);            
            rs4 = pst4.executeQuery();
            if(rs4.next()){
               grade_9 = Double.parseDouble(rs4.getString("COUNT(grade_num)"));
                //System.out.println(count + " hello");
                
            }
        }catch(Exception e){
            JOptionPane.showMessageDialog(null,e);
        }
       String sql6 = "SELECT grade_num, COUNT(grade_num) FROM grade WHERE grade_num = '8'  AND course_name = '"+course_name+"'"
                       + " GROUP BY grade_num";
        try{
            pst4 = conn.prepareStatement(sql6);            
            rs4 = pst4.executeQuery();
            if(rs4.next()){
               grade_10 = Double.parseDouble(rs4.getString("COUNT(grade_num)"));
                //System.out.println(count + " hello");
                
            }
        }catch(Exception e){
            JOptionPane.showMessageDialog(null,e);
        } 
                 
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        dataset.setValue(grade_5,"VALUE","5"); //Marks for y-axis and Student1 for x-axis
        dataset.setValue(grade_6,"Value","6");
        dataset.setValue(grade_7,"Value","7");
        dataset.setValue(grade_8,"Value","8");
        dataset.setValue(grade_9,"Value","9");
        dataset.setValue(grade_10,"Value","10");
        
       
        if(buttonGroup1.getSelection() != null){
            
       if(graphSelectionMode.equals("normal")){
        JFreeChart chart = ChartFactory.createBarChart("Grade for "+course_name,"Grade","Number of Students", dataset, PlotOrientation.VERTICAL,false, true, false);
        
        
        
        chart.setBackgroundPaint(Color.GRAY);
        chart.getTitle().setPaint(Color.BLUE);
        
        CategoryPlot p = chart.getCategoryPlot();
        p.setRangeGridlinePaint(Color.BLACK);
        
        ChartFrame frame = new ChartFrame("Bar Chart for course " +course_name,chart);
        frame.setSize(400,400);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);      

        try{
            final ChartRenderingInfo info = new ChartRenderingInfo(new StandardEntityCollection());
            final File file1 = new File(course_name+"\\bar2DChart.png");
            ChartUtilities.saveChartAsPNG(file1, chart, 600, 500,info);
            
            
            
        }catch(Exception e){
            //JOptionPane.showMessageDialog(null, e);
        }
                 
        }else if(graphSelectionMode.equals("3dnormal")){
        JFreeChart chart = ChartFactory.createBarChart3D("Grade for "+course_name,"Grade","Number of Students", dataset, PlotOrientation.VERTICAL,false, true, false);
        chart.setBackgroundPaint(Color.GRAY);
        chart.getTitle().setPaint(Color.BLUE);
        
        CategoryPlot p = chart.getCategoryPlot();
        p.setRangeGridlinePaint(Color.BLACK);
        
        ChartFrame frame = new ChartFrame("Bar Chart for course " +course_name,chart);
        frame.setSize(400,400);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true); 
       }   
        }else{
            JOptionPane.showMessageDialog(null, "Choose Graph mode on the Radio button");
        }      
      
    }
    

    private void pieChart(){
        
     Double grade_5 = 0.0;
     Double grade_6 = 0.0;
     Double grade_7 = 0.0;
     Double grade_8 = 0.0;
     Double grade_9 = 0.0;
     Double grade_10 = 0.0;
      
  
        String sql1 = "SELECT grade_num, COUNT(grade_num) FROM grade WHERE grade_num = '5'  AND course_name = '"+course_name+"'"
                      + " GROUP BY grade_num";
        try{
            pst1 = conn.prepareStatement(sql1);            
            rs1 = pst1.executeQuery();
            if(rs1.next()){
               grade_5 = Double.parseDouble(rs1.getString("COUNT(grade_num)"));
                //System.out.println(count + " hello");
                
            }
        }catch(Exception e){
            JOptionPane.showMessageDialog(null,e);
        }
 
        String sql2 = "SELECT grade_num, COUNT(grade_num) FROM grade WHERE grade_num = '6'  AND course_name = '"+course_name+"'"
                           + " GROUP BY grade_num";
        try{
            pst2 = conn.prepareStatement(sql2);            
            rs2 = pst2.executeQuery();
            if(rs2.next()){
               grade_6 = Double.parseDouble(rs2.getString("COUNT(grade_num)"));
                //System.out.println(count + " hello");
                
            }
        }catch(Exception e){
            JOptionPane.showMessageDialog(null,e);
        }
       String sql3 = "SELECT grade_num, COUNT(grade_num) FROM grade WHERE grade_num = '7'  AND course_name = '"+course_name+"'"
                       + " GROUP BY grade_num";
        try{
            pst3 = conn.prepareStatement(sql3);            
            rs3 = pst3.executeQuery();
            if(rs3.next()){
               grade_7 = Double.parseDouble(rs3.getString("COUNT(grade_num)"));
                //System.out.println(count + " hello");
                
            }
        }catch(Exception e){
            JOptionPane.showMessageDialog(null,e);
        } 
       String sql4 = "SELECT grade_num, COUNT(grade_num) FROM grade WHERE grade_num = '8'  AND course_name = '"+course_name+"'"
                       + " GROUP BY grade_num";
        try{
            pst4 = conn.prepareStatement(sql4);            
            rs4 = pst4.executeQuery();
            if(rs4.next()){
               grade_8 = Double.parseDouble(rs4.getString("COUNT(grade_num)"));
                //System.out.println(count + " hello");
                
            }
        }catch(Exception e){
            JOptionPane.showMessageDialog(null,e);
        } 
        String sql5 = "SELECT grade_num, COUNT(grade_num) FROM grade WHERE grade_num = '9'  AND course_name = '"+course_name+"'"
                        + " GROUP BY grade_num";
        try{
            pst4 = conn.prepareStatement(sql5);            
            rs4 = pst4.executeQuery();
            if(rs4.next()){
               grade_9 = Double.parseDouble(rs4.getString("COUNT(grade_num)"));
                //System.out.println(count + " hello");
                
            }
        }catch(Exception e){
            JOptionPane.showMessageDialog(null,e);
        }
       String sql6 = "SELECT grade_num, COUNT(grade_num) FROM grade WHERE grade_num = '8'  AND course_name = '"+course_name+"'"
                       + " GROUP BY grade_num";
        try{
            pst4 = conn.prepareStatement(sql6);            
            rs4 = pst4.executeQuery();
            if(rs4.next()){
               grade_10 = Double.parseDouble(rs4.getString("COUNT(grade_num)"));
                //System.out.println(count + " hello");
                
            }
        }catch(Exception e){
            JOptionPane.showMessageDialog(null,e);
        } 
        //System.out.println(countCSE + " "+countCNS+ " "+countMIR+ " "+countISVMA);
         
        DefaultPieDataset pieDataset = new DefaultPieDataset();
        pieDataset.setValue("Grade 5", grade_5);
        pieDataset.setValue("Grade 6",grade_6);
        pieDataset.setValue("Grade 7",grade_7);
        pieDataset.setValue("Grade 8",grade_8);
        pieDataset.setValue("Grade 9",grade_9);
        pieDataset.setValue("Grade 10",grade_10);
        if(buttonGroup1.getSelection() != null){
            
       if(graphSelectionMode.equals("normal")){
         JFreeChart chart1 = ChartFactory.createPieChart("Grade for "+course_name, pieDataset, true, true, true); //2D
         PiePlot p = (PiePlot)chart1.getPlot();
        
        /*
            I have used the library import org.jfree.chart.plot.PiePlot3D; instead of this import com.orsoncharts.plot.PiePlot3D due the fact that the 
        later will through the error cannot Convert Plot to PiePlot3D;
        */ 
        ChartFrame frame = new ChartFrame("Pie Chart", chart1);
        frame.setSize(400,400);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);       

        try{
            final ChartRenderingInfo info = new ChartRenderingInfo(new StandardEntityCollection());
            final File file1 = new File(course_name+"\\PieChart.png");
            ChartUtilities.saveChartAsPNG(file1, chart1, 600, 500,info);
            
            
            
        }catch(Exception e){
            //JOptionPane.showMessageDialog(null, e);
        }
                 
        }else if(graphSelectionMode.equals("3dnormal")){
          JFreeChart chart1 = ChartFactory.createPieChart3D("Grade for "+course_name, pieDataset, true, true, true); //3D
         PiePlot3D p = (PiePlot3D)chart1.getPlot();
        //p.setForegroundAlpha(TOP_ALIGNMENT);
        
        ChartFrame frame = new ChartFrame("Pie Chart", chart1);
        frame.setSize(400,400);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true); 
       }

    
        }else{
            JOptionPane.showMessageDialog(null, "Choose Graph mode on the Radio button");
        }
        
        
    

   }
   private void lineChart(){
     Double grade_5 = 0.0;
     Double grade_6 = 0.0;
     Double grade_7 = 0.0;
     Double grade_8 = 0.0;
     Double grade_9 = 0.0;
     Double grade_10 = 0.0;
        
         try{
           String queryChart = "SELECT grade_num, COUNT(grade_num) FROM grade GROUP BY grade_num";
           JDBCCategoryDataset dataset = new JDBCCategoryDataset(DBConnection.connectDB(),queryChart);
           if(buttonGroup1.getSelection() != null){
               
            if(graphSelectionMode.equals("normal")){
                      JFreeChart chart = ChartFactory.createLineChart("Grade for "+course_name, "Grade", "Number of Students", dataset,PlotOrientation.VERTICAL,false,true,true);
           BarRenderer renderer = null;
        
                     /*To ge the orientation*/
           CategoryPlot plot = chart.getCategoryPlot();
           CategoryAxis xAxis = (CategoryAxis)plot.getDomainAxis();
           //xAxis.setCategoryLabelPositions(CategoryLabelPositions.UP_45);
            /*To ge the orientation*/
           
           renderer = new BarRenderer();
           //define frame to show your chart
           ChartFrame frame = new ChartFrame("Grade for "+course_name,chart);
           frame .setSize(400,400);
           frame.setLocationRelativeTo(null);
           frame.setVisible(true);
            
            }else if(graphSelectionMode.equals("3dnormal")){
           JFreeChart chart = ChartFactory.createLineChart3D("Grade for "+course_name, "Grade", "Number of Students", dataset,PlotOrientation.VERTICAL,false,true,true);
           BarRenderer renderer = null;
        
                     /*To ge the orientation*/
           CategoryPlot plot = chart.getCategoryPlot();
           CategoryAxis xAxis = (CategoryAxis)plot.getDomainAxis();
           //xAxis.setCategoryLabelPositions(CategoryLabelPositions.UP_45);
            /*To ge the orientation*/
           
           renderer = new BarRenderer();
           //define frame to show your chart
           ChartFrame frame = new ChartFrame("Grade for "+course_name,chart);
           frame .setSize(400,400);
           frame.setLocationRelativeTo(null);
           frame.setVisible(true);           
            }
 
           }else{
               JOptionPane.showMessageDialog(null,"Choose the view model in the radio button ");
           }
         }catch(Exception e){
             JOptionPane.showMessageDialog(null, e);
         } 
    }    

   String graphSelectionMode = null;
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_barChart;
    private javax.swing.JButton btn_lineChart;
    private javax.swing.JButton btn_pieChart;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JRadioButton rbrn_3D;
    private javax.swing.JRadioButton rbtn_2D;
    // End of variables declaration//GEN-END:variables
}
