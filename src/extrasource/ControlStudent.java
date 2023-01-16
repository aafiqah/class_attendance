package extrasource;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.JTable;


public class ControlStudent 
{
    Connection conn=null;
    PreparedStatement pst=null;
    Statement smt=null;
    ResultSet rst=null;    
    
    String username = "root";
    String password = "shellbt31_ofis";    
    String serverurl = "jdbc:mysql://localhost:3306/facerecognitionattendance";
    String timezone = "?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
    
    
    public void insert(studentData mod)
    {
        //String date = new SimpleDateFormat("dd/MM/yyyy").format(new Date(System.currentTimeMillis()));

        try 
        {
            String query = "INSERT INTO student (studentID, student_name, nickname, guardian_name, guardian_email, guardian_contact) VALUES ( ?, ?, ?, ?, ?, ?)";            
            conn = DriverManager.getConnection(serverurl+timezone,username,password);
            pst = conn.prepareStatement(query);                    
            rst = pst.executeQuery();
             
            pst.setString(1, mod.getStudentID());
            pst.setString(2, mod.getStudent_name());
            pst.setString(3, mod.getNick_name());
            pst.setString(4, mod.getGuardian_name());
            pst.setString(5, mod.getGuardian_email());
            pst.setString(6, mod.getGuardian_contact());
            pst.executeUpdate();
            System.out.println("Data do(a): " + mod.getNick_name() + " registered");
        } 
        catch (SQLException ex) 
        {
            System.out.println("Error: " + ex);
        }
    }
    
//    public void update(ModelStudent mod, int studentID)
//    {
//        try 
//        {
//            String query = "DELETE FROM student WHERE studentID= '" + studentID + "'";            
//            conn = DriverManager.getConnection(serverurl+timezone,username,password);
//            pst = conn.prepareStatement(query);                    
//            pst.executeUpdate();
//            JOptionPane.showMessageDialog(null, "Record Deleted Successfully!");
//        } 
//        catch (SQLException e) 
//        {
//            JOptionPane.showMessageDialog(null, "Error To Delete");
//        }
//    }
    
    public void delete(int studentID) 
    {
        try 
        {
            String query = "DELETE FROM student WHERE studentID= '" + studentID + "'";            
            conn = DriverManager.getConnection(serverurl+timezone,username,password);
            pst = conn.prepareStatement(query);                    
            pst.executeUpdate();
            JOptionPane.showMessageDialog(null, "Record Deleted Successfully!");
        } 
        catch (SQLException e) 
        {
            JOptionPane.showMessageDialog(null, "Error To Delete");
        }
    }
    
    public void fillTable(String SQL, JTable table)
    {
        String studentID = null;

        ArrayList data = new ArrayList();
        String[] Columns = new String[]{"Student ID", "Student Name", "Nick Name", "Guardian Name", "Guardian Email", "Guardian Conatct Number"};
          
        try 
        {
            conn = DriverManager.getConnection(serverurl+timezone,username,password);
            smt = conn.createStatement();
            rst = smt.executeQuery(SQL); 
            do 
            {
                data.add(new Object[]
                {
                    rst.getInt("studentID"),
                    rst.getString("student_name"),
                    rst.getString("nickname"),
                    rst.getString("guardian_name"),
                    rst.getString("guardian_email"),
                    rst.getString("guardian_contact"),
                });      
            } while (rst.next());
        } 
        catch (SQLException ex) 
        {
            JOptionPane.showMessageDialog(null, ex);
        } 
        
        //TemplateTable template = new TemplateTable(data, Columns);
        //table.setModel((TableModel) template);
        //table.getColumnModel().getColumn(0).setCellRenderer(new ControlStudent.ImageRenderer());
        //table.getColumnModel().getColumn(1).setMaxWidth(0);
        //table.getColumnModel().getColumn(1).setMinWidth(0);
        //table.getTableHeader().getColumnModel().getColumn(1).setMaxWidth(0);
        //table.getTableHeader().getColumnModel().getColumn(1).setMinWidth(0);
        //table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        //table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    
    }

    //class ImageRenderer implements TableCellRenderer 
    //{
    //    public JLabel lbl = new JLabel();
    //    @Override
    //    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) 
    //    {
    //        try 
    //        {
    //            Object text = table.getValueAt(row, 1);
    //            File image = new File("C:\\Class Attendnace\\test\\person." + text + ".1.jpg");
    //            String path = image.getAbsolutePath();
    //            ImageIcon i = new ImageIcon(new ImageIcon(String.valueOf(path)).getImage().getScaledInstance(lbl.getWidth() + 50, lbl.getHeight() + 50, Image.SCALE_SMOOTH));
    //            lbl.setIcon(i);
    //        } 
    //        catch (Exception e) 
    //        {
    //            e.printStackTrace();
    //      }
    //       return lbl;
    //    }
    //}

    //public void edit(ModelStudent mod, int studentID) 
    //{
    //    conne.connected();
    //    try {
    //        PreparedStatement pst = conne.con.prepareStatement("UPDATE apto SET apto= ? WHERE id=? ");
    //        pst.setString(1, mod.getFirst_name());
    //        pst.setInt(2, id);
    //        pst.execute();
    //        JOptionPane.showMessageDialog(null, "Edit successfully");
    //    } 
    //    catch (SQLException ex) 
    //    {
    //        JOptionPane.showMessageDialog(null, "Error. Try again!");
    //    } 
    //    finally 
    //    {
    //        conne.desconnected();
    //    }
    
}
