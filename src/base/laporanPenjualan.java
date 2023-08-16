/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package base;

import java.sql.Connection;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;
import java.awt.Toolkit;
import java.io.File;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;


/**
 *
 * @author 1nternetslav3
 */
public class laporanPenjualan extends javax.swing.JFrame {

    /**
     * Creates new form laporan
     */
    public laporanPenjualan() {
        initComponents();
        tabelPenjualan();
        tabelBlank();
        
       
    }
    
    private void tabelBlank(){
        DefaultTableModel modelBlank = new DefaultTableModel();
        
        modelBlank.addColumn("ID Penjualan");
        modelBlank.addColumn("Nama Barang");       
        modelBlank.addColumn("Qty");
        modelBlank.addColumn("Harga");
        tabel1.setModel(modelBlank);
    }
    
         private void tabelPenjualan() {
        DefaultTableModel model = new DefaultTableModel();
        
        model.addColumn("ID Penjualan");
     // model.addColumn("Nama Karyawan");       
        model.addColumn("Tanggal");
        model.addColumn("Pemasukan");
        model.addColumn("ID Member");
        try {
            String sql = "SELECT id_penjualan, tanggal, grand_total_jual, id_member "
                   // + "merk_barang, jumlah_barang, total_harga, tanggal "
                    + "FROM penjualan ";
                  //  + "where penjualan.id_karyawan = karyawan.id_karyawan "
                  //  + "order by id_penjualan asc";
            Connection conn = (Connection) koneksi.getKoneksi();
            java.sql.Statement st = conn.createStatement();
            java.sql.ResultSet res = st.executeQuery(sql);
            while (res.next()) {
                model.addRow(new Object[]{res.getString("id_penjualan"),
                    res.getString("tanggal"),
                  //  res.getString("grand_total"),
                    res.getString("grand_total_jual"),
                    res.getString("id_member")});
            }
            tabel2.setModel(model);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage() + "pada tabel penjualan");
        }
        }


        
 
    
     private void pemasukan() {
         
      
         
        String tampilan = "yyyy-MM-dd";
        SimpleDateFormat fm = new SimpleDateFormat(tampilan);
        String tanggal1 = String.valueOf(fm.format(tgl1.getDate()));
        String tanggal2 = String.valueOf(fm.format(tgl2.getDate()));
        
        try {
            String sql = "select sum(grand_total_jual) as harga "
                    + "FROM penjualan "
                    + "WHERE tanggal BETWEEN '" + tanggal1 +"' AND '" + tanggal2 + "'";
            Connection conn = (Connection) koneksi.getKoneksi();
            java.sql.Statement st = conn.createStatement();
            java.sql.ResultSet rs = st.executeQuery(sql);

            if (rs.next()) {
                
                txt_masuk.setText(rs.getString("harga"));
                
            }else {
                txt_masuk.setText("0");
            }

        } catch (SQLException | ClassNotFoundException e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }        
    }
     
        private void laba() {
                 
        String tampilan = "yyyy-MM-dd";
        SimpleDateFormat fm = new SimpleDateFormat(tampilan);
        String tanggal1 = String.valueOf(fm.format(tgl1.getDate()));
        String tanggal2 = String.valueOf(fm.format(tgl2.getDate()));
        
        try {
            String sql = "select sum(grand_total_beli) as beli, sum(grand_total_jual) as jual "
                    + "FROM penjualan "
                    + "WHERE tanggal BETWEEN '" + tanggal1 +"' AND '" + tanggal2 + "'";
            Connection conn = (Connection) koneksi.getKoneksi();
            java.sql.Statement st = conn.createStatement();
            java.sql.ResultSet rs = st.executeQuery(sql);

            if (rs.next()) {
                
                int beli = Integer.parseInt(rs.getString("beli"));
                int jual = Integer.parseInt(rs.getString("jual"));
                int laba = beli - jual;
                this.txt_laba.setText(String.valueOf(jual - beli));
                
            }else {
                txt_laba.setText("0");
            }

        } catch (SQLException | ClassNotFoundException e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }        
    }
        
        
        
        private void terlaris() {
         
      
         
        String tampilan = "yyyy-MM-dd";
        SimpleDateFormat fm = new SimpleDateFormat(tampilan);
        String tanggal1 = String.valueOf(fm.format(tgl1.getDate()));
        String tanggal2 = String.valueOf(fm.format(tgl2.getDate()));
        
        try {
            String sql = "select merk_barang, qty "
                    + "FROM detail_penjualan "
                    + "WHERE tanggal BETWEEN '" + tanggal1 +"' AND '" + tanggal2 + "'"
                    + " ORDER BY qty DESC";
            Connection conn = (Connection) koneksi.getKoneksi();
            java.sql.Statement st = conn.createStatement();
            java.sql.ResultSet rs = st.executeQuery(sql);

            if (rs.next()) {
                
                txt_laris.setText(rs.getString("merk_barang"));
                
            }else {
                txt_laris.setText(" ");
            }

        } catch (SQLException | ClassNotFoundException e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }        
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

        jScrollPane1 = new javax.swing.JScrollPane();
        tabel2 = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        tabel1 = new javax.swing.JTable();
        txt_laba = new javax.swing.JTextField();
        txt_masuk = new javax.swing.JTextField();
        tgl2 = new com.toedter.calendar.JDateChooser();
        tgl1 = new com.toedter.calendar.JDateChooser();
        jButton1 = new javax.swing.JButton();
        btn_out = new javax.swing.JButton();
        btn_dash = new javax.swing.JButton();
        btn_trans = new javax.swing.JButton();
        btn_master = new javax.swing.JButton();
        btn_dash1 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        txt_laris = new javax.swing.JTextField();
        txt_keluar = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setIconImage(new javax.swing.ImageIcon(getClass().getResource("/ui_component/icon.png")).getImage());
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tabel2.setDefaultEditor(Object.class, null);
        tabel2.setModel(new javax.swing.table.DefaultTableModel(
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
        tabel2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabel2MouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tabel2);

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 150, 400, 240));

        tabel1.setDefaultEditor(Object.class, null);
        tabel1.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane2.setViewportView(tabel1);

        getContentPane().add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 150, 410, 240));

        txt_laba.setEditable(false);
        txt_laba.setBackground(new java.awt.Color(217, 217, 217));
        txt_laba.setBorder(null);
        getContentPane().add(txt_laba, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 460, 150, 20));

        txt_masuk.setEditable(false);
        txt_masuk.setBackground(new java.awt.Color(217, 217, 217));
        txt_masuk.setBorder(null);
        getContentPane().add(txt_masuk, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 430, 150, 20));
        getContentPane().add(tgl2, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 80, 140, 40));
        getContentPane().add(tgl1, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 80, 160, 40));

        jButton1.setOpaque(false);
        jButton1.setContentAreaFilled(false);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 80, 110, 40));

        btn_out.setOpaque(false);
        btn_out.setContentAreaFilled(false);
        btn_out.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_outActionPerformed(evt);
            }
        });
        getContentPane().add(btn_out, new org.netbeans.lib.awtextra.AbsoluteConstraints(730, 530, 80, 90));

        btn_dash.setOpaque(false);
        btn_dash.setContentAreaFilled(false);
        btn_dash.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_dashActionPerformed(evt);
            }
        });
        getContentPane().add(btn_dash, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 540, 90, 80));

        btn_trans.setOpaque(false);
        btn_trans.setContentAreaFilled(false);
        btn_trans.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_transActionPerformed(evt);
            }
        });
        getContentPane().add(btn_trans, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 530, 70, 90));

        btn_master.setOpaque(false);
        btn_master.setContentAreaFilled(false);
        btn_master.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_masterActionPerformed(evt);
            }
        });
        getContentPane().add(btn_master, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 540, 70, 80));

        btn_dash1.setOpaque(false);
        btn_dash1.setContentAreaFilled(false);
        btn_dash1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_dash1ActionPerformed(evt);
            }
        });
        getContentPane().add(btn_dash1, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 530, 90, 80));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ui_revisi/laporan jual.png"))); // NOI18N
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        txt_laris.setBackground(new java.awt.Color(217, 217, 217));
        getContentPane().add(txt_laris, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 600, 50, 30));

        txt_keluar.setBackground(new java.awt.Color(217, 217, 217));
        txt_keluar.setBorder(null);
        getContentPane().add(txt_keluar, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 560, 30, 20));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed

        
        // tabel penjualan
        
        DefaultTableModel model1 = new DefaultTableModel();
        model1.addColumn("ID Penjualan");
        model1.addColumn("Tanggal");
        model1.addColumn("Pemasukan");
    //    model1.addColumn("Harga");
        
        String tampilan1 = "yyyy-MM-dd";
        SimpleDateFormat fm1 = new SimpleDateFormat(tampilan1);
        String tanggal1_1 = String.valueOf(fm1.format(tgl1.getDate()));
        String tanggal2_1 = String.valueOf(fm1.format(tgl2.getDate()));
        
        try{ 
        String sql = "SELECT * FROM penjualan WHERE tanggal BETWEEN '" + tanggal1_1 +"' AND '" + tanggal2_1 + "'" ;
        Connection conn = (Connection) koneksi.getKoneksi();
        java.sql.Statement st = conn.createStatement();
        java.sql.ResultSet rs = st.executeQuery(sql);
        //System.out.println(sql);
        
            while (rs.next()) {
                model1.addRow(new Object[]{rs.getString(1), rs.getString(2), rs.getString(3)});
            }
            tabel2.setModel(model1);
            pemasukan();
            laba();
           // terlaris();
           
        
       }catch(SQLException | ClassNotFoundException e){
           JOptionPane.showMessageDialog(this, e.getMessage());
       }
        
        DefaultTableModel modelBlank = new DefaultTableModel();
        
        modelBlank.addColumn("ID Penjualan");
        modelBlank.addColumn("Nama Barang");       
        modelBlank.addColumn("Qty");
        modelBlank.addColumn("Harga");
        tabel1.setModel(modelBlank);
        
     
    }//GEN-LAST:event_jButton1ActionPerformed

    private void btn_outActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_outActionPerformed
        soundClick();
        setVisible(false);
        new framePembelian().setVisible(true);
    }//GEN-LAST:event_btn_outActionPerformed

    private void btn_masterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_masterActionPerformed
     soundClick();
        setVisible(false);
       new master().setVisible(true);
    }//GEN-LAST:event_btn_masterActionPerformed

    private void btn_transActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_transActionPerformed
        soundClick();
        setVisible(false);
        new framePenjualan().setVisible(true);
    }//GEN-LAST:event_btn_transActionPerformed

    private void btn_dashActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_dashActionPerformed
        soundClick();
        setVisible(false);
        new dashboard().setVisible(true);
    }//GEN-LAST:event_btn_dashActionPerformed

    private void tabel2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabel2MouseClicked
        String idpenjualan = tabel2.getValueAt(tabel2.getSelectedRow(), 0).toString();         
        DefaultTableModel model = new DefaultTableModel();
        
        model.addColumn("ID Penjualan");
        model.addColumn("Nama Barang");       
        model.addColumn("Qty");
        model.addColumn("Harga");
    //    model.addColumn("Nama Member");
        try {
            String sql = "SELECT id_penjualan, merk_barang, sub_total, qty "
                   // + "merk_barang, jumlah_barang, total_harga, tanggal "
                    + "FROM detail_penjualan "
                   + "where id_penjualan ='" + idpenjualan + "'";
                  //  + "order by id_penjualan asc";
            Connection conn = (Connection) koneksi.getKoneksi();
            java.sql.Statement st = conn.createStatement();
            java.sql.ResultSet res = st.executeQuery(sql);
            while (res.next()) {
                model.addRow(new Object[]{res.getString("id_penjualan"),
                    res.getString("merk_barang"),
                    res.getString("qty"),
                    res.getString("sub_total")
                  //  res.getString("id_member")
                            });
            }
            tabel1.setModel(model);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage() + "pada tabel penjualan");
        }
        
    }//GEN-LAST:event_tabel2MouseClicked

    private void btn_dash1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_dash1ActionPerformed
        soundClick();
        setVisible(false);
        new laporan().setVisible(true);
    }//GEN-LAST:event_btn_dash1ActionPerformed

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
            java.util.logging.Logger.getLogger(laporanPenjualan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(laporanPenjualan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(laporanPenjualan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(laporanPenjualan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new laporanPenjualan().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_dash;
    private javax.swing.JButton btn_dash1;
    private javax.swing.JButton btn_master;
    private javax.swing.JButton btn_out;
    private javax.swing.JButton btn_trans;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable tabel1;
    private javax.swing.JTable tabel2;
    private com.toedter.calendar.JDateChooser tgl1;
    private com.toedter.calendar.JDateChooser tgl2;
    private javax.swing.JTextField txt_keluar;
    private javax.swing.JTextField txt_laba;
    private javax.swing.JTextField txt_laris;
    private javax.swing.JTextField txt_masuk;
    // End of variables declaration//GEN-END:variables
}
