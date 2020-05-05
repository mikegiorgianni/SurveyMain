package questionTypes;

import java.io.Serializable;

public class ShortAnswer extends Question implements Serializable {
    private String answer;

    public ShortAnswer( String question, String answer ) {
        super(question);
        this.answer = answer;
    }

    public String getAnswer() { return answer; }

    public void setAnswer( String answer ) {
        this.answer = answer;
    }

    @Override
    public String toString() {
        return "Short answer: " + super.getQuestion() + " : " + answer + "\n";
    }
}
