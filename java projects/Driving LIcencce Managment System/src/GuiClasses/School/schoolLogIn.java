package GuiClasses.School;

import CoreClasses.Hospital;
import CoreClasses.School;
import GuiClasses.Hospital.hospitalLogIn;
import GuiClasses.Hospital.hospitalMainFrame;

import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import javax.swing.text.StyleContext;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.Locale;

public class schoolLogIn extends JFrame {
    private JPanel schoolLogInPanel;
    private JPanel schoolMessagePanel;
    private JPanel schoolInputPanel;
    private JTextField schoolIdTf;
    private JPasswordField schoolPf;
    private JButton schoolLoginBtn;
    private JButton schoolCancelBtn;
    private JPanel footerPanel;
    private JLabel schoolLbl;
    private JLabel logInLbl;
    private JLabel schoolIcon;
    private JLabel schoolFooterLbl;
    private JLabel schoolIdLbl;
    private JLabel schoolPassLbl;

    public schoolLogIn(JFrame parent) {
        super();
        setTitle("School Login");
        setContentPane(schoolLogInPanel);
        setMinimumSize(new Dimension(450, 450));
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        schoolLoginBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String id = schoolIdTf.getText();
                String password = String.valueOf(schoolPf.getPassword());
                sch = getValidatedHosp(id, password);

                if(sch != null) {
                    dispose();
                    schoolMainFrame.boot(sch);

                    // just printing school credentials for debugging
                    System.out.println("Successful authentication of school: " + sch.getName());
                    System.out.println("            " + sch.getId());
                    System.out.println("            " + sch.getPhone());
                    System.out.println("            " + sch.getAddress());
                    System.out.println("            " + sch.getEmail());
                }
                else {
                    JOptionPane.showMessageDialog(schoolLogIn.this,
                            "Id or Password invalid", "error",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        schoolCancelBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                schoolIdTf.setText("");
                schoolPf.setText("");
            }
        });
        setVisible(true);
    }

    public School sch;
    private School getValidatedHosp(String id, String password) {
        School sch = null;

        final String dbUser = "root";
        final String dbUrl = "jdbc:mysql://localhost:3306/dlms";
        final String dbPass = "@Emansam2938";

        try {
            Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbPass);
            Statement statement = connection.createStatement();
            String query = "SELECT * FROM School WHERE Sid=? AND Pass=? ";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, id);
            preparedStatement.setString(2, password);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                sch = new School();
                sch.setId(resultSet.getInt("Sid"));
                sch.setName(resultSet.getString("Name"));
                sch.setPhone(resultSet.getString("Phone"));
                sch.setEmail(resultSet.getString("Email"));
                sch.setAddress(resultSet.getString("Address"));
                sch.setPass(resultSet.getString("Pass"));
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return sch;
    }

    public static void main(String[] args) throws UnsupportedLookAndFeelException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        schoolLogIn sl = new schoolLogIn(null);
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }

}
