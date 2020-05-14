package questionTypes;

import questionControllers.QuestionOps;

import java.io.Serializable;

import static questionTypes.QuestionType.*;

public class Question implements Serializable {
    private String question;

    public Question( String question ) { this.question = question; }

    public Question() {}

    public String getQuestion() { return question; }

    public void setQuestion( String question ) {
        this.question = question;
    }

    public QuestionOps fetchController() {
        return
            (this instanceof Essay)?          ESSAY.getController() :
                (this instanceof Matching)?       MATCHING.getController() :
                    (this instanceof MultipleChoice)? MULTIPLE_CHOICE.getController() :
                        (this instanceof ShortAnswer)?    SHORT_ANSWER.getController() :
                            (this instanceof TrueFalse)?      TRUE_FALSE.getController() :
                                VALID_DATE.getController();
    }

    @Override
    public String toString() {
        return question;
    }
}
