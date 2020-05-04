package questionTypes;

import java.io.Serializable;

public class Question implements Serializable {
    private final String question;

    public Question( String question ) { this.question = question; }

    public String getQuestion() { return question; }

    @Override
    public String toString() {
        return question;
    }
}
