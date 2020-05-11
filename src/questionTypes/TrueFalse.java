package questionTypes;

import java.io.Serializable;

public class TrueFalse extends Question implements Serializable {
    private boolean answer;

    public TrueFalse( String question, boolean answer ) {
        super(question);
        this.answer = answer;
    }

    public TrueFalse( String question ) { super(question); }

    public boolean isTrue() { return answer; }

    public void setAnswer( boolean answer ) {
        this.answer = answer;
    }

    @Override
    public String toString() {
        return "True/False: " + super.getQuestion() + "\n";
    }
}
