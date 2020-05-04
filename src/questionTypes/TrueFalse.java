package questionTypes;

import java.io.Serializable;

public class TrueFalse extends Question implements Serializable {
    private final boolean answer;

    public TrueFalse( String question, boolean answer ) {
        super(question);
        this.answer = answer;
    }

    public boolean isAnswer() { return answer; }

    @Override
    public String toString() {
        return "True/False: " + super.getQuestion() + " : " + answer + "\n";
    }
}
