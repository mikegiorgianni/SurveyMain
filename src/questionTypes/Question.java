package questionTypes;

import java.io.Serializable;

public class Question implements Serializable {
    private String question;

    public Question( String question ) { this.question = question; }

    public Question() {}

    public String getQuestion() { return question; }

    public void setQuestion( String question ) {
        this.question = question;
    }

    @Override
    public String toString() {
        return question;
    }
}
