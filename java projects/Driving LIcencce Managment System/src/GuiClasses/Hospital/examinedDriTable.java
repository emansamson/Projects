package GuiClasses.Hospital;

import CoreClasses.Hospital;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.*;
import java.util.Vector;

public class examinedDriTable {
    String hospId;
    Vector prepareColumn() {
        final Vector<String> column = new Vector<>();
        column.add("Driver Id");
        column.add("First Name");
        column.add("Last Name");
        column.add("General Status");
        column.add("Blood Type");
        column.add("Exam Date");

        return column;
    }

    DefaultTableModel model = new DefaultTableModel();
    examinedDriTable(JTable table, Hospital hospital) {
        hospId = String.valueOf(hospital.getId());
        Vector<String> column = prepareColumn();
        Vector<Vector> dataVector = prepareVector(hospital);
        model.setDataVector(dataVector, column);
        table.setModel(model);
    }

    Vector<Vector> prepareVector(Hospital hospital) {
        Vector data = new Vector();
        String examQuery = "SELECT d.Did, d.FName, d.LName, hd.General_Status, hd.Blood_Type, hd.Exam_Date" +
                          " FROM dlms.Driver d, dlms.Hospital_Driver_2 hd " +
                          "WHERE hd.Hid = ? AND d.Did = hd.Did";
        final String dbUser = "root";
        final String dbUrl = "jdbc:mysql://localhost:3306/dlms";
        final String dbPass = "@Emansam2938";
        try {
            Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbPass);
            PreparedStatement preparedStatement = connection.prepareStatement(examQuery);
            preparedStatement.setString(1, hospId);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                Vector v = new Vector();
                v.add(rs.getString("Did"));
                v.add(rs.getString("FName"));
                v.add(rs.getString("LName"));
                v.add(rs.getString("General_Status").equals("P") ? "Passed" : "Failed");
                v.add(rs.getString("Blood_Type"));
                v.add(rs.getString("Exam_Date"));
                data.add(v);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }
}
