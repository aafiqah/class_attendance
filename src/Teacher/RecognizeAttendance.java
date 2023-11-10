package Teacher;

import class_attendance.Welcomepage;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.sql.Array;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import static javax.imageio.ImageIO.read;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import org.bytedeco.javacpp.BytePointer;
import org.bytedeco.javacpp.DoublePointer;
import org.bytedeco.javacpp.IntPointer;
import static org.bytedeco.opencv.global.opencv_imgcodecs.imencode;
import org.bytedeco.opencv.global.opencv_imgproc;
import static org.bytedeco.opencv.global.opencv_imgproc.COLOR_BGRA2GRAY;
import static org.bytedeco.opencv.global.opencv_imgproc.FONT_HERSHEY_PLAIN;
import static org.bytedeco.opencv.global.opencv_imgproc.cvtColor;
import static org.bytedeco.opencv.global.opencv_imgproc.putText;
import static org.bytedeco.opencv.global.opencv_imgproc.rectangle;
import org.bytedeco.opencv.opencv_core.Mat;
import org.bytedeco.opencv.opencv_core.Point;
import org.bytedeco.opencv.opencv_core.Rect;
import org.bytedeco.opencv.opencv_core.RectVector;
import org.bytedeco.opencv.opencv_core.Scalar;
import org.bytedeco.opencv.opencv_core.Size;
import org.bytedeco.opencv.opencv_face.LBPHFaceRecognizer;
import org.bytedeco.opencv.opencv_objdetect.CascadeClassifier;
import org.bytedeco.opencv.opencv_videoio.VideoCapture;

public class RecognizeAttendance extends javax.swing.JFrame {

    String date,time, course,classname,session,subject;
    
    private DaemonThread myThread = null;
    
    //vars
    int idStudent;
    String NickName, guardian_telephone;
    String present = "PRESENT";
    
    Connection con=null;
    ResultSet rs = null;
    Statement smt=null;
    PreparedStatement ps=null;    
    String username = "root";
    String password = "shellbt31_ofis";    
    String serverurl = "jdbc:mysql://localhost:3306/facerecognitionattendance";
    String timezone = "?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
    
    
    public void setLecture(String date,String time,String course,String classname,String session,String subject)
    {
        this.date=date;
        
        this.time=time;
        
        this.course=course;
        this.classname=classname;
        this.session=session;
        this.subject=subject;        
    }
    
    //javacv
    VideoCapture webSource = null;
    Mat cameraImage = new Mat();
    CascadeClassifier cascade = new CascadeClassifier("C:\\Users\\user\\Desktop\\Class Attendance\\img\\haarcascade_frontalface_alt.xml");
    LBPHFaceRecognizer recognizer = LBPHFaceRecognizer.create();
    
    BytePointer mem = new BytePointer();
    RectVector detectedFace = new RectVector();
    
    public RecognizeAttendance() {
        initComponents();
        recognizer.read("C:\\Users\\user\\Desktop\\Class Attendance\\img\\classifierLBPH.yml");
        recognizer.setThreshold(90);
    }
    
    class DaemonThread implements Runnable 
    {
        protected volatile boolean runnable = false;

        @Override
        public void run() 
        {
            synchronized (this) 
            {
                while (runnable) 
                {
                    try
                    {
                        if (webSource.grab())
                        {
                            webSource.retrieve(cameraImage);
                            Graphics g = jPanel2.getGraphics();
                            
                            Mat imageGray = new Mat();
                            cvtColor(cameraImage, imageGray, COLOR_BGRA2GRAY);
                            
                            RectVector detectedFace = new RectVector();
                            cascade.detectMultiScale(imageGray, detectedFace, 1.1, 2, 0, new Size(150, 150), new Size(500, 500));
                            
                            for (int i = 0; i < detectedFace.size(); i++) 
                            {
                                Rect FaceData = detectedFace.get(i);
                                rectangle(cameraImage, FaceData, new Scalar(0, 255, 0, 3), 3, 0, 0);
                                
                                Mat faceCaptured = new Mat(imageGray, FaceData);
                                opencv_imgproc.resize(faceCaptured, faceCaptured, new Size(160, 160));
                                
                                IntPointer label = new IntPointer(1);
                                DoublePointer confidence = new DoublePointer(1);
                                recognizer.predict(faceCaptured, label, confidence);
                                
                                int prediction = label.get(0);
                                String nicknam;
                                nicknam = NickName;
                                
                                if (prediction == -1) 
                                {
                                    rectangle(cameraImage, FaceData, new Scalar(0, 0, 255, 0), 3, 0, 0);
                                    idStudent = 0;
                                    displaynickname.setText("Unknown Student!");
                                    displayparentcontact.setText("----");
                                    sendws.setText("Send Message");
                                }
                                else
                                {
                                    rectangle(cameraImage, FaceData, new Scalar(0, 255, 0, 3), 3, 0, 0);
                                    //System.out.println(confidence.get(0));
                                    idStudent = prediction;
                                    rec();
                                }
                                nicknam = displaynickname.getText();
                                int x = Math.max(FaceData.tl().x() - 10, 0);
                                int y = Math.max(FaceData.tl().y() - 10, 0);
                                putText(cameraImage, nicknam, new Point(x, y), FONT_HERSHEY_PLAIN, 1.7, new Scalar(0, 255, 0, 2));
                            }
                            
                            imencode(".bmp", cameraImage, mem);
                            Image im = read(new ByteArrayInputStream(mem.getStringBytes()));
                            BufferedImage buff = (BufferedImage) im;
                            
                            try
                            {
                                if (g.drawImage(buff, 0, 0, 834, 572, 0, 0, buff.getWidth(), buff.getHeight(), null))
                                {
                                    if (runnable == false) 
                                    {
                                        this.wait();
                                    }
                                }
                            }
                            catch (Exception e) 
                            {
                            }
                        }
                        else 
                        {
                        }
                        
                    } 
                    catch (Exception ex) 
                    {
                        
                   }
                }
            }
        }
    }
    
    //Retrive Data from database
    public void rec() 
    {
        new Thread() 
        {
            @Override
            public void run() 
            {
                try 
                {
                    String sql = "SELECT * FROM student WHERE studentID = " + String.valueOf(idStudent);
                    con = DriverManager.getConnection(serverurl+timezone,username,password);
                    smt = con.createStatement();
                    rs = smt.executeQuery(sql);
                    
                    while (rs.next()) 
                    {
                        NickName = rs.getString("nickname");
                        displaynickname.setText(NickName);
                        
                        guardian_telephone = rs.getString("guardian_contact");
                        displayparentcontact.setText(guardian_telephone);
                        
                        sendws.setText("Send Message to " + rs.getString("guardian_contact"));
                        
                        //txt_id_label.setText(rs.getString("studentID"));
                        
                        Array ident = rs.getArray("nickname");
                        String[] student = (String[]) ident.getArray();

                        for (String student1 : student) 
                        {
                            System.out.println(student1);
                        }    
                    }
                } 
                catch (Exception e) 
                {
                }
            }
        }.start();
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
        jPanel2 = new javax.swing.JPanel();
        displaynickname = new javax.swing.JTextField();
        sendws = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        finishbttn = new javax.swing.JButton();
        openbttn = new javax.swing.JButton();
        closebttn = new javax.swing.JButton();
        displayparentcontact = new javax.swing.JTextField();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        jMenuItem3 = new javax.swing.JMenuItem();
        jMenu6 = new javax.swing.JMenu();
        jMenuItem8 = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(204, 204, 255));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 731, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 516, Short.MAX_VALUE)
        );

        displaynickname.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        displaynickname.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        displaynickname.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Student Name", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 18))); // NOI18N

        sendws.setBackground(new java.awt.Color(51, 102, 0));
        sendws.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        sendws.setForeground(new java.awt.Color(204, 255, 204));
        sendws.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sendwsActionPerformed(evt);
            }
        });

        jButton1.setBackground(new java.awt.Color(0, 102, 102));
        jButton1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("MARK");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        finishbttn.setBackground(new java.awt.Color(153, 0, 153));
        finishbttn.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        finishbttn.setForeground(new java.awt.Color(255, 255, 255));
        finishbttn.setText("FINISH");
        finishbttn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                finishbttnActionPerformed(evt);
            }
        });

        openbttn.setBackground(new java.awt.Color(153, 0, 153));
        openbttn.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        openbttn.setForeground(new java.awt.Color(255, 255, 255));
        openbttn.setText("OPEN CAMERA");
        openbttn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                openbttnActionPerformed(evt);
            }
        });

        closebttn.setBackground(new java.awt.Color(153, 0, 153));
        closebttn.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        closebttn.setForeground(new java.awt.Color(255, 255, 255));
        closebttn.setText("CLOSE CAMERA");
        closebttn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                closebttnActionPerformed(evt);
            }
        });

        displayparentcontact.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        displayparentcontact.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        displayparentcontact.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Guardian Contact Number", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 18))); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(displaynickname)
                            .addComponent(displayparentcontact)
                            .addComponent(sendws, javax.swing.GroupLayout.PREFERRED_SIZE, 470, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                            .addGap(0, 0, Short.MAX_VALUE)
                            .addComponent(openbttn, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(59, 59, 59)
                            .addComponent(closebttn)
                            .addGap(67, 67, 67)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(134, 134, 134)
                                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 195, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(162, 162, 162)
                                .addComponent(finishbttn, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(141, 141, 141)))
                .addGap(30, 30, 30))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(56, 56, 56)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(displaynickname, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(displayparentcontact, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(openbttn, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(closebttn, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(sendws, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(finishbttn, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(0, 55, Short.MAX_VALUE))
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

        jMenuItem3.setText("Take Attendance");
        jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem3ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem3);

        jMenuBar1.add(jMenu2);

        jMenu6.setText("Report");

        jMenuItem8.setText("Attendance Report");
        jMenuItem8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem8ActionPerformed(evt);
            }
        });
        jMenu6.add(jMenuItem8);

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
        TeacherPortal rs = new TeacherPortal();
        rs.setVisible(true);
        rs.pack();
        rs.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
        takeattandance ar = new takeattandance();
        ar.setVisible(true);
        ar.pack();
        ar.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.dispose();
    }//GEN-LAST:event_jMenuItem3ActionPerformed

    private void jMenuItem8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem8ActionPerformed
        attendance_report_t ar = new attendance_report_t();
        ar.setVisible(true);
        ar.pack();
        ar.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.dispose();
    }//GEN-LAST:event_jMenuItem8ActionPerformed

    private void sendwsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sendwsActionPerformed
        new send_Whatsapp(this, true, NickName, guardian_telephone,date, time).setVisible(true);
    }//GEN-LAST:event_sendwsActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        String nickname = displaynickname.getText();
        String guardianTel = displayparentcontact.getText();

        try
        {
            String query ="Insert into attendancereport (student_name,guardian_contact,course_name,class_name,session_name,subject_name,date,time,attandancestatus) values (?,?,?,?,?,?,?,?,?)";
            con = DriverManager.getConnection(serverurl+timezone,username,password);
            ps = con.prepareStatement(query);
            ps.setString(1, nickname);
            ps.setString(2, guardianTel);
            ps.setString(3, course);
            ps.setString(4, classname);
            ps.setString(5, session);
            ps.setString(6, subject);
            ps.setString(7, date);
            ps.setString(8, time);
            ps.setString(9, present);
            ps.executeUpdate();
            
            JOptionPane.showMessageDialog(null, "Student Mark");
            smt.close();
        }
        catch(Exception ex)
        {
            JOptionPane.showMessageDialog(null, ex);
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void finishbttnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_finishbttnActionPerformed
        TeacherPortal t = new TeacherPortal();
        webSource.release();
        t.setVisible(true);
        this.setVisible(false);
        this.dispose();
    }//GEN-LAST:event_finishbttnActionPerformed

    private void openbttnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_openbttnActionPerformed
        new Thread()
        {
            public void run()
            {
                webSource = new VideoCapture(0); // video capture from default cam
                myThread = new RecognizeAttendance.DaemonThread(); //create object of Dameonthread class
                Thread t = new Thread(myThread);
                t.setDaemon(true);
                myThread.runnable = true;
                //JOptionPane.showMessageDialog(null, "Reading Of 'YML' File is Done.", "Message : " + "Message Box", JOptionPane.INFORMATION_MESSAGE);
                //JOptionPane.showMessageDialog(null, "Finished Reading :D");
                t.start();
            }
        }.start();
    }//GEN-LAST:event_openbttnActionPerformed

    private void closebttnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_closebttnActionPerformed
        myThread.runnable = false;
        webSource.release();
    }//GEN-LAST:event_closebttnActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new RecognizeAttendance().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton closebttn;
    private javax.swing.JTextField displaynickname;
    private javax.swing.JTextField displayparentcontact;
    private javax.swing.JButton finishbttn;
    private javax.swing.JButton jButton1;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu6;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuItem jMenuItem8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JButton openbttn;
    private javax.swing.JButton sendws;
    // End of variables declaration//GEN-END:variables
}
