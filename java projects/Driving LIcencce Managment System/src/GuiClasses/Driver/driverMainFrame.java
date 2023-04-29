package GuiClasses.Driver;

import CoreClasses.Driver;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;

public class driverMainFrame extends JFrame {
    JTabbedPane driTab;
    private JPanel driMainPnl;
    private JPanel driPersonalInfoPnl;
    private JButton driSaveBtn;
    private JTextField driOldPassTf;
    JTextField driNewPassTf;
    JTextField driConfirmPassTf;
    private JPanel driGradePnl;
    private JPanel driMedicalPnl;
    private JPanel driChangePassPnl;
    private JPanel driLogoutPnl;
    private JLabel driIdLbl;
    private JLabel driNameLbl;
    private JLabel driSexLbl;
    private JLabel driPhoneLbl;
    private JLabel driEmailLbl;
    private JLabel driAddressLbl;
    JTextPane question;
    JRadioButton choiseA;
    JProgressBar progressBar;
    JButton checkButton;
    JLabel checkLabel;
    JRadioButton choiceB;
    JRadioButton choiseC;
    private JPanel Demo;
    JLabel hospNameLbl;
    JLabel bloodLbl;
    JLabel statusLbl;
    JLabel examDateLbl;
    JTable gradeTable;
    JLabel promotionLabel;
    String Password;
    Driver driver = new Driver();
    public driverMainFrame(JFrame parent) {
        super();
        setTitle("Driver Main Frame");
        setContentPane(driMainPnl);
        setMinimumSize(new Dimension(700, 700));
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);
        demoQuiz quiz = new demoQuiz(this);

        driTab.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                try {
                    new driverTabs(driverMainFrame.this, driver);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        driOldPassTf.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                if(driOldPassTf.getText().equals(driver.getPass())) {
                    driNewPassTf.setEnabled(true);
                    driConfirmPassTf.setEnabled(true);
                    driSaveBtn.setEnabled(true);
                }
            }
        });

        driSaveBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                new changePassword(driverMainFrame.this, driver);
            }
        });

        checkButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                quiz.demoPage(driverMainFrame.this);
            }
        });
    }

    public void boot(Driver dri) {
//        driverMainFrame dmf = new driverMainFrame(null);
        driIdLbl.setText(String.valueOf(dri.getId()));
        driNameLbl.setText(dri.getfName() + " " + dri.getlName());
        driSexLbl.setText(dri.getSex());
        driPhoneLbl.setText(dri.getPhone());
        driEmailLbl.setText(dri.getEmail());
        driAddressLbl.setText(dri.getAddress());
        driver = dri;
    }

    public static void main(String[] args) {
            new driverMainFrame(null);
    }
}
