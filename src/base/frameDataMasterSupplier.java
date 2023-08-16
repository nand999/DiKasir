/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package base;
import java.awt.event.KeyEvent;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import java.awt.Toolkit;
import java.io.File;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
/**
 *
 * @author 1nternetslav3
 */
public class frameDataMasterSupplier extends javax.swing.JFrame {

    /**
     * Creates new form frameDataMasterSupplier
     */
    public frameDataMasterSupplier() {
        initComponents();
        load_table();
        idsupplier();
    }
    
     private void load_table(){
    
        DefaultTableModel model = new DefaultTableModel();
        
        model.addColumn("ID Supplier");
        model.addColumn("Alamat Supplier");
        model.addColumn("Nama Supplier");
        model.addColumn("No. Telp");
        
        
        //menampilkan data database kedalam tabel
        try {
            int no=1;
            String sql = "select * from supplier";
            java.sql.Connection conn=(Connection)koneksi.getKoneksi();
            java.sql.Statement stm=conn.createStatement();
            java.sql.ResultSet res=stm.executeQuery(sql);
            while(res.next()){
                model.addRow(new Object[]{res.getString(1),res.getString(2),res.getString(3),res.getString(4)});
            }
            table.setModel(model);
        } catch (Exception e) {
        } }
     
     
     
    private void kosong(){
        txt_idsupplier.setText(null);
        txt_namasupplier.setText(null);
        txt_alamat.setText(null);
        txt_notelp.setText(null);      
        }
        
    private void idsupplier() {
        try {
            String sql = "SELECT id_supplier FROM supplier order by id_supplier desc";
            Connection conn = (Connection) koneksi.getKoneksi();
            java.sql.Statement stm = conn.createStatement();
            java.sql.ResultSet rs = stm.executeQuery(sql);

            if (rs.next()) {
                String Idno = rs.getString("id_supplier").substring(2);
                String AN = "" + (Integer.parseInt(Idno) + 1);
                String Nol = "";
                if (AN.length() == 1) {
                    Nol = "000";
                } else if (AN.length() == 2) {
                    Nol = "00";
                } else if (AN.length() == 3) {
                    Nol = "0";
                } else if (AN.length() == 4) {
                    Nol = "";
                }

                txt_idsupplier.setText("SP" + Nol + AN);
            } else {
                txt_idsupplier.setText("SP0001");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
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
        table = new javax.swing.JTable();
        txt_alamat = new javax.swing.JTextField();
        txt_idsupplier = new javax.swing.JTextField();
        txt_namasupplier = new javax.swing.JTextField();
        txt_notelp = new javax.swing.JTextField();
        btn_tambah = new javax.swing.JButton();
        btn_hapus = new javax.swing.JButton();
        btn_laporan = new javax.swing.JButton();
        btn_dashboard = new javax.swing.JButton();
        btn_transaksi = new javax.swing.JButton();
        btn_datamaster = new javax.swing.JButton();
        btn_edit = new javax.swing.JButton();
        btn_clear = new javax.swing.JButton();
        btn_laporan1 = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setIconImage(new javax.swing.ImageIcon(getClass().getResource("/ui_component/icon.png")).getImage());
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        table.setDefaultEditor(Object.class, null);
        table.setModel(new javax.swing.table.DefaultTableModel(
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
        table.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(table);

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 80, 500, 390));

        txt_alamat.setBackground(new java.awt.Color(217, 217, 217));
        txt_alamat.setBorder(null);
        getContentPane().add(txt_alamat, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 230, 210, -1));

        txt_idsupplier.setBackground(new java.awt.Color(217, 217, 217));
        txt_idsupplier.setBorder(null);
        getContentPane().add(txt_idsupplier, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 110, 210, -1));

        txt_namasupplier.setBackground(new java.awt.Color(217, 217, 217));
        txt_namasupplier.setBorder(null);
        txt_namasupplier.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_namasupplierActionPerformed(evt);
            }
        });
        getContentPane().add(txt_namasupplier, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 170, 210, 20));

        txt_notelp.setBackground(new java.awt.Color(217, 217, 217));
        txt_notelp.setBorder(null);
        getContentPane().add(txt_notelp, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 290, 210, -1));

        btn_tambah.setOpaque(false);
        btn_tambah.setContentAreaFilled(false);
        btn_tambah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_tambahActionPerformed(evt);
            }
        });
        getContentPane().add(btn_tambah, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 390, 80, 30));

        btn_hapus.setOpaque(false);
        btn_hapus.setContentAreaFilled(false);
        btn_hapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_hapusActionPerformed(evt);
            }
        });
        getContentPane().add(btn_hapus, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 390, 80, 30));

        btn_laporan.setOpaque(false);
        btn_laporan.setContentAreaFilled(false);
        btn_laporan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_laporanActionPerformed(evt);
            }
        });
        getContentPane().add(btn_laporan, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 520, 60, 80));

        btn_dashboard.setOpaque(false);
        btn_dashboard.setContentAreaFilled(false);
        btn_dashboard.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_dashboardActionPerformed(evt);
            }
        });
        getContentPane().add(btn_dashboard, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 530, 70, 60));

        btn_transaksi.setOpaque(false);
        btn_transaksi.setContentAreaFilled(false);
        btn_transaksi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_transaksiActionPerformed(evt);
            }
        });
        getContentPane().add(btn_transaksi, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 530, 70, 60));

        btn_datamaster.setOpaque(false);
        btn_datamaster.setContentAreaFilled(false);
        btn_datamaster.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_datamasterActionPerformed(evt);
            }
        });
        getContentPane().add(btn_datamaster, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 530, 60, 70));

        btn_edit.setOpaque(false);
        btn_edit.setContentAreaFilled(false);
        btn_edit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_editActionPerformed(evt);
            }
        });
        getContentPane().add(btn_edit, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 390, 70, 30));

        btn_clear.setOpaque(false);
        btn_clear.setContentAreaFilled(false);
        btn_clear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_clearActionPerformed(evt);
            }
        });
        getContentPane().add(btn_clear, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 440, 250, 30));

        btn_laporan1.setOpaque(false);
        btn_laporan1.setContentAreaFilled(false);
        btn_laporan1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_laporan1ActionPerformed(evt);
            }
        });
        getContentPane().add(btn_laporan1, new org.netbeans.lib.awtextra.AbsoluteConstraints(740, 520, 70, 90));

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ui_revisi/master supplier revisi.png"))); // NOI18N
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, -10, -1, -1));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btn_datamasterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_datamasterActionPerformed
        soundClick();
        dispose();
      new dashboard().setVisible(true);
    }//GEN-LAST:event_btn_datamasterActionPerformed

    private void btn_transaksiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_transaksiActionPerformed
        soundClick();
        dispose();
      new laporan().setVisible(true);
    }//GEN-LAST:event_btn_transaksiActionPerformed

    private void btn_tambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_tambahActionPerformed
        String notelp1 = txt_notelp.getText();       
        char[] chars = notelp1.toCharArray();
            
        if (txt_idsupplier.getText().equals("")|| txt_namasupplier.getText().equals("") || txt_notelp.getText().equals("")){
            JOptionPane.showMessageDialog(null,"lengkapi data"); 
        } else if(chars[0]!='0'){
                JOptionPane.showMessageDialog(null, "Format nomor dimulai dengan 08");
                txt_notelp.setText("");
        }else if(chars.length<12){
                JOptionPane.showMessageDialog(null, "Format nomor minimal 12 angka");                
        }else if(chars.length>13){
                JOptionPane.showMessageDialog(null, "Format nomor maksimal 13 angka");
        }
        
       else{
        try {
            String sql = "INSERT INTO supplier VALUES ('"+txt_idsupplier.getText()+"','"+txt_alamat.getText()+"','"+txt_namasupplier.getText()+"','"+txt_notelp.getText()+"')";
            java.sql.Connection conn=(Connection)koneksi.getKoneksi();
            java.sql.PreparedStatement pst=conn.prepareStatement(sql);
            pst.execute();
            JOptionPane.showMessageDialog(null, "Penyimpanan Data Berhasil");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "terjadi kesalahan");
        }
            load_table();
            kosong();
            idsupplier();}
    }//GEN-LAST:event_btn_tambahActionPerformed

    private void btn_hapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_hapusActionPerformed
            int n = JOptionPane.showConfirmDialog(
            null,
            "apakah anda yakin untuk menghapus?",
            "konfirmasi hapus",
            JOptionPane.YES_NO_OPTION);

        if( n == JOptionPane.YES_OPTION){
                  try {
            String sql ="delete from supplier where id_supplier='"+txt_idsupplier.getText()+"'";
            java.sql.Connection conn=(Connection)koneksi.getKoneksi();
            java.sql.PreparedStatement pst=conn.prepareStatement(sql);
            pst.execute();
            JOptionPane.showMessageDialog(this, "berhasil di hapus");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
        }
        else {
            JOptionPane.showMessageDialog(null, "hapus dibatalkan");
        }
        
        
        

        load_table();
        kosong();
        idsupplier();
    }//GEN-LAST:event_btn_hapusActionPerformed

    private void btn_editActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_editActionPerformed
             try {
            String sql = "UPDATE supplier SET nama_supplier='" + txt_namasupplier.getText() + "',notelp='" + txt_notelp.getText()+ "',alamat='" + txt_alamat.getText()+ "' where id_supplier='" + txt_idsupplier.getText() + "'";
            java.sql.Connection conn=(Connection)koneksi.getKoneksi();
            java.sql.PreparedStatement pst=conn.prepareStatement(sql);
            pst.execute();
            JOptionPane.showMessageDialog(null, "data berhasil di edit");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Perubahan Data Gagal: "+e.getMessage());
        }
        load_table();
        kosong();
        idsupplier();
    }//GEN-LAST:event_btn_editActionPerformed

    private void tableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableMouseClicked
        int baris = table.rowAtPoint(evt.getPoint());
        String idsup =table.getValueAt(baris, 0).toString();
        txt_idsupplier.setText(idsup);
        
        String alamat = table.getValueAt(baris,1).toString();
        txt_alamat.setText(alamat);
        
        String nama=table.getValueAt(baris, 2).toString();
        txt_namasupplier.setText(nama);
        
        String notelp=table.getValueAt(baris, 3).toString();
        txt_notelp.setText(notelp);
    }//GEN-LAST:event_tableMouseClicked

    private void btn_clearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_clearActionPerformed
        kosong();
        idsupplier();
        load_table();
    }//GEN-LAST:event_btn_clearActionPerformed

    private void btn_dashboardActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_dashboardActionPerformed
         soundClick();
        dispose();
         new master().setVisible(true);
    }//GEN-LAST:event_btn_dashboardActionPerformed

    private void btn_laporanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_laporanActionPerformed
      soundClick();
        dispose();
         new framePenjualan().setVisible(true);
    }//GEN-LAST:event_btn_laporanActionPerformed

    private void btn_laporan1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_laporan1ActionPerformed
        soundClick();
        dispose();
      new frameLogin().setVisible(true);
    }//GEN-LAST:event_btn_laporan1ActionPerformed

    private void txt_namasupplierActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_namasupplierActionPerformed
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Id Supplier");
        model.addColumn("Alamat Supplier");
        model.addColumn("Nama Supplier");
        model.addColumn("No. telepon");
       
        
        try{ 
        String nama = txt_namasupplier.getText();
        String sql = "SELECT * FROM supplier WHERE nama_supplier LIKE '%"+nama+"%'";
        Connection conn = (Connection) koneksi.getKoneksi();
        java.sql.Statement st = conn.createStatement();
        java.sql.ResultSet rs = st.executeQuery(sql);
        
        
       while (rs.next()) {
                model.addRow(new Object[]{rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4)});
            }
            table.setModel(model);
           
        
       }catch(SQLException | ClassNotFoundException e){
           JOptionPane.showMessageDialog(this, e.getMessage());
       }
    }//GEN-LAST:event_txt_namasupplierActionPerformed

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
            java.util.logging.Logger.getLogger(frameDataMasterSupplier.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(frameDataMasterSupplier.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(frameDataMasterSupplier.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(frameDataMasterSupplier.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new frameDataMasterSupplier().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_clear;
    private javax.swing.JButton btn_dashboard;
    private javax.swing.JButton btn_datamaster;
    private javax.swing.JButton btn_edit;
    private javax.swing.JButton btn_hapus;
    private javax.swing.JButton btn_laporan;
    private javax.swing.JButton btn_laporan1;
    private javax.swing.JButton btn_tambah;
    private javax.swing.JButton btn_transaksi;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable table;
    private javax.swing.JTextField txt_alamat;
    private javax.swing.JTextField txt_idsupplier;
    private javax.swing.JTextField txt_namasupplier;
    private javax.swing.JTextField txt_notelp;
    // End of variables declaration//GEN-END:variables
}
