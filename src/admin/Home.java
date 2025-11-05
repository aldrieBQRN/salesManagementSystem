package admin;

import org.jfree.chart.plot.CategoryPlot;
import org.jfree.data.category.DefaultCategoryDataset;
import java.awt.Color;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import login.Login;
import salesperson.spHome;

public final class Home extends javax.swing.JFrame {

    public Home() {
        initComponents();
        Connect();
        totalSale();
        revenue();
        displayLowStock();
        getTopProduct();       
        showPieChart();
        showBarChart();
        fixedSize();
    }
    
    Connection con; 
    PreparedStatement pst;
    ResultSet rs; 

    public void Connect() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost/salesmanagement", "root", "");
            System.out.println("Database connection successful.");
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(Home.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Error connecting to database: " + ex.getMessage());
        }
    }
    
     public final void totalSale(){
        try {
            double totalSales = 0.0;
            String query = "SELECT SUM(total) AS total_sales FROM sales";
            pst = con.prepareStatement(query);
            rs = pst.executeQuery();
            
            if (rs.next()) {
                totalSales = rs.getDouble("total_sales");
            }
            
            rs.close();
            pst.close();

            NumberFormat formatter = NumberFormat.getInstance(Locale.US);
            formatter.setMinimumFractionDigits(2);
            formatter.setMaximumFractionDigits(2);

            txttotalsales.setText(formatter.format(totalSales));

        } catch (SQLException ex) {
            Logger.getLogger(Home.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Error fetching total sales: " + ex.getMessage());
        }
    } 
     
    public void revenue() {
        try {
            Date selectedDate = new Date(); 
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String formattedDate = dateFormat.format(selectedDate);
            System.out.println("Formatted date: " + formattedDate);

            String query = "SELECT SUM(total) AS total_revenue FROM sales WHERE sale_date >= CURDATE() - INTERVAL 30 DAY";
            pst = con.prepareStatement(query);
            rs = pst.executeQuery();
            
            double totalRevenue = 0.0;
            if (rs.next()) {
                totalRevenue = rs.getDouble("total_revenue");
            }

            rs.close();
            pst.close();

            NumberFormat formatter = NumberFormat.getInstance(Locale.US);
            formatter.setMinimumFractionDigits(2);
            formatter.setMaximumFractionDigits(2);

            txtrevenue.setText(formatter.format(totalRevenue));

        } catch (SQLException ex) {
            Logger.getLogger(Home.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
      
    public void displayLowStock() {
        try {
            String query = "SELECT name, stock FROM product WHERE stock <= 10"; 
            pst = con.prepareStatement(query);
            rs = pst.executeQuery();

            String lowestStockProductName = "";
            int lowestStock = Integer.MAX_VALUE;

            while (rs.next()) {
                String productName = rs.getString("name");
                int productStock = rs.getInt("stock");

                if (productStock < lowestStock) {
                    lowestStock = productStock;
                    lowestStockProductName = productName;
                }
            }

            if (!lowestStockProductName.isEmpty()) {
                txtstock.setFont(new Font("Helvetica Neue", Font.BOLD, 24));
                txtstock.setForeground(Color.red);
                txtpname.setText(lowestStockProductName);               
                txtstock.setText(String.valueOf(lowestStock + " Stocks")); 
            } else {
                txtstock.setFont(new Font("Segoe UI", Font.BOLD, 16));
                txtstock.setText("Stock Sufficient");
                txtpname.setText(""); 
            }

            rs.close();
            pst.close();

        } catch (SQLException ex) {
            Logger.getLogger(Home.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(this, "Error retrieving low stock products: " + ex.getMessage());
        }

    }
    
    
    public void showPieChart() {
        try {   
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/salesmanagement", "root", "");
            DefaultPieDataset pieDataset = new DefaultPieDataset();
            String query = "SELECT p.name AS productName, SUM(s.quantity) AS total_quantity " +
                    "FROM sales s JOIN product p ON s.productID = p.productID " +
                    "GROUP BY s.productID";
            pst = con.prepareStatement(query);
            rs = pst.executeQuery();           
          
            while (rs.next()) {
                String productName = rs.getString("productName");
                int quantity = rs.getInt("total_quantity");
                pieDataset.setValue(productName, quantity);
            }
                  
            JFreeChart pieChartObject = ChartFactory.createPieChart("", pieDataset, false, true, false);  
            PiePlot piePlot = (PiePlot) pieChartObject.getPlot();
            piePlot.setBackgroundPaint(Color.WHITE);
                 
            ChartPanel chartPanel = new ChartPanel(pieChartObject);
            chartPanel.setPreferredSize(new java.awt.Dimension(300, 200));
            pieChart.removeAll(); 
            pieChart.setLayout(new BorderLayout());
            pieChart.add(chartPanel, BorderLayout.CENTER);
            pieChart.validate();
                       
            rs.close();
            pst.close();
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(Home.class.getName()).log(Level.SEVERE, null, ex);
        }
    
    }   
 
    public void showBarChart() {
        try {         
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/salesmanagement", "root", "");                     
            DefaultCategoryDataset dataset = new DefaultCategoryDataset();           
            String query = "SELECT YEAR(s.sale_date) AS year, SUM(s.total) AS total_sales " +
                    "FROM sales s WHERE YEAR(s.sale_date) BETWEEN 2020 AND 2025 " +
                    "GROUP BY YEAR(s.sale_date)";
            pst = con.prepareStatement(query);
            rs = pst.executeQuery();
          
            while (rs.next()) {
                int year = rs.getInt("year");
                double totalSales = rs.getDouble("total_sales");
                dataset.addValue(totalSales, "Sales", String.valueOf(year));
            }
                     
            JFreeChart barChart = ChartFactory.createBarChart(
                    "",   
                    "",                 
                    "",             
                    dataset,                
                    org.jfree.chart.plot.PlotOrientation.VERTICAL,
                    true,                  
                    true,               
                    false                   
            );
                     
            CategoryPlot plot = barChart.getCategoryPlot();
            plot.setBackgroundPaint(Color.white);  
                      
            ChartPanel chartPanel = new ChartPanel(barChart);
            chartPanel.setPreferredSize(new java.awt.Dimension(300, 200));  
            barGraph.removeAll();  
            barGraph.setLayout(new BorderLayout());
            barGraph.add(chartPanel, BorderLayout.CENTER);
            barGraph.validate();
           
            rs.close();
            pst.close();
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(Home.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
    public void getTopProduct() {
        String query = "SELECT p.name, SUM(s.quantity) AS total_sales " +
                       "FROM sales s " +
                       "JOIN product p ON s.productID = p.productID " +
                       "GROUP BY s.productID " +
                       "ORDER BY total_sales DESC " +
                       "LIMIT 1";

        try {
            pst = con.prepareStatement(query);
            rs = pst.executeQuery();

            if (rs.next()) {
                String productName = rs.getString("name");
                int totalSales = rs.getInt("total_sales");

                txtproduct.setText(productName); 
                txtsold.setText(totalSales + " solds"); 
            }
        } catch (SQLException ex) {
            Logger.getLogger(spHome.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
    public void fixedSize(){
        pn1.setPreferredSize(new Dimension(173, 125));
        pn1.setMinimumSize(new Dimension(173, 125));
        pn1.setMaximumSize(new Dimension(173, 125));
        pn1.setSize(173, 125);
        pn1.setLayout(null);
        
        pn2.setPreferredSize(new Dimension(173, 125));
        pn2.setMinimumSize(new Dimension(173, 125));
        pn2.setMaximumSize(new Dimension(173, 125));
        pn2.setSize(173, 125);
        pn2.setLayout(null);
        
        pn3.setPreferredSize(new Dimension(173, 125));
        pn3.setMinimumSize(new Dimension(173, 125));
        pn3.setMaximumSize(new Dimension(173, 125));
        pn3.setSize(173, 125);
        pn3.setLayout(null);
        
        pn4.setPreferredSize(new Dimension(173, 125));
        pn4.setMinimumSize(new Dimension(173, 125));
        pn4.setMaximumSize(new Dimension(173, 125));
        pn4.setSize(173, 125);
        pn4.setLayout(null);
        
    }
  
   
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTextField4 = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        pnlhome = new javax.swing.JPanel();
        txthome = new javax.swing.JLabel();
        pnlpd = new javax.swing.JPanel();
        txtpd = new javax.swing.JLabel();
        pnlsls = new javax.swing.JPanel();
        txtsls = new javax.swing.JLabel();
        pnlsp = new javax.swing.JPanel();
        txtsp = new javax.swing.JLabel();
        pnlsrs = new javax.swing.JPanel();
        txtsrs = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        pieChart = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        barGraph = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        pn1 = new javax.swing.JPanel();
        txttotalsales = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        pn2 = new javax.swing.JPanel();
        txtrevenue = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        pn3 = new javax.swing.JPanel();
        txtproduct = new javax.swing.JLabel();
        txtsold = new javax.swing.JLabel();
        pn4 = new javax.swing.JPanel();
        txtstock = new javax.swing.JLabel();
        txtpname = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();

        jTextField4.setText("jTextField1");

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel5.setText("Top Product");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setForeground(new java.awt.Color(204, 204, 204));

        jPanel2.setBackground(new java.awt.Color(0, 51, 0));
        jPanel2.setPreferredSize(new java.awt.Dimension(167, 573));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("WELCOME,");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Admin");

        pnlhome.setBackground(new java.awt.Color(0, 51, 0));

        txthome.setBackground(new java.awt.Color(0, 51, 0));
        txthome.setFont(new java.awt.Font("Helvetica Neue", 1, 14)); // NOI18N
        txthome.setForeground(new java.awt.Color(255, 255, 255));
        txthome.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        txthome.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8-home-30.png"))); // NOI18N
        txthome.setText("HOME");
        txthome.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txthomeMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout pnlhomeLayout = new javax.swing.GroupLayout(pnlhome);
        pnlhome.setLayout(pnlhomeLayout);
        pnlhomeLayout.setHorizontalGroup(
            pnlhomeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlhomeLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txthome, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnlhomeLayout.setVerticalGroup(
            pnlhomeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlhomeLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(txthome)
                .addGap(6, 6, 6))
        );

        pnlpd.setBackground(new java.awt.Color(0, 51, 0));
        pnlpd.setForeground(new java.awt.Color(255, 255, 255));

        txtpd.setFont(new java.awt.Font("Helvetica Neue", 1, 14)); // NOI18N
        txtpd.setForeground(new java.awt.Color(255, 255, 255));
        txtpd.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        txtpd.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8-product-30.png"))); // NOI18N
        txtpd.setText("PRODUCT");
        txtpd.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtpdMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout pnlpdLayout = new javax.swing.GroupLayout(pnlpd);
        pnlpd.setLayout(pnlpdLayout);
        pnlpdLayout.setHorizontalGroup(
            pnlpdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlpdLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txtpd, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnlpdLayout.setVerticalGroup(
            pnlpdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlpdLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(txtpd)
                .addGap(6, 6, 6))
        );

        pnlsls.setBackground(new java.awt.Color(0, 51, 0));

        txtsls.setFont(new java.awt.Font("Helvetica Neue", 1, 14)); // NOI18N
        txtsls.setForeground(new java.awt.Color(255, 255, 255));
        txtsls.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        txtsls.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8-sales-30 (1).png"))); // NOI18N
        txtsls.setText("SALES");
        txtsls.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtslsMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout pnlslsLayout = new javax.swing.GroupLayout(pnlsls);
        pnlsls.setLayout(pnlslsLayout);
        pnlslsLayout.setHorizontalGroup(
            pnlslsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlslsLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txtsls, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnlslsLayout.setVerticalGroup(
            pnlslsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlslsLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(txtsls)
                .addGap(6, 6, 6))
        );

        pnlsp.setBackground(new java.awt.Color(0, 51, 0));

        txtsp.setFont(new java.awt.Font("Helvetica Neue", 1, 14)); // NOI18N
        txtsp.setForeground(new java.awt.Color(255, 255, 255));
        txtsp.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        txtsp.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8-reseller-30.png"))); // NOI18N
        txtsp.setText("SALESPERSON");
        txtsp.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtspMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout pnlspLayout = new javax.swing.GroupLayout(pnlsp);
        pnlsp.setLayout(pnlspLayout);
        pnlspLayout.setHorizontalGroup(
            pnlspLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlspLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txtsp, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        pnlspLayout.setVerticalGroup(
            pnlspLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlspLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(txtsp)
                .addGap(6, 6, 6))
        );

        pnlsrs.setBackground(new java.awt.Color(0, 51, 0));

        txtsrs.setBackground(new java.awt.Color(0, 51, 0));
        txtsrs.setFont(new java.awt.Font("Helvetica Neue", 1, 14)); // NOI18N
        txtsrs.setForeground(new java.awt.Color(255, 255, 255));
        txtsrs.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        txtsrs.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8-users-30.png"))); // NOI18N
        txtsrs.setText("USERS");
        txtsrs.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtsrsMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout pnlsrsLayout = new javax.swing.GroupLayout(pnlsrs);
        pnlsrs.setLayout(pnlsrsLayout);
        pnlsrsLayout.setHorizontalGroup(
            pnlsrsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlsrsLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txtsrs, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnlsrsLayout.setVerticalGroup(
            pnlsrsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlsrsLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(txtsrs)
                .addGap(10, 10, 10))
        );

        jLabel17.setFont(new java.awt.Font("Helvetica Neue", 1, 14)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(255, 255, 255));
        jLabel17.setText("LOG OUT");
        jLabel17.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel17MouseClicked(evt);
            }
        });

        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel13.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8-fast-cart-55.png"))); // NOI18N

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlsls, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(pnlsrs, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(pnlhome, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(pnlsp, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(51, 51, 51)
                        .addComponent(jLabel17)
                        .addGap(0, 46, Short.MAX_VALUE))
                    .addComponent(pnlpd, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(32, 32, 32))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(55, 55, 55))))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(jLabel13)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addGap(33, 33, 33)
                .addComponent(pnlhome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnlsp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnlpd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnlsls, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnlsrs, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 126, Short.MAX_VALUE)
                .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20))
        );

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 51, 0));
        jLabel3.setText("SALE MANAGEMENT SYSTEM");

        javax.swing.GroupLayout pieChartLayout = new javax.swing.GroupLayout(pieChart);
        pieChart.setLayout(pieChartLayout);
        pieChartLayout.setHorizontalGroup(
            pieChartLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 288, Short.MAX_VALUE)
        );
        pieChartLayout.setVerticalGroup(
            pieChartLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 176, Short.MAX_VALUE)
        );

        jLabel12.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(0, 51, 0));
        jLabel12.setText("SALES PERFORMACE");

        javax.swing.GroupLayout barGraphLayout = new javax.swing.GroupLayout(barGraph);
        barGraph.setLayout(barGraphLayout);
        barGraphLayout.setHorizontalGroup(
            barGraphLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 288, Short.MAX_VALUE)
        );
        barGraphLayout.setVerticalGroup(
            barGraphLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 176, Short.MAX_VALUE)
        );

        jLabel10.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel10.setText("LOW STOCK");

        jLabel14.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel14.setText("TOP PRODUCT");

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel4.setText("REVENUE");

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        jLabel6.setText("TOTAL SALES");

        pn1.setBorder(javax.swing.BorderFactory.createMatteBorder(15, 0, 0, 0, new java.awt.Color(255, 153, 102)));

        txttotalsales.setFont(new java.awt.Font("Helvetica Neue", 1, 24)); // NOI18N
        txttotalsales.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        txttotalsales.setText("jLabel7");

        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel11.setText("All Period");

        javax.swing.GroupLayout pn1Layout = new javax.swing.GroupLayout(pn1);
        pn1.setLayout(pn1Layout);
        pn1Layout.setHorizontalGroup(
            pn1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pn1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pn1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txttotalsales, javax.swing.GroupLayout.DEFAULT_SIZE, 161, Short.MAX_VALUE)
                    .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        pn1Layout.setVerticalGroup(
            pn1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pn1Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addComponent(txttotalsales)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel11)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pn2.setBorder(javax.swing.BorderFactory.createMatteBorder(15, 0, 0, 0, new java.awt.Color(255, 153, 102)));

        txtrevenue.setFont(new java.awt.Font("Helvetica Neue", 1, 24)); // NOI18N
        txtrevenue.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        txtrevenue.setText("jLabel7");

        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setText("Last 30 Days");

        javax.swing.GroupLayout pn2Layout = new javax.swing.GroupLayout(pn2);
        pn2.setLayout(pn2Layout);
        pn2Layout.setHorizontalGroup(
            pn2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pn2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pn2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, 165, Short.MAX_VALUE)
                    .addComponent(txtrevenue, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 165, Short.MAX_VALUE))
                .addContainerGap())
        );
        pn2Layout.setVerticalGroup(
            pn2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pn2Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(txtrevenue)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel7)
                .addContainerGap(21, Short.MAX_VALUE))
        );

        pn3.setBorder(javax.swing.BorderFactory.createMatteBorder(15, 0, 0, 0, new java.awt.Color(255, 153, 102)));

        txtproduct.setFont(new java.awt.Font("Helvetica Neue", 1, 24)); // NOI18N
        txtproduct.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        txtproduct.setText("jLabel7");

        txtsold.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        txtsold.setText("Total Products");

        javax.swing.GroupLayout pn3Layout = new javax.swing.GroupLayout(pn3);
        pn3.setLayout(pn3Layout);
        pn3Layout.setHorizontalGroup(
            pn3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(txtproduct, javax.swing.GroupLayout.DEFAULT_SIZE, 173, Short.MAX_VALUE)
            .addGroup(pn3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txtsold, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        pn3Layout.setVerticalGroup(
            pn3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pn3Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(txtproduct)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtsold)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pn4.setBorder(javax.swing.BorderFactory.createMatteBorder(15, 0, 0, 0, new java.awt.Color(255, 153, 102)));

        txtstock.setFont(new java.awt.Font("Helvetica Neue", 1, 24)); // NOI18N
        txtstock.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        txtstock.setText("jLabel7");

        txtpname.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        txtpname.setText("txt");

        javax.swing.GroupLayout pn4Layout = new javax.swing.GroupLayout(pn4);
        pn4.setLayout(pn4Layout);
        pn4Layout.setHorizontalGroup(
            pn4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pn4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pn4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtpname, javax.swing.GroupLayout.DEFAULT_SIZE, 161, Short.MAX_VALUE)
                    .addComponent(txtstock, javax.swing.GroupLayout.DEFAULT_SIZE, 161, Short.MAX_VALUE))
                .addContainerGap())
        );
        pn4Layout.setVerticalGroup(
            pn4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pn4Layout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addComponent(txtstock)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtpname)
                .addContainerGap(24, Short.MAX_VALUE))
        );

        jLabel9.setFont(new java.awt.Font("Helvetica Neue", 1, 18)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(0, 51, 0));
        jLabel9.setText("X");
        jLabel9.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel9MouseClicked(evt);
            }
        });
        jLabel9.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jLabel9KeyReleased(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 69, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(pn1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 37, Short.MAX_VALUE)
                                .addComponent(pn2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(29, 29, 29))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel6)
                                .addGap(105, 105, 105)
                                .addComponent(jLabel4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(pn3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel14))
                        .addGap(31, 31, 31)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(pn4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel10))
                        .addGap(61, 61, 61))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(139, 139, 139)
                                .addComponent(barGraph, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel8)
                                .addGap(51, 51, 51)
                                .addComponent(pieChart, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(362, 362, 362)
                                .addComponent(jLabel12)))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel3)
                        .addGap(296, 296, 296)
                        .addComponent(jLabel9)
                        .addGap(16, 16, 16))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(23, 23, 23)
                        .addComponent(jLabel3))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel9)))
                .addGap(39, 39, 39)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel6)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel10, javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(jLabel14))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(pn2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(pn1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(pn3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(pn4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addComponent(jLabel12)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(91, 91, 91)
                        .addComponent(jLabel8))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(barGraph, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(29, 29, 29)
                        .addComponent(pieChart, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(50, 50, 50))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void txthomeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txthomeMouseClicked
        pnlhome.setBackground(new Color(204, 204, 204)); 
        txthome.setForeground(new Color(0, 51, 0));
        
        txtpd.setForeground(new Color(255, 255, 255)); 
        pnlpd.setBackground(new Color(0, 51, 0));
        txtsls.setForeground(new Color(255, 255, 255)); 
        pnlsls.setBackground(new Color(0, 51, 0));
        txtsp.setForeground(new Color(255, 255, 255)); 
        pnlsp.setBackground(new Color(0, 51, 0));
        txtsrs.setForeground(new Color(255, 255, 255)); 
        pnlsrs.setBackground(new Color(0, 51, 0));
        
        Connect();
        totalSale();
        revenue();
        displayLowStock();
        getTopProduct();   
        showPieChart();
        showBarChart();
        
        new adminSales().setVisible(false);
        new adminProduct().setVisible(false);
        new adminUser().setVisible(false);
        new adminSalesperson().setVisible(false);
        
    }//GEN-LAST:event_txthomeMouseClicked

    private void txtpdMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtpdMouseClicked
        pnlpd.setBackground(new Color(204, 204, 204)); 
        txtpd.setForeground(new Color(0, 51, 0));
        
        txthome.setForeground(new Color(255, 255, 255)); 
        pnlhome.setBackground(new Color(0, 51, 0));
        txtsls.setForeground(new Color(255, 255, 255)); 
        pnlsls.setBackground(new Color(0, 51, 0));
        txtsp.setForeground(new Color(255, 255, 255)); 
        pnlsp.setBackground(new Color(0, 51, 0));
        txtsrs.setForeground(new Color(255, 255, 255)); 
        pnlsrs.setBackground(new Color(0, 51, 0));
        
        new adminSales().setVisible(false);
        new adminProduct().setVisible(true);
        new adminUser().setVisible(false);
        new adminSalesperson().setVisible(false);
    }//GEN-LAST:event_txtpdMouseClicked

    private void txtslsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtslsMouseClicked
        pnlsls.setBackground(new Color(204, 204, 204)); 
        txtsls.setForeground(new Color(0, 51, 0));
        
        txthome.setForeground(new Color(255, 255, 255)); 
        pnlhome.setBackground(new Color(0, 51, 0));
        txtpd.setForeground(new Color(255, 255, 255)); 
        pnlpd.setBackground(new Color(0, 51, 0));
        txtsp.setForeground(new Color(255, 255, 255)); 
        pnlsp.setBackground(new Color(0, 51, 0));
        txtsrs.setForeground(new Color(255, 255, 255)); 
        pnlsrs.setBackground(new Color(0, 51, 0));
        
        new adminSales().setVisible(true);
        new adminProduct().setVisible(false);
        new adminUser().setVisible(false);
        new adminSalesperson().setVisible(false);
    }//GEN-LAST:event_txtslsMouseClicked

    private void txtspMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtspMouseClicked
        pnlsp.setBackground(new Color(204, 204, 204)); 
        txtsp.setForeground(new Color(0, 51, 0));
        
        txthome.setForeground(new Color(255, 255, 255)); 
        pnlhome.setBackground(new Color(0, 51, 0));
        txtpd.setForeground(new Color(255, 255, 255)); 
        pnlpd.setBackground(new Color(0, 51, 0));
        txtsls.setForeground(new Color(255, 255, 255)); 
        pnlsls.setBackground(new Color(0, 51, 0));
        txtsrs.setForeground(new Color(255, 255, 255)); 
        pnlsrs.setBackground(new Color(0, 51, 0));
        
        new adminSales().setVisible(false);
        new adminProduct().setVisible(false);
        new adminUser().setVisible(false);
        
        new adminSalesperson().setVisible(true);
    }//GEN-LAST:event_txtspMouseClicked

    private void txtsrsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtsrsMouseClicked
        pnlsrs.setBackground(new Color(204, 204, 204)); 
        txtsrs.setForeground(new Color(0, 51, 0));
        
        txthome.setForeground(new Color(255, 255, 255)); 
        pnlhome.setBackground(new Color(0, 51, 0));
        txtpd.setForeground(new Color(255, 255, 255)); 
        pnlpd.setBackground(new Color(0, 51, 0));
        txtsls.setForeground(new Color(255, 255, 255)); 
        pnlsls.setBackground(new Color(0, 51, 0));
        txtsp.setForeground(new Color(255, 255, 255)); 
        pnlsp.setBackground(new Color(0, 51, 0));
        
        new adminSales().setVisible(false);
        new adminProduct().setVisible(false);
        new adminUser().setVisible(true);
        new adminSalesperson().setVisible(false);
    }//GEN-LAST:event_txtsrsMouseClicked

    private void jLabel17MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel17MouseClicked
   
        for (java.awt.Window window : java.awt.Window.getWindows()) {
            window.dispose();
        }
        new Login().setVisible(true);       
    }//GEN-LAST:event_jLabel17MouseClicked

    private void jLabel9MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel9MouseClicked

        for (java.awt.Window window : java.awt.Window.getWindows()) {
            window.dispose();
        }
    }//GEN-LAST:event_jLabel9MouseClicked

    private void jLabel9KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jLabel9KeyReleased
       
    }//GEN-LAST:event_jLabel9KeyReleased

  
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
            java.util.logging.Logger.getLogger(Home.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            new Home().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel barGraph;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel17;
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
    private javax.swing.JTextField jTextField4;
    private javax.swing.JPanel pieChart;
    private javax.swing.JPanel pn1;
    private javax.swing.JPanel pn2;
    private javax.swing.JPanel pn3;
    private javax.swing.JPanel pn4;
    private javax.swing.JPanel pnlhome;
    private javax.swing.JPanel pnlpd;
    private javax.swing.JPanel pnlsls;
    private javax.swing.JPanel pnlsp;
    private javax.swing.JPanel pnlsrs;
    private javax.swing.JLabel txthome;
    private javax.swing.JLabel txtpd;
    private javax.swing.JLabel txtpname;
    private javax.swing.JLabel txtproduct;
    private javax.swing.JLabel txtrevenue;
    private javax.swing.JLabel txtsls;
    private javax.swing.JLabel txtsold;
    private javax.swing.JLabel txtsp;
    private javax.swing.JLabel txtsrs;
    private javax.swing.JLabel txtstock;
    private javax.swing.JLabel txttotalsales;
    // End of variables declaration//GEN-END:variables
}
