package GuiClasses.School;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class promotionStatus {
    promotionStatus(String DriverId) {
        final String dbUser = "root";
        final String dbUrl = "jdbc:mysql://localhost:3306/dlms";
        final String dbPass = "@Emansam2938";
        try {
            String sql = "select Grade from trainer_driver_2 where Did = ?";
            Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbPass);
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, DriverId);
            ResultSet rs = preparedStatement.executeQuery();
            float grade = 0;
            int numberOfcourse = 0;
            boolean 
                    promot = true;
            while (rs.next()) {
                if(rs.getString("Grade") == null) {
                    promot = false;
                    break;
                }

                grade += rs.getFloat("Grade");
                numberOfcourse++;
            }
            float average = grade/numberOfcourse;
            String Status = "update Driver set promotion_status = ? where Did = ?";
            if(average > 50.0) {
                PreparedStatement promotion = connection.prepareStatement(Status);
                promotion.setBoolean(1, promot);
                promotion.setString(2, DriverId);
                promotion.executeUpdate();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
