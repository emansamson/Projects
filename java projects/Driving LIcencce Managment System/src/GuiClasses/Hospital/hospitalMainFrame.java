package GuiClasses.Hospital;

import CoreClasses.Hospital;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.*;

public class hospitalMainFrame extends JFrame {
    private JPanel hosMainPnl;
    JTabbedPane hosTab;
    private JButton submitBtn;
    private JButton clearBtn;
    private JTextField examineDriHosIdTf;
    JTextField examineDriDriIdTf;
    JTextField examineDriDateTf;
    JTextField examineDriBloodTf;
    JRadioButton passedRadioBtn;
    JRadioButton failedRadioBtn;
    private JPanel hosExamineDriPnl;
    private JPanel hosDriExamInfoPnl;
    private JPanel hosLogoutPnl;
    JTable driHospTable;
    Connection connection = null;
    PreparedStatement preparedStatement = null;

    private ButtonGroup bg = new ButtonGroup();
    JTextField[] nonEmptyFields = {examineDriDriIdTf, examineDriDateTf, examineDriBloodTf};
    public hospitalMainFrame(JFrame parent) {
        super();
        bg.add(passedRadioBtn);
        bg.add(failedRadioBtn);
        setTitle("Hospital Main Frame");
        setContentPane(hosMainPnl);
        setMinimumSize(new Dimension(500, 500));
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);

        clearBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                for (JTextField i : nonEmptyFields) {
                    i.setText("");
                }
            }
        });

    }
    static Hospital hosp;
    public static void boot(Hospital hospital) {
        hosp = hospital;
        hospitalMainFrame hmf = new hospitalMainFrame(null);
        hmf.submitBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                new registerbutton(hmf, hosp);
            }
        });

        hmf.hosTab.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                new hospitalTabs(hmf, hosp);
            }
        });
    }
    public static void main(String[] args) {boot(hosp);}
}
