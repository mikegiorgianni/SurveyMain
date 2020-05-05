import questionControllers.QuestionResp;

import java.io.Serializable;
import java.util.List;

public class SurveyResponse implements Serializable {
    private Survey survey;
    private String responderName;
    private List<QuestionResp> responses;
}
