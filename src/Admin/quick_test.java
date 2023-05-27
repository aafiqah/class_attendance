package Admin;

import class_attendance.Welcomepage;
import java.awt.Graphics;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.sql.Array;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.imageio.ImageIO;
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
import org.opencv.core.Core;

public class quick_test extends javax.swing.JFrame {

    private DaemonThread myThread = null;
    
    //JavaCV 
    VideoCapture webSource = null;
    Mat cameraImage = new Mat();
    CascadeClassifier cascade = new CascadeClassifier("C:\\Users\\user\\Desktop\\Class Attendance\\img\\haarcascade_frontalface_alt.xml");
    LBPHFaceRecognizer recognizer = LBPHFaceRecognizer.create();
    
    BytePointer mem = new BytePointer();
    
    //vars
    String root,NickName;
    int idStudent;
    
    Connection conn=null;
    PreparedStatement pst=null;
    ResultSet rst=null;    
    Statement smt=null;
    
    String username = "root";
    String password = "shellbt31_ofis";    
    String serverurl = "jdbc:mysql://localhost:3306/facerecognitionattendance";
    String timezone = "?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
        
    
    public quick_test() {
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
                                String nicknam ;
                                nicknam = NickName;
                                
                                if (prediction == -1) 
                                {
                                    rectangle(cameraImage, FaceData, new Scalar(0, 0, 255, 3), 3, 0, 0);
                                    idStudent = 0;
                                    displaynickname.setText("Unknown");
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
                            Image im = ImageIO.read(new ByteArrayInputStream(mem.getStringBytes()));
                            BufferedImage buff = (BufferedImage) im;
                            
                            try 
                            {
                                if (g.drawImage(buff, 0, 0, 673, 492, 0, 0, buff.getWidth(), buff.getHeight(), null)) {
                                    if (runnable == false) 
                                    {
                                        this.wait();
                                    }
                                }
                            }
                            catch (InterruptedException e) 
                            {
                                JOptionPane.showMessageDialog(null, e);
                            }
                        }  
                        else 
                        {
                            
                        }
                    }
                    catch (HeadlessException | IOException ex) 
                    {
                        JOptionPane.showMessageDialog(null, ex);
                    }
                }
            } 
        }
    }
     
    //Retrive Data from database
    private void rec() 
    {
        new Thread() 
        {
            @Override
            public void run() 
            {
                try 
                {
                    String sql = "SELECT * FROM student WHERE studentID = " + String.valueOf(idStudent);
                    conn = DriverManager.getConnection(serverurl+timezone,username,password);
                    smt = conn.createStatement();
                    rst = smt.executeQuery(sql);
                    
                    
                    while (rst.next()) 
                    {
                        NickName = rst.getString("nickname");
                        displaynickname.setText(NickName);
                        
                        Array ident = rst.getArray("nickname");
                        String[] student = (String[]) ident.getArray();

                        for (String student1 : student) 
                        {
                            System.out.println(student1);
                        }    
                    }
                }
                catch (SQLException e)
                {
                    System.out.println(e);
                }
            }
        }.start();
    }            
    
    //This method turns off the software connection with your web cam.
    public void stopCamera() 
    {
        myThread.runnable = false;
        webSource.release();
    }

    //This method turns on the software connection with your web cam.
    public void startCamera() 
    {
        new Thread() 
        {
            @Override
            public void run() 
            {
                webSource = new VideoCapture(1);
                myThread = new quick_test.DaemonThread();
                Thread t = new Thread(myThread);
                t.setDaemon(true);
                myThread.runnable = true;
                t.start();
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
        displaynickname = new javax.swing.JLabel();
        openbttn = new javax.swing.JButton();
        closebttn = new javax.swing.JButton();
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

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 673, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 492, Short.MAX_VALUE)
        );

        displaynickname.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        displaynickname.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        displaynickname.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Student Name", javax.swing.border.TitledBorder.LEFT, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 14))); // NOI18N

        openbttn.setBackground(new java.awt.Color(153, 0, 153));
        openbttn.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        openbttn.setForeground(new java.awt.Color(255, 255, 255));
        openbttn.setText("OPEN CAMERA");
        openbttn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                openbttnActionPerformed(evt);
            }
        });

        closebttn.setBackground(new java.awt.Color(204, 204, 204));
        closebttn.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        closebttn.setForeground(new java.awt.Color(255, 255, 255));
        closebttn.setText("CLOSE CAMERA");
        closebttn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                closebttnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(126, 126, 126)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(51, 51, 51)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(displaynickname, javax.swing.GroupLayout.PREFERRED_SIZE, 320, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(80, 80, 80)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(openbttn, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(closebttn, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(120, 120, 120)
                .addComponent(displaynickname, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(50, 50, 50)
                .addComponent(openbttn, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(50, 50, 50)
                .addComponent(closebttn, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(114, 114, 114))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(39, 39, 39))
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
        
    }//GEN-LAST:event_jMenuItem5ActionPerformed

    private void jMenuItem6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem6ActionPerformed
        manage_staff mst = new manage_staff();
        mst.setVisible(true);
        mst.pack();
        mst.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        admin_portal ap = new admin_portal();
        ap.setVisible(true);
        ap.pack();
        ap.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.dispose();
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void openbttnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_openbttnActionPerformed
        startCamera();
    }//GEN-LAST:event_openbttnActionPerformed

    private void closebttnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_closebttnActionPerformed
        stopCamera();
    }//GEN-LAST:event_closebttnActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
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
            java.util.logging.Logger.getLogger(quick_test.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(quick_test.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(quick_test.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(quick_test.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new quick_test().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton closebttn;
    private javax.swing.JLabel displaynickname;
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
    private javax.swing.JButton openbttn;
    // End of variables declaration//GEN-END:variables
}
