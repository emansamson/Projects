import javax.swing.*;
import java.awt.*;

public class test extends JFrame {
    private JComboBox comboBox1;
    private JPanel panel1;
    private JLabel label1;

    public test(JFrame parent) {
        super();
        setTitle("Trainer Login");
        setContentPane(panel1);
        setMinimumSize(new Dimension(450, 450));
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);
    }

    public static void main(String[] args) {
        test t = new test(null);
        while (true) {
            t.label1.setText((String) t.comboBox1.getSelectedItem());
        }


//        switch (t.comboBox1.getSelectedIndex()) {
//            case 0:
//                t.label1.setText((String) t.comboBox1.getSelectedItem());
//            case 1:
//                t.label1.setText((String) t.comboBox1.getItemAt(1));
//            case 2:
//                t.label1.setText((String) t.comboBox1.getItemAt(2));
//        }
    }

}
