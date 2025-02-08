package salesperson;

import admin.*;
import java.awt.Font;
import java.awt.HeadlessException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.RowFilter;
import javax.swing.table.TableRowSorter;


public class spPurchase extends javax.swing.JFrame {

    String salesperson;
   
    public spPurchase(String name) {
        this.salesperson = name;
        initComponents();
        Connect();
        showPCart();
        table();  
     
    }

    Connection con; 
    PreparedStatement pst;
    ResultSet rs; 

    public final void Connect() {       
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost/salesmanagement", "root", "");
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(adminProduct.class.getName()).log(Level.SEVERE, null, ex);
        }
       
    }
    
    public final void showPCart() {
        try {
            pst = con.prepareStatement("SELECT * FROM product WHERE Stock > 0");
            rs = pst.executeQuery();
            
            DefaultTableModel productModel = (DefaultTableModel) tblpcart.getModel();
            productModel.setRowCount(0);

            while (rs.next()) {
                int productID = rs.getInt("productID");
                String name = rs.getString("name");
                String category = rs.getString("category");
                double price = rs.getDouble("price");
                int stock = rs.getInt("stock");

                productModel.addRow(new Object[]{productID, name, category, price, stock});
            }
        }   catch (SQLException ex) {
            Logger.getLogger(adminProduct.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
        
    public final void table(){
        tblpcart.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
        tblcart.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
        txtpay.setFocusable(false);
        txtchange.setFocusable(false);
        txttotal.setFocusable(false);
        carttotal.setEditable(false);
        
    }
 

 
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        txtqtn = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblcart = new javax.swing.JTable();
        jLabel7 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        carttotal = new javax.swing.JTextField();
        jPanel6 = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        txtpid = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtqnt = new javax.swing.JTextField();
        txttotal = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        d = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tblpcart = new javax.swing.JTable();
        jLabel9 = new javax.swing.JLabel();
        s1 = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        txtpay = new javax.swing.JTextField();
        txtchange = new javax.swing.JTextField();
        dtdate = new com.toedter.calendar.JDateChooser();
        jPanel3 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        jPanel8 = new javax.swing.JPanel();
        jButton2 = new javax.swing.JLabel();

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setLocation(new java.awt.Point(415, 200));
        setUndecorated(true);

        txtqtn.setBackground(new java.awt.Color(0, 51, 0));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        tblcart.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Product ID", "Quantity", "Total", "Date"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblcart.setRowHeight(35);
        tblcart.setSelectionBackground(new java.awt.Color(0, 51, 0));
        tblcart.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblcartMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(tblcart);
        if (tblcart.getColumnModel().getColumnCount() > 0) {
            tblcart.getColumnModel().getColumn(0).setResizable(false);
            tblcart.getColumnModel().getColumn(1).setResizable(false);
            tblcart.getColumnModel().getColumn(2).setResizable(false);
            tblcart.getColumnModel().getColumn(3).setResizable(false);
        }

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel7.setText("Cart");

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel4.setText("Total:");

        carttotal.setFont(new java.awt.Font("Helvetica Neue", 1, 18)); // NOI18N
        carttotal.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        carttotal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                carttotalActionPerformed(evt);
            }
        });

        jPanel6.setBackground(new java.awt.Color(0, 51, 0));

        jLabel14.setFont(new java.awt.Font("Helvetica Neue", 1, 14)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(255, 255, 255));
        jLabel14.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel14.setText("REMOVE");
        jLabel14.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel14MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel14, javax.swing.GroupLayout.DEFAULT_SIZE, 75, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel14, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
        );

        jPanel7.setBackground(new java.awt.Color(0, 51, 0));

        jLabel15.setFont(new java.awt.Font("Helvetica Neue", 1, 14)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(255, 255, 255));
        jLabel15.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel15.setText("PROCEED");
        jLabel15.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel15MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel15, javax.swing.GroupLayout.DEFAULT_SIZE, 75, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel15, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 184, Short.MAX_VALUE)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(carttotal, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(89, 89, 89)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel2Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 342, Short.MAX_VALUE)
                    .addContainerGap()))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(carttotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 217, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel6, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel7, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel2Layout.createSequentialGroup()
                    .addGap(56, 56, 56)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 189, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(48, Short.MAX_VALUE)))
        );

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Product ID");

        txtpid.setBackground(new java.awt.Color(0, 51, 0));
        txtpid.setFont(new java.awt.Font("Helvetica Neue", 0, 14)); // NOI18N
        txtpid.setForeground(new java.awt.Color(255, 255, 255));
        txtpid.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(255, 255, 255)));
        txtpid.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtpidActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Quantity");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Total");

        txtqnt.setBackground(new java.awt.Color(0, 51, 0));
        txtqnt.setFont(new java.awt.Font("Helvetica Neue", 0, 14)); // NOI18N
        txtqnt.setForeground(new java.awt.Color(255, 255, 255));
        txtqnt.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(255, 255, 255)));
        txtqnt.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtqntKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtqntKeyTyped(evt);
            }
        });

        txttotal.setBackground(new java.awt.Color(0, 51, 0));
        txttotal.setFont(new java.awt.Font("Helvetica Neue", 0, 14)); // NOI18N
        txttotal.setForeground(new java.awt.Color(255, 255, 255));
        txttotal.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(255, 255, 255)));

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("Date");

        d.setBackground(new java.awt.Color(255, 255, 255));

        tblpcart.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null}
            },
            new String [] {
                "ID", "Name", "Category", "Price"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblpcart.setRowHeight(35);
        tblpcart.setSelectionBackground(new java.awt.Color(0, 51, 0));
        tblpcart.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblpcartMouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(tblpcart);

        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel9.setText("Products");

        s1.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));
        s1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                s1KeyReleased(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Helvetica Neue", 0, 14)); // NOI18N
        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8-search-20.png"))); // NOI18N

        javax.swing.GroupLayout dLayout = new javax.swing.GroupLayout(d);
        d.setLayout(dLayout);
        dLayout.setHorizontalGroup(
            dLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, dLayout.createSequentialGroup()
                .addContainerGap(224, Short.MAX_VALUE)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(s1, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(dLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(dLayout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(dLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(dLayout.createSequentialGroup()
                            .addComponent(jLabel9)
                            .addGap(0, 167, Short.MAX_VALUE))
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 318, Short.MAX_VALUE))
                    .addContainerGap()))
        );
        dLayout.setVerticalGroup(
            dLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dLayout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(dLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5)
                    .addComponent(s1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(dLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(dLayout.createSequentialGroup()
                    .addGap(13, 13, 13)
                    .addComponent(jLabel9)
                    .addGap(18, 18, 18)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 223, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(17, Short.MAX_VALUE)))
        );

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Payment");

        jLabel10.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setText("Change");

        txtpay.setBackground(new java.awt.Color(0, 51, 0));
        txtpay.setFont(new java.awt.Font("Helvetica Neue", 0, 14)); // NOI18N
        txtpay.setForeground(new java.awt.Color(255, 255, 255));
        txtpay.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(255, 255, 255)));
        txtpay.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtpayActionPerformed(evt);
            }
        });
        txtpay.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtpayKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtpayKeyReleased(evt);
            }
        });

        txtchange.setBackground(new java.awt.Color(0, 51, 0));
        txtchange.setFont(new java.awt.Font("Helvetica Neue", 0, 14)); // NOI18N
        txtchange.setForeground(new java.awt.Color(255, 255, 255));
        txtchange.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(255, 255, 255)));
        txtchange.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtchangeActionPerformed(evt);
            }
        });

        dtdate.setBackground(new java.awt.Color(0, 51, 0));
        dtdate.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));

        jLabel11.setFont(new java.awt.Font("Helvetica Neue", 1, 14)); // NOI18N
        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel11.setText("ADD");
        jLabel11.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel11MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, 95, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
        );

        jButton2.setFont(new java.awt.Font("Helvetica Neue", 1, 14)); // NOI18N
        jButton2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jButton2.setText("PURCHASE");
        jButton2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton2MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout txtqtnLayout = new javax.swing.GroupLayout(txtqtn);
        txtqtn.setLayout(txtqtnLayout);
        txtqtnLayout.setHorizontalGroup(
            txtqtnLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(txtqtnLayout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(txtqtnLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(txtqtnLayout.createSequentialGroup()
                        .addComponent(d, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 17, Short.MAX_VALUE)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(txtqtnLayout.createSequentialGroup()
                        .addGroup(txtqtnLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3))
                        .addGap(18, 18, 18)
                        .addGroup(txtqtnLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtpid, javax.swing.GroupLayout.DEFAULT_SIZE, 101, Short.MAX_VALUE)
                            .addComponent(txtqnt)
                            .addComponent(txttotal))
                        .addGap(39, 39, 39)
                        .addComponent(jLabel8)
                        .addGap(18, 18, 18)
                        .addGroup(txtqtnLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(dtdate, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(txtqtnLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, txtqtnLayout.createSequentialGroup()
                                .addComponent(jLabel6)
                                .addGap(18, 18, 18)
                                .addComponent(txtpay, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, txtqtnLayout.createSequentialGroup()
                                .addComponent(jLabel10)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(txtqtnLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(txtchange, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE))))))
                .addGap(22, 22, 22))
        );
        txtqtnLayout.setVerticalGroup(
            txtqtnLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, txtqtnLayout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addGroup(txtqtnLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(txtqtnLayout.createSequentialGroup()
                        .addGroup(txtqtnLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(txtpid, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel8))
                        .addGap(18, 18, 18)
                        .addGroup(txtqtnLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(txtqnt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(txtqtnLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(txttotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(txtqtnLayout.createSequentialGroup()
                        .addGroup(txtqtnLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(txtqtnLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel6)
                                .addComponent(txtpay, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(dtdate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(txtqtnLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(txtqtnLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(txtchange, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel10)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 24, Short.MAX_VALUE)
                .addGroup(txtqtnLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(d, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(14, 14, 14))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(txtqtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(txtqtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void tblcartMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblcartMouseClicked

    }//GEN-LAST:event_tblcartMouseClicked

    private void tblpcartMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblpcartMouseClicked
        int selectedRow = tblpcart.getSelectedRow();
            if (selectedRow != -1) {
                txtpid.setText(tblpcart.getValueAt(selectedRow, 0).toString()); 
                
              
                 
            }
    }//GEN-LAST:event_tblpcartMouseClicked

    private void txtqntKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtqntKeyReleased
        try {
            if (txtpid.getText().isEmpty() || txtqnt.getText().isEmpty()) {
                return; 
            }

            int productID = Integer.parseInt(txtpid.getText());
            int qnt = Integer.parseInt(txtqnt.getText());

            String sql = "SELECT price FROM product WHERE productID = ?";

            try (PreparedStatement pst2 = con.prepareStatement(sql)) {
                pst2.setInt(1, productID); 

                try (ResultSet pst3 = pst2.executeQuery()) {
                    if (pst3.next()) {
                        double price = pst3.getDouble("price"); 
                        double total = price * qnt; 

                        txttotal.setText(String.format("%.2f", total));
                    } else {

                        JOptionPane.showMessageDialog(this, "Product not found!", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }

        } catch (SQLException ex) {
            Logger.getLogger(spPurchase.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(this, "Database error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please enter valid numbers for Product ID and Quantity.", "Input Error", JOptionPane.ERROR_MESSAGE);
        }       
    }//GEN-LAST:event_txtqntKeyReleased

    private void txtqntKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtqntKeyTyped
       
    }//GEN-LAST:event_txtqntKeyTyped

    private void carttotalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_carttotalActionPerformed
        
    }//GEN-LAST:event_carttotalActionPerformed

    private void txtpayKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtpayKeyReleased
        try {
            String totalText = carttotal.getText().trim().replace(",", "");

            if (totalText.isEmpty()) {
                JOptionPane.showMessageDialog(this, "No product in Cart!", "Error", JOptionPane.ERROR_MESSAGE);
                txtpay.setText("");
                return;
            }

            double totalAmount = Double.parseDouble(totalText);

            String payText = txtpay.getText().trim().replace(",", "");

            if (payText.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter a payment amount!", "Input Error", JOptionPane.ERROR_MESSAGE);
                txtchange.setText("0.00");
                return;
            }

            if (!payText.matches("^\\d+(\\.\\d+)?$")) { 
                JOptionPane.showMessageDialog(this, "Payment must be a valid number!", "Input Error", JOptionPane.ERROR_MESSAGE);
                txtpay.setText(""); 
                txtchange.setText("0.00");
                return;
            }

            double payAmount = Double.parseDouble(payText);
            double changeAmount = payAmount - totalAmount;

            txtchange.setText(String.format("%.2f", changeAmount));

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid input! Please enter a valid number.", "Input Error", JOptionPane.ERROR_MESSAGE);
            txtpay.setText("");
            txtchange.setText("0.00");
        }

    }//GEN-LAST:event_txtpayKeyReleased

    private void txtpidActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtpidActionPerformed
       
    }//GEN-LAST:event_txtpidActionPerformed

    private void s1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_s1KeyReleased

        DefaultTableModel obj =(DefaultTableModel) tblpcart.getModel();
        TableRowSorter<DefaultTableModel> obj1=new TableRowSorter<>(obj);
        tblpcart.setRowSorter(obj1);
        obj1.setRowFilter(RowFilter.regexFilter(s1.getText()));

    }//GEN-LAST:event_s1KeyReleased

    private void txtpayKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtpayKeyPressed

    }//GEN-LAST:event_txtpayKeyPressed

    private void txtchangeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtchangeActionPerformed
       
    }//GEN-LAST:event_txtchangeActionPerformed

    private void jLabel11MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel11MouseClicked
        try {
            String productID = txtpid.getText();
            String qtn = txtqnt.getText(); 
            String total = txttotal.getText();
            java.util.Date date = dtdate.getDate();

            DefaultTableModel model = (DefaultTableModel) tblcart.getModel();

            if (productID.isEmpty() || qtn.isEmpty() || total.isEmpty() || date == null) {
                JOptionPane.showMessageDialog(this, "Please fill all fields", "Error", JOptionPane.ERROR_MESSAGE);
                return; 
            }

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String formattedDate = dateFormat.format(date);

            model.addRow(new Object[]{productID, qtn, total, formattedDate});

            txtpid.setText("");
            txtqnt.setText("");
            txttotal.setText("");
            dtdate.setDate(null);

            double sum = 0;
            for (int i = 0; i < tblcart.getRowCount(); i++) {

                Object valueObj = tblcart.getValueAt(i, 2); 

                if (valueObj != null) {
                    try {
                        double rowTotal = Double.parseDouble(valueObj.toString().trim());
                        sum += rowTotal; 
                    } catch (NumberFormatException e) {
                        JOptionPane.showMessageDialog(this, "Invalid total value at row " + (i + 1), "Error", JOptionPane.ERROR_MESSAGE);
                        return; 
                    }
                }
            }
            NumberFormat formatter = NumberFormat.getInstance(Locale.US);
            formatter.setMinimumFractionDigits(2);
            formatter.setMaximumFractionDigits(2);

            carttotal.setText(formatter.format(sum));

        } catch (HeadlessException e) {
            JOptionPane.showMessageDialog(this, "An error occurred: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }


    }//GEN-LAST:event_jLabel11MouseClicked

    private void jLabel14MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel14MouseClicked
        int selectedRow = tblcart.getSelectedRow();
        if (selectedRow != -1) {

            Object valueObj = tblcart.getValueAt(selectedRow, 2);

            if (valueObj != null) {
                try {
                    double rowTotal = Double.parseDouble(valueObj.toString().trim());

                    double currentTotal = Double.parseDouble(carttotal.getText().replace(",", "").trim()); // Assuming carttotal is a JTextField
                    double newTotal = currentTotal - rowTotal;

                    NumberFormat formatter = NumberFormat.getInstance(Locale.US);
                    formatter.setMinimumFractionDigits(2);
                    formatter.setMaximumFractionDigits(2);

                    carttotal.setText(formatter.format(newTotal));

                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(this, "Invalid total value in selected row.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }

            DefaultTableModel model = (DefaultTableModel) tblcart.getModel();
            model.removeRow(selectedRow);

            JOptionPane.showMessageDialog(null, "Item removed from cart", "Success", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, "Please select a row to delete", "Error", JOptionPane.ERROR_MESSAGE);
        }

        
        
    }//GEN-LAST:event_jLabel14MouseClicked

    private void jLabel15MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel15MouseClicked
        txtpay.setFocusable(true);
        jButton2.setEnabled(true);
        
    }//GEN-LAST:event_jLabel15MouseClicked

    private void jButton2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton2MouseClicked
        
        try {
 
            if (txtpay.getText().isEmpty() || txtchange.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill all fields", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
           
            if (Double.parseDouble(txtchange.getText().trim()) < 0){
                JOptionPane.showMessageDialog(this, "Payment is not enough! Please pay the full amount", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int confirm = JOptionPane.showConfirmDialog(this, "Are you sure do you want to purchase this product/s?", 
                                                        "Confirm Update", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                con.setAutoCommit(false);

                String salesQuery = "INSERT INTO sales (productID, salesperson, quantity, total, sale_date) VALUES (?, ?, ?, ?, ?)";
                String stockUpdateQuery = "UPDATE product SET stock = stock - ? WHERE productID = ?";

                pst = con.prepareStatement(salesQuery);
                try (PreparedStatement pstStock = con.prepareStatement(stockUpdateQuery)) {
                    DefaultTableModel model = (DefaultTableModel) tblcart.getModel();
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

                    for (int i = 0; i < model.getRowCount(); i++) {
                        try {
                            Object productIDObj = model.getValueAt(i, 0);
                            Object quantityObj = model.getValueAt(i, 1);
                            Object totalObj = model.getValueAt(i, 2);
                            Object dateObj = model.getValueAt(i, 3);

                            if (productIDObj == null || quantityObj == null || totalObj == null || dateObj == null) {
                                JOptionPane.showMessageDialog(null, "Missing data in row " + (i + 1));
                                continue;
                            }

                            int productID = Integer.parseInt(productIDObj.toString().trim());
                            String spname = this.salesperson;
                            int quantity = Integer.parseInt(quantityObj.toString().trim());
                            double total = Double.parseDouble(totalObj.toString().trim());
                            String dateString = dateObj.toString().trim();

                            java.util.Date utilDate = dateFormat.parse(dateString);
                            java.sql.Date saleDate = new java.sql.Date(utilDate.getTime());

                            pst.setInt(1, productID);
                            pst.setString(2, spname);
                            pst.setInt(3, quantity);
                            pst.setDouble(4, total);
                            pst.setDate(5, saleDate);
                            pst.addBatch();
                            
                            pstStock.setInt(1, quantity);
                            pstStock.setInt(2, productID);
                            pstStock.addBatch();

                        } catch (NumberFormatException ex) {
                            JOptionPane.showMessageDialog(null, "Invalid number format in row " + (i + 1), "Error", JOptionPane.ERROR_MESSAGE);
                        } catch (ParseException ex) {
                            JOptionPane.showMessageDialog(null, "Error parsing date in row " + (i + 1) + ": " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }

                    pst.executeBatch();
                    pstStock.executeBatch();

                    con.commit();
                    pst.close();
                }

                JOptionPane.showMessageDialog(null, "Sales recorded successfully!");
                DefaultTableModel model = (DefaultTableModel) tblcart.getModel();
                model.setRowCount(0);

                txtpid.setText("");
                txtqnt.setText("");
                txttotal.setText("");
                txtpay.setText("");
                txtchange.setText("");
                dtdate.setDate(null);
                carttotal.setText("");
                
                txtpay.setFocusable(false);
                jButton2.setEnabled(false);

                showPCart();
            } else {
                JOptionPane.showMessageDialog(this, "Transaction canceled!", "Action Canceled", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (SQLException ex) {
            Logger.getLogger(spPurchase.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(this, "Database error: " + ex.getMessage(), "SQL Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_jButton2MouseClicked

    private void txtpayActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtpayActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtpayActionPerformed

   
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
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(spPurchase.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            
        String salespersonName = "Default User"; // Change this or pass the actual name
        new spPurchase(salespersonName).setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField carttotal;
    private javax.swing.JPanel d;
    private com.toedter.calendar.JDateChooser dtdate;
    private javax.swing.JLabel jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTextField s1;
    private javax.swing.JTable tblcart;
    private javax.swing.JTable tblpcart;
    private javax.swing.JTextField txtchange;
    private javax.swing.JTextField txtpay;
    private javax.swing.JTextField txtpid;
    private javax.swing.JTextField txtqnt;
    private javax.swing.JPanel txtqtn;
    private javax.swing.JTextField txttotal;
    // End of variables declaration//GEN-END:variables
}
