package questionTypes;

import java.io.Serializable;

public class Essay extends Question implements Serializable {
    private String answer;

    public Essay( String question, String answer ) {
        super(question);
        this.answer = answer;
    }

    public String getAnswer() { return answer; }

    public void setAnswer( String answer ) {
        this.answer = answer;
    }

    @Override
    public String toString() {
        return "questionTypes.Essay: " + super.getQuestion() + " : " + answer + "\n";
    }
}
