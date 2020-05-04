package questionTypes;

import java.io.Serializable;

public class Essay extends Question implements Serializable {
    private final String answer;

    public Essay( String question, String answer ) {
        super(question);
        this.answer = answer;
    }

    public String getAnswer() { return answer; }

    @Override
    public String toString() {
        return "questionTypes.Essay: " + super.getQuestion() + " : " + answer + "\n";
    }
}
