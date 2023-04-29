package GuiClasses.Driver;

import CoreClasses.Driver;

import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class driverTabs {
    driverTabs(driverMainFrame dmf, Driver dri) throws SQLException {
        if(dmf.driTab.getSelectedIndex() == 5) {
            int option = JOptionPane.showConfirmDialog(null, "Are you sure you want logout?", "LogOut", JOptionPane.YES_NO_OPTION);
            if(option == JOptionPane.YES_OPTION){
                dmf.dispose();
                driverLogIn dl = new driverLogIn(null);
            } else {
                dmf.driTab.setSelectedIndex(0);
            }
        }
        else if (dmf.driTab.getSelectedIndex() == 2) {
            final String dbUser = "root";
            final String dbUrl = "jdbc:mysql://localhost:3306/dlms";
            final String dbPass = "@Emansam2938";

            try {
                Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbPass);
                Statement statement = connection.createStatement();
                String query = "SELECT hd.Exam_Date, hd.General_Status, hd.Blood_Type, h.Name FROM Hospital_Driver_2 hd, Hospital h WHERE Did = ? AND hd.Hid = h.Hid";
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1, String.valueOf(dri.getId()));
                ResultSet resultSet = preparedStatement.executeQuery();
//                String hospitalId = null;
                String examDate = null;
                String status = null;
                String bloodType = null;
                String hospName = null;
                if (resultSet.next()) {
//                    hospitalId = resultSet.getString("Hid");
                    examDate = resultSet.getString("Exam_Date");
                    status = resultSet.getString("General_Status").equals("P") ? "Passed" : "Failed";
                    bloodType = resultSet.getString("Blood_Type");
                    hospName = resultSet.getString("Name");
               }
                dmf.hospNameLbl.setText(hospName);
                dmf.bloodLbl.setText(bloodType);
                dmf.statusLbl.setText(status);
                dmf.examDateLbl.setText(examDate);
            }
            catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Error: Database Error", "Error", JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            }
        } else if (dmf.driTab.getSelectedIndex() == 1) {
            driverGradeTable driverTable = new driverGradeTable(dmf.gradeTable, dri);

            final String dbUser = "root";
            final String dbUrl = "jdbc:mysql://localhost:3306/dlms";
            final String dbPass = "@Emansam2938";

            try {
                Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbPass);
                Statement statement = connection.createStatement();
                String query = "SELECT * FROM driver WHERE Did = ?";
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1, String.valueOf(dri.getId()));
                ResultSet resultSet = preparedStatement.executeQuery();

                if (resultSet.next()) {
                    if (resultSet.getString("Promotion_Status").equals("1")) {
                        dmf.promotionLabel.setText("Promoted");
                        dmf.promotionLabel.setForeground(Color.green);
                    }
                    else {
                        dmf.promotionLabel.setText("Failed");
                        dmf.promotionLabel.setForeground(Color.red);
                    }
                }
            } catch (Exception e) {
                JOptionPane.showConfirmDialog(null,"Database Error", "Error", JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            }
        }
    }
}
