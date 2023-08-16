/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package base;

import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.io.File;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

/**
 *
 * @author 1nternetslav3
 */
public class framePembelian extends javax.swing.JFrame {

    /**
     * Creates new form framePembelian
     */
    public framePembelian() {
        initComponents();      
        id_pembelian();
        kalender();
        listsup();
        listmerk();
        AutoCompleteDecorator.decorate(com_merk);
        AutoCompleteDecorator.decorate(com_sup);
        tabelPembelian();
        tabelBarang();
        totalBiaya();
    }
    
    private void id_pembelian() {
        try {
            String sql = "SELECT id_pembelian FROM pembelian order by id_pembelian desc";
            Connection conn = (Connection) koneksi.getKoneksi();
            java.sql.Statement stm = conn.createStatement();
            java.sql.ResultSet rs = stm.executeQuery(sql);

            if (rs.next()) {
                String Idno = rs.getString("id_pembelian").substring(1);
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
                

                txt_id.setText("B" + Nol + AN);
            } else {
                txt_id.setText("B00000001");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e + " pada ID_PEMBELIAN");
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
                    res.getString("harga_beli"), res.getString("stok")});
            }
            table_barang.setModel(model);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e + "tabelProduk");

        }
    }
               
        private void tabelPembelian() {
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("ID Penjualan");
        model.addColumn("Nama Barang");
        model.addColumn("Qty");
       // model.addColumn("Harga");
        model.addColumn("Harga Beli");
        model.addColumn("ID Supplier");
        

        

        //menampilkan data database kedalam tabel
        try {
            String sql = "SELECT id_pembelian, merk_barang, id_barang,"
                    + " qty, sub_total, id_supplier "
                    + "FROM detail_pembelian"
                   + " where id_pembelian='"+txt_id.getText()+"'";
 //                   + "&& WEEK(tanggal) = WEEK(CURRENT_DATE())"
  //                  + "order by id_penjualan asc";
            Connection conn = (Connection) koneksi.getKoneksi();
            java.sql.Statement st = conn.createStatement();
            java.sql.ResultSet res = st.executeQuery(sql);
            while (res.next()) {
                model.addRow(new Object[]{res.getString("id_pembelian"), res.getString("merk_barang"),
                    res.getString("qty"), res.getString("sub_total"),res.getString("id_supplier")});
            }
            table_pembelian.setModel(model);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage() + "pada tabel pembelian");
        }
        }

        private void clear() {
        txt_jumlah.setText("");
        txt_total.setText(""); 
        txt_idbarang.setText("");
        kalender();       
        }
        
        private void clearSemua() {
        txt_jumlah.setText("");
        txt_total.setText(""); 
        txt_idbarang.setText("");
        txt_totalbayar.setText("");
        kalender(); 
        id_pembelian();
        }
        
        
        public void totalBiaya() {
        int jumlahBaris = table_pembelian.getRowCount();
        int totalBiaya = 0;
        int totalBeli=0;
        int hargaBeli, hargaBarang;
        for (int i = 0; i < jumlahBaris; i++) {
 //         hargaBeli = Integer.parseInt(table_pembelian.getValueAt(i, 4).toString());
            hargaBarang = Integer.parseInt(table_pembelian.getValueAt(i, 3).toString());
 //         totalBiaya = totalBiaya + (jumlahBarang * hargaBarang);
            totalBiaya = totalBiaya + hargaBarang;
 //         totalBeli = totalBeli + hargaBeli;
        }
        txt_totalbayar.setText(String.valueOf(totalBiaya));
 //       txt_totalBeli.setText(String.valueOf(totalBeli));
 //       txt_tampil.setText("Rp."+ totalBiaya +",00");
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
    
    private void listsup() {
        try {
            String sql = "SELECT * From supplier ";
            Connection conn = (Connection) koneksi.getKoneksi();
            java.sql.Statement pst = conn.createStatement();
            java.sql.ResultSet res = pst.executeQuery(sql);

            while (res.next()) {
                com_sup.addItem(res.getString("nama_supplier"));

            }

        } catch (SQLException | ClassNotFoundException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());

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
        table_pembelian = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        table_barang = new javax.swing.JTable();
        txt_id = new javax.swing.JTextField();
        txt_tanggal = new javax.swing.JTextField();
        txt_idbarang = new javax.swing.JTextField();
        txt_jumlah = new javax.swing.JTextField();
        com_merk = new javax.swing.JComboBox<>();
        com_sup = new javax.swing.JComboBox<>();
        txt_total = new javax.swing.JTextField();
        btn_tambah = new javax.swing.JButton();
        btn_hapus = new javax.swing.JButton();
        btn_clear = new javax.swing.JButton();
        btn_logout = new javax.swing.JButton();
        btn_dashboard = new javax.swing.JButton();
        btn_trans = new javax.swing.JButton();
        btn_data = new javax.swing.JButton();
        btn_laporan = new javax.swing.JButton();
        btn_edit = new javax.swing.JButton();
        txt_totalbayar = new javax.swing.JTextField();
        btn_tambahkan = new javax.swing.JButton();
        txt_idSup = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setIconImage(new javax.swing.ImageIcon(getClass().getResource("/ui_component/icon.png")).getImage());
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        table_pembelian.setDefaultEditor(Object.class, null);
        table_pembelian.setModel(new javax.swing.table.DefaultTableModel(
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
        table_pembelian.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                table_pembelianMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(table_pembelian);

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 190, 570, 220));

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

        getContentPane().add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 100, -1, 70));

        txt_id.setEditable(false);
        txt_id.setBackground(new java.awt.Color(217, 217, 217));
        txt_id.setBorder(null);
        txt_id.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_idActionPerformed(evt);
            }
        });
        getContentPane().add(txt_id, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 70, 200, 20));

        txt_tanggal.setEditable(false);
        txt_tanggal.setBackground(new java.awt.Color(217, 217, 217));
        txt_tanggal.setBorder(null);
        txt_tanggal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_tanggalActionPerformed(evt);
            }
        });
        getContentPane().add(txt_tanggal, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 30, 200, 20));

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
        getContentPane().add(txt_idbarang, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 120, 200, 20));

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
        });
        getContentPane().add(txt_jumlah, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 220, 200, -1));

        com_merk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                com_merkActionPerformed(evt);
            }
        });
        getContentPane().add(com_merk, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 160, 230, 30));

        com_sup.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                com_supActionPerformed(evt);
            }
        });
        getContentPane().add(com_sup, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 360, 230, -1));

        txt_total.setBackground(new java.awt.Color(217, 217, 217));
        txt_total.setBorder(null);
        txt_total.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_totalActionPerformed(evt);
            }
        });
        getContentPane().add(txt_total, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 270, 200, 20));

        btn_tambah.setOpaque(false);
        btn_tambah.setContentAreaFilled(false);
        btn_tambah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_tambahActionPerformed(evt);
            }
        });
        getContentPane().add(btn_tambah, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 400, 100, 30));

        btn_hapus.setOpaque(false);
        btn_hapus.setContentAreaFilled(false);
        btn_hapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_hapusActionPerformed(evt);
            }
        });
        getContentPane().add(btn_hapus, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 400, 100, 30));

        btn_clear.setOpaque(false);
        btn_clear.setContentAreaFilled(false);
        btn_clear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_clearActionPerformed(evt);
            }
        });
        getContentPane().add(btn_clear, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 450, 220, 30));

        btn_logout.setOpaque(false);
        btn_logout.setContentAreaFilled(false);
        btn_logout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_logoutActionPerformed(evt);
            }
        });
        getContentPane().add(btn_logout, new org.netbeans.lib.awtextra.AbsoluteConstraints(720, 530, 100, 90));

        btn_dashboard.setOpaque(false);
        btn_dashboard.setContentAreaFilled(false);
        btn_dashboard.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_dashboardActionPerformed(evt);
            }
        });
        getContentPane().add(btn_dashboard, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 530, 70, 90));

        btn_trans.setOpaque(false);
        btn_trans.setContentAreaFilled(false);
        btn_trans.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_transActionPerformed(evt);
            }
        });
        getContentPane().add(btn_trans, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 540, 70, 80));

        btn_data.setOpaque(false);
        btn_data.setContentAreaFilled(false);
        btn_data.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_dataActionPerformed(evt);
            }
        });
        getContentPane().add(btn_data, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 540, 70, 80));

        btn_laporan.setOpaque(false);
        btn_laporan.setContentAreaFilled(false);
        btn_laporan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_laporanActionPerformed(evt);
            }
        });
        getContentPane().add(btn_laporan, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 540, 70, 80));

        btn_edit.setOpaque(false);
        btn_edit.setContentAreaFilled(false);
        btn_edit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_editActionPerformed(evt);
            }
        });
        getContentPane().add(btn_edit, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 650, 30, 30));

        txt_totalbayar.setBackground(new java.awt.Color(217, 217, 217));
        txt_totalbayar.setBorder(null);
        txt_totalbayar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_totalbayarActionPerformed(evt);
            }
        });
        getContentPane().add(txt_totalbayar, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 450, 260, 20));

        btn_tambahkan.setOpaque(false);
        btn_tambahkan.setContentAreaFilled(false);
        btn_tambahkan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_tambahkanActionPerformed(evt);
            }
        });
        getContentPane().add(btn_tambahkan, new org.netbeans.lib.awtextra.AbsoluteConstraints(770, 440, 130, 40));

        txt_idSup.setBackground(new java.awt.Color(217, 217, 217));
        txt_idSup.setBorder(null);
        txt_idSup.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_idSupActionPerformed(evt);
            }
        });
        getContentPane().add(txt_idSup, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 320, 200, -1));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ui_revisi/pembelian1.png"))); // NOI18N
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 940, -1));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void txt_idActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_idActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_idActionPerformed

    private void txt_tanggalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_tanggalActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_tanggalActionPerformed

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

    private void txt_jumlahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_jumlahActionPerformed
          try {
            String total = "SELECT * FROM barang where id_barang='" + txt_idbarang.getText() + "'";
            Connection conn = (Connection) koneksi.getKoneksi();
            java.sql.Statement st = conn.createStatement();
            java.sql.ResultSet rs = st.executeQuery(total);
            if (rs.next()) {
                int stok = Integer.parseInt(rs.getString("stok"));
                int jumlah = Integer.parseInt(txt_jumlah.getText());
                int harga = Integer.parseInt(rs.getString("harga_beli"));
                this.txt_total.setText(String.valueOf(harga * jumlah));
            }
        } catch (SQLException | ClassNotFoundException e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }//GEN-LAST:event_txt_jumlahActionPerformed

    private void txt_totalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_totalActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_totalActionPerformed

    private void com_merkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_com_merkActionPerformed
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Id Produk");
        model.addColumn("Nama Produk");
        model.addColumn("Harga");
        model.addColumn("Stok");
        
        try{ 
        String nama = com_merk.getSelectedItem().toString();
        String sql = "SELECT * FROM barang WHERE merk_barang LIKE '%"+nama+"%'";
        Connection conn = (Connection) koneksi.getKoneksi();
        java.sql.Statement st = conn.createStatement();
        java.sql.ResultSet rs = st.executeQuery(sql);
        
        
       while (rs.next()) {
                model.addRow(new Object[]{rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(5)});
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

    private void btn_tambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_tambahActionPerformed
          if (txt_id.getText().equals("") ||txt_tanggal.getText().equals("") || 
                txt_jumlah.getText().equals("") || txt_total.getText().equals("") || txt_idSup.getText().equals("") ) {
            JOptionPane.showMessageDialog(this, "ISI SEMUA DATA TERLEBIH DAHULU");
        } 
        else {
              
        String productName = (String) com_merk.getSelectedItem();
        int qtyToAdd = Integer.parseInt(txt_jumlah.getText());

        String url = "jdbc:mysql://localhost:3306/test_dikasir2";
        String username = "root";
        String password = "";

        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            String selectQuery = "SELECT COUNT(*) FROM detail_pembelian WHERE merk_barang = ? AND id_pembelian = '" + txt_id.getText() + "'";
            
            try (PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {
                preparedStatement.setString(1, productName);
                ResultSet resultSet = preparedStatement.executeQuery();
                resultSet.next();
                int rowCount = resultSet.getInt(1);
                
                if (rowCount > 0) {
                    String updateQuery = "UPDATE detail_pembelian SET qty = qty + ? WHERE merk_barang = ? AND id_pembelian = '" + txt_id.getText() + "'";
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
                    String insertQuery = "INSERT INTO detail_pembelian (merk_barang, qty, sub_total, id_barang, id_pembelian, id_supplier) VALUES (?, ?, ?, ? , ?, ?)";
                    try (PreparedStatement insertStatement = connection.prepareStatement(insertQuery)) {
                        insertStatement.setString(1, productName);
                        insertStatement.setInt(2, qtyToAdd);
                        insertStatement.setInt(3, Integer.parseInt(txt_total.getText()));
                        insertStatement.setString(4, txt_idbarang.getText());
                        insertStatement.setString(5, txt_id.getText());
                        insertStatement.setString(6, (String) com_sup.getSelectedItem());
                        
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
       
        tabelPembelian();
        tabelBarang();
        clear();
        totalBiaya();
    }//GEN-LAST:event_btn_tambahActionPerformed

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
                    
                    
                String sql = "DELETE from detail_pembelian where id_pembelian ='" + txt_id.getText() + "'"
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
         tabelPembelian();
         clear();
         clearSemua();
         tabelBarang();
         
         
    }//GEN-LAST:event_btn_hapusActionPerformed

    private void btn_editActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_editActionPerformed
          
        
      /* try {
            String idPembelian = txt_id.getText();
            String idBarang = txt_idbarang.getText();
            String sql = "SELECT id_pembelian,jumlah_barang, barang.id_barang, barang.stok, "
                    + " FROM pembelian JOIN barang "
                    + "on barang.id_barang = pembelian.id_barang  WHERE "
                    + "id_pembelian ='" + idPembelian + "'";
            Connection conn = (Connection) koneksi.getKoneksi();
            java.sql.Statement st = conn.createStatement();
            java.sql.ResultSet res = st.executeQuery(sql);
            if (res.next()) {
                int lama = Integer.parseInt(res.getString("jumlah_barang"));
                int baru = Integer.parseInt(txt_jumlah.getText());
                

            }

        } catch (SQLException | ClassNotFoundException e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        } */
        
        
        
        
 
    }//GEN-LAST:event_btn_editActionPerformed

    private void btn_laporanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_laporanActionPerformed
        soundClick();
        setVisible(false);
       new laporan().setVisible(true);
    }//GEN-LAST:event_btn_laporanActionPerformed

    private void table_pembelianMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_table_pembelianMouseClicked
        try {
            String idpembelian = table_pembelian.getValueAt(table_pembelian.getSelectedRow(), 0).toString();
            String tanggal = txt_tanggal.getText();
            String nama = table_pembelian.getValueAt(table_pembelian.getSelectedRow(), 1).toString();
            String qty = table_pembelian.getValueAt(table_pembelian.getSelectedRow(), 2).toString();
            String hrg = table_pembelian.getValueAt(table_pembelian.getSelectedRow(), 3).toString();
            String idSup = table_pembelian.getValueAt(table_pembelian.getSelectedRow(), 4).toString();
            
            String sql = "SELECT id_pembelian,id_barang, qty, sub_total, merk_barang, id_supplier "
                    + "FROM detail_pembelian"
                    + " WHERE "
                    + "id_pembelian ='" + idpembelian + "'"
                //    + " and tanggal ='" + tanggal + "'"
                    + " and qty ='" + qty + "'"
                    + " and merk_barang ='" + nama + "'"
                    + " and sub_total ='" + hrg + "'"
                    + " and id_supplier ='" + idSup + "'";
            Connection conn = (Connection) koneksi.getKoneksi();
            java.sql.Statement st = conn.createStatement();
            java.sql.ResultSet res = st.executeQuery(sql);
            if (res.next()) {
                this.txt_id.setText(res.getString("id_pembelian"));
             // this.txt_tanggal.setText(res.getString("tanggal"));
            //  this.com_karyawan.setSelectedItem(res.getString("karyawan.id_karyawan")+" "+res.getString("karyawan.nama_karyawan"));
                this.txt_idbarang.setText(res.getString("id_barang"));
                this.com_merk.setSelectedItem(res.getString("merk_barang"));
                
                this.txt_jumlah.setText(res.getString("qty"));
                this.txt_total.setText(res.getString("sub_total"));
                this.txt_idSup.setText(res.getString("id_supplier"));

            }

        } catch (SQLException | ClassNotFoundException e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
        tabelPembelian();
    }//GEN-LAST:event_table_pembelianMouseClicked

    private void btn_transActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_transActionPerformed
      soundClick();
      
      dispose();
      new framePenjualan().setVisible(true);
    }//GEN-LAST:event_btn_transActionPerformed

    private void btn_dataActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_dataActionPerformed
        soundClick();
        setVisible(false);
       new master().setVisible(true);
    }//GEN-LAST:event_btn_dataActionPerformed

    private void btn_logoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_logoutActionPerformed
        soundClick();
 
    }//GEN-LAST:event_btn_logoutActionPerformed

    private void btn_dashboardActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_dashboardActionPerformed
        soundClick();
        setVisible(false);
      new dashboard().setVisible(true);
    }//GEN-LAST:event_btn_dashboardActionPerformed

    private void com_supActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_com_supActionPerformed
           try {
            String member = com_sup.getSelectedItem().toString();
            String sql = "SELECT * FROM supplier WHERE nama_supplier LIKE '%"+member+"%'";
            Connection conn = (Connection) koneksi.getKoneksi();
            java.sql.Statement st = conn.createStatement();
            java.sql.ResultSet rs = st.executeQuery(sql);
            if (rs.next()) {
                
               String idbarang = rs.getString("id_supplier");
              
                this.txt_idSup.setText(idbarang);
            }
        } catch (SQLException | ClassNotFoundException e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }//GEN-LAST:event_com_supActionPerformed

    private void btn_clearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_clearActionPerformed
        clear();
        tabelBarang();
    }//GEN-LAST:event_btn_clearActionPerformed

    private void btn_tambahkanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_tambahkanActionPerformed
               try {
//                rs.getString("id_transaksi").substring(1)
//                String idPegawai = com_karyawan.getSelectedItem().toString().substring(0, 6);
                String sql = "INSERT INTO pembelian (id_pembelian, tanggal, grand_total, id_supplier) VALUES ('" + txt_id.getText() + "','"
                        + txt_tanggal.getText() + "','" + txt_totalbayar.getText() + "','" +  txt_idSup.getText() + "')";
                    //    + txt_id.getText()+"')";
                java.sql.Connection conn = (Connection) koneksi.getKoneksi();
                java.sql.PreparedStatement pst = conn.prepareStatement(sql);
                pst.execute();
                JOptionPane.showMessageDialog(null, "penyimpanan Berhasil");
                clear();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, e.getMessage());
            }
         clearSemua();
         id_pembelian();
         tabelPembelian();
    }//GEN-LAST:event_btn_tambahkanActionPerformed

    private void txt_totalbayarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_totalbayarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_totalbayarActionPerformed

    private void txt_idSupActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_idSupActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_idSupActionPerformed

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

    private void txt_jumlahKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_jumlahKeyReleased
         int cekJumlah = Integer.parseInt(txt_jumlah.getText());
        if(cekJumlah<0){
             JOptionPane.showMessageDialog(null, "jumlah barang tidak boleh kurang dari 0");
             txt_jumlah.setText("");
             txt_total.setText("");
        } else{
                      try {
            String total = "SELECT * FROM barang where id_barang='" + txt_idbarang.getText() + "'";
            Connection conn = (Connection) koneksi.getKoneksi();
            java.sql.Statement st = conn.createStatement();
            java.sql.ResultSet rs = st.executeQuery(total);
            if (rs.next()) {
                int stok = Integer.parseInt(rs.getString("stok"));
                int jumlah = Integer.parseInt(txt_jumlah.getText());
                int harga = Integer.parseInt(rs.getString("harga_beli"));
                this.txt_total.setText(String.valueOf(harga * jumlah));
            }
        } catch (SQLException | ClassNotFoundException e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
        }
    }//GEN-LAST:event_txt_jumlahKeyReleased

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
            java.util.logging.Logger.getLogger(framePembelian.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(framePembelian.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(framePembelian.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(framePembelian.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new framePembelian().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_clear;
    private javax.swing.JButton btn_dashboard;
    private javax.swing.JButton btn_data;
    private javax.swing.JButton btn_edit;
    private javax.swing.JButton btn_hapus;
    private javax.swing.JButton btn_laporan;
    private javax.swing.JButton btn_logout;
    private javax.swing.JButton btn_tambah;
    private javax.swing.JButton btn_tambahkan;
    private javax.swing.JButton btn_trans;
    private javax.swing.JComboBox<String> com_merk;
    private javax.swing.JComboBox<String> com_sup;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable table_barang;
    private javax.swing.JTable table_pembelian;
    private javax.swing.JTextField txt_id;
    private javax.swing.JTextField txt_idSup;
    private javax.swing.JTextField txt_idbarang;
    private javax.swing.JTextField txt_jumlah;
    private javax.swing.JTextField txt_tanggal;
    private javax.swing.JTextField txt_total;
    private javax.swing.JTextField txt_totalbayar;
    // End of variables declaration//GEN-END:variables
}
