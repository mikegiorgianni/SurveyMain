package entities;

import questionTypes.Question;
import responses.QuestionResp;

import java.io.Serializable;
import java.util.List;

public class SurveyResponse implements Serializable {
    private Survey survey;
    private String responderName;
    private List<Question> questions;
    private List<QuestionResp> responses;

    public SurveyResponse( Survey survey, String responderName,
                           List<Question> questions, List<QuestionResp> responses ) {
        this.survey = survey;
        this.responderName = responderName;
        this.questions = questions;
        this.responses = responses;
    }

    public Survey getSurvey() {
        return survey;
    }

    public void setSurvey( Survey survey ) {
        this.survey = survey;
    }

    public String getResponderName() {
        return responderName;
    }

    public void setResponderName( String responderName ) {
        this.responderName = responderName;
    }

    public List<Question> getQuestions() { return questions; }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    public List<QuestionResp> getResponses() {
        return responses;
    }

    public void setResponses( List<QuestionResp> responses ) {
        this.responses = responses;
    }

    public void addResponse(QuestionResp response) {
        responses.add(response);
    }
}
