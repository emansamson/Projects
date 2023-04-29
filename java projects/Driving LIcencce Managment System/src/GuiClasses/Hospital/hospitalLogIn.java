package GuiClasses.Hospital;

import CoreClasses.Hospital;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class hospitalLogIn extends JFrame {
    private JPanel hospitalLogInPnl;
    private JPanel hospitalMessagePnl;
    private JPanel hospitalInputPnl;
    private JLabel hospitalLbl;
    private JLabel hospitalIcon;
    private JTextField hospitalIdTf;
    private JPasswordField hospitalPf;
    private JButton hospitalLogInBtn;
    private JButton hospitalCancelBtn;
    private JLabel hospitalIDLbl;
    private JLabel hospitalPassLbl;
    private JPanel hospitalFooterPanel;

    public hospitalLogIn(JFrame parent) {
        super();
        setTitle("Hospital Login Portal");
        setContentPane(hospitalLogInPnl);
        setMinimumSize(new Dimension(450,450));
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        hospitalLogInBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String id = hospitalIdTf.getText();
                String password = String.valueOf(hospitalPf.getPassword());
                hosp = getValidatedHosp(id, password);

                if(hosp != null) {
                    dispose();
//                    hospitalMainFrame hmf = new hospitalMainFrame(null);
//                    hmf.boot(hosp);
                    hospitalMainFrame.boot(hosp);

                    // printing hospital attributes for debugging
                    System.out.println("Successful authentication of hospital: " + hosp.getName());
                    System.out.println("            " + hosp.getId());
                    System.out.println("            " + hosp.getPhone());
                    System.out.println("            " + hosp.getAddress());
                    System.out.println("            " + hosp.getEmail());
                }
                else {
                    JOptionPane.showMessageDialog(hospitalLogIn.this,
                            "Id or Password invalid", "error",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        hospitalCancelBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                hospitalIdTf.setText("");
                hospitalPf.setText("");
            }
        });
        setVisible(true);
    }

    public Hospital hosp;
    private Hospital getValidatedHosp(String id, String password) {
        Hospital hosp = null;

        final String dbUser = "root";
        final String dbUrl = "jdbc:mysql://localhost:3306/dlms";
        final String dbPass = "@Emansam2938";

        try {
            Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbPass);
            Statement statement = connection.createStatement();
            String query = "SELECT * FROM Hospital WHERE Hid=? AND Pass=? ";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, id);
            preparedStatement.setString(2, password);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                hosp = new Hospital();
                hosp.setId(resultSet.getInt("Hid"));
                hosp.setName(resultSet.getString("Name"));
                hosp.setPhone(resultSet.getString("Phone"));
                hosp.setEmail(resultSet.getString("Email"));
                hosp.setAddress(resultSet.getString("Address"));
                hosp.setPass(resultSet.getString("Pass"));
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return hosp;
    }

    public static void main(String[] args) throws UnsupportedLookAndFeelException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");

        hospitalLogIn hl = new hospitalLogIn(null);
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }

}

