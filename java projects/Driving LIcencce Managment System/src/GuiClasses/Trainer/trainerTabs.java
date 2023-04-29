package GuiClasses.Trainer;

import javax.swing.*;

public class trainerTabs {

    public trainerTabs(trainerMainFrame tmf) {
        if (tmf.traTab.getSelectedIndex() == 3) {
            int option = JOptionPane.showConfirmDialog(null, "Are you sure you want logout?", "LogOut", JOptionPane.YES_NO_OPTION);
            if (option == JOptionPane.YES_OPTION) {
                tmf.dispose();
                trainerLogIn dl = new trainerLogIn(null);
            } else {
                tmf.traTab.setSelectedIndex(0);
            }
        }
    }
}
