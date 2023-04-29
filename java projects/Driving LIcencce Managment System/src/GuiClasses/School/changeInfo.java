package GuiClasses.School;

import CoreClasses.School;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class changeInfo {
    public changeInfo(schoolMainFrame smf, School sch) {
        smf.searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String searchQuery = "SELECT d.FName, d.LName, d.DOB, d.Sex, d.Phone, d.Email, d.Address, d.Pass " +
                                    "FROM Driver d, School_Driver sd, School s " +
                                    "WHERE d.Did = ? AND s.Sid = ? AND d.Did = sd.Did AND s.Sid = sd.Sid";

                final String dbUser = "root";
                final String dbUrl = "jdbc:mysql://localhost:3306/dlms";
                final String dbPass = "@Emansam2938";
                try {
                    Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbPass);
                    PreparedStatement ps = connection.prepareStatement(searchQuery);
                    ps.setString(1, smf.changeInfoIdTf.getText());
                    ps.setString(2, String.valueOf(sch.getId()));
                    ResultSet rs = ps.executeQuery();

                    while (rs.next()) {
                        smf.changeInfoFNameTf.setText(rs.getString("FName"));
                        smf.changeInfoLNameTf.setText(rs.getString("LName"));
                        smf.changeInfoBirthTf.setText(rs.getString("DOB"));
                        if (rs.getString("Sex").equals("M")) {
                            smf.changeInfomaleRadioBtn.doClick();
                        } else {
                            smf.changeInfofemaleRadioBtn.doClick();
                        }
                        smf.changeInfoPhoneTf.setText(rs.getString("Phone"));
                        smf.changeInfoEmailTf.setText(rs.getString("Email"));
                        smf.changeInfoAddressTf.setText(rs.getString("Address"));
                        smf.changeInfoPassTf.setText(rs.getString("Pass"));

                    }
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "Error: Database error", "Error", JOptionPane.ERROR_MESSAGE);
                    e.printStackTrace();
                }

            }
        });

        smf.updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String updateDriQuery = "UPDATE Driver SET FName = ?, LName = ?, DOB = ?, " +
                                        "Sex = ?, Phone = ?, Email = ?, Address = ?, Pass = ?" +
                                        "WHERE Did = ?";

                String updateSchDriQuery = "UPDATE School_Driver SET End_Date = ? WHERE Did = ?";

                final String dbUser = "root";
                final String dbUrl = "jdbc:mysql://localhost:3306/dlms";
                final String dbPass = "@Emansam2938";

                try {
                    Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbPass);
                    PreparedStatement ps = connection.prepareStatement(updateDriQuery);
                    ps.setString(1, smf.changeInfoFNameTf.getText());
                    ps.setString(2, smf.changeInfoLNameTf.getText());
                    ps.setString(3, smf.changeInfoBirthTf.getText());
                    String sex = smf.changeInfomaleRadioBtn.isSelected() ? "M" : "F";
                    ps.setString(4, sex);
                    ps.setString(5, smf.changeInfoPhoneTf.getText());
                    ps.setString(6, smf.changeInfoEmailTf.getText());
                    ps.setString(7, smf.changeInfoAddressTf.getText());
                    ps.setString(8, smf.changeInfoPassTf.getText());
                    ps.setString(9, smf.changeInfoIdTf.getText());
                    ps.executeUpdate();

                    PreparedStatement ps1 = connection.prepareStatement(updateSchDriQuery);
                    if (!smf.changeInfoEndTf.getText().isEmpty()) {
                        new promotionStatus(smf.changeInfoIdTf.getText());
                    }
                    ps1.setString(1, smf.changeInfoEndTf.getText());
                    ps1.setString(2, smf.changeInfoIdTf.getText());
                    ps1.executeUpdate();

                    JOptionPane.showMessageDialog(null, "Updated successfully", "", JOptionPane.INFORMATION_MESSAGE);

                } catch (SQLException e) {
                    JOptionPane.showMessageDialog(null, "Error: Database error", "Error", JOptionPane.ERROR_MESSAGE);
                }

            }
        });

        smf.clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                smf.changeInfoIdTf.setText("");
                smf.changeInfoFNameTf.setText("");
                smf.changeInfoLNameTf.setText("");
                smf.changeInfoBirthTf.setText("");
                smf.changeInfoPhoneTf.setText("");
                smf.changeInfoEmailTf.setText("");
                smf.changeInfoAddressTf.setText("");
                smf.changeInfoEndTf.setText("");
                smf.changeInfoPassTf.setText("");
            }
        });
    }
}
