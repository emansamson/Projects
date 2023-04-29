package GuiClasses.Office;

import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import javax.swing.text.StyleContext;
import java.awt.*;
import java.util.Locale;

public class officeLogIn extends JFrame {
    private JPanel officeLogInPanel;
    private JPanel officeMessagePanel;
    private JPanel officeInputPanel;
    private JLabel officeLbl;
    private JLabel officeIcon;
    private JTextField officeIdTf;
    private JPasswordField officePassTf;
    private JButton officeLogInBtn;
    private JButton officeCancelBtn;
    private JLabel officeIDLbl;
    private JLabel officePassLbl;
    private JPanel officeFooterPanel;

    public officeLogIn(JFrame parent) {
        super();
        setTitle("Office Login");
        setContentPane(officeLogInPanel);
        setMinimumSize(new Dimension(450, 450));
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);
    }

    public static void main(String[] args) {
        officeLogIn ol = new officeLogIn(null);
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }

}
