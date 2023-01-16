package Admin;

import class_attendance.Welcomepage;
import extrasource.staffData;
import java.awt.HeadlessException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

public class manage_staff extends javax.swing.JFrame {

    int  id;
    
    Connection con=null;
    PreparedStatement ps=null;
    ResultSet rs = null;
    Statement smt = null;
    DefaultTableModel model;
    String emailpattern = "^[a-zA-Z0-9]+[@]+[a-zA-Z0-9]+[.]+[a-zA-Z0-9]+$";
    
    String username = "root";
    String password = "shellbt31_ofis";    
    String serverurl = "jdbc:mysql://localhost:3306/facerecognitionattendance";
    String timezone = "?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
    
    public manage_staff() {
        initComponents();        
        setStaffTableData();
        FileCombo();
    }
    
    private void filter(String query){
        TableRowSorter<DefaultTableModel> tr = new TableRowSorter<>(model);
        staffData_table.setRowSorter(tr);
        tr.setRowFilter(RowFilter.regexFilter(query));
    }
    
    private void FileCombo()
    {
        try{
            String sql = "select * from useraccount";
            con = DriverManager.getConnection(serverurl+timezone,username,password);
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            
            staffstatus_drop.addItem("");
            while(rs.next())
            {
                //nolocalacc_drop.addItem("");
                staffstatus_drop.addItem(rs.getString("Status"));
            }
            
        }catch(SQLException e){
             JOptionPane.showMessageDialog(null,e);       
        }
    }
    
    private void resetData() {
        id_txt.setText("");
        firstname_txt.setText("");
        lastname_txt.setText("");
        status_combo.setSelectedItem("---");        
        email_txt.setText("");
        phone_txt.setText("");
        username_txt.setText("");
        pwd_txt.setText("");
        securityques_combo.setSelectedItem("---"); 
        securityans_txt.setText("");
    }
    
    public ArrayList<staffData> staffList(){
        ArrayList<staffData> staffList = new ArrayList<> ();         
        try{
            String sql = "select * from useraccount";
            con = DriverManager.getConnection(serverurl+timezone,username,password);
            smt = con.createStatement();
            rs = smt.executeQuery(sql);                
                       
            while(rs.next()){
                staffData staffData= new staffData();
                staffData.setUserID(rs.getString("UserID"));
                staffData.setFirstName(rs.getString("FirstName"));
                staffData.setLastName(rs.getString("LastName"));
                staffData.setStatus(rs.getString("Status"));
                staffData.setEmail(rs.getString("Email"));
                staffData.setMobilePhone(rs.getString("MobilePhone"));
                staffData.setUsername(rs.getString("Username"));
                staffData.setPassword(rs.getString("Password"));
                staffData.setSecurityQuestion(rs.getString("SecurityQuestion"));
                staffData.setSecurityAnswer(rs.getString("SecurityAnswer"));
                staffList.add(staffData);
            }
        }catch(SQLException ex){
            JOptionPane.showMessageDialog(null, ex);
        }
        return staffList;        
    }
    
    public void setStaffTableData(){
        ArrayList<staffData> dataArray = staffList();
        model = (DefaultTableModel) staffData_table.getModel();
        model.setRowCount(0);
        Object[] rows = new Object[10];
        
        for(int i=0; i<dataArray.size(); i++)
        {
            rows[0] = dataArray.get(i).getUserID();
            rows[1] = dataArray.get(i).getFirstName();
            rows[2] = dataArray.get(i).getLastName();
            rows[3] = dataArray.get(i).getStatus();
            rows[4] = dataArray.get(i).getEmail();
            rows[5] = dataArray.get(i).getMobilePhone();
            rows[6] = dataArray.get(i).getUsername();
            rows[7] = dataArray.get(i).getPassword();
            rows[8] = dataArray.get(i).getSecurityQuestion();
            rows[9] = dataArray.get(i).getSecurityAnswer();
            model.addRow(rows);
        }
    }

     public boolean checkusername(String Name)
    {
        boolean checkUser = false;
        
        try 
        {
            String query = "SELECT * FROM `useraccount` WHERE `Username` ='" + username_txt.getText()+ "'";            
            con = DriverManager.getConnection(serverurl+timezone,username,password);
            ps = con.prepareStatement(query);                    
            rs = ps.executeQuery();
            
            if(rs.next())
            {
                checkUser = true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(this.getName()).log(Level.SEVERE, null, ex);
        }
         return checkUser;
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        staffData_table = new javax.swing.JTable();
        searchnolocal1 = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        staffstatus_drop = new javax.swing.JComboBox<>();
        jPanel2 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        firstname_txt = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        email_txt = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        add_btn = new javax.swing.JButton();
        update_btn = new javax.swing.JButton();
        reset_btn = new javax.swing.JButton();
        delete_btn = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        id_txt = new javax.swing.JTextField();
        securityques_combo = new javax.swing.JComboBox<>();
        jLabel16 = new javax.swing.JLabel();
        lastname_txt = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        phone_txt = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        username_txt = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        securityans_txt = new javax.swing.JTextField();
        jLabel19 = new javax.swing.JLabel();
        status_combo = new javax.swing.JComboBox<>();
        jLabel20 = new javax.swing.JLabel();
        pwd_txt = new javax.swing.JTextField();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        jMenuItem3 = new javax.swing.JMenuItem();
        jMenuItem4 = new javax.swing.JMenuItem();
        jMenuItem5 = new javax.swing.JMenuItem();
        jMenu4 = new javax.swing.JMenu();
        jMenuItem6 = new javax.swing.JMenuItem();
        jMenu10 = new javax.swing.JMenu();
        jMenuItem7 = new javax.swing.JMenuItem();
        jMenu6 = new javax.swing.JMenu();
        jMenuItem8 = new javax.swing.JMenuItem();
        jMenuItem9 = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(204, 204, 255));

        jLabel1.setFont(new java.awt.Font("Consolas", 1, 24)); // NOI18N
        jLabel1.setText("Manage Staff");

        jTabbedPane1.setPreferredSize(new java.awt.Dimension(1510, 710));

        jScrollPane1.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        jScrollPane1.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        staffData_table.setFont(new java.awt.Font("Consolas", 0, 14)); // NOI18N
        staffData_table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Id", "Fisrt Name", "Last Nickname", "Status", "Email", "Mobile Phone", "Username", "Password", "Security Question", "Security Answer"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        staffData_table.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_ALL_COLUMNS);
        staffData_table.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                staffData_tableMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(staffData_table);

        searchnolocal1.setFont(new java.awt.Font("Consolas", 0, 18)); // NOI18N
        searchnolocal1.setText("Search");
        searchnolocal1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchnolocal1ActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Consolas", 0, 18)); // NOI18N
        jLabel2.setText("Search :");

        staffstatus_drop.setFont(new java.awt.Font("Consolas", 0, 18)); // NOI18N
        staffstatus_drop.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                staffstatus_dropActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(staffstatus_drop, javax.swing.GroupLayout.PREFERRED_SIZE, 480, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addComponent(searchnolocal1, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1170, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(staffstatus_drop, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(searchnolocal1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 370, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jTabbedPane1.addTab("View Data", jPanel4);

        jLabel3.setFont(new java.awt.Font("Consolas", 0, 24)); // NOI18N
        jLabel3.setText("First Name  :");

        firstname_txt.setFont(new java.awt.Font("Consolas", 0, 18)); // NOI18N
        firstname_txt.setPreferredSize(new java.awt.Dimension(71, 35));

        jLabel12.setFont(new java.awt.Font("Consolas", 0, 24)); // NOI18N
        jLabel12.setText("Last Name   :");

        email_txt.setFont(new java.awt.Font("Consolas", 0, 18)); // NOI18N
        email_txt.setPreferredSize(new java.awt.Dimension(71, 35));

        jLabel13.setFont(new java.awt.Font("Consolas", 0, 24)); // NOI18N
        jLabel13.setText("Email       :");

        add_btn.setFont(new java.awt.Font("Consolas", 0, 18)); // NOI18N
        add_btn.setText("Add Data");
        add_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                add_btnActionPerformed(evt);
            }
        });

        update_btn.setFont(new java.awt.Font("Consolas", 0, 18)); // NOI18N
        update_btn.setText("Update Data");
        update_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                update_btnActionPerformed(evt);
            }
        });

        reset_btn.setFont(new java.awt.Font("Consolas", 0, 18)); // NOI18N
        reset_btn.setText("Reset Data");
        reset_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                reset_btnActionPerformed(evt);
            }
        });

        delete_btn.setFont(new java.awt.Font("Consolas", 0, 18)); // NOI18N
        delete_btn.setText("Delete Data");
        delete_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                delete_btnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(add_btn, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(delete_btn, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(reset_btn, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(update_btn, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(14, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(add_btn, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(33, 33, 33)
                .addComponent(delete_btn, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(23, 23, 23)
                .addComponent(reset_btn, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addComponent(update_btn, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(27, Short.MAX_VALUE))
        );

        jLabel4.setFont(new java.awt.Font("Consolas", 0, 24)); // NOI18N
        jLabel4.setText("Id          :");

        id_txt.setEditable(false);
        id_txt.setFont(new java.awt.Font("Consolas", 0, 18)); // NOI18N
        id_txt.setPreferredSize(new java.awt.Dimension(71, 35));

        securityques_combo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "---", "When your birthday date?", "What your first name pet?", "Where you favourite place to travel?" }));

        jLabel16.setFont(new java.awt.Font("Consolas", 0, 24)); // NOI18N
        jLabel16.setText("Status      :");

        lastname_txt.setFont(new java.awt.Font("Consolas", 0, 18)); // NOI18N
        lastname_txt.setPreferredSize(new java.awt.Dimension(71, 35));

        jLabel17.setFont(new java.awt.Font("Consolas", 0, 24)); // NOI18N
        jLabel17.setText("Mobile Phone:");

        phone_txt.setFont(new java.awt.Font("Consolas", 0, 18)); // NOI18N
        phone_txt.setPreferredSize(new java.awt.Dimension(71, 35));

        jLabel5.setFont(new java.awt.Font("Consolas", 0, 24)); // NOI18N
        jLabel5.setText("Username    :");

        username_txt.setFont(new java.awt.Font("Consolas", 0, 18)); // NOI18N
        username_txt.setPreferredSize(new java.awt.Dimension(71, 35));

        jLabel18.setFont(new java.awt.Font("Consolas", 0, 24)); // NOI18N
        jLabel18.setText("Security Answer:");

        securityans_txt.setFont(new java.awt.Font("Consolas", 0, 18)); // NOI18N
        securityans_txt.setPreferredSize(new java.awt.Dimension(71, 35));

        jLabel19.setFont(new java.awt.Font("Consolas", 0, 24)); // NOI18N
        jLabel19.setText("Password    :");

        status_combo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "---", "Staff", "Teacher", "Admin" }));

        jLabel20.setFont(new java.awt.Font("Consolas", 0, 24)); // NOI18N
        jLabel20.setText("Security Question  :");

        pwd_txt.setFont(new java.awt.Font("Consolas", 0, 18)); // NOI18N
        pwd_txt.setPreferredSize(new java.awt.Dimension(71, 35));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(id_txt, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(firstname_txt, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lastname_txt, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(status_combo, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(email_txt, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(phone_txt, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(username_txt, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(pwd_txt, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 270, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(securityques_combo, javax.swing.GroupLayout.PREFERRED_SIZE, 450, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(securityans_txt, javax.swing.GroupLayout.PREFERRED_SIZE, 450, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(30, 30, 30)
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(20, 20, 20)
                        .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(20, 20, 20)
                        .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(30, 30, 30)
                        .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(30, 30, 30)
                        .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(id_txt, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(30, 30, 30)
                        .addComponent(firstname_txt, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(20, 20, 20)
                        .addComponent(lastname_txt, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(20, 20, 20)
                        .addComponent(status_combo, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(30, 30, 30)
                        .addComponent(email_txt, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(30, 30, 30)
                        .addComponent(phone_txt, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(username_txt, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(20, 20, 20)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(pwd_txt, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(30, 30, 30)
                        .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(securityques_combo, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(20, 20, 20)
                        .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(securityans_txt, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(53, 53, 53))
        );

        jTabbedPane1.addTab("Edit", jPanel2);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(29, 29, 29)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 359, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1215, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(48, 48, 48))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5)
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 523, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jMenu1.setText("File");

        jMenuItem1.setText("Dashboard");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem1);

        jMenuItem2.setText("Log Out");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem2);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("Student");

        jMenuItem3.setText("Register New Student");
        jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem3ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem3);

        jMenuItem4.setText("Manage Student");
        jMenuItem4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem4ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem4);

        jMenuItem5.setText("Quick Test");
        jMenuItem5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem5ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem5);

        jMenuBar1.add(jMenu2);

        jMenu4.setText("Staff");

        jMenuItem6.setText("Manage Staff");
        jMenuItem6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem6ActionPerformed(evt);
            }
        });
        jMenu4.add(jMenuItem6);

        jMenuBar1.add(jMenu4);

        jMenu10.setText("Classroom");

        jMenuItem7.setText("Manage Classroom");
        jMenuItem7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem7ActionPerformed(evt);
            }
        });
        jMenu10.add(jMenuItem7);

        jMenuBar1.add(jMenu10);

        jMenu6.setText("Report");

        jMenuItem8.setText("Attendance Report");
        jMenuItem8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem8ActionPerformed(evt);
            }
        });
        jMenu6.add(jMenuItem8);

        jMenuItem9.setText("History Message");
        jMenuItem9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem9ActionPerformed(evt);
            }
        });
        jMenu6.add(jMenuItem9);

        jMenuBar1.add(jMenu6);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        admin_portal ap = new admin_portal();
        ap.setVisible(true);
        ap.pack();
        ap.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.dispose();
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        Welcomepage mp = new Welcomepage();
        mp.setVisible(true);
        mp.pack();
        mp.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.dispose();
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem3ActionPerformed
        register_student rs = new register_student();
        rs.setVisible(true);
        rs.pack();
        rs.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.dispose();
    }//GEN-LAST:event_jMenuItem3ActionPerformed

    private void jMenuItem4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem4ActionPerformed
        manage_student ms = new manage_student();
        ms.setVisible(true);
        ms.pack();
        ms.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.dispose();
    }//GEN-LAST:event_jMenuItem4ActionPerformed

    private void jMenuItem5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem5ActionPerformed
        quick_test qt = new quick_test();
        qt.setVisible(true);
        qt.pack();
        qt.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.dispose();
    }//GEN-LAST:event_jMenuItem5ActionPerformed

    private void jMenuItem6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem6ActionPerformed
        
    }//GEN-LAST:event_jMenuItem6ActionPerformed

    private void jMenuItem7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem7ActionPerformed
        manage_classroom mc = new manage_classroom();
        mc.setVisible(true);
        mc.pack();
        mc.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.dispose();
    }//GEN-LAST:event_jMenuItem7ActionPerformed

    private void jMenuItem8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem8ActionPerformed
        attendance_report ar = new attendance_report();
        ar.setVisible(true);
        ar.pack();
        ar.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.dispose();
    }//GEN-LAST:event_jMenuItem8ActionPerformed

    private void jMenuItem9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem9ActionPerformed
        history_message hm = new history_message();
        hm.setVisible(true);
        hm.pack();
        hm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.dispose();
    }//GEN-LAST:event_jMenuItem9ActionPerformed

    private void staffData_tableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_staffData_tableMouseClicked
        boolean a = staffData_table.isEditing();
        if(a==false){
            JOptionPane.showMessageDialog(this, "Proceed to Edit page");
        }

        try
        {
            id =Integer.parseInt(staffData_table.getValueAt(staffData_table.getSelectedRow(), 0).toString());
            smt = con.createStatement();
            rs = smt.executeQuery("select * from useraccount where UserID=" + id);

            if (rs.next()) {
                id_txt.setText(rs.getString(1));
                firstname_txt.setText(rs.getString(2));
                lastname_txt.setText(rs.getString(3));
                status_combo.setSelectedItem(rs.getString(4));
                email_txt.setText(rs.getString(5));
                phone_txt.setText(rs.getString(6));
                username_txt.setText(rs.getString(7));
                pwd_txt.setText(rs.getString(8));
                securityques_combo.setSelectedItem(rs.getString(9));
                securityans_txt.setText(rs.getString(10));
            }

            rs.close();
            smt.close();

        } catch( SQLException ex)
        {
            JOptionPane.showMessageDialog(this,"Cannot Procced to Edit Page");
        }
    }//GEN-LAST:event_staffData_tableMouseClicked

    private void searchnolocal1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchnolocal1ActionPerformed
        String query = staffstatus_drop.getSelectedItem().toString();
        filter(query);
    }//GEN-LAST:event_searchnolocal1ActionPerformed

    private void staffstatus_dropActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_staffstatus_dropActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_staffstatus_dropActionPerformed

    private void add_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_add_btnActionPerformed
        String fname = firstname_txt.getText();
        String lname = lastname_txt.getText();
        String status = status_combo.getSelectedItem().toString();
        String email = email_txt.getText();
        String mobilephone = phone_txt.getText();
        String username1 = username_txt.getText();
        String  password1 = pwd_txt.getText();
        String securityquestion = securityques_combo.getSelectedItem().toString();
        String securityanswer = securityans_txt.getText();

        if(fname.equals(""))
        {
            JOptionPane.showMessageDialog(null, "Add First Name");
        }
        else if(lname.equals(""))
        {
            JOptionPane.showMessageDialog(null, "Add an Last Name");
        }
        else if(status.equals("---"))
        {
            JOptionPane.showMessageDialog(null, "Please choose your status");
        }
        else if(email.equals(""))
        {
            JOptionPane.showMessageDialog(null, "Add a Email Address");
        }
        else if (!Pattern.matches(emailpattern, email_txt.getText()))
        {
            JOptionPane.showMessageDialog(null, "Please enter a valid email", "Error", JOptionPane.ERROR_MESSAGE);
        }
        else if(mobilephone.equals(""))
        {
            JOptionPane.showMessageDialog(null, "Add a Mobile Phone");
        }
        else if(mobilephone.length()<11)
        {
            JOptionPane.showMessageDialog(null, "Your Mobile Phone must be '012-3456789'");
        }
        else if(username1.equals(""))
        {
            JOptionPane.showMessageDialog(null, "Add a Username");
        }
        else if (checkusername(username1))
        {
            JOptionPane.showMessageDialog(null, "This username Already Exist");
        }
        else if(password1.equals(""))
        {
            JOptionPane.showMessageDialog(null, "Add a Password");
        }
        else if(password1.length()<3)
        {
            JOptionPane.showMessageDialog(null, "Your Password is Too Small");
        }
        else if(securityquestion.equals("---"))
        {
            JOptionPane.showMessageDialog(null, "Please choose your security question");
        }
        else if(securityanswer.equals(""))
        {
            JOptionPane.showMessageDialog(null, "Add your security answer");
        }
        else
        {
            try
            {
                String query ="Insert into useraccount (FirstName,LastName,Status,Email,MobilePhone,Username,Password,SecurityQuestion,SecurityAnswer) values (?,?,?,?,?,?,?,?,?)";
                con = DriverManager.getConnection(serverurl+timezone,username,password);
                ps = con.prepareStatement(query);
                ps.setString(1, fname);
                ps.setString(2, lname);
                ps.setString(3, status);
                ps.setString(4, email);
                ps.setString(5, mobilephone);
                ps.setString(6, username1);
                ps.setString(7, password1);
                ps.setString(8, securityquestion);
                ps.setString(9, securityanswer);
                ps.executeUpdate();

                JOptionPane.showMessageDialog(null, "New User Add Successfully");
                setStaffTableData();
                resetData();
            }
            catch (HeadlessException | SQLException ex)
            {
                JOptionPane.showMessageDialog(null, ex);
            }
        }
    }//GEN-LAST:event_add_btnActionPerformed

    private void update_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_update_btnActionPerformed
        String fname = firstname_txt.getText();
        String lname = lastname_txt.getText();
        String status = status_combo.getSelectedItem().toString();
        String email = email_txt.getText();
        String mobilephone = phone_txt.getText();
        String username1 = username_txt.getText();
        String  password1 = pwd_txt.getText();
        String securityquestion = securityques_combo.getSelectedItem().toString();
        String securityanswer = securityans_txt.getText();

        try
        {
            String sql="update useraccount set FirstName=?,LastName=?,Status=?,Email=?,MobilePhone=?,Username=?,Password=?,SecurityQuestion=?,SecurityAnswer=? where UserID=?";
            con = DriverManager.getConnection(serverurl+timezone,username,password);
            ps = con.prepareStatement(sql);

            ps.setString(10, id_txt.getText());
            ps.setString(1, fname);
            ps.setString(2, lname);
            ps.setString(3, status);
            ps.setString(4, email);
            ps.setString(5, mobilephone);
            ps.setString(6, username1);
            ps.setString(7, password1);
            ps.setString(8, securityquestion);
            ps.setString(9, securityanswer);

            if (ps.executeUpdate() >0)
            {
                JOptionPane.showMessageDialog(null, "Update Data Successfully");
                setStaffTableData();
                resetData();
            }
        } catch(SQLException ex)
        {
            JOptionPane.showMessageDialog(this,"Cannot Update Data");
        }
        
    }//GEN-LAST:event_update_btnActionPerformed

    private void reset_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_reset_btnActionPerformed
        resetData();
    }//GEN-LAST:event_reset_btnActionPerformed

    private void delete_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_delete_btnActionPerformed
        if (id != 0)
        {
            try
            {
                String sql = "delete from student where studentID="+id;

                con = DriverManager.getConnection(serverurl+timezone ,username, password);
                smt = con.createStatement();
                smt.execute(sql);

                JOptionPane.showMessageDialog(null, "Delete Data Successfully");
                setStaffTableData();
                resetData();
                id=0;
            }
            catch( SQLException ex)
            {
                JOptionPane.showMessageDialog(this,"Cannot Delete Data");
            }
        }
    }//GEN-LAST:event_delete_btnActionPerformed

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
            java.util.logging.Logger.getLogger(manage_staff.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(manage_staff.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(manage_staff.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(manage_staff.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new manage_staff().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton add_btn;
    private javax.swing.JButton delete_btn;
    private javax.swing.JTextField email_txt;
    private javax.swing.JTextField firstname_txt;
    private javax.swing.JTextField id_txt;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu10;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu4;
    private javax.swing.JMenu jMenu6;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JMenuItem jMenuItem5;
    private javax.swing.JMenuItem jMenuItem6;
    private javax.swing.JMenuItem jMenuItem7;
    private javax.swing.JMenuItem jMenuItem8;
    private javax.swing.JMenuItem jMenuItem9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTextField lastname_txt;
    private javax.swing.JTextField phone_txt;
    private javax.swing.JTextField pwd_txt;
    private javax.swing.JButton reset_btn;
    private javax.swing.JButton searchnolocal1;
    private javax.swing.JTextField securityans_txt;
    private javax.swing.JComboBox<String> securityques_combo;
    private javax.swing.JTable staffData_table;
    private javax.swing.JComboBox<String> staffstatus_drop;
    private javax.swing.JComboBox<String> status_combo;
    private javax.swing.JButton update_btn;
    private javax.swing.JTextField username_txt;
    // End of variables declaration//GEN-END:variables
}
