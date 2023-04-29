package GuiClasses.Trainer;

import javax.swing.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static GuiClasses.Trainer.trainerMainFrame.tr;

public class changePassword {
    changePassword(JTextField newPassTf, JTextField confirmPassTf) {
        if(newPassTf.getText().isEmpty() || confirmPassTf.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Error: Fill empty fields", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!newPassTf.getText().equals(confirmPassTf.getText())) {
            JOptionPane.showMessageDialog(null, "Error: Passwords do not match", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        String sql = "UPDATE Trainer SET Pass =? WHERE Tid = ?";
        final String dbUser = "root";
        final String dbUrl = "jdbc:mysql://localhost:3306/dlms";
        final String dbPass = "@Emansam2938";

        try {
            Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbPass);
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, newPassTf.getText());
            preparedStatement.setString(2, String.valueOf(tr.getId()));
            preparedStatement.executeUpdate();
            JOptionPane.showMessageDialog(null, "Password successfully changed");
        } catch (SQLException er) {
            JOptionPane.showMessageDialog(null, "Error: Database error", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
