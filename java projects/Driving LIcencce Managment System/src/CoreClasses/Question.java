package CoreClasses;

public class Question {
    String question;
    String ChoiceA;
    String ChoiceB;
    String ChoiceC;
    String answer;

    public void setQuestion(String question) {
        this.question = question;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public void setChoiceA(String choiceA) {
        ChoiceA = choiceA;
    }

    public void setChoiceB(String choiceB) {
        ChoiceB = choiceB;
    }

    public void setChoiceC(String choiceC) {
        ChoiceC = choiceC;
    }

    public String getQuestion() {
        return question;
    }

    public String getChoiceA() {
        return ChoiceA;
    }

    public String getChoiceB() {
        return ChoiceB;
    }

    public String getAnswer() {
        return answer;
    }

    public String getChoiceC() {
        return ChoiceC;
    }
}
