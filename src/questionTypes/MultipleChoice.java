package questionTypes;

import java.io.Serializable;
import java.util.List;

public class MultipleChoice extends Question implements Serializable {
    private int numOfChoices;
    private int correctAnswer;
    private List<String> choices;

    public MultipleChoice( String question, int numOfChoices, List<String> choices ) {
        super(question);
        this.numOfChoices = numOfChoices;
        this.choices = choices;
    }

    public MultipleChoice( String question, int numOfChoices, List<String> choices, int correctAnswer ) {
        super(question);
        this.numOfChoices = numOfChoices;
        this.correctAnswer = correctAnswer;
        this.choices = choices;
    }

    public int getNumOfChoices() {
        return numOfChoices;
    }

    public void setNumOfChoices( int numOfChoices ) {
        this.numOfChoices = numOfChoices;
    }

    public List<String> getChoices() {
        return choices;
    }

    public void addChoice(String answer) {
        numOfChoices++;
        choices.add(answer);
    }

    public void setChoices( List<String> choices ) {
        this.choices = choices;
    }

    public int getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer( int correctAnswer ) {
        this.correctAnswer = correctAnswer;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Multiple Choice: ")
            .append(getQuestion()).append("\n");
        for ( int i = 0; i < numOfChoices; i++ ) {
            sb.append("\t").append(i + 1).append(". ").append(choices.get(i)).append("\n");
        }
        return sb.toString();
    }
}
