package responses;

import questionTypes.Question;

import java.io.Serializable;

public class QuestionResp implements Serializable {
    private Question question;

    public QuestionResp( Question question ) {
        this.question = question;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion( Question question ) {
        this.question = question;
    }
}
