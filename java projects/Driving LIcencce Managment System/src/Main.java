import java.sql.*;

public class Main {
    public static void main(String[] args) {
        try {
            String dburl = "jdbc:mysql://localhost:3306/test";
            String username = "root";
            String password = "admas121921";

            String maxIdQuery = "select max(id) from person";
            Connection maxIdConnection = DriverManager.getConnection(dburl, username, password);
            PreparedStatement prepareStatementMaxId = maxIdConnection.prepareStatement(maxIdQuery);
            ResultSet rsMaxId = prepareStatementMaxId.executeQuery();

            if (rsMaxId.next()) {
                int maxId;
                if (rsMaxId.getString("max(id)") == null) {
                    maxId = 1;
                } else {
                    maxId = Integer.parseInt(rsMaxId.getString("max(id)")) + 1;
                }
                System.out.println(maxId);
            }
        } catch (SQLException e) {
            System.out.println("Connection Failed!!!");
            e.printStackTrace();
        }
    }
}