package GuiClasses.Trainer;

import CoreClasses.Trainer;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Vector;

public class prepareTable {
    Vector prepareColumn() {
        final Vector<String> column = new Vector<>();
        column.add("DriverId");
        column.add("FirstName");
        column.add("LastName");
        column.add("Course");
        column.add("Grade");
        column.add("Training Date");
        return column;
    }
    DefaultTableModel model = new DefaultTableModel();
    prepareTable(JTable table, Trainer trainer) {
        Vector<String> column = prepareColumn();
        Vector<Vector> dataVector = prepareVector(trainer);
        model.setDataVector(dataVector, column);
        table.setModel(model);
    }
    Vector prepareVector(Trainer trainer) {
        Vector vector = new Vector();
        String sql = "SELECT d.Did, d.FName, d.LName, c.Name, td.Grade, td.Training_Date " +
                    "FROM Trainer_Driver_2 td, Driver d, Course c " +
                    "WHERE td.Did=d.Did AND td.Cid=c.Cid AND td.Tid ="+trainer.getId();
        String sql1 = " ORDER BY d.FName";

        final String dbUser = "root";
        final String dbUrl = "jdbc:mysql://localhost:3306/dlms";
        final String dbPass = "@Emansam2938";
        try {
            Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbPass);
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(sql+sql1);
            while (rs.next()) {
                Vector v = new Vector();
                v.add(rs.getString("Did"));
                v.add(rs.getString("FName"));
                v.add(rs.getString("LName"));
                v.add(rs.getString("Name"));
                v.add(rs.getString("Grade"));
                v.add(rs.getString("Training_Date"));
                vector.add(v);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: Database error", "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
        return vector;
    }

    //First parameter will be the search field
    //second parameter will be the raw data vector from which the search will be performed
    //third parameter will be the table
    void searchTable(JTextField tableSearchBar, Vector ve, JTable table, int j) {
        Vector search = new Vector();
        for(int i = 0; i < ve.size();i++) {
            Vector v = (Vector) ve.get(i);
            String s = (String) v.get(j);

            if(j == 1)
                s += v.get(2);
            if (s == null)
                continue;

            if ((s.toLowerCase().contains(tableSearchBar.getText().trim().toLowerCase()))) {
                search.add(v);
            }
        }
        Vector<String> column = prepareColumn();
        model.setDataVector(search, column);
        table.setModel(model);
    }
}

