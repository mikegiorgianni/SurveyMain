package responses;

import questionTypes.Question;

public class SimpleResp extends QuestionResp {
    private String response;

    public SimpleResp( Question question, String response ) {
        super(question);
        this.response = response;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse( String response ) {
        this.response = response;
    }
}
