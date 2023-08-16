/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package base;
import java.awt.BorderLayout;
import java.awt.Color;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import java.io.File;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
/**
 *
 * @author 1nternetslav3
 */
public class dashboard extends javax.swing.JFrame {

    /**
     * Creates new form dashboard
     */
    public dashboard() {
        initComponents();
        terjual();
        pembelian();
        laba();
        tampilGrafik();
    }

    private void terjual() {

        try {
            String sql = "select sum(grand_total_jual) as harga, WEEK(tanggal) as tgl "
                    + "FROM penjualan group by tgl "
                    + "having tgl = WEEK(NOW())";
            Connection conn = (Connection) koneksi.getKoneksi();
            java.sql.Statement st = conn.createStatement();
            java.sql.ResultSet rs = st.executeQuery(sql);

            if (rs.next()) {
              
              //  txt_terjual.setText("     "+rs.getString("produk"));
                txt_pendapatan.setText(" " + rs.getString("harga"));
                
            }else {
                txt_pendapatan.setText(" " + "0");
            }

        } catch (SQLException | ClassNotFoundException e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }
    
     private void pembelian() {

        try {
            String sql = "select sum(grand_total) as harga, WEEK(tanggal) as tgl "
                    + "FROM pembelian group by tgl "
                    + "having tgl = WEEK(NOW())";
            Connection conn = (Connection) koneksi.getKoneksi();
            java.sql.Statement st = conn.createStatement();
            java.sql.ResultSet rs = st.executeQuery(sql);

            if (rs.next()) {
                
                txt_pengeluaran.setText(" " + rs.getString("harga"));
                
                
            }else {
                txt_pengeluaran.setText(" " + "0");
                
            }

        } catch (SQLException | ClassNotFoundException e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }
        
        
    private void laba() {

        try {
            String sql = "select SUM(grand_total_jual-grand_total_beli) as harga, WEEK(tanggal) as tgl "
                    + "FROM penjualan group by tgl "
                    + "having tgl = WEEK(NOW())";
            Connection conn = (Connection) koneksi.getKoneksi();
            java.sql.Statement st = conn.createStatement();
            java.sql.ResultSet rs = st.executeQuery(sql);

            if (rs.next()) {
              
              //  txt_terjual.setText("     "+rs.getString("produk"));
                txt_laba.setText(" " + rs.getString("harga"));
                
            }else {
                txt_laba.setText(" " + "0");
            }

        } catch (SQLException | ClassNotFoundException e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }
    
    
    private double getPendapatan(int bulan){
        try{
            Connection c = (Connection) koneksi.getKoneksi();
            Statement s = c.createStatement();
            ResultSet r = s.executeQuery("SELECT SUM(grand_total_jual-grand_total_beli) FROM penjualan WHERE MONTH(tanggal) = " + bulan + " AND YEAR(tanggal) = YEAR(NOW())");
            
            if(r.next()){
               return r.getDouble(1);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return 0;
    }

public void tampilGrafik() {       
//        double b12 = this.getPendapatan(12);       
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
//        dataset.setValue(0.10000000, "Contribution Amount", "");
        dataset.setValue(this.getPendapatan(1), "Contribution Amount", "JANUARI");
        dataset.setValue(this.getPendapatan(2), "Contribution Amount", "FEBRUARI");
        dataset.setValue(this.getPendapatan(3), "Contribution Amount", "MARET");
        dataset.setValue(this.getPendapatan(4), "Contribution Amount", "APRIL");
        dataset.setValue(this.getPendapatan(5), "Contribution Amount", "MEI"); 
        dataset.setValue(this.getPendapatan(6), "Contribution Amount", "JUNI");
        dataset.setValue(this.getPendapatan(7), "Contribution Amount", "JULI");
        dataset.setValue(this.getPendapatan(8), "Contribution Amount", "AGUSTUS");
        dataset.setValue(this.getPendapatan(9), "Contribution Amount", "SEPTEMBER");
        dataset.setValue(this.getPendapatan(10), "Contribution Amount", "OKTOBER");
        dataset.setValue(this.getPendapatan(11), "Contribution Amount", "NOVEMBER");
        dataset.setValue(this.getPendapatan(12), "Contribution Amount", "DESEMBER");
        
        JFreeChart barChart = ChartFactory.createBarChart3D("GRAFIK KEUNTUNGAN PER BULAN", "Bulan", "Laba ", dataset, PlotOrientation.HORIZONTAL, false, true, false);
        CategoryPlot p = barChart.getCategoryPlot();
        p.setRangeGridlinePaint(Color.RED);
        
        ChartPanel barPanel = new ChartPanel(barChart);
        jPanel.removeAll();
        jPanel.add(barPanel,BorderLayout.CENTER);
        jPanel.validate();     
    }

private void soundClick(){
    try {
    File sound = new File("C:\\Users\\Windows 10\\Downloads\\MUSIC\\click.wav");
    Clip c = AudioSystem.getClip();
    c.open(AudioSystem.getAudioInputStream(sound));
    c.start();
    } catch (Exception e){       
    }
}
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        txt_pengeluaran = new javax.swing.JTextField();
        txt_pendapatan = new javax.swing.JTextField();
        btn_laporan = new javax.swing.JButton();
        btn_out = new javax.swing.JButton();
        btn_trans = new javax.swing.JButton();
        btn_master = new javax.swing.JButton();
        txt_laba = new javax.swing.JTextField();
        jPanel = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setIconImage(new javax.swing.ImageIcon(getClass().getResource("/ui_component/icon.png")).getImage());
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        txt_pengeluaran.setEditable(false);
        txt_pengeluaran.setBackground(new java.awt.Color(217, 217, 217));
        txt_pengeluaran.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txt_pengeluaran.setBorder(null);
        txt_pengeluaran.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_pengeluaranActionPerformed(evt);
            }
        });
        getContentPane().add(txt_pengeluaran, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 260, 150, 30));

        txt_pendapatan.setEditable(false);
        txt_pendapatan.setBackground(new java.awt.Color(217, 217, 217));
        txt_pendapatan.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txt_pendapatan.setBorder(null);
        txt_pendapatan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_pendapatanActionPerformed(evt);
            }
        });
        getContentPane().add(txt_pendapatan, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 100, 150, 30));

        btn_laporan.setOpaque(false);
        btn_laporan.setContentAreaFilled(false);
        btn_laporan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_laporanActionPerformed(evt);
            }
        });
        getContentPane().add(btn_laporan, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 530, 70, 80));

        btn_out.setOpaque(false);
        btn_out.setContentAreaFilled(false);
        btn_out.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_outActionPerformed(evt);
            }
        });
        getContentPane().add(btn_out, new org.netbeans.lib.awtextra.AbsoluteConstraints(730, 540, 80, 80));

        btn_trans.setOpaque(false);
        btn_trans.setContentAreaFilled(false);
        btn_trans.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_transActionPerformed(evt);
            }
        });
        getContentPane().add(btn_trans, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 540, 70, 80));

        btn_master.setOpaque(false);
        btn_master.setContentAreaFilled(false);
        btn_master.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_masterActionPerformed(evt);
            }
        });
        getContentPane().add(btn_master, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 530, 70, 90));

        txt_laba.setEditable(false);
        txt_laba.setBackground(new java.awt.Color(217, 217, 217));
        txt_laba.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txt_laba.setBorder(null);
        txt_laba.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_labaActionPerformed(evt);
            }
        });
        getContentPane().add(txt_laba, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 410, 150, 30));

        jPanel.setLayout(new java.awt.BorderLayout());
        getContentPane().add(jPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 90, 540, 380));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ui_revisi/dashboard revisi.png"))); // NOI18N
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void txt_pengeluaranActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_pengeluaranActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_pengeluaranActionPerformed

    private void txt_pendapatanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_pendapatanActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_pendapatanActionPerformed

    private void btn_transActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_transActionPerformed
        soundClick();
        dispose();
      new framePenjualan().setVisible(true);
    }//GEN-LAST:event_btn_transActionPerformed

    private void btn_masterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_masterActionPerformed
        soundClick();
        setVisible(false);
      new master().setVisible(true);
    }//GEN-LAST:event_btn_masterActionPerformed

    private void btn_laporanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_laporanActionPerformed
        soundClick();
        setVisible(false);
      new laporan().setVisible(true);
    }//GEN-LAST:event_btn_laporanActionPerformed

    private void btn_outActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_outActionPerformed
        soundClick(); 
        setVisible(false);
      new framePembelian().setVisible(true);
    }//GEN-LAST:event_btn_outActionPerformed

    private void txt_labaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_labaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_labaActionPerformed

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
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(dashboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(dashboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(dashboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(dashboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new dashboard().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_laporan;
    private javax.swing.JButton btn_master;
    private javax.swing.JButton btn_out;
    private javax.swing.JButton btn_trans;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel;
    private javax.swing.JTextField txt_laba;
    private javax.swing.JTextField txt_pendapatan;
    private javax.swing.JTextField txt_pengeluaran;
    // End of variables declaration//GEN-END:variables
}
