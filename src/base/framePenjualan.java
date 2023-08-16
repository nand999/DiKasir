/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package base;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
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
import java.lang.String;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JRDesignQuery;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;
import java.util.*;
import java.io.InputStream;
import javax.swing.*;
import java.awt.event.*;
/**
 *
 * @author 1nternetslav3
 */
public class framePenjualan extends javax.swing.JFrame {

    /**
     * Creates new form framePenjualan
     */
    public framePenjualan() {
        initComponents();
        id_penjualan();
        kalender();
      //  totalBiaya();
        listmerk();
        AutoCompleteDecorator.decorate(com_merk);
        AutoCompleteDecorator.decorate(com_member);
        tabelTransaksi();
        tabelBarang();
        totalBiaya();
        listmember();
        
    }
    
     private void id_penjualan() {
        try {
            String sql = "SELECT id_penjualan FROM penjualan order by id_penjualan desc";
            Connection conn = (Connection) koneksi.getKoneksi();
            java.sql.Statement stm = conn.createStatement();
            java.sql.ResultSet rs = stm.executeQuery(sql);

            if (rs.next()) {
                String Idno = rs.getString("id_penjualan").substring(1);
                String AN = "" + (Integer.parseInt(Idno) + 1);
                String Nol = "";
                if (AN.length() == 1) {
                    Nol = "0000000";
                } else if(AN.length() == 2){
                    Nol = "000000";
                }else if(AN.length() == 3){
                    Nol = "00000";
                }else if(AN.length() == 4){
                    Nol = "0000";
                }else if(AN.length() == 5){
                    Nol = "000";
                }else if(AN.length() == 6){
                    Nol = "00";
                }else if(AN.length() == 7){
                    Nol = "0";
                }else if(AN.length() == 8){
                    Nol = "";
                }
                

                txt_id.setText("P" + Nol + AN);
            } else {
                txt_id.setText("P00000001");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e + " pada id_penjualan");
        }
    }
     
      private void kalender(){
        Date ys=new Date();
        SimpleDateFormat fm = new SimpleDateFormat("yyyy-MM-dd");
        
        txt_tanggal.setText(fm.format(ys));
        
      }
      

          
          
        private void listmerk() {
        try {
            String sql = "SELECT * From barang ";
            Connection conn = (Connection) koneksi.getKoneksi();
            java.sql.Statement pst = conn.createStatement();
            java.sql.ResultSet res = pst.executeQuery(sql);

            while (res.next()) {
                com_merk.addItem(res.getString("merk_barang"));

            }

        } catch (SQLException | ClassNotFoundException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());

        }      
        }
        
        private void listmember() {
        try {
            String sql = "SELECT * From member ";
            Connection conn = (Connection) koneksi.getKoneksi();
            java.sql.Statement pst = conn.createStatement();
            java.sql.ResultSet res = pst.executeQuery(sql);

            while (res.next()) {
                com_member.addItem(res.getString("nama_member"));

            }

        } catch (SQLException | ClassNotFoundException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());

        }      
        }
          
          
          
          
        private void tabelBarang() {
        //membuat tampilan
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Id barang");
        model.addColumn("merk barang");
        model.addColumn("Harga");
        model.addColumn("Stok");

        //menampilkan data database kedalam tabel
        try {
            String sql = "SELECT * FROM barang";
            Connection conn = (Connection) koneksi.getKoneksi();
            java.sql.Statement st = conn.createStatement();
            java.sql.ResultSet res = st.executeQuery(sql);
            while (res.next()) {
                model.addRow(new Object[]{res.getString("id_barang"), res.getString("merk_barang"),
                    res.getString("harga_jual"), res.getString("stok")});
            }
            table_barang.setModel(model);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e + "tabelProduk");

        }
    }
          
                   
        private void tabelTransaksi() {
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("ID Penjualan");
        model.addColumn("Nama Barang");
        model.addColumn("Qty");
        model.addColumn("Harga");
        model.addColumn("Harga Beli");
      //  model.addColumn("ID Member");

        

        //menampilkan data database kedalam tabel
        try {
            String sql = "SELECT detail_penjualan.id_penjualan, penjualan.id_penjualan, merk_barang, id_barang,"
                    + " qty, sub_total, sub_total_beli, penjualan.id_member "
                    + "FROM detail_penjualan LEFT JOIN penjualan ON detail_penjualan.id_penjualan = penjualan.id_penjualan"
                   + " where detail_penjualan.id_penjualan ='"+txt_id.getText()+"'";
 //                   + "&& WEEK(tanggal) = WEEK(CURRENT_DATE())"
  //                  + "order by id_penjualan asc";
            Connection conn = (Connection) koneksi.getKoneksi();
            java.sql.Statement st = conn.createStatement();
            java.sql.ResultSet res = st.executeQuery(sql);
            while (res.next()) {
                model.addRow(new Object[]{res.getString("detail_penjualan.id_penjualan"), res.getString("merk_barang"),
                    res.getString("qty"), res.getString("sub_total"), res.getString("sub_total_beli")});
            }
            table_penjualan.setModel(model);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage() + "pada tabel penjualan");
        }
        }

        private void clear() {
        txt_jumlah.setText("");
        txt_total.setText(""); 
        txt_idbarang.setText("");
        kalender();
        txt_hargabeli.setText("");
        txt_bayar.setText("");
        txt_kembalian.setText("");
        txt_diskon.setText(null);
        txt_ttlDiskon.setText(null);
        }
        
        private void idTetap() {
            String id = txt_id.getText();
            txt_id.setText(id);
        }
        
        private void clearSemua() {
        txt_jumlah.setText("");
        txt_total.setText(""); 
        txt_idbarang.setText("");
        kalender();
        id_penjualan();
        txt_totalbayar.setText("");
        txt_totalBeli.setText("");
        txt_hargabeli.setText("");
        txt_bayar.setText("");
        txt_kembalian.setText("");
        txt_diskon.setText(null);
        txt_ttlDiskon.setText(null);
        txt_idMember.setText("");
        com_member.setSelectedItem("  ");
        }
        
        public void totalBiaya() {
        int jumlahBaris = table_penjualan.getRowCount();
        int totalBiaya = 0;
        int totalBeli=0;
        int hargaBeli, hargaBarang;
        for (int i = 0; i < jumlahBaris; i++) {
            hargaBeli = Integer.parseInt(table_penjualan.getValueAt(i, 4).toString());
            hargaBarang = Integer.parseInt(table_penjualan.getValueAt(i, 3).toString());
 //         totalBiaya = totalBiaya + (jumlahBarang * hargaBarang);
            totalBiaya = totalBiaya + hargaBarang;
            totalBeli = totalBeli + hargaBeli;
        }
        txt_totalbayar.setText(String.valueOf(totalBiaya));
        txt_totalBeli.setText(String.valueOf(totalBeli));
       // txt_tampil.setText("Rp."+ totalBiaya +",00");
        }
        
        public void totalHargaBeli() {
        int jumlahBaris = table_penjualan.getRowCount();
        int totalBiaya = 0;
        int totalBeli=0;
        int hargaBeli, hargaBarang;
        for (int i = 0; i < jumlahBaris; i++) {
            hargaBeli = Integer.parseInt(table_penjualan.getValueAt(i, 4).toString());
            hargaBarang = Integer.parseInt(table_penjualan.getValueAt(i, 3).toString());
 //         totalBiaya = totalBiaya + (jumlahBarang * hargaBarang);
            totalBiaya = totalBiaya + hargaBarang;
            totalBeli = totalBeli + hargaBeli;
        }
        txt_totalBeli.setText(String.valueOf(totalBeli));
       // txt_tampil.setText("Rp."+ totalBiaya +",00");
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
    
    private void soundCash(){
    try {
    File sound = new File("C:\\Users\\Windows 10\\Downloads\\MUSIC\\cash.wav");
    Clip c = AudioSystem.getClip();
    c.open(AudioSystem.getAudioInputStream(sound));
    c.start();
        } catch (Exception e){       
          }
    }
    
    public void diskon(){
        int bayar = Integer.parseInt(txt_totalbayar.getText());
        int p = (int)(bayar*(5.0f/100.0f));
        int diskon =bayar*p;
        String member = txt_idMember.getText();
        if (member.equals("  ") || member.equals("") || member.equals(" ")){
           txt_diskon.setText(String.valueOf(0));
        }else{
            txt_diskon.setText(String.valueOf(p));
        }
    }
    
    public void setMember(){
       String memberTerpilih = (String) table_penjualan.getValueAt(0, 5);
       com_member.setSelectedItem(memberTerpilih);
    }
    
    public void afterDiskon(){
        int totalBayar = Integer.parseInt(txt_totalbayar.getText());
        int diskon = Integer.parseInt(txt_diskon.getText());
        int totalDiskon = totalBayar - diskon;
        txt_ttlDiskon.setText(String.valueOf(totalDiskon));
    }
    
    public void cekMember(){
        String memberTerpilih = (String) table_penjualan.getValueAt(0, 5);
        int jumlahBaris = table_penjualan.getRowCount();
          for (int i = 0; i < jumlahBaris; i++) {
            if (table_penjualan.getValueAt(i, 5).toString()!= memberTerpilih){
                JOptionPane.showMessageDialog(null, "nama member harus sama");
            }
        }       
    }
    
    public boolean cekmember(){
        String memberTerpilih = (String) com_member.getSelectedItem();
        int jumlahBaris = table_penjualan.getRowCount();
          for (int i = 0; i < jumlahBaris; i++) {
            if (table_penjualan.getValueAt(i, 5).toString()!= memberTerpilih){
              return  false;
            }            
         }return true;
    }
    
    public boolean cekkolom(){
        int jumlahBaris = table_penjualan.getRowCount();
        if (jumlahBaris<0){
            return false;
        }else{return true;}
    }
    
        public boolean cekmemberr(){
        String memberTerpilih = (String) com_member.getSelectedItem();
        String member = table_penjualan.getValueAt(0, 5).toString();
        int jumlahBaris = table_penjualan.getRowCount();
            if (member!= memberTerpilih){
              return  false;                       
         }  else if(!cekkolom()){
             return true;
         }
            else {return true;}
    }
    
    public void tambahPenjualan(){
            try {
            String total = "SELECT * FROM barang where id_barang='" + txt_idbarang.getText() + "'";
            Connection conn = (Connection) koneksi.getKoneksi();
            java.sql.Statement st = conn.createStatement();
            java.sql.ResultSet rs = st.executeQuery(total);
            if (rs.next()) {
                
                int jumlah = Integer.parseInt(txt_jumlah.getText());
                int stok = Integer.parseInt(rs.getString("stok"));
                int harga = Integer.parseInt(rs.getString("harga_jual"));
                int hargabeli = Integer.parseInt(rs.getString("harga_beli"));
                  if ((stok-jumlah)<0){
                     String pesan = "stok barang tidak cukup";
                     JOptionPane.showMessageDialog(null, pesan);
                }else{
                this.txt_total.setText(String.valueOf(harga * jumlah));
                this.txt_hargabeli.setText(String.valueOf(hargabeli * jumlah));
                }
                
            }
                
//                rs.getString("id_transaksi").substring(1)
//                String idPegawai = com_karyawan.getSelectedItem().toString().substring(0, 6);
                String sql = "INSERT INTO detail_penjualan VALUES ('" + com_merk.getSelectedItem() + "','" 
                        + txt_jumlah.getText() + "','"
                        + com_member.getSelectedItem() + "','"
                        + txt_hargabeli.getText() + "','" 
                        + txt_total.getText() + "','" 
                        + txt_idbarang.getText() + "','"
                        + txt_id.getText()+"')";
 //               java.sql.Connection conn = (Connection) koneksi.getKoneksi();
                java.sql.PreparedStatement pst = conn.prepareStatement(sql);
                pst.execute();
                JOptionPane.showMessageDialog(null, "penyimpanan Berhasil");
                clear();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this,"ERROR DI " + e.getMessage());
            }
    }
    
        public void tambahData(DefaultTableModel model, String nama, int jumlah) {
        // Mengecek baris mana yang memiliki nama yang sama
        int rowIndex = -1;
        for (int i = 0; i < model.getRowCount(); i++) {
            if (model.getValueAt(i, 0).equals(nama)) {
                rowIndex = i;
                break;
            }
        }

        if (rowIndex != -1) {
            // Jika nama sudah ada dalam tabel, maka tambahkan jumlah baru ke jumlah yang sudah ada
            int jumlahLama = (int) model.getValueAt(rowIndex, 1);
            int jumlahBaru = jumlahLama + jumlah;
           // model.setValueAt(jumlahBaru, rowIndex, 1);
                      try {
            String sql = "UPDATE detail_penjualan SET " + "qty='" + jumlahBaru+ "' where merk_barang='" + com_merk.getSelectedItem() + "'";
            java.sql.Connection conn=(Connection)koneksi.getKoneksi();
            java.sql.PreparedStatement pst=conn.prepareStatement(sql);
            pst.execute();
            JOptionPane.showMessageDialog(null, "data berhasil di edit");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Perubahan Data Gagal: "+e.getMessage());
        }
        } else {
            // Jika nama belum ada, tambahkan data baru sebagai baris baru dalam tabel
            Object[] rowData = {nama, jumlah};
            model.addRow(rowData);
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

        txt_total = new javax.swing.JTextField();
        txt_id = new javax.swing.JTextField();
        txt_tanggal = new javax.swing.JTextField();
        txt_jumlah = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        table_penjualan = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        table_barang = new javax.swing.JTable();
        com_merk = new javax.swing.JComboBox<>();
        btn_tambah = new javax.swing.JButton();
        btn_hapus = new javax.swing.JButton();
        btn_clear = new javax.swing.JButton();
        txt_idbarang = new javax.swing.JTextField();
        btn_log = new javax.swing.JButton();
        btn_dash = new javax.swing.JButton();
        btn_trans = new javax.swing.JButton();
        btn_data = new javax.swing.JButton();
        btn_lap = new javax.swing.JButton();
        txt_bayar = new javax.swing.JTextField();
        txt_kembalian = new javax.swing.JTextField();
        txt_totalbayar = new javax.swing.JTextField();
        btn_cetak = new javax.swing.JButton();
        txt_diskon = new javax.swing.JTextField();
        txt_ttlDiskon = new javax.swing.JTextField();
        com_member = new javax.swing.JComboBox<>();
        txt_idMember = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        txt_totalBeli = new javax.swing.JTextField();
        txt_hargabeli = new javax.swing.JTextField();
        btn_edit = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setIconImage(new javax.swing.ImageIcon(getClass().getResource("/ui_component/icon.png")).getImage());
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        txt_total.setBackground(new java.awt.Color(217, 217, 217));
        txt_total.setBorder(null);
        getContentPane().add(txt_total, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 320, 200, 20));

        txt_id.setEditable(false);
        txt_id.setBackground(new java.awt.Color(217, 217, 217));
        txt_id.setBorder(null);
        getContentPane().add(txt_id, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 60, 200, 20));

        txt_tanggal.setEditable(false);
        txt_tanggal.setBackground(new java.awt.Color(217, 217, 217));
        txt_tanggal.setBorder(null);
        txt_tanggal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_tanggalActionPerformed(evt);
            }
        });
        getContentPane().add(txt_tanggal, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 30, 200, 20));

        txt_jumlah.setBackground(new java.awt.Color(217, 217, 217));
        txt_jumlah.setBorder(null);
        txt_jumlah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_jumlahActionPerformed(evt);
            }
        });
        txt_jumlah.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_jumlahKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_jumlahKeyTyped(evt);
            }
        });
        getContentPane().add(txt_jumlah, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 260, 200, 20));

        table_penjualan.setDefaultEditor(Object.class, null);
        table_penjualan.setModel(new javax.swing.table.DefaultTableModel(
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
        table_penjualan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                table_penjualanMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(table_penjualan);

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 130, 570, 170));

        table_barang.setDefaultEditor(Object.class, null);
        table_barang.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane2.setViewportView(table_barang);

        getContentPane().add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 60, 400, 60));

        com_merk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                com_merkActionPerformed(evt);
            }
        });
        getContentPane().add(com_merk, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 190, 230, 30));

        btn_tambah.setOpaque(false);
        btn_tambah.setContentAreaFilled(false);
        btn_tambah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_tambahActionPerformed(evt);
            }
        });
        getContentPane().add(btn_tambah, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 380, 90, 30));

        btn_hapus.setOpaque(false);
        btn_hapus.setContentAreaFilled(false);
        btn_hapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_hapusActionPerformed(evt);
            }
        });
        getContentPane().add(btn_hapus, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 380, 90, 30));

        btn_clear.setOpaque(false);
        btn_clear.setContentAreaFilled(false);
        btn_clear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_clearActionPerformed(evt);
            }
        });
        getContentPane().add(btn_clear, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 420, 230, 40));

        txt_idbarang.setBackground(new java.awt.Color(217, 217, 217));
        txt_idbarang.setBorder(null);
        txt_idbarang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_idbarangActionPerformed(evt);
            }
        });
        txt_idbarang.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_idbarangKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_idbarangKeyTyped(evt);
            }
        });
        getContentPane().add(txt_idbarang, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 130, 200, 20));

        btn_log.setOpaque(false);
        btn_log.setContentAreaFilled(false);
        btn_log.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_logActionPerformed(evt);
            }
        });
        getContentPane().add(btn_log, new org.netbeans.lib.awtextra.AbsoluteConstraints(730, 540, 80, 80));

        btn_dash.setOpaque(false);
        btn_dash.setContentAreaFilled(false);
        btn_dash.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_dashActionPerformed(evt);
            }
        });
        getContentPane().add(btn_dash, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 540, 80, 80));

        btn_trans.setOpaque(false);
        btn_trans.setContentAreaFilled(false);
        btn_trans.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_transActionPerformed(evt);
            }
        });
        getContentPane().add(btn_trans, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 540, 70, 80));

        btn_data.setOpaque(false);
        btn_data.setContentAreaFilled(false);
        btn_data.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_dataActionPerformed(evt);
            }
        });
        getContentPane().add(btn_data, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 540, 70, 80));

        btn_lap.setOpaque(false);
        btn_lap.setContentAreaFilled(false);
        btn_lap.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_lapActionPerformed(evt);
            }
        });
        getContentPane().add(btn_lap, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 540, 70, 80));

        txt_bayar.setBackground(new java.awt.Color(217, 217, 217));
        txt_bayar.setBorder(null);
        txt_bayar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_bayarActionPerformed(evt);
            }
        });
        txt_bayar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_bayarKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_bayarKeyTyped(evt);
            }
        });
        getContentPane().add(txt_bayar, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 430, 200, 20));

        txt_kembalian.setBackground(new java.awt.Color(217, 217, 217));
        txt_kembalian.setBorder(null);
        txt_kembalian.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_kembalianActionPerformed(evt);
            }
        });
        getContentPane().add(txt_kembalian, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 470, 200, 20));

        txt_totalbayar.setBackground(new java.awt.Color(217, 217, 217));
        txt_totalbayar.setBorder(null);
        txt_totalbayar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_totalbayarActionPerformed(evt);
            }
        });
        getContentPane().add(txt_totalbayar, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 330, 200, 20));

        btn_cetak.setOpaque(false);
        btn_cetak.setContentAreaFilled(false);
        btn_cetak.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_cetakActionPerformed(evt);
            }
        });
        getContentPane().add(btn_cetak, new org.netbeans.lib.awtextra.AbsoluteConstraints(770, 440, 120, 40));

        txt_diskon.setBackground(new java.awt.Color(217, 217, 217));
        txt_diskon.setBorder(null);
        txt_diskon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_diskonActionPerformed(evt);
            }
        });
        txt_diskon.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_diskonKeyReleased(evt);
            }
        });
        getContentPane().add(txt_diskon, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 370, 190, 20));

        txt_ttlDiskon.setBackground(new java.awt.Color(217, 217, 217));
        txt_ttlDiskon.setBorder(null);
        txt_ttlDiskon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_ttlDiskonActionPerformed(evt);
            }
        });
        getContentPane().add(txt_ttlDiskon, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 400, 190, 20));

        com_member.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                com_memberItemStateChanged(evt);
            }
        });
        com_member.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                com_memberActionPerformed(evt);
            }
        });
        getContentPane().add(com_member, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 400, 150, 20));

        txt_idMember.setBackground(new java.awt.Color(217, 217, 217));
        txt_idMember.setBorder(null);
        txt_idMember.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_idMemberActionPerformed(evt);
            }
        });
        txt_idMember.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_idMemberKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_idMemberKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_idMemberKeyTyped(evt);
            }
        });
        getContentPane().add(txt_idMember, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 350, 150, -1));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ui_revisi/kasir.png"))); // NOI18N
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        txt_totalBeli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_totalBeliActionPerformed(evt);
            }
        });
        getContentPane().add(txt_totalBeli, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 630, 60, -1));

        txt_hargabeli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_hargabeliActionPerformed(evt);
            }
        });
        getContentPane().add(txt_hargabeli, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 590, 50, -1));

        btn_edit.setOpaque(false);
        btn_edit.setContentAreaFilled(false);
        btn_edit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_editActionPerformed(evt);
            }
        });
        getContentPane().add(btn_edit, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 500, 20, 30));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btn_tambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_tambahActionPerformed
        soundClick();
        
   
        if (txt_id.getText().equals("") ||txt_tanggal.getText().equals("") || 
                txt_jumlah.getText().equals("") || txt_total.getText().equals("") || txt_hargabeli.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "ISI SEMUA DATA TERLEBIH DAHULU");
        }
        
     /*   else if (!cekmemberr()){
            JOptionPane.showMessageDialog(null, "member harus sama!");
        } */
        
        
        else {
            
        String productName = (String) com_merk.getSelectedItem();
        int qtyToAdd = Integer.parseInt(txt_jumlah.getText());

        String url = "jdbc:mysql://localhost:3306/test_dikasir2";
        String username = "root";
        String password = "";

        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            String selectQuery = "SELECT COUNT(*) FROM detail_penjualan WHERE merk_barang = ? AND id_penjualan = '" + txt_id.getText() + "'";
            
            try (PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {
                preparedStatement.setString(1, productName);
                ResultSet resultSet = preparedStatement.executeQuery();
                resultSet.next();
                int rowCount = resultSet.getInt(1);
                
                if (rowCount > 0) {
                    String updateQuery = "UPDATE detail_penjualan SET qty = qty + ? WHERE merk_barang = ? AND id_penjualan = '" + txt_id.getText() + "'";
                    try (PreparedStatement updateStatement = connection.prepareStatement(updateQuery)) {
                        updateStatement.setInt(1, qtyToAdd);
                        updateStatement.setString(2, productName);
                        int rowsAffected = updateStatement.executeUpdate();
                        if (rowsAffected > 0) {
                            System.out.println("Jumlah barang telah ditambahkan.");
                        } else {
                            System.out.println("Gagal menambahkan jumlah barang.");
                        }
                    }
                } else {
                    String insertQuery = "INSERT INTO detail_penjualan (merk_barang, qty, sub_total_beli, sub_total, id_barang, id_penjualan) VALUES (?, ?, ?, ? , ?, ?)";
                    try (PreparedStatement insertStatement = connection.prepareStatement(insertQuery)) {
                        insertStatement.setString(1, productName);
                        insertStatement.setInt(2, qtyToAdd);
                        insertStatement.setInt(3, Integer.parseInt(txt_hargabeli.getText()));
                        insertStatement.setInt(4, Integer.parseInt(txt_total.getText()));
                        insertStatement.setString(5, txt_idbarang.getText());
                        insertStatement.setString(6, txt_id.getText());
                        
                        int rowsAffected = insertStatement.executeUpdate();
                        if (rowsAffected > 0) {
                            System.out.println("Produk baru telah ditambahkan.");
                        } else {
                            System.out.println("Gagal menambahkan produk baru.");
                        }
                    }
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

                
        }
                   
        tabelTransaksi();
        tabelBarang();
        clear();
        totalBiaya();
        diskon();
   //     setMember();
        afterDiskon();
        
    }//GEN-LAST:event_btn_tambahActionPerformed

    private void txt_jumlahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_jumlahActionPerformed
       try {
            String total = "SELECT * FROM barang where id_barang='" + txt_idbarang.getText() + "'";
            Connection conn = (Connection) koneksi.getKoneksi();
            java.sql.Statement st = conn.createStatement();
            java.sql.ResultSet rs = st.executeQuery(total);
            if (rs.next()) {
                
                int jumlah = Integer.parseInt(txt_jumlah.getText());
                int stok = Integer.parseInt(rs.getString("stok"));
                int harga = Integer.parseInt(rs.getString("harga_jual"));
                int hargabeli = Integer.parseInt(rs.getString("harga_beli"));
                  if ((stok-jumlah)<0){
                     String pesan = "stok barang tidak cukup";
                     JOptionPane.showMessageDialog(null, pesan);
                }else{
                this.txt_total.setText(String.valueOf(harga * jumlah));
                this.txt_hargabeli.setText(String.valueOf(hargabeli * jumlah));
                }
                
            }
        } catch (SQLException | ClassNotFoundException e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }//GEN-LAST:event_txt_jumlahActionPerformed

    private void com_merkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_com_merkActionPerformed
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("ID Barang");
        model.addColumn("Nama Barang");
        model.addColumn("Harga");
        model.addColumn("Stok");
        
        try{ 
        String nama = com_merk.getSelectedItem().toString();
        String sql = "SELECT * FROM barang WHERE merk_barang LIKE '%"+nama+"%'";
        Connection conn = (Connection) koneksi.getKoneksi();
        java.sql.Statement st = conn.createStatement();
        java.sql.ResultSet rs = st.executeQuery(sql);
        //System.out.println(sql);
        
       while (rs.next()) {
                model.addRow(new Object[]{rs.getString(1), rs.getString(2), rs.getString(4), rs.getString(5)});
            }
            table_barang.setModel(model);
           
        
       }catch(SQLException | ClassNotFoundException e){
           JOptionPane.showMessageDialog(this, e.getMessage());
       }
        //hahahahahahahahahahahaha
        
             try {
             String nama = com_merk.getSelectedItem().toString();
             String sql = "SELECT * FROM barang WHERE merk_barang LIKE '%"+nama+"%'";
            Connection conn = (Connection) koneksi.getKoneksi();
            java.sql.Statement st = conn.createStatement();
            java.sql.ResultSet rs = st.executeQuery(sql);
            if (rs.next()) {
                
               String idbarang = rs.getString("id_barang");
              
                this.txt_idbarang.setText(idbarang);
            }
        } catch (SQLException | ClassNotFoundException e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }//GEN-LAST:event_com_merkActionPerformed

    private void table_penjualanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_table_penjualanMouseClicked
        try {
            String idpenjualan = table_penjualan.getValueAt(table_penjualan.getSelectedRow(), 0).toString();
            String tanggal = txt_tanggal.getText();
            String nama = table_penjualan.getValueAt(table_penjualan.getSelectedRow(), 1).toString();
            String qty = table_penjualan.getValueAt(table_penjualan.getSelectedRow(), 2).toString();
            String hrg = table_penjualan.getValueAt(table_penjualan.getSelectedRow(), 3).toString();
      //      String member = table_penjualan.getValueAt(table_penjualan.getSelectedRow(), 5).toString();
            
            String sql = "SELECT id_penjualan,id_barang, qty, sub_total, merk_barang "
                    + "FROM detail_penjualan"
                    + " WHERE "
                    + "id_penjualan ='" + idpenjualan + "'"
                //  + " and tanggal ='" + tanggal + "'"
                    + " and qty ='" + qty + "'"
                    + " and merk_barang ='" + nama + "'"
     //               + " and nama_member ='" + member + "'"
                    + " and sub_total ='" + hrg + "'";
            Connection conn = (Connection) koneksi.getKoneksi();
            java.sql.Statement st = conn.createStatement();
            java.sql.ResultSet res = st.executeQuery(sql);
            if (res.next()) {
                this.txt_id.setText(res.getString("id_penjualan"));
             // this.txt_tanggal.setText(res.getString("tanggal"));
            //    this.com_member.setSelectedItem(res.getString("nama_member"));
                this.txt_idbarang.setText(res.getString("id_barang"));
                this.com_merk.setSelectedItem(res.getString("merk_barang"));
                
                this.txt_jumlah.setText(res.getString("qty"));
                this.txt_total.setText(res.getString("sub_total"));

            }

        } catch (SQLException | ClassNotFoundException e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
        tabelTransaksi();
    }//GEN-LAST:event_table_penjualanMouseClicked

    private void btn_clearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_clearActionPerformed
       clear();
    }//GEN-LAST:event_btn_clearActionPerformed

    private void btn_editActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_editActionPerformed
 
    }//GEN-LAST:event_btn_editActionPerformed

    private void btn_hapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_hapusActionPerformed
            int n = JOptionPane.showConfirmDialog(
            null,
            "apakah anda yakin untuk menghapus?",
            "konfirmasi hapus",
            JOptionPane.YES_NO_OPTION);

        if( n == JOptionPane.YES_OPTION){
                try {
                    String qty = txt_jumlah.getText();
                    int hrg = Integer.parseInt(txt_total.getText());
                    
                    
                String sql = "DELETE from detail_penjualan where id_penjualan ='" + txt_id.getText() + "'"
                    + " and qty ='" + qty + "'"
                    + " and merk_barang ='" + com_merk.getSelectedItem() + "'"
                    + " and sub_total ='" + hrg + "'";
                java.sql.Connection conn = (Connection) koneksi.getKoneksi();
                java.sql.PreparedStatement pst = conn.prepareStatement(sql);
                pst.execute();
                JOptionPane.showMessageDialog(this, "Berhasil Menghapus Data");
                clear();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, e.getMessage());
            }
         
        }else {
            JOptionPane.showMessageDialog(null, "hapus dibatalkan");
        }
         tabelTransaksi();
         totalBiaya();
         clear();
         tabelBarang();
         setMember();
         afterDiskon();
         
    }//GEN-LAST:event_btn_hapusActionPerformed

    private void btn_logActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_logActionPerformed
      soundClick();
        setVisible(false);
       new framePembelian().setVisible(true);
    }//GEN-LAST:event_btn_logActionPerformed

    private void btn_dataActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_dataActionPerformed
      soundClick();
        setVisible(false);
       new master().setVisible(true);
    }//GEN-LAST:event_btn_dataActionPerformed

    private void btn_transActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_transActionPerformed
        soundClick();
   
    }//GEN-LAST:event_btn_transActionPerformed

    private void btn_dashActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_dashActionPerformed
        soundClick();
        setVisible(false);
      new dashboard().setVisible(true);
    }//GEN-LAST:event_btn_dashActionPerformed

    private void btn_lapActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_lapActionPerformed
        setVisible(false);
       new laporan().setVisible(true);
    }//GEN-LAST:event_btn_lapActionPerformed

    private void txt_tanggalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_tanggalActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_tanggalActionPerformed

    private void txt_bayarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_bayarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_bayarActionPerformed

    private void txt_kembalianActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_kembalianActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_kembalianActionPerformed

    private void txt_totalbayarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_totalbayarActionPerformed
 
    }//GEN-LAST:event_txt_totalbayarActionPerformed

    private void btn_cetakActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_cetakActionPerformed
          
        int totalBayar = Integer.parseInt(txt_ttlDiskon.getText());
        int bayar = Integer.parseInt(txt_bayar.getText());
        int kembalian = bayar - totalBayar;
        int balik = Integer.parseInt(txt_kembalian.getText()); 
        String idTransaksi = txt_id.getText();
        
   if (balik != kembalian || balik<0){
       JOptionPane.showMessageDialog(null, "nominal kembalian tidak sesuai");
    } else if(bayar==0||kembalian==0){
        JOptionPane.showMessageDialog(null, "masukkan nominal bayar");
    }
   
   else {   
        soundCash();
        
        
        try {
//                rs.getString("id_transaksi").substring(1)
//                String idPegawai = com_karyawan.getSelectedItem().toString().substring(0, 6);
                String sql = "INSERT INTO penjualan (id_penjualan, tanggal, grand_total_jual, grand_total_beli, diskon, grand_total_diskon, bayar, kembalian, id_member) VALUES ('" + txt_id.getText() + "','"
                        + txt_tanggal.getText() + "','" +  txt_totalbayar.getText() + "','" +  txt_totalBeli.getText() + "','" +  txt_diskon.getText() + "','" +  txt_ttlDiskon.getText() + "','" +  txt_bayar.getText() + "','" +  txt_kembalian.getText() + "','" + txt_idMember.getText() + "')";
                    //    + txt_id.getText()+"')";
                
                java.sql.Connection conn = (Connection) koneksi.getKoneksi();
                java.sql.PreparedStatement pst = conn.prepareStatement(sql);
                pst.execute();
                JOptionPane.showMessageDialog(null, "transaksi Berhasil");
                clear();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, e.getMessage());
            }
         clearSemua();
         id_penjualan();
         tabelTransaksi();
    }       
        //KODE UNTUK MENCETAK JASPER REPORT
 
        try {
            Connection conn = (Connection) koneksi.getKoneksi();
            // Menyiapkan id transaksi
            HashMap parameter = new HashMap();
            parameter.put("id_penjualan", idTransaksi);

            // Membaca file JasperDesign dari JRXML
            InputStream file = getClass().getResourceAsStream("/base/test2_1_1.jrxml");

            JasperDesign desain = JRXmlLoader.load(file);

            // Mengompilasi report dari JasperDesign
            JasperReport report = JasperCompileManager.compileReport(desain);

            // Mengisi report dengan data dan parameter
            JasperPrint print = JasperFillManager.fillReport(report, parameter, conn);

            // Menampilkan jasper viewer
            JasperViewer jview = new JasperViewer(print, false);
            jview.setTitle("Cetak Struk " + idTransaksi);
            jview.setVisible(true);
            jview.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
            jview.setLocationRelativeTo(null);
            jview.setFitPageZoomRatio();

            // Menutup jasper viewer saat user menekan tombol close
            jview.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    jview.dispose();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }         
    }//GEN-LAST:event_btn_cetakActionPerformed

    private void txt_hargabeliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_hargabeliActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_hargabeliActionPerformed

    private void txt_totalBeliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_totalBeliActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_totalBeliActionPerformed

    private void txt_bayarKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_bayarKeyTyped
    
    }//GEN-LAST:event_txt_bayarKeyTyped

    private void txt_bayarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_bayarKeyReleased
        int totalBayar = Integer.parseInt(txt_ttlDiskon.getText());
        int bayar = Integer.parseInt(txt_bayar.getText());
        int kembalian = bayar - totalBayar;
        txt_kembalian.setText(String.valueOf(kembalian));
    }//GEN-LAST:event_txt_bayarKeyReleased

    private void txt_idbarangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_idbarangActionPerformed
             try {
             String nama = txt_idbarang.getText();
             String sql = "SELECT * FROM barang WHERE id_barang LIKE '%"+nama+"%'";
            Connection conn = (Connection) koneksi.getKoneksi();
            java.sql.Statement st = conn.createStatement();
            java.sql.ResultSet rs = st.executeQuery(sql);
            if (rs.next()) {
                
               String idbarang = rs.getString("id_barang");
              
               this.com_merk.setSelectedItem(rs.getString("merk_barang"));
            }
        } catch (SQLException | ClassNotFoundException e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }//GEN-LAST:event_txt_idbarangActionPerformed

    private void com_memberActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_com_memberActionPerformed
    try {
            String member = com_member.getSelectedItem().toString();
            String sql = "SELECT * FROM member WHERE nama_member LIKE '%"+member+"%'";
            Connection conn = (Connection) koneksi.getKoneksi();
            java.sql.Statement st = conn.createStatement();
            java.sql.ResultSet rs = st.executeQuery(sql);
            if (rs.next()) {
                
               String idbarang = rs.getString("id_member");
              
               this.txt_idMember.setText(idbarang);
               txt_idMember.requestFocus();
                            
            }
        } catch (SQLException | ClassNotFoundException e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }

    
    }//GEN-LAST:event_com_memberActionPerformed

    private void txt_diskonKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_diskonKeyReleased
        int totalBayar = Integer.parseInt(txt_totalbayar.getText());
        int diskon = Integer.parseInt(txt_diskon.getText());
        int totalDiskon = totalBayar - diskon;
        txt_ttlDiskon.setText(String.valueOf(totalDiskon));
    }//GEN-LAST:event_txt_diskonKeyReleased

    private void txt_diskonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_diskonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_diskonActionPerformed

    private void txt_idMemberActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_idMemberActionPerformed
         try {
            String member = txt_idMember.getText();
            String sql = "SELECT * FROM member WHERE id_member LIKE '%"+member+"%'";
            Connection conn = (Connection) koneksi.getKoneksi();
            java.sql.Statement st = conn.createStatement();
            java.sql.ResultSet rs = st.executeQuery(sql);
            if (rs.next()) {
              
               this.com_member.setSelectedItem(rs.getString("nama_member"));
            }
        } catch (SQLException | ClassNotFoundException e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
         
/*       try {
            Robot r = new Robot();
            r.keyPress(KeyEvent.VK_ENTER);
            r.keyRelease(KeyEvent.VK_ENTER);
        } catch (AWTException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } */
        
    }//GEN-LAST:event_txt_idMemberActionPerformed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
txt_idMember.requestFocus();
txt_idMember.setText("");
txt_idbarang.setText("");
txt_idbarang.requestFocus();
    }//GEN-LAST:event_formWindowOpened

    private void txt_idMemberKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_idMemberKeyReleased
 diskon();
 afterDiskon();
    }//GEN-LAST:event_txt_idMemberKeyReleased

    private void txt_jumlahKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_jumlahKeyReleased
        int cekJumlah = Integer.parseInt(txt_jumlah.getText());
        if(cekJumlah<0){
             JOptionPane.showMessageDialog(null, "jumlah barang tidak boleh kurang dari 0");
             txt_jumlah.setText("");
        } else{
        
        try {
            String total = "SELECT * FROM barang where id_barang='" + txt_idbarang.getText() + "'";
            Connection conn = (Connection) koneksi.getKoneksi();
            java.sql.Statement st = conn.createStatement();
            java.sql.ResultSet rs = st.executeQuery(total);
            if (rs.next()) {
                
                int jumlah = Integer.parseInt(txt_jumlah.getText());
                int stok = Integer.parseInt(rs.getString("stok"));
                int harga = Integer.parseInt(rs.getString("harga_jual"));
                int hargabeli = Integer.parseInt(rs.getString("harga_beli"));
                  if ((stok-jumlah)<0){
                     String pesan = "stok barang tidak cukup";
                     JOptionPane.showMessageDialog(null, pesan);
                     txt_jumlah.setText("");
                     txt_total.setText("");
                }else{
                this.txt_total.setText(String.valueOf(harga * jumlah));
                this.txt_hargabeli.setText(String.valueOf(hargabeli * jumlah));
                }
                
            }
        } catch (SQLException | ClassNotFoundException e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
            }
    }//GEN-LAST:event_txt_jumlahKeyReleased

    private void txt_idbarangKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_idbarangKeyPressed
      if(evt.getKeyCode()==KeyEvent.VK_ENTER){
                
      DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Id barang");
        model.addColumn("merk barang");
        model.addColumn("Harga");
        model.addColumn("Stok");

        //menampilkan data database kedalam tabel
        try {
            String sql = "SELECT * FROM barang WHERE id_barang = '"+txt_idbarang.getText()+"'";
            Connection conn = (Connection) koneksi.getKoneksi();
            java.sql.Statement st = conn.createStatement();
            java.sql.ResultSet res = st.executeQuery(sql);
            while (res.next()) {
                model.addRow(new Object[]{res.getString("id_barang"), res.getString("merk_barang"),
                    res.getString("harga_jual"), res.getString("stok")});
            }
            table_barang.setModel(model);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e + "tabelProduk");

        }
        }
    }//GEN-LAST:event_txt_idbarangKeyPressed

    private void txt_idbarangKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_idbarangKeyTyped
             if("".equals(txt_idbarang.getText())){
            tabelBarang();
        }
        
    }//GEN-LAST:event_txt_idbarangKeyTyped

    private void txt_idMemberKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_idMemberKeyTyped

    }//GEN-LAST:event_txt_idMemberKeyTyped

    private void txt_idMemberKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_idMemberKeyPressed

       
    }//GEN-LAST:event_txt_idMemberKeyPressed

    private void com_memberItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_com_memberItemStateChanged
    Robot robot = null;
        try {
             robot = new Robot();
            } catch (AWTException e) {
                                     diskon();
                                     afterDiskon();
                                     e.printStackTrace();
                                     }
    robot.keyPress(KeyEvent.VK_ENTER);
    robot.keyRelease(KeyEvent.VK_ENTER);
    }//GEN-LAST:event_com_memberItemStateChanged

    private void txt_jumlahKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_jumlahKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_jumlahKeyTyped

    private void txt_ttlDiskonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_ttlDiskonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_ttlDiskonActionPerformed

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
            java.util.logging.Logger.getLogger(framePenjualan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(framePenjualan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(framePenjualan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(framePenjualan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new framePenjualan().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_cetak;
    private javax.swing.JButton btn_clear;
    private javax.swing.JButton btn_dash;
    private javax.swing.JButton btn_data;
    private javax.swing.JButton btn_edit;
    private javax.swing.JButton btn_hapus;
    private javax.swing.JButton btn_lap;
    private javax.swing.JButton btn_log;
    private javax.swing.JButton btn_tambah;
    private javax.swing.JButton btn_trans;
    private javax.swing.JComboBox<String> com_member;
    private javax.swing.JComboBox<String> com_merk;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable table_barang;
    private javax.swing.JTable table_penjualan;
    private javax.swing.JTextField txt_bayar;
    private javax.swing.JTextField txt_diskon;
    private javax.swing.JTextField txt_hargabeli;
    private javax.swing.JTextField txt_id;
    private javax.swing.JTextField txt_idMember;
    private javax.swing.JTextField txt_idbarang;
    private javax.swing.JTextField txt_jumlah;
    private javax.swing.JTextField txt_kembalian;
    private javax.swing.JTextField txt_tanggal;
    private javax.swing.JTextField txt_total;
    private javax.swing.JTextField txt_totalBeli;
    private javax.swing.JTextField txt_totalbayar;
    private javax.swing.JTextField txt_ttlDiskon;
    // End of variables declaration//GEN-END:variables
}
