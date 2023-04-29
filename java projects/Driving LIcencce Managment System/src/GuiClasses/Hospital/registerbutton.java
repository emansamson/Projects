package GuiClasses.Hospital;

import CoreClasses.Hospital;

import javax.swing.*;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

public class registerbutton {
    registerbutton(hospitalMainFrame hmf, Hospital hospital) {
        boolean empty = false;
        for (JTextField i : hmf.nonEmptyFields) {
            if(i.getText().trim().isEmpty()) {
                empty = true;
                break;
            }
        }
        if (!(hmf.passedRadioBtn.isSelected() | hmf.failedRadioBtn.isSelected()))
            empty = true;
        if(empty) {
            JOptionPane.showMessageDialog(null, "Error: Fill empty fields", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            Date d = sdf.parse(hmf.examineDriDateTf.getText());
            long ms = d.getTime();
            java.sql.Date sqlD = new java.sql.Date(ms);

            final String dbUser = "root";
            final String dbUrl = "jdbc:mysql://localhost:3306/dlms";
            final String dbPass = "@Emansam2938";

            Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbPass);
            String sql = "INSERT INTO Hospital_Driver_2 VALUES (?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, String.valueOf(hospital.getId()));
            preparedStatement.setString(2, hmf.examineDriDriIdTf.getText());
            preparedStatement.setDate(3, sqlD);

            String pf = hmf.passedRadioBtn.isSelected() ? "P":"F";

            preparedStatement.setString(4, pf);
            preparedStatement.setString(5, hmf.examineDriBloodTf.getText());

            //to get the program of the examined student
            PreparedStatement driProStatement = connection.prepareStatement("SELECT * FROM Driver_Program WHERE Did = " +
                                    hmf.examineDriDriIdTf.getText());
            ResultSet rst = driProStatement.executeQuery();
            rst.next();

            //this list will store every courses that are registered under a program
            ArrayList<String> courseId = courses(rst.getString("Pid"));

            PreparedStatement traDriStatement = connection.prepareStatement("INSERT INTO Trainer_Driver_2 VALUES (?, ?, ?, ?, ?)");
            PreparedStatement driActivationStatement = connection.prepareStatement("UPDATE Driver SET active = 1 WHERE Did = ?");
            driActivationStatement.setString(1, hmf.examineDriDriIdTf.getText());

            if(pf.equals("P")) {
                for(String i : courseId) {
                    ArrayList<String> teacherId = teachers(i);
                    Random randomTeacher = new Random();
                    randomTeacher.setSeed(randomTeacher.nextLong());

                    // randomly choosing a trainer id form teacher id list
                    String randtrainer = teacherId.get(randomTeacher.nextInt(teacherId.size()));

                    traDriStatement.setString(1, randtrainer);
                    traDriStatement.setString(2, hmf.examineDriDriIdTf.getText());
                    traDriStatement.setString(3, i);
                    traDriStatement.setString(4, null);
                    traDriStatement.setString(5, null);
                    traDriStatement.executeUpdate();
                }

                driActivationStatement.executeUpdate();

            }
            preparedStatement.executeUpdate();
            JOptionPane.showMessageDialog(null, "Successful!");

            } catch (Exception er) {
                JOptionPane.showMessageDialog(null, "Error: Database Error", "Error", JOptionPane.ERROR_MESSAGE);
                er.printStackTrace();
            }
        }

    //adds every course under a program to the courseId list
    ArrayList<String> courses(String Pid) throws Exception {
        ArrayList<String> courseId = new ArrayList<>();

        final String dbUser = "root";
        final String dbUrl = "jdbc:mysql://localhost:3306/dlms";
        final String dbPass = "@Emansam2938";

        Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbPass);
        Statement st = connection.createStatement();
        ResultSet rs = st.executeQuery("SELECT Cid FROM Program_Course WHERE Pid = " + Pid);

        while (rs.next()) {
            courseId.add(rs.getString("Cid"));
        }

        return courseId;
    }

    //adds every teacher assigned to a course to the teachersID list
    ArrayList<String> teachers(String Cid) throws Exception {
        ArrayList<String> teacherId = new ArrayList<>();

        final String dbUser = "root";
        final String dbUrl = "jdbc:mysql://localhost:3306/dlms";
        final String dbPass = "@Emansam2938";

        Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbPass);
        Statement st = connection.createStatement();
        ResultSet rs = st.executeQuery("SELECT Tid FROM Trainer_Course WHERE Cid = " + Cid);

        while (rs.next()) {
            teacherId.add(rs.getString("Tid"));
        }

        return teacherId;
    }

}
