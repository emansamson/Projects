package GuiClasses.School;

import CoreClasses.School;
import GuiClasses.Driver.driverGradeTable;

import javax.swing.*;

public class schoolTabs {
    schoolTabs(schoolMainFrame smf, School sch) {
        if(smf.schTab.getSelectedIndex() == 4) {
            int option = JOptionPane.showConfirmDialog(null, "Are you sure you want logout ?",
                        "LogOut", JOptionPane.YES_NO_OPTION);
            if(option == JOptionPane.YES_OPTION){
                smf.dispose();
                schoolLogIn sl = new schoolLogIn(null);
            } else {
                smf.schTab.setSelectedIndex(0);
            }
        }
        else if (smf.schTab.getSelectedIndex() == 1) {
            infoTable driverTable = new infoTable(smf.driInfoTable, sch);
        } else if (smf.schTab.getSelectedIndex() == 2) {
            changeInfo changeInfo = new changeInfo(smf, sch);
        }
    }
}
