package Admin;

import class_attendance.Welcomepage;
import extrasource.studentreportData;
import javax.swing.JFrame;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.JSpinner;
import javax.swing.RowFilter;
import javax.swing.SpinnerDateModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

public class attendance_report extends javax.swing.JFrame {

    int  id;
    
    Connection con=null;
    PreparedStatement ps=null;
    ResultSet rs = null;
    Statement smt = null;
    DefaultTableModel model;
    
    String username = "root";
    String password = "shellbt31_ofis";    
    String serverurl = "jdbc:mysql://localhost:3306/facerecognitionattendance";
    String timezone = "?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
    
    public attendance_report() {
        initComponents();
        setAttendanceReportTableData();
        FileCombo1();
        FileCombo2();
        FileCombo3();
        FileCombo4();
        FileCombo5();
    }
    
    private void filter(String query){
        TableRowSorter<DefaultTableModel> tr1 = new TableRowSorter<>(model);
        attendancereportData_table.setRowSorter(tr1);
        tr1.setRowFilter(RowFilter.regexFilter(query));
    }
    
    private void FileCombo1()
    {
        try{
            String sql = "select * from attendancereport";
            con = DriverManager.getConnection(serverurl+timezone,username,password);
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            
            studentname_drop.addItem("");
            while(rs.next())
            {
                //nolocalacc_drop.addItem("");
                studentname_drop.addItem(rs.getString("student_name"));
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
            
            course_combo.addItem("");
            while(rs.next())
            {
                course_combo.addItem(rs.getString("coursename"));
            }
            
        }catch(SQLException e){
             JOptionPane.showMessageDialog(null,e);       
        }
    }
    
    private void FileCombo3()
    {
        try{
            String sql = "select * from classroom";
            con = DriverManager.getConnection(serverurl+timezone,username,password);
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            
            class_combo.addItem("");
            while(rs.next())
            {
                //nolocalacc_drop.addItem("");
                class_combo.addItem(rs.getString("classname"));
            }
            
        }catch(SQLException e){
             JOptionPane.showMessageDialog(null,e);       
        }
    }
    
    private void FileCombo4()
    {
        try{
            String sql = "select * from session";
            con = DriverManager.getConnection(serverurl+timezone,username,password);
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            
            session_combo.addItem("");
            while(rs.next())
            {
                //nolocalacc_drop.addItem("");
                session_combo.addItem(rs.getString("sessionname"));
            }
            
        }catch(SQLException e){
             JOptionPane.showMessageDialog(null,e);       
        }
    }
    
    private void FileCombo5()
    {
        try{
            String sql = "select * from subject";
            con = DriverManager.getConnection(serverurl+timezone,username,password);
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            
            subject_combo.addItem("");
            while(rs.next())
            {
                //nolocalacc_drop.addItem("");
                subject_combo.addItem(rs.getString("subjectname"));
            }
            
        }catch(SQLException e){
             JOptionPane.showMessageDialog(null,e);       
        }
    }
    
    public ArrayList<studentreportData> studentreport_list(){
        ArrayList<studentreportData> studentreport_list = new ArrayList<> ();
        String sql = "select* from attendancereport";        
        
        try{
            con = DriverManager.getConnection(serverurl+timezone,username,password);
            smt = con.createStatement();
            rs = smt.executeQuery(sql);                
                       
            while(rs.next()){
                studentreportData studentreportData= new studentreportData();
                studentreportData.setReportID(rs.getString("id_report"));
                studentreportData.setStudentName(rs.getString("student_name"));
                studentreportData.setGuardianContact(rs.getString("guardian_contact"));
                studentreportData.setCourseName(rs.getString("course_name"));
                studentreportData.setClassname(rs.getString("class_name"));
                studentreportData.setSessionName(rs.getString("session_name"));                
                studentreportData.setSubjectname(rs.getString("subject_name"));
                studentreportData.setDate(rs.getString("date"));
                 studentreportData.setTime(rs.getString("time"));  
                 studentreportData.setStatus(rs.getString("attandancestatus"));        
                studentreport_list.add(studentreportData);                
            }
        }catch(SQLException ex){
            JOptionPane.showMessageDialog(null, ex);
        }
        return studentreport_list;        
    }
    
    public void setAttendanceReportTableData(){
        ArrayList<studentreportData> dataArray = studentreport_list();
        model = (DefaultTableModel) attendancereportData_table.getModel();
        model.setRowCount(0);
        Object[] rows = new Object[10];
        
        for(int i=0; i<dataArray.size(); i++)
        {
            rows[0] = dataArray.get(i).getReportID();
            rows[1] = dataArray.get(i).getStudentName();
            rows[2] = dataArray.get(i).getGuardianContact();
            rows[3] = dataArray.get(i).getCourseName();
            rows[4] = dataArray.get(i).getClassname();
            rows[5] = dataArray.get(i).getSessionName();
            rows[6] = dataArray.get(i).getSubjectname();
            rows[7] = dataArray.get(i).getDate();
            rows[8] = dataArray.get(i).getTime();  
            rows[9] = dataArray.get(i).getStatus();
            model.addRow(rows);
        }
    }  
    
    private void resetData() {
        id_txt.setText("");
        studentname_txt.setText("");
        guardiancontact_txt.setText("");
        course_combo.setSelectedItem("");
        class_combo.setSelectedItem("");
        session_combo.setSelectedItem("");
        subject_combo.setSelectedItem("");
        attandancestatus_txt.setText("");
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
        attendancereportData_table = new javax.swing.JTable();
        searchnolocal1 = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        studentname_drop = new javax.swing.JComboBox<>();
        jPanel2 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        studentname_txt = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        add_btn = new javax.swing.JButton();
        update_btn = new javax.swing.JButton();
        reset_btn = new javax.swing.JButton();
        delete_btn = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        id_txt = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        guardiancontact_txt = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        attandancestatus_txt = new javax.swing.JTextField();
        jLabel19 = new javax.swing.JLabel();
        subject_combo = new javax.swing.JComboBox<>();
        course_combo = new javax.swing.JComboBox<>();
        class_combo = new javax.swing.JComboBox<>();
        session_combo = new javax.swing.JComboBox<>();
        date_chooser = new com.toedter.calendar.JDateChooser();
        Date date = new Date();
        SpinnerDateModel sm=new SpinnerDateModel(date, null,null, Calendar.HOUR_OF_DAY);
        time_spinner = new javax.swing.JSpinner(sm);
        jLabel20 = new javax.swing.JLabel();
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

        jPanel1.setBackground(new java.awt.Color(204, 204, 255));

        jLabel1.setFont(new java.awt.Font("Consolas", 1, 24)); // NOI18N
        jLabel1.setText("Attendance Report");

        jTabbedPane1.setPreferredSize(new java.awt.Dimension(1510, 710));

        jScrollPane1.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        jScrollPane1.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        attendancereportData_table.setFont(new java.awt.Font("Consolas", 0, 14)); // NOI18N
        attendancereportData_table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Id", "Student Name", "Guardian Contact", "Course", "Class", "Session", "Subject", "Date", "Time", "Attandance Status"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        attendancereportData_table.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_ALL_COLUMNS);
        attendancereportData_table.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                attendancereportData_tableMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(attendancereportData_table);

        searchnolocal1.setFont(new java.awt.Font("Consolas", 0, 18)); // NOI18N
        searchnolocal1.setText("Search");
        searchnolocal1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchnolocal1ActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Consolas", 0, 18)); // NOI18N
        jLabel2.setText("Search :");

        studentname_drop.setFont(new java.awt.Font("Consolas", 0, 18)); // NOI18N
        studentname_drop.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                studentname_dropActionPerformed(evt);
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
                .addComponent(studentname_drop, javax.swing.GroupLayout.PREFERRED_SIZE, 480, javax.swing.GroupLayout.PREFERRED_SIZE)
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
                    .addComponent(studentname_drop, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(searchnolocal1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 370, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jTabbedPane1.addTab("View Data", jPanel4);

        jLabel3.setFont(new java.awt.Font("Consolas", 0, 24)); // NOI18N
        jLabel3.setText("Student Name    :");

        studentname_txt.setFont(new java.awt.Font("Consolas", 0, 18)); // NOI18N
        studentname_txt.setPreferredSize(new java.awt.Dimension(71, 35));

        jLabel12.setFont(new java.awt.Font("Consolas", 0, 24)); // NOI18N
        jLabel12.setText("Guardian Contact:");

        jLabel13.setFont(new java.awt.Font("Consolas", 0, 24)); // NOI18N
        jLabel13.setText("Class           :");

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
                .addGap(17, 17, 17)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(reset_btn, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(update_btn, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(add_btn, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(delete_btn, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(13, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addComponent(add_btn, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(delete_btn, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27)
                .addComponent(reset_btn, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addComponent(update_btn, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(51, Short.MAX_VALUE))
        );

        jLabel4.setFont(new java.awt.Font("Consolas", 0, 24)); // NOI18N
        jLabel4.setText("Id              :");

        id_txt.setEditable(false);
        id_txt.setFont(new java.awt.Font("Consolas", 0, 18)); // NOI18N
        id_txt.setPreferredSize(new java.awt.Dimension(71, 35));

        jLabel16.setFont(new java.awt.Font("Consolas", 0, 24)); // NOI18N
        jLabel16.setText("Course          :");

        guardiancontact_txt.setFont(new java.awt.Font("Consolas", 0, 18)); // NOI18N
        guardiancontact_txt.setPreferredSize(new java.awt.Dimension(71, 35));

        jLabel17.setFont(new java.awt.Font("Consolas", 0, 24)); // NOI18N
        jLabel17.setText("Session         :");

        jLabel5.setFont(new java.awt.Font("Consolas", 0, 24)); // NOI18N
        jLabel5.setText("Subject     :");

        jLabel18.setFont(new java.awt.Font("Consolas", 0, 24)); // NOI18N
        jLabel18.setText("Time        :");

        attandancestatus_txt.setFont(new java.awt.Font("Consolas", 0, 18)); // NOI18N
        attandancestatus_txt.setPreferredSize(new java.awt.Dimension(71, 35));

        jLabel19.setFont(new java.awt.Font("Consolas", 0, 24)); // NOI18N
        jLabel19.setText("Date        :");

        date_chooser.setDateFormatString("dd-MM-yyyy");
        date_chooser.setFont(new java.awt.Font("Consolas", 0, 18)); // NOI18N

        JSpinner.DateEditor de = new JSpinner.DateEditor(time_spinner, "HH:mm:ss");
        time_spinner.setEditor(de);
        time_spinner.setFont(new java.awt.Font("Consolas", 0, 18)); // NOI18N

        jLabel20.setFont(new java.awt.Font("Consolas", 0, 24)); // NOI18N
        jLabel20.setText("Status      :");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addComponent(jLabel3)
                    .addComponent(jLabel12)
                    .addComponent(jLabel16)
                    .addComponent(jLabel13)
                    .addComponent(jLabel17))
                .addGap(6, 6, 6)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(id_txt, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(studentname_txt, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(guardiancontact_txt, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(course_combo, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(class_combo, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(session_combo, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(17, 17, 17)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(10, 10, 10)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(subject_combo, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(date_chooser, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(time_spinner, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(attandancestatus_txt, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(19, 19, 19)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(course_combo, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30)
                .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addComponent(id_txt, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addComponent(studentname_txt, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addComponent(guardiancontact_txt, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(90, 90, 90)
                .addComponent(class_combo, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addComponent(session_combo, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addComponent(subject_combo, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addComponent(date_chooser, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addComponent(time_spinner, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addComponent(attandancestatus_txt, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
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
                        .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1229, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(34, 34, 34))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5)
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 523, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(41, Short.MAX_VALUE))
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
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
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
        manage_staff mc = new manage_staff();
        mc.setVisible(true);
        mc.pack();
        mc.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.dispose();
    }//GEN-LAST:event_jMenuItem6ActionPerformed

    private void jMenuItem7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem7ActionPerformed
        manage_classroom mc = new manage_classroom();
        mc.setVisible(true);
        mc.pack();
        mc.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.dispose();
    }//GEN-LAST:event_jMenuItem7ActionPerformed

    private void jMenuItem8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem8ActionPerformed
        
    }//GEN-LAST:event_jMenuItem8ActionPerformed

    private void jMenuItem9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem9ActionPerformed
        history_message hm = new history_message();
        hm.setVisible(true);
        hm.pack();
        hm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.dispose();
    }//GEN-LAST:event_jMenuItem9ActionPerformed

    private void attendancereportData_tableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_attendancereportData_tableMouseClicked
        boolean a = attendancereportData_table.isEditing();
        if(a==false){
            JOptionPane.showMessageDialog(this, "Proceed to Edit page");
        }

        try
        {
            id =Integer.parseInt(attendancereportData_table.getValueAt(attendancereportData_table.getSelectedRow(), 0).toString());
            smt = con.createStatement();
            rs = smt.executeQuery("select * from attendancereport where id_report=" + id);

            if (rs.next()) {
                
                int srow = attendancereportData_table.getSelectedRow();
                Date date = new SimpleDateFormat("yyyy-MM-dd").parse((String)attendancereportData_table.getValueAt(srow,7));
                Date time = new SimpleDateFormat("HH:mm:ss").parse((String)attendancereportData_table.getValueAt(srow,8));
                
                id_txt.setText(rs.getString(1));
                studentname_txt.setText(rs.getString(2));
                guardiancontact_txt.setText(rs.getString(3));
                course_combo.setSelectedItem(rs.getString(4));
                class_combo.setSelectedItem(rs.getString(5));
                session_combo.setSelectedItem(rs.getString(6));
                subject_combo.setSelectedItem(rs.getString(7));
                date_chooser.setDate(date);
                time_spinner.setValue(time);
                attandancestatus_txt.setText(rs.getString(10));
            }

            rs.close();
            smt.close();

        } catch( NumberFormatException | SQLException | ParseException ex)
        {
            JOptionPane.showMessageDialog(this,ex);
        }
    }//GEN-LAST:event_attendancereportData_tableMouseClicked

    private void searchnolocal1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchnolocal1ActionPerformed
        String query = studentname_drop.getSelectedItem().toString();
        filter(query);
    }//GEN-LAST:event_searchnolocal1ActionPerformed

    private void studentname_dropActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_studentname_dropActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_studentname_dropActionPerformed

    private void add_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_add_btnActionPerformed
        String sname = studentname_txt.getText();
        String gcontact = guardiancontact_txt.getText();
        String course = course_combo.getSelectedItem().toString();
        String classname = class_combo.getSelectedItem().toString();
        String session = session_combo.getSelectedItem().toString();
        String subject = subject_combo.getSelectedItem().toString();
        
        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String date = sdf.format(date_chooser.getDate());
        
        SimpleDateFormat sdf1 = new SimpleDateFormat("HH:mm:ss");
        String time = sdf1.format(time_spinner.getValue());
        
        String status = attandancestatus_txt.getText();
      
        try
        {
            String query ="Insert into attendancereport (student_name,guardian_contact,course_name,class_name,session_name,subject_name,date,time,attandancestatus) values (?,?,?,?,?,?,?,?,?)";
            con = DriverManager.getConnection(serverurl+timezone,username,password);
            ps = con.prepareStatement(query);
            ps.setString(1, sname);
            ps.setString(2, gcontact);
            ps.setString(3, course);
            ps.setString(4, classname);
            ps.setString(5, session);
            ps.setString(6, subject);
            ps.setString(7, date);
            ps.setString(8, time);
            ps.setString(9, status);
            ps.executeUpdate();

            JOptionPane.showMessageDialog(null, "New Data Add Successfully");
            setAttendanceReportTableData();
            resetData();
        }
        catch (SQLException ex)
        {
            JOptionPane.showMessageDialog(null, ex);
        }
        
    }//GEN-LAST:event_add_btnActionPerformed

    private void update_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_update_btnActionPerformed
        String sname = studentname_txt.getText();
        String gcontact = guardiancontact_txt.getText();
        String course = course_combo.getSelectedItem().toString();
        String classname = class_combo.getSelectedItem().toString();
        String session = session_combo.getSelectedItem().toString();
        String subject = subject_combo.getSelectedItem().toString();
        
        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String date = sdf.format(date_chooser.getDate());
        
        SimpleDateFormat sdf1 = new SimpleDateFormat("HH:mm:ss");
        String time = sdf1.format(time_spinner.getValue());
        
        String status = attandancestatus_txt.getText();

        try
        {
            String sql="update attendancereport set student_name=?,guardian_contact=?,course_name=?,class_name=?,session_name=?,subject_name=?,date=?,time=?,attandancestatus=? where id_report=?";
            con = DriverManager.getConnection(serverurl+timezone,username,password);
            ps = con.prepareStatement(sql);

            ps.setString(10, id_txt.getText());
            ps.setString(1, sname);
            ps.setString(2, gcontact);
            ps.setString(3, course);
            ps.setString(4, classname);
            ps.setString(5, session);
            ps.setString(6, subject);
            ps.setString(7, date);
            ps.setString(8, time);
            ps.setString(9, status);

            if (ps.executeUpdate() >0)
            {
                JOptionPane.showMessageDialog(null, "Update Data Successfully");
                setAttendanceReportTableData();
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
                String sql = "delete from attendancereport where id_report="+id;

                con = DriverManager.getConnection(serverurl+timezone ,username, password);
                smt = con.createStatement();
                smt.execute(sql);

                JOptionPane.showMessageDialog(null, "Delete Data Successfully");
                setAttendanceReportTableData();
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
            java.util.logging.Logger.getLogger(attendance_report.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(attendance_report.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(attendance_report.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(attendance_report.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new attendance_report().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton add_btn;
    private javax.swing.JTextField attandancestatus_txt;
    private javax.swing.JTable attendancereportData_table;
    private javax.swing.JComboBox<String> class_combo;
    private javax.swing.JComboBox<String> course_combo;
    private com.toedter.calendar.JDateChooser date_chooser;
    private javax.swing.JButton delete_btn;
    private javax.swing.JTextField guardiancontact_txt;
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
    private javax.swing.JButton reset_btn;
    private javax.swing.JButton searchnolocal1;
    private javax.swing.JComboBox<String> session_combo;
    private javax.swing.JComboBox<String> studentname_drop;
    private javax.swing.JTextField studentname_txt;
    private javax.swing.JComboBox<String> subject_combo;
    private javax.swing.JSpinner time_spinner;
    private javax.swing.JButton update_btn;
    // End of variables declaration//GEN-END:variables
}
