package GuiClasses.Driver;

import CoreClasses.Driver;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.*;
import java.util.Vector;

public class driverGradeTable {
    String driId;

    Vector prepareColumn() {
        final Vector<String> column = new Vector<>();
        column.add("Course");
        column.add("Grade");

        return column;
    }

    DefaultTableModel model = new DefaultTableModel();
    driverGradeTable(JTable table, Driver dri) {
        driId = String.valueOf(dri.getId());
        Vector<String> column = prepareColumn();
        Vector<Vector> dataVector = prepareVector(dri);
        model.setDataVector(dataVector, column);
        table.setModel(model);
    }

    Vector<Vector> prepareVector(Driver driver) {
        Vector data = new Vector();
        String gradeQuery = "SELECT c.Name, td.Grade " +
                        "FROM Trainer_Driver_2 td, Course c " +
                        "WHERE td.Did = ? AND td.Cid = c.Cid";
        final String dbUser = "root";
        final String dbUrl = "jdbc:mysql://localhost:3306/dlms";
        final String dbPass = "@Emansam2938";

        try {
            Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbPass);
            PreparedStatement preparedStatement = connection.prepareStatement(gradeQuery);
            preparedStatement.setString(1, driId);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                Vector v = new Vector();
                v.add(rs.getString("Name"));
                v.add(rs.getString("Grade"));
                data.add(v);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }
}
