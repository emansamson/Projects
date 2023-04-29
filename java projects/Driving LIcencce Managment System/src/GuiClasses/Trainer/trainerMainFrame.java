package GuiClasses.Trainer;

import CoreClasses.Trainer;
import GuiClasses.School.infoTable;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.Vector;

public class trainerMainFrame extends JFrame {
    private JPanel traMainPnl;
    public JTabbedPane traTab;
    private JPanel traSubmitGradePnl;
    private JPanel traLogoutPnl;
    private JButton submitButton;
    private JButton clearButton;
    JTextField subGradeDriIdTf;
    JTextField subGradeCourseIdTf;
    JTextField subGradeGradeTf;
    JTextField subGradeDateTf;
    private JPanel traRosterPnl;
    private JButton confirmChangesButton;
    private JTextField oldPassTf;
    JTextField newPassTf;
    JTextField confirmPassTf;
    private JPanel logout;
    private JTable rosterTable;
    private JTextField traTableSearchBar;
    private JButton traRefresh;
    private JComboBox traSearchCombo;
    JTextField[] nonEmptyFields = {subGradeDriIdTf, subGradeCourseIdTf, subGradeGradeTf, subGradeDateTf};

    DefaultTableModel model = new DefaultTableModel();
    public trainerMainFrame(JFrame parent) {
        super();
        setTitle("Trainer Main Frame");
        setContentPane(traMainPnl);
        setMinimumSize(new Dimension(711, 640));
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);
        prepareTable tableObject = new prepareTable(rosterTable, tr);

        oldPassTf.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                super.keyTyped(e);
                if(oldPassTf.getText().equals(tr.getPass())) {
                    newPassTf.setEnabled(true);
                    confirmPassTf.setEnabled(true);
                    confirmChangesButton.setEnabled(true);
                }
            }
        });

        clearButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                for(JTextField i : nonEmptyFields) {
                    i.setText("");
                }
                subGradeDateTf.setText("dd-MM-yyyy");
            }
        });


        traRefresh.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new prepareTable(rosterTable, tr);
                traTableSearchBar.setText("");
            }
        });

        confirmChangesButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                new changePassword(newPassTf, confirmPassTf);
            }
        });

        subGradeDateTf.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                super.focusGained(e);
                if(subGradeDateTf.getText().equals("dd-MM-yyyy")) {
                    subGradeDateTf.setText("");
                    subGradeDateTf.setForeground(Color.black);
                }
            }
        });
    }
    static Trainer tr = new Trainer();
    public static void boot(Trainer tra) {
        tr = tra;
        trainerMainFrame tmf = new trainerMainFrame(null);
        tmf.traTab.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                new trainerTabs(tmf);
            }
        });
        tmf.submitButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                new SubmitGrade(tmf, tr);
            }
        });

        prepareTable traTableObject = new prepareTable(tmf.rosterTable, tra);

        //the search will be performed from this nested vector
        Vector<Vector> dataVector = traTableObject.prepareVector(tra);
        tmf.traTableSearchBar.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e);
                Integer j = null;
                switch (tmf.traSearchCombo.getSelectedIndex()) {
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
                        j = 5;
                        break;
                }
                traTableObject.searchTable(tmf.traTableSearchBar, dataVector, tmf.rosterTable, j);
            }
        });
    }
    public static void main(String[] args) {
        boot(tr);
    }
}