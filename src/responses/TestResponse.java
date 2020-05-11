package responses;

import controllers.Test;

import java.io.Serializable;
import java.util.List;

public class TestResponse implements Serializable {
    private final Test test;
    private final String takerName;
    private final List<QuestionResp> responses;
    private final List<QuestionResp> correctAnswers;
    private final float grade;

    public TestResponse( Test test, String takerName,
                         List<QuestionResp> responses,
                         List<QuestionResp> correctAnswers,
                         float grade ) {
        this.test = test;
        this.takerName = takerName;
        this.responses = responses;
        this.correctAnswers = correctAnswers;
        this.grade = grade;
    }

    public Test getTest() {
        return test;
    }

    public String getTakerName() {
        return takerName;
    }

    public List<QuestionResp> getResponses() {
        return responses;
    }

    public List<QuestionResp> getCorrectAnswers() {
        return correctAnswers;
    }

    public float getGrade() {
        return grade;
    }

    @Override
    public String toString() {
        return "TestResponse{" + "test=" + test.getName() +
            ", takerName=" + takerName + ", grade=" + String.format("%.1f",grade*100) + '}';
    }
}
