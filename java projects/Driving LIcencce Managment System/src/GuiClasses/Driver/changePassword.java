package GuiClasses.Driver;

import CoreClasses.Driver;
import CoreClasses.Entities;

import javax.swing.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class changePassword {
    changePassword(driverMainFrame dmf, Driver driver) {
        if(dmf.driNewPassTf.getText().isEmpty() || dmf.driConfirmPassTf.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "error ", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if(!(dmf.driNewPassTf.getText().equals(dmf.driConfirmPassTf.getText()))) {
            JOptionPane.showMessageDialog(null, "error ", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        String sql = "UPDATE Driver SET Pass = ? WHERE Did = ?";
        final String dbUser = "root";
        final String dbUrl = "jdbc:mysql://localhost:3306/dlms";
        final String dbPass = "@Emansam2938";
        try {
            Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbPass);
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, dmf.driNewPassTf.getText());
            preparedStatement.setString(2, String.valueOf(driver.getId()));
            preparedStatement.executeUpdate();
            JOptionPane.showMessageDialog(null, "Password successfully changed");
        } catch (SQLException er) {
            JOptionPane.showMessageDialog(null, "Error: Database Error", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
