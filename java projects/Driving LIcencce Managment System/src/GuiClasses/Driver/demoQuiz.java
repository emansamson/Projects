package GuiClasses.Driver;

import CoreClasses.Question;

import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class demoQuiz {
    ArrayList<Question> questions = readFile();
    int index = 0;
    demoQuiz(driverMainFrame dmf) {
        updateQuestion(dmf, index);
    }
    void updateQuestion(driverMainFrame dmf, int index) {
        dmf.question.setText(questions.get(index).getQuestion());
        dmf.choiseA.setText(questions.get(index).getChoiceA());
        dmf.choiceB.setText(questions.get(index).getChoiceB());
        dmf.choiseC.setText(questions.get(index).getChoiceC());
    }

    void demoPage(driverMainFrame dmf) {
        if (dmf.checkButton.getText().equals("Continue")) {
            this.index++;
            if(index == questions.size()) {
                dmf.checkLabel.setText("Finished");
                dmf.progressBar.setValue((index*100) / questions.size());
                return;
            }
            dmf.checkButton.setText("Check");
            dmf.checkLabel.setVisible(false);
            dmf.progressBar.setValue((index*100) / questions.size());
            updateQuestion(dmf, index);
            return;
        }
        if(ourChoice(dmf).equals(questions.get(index).getAnswer())){
            dmf.checkLabel.setText("Correct!");
            dmf.checkLabel.setForeground(Color.GREEN);
        }
        else {
            dmf.checkLabel.setText("Wrong the answer is "+questions.get(index).getAnswer());
            dmf.checkLabel.setForeground(Color.red);
        }
        dmf.checkLabel.setVisible(true);
        dmf.checkButton.setText("Continue");
    }

    String ourChoice(driverMainFrame dmf) {
        if(dmf.choiseA.isSelected() )
            return "A";
        else if(dmf.choiceB.isSelected())
            return "B";
        else if(dmf.choiseC.isSelected())
            return "C";
        return "";
    }
    ArrayList<Question> readFile() {
        File file = new File("LicenseQuestion.txt");
        ArrayList<Question> questions = new ArrayList<>();
        try {
            Scanner reader = new Scanner(file);
            while(reader.hasNext()) {
                Question instance = new Question();
                instance.setQuestion(reader.nextLine());
                instance.setChoiceA(reader.nextLine());
                instance.setChoiceB(reader.nextLine());
                instance.setChoiceC(reader.nextLine());
                instance.setAnswer(reader.nextLine());
                questions.add(instance);
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        return questions;
    }
    public static void main(String []args) {

    }
}
