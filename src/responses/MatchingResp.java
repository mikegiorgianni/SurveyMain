package responses;

import questionTypes.Question;

import java.util.Map;

public class MatchingResp extends QuestionResp {
    Map<Integer, String> response;

    public MatchingResp( Question question, Map<Integer, String> response ) {
        super(question);
        this.response = response;
    }

    public Map<Integer, String> getResponse() {
        return response;
    }

    public void setResponse( Map<Integer, String> response ) {
        this.response = response;
    }
}
