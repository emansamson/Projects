package GuiClasses.School;

import CoreClasses.Driver;
import CoreClasses.School;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Vector;

public class infoTable {
    String schId;
    Vector prepareColumn() {
        final Vector<String> column = new Vector<>();
        column.add("Id");
        column.add("FName");
        column.add("LName");
        column.add("Sex");
        column.add("Program");
        column.add("Phone");
        column.add("Email");
        column.add("Address");
        column.add("Active");
        column.add("Start Date");
        column.add("End Date");
        column.add("Status");

        return column;
    }

    DefaultTableModel model = new DefaultTableModel();

    public infoTable(JTable table, School sch) {
        schId = String.valueOf(sch.getId());
        Vector<String> column = prepareColumn();
        Vector<Vector> dataVector = prepareVector(sch);
        model.setDataVector(dataVector, column);
        table.setModel(model);
    }

    Vector<Vector> prepareVector(School school) {
        Vector data = new Vector();
        String infoQuery = "SELECT d.Did, d.FName, d.LName, d.Sex, p.Name, d.Phone, d.Email, d.Address, d.Active, sd.Start_Date, sd.End_Date, d.promotion_status " +
                            "FROM dlms.Driver d, dlms.Program p, dlms.School_Driver sd, dlms.School s, dlms.Driver_Program dp  " +
                            "WHERE s.Sid = ? AND d.Did = dp.Did AND p.Pid = dp.Pid AND s.Sid = sd.Sid AND d.Did = sd.Did";
        final String dbUser = "root";
        final String dbUrl = "jdbc:mysql://localhost:3306/dlms";
        final String dbPass = "@Emansam2938";

        try {
            Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbPass);
            PreparedStatement preparedStatement = connection.prepareStatement(infoQuery);
            preparedStatement.setString(1, schId);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                Vector v = new Vector();
                v.add(rs.getString("Did"));
                v.add(rs.getString("FName"));
                v.add(rs.getString("LName"));
                v.add(rs.getString("Sex"));
                v.add(rs.getString("Name"));
                v.add(rs.getString("Phone"));
                v.add(rs.getString("Email"));
                v.add(rs.getString("Address"));
                v.add(rs.getString("Active").equals("1") ? "Yes" : "No");
                v.add(rs.getString("Start_Date"));
                v.add(rs.getString("End_Date"));
                v.add(rs.getString("promotion_status").equals("1") ? "Promoted" : "Failed");

                data.add(v);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
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
