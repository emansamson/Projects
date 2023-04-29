package GuiClasses.Driver;

import CoreClasses.Driver;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class driverLogIn extends JFrame {
    private JPanel driverLogInPanel;
    private JPanel driverMessagePanel;
    private JPanel driverInputPanel;
    private JTextField driverIdTf;
    private JPasswordField driverPf;
    private JButton driverLoginBtn;
    private JButton driverCancelBtn;
    private JPanel footerPanel;
    private JLabel driverLbl;
    private JLabel logInLbl;
    private JLabel driverIcon;
    private JLabel driverFooterLbl;
    private JLabel driverIdLbl;
    private JLabel driverPassLbl;

    public driverLogIn(JFrame parent) {
        super();
        setTitle("Diver Login");
        setContentPane(driverLogInPanel);
        setMinimumSize(new Dimension(450, 450));
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        driverLoginBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String id = driverIdTf.getText();
                String password = String.valueOf(driverPf.getPassword());
                dri = getValidatedDri(id, password);

                if(dri != null) {
                    dispose();
                    driverMainFrame dmf = new driverMainFrame(null);
                    dmf.boot(dri);

                    // printing driver credentials for debugging
                    System.out.println("Successful authentication of driver: " +
                                        dri.getfName() + dri.getlName());
                    System.out.println("            " + dri.getId());
                    System.out.println("            " + dri.getPhone());
                    System.out.println("            " + dri.getAddress());
                    System.out.println("            " + dri.getDOB());
                }

            }
        });
        driverCancelBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                driverIdTf.setText("");
                driverPf.setText("");

            }
        });
        setVisible(true);
    }
    public Driver dri;
    private Driver getValidatedDri(String id, String password) {
        Driver dri = null;

        final String dbUser = "root";
        final String dbUrl = "jdbc:mysql://localhost:3306/dlms";
        final String dbPass = "@Emansam2938";

        try {
            Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbPass);
            Statement statement = connection.createStatement();
            String query = "SELECT * FROM Driver WHERE Did=? AND Pass=? ";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, id);
            preparedStatement.setString(2, password);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                if(resultSet.getString("active").equals("1")) {
                    dri = new Driver();
                    dri.setId(resultSet.getInt("Did"));
                    dri.setfName(resultSet.getString("FName"));
                    dri.setlName(resultSet.getString("LName"));
                    dri.setDOB(resultSet.getString("DOB"));
                    dri.setSex(resultSet.getString("Sex"));
                    dri.setPhone(resultSet.getString("Phone"));
                    dri.setEmail(resultSet.getString("Email"));
                    dri.setAddress(resultSet.getString("Address"));
                    dri.setPass(resultSet.getString("Pass"));
                } else {
                    String message = "\nPlease take the medical examination to activate your account!";
                    JOptionPane.showMessageDialog(null, "Your account is not activated yet!"+message, "Notice", JOptionPane.INFORMATION_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(driverLogIn.this,
                        "Error: Invalid Id or Password", "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
            statement.close();
            connection.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return dri;
    }

    public static void main(String[] args) throws UnsupportedLookAndFeelException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");

        driverLogIn dl = new driverLogIn(null);
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }

}