package GuiClasses.School;

import CoreClasses.School;

import javax.swing.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class changePassword {
    changePassword(JTextField confirmPass, JTextField newPass, School school) {
        if(newPass.getText().isEmpty() || confirmPass.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Error: Fill empty fields", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (!newPass.getText().equals(confirmPass.getText())) {
            JOptionPane.showMessageDialog(null, "Error: Passwords mismatch", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String sql = "UPDATE School SET Pass = ? where Sid = ?";
        final String dbUser = "root";
        final String dbUrl = "jdbc:mysql://localhost:3306/dlms";
        final String dbPass = "@Emansam2938";

        try {
            Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbPass);
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, newPass.getText());
            preparedStatement.setString(2, String.valueOf(school.getId()));
            preparedStatement.executeUpdate();
            JOptionPane.showMessageDialog(null, "Password successfully changed");
        } catch (SQLException er) {
            JOptionPane.showMessageDialog(null, "Error: Database error", "Error", JOptionPane.ERROR_MESSAGE);

        }

    }
}
