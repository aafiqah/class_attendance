package Admin;

import static com.googlecode.javacv.cpp.opencv_highgui.CV_LOAD_IMAGE_GRAYSCALE;
import extrasource.TrainImage;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;

import org.opencv.core.MatOfRect;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import static org.opencv.imgcodecs.Imgcodecs.imencode;
import static org.opencv.imgcodecs.Imgcodecs.imwrite;
import org.opencv.imgproc.Imgproc;
import static org.opencv.imgproc.Imgproc.cvtColor;
import org.opencv.objdetect.CascadeClassifier;
import org.opencv.videoio.VideoCapture;

public class capture_student extends javax.swing.JFrame implements ActionListener {
    
    private DaemonThread myThread = null;
    //int count = 0;
    
    //JavaCV
    VideoCapture webSource = null;
    Mat frame = new Mat();
    //LBPHFaceRecognizer recognizer = LBPHFaceRecognizer.create();
    CascadeClassifier faceDetector = new CascadeClassifier("C:\\opencv\\sources\\data\\haarcascades\\haarcascade_frontalface_alt.xml");
    MatOfRect faceDetections = new MatOfRect();
    
    MatOfByte mem = new MatOfByte();
    //BytePointer mem = new BytePointer();
    //RectVector detectedFaces = new RectVector();
    
    //Vars
    String studentName, nickName, guardianName, guardianEmail, guardianContact;
    int numSamples = 25, sample = 1;
    
    Connection conn=null;
    PreparedStatement pst=null;
    ResultSet rst=null;    
    Statement smt=null;
    
    String username = "root";
    String password = "shellbt31_ofis";    
    String serverurl = "jdbc:mysql://localhost:3306/facerecognitionattendance";
    String timezone = "?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
        
    capture_student.DaemonThread mythread = null;
   
    public capture_student() {
        initComponents();
    }
    
    
     public capture_student(String stuname, String nickname, String gname, String gemail, String gcontact) 
    {
        initComponents();
        studentName = stuname;
        nickName= nickname;
        guardianName= gname;
        guardianEmail= gemail;
        guardianContact= gcontact;
        
        myThread = new DaemonThread();
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
    class DaemonThread implements Runnable
    {
        Rect face;
        protected volatile boolean runnable= false;

        @Override
        public void run() 
        {
            synchronized (this)
            {
                while (runnable) 
                {
                    if (webSource.grab()) 
                    {
                        try 
                        {
                            if (webSource.grab())
                            {
                                webSource.retrieve(frame);
                                Graphics g = jPanel5.getGraphics();
                                
                                Mat imageColor = new Mat(); //imagem colorida
                                imageColor = frame;
                                
                                Mat imageGray = new Mat();
                                cvtColor(imageColor, imageGray, CV_LOAD_IMAGE_GRAYSCALE);
                                
                                faceDetector.detectMultiScale(frame, faceDetections);
                                
                                for (Rect rect : faceDetections.toArray()) 
                                {
                                    
                                    Imgproc.rectangle(frame, new Point(rect.x, rect.y), new Point(rect.x + rect.width, rect.y + rect.height),new Scalar(0, 0, 0));
                                    
                                    face = new Rect(rect.x, rect.y, rect.width, rect.height);
                                    Mat image_roi = new Mat(frame, face);
                                    
                                    if (sample <= numSamples) 
                                   {
                                        String cropped = "C:\\Users\\user\\Desktop\\Class Attendance\\img\\" + nickName  + "." + sample + ".jpg";
                                        imwrite(cropped, image_roi);
                                        counterLabel.setText(String.valueOf(sample)+ "/25");
                                        sample++;
                                   }
                                    if (sample > 25)
                                    {
                                        generate();
                                        insertDatabase();
                                        JOptionPane.showMessageDialog(null, "Image Generate Successfully");
                                        CameraStop();
                                    }
                                }
                                imencode(".bmp", frame, mem);
                                Image im = ImageIO.read(new ByteArrayInputStream(mem.toArray()));
                                BufferedImage buff = (BufferedImage) im;
                                try
                                {
                                    if (g.drawImage(buff, 0, 0, 673, 492, 0, 0, buff.getWidth(), buff.getHeight(), null)) 
                                    {
                                        if (runnable == false) 
                                        {
                                            System.out.println("Photo(s) are Save ");
                                            this.wait();
                                        }
                                    }
                                }
                                catch (Exception e) 
                                {
                                }
                            }
                        }
                        catch (Exception ex)
                        {
                            ex.printStackTrace();
                        }
                    }
                }
            }    
        }
    }   
    
    public void insertDatabase() 
    {       
         try 
            {    

                String sql = "INSERT INTO student (student_name, nickname, guardian_name, guardian_email, guardian_contact) VALUES (?, ?, ?, ?, ?)";
                conn = DriverManager.getConnection(serverurl+timezone,username,password);
                pst = conn.prepareStatement(sql);
                
                pst.setString(1, studentName);
                pst.setString(2, nickName);
                pst.setString(3, guardianName);
                pst.setString(4, guardianEmail);
                pst.setString(5, guardianContact);

                if (pst.executeUpdate()>0)
                {
                    JOptionPane.showMessageDialog(null, "New Data Successfully");
                }
            } catch(SQLException ex)
            {
                JOptionPane.showMessageDialog(this, "Cannot Add Data");
            }
        
    }
    
    public void generate() 
    {
        TrainImage.main();
    }
      
    public void CameraStop() 
    {
        myThread.runnable = false;  // stop thread
        start.setEnabled(true);   // activate start button 
        webSource.release();// stop caturing front camera
        JOptionPane.showMessageDialog(null, "Done Registration");
                
        admin_portal ap = new admin_portal();
        ap.setVisible(true);
        ap.pack();
        ap.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.dispose();
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
        jLabel11 = new javax.swing.JLabel();
        gname_field = new javax.swing.JTextField();
        jPanel5 = new javax.swing.JPanel();
        counterLabel = new javax.swing.JLabel();
        start = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Class Attendance");
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(204, 204, 255));

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(51, 51, 51));
        jLabel11.setText("GUARDIAN NAME:");

        gname_field.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 660, Short.MAX_VALUE)
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 490, Short.MAX_VALUE)
        );

        counterLabel.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        counterLabel.setForeground(new java.awt.Color(51, 51, 51));
        counterLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        counterLabel.setText("/25");

        start.setBackground(new java.awt.Color(102, 102, 102));
        start.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        start.setForeground(new java.awt.Color(255, 255, 255));
        start.setText("START");
        start.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                startMouseClicked(evt);
            }
        });
        start.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                startActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(280, 280, 280)
                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(20, 20, 20)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(counterLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(start, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(316, 316, 316)
                        .addComponent(jLabel11))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(243, 243, 243)
                        .addComponent(gname_field, javax.swing.GroupLayout.PREFERRED_SIZE, 415, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(172, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(80, 80, 80)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(210, 210, 210)
                        .addComponent(counterLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(start, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(813, 813, 813)
                .addComponent(jLabel11)
                .addGap(6, 6, 6)
                .addComponent(gname_field, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 650, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void startMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_startMouseClicked

    }//GEN-LAST:event_startMouseClicked

    private void startActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_startActionPerformed
        webSource = new VideoCapture(1); // video capture from default cam
        myThread = new capture_student.DaemonThread(); //create object of Dameonthread class
        Thread t = new Thread(myThread);
        t.setDaemon(true);
        myThread.runnable = true;
        t.start(); //start thread
        start.setEnabled(false);
    }//GEN-LAST:event_startActionPerformed

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
            java.util.logging.Logger.getLogger(capture_student.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(capture_student.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(capture_student.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(capture_student.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new capture_student().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel counterLabel;
    private javax.swing.JTextField gname_field;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JButton start;
    // End of variables declaration//GEN-END:variables
}
