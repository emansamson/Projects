package GuiClasses.Hospital;

import CoreClasses.Hospital;

import javax.swing.*;

public class hospitalTabs {
    hospitalTabs(hospitalMainFrame hmf, Hospital hospital) {
        if(hmf.hosTab.getSelectedIndex() == 2) {
            int option = JOptionPane.showConfirmDialog(null, "Are you sure you want logout ?",
                        "LogOut", JOptionPane.YES_NO_OPTION);
            if(option == JOptionPane.YES_OPTION){
                hmf.dispose();
                hospitalLogIn hl = new hospitalLogIn(null);
            } else {
                hmf.hosTab.setSelectedIndex(0);
            }
        } else if (hmf.hosTab.getSelectedIndex() == 1) {
            examinedDriTable examTable = new examinedDriTable(hmf.driHospTable, hospital);
        }
    }
}
