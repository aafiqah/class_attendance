package Admin;

import class_attendance.Welcomepage;
import extrasource.classData;
import extrasource.courseData;
import extrasource.sessionData;
import extrasource.subjectData;
import java.awt.HeadlessException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

public class manage_classroom extends javax.swing.JFrame {

    int  id1,id2,id3,id4;
    
    Connection con=null;
    PreparedStatement ps=null;
    ResultSet rs = null;
    Statement smt = null;
    DefaultTableModel model;
    
    String username = "root";
    String password = "shellbt31_ofis";    
    String serverurl = "jdbc:mysql://localhost:3306/facerecognitionattendance";
    String timezone = "?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
    
    public manage_classroom() {
        initComponents();
        setClassTableData();
        setCourseTableData();
        setSessionTableData();
        setSubjectTableData();
        FileCombo1();
        FileCombo2();
        FileCombo3();
        FileCombo4();
    }
    
    private void filter1(String query){
        TableRowSorter<DefaultTableModel> tr1 = new TableRowSorter<>(model);
        classData_table.setRowSorter(tr1);
        tr1.setRowFilter(RowFilter.regexFilter(query));
    }
    private void filter2(String query){
        TableRowSorter<DefaultTableModel> tr2 = new TableRowSorter<>(model);
        courseData_table.setRowSorter(tr2);
        tr2.setRowFilter(RowFilter.regexFilter(query));
    }
    private void filter3(String query){
        TableRowSorter<DefaultTableModel> tr3 = new TableRowSorter<>(model);
        sessionData_table.setRowSorter(tr3);
        tr3.setRowFilter(RowFilter.regexFilter(query));
    }
    private void filter4(String query){
        TableRowSorter<DefaultTableModel> tr4 = new TableRowSorter<>(model);
        subjectData_table.setRowSorter(tr4);
        tr4.setRowFilter(RowFilter.regexFilter(query));
    }
    
    private void FileCombo1()
    {
        try{
            String sql = "select * from classroom";
            con = DriverManager.getConnection(serverurl+timezone,username,password);
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            
            classname_drop.addItem("");
            while(rs.next())
            {
                //nolocalacc_drop.addItem("");
                classname_drop.addItem(rs.getString("classname"));
            }
            
        }catch(SQLException e){
             JOptionPane.showMessageDialog(null,e);       
        }
    }
    
    private void FileCombo2()
    {
        try{
            String sql = "select * from course";
            con = DriverManager.getConnection(serverurl+timezone,username,password);
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            
            course_drop.addItem("");
            while(rs.next())
            {
                //nolocalacc_drop.addItem("");
                course_drop.addItem(rs.getString("coursename"));
            }
            
        }catch(SQLException e){
             JOptionPane.showMessageDialog(null,e);       
        }
    }
    
    private void FileCombo3()
    {
        try{
            String sql = "select * from session";
            con = DriverManager.getConnection(serverurl+timezone,username,password);
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            
            sessionname_drop.addItem("");
            while(rs.next())
            {
                //nolocalacc_drop.addItem("");
                sessionname_drop.addItem(rs.getString("sessionname"));
            }
            
        }catch(SQLException e){
             JOptionPane.showMessageDialog(null,e);       
        }
    }
    
    private void FileCombo4()
    {
        try{
            String sql = "select * from subject";
            con = DriverManager.getConnection(serverurl+timezone,username,password);
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            
            subjectname_drop.addItem("");
            while(rs.next())
            {
                //nolocalacc_drop.addItem("");
                subjectname_drop.addItem(rs.getString("subjectname"));
            }
            
        }catch(SQLException e){
             JOptionPane.showMessageDialog(null,e);       
        }
    }
    
    public ArrayList<classData> classList(){
        ArrayList<classData> classList = new ArrayList<> ();         
        try{
            String sql = "select * from classroom";
            con = DriverManager.getConnection(serverurl+timezone,username,password);
            smt = con.createStatement();
            rs = smt.executeQuery(sql);                
                       
            while(rs.next()){
                classData classData= new classData();
                classData.setClassID(rs.getString("classID"));
                classData.setClassName(rs.getString("classname"));
                classList.add(classData);
            }
        }catch(SQLException ex){
            JOptionPane.showMessageDialog(null, ex);
        }
        return classList;        
    }
    
    public ArrayList<courseData> courseList(){
        ArrayList<courseData> courseList = new ArrayList<> ();         
        try{
            String sql = "select * from course";
            con = DriverManager.getConnection(serverurl+timezone,username,password);
            smt = con.createStatement();
            rs = smt.executeQuery(sql);                
                       
            while(rs.next()){
                courseData courseData= new courseData();
                courseData.setCourseID(rs.getString("courseID"));
                courseData.setCourseName(rs.getString("coursename"));
                courseList.add(courseData);
            }
        }catch(SQLException ex){
            JOptionPane.showMessageDialog(null, ex);
        }
        return courseList;        
    }
    
    public ArrayList<subjectData> subjectList(){
        ArrayList<subjectData> subjectList = new ArrayList<> ();         
        try{
            String sql = "select * from subject";
            con = DriverManager.getConnection(serverurl+timezone,username,password);
            smt = con.createStatement();
            rs = smt.executeQuery(sql);                
                       
            while(rs.next()){
                subjectData subjectData= new subjectData();
                subjectData.setSubjectID(rs.getString("subjectID"));
                subjectData.setSubjectName(rs.getString("subjectname"));
                subjectList.add(subjectData);
            }
        }catch(SQLException ex){
            JOptionPane.showMessageDialog(null, ex);
        }
        return subjectList;        
    }
    
    public ArrayList<sessionData> sessionList(){
        ArrayList<sessionData> sessionList = new ArrayList<> ();         
        try{
            String sql = "select * from session";
            con = DriverManager.getConnection(serverurl+timezone,username,password);
            smt = con.createStatement();
            rs = smt.executeQuery(sql);                
                       
            while(rs.next()){
                sessionData sessionData= new sessionData();
                sessionData.setSessionID(rs.getString("sessionID"));
                sessionData.setSessionName(rs.getString("sessionname"));
                sessionList.add(sessionData);
            }
        }catch(SQLException ex){
            JOptionPane.showMessageDialog(null, ex);
        }
        return sessionList;        
    }
    
    public void setClassTableData(){
        ArrayList<classData> dataArray = classList();
        model = (DefaultTableModel) classData_table.getModel();
        model.setRowCount(0);
        Object[] rows = new Object[2];
        
        for(int i=0; i<dataArray.size(); i++)
        {
            rows[0] = dataArray.get(i).getClassID();
            rows[1] = dataArray.get(i).getClassName();
            model.addRow(rows);
        }
    }
    
    public void setCourseTableData(){
        ArrayList<courseData> dataArray = courseList();
        model = (DefaultTableModel) courseData_table.getModel();
        model.setRowCount(0);
        Object[] rows = new Object[2];
        
        for(int i=0; i<dataArray.size(); i++)
        {
            rows[0] = dataArray.get(i).getCourseID();
            rows[1] = dataArray.get(i).getCourseName();
            model.addRow(rows);
        }
    }
    
    public void setSessionTableData(){
        ArrayList<sessionData> dataArray = sessionList();
        model = (DefaultTableModel) sessionData_table.getModel();
        model.setRowCount(0);
        Object[] rows = new Object[2];
        
        for(int i=0; i<dataArray.size(); i++)
        {
            rows[0] = dataArray.get(i).getSessionID();
            rows[1] = dataArray.get(i).getSessionName();
            model.addRow(rows);
        }
    }
    
    public void setSubjectTableData(){
        ArrayList<subjectData> dataArray = subjectList();
        model = (DefaultTableModel) subjectData_table.getModel();
        model.setRowCount(0);
        Object[] rows = new Object[2];
        
        for(int i=0; i<dataArray.size(); i++)
        {
            rows[0] = dataArray.get(i).getSubjectID();
            rows[1] = dataArray.get(i).getSubjectName();
            model.addRow(rows);
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

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        classData_table = new javax.swing.JTable();
        search = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        classname_drop = new javax.swing.JComboBox<>();
        jLabel6 = new javax.swing.JLabel();
        classname_txt = new javax.swing.JTextField();
        jPanel7 = new javax.swing.JPanel();
        add_btn1 = new javax.swing.JButton();
        update_btn1 = new javax.swing.JButton();
        delete_btn1 = new javax.swing.JButton();
        jLabel12 = new javax.swing.JLabel();
        id_txt1 = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        course_drop = new javax.swing.JComboBox<>();
        jPanel8 = new javax.swing.JPanel();
        add_btn2 = new javax.swing.JButton();
        update_btn2 = new javax.swing.JButton();
        delete_btn2 = new javax.swing.JButton();
        coursename_txt = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        courseData_table = new javax.swing.JTable();
        search1 = new javax.swing.JButton();
        jLabel13 = new javax.swing.JLabel();
        id_txt2 = new javax.swing.JTextField();
        jPanel5 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        sessionname_drop = new javax.swing.JComboBox<>();
        jPanel9 = new javax.swing.JPanel();
        add_btn3 = new javax.swing.JButton();
        update_btn3 = new javax.swing.JButton();
        delete_btn3 = new javax.swing.JButton();
        session_txt = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        sessionData_table = new javax.swing.JTable();
        search2 = new javax.swing.JButton();
        jLabel15 = new javax.swing.JLabel();
        id_txt3 = new javax.swing.JTextField();
        jPanel6 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        subjectname_drop = new javax.swing.JComboBox<>();
        jPanel10 = new javax.swing.JPanel();
        add_btn4 = new javax.swing.JButton();
        update_btn4 = new javax.swing.JButton();
        delete_btn4 = new javax.swing.JButton();
        subjectname_txt = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        search3 = new javax.swing.JButton();
        jScrollPane5 = new javax.swing.JScrollPane();
        subjectData_table = new javax.swing.JTable();
        jLabel16 = new javax.swing.JLabel();
        id_txt4 = new javax.swing.JTextField();
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
        jLabel1.setText("Manage Classroom");

        jTabbedPane1.setPreferredSize(new java.awt.Dimension(1510, 710));

        jScrollPane1.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        jScrollPane1.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        classData_table.setFont(new java.awt.Font("Consolas", 0, 14)); // NOI18N
        classData_table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Id", "Class Name"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        classData_table.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_ALL_COLUMNS);
        classData_table.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                classData_tableMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(classData_table);

        search.setFont(new java.awt.Font("Consolas", 0, 18)); // NOI18N
        search.setText("Search");
        search.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Consolas", 0, 18)); // NOI18N
        jLabel2.setText("Search :");

        classname_drop.setFont(new java.awt.Font("Consolas", 0, 18)); // NOI18N
        classname_drop.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                classname_dropActionPerformed(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Consolas", 0, 24)); // NOI18N
        jLabel6.setText("Class Name  :");

        classname_txt.setFont(new java.awt.Font("Consolas", 0, 18)); // NOI18N
        classname_txt.setPreferredSize(new java.awt.Dimension(71, 35));

        add_btn1.setFont(new java.awt.Font("Consolas", 0, 18)); // NOI18N
        add_btn1.setText("Add Data");
        add_btn1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                add_btn1ActionPerformed(evt);
            }
        });

        update_btn1.setFont(new java.awt.Font("Consolas", 0, 18)); // NOI18N
        update_btn1.setText("Update Data");
        update_btn1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                update_btn1ActionPerformed(evt);
            }
        });

        delete_btn1.setFont(new java.awt.Font("Consolas", 0, 18)); // NOI18N
        delete_btn1.setText("Delete Data");
        delete_btn1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                delete_btn1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGap(31, 31, 31)
                        .addComponent(delete_btn1, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(update_btn1, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGap(135, 135, 135)
                        .addComponent(add_btn1, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(add_btn1, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(33, 33, 33)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(delete_btn1, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(update_btn1, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(25, Short.MAX_VALUE))
        );

        jLabel12.setFont(new java.awt.Font("Consolas", 0, 24)); // NOI18N
        jLabel12.setText("Id          :");

        id_txt1.setEditable(false);
        id_txt1.setFont(new java.awt.Font("Consolas", 0, 18)); // NOI18N
        id_txt1.setPreferredSize(new java.awt.Dimension(71, 35));

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(classname_drop, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(search, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 575, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 39, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(id_txt1, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(classname_txt, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(4, 4, 4)))
                .addGap(108, 108, 108))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(classname_drop, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(search, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(26, 26, 26)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 370, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(11, 11, 11)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(id_txt1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(classname_txt, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(66, 66, 66))
        );

        jTabbedPane1.addTab("Class", jPanel4);

        jLabel7.setFont(new java.awt.Font("Consolas", 0, 18)); // NOI18N
        jLabel7.setText("Search :");

        course_drop.setFont(new java.awt.Font("Consolas", 0, 18)); // NOI18N
        course_drop.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                course_dropActionPerformed(evt);
            }
        });

        add_btn2.setFont(new java.awt.Font("Consolas", 0, 18)); // NOI18N
        add_btn2.setText("Add Data");
        add_btn2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                add_btn2ActionPerformed(evt);
            }
        });

        update_btn2.setFont(new java.awt.Font("Consolas", 0, 18)); // NOI18N
        update_btn2.setText("Update Data");
        update_btn2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                update_btn2ActionPerformed(evt);
            }
        });

        delete_btn2.setFont(new java.awt.Font("Consolas", 0, 18)); // NOI18N
        delete_btn2.setText("Delete Data");
        delete_btn2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                delete_btn2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGap(31, 31, 31)
                        .addComponent(delete_btn2, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(update_btn2, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGap(127, 127, 127)
                        .addComponent(add_btn2, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addComponent(add_btn2, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(29, 29, 29)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(delete_btn2, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(update_btn2, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(25, Short.MAX_VALUE))
        );

        coursename_txt.setFont(new java.awt.Font("Consolas", 0, 18)); // NOI18N
        coursename_txt.setPreferredSize(new java.awt.Dimension(71, 35));

        jLabel8.setFont(new java.awt.Font("Consolas", 0, 24)); // NOI18N
        jLabel8.setText("Course Name  :");

        jScrollPane2.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        jScrollPane2.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        courseData_table.setFont(new java.awt.Font("Consolas", 0, 14)); // NOI18N
        courseData_table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Id", "Course Name"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        courseData_table.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_ALL_COLUMNS);
        courseData_table.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                courseData_tableMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(courseData_table);

        search1.setFont(new java.awt.Font("Consolas", 0, 18)); // NOI18N
        search1.setText("Search");
        search1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                search1ActionPerformed(evt);
            }
        });

        jLabel13.setFont(new java.awt.Font("Consolas", 0, 24)); // NOI18N
        jLabel13.setText("Id          :");

        id_txt2.setEditable(false);
        id_txt2.setFont(new java.awt.Font("Consolas", 0, 18)); // NOI18N
        id_txt2.setPreferredSize(new java.awt.Dimension(71, 35));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(course_drop, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(search1, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 575, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 39, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(108, 108, 108))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 189, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(coursename_txt, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(91, 91, 91))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(id_txt2, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(91, 91, 91))))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(course_drop, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(search1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(26, 26, 26)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 370, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(id_txt2, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(coursename_txt, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(66, 66, 66))
        );

        jTabbedPane1.addTab("Course", jPanel2);

        jLabel9.setFont(new java.awt.Font("Consolas", 0, 18)); // NOI18N
        jLabel9.setText("Search :");

        sessionname_drop.setFont(new java.awt.Font("Consolas", 0, 18)); // NOI18N
        sessionname_drop.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sessionname_dropActionPerformed(evt);
            }
        });

        add_btn3.setFont(new java.awt.Font("Consolas", 0, 18)); // NOI18N
        add_btn3.setText("Add Data");
        add_btn3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                add_btn3ActionPerformed(evt);
            }
        });

        update_btn3.setFont(new java.awt.Font("Consolas", 0, 18)); // NOI18N
        update_btn3.setText("Update Data");
        update_btn3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                update_btn3ActionPerformed(evt);
            }
        });

        delete_btn3.setFont(new java.awt.Font("Consolas", 0, 18)); // NOI18N
        delete_btn3.setText("Delete Data");
        delete_btn3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                delete_btn3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addGap(31, 31, 31)
                        .addComponent(delete_btn3, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(update_btn3, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addGap(132, 132, 132)
                        .addComponent(add_btn3, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(38, Short.MAX_VALUE))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addComponent(add_btn3, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(delete_btn3, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(update_btn3, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(25, Short.MAX_VALUE))
        );

        session_txt.setFont(new java.awt.Font("Consolas", 0, 18)); // NOI18N
        session_txt.setPreferredSize(new java.awt.Dimension(71, 35));

        jLabel10.setFont(new java.awt.Font("Consolas", 0, 24)); // NOI18N
        jLabel10.setText("Session Name  :");

        jScrollPane3.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        jScrollPane3.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        sessionData_table.setFont(new java.awt.Font("Consolas", 0, 14)); // NOI18N
        sessionData_table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Id", "Session Name"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        sessionData_table.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_ALL_COLUMNS);
        sessionData_table.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                sessionData_tableMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(sessionData_table);

        search2.setFont(new java.awt.Font("Consolas", 0, 18)); // NOI18N
        search2.setText("Search");
        search2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                search2ActionPerformed(evt);
            }
        });

        jLabel15.setFont(new java.awt.Font("Consolas", 0, 24)); // NOI18N
        jLabel15.setText("Id            :");

        id_txt3.setEditable(false);
        id_txt3.setFont(new java.awt.Font("Consolas", 0, 18)); // NOI18N
        id_txt3.setPreferredSize(new java.awt.Dimension(71, 35));

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(sessionname_drop, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(search2, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 575, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 42, Short.MAX_VALUE)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(108, 108, 108))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jLabel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(session_txt, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(id_txt3, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(sessionname_drop, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(search2, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(26, 26, 26)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 370, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(16, 16, 16)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(id_txt3, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(17, 17, 17)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(session_txt, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(66, 66, 66))
        );

        jTabbedPane1.addTab("Session", jPanel5);

        jLabel11.setFont(new java.awt.Font("Consolas", 0, 18)); // NOI18N
        jLabel11.setText("Search :");

        subjectname_drop.setFont(new java.awt.Font("Consolas", 0, 18)); // NOI18N
        subjectname_drop.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                subjectname_dropActionPerformed(evt);
            }
        });

        add_btn4.setFont(new java.awt.Font("Consolas", 0, 18)); // NOI18N
        add_btn4.setText("Add Data");
        add_btn4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                add_btn4ActionPerformed(evt);
            }
        });

        update_btn4.setFont(new java.awt.Font("Consolas", 0, 18)); // NOI18N
        update_btn4.setText("Update Data");
        update_btn4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                update_btn4ActionPerformed(evt);
            }
        });

        delete_btn4.setFont(new java.awt.Font("Consolas", 0, 18)); // NOI18N
        delete_btn4.setText("Delete Data");
        delete_btn4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                delete_btn4ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addGap(31, 31, 31)
                        .addComponent(delete_btn4, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(update_btn4, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addGap(131, 131, 131)
                        .addComponent(add_btn4, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addComponent(add_btn4, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(delete_btn4, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(update_btn4, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(25, Short.MAX_VALUE))
        );

        subjectname_txt.setFont(new java.awt.Font("Consolas", 0, 18)); // NOI18N
        subjectname_txt.setPreferredSize(new java.awt.Dimension(71, 35));

        jLabel14.setFont(new java.awt.Font("Consolas", 0, 24)); // NOI18N
        jLabel14.setText("Subject Name  :");

        search3.setFont(new java.awt.Font("Consolas", 0, 18)); // NOI18N
        search3.setText("Search");
        search3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                search3ActionPerformed(evt);
            }
        });

        jScrollPane5.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        jScrollPane5.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        subjectData_table.setFont(new java.awt.Font("Consolas", 0, 14)); // NOI18N
        subjectData_table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Id", "Subject Name"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        subjectData_table.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_ALL_COLUMNS);
        subjectData_table.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                subjectData_tableMouseClicked(evt);
            }
        });
        jScrollPane5.setViewportView(subjectData_table);

        jLabel16.setFont(new java.awt.Font("Consolas", 0, 24)); // NOI18N
        jLabel16.setText("Id            :");

        id_txt4.setEditable(false);
        id_txt4.setFont(new java.awt.Font("Consolas", 0, 18)); // NOI18N
        id_txt4.setPreferredSize(new java.awt.Dimension(71, 35));

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(subjectname_drop, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(search3, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 575, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 39, Short.MAX_VALUE)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(108, 108, 108))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addComponent(jLabel16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(12, 12, 12)
                                .addComponent(id_txt4, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(subjectname_txt, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(89, 89, 89))))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(subjectname_drop, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(search3, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(37, 37, 37)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(id_txt4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(subjectname_txt, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 370, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(74, 74, 74))
        );

        jTabbedPane1.addTab("Subject", jPanel6);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 359, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1202, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(6, 6, 6)
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
        manage_staff ms = new manage_staff();
        ms.setVisible(true);
        ms.pack();
        ms.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.dispose();
    }//GEN-LAST:event_jMenuItem6ActionPerformed

    private void jMenuItem7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem7ActionPerformed
        
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

    private void classData_tableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_classData_tableMouseClicked
        boolean a = classData_table.isEditing();
        if(a==false){
            JOptionPane.showMessageDialog(this, "Proceed to Edit page");
        }

        try
        {
            id1 =Integer.parseInt(classData_table.getValueAt(classData_table.getSelectedRow(), 0).toString());
            smt = con.createStatement();
            rs = smt.executeQuery("select * from classroom where classID=" + id1);

            if (rs.next()) {
                id_txt1.setText(rs.getString(1));
                classname_txt.setText(rs.getString(2));                
            }

            rs.close();
            smt.close();

        } catch( SQLException ex)
        {
            JOptionPane.showMessageDialog(this,"Cannot Procced to Edit Page");
        }
    }//GEN-LAST:event_classData_tableMouseClicked

    private void searchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchActionPerformed
        String query = classname_drop.getSelectedItem().toString();
        filter1(query);
    }//GEN-LAST:event_searchActionPerformed

    private void classname_dropActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_classname_dropActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_classname_dropActionPerformed

    private void add_btn1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_add_btn1ActionPerformed
        String classname = classname_txt.getText();

        if(classname.equals(""))
        {
            JOptionPane.showMessageDialog(null, "Add Class Name");
        }
        else
        {
            try
            {
                String query ="Insert into classroom (classname) values (?)";
                con = DriverManager.getConnection(serverurl+timezone,username,password);
                ps = con.prepareStatement(query);
                ps.setString(1, classname);
                
                if (ps.executeUpdate()>0)
                {
                    JOptionPane.showMessageDialog(null, "New Class Add Successfully");
                    setClassTableData();
                    classname_txt.setText("");
                }                
            }
            catch (HeadlessException | SQLException ex)
            {
                JOptionPane.showMessageDialog(null, ex);
            }
        }
    }//GEN-LAST:event_add_btn1ActionPerformed

    private void update_btn1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_update_btn1ActionPerformed
        String classname = classname_txt.getText();

        try
        {
            String sql="update classroom set classname=? where classID=?";
            con = DriverManager.getConnection(serverurl+timezone,username,password);
            ps = con.prepareStatement(sql);

            ps.setString(2, id_txt1.getText());
            ps.setString(1, classname);

            if (ps.executeUpdate() >0)
            {
                JOptionPane.showMessageDialog(null, "Update Data Successfully");
                setClassTableData();
                id_txt1.setText("");
                classname_txt.setText("");
            }
        } catch(SQLException ex)
        {
            JOptionPane.showMessageDialog(null,ex);
        }
    }//GEN-LAST:event_update_btn1ActionPerformed

    private void delete_btn1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_delete_btn1ActionPerformed
        if (id1 != 0)
        {
            try
            {
                String sql = "delete from classroom where classID="+id1;

                con = DriverManager.getConnection(serverurl+timezone ,username, password);
                smt = con.createStatement();
                smt.execute(sql);

                JOptionPane.showMessageDialog(null, "Delete Data Successfully");
                setClassTableData();
                classname_txt.setText("");
                id1=0;
            }
            catch( SQLException ex)
            {
                JOptionPane.showMessageDialog(this,"Cannot Delete Data");
            }
        }
    }//GEN-LAST:event_delete_btn1ActionPerformed

    private void course_dropActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_course_dropActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_course_dropActionPerformed

    private void add_btn2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_add_btn2ActionPerformed
        String coursename = coursename_txt.getText();

        if(coursename.equals(""))
        {
            JOptionPane.showMessageDialog(null, "Add Course Name");
        }
        else
        {
            try
            {
                String query ="Insert into course (coursename) values (?)";
                con = DriverManager.getConnection(serverurl+timezone,username,password);
                ps = con.prepareStatement(query);
                ps.setString(1, coursename);
                ps.executeUpdate();

                JOptionPane.showMessageDialog(null, "New Course Add Successfully");
                setCourseTableData();
                coursename_txt.setText("");
            }
            catch (HeadlessException | SQLException ex)
            {
                JOptionPane.showMessageDialog(null, ex);
            }
        }
    }//GEN-LAST:event_add_btn2ActionPerformed

    private void update_btn2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_update_btn2ActionPerformed
        String coursename = coursename_txt.getText();

        try
        {
            String sql="update course set coursename=? where courseID=?";
            con = DriverManager.getConnection(serverurl+timezone,username,password);
            ps = con.prepareStatement(sql);

            ps.setString(2, id_txt2.getText());
            ps.setString(1, coursename);

            if (ps.executeUpdate() >0)
            {
                JOptionPane.showMessageDialog(null, "Update Data Successfully");
                setCourseTableData();
                id_txt2.setText("");
                coursename_txt.setText("");
            }
        } catch(SQLException ex)
        {
            JOptionPane.showMessageDialog(this,"Cannot Update Data");
        }
    }//GEN-LAST:event_update_btn2ActionPerformed

    private void delete_btn2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_delete_btn2ActionPerformed
        if (id2 != 0)
        {
            try
            {
                String sql = "delete from course where courseID="+id2;

                con = DriverManager.getConnection(serverurl+timezone ,username, password);
                smt = con.createStatement();
                smt.execute(sql);

                JOptionPane.showMessageDialog(null, "Delete Data Successfully");
                setCourseTableData();
                coursename_txt.setText("");
                id2=0;
            }
            catch( SQLException ex)
            {
                JOptionPane.showMessageDialog(this,"Cannot Delete Data");
            }
        }
    }//GEN-LAST:event_delete_btn2ActionPerformed

    private void courseData_tableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_courseData_tableMouseClicked
        boolean a = courseData_table.isEditing();
        if(a==false){
            JOptionPane.showMessageDialog(this, "Proceed to Edit page");
        }

        try
        {
            id2 =Integer.parseInt(courseData_table.getValueAt(courseData_table.getSelectedRow(), 0).toString());
            smt = con.createStatement();
            rs = smt.executeQuery("select * from course where courseID=" + id2);

            if (rs.next()) {
                id_txt2.setText(rs.getString(1));
                coursename_txt.setText(rs.getString(2));                
            }

            rs.close();
            smt.close();

        } catch( SQLException ex)
        {
            JOptionPane.showMessageDialog(this,"Cannot Procced to Edit Page");
        }
    }//GEN-LAST:event_courseData_tableMouseClicked

    private void search1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_search1ActionPerformed
        String query = course_drop.getSelectedItem().toString();
        filter2(query);
    }//GEN-LAST:event_search1ActionPerformed

    private void sessionname_dropActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sessionname_dropActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_sessionname_dropActionPerformed

    private void add_btn3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_add_btn3ActionPerformed
         String sessionname = session_txt.getText();

        if(sessionname.equals(""))
        {
            JOptionPane.showMessageDialog(null, "Add Session Name");
        }
        else
        {
            try
            {
                String query ="Insert into session (sessionname) values (?)";
                con = DriverManager.getConnection(serverurl+timezone,username,password);
                ps = con.prepareStatement(query);
                ps.setString(1, sessionname);
                ps.executeUpdate();

                JOptionPane.showMessageDialog(null, "New Session Add Successfully");
                setSessionTableData();
                session_txt.setText("");
            }
            catch (HeadlessException | SQLException ex)
            {
                JOptionPane.showMessageDialog(null, ex);
            }
        }
    }//GEN-LAST:event_add_btn3ActionPerformed

    private void update_btn3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_update_btn3ActionPerformed
        String session = session_txt.getText();

        try
        {
            String sql="update session set sessionname=? where sessionID=?";
            con = DriverManager.getConnection(serverurl+timezone,username,password);
            ps = con.prepareStatement(sql);

            ps.setString(2, id_txt3.getText());
            ps.setString(1, session);

            if (ps.executeUpdate() >0)
            {
                JOptionPane.showMessageDialog(null, "Update Data Successfully");
                setSessionTableData();
                id_txt3.setText("");
                session_txt.setText("");
            }
        } catch(SQLException ex)
        {
            JOptionPane.showMessageDialog(this,"Cannot Update Data");
        }
    }//GEN-LAST:event_update_btn3ActionPerformed

    private void delete_btn3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_delete_btn3ActionPerformed
        if (id3 != 0)
        {
            try
            {
                String sql = "delete from session where sessionID="+id3;

                con = DriverManager.getConnection(serverurl+timezone ,username, password);
                smt = con.createStatement();
                smt.execute(sql);

                JOptionPane.showMessageDialog(null, "Delete Data Successfully");
                setSessionTableData();
                session_txt.setText("");
                id3=0;
            }
            catch( SQLException ex)
            {
                JOptionPane.showMessageDialog(this,"Cannot Delete Data");
            }
        }
    }//GEN-LAST:event_delete_btn3ActionPerformed

    private void sessionData_tableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_sessionData_tableMouseClicked
        boolean a = sessionData_table.isEditing();
        if(a==false){
            JOptionPane.showMessageDialog(this, "Proceed to Edit page");
        }

        try
        {
            id3 =Integer.parseInt(sessionData_table.getValueAt(sessionData_table.getSelectedRow(), 0).toString());
            smt = con.createStatement();
            rs = smt.executeQuery("select * from session where sessionID=" + id3);

            if (rs.next()) {
                id_txt3.setText(rs.getString(1));
                session_txt.setText(rs.getString(2));                
            }

            rs.close();
            smt.close();

        } catch( SQLException ex)
        {
            JOptionPane.showMessageDialog(this,"Cannot Procced to Edit Page");
        }
    }//GEN-LAST:event_sessionData_tableMouseClicked

    private void search2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_search2ActionPerformed
        String query = sessionname_drop.getSelectedItem().toString();
        filter3(query);
    }//GEN-LAST:event_search2ActionPerformed

    private void subjectname_dropActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_subjectname_dropActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_subjectname_dropActionPerformed

    private void add_btn4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_add_btn4ActionPerformed
        String subjectname = subjectname_txt.getText();

        if(subjectname.equals(""))
        {
            JOptionPane.showMessageDialog(null, "Add Subject Name");
        }
        else
        {
            try
            {
                String query ="Insert into subject (subjectname) values (?)";
                con = DriverManager.getConnection(serverurl+timezone,username,password);
                ps = con.prepareStatement(query);
                ps.setString(1, subjectname);
                ps.executeUpdate();

                JOptionPane.showMessageDialog(null, "New Subject Add Successfully");
                setSubjectTableData();
                subjectname_txt.setText("");
            }
            catch (HeadlessException | SQLException ex)
            {
                JOptionPane.showMessageDialog(null, ex);
            }
        }
    }//GEN-LAST:event_add_btn4ActionPerformed

    private void update_btn4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_update_btn4ActionPerformed
       String subjectname = subjectname_txt.getText();

        try
        {
            String sql="update subject set subjectname=? where subjectID=?";
            con = DriverManager.getConnection(serverurl+timezone,username,password);
            ps = con.prepareStatement(sql);
            
            ps.setString(2, id_txt4.getText());
            ps.setString(1, subjectname);

            if (ps.executeUpdate() >0)
            {
                JOptionPane.showMessageDialog(null, "Update Data Successfully");
                setSessionTableData();
                id_txt4.setText("");
                subjectname_txt.setText("");
            }
        } catch(SQLException ex)
        {
            JOptionPane.showMessageDialog(this,"Cannot Update Data");
        }
    }//GEN-LAST:event_update_btn4ActionPerformed

    private void delete_btn4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_delete_btn4ActionPerformed
        if (id4 != 0)
        {
            try
            {
                String sql = "delete from subject where subjectID="+id4;

                con = DriverManager.getConnection(serverurl+timezone ,username, password);
                smt = con.createStatement();
                smt.execute(sql);

                JOptionPane.showMessageDialog(null, "Delete Data Successfully");
                setSubjectTableData();
                subjectname_txt.setText("");
                id4=0;
            }
            catch( SQLException ex)
            {
                JOptionPane.showMessageDialog(this,"Cannot Delete Data");
            }
        }
    }//GEN-LAST:event_delete_btn4ActionPerformed

    private void search3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_search3ActionPerformed
        String query = subjectname_drop.getSelectedItem().toString();
        filter4(query);
    }//GEN-LAST:event_search3ActionPerformed

    private void subjectData_tableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_subjectData_tableMouseClicked
        boolean a = subjectData_table.isEditing();
        if(a==false){
            JOptionPane.showMessageDialog(this, "Proceed to Edit page");
        }

        try
        {
            id4 =Integer.parseInt(subjectData_table.getValueAt(subjectData_table.getSelectedRow(), 0).toString());
            smt = con.createStatement();
            rs = smt.executeQuery("select * from subject where subjectID=" + id4);

            if (rs.next()) {
                id_txt4.setText(rs.getString(1));
                subjectname_txt.setText(rs.getString(2));                
            }

            rs.close();
            smt.close();

        } catch( SQLException ex)
        {
            JOptionPane.showMessageDialog(this,"Cannot Procced to Edit Page");
        }
    }//GEN-LAST:event_subjectData_tableMouseClicked

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
            java.util.logging.Logger.getLogger(manage_classroom.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(manage_classroom.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(manage_classroom.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(manage_classroom.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new manage_classroom().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton add_btn1;
    private javax.swing.JButton add_btn2;
    private javax.swing.JButton add_btn3;
    private javax.swing.JButton add_btn4;
    private javax.swing.JTable classData_table;
    private javax.swing.JComboBox<String> classname_drop;
    private javax.swing.JTextField classname_txt;
    private javax.swing.JTable courseData_table;
    private javax.swing.JComboBox<String> course_drop;
    private javax.swing.JTextField coursename_txt;
    private javax.swing.JButton delete_btn1;
    private javax.swing.JButton delete_btn2;
    private javax.swing.JButton delete_btn3;
    private javax.swing.JButton delete_btn4;
    private javax.swing.JTextField id_txt1;
    private javax.swing.JTextField id_txt2;
    private javax.swing.JTextField id_txt3;
    private javax.swing.JTextField id_txt4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
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
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JButton search;
    private javax.swing.JButton search1;
    private javax.swing.JButton search2;
    private javax.swing.JButton search3;
    private javax.swing.JTable sessionData_table;
    private javax.swing.JTextField session_txt;
    private javax.swing.JComboBox<String> sessionname_drop;
    private javax.swing.JTable subjectData_table;
    private javax.swing.JComboBox<String> subjectname_drop;
    private javax.swing.JTextField subjectname_txt;
    private javax.swing.JButton update_btn1;
    private javax.swing.JButton update_btn2;
    private javax.swing.JButton update_btn3;
    private javax.swing.JButton update_btn4;
    // End of variables declaration//GEN-END:variables
}
