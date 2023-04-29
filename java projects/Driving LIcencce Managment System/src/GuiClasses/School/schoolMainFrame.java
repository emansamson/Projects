package GuiClasses.School;

import CoreClasses.School;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.Vector;

public class schoolMainFrame extends JFrame {
    private JPanel schMainPnl;
    JTabbedPane schTab;
    private JPanel schRegisterDriPnl;
    private JPanel schDriInfoPnl;
    private JPanel schChangeDriInfoPnl;
    private JPanel schChangePassPnl;
    private JPanel schLogoutPnl;
    JTextField registerDriLNameTf;
    JTextField registerDriBirthTf;
    JTextField registerDriPhoneTf;
    JTextField registerDriEmailTf;
    JTextField registerDriAddressTf;
    JTextField registerDriPassTf;
    private JButton registerDriBtn;
    private JButton registerDriClearBtn;
    JRadioButton registerDriMaleBtn;
    JRadioButton registerDriFemaleBtn;
    JTextField registerDriIdTf;
    JTextField registerDriFNameTf;
    private JButton confirmChangesButton;
    private JTextField oldPass;
    private JTextField newPass;
    private JTextField confirmPass;
    JTextField registerDriStartTf;
    JTextField registerDriProgramTf;
    JTextField changeInfoIdTf;
    JTextField changeInfoFNameTf;
    JTextField changeInfoLNameTf;
    JTextField changeInfoBirthTf;
    JTextField changeInfoPhoneTf;
    JTextField changeInfoEmailTf;
    JTextField changeInfoAddressTf;
    JTextField changeInfoEndTf;
    JTextField changeInfoPassTf;
    JButton searchButton;
    JButton updateButton;
    JButton clearButton;
    JRadioButton changeInfomaleRadioBtn;
    JRadioButton changeInfofemaleRadioBtn;
    private JButton generateP;
    JComboBox programList;
    JTable driInfoTable;
    private JTextField infoSearchBar;
    private JButton infoRefreshBtn;
    private JComboBox searchCombo;
    static School sc = new School();
    ButtonGroup bg = new ButtonGroup();

    JTextField[] nonEmptyFields = {registerDriAddressTf, registerDriBirthTf, registerDriEmailTf,
                                    registerDriFNameTf, registerDriPhoneTf, registerDriPassTf,
                                    registerDriLNameTf, registerDriStartTf};
    public schoolMainFrame(JFrame parent) {
        super();
        bg.add(registerDriMaleBtn);
        bg.add(registerDriFemaleBtn);
        setTitle("School Main Frame");
        setContentPane(schMainPnl);
        setMinimumSize(new Dimension(711, 700));
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);

        Date today = new Date();
        SimpleDateFormat sdf1 = new SimpleDateFormat("dd-MM-yyyy");
        String StartDate = sdf1.format(today);
        registerDriStartTf.setText(StartDate);

        // listening to change password button
        confirmChangesButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                new changePassword(confirmPass, newPass, sc);
            }
        });

        // clearing fields in register driver tab
        registerDriClearBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                for (int i = 0; i < nonEmptyFields.length; i++) {
                    nonEmptyFields[i].setText("");
                }
                registerDriIdTf.setText("");
                registerDriBirthTf.setForeground(new Color(187, 187, 187));
                registerDriBirthTf.setText("dd-MM-yyyy");
                registerDriStartTf.setText(StartDate);
            }
        });

        // generating a 4-digit password for initial registration the driver can change it later
        generateP.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                Random r = new Random();
                String st = "";
                for(int i = 0; i < 4; i++) {
                    r.setSeed(r.nextLong());
                    st += r.nextInt(10);
                }
                registerDriPassTf.setText(st);
            }
        });

        registerDriBirthTf.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                super.focusGained(e);
                if(registerDriBirthTf.getText().equals("dd-MM-yyyy")) {
                    registerDriBirthTf.setText("");
                    registerDriBirthTf.setForeground(Color.black);
                }

            }
        });

        oldPass.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                super.keyTyped(e);
                if(oldPass.getText().equals(sc.getPass())) {
                    newPass.setEnabled(true);
                    confirmPass.setEnabled(true);
                    confirmChangesButton.setEnabled(true);
                }
            }
        });

    }

    public static void boot(School sch) {
        sc = sch;
        schoolMainFrame smf = new schoolMainFrame(null);

        smf.registerDriBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                new registerStudent(smf, sc);
            }
        });

        // listening to a mouse click on the tab
        smf.schTab.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                new schoolTabs(smf, sch);
            }
        });

        infoTable tableObject = new infoTable(smf.driInfoTable, sc);

        //the search will be performed from this nested vector
        Vector<Vector> dataVector = tableObject.prepareVector(sc);
        smf.infoSearchBar.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e);
                Integer j = null;
                switch (smf.searchCombo.getSelectedIndex()) {
                    case 0:
                        j = 0;
                        break;
                    case 1:
                        j = 1;
                        break;
                    case 2:
                        j = 3;
                        break;
                    case 3:
                        j = 4;
                        break;
                    case 4:
                        j = 8;
                        break;
                    case 5:
                        j = 7;
                        break;
                    case 6:
                        j = 11;
                }
                tableObject.searchTable(smf.infoSearchBar, dataVector, smf.driInfoTable, j);
            }
        });

        smf.infoRefreshBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new infoTable(smf.driInfoTable, sc);
                smf.infoSearchBar.setText("");
            }
        });
    }
}
