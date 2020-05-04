package questionTypes;

import java.io.Serializable;

public class ShortAnswer extends Question implements Serializable {
    private final String answer;

    public ShortAnswer( String question, String answer ) {
        super(question);
        this.answer = answer;
    }

    public String getAnswer() { return answer; }

    @Override
    public String toString() {
        return "Short answer: " + super.getQuestion() + " : " + answer + "\n";
    }
}
