package questionTypes;

import java.io.Serializable;
import java.util.List;

public class MultipleChoice extends Question implements Serializable {
    private int numOfChoices;
    private int correctAnswer;
    private List<String> choices;

    public void setNumOfChoices( int numOfChoices ) {
        this.numOfChoices = numOfChoices;
    }

    public void setCorrectAnswer( int correctAnswer ) {
        this.correctAnswer = correctAnswer;
    }

    public void setChoices( List<String> choices ) {
        this.choices = choices;
    }

    public MultipleChoice( String question, int numOfChoices, int correctAnswer, List<String> choices ) {
        super(question);
        this.numOfChoices = numOfChoices;
        this.correctAnswer = correctAnswer;
        this.choices = choices;
    }

    public int getNumOfChoices() {
        return numOfChoices;
    }

    public int getCorrectAnswer() {
        return correctAnswer;
    }

    public List<String> getChoices() {
        return choices;
    }

    public void addChoice(String answer) {
        numOfChoices++;
        choices.add(answer);
    }

    @Override
    public String toString() {
        StringBuilder answer = new StringBuilder("questionTypes.MultipleChoice: ")
            .append((correctAnswer + 1)).append("\t").append(getQuestion()).append("\n");
        for ( int i = 0; i < numOfChoices; i++ ) {
            answer.append("\t").append(i + 1).append(". ").append(choices.get(i)).append("\n");
        }
        return answer.toString();
    }
}
