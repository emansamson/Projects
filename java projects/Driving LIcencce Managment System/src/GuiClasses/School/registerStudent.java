package GuiClasses.School;

import CoreClasses.School;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;

public class registerStudent {
    registerStudent(schoolMainFrame smf, School school) {

        boolean empty = false;
        for(int i = 0; i < smf.nonEmptyFields.length; i++) {
            if(smf.nonEmptyFields[i].getText().trim().isEmpty()) {
                empty = true;
                break;
            }
        }
        if(smf.registerDriBirthTf.getText().equals("dd-MM-yyyy"))
            empty = true;
        if(empty || !(smf.registerDriMaleBtn.isSelected() || smf.registerDriFemaleBtn.isSelected())) {
            JOptionPane.showMessageDialog(null, "Error: There is an empty field", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            Date birthDay = sdf.parse(smf.registerDriBirthTf.getText());
            long ms = birthDay.getTime();
            java.sql.Date sqDob = new java.sql.Date(ms);

            Date today = sdf.parse(smf.registerDriStartTf.getText());
            ms = today.getTime();
            java.sql.Date sqlToday = new java.sql.Date(ms);

            String phone = smf.registerDriPhoneTf.getText();

            // phone number format matching using regular expression
            if (!(phone.matches("^[0-9]*$") & phone.length() == 10)) {
                JOptionPane.showMessageDialog(null, "Error: Incorrect phone format", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (today.getYear() - birthDay.getYear() <= 18) {
                JOptionPane.showMessageDialog(null, "Error: Underage! unable to register!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            final String dbUser = "root";
            final String dbUrl = "jdbc:mysql://localhost:3306/dlms";
            final String dbPass = "@Emansam2938";

            // adding record to Driver table
            String maxIdQuery = "SELECT max(Did) FROM Driver";
            Connection maxIdConnection = DriverManager.getConnection(dbUrl, dbUser, dbPass);
            PreparedStatement prepareStatementMaxId = maxIdConnection.prepareStatement(maxIdQuery);
            ResultSet rsMaxId = prepareStatementMaxId.executeQuery();

            if (rsMaxId.next()) {
                int maxId;
                // a case for the first student to be registered
                if (rsMaxId.getString("max(Did)") == null) {
                    maxId = 1;
                } else {
                    maxId = Integer.parseInt(rsMaxId.getString("max(Did)")) + 1;
                    smf.registerDriIdTf.setText(String.valueOf(maxId));
                }
            }

            String sql = "INSERT INTO Driver(Did, FName, LName, DOB, Sex, Phone, Email, Address, Pass) " +
                            "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
            Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbPass);
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, smf.registerDriIdTf.getText());
            preparedStatement.setString(2, smf.registerDriFNameTf.getText());
            preparedStatement.setString(3, smf.registerDriLNameTf.getText());
            String sex = smf.registerDriMaleBtn.isSelected() ? "M" : "F";
            preparedStatement.setString(5, sex);
            preparedStatement.setDate(4, sqDob);
            preparedStatement.setString(6, phone);
            preparedStatement.setString(7, smf.registerDriEmailTf.getText());
            preparedStatement.setString(8, smf.registerDriAddressTf.getText());
            preparedStatement.setString(9, smf.registerDriPassTf.getText());
            preparedStatement.executeUpdate();

            // populating School_Driver table
            PreparedStatement prepareStatementSchDri = connection.prepareStatement("INSERT INTO School_Driver VALUES(?, ?, ?, ?)");
            prepareStatementSchDri.setInt(1, school.getId());
            prepareStatementSchDri.setString(2, smf.registerDriIdTf.getText());
            prepareStatementSchDri.setDate(3, sqlToday);
            prepareStatementSchDri.setDate(4, null);
            prepareStatementSchDri.executeUpdate();

            // populating Driver_Program
            PreparedStatement prepareStatementDriPro = connection.prepareStatement("INSERT INTO Driver_Program values(?, ?)");
            prepareStatementDriPro.setString(1, smf.registerDriIdTf.getText());
            prepareStatementDriPro.setString(2, readProgram(smf.programList));
            prepareStatementDriPro.executeUpdate();

            JOptionPane.showMessageDialog(null, "Registered successfully!");
        } catch (Exception er) {
            JOptionPane.showMessageDialog(null, "Error: Database error ", "Error", JOptionPane.ERROR_MESSAGE);
            er.printStackTrace();
        }
    }
    String readProgram(JComboBox programList) {
        String programId = null;
        switch(programList.getSelectedIndex()) {
            case 0:
                programId = "1";
                break;
            case 1:
                programId = "2";
                break;
            case 2:
                programId = "3";
                break;
            case 3:
                programId = "4";
                break;
        }
        return programId;
    }
}

