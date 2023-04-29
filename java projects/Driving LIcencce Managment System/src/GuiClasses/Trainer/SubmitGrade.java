package GuiClasses.Trainer;

import CoreClasses.Trainer;

import javax.swing.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SubmitGrade {
    SubmitGrade(trainerMainFrame tmf, Trainer trainer) {
        {
            boolean empty = false;
            for(JTextField i : tmf.nonEmptyFields) {
                if(i.getText().trim().isEmpty()) {
                    empty = true;
                    break;
                }
            }
            if(empty){
                JOptionPane.showMessageDialog(null, "Error: Fill empty fields", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
                Date d = sdf.parse(tmf.subGradeDateTf.getText());
                long ms = d.getTime();
                java.sql.Date sqlD = new java.sql.Date(ms);

                String sql = "UPDATE Trainer_Driver_2 SET Grade=?, Training_Date=? WHERE Did=? AND Cid=? AND Tid=?";
                final String dbUser = "root";
                final String dbUrl = "jdbc:mysql://localhost:3306/dlms";
                final String dbPass = "@Emansam2938";

                Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbPass);
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(5, String.valueOf(trainer.getId()));
                preparedStatement.setString(3, String.valueOf(tmf.subGradeDriIdTf.getText()));
                preparedStatement.setString(4, tmf.subGradeCourseIdTf.getText());
                preparedStatement.setString(1, tmf.subGradeGradeTf.getText());
                preparedStatement.setDate(2, sqlD);
                preparedStatement.executeUpdate();
                JOptionPane.showMessageDialog(null, "Successful!");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Error: Database error", "Error", JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            }
        }
    }
}
